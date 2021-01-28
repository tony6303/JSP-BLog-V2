package com.cos.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/ajax3")
public class Ajax3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Ajax3() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//응답하면 
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		out.println("한글");
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader br = request.getReader();
		String requestData = br.readLine();
		System.out.println("(JSON)POSTdata: "+requestData);
		
		Gson gson = new Gson();
		//gson.fromJson() : json을 자바오브젝트로
		//gson.toJson() : 자바오브젝트를 json으로 
		UserDto userDto = gson.fromJson(requestData, UserDto.class);
		
		//toString()
		System.out.println("(Object)"+userDto);
		
		User user = new User();
		user.setId(1);
		user.setUsername("cos");
		user.setPassword("1234");
		user.setPhone("0102222");
		
		String jsonUser = gson.toJson(user);
		System.out.println("(JSON)jsonUser: "+jsonUser);
		
		PrintWriter out = response.getWriter();
		out.println(jsonUser);
		out.flush();
		
	}

}
