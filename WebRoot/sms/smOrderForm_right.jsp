<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,30);
			}
			window.onload = function() 
			{
				setCenter(0,30);
				var condition_wftype = "${param.wftype}";
				if(condition_wftype!="undefined"&&condition_wftype!="")
				{
					var saveResult = "${saveResult}";
					if(saveResult=="success")
					{
						alert("<eoms:lable name='com_msg_saveSuccess'/>！");
					}
					else if(saveResult=="failure")
					{
						alert("<eoms:lable name='com_msg_saveErr'/>！");
					}
					var actionStr = document.getElementById("actionStr").value;
					if(actionStr!="")
					{
						var actionCbx = document.getElementsByName("formAction");
						for(var i=0;i<actionCbx.length;i++)
						{
							if(actionStr.search(actionCbx[i].value)!=-1)
							{
								actionCbx[i].checked = true;
							}
						}
					}
					var formSchema = "${formSchema}";
					var pageNum1 = 1;
					var displayFlag1 = false;
					while(pageNum1!=-1){
						$.ajax({
							url:"${ctx}/smOrderForm/getSmsBaseItem.action?rnd="+(new Date()).getTime(),
							async:false,
							data:"schema="+formSchema+"&pageNum="+pageNum1,
							dataType:"json",
							success:function(data){
								if(data==null||data==undefined){
									pageNum1 = -1;
								}
								else{
									len = data.length;
									if(len>0)
										displayFlag1 = true;
									if(len==50)
										pageNum1 += 1;
									else
										pageNum1 = -1;
									var seloptions = document.getElementById("selItemed").options;//selItemed selItemAll
									for(var i=0;i<len;i++){
										seloptions.add(new Option(data[i].item,data[i].item));
									}
								}
							}
						});
					}
					var pageNum2 = 1;
					var displayFlag2 = false;
					while(pageNum2!=-1){
						$.ajax({
							url:"${ctx}/smOrderForm/getSmsBaseItemOfSchema.action?rnd="+(new Date()).getTime(),
							async:false,
							data:"schema="+formSchema+"&pageNum="+pageNum2,
							dataType:"json",
							success:function(data){
								if(data==null||data==undefined){
									pageNum2 = -1;
								}
								else{
									len = data.length;
									if(len>0)
										displayFlag2 = true;
									if(len==50)
										pageNum2 += 1;
									else
										pageNum2 = -1;
									var seloptions = document.getElementById("selItemAll").options;
									for(var i=0;i<len;i++){
										seloptions.add(new Option(data[i].item,data[i].item));
									}
								}
							}
						});
					}
					if(displayFlag1==true||displayFlag2==true){
						document.getElementById("fieldset_itemSelect").style.display = 'block';
					}
					if(formSchema=="Falt_Form")//Falt_Form根据字典值修改；formOrder根据模板标识修改
					{//如果是故障管理工单
						$.getJSON("${ctx}/smOrderForm/getRulePropertyInfo.action?rnd="+(new Date()).getTime(),{formSchema:formSchema,modelMark:'formOrder'},function(ruleProStr)
						{
							//alert(ruleProStr.length);
							$.each(ruleProStr,function(i)
							{
								var tempRow = $("<tr></tr>").appendTo("#rulePro");
								$("<td style=\"width:15%\">"+ruleProStr[i].fieldName+"：<input type=\"hidden\" name=\"rpid"+i+"\" id=\"rpid"+i+"\" value=\""+ruleProStr[i].rpid+"\"/></td>").appendTo(tempRow);
								if(ruleProStr[i].inputType=="2")
								{
									var tempTdStr = "<td>";
									tempTdStr += "<input type=\"text\" name=\"displayValue"+i+"\" id=\"displayValue"+i+"\" value=\""+ruleProStr[i].displayValue+"\" readonly=\"readonly\" class=\"textInput\"/>";
									tempTdStr += "<input type=\"hidden\" name=\"value"+i+"\" id=\"value"+i+"\" value=\""+ruleProStr[i].value+"\"/>";
									tempTdStr += "</td>";
									$(tempTdStr).appendTo(tempRow);
									tempTdStr = "<td style=\"width:20%\">";
									tempTdStr += "<input type=\"button\" value=\"<eoms:lable name='com_btn_choose'/>\" class=\"operate_button\" onmouseover=\"this.className='operate_button_hover'\" onmouseout=\"this.className='operate_button'\"";
									tempTdStr += "onclick=\"chooseValue('displayValue"+i+"','value"+i+"','"+ruleProStr[i].inputDataSourceType+"','"+ruleProStr[i].rpid+"','ruleForm');\"/>";
									tempTdStr += "<input type=\"button\" value=\"<eoms:lable name='com_btn_clear'/>\" class=\"operate_button\" onmouseover=\"this.className='operate_button_hover'\" onmouseout=\"this.className='operate_button'\"";
									tempTdStr += "onclick=\"document.getElementById('value"+i+"').value='';document.getElementById('displayValue"+i+"').value='';\"/>";
									$(tempTdStr).appendTo(tempRow);
								}
								else
								{
									var tempTdStr = "<td>";
									if(ruleProStr[i].inputValueType=="2")//时间型
									{
										tempTdStr += "<input readonly=\"readonly\" type=\"text\" name=\"displayValue"+i+"\" id=\"displayValue"+i+"\" value=\""+ruleProStr[i].displayValue+"\" class=\"textInput\" style=\"width:100%\"";
										tempTdStr += "onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true});this.className='focus'\"";
										
									}
									else
									{
										tempTdStr += "<input type=\"text\" name=\"displayValue"+i+"\" id=\"displayValue"+i+"\" value=\""+ruleProStr[i].displayValue+"\" class=\"textInput\" style=\"width:100%\"";
									}
									tempTdStr += "onchange=\"document.getElementById('value"+i+"').value=document.getElementById('displayValue"+i+"').value;\"/>";
									
									tempTdStr += "<input type=\"hidden\" name=\"value"+i+"\" id=\"value"+i+"\" value=\""+ruleProStr[i].value+"\"/>";
									tempTdStr += "</td>";
									$(tempTdStr).appendTo(tempRow);
									tempTdStr = "<td style=\"width:20%\"></td>";
									$(tempTdStr).appendTo(tempRow);
								}
							});
							document.getElementById("ruleDiv").style.display = "block";
						});
					}
				}
			}
			function chooseValue(input_name,input_id,dsType,rpid,formName) //rpid:规则属性ID
			{
				//alert("input_name:"+input_name+"\n"+"input_id:"+input_id+"\ndsType:"+dsType+"\nrpid:"+rpid+"\nformname:"+formName);
				if(dsType == '1' || dsType == '2' || dsType == '3')
				{//系统内部变量、字典、脚本
					openwindow('${ctx}/slaActionManager/getValue.action?type='+dsType+'&rpid='+rpid+'&input_name='+input_name+'&input_id='+input_id,'',700,450);
				}
				else if(dsType=="4")
				{//人员树
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?isRadio=1&isSelectType=1&input_name='+input_name+'&input_id='+input_id,'',350,400);
				}
				else if(dsType=="5")
				{//部门树
					openwindow('${ctx}/common/tools/depOrUserSelect.jsp?isRadio=1&isSelectType=0&input_name='+input_name+'&input_id='+input_id,'',350,400);
				}
			}
			function createSlaRuleProStr()
			{
				var tab = document.getElementById("rulePro");
				var slaRuleProStr = "";
				var len = tab.rows.length;
				for(var i=0;i<len;i++)
				{
					var temp = "";
					temp += document.getElementById('rpid'+i).value+"&comm;"; //属性ID
					temp += document.getElementById('value'+i).value+"&comm;"; //值
					temp += document.getElementById('displayValue'+i).value+"&comm;"; //显示值
					temp += "1"; //操作符
					slaRuleProStr += temp+"&semi;";
				}
				if(slaRuleProStr != "")
				{
					slaRuleProStr = slaRuleProStr.substring(0,slaRuleProStr.lastIndexOf("&semi;"));
				}
				document.getElementById("slaRuleProStr").value = slaRuleProStr;
			}
			function rightChoose(){
				var lsel = document.getElementById("selItemAll");
				var rsel = document.getElementById("selItemed");
				var len = lsel.length;
				for(var i=0;i<len;i++){
					if(lsel.options[i].selected==true){
						rsel.options.add(new Option(lsel.options[i].text,lsel.options[i].value));
						lsel.options.remove(i);
						len--;
						i--;
					}
				}
			}
			function rightChooseAll(){
				var lsel = document.getElementById("selItemAll");
				var rsel = document.getElementById("selItemed");
				var len = lsel.length;
				for(var i=0;i<len;i++){
					rsel.options.add(new Option(lsel.options[i].text,lsel.options[i].value));
				}
				lsel.options.length = 0;
			}
			function leftChoose(){
				var rsel = document.getElementById("selItemAll");
				var lsel = document.getElementById("selItemed");
				var len = lsel.length;
				for(var i=0;i<len;i++){
					if(lsel.options[i].selected==true){
						rsel.options.add(new Option(lsel.options[i].text,lsel.options[i].value));
						lsel.options.remove(i);
						len--;
						i--;
					}
				}
			}
			function leftChooseAll(){
				var rsel = document.getElementById("selItemAll");
				var lsel = document.getElementById("selItemed");
				var len = lsel.length;
				for(var i=0;i<len;i++){
					rsel.options.add(new Option(lsel.options[i].text,lsel.options[i].value));
				}
				lsel.options.length = 0;
			}
			function mergeSel(){
				var tempSel = document.getElementById("selItemSearch");
				var lsel = document.getElementById("selItemAll");
				var len = tempSel.options.length;
				for(var i=0;i<len;i++){
					lsel.options.add(new Option(tempSel.options[i].text,tempSel.options[i].value));
				}
				tempSel.options.length = 0;
			}
			function searchSel(){
				var searchValue = document.getElementById("researchTxt").value;
				if(searchValue=='快速查找...'||searchValue=='')
					return;
				var reg = new RegExp(".*"+searchValue+".*");
				var tempSel = document.getElementById("selItemSearch");
				var lsel = document.getElementById("selItemAll");
				var len1 = lsel.options.length;
				var len2 = tempSel.options.length;
				for(var i=0;i<len1;i++){
					if(reg.test(lsel.options[i].value)==false){
						tempSel.options.add(new Option(lsel.options[i].text,lsel.options[i].value));
						lsel.options.remove(i);
						len1--;
						i--;
					}
				}
				for(var i=0;i<len2;i++){
					if(reg.test(tempSel.options[i].value)==true){
						lsel.options.add(new Option(tempSel.options[i].text,tempSel.options[i].value));
						tempSel.options.remove(i);
						len2--;
						i--;
					}
				}
			}
			function createItemSelStr(){
				var sel = document.getElementById("selItemed").options;
				var len = sel.length;
				var str = '';
				for(var i=0;i<len;i++){
					if(i!=0)
						str += "&comm;";
					str += sel[i].value;
				}
				document.getElementById("selectedItem").value = str;
			}
			function selectAllAction(source){
				var checked = source.checked;
				$("input[name='formAction']").attr("checked", checked);
			}
			function formSubmit()
			{
				var startTime = document.getElementById("startTime").value;
				var endTime = document.getElementById("endTime").value;
				if(startTime=="" || endTime=="")
				{
					alert("<eoms:lable name='sm_msg_chooseStartTimeEndTimeTip'/>！");
					return;
				}
				var userMobile = "${userMobile}";
				if(userMobile=="")
				{
					alert("<eoms:lable name='sm_msg_smOrderFormNullMobileTip'/>！");
					return;
				}
				var actionCbx = document.getElementsByName("formAction");
				var actionStr = "";
				for(var i=0;i<actionCbx.length;i++)
				{
					if(actionCbx[i].checked==true)
					{
						actionStr += actionCbx[i].value + "&comm;";
					}
				}
				if(actionStr!="")
				{
					actionStr = actionStr.substring(0,actionStr.lastIndexOf("&comm;"));
				}
				document.getElementById("actionStr").value = actionStr;
				if("${formSchema}"=="Falt_Form")//如果对应工单类别字典值改变，则判断条件也要变
				{
					createSlaRuleProStr();
				}
				createItemSelStr();//专业选择
				document.forms[0].submit();
			}
		</script>
	</head>
	<body>
	   <div class="content">
			<div class="page_div_bg">
				<c:if test="${param.wftype!='undefined'&&param.wftype !=null&&param.wftype !=''}">
				<div class="page_div">
					<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
				  			onmouseout="this.className='page_add_button'" 
				  			onclick="formSubmit();"
				  			text="com_btn_save"/>
		 	 	</div>
		 	 	</c:if>
			</div>
			<div id="center" class="add_scroll_div_x">
				<c:if test="${param.wftype!='undefined'&&param.wftype !=null&&param.wftype !=''}">
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style" style="width:90%">
					<legend>
						<eoms:lable name="sm_lb_formAction"/>
						<span style="font-weight:normal;">
							【<input type="checkbox" name="formAction_all" onclick="selectAllAction(this)"/>全选】
						</span>
					</legend>
					<div class="blank_tr"></div>
					<table align="center" style="width:80%;font-weight:normal;">
						<tr>
						<c:forEach items="${allFormAction}" var="action" varStatus="sta">
							<td><input type="checkbox" name="formAction" value="${action.divalue}"/>${action.diname}</td>
							<c:if test="${sta.count%10==0 && !sta.last}">
								<tr/>
								<tr>
							</c:if>
							<c:if test="${sta.last && sta.count%10!=0}">
								<tr/>
							</c:if>
						</c:forEach>
					</table>
					<div class="blank_tr"></div>
				</fieldset>
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style" style="width:90%">
					<legend><eoms:lable name="sm_lb_basicAttribute"/></legend>
					<div class="blank_tr"></div>
						<form action="${ctx}/smOrderForm/smOrderFormSave.action" method="post">
							<input type="hidden" name="actionStr" id="actionStr" value="${actionStr}"/>
							<input type="hidden" name="formSchema" id="formSchema" value="${formSchema}"/>
							<input type="hidden" name="slaRuleProStr" id="slaRuleProStr" value=""/><!-- 当为故障管理工单的时候，记录规则属性字符串传到后台 -->
							<input type="hidden" name="selectedItem" id="selectedItem" value=""/><!-- 保存所选择的专业 -->
							<table align="center" style="width:85%;font-weight:normal;">
								<tr>
									<td width="15%"><eoms:lable name="sm_lb_reciverstarttime"/>：<span class="must">*</span></td>
									<td width="35%">
										<input type="text" name="startTime" id="startTime" maxlength="8"
											onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});this.className='focus'"
											readonly="readonly" style='width:95%' value="${startTime}" />
									</td>
									<td width="15%" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<eoms:lable name="sm_lb_sendendtime"/>：<span class="must">*</td>
									<td width="35%">
										<input type="text" name="endTime" id="endTime" maxlength="8"
											onclick="WdatePicker({dateFmt:'HH:mm',autoPickDate:true});this.className='focus'"
											readonly="readonly" style='width:95%' value="${endTime}" />
									</td>
								</tr>
								<tr>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;<eoms:lable name="sm_lb_isholidayactive"/>：</td>
									<td>
										<eoms:select name="isHoliday" style="select" dataDicTypeCode="isHoliday" value="${isHoliday}" isnull="false"/>
									</td>
									<td align="center"><eoms:lable name="com_lb_status"/>：</td>
									<td>
										<eoms:select name="status" style="select" dataDicTypeCode="status" value="${status}" isnull="false"/>
									</td>
								</tr>
							</table>
						</form>
					<div class="blank_tr"></div>
				</fieldset>
				<div class="blank_tr"></div>
				<div class="blank_tr"></div>
				<fieldset class="fieldset_style" style="width:90%;display:none;" id="fieldset_itemSelect">
					<legend>专业选择</legend>
					<div class="blank_tr"></div>
					<table>
						<tr>
							<td>
								<div class="configroletree">
								  <li><input type="text" id="researchTxt" maxlength="20" style="width:200px;height:17px;" value="快速查找..." onfocus="if (value =='快速查找...'){value =''}" onblur="if (value ==''){value='快速查找...';mergeSel();}"></li>
								  <li><div class="ser1" onmouseover="this.className='ser2'" onmouseout="this.className='ser1'" onclick="searchSel();"></div></li>
								</div>
							</td>
							<td></td>
							<td style="font-weight:normal;">已选专业：</td>
						</tr>
						<tr>
							<td>
								<select id="selItemSearch" size="30" style="display:none"><!-- 用以查找，中间存储 -->
								</select>
								<select id="selItemAll" size="25" style="width:229px;" multiple="multiple" ondblclick="rightChoose();">
								</select>
							</td>
							<td>
								<input type="button" value=" 右选> " style="width:80px;" onclick="rightChoose();"/><br/>
								<input type="button" value=" 全右选>> " style="width:80px;" onclick="rightChooseAll();"/><br/>
								<input type="button" value=" &lt;左选 " style="width:80px;" onclick="leftChoose();"/><br/>
								<input type="button" value=" &lt;&lt;全左选 " style="width:80px;" onclick="leftChooseAll();"/>
							</td>
							<td>
								<select id="selItemed" size="25" style="width:229px;" multiple="multiple" ondblclick="leftChoose();">
								</select>
							</td>
						</tr>
					</table>
					<div class="blank_tr"></div>
				</fieldset>
				<div id="ruleDiv" style="display:none">
					<div class="blank_tr"></div>
					<div class="blank_tr"></div>
					<fieldset class="fieldset_style" style="width:90%">
						<legend><eoms:lable name="sm_lb_advancedAttribute"/></legend>
						<div class="blank_tr"></div>
						<table class="add_user" style="width:85%" align="center" id="rulePro">
							<!--  
							<c:forEach items="${slaRuleProLst}" var="srps" varStatus="sta">
								<tr>
									<td style="width:15%">
										${srps.fieldname}：
										<input type="hidden" name="rpid${sta.index }" id="rpid${sta.index }" value="${srps.rpid}"/>
									</td>
									<c:choose>
										<c:when test="${srps.inputtype==2}">
											<td>
												<input type="text" name="displayValue${sta.index }" id="displayValue${sta.index }" value="${srps.slaRuleProperty.displayvalue}" readonly="readonly" class="textInput"/>
												<input type="hidden" name="value${sta.index }" id="value${sta.index }" value="${srps.slaRuleProperty.value}"/>
											</td>
											<td style="width:20%">
												<input type="button" name="button3" id="button3"
													value="<eoms:lable name="com_btn_choose"/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'"
													onclick="chooseValue('displayValue${sta.index }','value${sta.index }','${srps.inputdatasourcetype }','${srps.rpid }','ruleForm');" />
												<input type="button" name="button3" id="button3"
													value="<eoms:lable name="com_btn_clear"/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'"
													onclick="document.getElementById('value${sta.index }').value='';document.getElementById('displayValue${sta.index }').value='';" />
											</td>
										</c:when>
										<c:otherwise>
											<td>
												<input type="text" name="displayValue${sta.index }" id="displayValue${sta.index }" value="${srps.slaRuleProperty.displayvalue}" class="textInput"
												 onchange="document.getElementById('value${sta.index }').value=document.getElementById('displayValue${sta.index }').value;"/>
												<input type="hidden" name="value${sta.index }" id="value${sta.index }" value="${srps.slaRuleProperty.value}"/>
											</td>
											<td style="width:20%"></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
							-->
						</table>
						<div class="blank_tr"></div>
					</fieldset>
				<div>
				</c:if>
			</div>
	   </div>
	</body>
</html>
