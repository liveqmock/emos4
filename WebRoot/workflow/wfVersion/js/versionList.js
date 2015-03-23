/**
*启用流程版本方法
*liuchuanzu 20100601
*id	流程版本id
*/
function startFlow(id){
	filedform.action = 'startWf.action';
	filedform.wfVersionId.value = id;
	filedform.submit();
}


/**
*停用流程版本方法
*liuchuanzu 20100601
*id	流程版本id
*/
function stopFlow(id){
	filedform.action = 'stopWf.action';
	filedform.wfVersionId.value = id;
	filedform.submit();
}


/**
*删除流程版本方法
*liuchuanzu 20100601
*entrycount 流程实例数
*id	流程版本id
*/
function delFlow(entrycount,id, isPublish){
	if(isPublish == 1) {
		alert('该版本正在启用，不允许删除');
		return false;
	}
	if(entrycount == 0){
		if(confirm('确定删除该版本吗？')){
			document.getElementById('filedform').action = 'delWf.action';
			document.getElementById('wfVersionId').value = id;
			document.getElementById('filedform').submit();
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
	window.open('../workflow/design/v4/WFDesigner.jsp?mode=new&baseschema=' + baseCode + "&modeltype=EOMS");
}

/**
*修改流程版本方法
*liuchuanzu 20100601
*/
function editFlow(code, entryCount){
	var baseCode = document.getElementById('baseCode').value;
	window.open('../workflow/design/v4/WFDesigner.jsp?mode=edit&baseschema=' + baseCode + '&tplid='+code + "&modeltype=EOMS" + "&entryCount=" + entryCount);
}