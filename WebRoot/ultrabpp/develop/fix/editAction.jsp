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
	    //初始化选择组件
	    initIsClose();
	    initIsFree();
	    initHasNext();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	
	function actionFormSubmit(){
		var bl  = Validator.Validate(actionForm,2,false);
		var baseSchema = document.getElementById('actionModel.baseSchema').value;
		var actionName = document.getElementById('actionModel.actionName').value;
		var stepNo = document.getElementById('actionModel.stepNo').value;
		var actionID = document.getElementById('actionModel.id').value;
		if(bl)
		{
			$.get("${ctx}/ultrabpp/develop/actionNameCheckUnique.action?stamp="+new Date().getTime(),{'baseSchema':baseSchema,'actionName':actionName,'stepNo':stepNo,'actionID':actionID},function(result)
			{
				if(result == 'false'){
					if(confirm('动作重复已经存在！已经存在动作为：'+actionName+'的组件了！如果继续提交将在部署时只生成一个动作表单页面。')){
						document.getElementById('actionForm').submit(); 
					}
				}else{
					document.getElementById('actionForm').submit(); 
				}
			}
			)
		}
	}

	function changeIsFee()
	{
		var freeRadio = document.getElementsByName('isFree');
		for(var i=0;i<freeRadio.length;i++){
			if(freeRadio[i].checked==true){
				document.getElementById('actionModel.isFree').value=freeRadio[i].value;
			}
		}
	}
	
	function changeIsClose()
	{
		var closeRadio = document.getElementsByName('isClose');
		for(var i=0;i<closeRadio.length;i++){
			if(closeRadio[i].checked==true){
				document.getElementById('actionModel.isClose').value=closeRadio[i].value;
			}
		}
	}
	
	function changeHasNext(){
		var hasNext = document.getElementById('hasNext');
		document.getElementById('actionModel.hasNext').value = hasNext.checked==true?'1':'0';
	}
	
	
	function initIsClose(){
		var closeRadio = document.getElementsByName('isClose');
		for(var i=0;i<closeRadio.length;i++){
			if(closeRadio[i].value==document.getElementById('actionModel.isClose').value){
				closeRadio[i].checked=true;
			}
		}
	}
	
	function initIsFree(){
		var freeRadio = document.getElementsByName('isFree');
		for(var i=0;i<freeRadio.length;i++){
			if(freeRadio[i].value==document.getElementById('actionModel.isFree').value){
				freeRadio[i].checked=true;
			}
		}
	}
	
	function initHasNext(){
		var hasNext = document.getElementById('hasNext');
		hasNext.checked=document.getElementById('actionModel.hasNext').value==1?true:false;
	}
	
</script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							动作详情信息
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="scroll_div" id="center">
		  <s:form action="saveAction.action" theme="simple" name="actionForm" id="actionForm" method="post" target="_self">
			<div class="tabContent_top">
				<table class="add_user">
				<tr style="display:none;">
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_id"/>
						</td>
						<td colspan="2">
							<input type="text" id="actionModel.operate" name="actionModel.operate" class="textInput"
								value="${actionModel.operate}" maxlength="15" />
						</td>
					</tr>
					<tr style="display:none;">
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_id"/>
						</td>
						<td colspan="2">
							<input type="text" id="actionModel.id" name="actionModel.id" class="textInput"
								value="${actionModel.id}" maxlength="15" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_stepNo"/>
						</td>
						<td>
							<input type="text" id="actionModel.stepNo" name="actionModel.stepNo" class="textInput"
								value="${actionModel.stepNo}" maxlength="50" readOnly=true/>
							<validation id="actionModel.stepNoV" require="true" dataType="Require" Max="20" msg="环节标识不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_baseSchema"/>
						</td>
						<td>
							<input type="text" name="actionModel.baseSchema" id="actionModel.baseSchema" class="textInput"
								value="${actionModel.baseSchema}" maxlength="50" readOnly=true/>
							<validation id="actionModel.baseSchemaV" require="true" dataType="Require" Max="20" msg="工单类型不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_label"/>
						</td>
						<td>
							<input type="text" id="actionModel.label" name="actionModel.label" class="textInput"
								value="${actionModel.label}" maxlength="50" />
							<validation id="actionModel.labelV" require="true" dataType="Require" Max="20" msg="中文名不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_actionName"/>
						</td>
						<td>
							<input type="text" id="actionModel.actionName" name="actionModel.actionName" class="textInput"
								value="${actionModel.actionName}" maxlength="50" />
							<validation id="actionModel.actionNameV" require="true" dataType="Require" Max="30" msg="英文名不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="com_lb_desc"/>
						</td>
						<td>
							<input type="text" id="actionModel.description" name="actionModel.description" class="textInput"
								value="${actionModel.description}" maxlength="50" />
							<validation id="actionModel.descriptionV" require="true" dataType="Require" Max="50" msg="描述不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_dongzuoType"/>
						</td>
						<td>
							<input type="text" id="actionModel.actionType" name="actionModel.actionType" class="textInput"
								value="${actionModel.actionType}" maxlength="50" />
							<validation id="actionModel.actionTypeV" require="true" dataType="Require" Max="20" msg="类型不能为空并且长度小于50" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							显示顺序
						</td>
						<td>
							<input type="text" id="actionModel.orderId" name="actionModel.orderId" class="textInput" 
							value="${actionModel.orderId}" maxlength="10" />
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_free"/>/<eoms:lable name="bpp_develop_fix_fix"/>
						</td>
						<td>
							<input type="hidden" id="actionModel.isFree" name="actionModel.isFree" value="${actionModel.isFree}"/>
							固定
							<input type="radio" name="isFree" value="0"   onclick="changeIsFee()"/>
							自由
							<input type="radio" name="isFree" value="1"   onclick="changeIsFee()"/>
						</td>
					</tr>
					<tr>
						<td class="texttd">
							<eoms:lable name="bpp_develop_fix_hasNext"/>
						</td>
						<td>
							<input type="hidden" id="actionModel.hasNext" name="actionModel.hasNext" value="${actionModel.hasNext}"/>
							<input type="checkbox" id="hasNext" name="hasNext"  onclick="changeHasNext()"/>
						</td>
					</tr>
					<tr>
						<td class="texttd">
							动作提交后关闭
						</td>
						<td>
							是<input type="radio" name="isClose" id="isClose"  value="1"  onclick="changeIsClose()"/>
							否<input type="radio" name="isClose" id="isClose"  value="0"  onclick="changeIsClose()"/>
							<input type="hidden" id="actionModel.isClose" name="actionModel.isClose" value="${actionModel.isClose}"/>
						</td>
					</tr>
				</table>
			</div>
			</s:form>	
			</div>

			<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="actionFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>