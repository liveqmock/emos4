<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name="sm_t_roleInfo" /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,71);
		}
		window.onload = function() 
		{
			setCenter(0,71);
			loadTree();
		}
		var tree;
		function loadTree()
		{
			tree=new dhtmlXTreeObject("center","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			tree.enableCheckBoxes(1);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/common/roleTree.action");
			tree.loadXML("${ctx}/common/roleTree.action?id=0");
			tree.enableHighlighting(0);
			//tree.setOnClickHandler(doHandler)
		}
		function doHandler(){
			var ids = tree.getAllChecked();
			var textArr = ids.split(',');	
			var text='';
			for(var i=0;i<textArr.length;i++){
				var t = tree.getUserData(textArr[i],"text");
				text += t+',';
			}
			if(text!="")
				text = text.substring(0,text.length-1);   
			if(text=="undefined")
				text = '';
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
		<div class="add_scroll_div_x" id="center"></div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_confirm"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="doHandler();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	</div>
  </body>
</html>
