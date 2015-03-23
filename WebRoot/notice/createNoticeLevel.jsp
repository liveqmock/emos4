<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name='sm_t_roleOrg' />
		</title>
		<link rel="stylesheet" type="text/css"
			href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css" />
		<script type="text/javascript"
			src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
		<script type="text/javascript"
			src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
		<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,55);
		}
		window.onload = function() 
		{
			setCenter(0,55);
		}
		
		/*
		 获得所选择的部门和人员的ID
		*/
		function getItemInfo()
		{
			var str_temp = document.getElementById('tree').contentWindow.getDepAndUser();
			document.getElementById("selectUser").value=str_temp;
			return true;
		}
		function formSubmit()
		{
			if(getItemInfo()){
				addForm.submit();
			}
		}
		</script>
	</head>

	<body>
		<form name="addForm" action="saveNoticeLevel.action" method="post">
			<input type="hidden" name="selectUser" id="selectUser" value=""/>
			<input type="hidden" name="noticeLevelManage.noticeLevel" id="noticeLevel" value="${noticeLevelStr}"/>
		</form>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">公告等级：<eoms:dic value="${noticeLevelStr}"  dictype="noticeLevel"></eoms:dic>
					</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<table style="width: 100%; height: 100%">
					<tr valign="top" style="height: 92%">
						<td style="width: 50%">
							<fieldset
								style="border: 1px #d2e5fe solid; padding: 2px; height: 100%">
								<legend>
									<font style="font-weight: bold"><eoms:lable
											name='sm_lb_chooseOrg' />
									</font>
								</legend>
								<div class="blank_tr"></div>
								<div class="tabContent_top"
									style="height: 80%; overflow: hidden">
									<%-- 生成人员结构树页面 isRadio=0表示单选 isRadio=1 标示多选 --%>
								<iframe
									src="${ctx}/common/tools/depListTree.jsp?isRadio=1&isSelectType=2"
									id='tree' scrolling="no" frameborder="0" width="100%"
									height="320px"></iframe>
							</div>
								<div class="blank_tr"></div>
							</fieldset>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="确定" class="save_button"
				onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit();"
				id="submitButton" style="visibility: visible;" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</body>
</html>
