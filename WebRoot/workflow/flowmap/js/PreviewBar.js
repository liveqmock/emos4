function Config() {}
Config.modelwidth = 120;
Config.modelheight = 50;

function PreviewBar() {}

PreviewBar.preBarObj = null;
PreviewBar.preDrawObj = null;
PreviewBar.preTitleObj = null;
PreviewBar.preBtnObj = null;
PreviewBar.preBoxObj = null;
PreviewBar.barHeight = 0;
PreviewBar.barWidth = 0;

PreviewBar.initX = 7;
PreviewBar.smallX = 1;
PreviewBar.BarX = 5;
PreviewBar.modelWidth = Config.modelwidth/PreviewBar.smallX;
PreviewBar.modelHeight = Config.modelheight/PreviewBar.smallX;

PreviewBar.maxX = PreviewBar.barWidth;
PreviewBar.maxY = PreviewBar.barHeight;
PreviewBar.processes = new Array();
PreviewBar.links = new Array();

PreviewBar.inittip = true;
PreviewBar.status = 'close';

PreviewBar.scrollTip = 2;

PreviewBar.init = function()
{
	PreviewBar.smallX = PreviewBar.initX;
	PreviewBar.preBarObj = document.getElementById('pPreviewDIV');
	PreviewBar.preDrawObj = document.getElementById('pDrawDIV');
	PreviewBar.preTitleObj = document.getElementById('pTitleDIV');
	PreviewBar.preBtnObj = document.getElementById('preButton');
	PreviewBar.preBoxObj = document.getElementById('preBox');
	PreviewBar.preBarObj.style.top = parseInt(document.body.clientHeight) - 18 - 20;
	PreviewBar.preBarObj.style.left = 1;
	PreviewBar.preBarObj.style.display = 'block';
	PreviewBar.preBtnObj.onclick = PreviewBar.open;
	
	var designObj = document.body;
	PreviewBar.barWidth = parseInt(designObj.clientWidth)/PreviewBar.BarX;
	PreviewBar.barHeight = parseInt(designObj.clientHeight)/PreviewBar.BarX;
	window.attachEvent('onresize', PreviewBar.windowresize);
}

PreviewBar.open = function()
{
	PreviewBar.preBarObj.style.top = parseInt(document.body.clientHeight) - 16 - PreviewBar.barHeight - 20;
	PreviewBar.preBarObj.style.height = PreviewBar.barHeight + 20;
	PreviewBar.preBarObj.style.width = PreviewBar.barWidth;
	PreviewBar.preDrawObj.style.height = PreviewBar.barHeight - 3;
	PreviewBar.preDrawObj.style.width = PreviewBar.barWidth - 2;
	PreviewBar.preDrawObj.style.display = 'block';
	PreviewBar.preTitleObj.style.display = 'block';
	PreviewBar.preBarObj.style.borderWidth = 2;
	PreviewBar.preBtnObj.src = 'images/previewClose.jpg';
	PreviewBar.preBtnObj.style.left = PreviewBar.barWidth - 23;
	PreviewBar.preBtnObj.style.top = 0;
	
	PreviewBar.setBoxSize();
	
	PreviewBar.preBtnObj.onclick = PreviewBar.close;
	PreviewBar.status = 'open';
}

PreviewBar.close = function()
{
	PreviewBar.preBarObj.style.top = parseInt(document.body.clientHeight) - 18 - 20;
	PreviewBar.preBarObj.style.height = 20;
	PreviewBar.preBarObj.style.width = 20;
	PreviewBar.preBarObj.style.borderWidth = 1;
	PreviewBar.preDrawObj.style.height = 0;
	PreviewBar.preDrawObj.style.width = 0;
	PreviewBar.preDrawObj.style.display = 'none';
	PreviewBar.preTitleObj.style.display = 'none';
	PreviewBar.preBtnObj.src = 'images/previewOpen.jpg';
	PreviewBar.preBtnObj.style.left = 0;
	PreviewBar.preBtnObj.style.top = 0;
	PreviewBar.preBoxObj.style.width = 0;
	PreviewBar.preBoxObj.style.height = 0;
	PreviewBar.preBtnObj.onclick = PreviewBar.open;
	PreviewBar.status = 'close';
}

PreviewBar.setBoxSize = function()
{
	PreviewBar.preBoxObj.style.width = PreviewBar.barWidth / (PreviewBar.smallX/PreviewBar.BarX);
	PreviewBar.preBoxObj.style.height = PreviewBar.barHeight / (PreviewBar.smallX/PreviewBar.BarX);
}

PreviewBar.setBarSize = function(x, y)
{
	if((parseInt(x) + Config.modelwidth*1.5)/PreviewBar.barWidth > PreviewBar.smallX)
	{
		PreviewBar.smallX = (x + Config.modelwidth*1.5)/PreviewBar.barWidth;
		PreviewBar.draw();
	}
	if((parseInt(y) + Config.modelheight*1.5)/PreviewBar.barHeight > PreviewBar.smallX)
	{
		PreviewBar.smallX = (y + Config.modelheight*1.5)/PreviewBar.barHeight;
		PreviewBar.draw();
	}
}

