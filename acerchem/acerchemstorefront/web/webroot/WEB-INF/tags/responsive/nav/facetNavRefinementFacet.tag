<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<!--shaun:hybris originalDirectory  -->
<c:if test="${not empty facetData.values}">
<ycommerce:testId code="facetNav_title_${facetData.name}">

	<div class="gen-content gal-centent">
	<div class="g-cont">
	<div class="g-cartable">
		<div class="g-cell gall-left" >
		
			
			<dl>
				<dt><h5><spring:theme code="search.nav.facetTitle" arguments="${facetData.name}"/></h5></dt>
				
						
						<div class=" js-facet-values js-facet-form">
													<!--Not working  -->
													<c:if test="${not empty facetData.topValues}">
														<ul class="facet__list js-facet-list js-facet-top-values">
															<c:forEach items="${facetData.topValues}" var="facetValue">
																<li>
																	<c:if test="${facetData.multiSelect}">
																		<form action="#" method="get">
																			<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
																			<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
																			<label>
																				<input class="facet__list__checkbox" type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} class="facet-checkbox" />
																				<span class="facet__list__label">
																					<!-- <span class="facet__list__mark"></span> -->
																					<span class="facet__list__text">
																						${fn:escapeXml(facetValue.name)}
																						<ycommerce:testId code="facetNav_count">
																							<span class="facet__value__count"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
																						</ycommerce:testId>
																					</span>
																				</span>
																			</label>
																		</form>
																	</c:if>
																	
																	<c:if test="${not facetData.multiSelect}">
																		<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
																		<span class="facet__text">
																			<a href="${facetValueQueryUrl}&amp;text=${fn:escapeXml(searchPageData.freeTextSearch)}">${fn:escapeXml(facetValue.name)}</a>&nbsp;
																			<ycommerce:testId code="facetNav_count">
																				<span class="facet__value__count"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
																			</ycommerce:testId>
																		</span>
																	</c:if>
																</li>
															</c:forEach>
														</ul>
													</c:if>
							<!-- Specific rules -->
							<ul class=" js-facet-list <c:if test="${not empty facetData.topValues}">facet__list--hidden js-facet-list-hidden</c:if>">
								<c:forEach items="${facetData.values}" var="facetValue">
								<dd>
										<c:if test="${facetData.multiSelect}">
											<ycommerce:testId code="facetNav_selectForm">
											<form action="#" method="get">
												<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
												<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
												<label>
													<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''}  class=" js-facet-checkbox sr-only" />
													<span class="">
														<!-- <span class="facet__list__mark"></span> -->
														<span class="">
															${fn:escapeXml(facetValue.name)}&nbsp;
															<ycommerce:testId code="facetNav_count">
																<span class=""><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
															</ycommerce:testId>
														</span>
													</span>
												</label>
											</form>
											</ycommerce:testId>
										</c:if>
										<c:if test="${not facetData.multiSelect}">
											<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
											<span class="">
												<a href="${facetValueQueryUrl}">${fn:escapeXml(facetValue.name)}</a>
												<ycommerce:testId code="facetNav_count">
													<span class=""><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
												</ycommerce:testId>
											</span>
										</c:if>
									</dd>
									
								</c:forEach>
							</ul>
						</div>
						
						
						
					
					
						
				
			</dl>
		</div>

	</div>
	</div>
	</div>
	
</ycommerce:testId>
</c:if>