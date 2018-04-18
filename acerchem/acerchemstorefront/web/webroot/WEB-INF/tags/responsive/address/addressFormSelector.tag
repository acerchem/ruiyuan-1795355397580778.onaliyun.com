<%@ attribute name="supportedCountries" required="true" type="java.util.List"%>
<%@ attribute name="regions" required="true" type="java.util.List"%>
<%@ attribute name="country" required="false" type="java.lang.String"%>
<%@ attribute name="cancelUrl" required="false" type="java.lang.String"%>
<%@ attribute name="addressBook" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<div class="form both">
<form:form id="addressAddForm" method="post" commandName="addressForm" >
								
<form:hidden path="addressId" class="add_edit_delivery_address_id"
	status="${not empty suggestedAddresses ? 'hasSuggestedAddresses' : ''}" />
<input type="hidden" name="bill_state" id="address.billstate" />

	<label>
		<span class="label-title">FIRST NAME</span>	
		<input type="text" name="firstName"  alt="Please Enter Contacts" class="required rep">
	</label>
	<label>
		<span class="label-title">LAST NAME</span>	
		<input type="text" name="lastName"  alt="Please Enter Contacts" class="required rep">
	</label>
	
	<label>
	<span class="label-title">Title</span>	
		<div class="flex-wrap">
			<div class="flex">					
				<div class="selbox">
					<input type="hidden"  value="" name="titleCode" alt="Please Select Shipping nation" class="required">
					<span class="pitch"></span>
					<ul class="select">
						<li data-val="mrs">Mrs.</li>
						<li data-val="miss">Miss</li>
						<li data-val="ms">Ms.</li>
						<li data-val="mr">Mr.</li>
						<li data-val="dr">Dr.</li>
						<li data-val="rev">Rev.</li>
					</ul>
				</div>	
			</div>
				
		</div>
	</label>
	
	<label>
		<span class="label-title">Telephone</span>	
		<input type="text" name="line1"  alt="Please Enter Telephone">
	</label>
	<label>
		<span class="label-title">Mobile Phone</span>	
		<input type="text" name="phone"  alt="Please Enter Mobile Phone" class="required">
	</label>
	<label>
		<span class="label-title">Zip Code</span>	
		<input type="text" name="postcode" alt="Please Enter Zip Code" class="required">
	</label>

	<label>
	<span class="label-title">Shipping Address</span>	
		<div class="flex-wrap">
			<div class="flex">					
				<div class="selbox">
					<input type="hidden"  value="" name="countryIso" alt="Please Select Shipping nation" class="required">
					<span class="pitch"></span>
					<ul class="select">
						<li>Nation</li>
						<li data-val="US">U.S.A</li>
						<li data-val="GB">England</li>
						<li data-val="ITA">Repubblica Italiana</li>
						<li data-val="SUI">Swiss Confederation</li>
						<li data-val="MEX">The United States of Mexico</li>
						<li data-val="FRA">French Republic</li>
					</ul>
				</div>	
			</div>
			<div class="flex">
				<div class="selbox ">
					<input type="hidden" value="" name="regionIso" alt="Please Select Shipping city" class="required">
					<span class="pitch"></span>
					<ul class="select">
						<li>City</li>
						<li data-val="US-NY">New York</li>
						<li data-val="LA">Los Angeles</li>
						<li data-val="CI">Chicago</li>
					</ul>
				</div>	
			</div>				
		</div>
	</label>

	<label>						
		<input type="text" name="townCity" class="lab-row required" placeholder="Detailed address" alt="Please Enter Shipping Detailed Address">
	</label>
	
	<!-- <div class="btn-set">
	<a class="btn btn-line" href="javascript:void(0)">Cancel</a>
		<button type="submit" class="btn btn-submit">Confirm</button>
		
		</div> -->
 </form:form>	
 </div>
 <div class="btn-set">				
	<a class="btn btn-line" href="javascript:void(0)">Cancel</a>
	<a class="btn btn-submit" href="javascript:void(0)">Confirm</a>
</div>

