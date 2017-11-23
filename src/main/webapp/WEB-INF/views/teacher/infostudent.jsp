<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Giảng viên - Thông tin nhóm sinh viên</title>
		<%@ include file="../head_tag.jsp"%>
	</head>
	<body>

		<%@ include file="../header.jsp"%>

		<div class="container-fluid" style="margin-top: 10px">
			<div class="row">
				<%@ include file="menu.jsp"%>

				<div class="col-md-8">
					<div class="bs-example well">
						<form class="form-create" name='createForm' action="/teacher/infostudent" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<label for="sel1">Chọn nhóm sinh viên:</label>
							<select class="form-control" id="sel1" name="group">
								<c:forEach items="${groups}" var="group" varStatus="itr">
									<option>${group}</option>
								</c:forEach>
							</select>

							<c:if test="${success != null && success}">
								<table class="table" style="margin-top: 5px">
									<tbody>
										<tr>
											<th scope="row">Tên nhóm sinh viên:</th>
											<td><c:out value="${nameGroup}"/></td>
										</tr>
										<tr>
											<th scope="row">Số lượng sinh viên:</th>
											<td><c:out value="${countStudent}"/></td>
										</tr>
										<tr>
											<th scope="row">Số lượng sinh viên đã thi:</th>
											<td><c:out value="${countStudentTested}"/></td>
										</tr>
										<tr>
											<th scope="row">Số lượng sinh viên chưa thi:</th>
											<td><c:out value="${countStudentNotTested}"/></td>
										</tr>
									</tbody>
								</table>
							</c:if>

							<div style="margin-top: 5px">
								<button type="submit" class="btn btn-primary">Xem</button>
								<button type="submit" class="btn btn-primary" style="float: right"><a id="bt-login" href="/teacher">Hủy</a></button>
							</div>

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