<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Quản trị</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
		<script type="application/javascript" src="/js/jquery.js"></script>
		<script type="application/javascript" src="/js/bootstrap.js"></script>
	</head>

	<body>
		<c:if test="${empty pageContext.request.userPrincipal.name}">
			<c:redirect url = "/login"/>
		</c:if>

		<%@ include file="../header.jsp"%>

		<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN")); %>
		<c:if test="${!requestScope.isAdmin}">
			<c:redirect url = "/403"/>
		</c:if>

		<button class="btn btn-primary"><a id="bt-login" href="/admin/create">Thêm giảng viên</a></button>

		<c:url value="/logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				<button class="btn btn-primary"><a id="bt-login" href="javascript:formSubmit()">Đăng xuất</a></button>
			</h2>
		</c:if>

		<%@ include file="../footer.jsp"%>

		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>

	</body>

</html>