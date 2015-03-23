<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/style/blue/css/portal.css" />
<script type="text/javascript" src="${ctx }/common/plugin/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript">
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
		    var temp_hour = (''+temp_date.getHours()).length==1?('0'+temp_date.getHours()):temp_date.getHours();
		    var temp_minute= (''+temp_date.getMinutes()).length==1?('0'+temp_date.getMinutes()):temp_date.getMinutes();
		    var temp_second = (''+temp_date.getSeconds()).length==1?('0'+temp_date.getSeconds()):temp_date.getSeconds();
		    var str_date = "<eoms:lable name='sm_lb_currentDateTime'/>："+temp_date.getFullYear()+"-"+(temp_date.getMonth()+1)+"-"+temp_date.getDate()+" "+temp_hour+":"+temp_minute+":"+temp_second;
		    currentTimeSpan.innerHTML = str_date;
		}
		var smsOrderStatus = "";
		window.onload = function()
		{
		    var currentTimeSpan = document.getElementById('currentTimeSpan');
		    var temp_date = new Date();
		    var temp_hour = (''+temp_date.getHours()).length==1?('0'+temp_date.getHours()):temp_date.getHours();
		    var temp_minute= (''+temp_date.getMinutes()).length==1?('0'+temp_date.getMinutes()):temp_date.getMinutes();
		    var temp_second = (''+temp_date.getSeconds()).length==1?('0'+temp_date.getSeconds()):temp_date.getSeconds();
		    var str_date = "<eoms:lable name='sm_lb_currentDateTime'/>："+temp_date.getFullYear()+"-"+(temp_date.getMonth()+1)+"-"+temp_date.getDate()+" "+temp_hour+":"+temp_minute+":"+temp_second;
		    currentTimeSpan.innerHTML = str_date;
		    window.setInterval(currentTimeShow,1000);
		    getOrderFormSms();
		}
		
		function getOrderFormSms(){
			$.get('${ctx}/smOrderForm/getSmsOrderStatus.action?rnd='+(new Date()).getTime(),{},function(status){
		    	smsOrderStatus = status;
		    	var btn = document.getElementById("changeOrderFmSatus");
		    	if("0"==status){
		    		btn.value = "启用短信";
		    		btn.style.visibility = "visible";
		    	}else if("1"==status){
		    		btn.value = "停用短信";
		    		btn.style.visibility = "visible";
		    	}
		    });
		}
		
		function changeOrderFormSms(){
			var status;
			if(smsOrderStatus=="1")
				status = "0";
			else
				status = "1";
			$.get("${ctx}/smOrderForm/changeSmsOrderStatus.action?rnd="+(new Date()).getTime(),{status:status},function(result){
				if("true"==result){
					var btn = document.getElementById("changeOrderFmSatus");
					if(smsOrderStatus=="1"){
						smsOrderStatus = "0";
						btn.value = "启用短信";
						alert("工单订阅短信停用成功！");
					}else{
						smsOrderStatus = "1";
						btn.value = "停用短信";
						alert("工单订阅短信启用成功！");
					}
				}
				else{
					if(smsOrderStatus=="1"){
						alert("工单订阅短信停用失败！");
					}else{
						alert("工单订阅短信启用失败！");
					}
				}
			});
		}
</script>
<title>personal_information</title>
</head>
<body style="overflow:hidden; margin:0px; padding:0px; background-color:white;">
<div style="height:100%">
  <div class="title_right">
    <div class="title_left"> <span class="title_bg"> <span class="title_icon_user">
      <eoms:lable name='sm_lb_personalInformation'/>
      </span> </span> <span class="title_xieline"></span> </div>
  </div>
  <table  class="userinfo_content_table">
    <tr>
      <td width="20%" height="191"><c:choose>
          <c:when test="${userSession.image=='default.png'}"> <img src="${ctx}/common/userimage/${userSession.image}" style="width:100px;height:110px"/> </c:when>
          <c:otherwise> <img src="${ctx}/userManager/getUserImgStream.action?realName=${userSession.image}" style="width:100px;height:110px"/> </c:otherwise>
        </c:choose></td>
      <td><div class="user_info">
          <div>
            <li id="currentTimeSpan"></li>
            <li>
              <eoms:lable name='sm_lb_currentLoginUser'/>
              ：${userSession.fullName }</li>
            <li>
              <eoms:lable name='sm_lb_currentDepartment'/>
              ：${userSession.depName }</li>
            <li>
              <eoms:lable name='sm_lb_currentCompany'/>
              ：${userSession.companyName}</li>
          </div>
          <div class="user_info_button"> 
            <!-- 
      	<input type="button" name="change_information" value="修改信息" class="edit_button" onmouseover="this.className='edit_button_hover'" onmouseout="this.className='edit_button'"/>
  	  	 -->
            <c:choose>
              <c:when test=""> </c:when>
              <c:otherwise> </c:otherwise>
            </c:choose>
            <input type="button" name="change_password" value="<eoms:lable name='sm_lb_updatePwd'/>" class="operate_button" 
			   onmouseover="this.className='operate_button_hover'" onmouseout="this.className='operate_button'" 
			   onclick="openwindow('${ctx }/portal/updatePwd.action','',500,350);"/>
            <input type="button" id="changeOrderFmSatus" value="" class="operate_button" style="visibility:hidden;"
			   onmouseover="this.className='operate_button_hover'" onmouseout="this.className='operate_button'" 
			   onclick="changeOrderFormSms();"/>
          </div>
        </div></td>
    </tr>
  </table>
</div>
</body>
</html>