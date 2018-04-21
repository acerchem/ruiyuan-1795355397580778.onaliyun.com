<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<template:page pageTitle="${pageTitle}">
  
 <div class="gen-content maxsucce">
	<div class="g-cont">
		<div class="maxstatu">
			<img src="images/succ1.png"/>
			<h3>Thank You For Your Order!</h3>
			<p>Your Order Number is ${fn:escapeXml(orderData.code)}</p>
			<p>A copy of your order details has been sent to <span>${fn:escapeXml(email)}</span></p>
		</div>
		<!--s-->
		<div class="rigcont">
			<!-- Summary -->
			<div class="g-table">
				<div class="g-title">
					<span>Order Summary</span>
				</div>

				<div class="list">
					<div class="item">
						<span>
							<em>Order Number</em>
							<i>${fn:escapeXml(orderData.code)}</i>
						</span>

						<span>
							<em>Order Status</em>
							<i><spring:theme code="text.account.order.status.display.${orderData.statusDisplay}"/></i>
						</span>

						<span>
							<em>Date Placed</em>
							<i><fmt:formatDate value="${orderData.created}" dateStyle="medium" timeStyle="short" type="both"/></i>
						</span>

						<span>
							<em>Total</em>
							<i><format:price priceData="${orderData.totalPrice}"/></i>
						</span>
					</div>					
				</div>
			</div>
			<!-- end -->

			<!-- Ship To -->
			<div class="g-table">
				<div class="g-title">
					<span>Ship To</span>
				</div>
				<div class="text">
					<order:addressItem address="${orderData.deliveryAddress}"/>
				</div>				
			</div>
			<!-- end -->

			<!-- product Item -->
		
			<order:accountOrderDetailsItem order="${orderData}" consignment="${consignment}" inProgress="true"/>
			  
	 
        			
			<!-- end -->

			<!-- Billing -->
			<div class="g-table">
				<div class="g-title">
					<span>Billing Information</span>
				</div>
				<div class="textlist">
					
					<span>Payment Info</span>
					<div class="text">
						xinyong pay<br/>
						
					</div>		
				</div>			
			</div>
			<!-- end -->

			<!-- Summary -->
			<div class="g-table">
				<div class="g-title">
					<span>Summary</span>
				</div>
				<div class="Summary">
					<span>Part of your order qualifies for FREE Shipping;</span>
					<span>span.Buy 2 items  sales to 10% Off; </span>
				</div>
				<div class="list ord-total">
					<div class="item">
						<span>
							<em>Subtotal</em>
							<i><format:price priceData="${orderData.subTotal}"/></i>
						</span>

						<span>
							<em>Delivery</em>
							<i><format:price priceData="${orderData.deliveryCost}"/></i>
						</span>
<!-- 
						<span>
							<em>Discount Amount</em>
							<i>- $ 18.00</i>
						</span> -->

						<span>
							<em>Order Total</em>
							<i><format:price priceData="${orderData.totalPrice}"/></i>
						</span>
					</div>					
				</div>			
			</div>
			<!-- end -->
			
			<div class="btn-set">				
				<a class="btn btn-back" href="index.html">Continue Shopping</a>
			</div>
		</div>
		<!--s end-->
	</div>	

</div>
</template:page>