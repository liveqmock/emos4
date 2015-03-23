/* 
 * 作者：李昊原（BigMouse）
 * 版本：v1.0.0
 * 日期：2010-06-28
 * 说明：
 * 该工具为了实现利用Ajax方式与服务端交互的类
 */

/*
 * 修改记录
 */

/*
 * 类结构为：（没有列出使用时不需要调用的内部使用方法）
 * AjaxBus() 构造函数
 *  |-callBackGet(url) 向url发出get请求
 *  |-(url, doc) 向url发出post请求，传递doc参数
 *  |-abortCallBack() 终止请求
 *  |-onLoading() 加载时的事件
 *  |-onLoaded() 加载完毕时的事件
 *  |-onInteractive() 数据传输时的事件
 *  |-onComplete(responseText, responseXml) 数据传输完毕时的事件
 *  |-onError(status, statusText, responseText) 数据传输失败时的事件
 *  |-onAbort() 终止数据传输时的事件
 */

/*
 * 使用范例：
 *   var bus = new AjaxBus();
 *   bus.onComplete = function(responseText, responseXml)
 *   {
 *     //responseText为返回字符串
 *     //responseXml为返回的xml
 *   }
 *   bus.callBackGet(url);或bus.callBackPost(url,doc);
 * 如果需要监听其他状态，重写bus对象的对应方法就行了。（是对象实例的，不是类的）
 */

function AjaxBus()
{
    this.ajaxObj = this.getXmlHttpObject();
}

AjaxBus.prototype =
{
    getXmlHttpObject : function()
    {
	    var _ajaxObj;
	    try
	    {
		    _ajaxObj = new ActiveXObject("Msxml2.XMLHTTP");
	    }
	    catch(e1)
	    {
		    try
		    {
			    _ajaxObj = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    catch(e2)
		    {
			    _ajaxObj = false;
		    }
	    }
	    if(!_ajaxObj && typeof(XMLHttpRequest) != 'undefined')
	    {
		    _ajaxObj = new XMLHttpRequest();
	    }
	    return _ajaxObj;
	},
    
    callBackGet : function(url)
    {
	    if(this.ajaxObj)
	    {
		    this.ajaxObj.open('GET', url);
		    var othis = this;
		    this.ajaxObj.onreadystatechange = function() {othis.readyStateChange()};
		    this.ajaxObj.send(null);
	    }
	},
    
    callBackPost : function(url, doc)
    {
	    if(this.ajaxObj)
	    {
		    this.ajaxObj.open('POST', url, 'true');
		    var othis = this;
		    this.ajaxObj.onreadystatechange = function() {othis.readyStateChange()};
		    this.ajaxObj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		    this.ajaxObj.send(doc);
	    }
	},
    
    abortCallBack : function()
    {
	    if(this.ajaxObj)
	    {
		    this.ajaxObj.abort();
	    }
	},
    
    readyStateChange : function()
    {
	    if(this.ajaxObj.readyState == 1)
	    {
		    this.onLoading();
	    }
	    else if(this.ajaxObj.readyState == 2)
	    {
		    this.onLoaded();
	    }
	    else if(this.ajaxObj.readyState == 3)
	    {
		    this.onInteractive();
	    }
	    else if(this.ajaxObj.readyState == 4)
	    {
		    if(this.ajaxObj.status == 0)
		    {
			    this.onAbort();
		    }
		    else if(this.ajaxObj.status == 200)
		    {
			    this.onComplete(this.ajaxObj.responseText, this.ajaxObj.responseXML);
		    }
		    else
		    {
			    this.onError(this.ajaxObj.status, this.ajaxObj.statusText, this.ajaxObj.responseText);
		    }
	    }
    },

    //加载时触发的方法
    onLoading : function() {},

    //加载完毕时触发的方法
    onLoaded : function() {},

    //数据传输时触发的方法
    onInteractive : function() {},

    //数据传输完毕时触发的方法
    onComplete : function(responseText, responseXml) {},

    //数据传输失败时触发的方法
    onError : function(status, statusText, responseText) {},

    //终止数据传输时触发的方法
    onAbort : function() {}


}