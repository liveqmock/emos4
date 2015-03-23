<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>   
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
		}
	</script>
  </head>
  <body>
  	<dg:datagrid var="">
  		<dg:lefttoolbar>
  			<eoms:operate cssclass="page_excel_import_button" id="com_btn_import" onmouseover="this.className='page_excel_import_button_hover'" 
     			onmouseout="this.className='page_excel_import_button'" text="事件工单导入"  onclick="importExcel('${ctx}','IMP_SM_INCIDENT_ExcelSheet.xml');" operate="com_imp"/>
     		<eoms:operate cssclass="page_excel_import_button" id="com_btn_import" onmouseover="this.className='page_excel_import_button_hover'" 
     			onmouseout="this.className='page_excel_import_button'" text="服务请求导入"  onclick="importExcel('${ctx}','IMP_SM_SERVICEREQUEST_ExcelSheet.xml');" operate="com_imp"/>
  		</dg:lefttoolbar>
	</dg:datagrid>
  </body>
</html>
