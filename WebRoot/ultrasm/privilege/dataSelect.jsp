<%@ page language="java" import="java.util.List;" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="sm_lb_dataInfoChoose"/></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script> 
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0,55);
		}
		window.onload = function() 
		{
			setCenter(0,55);
		}
		function submit()
		{
			var cbxArr = $(":checked");
			if(cbxArr.length==0)
			{
				alert('<eoms:lable name="sm_msg_pleaseChooseDataInfo"/>！');
				return;
			}
			var names = '';
			var values = '';
			for(var i=0;i<cbxArr.length;i++)
			{
				var temp = cbxArr[i].value.split(';');
				values = values + temp[0] + ',';
				names = names + temp[1] + ',';
			}
			values = values.substring(0,values.lastIndexOf(','));
			names = names.substring(0,names.lastIndexOf(','));
			window.opener.document.getElementById('${input_id}').value = values;
			window.opener.document.getElementById('${input_name}').value = names;
			window.close();
		}
	</script>
	</head>

	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="sm_lb_dataInfoChoose"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="sm_lb_allDataInfo"/></legend>
					<div class="blank_tr"></div>
					<div style="font-weight:normal" id="dataBox">
						<c:if test="${dataList!=null&&fn:length(dataList)>0}">
							<table width="100%">
								<c:forEach begin="0" end="${fn:length(dataList) - 1}" step="3" varStatus="staout">
									<tr>
										<c:forEach items="${dataList}" begin="${staout.index}" end="${staout.index + 2}" var="data">
											<td><input type="checkbox" name="cbx" value="${data.id};${data.value}"/>${data.value}</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						<c:if test="${dataList==null || fn:length(dataList)==0}">
							<table align="center"><tr><td><eoms:lable name="com_lb_noData"/>！</td></tr></table>
						</c:if>
					</div>
					<div class="blank_tr"></div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="submit();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</body>
</html>
