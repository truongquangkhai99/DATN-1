<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Giảng viên - Xuất tất cả các nhóm</title>
		<%@ include file="../head_tag.jsp"%>
	</head>
	<body>

		<%@ include file="../header.jsp"%>

		<div class="container-fluid" style="margin-top: 10px">
			<div class="row">
				<%@ include file="menu.jsp"%>

				<div class="col-md-8">
					<div class="bs-example well">
						<form class="form-create" name='createForm' action="/teacher/output_all" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<input type="hidden" name="group"/>
							<input type="hidden" name="original" value=""/>

							<div class="form-group">
								<label for="namefile">Tên file cần xuất:</label>
								<input type="input" class="form-control" id="namefile" name="fileName" placeholder="Tên file cần xuất ra">
							</div>

							<div>
								<button type="submit" class="btn btn-primary">Tạo</button>
								<button type="submit" class="btn btn-primary" style="float: right"><a id="bt-login" href="/teacher">Hủy</a></button>
							</div>

							<c:if test="${success != null && success}">
								<div style="color: blue">Xuất file thành công</div>
							</c:if>
							<c:if test="${success != null && !success}">
								<div style="color: red">Xuất file không thành công</div>
							</c:if>
						</form>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="condition.jsp"%>

		<%@ include file="../footer.jsp"%>

	</body>
</html>