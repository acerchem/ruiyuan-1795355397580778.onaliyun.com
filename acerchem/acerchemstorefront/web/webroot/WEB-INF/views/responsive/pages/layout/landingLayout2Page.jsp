<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<template:pageForSearch pageTitle="${pageTitle}">
    
 <div class="col-fun maxlatest" >
	
	<div class="banner">
		<div class="device">
			<div class="banner_btn">
				<a class="arrow-left" href="#"></a> <a class="arrow-right" href="#"></a>
			</div>
			<div class="swiper-container">
				<div class="swiper-wrapper">
				
				  <cms:pageSlot position="Section1" var="component">     
				     <cms:component component="${component}" />
				  </cms:pageSlot>
	 
		
		</div>
	  </div>
	         <div class="pagination"></div>
		</div>
	</div>
	  
	  <!-- search compent banner -->
				<cms:pageSlot position="Section3" var="component">
					<cms:component component="${component}" />
				</cms:pageSlot>
	  
	  <!-- login compent banner -->
<%--<cms:pageSlot position="Section4" var="feature">
		<cms:component component="${feature}" />
	</cms:pageSlot>
	 --%>

  </div>
  
    <div class="maxmain max-col">
	<div class="container">
		<div class="maxmean">
			<ul>
		<cms:pageSlot position="Section5" var="component">
			<cms:component component="${component}" />
		</cms:pageSlot>
		
			</ul>
		</div>
					
	</div>
	<%-- 
	<div class="maxon_sales maxleft page2">
    
	      <cms:pageSlot position="Section2A" var="feature" >
		        <cms:component component="${feature}" />
		   </cms:pageSlot>
    </div>
     --%>
		 
  
 </div>



</template:pageForSearch>
