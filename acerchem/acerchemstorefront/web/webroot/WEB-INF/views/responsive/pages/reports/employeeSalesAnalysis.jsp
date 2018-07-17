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
<div class="title"><h2>Employee Sales Analysis Report</h2></div>
<hr />
<form:form method="post" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
				<td>
					<label style="margin-left: 20px;">Input year:</label>
					<input name="year" size="6" type="text" value="${curYear}" placeholder="2018"/>
				</td>
				
				
			</tr>
	</table>
</fieldset>
<hr/>
<div class="btn-set">
	<button type="submit">Show Report</button> 
</div>

<%-- <nav:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/reports/orderDetails"  numberPagesShown="100"/>
 --%>		
</form:form>	
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
			  <td><fmt:formatNumber type="number" value="${detail.amount}" pattern="#.00"/> </td>
	          
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
	src="${commonResourcePath}/acerchem/employeeSalesAnalysis.js"></script>

