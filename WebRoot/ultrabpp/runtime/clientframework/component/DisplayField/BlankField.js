function BlankField(fID)
{
	this.fieldType = "BlankField";
	this.fieldID = fID;
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if(display == "none") this.isVisiable = false;
}

BlankField.prototype.G = function()
{
	return "";
}
BlankField.prototype.S = function(value)
{
	return "";
}
BlankField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID + "_ubfbox").css("display", "block");
	else $("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
}
BlankField.prototype.readonly = function(isro) {}