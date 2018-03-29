<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<template:page pageTitle="${pageTitle}">
    
 <div class="col-fun maxlatest" >
	  <cms:pageSlot position="Section1" var="feature">     
	     <cms:component component="${feature}" />
	  </cms:pageSlot>
	  
	  <!-- search compent banner -->
	<cms:pageSlot position="Section3" var="component">
		<cms:component component="${component}" />
	</cms:pageSlot>
	  
	  
	  <!-- login compent banner 
	<cms:pageSlot position="Section4" var="feature">
		<cms:component component="${feature}" />
	</cms:pageSlot>
	-->
  <div class="maxmain max-col">
	<div class="container">
		<div class="maxmean">
		
		 <ul>
		 
		 <cms:pageSlot position="Section2A" var="component">
			<cms:component component="${component}" />
		</cms:pageSlot>
		 
		  <cms:pageSlot position="Section2B" var="component">
			<cms:component component="${component}" />
		</cms:pageSlot> 
		
		  <cms:pageSlot position="Section2B" var="component">
			<cms:component component="${component}" />
		</cms:pageSlot> 
		
		<cms:pageSlot position="Section2C" var="feature">
			<cms:component component="${feature}" />
		</cms:pageSlot> 
		
		<cms:pageSlot position="Section5" var="component">
			<cms:component component="${component}" />
		</cms:pageSlot>
		 
		 </ul>
		</div>
	</div>
  </div>
  
 </div>



</template:page>
