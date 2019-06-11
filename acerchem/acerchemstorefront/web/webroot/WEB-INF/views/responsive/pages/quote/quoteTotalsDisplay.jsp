<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
    ~ /*
    ~  * [y] hybris Platform
    ~  *
    ~  * Copyright (c) 2000-2017 SAP SE or an SAP affiliate company.
    ~  * All rights reserved.
    ~  *
    ~  * This software is the confidential and proprietary information of SAP
    ~  * ("Confidential Information"). You shall not disclose such Confidential
    ~  * Information and shall use it only in accordance with the terms of the
    ~  * license agreement you entered into with SAP.
    ~  *
    ~  */
--%>

<c:if test="${not empty quoteData}">
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
                        ${fn:substring(quoteData.subTotal.formattedValue, 0, 1)}
                        <fmt:formatNumber type="number" value="${quoteData.subTotal.value+quoteData.totalDiscounts.value}" pattern="#.00"/>
                    </i>
                </span>
                <span>
                    <em>Delivery</em>
                    <i><format:price priceData="${quoteData.deliveryCost}"/></i>
                </span>

                <span>
                    <em>Release Cost</em>
                    <c:choose>
                        <c:when test="${quoteData.storageCost!=null}">
                            <i><format:price priceData="${quoteData.storageCost}"/></i>
                        </c:when>
                        <c:otherwise>
                            <i>$0.00</i>
                        </c:otherwise>
                    </c:choose>
                </span>

                <span>
                    <em>Handling Charge</em>
                    <c:choose>
                        <c:when test="${quoteData.operateCost!=null}">
                            <i><format:price priceData="${quoteData.operateCost}"/></i>
                        </c:when>
                        <c:otherwise>
                            <i>$0.00</i>
                        </c:otherwise>
                    </c:choose>
                </span>

                <span>
                    <em>Discount</em>
                    <i>-<format:price priceData="${quoteData.totalDiscounts}"/></i>
                </span>

                <span>
                    <em>Total</em>
                    <i><format:price priceData="${quoteData.totalPrice}"/></i>
                </span>
            </div>
        </div>
    </div>

</c:if>