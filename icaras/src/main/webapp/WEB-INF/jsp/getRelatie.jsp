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
<title>Icaras - Relatie Overzicht</title>
<link href="<c:url value="/resources/rs4/css/icaras.css"/>"
	rel="stylesheet">
</head>

<body>

	<div id="menu">
	<ul id="menubar">
		<li class="menubar_item"><a href="/Icaras/start">vul database</a></li>
		<li class="menubar_item"><a href="/Icaras/getAllRelaties">relaties</a></li>
	</ul>
	</div>	


	<c:if test="${not empty relatie}">
		<c:choose>

			<c:when
				test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Organisatie'}">
				<form:form action="/Icaras/updateOrganisatie" method="post"
					commandName="relatie">
					
					<form:input type="hidden" path="id" readonly="true" />
					<fieldset>
					<legend>Gegevens</legend>
					<label for="naam">Naam</label>
					<form:input path="naam" readonly="true" />
					<br />
					<br />
					<input style="display:inline;" type="submit" value="Wijzig gegevens" disabled="disabled" />
					
					<p class="warning">Organisatie heeft slechts een naam als attribuut en deze is onaanpasbaar!</p>
					</fieldset>
				</form:form>
			</c:when>

			<c:when
				test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Persoon'}">
				<form:form action="/Icaras/updatePersoon" method="post"
					commandName="relatie">
					<fieldset>
					<legend>Gegevens</legend>
					<form:input type="hidden" name="id" path="id" readonly="true" />
					
					<label for="voornaam">Voornaam</label>
					<form:input id="voornaam" path="voornaam" />
					<br />
					<label for="tussenvoegsels">Tussenvoegsels</label>
					<form:input path="tussenvoegsels" />
					<br />
					<label for="achternaam">Achternaam</label>
					<form:input path="achternaam" />
					<br />
					<br />
					<input type="submit" value="Wijzig gegevens">
					</fieldset>
				</form:form>

			</c:when>
			
		</c:choose>
			<fieldset>
			<legend>Adressen</legend>
				<ul id="adressenlist">
					<c:forEach items="${relatie.adressen}" var="adres">
						<li class="adres <c:if test="${adres.isCorrespondentieAdres}">correspondentieadres</c:if>">
							<a href="/Icaras/voegAdresToe/${relatie.id}"> ${adres} </a>
						</li>
					</c:forEach>
					<li style="margin-top: 12px;" class="new"><a
						href="/Icaras/voegAdresToe/${relatie.id}">Adres</a></li>
				</ul>
			</fieldset>
	</c:if>

</body>
</html>