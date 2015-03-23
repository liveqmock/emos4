function HiddenField(fID, fText, _maxlen)
{
	this.fieldType = "HiddenField";
	this.fieldID = fID;
	this.text = fText;
	this.maxlen = _maxlen;

	this.isVisiable = false;
	this.isReadonly = false;
	
	this.changeEventMap = new Map();
}
HiddenField.prototype.G = function()
{
	var field = $("#" + this.fieldID);
	if(field.length == 0) return null;
	else return field[0].value;
}
HiddenField.prototype.S = function(value)
{
	var field = $("#" + this.fieldID);
	if(field.length == 0) return null;
	else
	{
		var oldData = this.G();
		field[0].value = value;
		if(oldData != value)
		{
			for(var optit = this.changeEventMap.iterator(); optit.hasNext();)
			{
				optit.next()();
			}
		}
	}
	return value;
}
HiddenField.prototype.visiable = function(isshow) {}
HiddenField.prototype.readonly = function(isro) {}

HiddenField.prototype.change = function(eventName, eventFunc)
{
	this.changeEventMap.put(eventName, eventFunc);
	$("#" + this.fieldID).change(function(){eventFunc();})
}