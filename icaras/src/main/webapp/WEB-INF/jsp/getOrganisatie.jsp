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
			<div class="fieldset"><fieldset>
				<legend>Gegevens</legend>
				
				<form:input type="hidden" name="id" path="id" />

				<p>
				<label for="naam">Naam</label>
				<form:input name="naam" path="naam" />
				<form:errors path="naam" cssClass="validationError"></form:errors>
				</p>
				
				<p>
				<input type="submit" value="Wijzig gegevens">
				</p>

			</fieldset></div>
		</form:form>

	</c:if>
	
	<c:if test="${not empty organisatie}">
	<div class="fieldset"><fieldset>
		<legend>Adressen</legend>
		<ul id="adressenlist">
			<c:forEach items="${organisatie.adressen}" var="adres">
			<li class="adres edit <c:if test="${adres.isCorrespondentieAdres}">correspondentie</c:if>">
				<c:choose>
					<c:when test="${adres.isPostbus}">
						<a href="/Icaras/getPostbus/${organisatie.id}/${adres.id}">${adres}</a>
					</c:when>
					<c:otherwise>
						<a href="/Icaras/getAdres/${organisatie.id}/${adres.id}">${adres}</a>
					</c:otherwise>
				</c:choose>
			</li>
			</c:forEach>
			<li class="new"><a
				href="/Icaras/voegAdresToe/${organisatie.id}">Voeg adres toe</a></li>
			<li class="new"><a
				href="/Icaras/voegPostbusToe/${organisatie.id}">Voeg postbus toe</a></li>
		</ul>
	</fieldset></div>
	
	<div class="fieldset"><fieldset>
		<legend>Nfa's</legend>
		<ul id="nfalist">
			<c:forEach items="${organisatie.nfaLijst}" var="nfa">
			<li class="nfa edit <c:out value="${nfa.nfaSoort}"/>">
				<a href="/Icaras/getNfa/${organisatie.id}/${nfa.id}">${nfa.nfaAdres}</a>
			</li>
			</c:forEach>
			<li class="new"><a
				href="/Icaras/voegNfaToe/${organisatie.id}">Voeg een nfa toe</a></li>
		</ul>
	</fieldset></div>
	
	<div class="fieldset"><fieldset>
		<legend>Rollen</legend>
		<ul id="rollenlist">
		
			<c:forEach items="${rollenMap}" var="rol">
				<c:choose>
					<c:when test="${not empty rol.value}">
						<li class="rol edit ${rol.key}">
							<a class="" href="/Icaras/get${rol.key}/${organisatie.id}">${rol.key}</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="rol new">
							<a class="new" href="/Icaras/voeg${rol.key}Toe/${organisatie.id}">Voeg ${rol.key} toe</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
		</ul>
	</fieldset></div>
	</c:if>
	
</body>
</html>