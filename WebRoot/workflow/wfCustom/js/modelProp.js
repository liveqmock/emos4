var propMap;		//模型属性map，key为模型属性code，value为prop(JS对象)
var preFunctionMap;	//key为code，value为JsFunction(JS对象)
var postFunctionMap;	//key为code，value为JsFunction(JS对象)

/**
 *初始化页面方法
 */
 function init(){
 
 	
 	initPropList();
 	jsTableView();
 	
 	
 	preFunctionMap = new Map();
 	postFunctionMap = new Map();
 	initFunctionList();
 	
 	prefuncionSelect();
	jsPreFunctionTableView();
 	
    postfuncionSelect();
 	jsPostFunctionTableView();
 	
 }
 
 //弹出添加属性页面
 function toAddOrEditProp(propcode){
 	var src = "../../../workflow/wfCustom/propEdit.jsp";
 	if(propcode != ""){
 		src+="?propcode="+propcode;
 	} 
 	
 	window.open(src,'_blank','height=220,width=530,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,status=no,location=no,directories=no,resizable=yes');
 	 
 }
 
/**
 *模型属性列表显示(JsTable实现)
 */ 
 function jsTableView(){
 		var cList = new Array();
				for(var i=0;i<propMap.keySet().size();i++){
					var prop = propMap.get(propMap.keySet().get(i));
					if(prop.sign != 'del'){
						var data = [
						prop.propCode
						,'<p  id="' + prop.propCode + '" align="center" onclick="toAddOrEditProp(this.id)" >' + prop.propCode + ' </p>'
						,'<p id="' + prop.propCode + '" align="center" onclick="toAddOrEditProp(this.id)" >' + prop.propName + ' </p>'
						,'<p  id="' + prop.propCode + '" align="center" onclick="toAddOrEditProp(this.id)" >' + prop.propType + ' </p>'
						,'<p  id="' + prop.propCode + '" align="center" onclick="toAddOrEditProp(this.id)" >' + prop.propDict + ' </p>'
						,'<p  id="' + prop.propCode + '" align="center" onclick="toAddOrEditProp(this.id)" >' + prop.propIsNull + ' </p>'
						,'<p id="' + prop.propCode + '" align="center" onclick="toAddOrEditProp(this.id)" >' + prop.propDefaulValue + ' </p>'
						,'<p id="' + prop.propCode + '" align="center" onclick="toAddOrEditProp(this.id)" >' + prop.propOrderBy + ' </p>'
						,'<input type="button" id="' + prop.propCode + '" align="center" onclick="delProp(this.id)" value="删除" />'
						];
						cList.push(data);
					}
				}
				
				var jt = new JsTable('jstables', true, 'tableborder', null, null, null, null, null, null,
		        [
		            new JsCell(false, 'id', '', [], 1, [], ''),
		            new JsCell(true, 'code', '<b><center>属性标识</center></b>', [], 1, [], ''),
		            new JsCell(true, 'name', '<b><center>属性名称</center></b>', [], 2, [], ''),
		            new JsCell(true, 'category', '<b><center>属性类型</center></b>', [], 3, [], ''),
		            new JsCell(true, 'dict', '<b><center>属性字典</center></b>', [], 4, [], ''),
		            new JsCell(true, 'isnull', '<b><center>允许为空</center></b>', [], 5, [], ''),
		            new JsCell(true, 'default', '<b><center>默认值</center></b>', [], 6, [], ''),
		            new JsCell(true, 'orderby', '<b><center>排序</center></b>', [],7, [], ''),
		            new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 80]], -1, [], '<input type="button" class="button" value="删　除" onclick="delProp(\'{@COL0}\')">')
		        ],cList);
		        
		       jt.draw(document.getElementById('propList'));
 }
  
 /**
  * 删除模型属性列表数据
  */
  function delProp(propcode){
  	var prop = propMap.get(propcode);
  	if(prop.sign == 'add'){  		
  		propMap.remove(prop.propCode);
  	}else{
	  	prop.sign = 'del';
	  	propMap.put(prop.propCode,prop);
	}
	jsTableView();
  } 
  
