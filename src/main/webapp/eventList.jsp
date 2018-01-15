<%@include file="/layout.jsp"%>
<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration Success</title>
</head>
<body>
	<div class="container" align="center">
		<table class="table table-bordered">
			<h2>Lista wydarzeń</h2>
			<tr>
				<th style="text-align:center"></th>
				<th width="400">NAZWA</th>
				<th style="text-align:center">DATA</th>
			</tr>
			<c:forEach var="event" items="${EventList}" varStatus="loop">
				<tr>
					<td align="center"><c:out value="${loop.count}" /></td>
					<td><a href="/eventdetails?id=${event.id}"><c:out
								value="${event.name}" /></a></td>
					<td align="center"><c:out value="${event.data}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>