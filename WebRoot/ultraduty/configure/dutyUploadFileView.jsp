<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<%@ include file="/common/plugin/swfupload/import.jsp"%>
		<title>
			值班管理重保日值班附件信息
		</title>
		<link href="${ctx}/common/style/blue/css/main.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript"
			src="${ctx}/ultrarepository/js/repository.js"></script>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,30);
			}
			window.onload = function() {
				setCenter(0,30);
			}
		</script>
		<style>
.add_table_left {
	text-align: left;
	width: 15%;
	padding-left: 10px;
}

.add_table_right {
	width: 35%;
}
</style>
	</head>
	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> 
						<span class="title_icon2">
								重保日信息查看
						</span>
					 </span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div id="center">
				<form>
					<div style="margin-top: 3px;">
						<fieldset class="fieldset_style">
							<legend>
								<eoms:lable name='sm_lb_attachment' />
							</legend>
							<table class="add_table">
								<tr>
									<td class="add_table_left">
										<eoms:lable name='sm_lb_attachmentUpload' />
										：
										<!-- 上传附件 -->
									</td>
									<td width="85%" align="left">
										<atta:fileupload id="myfileupload" uploadBtnIsVisible="false"
											fileTypes="*.*" downable="true" progressIsVisible="false"
											isEdit="false" uploadable="false"
											attchmentGroupId="88888888888888"
											uploadDestination="ultrarepository" uploadTableVisible="false"
											isMultiDownload="true"
											flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf"></atta:fileupload>
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
			</div>
			</form>
		</div>
		</div>
	</body>
</html>