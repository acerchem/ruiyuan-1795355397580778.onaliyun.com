<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/my-account/address-book" var="addressBookUrl" htmlEscape="false" />

<c:choose>
	<c:when test="${edit eq true }">
        <c:set var="headline"><spring:theme code="text.account.addressBook.updateAddress" /></c:set>
	</c:when>
	<c:otherwise>
        <c:set var="headline"><spring:theme code="text.account.addressBook.addAddress" /></c:set>
	</c:otherwise>
</c:choose>

<div class="title">
	<a href="${addressBookUrl}">
    	<span class="glyphicon glyphicon-chevron-left"></span>
    </a>
	${headline}
</div>

<c:if test="${empty addressFormEnabled or addressFormEnabled}">
	<div class="rigcont" id="address">
		<div style="background: #f3f3f3;padding: 40px;margin-top: 40px;">
			<form:form method="post" commandName="addressForm" class="both" id="newadd" action="${action}">
				
				<label>
					<span class='label-title'>Contacts</span>	
					<input type="text" name='lastName' value="${addressForm.lastName}" class="required" alt='Please Enter Contacts'/>
					<div style="color:#F00"><form:errors path="lastName"/></div>
				</label>
				
				<label>
					<span class='label-title'>Telephone</span>
					<input type="text" name='phone' value="${addressForm.phone}" class="required" alt='Please Enter Telephone'/>
					<div style="color:#F00"><form:errors path="phone"/></div>
				</label>
				
				<label>
					<span class='label-title'>Mobile Number</span>
					<input type="text" name='line1' value="${addressForm.line1}" class="required" alt='Please Enter Mobile Number'/>
					<div style="color:#F00"><form:errors path="line1"/></div>
				</label>
				
				<label>
					<span class='label-title'>Zip Code</span>	
					<input type="text" name='postcode' value="${addressForm.postcode}" class="required" alt='Please Enter Zip Code'/>
					<div style="color:#F00"><form:errors path="postcode"/></div>
				</label>
				
				<label>
					<span class='label-title'>Shipping Address</span>	
					<div class="flex-wrap">
						<div class="flex">			
							<div class="selbox">
								<div id="selectShipCountry" data-address-code="${fn:escapeXml(addressData.id)}" data-country-iso-code="${fn:escapeXml(addressData.country.isocode)}" class="form-group">
									<formElement:customSelectBox idKey="countryIso" labelKey="" path="countryIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.country" items="${countries}" itemValue="isocode" selectedValue="${addressForm.countryIso}"/>
									<div style="color:#F00"><form:errors path="countryIso"/></div>
								</div>
							</div>
						</div>
						<div class="flex">					
							<div class="selbox">
								<div id="showShipRegions">
									<formElement:customSelectBox idKey="regionIso" labelKey="" path="regionIso" mandatory="true" skipBlank="false" skipBlankMessageKey="address.selectProvince" items="${regions}" itemValue="${useShortRegionIso ? 'isocodeShort' : 'isocode'}" selectedValue="${addressForm.regionIso}"/>
									<div style="color:#F00"><form:errors path="regionIso"/></div>
								</div>
							</div>	
						</div>
					</div>
				</label>
	
				<label>		
					<span class='label-title'>&nbsp;</span>			
					<input type="text" name='townCity' value="${addressForm.townCity}" placeholder="Detailed address" class="required" alt='Please Enter Detailed Address'>
					<div style="color:#F00"><span>&nbsp;</span><form:errors path="townCity"/></div>
				</label>		
				
				<form:hidden path="addressId" class="add_edit_delivery_address_id" status="${not empty suggestedAddresses ? 'hasSuggestedAddresses' : ''}" />
				<input type="hidden" name="bill_state" id="address.billstate" />
				<input type="hidden" name="defaultAddress" value="${addressForm.defaultAddress}" />
				
			</form:form>
			<div class="btn-set">				
				<a class="btn btn-line" href="${addressBookUrl}">Cancel</a>
				<button class="btn btn-submit" type="submit">Confirm</button>
			</div>
		</div>
	</div>
</c:if> 

<script type="text/javascript">
$(document).on("change",'#selectShipCountry select', function (){
	var options = {
		'addressCode': '',
		'countryIsoCode': $(this).val()
	};
	$.ajax({
		url: ACC.config.encodedContextPath + '/my-account/getAddressRegions',
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
	
inputint()	
$('.btn-submit').on('click',function(){
	var req = $('#address form');
	addressVerification(req);
})

function addressVerification(wrap){//address verification
	var req = wrap.find('.required');
 	req.each(function(){
		var aval = $(this).val(),
			aname = $(this).attr('name'),
			thistext = $(this).attr('alt');
		
		var mobile = /^\d{0,4}[-]?\d{8,12}$/;
		
		if(aval)
		{		
			if(aname=='mobileNumber' && !mobile.test(aval))
			{
				maxalert('Please confirm the Mobile Number is 8-12 numbers!')
				return false;
			}
			
			if(aname=='telephone' && !mobile.test(aval))
			{
				maxalert('Please confirm the Telephone is 8-12 numbers!')
				return false;
			}
			 
			$(this).addClass('tg');
			if($('.tg').length==req.length){
				wrap.submit();
				return false;	
			}
		}
		else
		{
			if(aname=='countryIso'){				
				maxalert('Please Select Shipping country!')
				return false;
			}
			else if(aname=='regionIso'){				
				maxalert('Please Select Shipping region!')
				return false;
			}
			else
			{
				maxalert(thistext)
				return false;
			}
		}
	})	
}

</script>
