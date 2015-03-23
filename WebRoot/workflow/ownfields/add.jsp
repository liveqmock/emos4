<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/core/taglibs_jsp.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/jsp/common/js/util.js"></script>
    
	 <title>
		     <eoms:lable name="wf_ownfield_add" />
	</title>
	
    <script type="">
	window.onload = function(){
		setCenter(0,61);
	}
	window.onresize = function()
			{
				setCenter(0,61);
			}

	function submit() {
		var baseName = getJQueryId("ownFields.baseSchema").find("option:selected").text();
		getJQueryId("ownFields.baseName").val(baseName);
		getJQueryId('ownFieldsForm')[0].submit();
	}
	
	var msg='${msg}';
	if(msg != '' && msg !='null') {
		alert(msg);
	}
    </script>
</head>
<body>
<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="wf_ownfield_add" />
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="scroll_div" id="center">
					    <s:form action="save.action" theme="simple" id="ownFieldsForm" name="ownFieldsForm" method="post" target="_self">
					    	<s:hidden name="ownFields.id"></s:hidden>
					    	<s:hidden name="ownFields.createTime"></s:hidden>
					    	<s:hidden name="ownFields.baseName" id="ownFields.baseName"></s:hidden>
						
							<fieldset>
								<legend><eoms:lable name="wf_ownfield_base" /></legend>
								<table class="gd_table1">
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_ownfield_basename" />：
										</td>
										<td>
											<s:select name="ownFields.baseSchema" id="ownFields.baseSchema" list="wftypes" listKey="code" listValue="name" emptyOption="false" cssClass="gd_select"></s:select>
										</td>
										<td class="rightalign">
											<eoms:lable name="wf_ownfield_name" />：
										</td>
										<td>
											<s:textfield name="ownFields.fieldName" id="ownFields.fieldName" cssClass="gd_input"></s:textfield>
										</td>
										<td class="rightalign">
											<eoms:lable name="wf_ownfield_code" />：
										</td>
										<td>
											<s:textfield name="ownFields.fieldCode" id="ownFields.fieldCode" cssClass="gd_input"></s:textfield>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_ownfield_type" />：
										</td>
										<td>
											<eoms:select name="ownFields.fieldType" dataDicTypeCode="fieldType" value="${ownFields.fieldType}" isnull="false" style="gd_select"/>
										</td>
										<td class="rightalign">
											<eoms:lable name="wf_ownfield_typevalue" />：
										</td>
										<td>
											<s:textfield name="ownFields.fieldTypeValue" id="ownFields.fieldTypeValue" cssClass="gd_input"></s:textfield>
										</td>
										<td class="rightalign">
											<eoms:lable name="wf_ownfield_printline" />：
										</td>
										<td>
											<eoms:select name="ownFields.printOneLine" dataDicTypeCode="isdefault" value="${ownFields.printOneLine}" isnull="false" style="gd_select"/>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_ownfield_order" />：
										</td>
										<td>
											<s:textfield name="ownFields.printOrder" id="ownFields.printOrder" cssClass="gd_input"></s:textfield>
										</td>
										<td class="rightalign">
											<eoms:lable name="wf_ownfield_isprint" />：
										</td>
										<td>
											<eoms:select name="ownFields.isPrint" dataDicTypeCode="isdefault" value="${ownFields.isPrint}" isnull="false" style="gd_select"/>
										</td>
										<td class="rightalign">
											<eoms:lable name="wf_ownfield_istolong" />：
										</td>
										<td>
											<eoms:select name="ownFields.fieldIsToLong" dataDicTypeCode="isdefault" value="${ownFields.fieldIsToLong}" isnull="false" style="gd_select"/>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_ownfield_post" />：
										</td>
										<td>
											<eoms:select name="ownFields.logPosition" dataDicTypeCode="isdefault" value="${ownFields.logPosition}" isnull="false" style="gd_select"/>
										</td>
										<td class="rightalign">
										</td>
										<td>
										</td>
										<td class="rightalign">
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_ownfield_freeaction" />：
										</td>
										<td colspan="7">
											<s:textfield name="ownFields.freeEditStep" id="ownFields.freeEditStep" cssClass="gd_input2"></s:textfield>
										</td>
									</tr>
									<tr>
										<td class="rightalign1">
											<eoms:lable name="wf_ownfield_defstepcode" />：
										</td>
										<td colspan="7">
											<s:textfield name="ownFields.defEditStep" id="ownFields.defEditStep" cssClass="gd_input2"></s:textfield>
										</td>
									</tr>
								</table>
							</fieldset>
  					  </s:form>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="submit()" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>