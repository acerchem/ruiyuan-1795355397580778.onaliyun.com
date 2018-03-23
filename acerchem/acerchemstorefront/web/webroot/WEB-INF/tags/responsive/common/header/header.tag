<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>

<cms:pageSlot position="TopHeaderSlot" var="component" element="div" class="container">
	<cms:component component="${component}" />
</cms:pageSlot>


<div class="maxprosi-top max-col">
			<div class="maxcomment">
				<span class="maxicon_pr"></span>
			    <ul>
			    	<li><a href="">All Orders Are Shipped This Weekend</a></li>			    	
			    </ul>
		    	<span class="maxcole"></span>
			</div>
		</div>
<div class="maxtop">
	 <div class="maxLogo js-site-logo">
	     <cms:pageSlot position="SiteLogo" var="logo" limit="1">
			 <cms:component component="${logo}" element="div" class="yComponentWrapper"/>
		 </cms:pageSlot>
	 </div>
	 
	 <nav:topNavigation />

	<div class="maxtop_rig">
		<ul>
		<!-- 
			<li><a class="maxicon-user" href=""></a></li>
			 -->
			 
			 <sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')">
				<c:set var="maxNumberChars" value="25" />
				<c:if test="${fn:length(user.firstName) gt maxNumberChars}">
					<c:set target="${user}" property="firstName"
						value="${fn:substring(user.firstName, 0, maxNumberChars)}..." />
				</c:if>

				<li class="logged_in js-logged_in">
					<ycommerce:testId code="header_LoggedUser">
						<spring:theme code="header.welcome" arguments="${user.firstName},${user.lastName}" htmlEscape="true" />
					</ycommerce:testId>
				</li>
			</sec:authorize>
							
			  <cms:pageSlot position="HeaderLinks" var="link">
			  
					<cms:component component="${link}" element="li" />
			 </cms:pageSlot>
			 
			 
							 
			<li><cms:pageSlot position="SearchBox" var="component">
					<cms:component component="${component}" element="div" />
				</cms:pageSlot></li>
			<li><cms:pageSlot position="MiniCart" var="cart" element="div"
					class="componentContainer">
					<cms:component component="${cart}" element="div" />
				</cms:pageSlot></li>
				
			<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')" >
			<li class="liOffcanvas">
				<ycommerce:testId code="header_Login_link">
					<a href="<c:url value='/login'/>">
						<spring:theme code="header.link.login" />
					</a>
				</ycommerce:testId>
			</li>
			</sec:authorize>

			<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" >
				<li class="liOffcanvas">
					<ycommerce:testId code="header_signOut">
						<a href="<c:url value='/logout'/>">
							<spring:theme code="header.link.logout" />
						</a>
					</ycommerce:testId>
				</li>
			</sec:authorize>
	        
		</ul>

	</div>
	
	<div class="hidden-xs hidden-sm js-secondaryNavAccount collapse" id="accNavComponentDesktopOne">
		<ul class="nav__links">

		</ul>
	</div>
</div>


<cms:pageSlot position="BottomHeaderSlot" var="component" element="div"	class="container-fluid">
	<cms:component component="${component}" />
</cms:pageSlot>

		