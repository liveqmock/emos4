function ButtonField(fID, btnCode)
{
	this.fieldType = "ButtonField";
	this.fieldID = fID;
	this.code = btnCode;
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if(display == "none") this.isVisiable = false;
	
	this.items = new Map();
	
	var btns = btnCode.split(",");
	for(var i = 0; i < btns.length; i++)
	{
		var btnInfor = btns[i].split(":");
		var item = new ButtonFieldItem(this.fieldID, btnInfor[0], btnInfor[1]);
		this.items.put(btnInfor[0], item);
	}
}

ButtonField.prototype.G = function()
{
	return null;
}
ButtonField.prototype.S = function(value)
{
	return null;
}
ButtonField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID + "_ubfbox").css("display", "block");
	else $("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
}
ButtonField.prototype.readonly = function(isro)
{
	for(var it_item = this.items.iterator(); it_item.hasNext();)
	{
		var item = it_item.next();
		item.readonly(isro);
	}
}
ButtonField.prototype.item = function(btnID)
{
	return this.items.get(btnID);
}

function ButtonFieldItem(fID, btnID, btnText)
{
	this.fieldID = fID;
	this.buttonID = btnID;
	this.text = btnText;
	
	this.buttonDom = $("#" + this.fieldID + "_" + this.buttonID);
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	this.clickEventMap = new Map();
	
	this.bindEvent();
}
ButtonFieldItem.prototype.visiable = function(isshow)
{
	if(isshow) this.buttonDom.css("display", "block");
	else this.buttonDom.css("display", "none");
	this.isVisiable = isshow;
}
ButtonFieldItem.prototype.readonly = function(isro)
{
	if(isro) this.buttonDom.attr("disabled", "true");
	else this.buttonDom.removeAttr("disabled");
	this.isReadonly = isro;
}
ButtonFieldItem.prototype.bindEvent = function()
{
	var field = this;
	this.buttonDom.click(function(e)
	{
		for(var it_event = field.clickEventMap.iterator(); it_event.hasNext();)
		{
			it_event.next()();
		}
	});
}
ButtonFieldItem.prototype.click = function(eventName, eventFunc)
{
	this.clickEventMap.put(eventName, eventFunc);
}