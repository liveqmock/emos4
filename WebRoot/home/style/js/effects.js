// JavaScript Document
//自提邮寄		
$(document).ready(function(e) {		

$("a").focus(function(){
    this.blur();

});
		
//底部微信二维码
	$(".icon_weix").click(function(e) {
        $(".weix_div").slideToggle();
    });
	$(".weix_div").click(function(e) {
        $(".weix_div").slideToggle();
    });
		
//服务支持页面标签切换
    $('.help_tab > li').click(tabs);
	function tabs() {
		$(this).addClass('current').siblings().removeClass('current');
		var tabs = $(this).attr('title');
		$('#' + tabs).show().siblings().hide();
		};
//社区首页鼠标移上图片显示层
	$(".normal").hover(
	  function () {
		$(this).addClass("hover");
	  },
	  function () {
		$(this).removeClass("hover");
	  }
	);
//社区详情页点击显示国家列表
	$('#bkch').click(function(e) {
        $('.txz_country_lst').show();
    });
	$('.txz_country_lstcnt > ul > li > a').click(function(e) {
        $('.txz_country_lst').hide();
    });
	$('#pl').click(function(e) {
        $('#txz_plst').toggle()
    });
//表情层
	$('#face').click(function(e) {
        $('#face-list').slideToggle()
    });
//上传图片层
	$('#upimg').click(function(e) {
        $('#update-img').slideToggle()
    });
//删除微博层
	$('#delete_weib').click(function(e) {
        $('#delete-cnt').css('display','block');
    });
	$('.btnorag62_30').click(function(e) {
        $('#delete-cnt').css('display','none');
    });
	$('.btngrey62_30').click(function(e) {
        $('#delete-cnt').css('display','none');
    });
//收藏层
	$('#collect').click(function(e) {
        $('#collect-cnt').slideToggle()
    });
//通知页面
$(document).ready(function(e) {
    $('.weifx_tab > li').click(tab);
	function tab() {
		$(this).addClass('current').siblings().removeClass('current');
		var tab = $(this).attr('title');
		$('#' + tab).show().siblings().hide();
		};
});

});