function addMap(sign,propId,propCode,propName,propType,propDict,propIsNull,propDefaulValue,propOrderBy){
	var prop = new JsProp();
	
	prop.sign = sign;
	prop.propId = propId;		//id
	prop.propCode = propCode;		//模型属性标识
	prop.propName = propName;		//模型属性名称
	prop.propType = propType;		//模型属性类型
	prop.propDict = propDict;		//模型属性字典
	prop.propIsNull = propIsNull;		//是否允许为空
	prop.propDefaulValue = propDefaulValue;	//默认值
	prop.propOrderBy = propOrderBy;	//排序
 	propMap.put(propCode,prop);
 	jsTableView();
}

/**
 *将模型属性拼成字符串提交至后台
 */
 function prop2String(){
 	var propString = '';
 	for(var i=0;i<propMap.keySet().size();i++){
			var prop = propMap.get(propMap.keySet().get(i));
			var tempString = '';
			if(prop.sign != 'defaul'){
				tempString += '##' + (prop.sign == '' || prop.sign == null ?null:prop.sign);
				tempString += '##' + (prop.propId == '' || prop.propId == null?null:prop.propId);
				tempString += '##' + (prop.propCode == '' || prop.propCode == null?null:prop.propCode);
				tempString += '##' + (prop.propName == '' || prop.propName == null?null:prop.propName);
				tempString += '##' + (prop.propType == '' || prop.propType==null?null:prop.propType);
				tempString += '##' + (prop.propDict == '' || prop.propDict==null?null:prop.propDict);
				tempString += '##' + (prop.propIsNull == '' || prop.propIsNull==null?null:prop.propIsNull);
				tempString += '##' + (prop.propDefaulValue == '' || prop.propDefaulValue == null?null:prop.propDefaulValue);
				tempString += '##' + (prop.propOrderBy == '' || prop.propOrderBy==null?null:prop.propOrderBy);
				tempString = tempString.substring(2);
				propString += '$$' + tempString;
			}
		}
		propString = propString.replace('undefined','');
		propString = propString.substring(2);
		return propString;
 }
 
 
 					
/********************* 前置函数(begin) ********************************/
 //前置函数下拉框数据初始化
function prefuncionSelect(){
	var preSelect = document.getElementById('prefunctionSlt');
	preSelect.innerHTML = '';
	for(var i=0;i<preFunctionMap.keySet().size();i++){
		var pre = preFunctionMap.get(preFunctionMap.keySet().get(i));
		if(pre.sign == '0'){
		   var objOption=document.createElement("OPTION");
		       objOption.value=pre.code;
		       objOption.text=pre.name;
		       if(MSIE)
		       {
		       		preSelect.add(objOption);
		       }
		       else
		       {
		       		preSelect.appendChild(objOption);
		       }
		}	
	}
}

//前置函数table
function jsPreFunctionTableView(){
 			var cList = new Array();
				for(var i=0;i<preFunctionMap.keySet().size();i++){
					var pre = preFunctionMap.get(preFunctionMap.keySet().get(i));
					if(pre.sign == '1'){
						var data = [
						pre.code
						,'<p style="width:20%;">' + pre.code + ' </p>'
						,'<p style="width:40%">' + pre.name + ' </p>'
						,'<p style="width:40%">' + pre.path + ' </p>'
						,'<input type="button" id="' + pre.code + '" align="center" onclick="delPreFunction(this.id)" value="删除" />'
						];
						cList.push(data);
					}
				}
				
				var jt = new JsTable('preTable', true, 'tableborder', null, null, null, null, null, null,
		        [
		            new JsCell(false, 'id', '', [], 1, [], ''),
		            new JsCell(true, 'code', '<b><center>接口标识</center></b>', [], 1, [], ''),
		            new JsCell(true, 'name', '<b><center>接口名称</center></b>', [], 2, [], ''),
		            new JsCell(true, 'path', '<b><center>接口路径</center></b>', [], 3, [], ''),
		            new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 80]], -1, [], '<input type="button" class="button" value="删　除" onclick="delPreFunction(\'{@COL0}\')">')
		        ],cList);
		        
		       jt.draw(document.getElementById('prefunctiontable'));
 }
 
 //表中删除方法
 function delPreFunction(code){
 	var pre = preFunctionMap.get(code);
 	
 	if(pre != null){
 		pre.sign = '0';
 		preFunctionMap.put(code,pre);
 	}
 	prefuncionSelect();
 	jsPreFunctionTableView();
 }
 
 //添加方法
 function addPreFunction(){
 	var code = document.getElementById('prefunctionSlt').value;
 	var pre = preFunctionMap.get(code);
 	if(pre != null){
 		pre.sign = '1';
 		preFunctionMap.put(code,pre);
 	}
 	prefuncionSelect();
 	jsPreFunctionTableView();
 }

