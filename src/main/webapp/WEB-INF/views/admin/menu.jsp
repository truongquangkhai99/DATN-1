<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >
<html>
	<body>

		<div class="col-md-4">
			<div id="MainMenu">
				<div class="list-group panel">
					<a href="#admin-menu" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Quản lý Admin<img src="/image/arrow_down.png" class="arrow-down"></a>
					<div class="collapse" id="admin-menu">
						<a href="/admin/info" class="list-group-item">Thông tin chung</a>
						<a href="/admin/changeinfo" class="list-group-item">Thay đổi thông tin</a>
					</div>

					<a href="#admin-teacher" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Quản lý giảng viên<img src="/image/arrow_down.png" class="arrow-down"></a>
					<div class="collapse" id="admin-teacher">
						<a href="/admin/info_teacher" class="list-group-item">Thông tin giảng viên</a>
						<a href="/admin/create" class="list-group-item">Thêm giảng viên</a>
						<a href="/admin/edit_pass_teacher" class="list-group-item">Cấp lại mật khẩu giảng viên</a>
						<a href="#group-type" class="list-group-item" data-toggle="collapse" data-parent="#admin-menu">Xóa giảng viên<img src="/image/arrow_down.png" class="arrow-down"></a>
						<div class="collapse" id="group-type">
							<a href="/admin/delete_logic" class="list-group-item list-group-item-warning" data-parent="#group-type"><img src="/image/arrow_right.png" class="arrow-right">Xóa logic</a>
							<a href="/admin/delete_physic" class="list-group-item list-group-item-warning" data-parent="#group-type"><img src="/image/arrow_right.png" class="arrow-right">Xóa vật lý</a>
						</div>
					</div>
					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<a href="javascript:formSubmit()" class="list-group-item list-group-item-success" data-parent="#MainMenu">Thoát</a>
					</c:if>
				</div>
			</div>
		</div>

	</body>
</html>

