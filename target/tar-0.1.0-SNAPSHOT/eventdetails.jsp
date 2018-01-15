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
			<h2>Szczegóły wydarzenia</h2>
			<tr>
				<th width="400">NAZWA</th>
				<th style="text-align: center">DATA</th>
				<th style="text-align: center">OPIS</th>
				<th style="text-align: center">AKCJA</th>
			</tr>
			<tr>
				<td><c:out value="${Event.name}" /></td>
				<td align="center"><c:out value="${Event.data}" /></td>
				<td align="center"><c:out value="${Event.description}" /></td>
				<c:if test="${isMember == true}">
					<c:choose>
						<c:when test="${Dolaczyl == true }">
							<td align="center"><a href="/wypisz?id=${Event.id}"><button
										class="btn btn-primary btn-sm" type="submit">Wypisz się</button></a></td>
						</c:when>
						<c:otherwise>
							<td align="center"><a href="/dolacz?id=${Event.id}">
									<button class="btn btn-primary btn-sm" type="submit">Dołącz</button>
							</a></td>
						</c:otherwise>
					</c:choose>
				</c:if>
			</tr>
		</table>
		<c:if test="${isAdmin == true }">
		<table class="table table-bordered">
			<h2>Lista uczestników</h2>
			<tr>
				<th width="400">Uczestnik</th>
				<th width="400">Akcja</th>
			</tr>
			<c:forEach var="user" items="${UserList}" varStatus="loop">
				<tr>
					<td align="center"><c:out value="${user}" /></td>
					<td align="center"><a href="/kick?userid=${user}&eventid=${Event.id}">
									<button class="btn btn-primary btn-sm" type="submit">Wyrzuć</button>
							</a></td>
				</tr>
			</c:forEach>
		</table>
		</c:if>
		

	</div>
</body>
</html>