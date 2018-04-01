<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="/search/" var="searchUrl" />
<spring:url value="/search/autocomplete/{/componentuid}" var="autocompleteUrl" htmlEscape="false">
     <spring:param name="componentuid"  value="${component.uid}"/>
</spring:url>

<c:choose>
		<c:when test="${component.displayProducts}">
	
	<div class="bansearch">
		<p>all stores one search best price</p>
		<form name="search_form_${fn:escapeXml(component.uid)}" method="get" action="${searchUrl}">
			<input type="text" id="js-site-search-input" name="text" class="lab-row" placeholder="Search"
								data-options='{"autocompleteUrl" : "${autocompleteUrl}","minCharactersBeforeRequest" : "${component.minCharactersBeforeRequest}","waitTimeBeforeRequest" : "${component.waitTimeBeforeRequest}","displayProductImages" : ${component.displayProductImages}}'>
			
			<button class="maxicon-search" type="submit" ></button>
		</form>
		<span>search all china wholesale stores . 45 million products free shipping</span>
	</div>
</c:when>
<c:otherwise>

<a class="maxicon-search" href=""></a>
			<div class="searchlist">
				<form action="" method="post" class="both" id="search">
					<input type="text" name="key" class="lab-row" placeholder="Search">
					<i class="btn-submit"></i>
				</form>
				<ul class="pdlist">
					<li><a href="product.html" class="img"><img
							src="images/maxsales-bg6.jpg" alt=""></a> <a href="product.html"
						class="text"> <span>TwinTiger Glutathione (5kg Bag) by
								Jincheng</span>
					</a></li>
			
					<li><a href="product.html" class="img"><img
							src="images/maxsales-bg6.jpg" alt=""></a> <a href="product.html"
						class="text"> <span>TwinTiger Glutathione (5kg Bag) by
								Jincheng</span>
					</a></li>
			
					<li><a href="product.html" class="img"><img
							src="images/maxsales-bg6.jpg" alt=""></a> <a href="product.html"
						class="text"> <span>TwinTiger Glutathione (5kg Bag) by
								Jincheng</span>
					</a></li>
			
				</ul>
			</div>

   
</c:otherwise>
</c:choose>