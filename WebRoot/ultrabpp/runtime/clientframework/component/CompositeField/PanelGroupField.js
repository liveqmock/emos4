function PanelGroupField(fID, pType, stitle)
{
	this.fieldType = "PanelGroupField";
	this.fieldID = fID;
	this.type = pType;
	this.showtitle = stitle;
	
	this.panelList = new List();
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var display = $("#" + this.fieldID).css("display");
	if(display == "none") this.isVisiable = false;
}

PanelGroupField.prototype.G = function() {return null;}

PanelGroupField.prototype.S = function(value) {return null;}

PanelGroupField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID).css("display", "block");
	else $("#" + this.fieldID).css("display", "none");
	this.isVisiable = isshow;
}

PanelGroupField.prototype.readonly = function(isro) {}