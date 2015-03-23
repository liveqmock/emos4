<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	
	<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
	<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
    
	<script src="${ctx}/common/javascript/util.js"></script>
	
	<script src="${ctx}/workflow/wfCustom/js/JsFunction.js"></script>
	<script src="${ctx}/workflow/wfCustom/js/preFunction.js"></script>
	<script src="${ctx}/workflow/wfCustom/js/postFunction.js"></script>
	<script src="${ctx}/workflow/wfCustom/js/JsProp.js"></script>
	<script src="${ctx}/workflow/wfCustom/js/modelProp.js"></script> 
	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
	<title>
	 <s:if test='%{modelCode != null}'>
			<eoms:lable name="wf_model_edit" />
	</s:if>
	<s:else>
	       <eoms:lable name="wf_model_add" />
	</s:else>
	</title>
    <script type="text/javascript">
	window.onload = function(){
    	setCenter(0,61);
    	getPageMenu('div1_1','div1');
    	PageMenuActive('div1_1','div1');
    	init();
	}

		window.onresize = function()
			{
				setCenter(0,61);
			}
    </script>
    
</head>
<body>
	<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2" id='title'>
							<s:if test='%{modelCode != null}'>
								<eoms:lable name="wf_model_edit" />
							</s:if>
							<s:else>
								<eoms:lable name="wf_model_add" />
							</s:else>
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

						
						
		<div class="scroll_div" id="center">
		<div class="blank_tr"></div>
					<fieldset class="fieldset_style">
						<s:form action="saveModel.action" theme="simple" id="modelForm" name="" method="post" target="_self">
					    	<s:hidden name="wfModel.id"></s:hidden>
					    	<s:hidden name="wfModel.photoPath"></s:hidden>
					    	<s:hidden name="wfModel.preFunction" id="preFunction"></s:hidden>
					    	<s:hidden name="wfModel.postFunction" id="postFunction"></s:hidden>
					    	
					    	<input name="propString" id="propString" type="hidden" />
							
						
							<legend><eoms:lable name="com_lb_basicInfo" /></legend>
							
							<div class="blank_tr"></div>
							
							<div class="tabContent_top">
								<table class="add_user">
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_model_code" />：
										</td>
										<td>
											<s:if test='%{modelCode != null}'>
												<s:textfield name="wfModel.code" id="wfModel.code"  readonly="true" cssClass="textInput"></s:textfield>
											</s:if>
											<s:else>
												<s:textfield name="wfModel.code" id="wfModel.code" cssClass="textInput" maxlength="100"></s:textfield>
											</s:else>
											<validation id="wfModel.codeV" require="true" dataType="Require" msg="<eoms:lable name='wf_model_code_require'/>！" />
										</td>
										<td rowspan="5" valign="top" class="texttd">
											<div class="user_photo">
												<span class="top"><eoms:lable name="wf_model_pic" /></span>
												<span class="middle"></span>
												<span class="bottom">
													<input type="button" name="button" id="button" value="<eoms:lable name='sm_btn_upload'/>"
														class="operate_button"
														onmouseover="this.className='operate_button_hover'"
														onmouseout="this.className='operate_button'"
														onclick="" />
													</span>
											</div>
										</td>
									</tr>
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_model_name" />：
										</td>
										<td>
											<input type="text" name="wfModel.name" class="textInput" value="${wfModel.name}" maxlength="100"/>
											<validation id="wfModel.nameV" require="true" dataType="Require" msg="<eoms:lable name='wf_model_name_require'/>！" />
										</td>
									</tr>
									<tr>
										<td class="texttd">
											<span class="texttd"><eoms:lable name="wf_model_type" />：</span>
										</td>
										<td>
											<select name="wfModel.type" id="wfModel.type" class="select" style="width:98%">
												<option value="EOMS">
													<eoms:lable name="wf_model_eoms"></eoms:lable>
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_model_isPublish" />：
										</td>
										<td>
											<select name="wfModel.isPublish" id="wfModel.isPublish" class="select" style="width:98%">
												<option value="1"><eoms:lable name="wf_lb_design_yes"/></option>
												<option value="0"><eoms:lable name="wf_lb_design_no"/></option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="texttd"><eoms:lable name="com_lb_remark" />：</td>
										<td>
											<s:textarea cssClass="textInput" cssStyle="width:97%" name="wfModel.remark" id="wfModel.remark" rows="3">
												
											</s:textarea>
										</td>
									</tr>
								</table>
							</div>
  					  </s:form>
					</fieldset>
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style">
						<legend><eoms:lable name="com_lb_advancedInfo" /></legend>
						<div class="blank_tr"></div>
						<div class="tab_bg">
							<div class="tab_show" id="div1_1" onclick="PageMenuActive('div1_1','div1')">
								<span> <eoms:lable name="wf_model_proplist" /></span>
							</div>
							<div class="tab_hide" id="div1_2" onclick="PageMenuActive('div1_2','div2')">
								<span><eoms:lable name="wf_model_prefunction" /></span>
							</div>
							<div class="tab_hide" id="div1_3" onclick="PageMenuActive('div1_3','div3')">
								<span><eoms:lable name="wf_model_postfunction" /></span>
							</div>
					</div>
						
					<div class="tabContent">
					<!-- 流程属性列表 -->
						<div id="div1" style="display:none;">
							<table class="add_user">
								<tr>
						            <td>
						            	<input type="button" value="<eoms:lable name="com_btn_add"/>" class="add_button" onclick="toAddOrEditProp('');" />						
									</td>
						       </tr>
						       <tr><td>
						           <div id='propList'></div>
						           </td>
						       </tr>
						    </table>
						</div>
							
						<!-- 前置函数 -->
						<div id="div2" style="display: none;">
							<table class="add_user">
								<tr>
									<td>
										<select id='prefunctionSlt' style="width:150px"></select>
										<input type="button" value="<eoms:lable name="com_btn_add"/>"  class="add_button" onclick="addPreFunction();" />
									</td>
								</tr>
								<tr>
									<td>
										<div id='prefunctiontable'></div>
									</td>
								</tr>
							</table>
						</div>


					<!-- 后置函数 -->
						<div id="div3" style="display:none;">
						<table class="add_user">
							<tr>
								<td>
									<select id="postfunctionSlt" style="width:150px"></select>
									<input type="button" value="<eoms:lable name="com_btn_add"/>" class="add_button" onclick="addPostFunction();" />
								</td>
							</tr>
							<tr>
								<td>
									<div id='postfunctiontable'></div>
								</td>
							</tr>
						</table>
						</div>
					</div>
					
					</fieldset>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onclick="modelFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
				</div>
				
				<script type="text/javascript">
					function initPropList(){
 						propMap = new Map();
						<s:iterator value="%{propList}" status="WfModelProperties">
							var id = "<s:property value='id' />";
							var code = "<s:property value='code' />";
							var name = "<s:property value='name' />";
							var type = "<s:property value='type' />";
							var dict = "<s:property value='dict' />";
							var isNull = "<s:property value='isNull' />";
							var defaulvalue = "<s:property value='defaulvalue' />";
							var orderby = "<s:property value='orderBy' />";
							
							var prop = new JsProp();
							prop.sign = 'defaul';
						 	prop.propId = id;
							prop.propCode = code;
						 	prop.propName = name;
						 	prop.propType = type;
						 	prop.propDict = dict;
						 	prop.propIsNull = isNull;
						 	prop.propDefaulValue = defaulvalue;
						 	prop.propOrderBy = orderby;
						 	propMap.put(prop.propCode,prop);
						</s:iterator>
					}
					
					
					//获取所有接口，并判断哪些接口已经选中
					function initFunctionList(){
						var prefunction = document.getElementById('preFunction').value;
						var postfunction = document.getElementById('postFunction').value;
						<s:iterator value="%{interfaceList}" status="WfInterface">
							var code = "<s:property value='code' />";
							var name = "<s:property value='name' />";
							var type = "<s:property value='type' />";
							var path = "<s:property value='path' />";
							
							var preFunction = new JsFunction();
							preFunction.code = code;
							preFunction.name = name;
							preFunction.path = path;
							if(prefunction.indexOf(code) == '-1'){
								preFunction.sign = '0';
							}else{
								preFunction.sign = '1';
							}
							preFunctionMap.put(code,preFunction);
							
							
							
							
							
							var postFunction = new JsFunction();
							postFunction.code = code;
							postFunction.name = name;
							postFunction.path = path;
							if(postfunction.indexOf(code) == '-1'){
								postFunction.sign = '0';
							}else{
								postFunction.sign = '1';
							}
							postFunctionMap.put(code,postFunction);							
						</s:iterator>
					}
					
					 
			 /**
			  * 自定义模型提前方法,提交前进行验证
			  */
			function modelFormSubmit(){
				var wfForm = document.getElementById('modelForm');
				var bl  = Validator.Validate(wfForm,2,false);
					if(bl){
						var wfCode =  document.getElementById('wfModel.code').value;
						if(!check(wfCode, "<eoms:lable name='wf_model_code_require'/>！")) {
							return ;
						}
					
						var code = '${wfModel.code}';
						if(code == null || code == ''){
							$.get("${ctx}/wfcustom/wfModelCheckUnique.action",{'code':wfCode},function(result)
								{
									if(result == 'false'){
										alert('<eoms:lable name="wf_modelcode_uniquechk" />');
									}else{
										formSubmit();
									}
								}
							)
						}else{
							formSubmit();
						}
					}	
			}
			
			function formSubmit(){
				var propString = prop2String();
				document.getElementById('propString').value = propString;
				document.getElementById('preFunction').value = preCode2String();
				document.getElementById('postFunction').value = postCode2String();
				document.getElementById('modelForm').submit();
			}

				</script>
</body>
</html>