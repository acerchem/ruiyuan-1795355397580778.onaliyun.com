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

<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/style.css"/>	 

<body class="gray">
<form:form action="${loginActionUrl}" method="post" commandName="loginForm">
	<div class="sign-header">
		<c:url value="/" var="homeUrl"/>
		<a href="${homeUrl}">
			<img height="60px" src="${themeResourcePath}/css/logo.png" alt="acerchem"/>
		</a>
	</div>
	<div class="sign-content" id="sign">
		<c:if test="${not empty message}">
			<span class="has-error"> <spring:theme code="${message}" />
			</span>
		</c:if>	
		<formElement:formInputBox idKey="j_username" labelKey="login.email" path="j_username" mandatory="true" />
		<formElement:formPasswordBox idKey="j_password" labelKey="login.password" path="j_password" inputCSS="form-control" mandatory="true" />
		<label>
			<input type="checkbox" name="remember-me" id="ck">
			<span class="checkbox">Keep me signed in</span><!-- alice: need update -->
			<a  href="#" data-link="<c:url value='/login/pw/request'/>" class="js-password-forgotten" data-cbox-title="<spring:theme code="forgottenPwd.title"/>">
				<spring:theme code="login.link.forgottenPwd" />
			</a>
		</label>
		<ycommerce:testId code="loginAndCheckoutButton">
			<div class="btn-set">
				<button type="submit" class="btn" id="loginbtn">
					<spring:theme code="login.login" />
				</button>
				<c:url value="/login/register" var="registerUrl"/>
				<a class="btn btn-line" href="${registerUrl}">Create your Account</a>
			</div>
		</ycommerce:testId>
	</div>
</form:form>
<script>

	$(function() {

		$("#cboxLoadingOverlay").remove();
		$("#cboxLoadingGraphic").remove();

		if ($.cookie('isChecked') == '1'){
			$("input[type='checkbox']").attr('checked', true);
			$("#j_username").val($.cookie('username'));
			$("#j_password").val($.cookie('password'));
		}else {
			$("input[type='checkbox']").attr('checked', false);
			$("#j_username").val('');
			$("#j_password").val('');
		}

		//设置用户是否选择记住密码
		$("#ck").click(function () {
			if ($(this).is(":checked")) {
				$.cookie('isChecked', "1");
			} else {
				$.cookie('isChecked', "2");
			}
		})
		$("#loginbtn").click(function () {
			if ($.cookie('isChecked') == '1'){
				$.cookie('username', $("#j_username").val());
				$.cookie('password', $("#j_password").val());
			}else {
				$.removeCookie('username');
				$.removeCookie('password');
			}
		})

	})
</script>
</body>