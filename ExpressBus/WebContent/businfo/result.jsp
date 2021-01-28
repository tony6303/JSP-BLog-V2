<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>운행정보</title>
</head>
<body>
	<h1>운행정보</h1>
	<table border="1">
		<tr>
			<th>출발지</th>
			<th>도착지</th>
			<th>요금</th>
			<th>등급</th>
			<th>출발시간</th>
			<th>도착시간</th>
		</tr>
		<c:forEach var="busInfo" items="${busInfos }">
			<tr>
				<td><c:out value="${busInfo.depPlaceNm }"></c:out></td>
				<td><c:out value="${busInfo.arrPlaceNm }"></c:out></td>
				<td><c:out value="${busInfo.charge }"></c:out></td>
				<td><c:out value="${busInfo.gradeNm }"></c:out></td>
				<td><c:out value="${busInfo.depPlandTime }"></c:out></td>
				<td><c:out value="${busInfo.arrPlandTime }"></c:out></td>
			</tr>
		</c:forEach>
		
	</table>
</body>
</html>