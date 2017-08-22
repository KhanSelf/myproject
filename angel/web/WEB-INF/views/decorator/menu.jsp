<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<style>

#accordion {
	height: 900px;
	width: 250px;
}

#url_menu{
	height:100px;
}
</style>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#accordion").accordion({"active":true, "collapsible" : true});
						$("#login").button().click(login);
						$("#logout").button().click(logout);
						function login() {
							location.href = '${pageContext.request.contextPath}/loginform.html';
						}
						function logout() {
							location.href = '${pageContext.request.contextPath}/login.do?method=logout';
						}
					});
</script>
</head>
<body>
	<c:if test='${sessionScope.empNo == null}'>
		<a id="login">로그인</a>
	</c:if>
	<c:if test='${sessionScope.empNo != null}'>
		 <a id="logout">로그아웃</a>
		<br />
		<br />
		<div id="accordion"  align="top">
			<c:forEach items="${sessionScope.menu}" var="Menu">
				<c:if test="${Menu.level==1 }">
					<h3>${Menu.menuName }</h3>
					<div id="url_menu">
						<c:forEach items="${sessionScope.menu}" var="subMenu">
							<c:if test="${subMenu.parentMenuCode==Menu.menuCode}">
								<a href="${pageContext.request.contextPath}/${subMenu.url}">${subMenu.menuName}</a>
								<br>
							</c:if>
						</c:forEach>
					</div>
				</c:if>
			</c:forEach>
		</div>
	</c:if>

</body>
</html>