<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="notificationBtnText">
	<spring:theme code="product.out.of.stock.notify" />
</c:set>
<c:set var="iconClass">
	glyphicon-envelope
</c:set>
<c:set var="colorBoxTitle">
	<spring:theme code="text.stock.notification.subscribe.title" />
</c:set>
<c:set var="watching">
	false
</c:set>
<c:if test="${not empty product.isWatching and product.isWatching}">
	<c:set var="notificationBtnText">
		<spring:theme code="product.notification.not.notify" />
	</c:set>
	<c:set var="colorBoxTitle">
		<spring:theme code="text.stock.notification.cancel.title" />
	</c:set>
	<c:set var="iconClass">
		glyphicon-eye-close
	</c:set>
	<c:set var="watching">
		true
	</c:set>
</c:if>

<div class="text-out-of-stock">
	<span> <spring:theme code="product.out.of.stock"
			text="OUT OF STOCK" />
	</span>
</div>

<c:if test="${not empty product.isWatching and product.isWatching}">
	<div class="notification-subscribed">
		<span><spring:theme code="text.stock.notification.subscribed" /></span>
	</div>
</c:if>

<button id="arrival-notification" data-box-title="${colorBoxTitle}"
	data-watching="${watching}" disabled="disabled" type="${buttonType}"
	class="btn btn-primary btn-block js-add-to-cart btn-icon ${iconClass} outOfStock">
	${notificationBtnText}</button>

<input type="hidden" name="pickUpInStoreBtnText"
	value="<spring:message code='check.availability.in.store'/>" />

