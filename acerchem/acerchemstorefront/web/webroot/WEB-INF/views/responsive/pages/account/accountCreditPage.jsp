<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:url value="/my-account/update-profile" var="updateProfileUrl"/>
<spring:url value="/my-account/update-password" var="updatePasswordUrl"/>
<spring:url value="/my-account/update-email" var="updateEmailUrl"/>
<spring:url value="/my-account/address-book" var="addressBookUrl"/>
<spring:url value="/my-account/payment-details" var="paymentDetailsUrl"/>
<spring:url value="/my-account/orders" var="ordersUrl"/>
<spring:url value="/my-account/credit" var="creditUrl"/>

<template:page pageTitle="${pageTitle}">
	<div class="member-content">
		<user:personalInfo/>
		<div class="sign-content g-right">
            <!-- 信用账户信息展示 -->
            <form:form action="" method="post" commandName="customerCreditAccountData">
            
            	<label>
					<span class='label-title'>账期时间</span>	
					<input type="text" name='billingInterval' value="${customerCreditAccountData.billingInterval}">
				</label>
		
            	<label>
					<span class='label-title'>当前可用额度</span>	
					<input type="text" name='creaditRemainedAmount' value="${customerCreditAccountData.creaditRemainedAmount}">
				</label>
		
            	<label>
					<span class='label-title'>总额度</span>	
					<input type="text" name='creditTotalAmount' value="${customerCreditAccountData.creditTotalAmount}">
				</label>
		
            	<label>
					<span class='label-title'>账户状态</span>	
					<input type="text" name='status' value="${customerCreditAccountData.status}">
				</label>
		
            	<%-- <label>
					<span class='label-title'>流水记录</span>	
					<input type="text" name='transactions' value="${customerCreditAccountData.transactions}">
				</label>
				
				<table>
					<tr>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Order Number</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Order Status</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Date Placed</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Total</th>
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
							</ycommerce:testId>
						</tr>
					</c:forEach>
				</table> --%>
					
					
			</form:form>
		</div>
        <user:PromotionItem/>
	</div>
</template:page>
