<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ taglib uri="/WEB-INF/workflow" prefix="wf"%>
	<script src="${ctx}/workflow/js/util.js"></script>
	<script type="text/javascript">
		window.onresize = function() 
		{
		  setCenter(0,56);
		}
		window.onload = function() 
		{
		  setCenter(0,56);
		  changeRow_color("tab");
		  init();
		  textLength('desc',20);
		  textLength('name',20);
		}
		
		function init(){
		  var roleCode = '${roleCode}';
		  document.getElementsByName('WFRole')[0].value = roleCode;
		}
		
		/**
		 *添加修改用户
		 */
		 function userManage(id){
		 	var roleCode = '${roleCode}';
		 	var childRoleIds = id;
		 	window.open('${ctx}/sheetrole/toSetRoleUser.action?roleCode=' + roleCode + '&childRoleId=' + childRoleIds,'_blank', 'height=400px,width=700px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
		}
			
		 function groupManage(id){
		 	var roleCode = '${roleCode}';
		 	var childRoleIds = id;
		 	window.open('${ctx}/sheetrole/toSetRoleGroup.action?roleCode=' + roleCode + '&childRoleId=' + childRoleIds,'_blank', 'height=500px,width=300px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
			}
		
		/**
		 * 批量添加用户
		 */
		 function batchAddUser(){
		 	var roleCode = '${roleCode}';
		 	
		 	getCheckValue("checkid");
			var ids = document.getElementsByName('var_selectvalues').value;
			
		 	var childRoleIds = ids;
		 	
		 	if(childRoleIds == ''){
		 		alert('<eoms:lable name="sm_msg_chooseOpObject"/>!');
				return;
		 	}else{
		 		window.open('${ctx}/sheetrole/toSetRoleUser.action?roleCode=' + roleCode + '&childRoleId=' + childRoleIds,'_blank', 'height=400px,width=700px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
		 	}
		 }
		 
		 /**
		  * 批量添加角色细分
		  */
		 function batchAddDimension(){
		 	var roleCode = '${roleCode}';
		 	window.open('${ctx}/childrole/toChildRoleSetting.action?roleCode=' + roleCode,'_blank', 'height=600,width=1000,top=10,left='+(window.screen.width/2-500)+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
		 }
		 
		 /**
		  *	删除
		  */
		  function del(){
		  	getCheckValue("checkid");
			var ids = document.getElementsByName('var_selectvalues').value;
			if(ids == ''){
				alert('<eoms:lable name="sm_msg_chooseOpObject"/>');
				return;
			}
			
			if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && ids != '') {
				document.getElementById('childRoleIds').value = ids;
				document.getElementById('sheetform').target = "_self";
				document.getElementById('sheetform').action = '${ctx}/sheetrole/removeChildRole.action';
				document.getElementById('sheetform').submit();
			}
		  }
		  
		  /**
		  	*下拉框选择事件
		  	*/
		 function toSelect(roleCode){
		 	if(roleCode != ''){
			 	var baseSchema = '${baseSchema}';
			 	var baseVersion = '${baseVersion}';
			  	window.open("${ctx}/sheetrole/getChildRoleByRoleCode.action?roleCode=" + roleCode + "&baseSchema=" + baseSchema + "&baseVersion=" +baseVersion,'_self');
		  	}
		  }
		  
		  /**
		   * 添加角色细分
		   */
		   function addChildRole(){
				var roleCode = '${roleCode}';
			  	window.open("${ctx}/sheetrole/toAddOrEditChildRole.action?roleCode=" + roleCode,'_blank', 'height=300px,width=550px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
		  	}
		  
		  /**
		   *修改角色细分
		   */
		   function editChildRole(childRoleId){
		   	window.open("${ctx}/sheetrole/toAddOrEditChildRole.action?childRoleId=" + childRoleId,'_blank', 'height=350px,width=550px,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no');
		  }
	</script>
  </head>
  
  <body>
  	<dg:datagrid var="childRoleView"  title="角色细分列表" items="${childRoleViewList}">
	  	<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="batchAddDimension();" text="com_btn_add" />
  		  <eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button" text="com_btn_delete" onmouseover="this.className='page_del_button_hover'" id="com_btn_del" onclick="del();"/>
  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="batchAddUser();" text="wf_sheet_batchadduser" />
	      <eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'" text="com_btn_help"/>
  		  <li><eoms:select name="WFRole" customSql="select t.rolecode as code,t.rolename as name from bs_t_wf_workflowrole t where t.baseschema = '${baseSchema}' and t.baseversion='${baseVersion}'" style="width:20%" onChangeFun="toSelect(this.value);" /></li>
  		</dg:lefttoolbar>
	  	<dg:condition>
		  	<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="hidden" name="ids" id="userID" value=""/>
					<input type="hidden" name="transType" value=""/>
		          	<input type="submit" name="searchUser" onclick="querysubmit()" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
		          </td>
		        </tr>
		      </table>
			</div>
	  	</dg:condition>
	  	<dg:gridtitle>
	    	<tr>
	    		<th width="5%"><input id="checkidAll" name="checkidAll" type="checkbox"  onclick="checkAll('checkid')"></input></th>
	    		<th width="30%"><eoms:lable name="wf_sheet_childrolename" /></th>
	    		<th width="35%"><eoms:lable name="wf_sheet_dimensions" /></th>
	    		<th width="5%"><eoms:lable name="wf_sheet_matchcount" /></th>
	    		<th width="15%">虚拟组</th>
	    		<th width="15%"><eoms:lable name="wf_sheet_usernames" /></th>
	    	</tr>
	    </dg:gridtitle> 
		<dg:gridrow>
			<tr class="${childRoleViewList_row}">
				<td><input id="checkid" type="checkbox" value='${childRoleView.childRoleId}'></input></td>
		        <td><a href="javascript:;" onclick="editChildRole('${childRoleView.childRoleId}');">${childRoleView.childRoleName}</a></td>
		        <td><a href="javascript:;" onclick="editChildRole('${childRoleView.childRoleId}');">${childRoleView.dimensionValue}</a></td>
		        <td><a href="javascript:;" onclick="editChildRole('${childRoleView.childRoleId}');">${childRoleView.matchCount}</a></td>	
		        <td>
		        	<a href="javascript:void(0)" id="${childRoleView.childRoleId}" onclick="groupManage(this.id)">
		        	${childRoleView.depName}
		        	<c:if test="${childRoleView.depName==''}" ><b>[+]</b></c:if>
		        	</a>
		        </td>
		         <td>
		        	<a href="javascript:void(0)" id="${childRoleView.childRoleId}" onclick="userManage(this.id)">
		        	${childRoleView.fullNames}
		        	<c:if test="${childRoleView.fullNames==''}" ><b>[+]</b></c:if>
		        	<!--<wf:childroleusers childroleid="${childroleid}" />-->
		        	</a>
		        </td>
		     </tr>
		</dg:gridrow>
	</dg:datagrid>
	<form id="sheetform" action="${ctx}/sheetrole/toNewOrUpdate.action" target="_blank">
		<input type="hidden" name="childRoleIds" id="childRoleIds" />
		<s:hidden name="roleCode" />
		<s:hidden name="baseSchema" />
		<s:hidden name="baseVersion" />
	</form>
  </body>
</html>	
