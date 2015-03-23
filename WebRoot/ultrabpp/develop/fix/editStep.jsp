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
	    constructFld('${stepModel.roleProcessRoleType}');
	    
	    var temp = document.getElementById('stepModel.stepNo').value;
	    if(temp=="" || temp== null )
	    {
	    	document.getElementById('stepModel.roleProcessRoleType').value = '5';
	    }else
	    {
	    }
	    //初始化受理时限
	    initAcceptH();
	    //初始化处理时限
	    initDealH();
	    //初始化
	    initHasSubFlow();
	    
	}
	
	//把时间转换成秒的格式
	function acceptClick()
	{
		var timeH = document.getElementById('acceptH').value;
		timeH = Math.round(timeH);
		var timeM = document.getElementById('acceptM').value;
		timeM = Math.round(timeM);
		var timeS = document.getElementById('acceptS').value;
		timeS = Math.round(timeS);
		document.getElementById('stepModel.acceptOver').value = timeH * 3600 + timeM * 60 + timeS ;
	}
	//把时间转化成秒的格式
	function dealClick()
	{
		var timeH = document.getElementById('dealH').value;
		timeH = Math.round(timeH);
		var timeM = document.getElementById('dealM').value;
		timeM = Math.round(timeM);
		var timeS = document.getElementById('dealS').value;
		timeS = Math.round(timeS);
		document.getElementById('stepModel.dealOver').value = timeH * 3600 + timeM * 60 + timeS ;
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	//从数据库拿到的数据是秒，把转化成 小时 分钟 秒的格式
	function initAcceptH()
	{
		var time = '${stepModel.acceptOver}';
		time = Math.round(time);
		var timeH = time / 3600;
		if(timeH >= 0)
		{
			timeH = Math.floor(timeH);
		}else
		{
			timeH = 0;
		}
		time = time - timeH * 3600; 
		
		var timeM = time / 60;
		if(timeM >= 0)
		{
			timeM = Math.floor(timeM);
		}else
		{
			timeM = 0;
		}
		time = time - timeM * 60;
		
		var timeS = time;
		
		document.getElementById('acceptH').value = timeH;
		document.getElementById('acceptM').value = timeM;
		document.getElementById('acceptS').value = timeS;
	}
	
	//从数据库拿到的数据是秒，把转化成 小时 分钟 秒的格式
	function initDealH()
	{
		var time = '${stepModel.dealOver}';
		time = Math.round(time);
		var timeH = time / 3600;
		if(timeH >= 0)
		{
			timeH = Math.floor(timeH);
		}else
		{
			timeH = 0;
		}
		time = time - timeH * 3600; 
		
		var timeM = time / 60;
		if(timeM >= 0)
		{
			timeM = Math.floor(timeM);
		}else
		{
			timeM = 0;
		}
		time = time - timeM * 60;
		
		var timeS = time;
		
		document.getElementById('dealH').value = timeH;
		document.getElementById('dealM').value = timeM;
		document.getElementById('dealS').value = timeS;
	}

	function stepFormSubmit(){
		var bl  = Validator.Validate(stepForm,2,false);
		var baseSchema = document.getElementById('stepModel.baseSchema').value;
		var stepID = document.getElementById('stepModel.id').value;
		var stepNo = document.getElementById('stepModel.stepNo').value;
		
		
		if(bl)
		{
			$.get("${ctx}/ultrabpp/develop/stepNoCheckUnique.action?stamp="+new Date().getTime(),{'baseSchema':baseSchema,'stepID':stepID,'stepNo':stepNo},function(result)
					{
						if(result == 'false'){
							alert('环节标识重复已经存在！已经存在环节标识为：'+stepNo+'的组件了！');
						}else{
							document.getElementById('stepForm').submit(); 
	         				
						}
					}
				)
		}
	}
		
	
	function constructFld(value)
	{
		if(value == "0")
		{
			document.getElementById('assignee').style.display = '';
			document.getElementById('groupName').style.display = '';
			document.getElementById('roleKey').style.display = 'none';
			document.getElementById('contextKey').style.display = 'none';
		}else
		if(value == "1")
		{
			document.getElementById('assignee').style.display = 'none';
			document.getElementById('groupName').style.display = 'none';
			document.getElementById('roleKey').style.display = '';
			document.getElementById('contextKey').style.display = 'none';
		}else
		if(value == '2')
		{
			document.getElementById('assignee').style.display = 'none';
			document.getElementById('groupName').style.display = 'none';
			document.getElementById('roleKey').style.display = 'none';
			document.getElementById('contextKey').style.display = '';
		}else
		{
			document.getElementById('assignee').style.display = 'none';
			document.getElementById('groupName').style.display = 'none';
			document.getElementById('roleKey').style.display = 'none';
			document.getElementById('contextKey').style.display = 'none';
		}
	}
	
	
	function initHasSubFlow(){
		var hasSubFlow = document.getElementById('hasSubFlow');
		hasSubFlow.checked=document.getElementById('stepModel.hasSubFlow').value==1?true:false;
	}
	function changeHasSubFlow()
	{
		var hasSubFlow = document.getElementById('hasSubFlow');
		document.getElementById('stepModel.hasSubFlow').value = hasSubFlow.checked==true?'1':'0';
	}
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="bpp_develop_fix_stepInfor"/>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="scroll_div" id="center">
		  <s:form action="saveStep.action" theme="simple" name="stepForm" id="stepForm" method="post" target="_self">
			<div class="tabContent_top">
				<table  class="add_user">
					
					<tr style="display:none;">
						<td></td>
						<td  colspan="3" width="85%">
							<input type="text" id="stepModel.id"  name="stepModel.id" class="textInput"
								value="${stepModel.id}" maxlength="15"/>
						</td>
					</tr>
					
					<tr style="display:none;">
						<td></td>
						<td  colspan="3" width="85%">
							<input type="text" id="stepModel.operate"  name="stepModel.operate" class="textInput"
								value="${stepModel.operate==''?'add':stepModel.operate}" maxlength="15"/>
						</td>
					</tr>
					<tr>
						<td class="texttd" width="15%">
							<eoms:lable name="bpp_develop_fix_stepNo"/>
						</td>
						<td  colspan="3" width="85%">
							<input type="text" id="stepModel.stepNo" name="stepModel.stepNo" class="textInput"
								value="${stepModel.stepNo}" maxlength="50"/>
							<validation id="stepModel.stepNoV" require="true" dataType="Require" Max="50" msg="环节标识不能为空并且长度小于50" />
						</td>
					</tr>
					<tr style="display:none;">
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_baseSchema"/>
						</td>
						<td colspan="3" width="85%">
							<input type="text" id="stepModel.baseSchema" name="stepModel.baseSchema" class="textInput"
								value="${stepModel.baseSchema}" maxlength="50" />
							<validation id="stepModel.baseSchemaV" require="true" dataType="Require" Max="50" msg="工单类型不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_description"/>
						</td>
						<td colspan="3" width="85%">
							<input type="text" id="stepModel.description" name="stepModel.description" class="textInput"
								value="${stepModel.description}" maxlength="50" />
							<validation id="stepModel.descriptionV" require="true" dataType="Require" Max="50" msg="环节简述不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd" width="15%">
							<eoms:lable name="bpp_develop_fix_roleName"/>
						</td>
						<td width="35%">
							<input type="text" id="stepModel.roleName" name="stepModel.roleName" class="textInput" style="width:90%"
								value="${stepModel.roleName}" maxlength="50" />
							<validation id="stepModel.roleNameV" require="true" dataType="Require" Max="50" msg="操作角色不能为空并且长度小于50" />
						</td>
						<td class="texttd" width="15%">
							 <eoms:lable name="bpp_develop_fix_roleID"/>
						</td>
						<td width="35%">
							<input type="text" id="stepModel.roleID" name="stepModel.roleID" class="textInput" style="width:90%"
								value="${stepModel.roleID}" maxlength="50" />
							<validation id="stepModel.roleIDV" require="true" dataType="Require" Max="50" msg="操作角色ID不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd" width="15">
							<eoms:lable name="bpp_develop_fix_roleProcessRoleType"/>
						</td>
						<td colspan="3" width="85%">
									<s:select name="stepModel.roleProcessRoleType" id="stepModel.roleProcessRoleType" cssStyle="width:97%"
										onchange="constructFld(this.value);"
										cssClass="select"   
										list="#{'0':'固定的执行角色','1':'流程中已经定制的执行角色','2':'流程流转中关联的上下文','3':'流程流转角色通过规则生成','4':'由母流程决定','5':'从前台传入处理人'}"
										listKey="key"
										listValue="value"
										
										/>
						</td>
					</tr>
						<tr id="assignee"  style="display:none;">
							<td class="texttd">
								<eoms:lable name="bpp_develop_fix_assignee"/>
							</td>
							<td>
								<input type="text" id="stepModel.assignee" name="stepModel.assignee" class="textInput" style="width:90%"
									value="${stepModel.assignee}" maxlength="50" />
							</td>
							<td class="texttd">
								<eoms:lable name="bpp_develop_fix_assigneeID"/>
							</td>
							<td>
								<input type="text" id="stepModel.assigneeID" name="stepModel.assigneeID" class="textInput" style="width:90%"
									value="${stepModel.assigneeID}" maxlength="50" />
							</td>
						</tr>
						<tr id="groupName" style="display:none;">
							<td class="texttd">
								<eoms:lable name="bpp_develop_fix_groupName"/>
							</td>
							<td>
								<input type="text" name="stepModel.groupName" class="textInput" style="width:90%"
									value="${stepModel.groupName}" maxlength="50" />
							</td>
							<td class="texttd">
								<eoms:lable name="bpp_develop_fix_groupID"/>
							</td>
							<td>
								<input type="text" name="stepModel.groupID" class="textInput" style="width:90%"
									value="${stepModel.groupID}" maxlength="50" />
							</td>
						</tr>
					<tr  id="roleKey" style="display:none;">
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_roleKey"/>
						</td>
						<td colspan="3" width="85%">
							<input type="text" name="stepModel.roleKey" class="textInput" 
								value="${stepModel.roleKey}" maxlength="50" />
						</td>
					</tr>
					
					<tr  id="contextKey" style="display:none">
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_contextKey"/>
						</td>
						<td colspan="3" width="85%">
							<input type="text" name="stepModel.contextKey" class="textInput"
								value="${stepModel.contextKey}" maxlength="50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_actionType"/>
						</td>
						<td colspan="3" width="85%">
									<s:select name="stepModel.actionType" id="stepModel.actionType" cssStyle="width:97%"
										cssClass="select" 
										list="#{'DEAL':'处理','AUDITING':'审批','ASSIST':'协办','COPY':'抄送'}"
										listKey="key"
										listValue="value"/>
							
						</td>
						
					</tr>
					<tr>
						<td class="texttd"  width="15%">
							<eoms:lable name="bpp_develop_fix_taskPolicy"/>
						</td>
						<td colspan="3" width="85%">
									<s:select name="stepModel.taskPolicy" id="stepModel.taskPolicy" cssStyle="width:97%"
										cssClass="select" list="#{'SHARE':'共享','ONLY':'独占','MANAGEMENT':'管理员分派'}" listKey="key"
										listValue="value"/>
							
						</td>
					</tr>
					<tr style="display:none;">
						<td>
							<input type="hidden" name="stepModel.acceptOver" id="stepModel.acceptOver" class="textInput"
									value="${stepModel.acceptOver}" maxlength="50" />
							<validation id="stepModel.acceptOverV" require="Custom" dataType="Integer" msg=<eoms:lable name="bpp_develop_fix_acceptOveMstNor"/>/>
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_acceptOver"/>
						</td>
						<td colspan="3">
							<input type="text" id="acceptH"  style="width:20px;"  onblur="acceptClick();" maxlength="3" />
							小时
							<input type="text" id="acceptM" style="width:20px;"  onblur="acceptClick();"  maxlength="3" />
							分钟
							<input type="text" id="acceptS" style="width:20px;"  onblur="acceptClick();"  maxlength="3" />
							秒
						</td>
					</tr>
					
					<tr style="display:none;">
						<td>
						<input type="text" name="stepModel.dealOver" id="stepModel.dealOver" class="textInput"
								value="${stepModel.dealOver}" maxlength="50"/>
						<validation id="stepModel.dealOverV" require="Custom" dataType="Integer" msg=<eoms:lable name="bpp_develop_fix_dealOverMstNo"/>/>
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_dealOver"/>
						</td>
						<td>
							<input type="text" id="dealH"  style="width:20px;"   onblur="dealClick();" maxlength="3" />
							小时
							<input type="text" id="dealM"  style="width:20px;"   onblur="dealClick();" maxlength="3" />
							分钟
							<input type="text" id="dealS"  style="width:20px;"  onblur="dealClick();" maxlength="3" />
							秒
						</td>
					</tr>
					
					<tr>
						<td class="texttd">
							是否为内部子流程
						</td>
						<td>
							<input type="hidden" id="stepModel.hasSubFlow" name="stepModel.hasSubFlow" value="${stepModel.hasSubFlow}"/>
							<input type="checkbox" id="hasSubFlow" name="hasSubFlow"  onclick="changeHasSubFlow()"/>
						</td>
					</tr>
					
					
				</table>
			</div>
			</s:form>
			</div>

			<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="stepFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
			</div>
	</div>
</body>
</html>