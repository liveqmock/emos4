<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>common/style/blue/css/portal.css" />
	<style>
    	html{overflow-y:auto;overflow-x:hidden;}
    </style>
	<script language="javascript">
		function changediv(id_num, id,n)
		{ 
			for(var i=1;i<=n;i++)
			{
				var divName=document.getElementById("div1_" + id_num + i);
				var divObj=document.getElementById("div" + id_num + i);
				if(i == id)
				{
					divName.className='on';
					divObj.style.display = '';
				}
				else
				{
					divName.className='off';
					divObj.style.display = 'none';
				}
			}
		}
		function openwindow(url,name,iWidth,iHeight)
		{
			var url; 
			var name; 
			var iWidth; 
			var iHeight; 
			var iTop = (window.screen.availHeight-30-iHeight)/2; 
			var iLeft = (window.screen.availWidth-10-iWidth)/2; 
			window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
		}
		var currentTimeShow = function()
		{
			var currentTimeSpan = document.getElementById('currentTimeSpan');
		    var temp_date = new Date();
		    var str_date = "当前日期："+temp_date.getFullYear()+"-"+temp_date.getMonth()+"-"+temp_date.getDate()+" "+temp_date.getHours()+":"+temp_date.getMinutes()+":"+temp_date.getSeconds();
		    currentTimeSpan.innerHTML = str_date;
		}
		window.onload = function()
		{
		    changeRow_color("tab");changeRow_color("tab2");changeRow_color("tab3");changeRow_color("tab4");changeRow_color("tab5");
		    var currentTimeSpan = document.getElementById('currentTimeSpan');
		    var temp_date = new Date();
		    var str_date = "当前日期："+temp_date.getFullYear()+"-"+temp_date.getMonth()+"-"+temp_date.getDate()+" "+temp_date.getHours()+":"+temp_date.getMinutes()+":"+temp_date.getSeconds();
		    currentTimeSpan.innerHTML = str_date;
		    window.setInterval(currentTimeShow,1000);
		}
		
		function changeTab(num)
		{
			var tabLength=document.getElementById("tabDiv").getElementsByTagName("div").length;
			var tabObj=document.getElementById("tabDiv").getElementsByTagName("div");
			var endnum=tabLength-1;
			for(i=0;i<tabLength;i++)//判断是否是最后一个
			{
			    if(i==endnum){
					tabObj.item(i).className="last_unselect";
					}
				else if(i==0){
					tabObj.item(i).className="first_unselect";
					}
				else{
					tabObj.item(i).className="common_unselect";
					}
				document.getElementById("tab11").style.display="none";
				document.getElementById("tab12").style.display="none";
			}
			if(num<endnum){//判断不是最后一个
				tabObj.item(num).className="common_select"; 
				document.getElementById("tab11").style.display="none";
				document.getElementById("tab12").style.display="";
			}
			else{
				tabObj.item(num).className="last_select";  
				document.getElementById("tab11").style.display="none";
				document.getElementById("tab12").style.display="";
			}
			if(num==0){
				tabObj.item(num).className="first_select";  
				document.getElementById("tab11").style.display="";
				document.getElementById("tab12").style.display="none";
				}
		}//function changeTab(num)
		
		function changeTab1(num)
		{
			var tabLength=document.getElementById("tabDiv01").getElementsByTagName("div").length;
			var tabObj=document.getElementById("tabDiv01").getElementsByTagName("div");
			var endnum=tabLength-1;
			for(i=0;i<tabLength;i++)//判断是否是最后一个
			{
			    if(i==endnum){
					tabObj.item(i).className="last_unselect";
				}
				else if(i==0){
					tabObj.item(i).className="first_unselect";
				}
				else{
					tabObj.item(i).className="common_unselect";
				}
				document.getElementById("tab21").style.display="none";
				document.getElementById("tab22").style.display="none";
			}
			if(num<endnum){//判断不是最后一个
				tabObj.item(num).className="common_select";  
				document.getElementById("tab21").style.display="none";
				document.getElementById("tab22").style.display="";
			}
			else{
				tabObj.item(num).className="last_select";  
				document.getElementById("tab21").style.display="none";
				document.getElementById("tab22").style.display="";
			}
				
			if(num==0){
				tabObj.item(num).className="first_select";  
				document.getElementById("tab21").style.display="";
				document.getElementById("tab22").style.display="none";
			}
		}
		function changeTab2(num)
		{
			var tabLength=document.getElementById("tabDiv02").getElementsByTagName("div").length;
			var tabObj=document.getElementById("tabDiv02").getElementsByTagName("div");
			var endnum=tabLength-1;
			for(i=0;i<tabLength;i++)//判断是否是最后一个
			{
			    if(i==endnum){
					tabObj.item(i).className="last_unselect";
				}
				else if(i==0){
					tabObj.item(i).className="first_unselect";
				}
				else{
					tabObj.item(i).className="common_unselect";
				}
				document.getElementById("tab31").style.display="none";
				document.getElementById("tab32").style.display="none";
			}
			if(num<endnum){//判断不是最后一个
				tabObj.item(num).className="common_select";  
				document.getElementById("tab31").style.display="none";
				document.getElementById("tab32").style.display="";
			}
			else{
				tabObj.item(num).className="last_select";  
				document.getElementById("tab31").style.display="none";
				document.getElementById("tab32").style.display="";
			}
				
			if(num==0){
				tabObj.item(num).className="first_select";  
				document.getElementById("tab31").style.display="";
				document.getElementById("tab32").style.display="none";
			}
		}
			
		function changeTab3(num)
		{
			var tabLength=document.getElementById("tabDiv03").getElementsByTagName("div").length;
			var tabObj=document.getElementById("tabDiv03").getElementsByTagName("div");
			var endnum=tabLength-1;
			for(i=0;i<tabLength;i++)//判断是否是最后一个
		    {
			    if(i==endnum){
					tabObj.item(i).className="last_unselect";
				}
				else if(i==0){
					tabObj.item(i).className="first_unselect";
				}
				else{
					tabObj.item(i).className="common_unselect";
				}
				document.getElementById("tab41").style.display="none";
				document.getElementById("tab42").style.display="none";
			}
			if(num<endnum){//判断不是最后一个
				tabObj.item(num).className="common_select";  
				document.getElementById("tab41").style.display="none";
				document.getElementById("tab42").style.display="";
			}
			else{
				tabObj.item(num).className="last_select";  
				document.getElementById("tab41").style.display="none";
				document.getElementById("tab42").style.display="";
			}
				
			if(num==0){
				tabObj.item(num).className="first_select";  
				document.getElementById("tab41").style.display="";
				document.getElementById("tab42").style.display="none";
			}
		}
		function openwindow(url,name,iWidth,iHeight)
		{
			var url; 
			var name; 
			var iWidth; 
			var iHeight; 
			var iTop = (window.screen.availHeight-30-iHeight)/2; 
			var iLeft = (window.screen.availWidth-10-iWidth)/2; 
			window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no');
		}
	</script>  
  </head>
  
  <body>
    <div class="right_search">
	    <li><input name="" type="text" value="请输入查询条件"/></li>
	    <li>
	    <select name="" style="margin-top:0px;*margin-top:1px;">
	      <option>智能模糊查询</option>
	    </select>
	    </li>
	    <li><input name="search" type="button" value="搜索" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" style="margin-top:0px;*margin-top:1px;"/></li>
	     <li><input name="searchB" type="button" value="高级搜索" class="searchadv_button" onmouseover="this.className='searchadv_button_hover'" onmouseout="this.className='searchadv_button'" style="margin-top:0px;*margin-top:1px;"/></li>
    </div>
   
	<div class="tag">
		<div class="tag_bg">
			<div class="hot"><span>热门TAG</span></div>
			<div class="hot_content">河北OSC故障、湖南业务开通短信、新一代运维平台正式上线</div>
		</div>
	</div>
	
	<table cellpadding="0" cellspacing="5" class="layout_table">
	  <tr>
	    <td class="left">
			<div class="title_right">
			      <div class="title_left">
			        <span class="title_bg">
			          <span class="title_icon_user">个人信息</span>
			        </span>
			        <span class="title_xieline"></span>
			      </div>
			</div>
			<table  class="userinfo_content_table">
			  <tr>
			    <td width=20%><img src="<%=basePath%>common/style/blue/images/portal/userpic.png" /></td>
			    <td><div class="user_info">
			    <div>
			      <li id="currentTimeSpan"></li>
			      <li>登录人：${userSession.fullName }</li>
			      <li>部门：${userSession.depName }</li>
			      <li>公 司：超级管理员公司</li></div>
			      <div class="user_info_button">
			      <!--  
			      <input type="button" name="change_information" value="修改信息" class="edit_button" 
			      		onmouseover="this.className='edit_button_hover'" onmouseout="this.className='edit_button'" 
			      		onclick="openwindow('${ctx}/userManager/userLoad.action?userID=${userSession.pid}','',800,600);"/>
			   	  -->
			   	  <input type="button" name="change_password" value="修改密码" class="operate_button" 
			   	  		onmouseover="this.className='operate_button_hover'" onmouseout="this.className='operate_button'" 
			   	  		onclick="openwindow('${ctx }/common/portal/updatePwd.jsp','',500,200);"/>
			      </div>
			    </div></td>
			  </tr>
			</table>
			
			<div class="title_right">
			      <div class="title_left">
			        <span class="title_bg">
			          <span class="title_icon_workplat">工作平台</span>
			        </span>
			        <span class="title_xieline"></span>
			        <span class="title_more"></span>
			      </div>
			</div>
			
			<table  class="userinfo_content_table">
			  <tr>
			    <td>
			   <div class="tablabel">
			    <div id="div1_11" class="on" onClick="changediv(1,1,4);"><span>今日任务</span></div>
			    <div id="div1_12" class="off"  onclick="changediv(1,2,4)"><span>工作提醒</span></div>
			    <div id="div1_13" class="off" onClick="changediv(1,3,4);"><span>今日任务</span></div>
			    <div id="div1_14" class="off"  onclick="changediv(1,4,4)"><span>工作提醒</span></div>
			   </div>
			    <div class="tab_content">
			      <div id="div11" class="info_content">
			      <div class="blank_br"></div>
			      <li>今天要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      </div>
			      
			      <div id="div12" class="info_content" style="display:none;">
			      <div class="blank_br"></div>
			      <li>222222今天要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      </div>
			      
			      <div id="div13" class="info_content" style="display:none;">
			      <div class="blank_br"></div>
			      <li>33333今天要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      </div>
			      
			      <div id="div14" class="info_content" style="display:none;">
			      <div class="blank_br"></div>
			      <li>4444今天要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      </div>
			    </div>
			    </td>
			  </tr>
			</table>
			
			<div class="title_right">
			      <div class="title_left2">
			      
			<div  id="tabDiv">
			<div class="first_select" onclick="changeTab(0)">
			<span><span><img src="<%=basePath%>common/style/blue/images/portal/img_01.png" width="18" height="18" align="absmiddle" />工单起草</span></span></div>
			
			<div class="last_unselect" onclick="changeTab(1)">
			<span><span><img src="<%=basePath%>common/style/blue/images/portal/img_02.png" width="15" height="14" align="absmiddle" />常用链接</span></span></div>
			</div>
			<span class="title_more"></span>
			</div>
			</div>
			
			<table  class="third_content_table">
			<tr>
			<td class="Info1" >
			<div  id="tab11">
			  <div class="contract_menu_top">工单分类一</div>
			  <div class="contract_menu">
			       <li> 01-通用类工单开幕</li>
			       <li>是付了款看的时方可数据库接口</li>
			       <li>2009的富士康几节课族的</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>发的是科技局方可会计师里</li>
			  </div>
			  <div class="contract_menu_top">工单分类二</div>
			</div>
			<div class="info_content" id="tab12" style="display:none;">
			      <div class="blank_br"></div>
			      <li>今天要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			</div>
			</td>
			</tr>
			
			</table>
		</td>
	   <!--centercol--> 
		<td>
			<div class="title_right">
			      <div class="title_left">
			<div  id="tabDiv01">
			<div class="first_select" onclick="changeTab1(0)">
			<span><span><img src="<%=basePath%>common/style/blue/images/title/tag7.png"  align="absmiddle" />待办工单</span></span></div>
			
			<div class="last_unselect" onclick="changeTab1(1)">
			<span><span><img src="<%=basePath%>common/style/blue/images/title/tag3.png"  align="absmiddle" />待办维护作业</span></span></div>
			</div>
			<span class="title_more"></span>
			
			      </div>
			</div>
			<table  class="daiban_content_table">
			<tr>
			<td>
			<div id="tab21">
			<table class="Ttaber" id="tab">
			  <tr>
			    <th>工单主题</th>
			    <th>处理时限</th>
			    <th>当前状态 </th>
			    <th> 标记</th>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png"  /></td>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png"  /></td>
			  </tr>
			  <tr>
			   <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" /></td>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" /></td>
			  </tr>
			  <tr>
			   <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" /></td>
			  </tr>
			  <tr>
			  <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" align="absmiddle"/></td>
			  </tr>
			  </table>
			  <div style="height:20px;padding-top:5px">
			        <img src="<%=basePath%>common/style/blue/images/title/tag4.png"  align="absmiddle" />  已完成，未超时
			        <img src="<%=basePath%>common/style/blue/images/title/tag5.png"  align="absmiddle" />  已完成，已超时 
			         <img src="<%=basePath%>common/style/blue/images/title/tag6.png" align="absmiddle" /> 未完成，未超时
			        <img src="<%=basePath%>common/style/blue/images/title/tag8.png" align="absmiddle" /> 未完成，已超时</div>
			   </div>
			   <div id="tab22" style="display:none;">
			        <table class="Ttaber" id="tab2">
			  <tr>
			    <th>工单主2题</th>
			    <th>处理时限</th>
			    <th>当前状态 </th>
			    <th> 标记</th>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png"  /></td>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png"  /></td>
			  </tr>
			  <tr>
			   <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" /></td>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" /></td>
			  </tr>
			  <tr>
			   <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" /></td>
			  </tr>
			  <tr>
			  <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>处理中</td>
			    <td><img src="<%=basePath%>common/style/blue/images/title/tag8.png" align="absmiddle"/></td>
			  </tr>
			  </table>
			  <div style="height:20px;padding-top:5px">
			        <img src="<%=basePath%>common/style/blue/images/title/tag4.png"  align="absmiddle" />  已完成，未超时
			        <img src="<%=basePath%>common/style/blue/images/title/tag5.png"  align="absmiddle" />  已完成，已超时 
			         <img src="<%=basePath%>common/style/blue/images/title/tag6.png" align="absmiddle" /> 未完成，未超时
			        <img src="<%=basePath%>common/style/blue/images/title/tag8.png" align="absmiddle" /> 未完成，已超时</div>
			   </div>
			</td>
			
			</tr>
			</table>
			
			
	        <div class="title_right">
			      <div class="title_left">    
			<div  id="tabDiv02">
			
			<div class="first_select" onclick="changeTab2(0)">
			<span><span><img src="<%=basePath%>common/style/blue/images/portal/img_01.png" width="18" height="18" align="absmiddle" />紧急故障</span></span></div>
			
			<div class="last_unselect" onclick="changeTab2(1)">
			<span><span><img src="<%=basePath%>common/style/blue/images/title/tag2.png" align="absmiddle" />遗留问题</span></span></div>
			</div>
			<span class="title_more"></span>
			</div>
			</div>
			
			<table  class="emergency_content_table">
			<tr>
			<td>
			<div id="tab31">
			<table class="Ttaber" id="tab3">
			  <tr>
			   <th>问题主题</th>
			    <th>问题创建人</th>
			    <th>问题创建时间 </th>
			    <th> 问题状态</th>
			  </tr>
			    <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			    <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			</table>
			</div>
			<div id="tab32"  style="display:none;">
			<table class="Ttaber" id="tab4">
			  <tr>
			   <th>问题主题22</th>
			    <th>问题创建人</th>
			    <th>问题创建时间 </th>
			    <th> 问题状态</th>
			  </tr>
			    <tr>
			     <td>业务变更工单（传输专线）：处理专emergency_content_table线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			    <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>王女士</td>
			    <td>2010-03-25 14:22:15</td>
			    <td>待处理</td>
			  </tr>
			</table>
			</div>
			</td>
			
			</tr>
			</table>
			<div class="title_right" >
			      <div class="title_left">
			        <span class="title_bg">
			          <span class="title_icon_arrange">排程看板</span>
			        </span>
			        <span class="title_xieline"></span>
			        <span class="title_more"></span>
			      </div>
			</div>
			<table  class="arrange_content_table">
			<tr>
			<td><table class="Ttaber" id="tab5">
			  <tr>
			    <th>变更时间</th>
			    <th>变更类别</th>
			    <th>专业</th>
			    <th>地域</th>
			    <th>变更网元</th>
			    <th>变更标题</th>
			  </tr>
			  <tr>
			   <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>类别</td>
			    <td>处理中</td>
			      <td>北京</td>
			        <td>网元</td>
			        <td>标题</td>
			  </tr>
			   <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>类别</td>
			    <td>处理中</td>
			      <td>北京</td>
			        <td>网元</td>
			        <td>标题</td>
			  </tr>
			  <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>类别</td>
			    <td>处理中</td>
			      <td>北京</td>
			        <td>网元</td>
			        <td>标题</td>
			  </tr>
			  <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>类别</td>
			    <td>处理中</td>
			      <td>北京</td>
			        <td>网元</td>
			        <td>标题</td>
			  </tr>
			   <tr>
			     <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>类别</td>
			    <td>处理中</td>
			      <td>北京</td>
			        <td>网元</td>
			        <td>标题</td>
			  </tr>
			   <tr>
			    <td>业务变更工单（传输专线）：处理专线 [完成]</td>
			    <td>类别</td>
			    <td>处理中</td>
			      <td>北京</td>
			        <td>网元</td>
			        <td>标题</td>
			  </tr>
			</table></td>
			
			</tr>
			</table>
	    </td>
	    <!--rightcol-->
	    <td class="right">
		    <div class="title_right">
		      <div class="title_left">
		        <span class="title_bg">
		          <span class="title_icon_broadcast">公告</span> 
		        </span>
		        <span class="title_xieline"></span>
		        <span class="title_more"></span>
		      </div>
		     </div>
			<table  class="notice_content_table">
			   <tr>
			   <td class="notice">
			     <div class="info_content">
			      <div class="blank_br"></div>
			      <li>今天要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      <li>9点召开大会</li>
			      <li>9今天绩效考核需要提交相关文档</li>
			      </div>
			</td>
			</tr>
			</table>
			<div class="title_right">
			      <div class="title_left">
			        <span class="title_bg">
			          <span class="title_icon_infosubject">信息专题</span>
			        </span>
			        <span class="title_xieline"></span>
			        <span class="title_more"></span>
			      </div>
			</div>
			<table  class="info_content_table">
			   <tr>
			   <td class="Info">
			     <div class="Infocontent">
			       <li>中国移动第九届员工代表大会开幕</li>
			       <li>是付了款看的时方可数据库接口</li>
			       <li>2009的富士康几节课族的</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>防的科技科技框架</li>
			       <li>发的是科技局方可会计师里</li>
			     </div>
			</td>
			</tr>
			</table>
			
			<div class="title_right">
			      <div class="title_left2">
			      
			<div  id="tabDiv03">
			<div class="first_select" onclick="changeTab3(0)">
			<span><span><img src="<%=basePath%>common/style/blue/images/title/tag13.png"  align="absmiddle" />论坛新贴</span></span></div>
			
			<div class="last_unselect" onclick="changeTab3(1)">
			<span><span><img src="<%=basePath%>common/style/blue/images/title/tag1.png"  align="absmiddle" />论坛热贴</span></span></div>
			</div>
			 <span class="title_more"></span>
			      </div>
			</div>
			
			<table  class="bbs_content_table">
			   <tr>
			   <td class="Info">
			   <div id="tab41">
			     <div class="Infocontent">
			       <li>中国移动第九届员工代表大会开幕</li>
			       <li>是付了款看的时方可数据库接口</li>
			       <li>2009的富士康几节课族的</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>防的科技科技框架</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>发的是科技局方可会计师里</li>
			     </div>
			   </div>
			   <div id="tab42" style="display:none;">
			     <div class="Infocontent">
			       <li>222222</li>
			       <li>是付了款看的时方可数据库接口</li>
			       <li>2009的富士康几节课族的</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>防的科技科技框架</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>发的是科技局方可会计师里</li>
			       <li>发的是科技局方可会计师里</li>
			     </div>
			   </div>
			</td>
			</tr>
			</table>
	    </td>
	  </tr>
	</table>
	<div class="footer">版权所有：神州泰岳软件股份有限公司   技术支持：ultra@power 系统支持电话：13900000000 </div>	
  </body>
</html>
