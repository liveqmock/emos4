<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.ultrapower.eoms.common.core.util.*"%>
<%
	
    // w:287px  h:315px
	
	String isRadio = request.getParameter("isRadio");//0:单选  1:多选 
	String isSelectChild = request.getParameter("isSelectChild");//0:false  1:true路径上的节点全选上
	String isSelectType = request.getParameter("isSelectType");// 0:部门  1:人员  2:默认(人和部门)
	String rearchUserOrDep = request.getParameter("reachUserOrDep");//查询条件值ll
	String topId = StringUtils.checkNullString(request.getParameter("topId"));
	if("".equals(topId)) {
		topId = "0";
	}
	String targetDataArr = StringUtils.checkNullString(request.getParameter("targetDataArr"));//默认选中值
	if(rearchUserOrDep==null)
		rearchUserOrDep = "快速查找......";
 %>
<html>
  <head>   
    <title>部门树</title>
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<%@ include file="/common/plugin/jquery/jquery.jsp"%>	
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
	
		var returnData = '';
		function rearchUserOrDep()
		{
			//if(check()) {
				document.datatree.submit();			
			//}
		}
		
		window.onload = function()
		{
			$.get("${ctx}/common/getViewData.action", {targetDataArr: "<%=targetDataArr%>"},
			  function(data){
			    $("#inertData").append(data);
			  }); 
		}
		
  
		function check() {
		//	var reg = /^[\w\u4E00-\u9FA5]+$/;
		//	var val = document.getElementById("researchTxt").value;
		//	if(!reg.test(val)) {
		//		alert("<eoms:lable name='sm_msg_chooseDepOrUserConstraint'/>！");
		//		return false;
		//	} 
		//	return true;
		}
	</script>
  </head>
  
  <body>
  	<div class="content" style="heigth:300px;">
  		<form action="" method="post" name="datatree" onsubmit="return check();">
	  		<div class="configroletree">
			  <li><input type="text" name="reachUserOrDep" id="researchTxt" maxlength="80" style="width:260px" value="<%=rearchUserOrDep%>" onfocus="if (value =='快速查找......'){value =''}" onblur="if (value ==''){value='快速查找......'}"></li>
			  <li><div class="ser1" onmouseover="this.className='ser2'" onmouseout="this.className='ser1'" onclick="rearchUserOrDep();"></div></li>
			</div>
		</form>
		<div id="treeboxbox_tree" style="clear:both;background:#ffffff;border:1px #d2e5fe solid;border-top:none; height:300px;"></div>
		<div id="treeData" style="overFlow-y:scroll; height:120px; padding:2px 4px 0px 4px; margin-top:2px;">
			<div>已经选择：</div>
			<div id="inertData" style="padding:0px 4px 4px 4px;">
			</div>
		</div>
		<script type="text/javascript">
			//部门组织树
			tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			var isRadioPara = '<%=isRadio%>';
		
			tree.enableCheckBoxes(isRadioPara);//1-带选择框的模式 (非1)-不带选择框的模式URLDecoder.decode(menuName,"UTF-8");
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/common/depuserTree.action?isSelectType=<%=isSelectType%>&targetDataArr=<%=targetDataArr%>");   
			tree.loadXML("${ctx}/common/depuserTree.action?id=<%=topId%>&first=true&isSelectType=<%=isSelectType%>&rearchUserOrDep=<%=URLEncoder.encode(URLEncoder.encode(rearchUserOrDep.equals("快速查找......")?"":rearchUserOrDep,"UTF-8"),"utf-8")%>");
			
			var isSelectChild = '<%=isSelectChild%>';
			tree.enableThreeStateCheckboxes(isSelectChild);
			tree.setOnClickHandler(function(id){getDepAndUser(id);});
			
			tree.onCheckBoxClick = function (e) {
				if (!this.treeNod.callEvent("onBeforeCheck", [this.parentObject.id, this.parentObject.checkstate]))
				{
						return;
				}
				if (this.parentObject.dscheck)
				{
						
						return true;
				}
				if (this.treeNod.tscheck) 
				{
					
					if (this.parentObject.checkstate == 1) 
					{
						this.treeNod._setSubChecked(false, this.parentObject);
					} else 
					{
						this.treeNod._setSubChecked(true, this.parentObject);
					}
				} else 
				{
					if (this.parentObject.checkstate == 1) 
					{
						delOneData(this.parentObject.id);
						this.treeNod._setCheck(this.parentObject, false);
					} else 
					{
						//自定义函数
						//insertOneData(this.parentObject.id);
						this.treeNod._setCheck(this.parentObject, true);
					}
				}
				this.treeNod._correctCheckStates(this.parentObject.parentObject);
				return this.treeNod.callEvent("onCheck", [this.parentObject.id, this.parentObject.checkstate]);
			};
			
			//如果是多选,返回选择的数据
			var returnStr = '';//返回选择的字符串集合 格式例如：D:id,name,fullname;U:id,name,loginname;
			function getDepAndUser()//返回选择的部门和人
			{
				returnStr = '';
				var ids = '';
				if(isRadioPara=='0')//单选
					ids = tree.getSelectedItemId();
				if(isRadioPara=='1')//多选
				    ids = tree.getAllChecked();//得到选择的id集合
				if(ids!=''){
					var idArr = ids.split(',');
					for (var i = 0; i < idArr.length; i++) {
					    if(idArr[i].indexOf("_") > 0 )   
      					    idArr[i] = idArr[i].substring(0,idArr[i].indexOf("_"));
      					
      					var pid = tree.getUserData(idArr[i],"id");
						var text = tree.getUserData(idArr[i],"text");
						var orgtype = tree.getUserData(idArr[i],"type");
						var name =  tree.getUserData(idArr[i],"name");
						returnStr += orgtype+':'+pid+','+text+','+name + ',' + idArr[i] +";";
					}
					return returnStr;
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
					return depids+';'+depnames+';'+userids+';'+usernames; 
				}
			}
			
		//text：已选数据名称
		//type: 人 或者 部门
		//preText：上级节点名称
		//eg：黄正勇(人)(上级:IT需求审核组)
		//text,type,preText
		//getParentId(itemId) 获取父节点id
		//getItemText 获取节点text
		
		/*
		function insertData(ids)
		{
			$("#treeData").empty();
			if(ids!=''){
					var idArr = ids.split(',');
					for (var i = 0; i < idArr.length; i++) {
					    if(idArr[i].indexOf("_") > 0 )   
      					    idArr[i] = idArr[i].substring(0,idArr[i].indexOf("_"));
      					var pid = tree.getUserData(idArr[i],"id");
						var text = tree.getUserData(idArr[i],"text");
						var orgtype = tree.getUserData(idArr[i],"type");
						var type = '';
						if(orgtype=="U")
						{
							type = '人';
						}
						else
						{
							type = '部门';
						}
						var parentId = tree.getParentId(pid);//获取父节点id
						var parentText = tree.getItemText(parentId);//获取父节点text
						$("#treeData").append("<li id=" + pid+"><b>" + text+"</b> ["+type+"]"+" （上级："+parentText+"）；<a href='javascript:;'  onclick='delItem(\""+pid+"\")'>删除</a></li>");
					}
			}
		}
		*/
		
		function insertOneData(id)
		{
			if(id!=''){
				if(id.indexOf("_")!=-1)
				{//含有该字符
					id = id.substr(0,id.indexOf("_"));
				}
				var text = tree.getUserData(id,"text");
				var orgtype = tree.getUserData(id,"type");
				var type = '';
				if(orgtype=="U")
				{
					type = '人';
				}
				else
				{
					type = '部门';
				}
				var parentId = tree.getParentId(id);//获取父节点id
				var parentText = tree.getItemText(parentId);//获取父节点text
				var pid = tree.getUserData(id,"id");
				$("#inertData").append("<div id=" + pid+" idText="+text+"><b>" + text+"</b> ["+type+"]"+"；<img src=\"../style/blue/images/del_user.jpg\" onclick='delItem(\""+id+"\")' style=\"margin-left:2px; margin-bottom:-1px;\" alt=\"删除\"></img></div>");
			}
		}
			
		//删除底部对应id数据
		function delOneData(id)
		{
			if(id!=''){
				if(id.indexOf("_")!=-1)
				{//含有该字符
					id = id.substr(0,id.indexOf("_"));
				}
				var pid = tree.getUserData(id,"id");
				$("#"+pid).remove();
			}
		}
		
		//删除节点,并取消"勾选"
		function delItem(id){
			if(id!=''){//删除底部该条记录
				if(id.indexOf("_")!=-1)
				{//含有该字符
					id = id.substr(0,id.indexOf("_"));
				}
				var pid = tree.getUserData(id,"id");
				if(pid!=null)
				{
					$("#"+pid).remove();
				}
				else
				{
					$("#"+id).remove();//如果是其他应用穿过来的ID(targetDataArr参数) 则根据ID清楚
				}
			}
			tree.setCheck(id,'2');
		}
		</script>
	</div>
  </body>
</html>
