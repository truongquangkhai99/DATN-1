<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Quản trị</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
	</head>
<body>
	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN")); %>
	<c:if test="${!requestScope.isAdmin}">
		<c:redirect url = "/403"/>
	</c:if>

	<form class="form-create" name='createForm' action="/admin/create" method="POST">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td>Tên giảng viên:</td>
					<td><input type="text" name="name" placeholder="Tên giảng viên"/></td>
				</tr>
				<tr>
					<td>Tài khoản:</td>
					<td><input type="text" name="account" placeholder="Tài khoản"/></td>
				</tr>
				<tr>
					<td>Mật khẩu:</td>
					<td><input type="text" name="password" placeholder="Mật khẩu"/></td>
				</tr>
			</tbody>
		</table>

		<button class="btn btn-primary type="submit">Tạo giảng viên</button>
		<c:if test="${success != null && success}">
			<div style="color: blue">Thêm giảng viên thành công</div>
		</c:if>
		<c:if test="${success != null && !success}">
			<div style="color: red">Thêm giảng viên không thành công</div>
		</c:if>
	</form>

	<script type="application/javascript" src="js/jquery.js"></script>
	<script type="application/javascript" src="js/bootstrap.js"></script>
</body>
</html>