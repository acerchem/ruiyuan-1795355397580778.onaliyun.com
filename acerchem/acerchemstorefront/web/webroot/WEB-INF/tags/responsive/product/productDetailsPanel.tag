<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<!-- <div class="m-crumbs">
		<div class="link">
			<a href="index.html">Home</a>
			<a href="gallery.html">Promotions </a>
			<span>Hemp Seed Protein 50% Powder (50lb Bag) by Hempco</span>
			<div class="m-starlev" date-val="2">
				<i></i>
				<em></em>
			</div>
		</div>
	</div>  -->

	<!-- top -->
	<div class="g-cont prod-cont">
			<!-- left -->
			<div class="g-cell">
				<!-- slide -->
				<div class="m-prodslide">
					<div class="minwrap">
						<div class="flip"> 
				    		<span class="up"></span> 
							<span class="down"></span>
					    </div>
						<div class="minimg">
						</div>
					</div>
					<div class="slidewrap">
						<div class="maxmark">
							<h3>10</h3>
			    			<p><em class="">%</em><em>OFF</em></p>
			    		</div>
						<ul class="slide-item">
						   <c:forEach items="${galleryImages}" var="container" varStatus="varStatus">
							<li class="item"><img src="${container.product.url}"  alt=""></li>
							
							</c:forEach>
						</ul>
					</div>						
				</div>					
				<!-- s end -->
				<!-- base -->
				<div class="prodbase">
					<div class="g-title">
						<p>${fn:escapeXml(product.name)}</p>
						<div class="min">
							<span class="mintitle">${ycommerce:sanitizeHTML(product.summary)}</span>
							<span class="sku">${fn:escapeXml(product.code)}</span>
						</div>
					</div>
					<div class="priceset">
						<span class="price"><format:fromPrice priceData="${product.price}"/></span>
						<span class="old-price"><format:fromPrice priceData="${product.price}"/></span>
					</div>
					<div class="Summary">
						<span><i>FREE</i>Buy 2 pieces for FREE Shipping;</span>
						<span><i>10% off</i>All goods Sales to 10% Off; </span>
						<span><i>40%</i>Buy 400 items  sales to 40% Off;</span>
					</div>
					<div class="specnum">
						<div class="spec">
							<label>
								<span class="label-title">Specifications</span>	
								<div class="selbox">
									<input type="hidden" class="required" value="" name="spec" alt="Please Select nation">
									<span class="pitch"></span>
									<ul class="select">
										<li data-val="25">25kg</li>
										<li data-val="50">50kg</li>
										<li data-val="75">75kg</li>						
									</ul>
								</div>	
							</label>	
							<label class="futday">
								<span class="label-title">Future days</span>	
								<div class="selbox">
									<input type="hidden" value="" name="futday" alt="Please Select nation" id="futday">
									<span class="pitch"></span>
									<ul class="select">
										<c:forEach items="${product.stock.futureStock}" var="data" varStatus="vs">
									 	
											<li data-val="${data.futureStockLevel}">${data.releaseDay}</li>
				  		 				 
										</c:forEach>
					
									</ul>
								</div>	
							</label>						
						</div>
						<div class="invernum">
							<span class="label-title inventory">Inventory:<i>${product.stock.stockLevel}</i> <span class="spot">(<em>${product.stock.stockLevel}</em>)</span></span>	

							<label>
								<input type="checkbox" name="Keep">
								<span class="checkbox">Display future inventory</span>
							</label>

							
						</div>

						<div class="prod-sum">
							<div class="m-setnum">
								<span class="set sub">-</span>
								<input type="text" name="pdnum" class="set" value="1">
								<span class="set add">+</span>
							</div>
							<i class="delintro">Delivery<em>30</em>days</i>
						</div>

						
					</div>
			
					
                    <div class="btn-set">							
							<a class="btn btn-submit" href="checkout.html">Check Out</a>
							<a class="btn btn-cart" href="javascript:void(0)">Add to Cart</a>
					</div>

				</div>
				<!-- b end -->
			</div>
			<!-- l end -->

			<!-- right -->
			<div class="g-cell product-right">
				<div class="g-table qclist">
					<div class="g-title">
							QC Documents
					</div>
					<ul class="donlist">
						<li>						
							<a href="#" >Allergen Statement</a>
						</li>
						<li>						
							<a href="#" >Allergen Statement</a>
						</li>
						<li>						
							<a href="#" >Allergen Statement</a>
						</li>
						<li>						
							<a href="#" >Allergen Statement</a>
						</li>
					</ul>
					<div class="btn-set line-setbtn">							
						<a class="btn btn-showlist" href="javascript:void(0)">Message Consultation</a>
					</div>		
				</div>
				<div class="tableshare">
					<div class="title">Share with a Friend</div>
				</div>
			</div>
			<!-- r end -->	
		</div>
		<!-- top end -->

		<div class="m-pagecard">
			<div class="card">
				<span class="now">Product Description</span>
				<span>Revews</span>
			</div>
		</div>
		<!-- down -->
		<div class="g-cont prod-cont down-cont">
			<div class="g-proudtable">
				<div class="g-cell">					
					<!-- descr -->
					<div class="m-cardwrap descr">
						<div class="m-note">
							<div class="title">Note</div>
							<div class="text">
								These statements have not been evaluated by the Food and Drug Administration. This product is not intended to diagnose, treat, cure or prevent any disease. <br/>*All product pictures are for display purposes only, it may not reflect the exact product color or mesh size, please refer to spec sheet for details.
							</div>
						</div>
						<ul class="g-desclist">
							<li>50% protein, as know, protein contributes to the growth and maintenance of muscle mass</li>
							<li>Hemp protein is considered complete plant-based protein, including all the 21 amino acids, as well as all the 8 essential amino acids</li>
							<li>Easy digestible, duo to the content of albumin and edestin</li>
							<li>High in vitamins (complex B) & minerals (Potassium, Phosphorus, Magnesium, Iron, Zinc, Copper, Manganese). High in Omega 3 fatty acids</li>
							<li>High in vitamins (complex B) & minerals (Potassium, Phosphorus, Magnesium, Iron, Zinc, Copper, Manganese). High in Omega 3 fatty acids</li>
						</ul>
						<div class="g-descblock">
							<div class="title">Specs</div>
							<ul class="g-table">								
								<li>
									<span>Founded</span>
									<span>2007</span>
								</li>
								<li>
									<span>Certifications</span>
									<span>Organic,Kosher, ISO 22000</span>
								</li>	
								<li>
									<span>Annual Export Amount</span>
									<span>EURO 4 million</span>
								</li>
								
								<li>
									<span>Major Ingredients</span>
									<span>Hulled/Shelled hemp seeds ,Hemp seed protein powder ,Cold pressed hempseed oil</span>
								</li>	
								<li>
									<span>Plant Part Used</span>
									<span> Seed</span>
								</li>	
								<li>
									<span>Processing Method</span>
									<span>Drying</span>
								</li>			
							</ul>
						</div>
						<div class="g-descblock">
							<div class="title">Additional Information</div>
							<ul class="g-table">								
								<li>
									<span>SKU</span>
									<span>97801-20KG-BAG-CANAH</span>
								</li>
								<li>
									<span>CAS</span>
									<span>N/A</span>
								</li>	
								<li>
									<span>Chemical Formula</span>
									<span>N/A</span>
								</li>	
								<li>
									<span>Solubility</span>
									<span>Insoluble in water</span>
								</li>
								<li>
									<span>Package Type</span>
									<span>110 : 20 kg Bag</span>
								</li>	
								<li>
									<span>Country Of Origin	</span>
									<span> Romania</span>
								</li>	
											
							</ul>
						</div>
					</div>
					<!-- descr end -->
					<!-- revews -->
					<div class="m-cardwrap revews">
						<ul class="m-revlist">
							<li>
								<div class="party">
									<span>C ****</span>
									<span class="m-starlev" date-val="2"><i></i><em></em></span>
								</div>
								<div class="text">I appreciate the ease of use and the convenience of the documentation available and the immediate confirmation. I will continue to use this site.</div>
								<div class="date">2018/2/9</div>
							</li>

							<li>
								<div class="party">
									<span>C ****</span>
									<span class="m-starlev" date-val="0"><i></i><em></em></span>
								</div>
								<div class="text">I appreciate the ease of use and the convenience of the documentation available and the immediate confirmation. I will continue to use this site.</div>
								<div class="date">2018/2/9</div>
							</li>

							<li>
								<div class="party">
									<span>C ****</span>
									<span class="m-starlev" date-val="5"><i></i><em></em></span>
								</div>
								<div class="text">I appreciate the ease of use and the convenience of the documentation available and the immediate confirmation. I will continue to use this site.</div>
								<div class="date">2018/2/9</div>
							</li>



						</ul>
					</div>
					<!-- revews end -->
				</div>

				<!-- right -->
				<div class="g-cell product-right">
					<div class="g-table qclist">
						<div class="g-title">
							Everyone is Buying
						</div>
						<ul class="pdlist">
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>
							<li class="both">
								<div class="img">
					    			<a href="product.html">
					    		  		<img src="" alt="">
					    		  	</a>
					    		</div>
					    		<div class="maxtext">
						    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
						    		<div class="spset">
						    			<span class="price">$68.00</span>
						    		</div>
						    	</div>
							</li>

					</ul>	
					</div>
				</div>
				<!-- r edn -->
			</div>
			<!-- item -->
	
	<!-- item end -->
		</div>
		<!-- down end -->
