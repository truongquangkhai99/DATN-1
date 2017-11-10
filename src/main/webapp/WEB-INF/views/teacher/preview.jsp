<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Giảng viên - Xem trước đề thi</title>
		<%@ include file="../head_tag.jsp"%>
	</head>
	<body>

		<%@ include file="../header.jsp"%>
		<div style="height: 95%; margin-top: 10px">
			<div id="condition-preview">
				<form class="form-inline" name='createForm' action="/teacher/preview" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="form-group">
						<label for="sel1">Chọn nhóm thi:</label>
						<select class="form-control" id="sel1" name="group">
							<c:forEach items="${groups}" var="group" varStatus="itr">
								<option>${group}</option>
							</c:forEach>
						</select>
					</div>
					<button type="submit" class="btn btn-primary">Xem</button>
					<button class="btn btn-primary"><a id="bt-login" href="/teacher">Hủy xem</a></button>
				</form>
			</div>

			<div style="height: 90%; overflow: scroll;">
				<c:forEach items="${examinations}" var="examination" varStatus="itr">
					<div class="break-question"></div>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th><pre>${examination.question}</pre></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${examination.answers}" var="answer" varStatus="itr">
								<c:if test="${examination.radio == true}">
									<tr>
										<td>
											<input type="radio" class="radio-test"><pre class="answer-test">${answer.answer}</pre>
										</td>
									</tr>
								</c:if>

								<c:if test="${examination.radio == false}">
									<tr>
										<td>
											<input type="checkbox" class="radio-test"><pre class="answer-test">${answer.answer}</pre>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
			</div>
		</div>

		<%@ include file="condition.jsp"%>

		<%@ include file="../footer.jsp"%>

	</body>
</html>