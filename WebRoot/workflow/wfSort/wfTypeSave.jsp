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
		<s:if test="%{wfTypeId != null}">
			<eoms:lable name="wf_type_edit" />
		</s:if>
		<s:else>
			<eoms:lable name="wf_type_add" />
		</s:else>
	</title>
	
<script language="javascript">
	window.onload = function(){
	    setCenter(0,61); 
    	var sign = '${sign}';
		if(sign == 'true'){
			parent.location.reload();
		}
	}

	window.onresize = function(){
		setCenter(0,61);
	}

	/**
	 *	表单验证
	 */
	function wfFormSubmit(){
	var bl  = Validator.Validate(wfForm,2,false);
	if(bl){
	
			var wfCode =  document.getElementById('wfType.code').value;
			if(!check(wfCode, "<eoms:lable name='wf_type_code_require'/>！")) {
				return;
			}
			
			var code = '${wfType.code}';
			if(code == null || code == ''){
				$.get("${ctx}/wfsort/wfTypeCheckUnique.action",{'code':wfCode,'type':'wfType','ran':Math.random()},function(result)
						{
							if(result == 'false'){
								alert('<eoms:lable name="wf_typecode_uniquechk" />');
							}else{
								document.getElementById('wfForm').submit();
							}
						}
					)
			}else{
				document.getElementById('wfForm').submit();
			}
		}
	}
	
</script>
</head>
<body>
<s:form name="sortForm" id="sortForm">
	<input type="hidden" id="wfSort" />
</s:form>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<s:if test="%{wfTypeId != null}">
								<eoms:lable name="wf_type_edit" />
							</s:if>
							<s:else>
								<eoms:lable name="wf_type_add" />
							</s:else>
							
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		
		<div class="scroll_div" id="center">
						
					    <s:form action="saveWfType.action" theme="simple" name="wfForm" id="wfForm" method="post" target="_self">
					    	<s:hidden name="wfType.id"></s:hidden>
					    	<s:hidden name="wfType.sortId"></s:hidden>					    	
							<s:hidden name="wfType.wfDefaultVersion"></s:hidden>
							<s:hidden name="wfType.baseCategoryIsDefaultFix"></s:hidden>
							<s:hidden name="wfType.lastEntryTime"></s:hidden>
							<s:hidden name="wfType.entryCount"></s:hidden>
							<s:hidden name="wfType.todayEntryCount"></s:hidden>
							<s:hidden name="wfType.baseCategoryState" value="1"></s:hidden>
							<s:hidden name="wfType.panelGroupID"></s:hidden>
							
							<div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_type_code" />：<span class="must">*</span>
									</td>
									<td>
									<s:if test="%{wfType.code != null}">
										<s:textfield name="wfType.code" readonly="true" id="wfType.code" cssClass="textInput"></s:textfield>
									</s:if>
									<s:else>
										<s:textfield name="wfType.code" id="wfType.code" cssClass="textInput" maxlength="21"></s:textfield>
									</s:else>
										<validation id="wfType.codeV" require="true" dataType="Require" msg="<eoms:lable name='wf_type_code_require'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_type_name" />：<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="wfType.name" class="textInput" value="${wfType.name}" maxlength="100"/>
										<validation id="wfType.nameV" require="true" dataType="Require" msg="<eoms:lable name='wf_type_name_require'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_type_basecategorycode" />：<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="wfType.baseCategoryCode" class="textInput" value="${wfType.baseCategoryCode}" maxlength="100"/>
										<validation id="wfType.baseCategoryCodeV" require="true" dataType="Require" msg="<eoms:lable name='wf_type_basecategorycode_require'/>！" />
									</td>
								</tr>
								
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_type_wfcounttype" />：<span class="must">*</span>
									</td>
									<td>
										<select id="wfType.wfCountType" name="wfType.wfCountType" class="select">
											<option value="D" <c:if test="${wfType.wfCountType=='D'}">selected</c:if>><eoms:lable name="wf_type_day" /></option>
											<option value="M" <c:if test="${wfType.wfCountType=='M'}">selected</c:if>><eoms:lable name="wf_type_mon" /></option>
											<option value="Y" <c:if test="${wfType.wfCountType=='Y'}">selected</c:if>><eoms:lable name="wf_type_year" /></option>
										</select>
										
									</td></tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_sort_orderby" />：<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="wfType.orderby" class="textInput" value="${wfType.orderby}" maxlength="5"/>
										<validation id="wfType.orderbyV" require="Custom" dataType="Integer" msg="<eoms:lable name='wf_sort_orderby_integer'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_type_wftype" />：<span class="must">*</span>
									</td>
									<td>	
									
									<s:if test="%{wfType.code != null}">
										<s:hidden name="wfType.wfType"></s:hidden><!-- select disable之后不传值，所以加个hidden用于传值 -->
										<s:select name="wfType.wfType" disabled="true" cssClass="select" list="#{'0':'自由流程','1':'固定流程'}"   listKey="key" listValue="value"	/>
									</s:if>
									<s:else>
										<s:select name="wfType.wfType"  cssClass="select" list="#{'0':'自由流程','1':'固定流程'}"   listKey="key" listValue="value"	/>
									</s:else>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										流程分类
									</td>
									<td>	
										<s:select name="wfType.flowType"  cssClass="select" list="#{'JSP':'JSP','AR':'AR'}"   listKey="key" listValue="value"	/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_type_basecategoryschama" />：
									</td>
									<td style="width:80%">
										<eoms:select style="select" name="wfType.theme" dataDicTypeCode="wfTypeTheme" value="${wfType.theme}" isnull="false"/>
									</td>
								</tr>
								
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_remark" />：
									</td>
									<td colspan="3">
										<s:textarea cssClass="textInput" name="wfType.remark" id="wfType.remark" rows="3"  cssStyle="width:97%"></s:textarea>
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