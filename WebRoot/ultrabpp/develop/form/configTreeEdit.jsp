<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<%@ include file="/common/core/taglibs.jsp"%>
   	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
   	<title><eoms:lable name="sm_lb_cfgSelfSendTree"/></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,90);
		}
		window.onload = function() 
		{
			setCenter(0,90);
		}
		function submit()
		{
			var configID = "${configID}";
			var wftype = "${wftype}";
			if(configID=="")
			{
				var obj = document.getElementById("stepList");
				var actionObj = document.getElementById("actionList");
				var fieldObj = document.getElementById("fieldList");
				var selectObj = document.getElementById("selectTypeList");
				var tabShow = document.getElementById("tabShowList");
				if(!(obj.selectedIndex>0&&actionObj.selectedIndex>0&&fieldObj.selectedIndex>0)&&wftype=="1"){
					alert("请选择对应环节，动作，派发树！");
					return false;
				}
				
				if(fieldObj.selectedIndex<=0&&wftype=="0"){
					alert("请选择对应派发树！");
					return false;
				}
				if(wftype=="1"){
					document.getElementById("stepNo").value = obj.options[obj.selectedIndex].value;
					document.getElementById("stepDesc").value = obj.options[obj.selectedIndex].text;
					document.getElementById("actionName").value = actionObj.options[actionObj.selectedIndex].value;
					document.getElementById("actionLabel").value = actionObj.options[actionObj.selectedIndex].text;
				}
				document.getElementById("fieldName").value = fieldObj.options[fieldObj.selectedIndex].value;
				document.getElementById("fieldLabel").value = fieldObj.options[fieldObj.selectedIndex].text;
				document.getElementById("selectType").value = selectObj.options[selectObj.selectedIndex].value;
				document.getElementById("tabShow").value = tabShow.options[tabShow.selectedIndex].value;
			}
			document.getElementById("tree").contentWindow.getDepAndUser();
			var sendTreeInfo = document.getElementById("tree").contentWindow.returnStr;
			if(sendTreeInfo!="")
			{
				var realInfo = "";
				var tempArr = sendTreeInfo.split(";");
				for(var i=0;i<tempArr.length;i++)
				{
					var temp = tempArr[i].split(",");
					realInfo += temp[0]+":"+temp[1]+":"+temp[3]+";";
				}
				if(realInfo!="")
				{
					realInfo = realInfo.substring(0,realInfo.lastIndexOf(";"));
					document.getElementById("organizeInfo").value = realInfo;
					var stepNo =  document.getElementById("stepNo").value;
					var actionName =  document.getElementById("actionName").value;
					var fieldName =  document.getElementById("fieldName").value;
					var configID =  document.getElementById("configID").value;
					var baseSchema =  document.getElementById("baseSchema").value;
					$.get("${ctx}/ultrabpp/assigntree/checkUniqueConfig.action?stamp="+new Date().getTime(),{'fieldName':fieldName,'actionName':actionName,'stepNo':stepNo,'configID':configID,'baseSchema':baseSchema},function(result)
					{
						if(result == 'false'){
							alert('自定义派发树已经存在，请进行修改或者删除后重新添加。');
						}else{
							document.getElementById('form1').submit();  
						}
					});
					
				}else{
					 alert('没有选择部门人员。');
				}
			}
		}
		function selectStep(){
			var obj = document.getElementById("stepList");
			var stepNo = obj.options[obj.selectedIndex].value;
			if(obj.selectedIndex!=0)
			{
				$.get("${ctx}/ultrabpp/develop/getActionListByStepNo.action?stamp="+new Date().getTime(),{'baseSchema':'${baseSchema}','stepNo':stepNo},function(result)
						{
							var actionObj = document.getElementById("actionList");
							actionObj.outerHTML = result;
						}
				);
			}
		}
		
		function selectAction(){
			var obj = document.getElementById("stepList");
			var actionObj = document.getElementById("actionList");
			
			var stepNo = obj.options[obj.selectedIndex].value;
			var actionName = actionObj.options[actionObj.selectedIndex].value;
			if(actionObj.selectedIndex!=0){
				$.get("${ctx}/ultrabpp/develop/getFieldListByActionName.action?stamp="+new Date().getTime(),{'baseSchema':'${baseSchema}','stepNo':stepNo,'actionName':actionName},function(result)
						{
							var fieldObj = document.getElementById("fieldList");
							fieldObj.outerHTML = result;
						}
				);
			}
		}

	</script>
  </head>
  <body>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name="sm_lb_cfgSelfSendTree"/></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	    </div>
	    <div class='page_div_bg' id="selectDIV" style="display:none">
			<div class='page_div'>
				<li>
					<select name="stepList" id="stepList" ></select>
				</li>
				<li>
					<select name="actionList" id="actionList" ></select>
				</li>
				<li>
					<select name="fieldList" id="fieldList" ></select>
				</li>
				<li>
					<select name="selectTypeList" id="selectTypeList">
						<option value="2">部门，人员</option>
						<option value="0">部门</option>
						<option value="1">人员</option>
					</select>
				</li>
				<li>
					<select name="tabShowList" id="tabShowList">
						<option value="0">全部</option>
						<!-- <option value="1">只显示组织机构</option>-->
						<option value="2">只显示自定义派发数</option>
					</select>
				</li>
			</div>
		</div>
	    <div class='page_div_bg' id="labelDIV" style="display:none">
			<div class='page_div'>
				<li>
				环节：${stepDesc}
				</li>
				<li>
				动作：${actionLabel}
				</li>
				<li>
				派发树：${fieldLabel}
				</li>
				<li>
				选择类型:
				<c:if test="${selectType=='0'}">部门</c:if>
				<c:if test="${selectType=='1'}">人员</c:if>
				<c:if test="${selectType=='2'}">部门和人员</c:if>
				</li>
				<li>
				显示类型:
				<c:if test="${tabShow=='0'}">全部</c:if>
				<c:if test="${tabShow=='1'}">只显示组织机构</c:if>
				<c:if test="${tabShow=='2'}">只显示自定义派发数</c:if>
				</li>
			</div>
		</div>
	    <div style="overflow:hidden;" id="center" >
	    	<iframe src="${ctx}/common/tools/customOrganiseTree.jsp?isRadio=1&isSelectChild=1" scrolling="no" id='tree' frameborder="0" style="width:100%;height:100%;"></iframe>
	    </div>
	    <div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="submit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
		<form action="saveAssignTreeConfig.action" name="form1" method="post" id="form1">
			<input type="hidden" id="configID" name="configID" value="${configID}"/>
			<input type="hidden" id="stepNo" name="stepNo" value="${stepNo}"/>
			<input type="hidden" id="stepDesc" name="stepDesc" value="${stepDesc}"/>
			<input type="hidden" id="actionName" name="actionName"  value="${actionName}"/>
			<input type="hidden" id="actionLabel" name="actionLabel" value="${actionLabel}"/>
			<input type="hidden" id="fieldName" name="fieldName" value="${fieldName}"/>
			<input type="hidden" id="fieldLabel" name="fieldLabel" value="${fieldLabel}"/>
			<input type="hidden" id="selectType" name="selectType" value="${selectType}"/>
			<input type="hidden" id="organizeInfo" name="organizeInfo"/>
			<input type="hidden" id="baseSchema" name="baseSchema" value="${baseSchema}"/>
			<input type="hidden" id="tabShow" name="tabShow" value="${tabShow}"/>
		</form>
	</div>	
  </body>
  <script  type="text/javascript">
  		init();
		function init(){
			var configID = "${configID}";
			var wftype = "${wftype}";
			if(configID=="")
			{
				document.getElementById("selectDIV").style.display = "";
				var configString = "${configOptions}";
				if(wftype == "1")
				{
					var obj = document.getElementById("stepList");
					obj.outerHTML = configString;
					obj.selectedIndex = 0;
				}else{
					var obj = document.getElementById("stepList");
					obj.style.display="none";
					var objAction = document.getElementById("actionList");
					objAction.style.display="none";
					var objField = document.getElementById("fieldList");
					objField.outerHTML = configString;
					objField.selectedIndex = 0;
				}
			}else
			{
				document.getElementById("labelDIV").style.display = "";
			}
		}
  </script>
</html>
