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
<link rel="stylesheet" href="${commonResourcePath}/acerchem/easyui.css">  

<template:javaScript/>

<div class="title">Product Price Analysis Report</div>
<form:form method="post" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
				<td>
					<label style="margin-left: 20px;">Input Month:</label>
				   
					<input id="birth_year" class="easyui-datebox" name="month" type="text" value="${month}"  style="width: 100px;padding-left: 10px"/>
				</td>
				
				
			</tr>
	</table>
</fieldset>

<div class="btn-set">
	<button type="submit">Show Report</button> <button type="reset">Reset</button>
</div>

<%-- <nav:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/reports/orderDetails"  numberPagesShown="100"/>
 --%>		
</form:form>	
<c:if test="${empty list}">
	No Product Price Analysis Found
</c:if>
<c:if test="${not empty list}">
  <table border="1" cellspacing="0" cellpadding="5">
      <tr>
          <th>&nbsp;</th>
		  <th>&nbsp;</th>
		  <th>&nbsp;</th>
          <th colspan="${maxWeek}">${month}</th>
          <th>&nbsp;</th>
        
      </tr>
      <tr>
          <th>Prodcut code</th>
		  <th>Product name</th>
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
		   <th>Average price</th>
          
      </tr>
      <c:forEach items="${list}" var="detail">
      	  <tr>
	          <td>${detail.productCode}</td>
			  <td>${detail.productName}</td>
			  <td>${detail.salesQuantity}</td>
			  <td><fmt:formatNumber type="number" value="${detail.firstWeekPrice}" pattern="#.00"/> </td>
			  <td><fmt:formatNumber type="number" value="${detail.secondWeekPrice}" pattern="#.00"/> </td>
			  <td><fmt:formatNumber type="number" value="${detail.thirdWeekPrice}" pattern="#.00"/> </td>
			  <td><fmt:formatNumber type="number" value="${detail.fouthWeekPrice}" pattern="#.00"/> </td>
			  <c:if test="${maxWeek >=5}">
			  <td><fmt:formatNumber type="number" value="${detail.fifthWeekPrice}" pattern="#.00"/> </td>
			   </c:if>
			    <c:if test="${maxWeek ==6}">
			  <td><fmt:formatNumber type="number" value="${detail.sixthWeekPrice}" pattern="#.00"/> </td>
			    </c:if>
			  <td><fmt:formatNumber type="number" value="${detail.averagePrice}" pattern="#.00"/> </td>
			 
			  
	          
	      </tr>
      </c:forEach>
  </table>
</c:if>

<script src="${commonResourcePath}/acerchem/jquery-1.8.3.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery.easyui.min.js"></script>
<script src="${commonResourcePath}/acerchem/yearmonth-easyui.js"></script>
