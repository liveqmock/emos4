/*--ExcelTable对象--*/
function ExcelTable(id,rows,className)
{
	this.id           = id;   //对象ID
	this.rows         = rows; //行集
	this.className        = className; //样式名
}
/**
 * 插入行
 * @param index --行索引，从0开始
 */
ExcelTable.prototype.insertRow = function(index)
{
	if(isNaN(index))
	{
		alert("The parameter 'index' is invilade!");
		return;
	}
	var newRow = ExcelTableUtil.copyRow(this.rows[index]);
	var temp1 = this.rows.slice(0,index);
	var temp2 = this.rows.slice(index);
	this.rows = (temp1.concat(newRow)).concat(temp2);
	newRow = this.rows[index];
	var midInsertStr = "";
	//更改新加行的跨特征
	for(var i=0;i<newRow.cells.length;i++)
	{
		var temCell = newRow.cells[i];
		if(index==0)
		{//首行插入
			if(temCell.colspan==1 && temCell.spantag=="1")
			{
				temCell.spantag = "";
				temCell.spanfeature = "";
				temCell.ownerpios = "";
				temCell.isupdate = "";
			}
			else if(temCell.colspan!=1 && temCell.spantag=="1")
			{
				//temCell.spantag = "1";
				temCell.spanfeature = temCell.spanfeature + "_c";
				//temCell.ownerpios = index+","+i;
				temCell.isupdate = "";
			}
			else if(temCell.spantag=="2")
			{
				//temCell.spantag = "2";
				temCell.spanfeature = temCell.spanfeature + "_c";
				//temCell.ownerpios = "";
				//temCell.isupdate = "";
			}
			
		}
		else if(index!=0 && temCell.spanfeature!="" && temCell.spanfeature!=this.rows[index-1].cells[i].spanfeature)
		{//非首行非包含插入
			if(temCell.colspan==1 && temCell.spantag=="1")
			{
				temCell.spantag = "";
				temCell.spanfeature = "";
				temCell.ownerpios = "";
				temCell.isupdate = "";
			}
			else if(temCell.colspan!=1 && temCell.spantag=="1")
			{
				//temCell.spantag = "1";
				temCell.spanfeature = temCell.spanfeature+"_c";
				//temCell.ownerpios = index+","+i;
				temCell.isupdate = "";
			}
			else if(temCell.spantag=="2")
			{
				//temCell.spantag = "2";
				temCell.spanfeature = temCell.spanfeature+"_c";
			}
		}
		else if(index!=0 && temCell.spanfeature!="" && temCell.spanfeature==this.rows[index-1].cells[i].spanfeature && temCell.spanfeature==this.rows[index+1].cells[i].spanfeature)
		{//非首行包含插入
			//temCell.spantag = "";
			//temCell.spanfeature = "";
			//temCell.ownerpios = "";
			var pioX = parseInt(temCell.ownerpios.split(",")[0]);
			var pioY = parseInt(temCell.ownerpios.split(",")[1]);
			if(this.rows[pioX].cells[pioY].isupdate!="true")
			{
				this.rows[pioX].cells[pioY].rowspan += 1;
				this.rows[pioX].cells[pioY].isupdate = "true";
			}
			temCell.isupdate = "";
			midInsertStr += temCell.spanfeature+"&semi;";
		}
	}
	if(midInsertStr!="")
	{
		midInsertStr = midInsertStr.substring(0,midInsertStr.lastIndexOf("&semi;"));
	}
	//还原新加行以上所有单元格的isupdate
	if(midInsertStr!="")
	{
		midInsertStr = "";
		for(var i=0;i<index;i++)
		{
			for(var j=0;j<this.rows[i].cells.length;j++)
			{
				if(this.rows[i].cells[j].spantag=="1")
				{
					this.rows[i].cells[j].isupdate = "";
					midInsertStr += this.rows[i].cells[j].spanfeature+"&semi;";
				}
			}
		}
	}
	//更新新加行以下ownerpios不为空的单元格的该属性
	for(var i=index+1;i<this.rows.length;i++)
	{
		for(var j=0;j<this.rows[i].cells.length;j++)
		{
			if(this.rows[i].cells[j].spantag!="" && this.rows[i].cells[j].ownerpios!="" && midInsertStr.search(this.rows[i].cells[j].spanfeature)==-1)
			{
				var oldX = parseInt(this.rows[i].cells[j].ownerpios.split(",")[0]);
				var oldY = parseInt(this.rows[i].cells[j].ownerpios.split(",")[1]);
				this.rows[i].cells[j].ownerpios = (oldX+1)+","+oldY;
			}
		}
	}
}

