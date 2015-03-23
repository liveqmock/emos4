<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/plugin/swfupload/import.jsp"%>
		<title><eoms:lable name="sm_t_user" />
		</title>
		<link type="text/css" rel="Stylesheet"
			href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript"
			src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript"
			src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script type="text/javascript"
			src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script language="javascript">
			window.onresize = function()
			{
				setCenter(0, LAYOUT_FORM_OPEN);
			}
			window.onload = function() 
			{
				setCenter(0, LAYOUT_FORM_OPEN);getPageMenu('div1_1','div1');PageMenuActive('div1_1','div1');
			}
			var rolejt = null;
			function roletable()
        	{
        		var initData = document.getElementById("initRoleData").value.substring('&ssemi;'.length).split('&ssemi;');
        		var initDataArr = new Array();
        		if(document.getElementById("initRoleData").value != "")
        		{
	        		for(var i = 0; i < initData.length; i++)
	        		{
	        			initDataArr.push(initData[i].split('&comm;'));
	        		}
        		}
        		else
        			initDataArr = [];
        		var systemVar = '${systemVar}';
        		if(systemVar == '')
        		{
	            	rolejt = new JsTable('roletable', true, 'tableborder', null, null, null, null, null, null,
	                [
	                    new JsCell(false, 'roleid', "<eoms:lable name='com_lb_role'/>ID", [], 0, [], ''),
	                    new JsCell(true, 'roletype', "<eoms:lable name='sm_lb_roleType'/>", [], 1, [], ''),
	                    new JsCell(true, 'rolename', "<eoms:lable name='sm_lb_roleName'/>", [], 2, [], ''),
	                    new JsCell(true, 'orgtype', "<eoms:lable name='sm_lb_orgType'/>", [], 3, [], ''),
	                    new JsCell(true, 'operate' , "<eoms:lable name='com_btn_delete'/>", [['width', 80]], -1, [], '<input type="button" class="button" value="<eoms:lable name='sm_lb_delete_2'/>" onclick="delrow(\'{@COL0}\',\'{@COL3}\',event)">')
	                ],
	                initDataArr
					);
				}
				else
				{
					rolejt = new JsTable('roletable', true, 'tableborder', null, null, null, null, null, null,
	                [
	                    new JsCell(false, 'roleid', "<eoms:lable name='com_lb_role'/>ID", [], 0, [], ''),
	                    new JsCell(true, 'roletype', "<eoms:lable name='sm_lb_roleType'/>", [], 1, [], ''),
	                    new JsCell(true, 'rolename', "<eoms:lable name='sm_lb_roleName'/>", [], 2, [], ''),
	                    new JsCell(true, 'orgtype', "<eoms:lable name='sm_lb_orgType'/>", [], 3, [], '')
	                ],
	                initDataArr
					);
				}
           	 	rolejt.draw(document.getElementById('rolejtList'));
           	 	changeRow_color("roletable");
        	}
        	
        	function delrow(key,type,src)
        	{
        		if(type=="部门")
        		{
        			//var eventSrc = src.srcElement || src.target;
        			//eventSrc.disabled = true;
        			alert("您不能删除成员部门角色！");
        			return;
        		}
        		rolejt.deleterow(key);
        	}
        	
        	function addrowdata(datas,type)
        	{
        		var addData = datas.substring('&semi;'.length).split('&semi;');
        		var addDataArr = new Array();
        		for(var i = 0; i < addData.length; i++)
        		{
        			addData[i] += "&comm;<eoms:lable name='com_lb_user'/>";
        			addDataArr.push(addData[i].split('&comm;'));
        		}
        		if(type=="role")
        			rolejt.addrow(addDataArr);
        	}
        	
			/*
			 在提交表单之前比较修改前后的兼职部门和所属组id并将比较结果分别赋给表单域groupIds和ptdepIds
			*/
			function setIds()
			{
				var isNew = '${userID}';
				if(isNew=='')
				{//新建的时候不用比较
					var depids = document.getElementById('userInfoPtdepid').value;
					var groupids = document.getElementById('userInfoGroupid').value;
					if(depids=='')
					{
						document.getElementById('ptdepIds').value = ';';
					}
					else
					{
						document.getElementById('ptdepIds').value = depids+';';
					}
					if(groupids=='')
					{
						document.getElementById('groupIds').value = ';';
					}
					else
					{
						document.getElementById('groupIds').value = groupids+';';
					}
				}
				else
				{//更新的时候需要比较
					document.getElementById('ptdepIds').value = compareStr(document.getElementById('oldPtdepid').value,document.getElementById('userInfoPtdepid').value);
					document.getElementById('groupIds').value = compareStr(document.getElementById('oldGroupid').value,document.getElementById('userInfoGroupid').value);
				}
			} 
			/*
			 打开部门树以供选择
			 isRadio 表示是否是多选 还是单选    1表示多选  0表示单选
			*/
			var global_isNew = '${userID}';
			var olddepid = '';
			var olddepname = '';
			function openDepCheckBoxTree(form_name,input_name,input_id,isRadio)
			{
				if(global_isNew!='' && input_name=='userInfoDepname')
				{//如果是修改，在修改行政部门时，需记录前一次的值
					olddepid = document.getElementById('userInfoDepid').value;
					olddepname = document.getElementById('userInfoDepname').value;
				}
				if(form_name!='undefined' && input_name!='undefined')
				{
					if(isRadio=="1"){
						//showModalDialog('${ctx}/common/tools/organiseTree.jsp?isRadio=1&isSelectType=0&form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:287px;dialogHeight:400px');
						showModalDialog('${ctx}/common/tools/depCheckBoxTree.jsp?form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:380px;dialogHeight:480px');
					}else if(isRadio=="0"){
						//showModalDialog('${ctx}/common/tools/organiseTree.jsp?isRadio=0&isSelectType=0&form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:287px;dialogHeight:400px');
						showModalDialog('${ctx}/common/tools/depOpenRadioTree.jsp?form_name='+form_name+"&input_name="+input_name+"&input_id="+input_id,window,'help:no;center:true;scroll:no;status:no;dialogWidth:380px;dialogHeight:480px');
					}
				}
				if(global_isNew!='' && input_name=='userInfoDepname')
				{//如果是修改，在修改行政部门时，需要同步所属组里面对应的值
					var newdepid = document.getElementById('userInfoDepid').value;
					var newdepname = document.getElementById('userInfoDepname').value;
					if(newdepid == olddepid)
					{
						return;
					}
					else
					{
						var groupidarr = document.getElementById('userInfoGroupid').value.split(',');
						var groupnamearr = document.getElementById('userInfoGroupname').value.split(',');
						for(var i=0;i<groupidarr.length;i++)
						{
							if(olddepid==groupidarr[i])
							{
								groupidarr[i] = newdepid;
								groupnamearr[i] = newdepname;
								olddepid==newdepid;
								olddepname==newdepname;
								break;
							}
						}
						document.getElementById('userInfoGroupid').value = groupidarr.join(',');
						document.getElementById('userInfoGroupname').value = groupnamearr.join(',');
					}
				}
			}
			/*
			 检查是否选择了行政部门，并在所属组中追加选择的行政部门
			*/
			function checkDep()
			{
				var depValue = document.getElementById('userInfoDepid').value;//记录行政部门ID
				var depName = document.getElementById('userInfoDepname').value;//记录行政部门名称
				if(depValue=='')
				{
					alert('<eoms:lable name="sm_msg_chooseDep"/>！');
					return false;
				}
				else
				{
					var groupValue = document.getElementById('userInfoGroupid').value;
					if(groupValue=='')
					{
						document.getElementById('userInfoGroupid').value = depValue;
						document.getElementById('userInfoGroupname').value = depName;
					}
					return true;
				}
			}
			/*
			 1、新建时登录名唯一性验证
			 2、所属部门验证
			 3、生成追加的和删除的兼职部门、所属组id串	
			*/
			function formSubmit()
			{
				var src = document.getElementById("userInfo.loginname");
				var temp = "${userID}";
				var pm = "${pwdmanage}";
				document.getElementById("roleIdXml").value = rolejt.gettablexml().toString();
				if(temp=='')
				{//是新建 做登录名唯一性验证
					var reg = /^\w{1,20}$/;
					if(src.value==''||src.value.length==0||!reg.test(src.value))
					{
						return Validator.Validate(document.forms[0],2);
					}
					$.get("${ctx}/userManager/checkUnique.action",{loginName:src.value},function(result)
					{
						if(result=='false')
						{
							src.style.color = 'red';
							alert('<eoms:lable name="sm_msg_uniqueChk"/>！');
							src.focus();
						}
						else
						{
							var result = 'true';
							var pwdSrc = document.getElementById('userInfo.pwd');
							if(pm == 'true')
							{
								var patrn=/^[A-Za-z0-9]{1,30}$/;
								var patrnSz=/^[0-9]{1,30}$/;
								var patrnZm=/^[A-Za-z]{1,30}$/;
								var len = pwdSrc.value.length;
								if(len < 6)
								{
									result = '为了您的安全性，密码不得少于6位字符，请重新输入！';
								}
								else if(!patrn.exec(pwdSrc.value) && (patrnSz.exec(pwdSrc.value || patrnZm.exec(pwdSrc.value))))
								{
									result = '密码只能并且必须包含字母和数字，请重新输入！';
								}
								else if(pwdSrc.value.substring(0, len-1) == pwdSrc.value.substring(1, len))
								{
									result = '为了您的安全性，密码不能输入连续的相同字符！';
								}
								else if(pwdSrc.value == src.value)
								{
									result = '密码与用户登陆名一致，为了您的安全性，请重新输入！';
								}
								else if(pwdSrc.value == document.getElementById('userInfo.mobile').value)
								{
									result = '密码与手机号码一致，为了您的安全性，请重新输入！';
								}
								else if(pwdSrc.value == document.getElementById('userInfo.phone').value)
								{
									result = '密码与固定电话号码一致，为了您的安全性，请重新输入！';
								}
								else if(pwdSrc.value == document.getElementById('userInfo.qq').value)
								{
									result = '密码与QQ号码一致，为了您的安全性，请重新输入！';
								}
							}
							if(result == 'true')
							{
								src.style.color = 'black';
								if(Validator.Validate(document.forms[0],2)&&checkDep())
								{
									setIds();
									document.getElementById('cryptPwd').value = 'true';
									document.forms[0].submit();
								}
							}
							else
							{
								pwdSrc.style.color = 'red';
								alert(result);
								pwdSrc.focus();
							}
						}
					});
				}
				else
				{//是修改 不做登录名唯一性验证
					if(Validator.Validate(document.forms[0],2)&&checkDep())
					{
						setIds();
						if(document.getElementById('cryptPwd').value==document.getElementById('userInfo.pwd').value)
						{
							document.getElementById('cryptPwd').value = 'false';
							document.forms[0].submit();
						}
						else
						{
							document.getElementById('cryptPwd').value = 'true';
							if(pm == 'true')
							{
								var pwdSrc = document.getElementById('userInfo.pwd');
								$.get("${ctx}/userManager/pwdSarbanes.action",{loginName:src.value,pwd:pwdSrc.value},function(result)
								{
									if(result != '')
									{
										pwdSrc.style.color = 'red';
										alert(result);
										pwdSrc.focus();
									}
									else
									{
										document.forms[0].submit();
									}
								});
							}
							else
							{
								document.forms[0].submit();
							}
						}
					}
				}
			}
		</script>
	</head>
	<body>
		<form action="${ctx}/userManager/userSave.action" method="post" name="saveUserForm">
			<input type="hidden" name="ptdepIds" id="ptdepIds" value="${ptdepIds }" />
			<input type="hidden" name="groupIds" id="groupIds" value="${groupIds }" />
			<input type="hidden" name="oldPtdepid" id="oldPtdepid" value="${userInfo.ptdepid }" />
			<input type="hidden" name="oldGroupid" id="oldGroupid" value="${userInfo.groupid }" />
			<input type="hidden" name="userInfo.lastmodifier" id="userInfo.lastmodifier" value="${userInfo.lastmodifier }" />
			<input type="hidden" name="userInfo.lastmodifytime" id="userInfo.lastmodifytime" value="${userInfo.lastmodifytime }" />
			<input type="hidden" name="systemVar" id="systemVar" value="${systemVar}" />
			<input type="hidden" name="cryptPwd" id="cryptPwd" value="${userInfo.pwd}" />
			<input type="hidden" name="isExistImage" id="isExistImage" value="${userInfo.image}" />
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2"> <c:choose>
									<c:when test="${systemVar=='1'}">
										${nodePath}
									</c:when>
									<c:otherwise>
										<eoms:lable name="sm_t_user" />
									</c:otherwise>
								</c:choose> </span> </span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="add_scroll_div_x" id="center">
					<fieldset class="fieldset_style">
						<legend>
							<eoms:lable name="com_lb_basicInfo" />
						</legend>
						<div class="blank_tr"></div>
						<div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_loginName" />：
										<span class="must">*</span>
									</td>
									<%-- 下面判断的目的：根据userID判断此页面打开是为了新增还是修改，从而决定登录名字段是否可编辑 --%>
									<c:choose>
										<c:when test="${userID==null}">
											<td width="28%">
												<input type="text" name="userInfo.loginname"
													id="userInfo.loginname" class="textInput"
													onkeypress="return clearSpecialChar(event);" />
												<validation id="userInfo.loginnameV" dataType="Custom"
													regexp="^\w{1,60}$"
													msg="<eoms:lable name='sm_msg_loginNameConstraint'/>！" />
											</td>
										</c:when>
										<c:otherwise>
											<td width="28%">
												<input type="hidden" name="userInfo.pid" id="userInfo.pid"
													value="${userInfo.pid }" />
												<input type="text" readonly="readonly"
													id="userInfo.loginname" name="userInfo.loginname"
													class="textInput" value="${userInfo.loginname }"
													onkeypress="return clearSpecialChar(event);" />
											</td>
										</c:otherwise>
									</c:choose>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_fullName" />：
										<span class="must">*</span>
									</td>
									<td width="28%">
										<input type="text" id="userInfo.fullname"
											name="userInfo.fullname" class="textInput"
											value="${userInfo.fullname }"
											onkeypress="return clearSpecialChar(event);" />
										<validation id="userInfo.fullnameV" dataType="Custom"
											regexp="^.{1,20}$"
											msg="<eoms:lable name='sm_msg_fullNameConstraint'/>！" />
									</td>
									<td rowspan="8" valign="top" align="center" width="17%">
										<!-- <input type="file" name="image"/> -->
										<div class="user_photo">
											<span class="top"><eoms:lable name="sm_lb_userPic" />
											</span>
											<span class="middle"> <c:choose>
													<c:when test="${userInfo.image==null||suerInfo.image==''}">
														<img id="imageList"
															src="${ctx}/common/userimage/default.png"
															style="width: 150px; height: 160px" />
													</c:when>
													<c:otherwise>
														<img id="imageList" src="${ctx}/userManager/getUserImgStream.action?realName=${image}"
															style="width: 150px; height: 160px" />
													</c:otherwise>
												</c:choose> </span>
											<span class="bottom"> <atta:fileupload id="userImage"
													attchmentGroupId="${userInfo.image}"
													uploadBtnIsVisible="false" fileTypes="*.jpg;*.gif;*.png"
													uploadTableVisible="false" downTableVisible="false"
													progressIsVisible="false" isMultiUpload="false"
													createDirByDate="false" isMultiDownload="false"
													returnValue="6" operationParams="0,0,0"
													isAutoUpload="true" uploadDestination="sys_userimage"
													flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf">
												</atta:fileupload> <input type="hidden" name="userInfo.image"
													value="${userInfo.image}" /> </span>
										</div>
									</td>
								</tr>
								<tr>
									<td class="texttd" width="15%">
										<span class="texttd"><eoms:lable name="sm_lb_position" />：</span>
									</td>
									<td width="28%">
										<eoms:select name="userInfo.position" style="select"
											dataDicTypeCode="userposition" value="${userInfo.position}" />
									</td>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_userType" />：
										<span class="must">*</span>
									</td>
									<td width="28%">
										<eoms:select name="userInfo.type" style="select"
											dataDicTypeCode="usertype" value="${userInfo.type}"
											isnull="false" />
										<validation id="userInfo.typeV" dataType="Require"
											msg="<eoms:lable name='sm_msg_usertypeConstraint'/>！" />
									</td>
									<td class="texttd">
									</td>
								</tr>
								
								<!-- strat增加用户工位和是否vip用户  -->		
								<tr>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_station" />：
									</td>
									<td width="28%">
										<input type="text" id="userInfo.fullname"
											name="userInfo.station" class="textInput"
											value="${userInfo.station }"
											onkeypress="return clearSpecialChar(event);" />
									</td>
									
									<td class="texttd" width="15%">
										<span class="texttd"><eoms:lable name="sm_lb_isVip" />：</span>
									</td>
									<td width="28%">
										<eoms:select name="userInfo.isVip" style="select"
											dataDicTypeCode="userisVip" value="${userInfo.isVip}" />
									</td>
									
									<td class="texttd">
									</td>
								</tr>
								<!-- end增加用户工位和是否vip用户  -->	
								
								<tr>
									<td class="texttd">
										<eoms:lable name="sm_lb_userPwd" />：
										<span class="must">*</span>
									</td>
									<td>
										<input type="password" id="userInfo.pwd" name="userInfo.pwd"
											class="textInput" value="${userInfo.pwd }"
											onkeypress="return clearSpecialChar(event);" />
										<validation id="userInfo.pwdV" dataType="Limit" min="3" max="30"
											msg="<eoms:lable name='sm_msg_pwdConstraint'/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name="com_lb_mobile" />：
									</td>
									<td>
										<input type="text" name="userInfo.mobile" id="userInfo.mobile" class="textInput"
											value="${userInfo.mobile }" maxlength="50" />
										<validation id="userInfo.mobileV" require="false"
											dataType="Custom" regexp="^[0-9\-]{0,50}$"
											msg="<eoms:lable name='sm_msg_mobileConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_fax" />：
									</td>
									<td>
										<input type="text" name="userInfo.fax" id="userInfo.fax" class="textInput"
											value="${userInfo.fax }" maxlength="50" />
										<validation id="userInfo.faxV" require="false"
											dataType="Custom" regexp="^[0-9\(\)\-]{0,50}$"
											msg="<eoms:lable name='sm_msg_faxConstraint'/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name="com_lb_phone" />：
									</td>
									<td>
										<input type="text" name="userInfo.phone" id="userInfo.phone" class="textInput"
											value="${userInfo.phone }" maxlength="50" />
										<validation id="userInfo.phoneV" require="false"
											dataType="Custom" regexp="^[0-9\(\)\-]{0,50}$"
											msg="<eoms:lable name='sm_msg_phoneConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_status" />：
										<span class="must">*</span>
									</td>
									<td>
										<eoms:select name="userInfo.status" style="select"
											dataDicTypeCode="status" value="${userInfo.status}"
											isnull="false" />
										<validation id="userInfo.statusV" dataType="Require"
											msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name="com_lb_orderNum" />：
										<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="userInfo.ordernum" id="userInfo.ordernum" class="textInput"
											value="${userInfo.ordernum }" />
										<validation id="userInfo.ordernumV" dataType="Custom"
											regexp="^[0-9]{1,5}$"
											msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										E-mail：
									</td>
									<td>
										<input type="text" name="userInfo.email" id="userInfo.email" class="textInput"
											value="${userInfo.email }" maxlength="100" />
										<validation id="userInfo.emailV" require="false"
											dataType="Email"
											msg="<eoms:lable name='sm_msg_emailConstraint'/>！" />
									</td>
									
									<td class="texttd" width="15%">
										<span class="texttd"><eoms:lable name="com_lb_oaNumber" />：</span>
									</td>
									<td width="28%">
										<input type="text" id="userInfo.oaNumber"
											name="userInfo.oaNumber" class="textInput"
											value="${userInfo.oaNumber }"
											onkeypress="return clearSpecialChar(event);" />
									</td>
									
								</tr>
								<tr>
									<td class="texttd" width="15%">
										<span class="texttd"><eoms:lable name="是否休假" />：</span>
									</td>
									<td width="28%">
										<eoms:select name="userInfo.holidayState" style="select"
											dataDicTypeCode="HolidayState" value="${userInfo.holidayState}" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_remark" />：
									</td>
									<td colspan="3" rowspan="2">
										<textarea name="userInfo.remark" id="textarea2" rows="4"
											class="textInput">${userInfo.remark }</textarea>
										<validation id="userInfo.remarkV" require="false"
											dataType="Limit" max="160"
											msg="<eoms:lable name='sm_msg_remarkConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										&nbsp;
									</td>
								</tr>
							</table>
						</div>
					</fieldset>
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style">
						<legend>
							<eoms:lable name="com_lb_advancedInfo" />
						</legend>
						<div class="blank_tr"></div>
						<div class="tab_bg">
							<div class="tab_show" id="div1_1"
								onclick="PageMenuActive('div1_1','div1')">
								<span><eoms:lable name="com_lb_subordOrga" />
								</span>
							</div>
							<div class="tab_hide" id="div1_2"
								onclick="PageMenuActive('div1_2','div2')">
								<span><eoms:lable name="com_lb_subordSysRole" />
								</span>
							</div>
							<div class="tab_hide" id="div1_4"
								onclick="PageMenuActive('div1_4','div4')">
								<span><eoms:lable name="com_lb_extInfo" />
								</span>
							</div>
						</div>
						<div class="tabContent">
							<div id="div1">
								<table class="add_user">
									<tr>
										<td valign="top" class="texttd">
											<eoms:lable name="com_lb_adminDep" />：
											<span class="must">*</span>
										</td>
										<td>
											<input type="text" readonly class="add_user_textarea"
												id="userInfoDepname" name="userInfo.depname"
												value="${userInfo.depname}" />
											<input type="hidden" name="userInfo.depid" id="userInfoDepid"
												value="${userInfo.depid}" />
										</td>
										<td class="texttd" valign="top">
											<c:if test="${systemVar==''}">
												<input type="button" name="button3" id="button3"
													value="<eoms:lable name="com_btn_choose"/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'"
													onclick="openDepCheckBoxTree('saveUserForm','userInfoDepname','userInfoDepid','0');" />
											</c:if>
										</td>
									</tr>
								</table>
								<table class="add_user">
									<tr>
										<td valign="top" class="texttd">
											<eoms:lable name="com_lb_concurrentDep" />：
										</td>
										<td>
											<input type="hidden" name="userInfo.ptdepid"
												id="userInfoPtdepid" value="${userInfo.ptdepid}" />
											<textarea readonly rows="3" cols="" class="add_user_textarea"
												name="userInfo.ptdepname" id="userInfoPtdepname">${userInfo.ptdepname}</textarea>
										</td>
										<td class="texttd" valign="top">
											<c:if test="${systemVar==''}">
												<input type="button" name="button2" id="button2"
													value="<eoms:lable name="com_btn_choose"/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'"
													onclick="openDepCheckBoxTree('saveUserForm','userInfoPtdepname','userInfoPtdepid','1');" />
											</c:if>
										</td>
									</tr>
								</table>
								<table class="add_user">
									<tr>
										<td valign="top" class="texttd">
											<eoms:lable name="sm_lb_group" />：
										</td>
										<td>
											<input type="hidden" name="userInfo.groupid"
												id="userInfoGroupid" value="${userInfo.groupid}" />
											<textarea readonly rows="3" cols="" name="userInfo.groupname"
												id="userInfoGroupname" class="add_user_textarea">${userInfo.groupname}</textarea>

										</td>
										<td class="texttd" valign="top">
											<c:if test="${systemVar==''}">
												<input type="button" name="button4" id="button4"
													value="<eoms:lable name="com_btn_choose"/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'"
													onclick="openDepCheckBoxTree('saveUserForm','userInfoGroupname','userInfoGroupid','1');" />
											</c:if>
										</td>
									</tr>
								</table>
							</div>
							<div id="div2" style="display: none">
								<c:set value="" var="roledata" scope="request" />
								<c:forEach items="${roShowList}" var="roShow">
									<c:set
										value="${roledata}&ssemi;${roShow.roleid}&comm;${roShow.roletype}&comm;${roShow.rolename}&comm;${roShow.orgtype}"
										var="roledata" />
								</c:forEach>
								<input type="hidden" id="initRoleData" name="initRoleData"
									value="${roledata}" />
								<div id="rolejtList" style="width: 100%; height: 100%"></div>
								<script language="javascript">roletable();</script>
								<input type="hidden" id="roleIdXml" name="roleIdXml" value="" />
								<div class="selet_role_div">
									<c:if test="${systemVar==''}">
										<input type="button" name="button5" id="button5"
											value="<eoms:lable name="com_btn_choose"/>"
											class="operate_button"
											onmouseover="this.className='operate_button_hover'"
											onmouseout="this.className='operate_button'"
											onclick="openwindow('${ctx}/userManager/selectRole.action?orgtype=1&orgid=${userInfo.pid }','',700,400);" />
									</c:if>
								</div>
							</div>
							<div id="div4" style="display: none">
								<table class="add_user">
									<tr>
										<td class="texttd" width="15%">
											<eoms:lable name='com_lb_creater' />：
										</td>
										<td width="30%">
											<input type="text" value="${userInfo.createrShow}"
												class="textInput" readonly="readonly" />
											<input type="hidden" name="userInfo.creater"
												value="${userInfo.creater }" />
										</td>
										<td class="texttd" width="15%">
											<eoms:lable name='com_lb_createTime' />：
										</td>
										<td width="30%">
											<input type="hidden" name="userInfo.createtime"
												value="${userInfo.createtime }" />
											<input type="text" name="createtimeShow"
												value='${userInfo.createtimeshow }' class="textInput"
												readonly="readonly" />
										</td>
									</tr>
									<tr>
										<td class="texttd">
											IP：
										</td>
										<td>
											<input type="text" name="userInfo.ipaddress" id="userInfo.ipaddress"
												value="${userInfo.ipaddress}" class="textInput" />
											<validation id="userInfo.ipaddressV" require="false"
												msg="<eoms:lable name='sm_msg_ipConstraint'/>！"
												dataType="Custom" regexp="^(\d+)\.(\d+)\.(\d+)\.(\d+)$" />
										</td>
										<td class="texttd">
											<eoms:lable name='com_lb_lastLoginTime' />：
										</td>
										<td>
											<input type="hidden" name="userInfo.lastlogintime" id="userInfo.lastlogintime"
												value="${userInfo.lastlogintime }" />
											<input type="text" name="lastlogintimeShow"
												value="${userInfo.lastlogintimeshow }" class="textInput"
												readonly="readonly" />
										</td>
									</tr>
									<tr>
										<td class="texttd">
											MSN：
										</td>
										<td>
											<input type="text" name="userInfo.msn" id="userInfo.msn"
												value="${userInfo.msn }" class="textInput" />
											<validation id="userInfo.msnV" require="false"
												dataType="Email"
												msg="<eoms:lable name='sm_msg_msnConstraint'/>！" />
										</td>
										<td class="texttd">
											QQ：
										</td>
										<td>
											<input type="text" name="userInfo.qq" id="userInfo.qq" value="${userInfo.qq }"
												class="textInput" />
											<validation id="userInfo.qqV" dataType="Custom"
												regexp="^\d{0,20}$"
												msg="<eoms:lable name='sm_msg_qqConstraint'/>！" />
										</td>
									</tr>

								</table>
							</div>
						</div>
					</fieldset>
					<div class="blank_tr"></div>
				</div>
			</div>
			<div class="add_bottom">
				<c:if test="${isCanSave != 'false'}">
					<input type="button" name="button5" id="button5"
						value="<eoms:lable name="com_btn_save"/>" class="save_button"
						onmouseover="this.className='save_button_hover'"
						onmouseout="this.className='save_button'" onclick="formSubmit();" />
				</c:if>
				<c:if test="${systemVar==''}">
					<input type="button" name="button5" id="button5"
						value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
						onmouseover="this.className='cancel_button_hover'"
						onmouseout="this.className='cancel_button'"
						onclick="window.close();" />
				</c:if>
			</div>
		</form>
		<script language="javascript">
			var n = 1;
			var bus = new AjaxBus();
			userImage.afterUploadSuccess = function(serverData)
			{
				var isExistImage = document.getElementById('isExistImage').value;
				bus.callBackPost("${ctx}/userManager/getImage.action?imageGroupId="+serverData+"&isExistImage="+isExistImage, null);
			}
			bus.onComplete = function(responseText, responseXml)
			{
				var image = responseText.split(';');
				document.getElementById('userInfo.image').value = image[1];
				document.getElementById("imageList").src = "${ctx}/userManager/getUserImgStream.action?realName="+image[0];
			}				
		</script>
	</body>
</html>
