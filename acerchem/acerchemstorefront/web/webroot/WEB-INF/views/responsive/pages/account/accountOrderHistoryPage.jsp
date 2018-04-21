<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/my-account/order/" var="orderDetailsUrl" htmlEscape="false"/>
<c:set var="searchUrl" value="/my-account/orders?sort=${ycommerce:encodeUrl(searchPageData.pagination.sort)}"/>
<c:set var="themeMsgKey" value="text.account.orderHistory.page"/>
<c:url value="/my-account/confirm/" var="confirmOrder"/>

<c:if test="${empty searchPageData.results}">
	<spring:theme code="text.account.orderHistory.noOrders" />
</c:if>
<c:if test="${not empty searchPageData.results}">
	<div class="title">Order History</div>
	<div class="rigcont">
		<c:if test="${not empty searchPageData.sorts}">
			<form name="sortForm1" method="get" action="#" class="both" id="sortForm1">
				<label style="width: 160px;font-weight:normal;">
					<span>Order Sort</span>	
	                    <select id="sortOptions1" name="sort">
	                    	<option value="code" ${sort=='code'? 'selected="selected"' : ''}>Order Number</option>
	                    	<option value="date" ${sort=='date'? 'selected="selected"' : ''}>Date</option>
                            <option value="total" ${sort=='total'? 'selected="selected"' : ''}>Total</option>
                            <option value="status" ${sort=='status'? 'selected="selected"' : ''}>Status</option>
	                    </select>
	                    <c:catch var="errorException">
	                        <spring:eval expression="searchPageData.currentQuery.query"
	                                     var="dummyVar"/><%-- This will throw an exception is it is not supported --%>
	                        <input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
	                    </c:catch>
				</label>
				<label style="font-weight:normal;">
					<input type="text" name='key' class="lab-row" placeholder="Search Order Number"/>
				</label>				
    		</form>
    	</c:if>
    
		<table>
			<tr>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Order Number</th>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Order Status</th>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Date Placed</th>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Total</th>
				<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Confirm Order</th>
			</tr>
			<tr><td style="padding:0px 0px;"></td></tr>
			<c:forEach items="${searchPageData.results}" var="order">
				<tr class="responsive-table-item">
					<ycommerce:testId code="orderHistoryItem_orderDetails_link">
						<td class="responsive-table-cell" style="padding:10px 10px;font-size:14px;">
							<a href="${orderDetailsUrl}${ycommerce:encodeUrl(order.code)}" class="responsive-table-link">
								${fn:escapeXml(order.code)}
							</a>
						</td>
						<td class="status" style="padding:10px 10px;font-size:14px;">
							<spring:theme code="text.account.order.status.display.${order.statusDisplay}"/>
						</td>
						<td class="responsive-table-cell" style="padding:10px 10px;font-size:14px;">
							<fmt:formatDate value="${order.placed}" dateStyle="medium" timeStyle="short" type="both"/>
						</td>
						<td class="responsive-table-cell responsive-table-cell-bold" style="padding:10px 10px;font-size:14px;">
							${fn:escapeXml(order.total.formattedValue)}
						</td>
						<td class="responsive-table-cell responsive-table-cell-bold" style="padding:10px 10px;font-size:14px;">
							<a href="${confirmOrder}${ycommerce:encodeUrl(order.code)}?confirm=order" style="${order.customerConfirm?'display: none;':''}">Confirm</a>
						</td>
						
					</ycommerce:testId>
				</tr>
			</c:forEach>
		</table>
		<div class="m-pagination">
		 	<pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
		</div>
	</div>
</c:if>		