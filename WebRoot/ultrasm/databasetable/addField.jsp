<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_addTbField"/></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/Map.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		function trim(str){//删除左右两端的空格
　　			return str.replace(/(^\s*)|(\s*$)/g, "");
　　		}
		var from_page = '${param.from}';//该变量用来判断请求是从哪个页面点击进入的，从而决定改页面数据的提交方式
		function formSubmit()
		{
			if(from_page!='other')
			{
				var fieldid = '${field.pid}';
				if(Validator.Validate(document.forms[0],2))
				{//是从库表信息进入
					var field = trim(document.getElementById("field.field").value);
					var fieldname = trim(document.getElementById("field.fieldname").value);
					//var fieldtype = trim(document.getElementById("field.fieldtype").value);
					var fieldtype = trim(document.getElementsByName('field.fieldtype')[0].value);
					var isrequired = trim(document.getElementById("field.isrequired").value);
					var length = trim(document.getElementById("field.length").value);
					var precision = trim(document.getElementById('field.precision').value);
					var defaultvalue = trim(document.getElementById("field.defaultvalue").value);
					var dtcode = trim(document.getElementById("field.dtcode").value);
					var remark = trim(document.getElementById("field.remark").value);
					map = new Map();
					map.put('field',field);
					map.put('fieldname',fieldname);
					map.put('fieldtype',fieldtype);
					map.put('isrequired',isrequired);
					map.put('length',length);
					map.put('precision',precision);
					map.put('defaultvalue',defaultvalue);
					map.put('dtcode',dtcode);
					map.put('remark',remark);
					var pid = document.getElementById("field.pid").value;
					if(pid!=""){
						map.put('pid',pid);
						window.opener.modifyRowData(map);
					}else{
						window.opener.addRowData(map);
					}
					window.close();
				}
			}
			else
			{//是从库表字段信息进入
				if(Validator.Validate(document.forms[0],2))
				{
					document.forms[0].submit();
				}
			}
			
		}
	</script>
	</head>

	<body>
	  <form action="${ctx}/tableManager/saveField.action" method="post">
	  	<input type="hidden" name="field.pid" id="field.pid" value="${field.pid}"/>
	  	<input type="hidden" name="field.enname" id="field.enname" value="${field.enname }"/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="sm_t_addTbField"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						<eoms:lable name="sm_lb_tbFieldManage"/>
					</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<div id="div1">
							<table class="add_user">
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fieldEnname"/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<c:if test="${field.pid==null}">
											<input type="text" name="field.field" id="field.field" class="textInput" value="${field.field }"/>
										</c:if>
										<c:if test="${field.pid!=null}">
											<input type="text" name="field.field" id="field.field" class="textInput" value="${field.field }" readonly="readonly"/>
										</c:if>
										<validation id="field.fieldV" dataType="Custom" regexp="^[\w]{1,50}$" msg="<eoms:lable name="sm_msg_fieldEnnameConstraint"/>！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fieldCnname"/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<input type="text" onblur="test();" name="field.fieldname" id="field.fieldname" value="${field.fieldname }" class="textInput" />
										<validation id="field.fieldnameV" dataType="Custom" regexp="^.{1,30}$" msg="<eoms:lable name='sm_msg_fieldCnnameConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fieldDataType"/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<eoms:select name="field.fieldtype" style="select" dataDicTypeCode="datatype" value="${field.fieldtype}" />
										<validation id="field.fieldtypeV" dataType="Require" msg="<eoms:lable name='sm_msg_fieldDataTypeConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_isRequiredField"/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<eoms:select name="field.isrequired" style="select" dataDicTypeCode="isdefault" value="${field.isrequired}"/>
										<validation id="field.isrequiredV" dataType="Require" msg="<eoms:lable name='sm_msg_fieldIsRequiredConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fieldLength"/>：
									</td>
									<td style="width:35%">
										<input type="text" name="field.length" id="field.length" class="textInput" value="${field.length }"/>
										<validation id="field.lengthV" require="false" dataType="Integer" msg="<eoms:lable name='sm_msg_fieldLenConstraint'/>！" />
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fieldPrecision"/>：
									</td>
									<td style="width:35%">
										<input type="text" name="field.precision" id="field.precision" class="textInput" value="${field.precision }"/>
										<validation id="field.precisionV" require="false" dataType="Integer" msg="<eoms:lable name='sm_msg_fieldPreciConstraint'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fieldDicType"/>：
									</td>
									<td style="width:35%">
										<input type="text" name="field.dtcode" id="field.dtcode" class="textInput" value="${field.dtcode }" 
										readonly="readonly" 
										onclick="showModalDialog('${ctx}/tableManager/loadDic.action',window,'help:no;scroll:no;status:no;dialogWidth:700px;dialogHeight:400px');"/>
									</td>
									<td class="texttd" style="width:15%">
										<eoms:lable name="sm_lb_fieldDefaultValue"/>：
									</td>
									<td style="width:35%">
										<input type="text" name="field.defaultvalue" id="field.defaultvalue" class="textInput" value="${field.defaultvalue }"/>
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top">
										<eoms:lable name="com_lb_remark"/>：
									</td>
									<td colspan="3">
										<textarea name="field.remark" id="field.remark" rows="5" style="width:98.7%">${field.remark }</textarea>
										<validation id="field.remarkV" require="false" dataType="Limit" max="150"
												msg="<eoms:lable name='sm_msg_fieldRemarkConstraint'/>！" />
									</td>
								</tr>
							</table>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" 
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
