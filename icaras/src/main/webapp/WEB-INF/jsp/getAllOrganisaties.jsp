<%@ page
	import="java.io.*, java.util.*, java.sql.*, javax.servlet.http.*, javax.servlet.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Icaras</title>
<link href="<c:url value="/resources/rs4/css/icaras.css"/>"
	rel="stylesheet">
</head>

<body>

	<div id="menu">
		<ul id="menubar">
			<li class="menubar_item"><a href="/Icaras/">welkom</a></li>
			<li class="menubar_item"><a href="/Icaras/getAllRelaties">relaties</a></li>
			<li class="menubar_item"><a href="/Icaras/getAllPersonen">personen</a></li>
			<li class="menubar_item active"><a href="/Icaras/getAllOrganisaties">organisaties</a></li>
		</ul>
	</div>

	<div class="fieldset"><fieldset>
	<legend>Organisaties</legend>
	
		<ul id="relatielist">
		<h3>OrganisatieLijst</h3>
			<c:if test="${not empty organisaties}">
				<c:forEach items="${organisaties}" var="organisatie">
					<li class="organisatie"><a id="${organisatie.id}" class="organisatie" href="getOrganisatie/${organisatie.id}"><c:out value="${organisatie.naam}" /></a>
				</c:forEach>
			</c:if>
			<li class="new"><a href="voegOrganisatieToe">Voeg Organisatie toe</a></li>
		</ul>

	</fieldset></div>

</body>
</html>