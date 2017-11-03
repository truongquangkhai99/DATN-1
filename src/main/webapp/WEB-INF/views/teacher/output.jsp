<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.ArrayList" %>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Tạo nhóm đề thi</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
		<script type="application/javascript" src="/js/jquery.js"></script>
		<script type="application/javascript" src="/js/bootstrap.js"></script>
	</head>
<body>
	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<% request.setAttribute("isTeacher", request.isUserInRole("TEACHER")); %>
	<c:if test="${!requestScope.isTeacher}">
		<c:redirect url = "/403"/>
	</c:if>

	<form class="form-create" name='createForm' action="/teacher/output" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="form-group">
			<label for="sel1">Chọn nhóm thi:</label>
			<select class="form-control" id="sel1" name="groupid">
				<c:forEach items="${groups}" var="group" varStatus="itr">
					<option>${group}</option>
				</c:forEach>
			</select>
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td>Tên file:</td>
						<td><input type="text" name="fileName" placeholder="Tên file cần xuất ra"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<button class="btn btn-primary" type='submit'>Xuất ra file Excel</button>
	</form>

</body>
</html>