function RollbackListField(fID, fText, isRequire, fType, fRadio) 
{
	this.fieldType = "RollbackListField";
	this.fieldID = fID;
	this.text = fText;
	this.type = fType;
	this.require = isRequire;
	this.radio = fRadio;
	this.require = isRequire;
	
	this.isVisiable = true;
	this.isReadonly = false;
	
	var display = $("#" + this.fieldID + "_ubfbox").css("display");
	if(display == "none") this.isVisiable = false;
	
	this.changeEventMap = new Map();
	
	RollbackListField.getRollbackList(fID, fRadio, fType);
}
RollbackListField.getRollbackList = function(fieldID, radio, type)
{
	if(type == "" || type == "null") type = ClientContext.actionType;
	var serviceParaMap = new Map();
	serviceParaMap.put("baseID", ClientContext.baseID);
	serviceParaMap.put("baseSchema", ClientContext.baseSchema);
	serviceParaMap.put("taskID", ClientContext.taskID);
	serviceParaMap.put("actionType", type);
	serviceParaMap.put("loginName", ClientContext.loginName);
	DataTransfer.callService("bppFormClientCall", "getRollbackList", serviceParaMap, function(data)
	{
		var selectedItem = [];
		var rootID = fieldID + "item";
		for(var i = 0; i < data.length; i++)
		{
			var tmp_stepID = data[i].stepid;
			var tmp_stepCode = data[i].stepcode;
			var tmp_Desc = data[i].desc;
			var tmp_dealer = data[i].dealer;
			var tmp_dealerdn = data[i].dealerdn;
			var tmp_Status = data[i].status;
			var radioID = rootID + "_" + tmp_stepID;
			var appendStr = "<tr>";
			var selValue = "#:#:" + type + "#:2#:0#:0#:0#:" + tmp_stepCode + ":" + tmp_stepID + ";#:#:#:#;";
			if(radio == 1)
			{
				appendStr += "<td align=\"center\"><input type=\"radio\" "+(i==0?"checked=\"true\"":"")+" id=\"" + radioID + "\" name=\"" + rootID + "\" value=\"" + selValue + "\" onclick=\"RollbackListField.buildRollbackString('" + fieldID + "', '" + radioID + "')\" /></td>";
				if (i == 0) {
					selectedItem.push([ fieldID, radioID ]);
				}
			}
			else
			{
				appendStr += "<td align=\"center\"><input type=\"checkbox\" checked=\"true\" id=\"" + radioID + "\" name=\"" + rootID + "\" value=\"" + selValue + "\" onclick=\"RollbackListField.buildRollbackString('" + fieldID + "', '" + radioID + "')\" /></td>";
				selectedItem.push([ fieldID, radioID ]);
			}
			appendStr += "<td><label for=\"" + radioID + "\">" + tmp_Desc + "</label></td>";
			appendStr += "<td><label for=\"" + radioID + "\">" + tmp_dealer + "（" + tmp_dealerdn + "）</label></td>";
			appendStr += "<td><label for=\"" + radioID + "\">" + tmp_Status + "</label></td>";
			appendStr += "</tr>";
			$(appendStr).insertAfter($("#" + fieldID + " tr:eq(0)"));
		}
		$.each(selectedItem, function(k, v) {
			RollbackListField.buildRollbackString(v[0], v[1]);
		});
	});
	//RollbackListField.buildRollbackString(fieldID, radioID);
}
RollbackListField.buildRollbackString = function(fieldID, inputID)
{
	var chkStr = "";
	var chkItem = $("#" + fieldID + " :checked");
	if(chkItem.length > 0)
	{
		for(var i = 0; i < chkItem.length; i++)
		{
			var item = chkItem[i];
			chkStr += item.value;
		}
		for(var optit = F(fieldID).changeEventMap.iterator(); optit.hasNext();)
		{
			optit.next()();
		}
	}
	ClientContext.setAssignString(chkStr);
}
RollbackListField.prototype.G = function() {
	var chkStr = null;
	var chkItem = $("#" + this.fieldID + " :checkbox");
	if(chkItem.length > 0)
	{
		chkStr = "";
		for(var i = 0; i < chkItem.length; i++)
		{
			var item = chkItem[i];
			if(item.checked) {
				chkStr += item.value;
			}
		}
	}
	
	var radioItem = $("#" + this.fieldID + " :radio");
	if(radioItem.length > 0)
	{
		chkStr = "";
		for(var i = 0; i < radioItem.length; i++)
		{
			var item = radioItem[i];
			if(item.checked) {
				chkStr += item.value;
			}
		}
	}
	
	return chkStr;
}
RollbackListField.prototype.change = function(eventName, eventFunc)
{
	this.changeEventMap.put(eventName, eventFunc);
}






