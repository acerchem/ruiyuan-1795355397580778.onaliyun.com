<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="address" tagdir="/WEB-INF/tags/responsive/address"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>
<%@ taglib prefix="multiCheckout" tagdir="/WEB-INF/tags/responsive/checkout/multi"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>


<spring:htmlEscape defaultHtmlEscape="true" />
<c:set var="deliveryAddress" value="${cartData.deliveryAddress}"/>
<c:set var="deliveryMode" value="${cartData.deliveryMode}"/>

<template:page pageTitle="${pageTitle}" hideHeaderLinks="true">

<nav:topBreadcrumb />
<div class="gen-content">

	<div class="g-cont">
		<div class="g-cartable">
			<!-- left -->
			<div class="g-cell">
			
			<!-- Shipping Method -->
				<div class="g-table">
					<div class="g-title">
						<span>Shipping Method</span>
					</div>
					<div class="inside-cont">
						<input type="hidden" name='shiptype' class="checkinput">
						
						
						<ul class="shiplist both">
						<%-- 
						<c:if test="${not empty deliveryMode}">
						<li class="now">
								<label>
									<div class="into int">
										<input type="radio" name="shipmethod"  checked="checked" value="${deliveryMode.code}" />
									</div>
									<div class="into">
										<p><format:fromPrice priceData="${cartData.deliveryCost}"/></p>
										<em>${deliveryMode.name}</em>
										<span>${deliveryMode.description}</span>
									</div>	
								</label>
							</li>
 							</c:if> --%>
 							
 								<c:forEach items="${deliveryMethods}" var="data" >
						
						<c:if test="${not empty data.deliveryCost}">
						<c:if test="${data.code=='DELIVERY_GROSS'||data.code=='DELIVERY_MENTION'}">
						<c:if test="${data.code==deliveryMode.code}">
						<li class="now">
								<label>
									<div class="into int">
										<input type="radio" name="shipmethod"  checked="checked" value="${data.code}"/>
									</div>
									<div class="into">
										<%-- <p><format:fromPrice priceData="${data.deliveryCost}"/></p> --%>
										<em>${data.name}</em>
										<%-- <span>${data.description}</span> --%>
									</div>	
								</label>
							</li>
							</c:if>
							<c:if test="${data.code!=deliveryMode.code}">
						<li class="now">
								<label>
									<div class="into int">
										<input type="radio" name="shipmethod"   value="${data.code}"/>
									</div>
									<div class="into">
										<%-- <p><format:fromPrice priceData="${data.deliveryCost}"/></p> --%>
										<em>${data.name}</em>
										<%-- <span>${data.description}</span> --%>
									</div>	
								</label>
							</li>
							</c:if>
 							</c:if>
							</c:if>

							</c:forEach>

						</ul>
						<!-- <div class="get-text">
							<label class="available">
								<input type="checkbox" name="available">
								<span class="checkbox">Since Mentioning</span>
							</label>	

							<span class="black">Items will ship as soon as they are available.<br/>
							see order summary for more information.</span>
						</div>	 -->		
					</div>
				</div>
				<!-- end -->
				
				
				<c:if test="${deliveryMode.code=='DELIVERY_MENTION'}">
				<div class="g-table">
					<div class="g-title">
						<span>Warehouse Address</span>
					</div>
					<div class="inside-cont">
						<input type="hidden" name='shipaddress' class="checkinput">
						<div class="get-text">
						  <c:forEach items="${deliveryAddresses}" var="deliveryAddress" varStatus="id">
						  <c:if test="${id.index==0}">
							<div class="nowadd">
							      <span>
				                    <b>${fn:escapeXml(deliveryAddress.title)}&nbsp;${fn:escapeXml(deliveryAddress.firstName)}&nbsp;${fn:escapeXml(deliveryAddress.lastName)}</b>
				                    <br/>
				                    <c:if test="${ not empty deliveryAddress.line1 }">
				                        ${fn:escapeXml(deliveryAddress.line1)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.line2 }">
				                        ${fn:escapeXml(deliveryAddress.line2)},&nbsp;
				                    </c:if>
				                    <c:if test="${not empty deliveryAddress.town }">
				                        ${fn:escapeXml(deliveryAddress.town)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.region.name }">
				                        ${fn:escapeXml(deliveryAddress.region.name)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.postalCode }">
				                        ${fn:escapeXml(deliveryAddress.postalCode)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.country.name }">
				                        ${fn:escapeXml(deliveryAddress.country.name)}
				                    </c:if>
				                    <br/>
				                    <c:if test="${ not empty deliveryAddress.phone }">
				                        ${fn:escapeXml(deliveryAddress.phone)}
				                    </c:if>
				                </span>
							</div>
							</c:if>
							</c:forEach>
						
						</div>	
						
						</div>
				</div>
				</c:if>
						
				<c:if test="${deliveryMode.code=='DELIVERY_GROSS'}">		
				<!-- Shipping Address -->
				<div class="g-table">
					<div class="g-title">
						<span>Shipping Address</span>
					</div>
					<div class="inside-cont">
						<input type="hidden" name='shipaddress' class="checkinput">
						<div class="get-text">
							
						  	  <c:if test="${not empty deliveryAddress}">
							<div class="nowadd">
							      <span>
				                    <b>${fn:escapeXml(deliveryAddress.title)}&nbsp;${fn:escapeXml(deliveryAddress.firstName)}&nbsp;${fn:escapeXml(deliveryAddress.lastName)}</b>
				                    <br/>
				                    <c:if test="${ not empty deliveryAddress.line1 }">
				                        ${fn:escapeXml(deliveryAddress.line1)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.line2 }">
				                        ${fn:escapeXml(deliveryAddress.line2)},&nbsp;
				                    </c:if>
				                    <c:if test="${not empty deliveryAddress.town }">
				                        ${fn:escapeXml(deliveryAddress.town)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.region.name }">
				                        ${fn:escapeXml(deliveryAddress.region.name)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.postalCode }">
				                        ${fn:escapeXml(deliveryAddress.postalCode)},&nbsp;
				                    </c:if>
				                    <c:if test="${ not empty deliveryAddress.country.name }">
				                        ${fn:escapeXml(deliveryAddress.country.name)}
				                    </c:if>
				                    <br/>
				                    <c:if test="${ not empty deliveryAddress.phone }">
				                        ${fn:escapeXml(deliveryAddress.phone)}
				                    </c:if>
				                </span>
							</div>
							</c:if>
							
							<div class="btn-set line-setbtn">							
								<a class="btn btn-showlist" href="javascript:void(0)">Address Book</a>
								<a class="btn btn-showform" href="javascript:void(0)">New Address</a>
							</div>
						</div>	

						<!-- new address -->
							<div class="solid-form newadd">
								<div class="title">New Address</div>
								
								
								
								<%--  <address:addressFormSelector supportedCountries="${countries}"
		                                      regions="${regions}" cancelUrl="${currentStepUrl}"
		                                      country="${country}" />
							 --%>
	
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
													<option value ="${country.isocode}">${country.name}</option>
													<c:forEach items="${supportedCountries}" var="countrys" >
													 
													<option value ="${countrys.isocode}">${countrys.name}</option>
													
													</c:forEach>
													</select>
								
												</div>	
											</div>
											<div class="flex">
												<div class="selbox ">
												
													<select name="regionIso" id="regionIso">
														<c:forEach items="${regions}" var="region" >
														<option value ="${region.isocode}">${region.name}</option>
														</c:forEach>
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
								
								
							</div>
						<!-- new end -->
						<!-- book -->
						<div class="shiplist hidlist addbook">
							<div class="title">Address Book <i class="icons close-icon"></i> </div>
							<ul>
							<c:forEach items="${deliveryAddresses}" var="da" varStatus="status">
								<li class="now">
									<label>
										<div class="into int">
										    <c:choose>
										         <c:when test="${deliveryAddress.id == da.id}">
											<input type="radio" name="addbook" id="addbook" value="${da.id}" checked="checked">
											    </c:when>
											     <c:otherwise>
											     <input type="radio" name="addbook" id="addbook" value="${da.id}" >
											     </c:otherwise>
											</c:choose>
										</div>
										<div class="into book-item">
											 <span>
							                    <b>${fn:escapeXml(da.title)}&nbsp;${fn:escapeXml(da.firstName)}&nbsp;${fn:escapeXml(da.lastName)}</b>
							                    <br/>
							                    <c:if test="${ not empty da.line1 }">
							                        ${fn:escapeXml(da.line1)},&nbsp;
							                    </c:if>
							                    <c:if test="${ not empty da.line2 }">
							                        ${fn:escapeXml(da.line2)},&nbsp;
							                    </c:if>
							                    <c:if test="${not empty da.town }">
							                        ${fn:escapeXml(da.town)},&nbsp;
							                    </c:if>
							                    <c:if test="${ not empty da.region.name }">
							                        ${fn:escapeXml(da.region.name)},&nbsp;
							                    </c:if>
							                    <c:if test="${ not empty da.postalCode }">
							                        ${fn:escapeXml(da.postalCode)},&nbsp;
							                    </c:if>
							                    <c:if test="${ not empty da.country.name }">
							                        ${fn:escapeXml(da.country.name)}
							                    </c:if>
							                    <br/>
							                    <c:if test="${ not empty da.phone }">
							                        ${fn:escapeXml(da.phone)}
							                    </c:if>
							                </span>
										</div>	
									</label>
								</li>
						</c:forEach>		

							</ul>
						</div>
						<!-- b end -->
					</div>
				</div>
				<!-- end -->
				
				</c:if>
				
				
					<!-- pickup date-->
			<div class="g-table">
					<div class="g-title">
						<c:if test="${deliveryMode.code=='DELIVERY_MENTION'}">
							<span>pickup date</span>
						</c:if>
						
						<c:if test="${deliveryMode.code=='DELIVERY_GROSS'}">
							<span>delivery date</span>
						</c:if>
					</div>
				  <div class="inside-cont">

					<!-- <p>please select the date： <input type="date" /></p> -->
						<div class="datewrap">
			 	 		<div class="date-item">
				 	 		<span class="d-titls">Date:</span>
							<div class='input-group date' id='strdate'>
							 <c:choose>
							<c:when test="${cartData.pickUpdate eq null}"> 
							    <input type='text' class="form-control2  sd"  id="textdate" /> 
							      </c:when> 
		                      <c:otherwise>
		                      <input type='text' class="form-control2" value ="${cartData.pickUpdate}" id="textdate" />
		                        </c:otherwise>
		                      </c:choose>
							    <span class="input-group-addon">
							    </span>
							</div>
							
						
						</div>
						
						<c:if test="${cartData.pickUpdate ne null}"> 
						<c:if test="${deliveryMode.code=='DELIVERY_GROSS'}">
						<div class="date-item">
				 	 		<span class="d-titls">ETA Date:</span>
							<div class='input-group date' id='etadate'>
							    <input type='text' class="form-control2"  value="${cartData.waitDeliveiedDate}"/>
							    <span class="input-group-addon">
							    </span>
							</div>
						</div>
						</c:if>
						</c:if>
						
					</div>

							<div style="height: 30px"></div>
					</div>
				</div>
				

		


					<!-- payment & Billing Address -->
				<div class="g-table"  id="payaddress">
					<div class="g-title">
						<span>payment</span>
					</div>
					
						<ul class="shiplist both">
						
							<c:if test="${not empty paymentInfos}">
							<c:forEach items="${paymentInfos}" var="data" >
							
							<li class="now">
									<label>
										<div class="into int">
											<input type="radio" name="paymentmethod"   value="${data.code}">
										</div>
										<div class="into">
											<em>${data.name}</em>
										</div>	
									</label>
								</li>
	 						
								</c:forEach>
								
							    </c:if>
							</ul>
							
				   </div>
				<!-- end -->

				<!-- Final Review -->
				<!-- <div class="g-table">
					<div class="g-title">
						<span>Invoice</span>
					</div>
					<div class="inside-cont">
						<input type="hidden" name='invoice' class="checkinput">
						<input type="hidden" name='invprice' class="checkinput">
						<div class="get-text">
							<div class="invadd">
								
							</div>
							<label class="available mar">
								<input type="checkbox" name="available" checked="checked">
								<span class="checkbox">by placing the order. i am confirming that i have read and agree with ths terns & conditions.</span>
							</label>
							<div class="btn-set line-setbtn">							
								<a class="btn btn-sign" href="javascript:void(0)">Signature</a>
								<a class="btn btn-inv" href="javascript:void(0)">Proforma Invoice</a>
							</div>
						</div>

					Signature
					<div class="solid-form newsign">
						<div class="title">Signature</div>
						<div class="form both">	
							<label>								
								<span class="label-title">Invoice type</span>	
								<div class="selbox">
									<input type="hidden" value="" name="invoicetype" alt="Please Select invoicetype">
									<span class="pitch"></span>
									<ul class="select">
										<li data-val="indi">Individual</li>
										<li data-val="corp">Corporate</li>	
									</ul>
								</div>	
							</label>

							<span class="indi invoshow">
								<label>
									<span class="label-title">Individual name</span>	
									<input type="text" name="invoname"  alt="Please Enter Individual Name" class="required">
								</label>
							</span>

							<span class="corp">
								<label>
									<span class="label-title">corporate name</span>	
									<input type="text" name="corporate"  alt="Please Enter Corporate name">
								</label>							
								<label>
									<span class="label-title">duty paragraph</span>	
									<input type="text" name="duty"  alt="Please Enter Duty Paragraph">
								</label>
							</span>							
											
						</div>	
						<div class="btn-set">				
							<a class="btn btn-line" href="javascript:void(0)">Cancel</a>
							<a class="btn btn-submit" href="javascript:void(0)">Confirm</a>
						</div>
					</div>
					new end			
					</div>
				</div> -->
				<!-- end -->

			</div>
			<!-- l end -->
		

			<!-- right -->
			<div class="g-cell gen-right">
			   <multiCheckout:checkoutOrderDetails cartData="${cartData}" showDeliveryAddress="true" showPaymentInfo="false" showTaxEstimate="false" showTax="true" />
				
			</div>
			<!-- r end -->		
		</div>
		
	</div>	
