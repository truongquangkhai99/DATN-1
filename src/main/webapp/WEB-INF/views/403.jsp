<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
        <title>Lỗi truy cập</title>
        <%@ include file="head_tag.jsp"%>
    </head>

	<body>

		<h1>HTTP Status 403 - Access is denied</h1>

		<c:choose>
			<c:when test="${empty username}">
				<h2>Bạn không có quyền truy cập vào trang này!</h2>
			</c:when>
			<c:otherwise>
				<h2>Username : ${username} <br/>Bạn không có quyền truy cập vào trang này!</h2>
			</c:otherwise>
		</c:choose>

	</body>
</html>