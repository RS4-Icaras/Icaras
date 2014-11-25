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

	<c:if test="${not empty postbusForm}">
		<form:form method="post"
			action="/Icaras/getPostbus"
			modelAttribute="postbusForm">
			<div class="fieldset"><fieldset>
				<legend>Postbus</legend>
				
				<form:input type="hidden" path="relatieId" />
				<form:input type="hidden" path="postbusId" />
				
				<p>
				<label for="postbusnummer">Postbusnummer</label>
				<form:input name="postbusnummer" path="postbusnummer" />
				<form:errors path="postbusnummer" cssClass="validationError" />
				</p>
				
				<p>
				<label for="postcode">Postcode</label>
				<form:input name="postcode" path="postcode" />
				<form:errors path="postcode" cssClass="validationError" />
				</p>
				
				<p>
				<label for="plaats">Plaats</label>
				<form:input name="plaats" path="plaats" />
				<form:errors path="plaats" cssClass="validationError" />
				</p>
				
				<p>
				<c:choose>
					<c:when test="${postbusForm.correspondentieAdres}">
						<input type="checkbox" name="nepwaarde" value="true" checked
							disabled>
							Stuur de post hiernaartoe?
						<input type="hidden" name="correspondentieAdres" value="true" />
						<form:input type="hidden" path="correspondentieAdres" />
					</c:when>
					<c:otherwise>
						<input type="checkbox" name="correspondentieAdres" value="true">
						Stuur de post hiernaartoe?
					</c:otherwise>
				</c:choose>
				</p>
				
				<p>
				<input type="submit" value="Voeg postbus toe" />
				</p>

			</fieldset></div>
		</form:form>
	</c:if>

</body>
</html>