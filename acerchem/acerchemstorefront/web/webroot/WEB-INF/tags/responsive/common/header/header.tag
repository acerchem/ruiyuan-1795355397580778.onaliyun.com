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
	 <div class="maxLogo">
	     <cms:pageSlot position="SiteLogo" var="logo" limit="1">
			 <cms:component component="${logo}" />
		 </cms:pageSlot>
	 </div>
	 
	 <nav:topNavigation />

	<div class="maxtop_rig">
		<ul>
		<li>
	 		
	 			
			<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')" >
			<span class="userintro"><br/>
					<%-- <a href="<c:url value='/login'/>">
						login
					</a> --%>
					
					<button onclick="login()">login</button>
				</span>
			
			</sec:authorize>
			
			<sec:authorize access="!hasAnyRole('ROLE_ANONYMOUS')" >
			
			<c:set var="maxNumberChars" value="25" />
				<c:if test="${fn:length(user.firstName) gt maxNumberChars}">
					<c:set target="${user}" property="firstName"
						value="${fn:substring(user.firstName, 0, maxNumberChars)}..." />
				</c:if>
				<span class="userintro">hello,${user.firstName}<br/>
						<%-- <a href="<c:url value='/logout'/>">
							logout
						</a> --%>
						<button onclick="logout()">logout</button>
				</span>
			</sec:authorize>
	 		
	 		
	 		
	 			<c:url value="/my-account/update-profile" var="accUrl"/>
			<a class="maxicon-user" href="${accUrl}"></a>
	 	</li>
	 	
	 	<li><cms:pageSlot position="SearchBox" var="component">
					<cms:component component="${component}" element="div" />
				</cms:pageSlot>
		</li>
			
			<li>
			
			<cms:pageSlot position="MiniCart" var="cart" element="div"
					class="componentContainer">
					<cms:component component="${cart}" element="div" />
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
		