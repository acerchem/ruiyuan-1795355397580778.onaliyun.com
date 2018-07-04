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

<template:javaScript/>
<div class="title">product Price Analysis Report</div>
<form:form method="post" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
				<td>
					<label style="margin-left: 20px;">Input Month:</label>
					<input name="year" size="6" type="text" value="${month}" placeholder="201807"/>
				</td>
				
				
			</tr>
	</table>
</fieldset>

<div class="btn-set">
	<button type="submit">Show Report</button> <button type="reset">reset</button>
</div>

<%-- <nav:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/reports/orderDetails"  numberPagesShown="100"/>
 --%>		
</form:form>	
<c:if test="${empty list}">
	No product Price Analysis Found
</c:if>
<c:if test="${not empty list}">
  <table border="1">
      <tr>
          <th>&nbsp;</th>
		  <th>&nbsp;</th>
		  <th>&nbsp;</th>
          <th colspan="6">${month}</th>
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
		   <th>5th Week</th>
		   <th>6th Week</th>
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
			  <td><fmt:formatNumber type="number" value="${detail.fifthWeekPrice}" pattern="#.00"/> </td>
			  <td><fmt:formatNumber type="number" value="${detail.sixthWeekPrice}" pattern="#.00"/> </td>
			  <td><fmt:formatNumber type="number" value="${detail.averagePrice}" pattern="#.00"/> </td>
			 
			  
	          
	      </tr>
      </c:forEach>
  </table>
</c:if>


