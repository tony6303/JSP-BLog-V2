<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//response.sendRedirect("board?cmd=list");
	//response.sendRedirect("board/list.jsp");
	RequestDispatcher dis = request.getRequestDispatcher("board?cmd=list&page=0");
	dis.forward(request, response);
%>