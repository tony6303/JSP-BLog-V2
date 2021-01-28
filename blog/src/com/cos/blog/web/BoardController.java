package com.cos.blog.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.dto.CommonRespDto;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.ReplyService;
import com.cos.blog.util.Script;
import com.google.gson.Gson;

//http://localhost:8080/blog/board
@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public BoardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("cmd");
		BoardService boardService = new BoardService();
		ReplyService replyService = new ReplyService();
		// http://localhost:8000/blog/board?cmd=saveForm
		HttpSession session = request.getSession();
		if(cmd.equals("saveForm")) { //글 작성페이지
			User principal = (User) session.getAttribute("principal");
			if(principal != null) { //로그인 여부 확인
				RequestDispatcher dis = request.getRequestDispatcher("board/saveForm.jsp");
				dis.forward(request, response);
				//response.sendRedirect("board/saveForm.jsp");
			}else {
				RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
				dis.forward(request, response);
				//response.sendRedirect("user/loginForm.jsp");
			}	
		}else if(cmd.equals("save")) { //글 저장
			int userId = Integer.parseInt(request.getParameter("userId")); //
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			SaveReqDto dto = new SaveReqDto();
			dto.setUserId(userId);
			dto.setTitle(title);
			dto.setContent(content);
			int result = boardService.글쓰기(dto);
			if(result == 1) { //정상
				response.sendRedirect("index.jsp");
			}else {
				Script.back(response, "글쓰기실패");
			}
		}else if(cmd.equals("list")) {
			int page = Integer.parseInt(request.getParameter("page"));
			List<Board> boards = boardService.목록보기(page);
			request.setAttribute("boards", boards);
			
			int boardCount = boardService.글개수();
			int lastPage = (boardCount-1)/4;
			double currentPosition = (double)page/(lastPage)*100; //퍼센트
			
			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);
			// request에 담고
				// RequestDispathcer 만들어서 이동
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("detail")) { //게시글 상세보기 + 댓글 불러오기
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id); // board테이블+user테이블 = 조인된 데이터!!
			List<Reply> replys = replyService.댓글목록보기(id);
			if(dto == null) {
				Script.back(response, "상세보기에 실패하였습니다.");
			}else {
				request.setAttribute("replys", replys);
				request.setAttribute("dto", dto);
				//System.out.println("DetailRespDto : "+dto);
				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
				dis.forward(request, response);
			}
			
			
		}else if(cmd.equals("delete")) { //게시글 삭제
			// 1. 요청 받은 json 데이터를 자바 오브젝트로 파싱
			//공통Dto 만들면서 삭제함
//			BufferedReader br = request.getReader();
//			String data = br.readLine();
//
//			Gson gson = new Gson();
			
//			DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class);

			int id = Integer.parseInt(request.getParameter("id"));

			// 2. DB에서 id값으로 글 삭제
//			int result = boardService.글삭제(dto.getBoardId());
			int result = boardService.글삭제(id);

			// 3. 응답할 json 데이터를 생성
//			DeleteRespDto respDto = new DeleteRespDto();
//			if(result == 1) {
//				respDto.setStatus("ok");
//			}else {
//				respDto.setStatus("fail");
//			}
//			String respData = gson.toJson(respDto);
			
			CommonRespDto<String> commonRespDto = new CommonRespDto<>();
			commonRespDto.setStatusCode(result);
			commonRespDto.setData("성공");

			Gson gson = new Gson();
			String respData = gson.toJson(commonRespDto);
			System.out.println("respData : "+respData);
			PrintWriter out = response.getWriter();
			out.print(respData);
			out.flush();
		}else if(cmd.equals("updateForm")) { //게시글 수정 페이지
			int id = Integer.parseInt(request.getParameter("id"));
			DetailRespDto dto = boardService.글상세보기(id);
			request.setAttribute("dto", dto);
			RequestDispatcher dis = request.getRequestDispatcher("board/updateForm.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("update")) { //게시글 수정완료
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			UpdateReqDto dto = new UpdateReqDto();
			dto.setId(id);
			dto.setTitle(title);
			dto.setContent(content);
			
			int result = boardService.글수정(dto);
			if(result == 1) { //정상
				// 고민해보세요. 왜 RequestDispatcher 안썻는지... 한번 써보세요. detail.jsp 호출
				response.sendRedirect("index.jsp");
//				RequestDispatcher dis = request.getRequestDispatcher("board/detail.jsp");
//				dis.forward(request, response);
			}else {
				Script.back(response, "수정 실패");
			}
		}else if(cmd.equals("search")) { //게시글 검색
			String keyword = request.getParameter("keyword");
			int page = Integer.parseInt(request.getParameter("page"));

			List<Board> boards = boardService.글검색(keyword, page);
			request.setAttribute("boards", boards);

			int boardCount = boardService.글개수(keyword);
			int lastPage = (boardCount-1)/4; // 2/4 = 0, 3/4 = 0, 4/4 = 1, 9/4 = 2 ( 0page, 1page, 2page) 
			double currentPosition = (double)page/(lastPage)*100;



			request.setAttribute("lastPage", lastPage);
			request.setAttribute("currentPosition", currentPosition);
			RequestDispatcher dis = request.getRequestDispatcher("board/list.jsp");
			dis.forward(request, response);
		}
	}
}
