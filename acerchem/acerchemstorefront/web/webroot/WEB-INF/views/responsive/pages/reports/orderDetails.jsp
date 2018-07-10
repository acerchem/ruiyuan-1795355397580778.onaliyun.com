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
<div class="title">Order Details Report</div>
<form:form method="post" commandName="searchCriteriaFrom" action="${action}">
<fieldset>
	<legend>
		<strong>Report Conditions</strong>
	</legend>
	<table>
			<tr style="white-space:nowrap">
				<td >
					<label style="margin-left: 20px;">Month</label>
					
					<input id="birth_year" class="easyui-datebox" name="month" type="text" value="${searchCriteriaFrom.month}"  style="width: 100px;padding-left: 10px"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Customer Area</label>
					<formElement:customSelectBox idKey="area" labelKey="" path="area" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${areas}" itemValue="isocode" itemLabel="name" selectedValue="${searchCriteriaFrom.area}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Country</label>
					<formElement:customSelectBox idKey="countryIso" labelKey="" path="countryCode" mandatory="true" skipBlank="false" skipBlankMessageKey="All" items="${countries}" itemValue="isocode" itemLabel="name" selectedValue="${searchCriteriaFrom.countryCode}"/>
				</td>
				<td>
					<label style="margin-left: 20px;">Order Code</label>
					<input name="orderCode" size="12" type="text" value="${searchCriteriaFrom.orderCode}">
				</td>
				<td>
					<label style="margin-left: 20px;">Customer Name</label>
					<input name="userName" size="12" type="text" value="${searchCriteriaFrom.userName}">
				</td>
				<td>
					<label style="margin-left: 20px;">Page Number</label>
					<input name="pageNumber" size="12" type="text" value="${searchCriteriaFrom.pageNumber}">
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
  <table border="1" cellspacing="0" cellpadding="5" >
      <tr>
          <th>Country</th>
          <th>Area</th>
          <th>OrderCode</th>
          <th>PlaceTime</th>
          <th>FinishedTime</th>
          <th>ProductName</th>
          <th>ProductQuantity</th>
          <th>OrderAmount</th>
          <th>UserId</th>
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
	          <td>
	          	<fmt:formatDate value="${orderDetail.orderFinishedTime}"  timeStyle="short" type="both"/>
	          </td>
	          <td>${orderDetail.productName}</td>
	          <td>${orderDetail.productQuantity}</td>
	          <td>
	          <c:if test="${not empty orderDetail.orderAmount}">
	          	USD
	          	</c:if>
	          	<fmt:formatNumber type="number" value="${orderDetail.orderAmount}" pattern="#.00"/>
	          </td>
	          <td>${orderDetail.userUid}</td>
	          <td>${orderDetail.deliveryAddress.firstName}${orderDetail.deliveryAddress.lastName}</td>
	          <td>${orderDetail.deliveryAddress.phone}</td>
	          <td>
	          	<c:choose>
	          		<c:when test="${not empty orderDetail.warehouse}">
	          		     ${orderDetail.warehouse}
	          		</c:when>
	          		<c:otherwise>
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
	       			</c:otherwise>
	       			</c:choose>
	          </td>
	          <td>${orderDetail.salesman}</td>
	          <td>${orderDetail.supplier}</td>
	      </tr>
      </c:forEach>
  </table>
</c:if>

<script src="${commonResourcePath}/acerchem/jquery-1.8.3.min.js"></script>
<script src="${commonResourcePath}/acerchem/jquery.easyui.min.js"></script>
<script src="${commonResourcePath}/acerchem/yearmonth-easyui.js"></script>

