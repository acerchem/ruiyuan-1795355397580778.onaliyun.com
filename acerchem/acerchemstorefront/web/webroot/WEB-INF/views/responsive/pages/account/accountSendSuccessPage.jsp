<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>

<script type="text/javascript">
	$().ready(function(){
		$("body").css("height","1441px");
	});
</script>

<spring:htmlEscape defaultHtmlEscape="true" />

<template:master pageTitle="${pageTitle}">
	<div class="gen-content maxsucce">
		<div class="g-cont">
			<div class="maxstatu">
				<img src="${themeResourcePath}/css/succ1.png" alt="ingredients4u"/>
				<h3>Successful send email!</h3>
			</div>
		</div>
	</div>
</template:master>




