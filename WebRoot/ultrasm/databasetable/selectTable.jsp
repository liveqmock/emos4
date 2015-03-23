<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_chooseDbTable"/></title>
		<base target="_self"/>
		<script language="javascript">
			window.onresize = function() 
			{
			  setCenter(0, LAYOUT_LIST_RIGHT);
			}
			window.onload = function() 
			{
			  setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
			}
			function selConfirm()
			{
				var radioArr = document.getElementsByName('radio_res');
				var resname = '';
				for(var i=0;i<radioArr.length;i++)
				{
					if(radioArr[i].checked == true)
					{
						resname = radioArr[i].value;
						break;
					}
				}
				if(resname=='')
				{
					alert('<eoms:lable name="sm_msg_excelCfgTbennameConstraint"/>！');
					return;
				}
				var callerWindowObj = dialogArguments;
				callerWindowObj.document.getElementById('excel.enname').value = resname;
				window.close();
			}
		</script>
	</head>
	<body>
		<dg:datagrid var="tbinfo" sqlname="SQL_SM_TableList.query" title="sm_t_chooseDbTable">
			<dg:lefttoolbar>
				<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="showsearch()" text="com_btn_search" />
				<li class="page_ok_button" onmouseover="this.className='page_ok_button_hover'" onmouseout="this.className='page_ok_button'" 
					onclick="selConfirm();"><eoms:lable name="com_btn_confirm"/></li>
		        <li class="page_del_button" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" 
		        	onclick="window.close()"><eoms:lable name="com_btn_cancel"/></li>
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
					<th width="28">
						<eoms:lable name="com_btn_choose" />
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
						<input name="radio_res" type="radio" value='${enname}'></input>
					</td>
					<td>
						${enname}
					</td>
					<td>
						${cnname}
					</td>
					<td>
						<eoms:dic value="${tabletype}" dictype="tabletype"></eoms:dic>
					</td>
					<td>
						${remark}
					</td>
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
