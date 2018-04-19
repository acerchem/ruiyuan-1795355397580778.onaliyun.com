inputint()

	var wrap = '.maxon_salesul .slide-wrap';
	maxon_salesul(wrap)



	// m-prodslide
	var prodslide = $('.m-prodslide'),
	minwrap = prodslide.find('.minwrap'),	
	slideparwidth = prodslide.parent().width();
	prodslide.width(slideparwidth*.5);

	/*resize*/
	$(window).resize(function(){
	  	slideparwidth = prodslide.parent().width();
	  	prodslide.width(slideparwidth*.5);
	})	

	var flipspan = '.m-prodslide .minimg span',
		flipbtn = $('.m-prodslide .flip span');
	var slidwrap = new Swiper('.slidewrap',{
        wrapperClass : 'slide-item', 
        slideClass : 'item',
        calculateHeight : true,
        resizeReInit : true,
        speed: 600,        
        pagination: '.m-prodslide .minimg', 
       // paginationClickable :true,
        onSwiperCreated:function(swiper){
        	$('.m-prodslide .slide-item li').each(function(){
		   		var aindex = $(this).index(),
		   			aimg = $(this).html(),
		   			aheight = swiper.width,
		   			minimg = $(flipspan);
		   			minimg.eq(aindex).append(aimg);
		   			$('.m-prodslide .minwrap').height(aheight);
		   			$('.m-prodslide .slide-item').height(aheight);
			})
	     }      
    })


	$(document).on('mouseover',flipspan,function(){
		var ain = $(this).index();
		slidwrap.swipeTo(ain)
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
			//spot.show();
			/* invi.text(futday+parseInt(invi.text())); */
			
			invi.text(futday);
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
				$('#qty').val(avl-1);
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
				$('#qty').val(avl+1);
				break;	
		}
		
	})

	$('.prodbase .btn-cart').on('click',function(){//加入购物车
		var srch = window.innerHeight;
		$('.maxfixed').show();
		$('body').css({'height':srch,'overflow':'hidden'});

		var maxfixed = $('.maxfixed .product-table ul'),
			thisele = $('.prodbase'),
			thisimg = $('.slidewrap .item').first().html(), 
			thistit = thisele.find('.g-title p').text(),				
			goodsnum = $('input[name="pdnum"]').val(),
			price = thisele.find('.price').text(),
			oldprice = thisele.find('.old-price').text(),
			fications = $('input[name="spec"]').val(),
			newele = '<li class="item"><div class="img">'+thisimg+'</div><div class="maxtext"><p class="in-title">'+thistit+'</p><p class="spec">Specifications:<i>'+fications+'</i></p><div class="spset"><span class="price">'+price+'</span><span class="old-price">'+oldprice+'</span><span class="num">'+goodsnum+'</span></div></div></li>';			
			maxfixed.append(newele);
			console.log(thisimg)
			//totalprice()
	})

	$(".btn-continue").on('click',function(){
		$('.maxfixed').hide();
		$('body').css({'height':'','overflow':''});
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

	$("#storeMulId").change(function(){
		 /*库存信息*/
		invi = $('.invernum .inventory i')
		invi.text($("#inventoryId option[value='"+$("#storeMulId").val()+"']").text());
		
		 /*先期库存天数*/ 
		emvi = $('.prod-sum i em')
		
		emvi.text($("#avaReleaseDayId option[value='"+$("#storeMulId").val()+"']").text());
	//	var data =$("#countryId option[value='"+$("#storeMulId").val()+"']").text();
		
        $('#storeId').val($("#storeMulId").val());
	});
    
  