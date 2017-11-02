<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Sinh viên</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
		<script type="application/javascript" src="/js/jquery.js"></script>
		<script type="application/javascript" src="/js/bootstrap.js"></script>
	</head>
<body>
	<%
		request.setAttribute("isStudent", request.isUserInRole("STUDENT"));
		long timer = 0;
		if(request.getAttribute("timer") != null) {
			timer = (long)request.getAttribute("timer");
		}
	%>

	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>

	<c:if test="${!requestScope.isStudent}">
		<c:redirect url = "/403"/>
	</c:if>

	<c:if test="${isTested != null && isTested}">
		<c:redirect url = "/student"/>
	</c:if>

	<div id="count-down" style="overflow: hidden; position: fixed; top: 0; width:inherit; height: 40px; font-weight: bold; font-size: 30px"></div>

	<form class="form-create" style="margin-top: 60px; overflow: scroll; height: 700px" name='createForm' action="/student/test" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<c:forEach items="${examinations}" var="examination" varStatus="itr">
			<div class="break-question"></div>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>${examination.question}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${examination.answers}" var="answer" varStatus="itr">
						<c:if test="${examination.radio == true}">
							<tr>
								<td>
									<input type="radio" name="radio_${examination.questionId}" value="radio_${answer.answer}"/>${answer.answer}
								</td>
							</tr>
						</c:if>

						<c:if test="${examination.radio == false}">
							<tr>
								<td>
									<input type="checkbox" name="checkbox_${examination.questionId}" value="checkbox_${answer.answer}"/>${answer.answer}
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</c:forEach>
	</form>

	<button class="btn btn-primary" onclick='SubmitFunction();'>Nộp bài</button>

	<script>
		var timerGlobal = 0;
		function CountDown(duration, display) {
			if (!isNaN(duration)) {
				var timer = duration
				var minutes, seconds;

				var interVal=  setInterval(function () {
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

</body>
</html>