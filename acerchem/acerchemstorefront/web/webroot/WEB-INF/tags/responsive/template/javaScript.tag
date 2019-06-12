<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/responsive/template/cms" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>

<c:url value="/" var="siteRootUrl"/>

<template:javaScriptVariables/>

<c:choose>
	<c:when test="${wro4jEnabled}">
	  	<script type="text/javascript" src="${contextPath}/wro/all_responsive.js"></script>
	  	<script type="text/javascript" src="${contextPath}/wro/addons_responsive.js"></script>
	</c:when>
	<c:otherwise>
	    <%-- acerchem js file --%>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery-1.9.0.min.js"></script>
	
		<%-- jquery --%>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery-2.1.1.min.js"></script>
		
		<%-- bootstrap --%>
		<script type="text/javascript" src="${commonResourcePath}/bootstrap/dist/js/bootstrap.min.js"></script>
		
		<%-- plugins --%>
		<script type="text/javascript" src="${commonResourcePath}/js/enquire.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/Imager.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.blockUI-2.66.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.colorbox-min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.form.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.hoverIntent.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.pstrength.custom-1.2.0.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.syncheight.custom.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.tabs.custom.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery-ui-1.11.2.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.zoom.custom.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/owl.carousel.custom.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.tmpl-1.0.0pre.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.currencies.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.waitforimages.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/jquery.slideviewer.custom.1.2.js"></script>



		<%-- acerchem js file --%>
		<script type="text/javascript" src="${commonResourcePath}/js/swiper.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/index.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/general.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/max.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/maxprod.js"></script>

		<script type="text/javascript" src="${commonResourcePath}/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/moment-with-locales.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/bootstrap-datetimepicker.js"></script>

		<%-- Cms Action JavaScript files --%>
		<c:forEach items="${cmsActionsJsFiles}" var="actionJsFile">
		    <script type="text/javascript" src="${commonResourcePath}/js/cms/${actionJsFile}"></script>
		</c:forEach>
		
		<%-- AddOn JavaScript files --%>
		<c:forEach items="${addOnJavaScriptPaths}" var="addOnJavaScript">
		    <script type="text/javascript" src="${addOnJavaScript}"></script>
		</c:forEach>
		
	</c:otherwise>
</c:choose>


<cms:previewJS cmsPageRequestContextData="${cmsPageRequestContextData}" />
