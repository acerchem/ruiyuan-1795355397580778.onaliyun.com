<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>

<c:url value="/account/add-support-ticket" var="url" />
<spring:htmlEscape defaultHtmlEscape="true" />

<template:master pageTitle="${pageTitle}">
	<div class="member-content" style="margin:auto;" >
		<div class="sign-content g-right">
		<div id="global-alerts" class="global-alerts"></div>
		<div class="back-link border">
			<div class="row">
				<div class="sign-content g-right">
					<a href="support-tickets"> <span
						class="glyphicon glyphicon-chevron-left"></span>
					</a> 
					<span class="label"><spring:theme code="text.account.supporttickets" text="Support Tickets" /></span>
				</div>
			</div>
		</div>

<div class="row">
	<div class="sign-content g-right">
		<div class="rigcont" id="support">
			<form:form method="post" commandName="supportTicketForm" id="supportTicketForm" class="both" action="${action}">
				<div>
					<label> 
						<span class='label-title'>Your Name</span> 
						<input type="text" name='yourname' value="${supportTicketForm.yourname}" alt='Please Enter Your Name' class="required">
						<div style="color: #F00"><form:errors path="yourname" /></div>
					</label> 
					
					<label> 
						<span class='label-title'>Product ID</span> 
						<input type="text" value="${supportTicketForm.productId==null?'':supportTicketForm.productId }" disabled="disabled"><!--   -->
						<input type="hidden" name='supportTicketForm.productId' value="${supportTicketForm.productId==null?'':supportTicketForm.productId }">
						<div style="color: #F00"><form:errors path="productId" /></div> 
					</label>
					
					<label> 
						<span class='label-title'>Telephone / Mobile Phone</span> 
						<input type="text" name="telephone" value="${supportTicketForm.telephone}" alt='Please Enter Telephone' class="required">
						<div style="color: #F00"><form:errors path="telephone" /></div>
					</label> 
					
					<label> 
						<span class='label-title'>Product Name</span> 
						<input type="text" value="${supportTicketForm.productName==null?'':supportTicketForm.productName}" disabled="disabled">
						<input type="hidden" name='supportTicketForm.productName' value="${supportTicketForm.productName==null?'':supportTicketForm.productName}">
						<div style="color: #F00">
							<form:errors path="productName" />
						</div>
					</label> 
					<label> 
						<span class='label-title'>Shipping Address</span>
						<input type="text" name='address' value="${supportTicketForm.address}" alt='Please Enter Address' class="required">
						<div style="color: #F00">
							<form:errors path="address" />
						</div>

					</label> 
					<label> 
						<span class='label-title'>Email</span> 
						<input type="text" name="email" value="${supportTicketForm.email }" alt='Please Enter Email' class="required">
						<div style="color: #F00">
							<form:errors path="email" />
						</div>
					</label> 
					<span class=''>message</span>
					<textarea id="message" name="message" alt='Please Enter message' class="required"> </textarea>
					<div style="color: #F00">
						<form:errors path="message" />
					</div>
				</div>
			</form:form>
			<div id="customer-ticketing-buttons" class="form-actions">
				<div class="accountActions">
					<div class="row">
						<div class="col-sm-6 col-sm-push-6 accountButtons">
							<button class="btn-submit btn btn-primary btn-block" type="submit"><!--   -->
								<spring:theme code="text.account.supporttickets.createTicket.submit" text="Submit" />
							</button>
						</div>
						<div class="col-sm-6 col-sm-pull-6 accountButtons">
							<a href="support-tickets" class="btn btn-default btn-block">
								<spring:theme code="text.account.supporttickets.createTicket.back" text="Cancel" />
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div style="display: none">
	<span id="supporttickets-tryLater"><spring:theme
			code="text.account.supporttickets.tryLater" /></span> <span
		id="attachment-file-max-size-exceeded-error-message"><spring:theme
			code="text.account.supporttickets.fileMaxSizeExceeded" /></span> <span
		id="file-too-large-message"><spring:theme
			code="text.account.supporttickets.file.is.large.than"
			arguments="${maxUploadSizeMB}" /></span>
</div>


<common:globalMessagesTemplates />
</div>
</div>

<script type="text/javascript">
var regEmail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
$("input[name='email']").blur(function(){
	var checkEmail=$("input[name='email']").val();
	if(!regEmail.test(checkEmail)){
		 maxalert('Please enter a valid email!');
		return false;
	}else if(checkEmail==""||checkEmail==null){
		 maxalert('Please enter a valid email!');
			return false;
	}
});
$("input[name='telephone']").blur(function(){
	var checkTelephone=$("input[name='telephone']").val();
	if(checkTelephone==""||checkTelephone==null){
		 maxalert('Please enter a valid telephone!');
		return false;
	}
});

$('.btn-submit').on('click',function(){
	
	var message=$("#message").val().replace(/^\s+|\s+$/g,"");
	if(message==""||message==null){
		 maxalert('Please enter a message!');
		return false;
	}
	
	var wrap = $('#support form');
	var req = wrap.find('.required');
 	req.each(function(){
		var aval = $(this).val(),
			aname = $(this).attr('name'),
			thistext = $(this).attr('alt');
		if(aval){		
			var mymail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;		
			if(aname=='email'&&!mymail.test(aval)){				
				maxalert('Please enter a valid mailbox！')
				return false;
			}

			$(this).addClass('tg');
			if($('.tg').length==req.length){
				wrap.submit();
				return false;	
			}
			
		}else{
			maxalert(thistext)
			return false;
		}
	})	
	
})

</script> 
</template:master>
