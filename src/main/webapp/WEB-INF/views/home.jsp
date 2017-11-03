<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Trang chủ</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
		<script type="application/javascript" src="/js/jquery.js"></script>
        <script type="application/javascript" src="/js/bootstrap.js"></script>
	</head>
	<body>
		<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN")); %>
		<c:if test="${requestScope.isAdmin}">
			<c:redirect url = "/admin"/>
		</c:if>

		<% request.setAttribute("isTeacher", request.isUserInRole("TEACHER")); %>
		<c:if test="${requestScope.isTeacher}">
			<c:redirect url = "/teacher"/>
		</c:if>

		<% request.setAttribute("isStudent", request.isUserInRole("STUDENT")); %>
		<c:if test="${requestScope.isStudent}">
			<c:redirect url = "/student"/>
		</c:if>

		<%@ include file="header.jsp"%>

		<div style="text-align: center; margin-top: 20px">
			<h3> Chào mừng các bạn đến với hệ thống thi trắc nghiệm Tin học Đại cương - Trường Đại học Bách khoa Đà Nẵng </h3>
			<button class="btn btn-primary"><a id="bt-login" href="/login">Đăng nhập</a></button>
		</div>

		<%@ include file="footer.jsp"%>

	</body>
</html>