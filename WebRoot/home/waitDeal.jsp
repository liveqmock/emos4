<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
  	<head>
	  	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		request.setAttribute("basePath", basePath);
		%>
	    <link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/default/easyui.css">
	    <link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/icon.css">
	    <link rel="stylesheet" type="text/css" href="style/css/icon.css">
	    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.min.js"></script>
	    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	    <script>
	    $(function(){
	    	var sheetTypes = $("#div_sheetTypes");//tabs
	    	sheetTypes.css("width","100%");
	    	var sheets = $("#iframe_sheets");//待办列表
	    	
	    	$.post("${basePath}/home/getWaitingDealSheetsType.action",{},function(data){
	    		var options = "iconCls:'icon-gk-all'";
	    		var tabHtml = "<div title='全部' data-options=\""+options+"\"></div>"
	    		
	    		$.each(data,function(index,item){//添加工单类别tab
	    			var sheetTypeClass = getSheetTypeClass(item.schema);
	    			options = sheetTypeClass != null ? "iconCls:'"+sheetTypeClass+"'" : "";
	    			
	    			tabHtml += "<div title='"+item.name+"' id='"+item.schema+"' data-options=\""+options+"\"></div>"
	    		});
	    		sheetTypes.append(tabHtml);
	    		//初始化选项卡
	    		sheetTypes.tabs({
	    			onSelect : function(title,index){
	    				if(title == "全部")
	    					sheets.attr("src","${basePath}/home/listWaitDealByType.action");
	    				else
	    					sheets.attr("src","${basePath}/home/listWaitDealByType.action"+"?baseSchema="+sheetTypes.tabs("getTab",index).panel("options").id);
	    				sheetTypes.tabs("update",{
	    					tab : sheetTypes.tabs("getTab",index),
	    					options : {
	    						content : sheets
	    					}
	    				});
    				}
    			});
//     			sheetTypes.tabs("add",{
// 	    			 title:"全部"
// 	    		});
// 				sheetTypes.tabs("update",{
//    					tab : sheetTypes.tabs("getTab",index),
//    					options : {
//    						content : sheets
//    					}
//    				});
    			sheetTypes.tabs("select",0)//模拟点击,调整右侧滚动条
	    	},"json");
	    });
	    
	    //根据schema取得工单
	    function getSheet(){
	    
	    }
	    
	    //根据流程标识确定使用的样式
	    function getSheetTypeClass(baseSchema){
	    	if(baseSchema == "CDB_SERVICEREQUEST") return "icon-gk-sr";
	    	if(baseSchema == "CDB_CHANGE") return "icon-gk-chg";
	    	if(baseSchema == "CDB_RELEASE") return "icon-gk-release";
	    	if(baseSchema == "CBD_INCIDENT") return "icon-gk-in";
	    	return null;
	    }
	    </script>
	</head>
	<body style="margin: 0px;overflow: hidden;height: 100%;">
	    <div id="div_sheetTypes" style=""></div>
	    <iframe id="iframe_sheets" style="width:100%;height:305px;border: 0;" src="" FRAMEBORDER="0"></iframe>
  	</body>
</html>
