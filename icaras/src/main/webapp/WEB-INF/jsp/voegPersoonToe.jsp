<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Icaras</title>
<link href="<c:url value="/resources/rs4/css/icaras.css" />"
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

	<c:if test="${not empty persoonForm}">
		<form:form action="voegPersoonToe" method="post" modelAttribute="persoonForm">
			<div class="fieldset"><fieldset>
			
				<legend>Persoon</legend>
				
				<br />
				<label for="voornaam">Voornaam</label>
				<form:input naam="voornaam" path="voornaam" />
				<form:errors path="voornaam" cssClass="validationError" />
				<br />
				
				<br />
				<label for="tussenvoegsels">Tussenvoegsel(s)</label>
				<form:input naam="tussenvoegsels" path="tussenvoegsels" />
				<form:errors path="tussenvoegsels" cssClass="validationError" />
				<br />
				
				<br />
				<label for="achternaam">Achternaam</label>
				<form:input name="achternaam" path="achternaam" />
				<form:errors path="achternaam" cssClass="validationError" />
				<br />
				
				<br />
				<label for="geboortedatum">Geboortedatum</label>
				<form:input name="geboortedatum" path="geboortedatum" />
				<form:errors path="geboortedatum" cssClass="validationError" />
				<br />
				
				<br />
				<input type="submit" value="Voeg persoon toe" />
				<br /> 
				
			</fieldset></div>
		</form:form>
	</c:if>
	
</body>
</html>