package com.cos.bus.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.cos.bus.domain.BusInfo;
import com.cos.bus.service.BusInfoService;

//http://localhost:8080/ExpressBus/businfo
@WebServlet("/businfo")
public class BusInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public BusInfoController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doProcess(request, response);
		} catch (ServletException | IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doProcess(request, response);
		} catch (ServletException | IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParserConfigurationException, SAXException {
		BusInfoService busInfoService = new BusInfoService();
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String cmd = request.getParameter("cmd");
		HttpSession session = request.getSession();
		
		if(cmd.equals("result")) { //
			String arrPlaceNm = request.getParameter("arrTerminalId");
			String depPlaceNm = request.getParameter("depTerminalId");
			String depTime = request.getParameter("depPlandTime");
			
			List<BusInfo> busInfos = busInfoService.버스조회(depPlaceNm, arrPlaceNm, depTime);
			request.setAttribute("busInfos", busInfos);
			System.out.println(busInfos);
			
			RequestDispatcher dis = request.getRequestDispatcher("businfo/result.jsp");
			dis.forward(request, response);
		}
	}
}
