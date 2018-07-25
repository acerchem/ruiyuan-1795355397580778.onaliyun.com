//var home="https://acerchem.local:9002";
var home = "https://acerchem.ibreakingpoint.com:9002"
var homeUrl=home+"/acerchemwebservices/v2/acerchem";
var tokenUrl=home+"/authorizationserver/oauth/token";

$(document).ready(function(){
/**
 * REM
 */
! function () {
        function rem() {
            var width = document.documentElement.clientWidth || document.body.clientWidth;
            document.documentElement.style.fontSize = width / 7.5 + 'px';
            //设置body字体大小，不影响body内字体大小
            document.body.style.fontSize = '12px';
        }
        window.onload = function() {rem();}
        window.addEventListener('resize', rem, false);
    }();
    
    $(function () {
	    var u = navigator.userAgent, app = navigator.appVersion;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //g
	    var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	    if (isAndroid) {
//	        alert("安卓机！")
		$("body").addClass('android')
	    }
	    if (isIOS) {
//	    alert("苹果果机！")
		$("body").addClass('ios')
	    }
	}); 

})

//banner
$(document).ready(function(){

var mySwiper = new Swiper('.slider-pic',{
      pagination: '.pagination',
      paginationClickable :true,
      loop:true,
      autoplay:3000,
      speed:600,
      calculateHeight : true,
      useCSS3Transforms : false   
})
$('.pagination span').on('mouseover',function(){$(this).click();})
$('.slider-pic .swiper-slide').height();
   
$('.dom .prev').on('click', function(e){
    e.preventDefault()
    mySwiper.swipePrev()
})
$('.dom .next').on('click', function(e){
    e.preventDefault()
    mySwiper.swipeNext()
})

});


