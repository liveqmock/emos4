<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%
	// 推荐弹出该页面大小
    // w:287px  h:315px
	String isRadio = request.getParameter("isRadio");//0:单选  1:多选
	String isSelectChild = request.getParameter("isSelectChild");//0:false 1:true
	String isSelectType = request.getParameter("isSelectType");// 0:部门  1:人员  2:默认(人和部门)
	String sendTreeId = request.getParameter("sendTreeId");
	//?isRadio=1&isSelectType=2&isSelectChild=true
 %>
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
  </head>
  
  <body>
  	<div class="content" id="treeboxbox_tree" style="clear:both;background:#ffffff;border:1px #d2e5fe solid;"></div>
		<script type="text/javascript">
			//部门组织树
			tree=new dhtmlXTreeObject("treeboxbox_tree","100%","90%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('csh_vista');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			var isRadioPara = '<%=isRadio%>';
		
			tree.enableCheckBoxes(isRadioPara);//1-带选择框的模式 (非1)-不带选择框的模式    URLDecoder.decode(menuName,"UTF-8");
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/common/customDepUserTree.action?isSelectType=<%=isSelectType%>&formCustSendTree=<%=sendTreeId%>");   
			tree.loadXML("${ctx}/common/customDepUserTree.action?id=0&isSelectType=<%=isSelectType%>&formCustSendTree=<%=sendTreeId%>");
			var isSelectChild = '<%=isSelectChild%>';
			tree.enableThreeStateCheckboxes(isSelectChild);
			tree.setOnClickHandler(function(id){getDepAndUser(id);});
			
			//如果是多选,返回选择的数据
			var returnStr = '';//返回选择的字符串集合
			function getDepAndUser()//返回选择的部门和人
			{
				returnStr = '';
				var ids = '';
				if(isRadioPara=='0')//单选
					ids = tree.getSelectedItemId();
				if(isRadioPara=='1')//多选
				    ids = tree.getAllCheckedBranches();//得到选择的id集合
				ids = ids.replace(/(^\s*)|(\s*$)/g, "");
				if(ids!="" && ids.lastIndexOf(",")==ids.length-1)
				{
					ids = ids.substring(0,ids.lastIndexOf(","));
				}
				if(ids!=''){
					var idArr = ids.split(',');
					for (var i = 0; i < idArr.length; i++) {
					    if(idArr[i].indexOf("_") > 0 )   
      					    idArr[i] = idArr[i].substring(0,idArr[i].indexOf("_"));
      					var pid = tree.getUserData(idArr[i],"id");
						var text = tree.getUserData(idArr[i],"text");
						var orgtype = tree.getUserData(idArr[i],"type");
						var name =  tree.getUserData(idArr[i],"name");
						var parentid = tree.getUserData(idArr[i],"parentid");
						returnStr += orgtype+','+pid+','+text + ',' + parentid + ";";
					}
					if(returnStr!="")
					{
						returnStr = returnStr.substring(0,returnStr.lastIndexOf(";"));
					}
				}
			}
			function getCheckedInfo()//将选择的部门以及人员的id和name返回：depId;depname;userId;userName
			{
				var depids = '';//部门的id集合,用,分割
				var depnames = '';//部门的名字集合,用,分割
				var userids = '';//用户的id集合,用,分割
				var usernames = '';//用户的名字集合,用,分割
				var ids = tree.getAllChecked();
				if(ids!='')
				{
					var idArr = ids.split(',');
					for (var i = 0; i < idArr.length; i++) 
					{
						var pid = tree.getUserData(idArr[i],"id");
						var text = tree.getUserData(idArr[i],"text");
						var orgtype = tree.getUserData(idArr[i],"type");
						if(orgtype=="D")
						{//部门
							depids += pid + ",";
							depnames +=  text + ",";
						}
						else
						{//人员
							userids += pid + ",";
							usernames += text + ",";
						}
					}
					if(depids!="")
					{
						depids = depids.substring(0,depids.length-1);
					}
					if(depnames!="")
					{
						depnames = depnames.substring(0,depnames.length-1);
					}
					if(userids!="")
					{
						userids = userids.substring(0,userids.length-1);
					}
					if(usernames!="")
					{
						usernames = usernames.substring(0,usernames.length-1);
					}
							
					
					if(depids=="undefined")
					{
						depids = '';
					}
					if(depnames=="undefined")
					{
						depnames = '';
					}
					if(userids=="undefined")
					{
						userids = '';
					}
					if(usernames=="undefined")
					{
						usernames = '';
					}
				}
				return depids+';'+depnames+';'+userids+';'+usernames; 
			}
		</script>
  </body>
</html>
