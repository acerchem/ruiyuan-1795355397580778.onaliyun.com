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
<div class="title">Order Details Report</div>
<form:form method="post" commandName="searchCriteriaFrom" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr>
				<td>
					<label>Month</label>
					<formElement:customSelectBox idKey="month" labelKey="" path="month" mandatory="true" skipBlank="false" skipBlankMessageKey="Month" items="${months}" itemValue="isocode" itemLabel="name" selectedValue="${month}"/>
				</td>
				
				<td>
					<label style="margin-left: 20px;">Customer Area</label>
					<formElement:customSelectBox idKey="area" labelKey="" path="area" mandatory="true" skipBlank="false" skipBlankMessageKey="Area" items="${areas}" itemValue="isocode" itemLabel="name" selectedValue="${area}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Country</label>
					<formElement:customSelectBox idKey="countryIso" labelKey="" path="countryCode" mandatory="true" skipBlank="false" skipBlankMessageKey="address.country" items="${countries}" itemValue="isocode" itemLabel="name" selectedValue="${countryIso}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Order Code</label>
					<input name="orderCode" size="12" type="text" value="${orderCode}">
				</td>
				<td>
					<label style="margin-left: 20px;">Customer Name</label>
					<input name="userName" size="12" type="text" value="${userName}">
				</td>
				<td>
					<label style="margin-left: 20px;">pageNumber</label>
					<input name="pageNumber" size="12" type="text" value="${pageNumber}">
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
<c:if test="${empty searchPageData}">
	No Order Details Found
</c:if>
<c:if test="${not empty searchPageData}">
  <table border="1">
      <tr>
          <th>Country</th>
          <th>Area</th>
          <th>OrderCode</th>
          <th>PlaceTime</th>
          <th>ProductName</th>
          <th>ProductQuantity</th>
          <th>OrderAmount</th>
          <th>UserUid</th>
          <th>DeliveryContact</th>
          <th>DeliveryPhone</th>
          <th>DeliveryAddress</th>
          <th>Salesman</th>
          <th>Supplier</th>
      </tr>
      
      <c:forEach items="${searchPageData}" var="orderDetail">
      	  <tr>
	          <td>${orderDetail.country}</td>
	          <td>${orderDetail.area}</td>
	          <td>${orderDetail.orderCode}</td>
	          <td>
	          	<fmt:formatDate value="${orderDetail.orderTime}"  timeStyle="short" type="both"/>
	          </td>
	          <td>${orderDetail.productName}</td>
	          <td>${orderDetail.productQuantity}</td>
	          <td>
	          	${orderDetail.currency}
	          	<fmt:formatNumber type="number" value="${orderDetail.orderAmount}" pattern="#.00"/>
	          </td>
	          <td>${orderDetail.userUid}</td>
	          <td>${orderDetail.deliveryAddress.firstName}${orderDetail.deliveryAddress.lastName}</td>
	          <td>${orderDetail.deliveryAddress.phone}</td>
	          <td>
          		<c:if test="${not empty orderDetail.deliveryAddress.line1}">
		            ${fn:escapeXml(orderDetail.deliveryAddress.line1)},
		        </c:if>
		        <c:if test="${not empty orderDetail.deliveryAddress.line2}">
		            ${fn:escapeXml(orderDetail.deliveryAddress.line2)},
		        </c:if>
				<c:if test="${not empty orderDetail.deliveryAddress.town}">
		            ${fn:escapeXml(orderDetail.deliveryAddress.town)},
		        </c:if>
		        <c:if test="${not empty orderDetail.deliveryAddress.region.name}">
		            ${fn:escapeXml(orderDetail.deliveryAddress.region.name)},
		        </c:if>
				<c:if test="${not empty orderDetail.deliveryAddress.postalCode}">
		            ${fn:escapeXml(orderDetail.deliveryAddress.postalCode)},
		        </c:if>
		        <c:if test="${not empty orderDetail.deliveryAddress.country.name}">
		            ${fn:escapeXml(orderDetail.deliveryAddress.country.name)}
       			</c:if>
	          </td>
	          <td>${orderDetail.salesman}</td>
	          <td>${orderDetail.supplier}</td>
	      </tr>
      </c:forEach>
  </table>
</c:if>


