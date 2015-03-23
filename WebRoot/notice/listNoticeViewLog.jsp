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
			//修改流程分类
			function view(noticeId){
				var src = '${ctx}/notice/viewNotice.action?noticeId='+noticeId;
				window.open(src,'','width=1000,height=450,top=100,left=200,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
			}
		</script>
	</head>
	<body>
	  	<dg:datagrid  var="noitceList" title="${nodePath}" action="" sqlname="SQL_NOITCEVIEWLOG_List.query" cachemode="sql">
  		<dg:lefttoolbar>
  		  <eoms:operate cssclass="page_search_button" id="com_btn_search" onmouseover="this.className='page_search_button_hover'" 
	  	  	  onmouseout="this.className='page_search_button'"  onclick="showsearch()" text='com_btn_search' />
  		  <eoms:operate cssclass="page_refresh_button" id="com_btn_refresh" onmouseover="this.className='page_refresh_button_hover'" 
  			  onmouseout="this.className='page_refresh_button'" onclick="location.reload();" text="com_btn_refresh"/>
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
	    		<th width="15%">发布人</th>
	    		<th>公告主题</th>
	    		<th width="12%">生效时间</th>
	    		<th width="12%">生效时间</th>
	    		<th width="12%">失效时间</th>
	    		<th width="12%">公告等级</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${noitceList_row}">
		        <td><a href="javascript:;" onclick="view('${noticeid}');">${creatorname}</a></td>
		        <td><a href="javascript:;" onclick="view('${noticeid}');">${noticetitle}</a></td>
		        <td><a href="javascript:;" onclick="view('${noticeid}');"><eoms:date value="${noticecreatetime}"/></a></td>
		        <td><a href="javascript:;" onclick="view('${noticeid}');"><eoms:date value="${noticestarttime}"/></a></td>
		        <td><a href="javascript:;" onclick="view('${noticeid}');"><eoms:date value="${noticelosttime}"/></a></td>
		        <td><a href="javascript:;" onclick="view('${noticeid}');"><eoms:dic value="${noticelevel}" dictype="noticeLevel"></eoms:dic></a></td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
	</body>
</html>
