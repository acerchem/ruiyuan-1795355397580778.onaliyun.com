<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="order" required="true" type="de.hybris.platform.commercefacades.order.data.OrderData" %>
<%@ attribute name="consignment" required="true" type="de.hybris.platform.commercefacades.order.data.ConsignmentData" %>
<%@ attribute name="inProgress" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<spring:htmlEscape defaultHtmlEscape="true" />



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
	<table class="list">
	
	<c:forEach items="${orderData.entries}" var="entry" varStatus="loop">
	
	<c:set var ="a" value="${entry.product.netWeight}"/>
	<c:set var="b" value="${entry.quantity}"/>
	<c:choose>
	<c:when test="${not empty entry.product.promotionPrice}">
	<c:set var="c" value="${entry.product.promotionPrice.value}"/>
	</c:when>
	
	<c:otherwise>
	<c:set var="c" value="${entry.product.price.value}"/>
	</c:otherwise>
	
	</c:choose>
    <c:set var ="Total" value="${a*b*c}"/>
	    <tr>
	<td>
		<div class="intro">
			<span class="minflex">
				 <a href="${entry.product.url}">
                <product:productPrimaryImage product="${entry.product}" format="thumbnail"/>
            </a>
			</span>
			<span class="minflex text">
				<span class="in-title">${fn:escapeXml(entry.product.name)}</span>
				<span class="price">package:<i>${entry.product.netWeight}${entry.product.unitName} &nbsp ${entry.product.packageType}</i></span>	
				<span class="old-price">price:<i><format:price priceData="${entry.product.price}" displayFreeForZero="true"/></i></span>
			</span>								
		</div>
	</td>
	<c:choose>
	<c:when test="${not empty entry.product.promotionPrice}">
	<td><b><format:price priceData="${entry.product.promotionPrice}" displayFreeForZero="true"/></b></td>
	</c:when>
	
	<c:otherwise>
	<td><b><format:price priceData="${entry.product.price}" displayFreeForZero="true"/></b></td>
	</c:otherwise>
	</c:choose>
	<td><b> ${entry.quantity}*${entry.product.netWeight}${entry.product.unitName}</b></td>
	<td><b>${entry.product.code}</b></td>
	<td>
		<div class="tot">
		${fn:substring(entry.product.price.formattedValue, 0, 1)}${Total}
		<%--	<format:price priceData="${TotalPrice}" displayFreeForZero="true"/>--%>
			<%-- <i><format:price priceData="${entry.totalPrice}" displayFreeForZero="true"/></i> --%>
		</div>
	</td>
</tr>
	
<%-- 		<order:orderEntryDetails orderEntry="${entry.orderEntry}" consignmentEntry="${entry}" order="${order}" itemIndex="${loop.index}"/>
 --%>	</c:forEach>
		
	</table>			
</div>