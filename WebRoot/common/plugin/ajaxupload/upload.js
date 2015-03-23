
////////////////////////////////////Begin Table ///////////////////////////////////////////////////////
function tableNewCell(htmlContent)
{
	var td=document.createElement("TD");
	if(null!=htmlContent)
	{
		td.innerHTML=htmlContent;
	}
	return td;
}
function tableCell(parentItem,num1,num2)
{
	var item=$(parentItem);
	if(null==item||null==num1||num1<0)
	{
		return;
	}	
	if(item.tagName.toLowerCase()=="tr")
	{
		var ary=findChildrenByTagName(parentItem,"td");
		if(num1>=ary.length)
		{
			return null;
		}		
		return ary[num1];
	}
	else if(item.tagName.toLowerCase()=="table")
	{
		if(num2==null||num2<0)
		{
			return null;
		}
		var ary=findChildrenByTagName(parentItem,"tr");
		if(num1>=ary.length)
		{
			return null;
		}		
		return tableCell(ary[num1],num2);
	}	
	return null;
}
function tableSetRowValuesXcode(rowName,xcode)
{
	if(null==rowName||null==xcode||arguments.length<3)
	{
		return;
	}
	var tr=$(rowName);
	if(null==tr)
	{
		return null;
	}
	for(var n=0;n<arguments.length-2;n++)
	{
		var v=arguments[n+2];
		v=xcode[v];
		v=v==null?"":v;
		if(n>=tr.children.length)
		{
			tr.appendChild(tableNewCell(v));			
		}
		else
		{
			tr.children[n].innerHTML=v;
		}
	}
}
function tableSetRowValues(rowName)
{
	var tr=$(rowName);
	if(null==tr)
	{
		return null;
	}
	for(var n=0;n<arguments.length-1;n++)
	{
		var v=arguments[n+1];
		v=v==null?"":v;
		if(null==tr.children||n>=tr.children.length)
		{
			tr.appendChild(tableNewCell(v));			
		}
		else
		{
			tr.children[n].innerHTML=v;
		}
	}	
}
function tableDeleteRow(tableName,rowName)
{
	var table=$(tableName);
	var tr=$(rowName);
	if(null==table||null==tr)
	{
		return null;
	}
	try
	{
		table.childNodes[0].removeChild(tr);
	}
	catch(excep)
	{
	}
}
function tableNewRow(tableName)
{
	var table=$(tableName);
	if(null==table)
	{
		return null;
	}
	var tbody = table.childNodes[0];
	if(null==tbody)
	{
		return null;
	}
	var tr = document.createElement("TR");
	tbody.appendChild(tr);
	for (var na=1; na<arguments.length; na++)
  	{
  		tr.appendChild(tableNewCell(arguments[na]));
	}
	return tr;
}
function tableAddRow(tableName)
{
	var table=$(tableName);
	if(null==table)
	{
		return null;
	}
	var tbody = table.childNodes[0];
	if(null==tbody)
	{
		return null;
	}
	var tr = document.createElement("TR");
	tbody.appendChild(tr);
	for (var na=1; na<arguments.length; na++)
  	{
  		tr.appendChild(tableNewCell(arguments[na]));
	}
	return tr;
}
function tableAddCell(trName)
{
	var tr=$(trName);
	if(null==tr)
	{
		return null;
	}
	for (var na=1; na<arguments.length; na++)
  	{
  		tr.appendChild(tableNewCell(arguments[na]));
	}
	return tr;
}
function findParentByTagName(itemName,tag)
{
	var item=$(itemName);
	if(null==item ||tag==null)
	{
		return null;
	}
	tag=tag.toLowerCase();
	while(item!=null && item.parentNode!=null)
	{
		if(item.parentNode.tagName!=null&& item.parentNode.tagName.toLowerCase()==tag)
		{
			return item.parentNode;
		}
		item=item.parentNode;
	}
	return null;
}
////////////////////////////////////End Table ///////////////////////////////////////////////////////