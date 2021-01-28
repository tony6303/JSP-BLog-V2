<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script
  src="https://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<button onclick="getajax()">클릭</button>
</body>

<script>
function getajax(){
	$.ajax({
		//GET으로 key=value로 데이터를 전달하고 text/plain으로 응답받을 예정
		type:"GET",
		url:"http://localhost:8000/ajax/ajax1?username=ssar&password=1234",
		//data: , get은 전송할 http의 body가 없음, 그래서 data필드가 필요없음
		//contentType: ,전송한 data가 없으니까 그 data를 설명한 필드도 필요없음
		dataType: "text" 
	})
	.done(function(result){ //ajax통신 완료후에 정상이면 실행
		alert(result);
		});
}
	
</script>
</html>