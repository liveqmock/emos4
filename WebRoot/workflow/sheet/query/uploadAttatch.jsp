<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.common.core.component.data.DataRow" %>
<%@ page import="com.ultrapower.eoms.common.core.component.data.DataTable" %>
<%@ page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter" %>
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>   
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,61);
		}
		window.onload = function(){
		    setCenter(0,61);
		}
	</script>
  </head>
	<body>
		<div class="content" id="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							附件上传
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="scroll_div" id="center">
				<table class="add_user">
					<tr>
						<td class="texttd">
							附件列表：
						</td>
						<td colspan="5">
							<atta:fileupload id="BaseAttachment_tag"
						 		uploadBtnIsVisible="false"
						 		fileTypes="*" 
						 		uploadable="true"
								progressIsVisible="true" 
								uploadTableVisible="true" 
								isMultiDownload="true" 
								isAutoUpload="true" 
								downTableVisible="true" 
								isMultiUpload="true"
								attchmentGroupId="${attchmentGroupId}" 
								operationParams="0,0,1" 
								flashUrl="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf" >
					</atta:fileupload>
						</td>
					</tr>
				</table>
  			</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_close"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
		</div>
	</div>
	</body>
</html>
