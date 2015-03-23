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
				jQuery.post('${ctx}/business/delDealUserConfig.action',
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
		
		
		function toAdd(ids){
			if(ids == null || ids == ''){
				ids = '';
			}
			openwindow('${ctx}/business/addChangeDealUserConfig.action?ids=' + ids + '&&userMap.CHANGETYPE=${userMap.changeType}&&userMap.BASESCHEMA=${userMap.baseSchema}','',800,400);
		}
	</script>
  </head>
  <body>
  	<dg:datagrid var="userinfo" title="变更发布工单处理人配置" action="" sqlname="SQL_CDB_DealUserConfig.query" cachemode="sql">
  		<dg:lefttoolbar>
  	  		<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
  	  			onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
	  		<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
	  			onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
	  		<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
	  			onmouseout="this.className='page_add_button'" 
	  			onclick="toAdd();"
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
	    		<th>变更分类</th>
	    		<th>变更对象</th>
	    		<th>变更类型</th>
	    		<th>修订人</th>
	    		<th>一级审批人</th>
	    		<th>二级审批人</th>
	    		<th>实施人</th>
	    		<th>验证人</th>
	    		<th>描述</th>
	    		<th>是否预授权</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${userinfo_row}">
				<td><input id="checkid" type="checkbox" value='${pid}'></input></td>
		        <td onclick="toAdd('${pid}');">${changesort}</td>
		        <td onclick="toAdd('${pid}');">${bussystem}</td>
		        <td onclick="toAdd('${pid}');">${changetype}</td>
		        <td>${edituser}</td>
		        <td>${auditusers}</td>
		        <td>${auditusers_2}</td>
		        <td>${excuteuser}</td>
		        <td>${testuser}</td>
		        <td>${configdesc}</td>
		        <td>${pre_authorized_flag}</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>






	</body>
</html>