/**
 * 删除行
 * @param index --行索引，从0开始
 */
ExcelTable.prototype.deleteRow = function(index)
{
	if(isNaN(index))
	{
		alert("参数不合法！");
		return;
	}
	if(index==this.rows.length-1)
	{
		alert("不能删除数据行！");
		return;
	}
	//循环将被删除行的单元格，修改跨相关特征
	for(var i=0;i<this.rows[index].cells.length;i++)
	{
		var temCell = this.rows[index].cells[i];
		if(temCell.spantag=="1")
		{
			if(temCell.colspan==1 && temCell.rowspan==2)
			{
				this.rows[index+1].cells[i].spantag = "";
				this.rows[index+1].cells[i].spanfeature = "";
				this.rows[index+1].cells[i].ownerpios = "";
				this.rows[index+1].cells[i].isupdate = "";
				
				this.rows[index+1].cells[i].enable = "1";
			}
			else if(temCell.colspan==1 && temCell.rowspan>2)
			{
				this.rows[index+1].cells[i].rowspan = temCell.rowspan -1;
				this.rows[index+1].cells[i].spantag = "1";
				//this.rows[index+1].cells[i].spanfeature = "";
				//this.rows[index+1].cells[i].ownerpios = "";
				this.rows[index+1].cells[i].isupdate = "";
				
				this.rows[index+1].cells[i].enable = "1";
			}
			else if(temCell.colspan>1 && temCell.rowspan>1)
			{
				this.rows[index+1].cells[i].rowspan = temCell.rowspan -1;
				this.rows[index+1].cells[i].spantag = "1";
				//this.rows[index+1].cells[i].spanfeature = "";
				//this.rows[index+1].cells[i].ownerpios = "";
				this.rows[index+1].cells[i].isupdate = "";
				
				this.rows[index+1].cells[i].enable = "1";
			}
		}
		else if(temCell.spantag=="2")
		{
			var ownerX = parseInt(temCell.ownerpios.split(",")[0]);
			var ownerY = parseInt(temCell.ownerpios.split(",")[1]);
			var owner = this.rows[ownerX].cells[ownerY];
			if(ownerX < index)
			{
				if(owner.colspan==1 && owner.rowspan==2)
				{
					owner.spantag = "";
					owner.spanfeature = "";
					owner.ownerpios = "";
					owner.isupdate = "";
					
					owner.rowspan -= 1;
				}
				else if(owner.colspan==1 && owner.rowspan>2)
				{
					//owner.spantag = "1";
					//owner.spanfeature = "";
					//owner.ownerpios = "";
					//owner.isupdate = "";
					owner.rowspan -= 1; 
				}
				else if(owner.colspan>1 && owner.rowspan>1)
				{
					//owner.spantag = "1";
					//owner.spanfeature = "";
					//owner.ownerpios = "";
					if(owner.isupdate!="true")
					{
						owner.rowspan -= 1; 
						owner.isupdate = "true";
					}
				}
			}
		}
	}
	var midDelStr = "";
	for(var i=0;i<this.rows[index].cells.length;i++)
	{
		if(this.rows[index].cells[i].spantag!="" && this.rows[index].cells[i].spanfeature==this.rows[index+1].cells[i].spanfeature)
		{
			midDelStr += this.rows[index].cells[i].spanfeature + "&semi;";
		}
	}
	//删除该行
	var temp1 = this.rows.slice(0,index);
	var temp2 = this.rows.slice(index+1);
	this.rows = temp1.concat(temp2);
	//更新删除行以上所有跨主isupdate属性
	for(var i=0;i<index;i++)
	{
		for(var j=0;j<this.rows[i].cells.length;j++)
		{
			if(this.rows[i].cells[j].spantag=="1")
			{
				this.rows[i].cells[j].isupdate = "";
			}
		}
	}
	//更新删除行一下所有行被夸单元格ownerpios坐标（除去被删除行上行跨到被删除行下的）
	for(var i=index+1;i<this.rows.length;i++)
	{
		for(var j=0;j<this.rows[i].cells.length;j++)
		{
			if(this.rows[i].cells[j].spantag!="" && this.rows[i].cells[j].ownerpios!="" && midDelStr.search(this.rows[i].cells[j].spanfeature)==-1)
			{
				var oldX = parseInt(this.rows[i].cells[j].ownerpios.split(",")[0]);
				var oldY = parseInt(this.rows[i].cells[j].ownerpios.split(",")[1]);
				var newpios = (oldX-1)+","+oldY;
				this.rows[i].cells[j].ownerpios = newpios;
			}
		}
	}
}

