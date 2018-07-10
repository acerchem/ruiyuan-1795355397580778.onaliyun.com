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
<div class="title">Product Sales Report</div>
<form:form method="post" commandName="productSalesForm" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
				<td>
					<label style="margin-left: 20px;">Month</label>
					<input id="birth_year" class="easyui-datebox" name="month" type="text" value="${month}"  style="width: 100px;padding-left: 10px"/>
					
				</td>
				<td>
					<label style="margin-left: 20px;">Category</label>
					<formElement:customSelectBox idKey="CategoryCode" labelKey="" path="categoryCode" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${categories}" itemValue="code" itemLabel="name" selectedValue="${code}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Customer Area</label>
					<formElement:customSelectBox idKey="area" labelKey="" path="area" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${areas}" itemValue="isocode" itemLabel="name" selectedValue="${area}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Country</label>
					<formElement:customSelectBox idKey="countryIso" labelKey="" path="countryCode" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${countries}" itemValue="isocode" itemLabel="name" selectedValue="${countryIso}"/>
				</td>
				
			</tr>
	</table>
</fieldset>

<div class="btn-set">
	<button type="submit">Show Report</button> 
</div>

<%-- <nav:pagination top="true" msgKey="text.account.supportTickets.page" showCurrentPageInfo="true" hideRefineButton="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="/reports/orderDetails"  numberPagesShown="100"/>
 --%>		
</form:form>	
<c:if test="${empty list}">
	No product Sales Found
</c:if>
<c:if test="${not empty list}">
  <table border="1" cellspacing="0" cellpadding="5">
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

<script src="${commonResourcePath}/acerchem/jquery-1.8.3.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery.easyui.min.js"></script>
<script src="${commonResourcePath}/acerchem/yearmonth-easyui.js"></script>

