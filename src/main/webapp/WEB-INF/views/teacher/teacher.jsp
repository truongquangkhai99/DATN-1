<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Giảng viên</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
	</head>
<body>
	<h1>Title : ${title}</h1>

	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
		<button class="btn btn-primary"><a id="bt-login" href="/teacher/create">Tạo nhóm thi</a></button>
		<h2>
			<a href="javascript:formSubmit()"> Logout</a>
		</h2>

	<script type="application/javascript" src="js/jquery.js"></script>
	<script type="application/javascript" src="js/bootstrap.js"></script>
</body>
</html>