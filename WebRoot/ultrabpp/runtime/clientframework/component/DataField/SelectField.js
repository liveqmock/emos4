function SelectField(fID, fText, isRequire, ctype, resName, keyName, valueName, paras)
{
	this.fieldType = "SelectField";
	this.fieldID = fID;
	this.text = fText;
	this.require = isRequire;
	this.type = ctype;
	this.resource = resName;
	this.key = keyName;
	this.value = valueName;
	this.parameters = paras;
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if(display == "none") this.isVisiable = false;
	var hasROClass = $("#" + this.fieldID + "_ubfbox").hasClass("bpp_Field_RO");
	if(hasROClass) this.isReadonly = true;
	
	this.selectItems = new List();
	
	this.changeEventMap = new Map();
	
	SelectField.bindEvent(this);
}
SelectField.activeSelect = null;
SelectField.activeLevel = 0;

SelectField.prototype.G = function()
{
	var field = $("#" + this.fieldID);
	if(field.length == 0) return null;
	else return field[0].value;
}
SelectField.prototype.S = function(value)
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
SelectField.prototype.visiable = function(isshow)
{
	if(isshow) $("#" + this.fieldID + "_ubfbox").css("display", "block");
	else $("#" + this.fieldID + "_ubfbox").css("display", "none");
	this.isVisiable = isshow;
}
SelectField.prototype.readonly = function(isro)
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
	SelectField.bindEvent(this);
}
SelectField.prototype.required = function(isreq)
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
SelectField.prototype.change = function(eventName, eventFunc)
{
	this.changeEventMap.put(eventName, eventFunc);
}


SelectField.bindEvent = function(selectFieldObj)
{
	var fieldObj = $("#" + selectFieldObj.fieldID);
	var fieldExtendObj = $("#" + selectFieldObj.fieldID + "_extend");
	if(selectFieldObj.isReadonly)
	{
		fieldObj.unbind("click");
		fieldExtendObj.unbind("click");
	}
	else
	{
		fieldObj.click(function()
		{
			SelectField.showItems(selectFieldObj);
		});
		fieldExtendObj.click(function()
		{
			SelectField.showItems(selectFieldObj);
		});
	}
}

SelectField.showItems = function(selectFieldObj)
{
	SelectField.activeSelect = selectFieldObj;
	var dataList = new List();
	var fieldID = selectFieldObj.fieldID;
	var type = selectFieldObj.type;
	var resName = selectFieldObj.resource;
	var keyName = selectFieldObj.key;
	var valueName = selectFieldObj.value;
	var paras = selectFieldObj.parameters;
	var finalParas = "";
	
	while(paras.indexOf("@!{") > -1)
	{
		finalParas += paras.substring(0, paras.indexOf("@!{"));
		paras = paras.substring(paras.indexOf("@!{") + 3);
		var fid = paras.substring(0, paras.indexOf("}"));
		finalParas += F(fid).G();
		paras = paras.substring(paras.indexOf("}") + 1);
	}
	finalParas += paras;
	if(selectFieldObj.parameters.indexOf("@!{") > -1 && finalParas == "") finalParas = "NoData";
	
	if(resName != "")
	{
		var serviceParaMap = new Map();
		serviceParaMap.put("dicType", type);
		serviceParaMap.put("dicResName", resName);
		serviceParaMap.put("dicKeyName", keyName);
		serviceParaMap.put("dicValueName", valueName);
		serviceParaMap.put("dicParas", finalParas);
		DataTransfer.callService("bppFormClientCall", "getDicData", serviceParaMap, function(data)
		{
			for(var i = 0; i < data.length; i++)
			{
				dataList.add(data[i]);
			}
			selectFieldObj.selectItems = dataList;
			SelectField.showOption();
			
			
			$(document).click(function(e)
			{
					if($("#ubf_SelectBox").length > 0)
					{
						$("#ubf_SelectBox")[0].outerHTML = "";
					}
				$(document).unbind(e);
			});
		});
	}
}
$(window).resize(function(e)
{
	if(Util.windowWidth != $(window).width())
	{
		if($("#ubf_SelectBox").length > 0)
		{
			$("#ubf_SelectBox")[0].outerHTML = "";
		}
		Util.windowWidth = $(window).width();
	}
});

