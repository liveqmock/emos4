<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@include file="/ultrabpp/runtime/theme/header.jsp"%>
	<%@include file="/ultrabpp/runtime/theme/classic/script.jsp"%>  <!-- 初始化相关组件的操作main.js -->
	<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%> <!-- 树形结构的初始化 -->
	<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/BaseQuery.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/ultrabpp/runtime/theme/classic/css/main.css" />
	<script src="${ctx}/ultrabpp/runtime/theme/classic/js/batchReview.js"></script>
	<script src="${ctx}/ultrabpp/runtime/clientframework/model/ClientContext.js"></script>
	<script src="${ctx}/common/javascript/date/WdatePicker.js"></script>
	<!-- <script src="${ctx}/workflow/design/ultraweb/js/ultraweb.js"></script> -->
	<jsp:useBean id="now" class="java.util.Date" scope="page"/>
	<script language="javascript">
	</script>
  </head>
  <body style="background-color:white;">
	<div class="title_right">
		<div class="title_left">
			<span class="title_bg">
				<span class="title_icon">变更评审批量审批</span>
			</span>
			<span class="title_xieline"></span>
	        </span>
		</div>
	</div>
	
	<div id="bpp_ActPanel" style="display: block;">
	<div style="height:5px;"></div>
	<div id="bpp_ActComment">评审通过</div>
	<div id="bpp_ActBody"> 
	<div id="bpp_ActFields">
	<!-- 页面隐藏域,提供批量审批所需的必要参数 -->
	<input id="baseIds" type="hidden" value="${baseIds }"></input> 
	<input id="baseSchema" type="hidden" value="${baseSchema }"> </input> 
	<input id="bpp_Sys_AssignString" type="hidden" value=""> </input> 
	<input name="attachmentId" type="hidden" id="attachmentId" /> </input>

				<div id="view_dealGroup_ubfbox" ubftype="CharField"
					class="bpp_Field bpp_CharField " style="width:320px; height:30px; ">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width:318px; height:29px;">
						<tbody>
							<tr>
								<td valign="top" class="bpp_Field_Label"><div style="height:29px;">
										<div>
											<span class="bpp_Require">处理组：</span>
										</div>
									</div></td>
								<td valign="top"><input id="view_dealGroup"
									name="view_dealGroup" style="width: 216px;" height:20px;=""
									value="">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="dealGroup_ubfbox" ubftype="CharField"
					class="bpp_Field bpp_CharField "
					style="width:320px; height:30px;  display:none;">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width:318px; height:29px;">
						<tbody>
							<tr>
								<td valign="top" class="bpp_Field_Label"><div
										style="height:29px;">
										<div>
											<span>处理组ID：</span>
										</div>
									</div></td>
								<td valign="top"><input id="dealGroup" name="dealGroup"
									style="width:193px;" height:20px;="" value=""><div
											id="dealGroup_extend" class="bpp_Field_Extend"></div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="view_dealUser_ubfbox" ubftype="SelectField"
					class="bpp_Field bpp_SelectField "
					style="width:320px; height:30px; ">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width:318px; height:29px;">
						<tbody>
							<tr>
								<td valign="top" class="bpp_Field_Label"><div
										style="height:29px;">
										<div>
											<span>处理人：</span>
										</div>
									</div></td>
								<td valign="top"><select id="view_dealUser"
									name="view_dealUser" style="width:194px; height:20;" value=""
									onkeydown="return false;" onpaste="return false;">
										<div id="view_dealUser_extend" class="bpp_Field_SelectExtend"></div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="dealUser_ubfbox" ubftype="CharField"
					class="bpp_Field bpp_CharField "
					style="width:320px; height:30px;  display:none;">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width:318px; height:29px;">
						<tbody>
							<tr>
								<td valign="top" class="bpp_Field_Label"><div
										style="height:29px;">
										<div>
											<span>处理人ID：</span>
										</div>
									</div></td>
								<td valign="top"><input id="dealUser" name="dealUser"
									style="width:193px;" height:20px;="" value=""><div
											id="dealUser_extend" class="bpp_Field_Extend"></div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 日期时间组件 -->
				<div id="review_time_ubfbox" ubftype="TimeField"
					class="bpp_Field bpp_TimeField " style="width:320px; height:30px; ">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width:318px; height:29px;">
						<tbody>
							<tr>
								<td valign="top" class="bpp_Field_Label"><div
										style="height:29px;">
										<div>
											<span class="bpp_Require">评审时间：</span>
										</div>
									</div></td>
								<td valign="top"><input id="review_time"
									name="review_time" style="width:216px; height:20"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})"
									onkeydown="return false" onpaste="return false"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="audit_huican_person_ubfbox" ubftype="CharField"
					class="bpp_Field bpp_CharField " style="width:320px; height:30px; ">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width:318px; height:29px;">
						<tbody>
							<tr>
								<td valign="top" class="bpp_Field_Label"><div
										style="height:29px;">
										<div>
											<span>评审会参会人：</span>
										</div>
									</div></td>
								<td valign="top"><input id="audit_huican_person" name="audit_huican_person"
									style="width: 216px;" height:20px;="" value=""><div
											id="audit_huican_person_extend" class="bpp_Field_Extend"
											style="display: none;"></div></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="other_canhui_person_ubfbox" ubftype="CharField"
					class="bpp_Field bpp_CharField " style="width:320px; height:30px; ">
					<table border="0" cellpadding="0" cellspacing="0"
						style="width:318px; height:29px;">
						<tbody>
							<tr>
								<td valign="top" class="bpp_Field_Label"><div
										style="height:29px;">
										<div>
											<span>其他参会人：</span>
										</div>
									</div></td>
								<td valign="top"><input id="other_canhui_person"
									name="other_canhui_person" style="width:193px;" height:20px;=""
									value=""></td>
							</tr>
						</tbody>
					</table>
				</div>
				<input type="hidden" id="audit_huican_person_id" name="audit_huican_person_id"
					style="width:0px;" value="">
					<div id="dealDesc_ubfbox" ubftype="CharField"
						class="bpp_Field bpp_CharField "
						style="width:960px; height:150px; ">
						<table border="0" cellpadding="0" cellspacing="0"
							style="width:958px; height:149px;">
							<tbody>
								<tr>
									<td valign="top" class="bpp_Field_Label"><div
											style="height:149px;">
											<div>
												<span>处理意见：</span>
											</div>
										</div></td>
									<td valign="top"><textarea id="dealDesc" name="dealDesc"
											style="width:834px; height:140px;"></textarea>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div id="attachmentList" style="width:960px; min-heght:200px; float: left;">
						<tr>
							<td class="texttd">附件列表：</td>
							<td colspan="5"><atta:fileupload id="noticAtta"
									uploadDestination="eoms_workSheet/${baseSchema }"
									uploadable="true" downTableVisible="true"
									isMultiDownload="true" isMultiUpload="true"
									uploadBtnIsVisible="true" uploadTableVisible="true"
									attchmentGroupId="${attachmentId}" isAutoUpload="true"
									createDirByDate="false" fileTypes="*.*"
									progressIsVisible="true" returnValue="1,2,3,4,5"
									delSelfOnly="true"></atta:fileupload></td>
						</tr>
					</div>
			</div>
	<div class="bpp_White_Block"></div>
	<div id="bpp_ActPanel_BtnPanel">
		<input type="button" value="提　交" onclick="batchDealReview()">
		<input type="button" value="取　消" onclick="goBack()">
		</div>
	</div>
