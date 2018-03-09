<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>

<template:page pageTitle="${pageTitle}">
<c:url value="/j_spring_security_check" var="loginActionUrl" />
<div class="login-section">
	<user:login actionNameKey="login.login" action="${loginActionUrl}"/>
</div>
</template:page>
