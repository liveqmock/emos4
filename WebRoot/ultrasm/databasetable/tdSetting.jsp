<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_fldAttrSetting"/></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		var pWindow;
		var excelTable;
		var curCell;
		var pioX;
		var pioY
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
			pWindow = dialogArguments;
			excelTable = pWindow.excelTable; 
			pioX = parseInt(pWindow.document.getElementById("tdPar").value.split(",")[0]);
			pioY = parseInt(pWindow.document.getElementById("tdPar").value.split(",")[1]);
			curCell = excelTable.rows[pioX].cells[pioY];
			//赋值开始
			var fieldname    = curCell.fieldname;
			var displayname  = curCell.displayname;
			var rowspan      = curCell.rowspan;
			var colspan      = curCell.colspan;
			var width        = curCell.width;
			var align        = curCell.align;
			var colcolor     = curCell.colcolor;
			var datatype     = curCell.datatype;
			var datalength   = curCell.datalength;
			var datainfo     = curCell.datainfo;
			var operator     = curCell.operator;
			var comparedata  = curCell.comparedata;
			var displaycolor = curCell.displaycolor;
			var isgroup      = curCell.isgroup;
			
			document.getElementById('fieldname').value      = fieldname;
			document.getElementById('displayname').value    = displayname;
			document.getElementById('rowspan').value        = rowspan;
			document.getElementById('colspan').value        = colspan;
			document.getElementById('width').value          = width;
			document.getElementsByName('align')[0].value    = align;
			document.getElementById('colcolor').value       = colcolor;
			document.getElementsByName('datatype')[0].value = datatype;
			document.getElementById('datalength').value     = datalength;
			document.getElementById('datainfo').value       = datainfo;
			document.getElementsByName('operator')[0].value = operator;
			document.getElementById('comparedata').value    = comparedata;
			document.getElementById('displaycolor').value   = displaycolor;
			document.getElementsByName('isgroup')[0].value  = isgroup;
			//赋值结束
		}
		function saveSetting()
		{
			if(Validator.Validate(document.forms[0],2))
			{
				//赋值开始
				var fieldname    = document.getElementById('fieldname').value;
				var displayname  = document.getElementById('displayname').value;
				var rowspan      = parseInt(document.getElementById('rowspan').value);
				var colspan      = parseInt(document.getElementById('colspan').value);
				var width        = parseInt(document.getElementById('width').value);
				var align        = document.getElementsByName('align')[0].value;
				var colcolor     = document.getElementById('colcolor').value;
				var datatype     = document.getElementsByName('datatype')[0].value;
				var datalength   = parseInt(document.getElementById('datalength').value);
				var datainfo     = document.getElementById('datainfo').value;
				var operator     = document.getElementsByName('operator')[0].value;
				var comparedata  = document.getElementById('comparedata').value;
				var displaycolor = document.getElementById('displaycolor').value;
				var isgroup      = document.getElementsByName('isgroup')[0].value;
				
				var b = excelTable.mergeRowAndCol(pioX,pioY,rowspan,colspan);
				if(b)
				{
					curCell.fieldname    = fieldname;
					curCell.displayname  = displayname;
					//curCell.rowspan      = rowspan;
					//curCell.colspan      = colspan;
					curCell.width        = width;
					curCell.align        = align;
					curCell.colcolor     = colcolor;
					curCell.datatype     = datatype;
					curCell.datalength   = datalength;
					curCell.datainfo     = datainfo;
					curCell.operator     = operator;
					curCell.comparedata  = comparedata;
					curCell.displaycolor = displaycolor;
					curCell.isgroup      = isgroup;
					
					pWindow.reDrawTable();
					window.close();
				}
			}
		}
	</script>
	</head>

	<body>
		<input type="hidden" id="initspan" value=""/>
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
							<form action="" method="post">
								<table  class="add_user">
							  		<caption id="tbCpation"></caption>
							  		<tr>
								  		<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_fieldEnname"/>：</td>
								  		<td style="width:35%">
								  			<input type="text" name="fieldname" id="fieldname" class="textInput" title="双击文本框选择库表字段！"
								  				   ondblclick="showModalDialog('${ctx}/common/tools/tableFldTree.jsp',window,'help:no;center:true;scroll:no;status:no;dialogWidth:380px;dialogHeight:480px');"/>
								  			<validation id="fieldnameV" dataType="Custom" regexp="^[\w]{0,50}$" msg="<eoms:lable name='sm_msg_fieldEnnameConstraint'/>！" />
								  		</td>
								  		<td class="texttd"  style="width:15%"><eoms:lable name="sm_lb_fldDisplayName"/>：</td>
								  		<td>
								  			<input type="text" name="displayname" id="displayname" class="textInput"/>
								  			<validation id="displaynameV" require="false" dataType="Limit" max="30" msg="<eoms:lable name='sm_msg_fldDisplayNameConstraint'/>！" />
								  		</td>
							  		</tr>
							  		<tr>
								  		<td class="texttd"><eoms:lable name="sm_lb_rowSpan"/>：<span class="must">*</span></td>
								  		<td>
								  			<input type="text" name="rowspan" id="rowspan" class="textInput"/>
								  			<validation id="rowspanV" dataType="Custom" regexp="^\d{1,2}$" msg="<eoms:lable name='sm_msg_rowSpanNumConstraint'/>！" />
								  		</td>
								  		<td class="texttd"><eoms:lable name="sm_lb_colSpan"/>：<span class="must">*</span></td>
								  		<td>
								  			<input type="text" name="colspan" id="colspan" class="textInput"/>
								  			<validation id="colspanV" dataType="Custom" regexp="^\d{1,2}$" msg="<eoms:lable name='sm_msg_colSpanNumConstraint'/>！" />
								  		</td>
							  		</tr>
							  		<tr>
								  		<td class="texttd"><eoms:lable name="sm_lb_colWidth"/>：<span class="must">*</span></td>
								  		<td>
								  			<input type="text" name="width" id="width" class="textInput"/>
								  			<validation id="widthV" dataType="Custom" regexp="^\d{1,10}$" msg="<eoms:lable name='sm_msg_colWidthNumConstraint'/>！" />
								  		</td>
								  		<td class="texttd"><eoms:lable name="sm_lb_isSheetGroup"/>：</td>
								  		<td>
								  			<eoms:select name="isgroup" style="select" dataDicTypeCode="isdefault"/>
								  		</td>
							  		</tr>
							  		<tr>
								  		<td class="texttd"><eoms:lable name="sm_lb_alignType"/>：</td>
								  		<td>
								  			<eoms:select name="align" style="select" dataDicTypeCode="alignType"/>
								  		</td>
								  		<td class="texttd"><eoms:lable name="sm_lb_dataLength"/>：<span class="must">*</span></td>
								  		<td>
								  			<input type="text" name="datalength" id="datalength" class="textInput"/>
								  			<validation id="datalengthV" dataType="Custom" regexp="^\d{1,5}$" msg="<eoms:lable name='sm_msg_dataLengthConstraint'/>！" />
								  		</td>
								  		<td class="texttd" style="display:none"><eoms:lable name="sm_lb_colDataBgcolor"/>：</td>
								  		<td style="display:none">
								  			<input type="text" name="colcolor" id="colcolor" class="textInput"/>
								  			<validation id="colcolorV" dataType="Custom" regexp="^[a-zA-Z#0-9]{0,20}$" msg="<eoms:lable name='sm_msg_colDataBgcolorConstraint'/>！" />
								  		</td>
							  		</tr>
							  		<tr>
								  		<td class="texttd"><eoms:lable name="sm_lb_dataType"/>：</td>
								  		<td>
								  			<eoms:select name="datatype" style="select" dataDicTypeCode="exportDataType"/>
								  		</td>
								  		<td class="texttd"><eoms:lable name="sm_lb_dataInfo"/>：</td>
								  		<td>
								  			<input type="text" name="datainfo" id="datainfo" class="textInput"/>
								  			<validation id="datainfoV" require="false" dataType="Limit" max="30" msg="<eoms:lable name='sm_msg_dataInfoConstraint'/>！" />
								  		</td>
							  		</tr>
							  		<tr>
								  		<td class="texttd" style="display:none"><eoms:lable name="sm_lb_operator"/>：</td>
								  		<td style="display:none">
								  			<eoms:select name="operator" style="select" dataDicTypeCode="operator"/>
								  		</td>
							  		</tr>
							  		<tr>
								  		<td class="texttd" style="display:none"><eoms:lable name="sm_lb_compareData"/>：</td>
								  		<td style="display:none">
								  			<input type="text" name="comparedata" id="comparedata" class="textInput"/>
								  			<validation id="comparedataV" require="false" dataType="Limit" max="50" msg="<eoms:lable name='sm_msg_compareDataConstraint'/>！" />
								  		</td>
								  		<td class="texttd" style="display:none"><eoms:lable name="sm_lb_displayColor"/>：</td>
								  		<td style="display:none">
								  			<input type="text" name="displaycolor" id="displaycolor" class="textInput"/>
								  			<validation id="displaycolorV" dataType="Custom" regexp="[a-zA-Z#0-9]{0,20}$" msg="<eoms:lable name='sm_msg_displayColorConstraint'/>！" />
								  		</td>
							  		</tr>
							  	</table>
							</form>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="saveSetting();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</body>
</html>