</div>


<script>
inputint()
		
$('.g-table .btn').on('click',function(){//
	var atext = $(this).attr('class');
	switch(atext){
		case 'btn btn-showlist':
			$('.hidlist , .solid-form').hide();
			$(this).parents('.g-table').find('.hidlist').show();
			break;
		case 'btn btn-showform':
			$('.hidlist , .solid-form').hide();
			$(this).parents('.g-table').find('.solid-form').show();
			break;
		case 'btn btn-newbill':
			$('.hidlist , .solid-form').hide();
			$(this).parents('.g-table').find('.newbilladd').show();
			$('.check-text.billing .available input').removeAttr('checked');
			break;
		case 'btn btn-sign':
			$('.hidlist , .solid-form').hide();
			$(this).parents('.g-table').find('.solid-form').show();
			break;
			
	}
})

$('.shiplist label').on('click',function(){//shiplist 的当前项
	var aclass = $(this).parents('.shiplist');
	var atext = $(this).find('.book-item').html();
	var ainval = $(this).find('input').val();

	$(this).parents('li').addClass('now').siblings().removeClass('now');

	if(aclass.hasClass('addbook')){
		$('.g-table .nowadd').html(atext);
	}
	else if(aclass.hasClass('cardbook')){
		$('.g-table .bankcard').html(atext);
		$('input[name="paymentval"]').val(ainval);
		inv()
	}

	checktot()

})

