<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
    
	 <title>
		     <eoms:lable name="wf_agency_add" />
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
		var bgDate = $('#agency_bgDate').val();
		var edDate = $('#agency_edDate').val();
		if(bgDate == '' || edDate == '') {
			alert('代办起止时间必填！');
		} else {
			$('#agencyForm')[0].submit();		
		}
	}

	function openActorTree(id, name) {
		window.open("${ctx}/common/tools/depOrUserSelect.jsp?isSelectType=1&isRadio=0&input_other="+id+"&input_id="+id+"&input_name="+name);
	}
    </script>
</head>
<body>
<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							<eoms:lable name="wf_agency_add" />
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="scroll_div" id="center">
						
					    <s:form action="userSave.action" theme="simple" id="agencyForm" method="post" target="_self">
					    	<s:hidden name="agency.id"></s:hidden>
					    	<s:hidden name="agency.agentId"></s:hidden>
					    	<s:hidden name="agency.createTime"></s:hidden>
						
							<div class="tabContent_top">
								<table class="add_user" style="height: 340px">
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_agency_agentname" />：
										</td>
										<td>
											<s:textfield name="agency.agent" id="agency.agent" cssStyle="width:80%" readonly="true"></s:textfield><input type="button" value="选择" onclick="openActorTree('agency.agentId','agency.agent')"/>
										</td>
									</tr>
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_agency_bgdate" />：
										</td>
										<td>
											<s:textfield name="agency.bgDateStr" id="agency.bgDate" cssStyle="width:80%" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"></s:textfield>
										</td>
									</tr>
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_agency_eddate" />：
										</td>
										<td>
											<s:textfield name="agency.edDateStr" id="agency.edDate" cssStyle="width:80%" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"></s:textfield>
										</td>
									</tr>
								</table>
							</div>
  					  </s:form>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="submit()" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>