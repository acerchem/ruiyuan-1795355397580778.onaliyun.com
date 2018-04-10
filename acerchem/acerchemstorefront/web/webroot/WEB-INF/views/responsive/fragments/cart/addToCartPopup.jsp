<%@ page trimDirectiveWhitespaces="true" contentType="application/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>

<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
{"cartData": {
"total": "${cartData.totalPrice.value}",
"products": [
<c:forEach items="${cartData.entries}" var="cartEntry" varStatus="status">
	{
		"sku":		"${fn:escapeXml(cartEntry.product.code)}",
		"name": 	"<c:out value='${cartEntry.product.name}' />",
		"qty": 		"${cartEntry.quantity}",
		"price": 	"${cartEntry.basePrice.value}",
		"categories": [
		<c:forEach items="${cartEntry.product.categories}" var="category" varStatus="categoryStatus">
			"<c:out value='${category.name}' />"<c:if test="${not categoryStatus.last}">,</c:if>
		</c:forEach>
		]
	}<c:if test="${not status.last}">,</c:if>
</c:forEach>
]
},

"quickOrderErrorData": [
<c:forEach items="${quickOrderErrorData}" var="quickOrderEntry" varStatus="status">
	{
		"sku":		"${fn:escapeXml(quickOrderEntry.productData.code)}",
		"errorMsg": "<spring:theme code='${quickOrderEntry.errorMsg}' htmlEscape="true"/>"
	}<c:if test="${not status.last}">,</c:if>
</c:forEach>
],

"cartAnalyticsData":{"cartCode" : "${cartCode}","productPostPrice":"${entry.basePrice.value}","productName":"<c:out value='${product.name}' />"}
,
"addToCartLayer":"<spring:escapeBody javaScriptEscape="true" htmlEscape="false">
	<spring:htmlEscape defaultHtmlEscape="true">
	<spring:theme code="text.addToCart" var="addToCartText"/>
	<c:url value="/cart" var="cartUrl"/>
	<ycommerce:testId code="addToCartPopup" >
		<div class="g-cartable maxfixed maxfixed-bg" style="display: block;">
	<div class="indwrap">
		<div class="cart-total">
			<div class="title">Shopping Cart</div>
			<form action="">
				<div class="maxfixed-over  product-table">
				
				<div class="cart_popup_error_msg">
	                 <c:choose>
		                <c:when test="${quickOrderErrorData ne null and not empty quickOrderErrorData}">
		                	<spring:theme code="${quickOrderErrorMsg}" arguments="${fn:length(quickOrderErrorData)}" />
	                    </c:when>
	                    <c:when test="${multidErrorMsgs ne null and not empty multidErrorMsgs}">
	                        <c:forEach items="${multidErrorMsgs}" var="multidErrorMsg">
	                            <spring:theme code="${multidErrorMsg}" />
	                        </c:forEach>
	                    </c:when>
	                    <c:otherwise>
	                        <spring:theme code="${errorMsg}" />
	                    </c:otherwise>
	                </c:choose> 
	            </div>
				
					<ul>
					
							
		            <c:choose>
		                <c:when test="${modifications ne null}">
		                    <c:forEach items="${modifications}" var="modification">
		                        <c:set var="product" value="${modification.entry.product}" />
		                        <c:set var="entry" value="${modification.entry}" />
		                        <c:set var="quantity" value="${modification.quantityAdded}" />
		                        <cart:popupCartItems entry="${entry}" product="${product}" quantity="${quantity}"/>
		                    </c:forEach>
		                </c:when>
		                <c:otherwise>
		
		                    <cart:popupCartItems entry="${entry}" product="${product}" quantity="${quantity}"/>
		                </c:otherwise>
		            </c:choose>
		            </ul>
				</div>
				<!-- 判断优惠 -->
				<div class="Summary">
					<span>Part of your order qualifies for FREE Shipping. </span>
				</div>
				<!-- 判断优惠 end-->
				<div class="list">
					<div class="item-row">
							
		            <c:choose>
		                <c:when test="${modifications ne null}">
		                   
		                        <span>Total (<em>${modifications.quantityAdded}</em> items)</span>
						        <span class="row total"><format:fromPrice priceData="${modifications.entry.totalPrice}"/></span>
		                   
		                </c:when>
		                <c:otherwise>
		
		                   <span>Total (<em>${quantity}</em> items)</span>
						   <span class="row total"><format:fromPrice priceData="${entry.totalPrice}"/></span>
		                </c:otherwise>
		            </c:choose>
						
					</div>			
				</div>
				  <ycommerce:testId code="checkoutLinkInPopup">
				<div class="btn-set">							
					<a class="btn btn-submit" href="checkout.html">Check Out</a>
					<a class="btn btn-continue" href="">Continue Shopping</a>
				</div>
				 </ycommerce:testId>
			</form>
		</div>
	</div>
</div>
		
	</ycommerce:testId>	
	</spring:htmlEscape>
</spring:escapeBody>"
}



