<%@ page trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<spring:htmlEscape defaultHtmlEscape="true" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="UTF-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title></title>
	
<link rel="stylesheet" type="text/css"
	href="${commonResourcePath}/acerchem/images/style.css">

</head>	
<template:javaScript/>
<c:url value="/reports/productPriceAnalysis"
	var="productPriceAnalysisUrl" />
<c:url value="/reports/productSalesRecord"
	var="productSalesRecordUrl" />
	
<c:url value="/reports/orderDetails"
	var="orderDetailsUrl" />
<c:url value="/reports/monthlySalesAnalysis"
	var="monthlySalesAnalysisUrl" />			
<c:url value="/reports/employeeSalesAnalysis"
	var="employeeSalesAnalysisUrl" />
	
<c:url value="/reports/customerSalesAnalysis"
	var="customerSalesAnalysisUrl" />
<c:url value="/reports/customerBillAnalysis"
	var="customerBillAnalysisUrl" />
	
<c:url value="/reports/vendorInventory/temp"
	var="vendorInventoryUrl" />
<c:url value="/reports/vendorOrderProduct/temp"
	var="vendorOrderProductUrl" />
<c:url value="/reports/docAdd"
	var="docAddUrl" />	
<c:url value="/reports/docList"
	var="docDelUrl" />	
<c:url value="/logout" var="homeUrl" />	


<body class="index-body">
	<header>
		<div class="logo">
			<a href="/acerchemstorefront/acerchem/en/">
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
					<c:if test="${param.section eq 'yes'}">
					<li><a href="">推文管理</a>
					<div class="col-list" style="display: none">
						<a href="${docAddUrl}">推文增加</a>
						 <a href="${docDelUrl}">推文删除</a>

					</div></li>
					</c:if>
					
					
			</ul>
		</nav>
		<div class="exit">
			<a href="${homeUrl}">
			   <img src="${commonResourcePath}/acerchem/images/arr.png" alt="Return">
			 </a>
	          
		</div>
		
	</header>
	

<script src="${commonResourcePath}/acerchem/jquery-1.9.1.min.js"></script>

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