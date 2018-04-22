<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="showTax" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="quote" tagdir="/WEB-INF/tags/responsive/quote" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:url value="${continueUrl}" var="continueShoppingUrl" scope="session"/>

<c:url value="/cart/checkout" var="checkoutUrl" scope="session"/>

<div class="g-cell cart-right">
			<div class="cart-total">
				<!-- <div class="title">Subtotal</div>

				<div class="m-Coupon">
					<form action="" id="coupon">
						<input type="text" placeholder="Enter Coupon Code" class="code">
						<input type="submit" value="Apply" class="btn-submit">
					</form>
				</div> -->
				
				<div class="g-table">	
					<form action="">		
						<!-- <div class="Summary">
							<span>Part of your order qualifies for FREE Shipping;</span>
							<span>span.Buy 2 items  sales to 10% Off; </span>
						</div> -->
						<div class="list">
							<div class="item">
								<div class="item-row">
									<span>Total Sum (<em>${cartData.totalUnitCount}</em> items)</span>
									<span class="row"> <format:price priceData="${cartData.subTotal}"/></span>
								</div>

								<div class="item-row">
									<span>Discount Amount</span>
									
									 <%-- <c:choose>
						                <c:when test="${cartData.totalDiscounts.value > 0}"> --%>
						                    <span class="row"><format:price priceData="${cartData.totalDiscounts}"/></span>
						             <%--    </c:when>
						                <c:otherwise>
						                     <span class="row">-$0.00</span>
						                </c:otherwise>
						            </c:choose> --%>
									
								</div>
								
								<div class="item-row">
									<span>Storage Cost</span>
									
						              <span class="row"><format:price priceData="${cartData.storageCost}"/></span>
						               
								</div>
								
								<div class="item-row">
									<span>Operate Cost</span>
									
						              <span class="row"><format:price priceData="${cartData.operateCost}"/></span>
						               
								</div>

								<%-- <div class="item-row">
									<span>Order Total</span>
						               
						              <span class="row"><format:price priceData="${cartData.totalPrice}"/></span>
									
								</div> --%>
							</div>					
						</div>
						<!-- <label>
							<input type="checkbox" name="" id="setexpress">
							<span class="checkbox">I Would Like To Express Checkout</span>
						</label>
 -->						<div class="btn-set">							
							<!-- <a class="btn btn-submit" href="checkout.html">Check Out</a> -->
							<button class="btn btn-primary btn-block btn--continue-checkout js-continue-checkout-button" data-checkout-url="${checkoutUrl}">Check Out</button>
							<a class="btn btn-line" href="${continueShoppingUrl}">Continue Shopping</a>
						</div>
					</form>					
				</div>
			</div>

			<!--  -->
			<div class="g-table">
				<div class="g-title">
					<span>Express Checkout</span>
				</div>
				<div class="text">
					<p>
					Benefit form a faster Checkout By:
					</p>
					<ul>
						<li>setting a default delivery address in your account or when you checkout.</li>
						<li>setting a default Payment Details when you checkout.</li>
						<li>using a default shipping method</li>
					</ul>
				</div>				
			</div>
		</div>
		
		<!-- 
<script>
	var wrap = '.maxon_salesul .slide-wrap';
	maxon_salesul(wrap)	

	// 金额计算
	function adnum(wrap,ainp){
			var atr = $('.product-table .list tr').length,
			aptext = wrap.parents('tr').find('td').eq(1).text(),
			nowavl = parseInt(ainp.val()),
			aprval = (parseFloat(aptext.slice(1))*nowavl).toFixed(2),
			atot = wrap.parents('tr').find('td').eq(3).find('em'),
			aold = parseFloat(wrap.parents('tr').find('.old-price i').text().slice(1)),
			aunlt = wrap.parents('tr').find('.old-price i').text().slice(0,1),
			anold = wrap.parents('tr').find('td').eq(3).find('i');

			atot.text(aunlt+aprval);
			anold.text(aunlt+(aold*nowavl).toFixed(2));

		var totsum = null, totprice = null,
			aitem = $('.cart-total .item-row'),
			amount = parseFloat($('.cart-total .item-row').eq(1).find('.row').text().slice(2))
			
		for(var i = 0; i<atr; i++){
			var sum = parseInt($('.product-table .m-setnum').eq(i).find('input').val()),
				alltot = parseFloat($('.product-table .list .tot').eq(i).find('em').text().slice(1));
			
			totsum = totsum+sum;
			totprice = totprice+alltot;
		}

		if(atr==0){
			totsum=totprice=0;
		}

		aitem.first().find('em').text(totsum);
		aitem.first().find('.row').text(aunlt+totprice.toFixed(2));
		aitem.find('.total').text(aunlt+(totprice-amount).toFixed(2));	
	}

	var cnum = $('.product-table .m-setnum span'),
		cinput = $('.product-table .m-setnum input');

	cnum.on('click',function(){
		var aclass=$(this).attr('class'),
			ainp = $(this).siblings('input'),
			avl = null;
		switch(aclass){
			case 'set sub':
				avl = parseInt(ainp.val());
				if(avl<=1){
					maxalert('A minimum of one piece！');
					$(this).css('background-color','#ddd')
					break;
				}
				ainp.val(avl-1);
				break;
			case 'set add':
				avl = parseInt(ainp.val());
				cnum.css('background-color','')
				ainp.val(avl+1);
				break;	
		}
		adnum($(this),ainp)
	})

	cinput.on('blur',function(){
		adnum($(this),$(this))
	})
	
		
	
	$(".product-table .del-icon").on('click',function(){
		var aele = $(this).parents('tr');
		var mess = 'Do you delete this product ?';
		
		$('.newadd').hide();
		delele(aele,mess)
		
	})

	$(document).on('click','.delpop .btn-del',function(){
		var oneinput = $('.product-table .m-setnum input').first();
		adnum(oneinput,oneinput)
	})
	// 金额计算 END


	
</script> -->