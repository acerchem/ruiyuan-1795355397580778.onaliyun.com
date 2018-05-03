<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/desktop/address"%>
<c:url value="/" var="homeUrl"/>
<c:url value="/login" var="loginUrl"/>

<body class="gray">
<div class="sign-header">
	<a href="${homeUrl}">
		<img src="${themeResourcePath}/css/acerchem.png" alt="acerchem"/>
	</a>
</div>
<div class="sign-content regular-content" id="login">
	<div class="title">
		Create An Account
	</div>
	<div class="text-section">
		For a fast checkout, easy access to previous orders, and the adility to create an address book and store settings. Register below
	</div>
	
	<form:form method="post" commandName="customRegisterForm" name="customRegisterForm" class="both" action="${action}">
	
		<label>
			<span class='label-title'>Email</span>	
			<input id="email" type="text" name='email' value="${customRegisterForm.email}"  alt='Please Enter Email' class="required">
			<div style="color:#F00"><form:errors path="email"/></div>
		</label>

		<label>
			<span class='label-title'>Name</span>	
			<input id="contacts" type="text" name='contacts' value="${customRegisterForm.contacts}" alt='Please Enter Contacts Name' class="required">
			<div style="color:#F00"><form:errors path="contacts"/></div>
		</label>
		
		<label>
			<span class='label-title'>password</span>	
			<input id="pwd" type="password" name='pwd'  alt='Please Enter Password' value="${customRegisterForm.pwd}" autocomplete="off" class="required"/>
			<div style="color:#F00"><form:errors path="pwd"/></div>
		</label>
		
		<label>
			<span class='label-title'>checkPwd</span>	
			<input id="checkPwd" type="password" name='checkPwd' autocomplete="off" value="${customRegisterForm.checkPwd}" alt='Please Enter checkPwd' class="required"/>
			<div style="color:#F00"><form:errors path="checkPwd"/></div>
		</label>
		
		<label>
			<span class='label-title'>Nickname</span>	
			<input id="name" type="text" name='name' value="${customRegisterForm.name}"  alt='Please Enter Your Name' class="required"/>
			<div style="color:#F00"><form:errors path="name"/></div>
		</label>

		<label>
			<span class='label-title'>Telephone</span>	
			<input id="telephone" type="text" name='telephone' value="${customRegisterForm.telephone}" alt='Please Enter Telephone' class="required"/>
			<div style="color:#F00"><form:errors path="telephone"/></div>
		</label>
		
		<label>
			<div class="flex-wrap select-wrap">
				<div class="flex">
					<span class='label-title'>Currency</span>	
					<div class="selbox">
							<select name="currency" id="currency">
								<c:forEach items="${currencies}" var="curr">
									<option value="${curr.isocode}" ${(customRegisterForm.currency!=null&&curr.isocode == customRegisterForm.currency)||(customRegisterForm.currency==null&&curr.isocode == currentCurrency.isocode) ? 'selected="selected"' : ''}>
										<c:out value="${curr.symbol} ${curr.isocode}"/>
									</option>
								</c:forEach>
							</select>
							<div style="color:#F00"><form:errors path="currency"/></div>
					</div>	
				</div>
				<div class="flex">
					<span class='label-title'>Language</span>	
					<div class="selbox">
							<select name="language" id="language">
								<c:forEach items="${languages}" var="lang">
									<c:choose>
										<c:when test="${(customRegisterForm.language==null&&lang.isocode == currentLanguage.isocode)||(customRegisterForm.language!=null&&lang.isocode == customRegisterForm.language)}">
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
			<span class='label-title'>Mobile Number</span>	
			<input type="text" name='mobileNumber' id="mobileNumber" value="${customRegisterForm.mobileNumber}" alt='Please Enter Mobile Phone' class="required"/>
			<div style="color:#F00"><form:errors path="mobileNumber"/></div>
		</label>
		
		<label>
			<span class='label-title'>Shipping Address</span>	
			<div class="flex-wrap">
				<div class="flex">					
					<div class="selbox">
						<div id="selectShipCountry" data-address-code="${fn:escapeXml(addressData.id)}" data-country-iso-code="${fn:escapeXml(addressData.country.isocode)}" class="form-group">
							<formElement:customSelectBox idKey="shipAddress.countryIso" labelKey="address.country" path="shipAddress.countryIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.country" items="${countries}" itemValue="isocode" selectedValue="${customRegisterForm.shipAddress.countryIso}"/>
							<div style="color:#F00"><form:errors path="shipAddress.countryIso"/></div>
						</div>
					</div>
				</div>
				<div class="flex">					
					<div class="selbox">	
						<div id="showShipRegions" class="showShipRegions">
							<formElement:customSelectBox idKey="shipAddress.regionIso" labelKey="address.province" path="shipAddress.regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.selectProvince" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${customRegisterForm.shipAddress.regionIso}"/>
							<div style="color:#F00"><form:errors path="shipAddress.regionIso"/></div>
						</div>
					</div>	
				</div>
			</div>
			<input type="text" id="shipAddress.townCity" name='shipAddress.townCity' value="${customRegisterForm.shipAddress.townCity}" placeholder="Detailed address" alt='Please Enter Shipping Detailed Address' class="lab-row required"/>
			<div style="color:#F00"><form:errors path="shipAddress.townCity"/></div>
		</label>

		<label>
			<span class='label-title'>Contact Address</span>	
			<div class="flex-wrap">
				<div class="flex">					
					<div class="selbox">
						<div id="selectContactCountry" data-address-code="${fn:escapeXml(addressData.id)}" data-country-iso-code="${fn:escapeXml(addressData.country.isocode)}" class="form-group">
							<formElement:customSelectBox idKey="contactAddress.countryIso" labelKey="address.country" path="contactAddress.countryIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.country" items="${countries}" itemValue="isocode" selectedValue="${customRegisterForm.contactAddress.countryIso}" />
							<div style="color:#F00"><form:errors path="contactAddress.countryIso"/></div>
						</div>
					</div>
				</div>
				<div class="flex">					
					<div class="selbox">	
						<div id="showContactRegions" class="showContactRegions">
							<formElement:customSelectBox idKey="contactAddress.regionIso" labelKey="address.province" path="contactAddress.regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.selectProvince" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${customRegisterForm.contactAddress.regionIso}"/>
							<div style="color:#F00"><form:errors path="contactAddress.regionIso"/></div>
						</div>
					</div>	
				</div>
			</div>
			<input type="text" id="contactAddress.townCity" name='contactAddress.townCity' value="${customRegisterForm.contactAddress.townCity}" class="lab-row required" placeholder="Detailed address" alt='Please Enter Contact Detailed Address'/>
			<div style="color:#F00"><form:errors path="contactAddress.townCity"/></div>
		</label>
		
		<input type="hidden" id="recaptchaChallangeAnswered" value="${requestScope.recaptchaChallangeAnswered}" />
		<div class="form_field-elements control-group js-recaptcha-captchaaddon"></div>
		
		<label align="center" style="width: calc(100% - 20px);">
			<input type="checkbox" name="aidField" value="agree"/>
			<span class="checkbox" style="font-weight: 800;">By creating an account, you agree to Acerchem<br/></span>
		</label>
		
		<%-- <div align="center">
			<a href="${loginUrl}">Conditions</a> of Use and <a href="${loginUrl}">Privacy Notice</a>.
		</div> --%>
		
		</form:form>
		<div class="btn-set">
			<button type="submit" class="btn btn-submit">Register</button>
		</div>
	<div class="link">
		Already have an account? <a href="${loginUrl}">Sign in </a>
	</div>
</div>

<!-- footer -->
<div class="sign-footer">
	<div class="link">
		<a href="">Conditions of Use</a>
		<a href="">Privacy Notice</a>
	</div>
	<div class="copyright">
		?1996-2018, Acerchem.com, Inc. or its affiliates 
	</div>
</div>
<script type="text/javascript">
$(document).on("change",'#selectShipCountry select', function (){
	var options = {
		'addressCode': '',
		'countryIsoCode': $(this).val()
	};
	$.ajax({
		url: ACC.config.encodedContextPath + '/login/getShipRegions',
		async: true,
		data: options,
		dataType: "html",
		beforeSend: function ()
		{
			$("#showShipRegions").html(ACC.address.spinner);
		}
	}).done(function (data)
			{
				$("#showShipRegions").html($(data).html());
			});
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
	
inputint()	
$('.btn-submit').on('click',function(){
	
	var id = document.getElementsByName('aidField');
	var value = new Array();
	for(var i = 0; i < id.length; i++){
	     if(id[i].checked)
	     value.push(id[i].value);
	}
	if(value.toString()=="agree")
	{
		var req = $('#login form');
		verification(req);
	}
	else
	{
		maxalert('Please agree to Acerchem!')
		return false;
	}
})
</script>
</body>