/**
 * 插入列
 * @param index --列索引，从0开始
 */
ExcelTable.prototype.insertCol = function(index)
{
	if(isNaN(index))
	{
		alert("The parameter 'index' is invalidate!");
		return;
	}
	var curCol = new Array();
	for(var i=0;i<this.rows.length;i++)
	{
		curCol.push(this.rows[i].cells[index]);
	}
	var newCol = ExcelTableUtil.copyCol(curCol);
	//插入列
	for(var i=0;i<this.rows.length;i++)
	{
		var curCells = this.rows[i].cells;
		var cells1 = curCells.slice(0,index);
		var cells2 = curCells.slice(index);
		cells1.push(newCol[i]);
		this.rows[i].cells = cells1.concat(cells2);
	}
	curCol = new Array();
	newCol = new Array();
	for(var i=0;i<this.rows.length;i++)
	{
		curCol.push(this.rows[i].cells[index+1]);
		newCol.push(this.rows[i].cells[index]);
	}
	//改变插入列各单元格属性
	for(var i=0;i<curCol.length;i++)
	{
		var curCell = curCol[i];
		var newCell = newCol[i];
		if(curCell.spantag=="1")
		{
			if(curCell.rowspan==1)
			{
				newCell.spantag = "";
				newCell.spanfeature = "";
				newCell.ownerpios = "";
				newCell.isupdate = "";
			}
			else if(curCell.rowspan>1)
			{
				newCell.spantag = "1";
				newCell.spanfeature = curCell.spanfeature+"_c";
				//newCell.ownerpios = curCell.ownerpios;
				newCell.isupdate = "";
				
				//newCell.rowspan = curCell.rowspan;
			}
		}
		else if(curCell.spantag=="2")
		{
			var ownerX = parseInt(curCell.ownerpios.split(",")[0]);
			var ownerY = parseInt(curCell.ownerpios.split(",")[1]);
			if(ownerY==index)
			{
				//newCell.spantag = "";
				newCell.spanfeature = curCell.spanfeature+"_c";
				//newCell.ownerpios = "";
				newCell.isupdate = "";
			}
			else
			{
				newCell.spantag = "2";
				//newCell.spanfeature = "";
				//newCell.ownerpios = "";
				//newCell.isupdate = "true";
				if(this.rows[ownerX].cells[ownerY].isupdate!="true")
				{
					this.rows[ownerX].cells[ownerY].colspan += 1;
					this.rows[ownerX].cells[ownerY].isupdate = "true";
				}
			}
		}
	}
	
	var midInsertStr = "";
	for(var i=0;i<this.rows.length;i++)
	{
		if(this.rows[i].cells[index].spantag!="" && this.rows[i].cells[index].spanfeature == this.rows[i].cells[index+1].spanfeature)
		{
			midInsertStr += this.rows[i].cells[index].spanfeature + "&semi;";
		}
	}
	
	//更新isupdate属性
	for(var i=0;i<this.rows.length;i++)
	{
		for(var j=0;j<index;j++)
		{
			if(this.rows[i].cells[j].spantag=="1")
			{
					this.rows[i].cells[j].isupdate = "";
			}
		}
	}
	//更新ownerpios属性和单元格排序值
	for(var i=0;i<this.rows.length;i++)
	{
		for(var j=index+1;j<this.rows[i].cells.length;j++)
		{
			this.rows[i].cells[j].ordernum += 1;
			if(this.rows[i].cells[j].spantag!="" && this.rows[i].cells[j].ownerpios!="" && midInsertStr.search(this.rows[i].cells[j].spanfeature)==-1)
			{
				var oldX = parseInt(this.rows[i].cells[j].ownerpios.split(",")[0]);
				var oldY = parseInt(this.rows[i].cells[j].ownerpios.split(",")[1]);
				this.rows[i].cells[j].ownerpios = oldX+","+(oldY+1);
			}
		}
	}
}

