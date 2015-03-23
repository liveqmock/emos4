<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/swfupload/import.jsp"%>
		<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/tree/js/commonViewTreePublic.js"></script>
		<script language="javascript">
			window.onresize = function()
			{
				setCenter(0,30);
			}
			window.onload = function()
			{
				setCenter(0,30);
			}
			function menuSave()
			{
				var isnullflog=false;
				if(Validator.Validate(document.forms[0],2))
				{
					var sid="";
					 $("[id^='audit_id_']").each(
							 function(i,item){
								 if(item.value==""){
									 isnullflog=true;
								 }else{
									 sid+=item.value+"#;";
								 }
								 
							 }
						);
					 
					 var sname="";
					 $("[id^='audit_name_']").each(
							 function(i,item){
								 if(item.value==""){
									 isnullflog=true;
								 }else{
									 sname+=item.value+"#;";
								 }
							 }
						);
					 
					 sid=sid.substring(0,sid.length-2);
					 sname=sname.substring(0,sname.length-2);
					// 此为附件id，规则见ultraswfupload.js this.uploaderId+ UltraSWFUpload.INPUT_HIDDEN_ID
					var attaGroupId = document.getElementById("noticAttaAttachmentGroupId").value;
					if(attaGroupId != "" && attaGroupId != 'undefined'){
						document.getElementById("serverQuestModel.attamentid").value = attaGroupId;
					}
					
					var responsibleDepId = $("#responsibleDepId");
					var responsibleDepIdStr = responsibleDepId.val();
					if(responsibleDepIdStr!="" && (responsibleDepIdStr.indexOf("#:")>-1)){
						responsibleDepIdStr = responsibleDepIdStr.substring(3);
					}
					$.post("${ctx}/serverQuest/serverQuestInfo.action",
						{
						"serverQuestModel.pid":document.getElementById('serverQuestModel.pid').value,
						"serverQuestModel.serverquestname":document.getElementById('serverQuestModel.serverquestname').value,
						"serverQuestModel.serverquestvalue":document.getElementById('serverQuestModel.serverquestvalue').value,
						"serverQuestModel.serverquestdn":document.getElementById('serverQuestModel.serverquestdn').value,
						"serverQuestModel.serverquestdns":document.getElementById('serverQuestModel.serverquestdns').value,
						"serverQuestModel.serverquestfullname":document.getElementById('serverQuestModel.serverquestfullname').value,
						"serverQuestModel.status":document.getElementById('serverQuestModel.status').value,
						"serverQuestModel.ordernum":document.getElementById('serverQuestModel.ordernum').value,
						"serverQuestModel.parentid":document.getElementById('parent.pid').value,
						"serverQuestModel.creater":document.getElementById('serverQuestModel.creater').value,
						"serverQuestModel.createtime":document.getElementById('serverQuestModel.createtime').value,
						"serverQuestModel.remark":document.getElementById('serverQuestModel.remark').value,
						"serverQuestModel.dilever":document.getElementById('serverQuestModel.dilever').value,
						"serverQuestModel.vipflog":document.getElementById('serverQuestModel.vipflog').value,
						"serverQuestModel.audit_id":sid,
						"serverQuestModel.audit_name":sname,
						"serverQuestModel.dealGroupName":document.getElementById("dealGroupName").value,
						"serverQuestModel.dealGroupId":document.getElementById("dealGroupId").value,
						"serverQuestModel.qt":document.getElementById('serverQuestModel.qt').value,
						"serverQuestModel.worktime":document.getElementById('serverQuestModel.worktime').value,
						"serverQuestModel.workbegintime":document.getElementById('serverQuestModel.workbegintime').value,
						"serverQuestModel.workendtime":document.getElementById('serverQuestModel.workendtime').value,
						"serverQuestModel.noworktime":document.getElementById('serverQuestModel.noworktime').value,
						"serverQuestModel.dealtimelimit":document.getElementById('serverQuestModel.dealtimelimit').value,
						"serverQuestModel.dealtimelimit2":document.getElementById('serverQuestModel.dealtimelimit2').value,
						"serverQuestModel.attamentid":document.getElementById('serverQuestModel.attamentid').value,
						"serverQuestModel.isCommon":document.getElementById('serverQuestModel.isCommon').value,
						"serverQuestModel.serviceInfo":document.getElementById('serverQuestModel.serviceInfo').value,
						"serverQuestModel.responsibleDep.pid":responsibleDepIdStr,
						"serverQuestModel.forWho":document.getElementById("serverQuestModel.forWho").value,
						"serverQuestModel.releaseScopeId":document.getElementById("serverQuestModel.releaseScopeId").value,
						"serverQuestModel.releaseScopeText":document.getElementById("serverQuestModel.releaseScopeText").value,
						"serverQuestModel.isSendAudit":document.getElementById("serverQuestModel.isSendAudit").value,
						
						"levertype":'${levertype}',
						"parentid":'${parentid}',
						"time_":new Date()
						},
						function(result){
							parent.document.getElementById('leftFrame2').contentWindow.refresh('${parentid}');
							alert("保存成功！");
						});
				}
			}
	function openpeopletree(returnid,isSelectType){
		window.open($ctx +"/serverQuest/peopleTree.jsp?isRadio=0&id='0'&first=true&isSelectType='"+isSelectType+"'&returnId="+returnid, '人员列表', 'height=400px, width=600px, top=100, left=300, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');
	}	
	
	function  addRow(obj){
		var table=obj.parentNode.parentNode.parentNode;   
		 var length=  document.getElementById("auditTable").rows.length+1;
		  var trid=obj.parentNode.parentNode.id;  
		 var inHTML = "<tr id='audittr_"+trid+length+"'><td class='texttd' style='width:15%' id='auditname"+length+"'>"+length+"级审批人：</td>"+
		 "<td style='width:20%'>"+
		 "<input type='hidden' id='audit_id_"+trid+length+"'   value='' />"+
		 "<input type='text' id='audit_name_"+trid+length+"'   class='textInput' value='' readonly='readonly'  "+
		 "onclick=\"openpeopletree('audit_id_"+trid+length+"@audit_name_"+trid+length+"',1);\" />"+
		 "</td>"+
		 "<td style='width:25%'>"+
		 "<img src='${ctx}/common/style/blue/images/add.png' alt='添加' onclick='addRow(this);'/>&nbsp;&nbsp;&nbsp;&nbsp;<img src='${ctx}/common/style/blue/images/del_user.jpg' alt='删除' onclick='delRow(this);'/>"+
		 "</td></tr>";
		 $("#"+trid).after(inHTML);//添加一行
		 $("[id^='auditname']").each(
			 function(i,item){
				 $(this).text(i+1+"级审批人");
			 }
		);		 
	}

	function  delRow(obj){
		var table=obj.parentNode.parentNode.parentNode;   
			table.removeChild(obj.parentNode.parentNode);
			$("[id^='auditname']").each(
					 function(i,item){
						 $(this).text(i+1+"级审批人");
					 }
				);	
	}
	
	/*
	function deptTree(){
		var url = $ctx+"/commonViewTree/commonViewTree.action?opt.query=SQL_SM_DepList.query&opt.nameElementId=serverQuestModel.releaseScopeText&opt.enableCheckBoxes=true&opt.idElementId=serverQuestModel.releaseScopeId&opt.idColumnName=pid&opt.textColumnName=depname&opt.parentid=0&opt.pidColumnName=parentid";
		window.open(url,'systemTreeWindow','fullscreen=yes,type=fullWindow,scrollbars=yes');
	}
	*/
		</script>
	</head>
	<body>
		<form action="${ctx}/serverQuest/serverQuestInfo.action"  id="serverQuestForm" method="post">
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="menuSave()" text="com_btn_save" operate="com_save"/>
	  	 		</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>服务目录信息</legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr <c:if test="${dilever eq '1'}">  style="display: none;"</c:if>>
							<td class="texttd" style="width:5%"><c:if test="${dilever eq '2'}"> 一级目录：</c:if><c:if test="${dilever eq '3'}"> 二级目录：</c:if></td>
							<td style="width:25%"  <c:if test="${dilever eq '1'}">  style="display: none;"</c:if>>
								<c:if test="${dilever eq '2' }">
									<c:set var="where" value="where dilever = '1'"></c:set>
								</c:if>
								<c:if test="${dilever eq '3' }">
									<c:set var="where" value="where dilever = '1' or dilever = '2'"></c:set>
								</c:if>
								<eoms:dictTree checkLevelFlag="-2" name="parent.pid" value="${serverQuestModel.parent.pid}" sqlStr="select pid as id, pid as value, serverquestname as text, serverquestname as fulltext, parentid as pid from bs_t_wf_serverquest ${where }" ></eoms:dictTree>
							</td> 
						</tr>
						
						<tr>	
							<td class="texttd"  >服务目录：<span class="must">*</span></td>
							<td >
								<input type="text" id="serverQuestModel.serverquestname" name="serverQuestModel.serverquestname" class="textInput" value="${serverQuestModel.serverquestname}" />
								<validation id="serverQuestModel.serverquestnameV" require="true" dataType="Limit" Max="50" min="1" msg="节点名称不能为空，并且长度不能超过50个字符" />
							</td>
						</tr>
						
						<tr>
							<td class="texttd">是否启用：<span class="must">*</span></td>
							<td >
								<select id="serverQuestModel.status" name="serverQuestModel.status" style="width: 100px">
									<option value="1" <c:if test="${serverQuestModel.status eq '1' }">selected="selected"</c:if> >是</option>
									<option value="0" <c:if test="${serverQuestModel.status eq '0' }">selected="selected"</c:if> >否</option>
								</select>
								<validation id="serverQuestModel.statusV" require="true" dataType="Limit" Max="50" min="1" msg="是否展示不能为空，并且长度不能超过50个字符"/>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">常用服务目录：</td>
							<td>
								<select id="serverQuestModel.isCommon" name="serverQuestModel.isCommon"  style="width:100px;">
									<option value="1">是</option>
									<option value="0" <c:if test="${serverQuestModel.isCommon ne '1' }">selected="selected"</c:if> >否</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">是否送审：</td>
							<td>
								<select id="serverQuestModel.isSendAudit" name="serverQuestModel.isSendAudit"  style="width:100px;">
									<option value="1">是</option>
									<option value="0" <c:if test="${serverQuestModel.isSendAudit ne '1' }">selected="selected"</c:if> >否</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">内外标识：</td>
							<td>
								<select id="serverQuestModel.forWho" name="serverQuestModel.forWho"  style="width:100px;">
									<option value="对内">对内</option>
									<option value="对外" <c:if test="${serverQuestModel.forWho eq '对外' }">selected="selected"</c:if> >对外</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">发布范围：</td>
							<td>
								<input readonly="readonly" type="text" id="serverQuestModel.releaseScopeText" name="serverQuestModel.releaseScopeText" class="textInput" value="${serverQuestModel.releaseScopeText}"  onclick="openDeptTree(true,'serverQuestModel.releaseScopeText','serverQuestModel.releaseScopeId')"/>
								<input type="hidden" id="serverQuestModel.releaseScopeId" name="serverQuestModel.releaseScopeId" class="textInput" value="${serverQuestModel.releaseScopeId}" />
								<validation id="serverQuestModel.releaseScopeTextV" require="true" dataType="Limit" min="1" msg="发布范围不能为空" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">负责部室：</td>
							<td>
								<input type="text" id="responsibleDepName" name="" class="textInput" value="${serverQuestModel.responsibleDep.depname}"  onclick="openpeopletree('responsibleDepId@responsibleDepName',1)"/>
								<input type="hidden" id="responsibleDepId" name="serverQuestModel.responsibleDep.pid" class="textInput" value="${serverQuestModel.responsibleDep.pid}" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">服务说明：</td>
							<td>
								<input type="text" id="serverQuestModel.serviceInfo" name="serverQuestModel.serviceInfo" class="textInput" value="${serverQuestModel.serviceInfo}" />
								<validation id="serverQuestModel.serviceInfoV" require="false" dataType="Limit" max="500" msg="服务说明超过500个字" />
							</td>
						</tr>
						<tr>
							<td class="texttd" valign="top">排序：<span class="must">*</span></td>
							<td >
								<input type="text" id="serverQuestModel.ordernum" name="serverQuestModel.ordernum" class="textInput" value="${serverQuestModel.ordernum}" />
								<validation id="serverQuestModel.ordernumV" require="true" dataType="Number" max="10" msg="排序应为数字或超过10个字" />
							</td>
						</tr>
					</table>
						</fieldset>
				<fieldset class="fieldset_style" <c:if test="${dilever ne '3'}"> style="display: none;"</c:if>>
					<legend>审批步骤</legend>		
					
					<table class="add_user"  id="auditTable">
						<tr id="audittr_0">
						    <td class="texttd" style="width:15%" id="auditname1">1级审批人：</td>
							<td style="width:20%">
								<input type="hidden" id="audit_id_0"   value="${aduitid1}" />
								<input type="text" id="audit_name_0"   class="textInput" value="${aduitName1}" readonly="readonly"  onclick="openpeopletree('audit_id_0@audit_name_0',1)" />
							<validation id="serverQuestModel.audit_name_0V" require="true" dataType="Limit" Max="50" min="1" msg="1级审批人空，并且长度不能超过50个字符"/>
							
							</td>
							<td style="width:25%">
								<img src="${ctx}/common/style/blue/images/add.png" alt="添加" onclick="addRow(this);"/>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						</tr>
						<c:forEach items="${aduitidNameList}" var="aduMap" varStatus="st">
							<c:if test="${st.count>=2}">
								<tr id="audittr_${st.count}">
								    <td class="texttd" style="width:15%" id="auditname${st.count}">${st.count}级审批人：</td>
									<td style="width:20%">
										<c:forEach items="${aduMap}" var="adu" >
										<input type="hidden" id="audit_id_${st.count}"   value="${adu.key}" />
										<input type="text" id="audit_name_${st.count}"   class="textInput" value="${adu.value}" readonly="readonly"  onclick="openpeopletree('audit_id_${st.count}@audit_name_${st.count}',1)" />
										</c:forEach>
									</td>
									<td style="width:25%">
										<img src="${ctx}/common/style/blue/images/add.png" alt="添加" onclick="addRow(this);"/>&nbsp;&nbsp;&nbsp;<img src='${ctx}/common/style/blue/images/del_user.jpg' alt='删除' onclick='delRow(this);'/>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					</fieldset>	
			
					
					<fieldset class="fieldset_style" <c:if test="${dilever ne '3'}"> style="display: none;"</c:if>>
					<legend>处理步骤</legend>			
					<table class="add_user"  >
						
						<tr>
							<td class="texttd" style="width:15%">处理组：</td>
							<td style="width:25%">
								<input type="text" id="dealGroupName" class="textInput" value="${serverQuestModel.dealGroupName}" onclick="openpeopletree('dealGroupId@dealGroupName',1)"/>
								<input type="hidden" id="dealGroupId" name="serverQuestModel.dealGroup" value="${serverQuestModel.dealGroupId}"/>
							</td>
							<td class="texttd" style="width:15%"></td>
							<td style="width:25%">
							</td>
						</tr>
						<tr>
							<td class="texttd" style="width:15%">是否有VIP服务：</td>
							<td style="width:25%">
								<select id="serverQuestModel.vipflog" name="serverQuestModel.vipflog" class="textInput" style="width:100px;">
									<option value="" <c:if test="${serverQuestModel.vipflog eq '' }">selected="selected"</c:if> ></option>
									<option value="1" <c:if test="${serverQuestModel.vipflog eq '1' }">selected="selected"</c:if> >是</option>
									<option value="0" <c:if test="${serverQuestModel.vipflog eq '0' }">selected="selected"</c:if> >否</option>
								</select>
							</td>
							<td class="texttd" style="width:10%">其他：</td>
							<td style="width:25%">
								<input type="text" id="serverQuestModel.qt" name="serverQuestModel.qt"  class="textInput" value="${serverQuestModel.qt}" />
							</td>
						</tr>
						<tr>
							<td class="texttd" style="width:15%">工作日响应时间：</td>
							<td style="width:25%">
								<input type="text" id="serverQuestModel.worktime" name="serverQuestModel.worktime"  class="textInput" value="${serverQuestModel.worktime}" />
							</td>
							<td class="texttd" style="width:15%">非工作日响应时间：</td>
							<td style="width:25%">
								<input type="text" id="serverQuestModel.noworktime" name="serverQuestModel.noworktime"  class="textInput" value="${serverQuestModel.noworktime}" />
							</td>
						</tr>
						<tr>
							<td class="texttd" style="width:10%">解决时间上限（不更换硬件）：</td>
							<td style="width:25%">
								<input type="text" id="serverQuestModel.dealtimelimit" name="serverQuestModel.dealtimelimit"  class="textInput" value="${serverQuestModel.dealtimelimit}" />
							</td>
							<td class="texttd" style="width:10%">解决时间上限（更换硬件）：</td>
							<td style="width:25%">
								<input type="text" id="serverQuestModel.dealtimelimit2" name="serverQuestModel.dealtimelimit2"  class="textInput" value="${serverQuestModel.dealtimelimit2}" />
							</td>
						</tr>
						<tr>
							<td class="texttd" style="width:10%">工作开始时间：</td>
							<td style="width:25%">
								<input type="text" id="serverQuestModel.workbegintime" name="serverQuestModel.workbegintime"  class="textInput" value="${serverQuestModel.workbegintime}" 
									onfocus="WdatePicker({dateFmt:'HH:mm:ss',autoPickDate:true})"
									onkeydown="return false" onpaste="return false" 
								/>
							</td>
							<td class="texttd" style="width:10%">工作结束时间：</td>
							<td style="width:25%">
								<input type="text" id="serverQuestModel.workendtime" name="serverQuestModel.workendtime"  class="textInput" value="${serverQuestModel.workendtime}" 
									onfocus="WdatePicker({dateFmt:'HH:mm:ss',autoPickDate:true})"
									onkeydown="return false" onpaste="return false" 
								/>
							</td>
						</tr>
						<tr>
							<td colspan="4">
						<atta:fileupload  id="noticAtta" 
								uploadDestination="notice/noitce"
								uploadable="true" 
								downTableVisible="true" 
								isMultiDownload="true" 
								isMultiUpload="true"
								uploadBtnIsVisible="true" 
								uploadTableVisible="true"
								attchmentGroupId="${serverQuestModel.attamentid}"
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
		</fieldset>
		
				</div>
		</div>
		<input type="hidden" id="serverQuestModel.pid" name="serverQuestModel.pid" value="${serverQuestModel.pid}"/>
		<input type="hidden" id="serverQuestModel.parentid" name="serverQuestModel.parentid" value="${parentid}"/>
		<input type="hidden" id="serverQuestModel.serverquestdn" name="serverQuestModel.serverquestdn" value="${serverQuestModel.serverquestdn}"/>
		<input type="hidden" id="serverQuestModel.serverquestdns" name="serverQuestModel.serverquestdns" value="${serverQuestModel.serverquestdns}"/>
		<input type="hidden" id="serverQuestModel.serverquestfullname" name="serverQuestModel.serverquestfullname" value="${serverQuestModel.serverquestfullname}"/>
		<input type="hidden" id="serverQuestModel.dilever" name="serverQuestModel.dilever" value="${serverQuestModel.dilever}"/>
		<input type="hidden" id="serverQuestModel.creater" name="serverQuestModel.creater" value="${serverQuestModel.creater}"/>
		<input type="hidden" id="serverQuestModel.createtime" name="serverQuestModel.createtime" value="${serverQuestModel.createtime}"/>
		<input type="hidden" id="serverQuestModel.serverquestvalue" name="serverQuestModel.serverquestvalue" value="${serverQuestModel.serverquestvalue}" />
		<input type="hidden" id="serverQuestModel.remark" name="serverQuestModel.remark" value="${serverQuestModel.remark}" />
		<input type="hidden" id="serverQuestModel.attamentid" name="serverQuestModel.attamentid" value="${serverQuestModel.attamentid}" />
		
		<input type="hidden" id="levertype" name="levertype" value="${levertype}"/>
		</form>
				
	</body>
</html>
