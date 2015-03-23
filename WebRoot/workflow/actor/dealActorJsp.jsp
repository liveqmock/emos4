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
<script src="${ctx}/workflow/actor/js/dealActorJsp.js"></script>
<title>
<eoms:lable name="wf_actor_assignUser" />
</title>
<script type="text/javascript">
		window.onresize = function(){
		  setCenter(0,70);
		}
		
		window.onload = function(){
		    setCenter(0,70);
			init();
	    	getPageMenu('div1_1','div1');
	    	PageMenuActive('div1_1','div1');
		}
		
		function msup(){
		 	ev = window.event;
			var left ='clientX: ' + ev.clientX + ' scrollLeft:' + document.body.scrollLeft + '  clientLeft:' + document.body.clientLeft;
			var top = 'clientY: ' + ev.clientY + '  scrollTop:' + document.body.scrollTop  + '  clientTop:' + document.body.clientTop;
			document.getElementById('X').value = left;
			document.getElementById('Y').value = top;
		}
		
		isselect = '<%=request.getParameter("radio")%>';
		wfVersion = '<%=request.getParameter("tplId")%>';
		action_sign = '<%=request.getParameter("actionType")%>';
	</script>
<style>
</style>
</head>
<body>
<div class="content" >
  <div class='title_right'>
    <div class='title_left'> <span class='title_bg'> <span class='title_icon2'>
      <eoms:lable name="wf_actor_currentPosition"/>
      </span> </span> <span class='title_xieline'> </span> </div>
  </div>
  <div class="scroll_div scroll_div12" id="center" >
    <table class="add_user" border="0" >
      <tr>
        <td width="30%" valign="top"><!--选择要派发的组或人-->
          <fieldset class="fieldset_style" >
            <legend>
            <div>请选择</div>
            </legend>
            <div class="blank_tr"></div>
            <div id="baseinfo" style=" width:285px;">
              <div class="tab_bg">
                <div class="tab_show" id="div1_1" onclick="PageMenuOnclick('div1_1','div1','1')"> <span>
                  <eoms:lable name="wf_actor_depUserTree" />
                  </span> </div>
                <div class="tab_hide" id="div1_2" onclick="PageMenuOnclick('div1_2','div2','2')"> <span>
                  <eoms:lable name="wf_actor_role" />
                  </span> </div>
              </div>
              <div id="div1" style="display:none; width:290px;" >
                <iframe src=""  frameborder="0" id="deptree" name="center" scrolling="no" style="width: 100%;height:500px;"></iframe>
              </div>
              <div id="div2" style="display:none; width:290px;">
                <iframe src=""  frameborder="0" id="roletree" name="center" style="width: 100%;height: 500px" scrolling="no"></iframe>
              </div>
            </div>
          </fieldset></td>
        <td  valign="top"><!--已经点击按钮添加要派发的组或人-->
          <table class="add_user"  border="0">
            <tr>
              <td width="10%"></td>
              <td valign="top"><div  class="dialogue" id="baseinfo" style="padding-top:5px;">
                  <table style="margin:5px 10px 5px 10px; width:100%">
                    <tr>
                      <td width="75" align="right">派发说明 :</td>
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
            <tr style="height: 40%;display:none;" valign="top" id="nextTr">
              <td valign="middle" align="center"><input type="button" value="<eoms:lable name="wf_actor_assign_title3"/>
                " class="add_button" onclick="addDealActor('NEXT');" /> </td>
              <td valign="top"><div class="listdealactor1">派发列表</div>
                <div id="nextList" class="listdealactor2">
                  <div class="listdealactor3" id="nextActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="assignTr">
              <td valign="middle" align="center"><input type="button" value="<eoms:lable name="wf_actor_assign_title3"/>
                " class="add_button"  onclick="addDealActor('ASSIGN');" /> </td>
              <td valign="top"><div class="listdealactor1">派发列表</div>
                <div id="assignList" class="listdealactor2">
                  <div  class="listdealactor3" id="assignActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="audit">
              <td valign="middle" align="center"><input type="button" value="审批" class="add_button" onclick="addDealActor('AUDIT');" /></td>
              <td valign="top"><div class="listdealactor1">审批列表</div>
                <div id="auditList" class="listdealactor2">
                  <div class="listdealactor3" id="auditActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="reaudit">
              <td valign="middle" align="center"><input type="button" value="转审" class="add_button" onclick="addDealActor('REAUDIT');" /></td>
              <td valign="top"><div class="listdealactor1">转审列表</div>
                <div id="reauditList" class="listdealactor2">
                  <div class="listdealactor3" id="reauditActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="orgaudit">
              <td valign="middle" align="center"><input type="button" value="会审" class="add_button" onclick="addDealActor('ORGANIZEAUDIT');" /></td>
              <td valign="top"><div class="listdealactor1">会审列表</div>
                <div id="orgauditList" class="listdealactor2">
                  <div class="listdealactor3" id="organizeauditActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="reassign">
              <td valign="middle" align="center"><input type="button" value="移交" class="add_button" onclick="addDealActor('REASSIGN');" /></td>
              <td valign="top"><div class="listdealactor1">移交列表</div>
                <div id="reassignList" class="listdealactor2">
                  <div class="listdealactor3" id="reassignActorTable"></div>
                </div></td>
            </tr>
            <tr style="height: 40%;display:none;" valign="top" id="makecopy">
              <td valign="middle" align="center"><input type="button" value="<eoms:lable name="wf_addCopyActor"/>
                " class="add_button" onclick="addDealActor('MAKECOPY');" /> </td>
              <td valign="top"><div class="listdealactor1">抄送列表</div>
                <div id="makecopyList"  class="listdealactor2">
                  <div class="listdealactor3" id="makecopyActorTable"></div>
                </div></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </div>
  <div class="add_bottom">
    <input type="button" value="<eoms:lable name="com_btn_save"/>
    " class="save_button" onclick="formsubmit();window.close();" />
    <input type="button" value="<eoms:lable name="com_btn_cancel"/>
    " class="cancel_button" onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'" onclick="window.close();" /> </div>