PreviewBar.drawProcess = function(pid, x, y, color)
{
	PreviewBar.processes.push([pid, 'STEP', x, y, color]);
	PreviewBar.setBarSize(x, y);
}

PreviewBar.drawLink = function(lid, ltype, x1, y1, x2, y2, x3, y3, x4, y4)
{
	PreviewBar.links.push([lid, ltype, x1, y1, x2, y2, x3, y3, x4, y4]);
}

PreviewBar.draw = function()
{
	if(!PreviewBar.inittip)
	{
		PreviewBar.clear();
		PreviewBar.setBoxSize();
		PreviewBar.modelWidth = Config.modelwidth/PreviewBar.smallX;
		PreviewBar.modelHeight = Config.modelheight/PreviewBar.smallX;
		for(var i = 0; i < PreviewBar.processes.length; i++)
		{
			var rect = document.createElement('v:RoundRect');
			rect.id = 'pre_' + PreviewBar.processes[i][0];
			rect.strokeweight = '1';
			rect.arcsize = '0.15';
			rect.style.width = PreviewBar.modelWidth + 'px';
			rect.style.height = PreviewBar.modelHeight + 'px';
			rect.style.position = 'absolute';
			rect.style.top = PreviewBar.processes[i][3]/PreviewBar.smallX + 'px';
			rect.style.left = PreviewBar.processes[i][2]/PreviewBar.smallX + 'px';
			rect.filled = 't';
			rect.fillcolor= PreviewBar.processes[i][4];
			var fill = document.createElement('v:fill');
			fill.type = 'gradient';
			fill.colors2 = 'White';
			rect.appendChild(fill);
			PreviewBar.preDrawObj.appendChild(rect);
			//PreviewBar.preBarObj.innerHTML += '<v:Rect id="pre_' + PreviewBar.processes[i][0] + '" strokeweight="1" style="width:' + PreviewBar.modelWidth + 'px; height:' + PreviewBar.modelHeight + 'px; position:absolute; top:' + PreviewBar.processes[i][3]/PreviewBar.smallX + 'px; left:' + PreviewBar.processes[i][2]/PreviewBar.smallX + 'px;" filled="t" fillcolor="' + PreviewBar.processes[i][4] + '"><v:fill type="gradient" colors2="White" /></v:Rect>';
		}
		
		for(var i = 0; i < PreviewBar.links.length; i++)
		{
			if(PreviewBar.links[i][1] == 'IN')
			{
				var arrow = document.createElement('v:PolyLine');
				arrow.id = 'pre_' + PreviewBar.links[i][0];
				arrow.style.position = 'absolute';
				arrow.filled = 'false';
				arrow.Points = PreviewBar.links[i][2]/PreviewBar.smallX+','+PreviewBar.links[i][3]/PreviewBar.smallX+' '+PreviewBar.links[i][4]/PreviewBar.smallX+','+PreviewBar.links[i][5]/PreviewBar.smallX+' '+PreviewBar.links[i][6]/PreviewBar.smallX+','+PreviewBar.links[i][7]/PreviewBar.smallX+' '+PreviewBar.links[i][8]/PreviewBar.smallX+','+PreviewBar.links[i][9]/PreviewBar.smallX;
				arrow.strokeweight = '1px';
				var stroke = document.createElement('v:stroke');
				arrow.appendChild(stroke);
				PreviewBar.preDrawObj.appendChild(arrow);
			}
			else
			{
				var arrow = document.createElement('v:Line');
				arrow.id = 'pre_' + PreviewBar.links[i][0];
				arrow.style.position = 'absolute';
				arrow.from = PreviewBar.links[i][2]/PreviewBar.smallX + ',' + PreviewBar.links[i][3]/PreviewBar.smallX;
				arrow.to = PreviewBar.links[i][4]/PreviewBar.smallX + ',' + PreviewBar.links[i][5]/PreviewBar.smallX;
				arrow.filled = 'false';
				arrow.strokeweight = '1px';
				var stroke = document.createElement('v:stroke');
				arrow.appendChild(stroke);
				PreviewBar.preDrawObj.appendChild(arrow);
			}
			
			//PreviewLinkDOMHandler.draw(PreviewBar.preBarObj, PreviewBar.links[i][0], PreviewBar.links[i][1], PreviewBar.links[i][2], PreviewBar.links[i][3], PreviewBar.links[i][4], PreviewBar.links[i][5], PreviewBar.links[i][6]);
		}
	}
}

PreviewBar.clear = function()
{
	PreviewBar.preDrawObj.innerHTML = '';
	PreviewBar.preDrawObj.appendChild(PreviewBar.preBoxObj);
}

