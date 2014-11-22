<%@ page
	import="java.io.*, java.util.*, java.sql.*, javax.servlet.http.*, javax.servlet.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Icaras - Relatie Overzicht</title>
<link href="<c:url value="/resources/rs4/css/icaras.css"/>"
	rel="stylesheet">
</head>

<body>

	<div id="menu">
	<ul id="menubar">
		<li class="menubar_item"><a href="/Icaras/start">vul database</a></li>
		<li class="menubar_item active"><a href="/Icaras/getAllRelaties">relaties</a></li>
	</ul>
	</div>	

	<h1>Lijst van relaties</h1>
	<ul id="relatielist">
		<c:if test="${not empty relaties}">
			<c:forEach items="${relaties}" var="relatie">
				<c:choose>
					<c:when
						test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Organisatie'}">
						<li class="organisatie"><a id="${relatie.id}"
							class="organisatie" href="getRelatie/${relatie.id}"><c:out
									value="${relatie.naam}" /></a>
					</c:when>
					<c:when
						test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Persoon'}">
						<li class="persoon"><a id="${relatie.id}" class="persoon"
							href="getRelatie/${relatie.id}"><c:out
									value="${relatie.voornaam} ${relatie.tussenvoegsels} ${relatie.achternaam}" /></a>
					</c:when>
				</c:choose>
			</c:forEach>
		</c:if>
		<li style="margin-top: 12px;" class="new"><a
			href="voegPersoonToe">Persoon</a></li>
		<li class="new"><a href="voegOrganisatieToe">Organisatie</a></li>
	</ul>

</body>
</html>