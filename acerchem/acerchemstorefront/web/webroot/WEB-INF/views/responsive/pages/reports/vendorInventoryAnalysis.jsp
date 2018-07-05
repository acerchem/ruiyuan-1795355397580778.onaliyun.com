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
<div class="title">InventoryAnalysis Report</div>

<c:if test="${empty list}">
	No Inventory Analysis Found
</c:if>
<c:if test="${not empty list}">
  <table border="1" cellspacing="0" cellpadding="5">
      <tr>
          <th>Product Code</th>
          <th>Product Name</th>
          <th>Inventory</th>
          <th>Future Inventory</th>
          
      </tr>
      
      <c:forEach items="${list}" var="detail">
      	  <tr>
	          <td>${detail.productCode}</td>
	          <td>${detail.productName}</td>
	          <td>${detail.inventoryCount}</td>
	           <td>${detail.futureInventory}</td>
	           
	      </tr>
      </c:forEach>
  </table>
</c:if>


