<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
    
	<script src="${ctx}/workflow/wfCustom/js/JsProp.js"></script>
	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
	 <title>
		     <eoms:lable name="wf_interface_title" />
	</title>
	
    <script type="text/javascript">
	window.onload = function(){
    	//init();
    	setCenter(0,55);
				openItem();
	}
	window.onresize = function()
	{
		setCenter(0,55);
	}

 /**
  * 模型属性列表选择
  */
  function openItem(){
  var propcode = '<%=request.getParameter("propcode")%>';
  	var prop = opener.propMap.get(propcode);
  	if(propcode != '' && prop != null){
	  	document.getElementById("propcode").value = prop.propCode; 
	 	document.getElementById("propname").value = prop.propName;
	 	document.getElementById("proptype").value = prop.propType;
	 	document.getElementById("propdict").value = prop.propDict;
	 	document.getElementById("propisnull").value = prop.propIsNull;
	 	document.getElementById("propdefaulvalue").value = prop.propDefaulValue;
	 	document.getElementById("proporderby").value = prop.propOrderBy;
	 }
  }

 //关闭属性页面
 function closePropDiv(){
 	document.getElementById('propDiv').style.display = 'none';
 }
//添加属性
function addProp(){
	
	var pCode = document.getElementById('propcode').value;
	var proporderby = document.getElementById('proporderby').value;
	if(!check(pCode, "<eoms:lable name='wf_model_propcodeConstraint'/>！") || !checkReg(/^\d+$/, proporderby, "<eoms:lable name='wf_sort_orderby_integer'/>！")) {
		return ;
	}
	var propcode = $("#propcode")[0].value;
	if(propcode == ''){
		alert('<eoms:lable name="wf_model_prop_msg"/>');
		return;
	}
	 
	var propname = $("#propname")[0].value;
	if(propname == ''){
		alert('<eoms:lable name="wf_model_prop_msg1"/>');
		return;
	}
	var code = $("#propcode")[0].value;
	var prop = opener.propMap.get($("#propcode")[0].value);
	if(prop == null){
		prop = new JsProp();
		prop.sign = 'add';
 	}else{
 		prop.sign = 'update';
 	}
 	prop.propCode = $("#propcode")[0].value;
 	prop.propName = $("#propname")[0].value;
 	prop.propType = $("#proptype")[0].value;
 	prop.propDict = $("#propdict")[0].value;
 	prop.propIsNull = $("#propisnull")[0].value;
 	prop.propDefaulValue = $("#propdefaulvalue")[0].value;
 	prop.propOrderBy = $("#proporderby")[0].value;
 	
 	if(opener.propMap.get(prop.propCode) == null){
		prop.sign = 'add';
 	}else{
 		prop.sign = 'update';
 	}
 	
 	opener.addMap(prop.sign,prop.propId,prop.propCode,prop.propName,prop.propType,prop.propDict,prop.propIsNull,prop.propDefaulValue,prop.propOrderBy);
 	//opener.propMap.put(prop.propCode,prop);
	//window.opener.jsTableView();
 	//document.getElementById('propDiv').style.display = 'none';
 	window.close();
}

    </script>
</head>
<body>

<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="add_scroll_div_x" id="center">
					<fieldset class="fieldset_style">
						<legend><eoms:lable name="com_lb_basicInfo" /></legend>
						
					   <div id="propDiv" class="tabContent_top">
							<form>
							<table class="add_user">
								<tr>
									<td class="texttd" width="200">
										<eoms:lable name="wf_model_propcode" />：
									</td>
									<td>
										<input type="text" id="propcode" class="textInput" maxlength="100"/>
									</td>
									
									<td class="texttd">
										<eoms:lable name="wf_model_propname" />：
									</td>
									<td>
										<input type="text" id="propname" class="textInput" maxlength="100"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<eoms:lable name="wf_model_proptype" />：
									</td>
									<td>
										<select  id="proptype" class="select">
											<option value="TEXT"><eoms:lable name="wf_model_prop_txt"/></option>
											<option value="SELECT"><eoms:lable name="wf_model_prop_select"/></option>
											<option value="RADIO"><eoms:lable name="wf_model_prop_radio"/></option>
										</select>
									</td>
									<td class="texttd">
										<eoms:lable name="wf_model_propdict" />：
									</td>
									<td>
										<input type="text" id="propdict" class="textInput" maxlength="100"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<span class="texttd"><eoms:lable name="wf_model_propisnull" />：</span>
									</td>
									<td>
										<select  id="propisnull" class="select">
											<option value="1"><eoms:lable name="wf_lb_design_yes"/></option>
											<option value="0"><eoms:lable name="wf_lb_design_no"/></option>
										</select>
									</td>
									<td class="texttd">
										<eoms:lable name="wf_model_propdefaulvalue" />：
									</td>
									<td>
										<input type="text" id="propdefaulvalue" class="textInput" maxlength="100"/>
									</td>
								</tr>
								<tr>
									<td class="texttd">
										<span class="texttd"><eoms:lable name="wf_model_proporderby" />：</span>
									</td>
									<td>
										<input type="text" id="proporderby" class="textInput" maxlength="5"/>
									</td>
								</tr>
							</table>
							</form>
						</div>
						
						
					</fieldset>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onclick="addProp();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>