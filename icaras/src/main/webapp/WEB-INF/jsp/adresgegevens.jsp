<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type= "text/javascript"">
function getPersoonId(){
	var table = document.getElementById('adresgegevens'),
    cells = table.getElementsByTagName('tdID');
	document.getElementById ( "tdid" ).innerText
}


</script>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Icaras - AdresGegevens</title>
<link href="includes/icaras.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="85%" border="1" align="center" cellpadding="0"
	cellspacing="0">
	<tr> <td>
	<table width="100%" border="0" align="center" cellpadding="4"
		cellspacing="0" bordercolor="#CCCCCC">
        <tr bgcolor="#c00000">
			<td width="90%" height="60" valign="middle">
			<h1 align="center">Adresgegevens</h1>
			</td>
	        
		</tr>
		<tr>
			<td align="center">
			
		Hieronder staan alle personen die op dit moment in de database staan.</td>
		</tr>
		</table>
		
		<table border="1" align="center" cellpadding="8" cellspacing="0" id="persoonLijst">
			
				<th>PersoonId</th>
				<th>Voornaam</th>
				<th>Achternaam</th>
				<th>Straat</th>
				<th>Huisnummer</th>
				<th>Postcode</th>
				<th>Plaatsnaam</th>
				
			
			<tr>
			      
				
					<td align="center" id="tdID"><c:out 
					value="${persoonAdres.persoonID}"
					
					 />
					</td>
					<td align="center">
					<c:out value="${persoonAdres.voornaam}" />
					</td>
					<td align="center">
					<c:out value="${persoonAdres.achternaam}" />
					</td>
					
					<td align="center"><c:out 
					value="${persoonAdres.adres.straat}" />
					</td>
					<td align="center">
					<c:out value="${persoonAdres.adres.huisnummer}" />
					</td>
					<td align="center"><c:out 
					value="${persoonAdres.adres.postcode}" />
					</td>
					<td align="center"><c:out 
					value="${persoonAdres.adres.plaatsnaam}" />
					</td>
					
			
				
					</tr>
				
		

	</table>
	</td></tr>
</table>
</body>

</html>
