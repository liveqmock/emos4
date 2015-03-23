<link href="${ctx}/common/plugin/ecside/css/ecside_style.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/common/plugin/ecside/js/ecside_msg_utf8_cn.js"
	type="text/javascript"></script>
<script src="${ctx}/common/plugin/ecside/js/ecside.js"
	type="text/javascript" ></script>
<script type="text/javascript">
var formid = 'ec';

function batchRemove(buttonObj,deleteFlags,pkid){
if(!confirm(ECSideMessage.UPDATE_CONFIRM)){
		return;
}
ECSideUtil.delFromGird(buttonObj,formid,deleteFlags);
var ecsideObj=ECSideUtil.getGridObj(formid);
var form=ecsideObj.ECForm;
var urld=form.getAttribute("deleteAction")+"";
var checkBoxList=form[deleteFlags];
var ids;
var url;
if(checkBoxList){
	for (var i=0;i<checkBoxList.length;i++){
		if (checkBoxList[i].checked&&checkBoxList[i].type=='checkbox'){
		   if(ids)
		     ids += ","+checkBoxList[i].value;
		    else
		   ids = checkBoxList[i].value;
		}
	}
	if(!ids){
	ids = checkBoxList.value;
	}
}

if(!ids){
	alert('<s:text name="errors.delete"/>');
	return false;
}
url = urld+"?"+ pkid +"="+ids;
executeXhr(url,formid);
}



function selectItems(tableName,delectFlag){
	var ecsideObj=ECSideUtil.getGridObj(formid);
	var form=ecsideObj.ECForm;
	var checkBoxList=form[delectFlag];
	var ids = new Array();
	var names = new Array();
	var dispNames = new Array();
	if(checkBoxList.checked){
		ids[0] = checkBoxList.value;
        if(document.all[checkBoxList.value + "NAME" ].length){
			names[0] = document.all[checkBoxList.value + "NAME" ][0].value;
		}
		else{
			names[0] = document.all[checkBoxList.value + "NAME" ].value;
		}
		var item = new Item();
		item.tableName = tableName
		item.id = ids[0]
		item.name = names[0];
		var existed = false;
		if(parent.result.arrValue.length == 0){
				dispNames[dispNames.length] = item.name;
				parent.result.arrValue[parent.result.arrValue.length] = item;
		}
		else{
			for(var j=0;j<parent.result.arrValue.length;j++){
				if(item.tableName == parent.result.arrValue[j].tableName){
					if(item.id == parent.result.arrValue[j].id){
						existed = true;
						break;
					}
				}
			}
			if(!existed){
				dispNames[dispNames.length] = item.name;
				parent.result.arrValue[parent.result.arrValue.length] = item;
			}
		}
	}
	for(var i=0;i<checkBoxList.length;i++){
		if (checkBoxList[i].checked&&checkBoxList[i].type=='checkbox'){
	        ids[i] = checkBoxList[i].value;
	        if(document.all[checkBoxList[i].value + "NAME" ].length){
				names[i] = document.all[checkBoxList[i].value + "NAME" ][0].value;
			}
			else{
				names[i] = document.all[checkBoxList[i].value + "NAME" ].value;
			}
			var item = new Item();
			item.tableName = tableName
			item.id = ids[i]
			item.name = names[i];
			var existed = false;
			if(parent.result.arrValue.length == 0){
				dispNames[dispNames.length] = item.name;
				parent.result.arrValue[parent.result.arrValue.length] = item;
			}
			else{
				for(var j=0;j<parent.result.arrValue.length;j++){
					if(item.tableName == parent.result.arrValue[j].tableName){
						if(item.id == parent.result.arrValue[j].id){
							existed = true;
							break;
						}
					}
				}
				if(!existed){
					dispNames[dispNames.length] = item.name;
					parent.result.arrValue[parent.result.arrValue.length] = item;
				}
			}
		}
	}
	
	var disp = parent.result.resultDisplay;
	var displayNames = "";
	for(var i=0;i<dispNames.length;i++){
		if(dispNames[i]){
			if(i == (dispNames.length - 1)){
				displayNames = displayNames+dispNames[i]; 
			}else{
				displayNames = displayNames+dispNames[i]+","; 
			}
		}
	}
	if(displayNames.length > 0){
		disp.value = disp.value+tableName+": "+displayNames+"\n";
	}
}

function batchRemoveForStep(buttonObj,deleteFlags,pkid,baseName){
if(!confirm(ECSideMessage.UPDATE_CONFIRM)){
		return;
}
ECSideUtil.delFromGird(buttonObj,formid,deleteFlags);
var ecsideObj=ECSideUtil.getGridObj(formid);
var form=ecsideObj.ECForm;
var urld=form.getAttribute("deleteAction")+"";
var checkBoxList=form[deleteFlags];
var ids = '';
if(checkBoxList.checked){
 ids = checkBoxList.value;
}
for (var i=0;i<checkBoxList.length;i++){
	if (checkBoxList[i].checked){
	   if(ids!='')
	     ids += ","+checkBoxList[i].value;
	    else
	   ids = checkBoxList[i].value;
	}
}
var url = urld+"?baseName="+baseName +"&"+ pkid +"="+ids;
executeXhr(url,formid);
 window.location.reload();
 
}

function executeXhr(url,formid) { 
// branch for native XMLHttpRequest object 
if (window.XMLHttpRequest) { 
req = new XMLHttpRequest(); 
req.onreadystatechange = processAjaxResponse; 
req.open("GET", url, true); 
req.send(null); 
} // branch for IE/Windows ActiveX version 
else if (window.ActiveXObject) { 
req = new ActiveXObject("Microsoft.XMLHTTP"); 
if (req) { 
req.onreadystatechange = processAjaxResponse; 
req.open("GET", url, true); 
req.send(); 
} 
} 
} 

function processAjaxResponse() { 
// only if req shows "loaded" 
if (req.readyState == 4) { 
// only if "OK" 
if (req.status == 200) {
if(req.responseText)
//alert(req.responseText);
ECSideUtil.reload(formid); 
} else { 
//alert(req.statusText); 
  } 
 } 
}
function disableNullValue(id){
  if(document.getElementById(id).value=='')
  document.getElementById(id).disabled= 'true';
}
function showQuery(){
	 var queryTable = document.getElementById("queryTable");
	 if(queryTable.style.display)
	   queryTable.style.display = '';
	 else
	   queryTable.style.display = 'none';
}
</script>