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

<div class="sign-content" style="width: 600px;" id="login"><!--  regular-content -->
	<div class="title">
		Create An Account
	</div>
	<div class="text-section">
		For a fast checkout, easy access to previous orders, and the adility to create an address book and store settings. Register below
	</div>
	
	<form:form method="post" commandName="customRegisterForm" name="customRegisterForm"  action="${action}">
	
		<label>
			<span class='label-title'>Email<span style="color:red;font-size: 150%;"> *</span></span>	
			<input id="email" type="text" name='email' value="${customRegisterForm.email}"  alt='Please Enter Email' class="required">
			<div style="color:#F00"><form:errors path="email"/></div>
		</label>

		<%-- <label>
			<span class='label-title'>Nickname<span style="color:red;font-size: 150%;"> *</span></span>	
			<input id="contacts" type="text" name='contacts' value="${customRegisterForm.contacts}" alt='Please Enter Contacts Name' class="required">
			<div style="color:#F00"><form:errors path="contacts"/></div>
		</label> --%>
		
		<%-- <label>
			<span class='label-title'>Mobile Number<span style="color:red;font-size: 150%;"> *</span></span>	
			<input type="text" name='mobileNumber' id="mobileNumber" value="${customRegisterForm.mobileNumber}" alt='Please Enter Mobile Phone' class="required"/>
			<div style="color:#F00"><form:errors path="mobileNumber"/></div>
		</label> --%>
		
		<label>
			<span class='label-title'>Password<span style="color:red;font-size: 150%;"> *</span></span>	
			<input id="pwd" type="password" name='pwd'  alt='Please Enter Password' value="${customRegisterForm.pwd}" autocomplete="off" class="required"/>
			<div style="color:#F00"><form:errors path="pwd"/></div>
		</label>
		
		<label>
			<span class='label-title'>Re-enter Password<span style="color:red;font-size: 150%;"> *</span></span>	
			<input id="checkPwd" type="password" name='checkPwd' autocomplete="off" value="${customRegisterForm.checkPwd}" alt='Please Enter checkPwd' class="required"/>
			<div style="color:#F00"><form:errors path="checkPwd"/></div>
		</label>
		
		<label>
			<div class="flex-wrap select-wrap">
				<div class="flex">
					<span class='label-title'>Currency<span style="color:red;font-size: 150%;"> *</span></span>	
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
					<span class='label-title'>Language<span style="color:red;font-size: 150%;"> *</span></span>	
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
			<span class='label-title'>Company Name<span style="color:red;font-size: 150%;"> *</span></span>	
			<input id="companyName" type="text" name='companyName' value="${customRegisterForm.companyName}" alt='Please Enter Company Name' class="required"/>
			<div style="color:#F00"><form:errors path="companyName"/></div>
		</label>
		
		<label>
			<span class='label-title'>Your Name<span style="color:red;font-size: 150%;"> *</span></span>	
			<input id="name" type="text" name='name' value="${customRegisterForm.name}"  alt='Please Enter Your Name' class="required"/>
			<div style="color:#F00"><form:errors path="name"/></div>
		</label>

		<label>
			<span class='label-title'>Telephone<span style="color:red;font-size: 150%;"> *</span></span>	
			<input id="telephone" type="text" name='telephone' value="${customRegisterForm.telephone}" alt='Please Enter Telephone' class="required"/>
			<div style="color:#F00"><form:errors path="telephone"/></div>
		</label>

		<label>
			<span class='label-title'>Contact Address<span style="color:red;font-size: 150%;"> *</span></span>	
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
		
		<label>
			<span class='label-title'>Shipping Address<span style="color:red;font-size: 150%;"> *</span></span>	
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
		
		<input type="hidden" id="recaptchaChallangeAnswered" value="${requestScope.recaptchaChallangeAnswered}" />
		<div class="form_field-elements control-group js-recaptcha-captchaaddon"></div>
		
		<label align="center" style="width: calc(100% - 20px);">
			<input type="checkbox" name="aidField" value="agree"/>
			<span class="checkbox" style="font-weight: 800;">
				<a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'">
					By creating an account, you agree to Acerchem
				</a>
			<br/>
			</span>
		</label>
		<%-- <div align="center">
			<a href="${loginUrl}">Conditions</a> of Use and <a href="${loginUrl}">Privacy Notice</a>.
		</div> --%>
		
		</form:form>
		<div class="btn-set">
			<button type="submit" class="btn btn-submit">Register</button>
		</div>
	<div class="link" style="width: 60%;">
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
		© 1996-2018, Acerchem.com, Inc. or its affiliates 
	</div>
</div>

