function DataField()
{
	this.require;
}
DataField.prototype = (new BaseField()).extend({});

DataField.prototype.G = function()
{
	var field = $("#" + this.fieldID);
	if(field.length == 0)
	{
		return null;
	}
	else
	{
		return $("#" + this.fieldID)[0].value;
	}
}
DataField.prototype.S = function(value)
{
	var field = $("#" + this.fieldID);
	if(field.length == 0)
	{
		return null;
	}
	else
	{
		$("#" + this.fieldID)[0].value = value;
		return value;
	}
}