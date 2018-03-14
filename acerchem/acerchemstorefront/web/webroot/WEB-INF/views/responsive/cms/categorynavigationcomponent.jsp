<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<c:if test="${component.visible}">
	 <div class="maxmenu">
		 <ul>
		 <c:forEach items="${component.navigationNode.children}" var="childLevel1">
		 	 <li>
				 <c:forEach items="${childLevel1.entries}" var="childlink1">
				 	<cms:component component="${childlink1.item}" evaluateRestriction="true" element="span" />
				 </c:forEach>
			 </li>
		 </c:forEach>
		 </ul>
	 </div>
</c:if>