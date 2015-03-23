<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_t_addOrUpdateExCfg" /></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/excelTable.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/excelTableUtil.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		var excelTable;
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
			document.oncontextmenu=function(){
				return false;
			} 
			//document.onselectstart=function()
			//{
				//屏蔽右键菜单2
				//return false;
			//}
			var pid = '${excelExpCfg.pid}';
			if(pid!='')
			{
				$.post("${ctx }/excelManager/getTbData.action",{pid:pid},function(xmlstr)
				{
					excelTable = ExcelTableUtil.createExcelTableFromXml(xmlstr);
					document.getElementById("tbContainer").innerHTML = ExcelTableUtil.toInnerHTML(excelTable);
				});
			}
		}
		/*单元格属性设置*/
		function cellSet(par)
		{
			var eventSrc = par.srcElement || par.target;
			var pioX = eventSrc.pioX;
			var pioY = eventSrc.pioY;
			document.getElementById("tdPar").value = pioX+","+pioY;
			showModalDialog('${ctx}/ultrasm/databasetable/tdSetting.jsp',window,'help:no;center:true;scroll:no;status:no;dialogWidth:800px;dialogHeight:450px');
		}
		/*生成新的表格*/
		function breedTable()
		{
			var rowCount = document.getElementById("gen_row").value;
			var colCount = document.getElementById("gen_col").value;
			if(document.getElementById('cfgTb')!=null)
			{
				$("#cfgTb").remove();
			}
			var reg = /^\d{1,3}$/; //最多三位数字
			if(""!=rowCount && ""!=colCount && reg.test(rowCount) && reg.test(colCount))
			{
				excelTable = ExcelTableUtil.initTable(rowCount,colCount);
				document.getElementById("tbContainer").innerHTML = ExcelTableUtil.toInnerHTML(excelTable);
			}
		}
		/*根据excelTable重画表格*/
		function reDrawTable()
		{
			document.getElementById("tbContainer").innerHTML = ExcelTableUtil.toInnerHTML(excelTable);
		}
		/*重置表格*/
		function resetTable()
		{
			if(confirm("重置表格后，单元格属性数据将会丢失，确定重置吗？"))
			{
				breedTable();
			}
			
		}
		/*构造XML数据传入后台*/
		function breedTbXml()
		{
			document.getElementById("xmlstr").value = ExcelTableUtil.toXmlEnable(excelTable);
		}
		/*提交表单*/
		function formSubmit()
		{
			if(Validator.Validate(document.forms[0],2))
			{
				var pid = "${excelExpCfg.pid }";
				if(pid=="")
				{
					var cfgMark = document.getElementById('excelExpCfg.cfgmark').value;
					$.get('${ctx}/excelManager/checkCfgMarkUnique.action',{cfgMark:cfgMark},function(result){
						if("true"==result)
						{
							if(document.getElementById('cfgTb')==null)
							{
								alert("<eoms:lable name='sm_msg_banBlankTable'/>！");
								return;
							}
							breedTbXml();
							document.forms[0].submit();
						}
						else
						{
							alert("导出配置标识已存在，请选择其他配置标识！");
						}
					});
				}
				else
				{
					if(document.getElementById('cfgTb')==null)
					{
						alert("<eoms:lable name='sm_msg_banBlankTable'/>！");
						return;
					}
					breedTbXml();
					document.forms[0].submit();
				}
				
				
			}
		}
		/*鼠标右击处理*/
		function rightClick(par)
		{
			var eventSrc = par.srcElement || par.target;
			var pioX = eventSrc.pioX;
			var pioY = eventSrc.pioY;
			document.getElementById("tdPar").value = pioX+","+pioY;
			var eventX = par.clientX-10;
			var eventY = par.clientY-10;
			var obj = document.getElementById("rMenu");
			obj.style.left = eventX;
			obj.style.top = eventY;
			obj.style.display = "block";
			return false;
		}
		/*当前单元格所在行插入行*/
		function tbInserRow()
		{
			var pioX = parseInt(document.getElementById("tdPar").value.split(",")[0]);
			excelTable.insertRow(pioX);
			reDrawTable();
			document.getElementById("rMenu").style.display = "none";
			var rowCount = document.getElementById("gen_row").value;
			document.getElementById("gen_row").value = parseInt(rowCount) + 1;
		}
		/*当前单元格所在列插入列*/
		function tbInsertCol()
		{
			var pioY = parseInt(document.getElementById("tdPar").value.split(",")[1]);
			excelTable.insertCol(pioY);
			reDrawTable();
			document.getElementById("rMenu").style.display = "none";
			document.getElementById("rMenu").style.display = "none";
			var colCount = document.getElementById("gen_col").value;
			document.getElementById("gen_col").value = parseInt(colCount) + 1;
		}
		/*当前表格后追加列*/
		function tbAppendCol()
		{
			var pioY = parseInt(document.getElementById("tdPar").value.split(",")[1]);
			excelTable.appendCol(pioY);
			reDrawTable();
			document.getElementById("rMenu").style.display = "none";
			var colCount = document.getElementById("gen_col").value;
			document.getElementById("gen_col").value = parseInt(colCount) + 1;
		}
		/*删除当前单元格所在行*/
		function rowDelete()
		{
			var pioX = parseInt(document.getElementById("tdPar").value.split(",")[0]);
			excelTable.deleteRow(pioX);
			reDrawTable();
			document.getElementById("rMenu").style.display = "none";
			var rowCount = document.getElementById("gen_row").value;
			document.getElementById("gen_row").value = parseInt(rowCount) - 1;
		}
		/*删除当前单元格所在列*/
		function colDelete()
		{
			var pioY = parseInt(document.getElementById("tdPar").value.split(",")[1]);
			excelTable.deleteCol(pioY);
			reDrawTable();
			document.getElementById("rMenu").style.display = "none";
			var colCount = document.getElementById("gen_col").value;
			document.getElementById("gen_col").value = parseInt(colCount) - 1;
		}
		/*回退操作*/
		function cancelInsert()
		{
			
		}
		/*保存回退点*/
		function saveTbFrame()
		{
			
		}
	</script>
	</head>

	<body>
	  <input type="hidden" id="initTbFrame" value=""/>
	  <input type="hidden" id="lastTbFrame" value=""/>
	  <!-- 右键菜单DIV -->
	  <div id="rMenu" style="position:absolute;background:white;border:1px blue solid;width:100px;display:none;left:0px;top:0px;"
	  	   onmouseover="this.style.display='block'" onmouseout="this.style.display='none'">
	  	<div onmouseover="this.style.background='cyan'" onmouseout="this.style.background='white'">
	  		<a href="javascript:;" onclick="tbInserRow();">插入一行</a>
	  	</div>
	  	<div onmouseover="this.style.background='cyan'" onmouseout="this.style.background='white'">
	  		<a href="javascript:;" onclick="tbInsertCol();">插入一列</a>
	  	</div>
	  	<div onmouseover="this.style.background='cyan'" onmouseout="this.style.background='white'">
	  		<a href="javascript:;" onclick="tbAppendCol();">追加一列</a>
	  	</div>
	  	<hr/>
	  	<div onmouseover="this.style.background='cyan'" onmouseout="this.style.background='white'">
	  		<a href="javascript:;" onclick="rowDelete();">删除该行</a>
	  	</div>
	  	<div onmouseover="this.style.background='cyan'" onmouseout="this.style.background='white'">
	  		<a href="javascript:;" onclick="colDelete();">删除该列</a>
	  	</div>
	  	<hr/>
	  	<div style="display:none" onmouseover="this.style.background='cyan'" onmouseout="this.style.background='white'">
	  		<a href="javascript:;" onclick="cancelInsert();">回退操作</a>
	  	</div>
	  </div>
	  <input type="hidden" id="tdPar" value=""/><!-- 记录当前td的行和列位置 -->
	  <form action="${ctx }/excelManager/saveExcelExpCfg.action" method="post">
  		<input type="hidden" id="xmlstr" name="xmlstr" id="xmlstr" value=""/>
	  	<input type="hidden" id="updateTableFrame" name="updateTableFrame" id="updateTableFrame" value=""/>
	  	<input type="hidden" id="excelExpCfg.pid" name="excelExpCfg.pid" value="${excelExpCfg.pid }"/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="sm_t_addOrUpdateExCfg" /></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>
						<eoms:lable name="sm_lb_excelCfgManage"/>
					</legend>
					<div class="blank_tr"></div>
					<div class="tabContent_top">
						<div id="div1">
							<table class="add_user">
								<tr>
							  		<td class="texttd" style="width:15%"><eoms:lable name="sm_lb_excelCfgMark"/>：<span class="must">*</span></td>
							  		<td style="width:35%">
							  			<input type="text" id="excelExpCfg.cfgmark" name="excelExpCfg.cfgmark" value="${excelExpCfg.cfgmark }" class="textInput"/>
							  			<validation id="excelExpCfg.cfgmarkV" dataType="Custom" regexp="^[\w]{1,50}$" msg="<eoms:lable name='sm_msg_excelCfgMarkConstraint'/>！" />
							  		</td>
							  		<td class="texttd"  style="width:15%"><eoms:lable name="sm_lb_excelCfgName"/>：<span class="must">*</span></td>
							  		<td>
							  			<input type="text" id="excelExpCfg.cfgname" name="excelExpCfg.cfgname" value="${excelExpCfg.cfgname }" class="textInput"/>
							  			<validation id="excelExpCfg.cfgnameV" dataType="Limit" min="1" max="30" msg="<eoms:lable name='sm_msg_excelCfgNameConstraint'/>！" />
							  		</td>
						  		</tr>
						  		<tr>
							  		<td class="texttd"><eoms:lable name="sm_lb_excelName"/>：</td>
							  		<td>
							  			<input type="text" id="excelExpCfg.excelname" name="excelExpCfg.excelname" value="${excelExpCfg.excelname }" class="textInput"/>
							  			<validation id="excelExpCfg.excelnameV" require="false" dataType="Limit" max="30" msg="<eoms:lable name='sm_msg_excelNameConstraint'/>！" />
							  		</td>
							  		<td class="texttd"><eoms:lable name="sm_lb_recordsPerPage"/>：</td>
							  		<td>
							  			<input type="text" id="excelExpCfg.sheetrow" name="excelExpCfg.sheetrow" value="${excelExpCfg.sheetrow }" class="textInput"/>
							  			<validation id="excelExpCfg.sheetrowV" dataType="Custom" regexp="^\d{0,10}$" msg="<eoms:lable name='sm_msg_perPageRecdNumConstraint'/>！" />
							  		</td>
						  		</tr>
						  		<tr>
							  		<td class="texttd"><eoms:lable name="sm_lb_titleRows"/>：<span class="must">*</span></td>
							  		<td>
							  			<input type="text" name="excelExpCfg.titlerow" id="gen_row" value="${excelExpCfg.titlerow }" class="textInput"/>
							  			<validation id="excelExpCfg.titlerowV" dataType="Custom" regexp="^\d{1,5}$" msg="<eoms:lable name='sm_msg_titleRowNumConstraint'/>！" />
							  		</td>
							  		<td class="texttd"><eoms:lable name="sm_lb_titleCols"/>：<span class="must">*</span></td>
							  		<td>
							  			<input type="text" name="excelExpCfg.titlecol" id="gen_col" value="${excelExpCfg.titlecol }" class="textInput"/>
							  			<validation id="excelExpCfg.titlecolV" dataType="Custom" regexp="^\d{1,5}$" msg="<eoms:lable name='sm_msg_titleColNumConstraint'/>！" />
							  		</td>
						  		</tr>
						  		<tr>
							  		<td class="texttd"><eoms:lable name="sm_lb_mergeColsInfo"/>：</td>
							  		<td>
							  			<input type="text" id="excelExpCfg.colmerge" name="excelExpCfg.colmerge" value="${excelExpCfg.colmerge }" class="textInput"/>
							  		</td>
							  		<td class="texttd"><eoms:lable name="sm_lb_isColorIntervalRow"/>：</td>
							  		<td>
							  			<eoms:select name="excelExpCfg.interlacedcolor" style="select" dataDicTypeCode="isdefault" value="${excelExpCfg.interlacedcolor}"/>
							  		</td>
						  		</tr>
						  		<tr>
							  		<td class="texttd"><eoms:lable name="com_lb_remark"/>：</td>
							  		<td colspan="3">
							  			<textarea rows="5" cols="60" id="excelExpCfg.remark" name="excelExpCfg.remark" style="width:98.7%">${excelExpCfg.remark }</textarea>
							  			<validation id="excelExpCfg.remarkV" require="false" dataType="Limit" max="160" msg="<eoms:lable name='sm_msg_tbRemarkConstraint'/>！" />
							  		</td>
						  		</tr>
							</table>
							<hr />
							<c:if test="${excelExpCfg.pid==null}">
								<input type="button" value="生成表格" onclick="breedTable();"/>
							</c:if>
							<c:if test="${excelExpCfg.pid!=null}">
								<input type="button" value="重置表格" onclick="resetTable();"/>
							</c:if>
								<input style="display:none" type="button" value="保存操作" onclick="saveTbFrame();"/>
								<input style="display:none" type="button" value="innerHTML" onclick="alert(document.getElementById('tbContainer').innerHTML);"/>
							<br/><br/>
							<div id="tbContainer"></div>
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
