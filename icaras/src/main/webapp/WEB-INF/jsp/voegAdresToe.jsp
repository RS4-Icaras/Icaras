<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	
	<c:if test="${not empty relatie}">
	<c:if test="${not empty adresForm}">
	
		<form:form method="post" action="/Icaras/voegAdresToe/${relatie.id}"
			modelAttribute="adresForm">
			<fieldset>
				<legend>Voeg een nieuw adres toe</legend>
				<br /> 
				
				<label for="straat">Straat</label>
				<form:input name="straat" path="straat" />
				<form:errors path="straat" cssClass="validationError" />
				<br /><br />
				
				<label for="huisnummer">Huisnummer</label>
				<form:input name="huisnummer" path="huisnummer" />
				<form:errors path="huisnummer" cssClass="validationError" />
				<br /><br />
				
				<label for="postcode">Postcode</label>
				<form:input name="postcode" path="postcode" />
				<form:errors path="postcode" cssClass="validationError" />
				<br /><br />
				
				<label for="plaats">Plaats</label>
				<form:input name="plaats" path="plaats" />
				<form:errors path="plaats" cssClass="validationError" />
				<br /><br />
				
				<input type="checkbox" name="correspondentieAdres" value="true" <c:if test="${adresForm.correspondentieAdres}">checked</c:if>>
				Stuur de post hiernaartoe?
				<br /><br />
				
				<input type="submit" value="Voeg adres toe" />
				<br />
				
			</fieldset>
		</form:form>

	</c:if>
	</c:if>

</body>
</html>