SelectField.showing = new Map();
SelectField.showOption = function()
{
	var selectFieldObj = SelectField.activeSelect;
	var targetTop = $("#" + selectFieldObj.fieldID).offset().top + 23;
	var targetLeft = $("#" + selectFieldObj.fieldID).offset().left;
	var targetWidth = $("#" + selectFieldObj.fieldID).width();
	if(!SelectField.showing.containsKey(selectFieldObj.fieldID))
	{
		SelectField.showing.put(selectFieldObj.fieldID, "");
		$("#bpp_WorksheetForm").append("<div id=\"ubf_SelectBox\"><ul></ul></div>");
		$("#ubf_SelectBox ul:eq(0)").append("<li type=\"selectmenu_end\" indexdn=\"-1\" itemval=\"\"><span type=\"selectmenu_end\">&lt;清空&gt;</span></li>");
		SelectField.activeLevel = 1;
		for(var itemit = selectFieldObj.selectItems.iterator(); itemit.hasNext();)
		{
			var item = itemit.next();
			var key = item.dnIDs;
			var val = item.text;
			var subMenus = item.subMenus;
			var index = itemit.index;
			if(subMenus.length > 0)
			{
				var li = $("<li type=\"selectmenu_mid\" class=\"bpp_SelectItem_sub\" indexdn=\"" + index + "\" itemval=\"" + key + "\"><span type=\"selectmenu_mid\">" + val + "</span><div type=\"selectmenu_mid\">&nbsp;&nbsp;</div></li>");
				$("#ubf_SelectBox ul:eq(0)").append(li);
			}
			else
			{
				var li = $("<li type=\"selectmenu_end\" indexdn=\"" + index + "\" itemval=\"" + key + "\"><span type=\"selectmenu_end\">" + val + "</span><div type=\"selectmenu_end\">&nbsp;&nbsp;</div></li>");
				$("#ubf_SelectBox ul:eq(0)").append(li);
			}
		}
		var liList = $("#ubf_SelectBox ul:eq(0) li");
		var maxWidth = targetWidth-10;
		for(var i = 0; i < liList.length; i++)
		{
			var tmpWidth = parseInt(liList[i].firstChild.clientWidth);
			if(tmpWidth > maxWidth) maxWidth = tmpWidth;
		}
		$("#ubf_SelectBox ul:eq(0)").width(maxWidth+32);
		liList.width(maxWidth+26);
		$("#ubf_SelectBox ul:eq(0) li span").width(maxWidth+5);
		$("#ubf_SelectBox").offset({top:targetTop,left:targetLeft});
		
		$("#ubf_SelectBox ul:eq(0) li").click(SelectField.clickEventFunc);
		$("#ubf_SelectBox ul:eq(0) li").mouseover(SelectField.showSubEventFunc);
		$("#ubf_SelectBox").show();
		SelectField.showing.remove(selectFieldObj.fieldID);
	}
}
SelectField.clickEventFunc = function(e)
{
	var selectFieldObj = SelectField.activeSelect;
	var liobj = $(e.currentTarget);
	var newData = liobj.attr("itemval");
	var itemType = liobj.attr("type");
	if(itemType == "selectmenu_end")
	{
		selectFieldObj.S(newData);
	}
}
SelectField.showSubEventFunc = function(e)
{
	var selectFieldObj = SelectField.activeSelect;
	var liobj = $(e.currentTarget);
	var indexdns = liobj.attr("indexdn").split(".");
	for(var i = indexdns.length; i <= SelectField.activeLevel; i++)
	{
		if($("#ubf_SelectBox_" + i).length > 0)
		{
			$("#ubf_SelectBox_" + i)[0].outerHTML = "";
		}
	}
	if(liobj.hasClass("bpp_SelectItem_sub"))
	{
		var menus = selectFieldObj.selectItems.arr;
		for(var i = 0; i < indexdns.length; i++)
		{
			menus = menus[indexdns[i]].subMenus;
		}
		SelectField.showSubOption(menus, liobj);
	}
}

SelectField.showSubOption = function(menus, liObj)
{
	var val = liObj.attr("itemval");
	if(val.split(".").length > SelectField.activeLevel) SelectField.activeLevel = val.split(".").length;
	var liindexdn = liObj.attr("indexdn");
	var id = "ubf_SelectBox_" + val.split(".").length;
	if($("#" + id).length > 0)
	{
		$("#" + id)[0].outerHTML = "";
	}
	
	$("#ubf_SelectBox").append("<div id=\"" + id + "\" class=\"bpp_SelectBox_Sub\"><ul></ul></div>");
	
	for(var i = 0; i < menus.length; i++)
	{
		var key = menus[i].dnIDs;
		var val = menus[i].text;
		var subMenus = menus[i].subMenus;
		var index = liindexdn + "." + i;
		if(subMenus.length > 0)
		{
			var li = $("<li type=\"selectmenu_mid\" class=\"bpp_SelectItem_sub\" indexdn=\"" + index + "\" itemval=\"" + key + "\"><span type=\"selectmenu_mid\">" + val + "</span><div type=\"selectmenu_mid\">&nbsp;&nbsp;</div></li>");
			$("#" + id + " ul:eq(0)").append(li);
		}
		else
		{
			$("#" + id + " ul:eq(0)").append("<li type=\"selectmenu_end\" indexdn=\"" + index + "\" itemval=\"" + key + "\"><span type=\"selectmenu_end\">" + val + "</span><div type=\"selectmenu_end\">&nbsp;&nbsp;</div></li>");
		}
	}
	
	var liList = $("#" + id + " ul:eq(0) li");
	var maxWidth = 0;
	for(var i = 0; i < liList.length; i++)
	{
		var tmpWidth = parseInt(liList[i].firstChild.clientWidth);
		if(tmpWidth > maxWidth) maxWidth = tmpWidth;
	}
	$("#" + id + " ul:eq(0)").width(maxWidth+32);
	liList.width(maxWidth+26);
	$("#" + id + " ul:eq(0) li span").width(maxWidth+5);
	var litop = liObj.offset().top;
	var lileft = liObj.offset().left + liObj.width() + 5;
	if((lileft + maxWidth + 32) > (parseInt($(document.body).width())))
	{
		lileft = liObj.offset().left - 5 - (maxWidth + 32);
	}
	$("#" + id).offset({top:litop,left:lileft});

	$("#" + id + " ul:eq(0) li").click(SelectField.clickEventFunc);
	$("#" + id + " ul:eq(0) li").mouseover(SelectField.showSubEventFunc);
}


