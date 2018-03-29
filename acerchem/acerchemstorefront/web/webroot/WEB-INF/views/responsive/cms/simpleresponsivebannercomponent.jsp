<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="banner">

	<c:forEach items="${medias}" var="media">
	   <img src="${media.url}" />
	</c:forEach>
   
</div>