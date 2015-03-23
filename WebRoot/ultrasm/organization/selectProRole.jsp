<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0,71);
		}
		window.onload = function() 
		{
			setCenter(0,71);
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
		<div class="title_right">
		    <div class="title_left">
		      <span class="title_bg">
		        <span class="title_icon2"><eoms:lable name="sm_lb_proTree"/></span>
		      </span>
		      <span class="title_xieline">
		      </span>
		    </div>
		</div>
		<div class="add_scroll_div_x" id="center">
		
		</div>
		<div class="add_bottom">
			<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_save"/>" class="save_button"
					onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick=""/>
			<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
  	</div>
  </body>
</html>
