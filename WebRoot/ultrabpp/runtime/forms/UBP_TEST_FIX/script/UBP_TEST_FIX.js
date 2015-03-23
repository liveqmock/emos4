function bpp_init() {
	ClientContext.submit("action", "ToInspect", function() {
			//取主表单建议复核人字段，如果不为空，将建议复核人放到公共参数里，后续环节要用到
			var suggestCheckPerson = F("SuggestPerson").G();
			return true;
	});
	ClientContext.submit("action", "ToStepGroup", function() {
			//取主表单建议复核人字段，如果不为空，将建议复核人放到公共参数里，后续环节要用到
			var suggestCheckPerson = $("#reCheckPerson_AssignString")[0].value;
			if(suggestCheckPerson!=null && suggestCheckPerson!='')
			{
				ClientContext.setAttr("ATT_CHECKPERSON",suggestCheckPerson);
			}
			if( F("APerson").G()=="" && F("BPerson").G()=="" && F("CPerson").G()=="" )
			{
				alert("请至少选择一条分支的处理人");
				return false;
			}
			return true;
	});
	ClientContext.submit("action", "ToAllLine", function() {
			//取主表单建议复核人字段，如果不为空，将建议复核人放到公共参数里，后续环节要用到
			var suggestCheckPerson = $("#reCheckPerson_AssignString")[0].value;
			if(suggestCheckPerson!=null && suggestCheckPerson!='')
			{
				ClientContext.setAttr("ATT_CHECKPERSON",suggestCheckPerson);
			}
			return true;
	});
	
}