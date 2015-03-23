<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/common/core/taglibs.jsp"%>
<div><textarea id="editor1" style='width: 100%; height: 100%' name="formhtml" >${formhtml}</textarea></div>
<script type="text/javascript" src="${ctx}/ultraform/editor/ckeditor.js"></script>
<script type="text/javascript"
	src="${ctx}/ultraform/editor/adapters/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/ultraform/editor/adapters/jquery-1.3.2.js"></script>
<script type="text/javascript">
	var dictData = $.ajax( {
		url : "${ctx}//ultraform/editor/ditc.html",
		async : false
	}).responseText;

	window.onload = function() {
		CKEDITOR.replace('editor1');
	}

	function reRenderEditor() {
		var aa = document.getElementById('showDiv').innerText;
		if (aa)
			CKEDITOR.instances.editor1.setData(aa);
	}
	
	function goPreview(){
	document.forms[0].target="_blank";
	document.forms[0].action="${ctx}/ultraform/preview.action";
	document.forms[0].submit();
	}

	function planProcess(f) {
		if (f == 3) {
			if (CKEDITOR.instances.editor1.getData().indexOf('table') <= 0)
				return true;
		}else if (f == 4) {
		CKEDITOR.instances.editor1.setData("");
		}else if (f == 5) {
		goPreview();
		}else {
			return true;
		}
		return false;
	}
</script>