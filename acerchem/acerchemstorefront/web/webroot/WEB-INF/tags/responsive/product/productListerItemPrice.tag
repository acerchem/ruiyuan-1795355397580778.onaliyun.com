<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<ycommerce:testId code="searchPage_price_label_${product.code}">

		<%-- if product is multidimensional with different prices, show range, else, show unique price --%>
		<c:choose>
			<c:when test="${not empty product.promotionPrice}">
			  <span class="old-price">  <format:fromPrice priceData="${product.price}"/></span>
			   <span class="price"> <format:fromPrice priceData="${product.promotionPrice}"/></span>
			</c:when>
			<c:otherwise>
				<span class="price"> <format:fromPrice priceData="${product.price}"/></span>
			</c:otherwise>
		</c:choose>

</ycommerce:testId>
