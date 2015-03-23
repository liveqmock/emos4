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
		function transStatus(type)
		{
			getCheckValue("checkid");
			var temp = document.getElementsByName('var_selectvalues').value;
			document.forms[0].var_selectvalues.value = temp;
			if(temp=='')
			{
				alert('<eoms:lable name="sm_msg_chooseOpObject"/>!');
				return;
			}
			if(confirm("<eoms:lable name='sm_msg_confirmStatus'/>？"))
			{
				document.forms[0].action = "${ctx}/resUrlManager/transferStatus.action?transType="+type;
				document.forms[0].submit();
			}
		}
		function delResUrl()
		{
			getCheckValue("checkid");
			var ids = document.getElementsByName('var_selectvalues').value;
			document.forms[0].var_selectvalues.value = ids;
			if(ids=='')
			{
				alert('<eoms:lable name="sm_msg_chooseOpObject" />！');
				return;
			}
			if(confirm("<eoms:lable name="com_btn_confirm_del" />"))
			{
				document.forms[0].action = '${ctx}/resUrlManager/delResUrl.action';
				document.forms[0].submit();
			}
		}
	</script>
  </head>
  
  <body>
	  	<dg:datagrid  var="resOpUrl" title="${nodePath}" sqlname="SQL_SM_ResOpUrlList.query">
	  		<dg:lefttoolbar>
	  	  		<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  			onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
  		  		<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  		  			onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  		<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
  		  			onmouseout="this.className='page_add_button'" 
  		  			onclick="openwindow('${ctx}/resUrlManager/resUrlLoad.action','',800,500)"
  		  			text="com_btn_add" operate="com_add"/>
  		  		<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button"
				    text="com_btn_delete" onmouseover="this.className='page_del_button_hover'"        
				    id="com_btn_del" onclick="delResUrl();" operate="com_delete"/>
  		  		<eoms:operate cssclass="page_enabled_button" id="" onmouseover="this.className='page_enabled_button_hover'" 
  		 			onmouseout="this.className='page_enabled_button'" onclick="transStatus('inuse')"  text="com_btn_inuse" operate="com_inuse"/>
  		 		<eoms:operate cssclass="page_stop_button" id="" onmouseover="this.className='page_stop_button_hover'" 
  		 			onmouseout="this.className='page_stop_button'" onclick="transStatus('outuse')"  text="com_btn_outuse" operate="com_stop"/>
	      		<eoms:operate cssclass="page_help_button" id="com_btn_help" onmouseover="this.className='page_help_button_hover'" 
	      			onmouseout="this.className='page_help_button'" text="com_btn_help"/>
	  		</dg:lefttoolbar>
	  		<dg:condition>
		  		<table class="serachdivTable" style="width:60%" align="center">
					<tr>
						<td colspan="4" align="center">
							<input type="submit" name="button" id="button" value="<eoms:lable name="com_btn_lookUp" />"
								class="searchButton"
								onmouseover="this.className='searchButton_hover'"
								onmouseout="this.className='searchButton'"/>
							<input type="reset" name="button2" id="button2" value="<eoms:lable name="com_btn_reset" />"
								class="ResetButton"
								onmouseover="this.className='ResetButton_hover'"
								onmouseout="this.className='ResetButton'" />
						</td>
					</tr>
				</table>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="25"><input id="checkidAll" name="checkidAll" type="checkbox"  onclick="checkAll('checkid')"></input></th>
		    		<th width="15%"><eoms:lable name='sm_lb_resourceName'/></th>
		    		<th width="15%"><eoms:lable name='sm_lb_operateName'/></th>
		    		<th width="25%"><eoms:lable name='sm_lb_URL'/></th>
		    		<th width="10%"><eoms:lable name='com_lb_status'/></th>
		    		<th><eoms:lable name='com_lb_desc'/></th>
		    	</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${resOpUrl_row}">
					<td><input id="checkid" type="checkbox" value='${pid}'></input></td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resUrlManager/resUrlLoad.action?resUrlID=${pid}','',800,500)">${resname}</a></td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resUrlManager/resUrlLoad.action?resUrlID=${pid}','',800,500)">${opname}</a></td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resUrlManager/resUrlLoad.action?resUrlID=${pid}','',800,500)">${url}</a></td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resUrlManager/resUrlLoad.action?resUrlID=${pid}','',800,500)">
			        		<eoms:dic value="${status}" dictype="status"/>
			        	</a>
			        </td>
			        <td><a href="javascript:;" onclick="openwindow('${ctx}/resUrlManager/resUrlLoad.action?resUrlID=${pid}','',800,500)">${remark}</a></td>
			    </tr>
			</dg:gridrow>
		</dg:datagrid>
  </body>
</html>
