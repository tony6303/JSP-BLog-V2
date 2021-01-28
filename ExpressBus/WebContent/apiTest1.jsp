<%@page import="com.cos.bus.config.ApiExplorer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	ApiExplorer test = new ApiExplorer();
	test.findAll("1", "20", "3");
%>
<h1>ApiExplorer Test</h1>
</body> 
</html>