/**
 * 简单的下拉菜单
 * @module menu
 * @requires JQuery 1.3
 * @creator 苗鹏<miaopeng1@ultrapower.com.cn>
 * 配置项
 * @cfg {String} [container=.menu-btn] 一级菜单容器<div>
 * @cfg {String} [menu=.menu-list] 二级菜单容器<ul>
 * @cfg {Number} [hoverTimer=100] 鼠标划过不触发的时间(ms)
 * @cfg {Function} [onClick=null] 点击菜单项后执行事件
 * @example
 * HTML:
 * 	<div id="xxx-menu">
 * 		<a class="a-btn" href="...">普通一级菜单</a>
 * 		<div class="menu-btn">
 * 			<a class="a-btn" href="/">一级菜单</a>
 * 			<ul class="menu-list">
 * 				<li><a href="...">二级菜单项</a></li>
 * 				<li><a href="...">二级菜单项</a></li>
 * 				<li><a href="...">二级菜单项</a></li>
 * 			</ul>
 * 		</div>
 * 	</div>
 * 	
 * 	JS:
 * 	最简型： $(function(){ $('#xxx-menu').menu(); })
 * 	
 * 	带配置项型：
 * 	$(function(){
 * 		$('#xxx-menu').menu({
 * 			container ： 'my container class',
 * 			onClick : function(el){
 * 				<!-- 你的代码 -->
 * 			}
 * 		});
 * 	})
 */
;(function($){
    $.fn.extend({
        menu: function(options){
            var cfg = {

                container: '.menu-btn',

                menu: '.menu-list',
                             
                hoverTimer: 100,
                                          
                onClick : null
            };
            options = options || {};
            $.extend(cfg, options);
            
            var hoverTimer = null;

            $(this).find(cfg.container).each(function(){
            	
            	var menu = $(this).find(cfg.menu);
            	
            	//一级菜单项设href="/"，不执行链接动作
            	$(this).find('a[href="/"]').click(function(){return false;});
            	$(this).find('a[href!="/"]').click(function(){
            		menu.hide();
					if(jQuery.isFunction(cfg.onClick)){
						cfg.onClick(this);					
					}				
				});
				
				//ie6下添加shim
				if (jQuery.browser.msie && parseInt(jQuery.browser.version) == 6) {
  					$(this).find(cfg.menu).append('<iframe src="javascript:false" style="border:0;position:absolute;visibility:inherit;'
        			+ 'top:0px;left:0px;width:'+menu.width()+'px; height:'+menu.height()
        			+'px;z-index:-1;filter:alpha(opacity=0);"></iframe>');
				}
				
				//mouseover显示二级菜单,mouseout隐藏二级菜单
                $(this).hover(function(){
                    	hoverTimer = setTimeout(function(){
                    		menu.show();
                    	}, cfg.hoverTimer);
                	}, 
                	function(){
                    	clearTimeout(hoverTimer);
                    	menu.hide();
                	}
                );
            })
        }
    })
})(jQuery);
