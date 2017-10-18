<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
    <head>
        <title>Đăng nhập</title>
        <link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
        <link type="text/css" href="/css/app.css" rel="stylesheet" />
    </head>
    <body onload='document.loginForm.username.focus();'>
        <div class="wrapper">
            <form class="form-signin" name='loginForm' action="<c:url value='/login' />" method='POST'>
                <h2 class="form-signin-heading">Vui lòng đăng nhập</h2>
                <input type="text" class="form-control" name="username" placeholder="Tài khoản"/>
                <input type="password" class="form-control" name="password" placeholder="Mật khẩu"/>
                <label class="checkbox">
                <% //<input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"> Lưu mật khẩu%>
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Đăng nhập</button>
                <input type="hidden" name="${_csrf.parameterName}"
                                    value="${_csrf.token}" />
            </form>
        </div>

        <script type="application/javascript" src="js/jquery.js"></script>
        <script type="application/javascript" src="js/bootstrap.js"></script>
    </body>
</html>