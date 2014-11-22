<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Icaras - Voeg Organisatie toe</title>
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
	
		<form:form method="post" action="/Icaras/updateAdressen"
			modelAttribute="relatie">
			<fieldset>
				<legend>Bekende Adressen:</legend>
				<c:forEach items="${relatie.adressen}" var="adres">
					<input type="hidden" name="id" value="${adres.id}">
					<input type="hidden" name="isCorrespondentieAdres"
						value="${adres.isCorrespondentieAdres}">
					<input type="hidden" name="isPostbus" value="${adres.isPostbus}" />
					
					<label for="straat">Straat</label>
					<input type="text" name="straat" value="${adres.straat}" /><br />
					
					<label for="huisOfPostbusNummer">huisOfPostbusNummer</label>
					<input type="text" name="huisOfPostbusNummer"
						value="${adres.huisOfPostbusNummer}" /><br />
					
					<label for="postcode">postcode</label>
					<input type="text" name="postcode" value="${adres.postcode}" /><br />
					
					<label for="plaats">plaats</label>
					<input type="text" name="plaats" value="${adres.plaats}" /><br />
					
					<input type="checkbox" name="isCorrespondentieAdres" value="true" <c:if test="${adres.isCorrespondentieAdres}">checked</c:if>>Is dit adres het correspendentieadres?<br />
					<input type="checkbox" name="isPostbus" value="true" <c:if test="${adres.isPostbus}">checked</c:if>>Is dit adres een Postbus?<br />
					
					<br />
				</c:forEach>
				<input type="submit" value="Wijzig adres(sen)" disabled="disabled" />
				<p class="warning">Hoe slaan we meerdere</p>
			</fieldset>
		</form:form>
	
		<form:form method="post" action="/Icaras/voegAdresToe/${relatie.id}"
			modelAttribute="adresForm">
			<fieldset>
				<legend>Voeg een nieuw adres toe:</legend>
	
				<label for="straat">Straat</label>
				<input type="text" name="straat" value="" /><br /> 
				
				<label for="huisOfPostbusNummer">huisOfPostbusNummer</label>
				<input type="text" name="huisOfPostbusNummer" value="" /><br /> 
				
				<label for="postcode">postcode</label>
				<input type="text" name="postcode"value="" /><br />
				
				<label for="plaats">plaats</label>
				<input type="text" name="plaats" value="" /><br />
				
				<input type="checkbox" name="correspondentieAdres" value="true" checked>Stuur de post hiernaartoe?<br />
				<input type="checkbox" name="postbus" value="true" checked>Is dit adres een postbus?<br />
				
				<input type="submit" value="Voeg adres toe" />
				
			</fieldset>
		</form:form>

	</c:if>

</body>
</html>