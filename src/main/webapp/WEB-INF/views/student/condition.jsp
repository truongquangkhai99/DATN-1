<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<!DOCTYPE html >

<html>

	<%
		request.setAttribute("isStudent", request.isUserInRole("STUDENT"));
	%>
	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<c:if test="${!requestScope.isStudent}">
		<c:redirect url = "/403"/>
	</c:if>

</html>