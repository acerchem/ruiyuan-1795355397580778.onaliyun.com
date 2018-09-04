<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>

<template:page pageTitle="${pageTitle}">
	
<script type="text/javascript">
reLogin();
function reLogin(){
	alert("Please Re-login");
	$.ajax({
		url: ACC.config.encodedContextPath + '/logout',
		async: true,
		dataType: "html"
	});
	window.location.href ='<c:url value='/login'/>';
} 
</script>
	
</template:page>


