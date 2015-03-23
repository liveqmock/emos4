<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String isRadio = request.getParameter("isRadio");//0:单选  1:多选
	String isSelectChild = request.getParameter("isSelectChild");//0:false 1:true
	String isSelectType = request.getParameter("isSelectType");// 0:角色与角色细分  1:角色细分（默认）
	String wfVersion = request.getParameter("wfVersion");
 %>
<html>
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>	
<title>
<eoms:lable name="wf_acotr_tree"/>
</title>
<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
<link href="${ctx}/common/style/${skintype}/css/newgongdan.css"  rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
</head>
<body>
<div id="treeboxbox_tree" class="treeboxbox_tree"></div>
<script type="text/javascript">
			//部门组织树
			var roletree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('csh_vista');//样式名称
			roletree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			
			var isRadioPara = '<%=isRadio%>';
			roletree.enableCheckBoxes(isRadioPara);//1-带选择框的模式 (非1)-不带选择框的模式
			roletree.enableTreeLines(true);//是否显示结点间的连线,默认false
			roletree.setXMLAutoLoading("${ctx}/childrole/getWfRoleChild_tree.action");
			roletree.loadXML("${ctx}/childrole/getWfRole_tree.action?wfVersion=" + '<%=wfVersion%>');
			
			var isSelectChild = '<%=isSelectChild%>';
			roletree.enableThreeStateCheckboxes(1);
			roletree.setOnClickHandler(function(id){getDepAndUser(id);});

			
			//如果是多选,返回选择的数据
			var returnStr = '';//返回选择的字符串集合 格式例如：D:id,name,fullname;U:id,name,loginname;
			function getDepAndUser()//返回选择的部门和人
			{
				returnStr = '';
				if(isRadioPara=='0')//单选
					ids = roletree.getSelectedItemId();
				if(isRadioPara=='1')//多选
				    ids = roletree.getAllChecked();//得到选择的id集合
				
				if(ids!=''){
					var idArr = ids.split(',');
					for (var i = 0; i < idArr.length; i++) {
						var text = roletree.getUserData(idArr[i],"text");
						var orgtype = roletree.getUserData(idArr[i],"type");
						returnStr += 'R' + ':' + idArr[i] + ',' + text + ";";
					}
				}
			}
			
			//删除节点,并取消"勾选"
		function delItem(id){
			if(id!=''){//删除底部该条记录
				if(id.indexOf("_")!=-1)
				{//含有该字符
					id = id.substr(0,id.indexOf("_"));
				}
				var pid = roletree.getUserData(id,"id");
				if(pid!=null)
				{
					$("#"+pid).remove();
				}
				else
				{
					$("#"+id).remove();//如果是其他应用穿过来的ID(targetDataArr参数) 则根据ID清楚
				}
			}
			roletree.setCheck(id,'2');
		}
		</script>
</body>
</html>
