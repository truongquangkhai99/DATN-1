<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Sinh viên - thi</title>
		<%@ include file="../head_tag.jsp"%>
	</head>
	<body>
		<%
			long timer = 0;
			if(request.getAttribute("timer") != null) {
				timer = (long)request.getAttribute("timer");
			}
		%>

		<%@ include file="../header.jsp"%>

		<%@ include file="condition.jsp"%>

		<% session.setAttribute("score", request.getAttribute("score")); %>
		<c:if test="${isTested != null && isTested}">
			<c:redirect url = "/student"/>
		</c:if>
		<div style="height: 92%; margin-top: 10px">

			<div id="count-down"></div>

				<form class="form-create" id="form-test" name='createForm' action="/student/test" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
												<input type="radio" class="radio-test" name="radio_${examination.questionId}" value="radio_${answer.answer}"/><pre class="answer-test">${answer.answer}</pre>
											</td>
										</tr>
									</c:if>

									<c:if test="${examination.radio == false}">
										<tr>
											<td>
												<input type="checkbox" class="radio-test" name="checkbox_${examination.questionId}" value="checkbox_${answer.answer}"/><pre class="answer-test">${answer.answer}</pre>
											</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</c:forEach>
				</form>

				<button class="btn btn-primary" id="submit-test" onclick='SubmitFunction();'>Nộp bài</button>

		</div>

		<script>
			var timerGlobal = 0;
			function CountDown(duration, display) {
				if (!isNaN(duration)) {
					var timer = duration
					var minutes, seconds;

					var interVal = setInterval(function () {
						timerGlobal = timer;
						minutes = parseInt(timer / 60, 10);
						seconds = parseInt(timer % 60, 10);

						minutes = minutes < 10 ? "0" + minutes : minutes;
						seconds = seconds < 10 ? "0" + seconds : seconds;

						$(display).html("<b>Bạn còn " + minutes + " : " + seconds + "</b>");
						if (--timer < 0) {
							SubmitFunction();
							$('#count-down').empty();
							clearInterval(interVal)
						}
					}, 1000);
				}
			}

			if(<%=timer != 0%>) {
				CountDown(<%=timer%>, '#count-down');
			}

			function SubmitFunction() {
				$('form[name="createForm"]').submit();
			}

			function load_ajax() {
				$.ajax({
					url : "/student/test",
					type : "get",
					data : {timerLast : timerGlobal}
				});
			}

			window.onbeforeunload = function (event) {
				load_ajax();
			};

			window.unload = function() {
				load_ajax();
			}
		</script>

		<%@ include file="../footer.jsp"%>

	</body>
</html>