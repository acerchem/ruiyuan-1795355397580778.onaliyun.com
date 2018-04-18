
<<<<<<< Updated upstream
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
=======
if($('.maxprosi-top')){
  setTimeout(function(){  
    $('.maxprosi-top').animate({'height':80});
  },1000)

  //点击关闭顶部广告
  $(".maxprosi-top .maxcole").click(function(){
     $('.maxprosi-top').animate({'height':0});
  });
}
>>>>>>> Stashed changes

//banner
setTimeout(function(){
  var slidewiper = new Swiper('.banner .swiper-container',{
      pagination: '.banner .pagination',
      loop:true,
      autoplay:3000,
      paginationClickable :true,
  })

  $('.banner .pagination span').on('mouseover',function(){
    $(this).click();
  })
},0)
