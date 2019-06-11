<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true"
              type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="top" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showTopTotals" required="false" type="java.lang.Boolean" %>
<%@ attribute name="supportShowAll" required="true" type="java.lang.Boolean" %>
<%@ attribute name="supportShowPaged" required="true" type="java.lang.Boolean" %>
<%@ attribute name="additionalParams" required="false" type="java.util.HashMap" %>
<%@ attribute name="msgKey" required="false" %>
<%@ attribute name="showCurrentPageInfo" required="false" type="java.lang.Boolean" %>
<%@ attribute name="hideRefineButton" required="false" type="java.lang.Boolean" %>
<%@ attribute name="numberPagesShown" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<c:set var="showCurrPage" value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}"/>
<c:set var="hideRefBtn" value="${hideRefineButton ? true : false}"/>
<c:set var="showTotals" value="${empty showTopTotals ? true : showTopTotals}"/>

<c:if test="${searchPageData.pagination.totalNumberOfResults == 0 && top && showTotals}">
    <div class="paginationBar top clearfix">
        <ycommerce:testId code="searchResults_productsFound_label">
            <div class="totalResults">${searchPageData.pagination.totalNumberOfResults} Ticket</div>
        </ycommerce:testId>
    </div>
</c:if>

<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
    <div class="pagination-bar ${(top)?"top":"bottom"}">
        <div>
            <c:if test="${not empty searchPageData.sorts}">
                <div class="helper clearfix hidden-md hidden-lg"></div>
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-5 pagination-wrap">
                        <pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}"
                                                            numberPagesShown="${numberPagesShown}"
                                                            themeMsgKey="${themeMsgKey}"/>
                    </div>
                </div>
            </c:if>
        </div>
        <c:if test="${top && showTotals}">
            <div class="row">
                <div class="col-xs-12">
                    <div class="pagination-bar-results">
                        <ycommerce:testId code="searchResults_productsFound_label">
                            <c:choose>
                                <c:when test="${showCurrPage}">
                                    <c:choose>
                                        <c:when test="${searchPageData.pagination.totalNumberOfResults == 1}">
                                            ${searchPageData.pagination.totalNumberOfResults} Ticket
                                        </c:when>
                                        <c:when test="${searchPageData.pagination.numberOfPages <= 1}">
                                        	${searchPageData.pagination.totalNumberOfResults} Tickets
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="currentPageItems" value="${(searchPageData.pagination.currentPage + 1) * searchPageData.pagination.pageSize}"/>
                                            <c:set var="upTo" value="${(currentPageItems > searchPageData.pagination.totalNumberOfResults ? searchPageData.pagination.totalNumberOfResults : currentPageItems)}"/>
                                            <c:set var="currentPage" value="${searchPageData.pagination.currentPage * searchPageData.pagination.pageSize + 1} - ${upTo}"/>
                                        	${currentPage} of  ${searchPageData.pagination.totalNumberOfResults} Tickets
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    ${searchPageData.pagination.totalNumberOfResults} Tickets
                                </c:otherwise>
                            </c:choose>
                        </ycommerce:testId>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</c:if>