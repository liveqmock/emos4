<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<%@ include file="/common/plugin/jquery/jquery.jsp" %>
    	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
		 <title>
			     <eoms:lable name="wf_sheet_dimension_addOrUpdate" />
		</title>
	<script  language="javascript">
		window.onload = function(){
    		setCenter(0,61);
    		init_dimensiondiv();
		}

		window.onresize = function()
			{
				setCenter(0,61);
				init_dimensiondiv();
			}

		function wfFormSubmit(){
			var wfForm = document.getElementById('wfForm');
			var dimCode = document.getElementById('dimension.dimCode').value;
			var bl  = Validator.Validate(wfForm,2,false);
			var bCheck = check(dimCode, "<eoms:lable name='wf_dimension_name_require'/>！");
			if(bl && bCheck){
				document.getElementById('wfForm').submit();
			}
		}
		//初始化div显示 根据维度类型 显示不同组件
		function init_dimensiondiv(){ 
			var dimensiontype = '${dimension.dimensiontype}';
			var div_table = document.getElementById("div_table");
			var div_sysdic = document.getElementById("div_sysdic");
			var div_remedy = document.getElementById("div_remedy");
			div_table.style.display = 'none';
			div_sysdic.style.display = 'none';
			div_remedy.style.display = 'none';
			if(dimensiontype == ''){
				div_remedy.style.display = '';
			}else{
				if(dimensiontype == 'table')div_table.style.display = '';
				if(dimensiontype == 'sysdic')div_sysdic.style.display = '';
				if(dimensiontype == 'remedy')div_remedy.style.display = '';
			}
		}
		function change_dimensiondiv(){
			div_table.style.display = 'none';
			div_sysdic.style.display = 'none';
			div_remedy.style.display = 'none';
			var dimensiontype = document.getElementById("dimension.dimensiontype").value;
			var id='div_'+dimensiontype;
			document.getElementById(id).style.display = '';
		}
    </script>
