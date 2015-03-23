<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() 
		{
		}
		window.onload = function() 
		{
		  setCenter(0,26);
		  changeRow_color("tab");
		}
		
		/**
		 *获取角色细分
		 */
		 function getChildRole(roleCode, baseVersion){
		 	document.getElementById('roleCode').value = roleCode;
		 	document.getElementById('baseVersion').value = baseVersion;
		 	document.getElementById('sheetform').submit();
		 }
		 
		 /**
		  *维度配置
		  */
		  function dimensionEdit(baseSchema, roleCode){
		 	window.open('${ctx}/sheetrole/toEidtDemension.action?roleCode='+roleCode+'&baseSchema='+baseSchema,'_blank', 'height=400px,width=600px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
		  }
		  
		  function addWorkflowRole(){
		  	window.open('${ctx}/sheetrole/addWorkflowRole.action','_blank', 'height=400px,width=600px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
		  }
		  
		  function editWorkflowRole(id){
		  	window.open('${ctx}/sheetrole/editWorkflowRole.action?roleId='+id,'_blank', 'height=400px,width=600px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
		  }
		  
		  function delWorkflowRole(){
		  	getCheckValue("checkid");
			var ids = document.getElementsByName('var_selectvalues').value;
			if(ids == ''){
				alert('请选择');
			} else {
				if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && ids != '') {
					document.getElementById('ids').value = ids;
					document.getElementById('sheetform').target = "_self";
					document.getElementById('sheetform').action = '${ctx}/sheetrole/removeWorkflowRole.action';
					document.getElementById('sheetform').submit();
				}
			}
		  }
	</script>
  </head>
  
  <body>
	<dg:datagrid  var="wf_sheet_role"   title="${nodePath}" action="" sqlname="SQL_WF_SheetRole.query">
		<dg:lefttoolbar>
			<s:if test="%{baseSchema == null or baseSchema == ''}">
				<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="addWorkflowRole()" text="com_btn_add" />
				<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button" text="com_btn_delete" onmouseover="this.className='page_del_button_hover'" id="com_btn_del" onclick="delWorkflowRole();"/>
			</s:if>
		</dg:lefttoolbar>
	  	<dg:gridtitle>
		    	<tr>
		    		<s:if test="%{baseSchema == null or baseSchema == ''}">
		    			<th width="5%"><input id="checkidAll" name="checkidAll" type="checkbox"  onclick="checkAll('checkid')"></input></th>
		    		</s:if>
		    		<th width="15%" align="center"><eoms:lable name="wf_sheet_rolecode" /></th>
		    		<th width="15%" align="center"><eoms:lable name="wf_sheet_rolename" /></th>
		    		<th width="15%" align="center"><eoms:lable name="wf_sheet_baseschema" /></th>
		    		<th width="15%" align="center"><eoms:lable name="wf_sheet_basename" /></th>
		    		<th width="20%" align="center"><eoms:lable name="wf_sheet_baseversion" /></th>
		    		<th width="10%" align="center"><eoms:lable name="sm_lb_operation" /></th>
		    		<s:if test="%{baseSchema == null or baseSchema == ''}">
		    			<th width="10%" align="center"></th>
		    		</s:if>
		    	</tr>
	    </dg:gridtitle>
		<dg:gridrow>
			<tr class="${waitingdealsheet_row}">
				<s:if test="%{baseSchema == null or baseSchema == ''}">
					<td><input id="checkid" type="checkbox" value='${roleid}'></input></td>
				</s:if>
		        <td><a href="javascript:;" onclick="getChildRole('${rolecode}', '${baseVersion}');">${rolecode}</a></td>
		        <td><a href="javascript:;" onclick="getChildRole('${rolecode}', '${baseVersion}');">${rolename}</a></td>
		        <td><a href="javascript:;" onclick="getChildRole('${rolecode}', '${baseVersion}');">${baseschema}</a></td>
		        <td><a href="javascript:;" onclick="getChildRole('${rolecode}', '${baseVersion}');">${basename}</a></td>
		        <td><a href="javascript:;" onclick="getChildRole('${rolecode}', '${baseVersion}');">${baseversion}</a></td>
		        <td><a id='${rolecode}' href="javascript:;" onclick="dimensionEdit('${baseschema}', this.id)"><B><eoms:lable name="wf_sheet_dimension_edit" /><B></a></td>
		        <s:if test="%{baseSchema == null or baseSchema == ''}">
		        	<td><a href="javascript:;" onclick="editWorkflowRole('${roleid}');">编辑</a></td>
		        </s:if>
		     </tr>
		</dg:gridrow>
	</dg:datagrid>
		<form id="sheetform" name="sheetform" action="${ctx}/sheetrole/getChildRoleByRoleCode.action" target="_self">
			<input type="hidden" name="roleCode" id="roleCode" />
			<input type="hidden" name="baseVersion" id="baseVersion" />
			<s:hidden name="baseSchema"></s:hidden>
			<input type='hidden' name='ids' id='ids' />
		</form>
  </body>
</html>
