<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Tạo nhóm đề thi</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
	</head>
<body>
	<%-- <c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<% request.setAttribute("isTeacher", request.isUserInRole("TEACHER")); %>
	<c:if test="${!requestScope.isTeacher}">
		<c:redirect url = "/403"/>
	</c:if> --%>

	<form>
		<div class="form-group">
			<label for="sel1">Chọn nhóm thi:</label>
			<select class="form-control" id="sel1" name="groupid">
				<c:forEach items="${groups}" var="group" varStatus="itr">
					<option>${group}</option>
				</c:forEach>
			</select>
		</div>
		<button class="btn btn-primary" type='submit'>Xem</button>
</form>

	<table class="table table-bordered">
		<tbody>
			<c:forEach items="${examinations}" var="examination" varStatus="itr">
				<tr>
					<td class = "question-content">${examination.name}</td>
					<td>${examination.name}</td>
					<td>${person.email}</td>
					<td>${person.mobile}</td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>

	</table>

	<script type="application/javascript" src="js/jquery.js"></script>
	<script type="application/javascript" src="js/bootstrap.js"></script>
</body>
</html>