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
<button onclick="jsonajax()">클릭</button>
</body>

<script>
//자바스크립트 오브젝트
var data={
	username:"ssar",
	password:"1234"
}
/*
 *Class data{
 *	private String username = "ssar";
 *	private String password = "1234";
 *} 
 */
console.log(data);
console.log(JSON.stringify(data));
 
function jsonajax(){
	$.ajax({
		//POST로 key=value로 데이터를 전달하고 JSON으로 응답받을 예정
		type:"POST",
		url:"http://localhost:8000/ajax/ajax3",
		//stringify() 자바스크립트오브젝트 -> JSON 으로 변경
		data: JSON.stringify(data),
		contentType: "application/x-www-form-urlencoded",
		dataType: "json" 
		//현재 Ajax2 서블릿에서 setContentType을 json으로 해주어서 json이 되었지만
		//서버가 뭐 어떻게되있는지 알 수 없으니까 여기에 직접 작성하는것을 추천
	})
	.done(function(result){ //ajax통신 완료후에 정상이면 실행
		console.log(result);
		console.log(result.username);
		});
}
	
</script>
</html>