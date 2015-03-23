/* 
 * 作者：李昊原（BigMouse）
 * 版本：v1.0.0
 * 日期：2010-07-13
 * 说明：
 * 该工具提供了基础的便于js逻辑的方法和类
 * 
 * Util.$(objid)：获取指定ID的dom对象
 * 
 * Util.ie：判断浏览器是否为IE
 * 
 * List类：模拟java的List，支持iterator
 *   add(obj)：向List的最后追加一个对象
 *   addat(index, obj)：在List的index位置插入一个对象
 *   get(index)：在List获取index位置的对象
 *   set(index, obj)：在List的替换index位置的对象
 *   size()：获取List的对象个数
 *   remove(index)：移除List中index位置的对象
 *   clear()：清空List
 *   iterator()：获取到List的迭代对象
 *
 * Map类：模拟java的Map，支持直接对value的iterator
 *   put(key, value)：在Map的添加一个key value的键值对
 *   get(key)：在Map获取键为key的值
 *   remove(key)：溢出Map中键为key的对象
 *   containsKey(key)：判断Map中是否存在指定key的对象
 *   keySet()：获取Map中所有的键的List
 *   values()：获取Map中所有的值的List
 *   size()：获取List的对象个数
 *   clear()：清空List
 *   iterator()：获取到List的迭代对象
 * 
 * Iterator用法：for(var it = list.iterator(); it.hasNext();) { var item = it.next(); }
 * 
 * Url类：获取url中参数的值，使用var u = new Url(location.search); var value = url.getvalue(para);即可得到
 * 
 * EventHandler类：封装事件方法，可以为事件方法的调用添加参数，屏蔽了浏览器差异
 *   EventHandler.createEvent(func, ...args)：创建事件对象，args为参数值，需要与func的参数对应，不需要传递event对象
 *   EventHandler.getEvent()：在事件的处理方法中获取当前的event对象
 *   EventHandler.getElement(e)：获取触发event事件的dom对象
 *   EventHandler.attachEvent(obj, eventname, func)：为obj添加eventname事件，事件处理方法为func
 *   EventHandler.detachEvent(obj, eventname, func)：为obj注销eventname事件的func方法
 * 
 * EventType类：封装了客户端的大多数事件类型，屏蔽了浏览器差异
 * 使用方式：EventHandler.attachEvent(obj, EventType.click, func)
 * 目前封装了click, rclick, mousedown, mousemove, mouseup, mouseover, mouseout, scroll, focus, blur, change, keypress, keydown, keyup, submit
 */

/*
 * 修改记录
 */

function Util() { }
Util.$ = function(objid)
{
    return document.getElementById(objid);
}
//浏览器的判断，true为IE，false为Firefox
Util.ie = navigator.appName.indexOf('Microsoft') != -1 ? true : false;



function Iterator(iteratorArray)
{
    this.itArr = iteratorArray;
    this.index = -1;
}

Iterator.prototype =
{
    hasNext: function()
    {
        if (this.index + 1 >= this.itArr.length)
        {
            return false;
        }
        else
        {
            return true;
        }
    },

    next: function()
    {
        this.index++;
        return this.itArr[this.index];
    }
}

function Map()
{
    this.arr = new Array();
}

Map.prototype =
{
    put: function(key, value)
    {
        if (!this.containsKey(key))
        {
            this.arr.push([key, value]);
        }
        else
        {
            for (var i = 0; i < this.arr.length; i++)
            {
                if (this.arr[i][0] == key)
                {
                    this.arr[i][1] = value;
                    return;
                }
            }
        }
    },

    get: function(key)
    {
        for (var i = 0; i < this.arr.length; i++)
        {
            if (this.arr[i][0] == key)
            {
                return this.arr[i][1];
            }
        }
        return null;
    },

    remove: function(key)
    {
        for (var i = 0; i < this.arr.length; i++)
        {
            if (this.arr[i][0] == key)
            {
                this.arr.splice(i, 1);
                return;
            }
        }
    },

    containsKey: function(key)
    {
        for (var i = 0; i < this.arr.length; i++)
        {
            if (this.arr[i][0] == key)
            {
                return true;
            }
        }
        return false;
    },

    keySet: function()
    {
        var l = new List();
        for (var i = 0; i < this.arr.length; i++)
        {
            l.add(this.arr[i][0]);
        }
        return l;
    },

    values: function()
    {
        var l = new List();
        for (var i = 0; i < this.arr.length; i++)
        {
            l.add(this.arr[i][1]);
        }
        return l;
    },

    size: function()
    {
        return this.arr.length;
    },

    clear: function()
    {
        this.arr = [];
    },

    iterator: function()
    {
        var vs = new Array();
        for (var i = 0; i < this.arr.length; i++)
        {
            vs.push(this.arr[i][1]);
        }
        var it = new Iterator(vs);
        return it;
    }
}

