<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Sinh viên</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
		<script type="application/javascript" src="/js/jquery.js"></script>
		<script type="application/javascript" src="/js/bootstrap.js"></script>
	</head>
<body>
	<%
		boolean isTested = false;
		if(request.getAttribute("isTested") != null) {
			isTested = (boolean)request.getAttribute("isTested");
		}
		request.setAttribute("isStudent", request.isUserInRole("STUDENT"));
	%>

	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<c:if test="${!requestScope.isStudent}">
		<c:redirect url = "/403"/>
	</c:if>

	<div id='info-is-tested' style="font-weight: bold; font-size: 16px"></div>

	<button id="student-test" class="btn btn-primary"><a id="bt-login" href="/student/test">Làm bài thi</a></button>

	<form action="/logout" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			<button class="btn btn-primary"><a id="bt-login" href="javascript:formSubmit()">Đăng xuất</a></button>
		</h2>
	</c:if>

	<script>
		if(<%=isTested%> === true) {
			document.getElementById("student-test").style.display = 'none';
			document.getElementById("info-is-tested").innerHTML  = 'BẠN ĐÃ LÀM BÀI THI !';
			document.getElementById("info-is-tested").style.color = 'RED';
		} else {
			document.getElementById("info-is-tested").innerHTML  = 'BẠN CHƯA LÀM BÀI THI !';
			document.getElementById("info-is-tested").style.color = 'BLUE';
		}

		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

</body>
</html>