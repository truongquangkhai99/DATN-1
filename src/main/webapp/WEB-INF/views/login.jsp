<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Đăng nhập</title>
		<%@ include file="../head_tag.jsp"%>
	</head>

	<body onload='document.loginForm.username.focus();'>

		<%@ include file="header.jsp"%>

		<div class="wrapper">
			<form class="form-signin" name='loginForm' action="<c:url value='/login' />" method='POST'>
				<c:if test="${param.error != null}">
					<div class="alert alert-danger">
						<p>Tài khoản hoặc mật khẩu không đúng. Xin nhập lại!</p>
					</div>
				</c:if>
				<h2 class="form-signin-heading">Đăng nhập</h2>
				<input type="text" class="form-control" name="username" placeholder="Tài khoản"/>
				<input type="password" class="form-control" name="password" placeholder="Mật khẩu" style="margin-top: 10px;"/>
				<label class="checkbox">
				<% //<input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"> Lưu mật khẩu%>
				</label>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Đăng nhập</button>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>

		<%@ include file="footer.jsp"%>

	</body>

</html>