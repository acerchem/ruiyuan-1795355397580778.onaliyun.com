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
<link rel="stylesheet" href="${commonResourcePath}/acerchem/jquery-calendar.css">  
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap.min.css">
<link rel="stylesheet"
	href="${commonResourcePath}/acerchem/bootstrap-table.css">
	
<template:javaScript/>

<div class="container">
<div class="title"><h2>VendorOrder Report</h2></div>
<hr />
<form:form method="post" commandName="vendorAnalysisForm"  action="${action}">


<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
			<td>
				<div> start date:<span>
				<input type="text" class="calendar" maxlength="19" onfocus="$(this).calendar()" name="startDate"  value="${vendorAnalysisForm.startDate}" readonly/>
				</span>
				</div>
			</td>
				
			<td>
				<div> end date:<span>
				<input type="text" class="calendar" maxlength="19" onfocus="$(this).calendar()" name="endDate"  value="${vendorAnalysisForm.endDate}" readonly/>
				</span>
				</div>
			</td>	
			
				<td>
					<label style="margin-left: 20px;">Choose Vendor:</label>
					<formElement:customSelectBox idKey="vendorCode" labelKey="" path="vendor" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${vendors}" itemValue="code" itemLabel="name" selectedValue="${vendorAnalysisForm.vendor}"/>
				</td>
				
			</tr>
	</table>
</fieldset>
<hr />
<div class="btn-set" >
    <br/>
	<button type="submit">Show Report</button> 
</div>

<%-- <nav:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/reports/orderDetails"  numberPagesShown="100"/>
 --%>		
</form:form>	

<c:if test="${empty list}">
	No Vendor Order Analysis Found
</c:if>
<c:if test="${not empty list}">
<div class="fixed-table-container">
  <table id="table_page">
  <!-- 
      <tr>
          <th>Order Code</th>
          <th>place Time</th>
          <th>finished Time</th>
          <th>product Name</th>
          <th>Quantity</th>
          <th>vendor</th>
        
      </tr>
       -->
      <c:forEach items="${list}" var="detail">
      	  <tr>
	          <td>${detail.orderCode}</td>
	          <td><fmt:formatDate value="${detail.placeTime}"  timeStyle="short" type="both"/></td>
	           <td><fmt:formatDate value="${detail.finishedTime}"  timeStyle="short" type="both"/></td>
	           <td>${detail.productName}</td>
	            <td>${detail.productQuantity}</td>
	             <td>${detail.vendorName}</td>
	            
	      </tr>
      </c:forEach>
  </table>
  </div>
</c:if>

</div>

<script src="${commonResourcePath}/acerchem/jquery-1.9.1.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery.base64.js"></script>
<script src="${commonResourcePath}/acerchem/jquery-1.8.3.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery-calendar.js"></script>

<script src="${commonResourcePath}/acerchem/bootstrap.min.js"></script>
<script src="${commonResourcePath}/acerchem/bootstrap-table.min.js"></script>
<script src="${commonResourcePath}/acerchem/tableExport.min.js"></script>
<script
	src="${commonResourcePath}/acerchem/bootstrap-table-export.min.js"></script>
<script
	src="${commonResourcePath}/acerchem/vendorOrderProduct.js"></script>

