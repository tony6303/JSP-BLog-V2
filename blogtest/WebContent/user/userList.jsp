<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>
<%
	request.getAttribute("principal");
%>
<h4>현재 접속중인 아이디 : "${sessionScope.principal.username }"</h4>
<div class="container">
	<div class="row">
		<div class="card col-sm-2">
			아이디		
		</div>
		<div class="card col-sm-2">
			비밀번호
		</div>
		<div class="card col-sm-3">
			이메일
		</div>
		<div class="card col-sm-1">
			역할
		</div>
		<div class="card col-sm-1">
			삭제
		</div>
	</div>
	
	
	<!-- JSTL foreach문을 써서 뿌리세요. el표현식과 함께 -->
	<c:choose>
		<c:when test="${sessionScope.principal.userRole == 'ADMIN' }">
			<c:forEach var="user" items="${users }">
				<div class="row">
					<div class="card col-sm-2">
						${user.username }
					</div>
					<div class="card col-sm-2">
						${user.password }
					</div>
					<div class="card col-sm-3">
						${user.email }
					</div>
					<div class="card col-sm-1">
						${user.userRole }
					</div>
					<div class="card col-sm-1">
						<i onclick="deleteUser(${user.id})" class="material-icons">delete</i>
					</div>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:forEach var="user" items="${users }">
				<div class="row" id="row-${user.id }">
					<div class="card col-sm-2">
						${user.username }
					</div>
					<div class="card col-sm-2">
						${user.password }
					</div>
					<div class="card col-sm-3">
						${user.email }
					</div>
					<div class="card col-sm-1">
						${user.userRole }
					</div>
					<div class="card col-sm-1">
						<c:if test="${sessionScope.principal.username == user.username }">
							<i onclick="deleteUser(${user.id})" class="material-icons">delete</i>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<br />
</div>
<script>
function deleteUser(id){
	// 세션의 유저의 id와 reply의 userId를 비교해서 같을때만!!
	$.ajax({
		type : "post",
		url : "/blogtest/user?cmd=delete&id="+id,
		dataType : "json"
	}).done(function(result) { //  { "statusCode" : 1 }
		if (result.statusCode == 1) {
			console.log(result);
			$("#row-"+id).remove();
			location.reload();
		} else {
			alert("유저삭제 실패");
		}
	});
}
</script>
</body>
</html>