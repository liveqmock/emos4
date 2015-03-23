<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.ultrapower.eoms.common.core.util.*"%>
<%
	
	String isRadio = request.getParameter("isRadio");//0:单选  1:多选 
	String isSelectType = request.getParameter("isSelectType");// 0:部门  1:人员  2:默认(人和部门)
	String rearchUserOrDep = request.getParameter("reachUserOrDep");//查询条件值ll
	String topId = StringUtils.checkNullString(request.getParameter("topId"));
	String returnId = StringUtils.checkNullString(request.getParameter("returnId"));//回填的控件id，xxx@xxx
	if("".equals(topId)) {
		topId = "0";
	}
	String targetDataArr = StringUtils.checkNullString(request.getParameter("targetDataArr"));//默认选中值
	if(rearchUserOrDep==null)
		rearchUserOrDep = "快速查找......";
	
	request.setAttribute("returnId", returnId);
	request.setAttribute("isRadio", isRadio);
 %>
<html>
  <head>   
    <title>部门树</title>
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<%@ include file="/common/plugin/jquery/jquery.jsp"%>	
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript" src="${ctx}/serverQuest/js/peopleTree.js"></script>
	<script type="text/javascript">
	
		var returnData = '';
		var BaseSummary='';
		function rearchUserOrDep()
		{
			document.datatree.submit();			
		}
		
		window.onload = function()
		{
			$.get("${ctx}/common/getViewData.action", {targetDataArr: "<%=targetDataArr%>"},
			  function(data){
			    $("#inertData").append(data);
			}); 
		}
  
	</script>
  	<link rel="stylesheet" href="css/peopleTree.css" type="text/css"></link>
  </head>
  
  <body>
  	<div class="content">
  		<form action="" method="post" name="datatree" onsubmit="">
	  		<div class="configroletree">
			  <li><input type="text" name="reachUserOrDep" id="researchTxt" maxlength="20" style="width:260px" value="<%=rearchUserOrDep%>" onfocus="if (value =='快速查找......'){value =''}" onblur="if (value ==''){value='快速查找......'}"></li>
			  <li><div class="ser1" onmouseover="this.className='ser2'" onmouseout="this.className='ser1'" onclick="rearchUserOrDep();"></div></li>
			</div>
		</form>
		<div id="treeboxbox_tree" style="clear:both;background:#ffffff;border:1px #d2e5fe solid;border-top:none; height:300px;"></div>
		<ul id="checkedItems" class="checkedItem"></ul>
		<script type="text/javascript">
			addExistItemInOpener("${returnId}");
			//部门组织树
			tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
			var isRadioPara = "${isRadio}";
			tree.enableCheckBoxes(${isRadio});//1-带选择框的模式 (非1)-不带选择框的模式URLDecoder.decode(menuName,"UTF-8");
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			tree.setXMLAutoLoading("${ctx}/common/depuserTree.action?returnId=<%=returnId%>&isSelectType=<%=isSelectType%>&targetDataArr=<%=targetDataArr%>");   
			tree.loadXML("${ctx}/common/depuserTree.action?returnId=<%=returnId%>&id=<%=topId%>&first=true&isSelectType=<%=isSelectType%>&rearchUserOrDep=<%=URLEncoder.encode(URLEncoder.encode(rearchUserOrDep.equals("快速查找......")?"":rearchUserOrDep,"UTF-8"),"utf-8")%>");
			if(isRadioPara == "1"){//多选情况下在加载完目录树时，选中已有的项
				tree.attachEvent("onXLE", function(){
					setChecked("${returnId}")
				});
			}
	
			tree.enableThreeStateCheckboxes(false);
			//tree.setOnClickHandler(function(id){getDepAndUser(id);});
			tree.attachEvent("onCheck", function(id, state){
				checkPeopleTree(id, state);
			});
			
			//如果是多选,返回选择的数据
			var pid='';
			var text='';
			function getDepAndUser()//返回选择的部门和人
			{
				var ids = '';
				if(isRadioPara=="0")//单选
					ids = tree.getSelectedItemId();
				if(isRadioPara=="1")//多选
				    ids = tree.getAllChecked();//得到选择的id集合
				if(ids!=''){
					var idArr = ids.split(',');
					for (var i = 0; i < idArr.length; i++) {
					    if(idArr[i].indexOf("_") > 0 )   
      					    idArr[i] = idArr[i].substring(0,idArr[i].indexOf("_"));
      					if("U"==tree.getUserData(idArr[i],"type")){
      						 pid=  pid+"#;"+tree.getUserData(idArr[i],"type")+"#:"+tree.getUserData(idArr[i],"name");
      					}else{
      						 pid=  pid+"#;"+"G#:"+tree.getUserData(idArr[i],"id");
      					}
						text = text + ","+tree.getUserData(idArr[i],"text");
						var name =  tree.getUserData(idArr[i],"name");
					}
					if(text.length>0){
						text = text.substring(1);
						pid = pid.substring(2);
					}
				}
			}
			
		</script>
		<div align="center">
			<input type="button" onclick="helper('${returnId}');" value="确定">
			<input type="button" onclick="clear2('${returnId}');" value="清空">
		</div>
	</div>
  </body>
</html>
