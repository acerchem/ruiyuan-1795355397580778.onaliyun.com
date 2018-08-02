<%@ page trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>
<spring:htmlEscape defaultHtmlEscape="true" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="UTF-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title></title>

<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap.min.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap-table.css">
<link rel="stylesheet" type="text/css"
	href="${commonResourcePath}/acerchem/images/style.css">

</head>

<template:javaScript />
<c:url value="/reports/productPriceAnalysis"
	var="productPriceAnalysisUrl" />
<c:url value="/reports/productSalesRecord" var="productSalesRecordUrl" />

<c:url value="/reports/orderDetails" var="orderDetailsUrl" />
<c:url value="/reports/monthlySalesAnalysis"
	var="monthlySalesAnalysisUrl" />
<c:url value="/reports/employeeSalesAnalysis"
	var="employeeSalesAnalysisUrl" />

<c:url value="/reports/customerSalesAnalysis"
	var="customerSalesAnalysisUrl" />
<c:url value="/reports/customerBillAnalysis"
	var="customerBillAnalysisUrl" />

<c:url value="/reports/vendorInventory/temp" var="vendorInventoryUrl" />
<c:url value="/reports/vendorOrderProduct/temp"
	var="vendorOrderProductUrl" />
<c:url value="/logout" var="homeUrl" />	

<body class="index-body">
<header>
		<div class="logo">
			<a href="${homeUrl}">
			<img src="${commonResourcePath}/acerchem/images/logo.png" alt="logo">
			</a>
		</div>
		<nav class="head-nav">
			<ul>
				<li><a href="">商品销售报表</a>
					<div class="col-list" style="display: none">
						<a href="${productPriceAnalysisUrl}">商品价格趋势分析</a> 
						<a href="${productSalesRecordUrl}">商品销售记录</a>
					</div>
				</li>
				<li><a href="">订单销售分析</a>
					<div class="col-list" style="display: none">
						<a href="${orderDetailsUrl}">订单明细表</a> 
						<a href="${monthlySalesAnalysisUrl}">区域月度销售分析</a> 
						<a href="${employeeSalesAnalysisUrl}">各业务员完成情况</a>

					</div></li>
				<li><a href="">用户分析报表</a>
					<div class="col-list" style="display: none">
						<a href="${customerSalesAnalysisUrl}">用户购买情况分析</a> 
						<a href="${customerBillAnalysisUrl}">账龄分析报表</a>

					</div></li>
				<li><a href="">供应商寄售分析</a>
					<div class="col-list" style="display: none">
						<a href="${vendorInventoryUrl}">商品库存</a>
						 <a href="${vendorOrderProductUrl}">商品订单分析</a>

					</div></li>
			</ul>
		</nav>
	</header>
	
	<div class="index-container">
		
		
			<form:form method="post" action="${action}">
			<div class="top-select">
				<table border="0" width="100%" cellspacing="0">
					<tbody>
						<tr>
						<td><span>Input year:</span> <input
							name="year" size="6" type="text" value="${curYear}"
							placeholder="2018" /></td>


					<td>
								<div class="btn" >
									<button type="submit">Show Report</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<%-- <nav:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/reports/orderDetails"  numberPagesShown="100"/>
 --%>
		</form:form>
		<div class="content">
			<div class="c-title">
				<div class="left">订单销售分析/各业务员完成情况</div>

			</div>
		<c:if test="${empty salesList}">
	No Employee Sales Analysis Found
</c:if>
		<c:if test="${not empty salesList}">
			<div class="fixed-table-container">
				<table id="table_page">
					<!-- 
      <tr>
          <th>Month</th>
          <th>User</th>
          <th>Amount</th>
        
      </tr>
       -->
					<c:forEach items="${salesList}" var="detail">
						<tr>
							<td>${detail.month}</td>

							<td>${detail.employee}</td>
							<td><fmt:formatNumber type="number" value="${detail.amount}"
									pattern="#.00" /></td>

						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
</div>
	</div>

	<script src="${commonResourcePath}/acerchem/jquery-1.9.1.min.js"></script>
	<script src="${commonResourcePath}/acerchem/jquery.base64.js"></script>
	<script src="${commonResourcePath}/acerchem/bootstrap.min.js"></script>
	<script src="${commonResourcePath}/acerchem/bootstrap-table.min.js"></script>
	<script src="${commonResourcePath}/acerchem/tableExport.min.js"></script>
	<script
		src="${commonResourcePath}/acerchem/bootstrap-table-export.min.js"></script>
	<script src="${commonResourcePath}/acerchem/employeeSalesAnalysis.js"></script>
	<script>
		(function() {
			$('.head-nav ul li').hover(function() {
				$(this).find('.col-list').slideDown();
			}, function() {
				$(this).find('.col-list').slideUp();
			})
		})()
	</script>
</body>
</html>
