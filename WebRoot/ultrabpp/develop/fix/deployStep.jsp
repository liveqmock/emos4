<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0,26);
		}
		
		window.onload = function() 
		{
			setCenter(0,26);
			//changeRow_color("tab");
		}
		
		function configField(baseSchema,stepNo)
		{
			 var src = '${ctx}/ultrabpp/develop/configWorksheetField.action?baseSchema='+baseSchema+'&stepNo='+stepNo; 
            window.open(src,'','width=600px,height=350px,top=250,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
		
		function addAction(){
			var baseSchema = document.getElementById('base').value;
			var stepNo = document.getElementById('stepNo').value;
			var src = '${ctx}/ultrabpp/develop/editAction.action?baseSchema='+baseSchema+'&stepNo='+stepNo;
			window.open(src,'','width=550px,height=350px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		}
		
		
	</script>
	<body>
		<input type="hidden" id="base" value="${baseSchema}"/>
		<input type="hidden" id="stepNo" value="${stepNo}"/>
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">${nodePath}</span> </span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="content">
		<div class="add_scroll_div_x" id="center">
			<div class='page_div_bg'>
				<div class='page_div'>
					<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" onmouseout="this.className='page_ok_button'" onclick="configField('${baseSchema}','${stepNo}');" text='bpp_develop_fix_configField' />
					<eoms:operate cssclass="page_cancelchanges_button" id="com_btn_search" onmouseover="this.className='page_cancelchanges_button_hover'" onmouseout="this.className='page_cancelchanges_button'" onclick="window.history.back();" text='com_btn_goback' />
				</div>
			</div>
			<iframe width="100%" height="180" frameborder="0" scrolling="auto" src='${ctx}/ultrabpp/develop/stepFieldList.action?baseSchema=${baseSchema}&stepNo=${stepNo}'></iframe>
			<fieldset class="fieldset_style" style="width:98%">
				<legend>
					<eoms:lable name="bpp_develop_fix_stepAction"/>
				</legend>
				<div class='page_div_bg'>
					<div class='page_div'>
						<eoms:operate cssclass="page_ok_button" id="com_btn_ok" onmouseover="this.className='page_ok_button_hover'" onmouseout="this.className='page_ok_button'" onclick="addAction();" text='dm_btn_add' />
					</div>
				</div>
				<iframe width="100%" height="200" frameborder="0" scrolling="auto" src='${ctx}/ultrabpp/develop/actionList.action?baseSchema=${baseSchema}&stepNo=${stepNo}'></iframe>
			</fieldset>
		</div>
		</div>
	</body>
</html>
