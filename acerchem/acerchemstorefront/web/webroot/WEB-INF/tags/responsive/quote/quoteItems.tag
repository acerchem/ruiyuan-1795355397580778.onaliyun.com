<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="quoteData" required="true" type="de.hybris.platform.commercefacades.order.data.AbstractOrderData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<div class="g-table product-table">
    <div class="g-title">
        <table>
            <tr>
                <td><div class="intro">Item <em class="min">(style number)</em></div></td>
                <td><spring:theme code="basket.page.price"/></td>
                <td><spring:theme code="basket.page.qty"/></td>
                <td>ID</td>
                <td><spring:theme code="basket.page.total"/></td>
            </tr>
        </table>
    </div>
    <table class="list">
        <c:forEach items="${quoteData.entries}" var="orderEntry" varStatus="loop">
            <c:url value="${orderEntry.product.url}" var="productUrl"/>
        <tr>
            <td>
                <div class="intro">
                    <span class="minflex">

                        <a href="#">
                            <img src="${orderEntry.product.images.get(0).url}" alt="">
                        </a>
                    </span>
        <span class="minflex text">
                    <span class="in-title">${fn:escapeXml(orderEntry.product.name)}</span>
                    <span class="spec">Package:<i>${orderEntry.product.netWeight}kg/${orderEntry.product.packageType}</i></span>
                    <span class="old-price">price:<i><format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" /></i></span>
                    </span>
                </div>
            </td>
            <td>
                <span class="visible-xs visible-sm"><spring:theme code="basket.page.itemPrice"/>:</span>
                <format:price priceData="${orderEntry.basePrice}" displayFreeForZero="true" />
            </td>
            <td>${orderEntry.quantity}*${orderEntry.product.netWeight}${orderEntry.product.unitName}</td>
            <td>
                ${fn:escapeXml(orderEntry.product.code)}
            </td>
            <td>
                <div class="tot">
                    <em>
                        ${fn:substring(orderEntry.totalPrice.formattedValue, 0, 1)}
                        <c:set var ="a" value="${orderEntry.product.netWeight}"/>
                        <c:set var="b" value="${orderEntry.quantity}"/>
                        <c:choose>
                            <c:when test="${not empty orderEntry.product.promotionPrice}">
                                <c:set var="c" value="${orderEntry.promotionBasePrice.value}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="c" value="${orderEntry.product.price.value}"/>
                            </c:otherwise>
                        </c:choose>
                        <c:set var="Total" value="${a*b*c}"/>
                        <fmt:formatNumber type="number" value="${Total}" pattern="#.00"/>
                    </em>
                </div>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>
