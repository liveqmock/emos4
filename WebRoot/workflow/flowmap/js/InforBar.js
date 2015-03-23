function InforBar() {}

InforBar.dpanel = null;
InforBar.dcontent = null;
InforBar.dbutton = null;
InforBar.selectObjs = [];
InforBar.selectObj = null;
InforBar.panelwidth = 300;
InforBar.panelHeight = 0;
InforBar.movestep = 0;
InforBar.moveadd = 0;
InforBar.movetime = 0;
InforBar.movecount = 0;
InforBar.movelength = 0;
InforBar.moveheight =0;
InforBar.Interval = null;
InforBar.Intervalstep = 0;

InforBar.init = function()
{
	InforBar.dpanel = document.getElementById('pPanelDIV');
	InforBar.dcontent = InforBar.dpanel.childNodes[0];
	InforBar.dbutton = InforBar.dpanel.childNodes[1];
	window.attachEvent('onresize', InforBar.windowresize);
    
    InforBar.dpanel.style.left = parseInt(document.body.clientWidth) - InforBar.panelwidth - 35;
	InforBar.dpanel.style.top = 0;
	InforBar.dcontent.innerHTML = '';
	InforBar.dcontent.style.display = 'none';
	InforBar.dcontent.style.width = InforBar.panelwidth;
	InforBar.dbutton.style.width = InforBar.panelwidth;
	InforBar.dbutton.innerHTML = '<a href="javascript:" onclick="InforBar.show()">显示环节详细信息</a>';
	InforBar.dpanel.style.display = 'block';
	
	InforBar.panelHeight = parseInt(document.body.clientHeight);
}

InforBar.show = function()
{
	InforBar.dpanel.style.left = parseInt(document.body.clientWidth) - InforBar.panelwidth - 35;
	InforBar.dpanel.style.top = 0;
	InforBar.dcontent.style.height = 1;
	InforBar.dcontent.style.display = 'block';
	
	InforBar.Move('open', parseInt(document.body.clientHeight) - 30 - InforBar.panelHeight/10);
	
	InforBar.dcontent.style.width = InforBar.panelwidth;
	InforBar.dbutton.style.width = InforBar.panelwidth;
	InforBar.dbutton.innerHTML = '<a href="javascript:" onclick="InforBar.hide()">隐藏环节详细信息</a>';
	InforBar.dpanel.style.display = '';
	isInforBarShow = true;
}

InforBar.hide = function()
{
	InforBar.dpanel.style.left = parseInt(document.body.clientWidth) - InforBar.panelwidth - 35;
	InforBar.dpanel.style.top = 0;
	InforBar.dcontent.innerHTML = '';
	InforBar.Move('close', parseInt(document.body.clientHeight) - 30 - InforBar.panelHeight/10);
	InforBar.dcontent.style.width = InforBar.panelwidth;
	InforBar.dbutton.style.width = InforBar.panelwidth;
	InforBar.dbutton.innerHTML = '<a href="javascript:" onclick="InforBar.show()">显示环节详细信息</a>';
	InforBar.dpanel.style.display = 'block';
	
	if(InforBar.selectObj != null)
	{
		ProcessDOMHandler.unfocus(InforBar.selectObj);
	}
	isInforBarShow = false;
}

InforBar.Move = function(type, height)
{
	InforBar.movestep = 0;
	InforBar.movetime = height/10;
	InforBar.moveadd = InforBar.movetime/5;
	InforBar.movecount = 0;
	InforBar.movelength = 0;
	InforBar.moveheight =height;
	InforBar.Interval = null;
	InforBar.Intervalstep = 20;
	if(type == 'open')
	{
		InforBar.Interval = setInterval(InforBar.MoveOpen, InforBar.Intervalstep);
	}
	else if(type == 'close')
	{
		InforBar.Interval = setInterval(InforBar.MoveClose, InforBar.Intervalstep);
	}
}

