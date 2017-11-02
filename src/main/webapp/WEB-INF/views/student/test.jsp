<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html >
<html>
	<head>
		<title>Sinh viên</title>
		<link type="text/css" href="/css/bootstrap.css" rel="stylesheet" />
		<link type="text/css" href="/css/app.css" rel="stylesheet" />
		<script type="application/javascript" src="js/jquery.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	</head>
<body>
	<c:if test="${empty pageContext.request.userPrincipal.name}">
		<c:redirect url = "/login"/>
	</c:if>
	
	<% request.setAttribute("isStudent", request.isUserInRole("STUDENT")); %>
	<c:if test="${!requestScope.isStudent}">
		<c:redirect url = "/403"/>
	</c:if>

	<c:if test="${isTested != null && isTested}">
		<% session.setAttribute("isTested", true); %>
		<c:redirect url = "/student"/>
	</c:if>

	<%
		long timer = 0;
		if(request.getAttribute("timer") != null) {
			timer = (Long)request.getAttribute("timer");
		}
	%>

	<div id="display"></div>

	<form class="form-create" name='createForm' action="/student/test" method="POST">
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
		<button class="btn btn-primary" type='submit'>Nộp bài</button>
	</form>
	<form name='timerForm' action="/student/test" method="GET">
		<input type="hidden" name="timerLast" value=""/>
	</form>
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
						$('#display').empty();
						clearInterval(interVal)
					}
				}, 1000);
			}
		}

		function SubmitFunction(){
			$('form[name="createForm"]').submit();
		}

		if(<%=timer != 0%>) {
			CountDown(<%=timer%>, '#display');
		}
		
		window.onbeforeunload = function (event) {
			$('input[name="timerLast"]').val(timerGlobal);
			$('form[name="timerForm"]').submit();
		};

		function load_ajax(){
			$.ajax({
				url : "/student/test",
				type : "get",
				data : {timerLast : timerGlobal}
			});
		}

		setTimeout(function(){

		}, 2000);

		function formSubmit() {
			load_ajax();
			setTimeout(function(){
				document.getElementById("logoutForm").submit();
			}, 500);
		}
	</script>

	<c:url value="/logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	</script>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()"> Logout</a>
		</h2>
	</c:if>
	<script type="application/javascript" src="js/jquery.js"></script>
	<script type="application/javascript" src="js/bootstrap.js"></script>
</body>
</html>