<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script language="javascript">
	$(function(){
			$("#center").height(190);//调整滚动部分的高度
		});
		function txtView(id,len){
			var txt = $('#'+id).val();
			if(txt != null && txt.length > len){
				var vl = '<span id="" nowrap="nowrap" title="' + txt + '">' + txt.substring(0,len) + "...</span>";
				document.write(vl);
			}else{
				document.write(txt);
			}
		}
	</script>
  </head>
  <body>
  	<dg:datagrid var="info" title="变更工单信息" action="" sqlname="SQL_CDB_release_interfaceChg.query" cachemode="sql">
    	<dg:gridtitle>
	    	<tr>
	    		<th width="100px">变更类型</th>
	    		<th width="100px">变更名称</th>
	    		<th width="80px">变更个数</th>
	    		<th>变更内容说明</th>
	    		<th width="100px">涉及模块</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${info_row}" nowrap="nowrap">
				<td>${chgtype}</td>
				<td><textarea style="display: none;" id='${pid}_chgname'>${chgname}</textarea><script type="">txtView('${pid}_chgname',7);</script></td>
				<td>${chgnum}</td>
				<td><textarea style="display: none;" id='${pid}_content'>${chgcontent}</textarea><script type="">txtView('${pid}_content',40);</script></td>
				<td>${chgmodule}</td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>   
</html>
