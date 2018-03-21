<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>



<c:forEach items="${pageData.facets}" var="facet">
	<c:choose>
		<c:when test="${facet.code eq 'availableInStores'}">
				<!--shaun: hybris  Top left corner -->
			<%-- <nav:facetNavRefinementStoresFacet facetData="${facet}" userLocation="${userLocation}"/>  --%>
		</c:when>
		<c:otherwise>
			<nav:facetNavRefinementFacet facetData="${facet}"/> 
		</c:otherwise>
	</c:choose>
</c:forEach>

<!-- shaun:acerchem falseData -->
 <div class="gen-content gal-centent">
	<div class="g-cont">
	<div class="g-cartable">
			<div class=" gall-left"  style=" margin-left: 10%;">  <!--  style="width:198px; height:auto; overflow:hidden; padding:1px;" -->
				<h4>Categories</h4>
				<dl>
					<dt><h5>Shop by Price</h5></dt>
					<dd>$0.00 - $99.99 (<span>24</span>)</dd>
					<dd>$100.00 And Above (<span>1</span>)</dd>
				</dl>
				<dl>
					<dt><h5>Shop by Stores</h5></dt>
					<dd>Sichuan Tongsheng Amino</dd>
					<dd>Shanghai KangXin Chemical</dd>
					<dd>Wuhan Grand Hoyo</dd>
					<dd>Zhangjiagang Shuguang ...</dd>
				</dl>
				<dl>
					<dt><h5>Applications</h5></dt>
					<dd>Anti-Oxidants (<span>3</span>)</dd>
					<dd>B-Complex (<span>3</span>)</dd>
					<dd>Cognitive (<span>1</span>)</dd>
					<dd>Energy Support (<span>4</span>)</dd>
					<dd>Food Additives (<span>6</span>)</dd>
					<dd>Immune Enhancement (<span>1</span>)</dd>
					<dd>Joint Health (<span>3</span>)</dd>
					<dd>Muscle Building (<span>3</span>)</dd>
					<dd>Pre-Work Out (<span>3</span>)</dd>
				</dl>
			</div>
		</div>
	</div>
</div> 


