<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script src="${ctx}/common/javascript/util.js"></script>
		<script language="javascript">
		window.onresize = function() 
		{
		setCenter(0,32);
		}
		window.onload = function() 
		{
		  setCenter(0,32);
		  setLevelInit();
		}
		
		
		
		  	var dimensionCode = '${dimension.dimensioncode}';
		  	var dimensionName = '${dimension.dimensionname}';
		  	var dimCode = '${dimension.dimCode}';
		  	var dictschema = '${dimension.dictschema}';
		  	var dictfieldid = '${dimension.dictfieldid}';
		  	
		  	if(dictschema == '' && dictfieldid == '') {//这两个属性都为空表示为系统字典表
		  		dimensionName = dimCode;
		  	}
		  	
		 //checkbox选择事件
		  function getValue(obj,values){
		  	var txt = document.getElementById(values).innerText;
		  	var value = values;
		  	if(obj.checked == true){
		  		parent.valueMap.put(dimensionCode+'_'+value,dimensionCode + ':_:' + dimensionName + '=' +value + ':_:' + dimensionName + '=' + value + '::' + txt);
		  		/**
		  		var str = '';
		  		for(var i=0; i<parent.valueMap.keySet().size();i++){
	  				var key = parent.valueMap.keySet().get(i);
		  			str += parent.valueMap.get(key)+'#';
		  		}
		  		alert(str);
		  		**/
		  		
		  	}else{
		  		parent.valueMap.remove(values);
		  	}
		  	
		  }
		  
		  //全选
		  function allChecked(){
		  var obj = document.getElementById('checkidAll');
		  
		  var m = document.getElementsByName("checkid")
			var len = m.length;
			for ( var i=0; i< len; i++)
			{
				 if(m[i].disabled) {continue;}
				 else{
				 	m[i].checked = obj.checked;
				 }
			}
			
			var objs = document.getElementsByName('checkid');
			for(var i=0; i<objs.length; i++){
				var o = objs[i];
				if(obj.checked == true && document.getElementById('tr_'+o.value).style.display != 'none'){
					var txt = document.getElementById(o.value).innerText;
	  				parent.valueMap.put(dimensionCode+'_'+o.value,dimensionCode + ':_:' + dimensionName + '=' +o.value + ':_:' + dimensionName + '=' +o.value + '::' + txt);
				}
				/**
				else{
					parent.valueMap.remove(o.value);
					o.checked = false;
				}
				**/
			}		  	
		  }
		  
		  function setLevelInit(){
		  	var level = '${level}';
		  	var select = document.getElementById('level');
		  	for(var i=1;i<=level;i++){
			  	var objOption=document.createElement("OPTION");
			       objOption.value = i;
			       objOption.innerText = '第' + i + '级';
			       if(MSIE)
			       {
			       		select.add(objOption);
			       }
			       else
			       {
			       		select.appendChild(objOption);
			       }
		  	}
		  	select.value = level;
		  	setValues(level);
		  }
		  
		  //调整维度值显示
		  function setValues(level){
		 	var objs = document.getElementsByTagName('p');
		  	var map = new Map();
		  	for(var i=0;i<objs.length;i++){
		  		var values = objs[i].id.split('.');
		  		var text = objs[i].id;
		  		/**if(level < values.length){
			  		for(var j=0;j<level;j++){
			  			text += '.' + values[j];
			  		}
			  		if(text != ''){
			  			text = text.substring(1);
			  		}
		  		}else{
		  			text = objs[i].id;
		  		}**/
		  		if(level==values.length){
		  				map.put(text,text);
		  				document.getElementById('tr_'+objs[i].id).style.display = '';
		  		}else {
		  				document.getElementById('tr_'+objs[i].id).style.display = 'none';
		  				parent.valueMap.remove(objs[i].id);
		  			}
		  		
		  		
		  		/**if(map.get(text) != null){
		  			document.getElementById('tr_'+objs[i].id).style.display = 'none';
		  			parent.valueMap.remove(objs[i].id);
		  		}else{
		  			map.put(text,text);
		  			document.getElementById('tr_'+objs[i].id).style.display = '';
		  		}**/
		  		
		  		objs[i].innerText =text;
		  	}
		  	
		  	var objs = document.getElementsByName('checkid');
		  	document.getElementById('checkidAll').checked = false;
			for(var i=0; i<objs.length; i++){
				var o = objs[i];
				parent.valueMap.remove(o.value);
				o.checked = false;
				/**
				if(o.checked == true && document.getElementById('tr_'+o.value).style.display != 'none' ){
		  			var value = document.getElementById(o.value).innerText;
					parent.valueMap.put(o.value,dimensionCode + ':' + dimensionName + '=' +value);
				}else{
					o.checked = false;
				}
				**/
			}
			
		  }
	</script>
	</head>

	<body style="background-color:#fafcff">
		<div class="content">
			<table class="add_user" border="0" style="height: 100%; width:250px;">
				<tr>
					<td class="title_line">
						<table width="300">
						 <tr>
						   <td width="100" align="left" style="padding-left:5px;">${dimension.dimensionname}</td>
						   <td align="right" valign="top"><select onchange="setValues(this.value)" style="width: 120px;height:19px;" id="level"></select></td>
						 </tr>
						</table>
					</td>
					
				</tr>
				<tr>
					<td colspan="2">
						<dg:datagrid items="${dimensionValueList}" var="d">
							<dg:gridtitle>
								<tr>
									<th width="25">
										<input id="checkidAll" name="checkidAll" type="checkbox" onclick="allChecked();"></input>
									</th>
									<th width="250">
										<eoms:lable name="wf_sheet_dimensionvalue"></eoms:lable>
									</th>
								</tr>
							</dg:gridtitle>
							<dg:gridrow>
								<tr class="${waitingdealsheet_row}" id="tr_${d.value}" style="cursor: hand">
									<td>
										<input id='checkid' name="checkid" value='${d.value}' type="checkbox" onclick="getValue(this,this.value);"></input>
									</td>
									<td>
										<p id="${d.value}">${d.value}</p>
									</td>
								</tr>
							</dg:gridrow>
						</dg:datagrid>
					</td>
				</tr>
			</table>
			
		</div>
	</body>
</html>
