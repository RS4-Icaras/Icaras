<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Icaras</title>
</head>
<body>
	<form:form method="post" action="/Icaras/saveAdressen"
		modelAttribute="contactForm">

		<c:forEach items="${adresForm.adressen}" var="adres"
			varStatus="status">

			<input type="hidden" name="adressen[${status.index}].id"
				value="${adres.id}" />
			<br />

			<input type="text" name="adressen[${status.index}].straat"
				value="${adres.straat}" />
			<br />

			<input type="text"
				name="adressen[${status.index}].huisOfPostbusNummer"
				value="${adres.huisOfPostbusNummer}" />
			<br />

			<input type="text" name="adressen[${status.index}].postcode"
				value="${adres.postcode}" />
			<br />

			<input type="text" name="adressen[${status.index}].plaats"
				value="${adres.plaats}" />
			<br />

			<input type="checkbox"
				name="adressen[${status.index}].isCorrespondentieAdres" value="true"
				<c:if test="${adres.isCorrespondentieAdres}">checked</c:if>>Is dit adres het correspendentieadres?<br />
			<input type="checkbox" name="adressen[${status.index}].isPostbus"
				value="true" <c:if test="${adres.isPostbus}">checked</c:if>>Is dit adres een Postbus?<br />

		</c:forEach>

		<input type="submit" value="Save" />

	</form:form>
</body>
</html>