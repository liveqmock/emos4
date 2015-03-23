<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
	
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
    
	<script src="${ctx}/workflow/wfInterface/js/interface.js"></script> 
	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
	 <title>
		     <eoms:lable name="wf_interface_title" />
	</title>
	
<script language="javascript">
	window.onload = function(){
    	setCenter(0,61);
	}
	window.onresize = function()
	{
		setCenter(0,61);
	}


/**
*保存流程接口方法
*liuchuanzu 20100601
*/
function wfFormSubmit(){
	var bl  = Validator.Validate($('#wfForm')[0],2,false);
	if(bl){
	
			var wfCode =  document.getElementById('wfinterface.code').value;
			if(!check(wfCode, "<eoms:lable name='wf_interface_code_require'/>！")) {
				return ;
			}
	
			var code = '${wfinterface.code}';
			if(code == null || code == ''){
			$.get("${ctx}/wfinterface/wfInterfaceCheckUnique.action",{'code':wfCode},function(result)
					{
						if(result == 'false'){
							alert('<eoms:lable name="wf_interface_uniquechk" />');
						}else{
							$('#wfForm')[0].submit();
						}
					}
				)
			}else{
				$('#wfForm')[0].submit();
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
						<s:if test='%{interfaceCode != null}'>
							<eoms:lable name="wf_interface_edit" />
						</s:if>
						<s:else>
							<eoms:lable name="wf_interface_add" />
						</s:else>
					</span>
			</span>
			<span class="title_xieline"></span>
		</div>
	</div>

	<div class="scroll_div" id="center">
			
		    <s:form action="saveInterface.action" theme="simple" id="wfForm" method="post" target="_self">
		    	<s:hidden name="wfinterface.id"></s:hidden>
		    	<input name="propString" id="propString" type="hidden" />
			
				<div class="tabContent_top">
					<table class="add_user">
						<tr>
							<td class="texttd">
								<eoms:lable name="wf_interface_code" />：<span class="must">*</span>
							</td>
							<td>
								<s:if test='%{interfaceCode != null}'>
									<s:textfield name="wfinterface.code" id="wfinterface.code" readonly="true" cssClass="textInput"></s:textfield>
									<validation id="wfinterface.codeV" require="true" dataType="Require" Max="20" msg="<eoms:lable name='wf_interface_code_require'/>！" />
								</s:if>
								<s:else>
									<s:textfield name="wfinterface.code" id="wfinterface.code" cssClass="textInput" maxlength="100"></s:textfield>
									<validation id="wfinterface.codeV" require="true" dataType="Require" Max="20" msg="<eoms:lable name='wf_interface_code_require'/>！" />
								</s:else>
							</td>
						</tr>
						<tr>
							<td class="texttd">
								<eoms:lable name="wf_interface_name" />：<span class="must">*</span>
							</td>
							<td>
								<input type="text" name="wfinterface.name" class="textInput" value="${wfinterface.name}" maxlength="100"/>
								<validation id="wfinterface.nameV" require="true" dataType="Require" Max="20" msg="<eoms:lable name='wf_interface_name_require'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd">
								<eoms:lable name="wf_interface_type" />：<span class="must">*</span>
							</td>
							<td>
								<select name="wfinterface.type" id="wfinterface.type" class="select" style="width:98%">
									<option value="1"><eoms:lable name="wf_interface_mapinterface"/></option>
									<option value="2"><eoms:lable name="wf_interface_freeinterface"/></option>
									<option value="3"><eoms:lable name="wf_interface_conditioninterface"/></option>
									<option value="4"><eoms:lable name="wf_interface_manainterface"/></option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="texttd">
								<eoms:lable name="wf_interface_path" />：<span class="must">*</span>
							</td>
							<td>
								<s:textarea cssClass="textInput" name="wfinterface.path" id="wfinterface.path" rows="2" cssStyle="width:97%;overflow: visible" />
								<validation id="wfinterface.pathV" require="true" dataType="Require" Max="20" msg="<eoms:lable name='wf_interface_path_require'/>！" />
							</td>
						</tr>
						<tr>
							<td class="texttd"><eoms:lable name="com_lb_remark" />：</td>
							<td>
								<s:textarea cssClass="textInput" name="wfinterface.remark" id="wfinterface.remark" rows="3" cssStyle="width:97%;overflow: visible" />
								<validation id="wfinterface.remarkV" require="false" dataType="Limit" Max="400" msg="<eoms:lable name='wf_sort_remark_size'/>！" />
							</td>
						</tr>
					</table>
				</div>
			</s:form>
	</div>
					
	<div class="add_bottom">
		<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="wfFormSubmit();" />
		<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
	</div>
</div>
</body>
</html>