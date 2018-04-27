<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="${url}" var="addToCartUrl"/>

<product:addToCartTitle/>

<form:form id="addToCartForm" method="post"  class="add_to_cart_form" action="${addToCartUrl}">
	
	<input type="hidden" maxlength="3" size="1" id="qty" name="qty"  value="1">
    <input type="hidden" name="productCodePost" value="${fn:escapeXml(product.code)}"/>
    
    <input type="hidden"  id="tag" name="tag"  value="0">
    
    <c:forEach items="${countrys}" var="data"  varStatus="id"  >
		<c:if test="${id.index==0}">
			    <input type="hidden" name="storeId"  id="storeId"  value="${data.storeId}"/>
		</c:if>	
    </c:forEach>
                        
    
    
    
<div class="btn-set">
	   <!--  <button class="btn btn-submit">Check Out</button> -->
<c:if test="${empty showAddToCart ? true : showAddToCart}">
	<c:set var="buttonType">button</c:set>
	<c:if test="${product.stock.stockLevelStatus.code ne 'outOfStock' }">
		<c:set var="buttonType">submit</c:set>
	</c:if>
	<c:choose>
	
		<c:when test="${fn:contains(buttonType, 'button')}">
			<button type="${buttonType}" class="btn btn-cart" disabled="disabled">
				Add to Cart
			</button>
		</c:when>
		<c:otherwise>
			<ycommerce:testId code="addToCartButton">
				<button id="addToCartButton" type="${buttonType}" class="btn btn-cart" >
					Add to Cart
				</button>
			</ycommerce:testId>
		</c:otherwise>
	
	</c:choose>
</c:if>
	</div>
</form:form>

