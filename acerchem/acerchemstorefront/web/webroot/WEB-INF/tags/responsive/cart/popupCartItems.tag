<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="quantity" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />
	<c:url value="${product.url}" var="entryProductUrl"/>
	<!--shaun  -->
	<ul>
		<li class="item">
			<!--image  -->	
    		<div class="img">
    			<a href="${entryProductUrl}">
    		  		<product:productPrimaryImage product="${entry.product}" format="cartIcon"/>
    		  	</a>
    		</div>
    		
    		<div class="maxtext">
    		<p class="in-title"><a href="${entryProductUrl}">${fn:escapeXml(product.name)}</a></p>
				<p class="spec">package:<i>${product.netWeight}${product.unitName}/${product.packageType}</i></p>
				<div class="spset"><span class="price">
				<c:if test="${not empty product.promotionPrice}">
				<format:fromPrice priceData="${product.promotionPrice}"/>
				</c:if>
				
				<c:if test="${empty product.promotionPrice}">
				<format:fromPrice priceData="${product.price}"/>
				</c:if>
				</span>
				
				<span class="num">${quantity}</span>
				</div>
			</div>
    	</li>
	</ul>