/**
 * 追加一列
 */
ExcelTable.prototype.appendCol = function()
{
	for(var i=0;i<this.rows.length;i++)
	{
		var curRow = this.rows[i];
		var newCell;
		newCell = new ExcelCell("","","newCell",1,1,1000,curRow.cells.length,
						"","","",0,"","",
						"","","0","1",
						"","","","");
		curRow.cells.push(newCell);
	}
}

/**
 * 删除列
 * @param index --列索引，从0开始
 */
ExcelTable.prototype.deleteCol = function(index)
{
	if(isNaN(index))
	{
		alert("The parameter 'index' is invalidate!");
		return;
	}
	//循环该列的所有单元格，修改关联单元格的属性
	for(var i=0;i<this.rows.length;i++)
	{
		var curCell = this.rows[i].cells[index];
		if(curCell.spantag=="1")
		{
			if(curCell.rowspan==1 && curCell.colspan==2)
			{
				this.rows[i].cells[index+1].spantag = "";
				this.rows[i].cells[index+1].spanfeature = "";
				this.rows[i].cells[index+1].ownerpios = "";
				this.rows[i].cells[index+1].isupdate = "";
				
				this.rows[i].cells[index+1].enable = "1";
			}
			else if(curCell.rowspan==1 && curCell.colspan>2)
			{
				this.rows[i].cells[index+1].spantag = "1";
				//this.rows[i].cells[index+1].spanfeature = "";
				//this.rows[i].cells[index+1].ownerpios = "";
				this.rows[i].cells[index+1].isupdate = "";
				this.rows[i].cells[index+1].colspan = curCell.colspan - 1;
				
				this.rows[i].cells[index+1].enable = "1";
				
			}
			else if(curCell.rowspan>1 && curCell.colspan>1)
			{
				this.rows[i].cells[index+1].spantag = "1";
				//this.rows[i].cells[index+1].spanfeature = "";
				//this.rows[i].cells[index+1].ownerpios = "";
				this.rows[i].cells[index+1].isupdate = "";
				this.rows[i].cells[index+1].colspan = curCell.colspan - 1;
				this.rows[i].cells[index+1].rowspan = curCell.rowspan;
				
				this.rows[i].cells[index+1].enable = "1";
				
			}
		}
		else if(curCell.spantag=="2")
		{
			var ownerX = parseInt(this.rows[i].cells[index].ownerpios.split(",")[0]);
			var ownerY = parseInt(this.rows[i].cells[index].ownerpios.split(",")[1]);
			var owner = this.rows[ownerX].cells[ownerY];
			if(owner.colspan>1)
			{
				if(owner.rowspan==1 &&　owner.colspan==2)
				{
					owner.spantag = "";
					owner.spanfeature = "";
					owner.ownerpios = "";
					owner.isupdate = "";
					owner.rowspan = 1;
					owner.colspan = 1;
				}
				else if(owner.rowspan==1 && owner.colspan>2)
				{
					//owner.spantag = "1";
					//owner.spanfeature = "";
					//owner.ownerpios = "";
					//owner.isupdate = "";
					owner.colspan -= 1;
				}
				else if(owner.rowspan>1 && owner.colspan>1)
				{
					if(ownerY!=index && owner.isupdate != "true")
					{
						owner.colspan -= 1;
						owner.isupdate = "true";
					}
				}
			}
		}
	}
	var midDelStr = "";
	if(index<this.rows[0].cells.length-1)
	{
		for(var i=0;i<this.rows.length;i++)
		{
			if(this.rows[i].cells[index].spantag!="" && this.rows[i].cells[index].spanfeature == this.rows[i].cells[index+1].spanfeature)
			{
				midDelStr += this.rows[i].cells[index].spanfeature + "&semi;";
			}
		}
	}
	//删除该列
	for(var i=0;i<this.rows.length;i++)
	{
		var curCells = this.rows[i].cells;
		var temp1 = curCells.slice(0,index);
		var temp2 = curCells.slice(index+1);
		this.rows[i].cells = temp1.concat(temp2);
	}
	//修改isupdate属性
	for(var i=0;i<this.rows.length;i++)
	{
		for(var j=0;j<index;j++)
		{
			if(this.rows[i].cells[j].spantag=="1")
			{
				this.rows[i].cells[j].isupdate  = "";
			}
		}
	}
	//修改ownerpios属性和排序值
	for(var i=0;i<this.rows.length;i++)
	{
		for(var j=index;j<this.rows[i].cells.length;j++)
		{
			this.rows[i].cells[j].ordernum -= 1; 
			if(this.rows[i].cells[j].spantag!="" && this.rows[i].cells[j].ownerpios!="" && midDelStr.search(this.rows[i].cells[j].spanfeature)==-1)
			{
				var oldX = parseInt(this.rows[i].cells[j].ownerpios.split(",")[0]);
				var oldY = parseInt(this.rows[i].cells[j].ownerpios.split(",")[1]);
				this.rows[i].cells[j].ownerpios = oldX + "," + (oldY-1);
			}
		}
	}
}

