<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script>
	$(document).ready(function() {
		$(":button").button();
		$(":submit").button();
	});
</script>
<style type="text/css">
.totalbody {
	background-color: black;
	color: #EDD200;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>▒▒Angel-in-us Coffee▒▒</title>

<jsp:include page="header.jsp" flush="false" />
<decorator:head />
<jsp:include page="titletop.jsp" />

</head>
<body class="totalbody">
	<center>
		<table>
			<tr>
				<td colspan="4"><jsp:include page="titlebottom.jsp" /></td>
			</tr>
			<%-- <tr>
				<td><jsp:include page="menu.jsp" /></td>
				<td colspan="3" width="1000px" height="700px"><decorator:body /></td>
			</tr> --%>
			<tr style="height: 100%;" style="width: 1280px;" valign="top">
				<td style="width: 120px;" valign="top"><jsp:include
						page="menu.jsp" /></td>
				<td width="1100px" height="700px" valign="top"><decorator:body /></td>
			</tr>
			<tr>
				<td colspan="4"><jsp:include page="bottom.jsp" /></td>
			</tr>
		</table>
	</center>
</body>
</html>