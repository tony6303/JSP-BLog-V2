<%@page import="java.net.URLDecoder"%>
<%@page import="jdk.internal.misc.FileSystemOption"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.css" />


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<body>

	<div class="jumbotron text-center">
		<h1>고속버스 조회하기</h1>

	</div>

	<%
		String serviceKey = "lm5Rn%2BrBLMSh%2F6ttTvkPQMpci6a7OgyXiIDbg%2BhszsGlhwdWfebxdTLLZmOixK4oCgarJHor47NPjb8PGSJfPQ%3D%3D";
		String decodeKey = URLDecoder.decode(serviceKey, "utf-8");
	%>
	<div class="container">
		<!-- action="http://openapi.tago.go.kr/openapi/service/ExpBusInfoService/getStrtpntAlocFndExpbusInfo?" -->
		<form action="/ExpressBus/businfo?cmd=result" method="post"
			onsubmit="Mapping();">
			<h1>정보입력</h1>

			<input type="hidden" name="serviceKey" value=<%=decodeKey%>>
			<div class="row">
				<div class="col-sm">
					출발지 : <input type="text" name="depTerminalId" id="depPland"
						readonly="readonly" disabled="disabled">
				</div>
				<div class="col-sm">
					도착지 : <input type="text" name="arrTerminalId" id="arrPland"
						readonly="readonly" disabled="disabled">
				</div>
				<div class="col-sm">
					출발날짜 : <input type="text" name="depPlandTime" id="date3" size="12"
						readonly="readonly" />
					<button id="submit" onclick="timeMapping();">조회</button>
				</div>
			</div>

		</form>


	</div>

	<div class="container">
		<div class="row">
			<div class="col-sm">
				<button id="NAEK300">대전복합=300</button>
			</div>
			<div class="col-sm">
				<button id="NAEK010">서울경부=010</button>
			</div>
			<div class="col-sm"></div>
		</div>
	</div>

</body>
</html>

<script>
	// dropdown 구현하려다가 안쓰게 된 함수
	/*
	$('#example .dropdown-menu li > a').bind(
		'click',
		function(e) {
			var html = $(this).html();
			$('#example button.dropdown-toggle').html(
					html + ' <span class="caret"></span>');
		});
	 */

	// DatePicker 속성 설정
	$("#date3").datepicker({
		changeYear : true,
		changeMonth : true,
		dateFormat : "yy-mm-dd"
	});

	function Mapping() {
		$("#depPland").val("NAEK300");
		$("#arrPland").val("NAEK010");
	}

	// DatePicker로 가져온 날짜에 "-" 문자를 지워주는 함수
	function timeMapping() {
		var viewText = $("#date3").val();

		var regEx = new RegExp("-", "gi");
		$("#date3").val(viewText.replace(regEx, ""));

	}
	$("#NAEK300").click(function() {
		$("#depPland").val("대전복합");
		$("#depPland").removeAttr("disabled");
	});

	$("#NAEK010").click(function() {
		$("#arrPland").val("서울경부");
		$("#arrPland").removeAttr("disabled");
	});
</script>
<script src="js/datepicker-ko.js"></script>
