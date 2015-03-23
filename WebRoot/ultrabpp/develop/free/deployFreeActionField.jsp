<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
<script language="javascript">
	window.onresize = function() 
	{
		setCenter(0,60);
	}
	
	window.onload = function() 
	{
		setCenter(0,60);
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
	
	function actionFormSubmit(){
	
		if(selectedCheckBox.length==0){
			alert("请选择需要部署的动作！");
			return false;
		}
		var msg='';
		var stepArray = new Array();
		for (var i=0; i<selectedCheckBox.length; i++){
               var stepTexValue = document.getElementById(selectedCheckBox[i]).value;
               stepArray[i]=stepTexValue;
               if(stepTexValue.indexOf('_')!=-1)
               {
               		msg = '关联环节名称不能包含下划线,请修改。';
               		alert(msg);
               		return false;
               }
        } 
        
       	for (var i=0; i<selectedCheckBox.length; i++){
               selectedCheckBox[i]+="_"+stepArray[i];
        }
		
		document.getElementById("actionType").value=selectedCheckBox.join(",");
		document.getElementById('baseForm').submit();
		
	}
	function preview(baseSchema, statusAndActionType){
		var src = '${ctx}/ultrabpp/previewAction.action?baseSchema='+baseSchema+'&actionType='+encodeURI(encodeURI(statusAndActionType))+'&mode=PREVIEWACTION';	  
		window.open(src);
	}
</script>

</head>
<body>
 <div class="content">
 <div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							动作详情信息
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		
  <dg:datagrid  var="statusAndFreeAction" items="${statusAndFreeActionList}">
	   	<dg:gridtitle>
	    	<tr>
	    		<th>
				全选
				<input type="checkbox" name="selectAll" id="selectAll"  onclick="changeAll()" />
				</th>
				<th>
				状态
				</th>
				<th>
				动作类型
				</th>
				<th>
				关联环节
				</th>
				<th>
				操作
				</th>
			</tr>
	   	</dg:gridtitle> 
		<dg:gridrow>
			<tr >
				<td><input type="checkbox" name="selectCheckbox" value="${statusAndFreeAction[0]}:${statusAndFreeAction[1]}" onchange="checkBoxChange()"/></td>
				<td>${statusAndFreeAction[0]}</td>
				<td>${statusAndFreeAction[1]}</td>
				<td>
				<input type="text" id="${statusAndFreeAction[0]}:${statusAndFreeAction[1]}" name="${statusAndFreeAction[0]}:${statusAndFreeAction[1]}" value=""/>
				</td>
				<td>
				<input type="button" value="预览" onclick="preview('${baseSchema}', '${statusAndFreeAction[0]}:${statusAndFreeAction[1]}')"/>
				</td>
			</tr>
		</dg:gridrow>
	</dg:datagrid>
	<div class="add_bottom">
					<input type="button" value="动作部署" class="save_button" onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="actionFormSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
					<input type="button" value="test" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="javascript:alert(selectedCheckBox);" />
	</div>
	<s:form action="/ultrabpp/deployAction.action" theme="simple" name="baseForm" id="baseForm" method="post" target="_self">
		<input type="hidden" id="baseSchema" name="baseSchema" value="${baseSchema}"/>
		<input type="hidden" id="actionType" name="actionType"/>
	</s:form>
	</div>
</body>
</html>