/**
 * 合并单元格
 * @param pioX --行索引，从0开始
 * @param pioY --列索引，从0开始
 * @param rowspan --跨行数，>=1
 * @param colspan --跨列数，>=1
 */
ExcelTable.prototype.mergeRowAndCol = function(pioX,pioY,rowspan,colspan)
{
	if(isNaN(pioX) || isNaN(pioY) || isNaN(rowspan) || isNaN(colspan) || rowspan<1 || colspan<1)
	{
		alert("参数不合法！");
		return false;
	}
	//if((pioX == this.rows.length-1) && (rowspan>1 || colspan>1))
	//{
	//	alert("不能合并数据行（最后一行）！");
	//	return false;
	//}
	var curCell = this.rows[pioX].cells[pioY];
	if(curCell.rowspan==rowspan && curCell.colspan==colspan)
	{
		return true;
	}
	if(pioX+rowspan>this.rows.length)
	{
		alert("合并行不能超过行边界！");
		return false;
	}
	if(pioY+colspan>this.rows[pioX].cells.length)
	{
		alert("合并列不能超过列边界!");
		return false;
	}
	if(rowspan>curCell.rowspan || colspan>curCell.colspan)
	{
		var temspanX = rowspan - curCell.rowspan;
		var temspanY = colspan - curCell.colspan;
		if(temspanX>0 && temspanY>0)
		{
			for(var i=pioX+curCell.rowspan;i<pioX+curCell.rowspan+temspanX;i++)
			{
				for(var j=pioY;j<pioY+colspan;j++)
				{
					if(this.rows[i].cells[j].spanfeature!="")
					{
						alert("向下合并跨越了其它合并区域，不能合并！");
						return false;
					}
				}
			}
			for(var i=pioX;i<pioX+curCell.rowSpan;i++)
			{
				for(var j=pioY+curCell.colspan;j<pioY+curCell.colspan+temspanY;j++)
				{
					if(this.rows[i].cells[j].spanfeature!="")
					{
						alert("向右合并跨越了其他合并区域，不能合并！");
						return false;
					}
				}
			}
		}
		if(temspanX>0 && temspanY<=0)
		{
			for(var i=pioX+curCell.rowspan;i<pioX+curCell.rowspan+temspanX;i++)
			{
				for(var j=pioY;j<pioY+colspan;j++)
				{
					if(this.rows[i].cells[j].spanfeature!="")
					{
						alert("向下合并跨越了其他合并区域，不能合并！");
						return false;
					}
				}
			}
		}
		if(temspanX<=0 && temspanY>0)
		{
			for(var i=pioX;i<pioX+rowspan;i++)
			{
				for(var j=pioY+curCell.colspan;j<pioY+curCell.colspan+temspanY;j++)
				{
					if(this.rows[i].cells[j].spanfeature!="")
					{
						alert("向右合并跨越了其它合并区域，不能合并！");
						return false;
					}
				}
			}
		}
	}
	if(curCell.rowspan==1 && curCell.colspan==1)
	{
		curCell.spantag = "1";
		curCell.enable = "1";
		curCell.ownerpios = pioX+","+pioY;
		curCell.isupdate = "";
		var feature = (new Date()).getTime()+"";
		curCell.spanfeature = feature;
		for(var i=pioX;i<pioX+rowspan;i++)
		{
			for(var j=pioY;j<pioY+colspan;j++)
			{
				if(i!=pioX || j!=pioY)
				{
					this.rows[i].cells[j].spanfeature = feature;
					this.rows[i].cells[j].ownerpios = pioX+","+pioY;
					this.rows[i].cells[j].isupdate = "";
					this.rows[i].cells[j].spantag = "2";
					this.rows[i].cells[j].enable = "0";
				}
				//ExcelTableUtil.cellToString(this.rows[i].cells[j]);
			}
		}
	}
	else
	{
		//alert("enter else");
		for(var i=pioX;i<pioX+curCell.rowspan;i++)
		{
			for(var j=pioY;j<pioY+curCell.colspan;j++)
			{
				if(i!=pioX || j!=pioY)
				{
					this.rows[i].cells[j].spanfeature = "";
					this.rows[i].cells[j].ownerpios = "";
					this.rows[i].cells[j].isupdate = "";
					this.rows[i].cells[j].spantag = "";
					this.rows[i].cells[j].enable = "1";
				}
			}
		}
		if(rowspan>1 || colspan>1)
		{
			for(var i=pioX;i<pioX+rowspan;i++)
			{
				for(var j=pioY;j<pioY+colspan;j++)
				{
					if(i!=pioX || j!=pioY)
					{
						this.rows[i].cells[j].spanfeature = curCell.spanfeature;
						this.rows[i].cells[j].ownerpios = pioX+","+pioY;
						this.rows[i].cells[j].isupdate = "";
						this.rows[i].cells[j].spantag = "2";
						this.rows[i].cells[j].enable = "0";
					}
				}
			}
		}
	}
	
	curCell.rowspan = rowspan;
	curCell.colspan = colspan;
	//ExcelTableUtil.cellToString(this.rows[0].cells[1]);
	//ExcelTableUtil.cellToString(this.rows[1].cells[1]);
	return true;
}
/*--ExcelTable对象--*/

