<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>

<div class="title">Modify The Password</div>
<div class="rigcont"  style="margin-left:30%;">
	<form:form action="${action}" method="post" commandName="updatePasswordForm" id="password" class="keep">
		<span class='label-title'>Used Password</span>	
		<formElement:formPasswordBox idKey="currentPassword" labelKey="" path="currentPassword" inputCSS="form-control" mandatory="true" />
		<span class='label-title'>New Password</span>	
		<formElement:formPasswordBox idKey="newPassword" labelKey="" path="newPassword" inputCSS="form-control" mandatory="true" />
		<span class='label-title'>Confirm Password</span>
		<formElement:formPasswordBox idKey="checkNewPassword" labelKey="" path="checkNewPassword" inputCSS="form-control" mandatory="true" />
		<div class="btn-set">				
			<button type="submit" class="btn btn-primary btn-block">Confirm</button>
		</div>
	</form:form>
</div>
