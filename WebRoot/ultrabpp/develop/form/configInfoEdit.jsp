<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<%@ include file="/common/core/taglibs.jsp"%>
   	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
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
			init();
		}
		function formSubmit()
		{
			var configID = "${configModel.id}";
			var selectObj = document.getElementById("selectTypeList");
			var tabShow = document.getElementById("tabShowList");
			document.getElementById("configModel.selectType").value = selectObj.options[selectObj.selectedIndex].value;
			document.getElementById("configModel.tabShow").value = tabShow.options[tabShow.selectedIndex].value;
			document.getElementById('fieldForm').submit();  
		}
		function init(){
			var selectType = '${configModel.selectType}';
			var tabShow = '${configModel.tabShow}';
			//初始化类型下拉选
			var selectObj = document.getElementById("selectTypeList");
			for(var i=0;i<selectObj.options.length;i++){
				var option = selectObj.options[i];
				if(option.value==selectType){
					option.selected = true;
				}
			}
			var tabShowObj = document.getElementById("tabShowList");
			for(var i=0;i<tabShowObj.options.length;i++){
				var option = tabShowObj.options[i];
				if(option.value==tabShow){
					option.selected = true;
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
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_worksheet_additional_charactertext"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<form action="saveConfigInfo.action" name="fieldForm" method="post" id="fieldForm">
			<input type="hidden" id="configModel.id" name="configModel.id" value="${configModel.id}"/>
			<input type="hidden" id="configModel.stepNo" name="configModel.stepNo" value="${configModel.stepNo}"/>
			<input type="hidden" id="configModel.stepDesc" name="configModel.stepDesc" value="${configModel.stepDesc}"/>
			<input type="hidden" id="configModel.actionName" name="configModel.actionName"  value="${configModel.actionName}"/>
			<input type="hidden" id="configModel.actionLabel" name="configModel.actionLabel" value="${configModel.actionLabel}"/>
			<input type="hidden" id="configModel.fieldName" name="configModel.fieldName" value="${configModel.fieldName}"/>
			<input type="hidden" id="configModel.fieldLabel" name="configModel.fieldLabel" value="${configModel.fieldLabel}"/>
			<input type="hidden" id="configModel.selectType" name="configModel.selectType" value="${configModel.selectType}"/>
			<input type="hidden" id="configModel.baseSchema" name="configModel.baseSchema" value="${configModel.baseSchema}"/>
			<input type="hidden" id="configModel.tabShow" name="configModel.tabShow" value="${configModel.tabShow}"/>
			<div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									环节：
									</td>
									<td>
									${configModel.stepDesc}
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									动作：
									</td>
									<td>
									${configModel.actionLabel}
									</td>
								</tr>
								<tr>
									<td class="texttd">
									派发树：
									</td>
									<td>	
									${configModel.fieldLabel}
									</td>
								</tr>
								<tr>
									<td class="texttd">
									选择类型：
									</td>
									<td>
									<select name="selectTypeList" id="selectTypeList">
										<option value="2">部门，人员</option>
										<option value="0">部门</option>
										<option value="1">人员</option>
									</select>
									</td>
								</tr>
								<tr>
									<td class="texttd">
									显示类型：
									</td>
									<td>
									<select name="tabShowList" id="tabShowList">
										<option value="0">全部</option>
										<!-- <option value="1">只显示组织机构</option>-->
										<option value="2">只显示自定义派发数</option>
									</select>
									</td>
								</tr>
							</table>
						</div>
				</div>
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="formSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
		   </form>	
		</div>
  </body>
</html>
