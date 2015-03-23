<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<head>
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	 <title>
		     <eoms:lable name="wf_sort_wfsort" />
	</title>
	
    <script type="text/javascript">
    
    var message = '${message}';
	if(message != null){
		if(message == 'true'){
			window.close();
		}
	}else{
		alert(message);
	}
    
   	window.onresize = function()
	{
		setCenter(0,61);
	}
	window.onload = function(){
	    setCenter(0,61);
	}
	
	function selectPer(){
		console.info("this is the noticeType");
		var obj = document.getElementById('noticeInfo.noticeLevel');
		console.info(obj)
		if('0'==obj.value){
			document.getElementById('customUserTr').style.display='block';
		}else{
			document.getElementById('customUserTr').style.display='none';
		}
	}
	
	function openSelPer(){
		src = '${ctx}/notice/selectPerson.jsp';
		window.open(src,'','width=350,height=470,top=150,left=350,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	}
	
	function saveForm(){
		var noticeTitle = document.getElementById("noticeInfo.noticeTitle");
		if("" == noticeTitle.value){
			alert("公告主题不能为空！");
			noticeTitle.focus();
			return;
		}
		
        var oEditor = FCKeditorAPI.GetInstance('noticeInfo.noticeContent');  
        var checkContent = oEditor.GetXHTML();  
        if(checkContent == "")  
        {  
           alert("公告内容不能为空！");  
           oEditor.Focus();  
           return;  
        }  
		var noticeStartTimeStr = document.getElementById("noticeInfo.noticeStartTimeStr");
		if("" == noticeStartTimeStr.value){
			alert("生效时间不能为空！");
			noticeStartTimeStr.focus();
			return;
		}
		var noticeLostTimeStr = document.getElementById("noticeInfo.noticeLostTimeStr");
		if("" == noticeLostTimeStr.value){
			alert("失效时间不能为空！");
			noticeLostTimeStr.focus();
			return;
		}
		/* 	
		var noticetype = document.getElementById("noticeInfo.noticetype");
		if("" == noticetype.value){
			alert("公告类别不能为空！");
			noticetype.focus();
			return;
		}
		var noticeLevelStr = document.getElementById("noticeInfo.noticeLevel").value;
		if('0' != noticeLevelStr){
			document.getElementById("selectUser").value = '';
			document.getElementById("noticeInfo.customUser").value = '';
		} */
	
		// 此为附件id，规则见ultraswfupload.js this.uploaderId+ UltraSWFUpload.INPUT_HIDDEN_ID
		var attaGroupId = document.getElementById("noticAttaAttachmentGroupId").value;
		if(attaGroupId != "" && attaGroupId != 'undefined'){
			document.getElementById("noticeInfo.attachmentId").value = attaGroupId;
		}
		addForm.submit();
	}
    </script>
</head>
<body>
<div class="content" id="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							系统公告
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="scroll_div" id="center">
			<br />
		    <s:form action="saveNotice.action" theme="simple" name="addForm" id="addForm" method="post">
		    	<s:hidden name="noticeInfo.pid" id="noticeInfo.pid" cssClass="textInput"/>
		    	<s:hidden name="noticeInfo.creatorId" id="noticeInfo.creatorId"/>
		    	<s:hidden name="noticeInfo.creatorName" id="noticeInfo.creatorName"/>
		    	<s:hidden name="noticeInfo.basesn" id="noticeInfo.basesn"/>
		    	<s:hidden name="noticeInfo.noticeCreateTime" id="noticeInfo.noticeCreateTime"/>
		    	<s:hidden name="noticeInfo.noticeStatus" id="noticeInfo.noticeStatus"/>
		    	<s:hidden name="noticeInfo.attachmentId" id="noticeInfo.attachmentId"/>
		    	<!-- 自定义通知人 -->
		    	<s:hidden name="noticeInfo.selectUser" id="selectUser"/>
		    	
				<table class="add_user">
					<tr>
						<td class="texttd">
							公告主题：<font color="red">*</font>
						</td>
						<td colspan="5">
							<s:textfield name="noticeInfo.noticeTitle" id="noticeInfo.noticeTitle" cssClass="textInput" maxlength="100" />
						</td>
					</tr>
					<tr style="height: 200px;">
						<td class="texttd">
							公告内容：<font color="red">*</font>
						</td>
						<td colspan="5">
							<FCK:editor instanceName="noticeInfo.noticeContent"
								basePath="/common/plugin/fckeditor" height="280" width="97%" value="${noticeInfo.noticeContent}">
							</FCK:editor>
						</td>
					</tr>
					<tr>
						<td class="texttd" style="width: 5%">
							生效时间：<font color="red">*</font>
						</td>
						<td  style="width: 15%">
							<s:textfield name="noticeInfo.noticeStartTimeStr" id="noticeInfo.noticeStartTimeStr" cssStyle="width:80%" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"></s:textfield>
						</td>
						<td class="texttd"  style="width: 5%">
							失效时间：<font color="red">*</font>
						</td>
						<td  style="width: 15%">
							<s:textfield name="noticeInfo.noticeLostTimeStr" id="noticeInfo.noticeLostTimeStr" cssStyle="width:80%" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"></s:textfield>
						</td>
						<td class="texttd"  style="width: 5%">
						  发布部门：
						</td>
						<td  style="width: 15%;padding-right: 50px;">
						
						
						<s:textfield name="noticeInfo.noticescopeid" id="noticescopeid" cssClass="textInput" type="hidden" maxlength="100" />
						<s:textfield name="noticeInfo.noticescope" id="noticescope" cssClass="textInput" maxlength="100" />
							<%-- <eoms:select  name="noticeInfo.noticeLevel" style="select"
											dataDicTypeCode="noticeLevel" value="${noticeInfo.noticeLevel}"
											
											isnull="false" onChangeFun="selectPer();"/> --%>
											
											
						</td>
					</tr>
					<c:if test="${noticeInfo.noticeLevel == '0'}">
						<tr id="customUserTr">
					</c:if>
					<c:if test="${noticeInfo.noticeLevel != '0'}">
						<tr id="customUserTr" style="display: none;">
					</c:if>
						<td class="texttd" style="width: 5%">
							自定义人员：<font color="red">*</font>
						</td>
						<td colspan="4">
							<textarea id="noticeInfo.customUser" name="noticeInfo.customUser" rows="5" cols="80" readonly="readonly">${noticeInfo.customUser}</textarea>
						</td>
						<td style="width: 15%;padding-right: 50px;">
							<button type="button" onclick="openSelPer()" >选择自定义人员</button>
						</td>
					</tr>
					<tr>
						<td>
						</td>
						<td colspan="5">
							服务器当前时间大于生效时间则公告生效，服务器当前时间小于生效时间或大于失效时间则公告失效。
						</td>
					</tr>
					<tr>
						<td class="texttd"  style="width: 5%">
						  发布组：
						</td>
						<td  style="width: 15%;padding-right: 50px;">
						
						
						<s:textfield name="noticeInfo.noticescopegroupid" id="noticescopegroupid" cssClass="textInput" type="hidden" maxlength="100" />
						<s:textfield name="noticeInfo.noticescopegroup" id="noticescopegroup" cssClass="textInput" maxlength="100" />
							<%-- <eoms:select  name="noticeInfo.noticeLevel" style="select"
											dataDicTypeCode="noticeLevel" value="${noticeInfo.noticeLevel}"
											isnull="false" onChangeFun="selectPer();"/> --%>
											
											
						</td>
						<%-- <td  class="texttd">
							公告类别：<font color="red">*</font>
						</td>
						<td  style="width: 15%;padding-right: 50px;">
							<eoms:select name="noticeInfo.noticetype" dataDicTypeCode="notice_Type" value="${noticeInfo.noticetype}" style="select"></eoms:select>
						</td>
						<td  colspan="4">
						</td> --%>
						
					</tr>
					<tr>
						<td class="texttd">
							附件列表：
						</td>
						<td colspan="5">
							<atta:fileupload 
								id="noticAtta" 
								uploadDestination="notice/noitce"
								uploadable="true" 
								downTableVisible="true" 
								isMultiDownload="true" 
								isMultiUpload="true"
								uploadBtnIsVisible="true" 
								uploadTableVisible="true"
								attchmentGroupId="${noticeInfo.attachmentId}"
								isAutoUpload="true"
								createDirByDate="false" 
								fileTypes="*.*" 
								progressIsVisible="true"
								returnValue="1,2,3,4,5"
								delSelfOnly="true"
								></atta:fileupload>
						</td>
					</tr>
				</table>
			</s:form>
  		</div>
		<div class="add_bottom">
			<input type="button" value="发布" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="saveForm();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
		</div>
	</div>
</body>
<script type = "text/javascript">

//发布部门选择树
$("#noticescope").click(function(){
	showModalDialog($ctx+'/common/tools/orgtree/organizaTree.jsp?doc_id='+this.id+'&back_fill="incident"&isSelectType=0&idtype=1&isRadio=1',window,'help:no;center:true;scroll:no;status:no;dialogWidth:590px;dialogHeight:490px');
	});
	
//发布组选择树，选择组
$("#noticescopegroup").click(function(){
	showModalDialog($ctx+'/common/tools/orgtree/organizaTree.jsp?doc_id='+this.id+'&back_fill="incident"&isSelectType=0&idtype=1&isRadio=1',window,'help:no;center:true;scroll:no;status:no;dialogWidth:590px;dialogHeight:490px');
	});


//发布范围回填
function fillBackUserInfo(data,doc_id) {
	if (data != '') {
		var dataArray = data.split(';');
		var depArray = dataArray[1].split(':');
		if (depArray[2] != '') {
			$("#"+doc_id+"id").val(depArray[1]);
			$("#"+doc_id).val(depArray[2]);
		}
		
	}
}

</script>
</html>