<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
				<form action="" method="post">
					<label>
						<span class='label-title'>Email Address</span>	
						<input type="text" name='email' alt="Please Enter Email" class="required">
					</label>

					<label>
						<span class='label-title'>Passwore</span>	
						<input type="password" name='passwore' alt="Please Enter Passwore" class="required">
					</label>		
					
					<label>
						<input type="checkbox" name="Keep">
						<span class="checkbox">Keep me signed in</span>
						<a href="password.html">Forgot your passwore?</a>
					</label>
				</form>
				<div class="btn-set">
					<a class="btn btn-submit" href="javascript:void(0)">Log In</a>
					<a class="btn btn-line" href="login.html">Create your Amazon account</a>
				</div>
			</div>