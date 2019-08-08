<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/responsive/common/footer"  %>

<div class="title">Personal Information</div>
<div class="rigcont">
    <form:form method="post" commandName="customRegisterForm" class="both" id="personal" action="${action}">
		<label>
			<span class='label-title' style="font-weight:normal;">Email<span style="color:red;font-size: 100%;"> *</span></span>	
			<input type="text" value="${customRegisterForm.email}" disabled="disabled"/>
			<div style="color:#F00"><form:errors path="email"/></div>
		</label>
		
		<label>
			<span class='label-title' style="font-weight:normal;">Company Name<span style="color:red;font-size: 100%;"> *</span></span>	
			<input type="text" value="${customRegisterForm.companyName}" disabled="disabled"/>
			<div style="color:#F00"><form:errors path="companyName"/></div>
		</label>
		
		<label>
			<span class='label-title' style="font-weight:normal;">Your Name<span style="color:red;font-size: 100%;"> *</span></span>	
			<input id="register.name" type="text" name='name' value="${customRegisterForm.name}"  alt='Please Enter Your Name' class="required"/>
			<div style="color:#F00"><form:errors path="name"/></div>
		</label>
		
		<label>
			<div class="flex-wrap select-wrap">
				<div class="flex">
					<span class='label-title' style="font-weight:normal;">Currency<span style="color:red;font-size: 100%;"> *</span></span>	
					<div class="selbox">
						<select name="currency" id="currency">
							<c:forEach items="${currencies}" var="curr">
								<option value="${curr.isocode}" ${curr.isocode == customRegisterForm.currency ? 'selected="selected"' : ''}>
									<c:out value="${curr.symbol} ${curr.isocode} "/>
								</option>
							</c:forEach>
						</select>
						<div style="color:#F00"><form:errors path="currency"/></div>
					</div>	
					
					<%--   <footer:currencySelector currencies="${currencies}"
                                                     currentCurrency="${currentCurrency}" /> --%>
				</div>
				<div class="flex">
					<span class='label-title' style="font-weight:normal;">Language<span style="color:red;font-size: 100%;"> *</span></span>	
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
		
		<%-- <label>
			<span class='label-title' style="font-weight:normal;">Contacts</span>	
			<input id="register.contacts" type="text" name='contacts' value="${customRegisterForm.contacts}" alt='Please Enter Contacts Name' class="required"/>
			<div style="color:#F00"><form:errors path="contacts"/></div>
		</label> --%>
		
		<label>
			<div class="flex-wrap select-wrap">
				<div class="flex">		
					<span class='label-title' style="font-weight:normal;">Contact Address<span style="color:red;font-size: 100%;"> *</span></span>	
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
			<input type="text" id="register.contactAddressDetail" name='contactAddress.townCity' value="${customRegisterForm.contactAddress.townCity}" class="lab-row required" placeholder="Detailed address" alt='Please Enter Contact Detailed Address'/>
			<div style="color:#F00"><form:errors path="contactAddress.townCity"/></div>
		</label>	
		
		
		
		<label>
			<span class='label-title' style="font-size:large;">Company Information</span>	
		</label>
		<label>
			<span class='label-title'>&nbsp;</span>	
		</label>
		<label>
			<span class='label-title' style="font-weight:normal;">Type of Company</span>	
			<input id="register.companyType" type="text" name='companyType' value="${customRegisterForm.companyType}"/>
			<div style="color:#F00"><form:errors path="companyType"/></div>
		</label>
		
		<label>
			<span class='label-title' style="font-weight:normal;">Established in</span>	
			<input id="register.establishedIn" type="text" name='establishedIn' value="${customRegisterForm.establishedIn}"/>
			<div style="color:#F00"><form:errors path="establishedIn"/></div>
		</label>
		
		<label>
			<span class='label-title' style="font-weight:normal;">Revenue</span>	
			<input id="register.revenue" type="text" name='revenue' value="${customRegisterForm.revenue}" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onblur="if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}"/>
			<div style="color:#F00"><form:errors path="revenue"/></div>
		</label>
		
		<label>
			<span class='label-title' style="font-weight:normal;">No. of Employees</span>
			<input id="register.employeesNo" type="text" name='employeesNo' value="${customRegisterForm.employeesNo}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
			<div style="color:#F00"><form:errors path="employeesNo"/></div>
		</label>
		
		<label>
			<span class='label-title' style="font-weight:normal;">The credit limit you will need for your business</span>	
			<input id="register.limitCreditAmount" type="text" name='limitCreditAmount' value="${customRegisterForm.limitCreditAmount}"/>
			<div style="color:#F00"><form:errors path="limitCreditAmount"/></div>
		</label>
		
		<label>
			<span class='label-title' style="font-weight:normal;">VAT No.</span>	
			<input id="register.vatNo" type="text" name='vatNo' value="${customRegisterForm.vatNo}"/>
			<div style="color:#F00"><form:errors path="vatNo"/></div>
		</label>
		
		<label>
			<input type="checkbox" name="aidField" value="provideTradeReference" ${customRegisterForm.provideTradeReference?'checked="checked"':''}/>
			<span class="checkbox">Can you provide any trade reference for Acerchem?</span>
		</label>
		<label>
			<input type="checkbox" name="aidField" value="haveFinancialReport" ${customRegisterForm.haveFinancialReport?'checked="checked"':''} />
			<span class="checkbox">Do you have public financial report?If so, please send to customerservice@acerchem.com</span>
		</label>
		
		<input type="hidden" name='email' value="${customRegisterForm.email}"/>
		<input type="hidden" name='companyName' value="${customRegisterForm.companyName}"/>
		<input type="hidden" name="contactAddress.addressId" value="${customRegisterForm.contactAddress.addressId}" />
	</form:form>
	<div class="btn-set">	
		<button type="submit" class="btn-submit btn">Confirm</button>
	</div>
</div>

<script type="text/javascript">
$('.btn-submit').on('click',function(){
	var req = $('#personal');
	verification(req);
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

 $(document).ready(function() {
    $('#currency').change(function() {
    	
    	var currencyValue= this.value;
    			
    	 $.post('<c:url value='/_s/currency'/>', { code: currencyValue},
		  function(arr){
    	  
    	  // alert(11111);
    	   
			   
		  });
    });
}); 
</script>