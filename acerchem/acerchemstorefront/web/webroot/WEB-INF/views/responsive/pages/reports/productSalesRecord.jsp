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
<link rel="stylesheet" href="${commonResourcePath}/acerchem/easyui.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap.min.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap-table.css">
	
	<link rel="stylesheet" type="text/css"
	href="${commonResourcePath}/acerchem/images/style.css">

</head>

<template:javaScript />

<body class="index-body">
	
	<!-- top -->
    <jsp:include page="reportMenu.jsp"/>
	<!-- content -->
	<div class="index-container">

		<form:form method="post" commandName="productSalesForm"
			action="${action}">
			<div class="top-select">
				<table border="0" width="100%" cellspacing="0">
					<tbody>
						<tr>
							<td><span>Month</span> <input id="birth_year"
								class="easyui-datebox" name="month" type="text" value="${month}"
								style="width: 100px; padding-left: 10px" /></td>
								
							<td><span>Category</span> <formElement:customSelectBox
									idKey="CategoryCode" labelKey="" path="categoryCode"
									mandatory="true" skipBlank="false" skipBlankMessageKey="All"
									items="${categories}" itemValue="code" itemLabel="name"
									selectedValue="${code}" /></td>
							<td><span>Customer Area</span> <formElement:customSelectBox
									idKey="area" labelKey="" path="area" mandatory="true"
									skipBlank="false" skipBlankMessageKey="All" items="${areas}"
									itemValue="isocode" itemLabel="name" selectedValue="${area}" />
							</td>
							<td><span>Country</span> <formElement:customSelectBox
									idKey="countryIso" labelKey="" path="countryCode"
									mandatory="true" skipBlank="false" skipBlankMessageKey="All"
									items="${countries}" itemValue="isocode" itemLabel="name"
									selectedValue="${countryIso}" /></td>
 
							<td>
								<div class="btn">
									<button type="submit">Show Report</button>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

		</form:form>
		<div class="content">
			<div class="c-title">
				<div class="left">商品销售报表/商品销售记录</div>

			</div>
			<c:if test="${empty list}">
	No product Sales Found
</c:if>
			<c:if test="${not empty list}">
				<div class="fixed-table-container">
					<table id="table_page">
						<!-- 
      <tr>
          <th>Product Code</th>
          <th>Product Name</th>
          <th>Customer</th>
          <th>Quantity</th>
          
      </tr>
       -->
						<c:forEach items="${list}" var="orderDetail">
							<tr>
								<td>${orderDetail.productCode}</td>
								<td>${orderDetail.productName}</td>

								<td>${orderDetail.customerName}</td>
								<td>${orderDetail.salesQuantity}</td>

							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
		</div>
	</div>
	<script src="${commonResourcePath}/acerchem/jquery-1.9.1.min.js"></script>
	<script src="${commonResourcePath}/acerchem/jquery.base64.js"></script>
	<script src="${commonResourcePath}/acerchem/jquery.easyui.min.js"></script>
	<script src="${commonResourcePath}/acerchem/yearmonth-easyui.js"></script>

	<script src="${commonResourcePath}/acerchem/bootstrap.min.js"></script>
	<script src="${commonResourcePath}/acerchem/bootstrap-table.min.js"></script>
	<script src="${commonResourcePath}/acerchem/tableExport.min.js"></script>
	<script
		src="${commonResourcePath}/acerchem/bootstrap-table-export.min.js"></script>
	<script src="${commonResourcePath}/acerchem/productSalesRecord.js"></script>

</body>
</html>