/*--ExcelRow对象--*/
function ExcelRow(id,cells,className)
{
	this.id          = id; //对象ID
	this.cells       = cells; //单元格集
	this.className   = className; //样式名
}
/*--ExcelRow对象--*/

/*--ExcelTd对象--*/
function ExcelCell(id,fieldname,displayname,rowspan,colspan,width,ordernum,
					align,colcolor,datatype,datalength,datainfo,operator,
					comparedata,displaycolor,isgroup,enable,
					spantag,spanfeature,ownerpios,isupdate)
{
		this.id           = id; //对象id
		this.fieldname    = fieldname; //字段名
		this.displayname  = displayname; //字段显示名
		this.rowspan      = rowspan; //跨行数
		this.colspan      = colspan; //跨列数
		this.width        = width; //列宽
		this.ordernum     = ordernum; //排序值
		this.align        = align; //对其方式
		this.colcolor     = colcolor; //列颜色
		this.datatype     = datatype; //数据类型
		this.datalength   = datalength; //数据长度
		this.datainfo     = datainfo; //数据信息
		this.operator     = operator; //操作符
		this.comparedata  = comparedata; //比较数据
		this.displaycolor = displaycolor; //展示颜色
		this.isgroup      = isgroup; //是否分sheet
		this.enable       = enable; //是否可用
		
		//以下四个标志全为有跨和被跨的单元格使用的，没有被跨且为1*1的单元格的这些属性值为""
		this.spantag      = spantag; //跨标志（"1"：跨;"2"：被跨;""：普通1*1）
		this.spanfeature  = spanfeature; //跨特征（标识同一组跨）
		this.ownerpios    = ownerpios; //跨主的坐标位置
		this.isUpdate     = isupdate; //是否被更改
}
/*--ExcelTd对象--*/
/*--ExcelCol对象--*/
function ExcelCol(cells)
{
	this.cells = cells;
}
/*--ExcelCol对象--*/