<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>配置项搜索</title>
	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/core/basePath.jsp"%>
	<link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/common/plugin/jquery-easyui-1.4.1/themes/icon.css">
    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="${basePath}/common/plugin/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/common/plugin/jquery-blockui/jquery.blockUI.js"></script>
	<script>
	function showDetail(ciclass,gid){
		window.open($basePath+"/ciRelevance/listAttributeByCI.action?ciclass="+ciclass+"&gid="+gid,'detail','');
	}
	
	$(function(){
		//渲染配置项下拉树
		var ciclassesTree = $("#ciclass").combotree({
			required:true,
			panelWidth:400,
			panelHeight:500,
			missingMessage:"必填",
			onClick:function(node){
				$("#ciclass").val(node.id);
			}
		});
		//加载配置项数据
		$.post("${basePath}ciRelevance/getAllCIClassesJson.action",function(data){
			ciclassesTree.combotree({
				data:data
			});
		},"json");
		
	});
	
	function searchCI(){
		if($("#cisearchForm").form("validate")){
			$.blockUI({message:"数据加载中"});
			$("#listCI").load($basePath+"ciRelevance/listCI.action",{ciclass:$("#ciclass").val(),ciname:$("#ciname").val()},function(){
				$.unblockUI();
			});
		}
	}
	
	function saveCIRelevance(){
		$.post($basePath+"ciRelevance/saveCIRelevance.action?"+$("#ciInfosForm").serialize()+"&ciRelevance.baseschema=${ciRelevance.baseschema}&ciRelevance.baseid=${ciRelevance.baseid}",function(data){
			if(data=="success"){
				window.close();
			}else{
				alert("配置关联失败");
			}
		});
	}
	</script>
</head>
<body style="">
<form id="cisearchForm" action="" method="post">
	配置项类型<input id="ciclass" style="width:200px;">
	配置项名称<input id="ciname" type="text" />
	<input class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"  type="button" id="Confirm" value="搜索" onclick="searchCI();"/>
	<input class="button" onmouseover="this.className='buttonhover'" onmouseout="this.className='button'"  type="button" value="关联" onclick="saveCIRelevance()"/>
</form>
<div id="listCI" style="width: 100%;height: 800px;overflow: scroll;"></div>
</body>
</html>