$('.hidlist .close-icon').on('click',function(){
	$(this).parents('.hidlist').hide();
})


function replsolid(wrap){// 重置表单
	var selbox = wrap.find('.selbox');
		wrap.hide();
		wrap.find('input').val('').removeClass('tg');

		selbox.each(function(){
			var pitch = $(this).find('.pitch'),
				firtext = pitch.siblings('.select').find('li').first().text();
				pitch.text(firtext);
				pitch.find('input').val('').removeClass('tg');
		})
}

function solidrequ(wrap,re){//必填验证
	var req = wrap.find('.required');
	
 	req.each(function(){
		var aval = $(this).val(),
			aname = $(this).attr('name'),
			thistext = $(this).attr('alt');
		
		if(aval){	
			re = 'tg'
			$(this).addClass('tg');
			if($('.tg').length==req.length){
				// return false;	
			}
			
		}else{
			re = 'none'			
			maxalert(thistext)
			return true;
		}
	})	
}

$(".solid-form .btn-line").on('click',function(){ 
		var ele = $(this).parents('.solid-form');
		replsolid(ele)
})


function formreq(wrap,parwrap,aspan,newval,ap,inputarep,addinput,inputval){//add 
	
	var check = false;
	wrap.each(function(){
		var aval = $(this).val(),
		thistext = $(this).attr('alt');

		if(aval){	
			$(this).addClass('tg');
			if($('.tg').length==wrap.length){
				/* parwrap.hide();
				aspan.html(newval);
				ap.html(inputarep);

				if(addinput){
					addinput.val(inputval);	
				}	

				replsolid(parwrap) */
				check = true;
				return false;
				 
			
			}
		}else{
			maxalert(thistext)
			//$(".newadd.solid-form .btn-submit").disabled = 'disabled';
			return false;
		}
	})
	
	 if (check){
		var formData = $("#addressAddForm");
		formData.submit();
		
	} 
	
}


