<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
<script language="javascript">
	window.onresize = function() 
	{
		setCenter(0,30);
	}
	
	window.onload = function() 
	{
		setCenter(0,30);
	}
	function createNewKnowledge(){
		window.parent.document.getElementById("kmform").submit();
	}
	
	function cancelGroRelate(){
		getCheckValue("checkid");
		var kmIds = document.getElementsByName('var_selectvalues').value;
		if(kmIds == "" || kmIds == null){
			alert("请选择要删除关联的知识！");
			return false;
		}
		if (confirm('确认要取消关联？') && kmIds != "") {
			var url =  '${ctx}/extractKnowledge/cancelRelate.action';
			$.post(
				url, 
				{kmId:kmIds},
				function(data){
					if(data == 'true'){
						window.location.reload();
					}
				}
			); 
		}
	}
	
	function cancelRelate(kmId){
		if (confirm('确认要取消关联？') && kmId != "") {
			var url =  '${ctx}/extractKnowledge/cancelRelate.action';
			$.post(
				url, 
				{kmId:kmId},
				function(data){
					if(data == 'true'){
						window.location.reload();
					}
				}
			); 
		}
	}
	
	function openKnowledge(id){
		var src = 'http://${kmURL}/${kmProName}/kmViewQuery.do?method=search&type=39&id='+id;
		window.open(src);
	}
</script>

</head>
<body>
  <input type="button" value="新建知识" class="button" onmouseover="this.className='button'" onmouseout="this.className='button'" onclick="createNewKnowledge();" />
   <input type="button" value="批量删除关联" class="button" onmouseover="this.className='button'" onmouseout="this.className='button'" onclick="cancelGroRelate();" />
  <dg:datagrid  var="map" items="${knowledgeInfoList}">
  		<dg:condition>
  		<div align="center">
  			<table class="serachdivTable" width="80%">
				<tr>
					<td class="texttd" width="20%"><eoms:lable name="sm_lb_userName"/>：</td>
					<td width="35%"><input type="text" name="" class="textInput" value="" /></td>
					<td class="texttd" width="10%"><eoms:lable name="sm_lb_fullName"/>：</td>
					<td width="35%"><input type="text" name="" class="textInput" value="" /></td>
					<td width="10%"><input type="button" value="<eoms:lable name="com_btn_lookUp"/>" class="searchButton" 
					    onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" />
					</td>
				</tr>
			</table>
			</div>
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th width="10%" ><input type="checkbox" name="checkidAll" id="checkidAll" onclick="checkAll('checkid');" align="left"/></th>
				<th width="20%"><eoms:lable name="知识标题"/></th>
				<th width="20%"><eoms:lable name="知识分类"/></th>
				<th width="20%"><eoms:lable name="关键字"/></th>
				<th width="20%"><eoms:lable name="创建人"/></th>
				<th width="10%"><eoms:lable name="操作"/></th>
	        </tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${wfType_row}">
				<td align="center"><input type="checkbox"  id="checkid"  value="${map.kmId }"/></td>
				<td><a href="javascript:;"  onclick="openKnowledge('${map.kmId}');">${map.kmcaption}</a></td>
				<td><a href="javascript:;"  onclick="openKnowledge('${map.kmId}');">${map.kmcategory}</a></td>
				<td><a href="javascript:;"  onclick="openKnowledge('${map.kmId}');">${map.kmkeyword}</a></td>
				<td><a href="javascript:;"  onclick="openKnowledge('${map.kmId}');">${map.kmcreator}</a></td>
				<td><a href="javascript:;"  onclick="cancelRelate('${map.kmId}');" >取消关联</a></td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
</body>
</html>
