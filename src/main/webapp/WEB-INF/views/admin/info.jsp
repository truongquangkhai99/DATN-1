<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Quản trị - Thông tin chung</title>
		<%@ include file="../head_tag.jsp"%>
	</head>

	<body>

		<%@ include file="../header.jsp"%>

		<div class="container-fluid" style="margin-top: 10px">
					<div class="row">
						<%@ include file="menu.jsp"%>

						<div class="col-md-8">
							<div class="bs-example well">
								<table class="table">
									<tbody>
										<tr>
											<th scope="row">Tài khoản:</th>
											<td><c:out value="${username}"/></td>
										</tr>
										<tr>
											<th scope="row">Số lượng giảng viên:</th>
											<td><c:out value="${countTeacher}"/></td>
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
							</div>
						</div>
					</div>
				</div>

		<%@ include file="../footer.jsp"%>

		<%@ include file="condition.jsp"%>

	</body>

</html>