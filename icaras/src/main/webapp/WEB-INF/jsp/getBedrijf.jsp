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
			<li class="menubar_1item"><a href="/Icaras/getAllRelaties">relaties</a></li>
			<li class="menubar_item"><a href="/Icaras/getAllPersonen">personen</a></li>
			<li class="menubar_item"><a href="/Icaras/getAllOrganisaties">organisaties</a></li>
		</ul>
	</div>

	<c:if test="${not empty organisatie}">
	<div class="fieldset"><fieldset>
		<legend>Aanbiedingen</legend>
		
		<ul id="aanbiedingenlist">
			<c:forEach items="${organisatie.bedrijf.aanbiedingen}" var="aanbieding">
			<li class="">
				<a href="/Icaras/getPersoon/${aanbieding.persoon.id}">${aanbieding.persoon}</a>
			</li>
			</c:forEach>
			<p>zijn aangeboden aan: ${organisatie.naam}</p>
		</ul>
	</fieldset></div>
	</c:if>
	
</body>
</html>