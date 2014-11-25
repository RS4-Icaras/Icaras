<%@ page
	import="java.io.*, java.util.*, java.sql.*, javax.servlet.http.*, javax.servlet.*, nl.rsvier.icaras.core.relatiebeheer.Adres, nl.rsvier.icaras.core.relatiebeheer.Relatie"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
			<li class="menubar_item"><a href="/Icaras/getAllOrganisaties">organisaties</a></li>
		</ul>
	</div>

	<c:if test="${not empty organisatieForm}">
		<form:form action="/Icaras/updateOrganisatie" method="post"
			commandName="organisatieForm">
			<fieldset>
				<legend>Gegevens</legend>
				<br />

				<form:input type="hidden" name="id" path="id" readonly="true" />

				<label for="naam">Naam</label>
				<form:input name="naam" path="naam" readonly="true" />
				<form:errors path="naam" cssClass="validationError"></form:errors>
				<br /><br />

				<input type="submit" value="Wijzig gegevens" disabled="disabled" />
				<br />

			</fieldset>
		</form:form>
	</c:if>

	<c:if test="${not empty persoonForm}">
		<form:form action="/Icaras/updatePersoon" method="post"
			commandName="persoonForm">
			<fieldset>
				<legend>Gegevens</legend>
				<br />

				<form:input type="hidden" name="id" path="id" readonly="true" />

				<label for="voornaam">Voornaam</label>
				<form:input name="voornaam" path="voornaam" />
				<form:errors path="voornaam" cssClass="validationError"></form:errors>
				<br /><br />
				
				<label for="tussenvoegsels">Tussenvoegsels</label>
				<form:input name="tussenvoegsels" path="tussenvoegsels" />
				<form:errors path="tussenvoegsels" cssClass="validationError"></form:errors>
				<br /><br />
				
				<label for="achternaam">Achternaam</label>
				<form:input name="achternaam"  path="achternaam" />
				<form:errors path="achternaam" cssClass="validationError"></form:errors>
				<br /><br />
				
				<label for="geboortedatum">Geboortedatum</label>
				<form:input name="geboortedatum" path="geboortedatum" />
				<form:errors path="geboortedatum" cssClass="validationError"></form:errors>
				<br /><br />
				
				<input type="submit" value="Wijzig gegevens">
				<br />

			</fieldset>
		</form:form>

	</c:if>
	
	<c:if test="${not empty relatie}">
	<fieldset>
		<legend>Adressen</legend>
		<ul id="adressenlist">
			<c:forEach items="${relatie.adressen}" var="adres">
			<li class="adres edit <c:if test="${adres.isCorrespondentieAdres}">correspondentie</c:if>">
				<c:choose>
					<c:when test="${adres.isPostbus}">
						<a href="/Icaras/getPostbus/${relatie.id}/${adres.id}">${adres}</a>
					</c:when>
					<c:otherwise>
						<a href="/Icaras/getAdres/${relatie.id}/${adres.id}">${adres}</a>
					</c:otherwise>
				</c:choose>
			</li>
			</c:forEach>
			<li style="margin-top: 12px;" class="new"><a
				href="/Icaras/voegAdresToe/${relatie.id}">Voeg adres toe</a></li>
			<li class="new"><a
				href="/Icaras/voegPostbusToe/${relatie.id}">Voeg postbus toe</a></li>
		</ul>
	</fieldset>
	</c:if>

</body>
</html>