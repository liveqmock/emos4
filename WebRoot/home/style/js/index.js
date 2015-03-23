$(function() {
	//输入框

	$(".gk_inptxt").focus(function() {
		$(this).addClass("focus");
		var value = $(this).val();
		if (value == "用户名" || value == "密码") {
			$(this).val("");
		}
		$(this).css({
			"color": "#333333"
		});
	}).blur(function() {
		var value = $(this).val();
		$(this).removeClass("focus");
		if (value == "") {
			if ($(this).hasClass("gk_user")) {
				$(this).val("用户名");
			} else if ($(this).hasClass("gk_pwd")) {
				$(this).val("密码");
			}
			$(this).css({
				"color": "#b5b5b5"
			});
		} else {
			$(this).css({
				"color": "#333333"
			});
		}
		if ($(this).hasClass("gk_pwd")) {
			$(this).val(value.replace(/[a-zA-Z0-9]/g, "*"))
		}
	}).keypress(function() {
		if ($(this).hasClass("gk_pwd")) {
			var value = $(this).val();
			$(this).val(value.replace(/[a-zA-Z0-9]/g, "*"))
		}
	})
	//nav
	navScroll();
	

	
	
	function navScroll(){
		var i = 0;
		var liIndex = 0;
		var navUl = $(".nav_ul");
		var subNavUL = $(".sub_nav");
		var navLiNum = $(".nav_ul").children("li").length;
		var hideFlag = false;

		$(".nav .nav_prev").click(function(){
			if($(this).hasClass("disable")) return false;
			i--;
			navUl.animate({"left" : - 120*i + "px"},400);
			subNavUL.animate({"left" : - 120*i + "px"},400);
			$(this).siblings(".nav_next").removeClass("disable");
			if(i == 0){
				$(this).addClass("disable");
				return false;
			}
		})
		$(".nav .nav_next").click(function(){
			if($(this).hasClass("disable")) return false;
			i++;
			navUl.animate({"left" : - 119*i + "px"},400);
			subNavUL.animate({"left" : - 119*i + "px"},400);
			$(this).siblings(".nav_prev").removeClass("disable");
			if(i == (navLiNum-8)){
				$(this).addClass("disable");
				return false;
			}
		})

		$('.nav_ul').children("li").each(function(i){
			var _this = $(this);
			var thisOL = _this.offset().left;
			var thisPOL = _this.offsetParent().offset().left;
			var subLI = $(this).closest(".nav_div").next(".sub_nav").children("li:eq(" + i + ")").find(".sub_nav_ul");
			$(this).hover(function() {
				liIndex = $(this).index();
				$(this).addClass("hover");
				subLI.css({"left":(thisOL - thisPOL + 18) + "px"}).show();
			}, function() {
				$(this).removeClass("hover");
				if(!hideFlag){
					subLI.hide();
				}
				
			})
			subLI.hover(function(){
				hideFlag = true;
				$(this).show();
				$(this).closest(".sub_nav").prev().find(".nav_ul>li").eq(liIndex).addClass("hover");
			},function(){
				hideFlag = false;
				$(this).hide();
				$(this).closest(".sub_nav").prev().find(".nav_ul>li").eq(liIndex).removeClass("hover");
			})
		})

		$(".sub_nav_ul>li").hover(function() {
			$(this).addClass("sub_hover");
		}, function() {
			$(this).removeClass("sub_hover");
		})
	}


	//banner
	function banner() {
		var imgTitle = $(".img_title");
		var imgBnt = $(".img_bnt").children("span");
		var imgLi = $(".img_ul").find("li");
		var imgLength = imgLi.length;
		var index = 0;
		imgBnt.each(function(i) {
			$(this).mouseover(function() {
				$(this).addClass("sel").siblings().removeClass("sel");
				changeImg(i);
				index = i;
			})
		})
		imgLi.eq(0).show().siblings().hide();
		setInterval(function() {
			index++;
			changeImg(index);
			imgBnt.eq(index).addClass("sel").siblings().removeClass("sel");

			if (index == (imgLength - 1)) {
				index = -1;
			}
		}, 3000);

		function changeImg(index) {
			var title = imgLi.eq(index).find("img").attr("alt");
			imgLi.eq(index).fadeIn(300).siblings().fadeOut(400);
			imgTitle.text(title);
		}
	}
	banner();

	
	// viewPic
	$(".small_photos_box").Scroll({
		line: 7,
		speed: 500,
		timer: 1500,
		left: "handle_right",
		right: "handle_left"
	});
});
(function($) {
	$.fn.extend({
		Scroll: function(opt, callback) {
			//参数初始化     
			if (!opt) var opt = {};
			var _btnLeft = $("." + opt.left); //Shawphy:向上按钮     
			var _btnRight = $("." + opt.right); //Shawphy:向下按钮     
			var timerID;
			var _this = this.eq(0).find("ul:first");
			var lineH = _this.find("li:first").width(), //获取行高     
				line = opt.line ? parseInt(opt.line, 10) : parseInt(this.width() / lineH, 10), //每次滚动的行数，默认为一屏，即父容器高度     
				speed = opt.speed ? parseInt(opt.speed, 10) : 500; //卷动速度，数值越大，速度越慢（毫秒）     
			timer = opt.timer //?parseInt(opt.timer,10):3000; //滚动的时间间隔（毫秒）     
			if (line == 0) line = 1;
			var upHeight = 0 - line * (lineH + 10);
			var index = 0;//控制大图动画
			var flag = false;
			//滚动函数     
			var scrollLeft = function() {
					_btnLeft.unbind("click", scrollLeft); //Shawphy:取消向上按钮的函数绑定     
					_this.animate({
						marginLeft: upHeight
					}, speed, function() {
						for (i = 1; i <= line; i++) {
							_this.find("li:first").appendTo(_this);
						}
						_this.css({
							marginLeft: 0
						});
						_btnLeft.bind("click", scrollLeft); //Shawphy:绑定向上按钮的点击事件     
					});
				}
				//Shawphy:向右翻页函数     
			var scrollRight = function() {
					_btnRight.unbind("click", scrollRight);
					for (i = 1; i <= line; i++) {
						_this.find("li:last").show().prependTo(_this);
					}
					_this.css({
						marginLeft: upHeight
					});
					_this.animate({
						marginLeft: 0
					}, speed, function() {
						_btnRight.bind("click", scrollRight);
					});
				}
				//Shawphy:自动播放     
			var autoPlay = function() {
				if (timer) timerID = window.setInterval(function() {
					_btnLeft.unbind("click", scrollLeft); //Shawphy:取消向上按钮的函数绑定     
					_this.animate({
						marginLeft: -(lineH + 5)
					}, speed, function() {
						_this.find("li:first").appendTo(_this);
						_this.css({
							marginLeft: 5
						});
						_btnLeft.bind("click", scrollLeft); //Shawphy:绑定向上按钮的点击事件     
					});
				}, timer);
			};
			var autoStop = function() {
				if (timer) window.clearInterval(timerID);
			};
			//鼠标事件绑定     
			_this.hover(autoStop, function(){if(!flag){autoPlay();}}).mouseout();
			_btnLeft.css("cursor", "pointer").click(scrollLeft).hover(autoStop, autoPlay); //Shawphy:向上向下鼠标事件绑定     
			_btnRight.css("cursor", "pointer").click(scrollRight).hover(autoStop, autoPlay);

			_this.find("li").each(function(i) {
				var li = $(this);
				$(this).mouseover(function() {
					autoStop();
					li.addClass("sel").siblings().removeClass("sel");
				}).mouseout(function(){
					if(flag) return;
  					li.removeClass("sel");
				}).click(function() {
					flag = true;
					li.addClass("sel").siblings().removeClass("sel");
					$(".big_img_mask").height($("html").height()); //遮罩层高度
					//
					$(".big_img_ul").children("li:eq(" + i + ")").fadeIn().siblings().fadeOut();
					$(".look_page").text(i + "/"+_this.find("li").length);
					$(".big_img_info").text(li.find("img").attr("alt"));
					$(".big_img_div").show();
					index = i;
				})


			});
			var closeFlag = false;
			$(".view_img").hover(function(){closeFlag = true;},function(){closeFlag = false;})
			$(".big_btn_left").hover(function() {
				$(this).addClass("left_btn");
			}, function() {
				$(this).removeClass("left_btn");
			})
			$(".big_btn_right").hover(function() {
				$(this).addClass("right_btn");
			}, function() {
				$(this).removeClass("right_btn");
			})
			$(".big_img_close").click(function() {
				$(this).closest(".big_img_div").hide();
				flag = false;
				autoPlay();
			})
			$(".big_img_div").click(function(){
				if(closeFlag) return false;
				$(this).closest(".big_img_div").hide();
				flag = false;
				autoPlay();
			})
			$(".big_btn_left").click(function(){
				index--;
				showBigImg(index);
				if(index == 0){ index = _this.find("li").length;}
			});
			$(".big_btn_right").click(function(){
				index++;
				showBigImg(index);
				if(index == _this.find("li").length){ index = 0;}
			});
			function showBigImg(index){
				$(".big_img_ul").children("li:eq(" + index + ")").fadeIn().siblings().fadeOut();
				$(".look_page").text(index + "/"+_this.find("li").length);
				$(".big_img_info").text(_this.find("li:eq("+ index +") img").attr("alt"));
			}
		}
	})

})(jQuery);