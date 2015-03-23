<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/plugin/swfupload/import.jsp"%>
		<script language="javascript">
			window.onresize = function()
			{
				setCenter(0,0);
			}
			window.onload = function() 
			{
				setCenter(0,0);
				getPageMenu('div1_1','div1');PageMenuActive('div1_1','div1');
			}
		</script>
	</head>
	<body>
	<div class="content">
		<div class="add_scroll_div_x" id="center">
			<fieldset class="fieldset_style">
				<legend>附件上传下载实例</legend>
				<div class="blank_tr"></div>
				<div class="tab_bg">
					<div class="tab_show" id="div1_1"
						onclick="PageMenuActive('div1_1','div1')">
						<span>提交按钮上传\下载</span>
					</div>
					<div class="tab_hide" id="div1_2"
						onclick="PageMenuActive('div1_2','div2')">
						<span>自动提交</span>
					</div>
					<div class="tab_hide" id="div1_3"
						onclick="PageMenuActive('div1_3','div3')">
						<span>上传按钮提交</span>
					</div>
				</div>
				<div class="tabContent">
					<div id="div1">
						<atta:fileupload id="myfileupload" uploadBtnIsVisible="false"
							fileTypes="*.jpg;*.gif;*.png" downable="true"
							progressIsVisible="false"
							attchmentGroupId="f8f3ed11-42ec-4d82-a463-c6bac01f0cf7"
							uploadTableVisible="true" isMultiDownload="true"
							flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf"></atta:fileupload>
						<br />
						<input type="button" value="提交" onclick="submitfrm();" />
					</div>
					<div id="div2" style="display: none">
						<table>
							<tr>
								<td>
									<atta:fileupload id="myfileupload3" uploadTableVisible="false"
										uploadBtnIsVisible="false" downable="false"
										isMultiUpload="false" progressIsVisible="false"
										isAutoUpload="true"
										flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf"></atta:fileupload>
								</td>
							</tr>
						</table>
					</div>
					<div id="div3" style="display: none">
						<atta:fileupload id="myfileupload2" uploadBtnIsVisible="true"
							downable="false" isMultiUpload="true" isMultiDownload="false"
							progressIsVisible="false"
							flashUrl="${ctx}/common/plugin/swfupload/js/swfupload.swf"></atta:fileupload>
					</div>
				</div>
			</fieldset>
		</div>
	</div>
	</body>
	<script language="javascript">
		function submitfrm()
		{
			myfileupload.afterUploadSuccess = function(serverData)
			{
				alert("afterUploadSuccess-------------"+serverData);
			}

			myfileupload.afterUploadComplete = function( serverData )
			{
				alert("afterUploadComplete-------------"+serverData);
			}
			
			myfileupload.startUploadFile();
		}
	</script>
</html>
