function JsTable(tname, ttype, twidth, theight, tcolumns, tdata)
{
	this.name = tname;
	this.type = ttype;
	this.width = twidth;
	this.height = theight;
	this.columns = tcolumns;
	this.data = tdata;
}

JsTable.prototype =
{
	draw : function(pNode)
	{
		var divbox = null;
		if(this.type)
		{
			divbox = document.createElement('div');
			divbox.id = this.name;
			divbox.className = 'JT_Box';
			divbox.style.width = this.width;
			divbox.style.height = this.height;
		}
		var tbl = document.createElement('table');
		tbl.className = 'JT_Table';
		tbl.cellPadding = 1;
		tbl.cellSpacing = 1;
		var headtr = tbl.insertRow(-1);
		headtr.className = 'JT_HeadTr';
		for(var i = 0; i < this.columns.length; i++)
		{
			if(this.columns[i].view)
			{
				var headtd = headtr.insertCell(-1);
				headtd.className = 'JT_HeadTd';
				headtd.innerHTML = this.columns[i].head;
				var stylesarr = this.columns[i].headstyles;
				for(var s = 0; s < stylesarr.length; s++)
                {
                    headtd.style[stylesarr[s][0]] = stylesarr[s][1];
                }
			}
		}
		
		for(var i = 0; i < this.data.length; i++)
		{
			var datarow = this.data[i];
			var datatr = tbl.insertRow(-1);
			if((i + 3) % 2 != 0)
			{
				datatr.className = 'JT_DataTr';
			}
			else
			{
				datatr.className = 'JT_DataTr_Cross';
			}
			
			for(var j = 0; j < this.columns.length; j++)
			{
				if(this.columns[j].view)
				{
					var paraarr = this.columns[j].paras;
					var datacol = datatr.insertCell(-1);
					datacol.className = 'JT_DataTd';
				    var stylesarr = this.columns[j].datastyles;
				    for(var s = 0; s < stylesarr.length; s++)
                    {
                        datacol.style[stylesarr[s][0]] = stylesarr[s][1];
                    }
					if(j < datarow.length)
					{
						var content = datarow[j];
						for(var p = 0; p < paraarr.length; p++)
						{
						    var para = paraarr[p][0];
						    var value = paraarr[p][1];
						    switch(value.slice(0, value.indexOf(':')))
						    {
						        case '@COL':
						            var pcindex = value.slice(value.indexOf(':') + 1);
						            if((datarow[parseInt(pcindex)] + '').indexOf(para) > -1)
						            {
						                alert('列数据绑定出现死循环！请检查绑定参数！');
						                return;
						            }
						            value = datarow[parseInt(pcindex)];
						            break;
						    }
						    while(content.indexOf(para) > -1)
						    {
							    content = content.replace(para, value);
							}
						}
						datacol.innerHTML = content;
					}
					else
					{
						datacol.innerHTML = '&nbsp;';
					}
				}
			}
		}
		divbox.appendChild(tbl);
		if(!pNode)
		{
		    pNode = document.body;
		}
		if(document.getElementById(this.name))
		{
		    document.getElementById(this.name).outerHTML = '';
		}
		pNode.appendChild(divbox);
	}
}

function JsCell(cview, chead, cheadstyles, cdatastyles, cparas)
{
	this.view = cview;
	this.head = chead;
	this.headstyles = cheadstyles;
	this.datastyles = cdatastyles;
	this.paras = cparas;
}