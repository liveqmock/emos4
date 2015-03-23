<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_LIST_RIGHT);
		}
		window.onload = function() {
			setCenter(0, LAYOUT_LIST_RIGHT);
			changeRow_color("tab");
		}
		function edit(id){
			openwindow('${ctx}/bsTCmbcAlarmnote/viewBsTCmbcAlarmnote.action?pid='+id,'',1000,500);
        }

		function del(){
			var objs = document.getElementsByName("checkid");
			var wfString = "";
			for (var i = 0; i < objs.length; i++) {
				if (objs[i].checked == true) {
					wfString += "," + objs[i].value;
				}
			}
			wfString = wfString.substring(1);
			if(wfString == '') {
				alert("请选择要删除对象！！！");
				return false;
			}
			if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && wfString != '') {
				var src = '${ctx}/bsTCmbcAlarmnote/delTCmbcAlarmnote.action?statueIds='+wfString;
				document.forms[0].action = src;
				document.forms[0].submit();
			}
		}	
		
		function createAlarm(){//dfds24234sdfd;0,12ew1232;1,
			var objs = document.getElementsByName("checkid");
			var wfString = "";
			for (var i = 0; i < objs.length; i++) {//截取末尾','
				if (objs[i].checked == true) {
					wfString += "," + objs[i].value;
				}
			}
			wfString = wfString.substring(1);//dfds24234sdfd;0,12ew1232;1
			if(wfString == '') {
				alert("请选择要重新建单的记录!");
				return false;
			}
			var isStatue = wfString.split(",");
			for(var i=0;i<isStatue.length;i++)
			{	
				var j = isStatue[i]
				var m = j.split(";");
				if(m[1]==1)
				{
					alert("请将建单成功的记录过滤掉!");
					return false;
				}				
			}
			//dfds24234sdfd;0,dfds3erd234sdfd;0
			if (confirm('确认是否重新建单!') && wfString != '') {
				var src = '${ctx}/bsTCmbcAlarmnote/createAlarm.action?statueIds='+wfString;
				document.forms[0].action = src;
				document.forms[0].submit();
			}
		}	
	</script>
</head>
<body>
	<dg:datagrid var="eventDefine" sqlname="SQL_SM_BsTCmbcAlarmnote.query" title="${nodePath}">
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
				onclick="createAlarm();"
				text="重新建立" operate="com_add" />
			<eoms:operate onmouseout="this.className='page_del_button'"
				cssclass="page_del_button" text="com_btn_delete"
				onmouseover="this.className='page_del_button_hover'"
				id="com_btn_del" onclick="del()" operate="com_delete" />
		</dg:lefttoolbar>
		<dg:condition>
			<table class="serachdivTable">
				<tr>
					<td colspan="6" align="center">
						<input type="submit" name="button" id="submitButton"
							value="<eoms:lable name="com_btn_lookUp" />"
							class="searchButton"
							onmouseover="this.className='searchButton_hover'"
							onmouseout="this.className='searchButton'" />
						<input type="reset" name="button2" id="button2"
							value="<eoms:lable name="com_btn_reset" />" class="ResetButton"
							onmouseover="this.className='ResetButton_hover'"
							onmouseout="this.className='ResetButton'" />
					</td>
				</tr>
			</table>
		</dg:condition>
		<dg:gridtitle>
			<tr>
				<th width="30"><input type="checkbox" onclick="checkAll('checkid')"></input></th>
				<th>事件标题</th>
				<th>应用系统分类</th>
				<th>告警一级分类</th>
				<th>告警二级分类</th>
				<th>告警三级分类</th>
				<th>发生时间</th>   
				<th>是否建单成功</th> 
			</tr>
		</dg:gridtitle>
		<dg:gridrow>
			<tr class="${agency_row}" style="cursor: hand">
				<td><input name="checkid" type="checkbox" value='${pid};${iscreatesheet}'></input></td>
				<td onclick="edit('${pid}');">${alarmtitle}</td>
				<td onclick="edit('${pid}');">${alarmsystype}</td>
				<td onclick="edit('${pid}');">${alarmonetype}</td>
				<td onclick="edit('${pid}');">${alarmtwotype}</td>
				<td onclick="edit('${pid}');">${alarmthreetype}</td>
				<td onclick="edit('${pid}');"><eoms:date value="${alarmhappentime}" /></td>
				<td onclick="edit('${pid}');"><eoms:dic dictype="alarmFalg" value="${iscreatesheet}"/></td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
