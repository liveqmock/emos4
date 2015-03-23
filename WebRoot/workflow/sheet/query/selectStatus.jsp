<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0,62);
		}
		$(window).load(function()
		{
		  setCenter(0,62);
		  changeRow_color_custom("tab",1);
		});
		function selectCheck(){
			var objs = document.getElementsByName("checkid");
			var wfString = "";
			for (var i = 0; i < objs.length; i++) {
				if (objs[i].checked == true) {
					wfString += "," + objs[i].value;
				}
			}
			wfString = wfString.substring(1);
			window.opener.document.getElementById("basestatus").value=wfString;
			window.close();
		}
		function cleanCheck(){
			window.opener.document.getElementById("basestatus").value="";
			window.close();
		}
	</script>
  </head>
  
  <body>
	<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">状态选择</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center">
				<table id='tab' class='tableborder'>
					<tr>
						<th style="width: 30px;">
						
							<input name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input>
						</th>
						<th>
							状态名称
						</th>
					</tr>
					<c:forEach var="statusFlowMapModel" items="${statusList}">
						<tr>
							<td>
								<input name="checkid" type="checkbox" value='${statusFlowMapModel.name}'></input>
							</td>
							<td>
								${statusFlowMapModel.name}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="add_bottom">
				<input type="button" value="清空"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'"
					onclick="cleanCheck();" />
				<input type="button" value="确定"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'"
					onclick="selectCheck();" />
				<input type="button" value="取消"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					 onclick="window.close()" />
			</div>
	</div>
  </body>
</html>
