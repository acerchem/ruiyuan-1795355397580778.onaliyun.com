<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<template:page pageTitle="${pageTitle}">

<!--  
	<cart:cartValidation/>
	<cart:cartPickupValidation/>
	-->
	
		 <c:if test="${not empty cartData.rootGroups}">
		 
			 <div class="g-cont">
					<div class="m-no">Cart ID:${fn:escapeXml(cartData.code)}</div>
					<div class="g-cartable">
					 <cms:pageSlot position="TopContent" var="feature">
				         <cms:component component="${feature}" />
				     </cms:pageSlot>
				     
		            <cms:pageSlot position="CenterRightContentSlot" var="feature">
		                <cms:component component="${feature}" />
		            </cms:pageSlot>
		            
		            </div>
		     </div>
           
		</c:if>

			<c:if test="${empty cartData.rootGroups}">
            <cms:pageSlot position="EmptyCartMiddleContent" var="feature">
                <cms:component component="${feature}" element="div" class="yComponentWrapper content__empty"/>
            </cms:pageSlot>
		</c:if>


    </div>
    <user:PromotionItem/>
    </div>
	
	

</template:page>