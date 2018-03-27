<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>

<spring:url value="/my-account/update-profile" var="updateProfileUrl"/>
<spring:url value="/my-account/update-password" var="updatePasswordUrl"/>
<spring:url value="/my-account/update-email" var="updateEmailUrl"/>
<spring:url value="/my-account/address-book" var="addressBookUrl"/>
<spring:url value="/my-account/payment-details" var="paymentDetailsUrl"/>
<spring:url value="/my-account/orders" var="ordersUrl"/>

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