<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>添加/修改SLA规则模板信息</title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
			getPageMenu('div1_1','div1');PageMenuActive('div1_1','div1');
		}
							
		var userjt = null;
		function usertable()
		{
       		var initData = document.getElementById("initUserData").value.substring('&semi;'.length).split('&semi;');
       		var initDataArr = new Array();
       		if(document.getElementById("initUserData").value != "")
       		{
        		for(var i = 0; i < initData.length; i++)
        		{
        			initDataArr.push(initData[i].split('&comm;'));
        		}
       		}
       		else
       			initDataArr = [];
           	userjt = new JsTable('usertable', true, 'tableborder', null, null, null, null, null, null,
               [
                   new JsCell(false, 'pid', 'pid', [], 0, [], ''),
                   new JsCell(true, 'fieldid', "<eoms:lable name='sm_lb_propertiesID'/>", [], 1, [], ''),
                   new JsCell(true, 'fieldname', "<eoms:lable name='sm_lb_propertiesName'/>", [], 2, [], ''),
                   new JsCell(false, 'inputtype', "inputtype", [], 3, [], ''),
                   new JsCell(false, 'inputvaluetype', "inputvaluetype", [], 4, [], ''),
                   new JsCell(false, 'inputdatasourcetype', "inputdatasourcetype", [], 5, [], ''),
                   new JsCell(false, 'indata', "indata", [], 6, [], ''),
                   new JsCell(false, 'status', "status", [], 7, [], ''),
                   new JsCell(false, 'ordernum', "ordernum", [], 8, [], ''),
                   new JsCell(false, 'describe', "describe", [], 9, [], ''),
                   new JsCell(true, 'operate', "<eoms:lable name='com_btn_delete'/>", [['width', 80]], -1, [], '<input type="button" class="button" value="<eoms:lable name='sm_lb_delete_2'/>" onclick="userdelrow(\'{@COL0}\')">'),
            	   new JsCell(true, 'modify', "<eoms:lable name='com_btn_edit'/>", [['width', 80]], -1, [], '<input type="button" class="button" value="<eoms:lable name='sm_lb_edit_2'/>" onclick="usermodrow(\'{@COL0}\')">')
               ],
            initDataArr//初始化数组
			);
       	 	userjt.draw(document.getElementById('userjtList'));//表格显示的位置
       	 	changeRow_color("usertable");//隔行换色
		}
		
		
		function userdelrow(key){//删除某一行数据
        	userjt.deleterow(key);
        }

		function usermodrow(key){//修改某一条数据
		   window.open('${ctx}/slaRuleTpl/slaRuleTplProperty.action?slaTplPropertypid='+key,"","width=700,height=400,top=200,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
		}
	
 		var i = 0;
		function addRowData(mapData){//获取子页面添加的数据
			i++;
			var fieldid             = mapData.get('fieldid');
			var fieldname           = mapData.get('fieldname');
			var inputtype           = mapData.get('inputtype');
			var inputvaluetype      = mapData.get('inputvaluetype');
			var inputdatasourcetype = mapData.get('inputdatasourcetype');
			var indata              = mapData.get('indata');
			var status              = mapData.get('status');
			var ordernum            = mapData.get('ordernum');
			var describe            = mapData.get('describe');
				
			var addDataArr = new Array(i,fieldid,fieldname,inputtype,inputvaluetype,inputdatasourcetype,indata,status,ordernum,describe);
			userjt.addrow(addDataArr);
		}
				
		function modifyRowData(mapData){
		
		    var pid                 = mapData.get('pid');
			var fieldid             = mapData.get('fieldid');
			var fieldname           = mapData.get('fieldname');
			var inputtype           = mapData.get('inputtype');
			var inputvaluetype      = mapData.get('inputvaluetype');
			var inputdatasourcetype = mapData.get('inputdatasourcetype');
			var indata              = mapData.get('indata');
			var status              = mapData.get('status');
			var ordernum            = mapData.get('ordernum');
			var describe            = mapData.get('describe');
			
			var modDataArr = new Array(pid,fieldid,fieldname,inputtype,inputvaluetype,inputdatasourcetype,indata,status,ordernum,describe);
			userjt.modifyrow(modDataArr);
		}
		
		function cleardata(input_name)
			{
				document.getElementById(input_name).value = '';
			}
		
		function formSubmit()
		{
			document.getElementById("xmlData").value = userjt.gettablexml().toString();
			if(Validator.Validate(document.forms[0],2))
			{
				if(""=="${slaRuleTpl.pid}")
				{
					var mark = document.getElementById("slaRuleTpl.tplmark").value;
					$.get("${ctx}/slaRuleTpl/isTplMarkUnique.action",{mark:mark},function(result)
					{
						if("true"==result)
						{
							document.forms[0].submit();
						}
						else
						{
							alert("模板标识符已经存在，请选择其他模板标识符！");
							return;
						}
					});
				}
				else
				{
					document.forms[0].submit();
				}
			}
		}
	</script>
	</head>

	<body>
	 <form action="${ctx}/slaRuleTpl/addRuleTpl.action" method="post" name="addRuleTpl" >
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2">添加/修改SLA规则模板信息</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						规则模板信息维护
					</legend>
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div class="tab_show" id="div1_1"
							onclick="PageMenuActive('div1_1','div1')">
							<span>规则模板配置</span>
						</div>
						<div class="tab_hide" id="div1_2"
							onclick="PageMenuActive('div1_2','div2')">
							<span>模板属性配置</span>
						</div>
					</div>
					<div class="tabContent">
						<div id="div1">
							<table class="add_user">
								<c:if test="${slaRuleTpl.pid!=null}">
									<input type="hidden" id="slaRuleTpl.pid" name="slaRuleTpl.pid" value="${slaRuleTpl.pid}"/>
									<input type="hidden" id="slaRuleTpl.creater" name="slaRuleTpl.creater" value="${slaRuleTpl.creater}"/>
									<input type="hidden" id="slaRuleTpl.createtime" name="slaRuleTpl.createtime" value="${slaRuleTpl.createtime}"/>
								</c:if>
								<tr>
									<td class="texttd">
										<eoms:lable name="sm_lb_ruletemname"/>：<span class="must">*</span>
									</td>
									<td colspan="3">
										<input type="text" id="slaRuleTpl.ruletemplatename" name="slaRuleTpl.ruletemplatename" class="textInput" value="${slaRuleTpl.ruletemplatename}" style="width:98.4%"/>
										<validation id="slaRuleTpl.ruletemplatenameV" dataType="Custom" regexp="^.{1,30}$" msg="模板名称不能为空,且长度必须小于30"！ />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="sm_lb_ruletemmark"/>：<span class="must">*</span>
									</td>
									<td>
										<c:if test="${slaRuleTpl.pid==null}">
											<input type="text" id="slaRuleTpl.tplmark" name="slaRuleTpl.tplmark" class="textInput" value="${slaRuleTpl.tplmark }" />
											<validation id="slaRuleTpl.tplmarkV" dataType="Custom" regexp="^\w{1,20}$" msg="模板标识不能为空,长度不超过20位,只能出现字母、数字和下划线"！ />
										</c:if>
										<c:if test="${slaRuleTpl.pid!=null}">
											<input type="text" id="slaRuleTpl.tplmark" name="slaRuleTpl.tplmark" class="textInput" value="${slaRuleTpl.tplmark }" readonly="readonly"/>
										</c:if>
									</td>
									<td class="texttd">
										<eoms:lable name="sm_lb_ruletemimpclass"/>：
									</td>
									<td>
										<input type="text" id="slaRuleTpl.implclass" name="slaRuleTpl.implclass" class="textInput" value="${slaRuleTpl.implclass }" />
										<validation id="slaRuleTpl.implclassV" dataType="Limit" max="200" msg="实现类长度不超过200位！"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_systemtype"/>：<span class="must">*</span>
									</td>
									<td width="35%">
										<eoms:select name="slaRuleTpl.systemtype" dataDicTypeCode="dictype" style="select" value="${slaRuleTpl.systemtype}" ></eoms:select>
									</td>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_datasource"/>：
									</td>
									<td width="35%">
										<input type="text" id="slaRuleTpl.datasource" name="slaRuleTpl.datasource" value="${slaRuleTpl.datasource}" class="textInput" style="width:53.5%" readonly/>
										<validation id="slaRuleTpl.datasourceV" dataType="Custom" regexp="^.{0,50}$" msg="数据源长度必须小于50"！ />
										<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_choose"/>"
											class="operate_button" onmouseover="this.className='operate_button_hover'"
											onmouseout="this.className='operate_button'" onclick="openwindow('${ctx}/common/tools/ruleTplDataSourceTree.jsp?input_name=slaRuleTpl.datasource','',350,450);"/>
										<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_clear"/>"
											class="operate_button" onmouseover="this.className='operate_button_hover'"
											onmouseout="this.className='operate_button'" onclick="cleardata('slaRuleTpl.datasource');" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_status"/>：
									</td>
									<td>
										<select name="slaRuleTpl.status" id="slaRuleTpl.status" class="select">
											<c:choose>
												<c:when test="${slaRuleTpl.status==1}">
														<option value="1" selected><eoms:lable name="com_btn_inuse" /></option>
														<option value="0"><eoms:lable name="com_btn_outuse" /></option>
												</c:when>
												<c:when test="${slaRuleTpl.status==0}">
														<option value="1"><eoms:lable name="com_btn_inuse" /></option>
														<option value="0" selected><eoms:lable name="com_btn_outuse" /></option>
												</c:when>
												<c:otherwise>
														<option value="1" selected><eoms:lable name="com_btn_inuse" /></option>
														<option value="0"><eoms:lable name="com_btn_outuse" /></option>
												</c:otherwise>
											    </c:choose>
										</select>
									</td>
									<td class="texttd">
										<eoms:lable name="com_lb_orderNum"/>：<span class="must">*</span>
									</td>
									<td>
										<input type="text" id="slaRuleTpl.ordernum" name="slaRuleTpl.ordernum" value="${slaRuleTpl.ordernum}" class="textInput" />
										<validation id="slaRuleTpl.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>!" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_desc"/>：
									</td>
									<td colspan="3" rowspan="2">
										<textarea name="slaRuleTpl.describe" id="textarea2" rows="6" style="width:98.7%">${slaRuleTpl.describe}</textarea>
										<validation id="slaRuleTpl.describeV" dataType="Custom" regexp="^.{0,200}$" msg="描述信息长度必须小于100！" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<div id="div2" style="display:none">
							<c:set value="" var="slaTplPropertydata" scope="request"/>
							<c:forEach items="${slaTplPropertyList}" var="slaTplProperty">
							<c:set value="${slaTplPropertydata}&semi;${slaTplProperty.pid}&comm;${slaTplProperty.fieldid}&comm;${slaTplProperty.fieldname}&comm;${slaTplProperty.inputtype}&comm;${slaTplProperty.inputvaluetype}&comm;${slaTplProperty.inputdatasourcetype}&comm;${slaTplProperty.indata}&comm;${slaTplProperty.status}&comm;${slaTplProperty.ordernum}&comm;${slaTplProperty.describe}" var="slaTplPropertydata"/>
							</c:forEach>                                                                                                             
							<input type="hidden" id="initUserData" name="initUserData" value="${slaTplPropertydata}"/> 
							<div id="userjtList" style="width:100%; height:100%"></div>
							<script language="javascript">usertable();</script>
							<input type="hidden" id="xmlData" name="xmlData" value=""/>
							<div class="selet_role_div">
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_add"/>"
										class="operate_button"
										onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="openwindow('${ctx}/slaRuleTpl/slaRuleTplProperty.action','',700,450);"/>
							</div>
						</div>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
		</form>
	</body>
</html>
