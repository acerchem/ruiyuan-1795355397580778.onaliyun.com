
//setTimeout(function(){  
//  $('.maxtop').animate({'padding-top':80});
//  $(".maxprosi-top.max-col").animate({top:0});
//},1000)
//
////点击关闭顶部广告
//$(".maxprosi-top .maxcole").click(function(){
//  $('.maxtop').animate({'padding-top':0});
//  $(".maxprosi-top.max-col").animate({top:-80});
//  $(".maxprosi-top.max-col").remove();
//});



//
var mySwipershow = new Swiper('.swiper-container2', {
      slidesPerView: 5,
      slidesPerGroup: 5,
      calculateHeight : true,
	    paginationAsRange : true,
      autoplay:50000,
      loop:true,
      pagination: '.page2 .maxpagin', 
      paginationClickable :true,
       onSwiperCreated:function(swiper){
        $('.page2 .maxpagin span').each(function(){
                var ai = $(this).index();
                $(this).text(ai)
                if(ai%5==0){
                    $(this).show();
                    $(this).text((ai/5)+1);
                }
           })
        }     
   })

$('.swiper-container2 .arrow-left').on('click', function(e){
    e.preventDefault()
    mySwipershow.swipePrev()
})
$('.swiper-container2 .arrow-right').on('click', function(e){
    e.preventDefault()
    mySwipershow.swipeNext()
})


var mySwipershow3 = new Swiper('.swiper-container3', {
      slidesPerView: 5,
      slidesPerGroup: 5,
	    paginationAsRange : true,
      calculateHeight : true,
      autoplay:50000,
      loop:true,
      pagination: '.page3 .maxpagin', 
      paginationClickable :true,
       onSwiperCreated:function(swiper){
        $('.page3 .maxpagin span').each(function(){
                var ai = $(this).index();
                $(this).text(ai)
                if(ai%5==0){
                    $(this).show();
                    $(this).text((ai/5)+1);
                }
           })
        }
         
       })

$('.swiper-container3 .arrow-left').on('click', function(e){
    e.preventDefault()
    mySwipershow3.swipePrev()
})
$('.swiper-container3 .arrow-right').on('click', function(e){
    e.preventDefault()
    mySwipershow3.swipeNext()

})

var mySwipershow4 = new Swiper('.swiper-container4', {
		pagination : '.pagination',
		loop:true,
		autoplay:300000,
    calculateHeight : true,
    })

$('.swiper-container4 .arrow-left').on('click', function(e){
    e.preventDefault();
    mySwipershow4.swipePrev();
})
$('.swiper-container4 .arrow-right').on('click', function(e){
    e.preventDefault();
    mySwipershow4.swipeNext();

})


//banner
  var mySwiper = new Swiper('.banner .swiper-container',{
    pagination: '.banner .pagination',
    loop:true,
    autoplay:3000,
    paginationClickable :true,
  })

$('.banner .pagination span').on('mouseover',function(){
  $(this).click();
})