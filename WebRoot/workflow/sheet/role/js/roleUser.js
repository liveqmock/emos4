var userMap = new Map();//用户MAP
var users = '';
//var users = 'Demo:Demo';

//初始化方法
function init(){
	users = document.getElementById('userName').value;
	if(users != '' && users != null && users != 'null'){
		var user = users.split(',');
		for(var i=0;i<user.length;i++){
		var temp = user[i].split(':');
			userMap.put(temp[0],temp[1]);
		}
	}
	userTableView();
}

 
 /**
 *处理人列表显示(JsTable实现)
 */ 
 function userTableView(){
 		var charge = document.getElementById('charge').value;
 		var cList = new Array();
				for(var i=0;i<userMap.keySet().size();i++){
						var key = userMap.keySet().get(i);
						var actor = userMap.get(key);
						var radioHtml = "<p align='center' ><input type='radio' name='userRadio' value='" + key + "'></p>"
						if(charge == key) {
							radioHtml = "<p align='center' ><input type='radio' name='userRadio' value='" + key + "' checked></p>"
						}
						var data = [
						key
						,radioHtml
						,'<p align="center" >' + actor + ' </p>'
						,'<center><img src="../workflow/sheet/images/del.png" id="' + key + '" align="center" onclick="delUser(' + key + ')" /></center>'
						];
						cList.push(data);
				}
				
				var jt = new JsTable('userTables', true, 'tableborder', null, null, null, null, null, null,
		        [
		            new JsCell(false, 'id', '', [], 0, [], ''),
		            new JsCell(true, 'charge', '<b><center>负责人</center></b>', [], 1, [], ''),
		            new JsCell(true, 'name', '<b><center>用户名</center></b>', [], 2, [], ''),
		            new JsCell(true, 'button','<b><center>操作</center></b>',[['width', 30]], -1, [], '<center><img src="../workflow/sheet/images/del.png" onclick="delUser(\'{@COL0}\')"/></center>')
		        ],cList);
		       jt.draw(document.getElementById('userinfo'));
 }
 
 //添加用户
 function addUser(){
 	document.getElementById('usertree').contentWindow.getDepAndUser();
	var actorStr = document.getElementById('usertree').contentWindow.returnStr;
	var actors = actorStr.split(';');
	var actorIds = '';
	var actorNames = '';
	for(var i=0;i<actors.length;i++){
		var type = actors[i].substring(0,1);
		var infos = actors[i].substring(2).split(',');
		if(type == 'U'){//用户
			userMap.put(infos[2],infos[1]);
		}
	}
	userTableView();
 }
 
 //删除用户
function delUser(name){
 	userMap.remove(name);
 	userTableView();
 }
 
 //表单提交
 function formsubmit(){
 	//设置负责人
 	var ras = document.getElementsByName('userRadio');
 	if(ras != null && ras.length > 0) {
 		for(var i=0;i<ras.length > 0;i++) {
 			if(ras[i].checked) {
 				document.getElementById('charge').value = ras[i].value;
 			}
 		}
 	}
 	
 	var userName = '';
 	for(var i=0;i<userMap.keySet().size();i++){
 		var key = userMap.keySet().get(i);
		userName += ',' + key + ':' + userMap.get(key);
	}
	if(userName != ''){
		userName = userName.substring(1);
	}
	document.getElementById('userName').value = userName;
	document.getElementById('sheetform').submit();
 }
 
  