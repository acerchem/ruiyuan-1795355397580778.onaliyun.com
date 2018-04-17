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
						<c:forEach items="${deliveryMethods}" var="data" >
						
						<c:if test="${not empty data.deliveryCost}">
						<c:if test="${data.code=='DELIVERY_GROSS'||data.code=='DELIVERY_MENTION'}">
						<li class="now">
								<label>
									<div class="into int">
										<input type="radio" name="shipmethod"   value="${data.code}"/>
									</div>
									<div class="into">
										<p><format:fromPrice priceData="${data.deliveryCost}"/></p>
										<em>${data.name}</em>
										<span>${data.description}</span>
									</div>	
								</label>
							</li>
 							</c:if>
							</c:if>

							</c:forEach>
						</ul>
						<div class="get-text">
							<label class="available">
								<input type="checkbox" name="available">
								<span class="checkbox">Since Mentioning</span>
							</label>	

							<span class="black">Items will ship as soon as they are available.<br/>
							see order summary for more information.</span>
						</div>			
					</div>
				</div>
				<!-- end -->
				
				

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


/* var radio=$('input[name="shipmethod"]');

radio.change(function(){
	
	//alert(this.value);
	
	alert(this.value);

}); */


$(document).ready(function() {
    $('input[type=radio][name=shipmethod]').change(function() {
    	
    	//alert(this.value);
    	
    	var  dm = this.value;
    	window.location.href ='<c:url value='/checkout/multi/delivery-method/select?delivery_method='/>'+dm;
    });
});


</script>
</template:page>


