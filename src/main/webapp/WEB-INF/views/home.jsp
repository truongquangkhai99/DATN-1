<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Trang chủ</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
	</head>
	<body>
		<div id="home-content">
			<h1 class="home-text-display">TRƯỜNG ĐẠI HỌC BÁCH KHOA ĐÀ NẴNG</h1>
			<h2 class="home-text-display">KHOA CÔNG NGHỆ THÔNG TIN</h2>
			<button class="btn btn-primary"><a id="bt-login" href="/login">Đăng nhập</a></button>

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

			<script type="application/javascript" src="js/jquery.js"></script>
			<script type="application/javascript" src="js/bootstrap.js"></script>
		</div>
	</body>
</html>