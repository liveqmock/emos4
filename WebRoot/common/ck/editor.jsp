<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="/common/core/taglibs.jsp"%>
<div><textarea id="editor1" style='width: 100%; height: 100%' name="ckhtml" >${ckhtml}</textarea></div>
<script type="text/javascript" src="${ctx}/common/ck/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	window.onload = function() {
		CKEDITOR.replace('editor1');
	}
</script>