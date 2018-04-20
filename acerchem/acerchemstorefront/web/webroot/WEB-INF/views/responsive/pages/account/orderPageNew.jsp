<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:url value="/my-account/detailsConfirm/" var="confirmOrder"/>
<link rel="stylesheet" type="text/css" href="https://electronics.local:9002/acerchemstorefront/_ui/desktop/common/css/orderCSS/general.css" />
<link rel="stylesheet" type="text/css" href="https://electronics.local:9002/acerchemstorefront/_ui/desktop/common/css/orderCSS/min.css" />

<spring:url value="/my-account/update-profile" var="updateProfileUrl"/>
<spring:url value="/my-account/update-password" var="updatePasswordUrl"/>
<spring:url value="/my-account/update-email" var="updateEmailUrl"/>
<spring:url value="/my-account/address-book" var="addressBookUrl"/>
<spring:url value="/my-account/payment-details" var="paymentDetailsUrl"/>
<spring:url value="/my-account/orders" var="ordersUrl"/>
<spring:url value="/my-account/orders" var="orderHistoryUrl"/>

<template:page pageTitle="${pageTitle}">
    <div class="member-content">
        <user:personalInfo/>
        <div class="sign-content g-right">
        	<div class="rigcont">
                <div class="title">Order Detail</div>
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
									<i>${orderData.code}</i>
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
								<span>
									<i>
										<a href="${confirmOrder}${orderData.code}?confirm=order" style="${orderData.customerConfirm?'display: none;':''}">Confirm Order</a>
										<a href="${confirmOrder}${orderData.code}?confirm=receipt" style="${orderData.customerConfirmDelivery?'display: none;':''}">Confirm Delivery</a>
										<a href="${confirmOrder}${orderData.code}?confirm=payment" style="${orderData.customerConfirmPay?'display: none;':''}">Confirm Payment</a>
									</i>
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

                              <c:if test="${not empty orderData.deliveryAddress.title}">
                                  ${fn:escapeXml(orderData.deliveryAddress.title)}&nbsp;
                              </c:if>
                                  ${fn:escapeXml(orderData.deliveryAddress.firstName)}&nbsp;${fn:escapeXml(orderData.deliveryAddress.lastName)}
                              <br>
                                  ${fn:escapeXml(orderData.deliveryAddress.line1)}
                              <c:if test="${not empty orderData.deliveryAddress.line2}">
                                  &nbsp;
                                  ${fn:escapeXml(orderData.deliveryAddress.line2)}
                              </c:if>
                              &nbsp;
                                  ${fn:escapeXml(orderData.deliveryAddress.town)}&nbsp;${fn:escapeXml(orderData.deliveryAddress.region.name)}
                              &nbsp;
                                  ${fn:escapeXml(orderData.deliveryAddress.country.name)}&nbsp;${fn:escapeXml(orderData.deliveryAddress.postalCode)}
                              <br/>
                                  ${fn:escapeXml(orderData.deliveryAddress.phone)}

                        </div>
                    </div>
                    <!-- end -->

                    <!-- product Item -->
                    <div class="g-table product-table">
                        <div class="g-title">
                            <table>
                                <tr>
                                    <td><div class="intro">Item <em class="min">(style number)</em></div></td>
                                    <td>Price</td>
                                    <td>Qty</td>
                                    <td>ID</td>
                                    <td>Total</td>
                                </tr>
                            </table>

                        </div>

                        <c:forEach items="${orderData.entries}" var="orderEntries">
                        <table class="list">
                            <tr>
                                <td>
                                    <div class="intro">
								<span class="minflex">

                                    <a href="#">
                                        <img src="${orderEntries.product.images.get(0).url}" alt="">
									</a>
								</span>
                                        <span class="minflex text">
									<span class="in-title">${fn:escapeXml(orderEntries.product.name)}</span>
									<span class="spec">Specifications:<i>50kg</i></span>
									<span class="old-price">price:<i><format:price priceData="${orderEntries.basePrice}" displayFreeForZero="true" /></i></span>
								</span>
                                    </div>
                                </td>
                                <td><format:price priceData="${orderEntries.basePrice}" displayFreeForZero="true" /></td>
                                <td> ${orderEntries.quantity}</td>
                                <td>
                                        ${fn:escapeXml(orderEntries.product.code)}
                                </td>
                                <td>
                                    <div class="tot">
                                        <em><format:price priceData="${orderEntries.basePrice}" displayFreeForZero="true" /></em>
                                        <i> <format:price priceData="${orderEntries.totalPrice}" displayFreeForZero="true"/></i>
                                    </div>
                                </td>
                            </tr>

                        </table>
                        </c:forEach>

                    </div>
                    <!-- end -->

                    <!-- Billing -->
                    <div class="g-table">
                        <div class="g-title">
                            <span>Billing Information</span>
                        </div>
                        <div class="textlist">
                            <span>Billing Address</span>
                            <div class="text">
                                  <c:if test="${not storeAddress }">
                                      <c:if test="${not empty orderData.paymentInfo.billingAddress.title}">
                                          ${fn:escapeXml(orderData.paymentInfo.billingAddress.title)}&nbsp;
                                      </c:if>
                                      ${fn:escapeXml(orderData.paymentInfo.billingAddress.firstName)}&nbsp;${fn:escapeXml(orderData.paymentInfo.billingAddress.lastName)}
                                      <br>
                                  </c:if>
                                      ${fn:escapeXml(orderData.paymentInfo.billingAddress.line1)}
                                  <c:if test="${not empty orderData.paymentInfo.billingAddress.line2}">
                                      &nbsp;
                                      ${fn:escapeXml(orderData.paymentInfo.billingAddress.line2)}
                                  </c:if>
                                  &nbsp;
                                      ${fn:escapeXml(orderData.paymentInfo.billingAddress.town)}&nbsp;${fn:escapeXml(orderData.paymentInfo.billingAddress.region.name)}
                                  &nbsp;
                                      ${fn:escapeXml(orderData.paymentInfo.billingAddress.country.name)}&nbsp;${fn:escapeXml(orderData.paymentInfo.billingAddress.postalCode)}
                                  <br/>
                                      ${fn:escapeXml(orderData.paymentInfo.billingAddress.phone)}
                            </div>
                            <span>Payment Type</span>
                            <div class="text">
                                MasterCard<br/>
                                ${orderData.paymentInfo.cardNumber}
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
		
		                        <span>
									<em>Discount Amount</em>
									<i>- <format:price priceData="${orderData.orderDiscounts}"/></i>
								</span>
		
		                        <span>
									<em>Order Total</em>
									<i><format:price priceData="${orderData.totalPrice}"/></i>
								</span>
		                     </div>
		                 </div>
		             </div>
		             <div class="btn-set">
		                <a class="btn btn-back" href="javascript:window.history.back()">Back</a>
		             </div>
		        </div>
		    </div>
		</div>
		<user:PromotionItem/>
	</div>
</template:page>