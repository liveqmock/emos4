<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<base target="_self"/>
   	<title><eoms:lable name='sm_lb_addRoleResOp' /></title>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,120);
		}
		window.onload = function() 
		{
			setCenter(0,120);
		}
		function selectRes()
		{
			var parentroleid = '${param.parentid}';//父角色ID
			showModalDialog('${ctx}/roleResOpManager/getResourceById.action?proleid='+parentroleid,window,'center:yes;help:no;scroll:yes;status:no;dialogWidth:550px;dialogHeight:400px');
			if(document.getElementById('resids').value=='')
			{
				return;
			}
			var selfroleid = '${param.theroleid}';//本级角色ID
			var proleid_temp = parentroleid;//父角色ID
			var resid_temp = document.getElementById('resids').value;//资源ID
			$.getJSON("${ctx}/roleResOpManager/getResourceOp.action",{roleid:selfroleid,proleid:proleid_temp,resid:resid_temp},function(jsonStr)
			{
				if(jsonStr.length==0)
				{
					var fieldset = document.getElementById('fieldset1');
					fieldset.removeChild(fieldset.childNodes[1]);
					$('<div align="center" style="font-weight:normal"><eoms:lable name='com_lb_noData' />!</div>').appendTo("#fieldset1");
				}
				else
				{
					var fieldset = document.getElementById('fieldset1');
					fieldset.removeChild(fieldset.childNodes[1]);
					var tab_temp = $("<table id='optab' width='100%' style='font-weight:normal'></table>").appendTo("#fieldset1");
					var row_size = 3;//这里设置每行显示的操作个数
					var str_temp='<tr>';
					$.each(jsonStr,function(i,json){
						if(json.own==true)
						{
							str_temp = str_temp + "<td><input type='checkbox' disabled checked name='opckb' value='"+json.opid+"'/>"+json.opname+"</td>";
						}
						if(json.own==false)
						{
							str_temp = str_temp + "<td><input type='checkbox' name='opckb' value='"+json.opid+"'/>"+json.opname+"</td>";
						}
						if((i+1)%row_size==0)
						{
							str_temp = str_temp + "</tr>";
							$(str_temp).appendTo("#optab");
							str_temp = "<tr>";
						}
						if((i+1)==jsonStr.length && (i+1)%row_size!=0)
						{
							str_temp = str_temp + "</tr>";
							$(str_temp).appendTo("#optab");
							str_temp = "<tr>";
						}
					});
					document.getElementById('submitbtn').style.visibility = 'visible';
				}
			});
		}
		function submit()
		{
			if(document.getElementById('resids').value=='')
			{
				alert("<eoms:lable name='sm_msg_chooseResource' />！")
				return;
			}
			var cbxArr = $(":checkbox:checked:not(:disabled)");
			if(cbxArr.length==0)
			{
				alert("<eoms:lable name='sm_msg_chooseOperate' />！");
				return;
			}
			var opids = '';
			for(var i=0;i<cbxArr.length;i++)
			{
				opids = opids + cbxArr[i].value + ',';
			}
			opids = opids.substring(0,opids.lastIndexOf(','));
			document.roleResOpForm.opids.value = opids;
			document.forms[0].submit();
		}
	</script>
  </head>
  <body>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name='sm_lb_addRoleResOp' /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	    </div>
	    <div style="height:40px;" align="center">
	    	<form action="${ctx}/roleManager/theRoleResOpSave.action" name="roleResOpForm" method="post">
	    		<input type="hidden" name="roleids" value="${param.theroleid}"/>
	    		<input type="hidden" name="opids" value=""/>
	    		<table width="100%">
	    			<tr>
	    				<td width="20%"><eoms:lable name='sm_lb_chooseResource' />：</td>
	    				<td width="50%">
	    					<input type="text" name="resNames" id="resNames" value="" class="textInput" readonly/>
							<input type="hidden" name="resids" id="resids" value=""/>
	    				</td>
	    				<td align="left">
	    					<input type="button" name="button3" id="button3" value="<eoms:lable name="com_btn_choose"/>"
								class="operate_button"
								onmouseover="this.className='operate_button_hover'"
								onmouseout="this.className='operate_button'" 
								onclick="selectRes();" />
	    				</td>
	    			</tr>
				</table>
			</form>
	    </div>
	    <div class="add_scroll_div_x" id="center">
	    	<fieldset class="fieldset_style" style="width:90%" id="fieldset1">
				<legend><eoms:lable name='sm_lb_chooseOperation' /></legend>
				<div align="center" style="font-weight:normal"><eoms:lable name='sm_lb_preOperationTip' />!</div>
			</fieldset>
	    </div>
	    <div class="add_bottom">
			<input type="button" id="submitbtn" value="<eoms:lable name="com_btn_save"/>" class="save_button" style="visibility:hidden"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="submit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</div>	
  </body>
</html>
