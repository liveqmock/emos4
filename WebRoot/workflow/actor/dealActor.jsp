<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<link type="text/css" rel="Stylesheet" href="${ctx}/common/plugin/JTable/JTable.css" />
<link href="${ctx}/common/style/${skintype}/css/newgongdan.css"  rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/common/plugin/JTable/JTable.js"></script>
<script type="text/javascript" src="${ctx}/common/javascript/datagrid.js"></script>
<script type="text/javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/common/javascript/date/dateFormat.js"></script>
<script src="${ctx}/common/javascript/util.js"></script>
<script src="${ctx}/workflow/actor/js/actor.js"></script>
<script src="${ctx}/workflow/actor/js/dealActor.js"></script>
<title>
<eoms:lable name="wf_actor_assignUser" />
</title>
<script type="text/javascript">
		window.onresize = function() 
		{
		  setCenter(70,0);
		}
		
		window.onload = function(){
		    setCenter(70,0);
			init();
	    	//getPageMenu('div1_1','div1');
	    	//PageMenuActive('div1_1','div1');
		}
		
		
		function msup(){
		 	ev = window.event;
			var left ='clientX: ' + ev.clientX + ' scrollLeft:' + document.body.scrollLeft + '  clientLeft:' + document.body.clientLeft;
			var top = 'clientY: ' + ev.clientY + '  scrollTop:' + document.body.scrollTop  + '  clientTop:' + document.body.clientTop;
			document.getElementById('X').value = left;
			document.getElementById('Y').value = top;
		}
	</script>

