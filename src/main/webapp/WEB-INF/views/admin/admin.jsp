<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<!DOCTYPE html >

<html>
	<%@ include file="../head_tag.jsp"%>
	<body>

		<%@ include file="../header.jsp"%>

		<div class="container-fluid">
			<div class="row">
			<%@ include file="menu_admin.jsp"%>
			</div>
		</div>

		<%@ include file="../footer.jsp"%>

		<%@ include file="content_admin.jsp"%>

	</body>

</html>