function List()
{
    this.arr = new Array();
}

List.prototype =
{
    add: function(obj)
    {
        this.arr.push(obj);
    },

    addat: function(index, obj)
    {
        this.arr.splice(index, 0, obj);
    },

    get: function(index)
    {
        return this.arr[index];
    },

    set: function(index, obj)
    {
        this.arr.splice(index, 1, obj);
    },

    size: function()
    {
        return this.arr.length;
    },

    remove: function(index)
    {
        this.arr.splice(index, 1);
    },

    clear: function()
    {
        this.arr = [];
    },

    iterator: function()
    {
        var it = new Iterator(this.arr);
        return it;
    }
}

function Url(urlstr)
{
    this.paraMap = new Map();

    if (urlstr.indexOf('?') > -1)
    {
        urlstr = urlstr.substr(1);
    }
    if (urlstr.indexOf('&') > -1)
    {
        var pvarr = urlstr.split('&');
        for (var i = 0; i < pvarr.length; i++)
        {
            var pv = pvarr[i].split('=');
            this.paraMap.put(pv[0], pv[1]);
        }
    }
    else
    {
        var pv = urlstr.split('=');
        this.paraMap.put(pv[0], pv[1]);
    }
}

Url.prototype =
{
    getvalue: function(para)
    {
        return this.paraMap.get(para);
    }
}

function EventType() { }

//鼠标单击
EventType.click = Util.ie ? 'onclick' : 'click';
EventType.rclick = Util.ie ? 'oncontextmenu' : 'contextmenu';
EventType.mousedown = Util.ie ? 'onmousedown' : 'mousedown';
EventType.mousemove = Util.ie ? 'onmousemove' : 'mousemove';
EventType.mouseup = Util.ie ? 'onmouseup' : 'mouseup';
EventType.mouseover = Util.ie ? 'onmouseover' : 'mouseover';
EventType.mouseout = Util.ie ? 'onmouseout' : 'mouseout';
EventType.scroll = Util.ie ? 'onscroll' : 'scroll';
EventType.focus = Util.ie ? 'onfocus' : 'focus';
EventType.blur = Util.ie ? 'onblur' : 'blur';
EventType.change = Util.ie ? 'onchange' : 'change';
EventType.keypress = Util.ie ? 'onkeypress' : 'keypress';
EventType.keydown = Util.ie ? 'onkeydown' : 'keydown';
EventType.keyup = Util.ie ? 'onkeyup' : 'keyup';
EventType.submit = Util.ie ? 'onsubmit' : 'submit';

function EventHandler() { }

//创建事件对象
EventHandler.createEvent = function(func)
{
    var argarr = [];
    for (var i = 1; i < arguments.length; i++) argarr.push(arguments[i]);
    return function()
    {
        func.apply(window, argarr);
    }
}

//获取事件的句柄
EventHandler.getEvent = function()
{
    if (Util.ie)
    {
        return window.event;
    }
    else
    {
        var ev = null;
        if (EventHandler.getEvent.caller.caller) ev = EventHandler.getEvent.caller.caller.arguments[0];
        return ev;
    }
}

EventHandler.getElement = function(e)
{
    var ev = e;
    if (!ev)
    {
        if (Util.ie)
        {
            return window.event;
        }
        else
        {
            if (EventHandler.getEvent.caller.caller) ev = EventHandler.getEvent.caller.caller.arguments[0];
            return ev;
        }
    }
    return Util.ie ? ev.srcElement : ev.target;
}

//添加事件
EventHandler.attachEvent = function(obj, eventname, func)
{
    if (Util.ie)
    {
        obj.attachEvent(eventname, func);
    }
    else
    {
        obj.addEventListener(eventname, func, true);
    }
}

//注销事件
EventHandler.detachEvent = function(obj, eventname, func)
{
    if (Util.ie)
    {
        obj.detachEvent(eventname, func);
    }
    else
    {
        obj.removeEventListener(eventname, func, true);
    }
}