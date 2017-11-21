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
						<form class="form-create" name='createForm' action="/admin/changeinfo" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="form-group">
								<label for="inputName">Tên tài khoản mới:</label>
								<input type="input" class="form-control" id="inputName" name="account" placeholder="Tên TK mới">
							</div>
							<div class="form-group">
								<label for="inputAccount">Mật khẩu cũ:</label>
								<input type="password" class="form-control" id="inputAccount" name="oldpass" placeholder="Mật khẩu cũ">
							</div>
							<div class="form-group">
								<label for="inputPassword1">Mật khẩu mới:</label>
								<input type="password" class="form-control" id="inputPassword1" name="newpass" placeholder="Mật khẩu mới">
							</div>

							<div class="form-group">
								<label for="inputPassword2">Nhập lại Mật khẩu mới:</label>
								<input type="password" class="form-control" id="inputPassword2" name="renewpass" placeholder="Nhập lại mật khẩu mới">
							</div>

							<div>
								<button type="submit" class="btn btn-primary">Thay đổi</button>
								<button type="submit" class="btn btn-primary" style="float: right"><a id="bt-login" href="/admin">Hủy</a></button>
							</div>
							<c:if test="${success != null && success}">
								<div style="color: blue">Thay đổi thông tin thành công</div>
							</c:if>
							<c:if test="${success != null && !success}">
								<div style="color: red"><c:out value="${error_message}"/></div>
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