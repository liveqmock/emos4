<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
	<title>
	</title>
	
<script language="javascript">
   		window.onload = function(){
	    setCenter(0,61); 
	    initVisiable();
	    initRequired();
	   	initSelect();
	}
	
	function initSelect()
	{
		if('${action}' == "action")
		{
			document.getElementById('actionTypeCheck').value='${freeActionfieldrel.actionType}';
		}
	}
	
	function initVisiable()
	{
		if(${freeActionfieldrel.visiable} == 1)
		{
			document.getElementById('visiable').checked = true;
			document.getElementById('freeActionfieldrel.visiable').value  = 1;
		}else
		{
			document.getElementById('visiable').checked = false;
			document.getElementById('freeActionfieldrel.visiable').value  = 0;
		}
	}
	function initRequired()
	{
		if(${freeActionfieldrel.required} == 1)
		{
			document.getElementById('required').checked = true;
			document.getElementById('freeActionfieldrel.required').value  = 1;
		}else
		{
			document.getElementById('required').checked = false;
			document.getElementById('freeActionfieldrel.required').value  = 0;
		}
	}
	
	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	
	}
	
	function cheVisiable(checked)
	{
		if(checked == true )
		{
			document.getElementById('freeActionfieldrel.visiable').value = 1;
		}else
		{
			document.getElementById('freeActionfieldrel.visiable').value = 0;
		}
	}
	
	function cheRequired(checked)
	{
		if(checked == true )
		{
			document.getElementById('freeActionfieldrel.required').value = 1;
		}else
		{
			document.getElementById('freeActionfieldrel.required').value = 0;
		}
	}
	
	function freeActionFormSubmit(){
		var bl  = Validator.Validate(freeActionfieldrelForm,2,false);
		
		if(bl)
		{
			document.getElementById('freeActionfieldrelForm').submit(); 
		}
	}
	
	function onChange(value)
	{
		document.getElementById('freeActionfieldrel.actionType').value = value;
	}
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							配置状态动作字段信息<!--文本详细信息  -->
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		   <form action="saveFreeActionfieldrel.action" name="freeActionfieldrelForm" id="freeActionfieldrelForm" method="post" id="fieldForm">
		        <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr style="display:none;">
									<td  colspan="3" width="85%">
										<input type="text" id="freeActionfieldrel.fieldID"  name="freeActionfieldrel.fieldID" class="textInput"
											value="${freeActionfieldrel.fieldID}" readonly=true maxlength="50"/>
									</td>
								</tr>
								<tr style="display:none;">
									<td></td>
									<td  colspan="3" width="85%">
										<input type="text" id="freeActionfieldrel.id"  name="freeActionfieldrel.id" class="textInput"
											value="${freeActionfieldrel.id}" readonly=true maxlength="50"/>
									</td>
								</tr>

								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_fix_baseSchema"/>
									</td>
									<td  colspan="3" width="85%">
										<input type="text" id="freeActionfieldrel.baseSchema"  name="freeActionfieldrel.baseSchema" class="textInput"
											value="${freeActionfieldrel.baseSchema}" readonly=true maxlength="50"/>
									</td>
								</tr> 
								
								<tr>
									<td class="texttd">
									中文名称
									</td>
									<td  colspan="3" width="85%">
										<input type="text" id="freeActionfieldrel.baseField.label"  class="textInput"
											value="${baseField.label}" readonly=true maxlength="50"/>
									</td>
								</tr> 
								
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_fix_actionName"/>
									</td>
									<td  colspan="3" width="85%">
										<input type="text" id="freeActionfieldrel.baseField.fieldName"  class="textInput"
											value="${baseField.fieldName}" readonly maxlength="50"/>
									</td>
								</tr> 
								
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_fix_fieldType"/>
									</td>
									<td  colspan="3" width="85%">
										<input type="text" id="freeActionfieldrel.fieldType"  name="freeActionfieldrel.fieldType" class="textInput"
											value="${freeActionfieldrel.fieldType}" readonly maxlength="50"/>
									</td>
								</tr>

								<c:if test="${action == 'action'}">
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_fix_dongzuoType"/>
									</td>
									<td  colspan="3" width="85%">
										<input type="hidden" id="freeActionfieldrel.actionType" name="freeActionfieldrel.actionType" value="${freeActionfieldrel.actionType}"/>
										<s:select cssStyle="width:97%" cssClass="paddingHack" id="actionTypeCheck" name="actionTypeCheck" list="freeActionList" onchange="onChange(this.value);" listKey="actionType" listValue="label">
			  							</s:select>	
			  							<validation id="freeActionfieldrel.actionTypeV" require="true" dataType="Require" Max="50" msg="动作类型为必选项" />
									</td>
								</tr>
								</c:if>
								<c:if test="${action != 'action'}">
								<tr>
									<td class="texttd">
									</td>
									<td  colspan="3" width="85%">
									<input type="hidden"  name="freeActionfieldrel.actionType" value="${freeActionfieldrel.actionType}"/>
									</td>
								</tr>
								</c:if>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_fix_status"/>
									</td>
									<td  colspan="3" width="85%">
										<input type="text" id="freeActionfieldrel.baseStatus"  name="freeActionfieldrel.baseStatus" class="textInput"
											value="${freeActionfieldrel.baseStatus}" maxlength="50"/>
										<validation id="freeActionfieldrel.baseStatusV" require="true" dataType="Require" Max="50" msg="状态不能为空并且长度小于50" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_fix_visiable"/>
									</td>
									<td>
										<input type="hidden" id="freeActionfieldrel.visiable" name="freeActionfieldrel.visiable" value="${freeActionfieldrel.visiable}"/>
										<input  type="checkbox" id="visiable" name="visiable"  onclick="cheVisiable(this.checked)" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
									<eoms:lable name="bpp_develop_fix_required"/>
									</td>
									<td>
										<input type="hidden" id="freeActionfieldrel.required" name="freeActionfieldrel.required" value="${freeActionfieldrel.required}"/>
										<input type="checkbox" id="required" name="required"  onclick="cheRequired(this.checked)"/>
									</td>
								</tr>
							 </table>
						</div>
						   
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="freeActionFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
		   </form>	
	</div>
</body>
</html>