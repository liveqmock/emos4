<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_resProperty"/></title>
		<script type="text/javascript" src="${ctx}/common/javascript/Map.js"></script>
		<script language="javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		
		function formSubmit(){
			if(Validator.Validate(document.forms[0],2)){//字段验证
				var fieldname = trim(document.getElementById("resProperty.fieldname").value);
				var fielddisplayvalue = trim(document.getElementById("resProperty.fielddisplayvalue").value);
				var intype = trim(document.getElementById("resProperty.intype").value);
				var ordernum = trim(document.getElementById("resProperty.ordernum").value);
				var invaluetype = trim(document.getElementById("resProperty.invaluetype").value);
				var indatasourtype = trim(document.getElementById("resProperty.indatasourtype").value);
				var indata = trim(document.getElementById("resProperty.indata").value);
				var remark = trim(document.getElementById("resProperty.remark").value);
				
				map = new Map();
				map.put('fieldname',fieldname);
				map.put('fielddisplayvalue',fielddisplayvalue);
				map.put('intype',intype);
				map.put('ordernum',ordernum);
				map.put('invaluetype',invaluetype);
				map.put('indatasourtype',indatasourtype);
				map.put('indata',indata);
				map.put('remark',remark);
				
				var pid = document.getElementById("resProperty.pid").value;
				if(pid!=""){
					map.put('pid',pid);
					window.opener.modifyRowData(map);
				}else{
					window.opener.addRowData(map);
				}
				window.close();
			}
		}
		
		function intypeCon(){//输入类型 字段值事件
			var intype = document.getElementById("resProperty.intype").value;
			if(intype=="1")	{//当选择文本域, 输入数据类型 输入数据值 不可写
				document.getElementById("resProperty.indata").value = "";
				document.getElementById("resProperty.indatasourtype").value = "";
				document.getElementById("resProperty.indata").disabled = true;
			 	document.getElementById("resProperty.indatasourtype").disabled = true;
			}else{
				document.getElementById("resProperty.indata").disabled = false;
				document.getElementById("resProperty.indatasourtype").disabled = false;
			}
		}
		
		function indatasourceCon(){//输入数据类型 字段值事件
			var indatasourcetype = document.getElementById("resProperty.indatasourtype").value;
			if(indatasourcetype!=""){
				document.getElementById("resProperty.intype").value = '2';
				
				if(indatasourcetype=="1" || indatasourcetype=="4" || indatasourcetype=="5"){
				document.getElementById("resProperty.indata").value = "";
				document.getElementById("resProperty.indata").disabled = true;
				}else{
					document.getElementById("resProperty.indata").disabled = false;
				}
			}
		}
		
		function trim(str){//删除左右两端的空格
　　			return str.replace(/(^\s*)|(\s*$)/g, "");
　　		}
	</script>
	</head>

	<body>
	  <form method="post" name="addResourceform" >
		<c:if test="${resProperty.pid!=null}">
			<input type="hidden" id="resProperty.pid" name="resProperty.pid" value="${resProperty.pid}"/>
		</c:if>
		<c:if test="${resProperty.pid==null}">
			<input type="hidden" id="resProperty.pid" name="resProperty.pid" value=""/>
		</c:if>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"><span class="title_icon2"><eoms:lable name="sm_t_resProperty"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						<eoms:lable name="com_lb_basicInfo"/>
					</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<table class="add_user">
							<tr>
								<td class="texttd" width="20%">
									<eoms:lable name="sm_lb_fieldID"/>： <span class="must">*</span>
								</td>
								<td width="30%">
									<input type="text" id="resProperty.fieldname" name="resProperty.fieldname" class="textInput" value="${resProperty.fieldname}" />
									<validation id="resProperty.fieldnameV" dataType="Custom" regexp="^.{1,20}$" msg="<eoms:lable name='sm_msg_resPropertyFieldname' />" />
								</td>
								<td class="texttd" width="20%">
									<eoms:lable name="sm_lb_fieldName"/>：<span class="must">*</span>
								</td>
								<td width="30%">
									<input type="text" id="resProperty.fielddisplayvalue" name="resProperty.fielddisplayvalue" value="${resProperty.fielddisplayvalue}" class="textInput" />
									<validation id="resProperty.fielddisplayvalueV" dataType="Custom" regexp="^.{1,20}$" msg="<eoms:lable name='sm_msg_resPropertyFielddisplayvalue' />" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_inType"/>：
								</td>
						        <td>
						        	<eoms:select name="resProperty.intype" dataDicTypeCode="inputtype" style="select" onChangeFun="intypeCon()" value="${resProperty.intype}" ></eoms:select>
								</td>
								<td class="texttd">
									<eoms:lable name="sm_lb_inValueType"/>：
								</td>
								<td>
									<eoms:select name="resProperty.invaluetype" dataDicTypeCode="invaluetype" style="select" value="${resProperty.invaluetype}" ></eoms:select>
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="sm_lb_inValueDataType"/>：
								</td>
								<td>
								    <eoms:select name="resProperty.indatasourtype" dataDicTypeCode="indatasourtype" style="select" onChangeFun="indatasourceCon()" value="${resProperty.indatasourtype}" ></eoms:select>
								</td>
								<td class="texttd">
									<eoms:lable name="sm_lb_inDataValue"/>：
								</td>
								<td>
									<input type="text" id="resProperty.indata" name="resProperty.indata" value="${resProperty.indata}" class="textInput" />
									<validation id="resProperty.indataV" dataType="Custom" regexp="^.{0,500}$" msg="<eoms:lable name="sm_msg_resPropertyIndata" />" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="com_lb_orderNum"/>：<span class="must">*</span>
								</td>
								<td>
									<input type="text" id="resProperty.ordernum" name="resProperty.ordernum" value="${resProperty.ordernum}" class="textInput" />
									<validation id="resProperty.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="com_lb_remark"/>：
								</td>
								<td colspan="3" rowspan="2">
									<textarea name="resProperty.remark" id="textarea2" rows="5" style="width:98.7%">${resProperty.remark}</textarea>
									<validation id="resProperty.remarkV" dataType="Custom" regexp="^.{0,200}$" msg="<eoms:lable name='sm_msg_reamrk'/>" />
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
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" 
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
