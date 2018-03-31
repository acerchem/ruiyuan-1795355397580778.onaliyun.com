<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasAnyRole('ROLE_ANONYMOUS')" >
<c:url value="/j_spring_security_check" var="loginActionUrl" />
<div class="login-section">
	<user:loginIndex actionNameKey="login.login" action="${loginActionUrl}"/>
</div>
</sec:authorize>
