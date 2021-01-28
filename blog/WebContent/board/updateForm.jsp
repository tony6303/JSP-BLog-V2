<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
<%  request.getAttribute("principal"); %>

<div class="container">
	<form action="/blog/board?cmd=update" method="POST">
	
		<div class="form-group">
			<input type="hidden" id="id" name="id" value="${dto.id}">
			<label for="title">Title:</label>
			<input type="text" class="form-control" placeholder="title" id="title" name="title" value="${dto.title }">
		</div>
	
		<div class="form-group">
			<label for="content">Content:</label>
			<textarea id="summernote" class="form-control" rows="5" id="content" name="content">
				${dto.content}
			</textarea>
		</div>
	
		<button type="submit" class="btn btn-primary">수정</button>
	</form>
</div>
<script>
  	$('#summernote').summernote({
        placeholder: '글을 쓰세요.',
        tabsize: 2,
        height: 400
      });
  </script>
</body>
</html>