</head>
<body>
<div class="content" >
  <div class='title_right'>
    <div class='title_left'> <span class='title_bg'> <span class='title_icon2'>
      <eoms:lable name="wf_actor_currentPosition"/>
      </span> </span> <span class='title_xieline'> </span> </div>
  </div>
  <div class="scroll_div scroll_div12" id="center">
    <table class="add_user" border="0" >
      <tr>
        <!-- 
						<td width="30%" valign="top">
							<fieldset class="fieldset_style" style="height:550px; width:283px;">
								
								<legend><div  id="title_1"><eoms:lable name="wf_actor_assign_title1" /></div></legend>
								<div class="blank_tr"></div>
								<div id="baseinfo" style=" width:285px;">
									<div class="tab_bg">
									
										<div class="tab_show" id="div1_1" onclick="PageMenuOnclick('div1_1','div1','1')">
											<span><eoms:lable name="wf_actor_depUserTree" /></span>
										</div>
										
										<div class="tab_hide" id="div1_2" onclick="PageMenuOnclick('div1_2','div2','2')">
											<span><eoms:lable name="wf_actor_role" /></span>
										</div>
										<div class="tab_hide" id="div1_9" onclick="PageMenuOnclick('div1_9','div9','9')">
											<span>自定义树</span>
										</div>										
									</div>
									<div id="div1" style="display:none; width:282px;" >
										<iframe src=""  frameborder="0" id="deptree" name="center" scrolling="no" style="width: 100%;height:500px;"></iframe>
									</div>
									<div id="div2" style="display:none; width:282px;">
										<iframe src=""  frameborder="0" id="roletree" name="center" style="width: 100%;height: 500px" scrolling="no"></iframe>
									</div>
									<div id="div9" style="display:none; width:282px;">
										<iframe src=""  frameborder="0" id="customertree" name="center" style="width: 100%;height: 500px" scrolling="no"></iframe>
									</div>	
								</div>
							</fieldset>
						</td>
					 -->
        <td width="601px" valign="top"><!--选择要派发的组或人-->
          <iframe src=""  frameborder="0" id="commonTree" name="center" scrolling="no" style="width: 600px;height:470px;"></iframe></td>
        <td  valign="top"><!--已经点击按钮添加要派发的组或人-->
          <table class="add_user"  border="0">
            <tr>
              <td width="10%"></td>
              <td valign="top"><div  class="dialogue" id="baseinfo" style="padding-top:5px;">
                  <table style="margin:5px 10px 5px 10px; width:100%">
                    <tr>
                      <td width="75" id="title_2" align="right"><eoms:lable name="wf_actor_assign_title2" />
                        :</td>
                      <td colspan="5"><input  id="desc" style="width:339px" onchange="formsubmit();"/></td>
                    </tr>
                    <tr>
                      <td align="right"><eoms:lable name="wf_actor_limittime" />
                        :</td>
                      <td><input type="text" id='limittime' style="width:120px;" onchange="timesetting_basic(this.value,'');" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" /></td>
                      <td width="65" align="right"><eoms:lable name="wf_actor_dealtime" />
                        :</td>
                      <td><input type="text" id='dealtime' style="width:120px;" onchange="timesetting_basic('',this.value);" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  /></td>
                    </tr>
                  </table>
                </div></td>
            </tr>
            <tr style="height: 40%" valign="top" id="dealTr">
              <td valign="middle" align="center">
              	<input type="button" value="<eoms:lable name='wf_actor_assign_title3'/>"  id="title_3"	class="add_button" onclick="addDealActor('ASSIGN');" />
              </td>
              <td valign="top"><div class="blank_tr"></div>
                <div  id="title_4" class="dealactorfont">
                  <eoms:lable name="wf_actor_assign_title4" />
                </div>
                <div id="dealList" class="listdealactor2">
                  <div class="listdealactor3" id="dealActorTable"></div>
                </div>
                <div class="blank_tr"></div>
                </td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="assist">
              <td valign="middle" align="center"><input type="button" value="协办" class="add_button" onclick="addDealActor('ASSIST');" /></td>
              <td valign="top"><div class="dealactorfont">协办</div>
                <div id="assistList"  class="listdealactor2">
                  <div  class="listdealactor3" id="assistActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="audit">
              <td valign="middle" align="center"><input type="button" value="审批" class="add_button" onclick="addDealActor('AUDIT');" /></td>
              <td valign="top"><div class="dealactorfont">审批</div>
                <div id="auditList"  class="listdealactor2">
                  <div  class="listdealactor3" id="auditActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="reassign">
              <td valign="middle" align="center"><input type="button" value="移交" class="add_button" onclick="addDealActor('REASSIGN');" /></td>
              <td valign="top"><div class="dealactorfont">移交</div>
                <div id="reassignList"  class="listdealactor2">
                  <div  class="listdealactor3" id="reassignActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="copy">
              <td valign="middle" align="center"><input type="button" value="<eoms:lable name='wf_addCopyActor'/>" class="add_button" onclick="addDealActor('MAKECOPY');" /> </td>
              <td valign="top"><div class="dealactorfont">
                  <eoms:lable name="wf_actor_copy"/>
                </div>
                <div id="copyList"  class="listdealactor2">
                  <div  class="listdealactor3" id="copyActorTable"></div>
                </div></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </div>
  <!-- 
			<div class="add_bottom">
					X:<input type="text" id="X" style="width: 400px;" />
					Y:<input type="text" id='Y' style="width: 400px;" />	
					<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button" onclick="formsubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" />
			</div>
			 -->
</div>
<div id='time' class="dealactortimestyle">
  <input type="hidden" id="actor_id" />
  <table width="100%">
    <tr>
      <td class="texttd"><eoms:lable name="wf_actor_limittime"/>
        :</td>
      <td><input type="text" id='actor_limittime' class="textInput" style="width:120px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  /></td>
      <td class="texttd"><eoms:lable name="wf_actor_dealtime"/>
        :</td>
      <td><input type="text" id='actor_dealtime' class="textInput" style="width:120px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});"  /></td>
    </tr>
    <tr>
      <td class="texttd" colspan="2" align="right"><input type="button" value="<eoms:lable name="com_btn_save"/>
        " class="save_button" onclick="timesetting();"  /> </td>
      <td class="texttd" colspan="2"><input type="button" value="<eoms:lable name="com_btn_cancel"/>
        " class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="document.getElementById('time').style.display='none'" /> </td>
    </tr>
  </table>
</div>
<script  type="text/javascript">
/**
 * 国际化
 */
