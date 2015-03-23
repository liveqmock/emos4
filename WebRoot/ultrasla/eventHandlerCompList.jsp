<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8"%>
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
			function edit(id) {
				openwindow('${ctx}/eventHandlerComp/editEventHandlerComp.action?id='+id,'',800,500);
			}
		    function del() {
				var objs = document.getElementsByName("checkid");
				//alert(objs);		
				var wfString = "";		
				for (var i = 0; i < objs.length; i++) {
			        if (objs[i].checked == true) {
						wfString += "," + objs[i].value;
					}
				}
				wfString = wfString.substring(1);
				if(wfString == ''){
			       alert("请选择要删除的对象！！！");
		           return false;
			    }
				if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && wfString != '') {
					var src = '${ctx}/eventHandlerComp/delEventHandlerComp.action?delIds='+wfString;
					document.forms[0].action = src;
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
		<dg:datagrid  var="eventHandlerComp" sqlname="SQL_WF_EventHandlerCompList.query" title="${nodePath}">
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
					onclick="openwindow('${ctx}/eventHandlerComp/addEventHandlerComp.action','',800,500);"
					text="com_btn_add" operate="com_add" />
				<eoms:operate onmouseout="this.className='page_del_button'" cssclass="page_del_button"
				    text="com_btn_delete" onmouseover="this.className='page_del_button_hover'"        
				    id="com_btn_del" onclick="del()" operate="com_delete" />
	  		</dg:lefttoolbar>
	  		<dg:condition>
				<table class="serachdivTable">
					<tr>
						<td colspan="6" align="center">
							<input type="submit" name="button" id="submitButton" value="<eoms:lable name="com_btn_lookUp" />"
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
		    		<th width="30"><input type="checkbox" onclick="checkAll('checkid')"/></th>
					<th>组件名称</th>
					<th>组件类型</th>
					<th>组件执行类型</th>
					<th>状态</th>
				</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
			
				<tr class="${agency_row}" style="cursor: hand">
					<td><input name="checkid" type="checkbox" value='${pid}'/></td>
					<td onclick="edit('${pid}');">${componentname}</td>
					<td onclick="edit('${pid}');">${componenttype}</td>
					<td onclick="edit('${pid}');"><eoms:dic dictype="dynamicconchecktype" value="${compexetype}"/></td>
					<td onclick="edit('${pid}');"><eoms:dic dictype="status" value="${status}"/></td>					
				</tr>
			</dg:gridrow>
		</dg:datagrid>			
	</body>
</html>