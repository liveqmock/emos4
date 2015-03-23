/*工单查询列表的处理记录快速查看功能*/
function getProcessLog(baseid, schema, srcObj)
{
	var llDiv = Util.$('logList');
	var srcx = parseInt(pageX(srcObj)) + document.body.scrollLeft + document.documentElement.scrollLeft;
	//alert(event.clientY);
	var srcy = event.clientY - 18;
	if(llDiv.style.display == 'none')
	{
		var abus = new AjaxBus();
		abus.onComplete = function(responseText, responseXml)
		{
			llDiv.children[0].children[0].innerHTML = '';
			var logList = responseXml.documentElement;
			var logString = '<table border="0" cellpadding="0" cellspacing="0">';
			logString += '<tr>';
			logString += '<th width=110>' + llDivTitle1 + '</th>';
			logString += '<th>' + llDivTitle2 + '</th>';
			logString += '</tr>';
			if(logList.childNodes.length > 0)
			{
				for(var i = 0; i < logList.childNodes.length; i++)
				{
					logString += '<tr>';
					var log = logList.childNodes[i];
					logString += '<td>' + (Util.ie?log.childNodes[0].text:log.childNodes[0].textContent) + '</td>';
					logString += '<td><span>' + (Util.ie?log.childNodes[1].text:log.childNodes[1].textContent) + '</span>' + (Util.ie?log.childNodes[2].text:log.childNodes[2].textContent) + '</td>';
					logString += '</tr>';
				}
			}
			logString += '</table>';
			llDiv.children[0].children[0].innerHTML = logString;
			//srcObj.appendChild(llDiv);
			llDiv.style.left = (srcx - 390) + 'px';
			llDiv.style.display = '';
			if((srcy + parseInt(llDiv.clientHeight)) > (parseInt(Util.$('center').clientHeight) + 30))
			{
				llDiv.style.top = (srcy - 116) +  'px';
				llDiv.children[1].children[0].style.display = 'none';
				llDiv.children[1].children[1].style.display = '';
			}
			else
			{
				llDiv.style.top = (srcy - 10)+ 'px';
				llDiv.children[1].children[0].style.display = '';
				llDiv.children[1].children[1].style.display = 'none';
			}
		}
		abus.callBackPost($ctx+'/sheet/getProcessLogXML.action?baseId=' + baseid + '&baseSchema=' + schema, null);
	}
   }


 //获取input的绝对位置X
 function pageX(obj){
 	return obj.offsetParent?obj.offsetLeft + pageX(obj.offsetParent):obj.offsetLeft;
 }
 //获取input的绝对位置Y
 function pageY(obj){
 	return obj.offsetParent?obj.offsetTop + pageY(obj.offsetParent):obj.offsetTop;
 }


function getScrollTop(){var scrollTop=0;    if(document.documentElement&&document.documentElement.scrollTop)    {        scrollTop=document.documentElement.scrollTop;    }    else if(document.body)    {        scrollTop=document.body.scrollTop;    }    return scrollTop;}
 
 
function hideProcessLog()
{
	
	var srcnode = event.srcElement;
	//document.getElementsByName('workSheetTitle')[0].value = srcnode.tagName + "=" + srcnode.id + "=" + (srcnode.id == 'logList' || (srcnode.tagName == 'A' && srcnode.tagName == 'a'));
	var isIn = false;
	while(srcnode.tagName != 'BODY' && srcnode.tagName != 'body')
	{
		if(srcnode.id == 'logList' || srcnode.tagName == 'A' || srcnode.tagName == 'a')
		{
			isIn = true;
			break;
		}
		else
		{
			srcnode = srcnode.parentNode;
		}
	}
	
	if(isIn)
	{
		return;
	}
	else
	{
		var llDiv = Util.$('logList');
		llDiv.children[0].children[0].innerHTML = '';
		llDiv.style.display = 'none';
	}
}

function initTitle(sortfield, sorttype){
	var type = document.getElementById(sorttype).value;
	var field = document.getElementById(sortfield).value;
	var ths = document.getElementsByTagName('th');
	for (i = 0; i < ths.length; i++) {
		var th = ths[i];
		var thfield = th.getAttribute('field');
		if (thfield != null) {
			th.onclick = function() {
				document.getElementById(sorttype).value = 
					document.getElementById(sortfield).value == this.getAttribute('field') 
						? Math.abs(document.getElementById(sorttype).value - 1) : "1";
				document.getElementById(sortfield).value = this.getAttribute('field');
				document.forms[0].submit();
			};
			th.style.cursor = 'pointer';
			if (thfield == field){
				th.innerHTML+=type == 0 ? "&uarr;" : "&darr;";
				th.className = "order_" + (type == 0 ? "up" : "down");
			}
		}
	}
}