<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script language="javascript"><!--
			window.onresize = function()
			{
				setCenter(0,30);
			}
			window.onload = function()
			{
				setCenter(0,30);
			}
			
			function saveOrgLevel()
			{
				var parentid = $("#parentid").val();
				var downorgid = $("#downorgid").val();
				if (downorgid == "") {
					alert("下级岗位不能为空");
					return false;
				}
				$.ajax({
					url: 'checkOrgLevelManage.action',
					type: 'POST',
					dataType: 'text',
					data:'dol.parentid='+parentid+'&downorgids='+downorgid,
					success: function(message){
						if (message == '0') {
							var form = $("#form");
							form.submit();
					    } else {
					    	alert("岗位上下级关系有冲突，请重新选择下级岗位");
					    	return false;
					    }
					}
				});
			}
			//部室
			function editGroup(id,name,type){
				window.open('${ctx}/common/tools/depOrUserSelect.jsp?isRadio='+type+'&isSelectType=0&input_name='+name+'&input_id='+id,'','location=no,toolbar=no,resizable=yes,scrollbars=yes,width=400,height=400,top=100,left=300');
			}
			//选择下级岗位
			function getOrgTree(inputid, inputname) {
				//par 岗位状态(0：停用，1：启用，2:全部),部室ID,是否包含本岗位(1,是;0,否),本岗位ID
				var par = "2,";
				if ($("#belongDep").val() == "") {
					par = par + "null,0,";
				} else {
					par = par + $("#belongDep").val()+",0,";
				}
				if ($("#befororgid").val() == "") {
					par = par + "null";
				} else {
					par = par + $("#befororgid").val();
				}
				var ids = $("#"+inputid).val();
				var values = $("#"+inputname).val();
				var targetDataArr = "";
				if(values != ""){
				    var  value = values.split(",");
				    var  id = ids.split(",");
				    $.each(id, function(i, n){//循环班次
		                targetDataArr = targetDataArr + id[i]+","+value[i]+";";
		            });
				  }
				window.open("${ctx}/common/getParaSelectTree.action?multiple=2&sourceDataObj=orgMap&par="+par+"&input_id="+inputid+"&input_name="+inputname+"&targetDataArr="+targetDataArr,"","width=700,height=400,top=200,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
			}
		</script>
	</head>
	<body>
		<form id="form" action="saveOrgLevelManage.action" method="post">
		<div class="content">
			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend>岗位上下级关系设置</legend>
					<div class="blank_tr"></div>
					<table class="add_user">
						<tr>
							<td class="texttd" style="width:25%">上级岗位名称：</td>
							<td style="width:30%">
								<input type="hidden" id="parentid" name="dol.parentid" <c:if test="${isChild == '0'}">value="${dol.parentid }"</c:if><c:if test="${isChild == '1'}">value="${dol.pid }"</c:if>/>
								<input type="hidden" id="befororgid" name="dol.befororgid" value="${dutyOrg.pid }"/>
								<input type="text" id="orgName" name="dutyOrg.organizationname" readonly="readonly"
									class="textInput" value="${dutyOrg.organizationname }" />
							</td>
							<td class="texttd" style="width:25%">下级岗位名称：</td>
							<td style="width:30%" colspan="3">
								<input type="hidden" id="downorgid" name="downorgids"/>
								<input type="text" id="downorgName" name="downorgName" readonly="readonly"
									class="textInput"  onclick="getOrgTree('downorgid','downorgName');"/>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" name="save_button" value="<eoms:lable name='com_btn_save'/>"
					class="save_button"
					onmouseover="this.className='save_button_hover'"
					onmouseout="this.className='save_button'" onclick="saveOrgLevel();" />
			<input type="button" name="button" id="button" value="<eoms:lable name='com_btn_close'/>"
					class="cancel_button"
					onmouseover="this.className='cancel_button_hover'"
					onmouseout="this.className='cancel_button'"
					onclick="window.close();" />
		</div>
		</form>
	</body>
</html>
