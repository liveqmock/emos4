<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    //String multiple = request.getParameter("multiple");// 1：单选 其它多选
	//String isUser = request.getParameter("isUser");// 0：部门  1：人员  2：默认(人和部门)
 %>
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
		<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
		<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
		<script language="javascript">
	window.onresize = function() 
	{
	  setCenter(0,56);
	}
	window.onload = function() 
	{
	  setCenter(0,56);
	}
	</script>	
		<title>选择下拉菜单</title>
	</head>
	<body>
	  <div class="content">
	       <div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2">请您选择</span>
					<span class="title_xieline"></span>
				</div>
			</div>
	
		  <div class="add_scroll_div" id="center">
		  
			<table border="0" width="600px" align="center">
			<form method="post" name="myform">
			 <tr height="30px"></tr>
				<tr>
					<td width="43%" border="0">
						<div class="select_tree" id="treeboxbox_tree"></div>
					</td>
					<td width="14%" align="center">
						<input type="button" value="添加"
						class="select_add_button"	onmouseover="this.className='select_add_hover'" 
  		  					onmouseout="this.className='select_add_button'" 
							onClick="moveOption(document.getElementById('right'))">
						<br>
						<br>
						<input type="button" value="删除" class="select_del_button"
						onmouseover="this.className='select_del_hover'" 
  		  					onmouseout="this.className='select_del_button'" 
							onClick="delOption(document.getElementById('right'))">
						<br>
					</td>
					<td width="43%"  border="0" color="#000000">
						<c:choose>
					        	<c:when test="${multiple=='1'}">
					        		<select style="width: 100%; height:260px;" name="right" size="100" ondblclick="delOption(document.getElementById('right'))">
					        	</c:when>
					        	<c:otherwise>
					        		<select style="width: 100%; height:260px;" multiple name="right" size="100" ondblclick="delOption(document.getElementById('right'))">
					        	</c:otherwise>
					    </c:choose>
					    				<c:forEach items="${rightData}" var="rightselectOptionBean">
											<option value="${rightselectOptionBean.key}">${rightselectOptionBean.value}</option>
										</c:forEach> 
									</select>
					</td>
				</tr>
				</form>
			</table>
			</div>
			
				
			<div class="add_bottom">
			  <input type="button" value="<eoms:lable name="com_btn_save"/>"
							class="save_button"
							onmouseover="this.className='save_button_hover'"
							onmouseout="this.className='save_button'"
							onClick="returnValue();">
						</input>
				<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
							class="cancel_button"
							onmouseover="this.className='cancel_button_hover'"
							onmouseout="this.className='cancel_button'"
							onClick="window.close();">
			</div>
		</div>
		<script language="JavaScript">
		<!--
			//部门组织树
			tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);//参数一是树所在页面组件的id,参数四是根结点id,可是是任意值或者字符串
			//tree.setSkin('dhx_skyblue');//样式名称
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");//设置树所使用的图片目录地址
		
			tree.enableCheckBoxes(1);//1-带选择框的模式 (非1)-不带选择框的模式
			tree.enableTreeLines(true);//是否显示结点间的连线,默认false
			var siUserSel = '${isUser}';
			tree.setXMLAutoLoading("${ctx}/common/depuserTree.action?isSelectType="+siUserSel);   
			tree.loadXML("${ctx}/common/depuserTree.action?id=0&isSelectType="+siUserSel);
			
			tree.enableThreeStateCheckboxes(false);
			
			//添加数据
		    function moveOption(e2){
		    	var ids;
		    	var isRadio = '${multiple}';//是否单选
				if(isRadio=='1'){//单选
				   ids = tree.getAllChecked();
				   if(ids!=''){
				   		var idArr = ids.split(',');
				   		if(idArr.length>1){
				   			alert('|单选模式|只能进行某一项选择!');
				   		}else{
							var text = tree.getUserData(ids,"text");
							var rightValue = getvalue(document.getElementById('right'));
					   		if(rightValue!=''){//检查该值是否已经被选择
	 						    var e2Arr = rightValue.split(',');
	 						    var isExist = "";
								for(var a=0;a<e2Arr.length;a++){
									if(ids==e2Arr[a]){
										isExist = 'true';
										break;
									}							
								}
								if(isExist==''){
									//alert('|单选模式|只能进行某一项选择!');
									clearOptions(e2);
									e2.options.add(new Option(text, ids));
								}else{
									alert('|'+text+'|已经在选择列表中!');
								}
							}else{
								 e2.options.add(new Option(text, ids));
							}
				   		}
				   }else{
				   		alert('没有选择任何数据项!');
				   }
				}else{//多选
				   ids = tree.getAllChecked();//得到选择的id集合
				   if(ids!=''){
						var idArr = ids.split(',');
						for (var i = 0; i < idArr.length; i++) {//遍历数据并添加
							var pid = tree.getUserData(idArr[i],"id");
							var text = tree.getUserData(idArr[i],"text");
							var rightValue = getvalue(document.getElementById('right'));
					   		if(rightValue!=''){//检查该值是否已经被选择
	 						    var e2Arr = rightValue.split(',');
	 						    var isExist = "";
								for(var a=0;a<e2Arr.length;a++){
									if(pid==e2Arr[a]){
										isExist = 'true';
										break;
									}							
								}
								if(isExist==''){
									e2.options.add(new Option(text, pid));
								}else{
									alert('|'+text+'|已经在选择列表中!');
								}
							}else{
								 e2.options.add(new Option(text, pid));
							}
						}
				    }else{
				    	alert('没有选择任何数据项!');
				    }
				}
			}
			
			//删除数据
			function delOption(e1){
				for(var i=0;i<e1.options.length;i++){
				  if(e1.options[i].selected){
					   e1.remove(i);
				  }
			   }
			}
			
			//返回值到父页面
			function returnValue(){
			   var resultText = gettext(document.getElementById('right'));
			   var resultValue = getvalue(document.getElementById('right'));
			   window.opener.document.getElementById('${input_id}').value=resultValue
			   window.opener.document.getElementById('${input_name}').value=resultText;
			   window.close();
			}
			
			//获取右边框的key值
			function getvalue(geto){
				 var resultArray = new Array();
				 for(var i=0;i<geto.options.length;i++){
				  	resultArray.push(geto.options[i].value);
				 }
				 return resultArray.join();
			}
			
			//获取右边框的显示值
			function gettext(geto){
				var resultArray = new Array();
				 for(var i=0;i<geto.options.length;i++){
				  	resultArray.push(geto.options[i].text);
				 }
				 return resultArray.join();
			}

			//清空options集合
			function clearOptions(colls){
			     var length = colls.length;
			     for(var i=length-1;i>=0;i--){
			               colls.remove(i);
			     }
			}
		//-->
		</script>
	</body>
</html>