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
<c:url value="/reports/employeeSalesAnalysis"
	var="action" />

<body class="index-body">

	<!-- top -->
	<jsp:include page="reportMenu.jsp" >
    	<jsp:param name="section"  value="${isDocMenu}" />
    </jsp:include>
	<!-- content -->
	<div class="index-container">
		
		
			<form:form method="post" action="${action}" commandName="employeeSalesAnalysisForm">
			<div class="top-select">
				<table border="0" width="100%" cellspacing="0">
					<tbody>
						<tr>
						<td><span>Select Year</span> 
												
							<formElement:customSelectBox
									 idKey="year" labelKey="" path="year" mandatory="true"
									skipBlank="true" skipBlankMessageKey="" items="${selectYears}"
									itemValue="code" itemLabel="name" selectedValue="${employeeSalesAnalysisForm.year}" />
								</td>
								<input id="select_year" type="hidden" name="field＿name" value="${employeeSalesAnalysisForm.year}">
							
							</td>
							<td>
								<div>
									<span>Employee Name</span>
									<input name="employeeName" size="12" type="text" value="${employeeSalesAnalysisForm.employeeName}">
								</div>
							</td>
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
							<td>${detail.empName}</td>
							<td><fmt:formatNumber type="number" value="${detail.janAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.febAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.marAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.aprAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.mayAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.junAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.julAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.augAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.sepAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.octAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.novAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.decAmount}" pattern="#.00" /></td>
							<td><fmt:formatNumber type="number" value="${detail.subTotal}" pattern="#.00" /></td>		

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
	<!-- foot -->
</body>
</html>
