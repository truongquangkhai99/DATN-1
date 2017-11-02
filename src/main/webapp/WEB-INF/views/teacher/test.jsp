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
	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<% request.setAttribute("isTeacher", request.isUserInRole("TEACHER")); %>
	<c:if test="${!requestScope.isTeacher}">
		<c:redirect url = "/403"/>
	</c:if>

	<form class="form-create" name='createForm' action="/teacher/test" method="POST" enctype="multipart/form-data" >
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="form-group">
			<label for="sel1">Chọn nhóm thi:</label>
			<select class="form-control" id="sel1" name="groupid">
				<c:forEach items="${groups}" var="group" varStatus="itr">
					<option>${group}</option>
				</c:forEach>
			</select>
		</div>
		<table class="table table-bordered">
			<tbody>
				<tr>
					<td>Số lượng câu hỏi:</td>
					<td><input type="text" name="timer" placeholder="Thời gian làm bài (phút)"/></td>
				</tr>
				<tr>
					<td>Duyệt file word:</td>
					<td><input type="file" name="file" accept=".doc,.docx"/></td>
				</tr>
			</tbody>
		</table>
		<div>
			<button class="btn btn-primary" type='submit'>Tạo đề thi</button>
			<c:if test="${success != null && success}">
				<div id='info-create-group' style="color: blue">Tạo đề thi thành công</div>
			</c:if>
			<c:if test="${success != null && !success}">
				<div id='info-create-group' style="color: red">Tạo đề thi không thành công</div>
			</c:if>
		</div>
	</form>

	<table class="table table-bordered">
			<tbody>
				<c:forEach items="${questions}" var="question" varStatus="itr">
					<tr>
						<td class = "question-content">${question.name}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>

	<script type="application/javascript" src="js/jquery.js"></script>
	<script type="application/javascript" src="js/bootstrap.js"></script>
</body>
</html>