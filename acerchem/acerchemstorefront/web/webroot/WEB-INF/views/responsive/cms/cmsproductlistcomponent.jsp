<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- shaun -->
<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/responsive/nav/pagination" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<!-- shaun -->
<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>
<c:set var="showCurrPage" value="${not empty showCurrentPageInfo ? showCurrentPageInfo : false}"/>
<c:set var="hideRefBtn" value="${hideRefineButton ? true : false}"/>
<c:set var="showTotals" value="${empty showTopTotals ? true : showTopTotals}"/>


<spring:htmlEscape defaultHtmlEscape="true" />

<%-- <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
 --%>
<%-- <div class="product__listing product__list">
    <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
        <product:productListerItem product="${product}"/>
    </c:forEach>
</div> --%>

<!-- shaun -->
 <div class="gen-content gal-centent">

	 <div class="m-term-bor">
		<div class="m-term">
					<!-- shaun:Selected conditions -->
					<nav:facetNavAppliedFilters pageData="${searchPageData}"/>
				
				<div class="m-term-ri" >
					
					<div class="m-pagination right">
								<span class="code">
								<pagination:pageSelectionPagination searchUrl="${searchPageData.currentQuery.url}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
								</span>
					</div>
					
						<div class="right">
							<span class="label-title">Sort by</span>
								<div class="selbox">
									<form id="sortForm${top ? '1' : '2'}" name="sortForm${top ? '1' : '2'}" method="get" action="#">
									     <select id="sortOptions${top ? '1' : '2'}" name="sort" class="form-control" style="background-color:#096735;">
									         <option disabled><spring:theme code="${themeMsgKey}.sortTitle"/></option>
									         <c:forEach items="${searchPageData.sorts}" var="sort">
									             <option value="${fn:escapeXml(sort.code)}" ${sort.selected? 'selected="selected"' : ''}>
									                 <c:choose>
									                     <c:when test="${not empty sort.name}">
									                         ${fn:escapeXml(sort.name)}
									                     </c:when>
									                     <c:otherwise>
									                         <spring:theme code="${themeMsgKey}.sort.${sort.code}"/>
									                     </c:otherwise>
									                 </c:choose>
									             </option>
									         </c:forEach>
									     </select>
									     <c:catch var="errorException">
									         <spring:eval expression="searchPageData.currentQuery.query"
									                      var="dummyVar"/>
									         <input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
									     </c:catch>
									 </form>
								</div>
						</div>
						
				</div>
		</div>
	</div>
	
	
	<div class="g-cont">
		<div class="g-cartable">
			<!-- right -->
			<div class="g-cell gall-right">
				<!-- item -->
				<div class="maxon_salesul">
				<div class="slide-wrap">
					<!--shaun: product & cart -->
					    <nav:searchSpellingSuggestion spellingSuggestion="${searchPageData.spellingSuggestion}" />
							   <ul class="slide-item both">
							            <c:forEach items="${searchPageData.results}" var="product">
							               <product:productListerItem product="${product}"/>   
								    	</c:forEach>
							    </ul> 
			    </div>
				</div>
				<!-- item end -->
						
				<pagination:pageSelectionPagination searchUrl="${searchPageData.currentQuery.url}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>

			</div>
		<!-- r end -->
				
				<!-- æç´¢åå®¹ä¸ºç©ºæ¶æ¾ç¤º -->
				<div class="g-cell gall-right" style="display: none;">
					<div class="maxon_salesul gall-right-no">
						<h4>Sorry ~ Not found and found ' goods '</h4>
						<h5> Donât See What Youâre Looking For?</h5>
						<p>Are you looking to save big on a raw ingredient thatâs not currently listed here?  We are the best at sourcing new types of materials used as raw ingredients for dietary supplements, animal nutrition, food & beverages, as well as personal care products for your skin, weight loss, hair growth and more. So let us do the searching for you by visiting our  
							<span>Sourcing New Ingredients</span>  
						section and let us know what youâre looking for while you take advantage of the 
							<span>factory-direct savings</span>.
						</p>
					</div>
				</div>
				<!-- æç´¢åå®¹ä¸ºç©ºæ¶æ¾ç¤º  end-->
		</div>
	</div>
	
