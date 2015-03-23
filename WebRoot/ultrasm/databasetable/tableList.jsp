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
			function tableDel()
			{
				getCheckValue("checkid");
				var ennames = document.getElementsByName('var_selectvalues').value;
				document.forms[0].var_selectvalues.value = ennames;
				if(ennames=='')
				{
					alert('<eoms:lable name="sm_msg_chooseOpObject" />！');
					return;
				}
				if(confirm("<eoms:lable name="com_btn_confirm_del" />"))
				{
					document.forms[0].action = '${ctx}/tableManager/delTable.action';
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="tbinfo" sqlname="SQL_SM_TableList.query" title="${nodePath}">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="showsearch()" text="com_btn_search" />
				<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
					onmouseover="this.className='page_refresh_button_hover'"
					onmouseout="this.className='page_refresh_button'"
					onclick="location.reload();" text="com_btn_refresh" />
				<eoms:operate cssclass="page_add_button" id="com_btn_add"
					onmouseover="this.className='page_add_button_hover'"
					onmouseout="this.className='page_add_button'" 
					onclick="openwindow('${ctx}/tableManager/load.action','',800,500);"
					text="com_btn_add" operate="com_add_op" />
				<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button"
				    text="com_btn_delete" onmouseover="this.className='page_del_button_hover'"        
				    id="com_btn_del" onclick="tableDel()"/>
				<eoms:operate cssclass="page_import_button" id="com_btn_import"
					onmouseover="this.className='page_import_button_hover'"
					onmouseout="this.className='page_import_button'" text="com_btn_import"
					onclick="getCheckValue('checkid')" operate="com_imp_op" />
				<eoms:operate cssclass="page_output_button" id="com_btn_exp"
					onmouseover="this.className='page_output_button_hover'"
					onmouseout="this.className='page_output_button'" text="com_btn_export"
					operate="com_exp_op" />
				<eoms:operate cssclass="page_help_button" id="com_btn_help"
					onmouseover="this.className='page_help_button_hover'"
					onmouseout="this.className='page_help_button'" text="com_btn_help" />
			</dg:lefttoolbar>
			<dg:condition>
				<table class="serachdivTable">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" name="button" value="<eoms:lable name="com_btn_lookUp" />"
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
					<th width="25">
						<input name="checkidall" type="checkbox"
							onclick="checkAll('checkid')"></input>
					</th>
					<th width="20%">
						<eoms:lable name="sm_lb_tbEnname" />
					</th>
					<th width="20%">
						<eoms:lable name="sm_lb_tbCnname" />
					</th>
					<th width="20%">
						<eoms:lable name="sm_lb_tbType" />
					</th>
					<th>
						<eoms:lable name="com_lb_remark" />
					</th>
				</tr>
			</dg:gridtitle>
			<dg:gridrow>
				<tr class="${tbinfo_row}">
					<td>
						<input name="checkid" type="checkbox" value='${enname}'></input>
					</td>
					<td>
						<a href="javascript:;" onclick="openwindow('${ctx}/tableManager/load.action?enname=${enname}','',800,500);">${enname}</a>
					</td>
					<td>
						<a href="javascript:;" onclick="openwindow('${ctx}/tableManager/load.action?enname=${enname}','',800,500);">${cnname}</a>
					</td>
					<td>
						<a href="javascript:;" onclick="openwindow('${ctx}/tableManager/load.action?enname=${enname}','',800,500);">
							<eoms:dic value="${tabletype}" dictype="tabletype"></eoms:dic>
						</a>
					</td>
					<td>
						<a href="javascript:;" onclick="openwindow('${ctx}/tableManager/load.action?enname=${enname}','',800,500);">${remark}</a>
					</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
