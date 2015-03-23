<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${ctx}/common/style/blue/css/main.css" rel="stylesheet"
			type="text/css" />
		<link href="${ctx}/common/style/blue/css/detail.css" rel="stylesheet"
			type="text/css" />
		<title>新增常识</title>
		<script src="${ctx}/common/javascript/main.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/validate.js"></script>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,53);
			}
			window.onload = function() {
				setCenter(0,53);
			}
		</script>
	</head>
	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">新增常识</span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<form id="form" action="saveDutyKnowledge.action" method="post" onsubmit="return Validator.Validate(this,1);">
				<div class="add_scroll_div4" id="center">
					<fieldset class="fieldset_style">
						<legend>
							常识信息
						</legend>
						<table class="add_table">
							<tr>
								<td class="add_table_left">
									标题：
								</td>
								<td class="add_table_right">
									<input type="hidden" name="dgkInfo.creater" value="${userId }"/>
									<input type="text" id="title" name="dgkInfo.title" class="textInput" maxlength="60"/><span class="must">*</span>
									<validation id="dgkInfo.titleV" dataType="Require" msg="标题不能为空！" />
								</td>
							</tr>
							<tr>
								<td class="add_table_left">
									内容：
								</td>
								<td class="add_table_right">
									<textarea id="contents" name="dgkInfo.contents" cols="80" rows="10" style="width: 96%"></textarea><span class="must">*</span>
									<validation id="dgkInfo.contentsV" dataType="Limit" min="1" max="250" msg="内容不能为空并且不能超过250个字符！" />  
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
				<div class="add_bottom">
					<input type="submit" name="save_button" value="<eoms:lable name='com_btn_save'/>"
						class="save_button"
						onmouseover="this.className='save_button_hover'"
						onmouseout="this.className='save_button'"/>
					<input type="reset" name="button5" id="button5" value="<eoms:lable name='com_btn_close'/>"
						class="cancel_button"
						onmouseover="this.className='cancel_button_hover'"
						onmouseout="this.className='cancel_button'"
						onclick="window.close();" />
				</div>
			</form>
		</div>
	</body>
</html>