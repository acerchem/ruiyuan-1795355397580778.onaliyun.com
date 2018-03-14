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