//将选中的接口拼成字符串
function preCode2String(){
	var preCodes  = '';
	for(var i=0;i<preFunctionMap.keySet().size();i++){
		var pre = preFunctionMap.get(preFunctionMap.keySet().get(i));
		if(pre.sign == '1'){
			preCodes += ','+ pre.code; 
		}
	}
	return preCodes.substring(1);
}
/********************* 前置函数(end) ********************************/



 					
/********************* 后置函数(begin) ********************************/
 //后置函数下拉框数据初始化
function postfuncionSelect(){
	var postSelect = document.getElementById('postfunctionSlt');
	postSelect.innerHTML = '';
	for(var i=0;i<postFunctionMap.keySet().size();i++){
		var post = postFunctionMap.get(postFunctionMap.keySet().get(i));
		if(post.sign == '0'){
		   var objOption=document.createElement("OPTION");
		       objOption.value=post.code;
		       objOption.text=post.name;
		       if(MSIE)
		       {
		       		postSelect.add(objOption);
		       }
		       else
		       {
		       		postSelect.appendChild(objOption);
		       }
		       
		       
		}	
	}
}

//后置函数table显示
function jsPostFunctionTableView(){
 	var cList = new Array();
				for(var i=0;i<postFunctionMap.keySet().size();i++){
					var post = postFunctionMap.get(postFunctionMap.keySet().get(i));
					if(post.sign == '1'){
						var data = [
						post.code
						,'<p style="width:20%">' + post.code + ' </p>'
						,'<p style="width:40%">' + post.name + ' </p>'
						,'<p style="width:40%">' + post.path + ' </p>'
						,'<input type="button" id="' + post.code + '" align="center" onclick="delPostFunction(this.id)" value="删除" />'
						];
						cList.push(data);
					}
				}
				
				var jt = new JsTable('postTable', true, 'tableborder', null, null, null, null, null, null,
		        [
		            new JsCell(false, 'id', '', [], 1, [], ''),
		            new JsCell(true, 'code', '<b><center>接口标识</center></b>', [], 1, [], ''),
		            new JsCell(true, 'name', '<b><center>接口名称</center></b>', [], 2, [], ''),
		            new JsCell(true, 'path', '<b><center>接口路径</center></b>', [], 3, [], ''),
		            new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 80]], -1, [], '<input type="button" class="button" value="删　除" onclick="delPostFunction(\'{@COL0}\')">')
		        ],cList);
		        
		       jt.draw(document.getElementById('postfunctiontable'));
 }
 
 //表中删除方法
 function delPostFunction(code){
 	var post = postFunctionMap.get(code);
 	
 	if(post != null){
 		post.sign = '0';
 		postFunctionMap.put(code,post);
 	}
 	postfuncionSelect();
 	jsPostFunctionTableView();
 }
 
 //添加方法
 function addPostFunction(){
 	var code = document.getElementById('postfunctionSlt').value;
 	var post = postFunctionMap.get(code);
 	if(post != null){
 		post.sign = '1';
 		postFunctionMap.put(code,post);
 	}
 	postfuncionSelect();
 	jsPostFunctionTableView();
 }
 
 
 //将选中的接口拼成字符串
function postCode2String(){
	var postCodes  = '';
	for(var i=0;i<postFunctionMap.keySet().size();i++){
		var post = postFunctionMap.get(postFunctionMap.keySet().get(i));
		if(post.sign == '1'){
			postCodes += ','+ post.code; 
		}
	}
	return postCodes.substring(1);
}
/********************* 后置函数(end) ********************************/