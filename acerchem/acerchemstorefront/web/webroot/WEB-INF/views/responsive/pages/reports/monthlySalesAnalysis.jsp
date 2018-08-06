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
<c:url value="/reports/monthlySalesAnalysis"
	var="action" />
<body class="index-body">
	<!-- top -->
	<jsp:include page="reportMenu.jsp" />
	<!-- content -->
	<div class="index-container">


		<form:form method="post" commandName="monthlySalesAnalysisForm"
			action="${action}">
			<div class="top-select">
				<table border="0" width="100%" cellspacing="0">
					<tbody>
						<tr>
							<td><span>Year</span> <input name="year" size="6"
								type="text" value="${year}" placeholder="2018" /></td>
							<td><span>Customer Area</span> <formElement:customSelectBox
									idKey="area" labelKey="" path="area" mandatory="true"
									skipBlank="false" skipBlankMessageKey="All" items="${areas}"
									itemValue="isocode" itemLabel="name" selectedValue="${isocode}" />
							</td>

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
				<div class="left">订单销售分析/区域月度销售分析</div>

			</div>
			<c:if test="${empty salesList}">
	No Monthly Sales Analysis Found
</c:if>
			<c:if test="${not empty salesList}">
				<div class="fixed-table-container">
					<table id="table_page">
						<!-- 
      <tr>
          <th>Country</th>
          <th>January</th>
          <th>February</th>
          <th>March</th>
          <th>April</th>
          <th>May</th>
          <th>June</th>
          <th>July</th>
          <th>August</th>
          <th>September</th>
          <th>October</th>
          <th>November</th>
          <th>December</th>
      </tr>
       -->
						<c:forEach items="${salesList}" var="detail">
							<tr>
								<td>${detail.country}</td>
								<td><fmt:formatNumber type="number"
										value="${detail.januaryAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.februaryAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.marchAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.aprllAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.mayAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.juneAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.julyAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.augustAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.septemberAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.octoberAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.novemberAmount}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.decemberAmount}" pattern="#.00" /></td>

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
	<script src="${commonResourcePath}/acerchem/monthlySalesAnalysis.js"></script>

	
</body>
</html>