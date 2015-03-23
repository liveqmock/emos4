<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//String path = request.getContextPath();
	//String basePath = request.getScheme() + "://"
			//+ request.getServerName() + ":" + request.getServerPort()
			//+ path + "/";
	
%>
<html>
	<head>
		<title></title>
    	<link id="skin" rel="stylesheet" href="${ctx}/notice/Pink/jbox.css" />
    	<script type="text/javascript" src="${ctx}/notice/js/jquery.jBox.src.js"></script>
    <script type="text/javascript" src="${ctx}/notice/js/speak.js"></script>
		<script type="text/javascript" defer="defer">
			/**首页弹出start**/
			//var VoiceObj = new ActiveXObject("SAPI.SpVoice");
			//VoiceObj.Volume=100;
				$(document).ready(function(){
					//每30秒采集一次数据
					//window.setTimeout("getNoticeInfo()",5000);
					$("#taskNoticeInfo").everyTime(30000,function() {
			              	getTaskNoticeInfo();
					});
				});
				function getTaskNoticeInfo(){
					jQuery.post("${ctx}/tasknotice/popView.action?" ,   //服务器页面地址
					{},
					function(jsondata) {
						if(typeof jsondata.notices != 'undefined' && jsondata.notices != '' && jsondata.notices != null){
							for(var i = 0; i < jsondata.notices.length; i++){
								if(i < 5){
						            jBox.messager(jsondata.notices[i].content, "", 15000, { width: 250, height:105, showType: 'show'});
								}
							}
			                say();
						}
			        },
					"json"
					);  
				}
				function openContentView1(noticeId){
					var src = '${ctx}/ultrabpp/view.action?'+noticeId;
					window.open(src);
				}
		        function say(){
		        	try {
						VoiceObj.Speak($("#sayContent").val(), 1);
					}
					catch (exception) {
					}
		        }
		</script>
		<SCRIPT FOR="window" EVENT="OnQuit()" LANGUAGE="JavaScript"> 
		// Clean up voice object 
			delete VoiceObj; 
		</SCRIPT>
	</head>
	<body>
			<div id="taskNoticeInfo" style="display: none;">
<!--				<div style="padding-left: 170px;background-color: #00CCFF;color: black;">-->
<!--					<span><span onclick="unView();">不再显示</span>&nbsp;&nbsp;&nbsp;<span onclick="closeView();">[x]</span></span>-->
<!--				</div>-->
<!--				<div style="padding:10px;" id="viewInfo"></div>-->
				<input value="您有新工单需要处理" id="sayContent" type='hidden' />
			</div>
	</body>
</html>