</head>
<body>
<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
								<eoms:lable name="wf_sheet_dimension_addOrUpdate" />
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>

		<div class="scroll_div" id="center">
						
					    <s:form action="saveDimension.action" theme="simple" id="wfForm" method="post" target="_self">
					    	<s:hidden name="dimension.dimensionid"></s:hidden>
					    	<s:if test="%{baseSchema != null and baseSchema != ''}">
								<s:hidden name="dimension.baseschema"></s:hidden>
								<s:hidden name="dimension.basename"></s:hidden>
							</s:if>
							<s:hidden name="dimension.dimensioncode"></s:hidden>
							<div class="tabContent_top">
								<table class="add_user2">
									<s:if test="%{baseSchema == null or baseSchema == ''}">
										<tr>
											<td class="texttd">
												业务名称<span class="must">*</span>：
											</td>
											<td>
												<s:textfield name="dimension.basename" id="dimension.basename" cssClass="textInput" maxlength="80"></s:textfield>
												<validation id="dimension.basenameV" require="true" dataType="Require" msg="业务名称！" />
											</td>
										</tr>
										<tr>
											<td class="texttd">
												业务标识<span class="must">*</span>：
											</td>
											<td>
												<s:textfield name="dimension.baseschema" id="dimension.baseschema" cssClass="textInput" maxlength="80"></s:textfield>
												<validation id="dimension.baseschemaV" require="true" dataType="Require" msg="业务标识！" />
											</td>
										</tr>
									</s:if>
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_sheet_dimension_dimensionname" /><span class="must">*</span>：
										</td>
										<td>
											<s:textfield name="dimension.dimensionname" id="dimension.dimensionname" cssClass="textInput" maxlength="80"></s:textfield>
											<validation id="dimension.dimensionnameV" require="true" dataType="Require" msg="<eoms:lable name='wf_dimension_name_require'/>！" />
										</td>
									</tr>
									<tr>
										<td class="texttd">
											<eoms:lable name="wf_sheet_dimension_dimensioncode" /><span class="must">*</span>：
										</td>
										<td>
											<input type="text" name="dimension.dimCode" id="dimension.dimCode" class="textInput" value="${dimension.dimCode}" maxlength="50"/>
											<validation id="dimension.dimCodeV" require="true" dataType="Require" msg="<eoms:lable name='wf_dimension_code_require'/>！" />
										</td>
									</tr> 
									<tr>
										<td class="texttd">
											维度类型<span class="must">*</span>：
										</td>
										<td>
										 	<s:select name="dimension.dimensiontype" id="dimension.dimensiontype" cssStyle="width:98%" cssClass="select" list="#{'remedy':'remedy','table':'table','sysdic':'sysdic'}"   listKey="key" listValue="value" onchange="change_dimensiondiv()"	/>								
										</td>
									</tr>
								</table>
								<div  id="div_table">
									<table class="add_user2">
										<tr>
											<td class="texttd">表名：</td>
											<td>
											<input type="text" name="dimension.tablename" class="textInput" value="${dimension.tablename}" maxlength="80"/>
											</td>
										</tr>
										<tr>
											<td class="texttd">列名：</td>
											<td>
											<input type="text" name="dimension.tablecol" class="textInput" value="${dimension.tablecol}" maxlength="80"/>
											</td>
										</tr>
										<tr>
											<td class="texttd">客户化SQL（第一列为key，第二列为value）：</td>
											<td>
											<input type="text" name="dimension.customSql" class="textInput" value="${dimension.customSql}" maxlength="1000"/>
											</td>
										</tr>
									</table>
								</div>
								<div id="div_sysdic">
									<table class="add_user2">
										<tr>
											<td class="texttd">字典标识：</td>
											<td>
											<input type="text" name="dimension.dtcode" class="textInput" value="${dimension.dtcode}" maxlength="80"/>
											</td>
										</tr>
										<tr>
											<td class="texttd">参数：</td>
											<td>
											<input type="text" name="dimension.divalue" class="textInput" value="${dimension.divalue}" maxlength="80"/>
											</td>
										</tr>
									</table>
								</div>
								<div id="div_remedy">
									<table  class="add_user2">
										<tr>
											<td class="texttd">
												<eoms:lable name="wf_sheet_dimension_fieldid" /><span class="must"></span>：
											</td>
											<td>
												<input type="text" name="dimension.fieldid" class="textInput" value="${dimension.fieldid}" maxlength="80"/>
												<!-- 
												<validation id="dimension.fieldidV" require="true" dataType="Require" msg="<eoms:lable name='wf_dimension_fileid_require'/>！" />
												 -->
											</td>
										</tr>
										<tr>
											<td class="texttd">
												<eoms:lable name="wf_sheet_dimension_dictname" /><span class="must"></span>：
											</td>
											<td>
												<input type="text" name="dimension.dictname" class="textInput" value="${dimension.dictname}" maxlength="80"/>
												<!-- 
												<validation id="dimension.dictnameV" require="true" dataType="Require" msg="<eoms:lable name='wf_dimension_dictname_require'/>！" />
												 -->
											</td>
										</tr>
										<tr>
											<td class="texttd">
												<eoms:lable name="wf_sheet_dimension_dictschema" /><span class="must"></span>：
											</td>
											<td>
												<input type="text" name="dimension.dictschema" class="textInput" value="${dimension.dictschema}" maxlength="80"/>
												<!-- 
												<validation id="dimension.dictschemaV" require="true" dataType="Require" msg="<eoms:lable name='wf_dimension_dictschema_require'/>！" />
												 -->
											</td>
										</tr>
										<tr>
											<td class="texttd">
												<eoms:lable name="wf_sheet_dimension_dictfiledid" /><span class="must"></span>：
											</td>
											<td>
												<input type="text" name="dimension.dictfieldid" class="textInput" value="${dimension.dictfieldid}" maxlength="80"/>
												<!-- 
												<validation id="dimension.dictfieldidV" require="true" dataType="Require" msg="<eoms:lable name='wf_dimension_dictfieldid_require'/>！" />
												 -->
											</td>
										</tr>
										<tr>
											<td class="texttd">
												<eoms:lable name="wf_sheet_dimension_dictfiledcode" />：
											</td>
											<td>
												<input type="text" name="dimension.dictfieldcode" class="textInput" value="${dimension.dictfieldcode}" maxlength="80"/>
											</td>
										</tr>
										<tr>
											<td class="texttd">
												<eoms:lable name="wf_sheet_dimension_dictfiledname" />：
											</td>
											<td>
												<input type="text" name="dimension.dictfieldname" class="textInput" value="${dimension.dictfieldname}" maxlength="80"/>
											</td>
										</tr>
									</table>
								</div>
							</div>
  					  </s:form>
				</div>
					
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="wfFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
				</div>
	</div>
</body>
</html>