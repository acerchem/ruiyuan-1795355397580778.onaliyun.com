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
<div class="title">MonthlySalesAnalysis Report</div>
<form:form method="post" commandName="monthlySalesAnalysisForm" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
				<td>
					<label style="margin-left: 20px;">Month</label>
					<input name="year" size="6" type="text" value="${year}" placeholder="2018"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Customer Area</label>
					<formElement:customSelectBox idKey="area" labelKey="" path="area" mandatory="true" skipBlank="false" skipBlankMessageKey="Area" items="${areas}" itemValue="isocode" itemLabel="name" selectedValue="${area}"/>
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
<c:if test="${empty salesList}">
	No Monthly Sales Analysis Found
</c:if>
<c:if test="${not empty salesList}">
  <table border="1">
      <tr>
          <th>Country</th>
          <th>January</th>
          <th>February</th>
          <th>March</th>
          <th>April</th>
          <th>May</th>
          <th>June</th>
          <th>July</th>
          <th>August</th>
          <th>September</th>
          <th>October</th>
          <th>November</th>
          <th>December</th>
      </tr>
      
      <c:forEach items="${salesList}" var="detail">
      	  <tr>
	          <td>${detail.country}</td>
	          <td>${detail.januaryAmount}</td>
	          <td>${detail.februaryAmount}</td>
	           <td>${detail.marchAmount}</td>
	            <td>${detail.aprllAmount}</td>
	             <td>${detail.mayAmount}</td>
	              <td>${detail.juneAmount}</td>
	               <td>${detail.julyAmount}</td>
	                <td>${detail.augustAmount}</td>
	                <td>${detail.septemberAmount}</td>
	                <td>${detail.octoberAmount}</td>
	                <td>${detail.novemberAmount}</td>
	                <td>${detail.decemberAmount}</td>
	      </tr>
      </c:forEach>
  </table>
</c:if>


