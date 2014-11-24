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
			<li class="menubar_item"><a href="/Icaras/start">vul
					database</a></li>
			<li class="menubar_item"><a href="/Icaras/getAllRelaties">relaties</a></li>
		</ul>
	</div>

	<c:if test="${not empty persoon}">
		<form:form action="voegPersoonToe" method="post" commandName="persoon">
			<fieldset>
				<legend>Voeg een nieuwe persoon toe:</legend>
				<br /> <label for="voornaam">Voornaam</label>
				<form:input naam="voornaam" path="voornaam" />
				<br /> <label for="tussenvoegsels">Tussenvoegsel(s)</label>
				<form:input naam="tussenvoegsels" path="tussenvoegsels" />
				<br /> <label for="achternaam">Achternaam</label>
				<form:input name="achternaam" path="achternaam" />
				<br /> <br /> <input type="submit" value="Voeg persoon toe" />
			</fieldset>
		</form:form>
	</c:if>
	
</body>
</html>