</div>
 </body>
</html>
<script language="javascript" type="text/javascript">

//设置时间选择框的默认时间
var nowDateStr = getNowFormatDate();
$("#review_time").val(nowDateStr);


/**
 * 批量处理提交
 */
function batchDealReview(){
	var dealGroup = $("#view_dealGroup").val(); 
	var assignString = $("#bpp_Sys_AssignString").val();
	var baseIds = $("#baseIds").val();
	var baseSchema = $("#baseSchema").val();
	var actionID= "organizeAuditPass"; //动作标识,暂时写死
	var actionType = "NEXT";
	var actionText = "评审通过";
	var taskID = "";
 	var fieldsStr = "audit_huican_person:"+$("#audit_huican_person").val()+";other_canhui_person:"+$("#other_canhui_person").val()+";review_time:"+$("#review_time").val()+";dealDesc:"+$("#dealDesc").val();
 	 // 此为附件id，规则见ultraswfupload.js this.uploaderId+ UltraSWFUpload.INPUT_HIDDEN_ID，此id为工单关联 
	var attaGroupId = document.getElementById("noticAttaAttachmentGroupId").value;
	if(attaGroupId != "" && attaGroupId != 'undefined'){
		document.getElementById("attachmentId").value = attaGroupId;
	}
	
	//处理组为空检查
	if(dealGroup==null || dealGroup ==""){
		alert("请输入处理组！");
		return;
	}
	
	if(review_time==null || review_time ==""){
		alert("请输入评审时间	！");
		return;
	}
	
	
	
 	var paras = 
	{
			assignString:assignString,
			baseIds:baseIds,
			baseSchema:baseSchema,
			actionID:actionID,
			actionType:actionType,
			actionText:actionText,
			taskID:taskID,
			fieldsStr:fieldsStr,
			attaGroupId:attaGroupId
	};
 	
	$.post("${ctx}/sheet/bacthDealReview.action",paras,function(data) 
	{
		if(data!=null && data!='null')
		{	
		alert(data);
		refreshParent();
		}
	});
}

//部门选择树
groupTree_batchOper('dealGroup','','','dealUser');

//人员选择树 
groupUserTree_batchOper({"viewid":"audit_huican_person","beforeInit":initDirector});

//刷新父窗口
function refreshParent() 
{
	window.opener.location.href = window.opener.location.href;
	window.close();  
}  


//取消返回
function goBack()
{
	window.opener.location.href = window.opener.location.href;
	window.close();  	
}



</script>