InforBar.MoveOpen = function()
{
	if(InforBar.movelength < InforBar.movetime)
	{
		InforBar.movestep += InforBar.moveadd;
		InforBar.movelength += InforBar.movestep;
		InforBar.movecount++;
	}
	else if(InforBar.movelength > parseInt(InforBar.moveheight) - InforBar.movetime && InforBar.movestep > 0)
	{
		InforBar.movestep -= InforBar.moveadd;
		InforBar.movelength += InforBar.movestep;
	}
	else
	{
		InforBar.movelength += InforBar.movestep;
	}
	
	if(parseInt(InforBar.dcontent.style.height) + InforBar.movestep > parseInt(InforBar.moveheight))
	{
		InforBar.dcontent.style.height = parseInt(InforBar.moveheight);
		InforBar.movestep = 0;
	}
	else
	{
		InforBar.dcontent.style.height = parseInt(InforBar.dcontent.style.height) + InforBar.movestep;
	}
	
	if(InforBar.movelength >= parseInt(InforBar.moveheight) || InforBar.movestep <= 0)
	{
		clearInterval(InforBar.Interval);
	}
}
InforBar.MoveClose = function()
{
	if(InforBar.movelength < InforBar.movetime)
	{
		InforBar.movestep += InforBar.moveadd;
		InforBar.movelength += InforBar.movestep;
		InforBar.movecount++;
	}
	else if(InforBar.movelength > parseInt(InforBar.moveheight) - InforBar.movetime && InforBar.movestep > 0)
	{
		InforBar.movestep -= InforBar.moveadd;
		InforBar.movelength += InforBar.movestep;
	}
	else
	{
		InforBar.movelength += InforBar.movestep;
	}
	
	if(parseInt(InforBar.dcontent.style.height) - InforBar.movestep < 0)
	{
		InforBar.dcontent.style.height = 0;
	}
	else
	{
		InforBar.dcontent.style.height = parseInt(InforBar.dcontent.style.height) - InforBar.movestep;
	}
	
	if(InforBar.movestep == 0 || parseInt(InforBar.dcontent.style.height) == 0)
	{
		InforBar.dcontent.style.display = 'none';
		clearInterval(InforBar.Interval);
	}
}

