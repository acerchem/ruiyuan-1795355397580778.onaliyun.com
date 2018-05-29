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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url value="/my-account/detailsConfirm/" var="confirmOrder"/>
<c:url value="/my-account/extendedPickup/" var="extendedPickup"/>
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
								    <i>
										${orderData.status=="CHECKED_VALID"?"UNCONFIRMED":""}
										${orderData.status=="UNDELIVERED"||orderData.status=="DELIVERED"?"PROCESSING":""}
										${orderData.status!="CHECKED_VALID"&&orderData.status!="UNDELIVERED"&&orderData.status!="DELIVERED"?orderData.status:""}
									</i>
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
						<div class="btn-set operate">
							<a class="btn btn-enter" href="${confirmOrder}${orderData.code}?confirm=receipt" style="${!orderData.customerConfirmDelivery && orderData.status=='DELIVERED'?'':'display: none;'}">Confirm Delivery</a>
							<a class="btn btn-enter" href="${confirmOrder}${orderData.code}?confirm=payment" style="${!orderData.customerConfirmPay && orderData.status=='UNPAIED' && orderData.paymentMode=='PRE-PAYMENT'?'':'display: none;'}">Confirm Payment</a>
							<a class="btn btn-line" href="${confirmOrder}${orderData.code}?confirm=cancel" style="${canCancel&&orderData.status!='CANCELLED'&&orderData.status!='DELIVERED'&&orderData.status!='COMPLETED'&&((orderData.status!='COMPLETED'&&orderData.paymentMode!='PRE-PAYMENT')||orderData.paymentMode=='PRE-PAYMENT')?'':'display: none;'}" class="cancelOrder">Cancel Order</a>
						</div>
					</div>
					<!-- end -->
                
					<div class="g-table">
                        <div class="g-title">
                            <span>Delivery Methods</span>
                        </div>
                        <div class="textlist">
                            <span>${orderData.deliveryMode.code=='DELIVERY_GROSS'?'DDP':'FCA'}</span>
                        </div>
                    </div>
					
                    <!-- Ship To -->
                    <div class="g-table">
                        <div class="g-title" style="${orderData.deliveryMode.code=='DELIVERY_GROSS'?'':'display: none;'}">
                            <span>Ship To</span>
                        </div>
                        <div class="g-title" style="${orderData.deliveryMode.code=='DELIVERY_MENTION'?'':'display: none;'}">
                            <span>Pickup Address</span>
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
                        
                        <c:set var ="a" value="${orderEntries.product.netWeight}"/>
							<c:set var="b" value="${orderEntries.quantity}"/>
							<c:choose>
							<c:when test="${not empty orderEntries.product.promotionPrice}">
							<c:set var="c" value="${orderEntries.promotionBasePrice.value}"/>
							</c:when>
							
							<c:otherwise>
							<c:set var="c" value="${orderEntries.product.price.value}"/>
							</c:otherwise>
							
							</c:choose>
						    <c:set var ="Total" value="${a*b*c}"/>
						    
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
									<span class="spec">Package:<i>${orderEntries.product.netWeight}kg/${orderEntries.product.packageType}</i></span>
									<span class="old-price">price:<i><format:price priceData="${orderEntries.basePrice}" displayFreeForZero="true" /></i></span>
								</span>
                                    </div>
                                </td>
                                
                                <c:choose>
									<c:when test="${not empty orderEntries.product.promotionPrice}">
									<td><b><format:price priceData="${orderEntries.product.promotionPrice}" displayFreeForZero="true"/></b></td>
									</c:when>
									
									<c:otherwise>
									<td><b><format:price priceData="${orderEntries.product.price}" displayFreeForZero="true"/></b></td>
									</c:otherwise>
								</c:choose>
                                
                                <td>${orderEntries.quantity}*${orderEntries.product.netWeight}${orderEntries.product.unitName}</td>
                                <td>
                                        ${fn:escapeXml(orderEntries.product.code)}
                                </td>
                                <td>
                                    <div class="tot">
                                        <em>
                                        	${fn:substring(orderEntries.totalPrice.formattedValue, 0, 1)}
                                    		<fmt:formatNumber type="number" value="${Total}" pattern="#.00"/>
                                        </em>
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
                            <%-- <span>Billing Address</span>
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
                            </div> --%>
                            <span>Payment Type</span>
                            <div class="text">
                                ${orderData.paymentMode}
                            </div>
                        </div>
                    </div>
                    <!-- end -->

                    <!-- Summary -->
                    <div class="g-table">
                        <div class="g-title">
                            <span>Summary</span>
                        </div>
                        <div class="list ord-total">
                            <div class="item">
								<span>
									<em>Subtotal</em>
									<i>
										${fn:substring(orderData.subTotal.formattedValue, 0, 1)}
										<fmt:formatNumber type="number" value="${orderData.subTotal.value+orderData.totalDiscounts.value}" pattern="#.00"/>
									</i>
								</span>
		
		                        <span>
									<em>Delivery</em>
									<i><format:price priceData="${orderData.deliveryCost}"/></i>
								</span>
								
								<span>
									<em>Release Cost</em>
									<i><format:price priceData="${orderData.storageCost}"/></i>
								</span>
								
								<span>
									<em>Handling Charge</em>
									<i><format:price priceData="${orderData.operateCost}"/></i>
								</span>
		
		                        <span>
									<em>Discount</em>
									<i>-<format:price priceData="${orderData.totalDiscounts}"/></i>
								</span>
		
		                        <span>
									<em>Total</em>
									<i><format:price priceData="${orderData.totalPrice}"/></i>
								</span>
		                     </div>
		                 </div>
		             </div>
		             
		             <div class="g-table">
                        <div class="g-title">
                            <span>Date</span>
                        </div>
                        <div>
                            <span style="padding-left:20px;width:100%;${orderData.deliveryMode.code=='DELIVERY_GROSS'?'':'display: none;'}">
                            	Delivery Date:
                            	<fmt:formatDate value="${orderData.pickupDateOfExtended==null?orderData.pickUpDate:orderData.pickupDateOfExtended}" pattern="yyyy-MM-dd"/> <br/>
								Estimated Delivery Date:${orderData.waitDeliveiedDate}
                            </span>
                            <span style="padding-left:20px;width:100%;${orderData.deliveryMode.code=='DELIVERY_MENTION'?'':'display: none;'}">
                            	Pickup Date:
                            	<fmt:formatDate value="${orderData.pickupDateOfExtended==null?orderData.pickUpDate:orderData.pickupDateOfExtended}" pattern="yyyy-MM-dd"/> <br/>
                            </span>
                            
                            <div style="${orderData.pickupDateOfExtended==null?'':'display: none;'}">
				             	<span>
				             		Extended Days(Max days:${maxday}):
				             		<input type="text" name='pickupDays' style="width:80px; height:40px;"/>
				             		<a class="pickup" href="#" style="display: inline;background: #28FF28;">Confirm</a>
				             	</span>
								<script type="text/javascript">									
									inputint()	
									$('.pickup').on('click',function(){
										var days = document.getElementsByName('pickupDays')[0].value;
									    if(isNaN(days)){
									    	maxalert('Please enter positive integer!');
									        return false;
									    }
									    else if(days<=${maxday}&&days>0)
										{
											window.location.href="${extendedPickup}${orderData.code}?days="+days;
											return false;	
										}
										else
										{
											maxalert('Please enter Less than ${maxday} days!');
											return false;
										}
									})
								</script>
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