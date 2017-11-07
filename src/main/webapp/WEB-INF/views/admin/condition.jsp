<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<body>

		<c:if test="${empty pageContext.request.userPrincipal.name}">
		    <c:redirect url = "/login"/>
		</c:if>

		<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN")); %>
		<c:if test="${!requestScope.isAdmin}">
			<c:redirect url = "/403"/>
		</c:if>

		<c:url value="/logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>

		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>

	</body>

</html>
