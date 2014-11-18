<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Voeg een organisatie toe aan de database</title>
</head>

<body>

	<ul>
		<li><a href="relatielijst">relaties</a></li>
		<li><a href="voegPersoonToe">voeg persoon toe</a></li>
		<li><a href="voegOrganisatieToe">voeg organisatie toe</a></li>
	</ul>
	
	<h1>Geef de naam voor een toe te voegen organisatie op</h1>
	<form:form action="voegOrganisatieToe" method="post" commandName="invulOrganisatie">

			<label>Naam</label>
			<form:input path="naam" />

			<input type="submit" value="Voeg organisatie toe" /> <input type="reset"
				value="Wis invoer" />
				
	</form:form>
</body>
</html>