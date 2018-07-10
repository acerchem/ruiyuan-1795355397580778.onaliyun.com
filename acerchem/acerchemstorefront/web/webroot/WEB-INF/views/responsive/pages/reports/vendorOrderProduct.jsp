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

<template:javaScript/>
<div class="title">VendorOrder Report</div>
<form:form method="post"  action="${action}">


<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
			<td>
				<div> start date:<span>
				<input type="text" class="calendar" maxlength="19" onfocus="$(this).calendar()" name="startDate"  readonly/>
				</span>
				</div>
			</td>
				
			<td>
				<div> end date:<span>
				<input type="text" class="calendar" maxlength="19" onfocus="$(this).calendar()" name="endDate"  readonly/>
				</span>
				</div>
			</td>	
				
			</tr>
	</table>
</fieldset>

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
  <table border="1" cellspacing="0" cellpadding="5">
      <tr>
          <th>Order Code</th>
          <th>place Time</th>
          <th>finished Time</th>
          <th>product Name</th>
          <th>Quantity</th>
          <th>vendor</th>
        
      </tr>
      
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
</c:if>



<script src="${commonResourcePath}/acerchem/jquery.js"></script>
<script src="${commonResourcePath}/acerchem/jquery-1.8.3.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery-calendar.js"></script>