function titleInit(){
	//动作不同，页面显示字符不同
	switch(action_sign){
		case "REASSIGN":{//转派
			//document.getElementById('title_1').innerText = '<eoms:lable name="wf_actor_reassign_title1"/>';
			document.getElementById('title_2').innerText = '<eoms:lable name="wf_actor_reassign_title2"/>:';
			document.getElementById('title_3').value = '<eoms:lable name="wf_actor_reassign_title3"/>';
			document.getElementById('title_4').innerText = '<eoms:lable name="wf_actor_reassign_title4"/>';
			//document.getElementById('copy').style.display = 'none';
			break;
		};
		case "AUDIT":{//审批
			//document.getElementById('title_1').innerText = '<eoms:lable name="wf_actor_audit_title1"/>';
			document.getElementById('title_2').innerText = '<eoms:lable name="wf_actor_audit_title2"/>';
			document.getElementById('title_3').value = '<eoms:lable name="wf_actor_audit_title3"/>';
			document.getElementById('title_4').innerText = '<eoms:lable name="wf_actor_audit_title4"/>';
			//document.getElementById('copy').style.display = 'none';
			break;
		};
		case "REAUDIT":{//转审
			//document.getElementById('title_1').innerText = '<eoms:lable name="wf_actor_reaudit_title1"/>';
			document.getElementById('title_2').innerText = '<eoms:lable name="wf_actor_reaudit_title2"/>';
			document.getElementById('title_3').value = '<eoms:lable name="wf_actor_reaudit_title3"/>';
			document.getElementById('title_4').innerText = '<eoms:lable name="wf_actor_reaudit_title4"/>';
			//document.getElementById('copy').style.display = 'none';
			break;
		};
		case "ORGANIZEAUDIT":{//组织会审
			//document.getElementById('title_1').innerText = '<eoms:lable name="wf_actor_organizeaudit_title1"/>';
			document.getElementById('title_2').innerText = '<eoms:lable name="wf_actor_organizeaudit_title2"/>';
			document.getElementById('title_3').innerText = '<eoms:lable name="wf_actor_organizeaudit_title3"/>';
			document.getElementById('title_4').innerText = '<eoms:lable name="wf_actor_organizeaudit_title4"/>';
			//document.getElementById('copy').style.display = 'none';
			break;
		};
		case "MAKECOPY":{//抄送
			//document.getElementById('title_1').innerText = '<eoms:lable name="wf_actor_copy_title1"/>';
			document.getElementById('title_2').innerText = '<eoms:lable name="wf_actor_copy_title2"/>:';
			document.getElementById('title_4').innerText = '<eoms:lable name="wf_actor_copy_title4"/>';
			document.getElementById('dealTr').style.display = 'none';
			document.getElementById('copy').style.display = '';
			break;
		};
		case "NEXT":{//派发
			//document.getElementById('copy').style.display = 'none';
			break;
		};
		default:
	}
	
	//2010-12-22 fanying 派发功能和抄送功能同时显示
	//2011-1-13 fanying 将动作整合到一起进行人员分配
	if(action_sign=='ASSIGN'){
		//assignAndReassign='0';//派发页面把移交过滤掉
		var shownum= parseInt(assignAndCopy)+parseInt(assignAndAssign)+parseInt(assignAndReassign)+parseInt(assignAndAudit)+parseInt(assignAndAssist);
		var height=0;
		if(shownum==5)height = 44;
		if(shownum==4)height = 100;
		if(shownum==3)height = 130;
		if(shownum==2)height = 200;
		if(shownum==1)height = 400;
		
		
		if(assignAndAssign == '1'){
			document.getElementById('dealTr').style.display = '';
			document.getElementById('dealList').style.height=height+'px';
			document.getElementById('dealActorTable').style.height=(height-10)+'px';
		}else{
			document.getElementById('dealTr').style.display = 'none';
		}
		
		if(assignAndAudit == '1'){
			document.getElementById('audit').style.display = '';
			document.getElementById('auditList').style.height=height+'px';
		}
		
		if(assignAndReassign=='1'){
			document.getElementById('reassign').style.display = '';
			document.getElementById('reassignList').style.height=height+'px';
		}
		
		if(assignAndCopy=='1'){
			document.getElementById('copy').style.display = '';
			document.getElementById('copyList').style.height=height+'px';
		}
		
		if(assignAndAssist=='1'){
			document.getElementById('assist').style.display = '';
			document.getElementById('assistList').style.height=height+'px';
		}
		
	}
}
</script>
</body>
</html>
