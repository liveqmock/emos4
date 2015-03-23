var dimensionMap = new Map();//用户MAP
var roleCode = '';


/**
 *处理人对象及属性
 *liuchuanzu
 *2010-06-17
 */
function JsDimension(){
	sign = '';		//标识,是否已添加,未添加：0；已添加：1
	code = '';		//维度code
	name = '';		//维度name
	orderby = 0;	//排序
}



//初始化方法
function init(){
	dimensionTableView();
	dimensionOptionView();
}

 
 /**
 *处理人列表显示(JsTable实现)
 */ 
 function dimensionTableView(){
 		var cList = new Array();
				for(var i=0;i<dimensionMap.keySet().size();i++){
					var key = dimensionMap.keySet().get(i);
					var dimension = dimensionMap.get(key);
					if(dimension.sign == '1'){
						var data = [
						key
						,'<p align="center" >' + dimension.name + ' </p>'
						,'<p align="center" >' + dimension.code + ' </p>'
						,'<input width="20px" type="text" id="' + key + '" value="' + dimension.orderby + '" onchange="toSetOrderBy(this.id,this.value)" />'
						,'<center><img src="../workflow/sheet/images/del.png" id="' + key + '" align="center" onclick="delDimension(' + key + ')" /></center>'
						];
						cList.push(data);
					}
				}
				
				var jt = new JsTable('dimensionTables', true, 'tableborder', null, null, null, null, null, null,
		        [
		            new JsCell(false, 'id', '', [], 0, [], ''),
		            new JsCell(true, 'name', '<b><center>维度名</center></b>', [], 1, [], ''),
		            new JsCell(true, 'code', '<b><center>维度标识</center></b>', [],2, [], ''),
		            new JsCell(true, 'orderby', '<b><center>排序</center></b>', [],3, [], ''),
		            new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="../workflow/sheet/images/del.png" onclick="delDimension(\'{@COL0}\')"/></center>')
		        ],cList);
		       jt.draw(document.getElementById('dimensionTable'));
 }
 
 
 //维度下拉框数据
 function dimensionOptionView(){
	var preSelect = document.getElementById('dimensionOption');
	preSelect.innerHTML = '';
	for(var i=0;i<dimensionMap.keySet().size();i++){
		var pre = dimensionMap.get(dimensionMap.keySet().get(i));
		if(pre.sign == '0'){
		   var objOption=document.createElement("OPTION");
		       objOption.value=pre.code;
		       objOption.text=pre.name;
		       preSelect.add(objOption);
		}	
	}
 }
 
 //添加维度
 function addDimension(){
 	var code = document.getElementById('dimensionOption').value;
 	var obj = dimensionMap.get(code); 
 	if(obj != null){
 		obj.sign = '1';
 		dimensionMap.put(code,obj);
 	}
	 dimensionTableView();
	 dimensionOptionView();
 }
 
 //修改排序
 function toSetOrderBy(code,orderby){
 	var obj = dimensionMap.get(code);
	if(obj != null){
		obj.orderby = orderby;
		dimensionMap.put(code,obj);
	}
 }
 
 //删除维度
function delDimension(code){
	var obj = dimensionMap.get(code);
	if(obj != null){
		obj.sign = '0';
		dimensionMap.put(code,obj);
	}
 	dimensionTableView();
 	dimensionOptionView();
 }
 
 //表单提交
 function formsubmit(){
 	var bl = true;
 	var dimensionCodes = '';
 	var orderby = '';
 	for(var i=0;i<dimensionMap.keySet().size();i++){
 		var key = dimensionMap.keySet().get(i);
 		var obj = dimensionMap.get(key);
 		if(obj.sign == '1'){
			dimensionCodes += ',' + key;
			if(obj.orderby == null || obj.orderby == ''){
				bl = false;
				break;	
			}
			orderby +=',' + obj.orderby;
		}
	}
	if(bl == true){
		if(dimensionCodes != ''){
			dimensionCodes = dimensionCodes.substring(1);
			orderby = orderby.substring(1);
		}
		document.getElementById('dimensionCodes').value = dimensionCodes;
		document.getElementById('dimensionOrderby').value = orderby;
		document.getElementById('sheetform').submit();
	}else{
		alert('请填写排序数字！');
	}
 }
 
  