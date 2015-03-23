<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_saveTable" /></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
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
       		{
       			initDataArr = [];
       		}
           	userjt = new JsTable('usertable', true, 'tableborder', null, null, null, null, null, null,
               [
                   new JsCell(false, 'pid', 'pid', [], 0, [], ''),
                   new JsCell(true, 'field', "<eoms:lable name='sm_lb_fieldEnname' />", [['width', 150]], 1, [], ''),
                   new JsCell(true, 'fieldname', "<eoms:lable name='sm_lb_fieldCnname' />", [['width', 150]], 2, [], ''),
                   new JsCell(false, 'fieldtype', "fieldtype", [], 3, [], ''),
                   new JsCell(false, 'length', "length", [], 4, [], ''),
                   new JsCell(false, 'precision', "precision", [], 5, [], ''),
                   new JsCell(false, 'isrequired', "isrequired", [], 6, [], ''),
                   new JsCell(false, 'defaultvalue', "defaultvalue", [], 7, [], ''),
                   new JsCell(false, 'dtcode', "dtcode", [], 8, [], ''),
                   new JsCell(true, 'remark', "<eoms:lable name='com_lb_desc' />", [], 9, [], ''),
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
        	changeRow_color("usertable");
        }

		function usermodrow(key){//修改某一条数据
		   openwindow('${ctx}/tableManager/loadField.action?pid='+key,'',700,450);
		}
	
 		var i = 0;
		function addRowData(mapData){//获取子页面添加的数据
			i++;
			var field             = mapData.get('field');
			var fieldname         = mapData.get('fieldname');
			var fieldtype         = mapData.get('fieldtype');
			var length            = mapData.get('length');
			var precision         = mapData.get('precision');
			var isrequired        = mapData.get('isrequired');
			var defaultvalue      = mapData.get('defaultvalue');
			var dtcode            = mapData.get('dtcode');
			var remark            = mapData.get('remark');
			
			var addDataArr = new Array(i,field,fieldname,fieldtype,length,precision,isrequired,defaultvalue,dtcode,remark);
			userjt.addrow(addDataArr);
			changeRow_color("usertable");
		}
		
		function modifyRowData(mapData){
		
		    var pid               = mapData.get('pid');
			var field             = mapData.get('field');
			var fieldname         = mapData.get('fieldname');
			var fieldtype         = mapData.get('fieldtype');
			var length            = mapData.get('length');
			var precision         = mapData.get('precision');
			var isrequired        = mapData.get('isrequired');
			var defaultvalue      = mapData.get('defaultvalue');
			var dtcode            = mapData.get('dtcode');
			var remark            = mapData.get('remark');
			
			var modDataArr = new Array(pid,field,fieldname,fieldtype,length,precision,isrequired,defaultvalue,dtcode,remark);
			userjt.modifyrow(modDataArr);
		}
		
		
		function formSubmit()
		{
			var tableid = '${tbinfo.pid}';
			if(Validator.Validate(document.forms[0],2))
			{
				if(tableid=='')
				{
					var src = document.getElementById('tbinfo.enname');
					$.get("${ctx}/tableManager/checkUnique.action",{tbName:src.value},function(result)
					{
						if(result=='false')
						{
							alert('<eoms:lable name="sm_msg_tbEnnameUniqueRe"/>！');
							src.focus();
						}
						else
						{
							document.getElementById("xmlData").value = userjt.gettablexml().toString();
							document.forms[0].submit();
						}
					});
				}
				else
				{
					document.getElementById("xmlData").value = userjt.gettablexml().toString();
					document.forms[0].submit();
				}
			}
		}
	</script>
	</head>

	<body>
	  <form action="${ctx }/tableManager/saveTable.action" method="post">
	  	<input type="hidden" name="tbinfo.pid" value="${tbinfo.pid}"/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="sm_t_saveTable" /></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						<eoms:lable name="sm_lb_dbTableInfo"/>
					</legend>
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div class="tab_show" id="div1_1"
							onclick="PageMenuActive('div1_1','div1')">
							<span><eoms:lable name="com_lb_basicInfo"/></span>
						</div>
						<div class="tab_hide" id="div1_2"
							onclick="PageMenuActive('div1_2','div2')">
							<span><eoms:lable name="sm_lb_tbFieldInfo"/></span>
						</div>
					</div>
					<div class="tabContent">
						<div id="div1">
							<table class="add_user">
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_tbEnname" />：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<c:if test="${tbinfo.pid==null}">
											<input type="text" name="tbinfo.enname" id="tbinfo.enname" class="textInput" value="${tbinfo.enname }"/>
										</c:if>
										<c:if test="${tbinfo.pid!=null}">
											<input type="text" name="tbinfo.enname" id="tbinfo.enname" class="textInput" value="${tbinfo.enname }" readonly="readonly"/>
										</c:if>
										<validation id="tbinfo.ennameV" dataType="Custom" regexp="^[\w]{1,50}$" msg="<eoms:lable name="sm_msg_tbEnnameConstraint" />！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_tbCnname" />：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<input type="text" id="tbinfo.cnname" name="tbinfo.cnname" value="${tbinfo.cnname }" class="textInput" />
										<validation id="tbinfo.cnnameV" dataType="Limit" min="1" max="30" msg="<eoms:lable name="sm_msg_tbCnnameConstraint" />！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_tbType" />：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<eoms:select name="tbinfo.tabletype" style="select" dataDicTypeCode="tabletype" value="${tbinfo.tabletype}" />
										<validation id="tbinfo.tabletypeV" dataType="Require" msg="<eoms:lable name="sm_msg_tbTypeConstraint" />！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name="com_lb_sequenceNumber" />：
									</td>
									<td style="width:35%">
										<input type="text" id="tbinfo.seqnum" name="tbinfo.seqnum" class="textInput" value="${tbinfo.seqnum }"/>
										<validation id="tbinfo.seqnumV" require="false" dataType="Custom" regexp="^[\d]{0,5}$" msg="<eoms:lable name="sm_msg_tbSeqNumConstraint" />！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="com_lb_orderNum" />：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<input type="text" id="tbinfo.ordernum" name="tbinfo.ordernum" class="textInput" value="${tbinfo.ordernum }"/>
										<validation id="tbinfo.ordernumV" dataType="Custom" regexp="^[\d]{1,5}$" msg="<eoms:lable name="sm_msg_tbOrderNumConstraint" />！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="com_lb_remark"/>：
									</td>
									<td colspan="3">
										<textarea id="tbinfo.remark" name="tbinfo.remark" rows="5" style="width:98.7%">${tbinfo.remark }</textarea>
										<validation id="tbinfo.remarkV" require="false" dataType="Limit" max="150"
												msg="<eoms:lable name="sm_msg_tbRemarkConstraint"/>！" />
									</td>
								</tr>
							</table>
						</div>
						<div id="div2" style="display:none">
							<c:set value="" var="tbFielddata" scope="request"/>
							<c:forEach items="${fieldList}" var="field">
							<c:set value="${tbFielddata}&semi;${field.pid}&comm;${field.field}&comm;${field.fieldname}&comm;${field.fieldtype}&comm;${field.length}&comm;${field.precision}&comm;${field.isrequired}&comm;${field.defaultvalue}&comm;${field.dtcode }&comm;${field.remark }" var="tbFielddata"/>
							</c:forEach>                                                                                                             
							<input type="hidden" id="initUserData" name="initUserData" value="${tbFielddata}"/> 
							<div id="userjtList" style="width:100%; height:100%"></div>
							<script language="javascript">usertable();</script>
							<input type="hidden" name="xmlData" value=""/>
							<div class="selet_role_div">
								<input type="button" name="button5" id="button5" value="<eoms:lable name="com_btn_add"/>"
										class="operate_button"
										onmouseover="this.className='operate_button_hover'"
										onmouseout="this.className='operate_button'" 
										onclick="openwindow('${ctx}/tableManager/loadField.action','',700,450);"/>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save" />" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="formSubmit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
