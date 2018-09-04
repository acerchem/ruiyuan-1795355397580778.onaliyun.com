<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />


<c:if test="${component.isSale}">
	<div class="g-title">
		Everyone is Buying
	</div>
	<ul class="pdlist">
		<c:forEach items="${productData}" var="product">
			<c:url value="${product.url}" var="productUrl"/>
			<li class="both">
				<div class="img">
		   			<a href="${productUrl}"><product:productPrimaryImage product="${product}" format="product"/></a>
		   		</div>
		   		<div class="maxtext">
		    		<p class="in-title">${fn:escapeXml(product.name)} (${product.netWeight}kg ${product.packageType})</p>
		    		<div class="spset">
		    		
		    		<c:choose>
							<c:when test="${not empty product.promotionPrice}">
							
			  					<span class="price">${product.promotionPrice.formattedValue}</span>
		    			<span class="old-price">${product.price.formattedValue}</span>
							</c:when>
						<c:otherwise>
							<span class="price">${product.price.formattedValue}</span>
						</c:otherwise>
						</c:choose>
						
		    		</div>
		    	</div>
			</li>
		</c:forEach>
	</ul>	
</c:if>	

<c:if test="${component.isPromotion}">
	<div class="title">
		Promotion Item
		<div class="maxpagin"></div>	
	</div>
	<div class="banner_btn"> 
   		<span class="arrow-left"></span> 
		<span class="arrow-right"></span>
    </div>
    <div class="slide-wrap">
	    <div class="slide-item both">
	    	<c:forEach items="${productData}" var="product">
	     		<c:url value="${product.url}" var="productUrl"/>
		    	<div class="item">	    		
		    		<div class="img">
		    			<a href="${productUrl}"><product:productPrimaryImage product="${product}" format="product"/></a>
		    		</div>
		    		<div class="maxtext">
		    			<p>${fn:escapeXml(product.name)} (${product.netWeight}kg ${product.packageType})</p>
		    			<c:choose>
		    			
							<c:when test="${not empty product.promotionPrice}">							
			  					<span class="price">${product.promotionPrice.formattedValue}</span>
		    			<span class="old-price">${product.price.formattedValue}</span>
							</c:when>
						<c:otherwise>
							<span class="price">${product.price.formattedValue}</span>
						</c:otherwise>
						</c:choose>
			    	</div>
		    	</div>
	    	</c:forEach>
	    </div>
	</div>
</c:if>	    
