<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title>选择下拉菜单</title>
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
	</head>
	<body>
	<div class="content">
	       <div class="title_right">
				<div class="title_left">
					<span class="title_bg">
						<span class="title_icon2">请您选择</span>
					</span>	
					<span class="title_xieline"></span>
				</div>
			</div>
	
		  <div class="scroll_div" id="center">
		  <div class="blank_tr"></div>
			<table border="0" width="490px" align="center">
			<form method="post" name="myform">
				<tr >
					<td width="43%" border="0">
						<c:choose>
					        	<c:when test="${multiple=='1'}">
					        		<select style="width:100%; height:381px;" id="left" name="left"  size="100" ondblclick="moveOption(document.getElementById('left'), document.getElementById('right'))">
					        	</c:when>
					        	<c:otherwise>
					        		<select style="width:100%; height:381px;" multiple id="left" name="left" size="100" ondblclick="moveOption(document.getElementById('left'), document.getElementById('right'))">
					        	</c:otherwise>
					   </c:choose>
										<c:forEach items="${leftData}" var="leftselectOptionBean">
											<option value="${leftselectOptionBean.key}">${leftselectOptionBean.value}</option>
										</c:forEach> 
									</select>
					</td>
					<td width="14%" align="center">
						<input type="button" value="添加"
						class="select_add_button"	onmouseover="this.className='select_add_hover'" 
  		  					onmouseout="this.className='select_add_button'" 
							onClick="moveOption(document.getElementById('left'), document.getElementById('right'))">
						<br>
						<br>
						<input type="button" value="删除" class="select_del_button"
						onmouseover="this.className='select_del_hover'" 
  		  					onmouseout="this.className='select_del_button'" 
							onClick="delOption(document.getElementById('right'))">
						<br>
					</td>
					<td width="43%" border="0">
						<c:choose>
					        	<c:when test="${multiple=='1'}">
					        		<select style="width:100%; height:381px;" id="right" name="right" size="100" ondblclick="delOption(document.getElementById('right'))">
					        	</c:when>
					        	<c:otherwise>
					        		<select style="width:100%; height:381px;" multiple id="right" name="right" size="100" ondblclick="delOption(document.getElementById('right'))">
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
			//添加数据
		    function moveOption(e1, e2){
			   for(var i=0;i<e1.options.length;i++){
				  if(e1.options[i].selected){
					   var e = e1.options[i];
					   var isRadio = '${multiple}';//是否单选
					   if(isRadio=='1'){//单选
					   	  var rightValue = getvalue(document.getElementById('right'));
						  if(rightValue!=''){
	 						    var e2Arr = rightValue.split(',');
	 						    var isExist = "";
								for(var a=0;a<e2Arr.length;a++){
									if(e2Arr[a]==e.value){
										isExist = 'true';
										break;
									}							
								}
								if(isExist==''){
								    clearOptions(e2);
									e2.options.add(new Option(e.text, e.value));
								}else{
									alert('|'+e.text+'|已经在选择列表中!');
								}
						   }else{
						   	 e2.options.add(new Option(e.text, e.value));
						   }
					   }else{
					      var rightValue = getvalue(document.getElementById('right'));
						  if(rightValue!=''){
	 						    var e2Arr = rightValue.split(',');
	 						    var isExist = "";
								for(var a=0;a<e2Arr.length;a++){
									if(e2Arr[a]==e.value){
										isExist = 'true';
										break;
									}
								}
								if(isExist==''){
									e2.options.add(new Option(e.text, e.value));
								}else{
									alert('|'+e.text+'|已经在选择列表中!');
								}
						   }else{
						   	 e2.options.add(new Option(e.text, e.value));
						   }
					   }
				  }
			   }
			}
			
			//删除数据
			function delOption(e1){
				for(var i = e1.options.length - 1;i >= 0;i--){
				  if(e1.options[i].selected){
					   e1.remove(i);
				  }
			   }
			}
			
			//返回数据给父页面
			function returnValue(){
				var resultText = gettext(document.getElementById('right'));
				var resultValue = getvalue(document.getElementById('right'));
				window.opener.document.getElementById('${input_id}').value=resultValue
				window.opener.document.getElementById('${input_name}').value=resultText;
				window.close();
			}
			
			//获取option显示值
			function getvalue(geto){
				 var resultArray = new Array();
				 for(var i=0;i<geto.options.length;i++){
				  	resultArray.push(geto.options[i].value);
				 }
				 return resultArray.join();
			}
			
			//获取option的value值
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