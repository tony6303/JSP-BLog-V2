package com.cos.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ajax1")
public class Ajax1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Ajax1() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password= request.getParameter("password");
		
		System.out.println("username: "+username);
		System.out.println("password: "+password);
		
		//응답하면 
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		out.println("한글");
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}