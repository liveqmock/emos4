<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@ page contentType="text/html;charset=UTF-8"%>

	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>事件主体组件选择页面</title>
		<base target="_self"/>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0,30);
		}
		window.onload = function() 
		{
			setCenter(0,30);changeRow_color("tab");
		}			
	    function add()
	    {
			src = '${ctx}/eventDefine/addEventHandlerComp.action';
			window.open(src,'','width=600,height=430,top=150,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	    }	    
		function returnComponent(){
			var ids = "";
			var names = "";
			$(":checkbox[name='checkid']:checked").each(function(i){
				if(ids==""){
					ids = this.value;
					names = this.parentNode.parentNode.cells[1].innerHTML;
				}else{
					ids += "," + this.value;
					names += "," + this.parentNode.parentNode.cells[1].innerHTML;
					}
			});
			window.dialogArguments.document.getElementById("custnoticecomp").value = names;
			window.dialogArguments.document.getElementById("custnoticecompid").value = ids;
			//showModalDialog('${ctx}/eventHandlerComp/doEventHandlerComp.action?name=' temp',window,'help:no;center:true;scroll:no;status:no;dialogWidth:580px;dialogHeight:490px');
			window.close();
	    }
	</script>	
	</head>
	<body>
           <dg:datagrid  var="eventHandlerComp" sqlname="SQL_WF_SelEventHandlerComp.query" title="${nodePath}">
	  		<dg:lefttoolbar>
		  			<eoms:operate cssclass="page_search_button" id="com_btn_search"
					onmouseover="this.className='page_search_button_hover'"
					onmouseout="this.className='page_search_button'"
					onclick="showsearch()" text="com_btn_search" />
					<eoms:operate cssclass="page_refresh_button" id="com_btn_refresh"
					onmouseover="this.className='page_refresh_button_hover'"
					onmouseout="this.className='page_refresh_button'"
					onclick="returnComponent()" text="确定" />
				
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
		    		<th width="50">
						<input type="checkbox" onclick="checkAll('checkid')"></input>
					</th>
					<th><eoms:lable name="组件名称"/></th>
					<th><eoms:lable name="组件类型"/></th>
					<th><eoms:lable name="组件执行类型"/></th>
				</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
			
				<tr class="${agency_row}" style="cursor: hand">
					<td>
						<input name="checkid" type="checkbox" value='${pid}'></input>
					</td>
					<td>${componentname}</td>
					<td>${componenttype}</td>
					<td><eoms:dic dictype="dynamicconchecktype" value="${compexetype}"/></td>					
				</tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>