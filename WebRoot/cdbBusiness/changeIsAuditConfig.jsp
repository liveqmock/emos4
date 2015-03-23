<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>   
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery.js"></script>
	<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
		}
		/*
		 转换用户的启用状态
		*/
		function add()
		{
			document.getElementById('configDiv').style.display = '';
		}
		
		
		//保存关联 
		function toSave(){
			var sort = document.getElementById('sort').value;
			var system = document.getElementById('system').value;
		
			if(sort != '' && system != ''){
				jQuery.post('${ctx}/business/addConfig.action',
					{
						busSystem:system,
						changeSort:sort
					},
					function(data){
						if(data == 'success'){
							document.getElementById('form1').submit();
						}else{
							alert(data);
						}
				    });
			}
			    
		}
		
		
		function del()
		{
			getCheckValue("checkid");
			var temp = document.getElementsByName('var_selectvalues').value;
			if(temp=='')
			{
				return;
			}
			if(confirm("确定删除?"))
			{
				jQuery.post('${ctx}/business/delConfig.action',
				{
					ids:temp
				},
				function(data){
					if(data == 'success'){
						document.getElementById('form1').submit();
					}else{
						alert(data);
					}
			    });
			}
		}
	</script>
  </head>
  <body>

		<DIV id='configDiv'	style="WIDTH: 100%;LEFT:10%;display:none;">
			变更分类：<eoms:dictTree name="sort" dicttype="chgSort" style="height:30px;width:300px;" checkboxes="2"></eoms:dictTree>
			业务系统：<eoms:dictTree name="system" dicttype="cdb_system" style="height:30px;width:300px;" checkboxes="2"></eoms:dictTree>
			<INPUT onclick='toSave();' style="HEIGHT: 24px; WIDTH: 70px" type=button value="保存" />
			<INPUT onclick='toCannel("configDiv");' style="HEIGHT: 24px; WIDTH: 70px" type=button value=取消 />
		</DIV>
		
  	<dg:datagrid var="userinfo" title="变更工单评审配置表（选中的为需要评审）" action="" sqlname="SQL_CDB_ChangeIsAudit.query" cachemode="sql">
  		<dg:lefttoolbar>
  	  		<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
  	  			onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
	  		<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
	  			onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
	  			onmouseout="this.className='page_add_button'" 
	  			onclick="add();"
	  			text="com_btn_add" operate="com_add"/>
			<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'"
				onmouseout="this.className='page_del_button'" onclick="del()" text="com_btn_delete" operate="com_delete"/>
  		</dg:lefttoolbar>
  		<dg:condition>
	  		<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="hidden" name="ids" id="pid" value=""/>
					<input type="hidden" name="transType" value=""/>
		          	<input type="submit" name="searchUser" id="searchUser" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
		          </td>
		        </tr>
		      </table>
		    </div>
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th width="25"><input id="checkidAll" name="checkidAll" type="checkbox"  onclick="checkAll('checkid')"/></th>
	    		<th width="25%">变更分类</th>
	    		<th>业务系统</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${userinfo_row}">
				<td><input id="checkid" type="checkbox" value='${pid}' <c:if test="${loginname=='Demo'}">disabled="disabled"</c:if>></input></td>
		        <td>${sort}</td>
		        <td>${system}</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>






	</body>
</html>
