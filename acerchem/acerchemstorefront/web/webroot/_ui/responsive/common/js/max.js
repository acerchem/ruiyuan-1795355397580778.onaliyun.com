//rem单位换算
	var currClientWidth, fontValue,originWidth;
    originWidth=375;
    __resize();

    window.addEventListener('resize', __resize, false);
    function __resize() {
        currClientWidth = document.documentElement.clientWidth;
        if (currClientWidth > 640) currClientWidth = 640;
        if (currClientWidth < 320) currClientWidth = 320;
        fontValue = ((62.5 * currClientWidth) /originWidth).toFixed(2);
        document.documentElement.style.fontSize = fontValue + '%';
    }
    
$(window).ready(function(){
  

//gotop
$('.gotop').on('click',function(){
  console.log('html body to top')
  $("body").animate({scrollTop:0},300);
  $("html").animate({scrollTop:0},300);
})

// top srarch

$(document).on({'click':function(){ $('.searchlist').hide();},'scroll':function(){ $('.searchlist').hide();}})

$('#search,.maxtop_rig .maxicon-search').on('click',function(e){
  e.preventDefault();
  $('.searchlist .lab-row').animate({'width':'100%'})
  $('.searchlist').show();
  return false;
})

$('#search .lab-row').on('focus',function(e){
  $('.pdlist').show();
  return false;
})

$('#search .btn-submit').on('click',function(){
  $('#search').submit();
})

})


