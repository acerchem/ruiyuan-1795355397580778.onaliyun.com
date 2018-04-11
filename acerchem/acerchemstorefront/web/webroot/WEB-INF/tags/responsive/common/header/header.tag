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

<!-- 
<div class="maxprosi-top max-col">
			<div class="maxcomment">
				<span class="maxicon_pr"></span>
			    <ul>
			    	<li><a href="">All Orders Are Shipped This Weekend</a></li>			    	
			    </ul>
		    	<span class="maxcole"></span>
			</div>
		</div> -->
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
	 		
	 			
			<%-- <sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')" >
			<span class="userintro"><br/>
					<a href="<c:url value='/login'/>">
						login
					</a>
					
					<button onclick="login()">login</button>
				</span>
			
			</sec:authorize>
			 --%>
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
	 	
	 		 <%-- <cms:pageSlot position="SearchBox" var="component">	
					<cms:component component="${component}"/>
				</cms:pageSlot> --%>
			<li>
			
			<cms:pageSlot position="MiniCart" var="cart" >
					<cms:component component="${cart}"/>
				</cms:pageSlot>
			</li>

	        
		</ul>
		<%-- <div class="searchlist">
				<form name="search_form_${fn:escapeXml(component.uid)}" method="get"
					action="${searchUrl}" class="both">
					<div class="input-group">
						<spring:theme code="search.placeholder" var="searchPlaceholder" />
							<input type="text" id="js-site-search-input"
								class="form-control js-site-search-input" name="text" value=""
			                    maxlength="100" placeholder="${searchPlaceholder}"
								data-options='{"autocompleteUrl" : "${autocompleteUrl}","minCharactersBeforeRequest" : "${component.minCharactersBeforeRequest}","waitTimeBeforeRequest" : "${component.waitTimeBeforeRequest}","displayProductImages" : ${component.displayProductImages}}'>
						
						
						<!-- <ul class="search-suggest">
								<button class="btn btn-link js_search_button" type="submit" disabled="true">
										<span class="glyphicon glyphicon-search"></span>
								</button>
						</ul>  -->
					</div>
				</form>
			</div> --%>
	</div>
	
	
</div>
	
<script type="text/javascript">

function logout(){
	window.location.href ='<c:url value='/logout'/>'
} 

function login(){
	window.location.href ='<c:url value='/login'/>'
} 

/**
 * 商品搜索
 *
 */
/* $('#searchProduct').autocomplete({
	source: function(request, response) {
			App.get('search/results/?q=' + $('#searchProduct').val(), {}, function(data) {
			// 信息不存在
			if(!data || !data.results || 0 == data.results.length) {
				response([]);
				return;
			}
			data = data.results;
			
			// 构建展示数据
			var responseList = new Array();
			//if(data[0] && data[0].length > 0){
				for(var i = 0; i < data.length; i++) {
					var showData = new Object();
					var showLabel = App.escapeNull(data[i].name);
					if(showLabel && data[i].code) {
						showLabel += ' ' + App.escapeNull(data[i].code);
					}
					if(showLabel && data[i].companyData && data[i].companyData.buyerSpecificCode) {
						showLabel += ' ' + App.escapeNull(data[i].companyData.buyerSpecificCode);
					}

					showData.label = showLabel;
					showData.value =App.escapeNull(data[i].name,data[i].code);
					showData.code = App.escapeNull(data[i].code);
					responseList.push(showData);
				}
			
			// 展示
			response(responseList);
		});
	}
}); */

<!--

//-->
</script>
		