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
				src = '${ctx}/notice/createNotice.action';
				window.open(src,'','width=1000,height=470,top=150,left=200,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
			}
			
			//修改流程分类
			function edit(noticeId){
				var src = '${ctx}/notice/editNotice.action?noticeId='+noticeId;
				window.open(src,'','width=1000,height=450,top=100,left=200,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
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
					var src = '${ctx}/notice/delNotice.action?delIds='+wfString;
					window.open(src,'_self','width=400,height=290,top=50,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
				}
			}
		</script>
	</head>
	<body>
	  	<dg:datagrid  var="noitceList" title="${nodePath}" action="" sqlname="SQL_NOITCE_List.query" cachemode="sql">
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
	    		<th width="9%">发布人</th>
	    		<th>公告主题</th>
	    		<th width="12%">发布时间</th>
	    		<th width="12%">生效时间</th>
	    		<th width="12%">失效时间</th>
	    		<th width="15%">发布部门</th>
	    		<th width="15%">发布组</th>
	    		<th width="12%">公告状态</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${noitceList_row}">
				<td><input name="checkid" type="checkbox" value='${noticeid}'></input></td>
		        <td><a href="javascript:;" onclick="edit('${noticeid}');">${creatorname}</a></td>
		        <td><a href="javascript:;" onclick="edit('${noticeid}');">${noticetitle}</a></td>
		        <td><a href="javascript:;" onclick="edit('${noticeid}');"><eoms:date value="${noticecreatetime}"/></a></td>
		        <td><a href="javascript:;" onclick="edit('${noticeid}');"><eoms:date value="${noticestarttime}"/></a></td>
		        <td><a href="javascript:;" onclick="edit('${noticeid}');"><eoms:date value="${noticelosttime}"/></a></td>
		        <td><a href="javascript:;" onclick="edit('${noticeid}');">${noticescope}</a></td>
		        <td><a href="javascript:;" onclick="edit('${noticeid}');">${noticescopegroup}</a></td>
		        <!--<td><a href="javascript:;" onclick="edit('${noticeid}');">${noticestatus}</a></td>-->
		        <td>
			        <c:if test="${noticestatus == '4'}">
			        	已删除
					</c:if>
			        <c:if test="${noticestatus != '4' && currentTime < noticestarttime}">
			        	未生效
					</c:if>
			        <c:if test="${noticestatus != '4' && currentTime >= noticestarttime && currentTime <= noticelosttime}">
			        	已生效
					</c:if>
			        <c:if test="${noticestatus != '4' && currentTime > noticelosttime}">
			        	失效
					</c:if>
				</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
	</body>
</html>
