function CollectField(fID, fText, isRequire,stype, ctype, resName, keyName, valueName, paras)
{
	this.fieldType = "CollectField";
	this.fieldID = fID;
	this.text = fText;
	this.require = isRequire;
	this.showtype = stype;
	this.type = ctype;
	this.resource = resName;
	this.key = keyName;
	this.value = valueName;
	this.parameters = paras;
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	this.displayFields = $("[name='" + this.fieldID + "_displayInput']");
	
	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if(display == "none") this.isVisiable = false;
	var hasROClass = $("#" + this.fieldID + "_ubfbox").hasClass("bpp_Field_RO");
	if(hasROClass) this.isReadonly = true;
	
	this.changeEventMap = new Map();
	
	this.bindEvent();
}

CollectField.prototype.G = function()
{
	var field = $("[name='" + this.fieldID + "_displayInput']:checked");
	var valStr = new Array();
	for(var i = 0; i < field.length; i++)
	{
		valStr.push(field[i].value);
	}
	return valStr.toString();
}

CollectField.prototype.S = function(value)
{
	for(var i = 0; i < this.displayFields.length; i++)
	{
		if(("," + value + ",").indexOf("," + this.displayFields[i].value + ",") > -1)
		{
			this.displayFields[i].checked = true;
		}
		else
		{
			this.displayFields[i].checked = false;
		}
	}
	var oldData = $("#" + this.fieldID)[0].value;
	$("#" + this.fieldID)[0].value = value;
	if(oldData != value)
	{
		for(var optit = this.changeEventMap.iterator(); optit.hasNext();)
		{
			optit.next()();
		}
	}
	return value;
}

CollectField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID + "_ubfbox").css("display", "block");
	else $("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
}

CollectField.prototype.readonly = function(isro)
{
	var hasROClass = $("#" + this.fieldID + "_ubfbox").hasClass("bpp_Field_RO");
	if(isro)
	{
		if(!hasROClass)
		{
			$("#" + this.fieldID + "_ubfbox").addClass("bpp_Field_RO");
		}
		this.displayFields.attr("disabled", "true");
	}
	else
	{
		if(hasROClass)
		{
			$("#" + this.fieldID + "_ubfbox").removeClass("bpp_Field_RO");
		}
		this.displayFields.removeAttr("disabled");
	}
	this.isReadonly = isro;
	SelectField.bindEvent(this);
}
CollectField.prototype.required = function(isreq)
{
	var label = $("#" + this.fieldID + "_ubfbox .bpp_Field_Label_Large span");
	if(label.length == 0)
	{
		label = $("#" + this.fieldID + "_ubfbox .bpp_Field_Label span");
	}
	
	if(!label.hasClass("bpp_Require") && isreq)
	{
		label.addClass("bpp_Require");
		//label.append("<font>*</font>");
	}
	else if(label.hasClass("bpp_Require") && !isreq)
	{
		label.removeClass("bpp_Require");
		//$("#" + this.fieldID + "_ubfbox .bpp_Field_Label span font").remove();
	}
	this.require = isreq;
}

CollectField.prototype.bindEvent = function()
{
	var field = this;
	this.displayFields.click(function(e)
	{
		field.S(field.G());
	});
}

CollectField.prototype.change = function(eventName, eventFunc)
{
	this.changeEventMap.put(eventName, eventFunc);
}
