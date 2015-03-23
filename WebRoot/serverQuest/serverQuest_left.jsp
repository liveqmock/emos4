<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script language="javascript">
		var id = "";//选择的项的id
		window.onresize = function()
		{
			setCenter(0,30);
		}
		window.onload = function()
		{
			initServerQuestTree();
			setCenter(0,30);
		}
		function menuDel()
		{
			if(id == "")
			{
				alert("<eoms:lable name='sm_msg_seletefirst'/>!");
				return false;
			}
			if(confirm("<eoms:lable name='sm_msg_delMenuinfo'/>？"))
			{

				$.get("${ctx}/serverQuest/delServerQuest.action?id="+id,
					{	
						"time_":new Date()
					},
					function(result){
						refresh(result);
						id ="";
					});
			}
		}
		
		function addnew(level)//level标识当前级别或是下级
		{
			if(id == "")
			{
				alert("<eoms:lable name='sm_msg_seletefirst'/>!");
				return false;
			}
			window.open("${ctx}/serverQuest/addserverQuestInfo.action?id="+id+"&levertype="+level,"rightFrame2");
		}
	function editSort(){
		if(id == "")
		{
			alert("<eoms:lable name='sm_msg_seletefirst'/>!");
			return false;
		}
		window.open("${ctx}/serverQuest/addserverQuestInfo.action?id="+id+"&levertype=","rightFrame2");
	}	
	
	function initServerQuestTree(){
		//菜单目录树
		tree=new dhtmlXTreeObject("center","97%","98%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
		//tree.setSkin('dhx_skyblue');//样式名称
		tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
		tree.enableCheckBoxes(2);//1-带选择框的模式 (非1)-不带选择框的模式
		tree.enableTreeLines(true);//是否显示结点间的连线,默认false
		tree.setXMLAutoLoading("${ctx}/serverQuest/getXML.action?time_="+new Date());
		tree.loadXML("${ctx}/serverQuest/getXML.action?id=0&time_="+new Date());
		//tree.enableHighlighting(1);
		tree.setOnClickHandler(doHandler)
	}
		function doHandler(key)
		{
			var dilever="";
			var name = tree.getSelectedItemText();
	    	id=tree.getSelectedItemId();
	    	dilever= tree.getUserData(id,"dilever");
	    	if(dilever=='3'){
	    		document.getElementById("page_loweradd_button").style.display="none";
	    		editSort();
	    	}else{
	    		document.getElementById("page_loweradd_button").style.display="";
	    		//window.open("${ctx}/serverQuest/serverQuestInfoList.action?serverQuestId="+id+"&dilever="+dilever,"rightFrame2");
	    		openWindowEx("POST","${ctx}/serverQuest/serverQuestInfoList.action",{"serverQuestId":id,"dilever":dilever},"rightFrame2");
	    	}
			
		}
		function refresh(itemid)
		{
			var text=tree.getItemText(itemid);
			if(text!='0'){
				tree.refreshItem(itemid);
			}else{
				tree.refreshItem('0');
			}
			id="";
			//tree.openItem(itemid);
			window.open("${ctx}/serverQuest/serverQuestInfoList.action?serverQuestId="+itemid,"rightFrame2");
		}
		
	</script>
  </head>
  
  <body>
  	<div class="content">
  	<div class="page_div_bg">
				<div class="page_div">
					<eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" onmouseout="this.className='page_del_button'" onclick="menuDel()" text="com_btn_delete" operate="com_delete"/>
					<eoms:operate cssclass="page_sameadd_button" id="page_sameadd_button" onmouseover="this.className='page_sameadd_button_hover'" onmouseout="this.className='page_sameadd_button'" onclick="addnew('current')" text="sm_btn_currentadd" operate="com_add"/>
					<eoms:operate cssclass="page_loweradd_button" id="page_loweradd_button" onmouseover="this.className='page_loweradd_button_hover'" onmouseout="this.className='page_loweradd_button'" onclick="addnew('lower')" text="sm_btn_loweradd" operate="com_add"/>
	  				<eoms:operate cssclass="page_edit_button" id="page_edit_button" onmouseover="this.className='page_edit_button_hover'" onmouseout="this.className='page_edit_button'"    onclick="editSort();"  text="com_btn_edit" operate="com_add"/>
			</div>	
		</div>		
		<div class="" id="center"></div>
  	</div>
  </body>
</html>
