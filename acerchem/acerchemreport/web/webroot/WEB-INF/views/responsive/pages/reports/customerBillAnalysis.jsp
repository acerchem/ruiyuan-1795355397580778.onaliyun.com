<%@ page trimDirectiveWhitespaces="true"%>
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
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/jquery-calendar.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap.min.css">

<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap-table.css">

<template:javaScript />
<div class="container">
	<div class="title">
		<h2>Customer Bill Analysis Report</h2>
	</div>
	<hr />
	<form:form method="post" action="${action}">


		<fieldset>
			<legend>
				<strong>Report Conditions</strong>
			</legend>
			<table>
				<tr>
					<td>
						<div>
							Start Date:<span> <input type="text" class="calendar"
								maxlength="19" onfocus="$(this).calendar()" name="startDate"
								value="${startDate}" readonly />
							</span>
						</div>
					</td>

					<td>
						<div>
							End Date:<span> <input type="text" class="calendar"
								maxlength="19" onfocus="$(this).calendar()" name="endDate"
								value="${endDate}" readonly />
							</span>
						</div>
					</td>

				</tr>
			</table>
		</fieldset>
<hr/>
		<div class="btn-set">
			<button type="submit">Show Report</button>
		</div>


	</form:form>

	<c:if test="${empty list}">
	No Customer Bill Analysis Found
</c:if>
	<c:if test="${not empty list}">

		

		<div class="fixed-table-container">
		<div id="toolbar1">
			<h4>Bill Analysis:</h4>
		</div>
			<table id="table_page1">
				<!-- 
		<tr>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>

			<th>Prepaid</th>
			<th>Bill Period</th>
			<th>0-30</th>
			<th>30-60</th>
			<th>60-90</th>
			<th>>90</th>
		</tr>
		<tr>
			<th>Order Code</th>
			<th>Customer</th>
			<th>Employee</th>
			<th>Place Time</th>
			<th>Finished TIme</th>

			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
			<th>&nbsp;&nbsp;</th>
		</tr>
		 -->
				<c:forEach items="${list}" var="detail">
					<tr class="result">
						<c:if test="${detail.orderCode eq 'Total'}">
							<td colspan="5">${detail.orderCode}</td>
						</c:if>
						<c:if test="${detail.orderCode ne 'Total'}">
							<td>${detail.orderCode}</td>
							<td>${detail.customerName}</td>
							<td>${detail.employeeName}</td>
							<td><fmt:formatDate value="${detail.placeTime}"
									timeStyle="short" type="both" /></td>
							<td><fmt:formatDate value="${detail.finishedTime}"
									timeStyle="short" type="both" /></td>
						</c:if>
						<td><fmt:formatNumber type="number" value="${detail.prePay}"
								pattern="#.00" /></td>
						<td><fmt:formatNumber type="number" value="${detail.inPay}"
								pattern="#.00" /></td>
						<td><fmt:formatNumber type="number"
								value="${detail.thirtyPayAmount}" pattern="#.00" /></td>
						<td><fmt:formatNumber type="number"
								value="${detail.sixtyPayAmount}" pattern="#.00" /></td>
						<td><fmt:formatNumber type="number"
								value="${detail.ninetyPayAmount}" pattern="#.00" /></td>
						<td><fmt:formatNumber type="number"
								value="${detail.outerNinetyPayAmount}" pattern="#.00" /></td>

					</tr>
				</c:forEach>
			</table>

		</div>
		<br />
		<div class="fixed-table-container">
		<div id="toolbar2">
			<h4>Credit Account Analysis:</h4>
		</div>
		<table id="table_page2">
		<!-- 
			<tr>
				<th>Customer</th>
				<th>LineOfCredit</th>
				<th>UsedCredit</th>
				<th>ResidueCredit</th>


			</tr>
 -->
			<c:forEach items="${creditList}" var="detail">
				<tr>
					<td>${detail.customerName}</td>
					<td><fmt:formatNumber type="number"
							value="${detail.lineOfCredit}" pattern="#.00" /></td>
					<td><fmt:formatNumber type="number"
							value="${detail.lineOfUsedCredit}" pattern="#.00" /></td>
					<td><fmt:formatNumber type="number"
							value="${detail.lineOfResedueCredit}" pattern="#.00" /></td>

				</tr>
			</c:forEach>
		</table>
		</div>
	</c:if>

</div>

<!-- <script src="${commonResourcePath}/acerchem/jquery.js"></script> -->
<script src="${commonResourcePath}/acerchem/jquery-1.9.1.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery.base64.js"></script>
<script src="${commonResourcePath}/acerchem/jquery-calendar.js"></script>
<script src="${commonResourcePath}/acerchem/bootstrap.min.js"></script>
<script src="${commonResourcePath}/acerchem/bootstrap-table.min.js"></script>

<script src="${commonResourcePath}/acerchem/tableExport.min.js"></script>
<script
	src="${commonResourcePath}/acerchem/bootstrap-table-export.min.js"></script>


<script
	src="${commonResourcePath}/acerchem/customerBillAnalysis.js"></script>

