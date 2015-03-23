function FieldModel(fID)
{
	this.fieldID = fID;
}
FieldModel.prototype = 
{
	G : function()
	{
		var fobj = $("#" + this.fieldID);
		if(fobj.length > 0)
		{
			var ftype = $("#" + this.fieldID + "_ubfbox").attr("ubftype");
			if(ftype == "CharField" || ftype == "TimeField" || ftype == "SelectField" || ftype == "NumField")
			{
				return fobj[0].value;
			}
			else
			{
				return ftype;
			}
		}
		else
		{
			return "";
		}
	}
}