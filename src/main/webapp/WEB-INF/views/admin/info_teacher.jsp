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
						<form class="form-create" name='createForm' action="/admin/info_teacher" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<label for="sel1">Chọn giảng viên:</label>
							<select class="form-control" id="sel1" name="teacher">
								<c:forEach items="${teachers}" var="teacher" varStatus="itr">
									<option>${teacher}</option>
								</c:forEach>
							</select>

							<c:if test="${success != null && !success}">
								<div style="color: red"><c:out value="${error_message}"/></div>
							</c:if>
							<c:if test="${success != null && success}">
								<table class="table" style="margin-top: 5px">
									<tbody>
										<tr>
											<th scope="row">Tên giảng viên:</th>
											<td><c:out value="${nameTeacher}"/></td>
										</tr>
										<tr>
											<th scope="row">Số lượng nhóm thi:</th>
											<td><c:out value="${countGroup}"/></td>
										</tr>
										<tr>
											<th scope="row">Số lượng sinh viên:</th>
											<td><c:out value="${countStudent}"/></td>
										</tr>
									</tbody>
								</table>
							</c:if>

							<div style="margin-top: 5px">
								<button type="submit" class="btn btn-primary">Xem</button>
								<button type="submit" class="btn btn-primary" style="float: right"><a id="bt-login" href="/admin">Hủy</a></button>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="../footer.jsp"%>

		<%@ include file="condition.jsp"%>

	</body>
</html>