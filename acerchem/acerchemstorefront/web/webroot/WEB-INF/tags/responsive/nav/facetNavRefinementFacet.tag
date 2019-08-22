<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="userWarehouse"></c:set>
<c:if test="${not empty user.warehouseList}">
	<c:forEach items="${user.warehouseList}" var="warehouse">
		<c:set value="${userWarehouse}${';'}${warehouse.code}" var="userWarehouse"></c:set>
	</c:forEach>
</c:if>

<c:if test="${not empty facetData.values}">
<ycommerce:testId code="facetNav_title_${facetData.name}">
	<div class="facet js-facet">
		<div class="facet__name js-facet-name">
			<span class="glyphicon facet__arrow"></span>
			<spring:theme code="search.nav.facetTitle" arguments="${facetData.name}"/>
		</div>

		<div class="facet__values js-facet-values js-facet-form" style="display: none;">

			<c:if test="${not empty facetData.topValues}">
				<ul class="facet__list js-facet-list js-facet-top-values">
					<c:forEach items="${facetData.topValues}" var="facetValue">
						<c:if test="${facetData.multiSelect and fn:containsIgnoreCase(userWarehouse, facetValue.code)}">
							<li>
								<form action="#" method="get">
									<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
									<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
									<label>
										<input class="facet__list__checkbox" type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} class="facet-checkbox" />
										<span class="facet__list__label">
											<span class="facet__list__mark"></span>
											<span class="facet__list__text">
												${fn:escapeXml(facetValue.name)}
												<ycommerce:testId code="facetNav_count">
													<span class="facet__value__count"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
												</ycommerce:testId>
											</span>
										</span>
									</label>
								</form>
							</li>
						</c:if>
						<c:if test="${not facetData.multiSelect}">
							<li>
								<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
								<span class="facet__text">
									<a href="${facetValueQueryUrl}&amp;text=${fn:escapeXml(searchPageData.freeTextSearch)}">${fn:escapeXml(facetValue.name)}</a>&nbsp;
									<ycommerce:testId code="facetNav_count">
										<span class="facet__value__count"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
									</ycommerce:testId>
								</span>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
			<ul class="facet__list js-facet-list <c:if test="${not empty facetData.topValues}">facet__list--hidden js-facet-list-hidden</c:if>" style="max-height: 500px;overflow-y: scroll;">
				<c:forEach items="${facetData.values}" var="facetValue">
					<c:if test="${facetData.multiSelect and (facetData.code!='warehouseCode' or (facetData.code=='warehouseCode' and fn:containsIgnoreCase(userWarehouse, facetValue.code)))}">
						<li>
							<ycommerce:testId code="facetNav_selectForm">
							<form action="#" method="get">
								<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
								<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
								<label <c:if test="${facetData.name eq 'Factory'}">style="text-transform: none"</c:if>>
									<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''}  class="facet__list__checkbox js-facet-checkbox sr-only" />
									<span class="facet__list__label">
										<span class="facet__list__mark"></span>
										<span class="facet__list__text">
											${fn:escapeXml(facetValue.name)}&nbsp;
											<ycommerce:testId code="facetNav_count">
												<span class="facet__value__count"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
											</ycommerce:testId>
										</span>
									</span>
								</label>
							</form>
							</ycommerce:testId>
						</li>
					</c:if>
					<c:if test="${not facetData.multiSelect}">
						<li>
							<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
							<span class="facet__list__label">
											<span class="facet__list__mark"></span>
								<span class="facet__text">
									<a href="${facetValueQueryUrl}">${fn:escapeXml(facetValue.name)}</a>
									<ycommerce:testId code="facetNav_count">
										<span class="facet__value__count"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
									</ycommerce:testId>
								</span>
							</span>
						</li>
					</c:if>
				</c:forEach>
			</ul>

			<c:if test="${not empty facetData.topValues}">
				<span class="facet__values__more js-more-facet-values">
					<a href="#" class="js-more-facet-values-link" ><spring:theme code="search.nav.facetShowMore_${facetData.code}" /></a>
				</span>
				<span class="facet__values__less js-less-facet-values">
					<a href="#" class="js-less-facet-values-link"><spring:theme code="search.nav.facetShowLess_${facetData.code}" /></a>
				</span>
			</c:if>
		</div>
	</div>
</ycommerce:testId>
</c:if>
