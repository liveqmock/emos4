

/**
*添加流程接口方法
*liuchuanzu 20100601
*/
function addInterface() {
	window.open("${ctx}/wfinterface/toEditOrAddInterface.action", "_blank", "height=242,width=600,top=" + (window.screen.height / 2 - 200) + ",left=" + (window.screen.width / 2 - 250) + ",toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
}


/**
*修改流程接口方法
*liuchuanzu 20100601
*/
function editInterface(interfaceId) {
	window.open("${ctx}/wfinterface/toEditOrAddInterface.action?interfaceCode=" + interfaceId, "_blank", "height=242,width=600,top=" + (window.screen.height / 2 - 200) + ",left=" + (window.screen.width / 2 - 250) + ",toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
}


