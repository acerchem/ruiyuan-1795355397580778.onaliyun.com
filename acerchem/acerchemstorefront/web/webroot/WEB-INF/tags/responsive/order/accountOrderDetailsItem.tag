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
				<span class="price">Specifications:<i>50kg</i></span>	
				<span class="old-price">price:<i><format:price priceData="${entry.product.price}" displayFreeForZero="true"/></i></span>
			</span>								
		</div>
	</td>
	<td><b><format:price priceData="${entry.product.price}" displayFreeForZero="true"/></b></td>
	<td><b> ${entry.quantity}</b></td>
	<td><b>${entry.product.code}</b></td>
	<td>
		<div class="tot">
			<em><format:price priceData="${entry.totalPrice}" displayFreeForZero="true"/></em>
			<i><format:price priceData="${entry.totalPrice}" displayFreeForZero="true"/></i>
		</div>
	</td>
</tr>
	
<%-- 		<order:orderEntryDetails orderEntry="${entry.orderEntry}" consignmentEntry="${entry}" order="${order}" itemIndex="${loop.index}"/>
 --%>	</c:forEach>
		
	</table>			
</div>