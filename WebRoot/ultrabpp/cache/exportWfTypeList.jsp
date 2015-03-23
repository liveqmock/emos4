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
	window.onresize = function() 
	{
		setCenter(0,200);
	}
	
	window.onload = function() 
	{
		setCenter(0,200);
	}
	

	
	function changeAll(){
	
		var selectAll = document.getElementById("selectAll");
		checkBoxArray = document.getElementsByName("selectCheckbox");
		if(selectAll.checked){
			
			for (var i=0; i<checkBoxArray.length; i++){
               checkBoxArray[i].checked = true;
        	}  
		}else{
			for (var i=0; i<checkBoxArray.length; i++){
               checkBoxArray[i].checked = false;
        	}  
		}
		
		checkBoxChange();
	}
	
	var selectedCheckBox=new Array();
	var checkBoxArray;
	function checkBoxChange()
	{
		checkBoxArray = document.getElementsByName("selectCheckbox");
		selectedCheckBox=new Array();
        for (var i=0; i<checkBoxArray.length; i++){
                if (checkBoxArray[i].checked){    
               	    selectedCheckBox[selectedCheckBox.length]=checkBoxArray[i].value;    
                }
        }   
	}
	function configExport(){
		
		if(selectedCheckBox.length>0){
				var baseSchemas = selectedCheckBox.join(",");
				document.getElementById("fieldExport").value = document.getElementById("fieldExportCheckBox").checked?"1":"0";
				document.getElementById("wfinfo").value = document.getElementById("wfinfoCheckBox").checked?"1":"0";
				document.getElementById("stepExport").value = document.getElementById("stepExportCheckBox").checked?"1":"0";
				document.getElementById("wfdesign").value = document.getElementById("wfdesignCheckBox").checked?"1":"0";
				document.getElementById("jsppage").value =  document.getElementById("jsppageCheckBox").checked?"1":"0";
				
				document.getElementById("baseSchemas").value = baseSchemas;
				document.getElementById('exportForm').action = '${ctx}/ultrabpp/cache/exportWfTypeList.action';
				document.getElementById('exportForm').submit(); 
			}else{
				alert("请选择需要导出的工单");
			}
	}
	
	

</script>
	</head>
	<body class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg"> <span class="title_icon2">${nodePath}</span> </span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class='scroll_div' id='center' >
			<table id='tab' class='tableborder'>
				<tr>
					<th>
						全选
						<input type="checkbox" name="selectAll" id="selectAll" onclick="changeAll()" />
					</th>
					<th>
						流程标识
					</th>
					<th>
						流程名称
					</th>
					<th>
						流程类型
					</th>
					
				</tr>
				<c:forEach var="wfType" items="${wfTypeList}">
					<tr>
						<td>
							<input id="checkbox_${wfType.code}" type="checkbox" name="selectCheckbox" value="${wfType.code}" onchange="checkBoxChange()" />
						</td>
						<td>
							<label for="checkbox_${wfType.code}">${wfType.code}</label>
						</td>
						<td>
							${wfType.name}
						</td>
						<td>&nbsp;
							<c:if test="${wfType.wfType == '0'}">
								<eoms:lable name="wf_type_free" />
							</c:if>
							<c:if test="${wfType.wfType == '1'}">
								<eoms:lable name="wf_type_fix" />
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<fieldset class="fieldset_style">
			<legend>
				导出工单配置
			</legend>
			<div class="blank_tr"></div>
			
				<div id="div1">
					<form action="exportWfTypeList.action" id="exportForm" name="exportForm">
						<input type="checkbox" name="wfinfoCheckBox" checked id="wfinfoCheckBox" />
						
						流程分类信息
						<input type="checkbox" name="fieldExportCheckBox" checked id="fieldExportCheckBox" />
						
						表单字段配置信息
						<input type="checkbox" name="stepExportCheckBox" checked id="stepExportCheckBox" />

						环节配置信息（固定）/ 状态动作关系信息（自由）
						<input type="checkbox" name="wfdesignCheckBox" checked id="wfdesignCheckBox" />

						流程定义文件
						<input type="checkbox" name="jsppageCheckBox" checked id="jsppageCheckBox" />

						jsp文件
						<input type="button" value="导出" onclick="configExport()" />
						<input type="hidden" id="baseSchemas" name="baseSchemas"/>
						<input type="hidden" name="wfinfo"  id="wfinfo" />
						<input type="hidden" name="fieldExport"  id="fieldExport" />
						<input type="hidden" name="stepExport"  id="stepExport" />
						<input type="hidden" name="wfdesign"  id="wfdesign" />
						<input type="hidden" name="jsppage"  id="jsppage" />
						<input type="hidden" id="wfSortId" value="000000000000011" name="wfSortId"/>
					</form>
				</div>
				<div class="blank_tr"></div>
				<div>
				<span class="must">注释：请选择全部选项，因为配置信息存在关联关系，如果选择部分,容易出错！</span>
				</div>
		</fieldset>
	</body>
</html>
