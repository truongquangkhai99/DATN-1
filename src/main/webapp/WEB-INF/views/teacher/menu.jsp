<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >
<html>
	<body>

		<div class="col-md-4">
			<div id="MainMenu">
				<div class="list-group panel">
					<a href="#manager-teacher" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Quản lý giảng viên<img src="/image/arrow_down.png" class="arrow-down"></a>
					<div class="collapse" id="manager-teacher">
						<a href="/teacher/info" class="list-group-item">Thông tin chung</a>
						<a href="#" class="list-group-item">Thay đổi thông tin</a>
					</div>
					<a href="#group-student" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Quản lý nhóm sinh viên<img src="/image/arrow_down.png" class="arrow-down"></a>
					<div class="collapse" id="group-student">
						<a href="#" class="list-group-item">Thông tin nhóm sinh viên</a>

						<a href="#group-type" class="list-group-item" data-toggle="collapse" data-parent="#group-student">Thêm nhóm sinh viên<img src="/image/arrow_down.png" class="arrow-down"></a>
						<div class="collapse" id="group-type">
							<a href="/teacher/create" class="list-group-item list-group-item-warning" data-parent="#group-type"><img src="/image/arrow_right.png" class="arrow-right">Thêm từng nhóm</a>
							<a href="/teacher/create_all" class="list-group-item list-group-item-warning" data-parent="#group-type"><img src="/image/arrow_right.png" class="arrow-right">Thêm toàn bộ</a>
						</div>

						<a href="#" class="list-group-item" >Sửa nhóm sinh viên</a>
						<a href="#" class="list-group-item">Xóa nhóm sinh viên</a>
					</div>

					<a href="#examination" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Quản lý đề thi<img src="/image/arrow_down.png" class="arrow-down"></a>
					<div class="collapse" id="examination">
						<a href="/teacher/preview" class="list-group-item">Xem đề thi</a>

						<a href="#group-type-test" class="list-group-item" data-toggle="collapse" data-parent="#examination">Thêm đề thi<img src="/image/arrow_down.png" class="arrow-down"></a>
						<div class="collapse" id="group-type-test">
							<a href="/teacher/test" class="list-group-item list-group-item-warning" data-parent="#group-type-test"><img src="/image/arrow_right.png" class="arrow-right">Thêm từng nhóm</a>
							<a href="/teacher/test_all" class="list-group-item list-group-item-warning" data-parent="#group-type-test"><img src="/image/arrow_right.png" class="arrow-right">Thêm toàn bộ</a>
						</div>

						<a href="#" class="list-group-item">Sửa đề thi</a>
						<a href="#" class="list-group-item">Xóa đề thi</a>
					</div>

					<a href="#ouput-file" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Xuất theo file đầu vào<img src="/image/arrow_down.png" class="arrow-down"></a>
					<div class="collapse" id="ouput-file">
						<a href="/teacher/output" class="list-group-item">Xuất theo nhóm</a>
						<a href="/teacher/output_all" class="list-group-item">Xuất toàn bộ</a>
					</div>

					<a href="#ouput-file-test" class="list-group-item list-group-item-success" data-toggle="collapse" data-parent="#MainMenu">Xuất theo bảng điểm<img src="/image/arrow_down.png" class="arrow-down"></a>
					<div class="collapse" id="ouput-file-test">
						<a href="/teacher/output_test" class="list-group-item">Xuất theo nhóm</a>
						<a href="/teacher/output_all_test" class="list-group-item">Xuất toàn bộ</a>
					</div>

					<c:if test="${pageContext.request.userPrincipal.name != null}">
						<a href="javascript:formSubmit()" class="list-group-item list-group-item-success" data-parent="#MainMenu">Thoát</a>
					</c:if>
				</div>
			</div>
		</div>

	</body>
</html>

