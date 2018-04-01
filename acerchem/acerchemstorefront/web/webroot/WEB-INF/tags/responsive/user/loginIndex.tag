<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<c:url value="/j_spring_security_check" var="loginActionUrl" />

<%-- 
<div class="sign-content">
	<user:login actionNameKey="login.login" action="${loginActionUrl}" />
</div>
 --%>
<div class="sign-content" id="sign">

		<div class="title">
			Returning Customer
		</div>
		<div class="text-section">
			Already have an account? Sign in to returning your account settings.
		</div>
		<form:form action="${loginActionUrl}" method="post" commandName="loginForm">
		
		<c:if test="${not empty message}">
			<span class="has-error"> <spring:theme code="${message}" />
			</span>
		</c:if>	
			<label>
				<span class='label-title'>Email Address</span>	
				<input type="text" name='j_username'  alt="Please Enter Email" class="required">
			</label>

			<label>
				<span class='label-title'>Password</span>	
				<input type="password" name='j_password'  alt="Please Enter Passwore" class="required">
			</label>		
			
			<label>
				<input type="checkbox" name="Keep">
				<span class="checkbox">Keep me signed in</span>
				<a  href="#" data-link="<c:url value='/login/pw/request'/>" class="js-password-forgotten" data-cbox-title="<spring:theme code="forgottenPwd.title"/>">
				<spring:theme code="login.link.forgottenPwd" />
			</a>
			</label>
			<div class="btn-set">
			<button type="submit" class="btn">Log In</button>
			<c:url value="/login/register" var="registerUrl"/>
				<a class="btn btn-line" href="${registerUrl}">Register</a>
			</div>
		</form:form>
		<!-- <div class="btn-set">
			<a class="btn btn-submit" href="javascript:userLogin()">Log In</a>
			<a class="btn btn-line" href="login.html">Create your Amazon account</a>
		</div> -->
</div>

<script>

function userLogin(){
	
var email =$("#emailId").val();
var password = $("#pwdId").val();

if(email==""){
	
	alert("email is allowed");
	result=false;
	return;
}

if(password==""){
	
	alert("password is allowed");
	result=false;
	return;
}
	
}
</script>