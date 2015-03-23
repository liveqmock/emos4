var logAddCursor = 0;
var logKeyList = new Array();
var logContentList = new Array();
var statusBarShow = 0;
var isInforBarShow = false;

function addPhaseLog(phaseID, logContent)
{
	logKeyList[logAddCursor] = phaseID;
	logContentList[logAddCursor] = logContent;
	logAddCursor++;
}

function showProcessInfo(phaseID)
{
	if(isInforBarShow) return false;
	var logDoc = getPhaseLog(phaseID);
	
	if(logDoc == null)
	{
		return false;
	}
	
	var logXml = new ActiveXObject("Microsoft.XMLDOM");
	logXml.async="false";
	logXml.loadXML(logDoc);
		
	var pList = logXml.documentElement;
	var contentDIV = document.getElementById("pContentDIV");
	contentDIV.style.top = event.y + document.getElementById('Designer').scrollTop - 30;
	contentDIV.style.left = event.x + document.getElementById('Designer').scrollLeft;
	contentDIV.style.padding = "5px";
	contentDIV.style.border = "#666666 solid 1px";
	contentDIV.style.width = "420px";
	contentDIV.filters.Alpha.Opacity = 90;
	contentDIV.style.display = "";
	contentDIV.innerHTML = '<font style="font-size:12px;">数据加载中......</font>';
	
	var contentString = '';
	if(pList.childNodes.length == 0)
	{
		contentString = "";
		contentString += "<font style=\"color:#CC0000; font-weight:bold;\">处理信息 ：</font>";
		contentString += "<br />";
		contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">环节描述：</font>";
		contentString += "<br />";
		contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">处 理 人：</font>";
		contentString += "　";
		contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">开始时间：</font>";
		contentString += "<br />";
		contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">受理时间：</font>";
		contentString += "　";
		contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">完成时间：</font>";
		contentString += "<br />";
	}
	else
	{
		var logString = '';
		var processInfoList = pList.getElementsByTagName('ProcessInfo');
		for(var i = 0; i < processInfoList.length; i++)
		{
			var pInfo = processInfoList[i];
			var Info = pInfo.childNodes[0];
			if(i == 0)
			{
				contentString = "";
			}
			else
			{
				contentString += "<hr />";
			}
			var m = i + 1;
			contentString += "<font style=\"color:#CC0000; font-weight:bold;\">处理信息 " + m + "：</font><font style=\"font-weight:bold\">状态：" + Info.childNodes[0].text + "</font>";
			contentString += "<br />";
			contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">环节描述：</font>";
			contentString += "<div style='display:inline;'>" + Info.childNodes[1].text + "</div>";
			contentString += "<br />";
			if(Info.childNodes[3].text != '')
			{
				contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">处理组成员：</font>";
				contentString += "<div style='display:inline;'>" + Info.childNodes[3].text == null||Info.childNodes[3].text=="null"?"":Info.childNodes[3].text + "</div>";
				contentString += "<br />";
			}
			/*
			var processData = "<table border=0 cellPadding=0 cellSpacing=0 style='font-size:12px;'>";
			processData += "<tr><td>";
			processData += "　<font style=\"color:#CC0000; font-weight:bold;\">处 理 人：</font>";
			processData += "</td><td>";
			processData += "<font style='width:120px;'>" + Info.childNodes[2].text + "</font>";
			processData += "</td><td>";
			processData += "　<font style=\"color:#CC0000; font-weight:bold;\">开始时间：</font>";
			processData += "</td><td>";
			processData += Info.childNodes[4].text;
			processData += "</td></tr><tr><td>";
			processData += "　<font style=\"color:#CC0000; font-weight:bold;\">受理时间：</font>";
			processData += "</td><td>";
			processData += "<font style='width:120px;'>" + Info.childNodes[5].text + "</font>";
			processData += "</td><td>";
			processData += "　<font style=\"color:#CC0000; font-weight:bold;\">完成时间：</font>";
			processData += "</td><td>";
			processData += Info.childNodes[6].text;
			processData += "&nbsp;</td></tr></table>";
			contentString += processData;
			*/
			
			contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">处 理 人：</font>";
			contentString += "<font style='width:120px;'>" + Info.childNodes[2].text + "</font>";
			contentString += "　";
			contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">开始时间：</font>";
			contentString += Info.childNodes[4].text;
			contentString += "<br />";
			contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">受理时间：</font>";
			contentString += "<font style='width:120px;'>" + Info.childNodes[5].text + "</font>";
			contentString += "　";
			contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">完成时间：</font>";
			contentString += Info.childNodes[6].text;
			contentString += "<br />";
			
			contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">处理记录：</font>";
			contentString += "<br />";
			contentString += "<table cellspacing='0' cellpadding='0' border='0' width='410px;' style='border-left: 1px solid #C1DAD7;border-top: 1px solid #C1DAD7;'><tr><td width='25%'></td><td width='25%'></td><td width='25%'></td><td width='25%'></td></tr>";
			//用户输入信息显示 Fields
			var fieldList = pInfo.getElementsByTagName('Field');
			var cols = 0;
			for(var s = 0; s < fieldList.length; s++)
			{
				var field = fieldList[s];
				var name = field.childNodes[0].text;
				var value = field.childNodes[1].text;
				var printOneLine = field.childNodes[2].text;
				var colspan = 1;
				var width = 'width:90px;';
				if(cols == 0){
					contentString+="<tr>";
				}
				if(printOneLine == '1'){
					colspan = 3;
					
				}
				if(cols == 1&& printOneLine =='1'){
					var index = contentString.lastIndexOf('colspan=');
					contentString = contentString.substring(0,index)+'colspan=3' + contentString.substring(index+9,contentString.length);
					
				 	contentString+="</tr><tr>";
				 	
				}
				cols++;
				if(s == (fieldList.length-1)&& cols==1){
				colspan = 3;
				}
				
				if(colspan == 3){width = 'width:300px;';}
				
				contentString += "<td colspan=1 style='background-color:#DCFFA8;font-size:12px;vertical-align:middle;border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7;padding: 3px 3px;'><font style=\"color:#CC0000; font-weight:bold;\">"+name+"：</font></td>";
				contentString += "<td colspan="+colspan+"  style='background-color:#E9FFCC;font-size:12px;vertical-align:middle;border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7;padding: 3px 3px;word-wrap : break-word ;'><font style='"+width+"'>" + value + "</font></td>";
				
				
				if(cols == 2){
					contentString+="</tr>";
					cols = 0;
				}
				if(printOneLine == '1')
				{
					contentString+="</tr>";
					cols = 0;
				}
			    
			}
			if(cols == 1)contentString+="</tr>";
			
			//封装日志
			var logs = pInfo.getElementsByTagName('Log');
			var logList = logs[0];
			for(var j = 0; j < logList.childNodes.length; j++)
			{
				var logInfo = logList.childNodes[j];
				logString += logInfo.childNodes[2].text;
				logString += " " + logInfo.childNodes[1].text + ":";
				logString += logInfo.childNodes[3].text;
				if(j != logList.childNodes.length-1)logString += "<br/>";
			}
			
			contentString+="<tr>";
			contentString += "<td colspan=1 style='background-color:#DCFFA8;font-size:12px;vertical-align:middle;border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7;padding: 3px 3px;'><font style=\"color:#CC0000; font-weight:bold;\">处理日志：</font></td>";
			contentString += "<td colspan=3 style='background-color:#E9FFCC;font-size:12px;vertical-align:middle;border-right: 1px solid #C1DAD7; border-bottom: 1px solid #C1DAD7;padding: 3px 3px;word-wrap : break-word ;'><font style=\"width:300px;\">" + logString + "</font></td>";
			contentString+="</tr>";	
			contentString += "</table>";
		
			logString='';
			
			/**
			var logList = pInfo.childNodes[1];
			for(var j = 0; j < logList.childNodes.length; j++)
			{
				var logInfo = logList.childNodes[j];
				var k = j + 1;
				contentString += "　　<font style=\"color:#CC0000;\">日 志 " + k + "：</font><br />";
				contentString += "　　　<font style=\"color:#CC0000;\">动　　作：</font>";
				contentString += logInfo.childNodes[0].text;
				contentString += "<br />";
				contentString += "　　　<font style=\"color:#CC0000;\">记 录 人：</font>";
				contentString += "<font style='width:95px;'>" + logInfo.childNodes[1].text + "</font>";
				contentString += "　";
				contentString += "　<font style=\"color:#CC0000;\">记录时间：</font>";
				contentString += logInfo.childNodes[2].text;
				contentString += "<br />";
				contentString += "　　　<font style=\"color:#CC0000;\">日志内容：</font>";
				contentString += logInfo.childNodes[3].text;
				contentString += "<br />";
			}
			**/

		}
		
		/**
	    var subLogsList = pList.getElementsByTagName('SubLogs');
	    var subLogList = subLogsList[0];
		for(var s = 0; s < subLogList.length; s++)
		{
			var subLogs = subLogList.childNodes[s];
			var n = s + 1;
			contentString += "<hr />";
			contentString += "<font style=\"color:#CC0000; font-weight:bold;\">分发任务 " + n + "：</font>";
			contentString += "<br />";
			for(var i = 0; i < subLogs.childNodes.length; i++)
			{
				var pInfo = subLogs.childNodes[i];
				var Info = pInfo.childNodes[0];
				var m = i + 1;
				contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">处理信息 " + m + "：</font><font style=\"font-weight:bold\">状态：" + Info.childNodes[0].text + "</font>";
				contentString += "<br />";
				contentString += "　　<font style=\"color:#CC0000; font-weight:bold;\">环节描述：</font>";
				contentString += "<div style='display:inline;'>" + Info.childNodes[1].text + "</div>";
				contentString += "<br />";
				if(Info.childNodes[3].text != '')
				{
					contentString += "　　<font style=\"color:#CC0000; font-weight:bold;\">处理组成员：</font>";
					contentString += "<div style='display:inline;'>" + Info.childNodes[3].text + "</div>";
					contentString += "<br />";
				}
				
				var processData = "<table border=0 cellPadding=0 cellSpacing=0 style='font-size:12px;'>";
				processData += "<tr><td>";
				processData += "　<font style=\"color:#CC0000; font-weight:bold;\">处 理 人：</font>";
				processData += "</td><td>";
				processData += "<font style='width:120px;'>" + Info.childNodes[2].text + "</font>";
				processData += "</td><td>";
				processData += "　<font style=\"color:#CC0000; font-weight:bold;\">开始时间：</font>";
				processData += "</td><td>";
				processData += Info.childNodes[4].text;
				processData += "</td></tr><tr><td>";
				processData += "　<font style=\"color:#CC0000; font-weight:bold;\">受理时间：</font>";
				processData += "</td><td>";
				processData += "<font style='width:120px;'>" + Info.childNodes[5].text + "</font>";
				processData += "</td><td>";
				processData += "　<font style=\"color:#CC0000; font-weight:bold;\">完成时间：</font>";
				processData += "</td><td>";
				processData += Info.childNodes[6].text;
				processData += "&nbsp;</td></tr></table>";
				contentString += processData;
				
				
				contentString += "　　<font style=\"color:#CC0000; font-weight:bold;\">处 理 人：</font>";
				contentString += "<font style='width:120px;'>" + Info.childNodes[2].text + "</font>";
				contentString += "　";
				contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">开始时间：</font>";
				contentString += Info.childNodes[4].text;
				contentString += "<br />";
				contentString += "　　<font style=\"color:#CC0000; font-weight:bold;\">受理时间：</font>";
				contentString += "<font style='width:120px;'>" + Info.childNodes[5].text + "</font>";
				contentString += "　";
				contentString += "　<font style=\"color:#CC0000; font-weight:bold;\">完成时间：</font>";
				contentString += Info.childNodes[6].text;
				contentString += "<br />";
				
				
				var logList = pInfo.childNodes[1];
				for(var j = 0; j < logList.childNodes.length; j++)
				{
					var logInfo = logList.childNodes[j];
					var k = j + 1;
					contentString += "　　<font style=\"color:#CC0000;\">日 志 " + k + "：</font><br />";
					contentString += "　　　<font style=\"color:#CC0000;\">动　　作：</font>";
					contentString += logInfo.childNodes[0].text;
					contentString += "<br />";
					contentString += "　　　<font style=\"color:#CC0000;\">记 录 人：</font>";
					contentString += "<font style='width:95px;'>" + logInfo.childNodes[1].text + "</font>";
					contentString += "　";
					contentString += "　<font style=\"color:#CC0000;\">记录时间：</font>";
					contentString += logInfo.childNodes[2].text;
					contentString += "<br />";
					contentString += "　　　<font style=\"color:#CC0000;\">日志内容：</font>";
					contentString += logInfo.childNodes[3].text;
					contentString += "<br />";
				}
				
			}
		}
		**/

	}
	contentDIV.innerHTML = contentString;
}

