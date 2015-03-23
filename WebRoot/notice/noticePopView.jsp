<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title></title>
		<script type="text/javascript" defer="defer">
			/**首页弹出start**/
				$(document).ready(function(){
					//每300秒采集一次数据
					window.setTimeout("getNoticeInfo()",5000);
					$("#noitcePopView").everyTime(300000,function() {
			                getNoticeInfo();
					});
				});
				function getNoticeInfo(){
					jQuery.post("${ctx}/notice/popView.action" ,   //服务器页面地址
			            {},
			            function(data) {
			              if(data!=""){
			                  //构建页面显示：
			                  //jQuery("#viewPage").html(data);
			                  //$("#viewPage").slideDown();
			                  testNotice(data);
			                  var viewPage=$(document.getElementById("noticeInfo"));
			                  //$(viewPage).css("cursor", "pointer").click(function() {updateListInfo();});
			                  //然后15秒后关闭显示页面 一次时间
				              $(viewPage).oneTime(15000,function(){$(viewPage).slideUp();});
			               }
			             }
					);  
				}
				function updateListInfo(){
					 jQuery.post("${ctx}/ultratask/readTaskNote.action",   //服务器页面地址
			         {},
			         function(data) {
			        	 $(document.getElementById("noticeInfo")).slideUp();
			         });
				}
				function testNotice(data){
					createNoticeDiv("popView",$(document.body));
					divObj = $(document.getElementById("noticeInfo"));
					divObj.css('display','none');
					divObj.css('position','absolute');
					divObj.css('width','250px');
					divObj.css('height','80');
					divObj.css('z-index','32001');
					divObj.css('top',document.body.clientHeight-75);
					divObj.css('left',document.body.clientWidth-270);
					divObj.css('background','#CCFFFF');
					//divObj.css('padding','10px');
					divObj.css('font-size','12px');
					divObj.css('color','#0000FF');
					divObj.css('filter','Alpha(opacity=80)') ;
					divObj.css('opacity','.80') ;
					divObj.css('border','1px solid #0000FF') ;
					divObj.css('border-top','1px solid #0000FF') ;
					$("#viewInfo").html(data);
			        divObj.slideDown();
				}
				function createNoticeDiv(id,jobj){
					$(jobj.children().get(0)).after("<div id='"+id+"'></div>");
				}
				
				function openRowCountView(){
					var src = "${ctx}/notice/noticeViewLogFrame.action";
					window.open(src,'_blank','');
				}
				
				function openContentView(noticeId){
					var src = '${ctx}/notice/viewNotice.action?noticeId='+noticeId;
					window.open(src);
				}
				
				//不再显示
				function unView(){
					jQuery.post("${ctx}/notice/unView.action",   //服务器页面地址
			         {},
			         function(data) {
			         });
			         $("#noticeInfo").slideUp();
				}
				
				function closeView(){
					$("#noticeInfo").slideUp();
				}
				/**首页弹出end**/
		</script>
		
		<style>
			
		</style>
	</head>
 		
	<body>
			<div id="noitcePopView" ></div>    
			<div id="noticeInfo" style="display: none;">
				<div style="padding-left: 170px;background-color: #00CCFF;color: black;">
					<span><span onclick="unView();">不再显示</span>&nbsp;&nbsp;&nbsp;<span onclick="closeView();">[x]</span></span>
				</div>
				<div style="padding:10px;" id="viewInfo"></div>
			</div>
	</body>
</html>