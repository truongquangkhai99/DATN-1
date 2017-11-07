<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<head>
		<title>Quản trị</title>
		<%@ include file="../head_tag.jsp"%>
	</head>

	<body>

		<%@ include file="../header.jsp"%>

		<div class="container-fluid" style="margin-top: 10px">
			<div class="row">
				<%@ include file="menu.jsp"%>
			</div>
		</div>

		<%@ include file="../footer.jsp"%>

		<%@ include file="condition.jsp"%>

	</body>

</html>