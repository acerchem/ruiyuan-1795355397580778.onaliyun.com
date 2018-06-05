<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>

<template:page pageTitle="${pageTitle}">
	<div class="member-content">
		<user:personalInfo/>
		<div class="sign-content g-right">
            <cms:pageSlot position="BodyContent" var="feature" element="div">
                <cms:component component="${feature}" />
            </cms:pageSlot>
		</div>
        <user:PromotionItem/>
	</div>
</template:page>