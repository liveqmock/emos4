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
	
			function add(){
				src = '${ctx}/notice/createNoticeLevel.action?noticeLevelStr=${noticeLevelStr}';
				window.open(src,'','width=350,height=450,top=150,left=500,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
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
				if (confirm('<eoms:lable name="com_btn_confirm_del"/>') && wfString != '') {
					var src = '${ctx}/notice/delNoticeLevel.action?noticeLevelStr=${noticeLevelStr}&delIds='+wfString;
					window.open(src,'_self','width=400,height=290,top=50,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
				}
			}
		</script>
	</head>
	<body>
	  	<dg:datagrid  var="noitceList" title="${nodePath}" action="" sqlname="SQL_NOITCELEVEL_List.query" cachemode="sql">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  	  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  			  onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
  		  <c:if test="${noticeLevelStr != null}">
  		  <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
  		      onmouseout="this.className='page_add_button'" 
  		      onclick="add();"
  		      text="com_btn_add" operate="com_add"/>
  		  </c:if>
  		  <eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
  		  	  onmouseout="this.className='page_del_button'" onclick="del();" text="com_btn_delete" operate="com_delete"/>
  		</dg:lefttoolbar>
  		<dg:condition>
	  		<div align="center">
		      <table  class="serachdivTable" style="width:80%">
		        <tr>
		          <td colspan="6" align="center">
		          	<input type="hidden" name="depIds" id="depIds" value=""/>
	  				<input type="hidden" name="transType" value=""/>
		          	<input type="submit" name="searchDep" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
		        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
		          </td>
		        </tr>
		      </table>  
	       </div>		
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th width="25"><input name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
	    		<th width="10%">公告级别</th>
	    		<th width="15%">名称</th>
	    		<th>部门名称</th>
	    		<th width="5%">类别</th>
	    		<th width="10%">创建人</th>
	    		<th width="15%">创建时间</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${noitceList_row}">
				<td><input name="checkid" type="checkbox" value='${pid}'></input></td>
		        <td><eoms:dic value="${noticelevel}" dictype="noticeLevel"></eoms:dic></td>
		        <td>${username}</td>
		        <td>${depname}</td>
		        <td>${recivertype}</td>
		        <td>${creatorname}</td>
		        <td><eoms:date value="${createtime}"></eoms:date></td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
	</body>
</html>