function dateChange(num, date1) {
	date = new Date(date1);
	date.setDate(date.getDate()+num);
	if (date.getMonth()<9&&date.getDate()<=9){
		date = date.getFullYear() + '-0' + (date.getMonth() + 1) + '-0' + date.getDate();
	}
	else if(date.getMonth()>=9&&date.getDate()<=9){
		date = date.getFullYear() + '-' + (date.getMonth() + 1) + '-0' + date.getDate();
	}
	else if (date.getMonth()<9&&date.getDate()>9){
		date = date.getFullYear() + '0-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
	else{
		date = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
	return date;
}

$(".newadd.solid-form .btn-submit").on('click',function(){ // 新增地址
	var afrom = $(this).parents('.solid-form'),
		req = afrom.find('.required'),
		inputarep = afrom.find('.rep').val(),
		add = afrom.find('input[name=shipadd]').val(),
		acity = afrom.find('input[name=city]').siblings('.pitch').text(),
		anat = afrom.find('input[name=nation]').siblings('.pitch').text(),
		atel = afrom.find('input[name=tel]').val(),
		amob = afrom.find('input[name=mobile]').val(),
		azoc = afrom.find('input[name=zcode]').val(),
		newval = add+','+acity+','+azoc+','+anat+'<br/>'+amob+','+atel;

		var atext = $(this).parents('.g-table').find('.get-text');
		atext.html('<p></p><span></span>');	
		var aspan = atext.find('span'),
		ap = atext.find('p');
		formreq(req,afrom,aspan,newval,ap,inputarep)
})

$(".newpay.solid-form .btn-submit").on('click',function(){ // 新增支付方式
	var afrom = $(this).parents('.solid-form'),
		req = afrom.find('.required'),
		payment = afrom.find('input[name=payment]').val(),
		num = afrom.find('input[name=cardnum]').val(),
		holder = afrom.find('input[name=cardholder]').val(),
		newval = '<img src="images/'+payment+'.jpg" />',
		sapn = '<i>'+holder+'</i> <em>'+num+'</em>';

		var atext = $(this).parents('.g-table').find('.bankcard');
			atext.html('<span class="bank"><img alt=""></span><span><i></i>em></em></span>');	
		var	aimg = atext.find('.bank'),
			ap = atext.find('span').eq(1),
			ainput = $(this).parents(".inside-cont").find('input[name="paymentval"]');
			
		formreq(req,afrom,aimg,newval,ap,sapn,ainput,payment)
})

$(".newbilladd.solid-form .btn-submit").on('click',function(){ // 新增帐单地址
	var afrom = $(this).parents('.solid-form'),
		req = afrom.find('.required'),
		inputarep = afrom.find('.rep').val(),
		add = afrom.find('input[name=billshipadd]').val(),
		acity = afrom.find('input[name=billcity]').siblings('.pitch').text(),
		anat = afrom.find('input[name=billnation]').siblings('.pitch').text(),
		atel = afrom.find('input[name=billtel]').val(),
		amob = afrom.find('input[name=billmobile]').val(),
		azoc = afrom.find('input[name=billzcode]').val(),
		newval = add+','+acity+','+azoc+','+anat+'<br/>'+amob+','+atel;

		var atext = $(this).parents('.g-table').find('.billadd');
		atext.html('<p></p><span></span>');		

		var aspan = atext.find('span'),
			ap = atext.find('p');	
		formreq(req,afrom,aspan,newval,ap,inputarep)		
})

$(".newsign.solid-form .btn-submit").on('click',function(){ // 新增发票地址
	var afrom = $(this).parents('.solid-form'),
		req = afrom.find('.required'),
		pitch = afrom.find('.pitch').text(),
		newval = '';
		
		req.each(function(){
			var at = $(this).val();
			newval = at+','+newval;
		})

		var atext = $(this).parents('.g-table').find('.get-text .invadd');
			atext.html('<p></p><span></span>');	

		  var aspan = atext.find('span'),
			ap = atext.find('p'),
			input = $('input[name="invoice"]');	
		formreq(req,afrom,aspan,newval,ap,pitch,input,pitch)
		$('.btn.btn-inv').addClass('inv-active');		
})

$(".newbilladd.solid-form .btn-line").on('click',function(){
	$('.check-text.billing .available input').prop('checked',true);
})


function inv(){//发票
	var apayment = $('input[name="paymentval"]').val(),
		select = $('.newsign .select li'),
		invbtn = $('.btn.btn-inv');
	
		select.on('click',function(){
			var atext = $(this).attr('data-val');
			$('.newsign').find('input').removeClass('required');
			$('.newsign').find('.'+atext).addClass('invoshow').siblings('span').removeClass('invoshow');
			$('.newsign').find('.invoshow input').addClass('required')
		})

		if(apayment){
			invbtn.addClass('showinv');
		}

}
inv()



function openText(){
	
	//alert(1111);
	$('#light').show();
	$('#fade').show();
}

$(document).on('click','.btn-inv.inv-active',function(){//增加发票价
	var pric = null,
		invoice = $('input[name="invoice"]').val();
		switch(invoice){
			case 'Individual':
				pric = '$0.00';
				break;
			case 'Corporate':
				pric = '$10.00';
				break;
		}
		
	   $('.check-total .list .invpric').addClass('now').find('.row').text(pric);
	   checktot()
	  
})	
	

// 金额计算
function checktot(){
	var checkrow = $('.gen-content .check-total .list .item-row'),
		checkitem = $('.gen-content .check-item'),
		chprice = checkitem.find('.price'),
		deliver = parseFloat($('input[name="shipmethod"]:checked').val()),
		invpr = parseFloat(checkrow.eq(2).find('.row').text().slice(1)),
		amount = parseFloat(checkrow.eq(3).find('.row').text().slice(2)),
		newpice = newtot = 0;


	chprice.each(function(){
		var atext = parseFloat($(this).text().slice(1)),
			anum = parseInt($(this).siblings('.num').text());

		newpice = newpice+(atext*anum);
	})

	if(!invpr){
		invpr=0
	}
	
	newtot = newpice+deliver+invpr-amount;

	checkrow.eq(0).find('.row').text('$'+newpice.toFixed(2));
	checkrow.eq(1).find('.row').text('$'+deliver.toFixed(2));
	checkrow.eq(4).find('.row').text('$'+newtot.toFixed(2));
}

checktot()



$(document).ready(function() {
    $('input[type=radio][name=addbook]').change(function() {
    	
    	//alert(this.value);
    	
    	var  ib = this.value;
    	window.location.href ='<c:url value='/checkout/multi/delivery-address/select?selectedAddressCode='/>'+ib;
    });
});



$(document).ready(function() {
    $('input[type=radio][name=shipmethod]').change(function() {
    	
    	//alert(this.value);
    	
    	var  dm = this.value;
    	window.location.href ='<c:url value='/checkout/multi/delivery-method/select?delivery_method='/>'+dm;
    });
});


$(document).ready(function() {
    $('input[type=radio][name=paymentmethod]').change(function() {
    	
    	//alert(this.value);
    	
    	//var  pm = this.value;
    	//window.location.href ='<c:url value='/checkout/multi/summary/choose?selectedPaymentMethodId='/>'+pm;
    	$('#paymentCode').val(this.value);
    });
});


$(document).ready(function() {
	
	$('#countryIso').change(function() {
		
        var selectValue = $("#countryIso").val();
        
       // alert(selectValue);
       
      //  window.location.href ='<c:url value='/checkout/multi/delivery-address/region?countryIso='/>'+selectValue;
      
      
      $.get('<c:url value='/checkout/multi/delivery-address/region'/>', { countryIso: selectValue},
		  function(arr){
    	  
    	   // alert("Data Loaded: " + data);
    	   
    	   //<option value ="${region.isocode}">${region.name}</option>
    	       var con="";  
			   $.each(arr,function(i,data){  
			        con+="<option value ="+data.isocode+">"+data.name+"</option>"  
			    });
			   
			   $("#regionIso").html(con);  
			   
		  });
      
       


	})
	
	
});

/* 
$("#dateBtn").on('click',function(){
	alert(($("#textDate").val);
})
 */

 // 时间控件
$(document).ready(function() {
	
	
	var date = new Date();//没有传入值时,默认是当前日期
	date.setDate(date.getDate()+1);
	if (date.getMonth()<9&&date.getDate()<=9){
		date = date.getFullYear() + '-0' + (date.getMonth() + 1) + '-0' + date.getDate();
	}
	else if(date.getMonth()>=9&&date.getDate()<=9){
		date = date.getFullYear() + '-' + (date.getMonth() + 1) + '-0' + date.getDate();
	}
	else if (date.getMonth()<9&&date.getDate()>9){
		date = date.getFullYear() + '0-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
	else{
		date = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	}
	
	$("#textdate").val(date);//设置默认日期
	
	  var endDate = '2018-12-31';
	  
	  var isFuture = ${cartData.isUseFutureStock};
	  
	if(isFuture){
		var sdate = dateChange(${cartData.deliveryDays},date),
        edatd = endDate;
   	 		
   	 	}else {
   	 		
   	 	var sdate = date,
        edatd = dateChange(${cartData.deliveryDays},date);
   	 	}
	
     $('#strdate').datetimepicker({
    	format: 'YYYY-MM-DD',
    	defaultDate: sdate,
        minDate: sdate,
        maxDate:edatd,
    	
    }).on('dp.change',function(e){    	
    	 
         var selectDate=$("#textdate").val();
         
    	 if (new Date(date).getTime()==new Date(selectDate).getTime()){
   		 
   	     }else {
   	    	    	    	 
   	    var date = new Date();
   	 	var currDate = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
   	 	
   	 	var releaseDate = dateChange(${cartData.deliveryDays},currDate);
   	 	
   	 	/* var isFuture = ${cartData.isUseFutureStock}; */
   	 	
   	 	//var myBoolean=new Boolean(true);
   	 	
   	 	/* if(isFuture){
   	 		
   	 		if (new Date(selectDate).getTime()<new Date(releaseDate).getTime()){
   	 			alert("please select after "+${cartData.deliveryDays}+" pickup date");
   	 			
   	 			return false;
   	 		}
   	 	}else {
   	 		if (new Date(selectDate).getTime()>new Date(releaseDate).getTime()){
   	 			alert("please select within "+${cartData.deliveryDays}+" pickup date");
   	 			
   	 			return false;
   	 		}
   	 	} */
   	 	
   	 	 window.location.href ='<c:url value='/checkout/multi/delivery-address/addPickUpDate?pickUpDate='/>'+selectDate;
   	    	 
   	     } 
     
     
      
    }); 
    
  
});
 

</script>
</template:page>


