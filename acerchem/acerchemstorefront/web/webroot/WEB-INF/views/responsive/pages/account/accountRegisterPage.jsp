<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>

<template:page pageTitle="${pageTitle}">
<div class="register__section">
	<c:url value="/login/register" var="registerActionUrl" />
	<user:register actionNameKey="register.submit" action="${registerActionUrl}" />
</div>
</template:page>