<script>
inputint()

var wrap = '.maxon_salesul .slide-wrap';
maxon_salesul(wrap)



// m-prodslide
var prodslide = $('.m-prodslide'),
minwrap = prodslide.find('.minwrap'),	
slideparwidth = prodslide.parent().width();
prodslide.width(slideparwidth*.5);

var flipspan = '.m-prodslide .minimg span',
	flipbtn = $('.m-prodslide .flip span');
var slidwrap = new Swiper('.slidewrap',{
    wrapperClass : 'slide-item', 
    slideClass : 'item',
    calculateHeight : true,
    speed: 600,        
    pagination: '.minimg', 
    paginationClickable :true,
    onSwiperCreated:function(swiper){
    	$('.m-prodslide .slide-item li').each(function(){
	   		var aindex = $(this).index(),
	   			aimg = $(this).html(),
	   			aheight = swiper.height,
	   			minimg = $(flipspan);
	   			minimg.eq(aindex).append(aimg);
	   			minwrap.height(aheight);
		})
     }      
})

$(document).on('mouseover',flipspan,function(){
	$(this).click();
});

flipbtn.on('click',function(){
	var aclass=$(this).attr('class'),
		fwrap = $('.m-prodslide .minimg'),
		atop = parseInt(fwrap.css('top')),
		ahi = parseInt(fwrap.find('span').css('height')),
		maxtop = parseInt(prodslide.find('.slidewrap .item').height())-fwrap.height(),
		topval = 0;

	switch(aclass){
		case 'up':
			topval=atop-ahi;
			if(topval<maxtop){
				topval=atop;
				$(this).addClass('over');
			}
			$(this).siblings().removeClass('over')	
			break;
		case 'down':
			if(atop==0){
				topval=0;
				$(this).addClass('over');
			}else{
				topval=atop+ahi;
			}
			$(this).siblings().removeClass('over')	
			break;
	}
	fwrap.animate({top:topval},300)
})
// m-prodslide end
var shareele = $('.footermin .share-buttons');
$('.product-right .tableshare').append(shareele.clone());

