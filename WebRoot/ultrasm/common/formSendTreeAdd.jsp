<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name="sm_lb_cfgSelfSendTree"/></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,60);
		}
		window.onload = function() 
		{
			setCenter(0,60);
		}
		function submit()
		{
			document.getElementById("tree").contentWindow.getDepAndUser();
			var sendTreeInfo = document.getElementById("tree").contentWindow.returnStr;
			if(sendTreeInfo!="")
			{
				var realInfo = "";
				var tempArr = sendTreeInfo.split(";");
				for(var i=0;i<tempArr.length;i++)
				{
					var temp = tempArr[i].split(",");
					realInfo += temp[0]+":"+temp[1]+";";
				}
				if(realInfo!="")
				{
					realInfo = realInfo.substring(0,realInfo.lastIndexOf(";"));
					window.dialogArguments.document.getElementById("customOrganizeInfo").value = realInfo;
					window.close();
				}
			}
		}
	</script>
  </head>
  <body>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name="sm_lb_cfgSelfSendTree"/></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	    </div>
	    <div style="overflow:hidden;" id="center" >
	    	<iframe src="${ctx}/common/tools/customOrganiseTree.jsp?isRadio=${param.isRadio}&isSelectChild=${param.isSelectChild }" scrolling="no" id='tree' frameborder="0" style="width:100%;height:100%;"></iframe>
	    </div>
	    <div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="submit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</div>	
  </body>
</html>
