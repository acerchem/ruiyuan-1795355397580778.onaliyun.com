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
	href="${commonResourcePath}/acerchem/jquery-calendar.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap.min.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap-table.css">
<link rel="stylesheet" type="text/css"
	href="${commonResourcePath}/acerchem/images/style.css">

</head>
<template:javaScript />
<c:url value="/reports/customerSalesAnalysis"
	var="action" />
<body class="index-body">

	<!-- top -->
	<jsp:include page="reportMenu.jsp" >
    	<jsp:param name="section"  value="${isDocMenu}" />
    </jsp:include>
	<!-- content -->
	<div class="index-container">
	
		
		<form:form method="post" commandName="customerSalesAnalysisForm"
			action="${action}">
			<div class="top-select">
				<table border="0" width="100%" cellspacing="0">
					<tbody>
						<tr>
<td>
								<div>
									Start Date:<span> <input type="text" class="calendar"
										maxlength="12" onfocus="$(this).calendar()" name="startDate"
										value="${customerSalesAnalysisForm.startDate}" size="12" readonly />
									</span>
								</div>
							</td>

							<td>
								<div>
									End Date:<span> <input type="text" class="calendar"
										maxlength="12" onfocus="$(this).calendar()" name="endDate"
										value="${customerSalesAnalysisForm.endDate}" size="12" readonly />
									</span>
								</div>
							</td>
						<td><span style="margin-left: 5px;">Customer Area</span> <formElement:customSelectBox
								idKey="area" labelKey="" path="area" mandatory="true"
								skipBlank="false" skipBlankMessageKey="All" items="${areas}"
								itemValue="isocode" itemLabel="name" selectedValue="${customerSalesAnalysisForm.area}" />
						</td>

						<td><span>Customer Name</span> <input
							name="customerName" size="12" type="text" value="${customerSalesAnalysisForm.customerName}">
						</td>
						<td><span> Order Amount ></span>
							<input name="amount" size="12" type="text" value="${customerSalesAnalysisForm.amount}">
						</td>
					<td>
					<span></span>
								<div class="btn" >
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
				<div class="left">用户分析报表/用户购买情况分析</div>

			</div>
		<c:if test="${empty list}">
	No Customer Sales Analysis Found
</c:if>
		<c:if test="${not empty list}">
			<div class="fixed-table-container">

				<table id="table_page">
					<!-- 
      <tr>
          <th>Area</th>
		  <th>Country</th>
          <th>Customer</th>
          <th>OrderAmount</th>
          
      </tr>
       -->
					<c:forEach items="${list}" var="orderDetail">
						<tr>
							<td>${orderDetail.area}</td>
							<td>${orderDetail.country}</td>
							<td>${orderDetail.customerName}</td>
							<td><fmt:formatNumber type="number"
									value="${orderDetail.salesAmount}" pattern="#.00" /></td>

						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>
		</div>
	</div>
	<script src="${commonResourcePath}/acerchem/jquery-1.9.1.min.js"></script>
	<script src="${commonResourcePath}/acerchem/jquery.base64.js"></script>
	<script src="${commonResourcePath}/acerchem/jquery-calendar.js"></script>
	<script src="${commonResourcePath}/acerchem/bootstrap.min.js"></script>
	<script src="${commonResourcePath}/acerchem/bootstrap-table.min.js"></script>
	<script src="${commonResourcePath}/acerchem/tableExport.min.js"></script>
	<script
		src="${commonResourcePath}/acerchem/bootstrap-table-export.min.js"></script>
	<script src="${commonResourcePath}/acerchem/customerSalesAnalysis.js"></script>
	
</body>
</html>