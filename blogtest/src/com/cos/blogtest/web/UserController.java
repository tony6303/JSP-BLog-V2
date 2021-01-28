package com.cos.blogtest.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.blogtest.domain.user.User;
import com.cos.blogtest.domain.user.dto.CommonRespDto;
import com.cos.blogtest.domain.user.dto.JoinReqDto;
import com.cos.blogtest.domain.user.dto.LoginReqDto;
import com.cos.blogtest.service.UserService;
import com.cos.blogtest.util.Script;
import com.google.gson.Gson;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserService();
		String cmd = request.getParameter("cmd");

		// http://localhost:8000/blogtest/user?cmd=loginForm
		if(cmd.equals("loginForm")) { //로그인 페이지
			RequestDispatcher dis = request.getRequestDispatcher("user/loginForm.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("login")) { //로그인 완료
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			LoginReqDto dto = new LoginReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			
			User userEntity = userService.로그인(dto);
			if(userEntity != null) {
				HttpSession session = request.getSession();
				session.setAttribute("principal", userEntity); //인증주체
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp"); //로그인 성공
				dis.forward(request, response);
			}else {
				Script.back(response, "로그인 실패");
			}
			
			
		}else if(cmd.equals("joinForm")) { //회원가입 페이지
			RequestDispatcher dis = request.getRequestDispatcher("user/joinForm.jsp");
			dis.forward(request, response);
			
		}else if(cmd.equals("join")) { //회원가입 완료
			// 서비스 호출
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			JoinReqDto dto = new JoinReqDto();
			dto.setUsername(username);
			dto.setPassword(password);
			dto.setEmail(email);
			System.out.println("회원가입 : "+dto);
			int result = userService.회원가입(dto); //DB와 통신함 (service -> Dao)
			if(result == 1) {
				RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
				dis.forward(request, response);
			}else {
				Script.back(response, "회원가입 실패 -1");
			}
		}else if(cmd.equals("logout")) { //로그아웃
			HttpSession session = request.getSession();
			session.invalidate(); //즉시 세션 만료
			RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("main")) {
			RequestDispatcher dis = request.getRequestDispatcher("user/userMain.jsp");
			dis.forward(request, response);
		}else if(cmd.equals("list")) {
			List<User> users = userService.목록보기();
			request.setAttribute("users", users);
			
			
			RequestDispatcher dis = request.getRequestDispatcher("user/userList.jsp");			
			dis.forward(request, response);
		}
		
		else if(cmd.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = userService.유저삭제(id);

			CommonRespDto commonDto = new CommonRespDto<>();
			commonDto.setStatusCode(result);  //1, -1

			Gson gson = new Gson();
			String jsonData = gson.toJson(commonDto);
			// { "statusCode" : 1 }
			Script.responseData(response, jsonData);
		}
	}
}
