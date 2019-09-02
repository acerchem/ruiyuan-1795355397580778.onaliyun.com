<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<script type="text/javascript">
    $().ready(function(){
        $("body").addClass("gray");
    });
</script>
<spring:htmlEscape defaultHtmlEscape="true"/>

        <div class="login-section">
            <div class="sign-header"><spring:theme code="text.account.profile.resetPassword"/></div>
            <div class="sign-content">
                <form:form method="post" commandName="updatePwdForm">
                        <div class="form-group">
                            <formElement:formPasswordBox idKey="password" labelKey="updatePwd.pwd" path="pwd"
                                                         inputCSS="form-control password-strength" mandatory="true"/>
                        </div>
                        <div class="form-group">
                            <formElement:formPasswordBox idKey="updatePwd.checkPwd" labelKey="updatePwd.checkPwd"
                                                         path="checkPwd" inputCSS="form-control" mandatory="true"/>
                        </div>
                        <div class="row login-form-action">
                            <div class="col-sm-6">
                                <button type="submit" onclick="validate()" class="btn btn-primary btn-block" style="background-color:#0d4170;border:1px solid #0d4170">
                                    <spring:theme code="updatePwd.submit"/>
                                </button>
                            </div>
                            <div class="col-sm-6">
                                <button type="button" class="btn btn-default btn-block backToHome">
                                    <spring:theme code="text.button.cancel"/>
                                </button>
                            </div>
                        </div>
                </form:form>
            </div>
        </div>
<script type="text/javascript">
    $(".help-block").attr("display","none");
    function validate(){
        var pwd = /^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z])[0-9a-zA-Z]{6,16}$/;
        var aval = $("#password").val();
        console.log(aval);
        if(aval.length<6 || aval.length>16 ||!pwd.test(aval))
        {
            maxalert('Password should be a combination of 6-16 lower case letters,uppercase letter and numbers!')
            return false;
        }
        return true;
    }
</script>

