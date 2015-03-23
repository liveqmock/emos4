<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="ftr_title_ftrMaintain" /></title>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,61);
			}
			window.onload = function() 
			{
				setCenter(0,61);
			}
			function submitForm()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					document.forms[0].submit();
				}
			}
		</script>
	</head>

	<body>
	  <form action="${ctx}/indexFieldManager/saveField.action" method="post">
		<input type="hidden" name="field.pid" value="${field.pid }"/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="ftr_lb_addOrUpdateField"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="ftr_lb_addOrUpdateField"/></legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<table class="add_user">
							<tr>
								<td class="texttd" style="width:15%">
									<eoms:lable name="ftr_lb_indexCategory"/>：<span class="must">*</span>
								</td>
								<td style="width:35%">
									<select name="field.indextypeid" class="select">
										<option value=""></option>
										<c:forEach items="${indexCategory}" var="category">
											<c:choose>
												<c:when test="${category.key==field.indextypeid}">
													<option value="${category.key }" selected>${category.value }</option>
												</c:when>
												<c:otherwise>
													<option value="${category.key }">${category.value }</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
									<validation id="field.indextypeidV" dataType="Require" msg="<eoms:lable name='ftr_msg_selectIdxCategoryConst'/>！" />
								</td>
								<td class="texttd" style="width:15%">
									<eoms:lable name="ftr_lb_oweToSourceType"/>：
								</td>
								<td style="width:35%">
									<eoms:select name="field.sourcetype" style="select" value="${field.sourcetype}" dataDicTypeCode="FTRSourceType" isnull="false"/>
									<validation id="field.sourcetypeV" dataType="Require" msg="<eoms:lable name='ftr_msg_oweToSourceTypeConst'/>！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="ftr_lb_SQLFieldName"/>：<span class="must">*</span>
								</td>
								<td>
									<input type="text" name="field.sqlfieldname" class="textInput" value="${field.sqlfieldname}"/>
									<validation id="field.sqlfieldnameV" dataType="Limit" min="1" max="30" msg="<eoms:lable name='ftr_msg_SQLFieldNameConst'/>！" />
								</td>
								<td class="texttd">
									<eoms:lable name="ftr_lb_idxFieldName"/>：<span class="must">*</span>
								</td>
								<td>
									<input type="text" name="field.indexfieldname" class="textInput" value="${field.indexfieldname}"/>
									<validation id="field.indexfieldnameV" dataType="Limit" min="1" max="30" msg="<eoms:lable name='ftr_msg_idxFieldNameConst'/>！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="ftr_lb_fieldDisplayName"/>：<span class="must">*</span>
								</td>
								<td>
									<input type="text" name="field.displayname" class="textInput" value="${field.displayname}"/>
									<validation id="field.displaynameV" dataType="Limit" min="1" max="30" msg="<eoms:lable name='ftr_msg_fieldDisplayNameConst'/>！" />
								</td>
								<td class="texttd">
									<eoms:lable name="ftr_lb_orderNumber"/>：<span class="must">*</span>
								</td>
								<td>
									<input type="text" name="field.ordernum" class="textInput" value="${field.ordernum}"/>
									<validation id="field.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="ftr_lb_fieldType"/>：
								</td>
								<td>
									<eoms:select name="field.fieldtype" style="select" value="${field.fieldtype}" dataDicTypeCode="exportDataType" isnull="false"/>
								</td>
								<td class="texttd">
									<eoms:lable name="ftr_lb_fieldTypeParam"/>：
								</td>
								<td>
									<input type="text" name="field.fieldtypedata" class="textInput" value="${field.fieldtypedata}"/>
									<validation id="field.fieldtypedataV" dataType="Limit" max="200" msg="<eoms:lable name='ftr_msg_fieldTypeParamConst'/>！" />
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="ftr_lb_isIndex"/>：<span class="must">*</span>
								</td>
								<td>
									<eoms:select name="field.isindex" style="select" value="${field.isindex}" dataDicTypeCode="isdefault" isnull="false"/>
								</td>
								<td class="texttd">
									<eoms:lable name="ftr_lb_isAnalyze"/>：<span class="must">*</span>
								</td>
								<td>
									<eoms:select name="field.isanalyze" style="select" value="${field.isanalyze}" dataDicTypeCode="isdefault" isnull="false"/>
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="ftr_lb_isStore"/>：<span class="must">*</span>
								</td>
								<td>
									<eoms:select name="field.isstore" style="select" value="${field.isstore}" dataDicTypeCode="isdefault" isnull="false"/>
								</td>
								<td class="texttd">
									<eoms:lable name="ftr_lb_isAddContentSearch"/>：<span class="must">*</span>
								</td>
								<td>
									<eoms:select name="field.iskeyfield" style="select" value="${field.iskeyfield}" dataDicTypeCode="isdefault" isnull="false"/>
								</td>
							</tr>
							<tr>
								<td class="texttd">
									<eoms:lable name="ftr_lb_isDisplay"/>：<span class="must">*</span>
								</td>
								<td>
									<eoms:select name="field.isdisplay" style="select" value="${field.isdisplay}" dataDicTypeCode="isdefault" isnull="false"/>
								</td>
								<td class="texttd">
									<eoms:lable name="ftr_lb_isTitle"/>：<span class="must">*</span>
								</td>
								<td>
									<c:if test="${field.istitle==null}">
										<eoms:select name="field.istitle" style="select" value="0" dataDicTypeCode="isdefault" isnull="false"/>
									</c:if>
									<c:if test="${field.istitle!=null}">
										<eoms:select name="field.istitle" style="select" value="${field.istitle}" dataDicTypeCode="isdefault" isnull="false"/>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="texttd">
									数据型索引字段：
								</td>
								<td>
									<eoms:select name="field.numericfield" style="select" value="${field.numericfield}" dataDicTypeCode="FTR_NumericFieldType" isnull="true"/>
								</td>
							</tr>
						</table>
					</div>
					<div class="blank_tr"></div>
				</fieldset>
				<div class="blank_tr"></div>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="submitForm();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
