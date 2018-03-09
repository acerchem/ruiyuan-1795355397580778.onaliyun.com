<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<template:javaScriptVariables/>
<c:url value="/j_spring_security_check" var="loginActionUrl" />

<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/style.css"/>	 

<body class="gray">
<form:form action="${loginActionUrl}" method="post" commandName="loginForm">
	<div class="sign-header">
		<a href="/acerchemstorefront">
			<img src="${themeResourcePath}/css/acerchem.png" alt="acerchem"/>
		</a>
	</div>
	<div class="sign-content" id="sign">
		<c:if test="${not empty message}">
			<span class="has-error"> <spring:theme code="${message}" />
			</span>
		</c:if>	
		<formElement:formInputBox idKey="j_username" labelKey="login.email" path="j_username" mandatory="true" />
		<formElement:formPasswordBox idKey="j_password" labelKey="login.password" path="j_password" inputCSS="form-control" mandatory="true" />
		<label>
			<input type="checkbox" name="Keep">
			<span class="checkbox">Keep me signed in</span><!-- alice: need update -->
			<a  href="#" data-link="<c:url value='/login/pw/request'/>" class="js-password-forgotten" data-cbox-title="<spring:theme code="forgottenPwd.title"/>">
				<spring:theme code="login.link.forgottenPwd" />
			</a>
		</label>
		<ycommerce:testId code="loginAndCheckoutButton">
			<div class="btn-set">
				<button type="submit" class="btn">
					<spring:theme code="login.login" />
				</button>
				<a class="btn btn-line" href="/acerchemstorefront/electronics/zh/login/register">Create your Amazon account</a>
			</div>
		</ycommerce:testId>
	</div>
</form:form>
</body>