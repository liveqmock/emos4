function PanelField(fID, fText, pType, pGroupID, stitle)
{
	this.fieldType = "PanelField";
	this.fieldID = fID;
	this.fieldText = fText;
	this.type = pType;
	this.showTitle = stitle;
	this.groupID = pGroupID;
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var display = $("#" + this.fieldID).css("display");
	if(display == "none") this.isVisiable = false;
	
	F(pGroupID).panelList.add(fID);
	
	if(this.type == "tab" && this.showTitle == 1)
	{
		this.buildTabHeader();
	}
	if(this.type == "panel" && this.showTitle == 1)
	{
		this.buildBoxHeader();
	}
}

PanelField.prototype.G = function() {return null;}

PanelField.prototype.S = function(value) {return null;}

PanelField.prototype.visiable = function(isshow)
{
	if(isshow)
	{
		if((this.type == "tab" && $("#" + this.fieldID + "_Header").hasClass("bpp_TabPanelGroup_Header_Active")) || this.type == "panel")
		{
			$("#" + this.fieldID).css("display", "block");
		}
		if(this.showTitle == 1)
		{
			$("#" + this.fieldID + "_Header").css("display", "block");
		}
	}
	else
	{
		if(this.showTitle == 1)
		{
			$("#" + this.fieldID + "_Header").css("display", "none");
		}
		$("#" + this.fieldID).css("display", "none");
	}
	this.isVisiable = isshow;
}

PanelField.prototype.readonly = function(isro) {}

PanelField.prototype.buildBoxHeader = function()
{
	var fID = this.fieldID;
	$("#" + this.fieldID + "_Header").click(function()
	{
		var status = $("#" + fID).css("display");
		if(status == 'none')
		{
			//$("#" + fID).css("display", "block");
			//$("#" + fID).show(100);
			//$("#" + fID + "_Header DIV").removeClass("div_minus");
			PanelField.resumeBoxHeader(fID);
		}
		else
		{
			//$("#" + fID).css("display", "none");
			//$("#" + fID).hide(100);
			//$("#" + fID + "_Header DIV").addClass("div_minus");
			PanelField.minBoxHeader(fID);
		}
	});
}

PanelField.prototype.buildTabHeader = function()
{
	var activeStr = "";
	var visiableStr = "";
	if(this.isVisiable)
	{
		if($("#" + this.groupID + " .bpp_TabPanelGroup_Header ul .bpp_TabPanelGroup_Header_Active").length == 0)
		{
			activeStr = "class=\"bpp_TabPanelGroup_Header_Active\"";
		}
		else
		{
			$("#" + this.fieldID).css("display", "none");
		}
	}
	else
	{
		$("#" + this.fieldID).css("display", "none");
		visiableStr = "style=\"display:none;\"";
	}
	$("#" + this.groupID + " .bpp_TabPanelGroup_Header ul").append("<li id=\"" + this.fieldID + "_Header\" ubftype=\"TabPanelHeader\" " + activeStr + " " + visiableStr + "><a href=\"javascript:void(0);\" onclick=\"PanelField.switchTabPanel('" + this.fieldID + "_Header')\">" + this.fieldText + "</a></li>");
}

PanelField.switchTabPanel = function(panelID)
{
	var focusPanel = $("#" + panelID).attr("id").replace("_Header", "");
	var headerul = $("#" + panelID).parent();
	headerul.children().each(function()
	{
		var thisPanelID = $(this).attr("id").replace("_Header", "");
		PanelField.displayTabPanel(thisPanelID, 0);
		if(thisPanelID == focusPanel)
		{
			PanelField.displayTabPanel(thisPanelID, 1);
		}
	});
}
PanelField.displayTabPanel = function(panelID, type)
{
	if(type == 1)
	{
		$("#" + panelID).css("display", "block");
		$("#" + panelID + "_Header").addClass("bpp_TabPanelGroup_Header_Active");
	}
	else
	{
		$("#" + panelID).css("display", "none");
		$("#" + panelID + "_Header").removeClass("bpp_TabPanelGroup_Header_Active");
	}
}

PanelField.resumeBoxHeader = function(fieldID)
{
	//var fID = this.fieldID;
	$("#" + fieldID).show();
	$("#" + fieldID + "_Header DIV").removeClass("div_minus");
}
PanelField.minBoxHeader = function(fieldID)
{
	//var fID = this.fieldID;
	$("#" + fieldID).hide();
	$("#" + fieldID + "_Header DIV").addClass("div_minus");
}

