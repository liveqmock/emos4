/**
*启用流程版本方法
*liuchuanzu 20100601
*id	流程版本id
*/
function startFlow(id){
	document.getElementById('sheetform').action = 'startWf.action';
	document.getElementById('wfVersionId').value = id;
	document.getElementById('sheetform').submit();
}


/**
*停用流程版本方法
*liuchuanzu 20100601
*id	流程版本id
*/
function stopFlow(id){
	document.getElementById('sheetform').action = 'stopWf.action';
	document.getElementById('wfVersionId').value = id;
	document.getElementById('sheetform').submit();
}


/**
*删除流程版本方法
*liuchuanzu 20100601
*entrycount 流程实例数
*id	流程版本id
*/
function delFlow(entrycount,id){
	if(entrycount == 0){
		if(confirm('确定删除该版本吗？')){
			document.getElementById('sheetform').action = 'delWf.action';
			document.getElementById('wfVersionId').value = id;
			document.getElementById('sheetform').submit();
		}
	}else{
		alert('存在实体，不允许删除该流程');
	}
	
}



/**
*添加流程版本方法
*liuchuanzu 20100601
*/
function addFlow(){
	var baseCode = document.getElementById('baseCode').value;
	var baseName = document.getElementById('baseName').value;
	window.open('../workflow/design/v3/vml/wfDesigner.jsp?mode=new&basecode=' + baseCode + '&basename='+baseName);
}

/**
*修改流程版本方法
*liuchuanzu 20100601
*/
function editFlow(id){
	var baseCode = document.getElementById('baseCode').value;
	var baseName = document.getElementById('baseName').value;
	window.open('../workflow/design/v3/vml/wfDesigner.jsp?mode=edit&basecode=' + baseCode + '&id='+id+ '&baseName='+baseName);
}