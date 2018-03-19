<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<template:page pageTitle="${pageTitle}">
    <cms:pageSlot position="Section1" var="feature">     
       <cms:component component="${feature}" />
    </cms:pageSlot>

<div class="maxmain max-col">
    <div class="container">
     <div class="maxfactory">
		<ul>
		
	       <cms:pageSlot position="Section2A" var="feature">     
		       <cms:component component="${feature}" />
		    </cms:pageSlot>
		</ul>
	</div>
    
    <div class="maxon_sales maxleft page2">
    
	      <cms:pageSlot position="Section3" var="feature" >
		        <cms:component component="${feature}" />
		   </cms:pageSlot>
    </div>
    
    <div class="maxon_sales maxleft page3">
         <cms:pageSlot position="Section4" var="feature" >
		        <cms:component component="${feature}" />
		 </cms:pageSlot>
    </div>
	    
    </div>
    
    <div class="maxsales maxleft">
    
    	<ul>
	    	
			<cms:pageSlot position="Section2B" var="feature">     
		       <cms:component component="${feature}" />
		    </cms:pageSlot>			
			
    	</ul>
    </div>
    
    
    <div class="maxprodu_sales maxleft ">
    	<div class="container">
    		<cms:pageSlot position="Section11" var="feature" >
		        <cms:component component="${feature}" />
		    </cms:pageSlot>
    	</div>
    
    </div>
    
    
    <div class="col-fun maxlatest_main maxleft">
    	<div class="container">
    		<div class="swiper-container4">
    			<div class="banner_btn"> 
		    		<a class="arrow-left" href="#"></a> 
					<a class="arrow-right" href="#"></a>
		    	</div>
		    	
		    	 <ul class="swiper-wrapper">
		    	 	
		    	 		<cms:pageSlot position="Section2C" var="feature">     
					       <cms:component component="${feature}" />
					    </cms:pageSlot>
		    	 </ul>
		    	
    		</div>
    	</div>
    </div>
    
    <div class="maxfeatured maxleft">
				<div class="container">
					<div class="title">Featured Factory Partners
						<div class="maxmore_tit">
							<a href="#" id="maxcenbtn1">more</a>
						</div>
					</div>
					<ul>
					<cms:pageSlot position="Section5" var="feature">     
						 <cms:component component="${feature}" />
					</cms:pageSlot>
					
				   </ul>
					
				</div>
			</div>
    
</div>

</template:page>
