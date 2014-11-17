<%@ page import="java.io.*, java.util.*, java.sql.*, javax.servlet.http.*, javax.servlet.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<body>
	
	<ul>
		<li><a href="relatielijst">relaties</a></li>
		<li><a href="voegPersoonToe">voeg persoon toe</a></li>
		<li><a href="voegOrganisatieToe">voeg organisatie toe</a></li>
	</ul>
	
	<ul>
	<c:if test="${not empty relaties}">
		<c:forEach items="${relaties}" var="relatie" >
			
			<c:if test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Organisatie'}">
				<li><a id="${relatie.id}" href="getPersoon/${relatie.id}"><c:out value="${relatie.naam}" /></a></li>
			</c:if>
			
			<c:if test="${relatie.getClass().name == 'nl.rsvier.icaras.core.relatiebeheer.Persoon'}">
				<li><a id="${relatie.id}" href="getPersoon/${relatie.id}"><c:out value="${relatie.voornaam}" /></a></li>
			</c:if>
			
		</c:forEach>
	</c:if>
	</ul>

</body>
</html>