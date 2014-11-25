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
			<li class="menubar_item active"><a href="/Icaras/getAllRelaties">relaties</a></li>
			<li class="menubar_item"><a href="/Icaras/getAllPersonen">personen</a></li>
			<li class="menubar_item"><a href="/Icaras/getAllOrganisaties">organisaties</a></li>
		</ul>
	</div>


<div class="fieldset"><fieldset>
	<legend>Relaties</legend>
	
	<div id="container2">
		<div id="container1">
			<div id="col1">
				<ul id="relatielist">
		<h3>OrganisatieLijst</h3>
			<c:if test="${not empty relaties}">
				<c:forEach items="${relaties}" var="relatie">
					<c:choose>
						<c:when
							test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Organisatie'}">
							<li class="organisatie"><a id="${relatie.id}"
								class="organisatie" href="getOrganisatie/${relatie.id}"><c:out
										value="${relatie.naam}" /></a>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
			<li class="new"><a
				href="voegOrganisatieToe">Voeg Organisatie toe</a></li>
			</ul>
			</div>
			<div id="col2">
				<ul id="relatielist">
		<h3>PersonenLijst</h3>
			<c:if test="${not empty relaties}">
				<c:forEach items="${relaties}" var="relatie">
					<c:choose>
						<c:when
							test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Persoon'}">
							<li class="persoon"><a id="${relatie.id}" class="persoon"
								href="getPersoon/${relatie.id}"><c:out
										value="${relatie.voornaam} ${relatie.tussenvoegsels} ${relatie.achternaam}" /></a>
						</c:when>
					</c:choose>
				</c:forEach>
			</c:if>
			<li class="new"><a
				href="voegPersoonToe">Voeg Persoon toe</a></li>
		</ul>
			</div>
		</div>
	</div>
	
	</fieldset></div>

</body>
</html>