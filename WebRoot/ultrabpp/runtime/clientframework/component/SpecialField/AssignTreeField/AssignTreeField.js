function AssignTreeField(fID, fText, rad, isRequire, act, _next) {
	this.fieldType = "AssignTreeField";
	this.fieldID = fID;
	this.text = fText;
	this.radio = rad;
	this.require = isRequire;
	this.action = act;
	this.next = _next;

	this.isVisiable = true;
	this.isReadonly = false;

	this.changeEventMap = new Map();

	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if (display == "none")
		this.isVisiable = false;

	$("#" + this.fieldID + "_ubfbox :button").click(function() {
		AssignTreeField.openAssignTree(fID);
	});

	if (_next == 1) {
		ClientContext.addAssignTags(fID);
	}
}
function AssignTreeField(fID, fText, rad, isRequire, act, _next, actN, sn,selectType) {
	this.fieldType = "AssignTreeField";
	this.fieldID = fID;
	this.text = fText;
	this.radio = rad;
	this.require = isRequire;
	this.action = act;
	this.next = _next;
	this.actionName = (typeof actN == 'undefined') ? '' : actN;
	this.step_no = (typeof sn == 'undefined') ? '' : sn;
	this.selectType = (typeof selectType == 'undefined') ? '' : selectType;

	this.isVisiable = true;
	this.isReadonly = false;

	this.changeEventMap = new Map();

	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if (display == "none")
		this.isVisiable = false;

	$("#" + this.fieldID + "_ubfbox :button").click(function() {
		AssignTreeField.openAssignTree(fID);
	});

	if (_next == 1) {
		ClientContext.addAssignTags(fID);
	}
}
AssignTreeField.prototype.G = function() {
	var field = $("#" + this.fieldID + "_AssignString");
	if (field.length == 0) return null;
	else return field[0].value;
};
AssignTreeField.prototype.G_View = function() {
	var field = $("#" + this.fieldID);
	if (field.length == 0) return null;
	else return field[0].value;
}
AssignTreeField.prototype.S = function(value, displayLabel) {
	var field = $("#" + this.fieldID + "_AssignString");
	if (field.length == 0)
		return null;
	else {
		$("#" + this.fieldID)[0].value = displayLabel;
		field[0].value = value;
	}
	for(var optit = this.changeEventMap.iterator(); optit.hasNext();)
	{
		optit.next()();
	}
	return value;
};
AssignTreeField.prototype.visiable = function(isshow) {
	if (isshow)
		$("#" + this.fieldID + "_ubfbox").css("display", "block");
	else
		$("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
};
AssignTreeField.prototype.change = function(eventName, eventFunc) {
	this.changeEventMap.put(eventName, eventFunc);
	$("#" + this.fieldID).change(function() {
		eventFunc();
	});
};

AssignTreeField.openAssignTree = function(fieldID) {
	var field = F(fieldID);
	var windowUrl = $ctx
			+ "/ultrabpp/runtime/clientframework/component/SpecialField/AssignTreeField/tree/dealActor.jsp?radio="
			+ field.radio + "&act=" + field.action + "&actN=" + field.actionName + "&step="+field.step_no+"&field="
			+ fieldID +"&isSelectType="+field.selectType+ "&stamp=" + (new Date()).getTime();
	window.showModalDialog(windowUrl, window, 'dialogHeight:570px;dialogWidth:1020px;help:no;scroll:no;status:no;');
};

AssignTreeField.setAssignTagString = function(tagID, assignStr) {
	var oldData = $("#" + tagID + "_AssignString")[0].value;
	$("#" + tagID + "_AssignString")[0].value = assignStr;
	if (oldData != assignStr) {
		for ( var optit = F(tagID).changeEventMap.iterator(); optit.hasNext();) {
			optit.next()();
		}
	}
};

AssignTreeField.getAssignTagString = function(tagID) {
	return $("#" + tagID + "_AssignString")[0].value;
};