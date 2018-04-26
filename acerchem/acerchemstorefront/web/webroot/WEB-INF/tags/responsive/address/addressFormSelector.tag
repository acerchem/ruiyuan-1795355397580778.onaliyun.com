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
				
					<select name="titleCode" id="tileId">
					<option value ="mrs">Mrs.</option>
					<option value ="miss">Miss</option>
					<option value ="ms">Ms.</option>
					<option value ="mr">Mr.</option>
					<option value ="dr">Dr.</option>
					<option value ="rev">Rev.</option>
					</select>
					
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
				
					<select name="countryIso" id="countryIso">
					<option value ="US">U.S.A</option>
					<option value ="GB">England</option>
					<option value ="ITA">Repubblica Italiana</option>
					<option value ="SUI">Swiss Confederation</option>
					<option value ="MEX">The United States of Mexico</option>
					<option value ="FRA">French Republic</option>
					</select>

				</div>	
			</div>
			<div class="flex">
				<div class="selbox ">
				
					<select name="regionIso" id="regionIso">
					<option value ="US-NY">New York</option>
					<option value ="LA">Los Angeles</option>
					<option value ="CI">Chicago</option>
					</select>

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

