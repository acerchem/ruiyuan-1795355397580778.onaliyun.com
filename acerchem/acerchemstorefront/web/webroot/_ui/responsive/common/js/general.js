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


function required(wrap){//必填验证
	var req = wrap.find('.required');
	
 	req.each(function(){
		var aval = $(this).val(),
			aname = $(this).attr('name'),
			thistext = $(this).attr('alt');
		
		if(aval){		
			var mymail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;		
			if(aname=='email'&&!mymail.test(aval)){				
				maxalert('Please enter a valid mailbox！')
				return false;
			}

			$(this).addClass('tg');
			if($('.tg').length==req.length){
				maxalert(thistext)
				wrap.submit();
				return false;	
			}
			
		}else{
			maxalert(thistext)
			return false;
		}
	})	
}

function verification(wrap){//register verification
	var req = wrap.find('.required');
 	req.each(function(){
		var aval = $(this).val(),
			aname = $(this).attr('name'),
			thistext = $(this).attr('alt');
		
		var mymail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;	
		var mobile = /^\d{0,4}[-]?\d{8,12}$/;
		
		if(aval)
		{		
			if(aname=='email'&&!mymail.test(aval)){				
				maxalert('Please enter a valid mailbox!')
				return false;
			}
			
			if(aname=='pwd' && (aval.length<6 || aval.length>16))
			{
				maxalert('Password should be between 6-16 in length!')
				return false;
			}
			
			if(aname=='pwd' && aval!=document.getElementById('checkPwd').value)//pwd.value
			{
				maxalert('Two passwords are inconsistent!')
				return false;
			}
			
			if(aname=='mobileNumber' && !mobile.test(aval))
			{
				maxalert('Please confirm the Mobile Number is 8-12 numbers!')
				return false;
			}
			
			if(aname=='telephone' && !mobile.test(aval))
			{
				maxalert('Please confirm the Telephone is 8-12 numbers!')
				return false;
			}
			 
			$(this).addClass('tg');
			if($('.tg').length==req.length){
				wrap.submit();
				return false;	
			}
		}
		else
		{
			if(aname=='shipAddress.countryIso'){				
				maxalert('Please Select Shipping country!')
				return false;
			}
			else if(aname=='shipAddress.regionIso'){				
				$(this).addClass('tg');
			}
			else if(aname=='contactAddress.countryIso'){				
				maxalert('Please Select Contact country!')
				return false;
			}
			else if(aname=='contactAddress.regionIso'){				
				$(this).addClass('tg');
			}
			else
			{
				maxalert(thistext)
				return false;
			}
		}
	})	
}

function maxalert(mess){ //pop tips
	var aele = '<div class="maxpop">'+mess+'</div>';
	if($('.maxpop').length>0){
		return false;
	}
	$('body').after(aele);
	$('.maxpop').fadeIn();

	setTimeout(function(){
		$('.maxpop').fadeOut();
		$('.maxpop').remove();
	},2000)
}


function delele(wrap,mess){//删除提示
	
	var aele = '<div class="delpop"> <div class="delcont"> <div class="title"> Tips </div> <div class="text"> '+mess+'</div><div class="btn-set"><a class="btn btn-line" href="javascript:void(0)">Cancel</a><a class="btn btn-del" href="javascript:void(0)">Confirm</a></div></div>';

	if($('.maxpop').length>0){
		return false;
	}
	$('body').after(aele);

	$('.btn').on('click',function(){
		var aval = $(this).text();
		switch(aval){
			case 'Cancel' :
				$('.delpop').remove();
				break;
			case 'Confirm' :
				wrap.remove();
				$('.delpop').remove();
				break;
		}
	})
}



function maxon_salesul(wrap){
	var salesul = new Swiper(wrap,{
        wrapperClass : 'slide-item', 
        slideClass : 'item',
        slidesPerView:5, 	 
        slidesPerGroup:5,
        calculateHeight : true,
        speed: 600,
        autoplay:3000,
        loop:true,
        pagination: '.maxpagin', 
        paginationClickable :true,
        onSwiperCreated:function(swiper){
        	$('.maxpagin span').each(function(){
		   		var ai = $(this).index();
		   		$(this).text(ai)
		   		if(ai%5==0){
		   			$(this).show();
		   			$(this).text((ai/5)+1);
		   		}
		   })
        }
    });
  
    $('.maxon_salesul .banner_btn span').on('click',function(){
    	var aclass = $(this).attr('class');
    	switch (aclass){
    		case 'arrow-left':
    			salesul.swipePrev();
    			break;
    		case 'arrow-right':
    			salesul.swipeNext();
    			break;	
    	}    	
    })    
}