//菜单按钮
$(document).ready(function(){
    $(".header .menu-nav").click(function () {
      var mask = $(".header .maxmenu-overlay");
      var ahei = $(window).height()-$('.g-main').offset().top;
      $(this).toggleClass('active');
      $(".menwrap").toggle();
      if($(this).hasClass('active')) {
        mask.show();
        $('.menu .glyphicon').addClass('clear');
        $('.g-main').css({'height':ahei,'overflow':'hidden','display':'block'})
      } else {
        mask.hide()
        $('.menu .glyphicon').removeClass('clear');
        $('.g-main').css({'height':'','overflow':'','display':'block'});
      }

    });
	$('.header .menwrap .bort-bot').on('click',function(){

		if($(this).hasClass('act')){
			$('.header .menwrap .bort-bot').removeClass('act');
		}else{
			 $(this).addClass('act').siblings('li').removeClass('act');
		}
	 
	})

    
    $(".header .search").click(function(){
			if($(".header .search").hasClass("active")){
				$(".header .search").removeClass('active');
				$(".search_head").hide();
				
			}else{
      	$(".header .search").addClass('active').siblings();
		$(".search_head").show();
      }
	})
   
  //product  
	var pSlide = new Swiper('.product-slide',{
       pagination: '.pagination',
       paginationClickable :true,
	    //loop:true,
	    autoplay:3000,
	    speed:600,
	    calculateHeight : true,
	    useCSS3Transforms : false   
  	})
  	$('.pagination span').on('mouseover',function(){$(this).click();})
    
	//切换
	$(".tabs a").on("click",function(e){
	    e.preventDefault()
	    var ain = $(this).index();
	    var tab = $(this).index();
	    $(".swiper-tab .m-contain").eq(ain).show().siblings().hide();
	    $(".m-product .tabs a").eq(tab).addClass('active').siblings().removeClass('active');
	})
	
	$(".tabs a").click(function(e){
	    e.preventDefault()
	})

//商品详情滑动列表

			var mySwiper = new Swiper('.sw-everyone',{
			slidesPerView : 2.5,
//			offsetSlidesAfter : 2,
			calculateHeight : true
			})
			var mySwiper = new Swiper('.sw-everytwo',{
			slidesPerView : 2.5,
//			offsetSlidesAfter : 2,
			calculateHeight : true,
			})
			
			//留言弹窗
			$(".pro-nav .news").click(function(){
	    $('.pro-navnews').show();
			})
			$(".pro-over").click(function(){
	    $('.pro-navnews').hide();
			})
			
			
			//下载弹窗
			$(".g-documents .maxrig").click(function(){
	    $('.g-document-popup').show();
			})
			$(".g-popupcon .right").click(function(){
	    $('.g-document-popup').hide();
			})
			
			
			//分享弹窗
			$(".header-top .right").click(function(){
	    $('.g-share-popup').show();
			})
			$(".g-popupcon .right").click(function(){
	    $('.g-share-popup').hide();
			})
			
			//规格弹窗
			$(".g-specs .right").click(function(){
	    $('.g-specs-popup').show();
			})
			$(".g-specs-popup .title .right").click(function(){
	    $('.g-specs-popup').hide();
			})
			
		//添加购物车弹窗
		function mincarttot(){
			var apop  = $('.g-cart-popup'),
				ali = apop.find('li'),
				total = $('.total-price.bort-top'),
				alltoto = 0;
				ali.each(function(ev){
					var apric = $(this).find('.price span').text(),
						aprnum = parseFloat(apric.slice(1));
						alltoto = aprnum+alltoto;
						total.find('i').text(ev+1)
				})
				alltoto = '$'+alltoto.toFixed(2);
				total.find('em').text(alltoto)
		}
		$(".gall-cat").click(function(){
			var aimg = $(this).parents('.maximg').find('a'),
				ahref  = $(this).parents('.maximg').find('a').attr('href'),
				atitle = $(this).parents('li').find('.text p').text(),
				apric = $(this).parents('li').find('.text span').text(),
				avol = '50kg';
		
			var apop  = $('.g-cart-popup'),
				ali = apop.find('ul'),
				newli = '<li> <a href = "'+ahref+'"><div class="maximg">'+aimg.html()+'</div><div class="text"><p>'+atitle+'</p><span class="prompt">Volume:'+avol+'</span><div class="price"><span>'+apric+'</span><em>x1</em></div></div></a></li>'
				ali.append(newli)
				apop.show();
				mincarttot()
		})
		$(".g-cart-popup .right").click(function(){
		    $('.g-cart-popup').hide();
		})

			
			//规格选择size
			$('.g-specs-popup .specs-size li').on('click',function(){
			  $(this).addClass('act').siblings('li').removeClass('act')
			})
			//规格
			$(".pro-radio").click(function(){
				if($(".specs-display").hasClass("active")){
					$(".specs-display").removeClass('active');
				}else{
	      		$(".specs-display").addClass('active').siblings();
	      		}
			})
		
			

 
		  //递增递减   

		  function sponeach(){
		  	var aitem = $('.specs-con .bort-bot'),
		  		protot = $('.cart-nav .price'),
		  		atot = null;

		  	aitem.each(function(){
  	 			var aper = $(this).find('.price b').text(),
  	 				numaper = parseFloat(aper.slice(1)),
  	 				abs = $(this).find('.pt-num input').val()
  	 				atot = numaper*abs+atot;
  	 		})
  	 		protot.text('$'+atot.toFixed(2))
		  }

		$(document).ready(function(){  
		      $('.specs-con .price .pt-num button').on('click',function(){
		      	 	var aclass = $(this).attr('class'),
		      	 		sibinput = $(this).siblings('input'),
		      	 		aval = parseInt(sibinput.val())

		      	 		switch(aclass){
		      	 			case 'maxadd1':
		      	 				aval++;
		      	 				break;
		      	 			case 'maxredu':
		      	 				if(aval<=1){aval==1}else{aval--;}
		      	 				break;	
		      	 		}
		      	 		sibinput.val(aval)
		      	 		sponeach();
		      	 		
		      })   
		});
		
//		列表页
	    $(".g-gallery .g-menu-nav").click(function () {
      var mask = $(".g-gallery .maxmenu-overlay");
      var ahei = $(window).height()-$('.g-main').offset().top;
      $(this).toggleClass('active');
      $(".g-gallery .g-menwrap").toggle();
	    $('.maxmenu-overlay-g').show();
    });
			$(".gall-but .gall-cel").click(function(){
	    $('.g-gallery .g-menwrap').hide();
	    $('.maxmenu-overlay-g').hide();
			})
			
			
	$('.gall-irm .gallery-art li').on('click',function(){
		var aeme = $(this).parents('li').find('.title em'),
			atext = $(this).text();
	  $(this).addClass('act').siblings('li').removeClass('act')
	  aeme.text(atext);
	})
	
	$('.g-popupcon .it-invoup').on('click',function(){
	  $(this).addClass('act').siblings('li').removeClass('act')
	})
	
	$(".it-invoice .con").click(function(){
				if($(this).hasClass("active")){
					$(this).removeClass('active');
				}else{
	      	$(this).addClass('active').siblings();
	      }
	})
	
	
	$(".m-gallery .gall-price,.g-gallery .gall-price").click(function(){
				if($(this).hasClass("on")){
					$(this).removeClass('on');
				}else{
	      	$(this).addClass('on').siblings();
	      }
	})
	
	$(".it-shipp").click(function(){
				if($(this).hasClass("active")){
					$(this).removeClass('active');
				}else{
	      	$(this).addClass('active').siblings();
	      }
	})
		
		



 });



