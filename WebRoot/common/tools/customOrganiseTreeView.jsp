<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%
	
	// 推荐弹出该页面大小
    // w:287px  h:315px
	
	String isRadio = request.getParameter("isRadio");//0:单选  1:多选
	String isSelectType = request.getParameter("isSelectType");// 0:部门  1:人员  2:默认(人和部门)
	String sendTreeId = request.getParameter("sendTreeId");
	String rearchUserOrDep = request.getParameter("rearchUserOrDep");//查询条件值
	if(rearchUserOrDep==null)
		rearchUserOrDep = "";
	//?isRadio=0&isSelectType=2
 %>
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css"/>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		function submitQuery()
		{
			document.datatree.submit();
		}
	</script>
  </head>
  
  <body>
  	<div class="content">
  		<c:if test="${param.sendTreeId!=null && param.sendTreeId!=''}">
	  		<form action="" method="post" name="datatree">
		  		<div class="configroletree">
				  <li><input type="text" name="rearchUserOrDep" style="width:320px;height:21px;" value="<%=rearchUserOrDep%>"></li>
				  <li><div class="ser1" onmouseover="this.className='ser2'" onmouseout="this.className='ser1'" onclick="submitQuery();"></div></li>
				</div>
			</form>
		</c:if>
		<!-- <div id="treeboxbox_tree" style="clear:both;background:#ffffff;border:1px #d2e5fe solid;" > -->
		<div id="treeboxbox_tree" style="clear:both;background:#ffffff;border:1px #d2e5fe solid;border-top:none; height:400px;"></div>
			<c:if test="${param.sendTreeId==null || param.sendTreeId==''}">
				<eoms:lable name="sm_lb_nullSendTreeTip"/>！
			</c:if>
		</div>
		<script type="text/javascript">
			tree=new dhtmlXTreeObject("treeboxbox_tree","100%","90%",0);
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
			var isRadioPara = '<%=isRadio%>';
			tree.enableThreeStateCheckboxes(1);
			tree.enableCheckBoxes(isRadioPara);
			tree.enableTreeLines(true);
			var sendTreeId = "<%=sendTreeId%>";
			if(sendTreeId!="")
			{
				tree.setXMLAutoLoading("${ctx}/common/customOrgnized.action?isSelectType=<%=isSelectType%>&formCustSendTree=<%=sendTreeId%>");   
				tree.loadXML("${ctx}/common/customOrgnized.action?id=0&rearchUserOrDep=<%=URLEncoder.encode(URLEncoder.encode(rearchUserOrDep,"UTF-8"),"utf-8")%>&isSelectType=<%=isSelectType%>&formCustSendTree=<%=sendTreeId%>");
			}
			tree.setOnClickHandler(function(id){getDepAndUser(id);});
			//如果是多选,返回选择的数据
			var returnStr = '';//返回选择的字符串集合 格式例如：D:id,name,fullname;U:id,name,loginname;
			function getDepAndUser()//返回选择的部门和人
			{
				returnStr = '';
				var ids = '';
				if(isRadioPara=='0')//单选
					ids = tree.getSelectedItemId();
				if(isRadioPara=='1')//多选
				    ids = tree.getAllCheckedBranches();//得到选择的id集合
				
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
						//returnStr += orgtype+':'+pid+','+text+','+name +";";
						returnStr += orgtype+':'+pid+';';
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
						if(orgtype=="D" || orgtype=="1")
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
				if(depids==""&&depnames==""&&userids==""&&usernames=="")
				{
					return "";
				}
				else
				{
					return depids+';'+depnames+';'+userids+';'+usernames;
				}
			}
		</script>
	</div>
  </body>
</html>
