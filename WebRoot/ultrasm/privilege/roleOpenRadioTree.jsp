<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name="sm_t_roleInfo" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0, LAYOUT_FORM_OPEN);
		}
		window.onload = function() 
		{
			setCenter(0, LAYOUT_FORM_OPEN);
			loadTree();
		}
		var tree;
		function doHandler(key)//单选
		{
			if('1'=='${param.enableCbx}')
			{
				return;
			}
			var name = tree.getSelectedItemText();
	    	var id =tree.getSelectedItemId();
	    	if(id=="nodata")
	    	{
	    		return;
	    	}
	    	var parentid = tree.getUserData(id,'parentId');
			var callerWindowObj = dialogArguments;
			if('${param.form_name}'!='')
			{
				if('${param.input_name}'!='')
				{
					callerWindowObj.document.${param.form_name}.${param.input_name}.value=name;
				}
				if('${param.input_id}'!='')
				{
					callerWindowObj.document.${param.form_name}.${param.input_id}.value=id;
				}
				<c:if test="${!empty param.input_parentid}">
					if('${param.input_parentid}'!='')
					{
						callerWindowObj.document.${param.form_name}.${param.input_parentid}.value=parentid;
					}
				</c:if>
			}
			window.close();
		}
		function returnCbx()//复选
		{
			var ids = tree.getAllChecked();
			var textArr = ids.split(',');	
			var text='';
			for(var i=0;i<textArr.length;i++)
			{
				var t = tree.getUserData(textArr[i],"text");
				text += t+',';
			}
			if(text!="")
			{
				text = text.substring(0,text.length-1);
			}
			if(text=="undefined")
			{
				text = '';
			}
			var callerWindowObj = dialogArguments;
			if('${param.form_name}'!='')
			{
				if('${param.input_name}'!='')
				{
					callerWindowObj.document.${param.form_name}.${param.input_name}.value=text;
				}
				if('${param.input_id}'!='')
				{
					callerWindowObj.document.${param.form_name}.${param.input_id}.value=ids;
				}
			}
			window.close();
		}
		function loadTree()
		{
			tree=new dhtmlXTreeObject("center","100%","100%",0);
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
			if('1'=='${param.enableCbx}')
			{
				tree.enableCheckBoxes(1);
			}
			else
			{
				tree.enableCheckBoxes(2);
			}
			tree.enableTreeLines(true);
			tree.enableHighlighting(2);
			tree.setXMLAutoLoading("${ctx}/common/roleTree.action");
			var parentid = '${param.parentid}';
			var roleIds = '${roleIds}';
			if(roleIds=='')
			{
				if(parentid==''||parentid=='undefined')
				{
					tree.loadXML("${ctx}/common/roleTree.action?id=0");
				}     
				else
				{
					tree.loadXML("${ctx}/common/roleChildrenTree.action?id="+parentid);
				}
			}
			else
			{
				tree.loadXML("${ctx}/common/roleTree.action?id=0&roleIds="+roleIds);
			}
			tree.setOnClickHandler(doHandler);
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name="sm_subt_roleChoose" /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	   </div>
		<div id="center"></div>
	</div>
	<div class="add_bottom">
		<c:if test="${param.enableCbx=='1'}">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="returnCbx();" id="submitButton"/>
		</c:if>
		<input type="button" value="<eoms:lable name="com_btn_cancel"/>" class="cancel_button"
			onmouseover="this.className='cancel_button_hover'" onmouseout="this.className='cancel_button'"
			onclick="window.close();"/>
	</div>
  </body>
</html>
