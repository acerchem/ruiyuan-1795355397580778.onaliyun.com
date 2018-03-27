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
			
			<div class="gal-centent">
				<div class=" gall-left" >
					<h4>Categories</h4>
				</div>
			</div>
				
				
		</c:when>
		<c:otherwise>
			<nav:facetNavRefinementFacet facetData="${facet}"/> 
		</c:otherwise>
	</c:choose>
</c:forEach>




