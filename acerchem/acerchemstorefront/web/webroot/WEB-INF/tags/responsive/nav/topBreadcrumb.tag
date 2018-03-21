<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/responsive/nav/breadcrumb"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	

<c:url value="/" var="homeUrl" />

<c:if test="${fn:length(breadcrumbs) > 0}">
	<div class="m-crumbs">
		
		<div class="link">
			<a href="${homeUrl}"><spring:theme code="breadcrumb.home" /></a>
			
			<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
				<c:url value="${breadcrumb.url}" var="breadcrumbUrl" />
				<c:choose>
					<c:when test="${status.last}">
						${fn:escapeXml(breadcrumb.name)}
					</c:when>
					<c:when test="${breadcrumb.url eq '#'}">
						
							<a href="#">${fn:escapeXml(breadcrumb.name)}</a>
						
					</c:when>
					<c:otherwise>
						
							<a href="${breadcrumbUrl}">${fn:escapeXml(breadcrumb.name)}</a>
						
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		
	</div>
</c:if>