<div id="light" class="white_content">
   	<div id="Terms">
		<table style="font-family:arial;font-size: 12px;border-color: #000000">
		<tbody>
			<tr>
				<td>
				<strong>			
						TERMS OF USE
				</strong>
				</td>
			</tr>
			<tr>
				<td>
				<p>
					Ingredients4U Inc. ("I4U," "we", "our" or "us") maintains the site located at the domain "Ingredients4U.com" ('Site"). Ingredients4U provides to you with services on this Site subject to the following terms and conditions ("Terms of Use"). Your use of this Site signifies your agreement to this Terms of Use. Please read the Terms of Use carefully. In addition to this Terms of Use, when you use any current or future Ingredients4U service you also may be subject to the guidelines and conditions applicable to such service or business.
				</p>
					
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						PRIVACY
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						Please review our Privacy Notice, which also governs your visit to this Site, to understand our practices relating to the collection and use of your personal information.
					</p>
				</td>
			</tr>
			<tr>
				<td>
				<strong>
					TRADEMARKS
				</strong>
					
				</td>
			</tr>
			<tr>
				<td>
				<p>
					
					All brand names, product and service names and titles and copyrights used in this site are trademarks, trade names, service marks or copyrights of their respective holders. No permission is given by Ingredients4U for their use by any person other than the said holders and such use may constitute an infringement of the holders' rights.
				</p>
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						ELECTRONIC COMMUNICATIONS
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						When you visit this Site or send e-mails to us, you are communicating with us electronically. You consent to receive communications from us electronically. We will communicate with you by e-mail or by posting notices on this Site. You agree that all agreements, notices, disclosures and other communications that we provide to you electronically satisfy any legal requirement that such communications be in writing, and that your checkmark in a box accepting any terms and conditions constitutes your signature and satisfies any requirement that your acceptance be signed.
					</p>
				</td>
			</tr>
			<tr>
				<td>
				<strong>
					INTELLECTUAL PROPERTY
				</strong>
					
				</td>
			</tr>
			<tr>
				<td>
					<p>
						All materials displayed on, or contained within, the Site, including, but not limited to, design, text, editorial materials, informational text, photographs, illustrations, artwork and other graphic materials, and names, logos, trademarks, and service marks (collectively, the "Materials"), are the property of Ingredients4U or its licensors and are protected by copyright, trademark, patent and all other applicable intellectual property laws. The Ingredients4U name, designs, and related marks contained within the Materials are trademarks of Ingredients4U. You acknowledge and agree that the Site and the Materials are the property of Ingredients4U and its licensors, and that you will not acquire any rights or licenses in any trademarks, patents, copyrights, or other intellectual property on the Site or in the Materials.  The Site and the Materials are protected by copyright, both individually and as a collective work or compilation, by trademark and patent laws, and by other applicable laws.
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						Your misuse of the Site or any of the Materials is strictly prohibited.
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						YOUR OBLIGATION
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						You may use the Siteâs features, offerings and content for lawful purposes only.  By using the Site, you represent and warrant that: (i) you have the power and authority to accept this Terms of Use and to enter into this agreement with Ingredients4U; (ii) you are capable of assuming, and do assume, any risks related to the use of the Materials on the Site; and (iii) you understand and accept the terms, conditions and risks relating to the use of the content and Materials on the Site. If you do not agree with this Terms of Use or are in any way dissatisfied with the Site or the Materials, your exclusive remedy is to discontinue using the Site.
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						The Site is not intended for use by children.
					</p> 
				</td>
			</tr>
			<tr>
				<td>
				<strong>
					DISCLAIMER OF WARRANTIES
				</strong>
					
				</td>
			</tr>
			<tr>
				<td>
				<p>
					Ingredients4U and its affiliates attempt to be as accurate as possible. However, Ingredients4U does not warrant that descriptions of services or other content of this site is accurate, complete, reliable, current, interruption-free or error-free. Moreover, Ingredients4U does not warrant that any information sent or received in connection therewith is secure and will not be intercepted by unauthorized parties, or that the site, including any storage services and its contents, or the server that makes them available, are free of viruses or other harmful components.<br>
	Without limiting the foregoing, Ingredients4U and its licensors, make no representation and disclaim all express or implied warranties and conditions of any kind related to the site, including, but not limited to, representations, warranties or conditions regarding accuracy, timeliness, completeness, non-infringement, satisfactory or merchantable quality or fitness for any particular purpose or those arising by law, statute, usage of trade, or course of dealing and warranties implied from a course of performance or course of dealing. This site is provided by Ingredients4U on an "as is" and "as available" basis. You expressly agree that your use of this site is at your sole risk.
				</p>
					
	
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						LIMITATION OF LIABILITY
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<br>
					<p>
						In no event shall Ingredients4U, its affiliates or licensors be liable for any direct, indirect, punitive, incidental, special, consequential or other damages arising out of or in any way connected with the site and your use, attempted use, or any delay or inability to use, the site, or for any information, software and services obtained through the site, whether based on contract, tort (including but not limited to negligence), strict liability or otherwise, even if Ingredients4U or any of its affiliates or licensors has been advised of the possibility of damages. This waiver applies, without limitation, to any damages or injury arising from any failure of performance, error, omission, interruption, deletion, defect, delay in operation or transmission, computer virus, file corruption, communication-line failure, network or system outage, or theft, destruction, unauthorized access to, alteration of, or use of any record. You specifically acknowledge and agree that Ingredients4U, its affiliates or licensor shall not be liable for any defamatory, offensive or illegal conduct of any user of the site.<br></p>
						<p>
						The above limitations and exclusions shall apply to you to the fullest extent that applicable law permits, in all actions of any kind, whether based on contract, tort (including, without limitation, negligence) or any other legal or equitable theory. Any clause declared invalid shall be deemed severable and not affect the validity or enforceability of the remainder of this terms of service.
					</p>
					
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						INDEMNIFICATION
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						You agree to indemnify, defend and hold Ingredients4U and its affiliates, and their respective officers, directors, owners, employees, agents, information providers and licensors (collectively the "Indemnified Parties") harmless from and against any and all claims, liability, losses, actions, suits, costs and expenses (including attorneysâ fees) arising out of or incurred by any breach by you of this Terms of Use. Ingredients4U reserves the right, at its own expense, to assume the exclusive defense and control of any matter otherwise subject to indemnification by you, and in such case, you agree to cooperate with Ingredients4U âs defense of such claim.  Ingredients4U has no duty to reimburse, defend, indemnify, or hold you harmless resulting from, relating to, or arising out of, this Terms of Use or the Site.
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						NO ENDORSEMENT OF THIRD PARTY SITES
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						The Site contains links to or references third party websites, resources and advertisers (collectively, "Third Party Sites").  Your linking to such Third Party Sites is at your own risk.  Ingredients4U is not responsible for the accuracy or reliability of any content, data, opinions, advice, statements, or other information made on the Third Party Sites.  Ingredients4U also is not responsible for the availability of these Third Party Sites, nor is it responsible for the aesthetics, appeal, suitability to taste or subjective quality of informational content, advertising, products or other materials made available on or through such Third Party Sites.  No endorsement of any third party content, information, data, opinions, advice, statements, goods, services or products is expressed or implied by any information, material or content of any third party contained in, referred to, included on, or linked from or to, the Site.  Under no circumstances shall Ingredients4U be held responsible or liable, directly or indirectly, for any loss, injury, or damage caused or alleged to have been caused to you in connection with the use of, or reliance on, any content, information, data, opinions, advice, statements, goods, services, or products available on such Third Party Sites.  You should direct any concerns to the respective Third Party Siteâs administrator or webmaster.  Any links to Third Party Sites do not imply that Ingredients4U is legally authorized to use any trademark, trade name, logo or copyright symbol displayed in or accessible through such links, or that any linked Third Party Site is authorized to use any trademark, trade name, logo or copyright symbol of Ingredients4U .
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						DISPUTES
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						Any dispute relating in any way to your visit to the Site shall be submitted to confidential arbitration, except that, to the extent you have in any manner violated or threatened to violate Ingredients4U âs intellectual property rights, Ingredients4U may seek injunctive or other appropriate relief in any state or federal court, and you consent to jurisdiction and venue in such courts. Arbitration under this agreement shall be conducted under the rules then prevailing of the American Arbitration Association. The arbitratorâs award shall be binding and may be entered as a judgment in any court of competent jurisdiction. To the fullest extent permitted by applicable law, no arbitration under this Agreement shall be joined to an arbitration involving any other party subject to this Agreement, whether through class arbitration proceedings or otherwise.
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						SITE GOVERNANCE AND SEVERABILITY
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						Please review our other policies posted on this Site. These policies also govern your visit to this Site.  If any provision of this Terms of Use shall be unlawful, void, or for any reason unenforceable, then that provision shall be deemed severable from this Terms of Use and shall not affect the validity and enforceability of any remaining provisions. This Terms of Use and any posted operating rules constitute the entire agreement of the parties with respect to the subject matter hereof, and supersede all prior or contemporaneous communications and proposals, whether oral or written, between the parties with respect to such subject matter.
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<strong>
						CHANGES TO THE TERMS OF USE
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					We reserve the right to make changes to our Site, policies, and this Terms of Use at any time.
				</td>
			</tr>
			</tbody>
		</table>
		<button onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">close</button>
	</div>
</div>
<div id="fade" class="black_overlay"></div>

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

<style> 
    .black_overlay{ 
        display: none; 
        position: absolute; 
        top: 0%; 
        left: 0%; 
        width: 100%; 
        height: 100%; 
        background-color: black; 
        z-index:1001; 
        -moz-opacity: 0.8; 
        opacity:.80; 
        filter: alpha(opacity=88); 
    } 
    .white_content { 
        display: none; 
        position: absolute; 
        top: 5%; 
        left: 25%; 
        width: 55%; 
        height: 80%; 
        padding: 20px; 
        border: 10px solid orange; 
        background-color: white; 
        z-index:1002; 
        overflow: auto; 
    } 
</style> 
</body>