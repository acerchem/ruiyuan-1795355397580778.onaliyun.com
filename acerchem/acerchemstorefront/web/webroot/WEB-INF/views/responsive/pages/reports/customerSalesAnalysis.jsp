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
<div class="title">Customer Sales Analysis Report</div>
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

<div class="btn-set">
	<button type="submit">Show Report</button> <button type="reset">Reset</button>
</div>

	
</form:form>	
<c:if test="${empty list}">
	No Customer Sales Analysis Found
</c:if>
<c:if test="${not empty list}">
  <table border="1" cellspacing="0" cellpadding="5">
      <tr>
          <th>Area</th>
		  <th>Country</th>
          <th>Customer</th>
          <th>OrderAmount</th>
          
      </tr>
      
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
</c:if>


