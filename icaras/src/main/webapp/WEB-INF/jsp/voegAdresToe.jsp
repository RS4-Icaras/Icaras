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
		<li class="menubar_item"><a href="/Icaras/start">vul database</a></li>
		<li class="menubar_item"><a href="/Icaras/getAllRelaties">relaties</a></li>
	</ul>
	</div>
	
	<c:if test="${not empty relatie}">
	
		<form:form method="post" action="/Icaras/voegAdresToe/${relatie.id}"
			modelAttribute="adresForm">
			<fieldset>
				<legend>Voeg een nieuw adres toe:</legend>
				
				<input type="hidden" name="id" value="${adresForm.id}">
				
				<br /> 
				<label for="straat">Straat</label>
				<input type="text" name="straat" value="${adresForm.straat}" /><br /> 
				
				<label for="huisOfPostbusNummer">huisOfPostbusNummer</label>
				<input type="text" name="huisOfPostbusNummer" value="${adresForm.huisOfPostbusNummer}" /><br /> 
				
				<label for="postcode">postcode</label>
				<input type="text" name="postcode"value="${adresForm.postcode}" /><br />
				
				<label for="plaats">plaats</label>
				<input type="text" name="plaats" value="${adresForm.plaats}" /><br />
				<br />
				
				<input type="checkbox" name="correspondentieAdres" value="true" <c:if test="${adresForm.correspondentieAdres}">checked</c:if>>Stuur de post hiernaartoe?<br />
				<input type="checkbox" name="postbus" value="true" <c:if test="${adresForm.postbus}">checked</c:if>>Is dit adres een postbus?<br />
				
				<c:if test="${not empty feedback}">
						<p class="error">${feedback.algemeen}</p>
				</c:if>
				
				<br />
				<input type="submit" value="Voeg adres toe" />
				
			</fieldset>
		</form:form>

	</c:if>

</body>
</html>