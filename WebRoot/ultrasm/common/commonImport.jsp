<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<%
	String xmlname = request.getParameter("xmlname");
	String imptype = request.getParameter("imptype");
	if(xmlname == null)
		xmlname = "";
	if(imptype == null)
		imptype = "";
%>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<title><eoms:lable name="sm_t_impExcel"/></title>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_FORM_OPEN);
		}
		function importExcelData()
		{
			if(document.getElementById("doc").value == "")
			{
				alert("<eoms:lable name='sm_msg_chooseExcel'/>！");
				return;
			}
			var filevalues = document.getElementById("doc").value.split("\\");
			var frontname = filevalues[filevalues.length-1].split(".");
			if( frontname[1] == "xls" || frontname[1] == "XLS")
			{
				importform.action = "${ctx}/excelManager/importExcel.action?xmlname=<%=xmlname%>";
				importform.submit();
			}
			else
			{
				alert("<eoms:lable name='sm_msg_chooseWrong'/>！");
				return;
			}
		}
		function downloadImportTemplate()
		{
			importform.action = "${ctx}/excelManager/downloadImportTemplate.action?imptype=<%=imptype%>&xmlname=<%=xmlname%>";
			importform.submit();
		}
	</script>
  </head>
  <body>
	<form id="importform" method="post" enctype="multipart/form-data">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2"><eoms:lable name="sm_t_impExcel"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center" style="text-align: center; margin: 0 auto;">
			<br/>
				<table class="tableborder">
					<tr>
						<td width="80" align="right"><eoms:lable name="sm_lb_chooseFile"/>：</td>
						<td><input type="file" id="doc" name="doc" style="width:100%" accept="application/msexcel"/></td>
					</tr>
				</table>
			</div>
			<div class="add_bottom">
				<a href="javascript:;" style="text-decoration:underline" onclick="downloadImportTemplate();">☞<eoms:lable name='sm_btn_downloadTemplate'/></a>
				<input type="button" value="<eoms:lable name='com_btn_import'/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="importExcelData();" />
				<input type="button" value="<eoms:lable name='com_btn_cancel'/>"
					class="cancel_button"
					id="cancelButton"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
			</div>
		</div>
	</form>
  </body>
</html>
