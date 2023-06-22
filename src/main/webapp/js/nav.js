$(document).ready(function(){
  var snb= $('.snb ul').prev('a'); /* 운영정책, API 활용 사례 */
  var snb_nav = $('.snb li');

  snb.addClass('dep');
  snb_nav.each(function(){
    if($(this).hasClass('on')){
        $(this).children('ul').show();
    }
   });
  snb.on('click', function(){
    $(this).parent().addClass('on').children('ul').stop().slideDown(400); /* --- 1번 */
    $(this).parent().siblings('li').removeClass('on').children('ul').stop().slideUp(200); /* ---- 2번 */
    return false;
  }); 

});/**
 * 
 */