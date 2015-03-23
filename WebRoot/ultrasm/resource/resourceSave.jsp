<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_resource"/></title>
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
                   new JsCell(true, 'fieldname', "<eoms:lable name='sm_lb_fieldID'/>", [], 1, [], ''),
                   new JsCell(true, 'fielddisplayvalue', "<eoms:lable name='sm_lb_fieldName'/>", [], 2, [], ''),
                   new JsCell(false, 'intype', "intype", [], 3, [], ''),
                   new JsCell(false, 'ordernum', "ordernum", [], 4, [], ''),
                   new JsCell(false, 'invaluetype', "invaluetype", [], 5, [], ''),
                   new JsCell(false, 'indatasourtype', "indatasourtype", [], 6, [], ''),
                   new JsCell(false, 'indata', "indata", [], 7, [], ''),
                   new JsCell(false, 'remark', "remark", [], 8, [], ''),
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
		   window.open('${ctx}/resManager/getResPropertyInfo.action?respropertyid='+key,"","width=700,height=400,top=200,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
		}
	
 		var i = 0;
		function addRowData(mapData){//获取子页面添加的数据
			i++;
			var fieldname         = mapData.get('fieldname');
			var fielddisplayvalue = mapData.get('fielddisplayvalue');
			var intype            = mapData.get('intype');
			var ordernum          = mapData.get('ordernum');
			var invaluetype       = mapData.get('invaluetype');
			var indatasourtype    = mapData.get('indatasourtype');
			var indata            = mapData.get('indata');
			var remark            = mapData.get('remark');
				
			var addDataArr = new Array(i,fieldname,fielddisplayvalue,intype,ordernum,invaluetype,indatasourtype,indata,remark);
			userjt.addrow(addDataArr);
		}
		
		function modifyRowData(mapData){
		
		    var pid               = mapData.get('pid');
			var fieldname         = mapData.get('fieldname');
			var fielddisplayvalue = mapData.get('fielddisplayvalue');
			var intype            = mapData.get('intype');
			var ordernum          = mapData.get('ordernum');
			var invaluetype       = mapData.get('invaluetype');
			var indatasourtype    = mapData.get('indatasourtype');
			var indata            = mapData.get('indata');
			var remark            = mapData.get('remark');
			
			var modDataArr = new Array(pid,fieldname,fielddisplayvalue,intype,ordernum,invaluetype,indatasourtype,indata,remark);
			userjt.modifyrow(modDataArr);
		}
		
		function formSubmit()
		{
			document.getElementById("xmlData").value = userjt.gettablexml().toString();
			if(Validator.Validate(document.forms[0],2))
				document.addResourceform.submit();
		}
	</script>
	</head>

	<body>
	 <form action="${ctx}/resManager/addResource.action" method="post" name="addResourceform" >
		<c:if test="${resource.pid!=null}">
			<input type="hidden" id="resource.pid" name="resource.pid" value="${resource.pid}"/>
			<input type="hidden" id="resource.creater" name="resource.creater" value="${resource.creater}"/>
			<input type="hidden" id="resource.createtime" name="resource.createtime" value="${resource.createtime}"/>
		</c:if>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2"><eoms:lable name="sm_t_resource"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						<eoms:lable name="sm_lb_resourceInfo"/>
					</legend>
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div class="tab_show" id="div1_1"
							onclick="PageMenuActive('div1_1','div1')">
							<span><eoms:lable name="com_lb_basicInfo"/></span>
						</div>
						<div class="tab_hide" id="div1_2"
							onclick="PageMenuActive('div1_2','div2')">
							<span><eoms:lable name="sm_lb_cfgProperties"/></span>
						</div>
					</div>
					<div class="tabContent">
						<div id="div1">
							<table class="add_user">
								<tr>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_resourceName"/>：<span class="must">*</span>
									</td>
									<td width="35%">
										<input type="text" id="resource.resname" name="resource.resname" class="textInput" value="${resource.resname}" />
										<validation id="resource.resnameV" dataType="Custom" regexp="^.{1,30}$" msg="<eoms:lable name='sm_msg_resourceNameConstraint'/>" />
									</td>
									<td class="texttd" width="15%">
										<eoms:lable name="sm_lb_resourceSystemType"/>：
									</td>
									<td width="35%">
										<input type="text" id="resource.systemtype" name="resource.systemtype" value="${resource.systemtype}" class="textInput" />
										<validation id="resource.systemtypeV" dataType="Custom" regexp="^.{0,10}$" msg="<eoms:lable name='sm_msg_resourceSystemType'/>" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="sm_lb_resourceDefineType"/>：
									</td>
									<td>
										<input type="text" id="resource.definetype" name="resource.definetype" value="${resource.definetype}" class="textInput" />
										<validation id="resource.definetypeV" dataType="Custom" regexp="^.{0,20}$" msg="<eoms:lable name='sm_msg_resourceDefineType'/>" />
									</td>
									<td class="texttd">
										<eoms:lable name="sm_lb_resourceParent"/>：
									</td>
									<td>
										<input type="text" id="resource.parentid" name="resource.parentid" value="${resource.parentid}" class="textInput" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_status"/>：
									</td>
									<td>
										<select name="resource.status" id="resource.status" class="select">
											<c:choose>
												<c:when test="${resource.status==1}">
														<option value="1" selected><eoms:lable name="com_btn_inuse" /></option>
														<option value="0"><eoms:lable name="com_btn_outuse" /></option>
												</c:when>
												<c:when test="${resource.status==0}">
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
										<input type="text" id="resource.ordernum" name="resource.ordernum" value="${resource.ordernum}" class="textInput" />
										<validation id="resource.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>!" />
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="com_lb_remark"/>：
									</td>
									<td colspan="3" rowspan="2">
										<textarea name="resource.remark" id="textarea2" rows="5" style="width:98.7%">${resource.remark}</textarea>
										<validation id="resource.remarkV" dataType="Custom" regexp="^.{0,200}$" msg="<eoms:lable name='sm_msg_reamrk'/>" />
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
							<c:set value="" var="resPropertydata" scope="request"/>
							<c:forEach items="${resPropertyList}" var="resProperty">
							<c:set value="${resPropertydata}&semi;${resProperty.pid}&comm;${resProperty.fieldname}&comm;${resProperty.fielddisplayvalue}&comm;${resProperty.intype}&comm;${resProperty.ordernum}&comm;${resProperty.invaluetype}&comm;${resProperty.indatasourtype}&comm;${resProperty.indata}&comm;${resProperty.remark}" var="resPropertydata"/>
							</c:forEach>                                                                                                             
							<input type="hidden" id="initUserData" name="initUserData" value="${resPropertydata}"/> 
							<div id="userjtList" style="width:100%; height:100%"></div>
							<script language="javascript">usertable();</script>
							<input type="hidden" id="xmlData" name="xmlData" value=""/>
							<div class="selet_role_div">
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_add"/>"
										class="operate_button"
										onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="openwindow('${ctx}/resManager/getResPropertyInfo.action','',700,450);"/>
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
