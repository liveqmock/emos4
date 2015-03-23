<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file = "/common/plugin/jquery/jquery.jsp" %>
	<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
	<script language="javascript">
		window.onresize = function()
		{
			setCenter(0,0);
		}
		window.onload = function()
		{
			setCenter(0,0);
			refresh();
		}
		
		function addnew(tp){
			var id = '';
			if(tp == 'current'){//同级增加
				id = '${treeMap.PID}';
			}else if(tp == 'lower'){//下级增加
				id = '${id}';
			}
			if(id != null && id != ''){		
				window.open('${ctx}/commonTree/editTreeNode.action?pageURL=${pageURL}&&type=${type}&&pid=' + id,'_self');
			}
		}
		
		function save(){
			var vali =  Validator.Validate(document.getElementById('treeFrom'),1);
			if(vali){
				$('#treeFrom').submit();
			}
		}
		
		
		//刷新树节点
		function refresh(){
			var msg = '<%=request.getAttribute("msg")%>';
			if(msg == 'true'){
				window.parent.refresh("${treeMap.pid}");
			}
		}
		
		//删除节点
		function delTreeNode(){
			var id = "${id}";
			if(confirm('确认删除本节点及以下所有节点？')){
				if(id != null && id != ''){
					window.open('${ctx}/commonTree/delTreeNode.action?pageURL=${pageURL}&&type=${type}&&id=' + id,'_self');
				}
			}
		}
		
		
		
		//选人或部门
		function openpeopletree(returnid,isSelectType){
			window.open($ctx +"/serverQuest/peopleTree.jsp?isRadio=0&id='0'&first=true&isSelectType='"+isSelectType+"'&returnId="+returnid, '人员列表', 'height=400px, width=600px, top=100, left=300, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
		}
	</script>
  </head>
  <body>
  <form id="treeFrom" action="${ctx}/commonTree/saveTreeNode.action" method="post" target="_self">
  	<div class="content">
  	<div class="page_div_bg">
		<div class="page_div">
			<s:if test="%{id != ''}">
				<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="delTreeNode()" text="com_btn_delete" operate="com_delete"/>
				<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="addnew('current')" text="sm_btn_currentadd" operate="com_add"/>
				<s:if test="%{level != treeMap.TLEVEL}">
					<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'" onclick="addnew('lower')" text="sm_btn_loweradd" operate="com_add"/>	
				</s:if>
			</s:if>
  			<eoms:operate cssclass="page_saves_button" id="page_save_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'"    onclick="save();"  text="com_btn_save" operate="com_save" />
		</div>	
	</div>  	
  		<div class="add_scroll_div_x" id="center">  			
  				<input type="hidden" name="pageURL" value="${pageURL}" />
  				<input type="hidden" name="type" value="${type}" />
  				<input type="hidden" name="level" value="${level}" />
  				<input type="hidden" name="id" value="${id}" />
  				<input type="hidden" name="treeMap.id" value="${treeMap.ID}" />
  				<input type="hidden" name="treeMap.TYPE" value="${treeMap.TYPE}" />
				<fieldset class="fieldset_style">
					<legend>基本信息</legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:15%">名称：<span class="must">*</span></td>
							<td style="width:35%">
								<input type="text" name="treeMap.name" id="treeMap.name" class="textInput" value="${treeMap.NAME}" />
								<validation id="treeMap.nameV" dataType="Custom" regexp="^.{1,100}$" msg="名称不能为空" />
							</td>
							<td class="texttd" style="width:15%">全称：</td>
							<td style="width:35%">
								<input type="text" name="treeMap.fullname" readonly="readonly" class="textInput" value="${treeMap.FULLNAME}" />
							</td>
						</tr>
						<tr>
							<td class="texttd">上级目录：</td>
							<td>
								<eoms:dictTree name="treeMap.pid" value="${treeMap.PID}" id="pid" sqlStr="select id,id as value,name as text,name as fulltext,pid from bs_t_sm_commonTree where type = '${type}'" ></eoms:dictTree>
							</td>
							<td class="texttd" style="width:15%">排序：</td>
							<td>
								<input type="text" name="treeMap.orderby" class="textInput" value="${treeMap.ORDERBY}" />
							</td>
						</tr>
					</table>
				</fieldset>
				<s:if test="%{pageURL != ''}">
					<s:if test="%{level == '-1' || level == treeMap.TLEVEL}">
						<jsp:include page="/${pageURL}.jsp" flush="false"></jsp:include>
					</s:if>
				</s:if>
			</div>
		</div>
  	</form>
  </body>
</html>