</div>

<!--å¼¹åºæ¡-->
<div class="g-cartable maxfixed maxfixed-bg">
	<div class="indwrap">
		<div class="cart-total">
			<div class="title">Shopping Cart</div>
			<form action="">
				<div class="maxfixed-over  product-table">
					<ul>
						
					</ul>
				</div>
				<!-- å¤æ­ä¼æ  -->
				<div class="Summary">
					<span>Part of your order qualifies for FREE Shipping. </span>
				</div>
				<!-- å¤æ­ä¼æ  end-->
				<div class="list">
					<div class="item-row">
						<span>Total (<em>4</em> items)</span>
						<span class="row total">$0.00</span>
					</div>			
				</div>
				<div class="btn-set">							
					<a class="btn btn-submit" href="checkout.html">Check Out</a>
					<a class="btn btn-continue" href="#">Continue Shopping</a>
				</div>
			</form>
		</div>
	</div>
</div>
<!--å¼¹åºæ¡ end-->


<script>	

	inputint()	
	var wrap = '.maxon_prom .slide-wrap';
	maxon_salesul(wrap)

	//å¼¹æ¡æ¾ç¤ºé
	$(".btn-continue").on('click',function(){
		$('.maxfixed').hide();
		$('body').css({'height':'','overflow':''});
	})
	//å¼¹çªæ¾ç¤ºéèend
	
	
	 $(".maxfixed-over").on('click','.maxdel',function(){
		var aele = $(this).parents('li');
		var mess = 'Whether to delete this address ?';
		$('.newadd').hide();
		var adleele = '<div class="delpop"> <div class="delcont"> <div class="title"> Tips </div> <div class="text"> '+mess+'</div><div class="btn-set"><a class="btn btn-line" href="javascript:void(0)">Cancel</a><a class="btn btn-del" href="javascript:void(0)">Confirm</a></div></div>';

			if($('.maxpop').length>0){
				return false;
			}
			$('body').after(adleele);

			$('.btn').on('click',function(){
				var aval = $(this).text();
				switch(aval){
					case 'Cancel' :
						$('.delpop').remove();
						break;
					case 'Confirm' :
						aele.remove();
						totalprice()
						$('.delpop').remove();
						break;
				}
			})
		
	})

	// éé¢è®¡ç®
	function totalprice(){
		var fixedli = $('.maxfixed .product-table li'),
			atot = $('.maxfixed .total'),
			aem = $('.maxfixed .list em'),
			totprice = totnum = 0;
		fixedli.each(function(){
			var aprice = parseFloat($(this).find('.price').text().slice(1)),	
				anum = parseInt($(this).find('.m-setnum input').val());
			totnum = totnum+anum;
			totprice = totprice+(aprice*anum);
		})
		atot.text(totprice.toFixed(1));
		aem.text(totnum);
		console.log('totalprice')
	}

	$(document).on('click','.maxfixed .m-setnum span',function(){
		 var aclass = $(this).attr('class'),
		 	 ainp = $(this).siblings('input'),
		 	 ainval = parseInt(ainp.val());

		 switch(aclass){
		 	case 'set sub':
		 		if(ainval<=1){
					maxalert('A minimum of one pieceï¼');
					$(this).css('background-color','#ddd')
					break;
				}
		 		ainp.val(ainval-1)
		 		break;
		 	case 'set add':
		 		$('.maxfixed .m-setnum span').css('background-color','')
		 		ainp.val(ainval+1)
		 		break;
		 }
		 totalprice()
	})


		
	// éé¢è®¡ç® END


	 function screen(){// èåç­é
		var adl = $('.g-cartable .gall-left dl');
		adl.each(function(){
			$('.m-term-le').append('<li>')
		})

		adl.on('click','dd',function(){
			var aindex = $(this).parents('dl').index()-1;
			var add = $(this).text();
			$(this).addClass('act').siblings('dd').removeClass('act')
			$('.m-term-le li').eq(aindex).html(add+'<i>')
		})

		$('.m-term-le').on('click','li i',function(){
			$(this).parents('li').html('')
		})

	} 
	screen()	
</script>	

<storepickup:pickupStorePopup/>