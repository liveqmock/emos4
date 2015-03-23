/*ExcelTableUtil对象*/
function ExcelTableUtil(){}
/*复制一行数据*/
ExcelTableUtil.copyRow = function(row)
{
	var newRow;
	var newCells = new Array();
	var cells = row.cells;
	for(var i=0;i<cells.length;i++)
	{
		newCells.push(ExcelTableUtil.copyCell(cells[i]));
	}
	newRow = new ExcelRow("",newCells,row.className);
	return newRow;
}

/*复制一个单元格的数据*/
ExcelTableUtil.copyCell = function(cell)
{
	var newCell;
	newCell = new ExcelCell("","","newCell",1,cell.colspan,cell.width,cell.ordernum,
					cell.align,cell.colcolor,"",0,"","",
					"","","0",cell.enable,
					cell.spantag,cell.spanfeature,cell.ownerpios,cell.isupdate);
	return newCell;
}
/*复制一列数据*/
ExcelTableUtil.copyCol = function(col)
{
	var newCol = new Array();
	for(var i=0;i<col.length;i++)
	{
		newCol.push(ExcelTableUtil.copyCell2(col[i]));
	}
	return newCol;
}
/*复制一个单元格的数据2*/
ExcelTableUtil.copyCell2 = function(cell)
{
	var newCell;
	newCell = new ExcelCell("","","newCell",cell.rowspan,1,cell.width,cell.ordernum,
					cell.align,cell.colcolor,"",0,"","",
					"","","0",cell.enable,
					cell.spantag,cell.spanfeature,cell.ownerpios,cell.isupdate);
	return newCell;
}
/*根据行列生成*/
ExcelTableUtil.initTable = function(rowCount,colCount)
{
	var table;
	var rows = new Array();
	for(var i=0;i<rowCount;i++)
	{
		var cells = new Array();
		for(var j=0;j<colCount;j++)
		{
			var newCell;
			newCell = new ExcelCell("","",i+","+j,1,1,1000,j,
									"","","",0,"","",
									"","","0","1",
									"","","","");
			cells.push(newCell);
		}
		var newRow;
		newRow = new ExcelRow("",cells,"");
		rows.push(newRow);
	}
	table = new ExcelTable("cfgTb",rows,"tableborder_excelTitile");
	return table;
}
/*根据表格对象构造表格innerHTML*/
ExcelTableUtil.toInnerHTML = function(table)
{
	var htmlstr = "";
	htmlstr +="<table id=\""+table.id+"\" class=\""+table.className+"\">\n";
	for(var i=0;i<table.rows.length;i++)
	{
		var row = table.rows[i];
		htmlstr += "<tr id=\""+row.id+"\" align=\"center\" class=\""+row.className+"\">\n";
		for(var j=0;j<row.cells.length;j++)
		{
			var cell = row.cells[j];
			//alert(cell.displayname+":"+cell.enable);
			if(cell.enable=="1")
			{
				htmlstr += "<td id=\""+cell.id+"\" pioX=\""+i+"\" pioY=\""+j+"\" rowspan=\""+cell.rowspan+"\" colspan=\""+cell.colspan+"\" onclick=\"cellSet(event);\" oncontextmenu=\"rightClick(event);\" style=\"cursor:hand;\"";
				if(cell.isgroup=="1")
				{
					htmlstr += "style=\"background:#0080FF;\"";
				}
				htmlstr += ">\n";
				htmlstr += cell.displayname;
				htmlstr += "</td>";
			}
		}
		htmlstr += "</tr>";
	}
	htmlstr +="</table>";
	return htmlstr;
}
/*根据表格对象构造XML传入后台保存表格*/
ExcelTableUtil.toXmlAll = function(table)
{
	var xmlstr = "";
	xmlstr += "<table id=\""+table.id+"\" rows='"+table.rows.length+"' cols='"+table.rows[0].cells.length+"'>\n";
	for(var i=0;i<table.rows.length;i++)
	{
		var row = table.rows[i];
		xmlstr += "\t<tr id=\""+row.id+"\">\n";
		for(var j=0;j<row.cells.length;j++)
		{
			var cell = row.cells[j];
			xmlstr += "\t\t<td id=\""+cell.id+"\" fieldname=\""+cell.fieldname+"\" displayname=\""+cell.displayname+"\" rowspan=\""+cell.rowspan+"\" colspan=\""+cell.colspan
					  +"\" width=\""+cell.width+"\" ordernum=\""+cell.ordernum+"\" align=\""+cell.align+"\" colcolor=\""+cell.colcolor+"\" datatype=\""+cell.datatype
					  +"\" datalength=\""+cell.datalength+"\" datainfo=\""+cell.datainfo+"\" operator=\""+cell.operator+"\" comparedata=\""+cell.comparedata
					  +"\" displaycolor=\""+cell.displaycolor+"\" isgroup=\""+cell.isgroup+"\" enable=\""+cell.enable+"\">";
			
			xmlstr += "</td>\n";
		}
		xmlstr += "\t</tr>\n";	
	}
	xmlstr +="</table>";
	return xmlstr;
}
ExcelTableUtil.toXmlEnable = function(table)
{
	var xmlstr = "";
	xmlstr += "<table id=\""+table.id+"\" rows='"+table.rows.length+"' cols='"+table.rows[0].cells.length+"'>\n";
	for(var i=0;i<table.rows.length;i++)
	{
		var row = table.rows[i];
		xmlstr += "\t<tr>\n";
		for(var j=0;j<row.cells.length;j++)
		{
			var cell = row.cells[j];
			if(cell.enable=="1")
			{
				xmlstr += "\t\t<td fieldname=\""+cell.fieldname+"\" displayname=\""+cell.displayname+"\" rowspan=\""+cell.rowspan+"\" colspan=\""+cell.colspan
						  +"\" width=\""+cell.width+"\" align=\""+cell.align+"\" colcolor=\""+cell.colcolor+"\" datatype=\""+cell.datatype
						  +"\" datalength=\""+cell.datalength+"\" datainfo=\""+cell.datainfo+"\" operator=\""+cell.operator+"\" comparedata=\""+cell.comparedata
						  +"\" displaycolor=\""+cell.displaycolor+"\" isgroup=\""+cell.isgroup+"\">";
				
				xmlstr += "</td>\n";
			}
			
		}
		xmlstr += "\t</tr>\n";	
	}
	xmlstr +="</table>";
	return xmlstr;
}
/*获得table对象所有单元格rospan与colspan的字符串*/
ExcelTableUtil.getSpanStr = function(table)
{
	var spanstr = "";
	for(var i=0;i<table.rows.length;i++)
	{
		for(var j=0;j<table.rows[i].cells.length;j++)
		{
			spanstr += table.rows[i].cells[j].rowspan+"*"+table.rows[i].cells[j].colspan+",";
		}
		spanstr += ";";
	}
	var reg = /,;/g;
	return spanstr.replace(reg,";");
}
ExcelTableUtil.createXmlDoc = function(xmlstr)
{
	var xmlDoc;
	if(window.ActiveXObject)
	{
		var ARR_ACTIVEX = ["MSXML4.DOMDocument","MSXML3.DOMDocument","MSXML2.DOMDocument","MSXML.DOMDocument","Microsoft.XmlDom"];
		var XmlDomflag = false;
		for (var i = 0;i < ARR_ACTIVEX.length && !XmlDomflag ;i++) 
		{
            try {
                var objXML = new ActiveXObject(ARR_ACTIVEX[i]);
                xmlDoc = objXML;
                XmlDomflag = true;
            } 
            catch(e)
            {
            	//alert(e);
            }
        }
        if (xmlDoc) {
            xmlDoc.async = false;
            xmlDoc.loadXML(xmlstr);
        } else {
            return;
        }
	}
	else if (document.implementation && document.implementation.createDocument)
	{
        //xmlDoc = document.implementation.createDocument('', 'doc', null);
        //xmlDoc.loadXML(xmlstr); 
        domParser = new DOMParser();
		xmlDoc = domParser.parseFromString(xmlstr, 'text/xml');  
    }
    else {
        return;
    }
    return xmlDoc;
}
/*根据xml字符串构造table对象*/
ExcelTableUtil.createExcelTableFromXml = function(xmlstr)
{
	var xmlDoc = ExcelTableUtil.createXmlDoc(xmlstr);
	var rows = new Array();
	var root = xmlDoc.getElementsByTagName("table")[0];
	var rowElements = root.childNodes;
	for(var i=0;i<rowElements.length;i++)
	{
		var cells = new Array();
		var cellElements = rowElements[i].childNodes;
		for(var j=0;j<cellElements.length;j++)
		{
			var element = cellElements[j];
			
			var id           = element.getAttribute("id");
			var fieldname    = element.getAttribute("fieldname");
			var displayname  = element.getAttribute("displayname");
			var rowspan      = parseInt(element.getAttribute("rowspan"));
			var colspan      = parseInt(element.getAttribute("colspan"));
			var width        = parseInt(element.getAttribute("width"));
			var ordernum     = parseInt(element.getAttribute("ordernum"));
			var align        = element.getAttribute("align");
			var colcolor     = element.getAttribute("colcolor");
			var datatype     = element.getAttribute("datatype");
			var datalength   = parseInt(element.getAttribute("datalength"));
			var datainfo     = element.getAttribute("datainfo");
			var operator     = element.getAttribute("operator");
			var comparedata  = element.getAttribute("comparedata");
			var displaycolor = element.getAttribute("displaycolor");
			var isgroup      = element.getAttribute("isgroup");
			var enable       = element.getAttribute("enable");
			
			var newCell = new ExcelCell(id,fieldname,displayname,rowspan,colspan,width,ordernum,
										align,colcolor,datatype,datalength,datainfo,operator,
										comparedata,displaycolor,isgroup,enable,
										"","","","");
			
			cells.push(newCell);
		}
		var newRow = new ExcelRow(rowElements[i].getAttribute("id"),cells,"");
		rows.push(newRow);
	}
	var table = new ExcelTable("cfgTb",rows,"tableborder_excelTitile");
	//赋值跨特征
	for(var i=0;i<table.rows.length;i++)
	{
		for(var j=0;j<table.rows[i].cells.length;j++)
		{
			var curCell = table.rows[i].cells[j];
			if(curCell.rowspan>1 || curCell.colspan>1)
			{
				curCell.spantag = "1";
				curCell.spanfeature = (new Date()).getTime()+"";
				curCell.ownerpios = i+","+j;
				for(var k=0;k<curCell.rowspan;k++)
				{
					for(var l=0;l<curCell.colspan;l++)
					{
						if(k!=0||l!=0)
						{
							table.rows[i+k].cells[j+l].spantag = "2";
							table.rows[i+k].cells[j+l].spanfeature = curCell.spanfeature;
							table.rows[i+k].cells[j+l].ownerpios = curCell.ownerpios;
						}
					}
				}
			}
		}
	}
	return table;
}
ExcelTableUtil.cellToString = function(cell)
{
	var str = "";
	str += "display:"+cell.displayname;
	str += "\nrowspan:"+cell.rowspan;
	str += "\ncolspan:"+cell.colspan;
	str += "\nspantag:"+cell.spantag;
	str += "\nspanfeature:"+cell.spanfeature;
	str += "\nenable:"+cell.enable;
	alert(str);
}