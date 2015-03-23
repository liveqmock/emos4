<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0,56);
		}
		window.onload = function() 
		{
		  setCenter(0,56);
		  changeRow_color("tab");
		}
		
		/**
		 *新建或修改
		 */
		 function addOrUpdate(id){
		 	var baseSchema = '${baseSchema}';
		 	var baseName = '${baseName}';
		 	window.open('${ctx}/sheetrole/toNewOrUpdate.action?baseSchema='+baseSchema + '&dimension.dimensionid='+id,'_blank', 'height=290px,width=500px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
			}
		 
		 /**
		  *	删除
		  */
		  function del(){
		  	getCheckValue("checkid");
			var ids = document.getElementsByName('var_selectvalues').value;
			if(ids == ''){
				alert('请选择');
			} else {
				if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && ids != '') {
					document.getElementById('ids').value = ids;
					document.getElementById('sheetform').target = "_self";
					document.getElementById('sheetform').action = '${ctx}/sheetrole/deleteDimensions.action';
					document.getElementById('sheetform').submit();
				}
			}
		  }
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="waitingdealsheet"   title="${nodePath}" action="" sqlname="SQL_WF_SheetDimension.query">
	  	<dg:lefttoolbar>
	  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="addOrUpdate('');" text="com_btn_add" />
	  		  <eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button" text="com_btn_delete" onmouseover="this.className='page_del_button_hover'" id="com_btn_del" onclick="del();"/>
		      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'" text="com_btn_help"/>
  		</dg:lefttoolbar>
	  	<dg:gridtitle>
		    	<tr>
					<th width="5%"><input id="checkidAll" name="checkidAll" type="checkbox"  onclick="checkAll('checkid')"></input></th>
		    		<th width="20%"><eoms:lable name="wf_sheet_dimension_dimensionname" /></th>
		    		<th width="20%"><eoms:lable name="wf_sheet_dimension_basename" /></th>
		    		<th width="25%"><eoms:lable name="wf_sheet_dimension_dictschema" /></th>
		    		<th width="30%"><eoms:lable name="wf_sheet_dimension_dictfiledname" /></th>
		    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}" style="cursor: hand">
				<td><input id="checkid" type="checkbox" value='${id}'></input></td>
		        <td onclick="addOrUpdate('${id}');">${dimensionname}</td>
		        <td onclick="addOrUpdate('${id}');">${basename}</td>
		        <td onclick="addOrUpdate('${id}');">${dictschema}</td>
		        <td onclick="addOrUpdate('${id}')">${dictfieldname}</td>
		     </tr>
		</dg:gridrow>
	</dg:datagrid>
		
		
		<form id="sheetform" action="${ctx}/sheetrole/toNewOrUpdate.action" target="_blank">
			<input type="hidden" name="baseSchema" id="baseSchemaId" value="${baseSchema}" />
			<input type="hidden" name="baseName" id="baseNameId" value="${baseName}" />			
			<input type="hidden" name="dimension.dimensionid" id="dimension.dimensionid" />
			<input type='hidden' name='ids' id='ids' />
		</form>
  </body>
</html>
