
function tquerysubmit()
{
	var tName = document.getElementById("tqueryname");
	var tValue = document.getElementById("tquerytext");
	if(tName!=null && tValue!=null)
	{
		//alert("1:"+tValue);
		//var tValue = document.getElementById("tqueryname");
		var setVar=document.getElementById(tName.value);
		//alert("2:"+setVar);
		if(setVar!=null)
		{
			setVar.value=tValue.value;
		}
	}
	//alert("8:"+tName.value);
}

function querysubmit()
{
	var tName = document.getElementById("tqueryname");
	var tValue = document.getElementById("tquerytext");
	if(tName!=null && tValue!=null)
	{
		//alert("1:"+tValue);
		//var tValue = document.getElementById("tqueryname");
		var setVar=document.getElementById(tName.value);
		//alert("2:"+setVar);
		if(setVar!=null)
		{
			tValue.value=setVar.value;
		}
	}
	//alert("8:"+tName.value);
}

function showsearch(name) {
   //alert(name);
	var objdiv;
	var centerdiv = document.getElementById("center");
	
	if (name == null || name == "") {
		objdiv = document.getElementById("serachdiv");
	} else {
		objdiv = document.getElementById(name + "_serachdiv");
	}
	if (objdiv != null) {
		var display = objdiv.style.display;
		if (display == "block") {
			objdiv.style.display = "none";
			centerdiv.style.height = "576px";
			$('#center').css('max-height', centerdiv.style.height);
		} else {
			objdiv.style.display = "block";
			centerdiv.style.height = (centerdiv.offsetHeight - objdiv.offsetHeight)+"px";
			//解决ie8下height属性设置后仍然无法将滚动条显示全的问题
			$('#center').css('max-height', centerdiv.style.height);
			
			
		}
	}
}
function showsearchcondition(ctx, sqlname){
	var src = ctx + "/userManager/configSearchCondition.action?sqlname=" + sqlname;
	 window.open(src,'','width=600px,height=350px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
}
var _ischeck = false;
function checkAll(checkName) {  
	//var selvalues=''; 
	var m = document.getElementsByName(checkName);
	var len = m.length;
	for (var i = 0; i < len; i++) {
		if (m[i].disabled) {
			continue;
		}
		if (_ischeck) {
			m[i].checked = false;
		} else {
			m[i].checked = true;
		 //selvalues+=m[i].value+',';
		}
	}
	if (_ischeck) {
		_ischeck = false;
		//document.getElementsByName('selectvalues').value='';
	} else {
		_ischeck = true;
		//document.getElementsByName('selectvalues').value=selvalues;
	}
}
function getCheckValue(checkName) {
	var selvalues = "";
	var m = document.getElementsByName(checkName);
	var len = m.length;
	for (var i = 0; i < len; i++) {
		if (m[i].disabled) {
			continue;
		}
		if (m[i].checked) {
			if (selvalues == "") {
				selvalues = m[i].value;
			} else {
				selvalues += "," + m[i].value;
			}
		}
	}
	document.getElementsByName("var_selectvalues").value = selvalues;
	 //alert(document.getElementsByName('selectvalues').value);
}
function setSelectValue(objSel, objValue)
{
	//if(objSel.
	//var o = document.getElementById("a").options;
	if(objSel.length>0)
	{
		objSel.options[objValue - 1].selected = true;
	}
}
function tranferPage(type) 
{
	document.getElementById("var_istranfer").value = "1";
	var objCurpage = document.getElementById("var_currentpage");
	var pageCount = document.getElementById("var_totalpages").value;
	var pageNumber = objCurpage.value;
	if (pageNumber == "") {
		pageNumber = "1";
	}
	if (type == "frist") {
		if (pageNumber != "1") {
			setSelectValue(objCurpage, 1);
			document.forms[0].submit();
		}
	} else {
		if (type == "previous") {
			var intpageNumber = parseInt(pageNumber);
			if (intpageNumber > 1) {
				intpageNumber--;
				setSelectValue(objCurpage, intpageNumber);
				document.forms[0].submit();
			}
		} else {
			if (type == "next") {
				var intpageNumber = parseInt(pageNumber);
				var intpageCount = parseInt(pageCount);
				if (intpageCount > intpageNumber) {
					intpageNumber++;
					setSelectValue(objCurpage, intpageNumber);
					document.forms[0].submit();
				}
			} else {
				if (type == "last") {
					if (objCurpage.value != pageCount) {
						setSelectValue(objCurpage, pageCount);
						document.forms[0].submit();
					}
				} else {
					if (type == "go") {
						document.forms[0].submit();
					} else {
						if (type == "setsize") {
							setSelectValue(objCurpage, 1);
							document.forms[0].submit();
						}
					}
				}
			}
		}
	}
}
function exportExcel(ctx, sqlname, cfgmark) {
	var oldSrc = document.forms[0].action;
	document.forms[0].action = ctx + "/excelManager/exportExcel.action?sqlname=" + sqlname + "&cfgmark=" + cfgmark;
	document.forms[0].submit();
	document.forms[0].action = oldSrc;
}
function importExcel(imptype, xmlname) {
	//showModalDialog(ctx+'/ultrasm/common/commonImport.jsp?xmlname='+xmlname,window,'help:no;center:true;scroll:no;status:no;dialogWidth:300px;dialogHeight:150px');
	if(imptype != 'remedy')
		imptype = '';
	window.open($ctx + "/ultrasm/common/commonImport.jsp?imptype=" + imptype + "&xmlname=" + xmlname, "", "location=no,toolbar=no,resizable=yes,scrollbars=no,width=500,height=300,top=100,left=300");
}

function sortQuery(sortFiled)
{
	document.getElementById("var_sortfield").value = sortFiled;
	//alert(document.getElementById("var_sorttype").value);
	if (document.getElementById("var_sorttype").value!= "1") 
	{
		document.getElementById("var_sorttype").value = "1";
	}
	else 
	{
		document.getElementById("var_sorttype").value = "0";
	}
	//alert(document.getElementById("var_sorttype").value);
	document.forms[0].submit();
}