function showVerdictInfo(phaseID)
{
	if(isInforBarShow) return false;
	var logDosc = getPhaseLog(phaseID);
	
	if(logDosc == null)
	{
		return false;
	}
	var contentDIV = document.getElementById("pContentDIV");
	contentDIV.style.top = event.y + document.getElementById('Designer').scrollTop - 30;
	contentDIV.style.left = event.x + document.getElementById('Designer').scrollLeft;
	contentDIV.style.padding = "5px";
	contentDIV.style.border = "#666666 solid 1px";
	contentDIV.style.width = "1px";
	contentDIV.noWrap = true;
	contentDIV.filters.Alpha.Opacity = 90;
	contentDIV.innerHTML = "<font style=\"color:#CC0000; font-weight:bold;\">判断条件：</font><font style=\"font-weight:bold\">" + logDosc + "</font>";
	contentDIV.style.display = "";
}

function hideProcessInfo()
{
	
}

function hideInfo()
{
	var contentDIV = document.getElementById("pContentDIV");
	contentDIV.style.display = "none";
	contentDIV.style.top = 0;
	contentDIV.style.left = 0;
	contentDIV.style.padding = "0px";
	contentDIV.style.border = "#999999 solid 0px";
	contentDIV.filters.Alpha.Opacity = 0;
	contentDIV.innerHTML = "";	
}

function getPhaseLog(phaseID)
{
	for(var i = 0 ; i < logKeyList.length; i++)
	{
		if(logKeyList[i] == phaseID)
		{
			return logContentList[i];
		}
	}
	return null;
}


function setDivPosition()
{
	document.getElementById('StatusView').style.top = document.body.clientHeight - parseInt(document.getElementById('StatusView').clientHeight) + 5;
	document.getElementById('StatusView').style.width = document.body.clientWidth - 10;
}

function changeStatusBar()
{
	if(statusBarShow == 1)
	{
		document.getElementById('StatusView').style.display = 'none';
		document.getElementById('StatusView').style.top = 15;
		document.getElementById('StatusView').style.width = 100;
		statusBarShow = 0;
	}
	else
	{
		document.getElementById('StatusView').style.width = document.body.clientWidth - 30;
		document.getElementById('BarText').innerText = '关闭';
		document.getElementById('StatusView').style.display = '';
		document.getElementById('StatusView').style.top = document.body.clientHeight - parseInt(document.getElementById('StatusView').clientHeight) - 30;
		statusBarShow = 1;
	}
}
