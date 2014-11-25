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

	<c:if test="${not empty nfaForm}">
		<form:form method="post" action="/Icaras/getNfa" modelAttribute="nfaForm">
			<div class="fieldset">
				<fieldset>
					<legend>Nfa</legend>
					
					<form:input type="hidden" path="relatieId" />
					<form:input type="hidden" path="NfaId" />

					<p>
					<label for="nfaSoort">Nfa soort</label>
					<form:select path="nfaSoort" disabled="true">
						<form:options />
					</form:select>
					<form:input type="hidden" path="nfaSoort" />
 					<p>

					<p>
					<label for="nfaAdres">Nfa adres</label>
					<form:input name="nfaAdres" path="nfaAdres" />
					<form:errors path="nfaAdres" cssClass="validationError" />
					</p>
					
					<p>
					<label for="extraInfo">Extra info</label>
					<form:input name="extraInfo" path="extraInfo" />
					<form:errors path="extraInfo" cssClass="validationError" />
					</p>
					
					<p>
					<input type="submit" value="Wijzig Nfa" />
					</p>

					</fieldset>
			</div>
		</form:form>
	</c:if>

</body>
</html>