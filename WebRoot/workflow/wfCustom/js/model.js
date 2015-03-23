/**
*删除流程自定义模型方法
*liuchuanzu 20100601
*entrycount 流程实例数
*id	流程自定义模型id
*/
function delModel(){
		var objs = document.getElementsByName('checkid');
		var propString = '';
		for(var i=0; i<objs.length;i++){
			if(objs[i].checked == true){
				propString += ',' + objs[i].value;
			}
		}
		if(propString == ''){
			alert('请选择');
		} else {
			if(confirm('确定版本吗？')){
				propString = propString.substring(1);
				$('#propString')[0].value = propString;
				$('#form')[0].action='${ctx}/wfcustom/delModel.action';
				$('#form')[0].submit();
			}
		}
	}

/**
*添加流程自定义模型方法
*liuchuanzu 20100601
*/
function addModel(){
	window.open('${ctx}/wfcustom/toEditOrAddModel.action','_blank','height=600,width=800,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
}



/**
*修改流程自定义模型方法
*liuchuanzu 20100601
*/
function editModel(code){
	window.open('${ctx}/wfcustom/toEditOrAddModel.action?modelCode='+code,'_blank','height=600,width=800,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no');
}


/**
 *初始化页面方法
 */
 function init(){
 	
 }