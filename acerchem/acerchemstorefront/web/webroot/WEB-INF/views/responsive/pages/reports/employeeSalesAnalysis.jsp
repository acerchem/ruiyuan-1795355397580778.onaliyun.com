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
<div class="title">Employee Sales Analysis Report</div>
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

<div class="btn-set">
	<button type="submit">Show Report</button> <button type="reset">Reset</button>
</div>

<%-- <nav:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/reports/orderDetails"  numberPagesShown="100"/>
 --%>		
</form:form>	
<c:if test="${empty salesList}">
	No Employee Sales Analysis Found
</c:if>
<c:if test="${not empty salesList}">
  <table border="1" cellspacing="0" cellpadding="5">
      <tr>
          <th>Month</th>
          <th>User</th>
          <th>Amount</th>
        
      </tr>
      
      <c:forEach items="${salesList}" var="detail">
      	  <tr>
	          <td>${detail.month}</td>
			
	          <td>${detail.employee}</td>
			  <td><fmt:formatNumber type="number" value="${detail.amount}" pattern="#.00"/> </td>
	          
	      </tr>
      </c:forEach>
  </table>
</c:if>


