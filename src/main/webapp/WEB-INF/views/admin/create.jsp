<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Quản trị - Thêm giảng viên</title>
		<%@ include file="../head_tag.jsp"%>
	</head>
	<body>

		<%@ include file="../header.jsp"%>

		<div class="container-fluid" style="margin-top: 10px">
			<div class="row">
				<%@ include file="menu.jsp"%>

				<div class="col-md-8">
					<div class="bs-example well">
						<form class="form-create" name='createForm' action="/admin/create" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="form-group">
								<label for="inputName">Tên giảng viên:</label>
								<input type="input" class="form-control" id="inputName" name="name" placeholder="Tên giảng viên">
							</div>
							<div class="form-group">
								<label for="inputAccount">Tài khoản:</label>
								<input type="input" class="form-control" id="inputAccount" name="account" placeholder="Tài khoản">
							</div>
							<div class="form-group">
								<label for="inputPassword">Mật khẩu:</label>
								<input type="input" class="form-control" id="inputPassword" name="password" placeholder="Mật khẩu">
							</div>

							<div>
								<button type="submit" class="btn btn-primary">Tạo</button>
								<button type="submit" class="btn btn-primary" style="float: right"><a id="bt-login" href="/admin">Hủy</a></button>
							</div>
							<c:if test="${success != null && success}">
								<div style="color: blue">Thêm giảng viên thành công</div>
							</c:if>
							<c:if test="${success != null && !success}">
								<div style="color: red">Thêm giảng viên không thành công</div>
							</c:if>
						</form>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="../footer.jsp"%>

		<%@ include file="condition.jsp"%>

	</body>
</html>