<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>

<%-- <c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/> --%>


<!--shaun: acerchem -->
<!-- productDatails -->
<li class="aitem">
	<ycommerce:testId code="test_searchPage_wholeProduct">
	
			<!-- 图片 -->
			<div class="img">
				<a href="${productUrl}"  >  
					<product:productPrimaryImage product="${product}" format="thumbnail"/>
				</a>
			</div>
			
			<div class="maxtext">
	    		<p>${fn:escapeXml(product.name)}</p>

	    		<product:productListerItemPrice product="${product}"/>
	    		<!-- <br> -->					
					
				 <c:set var="product" value="${product}" scope="request"/>
				 <c:set var="addToCartText" value="${addToCartText}" scope="request"/>
				 <c:set var="addToCartUrl" value="${addToCartUrl}" scope="request"/> 
				 
				 <!-- addToCart -->
				 <div class="addtocart">
					<div id="actions-container-for-${fn:escapeXml(component.uid)}" class="row">
						<%-- <action:actions element="div" parentComponent="${component}"/> --%>
						<a href="${productUrl}">
           		  			<div class="g-cart " id="addToCartDiv1451563" >
				            	<i></i>
			                </div>
						</a>
				 	</div> 
				 </div>
				 <!-- end addToCart -->
					
	    	</div>
	    	
	</ycommerce:testId>
</li>