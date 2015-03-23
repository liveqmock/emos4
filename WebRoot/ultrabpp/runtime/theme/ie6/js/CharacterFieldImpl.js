function CharacterField(fID, fText, isRequire, _maxlen)
{
	this.fieldType = "CharacterField";
	this.fieldID = fID;
	this.text = fText;
	this.require = isRequire;
	this.maxlen = _maxlen;
	
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
	var hasROClass = $("#" + this.fieldID + "_ubfbox").hasClass("bpp_Field_RO");
	if(hasROClass) this.isReadonly = true;
	
	var extendBtn = $("#" + fID + "_extend");
	if(extendBtn.length > 0)
	{
		extendBtn.click(function()
		{
			var editable = true;
			if($("#" + fID + "_ubfbox").hasClass("bpp_Field_RO")) editable = false;
			var val = F(fID).G();
			CharacterField.popup(fID, fText, val, editable);
		});
	}
	
	this.changeEventMap = new Map();
	this.focusEventMap = new Map();
}
CharacterField.prototype.searchBtnEvent = function(func)
{
	CharacterField.searchBtn(this.fieldID, func);
}
CharacterField.searchBtnStatus = false;
CharacterField.searchBtn = function(fID, func)
{
	var tagname = $("#" + fID)[0].tagName;
	if(!this.isReadonly && (tagname == "input" || tagname == "INPUT") && !CharacterField.searchBtnStatus)
	{
		$("#" + fID).mouseover(function()
		{
			var targetTop = $("#" + fID).offset().top + 1;
			var targetLeft = $("#" + fID).offset().left+ $("#" + fID).width() - 19;
			var searchStr = "<div id=\"" + fID + "_search\" class=\"bpp_Character_SearchBtn\" style=\"position:absolute; left:" + targetLeft + "px; top:" + targetTop + "px; height:22px; width:22px; background-color:#eeeeee;\"></div>";
			$(searchStr).insertAfter($("#" + fID));
			$("#" + fID + "_search").offset({top:targetTop,left:targetLeft});
			$("#" + fID + "_search").bind("click", {fieldID : fID}, func);
			$("#" + fID + "_search").mouseover(function()
			{
				CharacterField.searchBtnStatus = true;
			});
			$("#" + fID + "_search").mouseout(function()
			{
				CharacterField.searchBtnStatus = false;
				setTimeout("CharacterField.hideSearchBtn('" + fID + "')", 50);
			});
			$("#" + fID).mouseout(function()
			{
				CharacterField.searchBtnStatus = false;
				setTimeout("CharacterField.hideSearchBtn('" + fID + "')", 50);
			});
		});
		CharacterField.searchBtnStatus = true;
	}
}
CharacterField.hideSearchBtn = function(fID)
{
	if(!CharacterField.searchBtnStatus && $("#" + fID + "_search").length > 0) $("#" + fID + "_search")[0].outerHTML = "";
}
CharacterField.prototype.G = function()
{
	var field = $("#" + this.fieldID);
	if(field.length == 0) return null;
	else return field[0].value;
}
CharacterField.prototype.S = function(value)
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
CharacterField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID + "_ubfbox").css("display", "block");
	else $("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
}
CharacterField.prototype.readonly = function(isro)
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
	this.isReadonly = isro;
}
CharacterField.prototype.required = function(isreq)
{
	var label = $("#" + this.fieldID + "_ubfbox .bpp_Field_Label span");
	if(!label.hasClass("bpp_Require") && isreq)
	{
		label.addClass("bpp_Require");
		label.append("<font>*</font>");
	}
	else if(label.hasClass("bpp_Require") && !isreq)
	{
		label.removeClass("bpp_Require");
		$("#" + this.fieldID + "_ubfbox .bpp_Field_Label span font").remove();
	}
	this.require = isreq;
}
CharacterField.prototype.change = function(eventName, eventFunc)
{
	this.changeEventMap.put(eventName, eventFunc);
	$("#" + this.fieldID).change(function(){eventFunc();})
}
CharacterField.prototype.focus = function(eventName, eventFunc)
{
	this.focusEventMap.put(eventName, eventFunc);
	$("#" + this.fieldID).focus(function(){eventFunc();})
}
CharacterField.popup = function(srcID, title, content, editable)
{
	if($("#bpp_CharacterExtendBox").length > 0) $("#bpp_CharacterExtendBox")[0].outerHTML = "";
	var extendStr = "";
	extendStr += "<div id=\"bpp_CharacterExtendBox\">";
	extendStr += "<div id=\"bpp_CharacterExtendBox_title\"><span>" + title + "</span></div>";
	extendStr += "<div id=\"bpp_CharacterExtendBox_content\">";
	extendStr += "<textarea id=\"bpp_CharacterExtendBox_textarea\">" + content + "</textarea>";
	extendStr += "</div>";
	extendStr += "<div id=\"bpp_CharacterExtendBox_bottom\">";
	if(editable)
	{
		extendStr += "<input type=\"button\" id=\"bpp_CharacterExtendBox_confirm\" value=\"确 认\" />";
	}
	extendStr += "<input type=\"button\" id=\"bpp_CharacterExtendBox_cancel\" value=\"取消\" />";
	extendStr += "</div>";
	extendStr += "</div>";
	$("#bpp_WorksheetForm").append(extendStr);
	$("#bpp_CharacterExtendBox").show();
	if(editable)
	{
		$("#bpp_CharacterExtendBox_confirm").click(function()
		{
			F(srcID).S($("#bpp_CharacterExtendBox_textarea").val());
			$("#bpp_CharacterExtendBox")[0].outerHTML = "";
			Message.deactivateMask();
		});
	}
	else
	{
		$("#bpp_CharacterExtendBox_textarea").attr("readonly","readonly");
	}
	$("#bpp_CharacterExtendBox_cancel").click(function()
	{
		$("#bpp_CharacterExtendBox")[0].outerHTML = "";
		Message.deactivateMask();
	});
	
	var targetTop = $("#" + srcID + "_extend").offset().top + 23;
	var targetLeft = $("#" + srcID + "_extend").offset().left;
	var maxWidth = $(document).width();
	var maxHeight = $(document).height();
	if(targetTop + 250 > maxHeight)
	{
		targetTop = maxHeight - 250;
	}
	if(targetLeft + 350 > maxWidth)
	{
		targetLeft = maxWidth - 350;
	}
	$("#bpp_CharacterExtendBox").offset({top:targetTop,left:targetLeft});
	Message.activateMask();
}