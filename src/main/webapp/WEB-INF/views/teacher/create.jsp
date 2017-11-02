<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Tạo nhóm sinh viên</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
	</head>
<body>
	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<% request.setAttribute("isTeacher", request.isUserInRole("TEACHER")); %>
	<c:if test="${!requestScope.isTeacher}">
		<c:redirect url = "/403"/>
	</c:if>

	<form class="form-create" name='createForm' action="/teacher/create" method="POST" enctype="multipart/form-data" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td>Tên nhóm thi:</td>
					<td><input type="text" name="groupid" placeholder="Tên nhóm thi"/></td>
				</tr>
				<tr>
					<td>Duyệt file excel:</td>
					<td><input type="file" name="file" accept=".xls,.xlsx"/></td>
				</tr>
			</tbody>
		</table>
		<div>
			<button class="btn btn-primary" type='submit'>Tạo nhóm</button>
			<c:if test="${success != null && success}">
				<div id='info-create-group' style="color: blue">Tạo nhóm thành công</div>
			</c:if>
			<c:if test="${success != null && !success}">
				<div id='info-create-group' style="color: red">Tạo nhóm không thành công</div>
			</c:if>
		</div>
	</form>

	<script type="application/javascript" src="js/jquery.js"></script>
	<script type="application/javascript" src="js/bootstrap.js"></script>
</body>
</html>