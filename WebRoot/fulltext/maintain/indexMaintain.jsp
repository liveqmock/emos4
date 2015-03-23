<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="ftr_title_ftrMaintain"/></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,0);
			}
			window.onload = function() 
			{
				setCenter(0,0);
				getPageMenu('div1_1','div1');
				PageMenuActive('div1_1','div1');
				changeRow_color("paramTb");
			}
			function initSys()
			{
				if(confirm("确定初始化索引系统吗?"))
				{
					document.getElementById("initDiv").style.display = "block";
					$.get("${ctx}/indexManager/initSystem.action?"+(new Date()),{},function(result)
					{
						if("true"==result)
						{
							document.getElementById("initDiv").style.display = "none";
							alert("初始化成功！");
							window.location.reload();
						}
						else
						{
							document.getElementById("initDiv").style.display = "none";
							alert("初始化失败！");
							window.location.reload();
						}
					});
				}
			}
			function checkAllCa(source)
			{
				var caArr = document.getElementsByName("cacheck");
				for(var i=0;i<caArr.length;i++)
				{
					caArr[i].checked = source.checked;
				}
			}
			function maintainSubmit()
			{
				var caArr = document.getElementsByName("cacheck");
				var str = "";
				for(var i=0;i<caArr.length;i++)
				{
					if(caArr[i].checked==true)
					{
						str += "&comm;"+caArr[i].value;
					}
				}
				if(str=="")
				{
					alert("请选择需要维护的索引类别！");
					return;
				}
				document.getElementById("maintainCaIds").value = str.substring("&comm;".length);
				document.forms[0].submit();
			}
		</script>
	</head>

	<body>
		<div class="content">
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div class="tab_show" id="div1_1"
							onclick="PageMenuActive('div1_1','div1')">
							<span>参数配置信息</span>
						</div>
						<div class="tab_hide" id="div1_2"
							onclick="PageMenuActive('div1_2','div2')">
							<span>配置初始化</span>
						</div>
						<div class="tab_hide" id="div1_3"
							onclick="PageMenuActive('div1_3','div3')">
							<span>维护方式</span>
						</div>
					</div>
					<div class="tabContent">
						<div id="div1">
							<table class="tableborder" id="paramTb">
								<tr>
									<th width="20%">参数名称</th><th width="25%">参数值</th><th>描述信息</th>
								</tr>
								<c:forEach items="${paramInfo}" var="var">
									<tr>
										<td>${var.key }</td>
										<td>${var.value }</td>
										<td>${var.desc }</td>
									</tr>
								</c:forEach>
							</table>
						</div>
						<div id="div2" style="display: none">
							<div class="blank_tr"></div>
							<input type="button" value="初始化索引系统" onclick="initSys();"/>
							<div id="initDiv" style="display:none">
								<table>
									<tr>
										<td style="font-weight:normal"  valign="middle">
											正在初始化，请稍后...
										</td>
										<td>
											<img border="0" src="${ctx}/fulltext/images/init.gif" width="30" height="30"/>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div id="div3" style="display: none">
							<div class="blank_tr"></div>
							<c:if test="${mainThreadStatus==true}">
								<span style="font-weight:normal;">索引维护线程状态：正在运行...</span>
							</c:if>
							<c:if test="${mainThreadStatus==false}">
								<span style="font-weight:normal;">索引维护线程状态：已停止!</span>
							</c:if>
						     <hr />
						     <form action="${ctx}/indexManager/maintain.action" method="post">
						     	<input type="hidden" name="maintainCaIds" id="maintainCaIds"/>
						     	<table class="tableborder" style="width:900px;">
						     		<tr>
						     			<td width="15%">维护方式：</td>
						     			<td width="85%">
						     				<input type="radio" name="method" value="manual" checked/>手动维护
						     				<input type="radio" name="method" value="auto_cycle"/>启动索引维护线程
						     				<input type="radio" name="method" value="stop_auto_cycle"/>停止索引维护线程
						     			</td>
						     		</tr>
						     		<tr>
						     			<td>选择类别：</td>
						     			<td>
						     				<table cellspacing="0" cellpadding="0">
							     				<tr>
								     				<td style="border:0">
								     					<input type="checkbox" value="" id="cacheck_all" onclick="checkAllCa(this)" checked="checked"/>全部
								     				</td>
								     				<c:forEach items="${phyCategory}" var="ca" varStatus="sta">
								     					<c:if test="${sta.count%5==0}">
								     						<tr/>
								     					</c:if>
								     					<td style="border:0">
								     						<input type="checkbox" name="cacheck" value="${ca.key}" checked="checked"/>${ca.value}
								     					</td>
								     				</c:forEach>
							     				</tr>
						     				</table>
						     			</td>
						     		</tr>
						     		<tr>
						     			<td>开始日期：</td>
						     			<td>
						     				<input type="text" name="startTime" style="width:180px" readonly
						     					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true});"/>
						     				<span class="must">*只对索引增量起作用</span>
						     			</td>
						     		</tr>
						     		<tr>
						     			<td>结束日期：</td>
						     			<td>
						     				<input type="text" name="endTime" style="width:180px" readonly
						     					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true});"/>
						     			</td>
						     		</tr>
						     		<tr>
						     			<td colspan="2" align="center">
						     				<c:if test="${completeMaintain==true}">
							     				<input type="button" value="提交维护方式" onclick="maintainSubmit();"/>
							     			</c:if>
							     			<c:if test="${completeMaintain==false}">
							     				<input type="button" value="维护中..." disabled="disabled"/>
							     			</c:if>
						     			</td>
						     		</tr>
						     	</table>
						     </form>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
	</body>
</html>