// m-starlev
function starlev(){
	var star = $('.m-starlev');
	star.each(function(){
		var lv = (parseFloat($(this).attr('date-val'))/5)*100,
		aem = $(this).find('em');
		aem.css('width',lv+'%');
	})
	
}
starlev()

// m-pagecard
$('.m-pagecard span').on('click',function(){
	var aindex= $(this).index(),
		card = $('.m-cardwrap');
		$(this).addClass('now').siblings().removeClass('now');
		card.eq(aindex).show().siblings('.m-cardwrap').hide()
})

$('.invernum label span').on('click',function(){
	var acked = $(this).siblings('input').prop('checked'),
		days = $('.spec .futday'),
		invi = $('.invernum .inventory i'),
		spot = $('.invernum .inventory .spot'),
		futday = parseInt($('#futday').val()),
		setnum = $('.m-setnum input');
	if(acked){
		days.hide();
		spot.hide();
		invi.text(spot.find('em').text());
		if(parseInt(setnum.val())>parseInt(spot.find('em').text())){
			setnum.val(spot.find('em').text())
		}
		
	}else{
		days.show();
		spot.show();
		invi.text(futday+parseInt(invi.text()));
	}
	
})	

$('.futday .select li').on('click',function(){
	var aval = parseInt($(this).attr('data-val')),
		invi = $('.invernum .inventory i'),
		invem = $('.invernum .inventory em'),
		inval = parseInt(invem.text());
		invi.text(aval+inval);
})

//m-setnum
$('.m-setnum span').on('click',function(){
	var aclass=$(this).attr('class'),
		ainp = $(this).siblings('input'),
		maxnum =parseInt($('.invernum .inventory i').text()),
		delin = $('.specnum .prod-sum .delintro'),
		avl = null;
	switch(aclass){
		case 'set sub':
			avl = parseInt(ainp.val());
			if(avl<=1){
				maxalert('A minimum of one piece！');
				$(this).css('background-color','#ddd')
				break;
			}else{
				if(avl<30){
					delin.find('em').text(30)
				}else if(avl>30&&avl<60){
					delin.find('em').text(60)
				}else if(avl>60){
					delin.find('em').text(90)
				}
			}
			ainp.val(avl-1);
			break;
		case 'set add':
			avl = parseInt(ainp.val());
			$(this).css('background-color','');
			$(this).siblings('span').css('background-color','');

			if(avl>=maxnum){
				maxalert("It's already the largest inventory.");
				break;
			}else{
				if(avl<30){
					delin.find('em').text(30)
				}else if(avl>30&&avl<60){
					delin.find('em').text(60)
				}else if(avl>60){
					delin.find('em').text(90)
				}					
			}
			ainp.val(avl+1);
			break;	
	}
	
})

$('.prodbase .btn-cart').on('click',function(){
	maxalert('Join the shopping cart successfully！');
})

// m-message
$(document).on('click','.product-right .btn-showlist',function(){
	var srch = window.innerHeight;
	$('body').css({'height':srch,'overflow':'hidden'});
	$('.m-message').show();
})

$('.m-message .btn-set .btn').on('click',function(){
	var aclass= $(this).attr('class'),
		selbox = $(this).parents('.mes-table').find('.selbox');
	switch(aclass){
		case 'btn btn-line':
			$('#message')[0].reset();
			$('.m-message').hide();
			break;
		case 'btn btn-submit':
			$('#message').submit();
			break;					
	}
	$('body').css({'height':'','overflow':''});
})




</script>
