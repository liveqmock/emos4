<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);changeRow_color("tab");
			}
			function openwindow(url,name,iWidth,iHeight)
			{
				var url; 
				var name; 
				var iWidth; 
				var iHeight; 
				var iTop = (window.screen.availHeight-30-iHeight)/2; 
				var iLeft = (window.screen.availWidth-10-iWidth)/2; 
				window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
			}
			function fieldDel()
			{
				
					getCheckValue("checkid");
					var ids = document.getElementsByName('var_selectvalues').value;
					document.forms[0].var_selectvalues.value = ids;
					if(ids=='')
					{
						alert('<eoms:lable name="sm_msg_chooseOpObject" />！');
						return;
					}
					if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
					{
						document.forms[0].action = '${ctx}/tableManager/delField.action?tbEnname=${param.tbEnname}';
						document.forms[0].submit();
					}
			}
		</script>

	</head>
	<body>
	  <dg:datagrid  var="field" sqlname="SQL_SM_FieldList.query">
	  		<dg:lefttoolbar>
	  			<eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
		  			  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
		  		<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
		  			  onmouseout="this.className='page_add_button'"  
		  			  onclick="openwindow('${ctx}/tableManager/loadField.action?tbEnname=${param.tbEnname }&from=other','',800,500);"
		  			  text="com_btn_add" />
		  		<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button"
				    text="com_btn_delete" onmouseover="this.className='page_del_button_hover'"        
				    id="com_btn_del" onclick="fieldDel()"/>
	  		</dg:lefttoolbar>
	  		<dg:condition>
	  			<table class="add_user">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" value="<eoms:lable name="com_btn_lookUp"/>" class="searchButton" 
								onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" />
							<input type="reset" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton"
									onmouseover="this.className='ResetButton_hover'"
									onmouseout="this.className='ResetButton'"/>
						</td>
					</tr>
				</table>
				<div class="blank_tr"></div>
	  		</dg:condition>
	    	<dg:gridtitle>
		    	<tr>
					<th width="25"><input name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
					<th width="25%"><eoms:lable name="sm_lb_fieldEnname"/></th>
					<th width="25%"><eoms:lable name="sm_lb_fieldCnname"/></th>
					<th width="20%"><eoms:lable name="sm_lb_fieldDataType"/></th>
					<th><eoms:lable name="com_lb_desc"/></th>
		        </tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${field_row}">
					<td><input name="checkid" type="checkbox" value="${pid}"></input></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/tableManager/loadField.action?pid=${pid}&from=other','',800,500);">${field}</a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/tableManager/loadField.action?pid=${pid}&from=other','',800,500);">${fieldname}</a></td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/tableManager/loadField.action?pid=${pid}&from=other','',800,500);">
						<eoms:dic value="${fieldtype}" dictype="datatype"/>
						</a>
					</td>
					<td><a href="javascript:;" onclick="openwindow('${ctx}/tableManager/loadField.action?pid=${pid}&from=other','',800,500);">${remark}</a></td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
