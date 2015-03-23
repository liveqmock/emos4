<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/datagrid.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<script src="${ctx}/common/javascript/util.js"></script>
		<script src="${ctx}/workflow/sheet/role/js/roleDimension.js"></script>

		<title><eoms:lable name="wf_sheet_dimensionedit" /></title>

	<script type="text/javascript">
		window.onresize = function() 
		{
		  setCenter(0,38);
		}
	</script>
	
	</head>
	<body>
		<div class="content">
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="wf_sheet_dimensionedit" /></legend>
					<table class="add_user" border="0">
						<tr>
							<td class="texttd" height="30">
								<eoms:lable name="wf_sheet_dimensions" />：
							</td>
							<td>
								<select id='dimensionOption' style="width: 100px"></select>
							</td>
							<td>
								<input type="button" value="<eoms:lable name="com_btn_add"/>" id="title_3" class="add_button" onclick="addDimension();" />
							</td>
						</tr>
						<tr>
							<td valign="top" style="border:solid #666666 1px" height="90%" colspan="4">
								<div  class="add_scroll_div_x" id="dimensionTable"></div>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			
			
			<div class="add_bottom">	
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onclick="formsubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
			</div>
		</div>
		
		<form id="sheetform" action="${ctx}/sheetrole/saveRoleMatchDimension.action" target="_self">
			<s:hidden name="roleCode"></s:hidden>
			<input type="hidden" name="dimensionCodes" id="dimensionCodes" />
			<input type="hidden" name="dimensionOrderby" id="dimensionOrderby" />
		</form>
		
	<script type="text/javascript">
		window.onload = function(){
			init();
		}
		var i = 1;
		<s:iterator value="%{demensionList}" status="Dimension">
			var codes = "<s:property value='dimensioncode' />";
			var names = "<s:property value='dimensionname' />";
			var d = new JsDimension();
				d.sign = '0';
				d.code = codes;
				d.name = names;
				d.orderby = i;
				i++;
			dimensionMap.put(d.code,d);
		</s:iterator>
		
		
		<s:iterator value="%{roleMdemensionList}" status="RoleMatchDimension">
			var codes = "<s:property value='dimensioncode' />";
			var orderby = "<s:property value='orderby' />";
			var obj = dimensionMap.get(codes);
			if(obj != null){
				obj.sign = '1';
				obj.orderby = orderby;
				dimensionMap.put(codes,obj);
			}
		</s:iterator>
	</script>
	</body>
</html>
