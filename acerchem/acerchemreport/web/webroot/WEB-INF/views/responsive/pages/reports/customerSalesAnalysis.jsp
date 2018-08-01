<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<spring:htmlEscape defaultHtmlEscape="true" />
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap.min.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap-table.css">

<template:javaScript/>
<div class="container">
<div class="title"><h2>Customer Sales Analysis Report</h2></div>
<hr />
<form:form method="post" commandName="customerSalesAnalysisForm" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr style="white-space:nowrap">
				
				<td>
					<label style="margin-left: 20px;">Customer Area</label>
					<formElement:customSelectBox idKey="area" labelKey="" path="area" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${areas}" itemValue="isocode" itemLabel="name" selectedValue="${area}"/>
				</td>
				
				<td>
					<label style="margin-left: 20px;">Customer Name</label>
					<input name="customerName" size="12" type="text" value="${customerName}">
				</td>
				<td>
					<label style="margin-left: 20px;"> Order Amount ></label>
					<input name="amount" size="12" type="text" value="${amount}">
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
	          <td>
	          	<fmt:formatNumber type="number" value="${orderDetail.salesAmount}" pattern="#.00"/>
	          </td>
	         
	      </tr>
      </c:forEach>
  </table>
  </div>
</c:if>
</div>
<script src="${commonResourcePath}/acerchem/jquery-1.9.1.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery.base64.js"></script>
<script src="${commonResourcePath}/acerchem/bootstrap.min.js"></script>
<script src="${commonResourcePath}/acerchem/bootstrap-table.min.js"></script>
<script src="${commonResourcePath}/acerchem/tableExport.min.js"></script>
<script
	src="${commonResourcePath}/acerchem/bootstrap-table-export.min.js"></script>
<script
	src="${commonResourcePath}/acerchem/customerSalesAnalysis.js"></script>
