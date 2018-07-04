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
<div class="title">product Sales Report</div>
<form:form method="post" commandName="productSalesForm" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
				<td>
					<label style="margin-left: 20px;">Month</label>
					<input name="month" size="6" type="text" value="${month}" placeholder="eg:201806"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Category</label>
					<formElement:customSelectBox idKey="CategoryCode" labelKey="" path="categoryCode" mandatory="true" skipBlank="false" skipBlankMessageKey="CategoryCode" items="${categories}" itemValue="code" itemLabel="name" selectedValue="${code}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Customer Area</label>
					<formElement:customSelectBox idKey="area" labelKey="" path="area" mandatory="true" skipBlank="false" skipBlankMessageKey="Area" items="${areas}" itemValue="isocode" itemLabel="name" selectedValue="${area}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Country</label>
					<formElement:customSelectBox idKey="countryIso" labelKey="" path="countryCode" mandatory="true" skipBlank="false" skipBlankMessageKey="address.country" items="${countries}" itemValue="isocode" itemLabel="name" selectedValue="${countryIso}"/>
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
	No product Sales Found
</c:if>
<c:if test="${not empty list}">
  <table border="1">
      <tr>
          <th>Product Code</th>
          <th>Product Name</th>
          <th>Customer</th>
          <th>Quantity</th>
          
      </tr>
      
      <c:forEach items="${list}" var="orderDetail">
      	  <tr>
	          <td>${orderDetail.productCode}</td>
	          <td>${orderDetail.productName}</td>
	      
	          <td>${orderDetail.customerName}</td>
	          <td>${orderDetail.salesQuantity}</td>
	       
	      </tr>
      </c:forEach>
  </table>
</c:if>


