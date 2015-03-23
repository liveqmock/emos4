function TimeField(fID, fText, isRequire, fmt)
{
	this.fieldType = "TimeField";
	this.fieldID = fID;
	this.text = fText;
	this.require = isRequire;
	this.format = fmt;
	this.extendParas = {};
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if(display == "none") this.isVisiable = false;
	var hasROClass = $("#" + this.fieldID + "_ubfbox").hasClass("bpp_Field_RO");
	if(hasROClass) this.isReadonly = true;
	
	TimeField.bindEvent(this);
	
	this.changeEventMap = new Map();
	this.blurEventMap = new Map();
}
TimeField.prototype.G = function()
{
	var field = $("#" + this.fieldID);
	if(field.length == 0) return null;
	else return field[0].value;
}
TimeField.prototype.S = function(value)
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
TimeField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID + "_ubfbox").css("display", "block");
	else $("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
}
TimeField.prototype.readonly = function(isro)
{
	var hasROClass = $("#" + this.fieldID + "_ubfbox").hasClass("bpp_Field_RO");
	if(isro)
	{
		if(!hasROClass)
		{
			$("#" + this.fieldID + "_ubfbox").addClass("bpp_Field_RO");
		}
		$("#" + this.fieldID).attr("readonly", "true");
	}
	else
	{
		if(hasROClass)
		{
			$("#" + this.fieldID + "_ubfbox").removeClass("bpp_Field_RO");
		}
		$("#" + this.fieldID).attr("readonly", "false");
	}
	TimeField.bindEvent(this);
	this.isReadonly = isro;
}
TimeField.prototype.required = function(isreq)
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
TimeField.prototype.change = function(eventName, eventFunc)
{
	this.changeEventMap.put(eventName, eventFunc);
	$("#" + this.fieldID).change(function(){eventFunc();})
}

TimeField.prototype.blur = function(eventName, eventFunc)
{
	this.blurEventMap.put(eventName, eventFunc);
	$("#" + this.fieldID).blur(function(){eventFunc();})
}

TimeField.bindEvent = function(timeFieldObj)
{
	if(timeFieldObj.isReadonly)
	{
		$("#" + timeFieldObj.fieldID).unbind("focus");
	}
	else
	{
		$("#" + timeFieldObj.fieldID).focus(function()
		{
			timeFieldObj.extendParas.dateFmt = timeFieldObj.format;
			timeFieldObj.extendParas.enableInputMask = false;
			timeFieldObj.extendParas.onpicking = function(dp)
			{
				if(dp.cal.getDateStr()!=dp.cal.getNewDateStr())
				{
					F(timeFieldObj.fieldID).S(dp.cal.getNewDateStr());
				}
				return false;
			};
			WdatePicker(timeFieldObj.extendParas);
		});
	}
}