<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<spring:htmlEscape defaultHtmlEscape="true"/>
<c:set var="searchUrl" value="/my-account/my-quotes?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>

<div class="title">
    <spring:theme code="text.account.quote.myquotes"/>
</div>

<c:if test="${empty searchPageData.results}">
    <div class="row">
        <div class="col-md-6 col-md-push-3">
            <div class="account-section-content	content-empty">
                <ycommerce:testId code="quoteHistory_noQuotes_label">
                    <spring:theme code="text.account.quotes.noQuotes"/>
                </ycommerce:testId>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty searchPageData.results}">
    <div class="rigcont">
		<c:if test="${not empty searchPageData.sorts}">
			<form name="sortForm1" method="get" action="#" class="both" id="sortForm1">
				<label style="width: 160px;font-weight:normal;">
					<span>Order Sort</span>
					<select id="sortOptions1" name="sort">
						<option value="byCode" ${searchPageData.pagination.sort=='byCode'? 'selected="selected"' : ''}>Order Number</option>
						<option value="byDate" ${searchPageData.pagination.sort=='byDate'? 'selected="selected"' : ''}>Date</option>
						<option value="byName" ${searchPageData.pagination.sort=='byName'? 'selected="selected"' : ''}>Name</option>
						<option value="byState" ${searchPageData.pagination.sort=='byState'? 'selected="selected"' : ''}>Status</option>
					</select>
					<c:catch var="errorException">
						<spring:eval expression="searchPageData.currentQuery.query"
									 var="dummyVar"/><%-- This will throw an exception is it is not supported --%>
						<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
					</c:catch>
				</label>
			</form>
		</c:if>

		<table>
			<tr>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;" id="header1"><spring:theme code='text.account.quote.name'/></th>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;" id="header2"><spring:theme code='text.account.quote.code'/></th>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;" id="header3"><spring:theme code='text.account.quote.status'/></th>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;" id="header4"><spring:theme code='text.account.quote.date.updated'/></th>
			</tr>
			<tr><td style="padding:0px 0px;"></td></tr>
			<c:forEach items="${searchPageData.results}" var="quote">
			<tr class="responsive-table-item">
				<ycommerce:testId code="orderHistoryItem_orderDetails_link">
					<td headers="header1" class="hidden-sm hidden-md hidden-lg">
						<spring:theme code="text.account.quote.name" />
					</td>
					<td class="responsive-table-cell">
						<spring:url value="/my-account/my-quotes/{/quotecode}/" var="quoteDetailLink" htmlEscape="false">
							<spring:param name="quotecode"  value="${quote.code}"/>
						</spring:url>
						<a href="${quoteDetailLink}" class="responsive-table-link">${fn:escapeXml(quote.name)}</a>
					</td>
					<td class="hidden-sm hidden-md hidden-lg">
						<spring:theme code="text.account.quote.code"/>
					</td>
					<td class="responsive-table-cell">
							${fn:escapeXml(quote.code)}
					</td>
					<td class="hidden-sm hidden-md hidden-lg">
						<spring:theme code="text.account.quote.status"/>
					</td>
					<td class="status">
						<spring:theme code="text.account.quote.status.display.${quote.state}"/>
					</td>
					<td class="hidden-sm hidden-md hidden-lg">
						<spring:theme code="text.account.quote.date.updated"/>
					</td>
					<td class="responsive-table-cell">
						<fmt:formatDate value="${quote.updatedTime}" dateStyle="medium" timeStyle="short" type="both"/>
					</td>
				</ycommerce:testId>
			</tr>
			</c:forEach>
		</table>

		<div class="account-orderhistory-pagination">
			<nav:paginationwithoutsort top="false" msgKey="text.account.quote.page" showCurrentPageInfo="true"
							hideRefineButton="true" supportShowPaged="${isShowPageAllowed}"
							supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}"
							searchUrl="${searchUrl}" numberPagesShown="${numberPagesShown}"/>
		</div>
	</div>
</c:if>
