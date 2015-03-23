<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<head>
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
	 <title>
		     <eoms:lable name="wf_sort_wfsort" />
	</title>
	
    <script type="text/javascript">
    
    var message = '${message}';
	if(message != null){
		if(message == 'true'){
			window.close();
		}
	}else{
		alert(message);
	}
	
	window.onresize = function()
	{
		setCenter(0,61);
	}
	window.onload = function(){
	    setCenter(0,61);
	}
	
    </script>
</head>
<body>
<div class="content" id="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							系统公告
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div class="scroll_div" id="center">
			<br />
				<table class="add_user" style="height: 95%;width: 80%;" align="center">
					<tr >
						<td align="center"  colspan="2" height="5%">
							<h3 align="">${noticeInfo.noticeTitle }</h3>
						</td>
					</tr>
					<tr>
						<td align="center"  colspan="2" height="5%">
							<nobr>
								发布人：${noticeInfo.creatorName }&nbsp;&nbsp;&nbsp;生效时间：${noticeInfo.noticeStartTimeStr }&nbsp;&nbsp;&nbsp;失效时间：${noticeInfo.noticeLostTimeStr }&nbsp;&nbsp;&nbsp;公告等级：<eoms:dic value="${noticeInfo.noticeLevel}" dictype="noticeLevel"></eoms:dic>
							</nobr>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr/>
						</td>
					</tr>
					<tr style="height: 50%">
						<td colspan="2" style="padding-left: 20px;padding-top: 20px;" valign="top">
							${noticeInfo.noticeContent }
						</td>
					</tr>
					<tr>
						<td class="texttd">
							附件列表：
						</td>
						<td>
							<atta:fileupload 
								id="noticAtta" 
								uploadDestination="notice/noitce"
								uploadable="false" 
								downTableVisible="true" 
								isMultiDownload="false" 
								isMultiUpload="false"
								uploadBtnIsVisible="false" 
								uploadTableVisible="false"
								attchmentGroupId="${noticeInfo.attachmentId}"
								isAutoUpload="false"
								createDirByDate="false" 
								fileTypes="*.*" 
								progressIsVisible="false"
								returnValue="1"
								delSelfOnly="false"
								operationParams="0,0,1"
								></atta:fileupload>
						</td>
					</tr>
				</table>
  		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_close"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
		</div>
	</div>
</body>
</html>