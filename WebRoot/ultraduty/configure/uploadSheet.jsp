<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<%@ include file="/common/core/taglibs.jsp"%>
		<%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<%@ include file="/common/plugin/swfupload/import.jsp"%>
		<title><eoms:lable name='repository_title_repositoryAdd' />
		</title>
		<link href="${ctx}/common/style/blue/css/main.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript"
			src="${ctx}/ultrarepository/js/repository.js"></script>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,55);
			}
			window.onload = function() {
				setCenter(0,55);
			}
		</script>
		<script language="javascript">
			
    		// 保存附件
    		function submitfrm(){
				myfileupload.afterUploadComplete = function(serverData)
				{
					alert('<eoms:lable name="repository_msg_fileUploadSuccess"/>');
				}
				myfileupload.startUploadFile();
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
					<span class="title_bg"> <span class="title_icon2"><c:if
								test="${urlFrom == 'addNew' }">
								<eoms:lable name='repository_title_currentPosition' />：</c:if>
							值班管理重保日附件上传
					</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div id="center">
				<form name="form" id="form" action="saveDutyAttachment.action"
					method="post">
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
											isEdit="true" uploadable="true"
											attchmentGroupId="88888888888888"
											uploadDestination="ultrarepository" uploadTableVisible="true"
											isMultiDownload="true"
											flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf"></atta:fileupload>
										<input type="button"
											value="<eoms:lable name='sm_btn_upload'/>"
											class="save_button" onclick="submitfrm();" />
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