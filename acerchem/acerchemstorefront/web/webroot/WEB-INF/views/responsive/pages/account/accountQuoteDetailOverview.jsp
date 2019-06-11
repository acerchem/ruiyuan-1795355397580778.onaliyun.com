<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<spring:htmlEscape defaultHtmlEscape="true"/>
<spring:url value="/my-account/my-quotes/" var="quoteBaseLink" htmlEscape="false" />
<spring:url value="/quote/${ycommerce:encodeUrl(quoteData.code)}/checkout/" var="acceptQuoteAndCheckoutUrl" htmlEscape="false" />
<spring:url value="/quote/${ycommerce:encodeUrl(quoteData.code)}/edit/" var="editQuoteUrl" htmlEscape="false" />
<spring:url value="/quote/${ycommerce:encodeUrl(quoteData.code)}/submit/" var="submitQuoteUrl" htmlEscape="false" />
<spring:url value="/quote/${ycommerce:encodeUrl(quoteData.code)}/approve/" var="approveQuoteUrl" htmlEscape="false" />
<spring:url value="/quote/${ycommerce:encodeUrl(quoteData.code)}/reject/" var="rejectQuoteUrl" htmlEscape="false" />
<spring:url value="/quote/${ycommerce:encodeUrl(quoteData.code)}/requote/" var="requoteUrl" htmlEscape="false" />

<div class="rigcont">
    <div class="title">Quote Detail</div>
    <div class="rigcont">
        <!-- Summary -->
        <div class="g-table">
            <div class="g-title">
                <span>Quote Summary</span>
            </div>

            <div class="list">
                <div class="item">
                    <span>
                        <em>Quote Number</em>
                        <i>${quoteData.code}</i>
                    </span>

                    <span>
                        <em>Quote Status</em>
                        <i>
                            ${quoteData.state=="BUYER_SUBMITTED"?"SUBMITTED":""}
                        </i>
                    </span>

                    <span>
                        <em>Date Placed</em>
                        <i><fmt:formatDate value="${quoteData.creationTime}" dateStyle="medium" timeStyle="short" type="both"/></i>
                    </span>

                    <span>
                        <em>Total</em>
                        <i><format:price priceData="${quoteData.totalPrice}"/></i>
                    </span>
                </div>
            </div>
        </div>
        <!-- end -->

        <div class="g-table">
            <div class="g-title">
                <span>Delivery Methods</span>
            </div>
            <div class="textlist">
                <span>${quoteData.deliveryMode.code=='DELIVERY_GROSS'?'DDP':'FCA'}</span>
            </div>
        </div>

        <!-- Ship To -->
        <div class="g-table">
            <div class="g-title" style="${quoteData.deliveryMode.code=='DELIVERY_GROSS'?'':'display: none;'}">
                <span>Ship To</span>
            </div>
            <div class="g-title" style="${quoteData.deliveryMode.code=='DELIVERY_MENTION'?'':'display: none;'}">
                <span>Pickup Address</span>
            </div>
            <div class="text">
                <%-- Snowy Xu 2018-09-06
               <c:if test="${not empty orderData.deliveryAddress.title}">
                   ${fn:escapeXml(orderData.deliveryAddress.title)}&nbsp;
               </c:if>
                   ${fn:escapeXml(orderData.deliveryAddress.firstName)}&nbsp;${fn:escapeXml(orderData.deliveryAddress.lastName)}
                --%>
                ${fn:escapeXml(companyName)}
                <br/>

                <c:if test="${not empty quoteData.deliveryAddress.line1}">
                    ${fn:escapeXml(quoteData.deliveryAddress.line1)}&nbsp;
                </c:if>

                <c:if test="${not empty quoteData.deliveryAddress.line2}">
                    ${fn:escapeXml(quoteData.deliveryAddress.line2)}&nbsp;
                </c:if>

                <c:if test="${not empty quoteData.deliveryAddress.town}">
                    ${fn:escapeXml(quoteData.deliveryAddress.town)}&nbsp;
                </c:if>

                <c:if test="${not empty quoteData.deliveryAddress.region.name}">
                    ${fn:escapeXml(quoteData.deliveryAddress.region.name)}&nbsp;
                </c:if>

                <c:if test="${not empty quoteData.deliveryAddress.country.name}">
                    ${fn:escapeXml(quoteData.deliveryAddress.country.name)}&nbsp;
                </c:if>

                <c:if test="${not empty quoteData.deliveryAddress.postalCode}">
                    ${fn:escapeXml(quoteData.deliveryAddress.postalCode)}
                </c:if>

                <br/>
                ${fn:escapeXml(quoteData.deliveryAddress.phone)}

            </div>
        </div>

        <!-- end -->
    </div>
</div>
