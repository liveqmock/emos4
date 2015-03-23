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

		<title><eoms:lable name="wf_sheet_batchadddimension" /></title>

	<script type="text/javascript">	
	window.onresize = function(){
			setCenter(0,61);
		}	
		window.onload = function(){
			setCenter(0,61);
			init();
		}
		
		//维度值map
		var valueMap = new Map();
		
		function formsubmit(){
			var str = '';
			for(var i=0; i< valueMap.keySet().size();i++){
				var key = valueMap.keySet().get(i);
				str+='#' + valueMap.get(key);
			}
			
			if(str!=''){
				str = str.substring(1);
			}
			
			document.getElementById('dimensionValues').value = str;
			document.getElementById('sheetform').submit();
		}
		
		var iframeMap = new Map();
		
		
		//初始化数据，并设置iframe高度
		function init(){		
			var h = window.screen.height;
			var w = window.screen.width;
			var objs = document.getElementsByName('dimensionIframe');
			
			var iframe_tr = document.getElementById('iframe_tr');
			
			var i = 0;
			
			<s:iterator value="roleDimensionList" var="RoleMatchDimension">
				var src = "<s:property value='dimensioncode' />";
			 	iframeMap.put(i,"${ctx}/childrole/getDimensionValue.action?dimensionCode="+src);
			 	i++;
			</s:iterator>
			
			for(var i=0;i<iframeMap.keySet().size();i++){
				var value = iframeMap.get(iframeMap.keySet().get(i));
				var td = document.createElement('td');
				var iframe = document.createElement('iframe');
				td.style.height = '100%';
				td.style.width = (w-100)/iframeMap.keySet().size() ;
				iframe.style.height = '100%';
				iframe.frameBorder = '0';
				iframe.style.width = '300';
				iframe.src = value;
				td.appendChild(iframe);
				iframe_tr.appendChild(td);
			}

			window.resizeTo(300*iframeMap.keySet().size()+50, 680);
		}
	</script>
	
	</head>
	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2">
							"${wfRole.rolename}"<eoms:lable name="wf_sheet_batchadddimension" />
						</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			
			<div style="overflow:auto;width:100%;height:100%;" id="center">
				
					<table class="add_user" border="0">
					<form id="sheetform" action="${ctx}/childrole/saveDimensionBatch.action" method="post" target="_self">
						<s:hidden name="roleCode"></s:hidden>
						<s:hidden name="wfRole.rolename" />
						<tr>
							<td valign="top" align="center">
									<table class="add_user" border="0" style="height:530px;width:100%">
										<tr id="iframe_tr">
											<td width="10px" height="100%" align="center"></td>
										</tr>
									</table>
							</td>
						<input type="hidden" name="dimensionValues" id="dimensionValues" />
						</tr>
					</form>
					</table>
					
			</div>
			
			
			<div class="add_bottom">	
					<input type="button" value="<eoms:lable name="com_btn_save"/>" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" class="save_button" onclick="formsubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
			</div>
		</div>
		
	</body>
</html>
