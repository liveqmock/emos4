<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${ctx}/common/style/blue/css/main.css" rel="stylesheet"
			type="text/css" />
		<title>
			岗位修改
		</title>
		<script src="${ctx}/common/javascript/main.js"></script>
		<script language="javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,55);
			}
			window.onload = function() {
				setCenter(0,55);
			}
			
			function submitCheck() {
				var oldOrgName = $("#oldOrgName").val(); 
				var orgName = $("#orgName").val();//岗位名称
				if (orgName == '') {
					alert("岗位名称不能为空！");
					return false;
				}
				var telephone = $("#telephone").val();//值班电话
				if (telephone != '') {
					var filter=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,13})(-(\d{3,}))?$/;
					if(!filter.test(telephone))
				    {
				      	//不合法时
				      	alert("值班电话不合法！");
				        return false;
				    }
				}
				var errortime = $("#onTimeBef").val();//缓冲时间
				if (errortime == '') {
					alert("接班提前时间不能为空！");
					return false;
				}
				var errortime = $("#offTimeBef").val();//缓冲时间
				if (errortime == '') {
					alert("交班提前时间不能为空！");
					return false;
				}
				var shiftIds = $("#shiftIds").val();//班次
				if (shiftIds == '') {
					alert("班次不能为空！");
					return false;
				}
				/*
				if (oldOrgName != orgName) {
					$.post("checkDutyOrganization.action", // 服务器页面地址
					{
						name : orgName
					}, function(date) {
				       if (date == 'false') {
					       alert("岗位："+orgName+"已经被定义！");
					       return false;
				       } else {
				        var form = $("#form");
						form.submit();
				       }
					});
				} else {
				*/
					var form = $("#form");
					form.submit();
				//}
			}
			//机构
			function editGroup(id,name,type){
				window.open('${ctx}/common/tools/depOrUserSelect.jsp?isRadio='+type+'&isSelectType=0&input_name='+name+'&input_id='+id,'','location=no,toolbar=no,resizable=yes,scrollbars=yes,width=400,height=400,top=100,left=300');
			}
			//应用系统
			function editSystem(id,name,type){
			     //var targetDataArr = getTargetData(id,name);
				var targetDataArr = $("#"+id).val();
				//window.open('${ctx}/dutyApp/searchAppList.action?isRadio='+type+'&input_name='+name+'&input_id='+id,'system','location=no,toolbar=no,resizable=yes,scrollbars=yes,width=800,height=600,top=100,left=300');
				//showModalDialog('${ctx}/dutyApp/searchAppList.action',window,'help:no;center:true;scroll:no;status:no;dialogWidth:800px;dialogHeight:600px');
				showModalDialog('${ctx}/dutyApp/dutyAppManage.action?input_id='+id+"&input_name="+name+"&targetDataArr="+targetDataArr,window,'help:no;center:true;scroll:no;status:no;dialogWidth:900px;dialogHeight:560px');
			}
			//选择多个人
			function selectMultiPerSon(obj){
			   var perValue=obj.id;
			   var perId=(obj.id).split("_")[0];
			   //组装已选数据
			   var targetDataArr = "";
			   var  values = $("#"+perValue).val();
			   if(values != "" && typeof(values) != "undefined"){
				   var  ids = $("#"+perId).val();
				   var  id = ids.split(",");
				   $.each(id, function(i, n){//循环班次
				        targetDataArr = targetDataArr + id[i]+";";
				   });
				}
			   window.open('${ctx}/common/tools/depOrUserSelect.jsp?isRadio=1&isSelectType=1&input_name='+perValue+'&input_id='+perId+'&targetDataArr='+targetDataArr,'','location=no,toolbar=no,resizable=no,scrollbars=no,width=287,height=500,top=100,left=300');
			}
			//排班规则
			function editRule() {
				//组装已选择数据
				var targetDataArr = getTargetData("ruleIds", "ruleNames");
				window.open("${ctx}/common/getParaSelectTree.action?multiple=2&sourceDataObj=orgDicItem&par=schedulingRule&input_id=ruleIds&input_name=ruleNames&targetDataArr="+targetDataArr,"","width=700,height=500,top=200,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
			}
			//常识
			function editKnowledge() {
				var targetDataArr = getTargetData("knowledgeIds", "knowledgeNames");
				window.open("${ctx}/common/getParaSelectTree.action?multiple=2&sourceDataObj=dutyTree&par=1&input_id=knowledgeIds&input_name=knowledgeNames&targetDataArr="+targetDataArr,"","width=700,height=500,top=200,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
			}
			//班次
			function editShift() {
				var orgId = $("#orgId").val();
				window.open("listDutyShift.action?orgId="+orgId,"","width=700,height=500,top=200,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
			}
			
			function rollBack() {
				var pid = $("#orgId").val();
				var dutyShifts = $("#dutyShifts").val();
				$.post("rollBackDutyShift.action", // 服务器页面地址
				{
					pid : pid,
					dutyShifts : dutyShifts
				}, function(date) {
			      window.close();
				});
			}
			//组装已选择数据
			function getTargetData(dataIds, dataNames) {
				var targetDataArr = "";
				var  values = $("#"+dataNames).val();
				if(values != ""){
				    var  value = values.split(",");
				    var  ids = $("#"+dataIds).val();
				    var  id = ids.split(",");
				    $.each(id, function(i, n){//循环班次
				        targetDataArr = targetDataArr + id[i]+","+value[i]+";";
				   });
				}
				return targetDataArr;
			}
		</script>
	</head>
	<body>
	<input type="hidden" id="quartersflag" name="dutyOrganization.quartersflag" value="0"/>
	<input type="hidden" id="chieftype" name="dutyOrganization.chieftype" value="3"/>
	<input type="hidden" id="confirmflag" name="dutyOrganization.confirmflag" value="2"/>
	<input type="hidden" id="holidayflag" name="dutyOrganization.holidayflag" value="1"/>
	<input type="hidden" id="weekendflag" name="dutyOrganization.weekendflag" value="1"/>
	<input type="hidden" id="isshared" name="dutyOrganization.isshared" value="0"/>
	<form id="form" action="saveDutyOrganization.action" method="post">
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2">岗位修改</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="scroll_div" id="center" style="text-align:center;">
				<div class="blank_tr"></div>
					<fieldset class="fieldset_style">
						
						<table class="add_table">
							<tr>
								<td class="add_table_left">
									岗位名称：
								</td>
								<td class="add_table_right">
									<input type="hidden" id="orgId" name="dutyOrganization.pid" value="${dutyOrganization.pid }"/>
									<input type="hidden" id="oldOrgName" name="oldOrgName" value="${dutyOrganization.organizationname }"/>
									<input type="text" id="orgName" name="dutyOrganization.organizationname" maxlength="30"
										class="textInput" value="${dutyOrganization.organizationname }" /><span class="must">*</span>
								</td>
								<td class="add_table_left">
									值班电话：
								</td>
								<td class="add_table_right">
									<input type="text" id="telephone" name="dutyOrganization.telephone" maxlength="15"
										class="textInput" value="${dutyOrganization.telephone }"/>
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									<span class="desc">接班提前时间(分)：</span>
								</td>
								<td class="add_table_right">
									<span class="desc">
										<input type="text" name="dutyOrganization.onTimeBef" id="onTimeBef" maxlength="3"
											class="textInput" onchange="this.value=this.value.replace(/[^\d]/g,'')"
											value="${dutyOrganization.onTimeBef }"/><span class="must">*</span>
									</span>
								</td>
								<td class="add_table_left">
									<span class="desc">交班提前时间(分)：</span>
								</td>
								<td class="add_table_right">
									<span class="desc">
										<input type="text" name="dutyOrganization.offTimeBef" id="offTimeBef" maxlength="3"
											class="textInput" onchange="this.value=this.value.replace(/[^\d]/g,'')"
											value="${dutyOrganization.offTimeBef }"/><span class="must">*</span>
									</span>
								</td>
							</tr>
							<tr>
							   
								<td class="add_table_left">
									启用停用：
								</td>
								<td class="add_table_right">
									<span class="desc">
										<select class="select" name="dutyOrganization.state" style="width: 96%">
											<c:forEach items="${enableOrDisableMap}" var="ed">
											   <option value="${ed.key}" <c:if test="${ed.key == dutyOrganization.state && null != dutyOrganization.pid}">selected</c:if>>
													${ed.value}
												</option>
										   </c:forEach>	
										</select>
									</span>
								</td>
							</tr>
						</table>
					</fieldset>
					<fieldset class="fieldset_style">
						<div class="blank_tr"></div>
						<table class="add_table">
							<tr>
								<td class="add_table_left" width="20%">
									<span class="desc">班次：</span>
								</td>
								<td>
									<span class="desc">
										<input type="hidden" id="shiftIds" name="dutyOrganization.shiftIds" value="${dutyOrganization.shiftIds }"/>
										<input type="text" id="shiftNames" name="dutyOrganization.shiftNames" class="textInput"  readonly="readonly" onclick="editShift();"
											value="${dutyOrganization.shiftNames }"/><span class="must">*</span>
									</span>
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									<span class="desc">值班人员：</span>
								</td>
								<td>
									<span class="desc">
										<input type="hidden" id="qualityerIds" name="dutyOrganization.qualityerIds" value="${dutyOrganization.qualityerIds }"/>
										<input type="text" id="qualityerIds_name" name="dutyOrganization.qualityerNames" value="${dutyOrganization.qualityerNames }" class="textInput" readonly="readonly" onclick="selectMultiPerSon(this);"/><span class="must">*</span>
									</span>
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									描述：
								</td>
								<td width="80%" align="left">
									<textarea name="dutyOrganization.note" id="dutyOrganization.note" cols="45" rows="4" style="width: 98.5%" 
										onchange="if(this.value.length>150){this.value=this.value.substring(0,150);alert('描述的最大长度为150位');}">${dutyOrganization.note }</textarea>
								</td>
							</tr>
						</table>
					</fieldset>
				<div class="blank_tr"></div>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" name="save_button" value="<eoms:lable name='com_btn_save'/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="submitCheck();" />
			<input type="reset" name="button5" id="button5" value="<eoms:lable name='com_btn_close'/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
		</div>
		</form>
	</body>
</html>