InforBar.showProcessInfo = function(phaseID)
{
	if(!isInforBarShow) return false;
	var logDoc = getPhaseLog(phaseID);
	
	if(logDoc == null)
	{
		return false;
	}
	
	for(var i = 0; i < InforBar.selectObjs.length; i++)
	{
		if(InforBar.selectObjs[i][0] == phaseID)
		{
			var processobj = InforBar.selectObjs[i][1];
			InforBar.selectObj = processobj;
			break;
		}
	}
	
	var logXml = new ActiveXObject("Microsoft.XMLDOM");
	logXml.async="false"
	logXml.loadXML(logDoc)
	var pList = logXml.documentElement;
	
	InforBar.dcontent.innerHTML = '';
	if(pList.childNodes.length == 0)
	{
		var titleul = document.createElement('ul'); titleul.className = 'titleul';
		var liH1 = document.createElement('li'); liH1.className = 'titleH1'; liH1.innerText = '';
		var lihr = document.createElement('li'); liH2.style.listStyleType = 'none';
		var hr = document.createElement('hr'); lihr.appendChild(hr);
		titleul.appendChild(liH1);
		titleul.appendChild(liH2);
		titleul.appendChild(lihr);
		InforBar.dcontent.appendChild(titleul);
		
		var mainul = document.createElement('ul');
		mainul.className = 'mainul';
		var main1 = document.createElement('li'); main1.innerHTML = '<b>环节描述：</b>';
		var main2 = document.createElement('li'); main2.innerHTML = '<b>当前状态：</b>';
		var main3 = document.createElement('li'); main3.innerHTML = '<b>处理人：</b>';
		var main4 = document.createElement('li'); main4.innerHTML = '<b>开始时间：</b>';
		var main5 = document.createElement('li'); main5.innerHTML = '<b>受理时间：</b>';
		var main6 = document.createElement('li'); main6.innerHTML = '<b>完成时间：</b>';
		mainul.appendChild(main1);
		mainul.appendChild(main2);
		mainul.appendChild(main3);
		mainul.appendChild(main4);
		mainul.appendChild(main5);
		mainul.appendChild(main6);
		InforBar.dcontent.appendChild(mainul);
	}
	else
	{
	    var processInfoList = pList.getElementsByTagName('ProcessInfo');
	    
	    
		for(var i = 0; i < processInfoList.length; i++)
		{
			var pInfo = processInfoList[i];
			var Info = pInfo.childNodes[0];
			if(i == 0)
			{
				var titleul = document.createElement('ul'); titleul.className = 'titleul';
				var liH1 = document.createElement('li'); liH1.className = 'titleH1'; liH1.innerText = Info.childNodes[1].text;
				var lihr = document.createElement('li'); lihr.style.listStyleType = 'none';
				var hr = document.createElement('hr'); lihr.appendChild(hr);
				titleul.appendChild(liH1);
				titleul.appendChild(lihr);
				InforBar.dcontent.appendChild(titleul);
			}
			var m = i + 1;
			var mainul = document.createElement('ul');
			mainul.className = 'mainul';
			var main1 = document.createElement('li'); main1.innerHTML = '<b>环节描述：</b>' + Info.childNodes[1].text;
			var main2 = document.createElement('li'); main2.innerHTML = '<b>当前状态：</b>' + Info.childNodes[0].text;
			var main3 = document.createElement('li'); main3.innerHTML = '<b>处理组成员：</b>' + Info.childNodes[3].text == null||Info.childNodes[3].text=="null"?"":Info.childNodes[3].text;
			var main4 = document.createElement('li'); main4.innerHTML = '<b>处理人：</b>' + Info.childNodes[2].text;
			var main5 = document.createElement('li'); main5.innerHTML = '<b>开始时间：</b>' + Info.childNodes[4].text;
			var main6 = document.createElement('li'); main6.innerHTML = '<b>受理时间：</b>' + Info.childNodes[5].text;
			var main7 = document.createElement('li'); main7.innerHTML = '<b>完成时间：</b>' + Info.childNodes[6].text;
			var main8 = document.createElement('li'); main8.innerHTML = '<b>处理记录：</b>';
			mainul.appendChild(main1);
			mainul.appendChild(main2);
			if(Info.childNodes[3].text != '')
			{
				mainul.appendChild(main3);
			}
			mainul.appendChild(main4);
			mainul.appendChild(main5);
			mainul.appendChild(main6);
			mainul.appendChild(main7);
			mainul.appendChild(main8);
			
			var fieldList = pInfo.getElementsByTagName('Field');
			var fieldTable = "<table cellspacing='0' cellpadding='0' width='95%' border='0' style='border-left: 1px solid #C1DAD7;border-top: 1px solid #C1DAD7;'>";
			
			for(var s = 0; s < fieldList.length; s++)
			{
			    var field = fieldList[s];
				var name = field.childNodes[0].text;
				var value = field.childNodes[1].text;
				fieldTable += '<tr><td style=\'background-color:#F5FFD4;font-size:12px;vertical-align:middle;border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7;padding: 3px 3px;word-wrap : break-word ;\'>';
				fieldTable += '<font  style=\'color:#483D8B; font-weight:bold;\'>' +name + '：</font> '+value;
				fieldTable += '</td></tr>';
			    }
			
			var logs = pInfo.getElementsByTagName('Log');
			var logList = logs[0];
			var logString ='';
			for(var j = 0; j < logList.childNodes.length; j++)
			{
				if(j != 0)logString += '<br>';
				var logInfo = logList.childNodes[j];
				logString += logInfo.childNodes[2].text;
				logString += " " + logInfo.childNodes[1].text + ":";
				logString += logInfo.childNodes[3].text==null||logInfo.childNodes[3].text=="null"?"":logInfo.childNodes[3].text;
				

			}
			fieldTable += '<tr><td style=\'background-color:#F5FFD4;font-size:12px;vertical-align:middle;border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7;padding: 3px 3px;\'>';
			fieldTable += '<font  style=\'color:#483D8B; font-weight:bold;\'>处理日志：</font> <br>'+logString;
			fieldTable += '</td></tr>';
			fieldTable += '</table>';

			var fieldli = document.createElement('li'); fieldli.innerHTML = fieldTable;
			mainul.appendChild(fieldli);	
			
			InforBar.dcontent.appendChild(mainul);
			
			//界限
			var hrul = document.createElement('ul');
			hrul.className = 'logul';
			var hrli = document.createElement('li'); hrli.style.listStyleType = 'none';
			var bhr = document.createElement('hr'); hrli.appendChild(bhr);
			hrul.appendChild(hrli);
			InforBar.dcontent.appendChild(hrul);
		}
		
	}
}

InforBar.windowresize = function()
{
	InforBar.dpanel.style.left = parseInt(document.body.clientWidth) - InforBar.panelwidth - 35;
	InforBar.dpanel.style.top = 0;
	InforBar.panelHeight = parseInt(document.body.clientHeight);
	InforBar.dcontent.style.height = parseInt(document.body.clientHeight) - 30 - InforBar.panelHeight/10;
}