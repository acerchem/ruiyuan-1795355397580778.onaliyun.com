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
<div class="title"><h2>InventoryAnalysis Report</h2></div>
<hr />
<form:form method="post" commandName="vendorInventoryForm"  action="${action}">


<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
			<td>
					<label style="margin-left: 20px;">Choose Vendor:</label>
					<formElement:customSelectBox idKey="vendorCode" labelKey="" path="vendor" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${vendors}" itemValue="code" itemLabel="name" selectedValue="${vendorInventoryForm.vendor}"/>
				</td>
				
			
			</tr>
	</table>
</fieldset>
<hr />
<div class="btn-set" >
    <br/>
	<button type="submit">Show Report</button> 
</div>	
</form:form>

<c:if test="${empty list}">
	No Inventory Analysis Found
</c:if>
<c:if test="${not empty list}">
<div class="fixed-table-container">
  <table id="table_page">
  <!-- 
      <tr>
          <th>Product Code</th>
          <th>Product Name</th>
          <th>Inventory</th>
          <th>Future Inventory</th>
          
      </tr>
       -->
      <c:forEach items="${list}" var="detail">
      	  <tr>
	          <td>${detail.productCode}</td>
	          <td>${detail.productName}</td>
	          <td>${detail.inventoryCount}</td>
	           <td>${detail.futureInventory}</td>
	           
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
	src="${commonResourcePath}/acerchem/vendorInventoryAnalysis.js"></script>