PreviewBar.startMove = function()
{
	PreviewBar.addX = parseInt(event.offsetX);
	PreviewBar.addY = parseInt(event.offsetY);
	PreviewBar.ismove = true;
}

PreviewBar.moveBox = function()
{
	if(PreviewBar.ismove)
	{
		var aX = parseInt(event.offsetX) - PreviewBar.addX;
		var aY = parseInt(event.offsetY) - PreviewBar.addY;
		var designObj = document.getElementById('Designer');
		
		if(parseInt(PreviewBar.preBoxObj.style.left) + aX <= 0)
		{
			PreviewBar.preBoxObj.style.left = 0;
		}
		else if(parseInt(PreviewBar.preBoxObj.style.left) + parseInt(PreviewBar.preBoxObj.style.width) + aX >= PreviewBar.barWidth)
		{
			PreviewBar.preBoxObj.style.left = parseInt(PreviewBar.preDrawObj.style.width) - parseInt(PreviewBar.preBoxObj.style.width);
		}
		else
		{
			PreviewBar.preBoxObj.style.left = parseInt(PreviewBar.preBoxObj.style.left) + aX;
		}
		
		if(parseInt(PreviewBar.preBoxObj.style.top) + aY <= 0)
		{
			PreviewBar.preBoxObj.style.top = 0;
		}
		else if(parseInt(PreviewBar.preBoxObj.style.top) + parseInt(PreviewBar.preBoxObj.style.height) + aY >= PreviewBar.barHeight)
		{
			PreviewBar.preBoxObj.style.top = parseInt(PreviewBar.preDrawObj.style.height) - parseInt(PreviewBar.preBoxObj.style.height);
		}
		else
		{
			PreviewBar.preBoxObj.style.top = parseInt(PreviewBar.preBoxObj.style.top) + aY;
		}
	}
}

PreviewBar.endMove = function()
{
	if(PreviewBar.ismove)
	{
		var designObj = document.getElementById('Designer');
		PreviewBar.ismove = false;
		PreviewBar.scrollTip = 0;
		designObj.scrollLeft = parseInt(PreviewBar.preBoxObj.style.left) * PreviewBar.smallX;
		designObj.scrollTop = parseInt(PreviewBar.preBoxObj.style.top) * PreviewBar.smallX;
	}
}

PreviewBar.scroll = function()
{
	if(PreviewBar.scrollTip == 2)
	{
		var designObj = document.getElementById('Designer');
		PreviewBar.ismove = false;
		if(parseInt(designObj.scrollLeft) / PreviewBar.smallX + parseInt(PreviewBar.preBoxObj.style.width) < PreviewBar.barWidth)
		{
			PreviewBar.preBoxObj.style.left = parseInt(designObj.scrollLeft) / PreviewBar.smallX;
		}
		else
		{
			PreviewBar.preBoxObj.style.left = PreviewBar.barWidth - parseInt(PreviewBar.preBoxObj.style.width);
		}
		if(parseInt(designObj.scrollTop) / PreviewBar.smallX + parseInt(PreviewBar.preBoxObj.style.height) < PreviewBar.barHeight)
		{
			PreviewBar.preBoxObj.style.top = parseInt(designObj.scrollTop) / PreviewBar.smallX;
		}
		else
		{
			PreviewBar.preBoxObj.style.top = PreviewBar.barHeight - parseInt(PreviewBar.preBoxObj.style.height);
		}
	}
	else
	{
		PreviewBar.scrollTip++;
	}
}

PreviewBar.windowresize = function()
{
	var designObj = document.getElementById('Designer');
	if(PreviewBar.status == 'open')
	{
		PreviewBar.preBarObj.style.top = parseInt(document.body.clientHeight) - 16 - PreviewBar.barHeight - 20;
		PreviewBar.barWidth = parseInt(designObj.clientWidth)/PreviewBar.BarX;
		PreviewBar.barHeight = parseInt(designObj.clientHeight)/PreviewBar.BarX;
		PreviewBar.open();
	}
	else
	{
		PreviewBar.preBarObj.style.top = parseInt(document.body.clientHeight) - 18 - 20;
		PreviewBar.barWidth = parseInt(designObj.clientWidth)/PreviewBar.BarX;
		PreviewBar.barHeight = parseInt(designObj.clientHeight)/PreviewBar.BarX;
	}
	
	var pmX = 0;
	var pmY = 0;
	var ri = 0;
	for(var i = 0; i < PreviewBar.processes.length; i++)
	{
		if(PreviewBar.processes[i][2] > pmX)
			pmX = PreviewBar.processes[i][2];
		if(PreviewBar.processes[i][3] > pmY)
			pmY = PreviewBar.processes[i][3];
	}
	PreviewBar.smallX = PreviewBar.initX;
	PreviewBar.setBarSize(pmX, pmY);
}
