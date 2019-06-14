<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<template:pageForSearch pageTitle="${pageTitle}">
	<div class="col-fun maxlatest">
		<div class="banner">
			<div class="device">
				<div class="banner_btn">
					<a class="arrow-left" href="#"></a>
					<a class="arrow-right" href="#"></a>
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
		<c:url value="/search/" var="searchUrl" />
		<spring:url value="/search/autocomplete/{/componentuid}" var="autocompleteUrl" htmlEscape="false">
		     <spring:param name="componentuid"  value="${component.uid}"/>
		</spring:url>
		
		<div class="bansearch">
			<form name="search_form_${fn:escapeXml(component.uid)}" method="get" action="${searchUrl}">
				<div class="input-group">
					<spring:theme code="search.placeholder" var="searchPlaceholder" />
					<input type="text" id="js-site-search-input" class="form-control js-site-search-input" name="text" value="" 
							maxlength="100" placeholder="${searchPlaceholder}" 
							data-options="{&quot;autocompleteUrl&quot; : &quot;${autocompleteUrl}&quot;,&quot;minCharactersBeforeRequest&quot; : &quot;${component.minCharactersBeforeRequest}&quot;,&quot;waitTimeBeforeRequest&quot; : &quot;${component.waitTimeBeforeRequest}&quot;,&quot;displayProductImages&quot; : ${component.displayProductImages}}" 
							style="background:none;border: 1.1px solid #fff;color:#fff;" />
					<span class="input-group-btn">
						<button class="btn btn-link js_search_button" type="submit" disabled="true">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</span>
				</div>
			</form>
		</div>
	</div>
	<div class="maxmain max-col">
		<div>
			<h2 style="text-align: center;margin-top: 30px;font-size: 30px;font-weight: bold;">EXPLORE OUR CATALOG</h2>
			<span style="display: block;margin: 0 auto;width: 330px;border-bottom: 2px solid #666;"></span>
		</div>

		<div class="container">
			<div>
				<ul class="category-media">
					<cms:pageSlot position="Section6" var="component6">
						<cms:component component="${component6}" />
					</cms:pageSlot>
				</ul>
				<div style="clear: both;"></div>
				<ul class="maxmean">
					<cms:pageSlot position="Section5" var="component">
						<cms:component component="${component}" />
					</cms:pageSlot>
				</ul>
			</div>
		</div>
	</div>
</template:pageForSearch>
