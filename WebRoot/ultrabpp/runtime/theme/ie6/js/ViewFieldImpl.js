function ViewField(fID, fText)
{
	this.fieldType = "ViewField";
	this.fieldID = fID;
	this.text = fText;
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var boxObj = $("#" + this.fieldID + "_ubfbox");
	var boxTableObj = $("#" + this.fieldID + "_ubfbox table");
	if(boxObj.width() > 240 && boxObj.height() > 30)
	{
		boxObj.width(boxObj.width() - 3);
		boxTableObj.width(boxTableObj.width() - 2);
	}
	
	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if(display == "none") this.isVisiable = false;
}
ViewField.prototype.G = function()
{
	var field = $("#" + this.fieldID);
	if(field.length == 0) return null;
	else return field[0].src;
}
ViewField.prototype.S = function(value)
{
	var field = $("#" + this.fieldID);
	if(field.length == 0) return null;
	else field[0].src = value;
	return value;
}
ViewField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID + "_ubfbox").css("display", "block");
	else $("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
}
ViewField.prototype.load = function(url)
{
	$("#" + this.fieldID)[0].src = $ctx + url;
}