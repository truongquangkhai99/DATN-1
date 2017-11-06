<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<%@ include file="../head_tag.jsp"%>
	<body>

		<%@ include file="../header.jsp"%>

		<div class="container-fluid">
			<div class="row">
				<%@ include file="menu_admin.jsp"%>

				<div class="col-md-8">
					<form class="form-create" name='createForm' action="/admin/create" method="POST">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td>Tên giảng viên:</td>
									<td><input type="text" name="name" placeholder="Tên giảng viên"/></td>
								</tr>
								<tr>
									<td>Tài khoản:</td>
									<td><input type="text" name="account" placeholder="Tài khoản"/></td>
								</tr>
								<tr>
									<td>Mật khẩu:</td>
									<td><input type="text" name="password" placeholder="Mật khẩu"/></td>
								</tr>
							</tbody>
						</table>

						<button class="btn btn-primary type="submit">Tạo giảng viên</button>
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

		<%@ include file="../footer.jsp"%>

		<%@ include file="content_admin.jsp"%>

	</body>
</html>