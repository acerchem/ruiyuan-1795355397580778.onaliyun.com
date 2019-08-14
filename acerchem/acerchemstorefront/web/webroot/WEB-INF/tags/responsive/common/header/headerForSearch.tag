<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/responsive/common/header" %>


<cms:pageSlot position="TopHeaderSlot" var="component" element="div" class="container">
	<cms:component component="${component}" />
</cms:pageSlot>

	<div class="maxtop">
	 <div class="maxLogo">
	     <cms:pageSlot position="SiteLogo" var="logo" limit="1">
			 <cms:component component="${logo}" />
		 </cms:pageSlot>
	 </div>
	 
	 <nav:topNavigation />
		<c:url value="/my-account/update-profile" var="accUrl"/>
	<div class="maxtop_rig">
		<ul>
<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" >
		<li style="min-width: 59px;width: 30px;"><a style="background: none;font-size: 12px;line-height: 18px;text-align: center;width: 100%;" href="${accUrl}">My Account</a></li>
</sec:authorize>
		<li>
			<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" >

			<c:set var="maxNumberChars" value="25" />
				<c:if test="${fn:length(user.firstName) gt maxNumberChars}">
					<c:set target="${user}" property="firstName"
						value="${fn:substring(user.firstName, 0, maxNumberChars)}..." />
				</c:if>
				<span class="userintro">hello,${user.firstName}<br/>
						<button onclick="logout()">logout</button>
				</span>
			</sec:authorize>

			<a class="maxicon-user" href="${accUrl}"></a>
	 	</li>

			<li>
			
			<cms:pageSlot position="MiniCart" var="cart" >
					<cms:component component="${cart}"/>
				</cms:pageSlot>
			</li>
		</ul>
	</div>
	
</div>
	
<script type="text/javascript">

function logout(){
	window.location.href ='<c:url value='/logout'/>'
} 

function login(){
	window.location.href ='<c:url value='/login'/>'
} 

<!--

//-->
</script>
		