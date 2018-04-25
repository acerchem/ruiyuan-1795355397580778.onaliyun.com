<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="showDeliveryAddress" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showPotentialPromotions" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/checkout/multi/summary/placeOrder" var="placeOrderUrl"/>


<c:set var="hasShippedItems" value="${cartData.deliveryItemsQuantity > 0}" />
<c:set var="deliveryAddress" value="${cartData.deliveryAddress}"/>


<div class="cart-total check-total">					
<div class="g-table">	
	<div class="g-title"><span>Order Summary</span></div>
	<ul class="check-item">
<c:forEach items="${cartData.entries}" var="entry" varStatus="loop">
	<c:url value="${entry.product.url}" var="productUrl"/>
	<li class="item both">	    		
   		<div class="img">
   			<a href="${productUrl}">
   		  		<product:productPrimaryImage product="${entry.product}" format="thumbnail"/>
   		  	</a>
   		</div>
   		<div class="maxtext">
    		<p class="in-title">${fn:escapeXml(entry.product.name)}</p>
    		<p class="spec">Specifications:<i>50kg</i></p>
    		<div class="spset">
    			<span class="price">
    			    <format:price priceData="${entry.basePrice}" displayFreeForZero="true" />
    			</span>
    			<span class="num">${entry.quantity}</span>
    		</div>
    	</div>
   	</li>
	
	
</c:forEach>

</ul>

<div class="Summary">
	<span>Part of your order qualifies for FREE Shipping;</span>
	<span>span.Buy 2 items  sales to 10% Off; </span>
</div>

<div class="list">
	<div class="item">
		<div class="item-row">
			<span>Subtotal</span>
			<span>
				<format:price priceData="${cartData.subTotal}"/>
			</span>
		</div>
		
		<div class="item-row">
			<span>Discount Amount</span>
			
            <span><format:price priceData="${cartData.totalDiscounts}"/></span>
		</div>

<c:if test="${not empty cartData.deliveryCost}">
		<div class="item-row">
			<span>Delivery</span>
			<span ><format:price priceData="${cartData.deliveryCost}"/></span>
		</div>
</c:if>
		

		<!-- <div class="item-row">
			<span>Discount Amount</span>
			<span class="row">-$12.34</span>
		</div> -->
		
			<div class="item-row">
				<span>Release Cost</span>
				
	              <span><format:price priceData="${cartData.storageCost}"/></span>
	               
			</div>
			
			<div class="item-row">
				<span>Handling Charge</span>
				
	              <span><format:price priceData="${cartData.operateCost}"/></span>
	               
			</div>
<c:if test="${not empty cartData.totalPrice}">
		<div class="item-row">
			<span>Order Total</span>
			<span >
			<format:price priceData="${cartData.totalPrice}"/>
			</span>
		</div>
		</c:if>
	</div>					
</div>		

<form:form action="${placeOrderUrl}" id="placeOrderForm1" commandName="placeOrderForm">	
 				
<!-- <div class="btn-set">							
	<a class="btn btn-submit" href="success.html">Place Order</a>
</div> -->

  <button id="placeOrder" type="submit" class="btn btn-submit">
      Place Order
  </button>

</form:form>						
</div>

</div>