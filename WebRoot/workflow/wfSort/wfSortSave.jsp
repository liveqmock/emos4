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
		     <eoms:lable name="wf_sort_wfsort" />
	</title>
	
    <script type="text/javascript">
   window.onresize = function()
			{
				setCenter(0,61);
			}
	window.onload = function(){
	    setCenter(0,61);
    	var sign = '${sign}';
		if(sign == 'true'){
			document.getElementById('content').style.display = 'none';
			var pid = '${wfSort.pid}'
			opener.parent.document.getElementById('wfSortLeft').contentWindow.refresh(pid);
			window.close();
		}
	}
	
	function wfFormSubmit(){
		var bl  = Validator.Validate(wfForm,2,false);
		var code = document.getElementById('wfSort.code').value;
		var name = document.getElementById('wfSort.name').value;
		if(bl && check(code, "<eoms:lable name='wf_sort_code_require'/>！") && check(name, "<eoms:lable name='wf_sort_name_require'/>！")){
			if(code == null || code == ''){
				var wfCode =  document.getElementById('wfSort.code').value;
				$.get("${ctx}/wfsort/wfTypeCheckUnique.action",{'code':wfCode,'type':'wfSort'},function(result)
					{
						if(result == 'false'){
							alert('<eoms:lable name="wf_sortcode_uniquechk" />');
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
<div class="content" id="content">
		<s:form name="sortForm" id="sortForm">
			<input type="hidden" id="wfSort" />
		</s:form>
		
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<s:if test="%{wfSortId != null}">
								<eoms:lable name="wf_sort_edit" />
							</s:if>
							<s:else>
								<eoms:lable name="wf_sort_add" />
							</s:else>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="scroll_div" id="center">
						
					    <s:form action="saveWfSort.action" theme="simple" name="wfForm" id="wfForm" method="post" target="_self">
					    	<s:hidden name="wfSort.id"></s:hidden>
					    	<s:hidden name="wfSort.pid"></s:hidden>
					    	<s:hidden name="wfSort.createTime"></s:hidden>
						
							<div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_sort_code" />：<span class="must">*</span>
									</td>
									<td>
										<s:if test="%{wfSortId != null}">
											<s:textfield name="wfSort.code" id="wfSort.code" readonly="true" cssClass="textInput" maxlength="100"></s:textfield>
											<validation id="wfSort.codeV" require="true" dataType="Require" Max="20" msg="<eoms:lable name='wf_sort_code_require'/>！" />
										</s:if>
										<s:else>
											<s:textfield name="wfSort.code" id="wfSort.code" cssClass="textInput" maxlength="100"></s:textfield>
											<validation id="wfSort.codeV" require="true" dataType="Require" Max="20" msg="<eoms:lable name='wf_sort_code_require'/>！" />
										</s:else>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_sort_name" />：<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="wfSort.name" id="wfSort.name" class="textInput" value="${wfSort.name}" maxlength="100"/>
										<validation id="wfSort.nameV" require="true" dataType="Require" Max="20" msg="<eoms:lable name='wf_sort_name_require'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_sort_orderby" />：<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="wfSort.orderby" class="textInput" value="${wfSort.orderby}" maxlength="10"/>
										<validation id="wfSort.orderbyV" require="Custom" dataType="Integer" msg="<eoms:lable name='wf_sort_orderby_integer'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_remark" />：
									</td>
									<td>
										<s:textarea cssClass="textInput" name="wfSort.remark" id="wfSort.remark" rows="3" cssStyle="width:97%;overflow: visible"></s:textarea>
										<validation id="wfSort.remarkV" require="false" dataType="Limit" Max="400" msg="<eoms:lable name='wf_sort_remark_size'/>！" />
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