</div>
<div id='time' class="timenewstyle" style="display:none;">
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

//按钮逻辑
function titleInit(){
	if(action_sign == 'NEXT') {
		document.getElementById('nextTr').style.display = '';
	} else if(action_sign != 'NEXT') {
		var shownum= parseInt(opener.assign)+parseInt(opener.reassign)+parseInt(opener.audit)+parseInt(opener.makecopy)+parseInt(opener.reaudit);
		var height=0;
		if(shownum==5)height = 44;
		if(shownum==4)height = 100;
		if(shownum==3)height = 130;
		if(shownum==2)height = 200;
		if(shownum==1)height = 400;
		if(action_sign == 'ASSIGN') {
			if(opener.assign) {
				document.getElementById('assignTr').style.display = '';
				document.getElementById('assignList').style.height=height+40+'px';
			}
			if(opener.audit) {
				document.getElementById('audit').style.display = '';
				document.getElementById('auditList').style.height=height+40+'px';
			}
			if(opener.makecopy) {
				document.getElementById('makecopy').style.display = '';
				document.getElementById('makecopyList').style.height=height+40+'px';
			}
		}
		if(action_sign == 'REASSIGN') {
			if(opener.reassign) {
				document.getElementById('reassign').style.display = '';
				document.getElementById('reassignList').style.height=height+40+'px';
			}
		}
		if(action_sign == 'REAUDIT') {
			if(opener.reaudit) {
				document.getElementById('reaudit').style.display = '';
				document.getElementById('reauditList').style.height=height+40+'px';
			}
		}
		if(action_sign == 'AUDIT') {
			if(opener.audit) {
				document.getElementById('audit').style.display = '';
				document.getElementById('auditList').style.height=height+40+'px';
			}
			if(opener.makecopy) {
				document.getElementById('makecopy').style.display = '';
				document.getElementById('makecopyList').style.height=height+40+'px';
			}
		}
		if(action_sign == 'MAKECOPY') {
			if(opener.makecopy) {
				document.getElementById('makecopy').style.display = '';
				document.getElementById('makecopyList').style.height=height+40+'px';
			}
		}
		if(action_sign == 'ORGANIZEAUDIT') {
			if(opener.orgaudit) {
				document.getElementById('orgaudit').style.display = '';
				document.getElementById('orgauditList').style.height=height+40+'px';
			}
		}
	}
}
</script>
</body>
</html>
