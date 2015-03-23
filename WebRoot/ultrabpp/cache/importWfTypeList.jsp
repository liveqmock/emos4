<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
		<script language="javascript">
	var baseSchemaArray = new Array();
	var exportInfoArray = new Array(); 
	
	window.onresize = function() 
	{
		setCenter(0,200);
		
	}
	
	window.onload = function() 
	{
		setCenter(0,200);
		initCheckbox();
	}
	
	function initCheckbox()
	{
		for(var i=0;i<baseSchemaArray.length;i++){
			var id_BaseSchema = baseSchemaArray[i];
			var exportInfo = exportInfoArray[i];
			if(exportInfo.wfinfo == 1){
				document.getElementById(id_BaseSchema+'_wfinfo').checked = true;
			}
			if(exportInfo.fieldexport == 1){
				document.getElementById(id_BaseSchema+'_fieldexport').checked = true;
			}
			if(exportInfo.stepexport == 1){
				document.getElementById(id_BaseSchema+'_stepexport').checked = true;
			}
			if(exportInfo.jsppage == 1){
				document.getElementById(id_BaseSchema+'_jsppage').checked = true;
			}
		}
	}
	
	function changeAll(exportType){
	
		var checkbox = document.getElementById('selectAll_'+exportType);
		
		for(var i=0;i<baseSchemaArray.length;i++){
			var id_BaseSchema = baseSchemaArray[i];
			var exportInfo = exportInfoArray[i];
			
			document.getElementById(id_BaseSchema+'_'+exportType).checked = checkbox.checked;
			
			switch (exportType) {
			        case 'wfinfo':
			           exportInfo.wfinfo = checkbox.checked?'1':'0';
			        break;
			        case 'fieldexport':
			           exportInfo.fieldexport = checkbox.checked?'1':'0';
			        break;
			        case 'stepexport':
			           exportInfo.stepexport = checkbox.checked?'1':'0';
			        break;
			        case 'jsppage':
			           exportInfo.jsppage = checkbox.checked?'1':'0';
			        break;
			}

			
		}
	}
	
	function changeCheckBox(baseSchema,exportType){
		var checkbox = document.getElementById(baseSchema+'_'+exportType);
		var exportInfo;
		for(var i=0;i<baseSchemaArray.length;i++){
			if(baseSchemaArray[i]==baseSchema){
				exportInfo = exportInfoArray[i];
			}
		}
		
		switch (exportType) {
			        case 'wfinfo':
			           exportInfo.wfinfo = checkbox.checked?'1':'0';
			        break;
			        case 'fieldexport':
			           exportInfo.fieldexport = checkbox.checked?'1':'0';
			        break;
			        case 'stepexport':
			           exportInfo.stepexport = checkbox.checked?'1':'0';
			        break;
			        case 'jsppage':
			           exportInfo.jsppage = checkbox.checked?'1':'0';
			        break;
			}
	}

	function versionList(baseSchema,selectVersion){
		var src = '${ctx}/ultrabpp/cache/versionList.action?baseSchema='+baseSchema+'&selectVersion='+selectVersion;
		window.open(src,'','width=550px,height=350px,top=150px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
	 }
	
	function ExportInfo(wfinfo,fieldexport,stepexport,wfversion,jsppage){
		this.wfinfo = wfinfo;
		this.fieldexport = fieldexport;
		this.stepexport = stepexport;
		this.wfversion = wfversion;
		this.jsppage = jsppage;
	}

	//导出，先判断有没有字段导入操作 如果有的话 将先提供给用户sql操作语句，带操作完成之后才能进行导入操作
	function importBaseSchame(force){
		var subString = '';
		var needSqlArray = new Array();
		for(var i=0;i < baseSchemaArray.length;i++){
			var baseSchema = baseSchemaArray[i];
			var exportInfo = exportInfoArray[i];
			if(exportInfo.fieldexport == '1'){
				needSqlArray[needSqlArray.length]
			}
			if(i==0){
				subString = baseSchema+','+exportInfo.wfinfo+','+exportInfo.fieldexport+','+exportInfo.stepexport+','+exportInfo.jsppage+','+exportInfo.wfversion;
			}else{
				subString +='#'+baseSchema+','+exportInfo.wfinfo+','+exportInfo.fieldexport+','+exportInfo.stepexport+','+exportInfo.jsppage+','+exportInfo.wfversion;
			}
		}
		document.getElementById('force').value = force;
		if(force=='1'){
			if(confirm("强制导入流程信息操作会将原有流程信息以及工单信息清空，您确定要进行强制导入操作吗?")){
				document.getElementById('baseSchemasConfig').value = subString;
				document.getElementById('importBaseSchameForm').submit(); 
			}			
		}else{
			document.getElementById('baseSchemasConfig').value = subString;
			document.getElementById('importBaseSchameForm').submit();
		}
	}

function loadFile(){
	window.location.href('${ctx}/ultrabpp/cache/importFileSelect.jsp');
}
</script>
	</head>
	<body class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">当前位置：流程导入管理</span> </span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class='scroll_div' id='center' >
			<table id='tab' class='tableborder'>
				<tr>
					
					<th>
						流程标识
					</th>
					<c:if test="${exportInfo.wfinfo==1}">
					<th>
						流程分类信息
						<input type="checkbox" name="selectAll_wfinfo" id="selectAll_wfinfo" onclick="changeAll('wfinfo')" />
					</th>
					</c:if>
					<c:if test="${exportInfo.fieldexport==1}">
					<th>
						表单字段配置信息
						<input type="checkbox" name="selectAll_fieldexport" id="selectAll_fieldexport" onclick="changeAll('fieldexport')" />
					</th>
					</c:if>
					<c:if test="${exportInfo.stepexport==1}">
					<th>
						环节配置信息
						<input type="checkbox" name="selectAll_stepexport" id="selectAll_stepexport" onclick="changeAll('stepexport')" />
					</th>
					</c:if>
					<c:if test="${exportInfo.wfdesign==1}">
					<th>
						流程定义版本
					</th>
					</c:if>
					<c:if test="${exportInfo.jsppage==1}">
					<th>
						jsp文件
						<input type="checkbox" name="selectAll_jsppage" id="selectAll_jsppage" onclick="changeAll('jsppage')" />
					</th>
					</c:if>
				</tr>
				<c:forEach var="item" items="${exportInfo.wftypeMap}">
					<tr>
						<td>
							${item.key}
							<script type="text/javascript">
							baseSchemaArray[baseSchemaArray.length] = '${item.key}';
							exportInfoArray[exportInfoArray.length] = new ExportInfo('${item.value.wfinfo}',
																					 '${item.value.fieldexport}',
																					 '${item.value.stepexport}',
																					 '${item.value.publishVersion}',
																					 '${item.value.jsppage}');
							</script>
						</td>
						<c:if test="${exportInfo.wfinfo==1}">
						<td>
							<input type="checkbox" name="${item.key}_wfinfo" id="${item.key}_wfinfo" onclick="changeCheckBox('${item.key}','wfinfo')" />
						</td>
						</c:if>
						<c:if test="${exportInfo.fieldexport==1}">
						<td>
							<input type="checkbox" name="${item.key}_fieldexport" id="${item.key}_fieldexport" onclick="changeCheckBox('${item.key}','fieldexport')" />
						</td>
						</c:if>
						<c:if test="${exportInfo.stepexport==1}">
						<td>
							<input type="checkbox" name="${item.key}_stepexport" id="${item.key}_stepexport" onclick="changeCheckBox('${item.key}','stepexport')" />
						</td>
						</c:if>
						<c:if test="${exportInfo.wfdesign==1}">
						<td>
							<a href="javascript:;"  onclick="versionList('${item.key}','${item.value.publishVersion}')">
							${item.value.publishVersion}
							<c:if test="${item.value.publishVersion==null||item.value.publishVersion==''}">
							请选择流程定义文件
							</c:if>
							</a>
						</td>
						</c:if>
						<c:if test="${exportInfo.jsppage==1}">
						<td>
							<input type="checkbox" name="${item.key}_jsppage" id="${item.key}_jsppage" onclick="changeCheckBox('${item.key}','jsppage')" />
						</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>
		<fieldset class="fieldset_style">
			<legend>
				导入功能
			</legend>
			<div class="blank_tr"></div>
				<div id="div1">
					<!-- <form action="importWfTypeList.action" id="importForm" name="importForm" enctype="multipart/form-data"  method="post" target="_self">
						<input type="file" id="importFile" name="importFile"/>
						<input type="hidden" id="reLoad" name="reLoad" value="1"/>
						<input type="button" id="load" name="load" value="重新加载" onclick="loadFile()"/>
					</form> -->
					<form action="importConfig.action" id="importBaseSchameForm" name="importBaseSchameForm" method="post" target="_self">
						<input type="hidden" id="baseSchemasConfig" name="baseSchemasConfig" />
						<input type="hidden" id="force" name="force" />
						<input type="button" id="load" name="load" value="重新加载" onclick="loadFile()"/>
						<input type="button"  value="导入" onclick="importBaseSchame('0')"/>
						<input type="button"  value="强制导入" onclick="importBaseSchame('1')"/>
					</form>
				</div>
		</fieldset>
	</body>
</html>
