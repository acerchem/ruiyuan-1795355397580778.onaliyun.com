<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:url value="/my-account/update-profile" var="action"/>
<template:page pageTitle="${pageTitle}">
	<div class="member-content">
		<user:personalInfo/>
		<div class="sign-content g-right">
			<div class="title">Personal Information</div>
			<div class="rigcont">
		        <form:form method="post" commandName="customRegisterForm" class="both" id="personal" action="${action}">
				<label>
					<span class='label-title' style="font-weight:normal;">Email</span>	
					<input type="text" value="${customRegisterForm.email}"  alt='Please Enter Email' disabled="disabled"/>
					<div style="color:#F00"><form:errors path="email"/></div>
				</label>
				
				<label>
					<span class='label-title' style="font-weight:normal;">Your Name</span>	
					<input id="register.name" type="text" name='name' value="${customRegisterForm.name}"  alt='Please Enter Your Name'>
					<div style="color:#F00"><form:errors path="name"/></div>
				</label>
				
				<label>
					<div class="flex-wrap select-wrap">
						<div class="flex">
							<span class='label-title' style="font-weight:normal;">Currency</span>	
							<div class="selbox">
								<select name="currency" id="register.currency">
									<c:forEach items="${currencies}" var="curr">
										<option value="${curr.isocode}" ${curr.isocode == customRegisterForm.currency ? 'selected="selected"' : ''}>
											<c:out value="${curr.symbol} ${curr.isocode} "/>
										</option>
									</c:forEach>
								</select>
								<div style="color:#F00"><form:errors path="currency"/></div>
							</div>	
						</div>
						<div class="flex">
							<span class='label-title' style="font-weight:normal;">Language</span>	
							<div class="selbox">
								<select name="language" id="register.language">
									<c:forEach items="${languages}" var="lang">
										<c:choose>
											<c:when test="${lang.isocode == customRegisterForm.language}">
												<option value="${lang.isocode}" selected="selected" lang="${lang.isocode}">
													${lang.nativeName}
												</option>
											</c:when>
											<c:otherwise>
												<option value="${lang.isocode}" lang="${lang.isocode}">
													${lang.nativeName} (${lang.name})
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<div style="color:#F00"><form:errors path="language"/></div>
							</div>	
						</div>
					</div>
				</label>
				
				<label>
					<span class='label-title' style="font-weight:normal;">Contacts</span>	
					<input id="register.contacts" type="text" name='contacts' value="${customRegisterForm.contacts}" alt='Please Enter Contacts Name'>
					<div style="color:#F00"><form:errors path="contacts"/></div>
				</label>
				
				<label>
					<div class="flex-wrap select-wrap">
						<div class="flex">		
							<span class='label-title' style="font-weight:normal;">Contact Address</span>	
							<div class="selbox" id="selectContactCountry" data-address-code="${fn:escapeXml(addressData.id)}" data-country-iso-code="${fn:escapeXml(addressData.country.isocode)}">
								<formElement:customSelectBox idKey="register.contactAddressCountry" labelKey="address.country" path="contactAddress.countryIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.country" items="${countries}" itemValue="isocode" selectedValue="${customRegisterForm.contactAddress.countryIso}"/>
								<div style="color:#F00"><form:errors path="contactAddress.countryIso"/></div>
							</div>
						</div>
						<div class="flex">		
							<span class='label-title'>&nbsp;</span>			
							<div id="showContactRegions" class="selbox">
								<formElement:customSelectBox idKey="register.contactAddressRegion" labelKey="address.province" path="contactAddress.regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.selectProvince" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${customRegisterForm.contactAddress.regionIso}"/>
								<div style="color:#F00"><form:errors path="contactAddress.regionIso"/></div>
							</div>
						</div>
					</div>
				</label>
				
				<label>
					<span class='label-title'></span>	
					<input type="text" id="register.contactAddressDetail" name='contactAddress.townCity' value="${customRegisterForm.contactAddress.townCity}" class="lab-row" placeholder="Detailed address" alt='Please Enter Contact Detailed Address'/>
					<div style="color:#F00"><form:errors path="contactAddress.townCity"/></div>
				</label>	
				
				<input type="hidden" name='email' value="${customRegisterForm.email}"/>
				<input type="hidden" name="contactAddress.addressId" value="${customRegisterForm.contactAddress.addressId}" />
				</form:form>
				<div class="btn-set">	
					<button type="submit" class="btn-submit btn">Confirm</button>
				</div>
			</div>
		</div>
		<user:PromotionItem/>
	</div>
	
<script type="text/javascript">
$('.btn-submit').on('click',function(){
	var req = $('#personal');
	req.submit();
})

$(document).on("change",'#selectContactCountry select', function (){
	var options = {
		'addressCode': '',
		'countryIsoCode': $(this).val()
	};
	$.ajax({
		url: ACC.config.encodedContextPath + '/login/getContactRegions',
		async: true,
		data: options,
		dataType: "html",
		beforeSend: function ()
		{
			$("#showContactRegions").html(ACC.address.spinner);
		}
	}).done(function (data)
			{
				$("#showContactRegions").html($(data).html());
			});
}) 
</script>
</template:page>