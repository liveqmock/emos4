<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name="sm_subt_depUserTree" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,75);
		}
		window.onload = function() {
			setCenter(0,75);
		}
		
		function formSubmit()
		{
			document.getElementById('tree').contentWindow.getDepAndUser();
			var datas = document.getElementById('tree').contentWindow.returnStr;
			var divTagArr = document.frames["tree"].document.getElementById("inertData").getElementsByTagName("DIV");
			var retPid = '';
			var retText = '';
			for(var len=0;len<divTagArr.length;len++){
				retPid += divTagArr[len].id+',';
				retText += divTagArr[len].idText+','; 
			}
			if(retPid!='')
				retPid = retPid.substring(0,retPid.length-1);
			if(retText!='')
				retText = retText.substring(0,retText.length-1);
			
			//根据格式分解  D:id,name,fullname;U:id,name,loginname;
			datas = datas.substring(0,datas.length-1);//去除最后一位的';'
			var input_name = '${param.input_name}';
			var input_id = '${param.input_id}';
			var input_other = '${param.input_other}';
			var isSelectType = '${param.isSelectType}';
			var targetDataArr = '${param.targetDataArr}';
			var values = '';
			var ids = '';
			var others = '';
			
			if(datas != '')
			{
				var data = datas.split(';');
				for(var i=0;i<data.length;i++)
				{
					teps = data[i].split(':');
					if(isSelectType=='1'){
						if(teps[0]=='U'){
							tep = teps[1].split(',');
							if(ids != '')
							{
								ids += ',';
								values += ',';
								others += ',';
							}
							ids += tep[0];
							values += tep[1];
							others += tep[2];
						}else{
							ids += '';
							values += '';
							others += '';
						}
					}else{
						tep = teps[1].split(',');
						if(ids != '')
						{
							ids += ',';
							values += ',';
							others += ',';
						}
						ids += tep[0];
						values += tep[1];
						others += tep[2];
					}
				}
			}
			if(retPid != ''){
				opener.document.getElementById(input_id).value = retPid;
				opener.document.getElementById(input_name).value = retText;
			}else{
				opener.document.getElementById(input_id).value = ids;
				opener.document.getElementById(input_name).value = values;
			} 
			if(input_other != '')
				opener.document.getElementById(input_other).value = others; 
			window.close();
		}
	</script>
  </head>
  <body style="overflow:hidden;">
	  <div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
					<span class="title_icon2"><eoms:lable name='sm_subt_depUserTree' /></span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		<div style="overflow:hidden;" id="center">
			<iframe src="${ctx}/common/tools/depListTree.jsp?isRadio=${param.isRadio}&isSelectType=${param.isSelectType}&targetDataArr=${param.targetDataArr}" id='tree' scrolling="no" frameborder="0" width="100%" height="100%"></iframe>
		</div>
		
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" onmouseout="this.className='save_button'" onclick="formSubmit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
				onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </div>
  </body>
</html>
