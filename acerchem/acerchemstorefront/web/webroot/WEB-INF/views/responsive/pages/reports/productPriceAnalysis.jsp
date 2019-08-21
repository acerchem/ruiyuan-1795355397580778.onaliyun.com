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

<c:url value="/reports/productPriceAnalysis"
	var="action" />					
<body class="index-body">
	
	<!-- top -->
    <jsp:include page="reportMenu.jsp" >
    	<jsp:param name="section"  value="${isDocMenu}" />
    </jsp:include>
	<!-- content -->
	<div class="index-container">
		<form:form method="post" action="${action}">
			<div class="top-select">
				<table border="0" width="100%" cellspacing="0">
					<tbody>
						<tr>
							<td><span>Input Month:</span> <input id="birth_year"
								class="easyui-datebox" name="month" type="text" value="${month}"
								style="width: 100px; padding-left: 10px" /></td>

							<td>
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
				<div class="left">商品销售报表/商品价格趋势分析</div>

			</div>
			<c:if test="${empty list}">
	No Product Price Analysis Found
</c:if>
			<c:if test="${not empty list}">
				<div class="fixed-table-container">

					<div id="toolbar">
						<h4>${month}</h4>
					</div>
					<table id="table_page">
						<!-- 
      <tr>
          <th>&nbsp;</th>
		  <th>&nbsp;</th>
		  <th>&nbsp;</th>
          <th colspan="${maxWeek}">${month}</th>
          <th>&nbsp;</th>
        
      </tr>
      <tr>
          <th>Prodcut Code</th>
		  <th>Product Name</th>
		  <th>Quantity</th>
		   <th>1st Week</th>
		   <th>2nd Week</th>
		   <th>3rd Week</th>
		   <th>4th Week</th>
		   <c:if test="${maxWeek >=5}">
		   <th>5th Week</th>
		   </c:if>
		    <c:if test="${maxWeek ==6}">
		   <th>6th Week</th>
		    </c:if>
		   <th>Average Price</th>
          
      </tr>
       -->
						<c:forEach items="${list}" var="detail">
							<tr>
								<td>${detail.productCode}</td>
								<td>${detail.productName}</td>
								<%--<td>${detail.salesQuantity}</td>--%>
								<td><fmt:formatNumber type="number"
										value="${detail.firstWeekPrice}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.secondWeekPrice}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.thirdWeekPrice}" pattern="#.00" /></td>
								<td><fmt:formatNumber type="number"
										value="${detail.fouthWeekPrice}" pattern="#.00" /></td>

								<c:choose>
									<c:when test="${maxWeek >=5}">
										<td><fmt:formatNumber type="number"
												value="${detail.fifthWeekPrice}" pattern="#.00" /></td>
									</c:when>
									<c:otherwise>
										<td></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${maxWeek ==6}">
										<td><fmt:formatNumber type="number"
												value="${detail.sixthWeekPrice}" pattern="#.00" /></td>

									</c:when>
									<c:otherwise>
										<td></td>
									</c:otherwise>
								</c:choose>
								<td><fmt:formatNumber type="number"
										value="${detail.averagePrice}" pattern="#.00" /></td>



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
	<script src="${commonResourcePath}/acerchem/productPriceAnalysis.js"></script>
	
</body>
</html>