$(document).ready(function(){
	
	//发票弹窗
	$(".invoup-popup").click(function(){
	    $('.g-Invoice-popup').show();
	})
	$(".g-popupcon .title .right").click(function(){
	    $('.g-Invoice-popup').hide();
	})
	
	//Billing弹窗
	$(".Billing-popup").click(function(){
	    $('.g-Billing-popup').show();
	})
	$(".g-popupcon .title .right").click(function(){
	    $('.g-Billing-popup').hide();
	})
	
	//shipping弹窗
	$(".shipping-popup").click(function(){
	    $('.g-shipping-popup').show();
	})
	$(".g-popupcon .title .right").click(function(){
	    $('.g-shipping-popup').hide();
	})
	
	//shipping弹窗
	$(".payment-popup").click(function(){
	    $('.g-payment-popup').show();
	})
	$(".g-popupcon .title .right").click(function(){
	    $('.g-payment-popup').hide();
	})
	
	//address弹窗
	$(".addres-popup").click(function(){
	    $('.g-addres-popup').show();
	    
	    
	    
	})
	$(".g-popupcon .title .right").click(function(){
	    $('.g-addres-popup').hide();
	})
	
	//edit address弹窗
	$(".editaddres-popup").click(function(){
	    $('.g-editaddres-popup').show();
	})
	$(".g-popupcon .title .right").click(function(){
	    $('.g-editaddres-popup').hide();
	})
	
	//首页登陆弹窗
	$(".login-popup").click(function(){
	    $('.index-login').show();
	})
	$(".index-login .pro-over").click(function(){
	    $('.index-login').hide();
	})
	
	
	//切换
	$(".table a").on("click",function(e){
	    e.preventDefault()
	    var ain = $(this).index();
	    var tab = $(this).index();
	    $(".swiper-tab .m-contain").eq(ain).show().siblings().hide();
	    $(".g-popupcon .table a").eq(tab).addClass('active').siblings().removeClass('active');
	})
	
	$(".table a").click(function(e){
	    e.preventDefault()
	})
	
	$(".g-addres-popup .table a").on("click",function(e){
	    e.preventDefault()
	    var ain = $(this).index();
	    var tab = $(this).index();
	    $(".g-addres-popup .m-contain").eq(ain).show().siblings().hide();
	    $(".g-addres-popup .table a").eq(tab).addClass('active').siblings().removeClass('active');
	})
	
	$(".g-addres-popup .table a").click(function(e){
	    e.preventDefault()
	})
	
	
	//规格
	$(".it-invoup .right").click(function(){
			if($(this).hasClass("active")){
				$(this).removeClass('active');
			}else{
      	$(this).addClass('active').siblings();
      }
		
	})
	
	
	$('.shiplist li').on('click',function(){
	  $(this).addClass('now').siblings('li').removeClass('now')
	})
	
			
})



var unSelected = "#777";
var selected = "#222";
$(function () {
$("form select").css("color", unSelected);
$("form option").css("color", selected);
$("form select").change(function () {
var selItem = $(this).val();
if (selItem == $(this).find('option:first').val()) {
$(this).css("color", unSelected);
} else {
$(this).css("color", selected);
}
});
})
//selelc 选择后显示灰色



function inputint(){//初始化

	$('.pitch').on('click',function(){
		$(this).siblings('.select').toggle();
		$(this).toggleClass('rotary-flag');
		$(this).parents('label').addClass('overstory');
	})

	$('.selbox').on('mouseleave',function(){		
		$(this).find('.select').hide();
		$(this).parents('label').removeClass('overstory');
		$(this).find('.pitch').removeClass('rotary-flag');
	})	


	$('.selbox').each(function(){
		var ainp = $(this).find('input'),
			dufval = $(this).find('.select li').first(),
			pitch = $(this).find('.pitch');
			if(ainp.val()==''){
				pitch.text(dufval.text());
				ainp.val(dufval.attr('data-val'));
			}else{
				return false;
			}
	})

	$('.select li').on('click',function(){
			var athisval = $(this).attr('data-val'),
				pitch = $(this).parents('.selbox').find('.pitch'),
				inpt = $(this).parents('.selbox').find('input');

			pitch.text($(this).text());				
			pitch.removeClass('rotary-flag');
			inpt.val(athisval);
			$(this).parents('.select').hide();
	})	



}

