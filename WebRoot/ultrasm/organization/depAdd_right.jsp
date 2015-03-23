<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_dep"/></title>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script> 
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);
				getPageMenu('div1_1','div1');
				PageMenuActive('div1_1','div1');
			}
			/*--JTABLE部门成员和部门角色开始--*/
			var userjt = null;
			function usertable()
			{
	       		var initData = document.getElementById("initUserData").value.substring("&semi;".length).split('&semi;');
	       		var initDataArr = new Array();
	       		if(document.getElementById("initUserData").value != "")
	       		{
	        		for(var i = 0; i < initData.length; i++)
	        		{
	        			initDataArr.push(initData[i].split('&comm;'));
	        		}
	       		}
	       		else
	       		{
	       			initDataArr = [];
	       		}
	           	userjt = new JsTable('usertable', true, 'tableborder', null, null, null, null, null, null,
	               [
	                   new JsCell(false, 'userid', '人员ID', [], 0, [], ''),
	                   new JsCell(true, 'loginname', "<eoms:lable name='sm_lb_loginName'/>", [], 1, [], ''),
	                   new JsCell(true, 'fullname', "<eoms:lable name='sm_lb_fullName'/>", [], 2, [], ''),
	                   new JsCell(true, 'depname', "<eoms:lable name='com_lb_adminDep'/>", [], 3, [], ''),
	                   new JsCell(true, 'operate' , "<eoms:lable name='com_btn_delete'/>", [['width', 80]], -1, [], '<input type="button" class="button" value="删　除" onclick="userdelrow(\'{@COL0}\')">')
	               ],
	            initDataArr
				);
	       	 	userjt.draw(document.getElementById('userjtList'));
	       	 	changeRow_color("usertable");
			}
			
			var rolejt = null;
			function roletable()
			{
	       		var initData = document.getElementById("initRoleData").value.substring("&semi;".length).split('&semi;');
	       		var initDataArr = new Array();
	       		if(document.getElementById("initRoleData").value != "")
	       		{
	        		for(var i = 0; i < initData.length; i++)
	        		{
	        			initDataArr.push(initData[i].split('&comm;'));
	        		}
	       		}
	       		else
	       		{
	       			initDataArr = [];
	       		}
	           	rolejt = new JsTable('roletable', true, 'tableborder', null, null, null, null, null, null,
	               [
	                   new JsCell(false, 'roleid', '角色ID', [], 0, [], ''),
	                   new JsCell(true, 'roletype', "<eoms:lable name='sm_lb_roleType'/>", [], 1, [], ''),
	                   new JsCell(true, 'rolename', "<eoms:lable name='sm_lb_roleName'/>", [], 2, [], ''),
	                   new JsCell(true, 'operate' , "<eoms:lable name='com_btn_delete'/>", [['width', 80]], -1, [], '<input type="button" class="button" value="删　除" onclick="roledelrow(\'{@COL0}\')">')
	               ],
	               initDataArr
				);
	       	 	rolejt.draw(document.getElementById('rolejtList'));
	       	 	changeRow_color("roletable");
			}
			function userdelrow(key)
	        {
	        	userjt.deleterow(key);
	        }
			function roledelrow(key)
	        {
	        	rolejt.deleterow(key);
	        }
	        function addrowdata(datas,type)
	        {
	        	var addData = datas.substring('&semi;'.length).split('&semi;');
	        	var addDataArr = new Array();
	        	for(var i = 0; i < addData.length; i++)
	        	{
	        		addDataArr.push(addData[i].split('&comm;'));
	        	}
	        	if(type=="user")
	        	{
	        		userjt.addrow(addDataArr);
	        		changeRow_color("usertable");
	        	}
	        	else
	        	{
	        		rolejt.addrow(addDataArr);
	        		changeRow_color("roletable");
	        	}
	        }
	        /*--JTABLE部门成员和部门角色结束--*/
	        
	        /*
			 更新文本域中部门的全名
			*/		
			function updateFullName(src)
			{
				var depType = document.getElementsByName('depinfo.deptype')[0].value;
				if(depType=='1')
				{
					document.getElementById('depinfo.depfullname').value = src.value;
				}
				else
				{
					//根据父部门id取得父部门的全名，加上本部门名称就是本部门的全名
					var parentid = document.getElementById('depinfo.parentid').value;
					$.get("${ctx}/depManager/getDepFullName.action",{parentid:parentid},function(result)
					{
						if(result.lastIndexOf(',')!=-1)
						{
							var parent_fullname = result.split(',')[0];
							document.getElementById('depinfo.depfullname').value = parent_fullname + '.' + src.value;
						}
					});
				}
			}
			
	        /*
	         根据部门类型的变动改变部门全名和上级部门文本域的状态
	        */
			function depTypeTo(src)
			{
				var rootDep = '${rootDep}';
				if(rootDep=='true')
				{
					src.value=1;//总公司的值
					src.blur();
					alert('<eoms:lable name="sm_msg_topDepAddConstraint"/>！');
					return;
				}
				else
				{
					
					if(src.value=='1')
					{
						src.options[0].selected = true;
						src.focus();
						alert('<eoms:lable name="sm_msg_notTopDepAddConstraint"/>！');
						return;
					}
					if(document.getElementById("temp_PdepType").value>document.getElementsByName("depinfo.deptype")[0].value)
					{
						document.getElementsByName("depinfo.deptype")[0].value = "";
						alert("<eoms:lable name='sm_msg_choosedepTypeConstraint'/>！");
						return;
					}
				}
			}
			function formSubmit()
			{
				document.getElementById("userIdXml").value = userjt.gettablexml().toString();
				document.getElementById("roleIdXml").value = rolejt.gettablexml().toString();
				if(Validator.Validate(document.forms[0],2))
				{
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
	  <input type="hidden" name="temp_PdepType" id="temp_PdepType" value="${PdepType}"/>
	  <form action="${ctx}/depManager/depSave_right.action" method="post" name="addDepform">
	  	<input type="hidden" id="depinfo.depdns" name="depinfo.depdns" value="${depinfo.depdns }"/>
	  	<input type="hidden" id="depinfo.depdn" name="depinfo.depdn" value="${depinfo.depdn }"/>
	  	<input type="hidden" id="depinfo.creater" name="depinfo.creater" value="${depinfo.creater }"/>
	  	<input type="hidden" id="depinfo.createtime" name="depinfo.createtime" value="${depinfo.createtime }"/>
	  	<input type="hidden" id="depinfo.lastmodifier" name="depinfo.lastmodifier" value="${depinfo.lastmodifier }"/>
	  	<input type="hidden" id="depinfo.lastmodifytime" name="depinfo.lastmodifytime" value="${depinfo.lastmodifytime }"/>
	  	<input type="hidden" id="dialog_storeValue" name="dialog_storeValue" id="dialog_storeValue" value=""/>
	  	<input type="hidden" id="dialog_storeName" name="dialog_storeName" id="dialog_storeName" value=""/>
		<c:if test="${depID!=null}"><input type="hidden" id="depinfo.pid" name="depinfo.pid" value="${depinfo.pid}"/></c:if>
		<div class="content">
			<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_saves_button" id="page_saves_button" onmouseover="this.className='page_saves_button_hover'" onmouseout="this.className='page_saves_button'" onclick="formSubmit();" text="com_btn_save" />
	  	 		</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						<eoms:lable name="sm_t_depInfo"/>
					</legend>
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div class="tab_show" id="div1_1"
							onclick="PageMenuActive('div1_1','div1')">
							<span><eoms:lable name="com_lb_basicInfo"/></span>
						</div>
						<div class="tab_hide" id="div1_2"
							onclick="PageMenuActive('div1_2','div2')">
							<span><eoms:lable name="sm_lb_depUsers"/></span>
						</div>
						<div class="tab_hide" id="div1_3"
							onclick="PageMenuActive('div1_3','div3')">
							<span><eoms:lable name="com_lb_subordSysRole"/></span>
						</div>
					</div>
					<div class="tabContent">
						<div id="div1">
							
							<table class="add_user">
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_department"/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<input type="text" name="depinfo.depname" id="depinfo.depname" class="textInput" value="${depinfo.depname }" onblur="updateFullName(this)" onkeypress="return clearSpecialChar(event);"/>
										<validation id="depinfo.depnameV" dataType="Custom" regexp="^.{1,30}$" msg="<eoms:lable name='sm_msg_depConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fullName"/>：
									</td>
									<td style="width:35%">
										<input type="text" name="depinfo.depfullname" id="depinfo.depfullname" value="${depinfo.depfullname}" class="textInput" readonly="readonly"/>
										<validation id="depinfo.depfullnameV" dataType="Custom" regexp="^.{1,150}$" msg="<eoms:lable name='sm_msg_depFullNameConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<span class="texttd"><eoms:lable name="sm_lb_depType"/>：<span class="must">*</span></span>
									</td>
									<td>
										<eoms:select name="depinfo.deptype" style="select" dataDicTypeCode="deptype" value="${depinfo.deptype}" onChangeFun="depTypeTo(this)"/>
										<validation id="depinfo.deptypeV" dataType="Require" msg="<eoms:lable name='sm_msg_depTypeConstraint'/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name="sm_lb_parentDep"/>：
									</td>
									<td id="dynamicTd">
										<input type="text" readonly="readonly" id="parentidShow" name="parentidShow" value="${depinfo.parentShow }" class="textInput"/>
										<input type="hidden" id="depinfo.parentid" name="depinfo.parentid" value ="${depinfo.parentid}"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_status"/>：
									</td>
									<td>
										<eoms:select name="depinfo.status" style="select" dataDicTypeCode="status"  value="${depinfo.status}" isnull="false"/>
										<validation id="depinfo.statusV" dataType="Require" msg="<eoms:lable name='sm_msg_statusConstraint'/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name="com_lb_orderNum"/>：<span class="must">*</span>
									</td>
									<td>
										<input type="text" id="depinfo.ordernum" name="depinfo.ordernum" value="${depinfo.ordernum }" class="textInput" />
										<validation id="depinfo.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="sm_lb_assignee"/>：
									</td>
									<td>
										<input type="text" id="depinfo.depassginee" name="depinfo.depassginee" value="${depinfo.depassginee }" class="textInput" onkeypress="return clearSpecialChar(event);"/>
										<validation id="depinfo.depassgineeV" require="false" dataType="Limit" max="20" msg="<eoms:lable name='sm_msg_depassgineeConstraint'/>！" />
									</td>
									<td class="texttd">
										<eoms:lable name="com_lb_contactMethod"/>：
									</td>
									<td>
										<input type="text" id="depinfo.depphone" name="depinfo.depphone" value="${depinfo.depphone }" maxlength="50" class="textInput" />
										<validation id="depinfo.depphoneV" require="false" dataType="Custom" regexp="^[\d]{0,50}$"
												msg="<eoms:lable name='sm_msg_phoneConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_fax"/>：
									</td>
									<td>
										<input type="text" id="depinfo.depfax" name="depinfo.depfax" value="${depinfo.depfax }" maxlength="50" class="textInput" />
										<validation id="depinfo.depfaxV" require="false" dataType="Custom" regexp="^[0-9\(\)\-]{0,50}$"
												msg="<eoms:lable name='sm_msg_faxConstraint'/>！" />
									</td>
									<td class="texttd">
										E-mail：
									</td>
									<td>
										<input type="text" id="depinfo.depemail" name="depinfo.depemail" value="${depinfo.depemail }" maxlength="100" class="textInput"/>
										<validation id="depinfo.depemailV" require="false" dataType="Email"
												msg="<eoms:lable name='sm_msg_emailConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_remark"/>：
									</td>
									<td colspan="3" rowspan="2">
										<textarea name="depinfo.remark" id="textarea2" rows="5" style="width:98.7%">${depinfo.remark }</textarea>
										<validation id="depinfo.remarkV" require="false" dataType="Limit" max="160"
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
						<div id="div2" style="display: none">
							<c:set value="" var="userdata" scope="request"/>
							<c:forEach items="${userList}" var="userinfo">
							<c:set value="${userdata}&semi;${userinfo.pid}&comm;${userinfo.loginname}&comm;${userinfo.fullname}&comm;${userinfo.depname}" var="userdata"/>
							</c:forEach>
							<input type="hidden" id="initUserData" name="initUserData" value="${userdata}"/>
							<div id="userjtList" style="width:100%; height:100%"></div>
							<script language="javascript">usertable();</script>
							<input type="hidden" id="userIdXml" name="userIdXml" value=""/>
							<div class="selet_role_div">
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
										class="operate_button"
										onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="openwindow('${ctx}/depManager/cfgDepMembers.action?depid=${depinfo.pid }','',600,450);"/>
							</div>
						</div>

						<div id="div3" style="display: none">
							<c:set value="" var="roledata" scope="request"/>
							<c:forEach items="${roleList}" var="roleinfo">
							<c:set value="${roledata}&semi;${roleinfo.pid}&comm;${roleinfo.definetype}&comm;${roleinfo.rolename}" var="roledata"/>
							</c:forEach>
							<input type="hidden" id="initRoleData" name="initRoleData" value="${roledata}"/>
							<div id="rolejtList" style="width:100%; height:100%"></div>
							<script language="javascript">roletable();</script>
							<input type="hidden" id="roleIdXml" name="roleIdXml" value=""/>
							<div class="selet_role_div">
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
										class="operate_button"
										onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="openwindow('${ctx}/depManager/selectRole.action?orgtype=2&orgid=${depinfo.pid }','',600,450);"/>
							</div>
						</div>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
			</div>
		</div>
	  </form>
	</body>
</html>
