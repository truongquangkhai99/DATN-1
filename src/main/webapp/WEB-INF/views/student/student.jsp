<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
	    <title>Sinh viên</title>
	    <%@ include file="../head_tag.jsp"%>
	</head>
	<body>

		<%@ include file="../header.jsp"%>

		<%
			boolean isTested = false;
			if(request.getAttribute("isTested") != null) {
				isTested = (boolean)request.getAttribute("isTested");
			}
		%>

		<div style="margin-top: 20px">

			<div id='info-is-tested' style="font-weight: bold; font-size: 16px"></div>

			<button id="student-test" style="margin-top: 10px" class="btn btn-primary"><a id="bt-login" href="/student/test">Làm bài thi</a></button>

			<form action="/logout" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>

			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h2>
					<button class="btn btn-primary"><a id="bt-login" href="javascript:formSubmit()">Đăng xuất</a></button>
				</h2>
			</c:if>

		</div>

		<%@ include file="condition.jsp"%>

		<script>
			if(<%=isTested%> === true) {
				document.getElementById("student-test").style.display = 'none';
				document.getElementById("info-is-tested").innerHTML  = 'BẠN ĐÃ HOÀN THÀNH BÀI THI!';
				document.getElementById("info-is-tested").style.color = 'RED';
			} else {
				document.getElementById("info-is-tested").innerHTML  = 'BẠN CHƯA HOÀN THÀNH BÀI THI!';
				document.getElementById("info-is-tested").style.color = 'BLUE';
			}

			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>

		<%@ include file="../footer.jsp"%>

	</body>
</html>