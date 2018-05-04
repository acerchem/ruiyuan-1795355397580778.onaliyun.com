<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<template:page pageTitle="${pageTitle}">
	<div class="member-content">
		<user:personalInfo/>
		<div class="sign-content g-right">
            <div class="title">Credit Account</div>
            <form:form action="" method="post" commandName="customerCreditAccountData">
            	<table>
            		<tr>
            			<td align="right" style="color: #999;width: 33%">Credit Total Amount</td>
            			<td align="left" style="color: #333;">${customerCreditAccountData.creditTotalAmount=="0E-8"?"0":customerCreditAccountData.creditTotalAmount}</td>
            			<td align="right" style="color: #999;">Status</td>
            			<td align="left" style="color: #333;">${customerCreditAccountData.status}</td>
            		</tr>
            		<tr>
            			<td align="right" style="color: #999;">Creadit Remained Amount</td>
            			<td align="left" style="color: #333;">${customerCreditAccountData.creaditRemainedAmount=="0E-8"?"0":customerCreditAccountData.creaditRemainedAmount}</td>
            			<td align="right" style="color: #999;">Payment Term</td>
            			<td align="left" style="color: #333;">${customerCreditAccountData.billingInterval}</td>
            		</tr>
            	</table>
		
            	<label>
					<span class='label-title'>Transactions</span>	
				</label>
				
				<table>
					<tr>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Order Code</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Product Quantity</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Invoice Amount</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Due Date</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Paid</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Payback Time</th>
						<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Is Payback</th>
						
						
					</tr>
					<tr><td style="padding:0px 0px;"></td></tr>
					<c:forEach items="${customerCreditAccountData.transactions}" var="trans">
						<tr class="responsive-table-item">
							<ycommerce:testId code="orderHistoryItem_orderDetails_link">
								<td style="padding:10px 10px;font-size:14px;">
									${trans.orderCode}
								</td>
								<td style="padding:10px 10px;font-size:14px;">
									${trans.productNumber}
								</td>
								<td style="padding:10px 10px;font-size:14px;">
									${trans.creaditUsedAmount}
								</td>
								<td style="padding:10px 10px;font-size:14px;">
									${trans.shouldPaybackTime}
								</td>
								<td style="padding:10px 10px;font-size:14px;">
									${trans.paybackAmount}
								</td>
								<td style="padding:10px 10px;font-size:14px;">
									${trans.paybackTime}
								</td>
								<td style="padding:10px 10px;font-size:14px;">
									${trans.isPayback?"yes":"no"}
								</td>
							</ycommerce:testId>
						</tr>
					</c:forEach>
				</table>
			</form:form>
		</div>
        <user:PromotionItem/>
	</div>
</template:page>
