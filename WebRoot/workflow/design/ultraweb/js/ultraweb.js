/**
 * 封装客户端DOM模型和事件的组件
 * 版本：V1.0
 * @作者：李昊原
 * @创建时间：2008年9月10日
 * @修改记录：
 * ---------------------------------
 *   修改时间  | 修改人 | 备注
 *  - - - - - - - - - - - - - - - - 
 *  2008-09-10 | 李昊原 | 创建文件
 * ---------------------------------
*/

function $(objid)
{
    return document.getElementById(objid);
}

function Util() {}
//浏览器的判断，true为IE，false为Firefox
Util.ie = navigator.appName.indexOf('Microsoft') != -1 ? true : false;

//用于继承的方法块，用法：class2.protytype = (new class1()).extend({$some prototype$});
/**
Object.extend = function(destination, source)
{
    for(property in source)
    {
        destination[property] = source[property];
    }
    return destination;
}
Object.prototype.extend = function(object)
{
    return Object.extend.apply(this, [this, object]);
}
**/
function Iterator(iteratorArray)
{
    this.itArr = iteratorArray;
    this.index = -1;
}

Iterator.prototype =
{
    hasNext : function()
    {
        if(this.index + 1 >= this.itArr.length)
        {
            return false;
        }
        else
        {
            return true;
        }
    },
    
    next : function()
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
    put : function(key, value)
    {
        if(!this.containsKey(key))
        {
            this.arr.push([key, value]);
        }
        else
        {
            for(var i = 0; i < this.arr.length; i++)
            {
                if(this.arr[i][0] == key)
                {
                    this.arr[i][1] = value;
                    return;
                }
            }
        }
    },
    
    get : function(key)
    {
        for(var i = 0; i < this.arr.length; i++)
        {
            if(this.arr[i][0] == key)
            {
                return this.arr[i][1];
            }
        }
        return null;
    },
    
    remove : function(key)
    {
        for(var i = 0; i < this.arr.length; i++)
        {
            if(this.arr[i][0] == key)
            {
                this.arr.splice(i, 1);
                return;
            }
        }
    },
    
    containsKey : function(key)
    {
        for(var i = 0; i < this.arr.length; i++)
        {
            if(this.arr[i][0] == key)
            {
                return true;
            }
        }
        return false;
    },
    
    keySet : function()
    {
        var l = new List();
        for(var i = 0; i < this.arr.length; i++)
        {
            l.add(this.arr[i][0]);
        }
        return l;
    },
    
    values : function()
    {
        var l = new List();
        for(var i = 0; i < this.arr.length; i++)
        {
            l.add(this.arr[i][1]);
        }
        return l;
    },
    
    size : function()
    {
        return this.arr.length;
    },
    
    clear : function()
    {
        this.arr = [];
    },
    
    iterator : function()
    {
        var vs = new Array();
        for(var i = 0; i < this.arr.length; i++)
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
    add : function(obj)
    {
        this.arr.push(obj);
    },
    
    addat : function(index, obj)
    {
        this.arr.splice(index, 0, obj);
    },
    
    get : function(index)
    {
        return this.arr[index];
    },
    
    set : function(index, obj)
    {
        this.arr.splice(index, 1, obj);
    },
    
    size : function()
    {
        return this.arr.length;
    },
    
    remove : function(index)
    {
        this.arr.splice(index, 1);
    },
    
    clear : function()
    {
        this.arr = [];
    },
    
    iterator : function()
    {
        var it = new Iterator(this.arr);
        return it;
    }
}

function Url(urlstr)
{
    this.paraMap = new Map();
    
    if(urlstr.indexOf('?') > -1)
    {
        urlstr = urlstr.substr(1);
    }
    if(urlstr.indexOf('&') > -1)
    {
        var pvarr = urlstr.split('&');
        for(var i = 0; i < pvarr.length; i++)
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
    getvalue : function(para)
    {
        return this.paraMap.get(para);
    }
}

function EventType() {}

//鼠标单击
EventType.click = Util.ie ? 'onclick' : 'click';

EventType.rclick = Util.ie ? 'oncontextmenu' : 'contextmenu';

//鼠标按键按下
EventType.mousedown = Util.ie ? 'onmousedown' : 'mousedown';

//鼠标移动
EventType.mousemove = Util.ie ? 'onmousemove' : 'mousemove';

//鼠标按键抬起
EventType.mouseup = Util.ie ? 'onmouseup' : 'mouseup';

EventType.mouseover = Util.ie ? 'onmouseover' : 'mouseover';

EventType.mouseout = Util.ie ? 'onmouseout' : 'mouseout';

EventType.scroll = Util.ie ? 'onscroll' : 'scroll';

EventType.dbclick = Util.ie ? 'ondblclick' : 'dblclick';


function EventHandler() {}

//创建事件对象
EventHandler.createEvent = function(func)
{
    var argarr = [];
    for(var i = 1; i < arguments.length; i++) argarr.push(arguments[i]);
    return function()
    {
        func.apply(window, argarr);
    }
}

//获取事件的句柄
EventHandler.getEvent = function()
{
    if(Util.ie)
    {
        return window.event;
    }
    else
    {
        var ev = null;
        if(EventHandler.getEvent.caller.caller) ev = EventHandler.getEvent.caller.caller.arguments[0];
        return ev;
    }
}

EventHandler.getElement = function(e)
{
    var ev = e;
    if(!ev)
    {
        if(Util.ie)
        {
            return window.event;
        }
        else
        {
            if(EventHandler.getEvent.caller.caller) ev = EventHandler.getEvent.caller.caller.arguments[0];
            return ev;
        }
    }
    return Util.ie ? ev.srcElement : ev.target;
}

//添加事件
EventHandler.attachEvent = function(obj, eventname, func)
{
    if(Util.ie)
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
    if(Util.ie)
    {
        obj.detachEvent(eventname, func);
    }
    else
    {
        obj.removeEventListener(eventname, func, true);
    }
}

function DOM() {}

DOM.create = function(eleName)
{
    var reEle = null;
    if(eleName.indexOf('.') > 0)
    {
        var elearr = eleName.split('.');
        if(elearr[1] != 'radio')
        {
            reEle = document.createElement(elearr[0]);
            reEle.type = elearr[1];
        }
    }
    else
    {
        reEle = document.createElement(eleName);
    }
    return reEle;
}

DOM.body = function() {return document.body;}

DOM.div = function() {return DOM.create('div');}

DOM.text = function() {return DOM.create('input.text');}

DOM.checkbox = function() {return DOM.create('input.checkbox');}

DOM.radio = function() {return DOM.create('input.radio');}

DOM.button = function() {return DOM.create('input.button');}

DOM.font = function() {return DOM.create('font');}

DOM.label = function() {return DOM.create('label');}

DOM.select = function() {return DOM.create('select');}

DOM.option = function() {return DOM.create('option');}

DOM.span = function() {return DOM.create('span');}

DOM.br = function() {return DOM.create('br');}

function DOMModel(obj, hfname)
{
    this.scope = '';
    this.type = '';
    this.domobj = null;
    this.parameters = new Array();
    this.childobj = new Array();
    this.eventList = new Array();
    this.domObj(obj);
    this.handlerFunc = hfname;
}

DOMModel.prototype = 
{
    checkRadioObj : function()
    {
        if(this.domobj == null)
        {
            var objname = '';
            for(var i = 0; i < this.parameters.length; i++)
            {
                if(this.parameters[i][0] == 'name')
                {
                    objname = this.parameters[i][1];
                }
            }
            
            this.domobj = document.createElement('<input type="radio" name="' + objname + '">');
        }
    },
    //设置和获取对象
    domObj : function(obj)
    {
        if(obj)
        {
            this.scope = obj.scopeName;
            this.type = obj.nodeName;
            for(var i = 0; i < this.eventList.length; i++)
            {
                this.removeEvent(this.eventList[i][1], this.eventList[i][2]);
            }
            if(this.domobj) this.domobj.outerHTML = '';
            this.domobj = obj;
            //this.domobj.type = obj.type;
            for(var i = 0; i < this.eventList.length; i++)
            {
                this.addEvent(this.eventList[i][1], this.eventList[i][2]);
            }
            for(var i = 0; obj.attributes && i < obj.attributes.length; i++)
            {
                this.parameters.push([obj.attributes.name, obj.attributes.value]);
            }
        }
        this.checkRadioObj();
        return this.domobj;
    },
    
    //设置和获取对象的ID
    id : function(domid)
    {
        if(domid)
        {
            this.parameter('id', domid);
            this.checkRadioObj();
            if(this.domobj != null)
            {
                this.domobj.id = domid;
            }
        }
        return this.domobj.id;
    },

    //设置和获取对象的Name
    name : function(domname)
    {
        if(domname)
        {
            this.parameter('name', domname);
            this.checkRadioObj();
            this.domobj.name = domname;
        }
        return this.domobj.name;
    },
    
    //设置和获取对象的属性
    parameter : function(paraName, paraValue)
    {
        this.checkRadioObj();
        for(var i = 0; i < this.parameters.length; i++)
        {
            if(this.parameters[i][0] == paraName)
            {
                if(paraValue)
                {
                    this.parameters[i][1] = paraValue;
                    this.domobj.setAttribute(paraName, paraValue);
                }
                return this.parameters[i][1];
            }
        }
        if(paraValue)
        {
            this.parameters.push([paraName, paraValue]);
            this.domobj.setAttribute(paraName, paraValue);
            return paraValue;
        }
        return null;
    },
    
    parameterss : function(paraarr)
    {
        this.checkRadioObj();
        for(var i = 0; i < paraarr.length; i++)
        {
            this.parameter(paraarr[i][0], paraarr[i][1]);
        }
    },
    
    cssclass : function(classname)
    {
        this.checkRadioObj();
        this.domobj.className = classname;
    },
    
    //设置和获取对象的属性
    style : function(stylename, stylevalue)
    {
        this.checkRadioObj();
        if(stylevalue)
        {
            this.domobj.style[stylename] = stylevalue;
        }
        return this.domobj.style[stylename];
    },
    
    //批量设置对象的属性
    styles : function(stylearr)
    {
        this.checkRadioObj();
        for(var i = 0; i < stylearr.length; i++)
        {
            this.style(stylearr[i][0], stylearr[i][1]);
        }
    },
    
    value : function(val)
    {
        this.checkRadioObj();
        if(val)
        {
            if(Util.ie)
            {
                this.domobj.innerText = val;
            }
            else
            {
                this.domobj.textContent = val;
            }
        }
        if(Util.ie)
        {
            return this.domobj.innerText;
        }
        else
        {
            return this.domobj.textContent;
        }
    },
    
    //获取HTML
    getHTML : function()
    {
        if(this.domobj == null)
        {
            this.checkRadioObj();
        }
        
        for(var i = 0; i < this.parameters.length; i++)
        {
            this.domobj.setAttribute(this.parameters[i][0], this.parameters[i][1]);
        }
        for(var i = 0; i < this.childobj.length; i++)
        {
            this.domobj.innerHTML += this.childobj[i].getHTML();
        }
        return this.domobj.outerHTML;
    },
    
    //添加子元素
    appendChild : function(obj)
    {
        this.childobj.push(obj);
        this.checkRadioObj();
        this.domobj.appendChild(obj.domobj);
    },
    
    clearChildren : function()
    {
        this.childobj = [];
        this.checkRadioObj();
        this.domobj.innerHTML = '';
    },
    
    //添加事件
    attachEvent : function(eventtag, eventname, func)
    {
        for(var i = 0; i < this.eventList.length; i++)
        {
            if(this.eventList[i][0] == eventtag)
            {
                this.detachEvent(eventtag);
                break;
            }
        }
        this.eventList.push([eventtag, eventname, func]);
        this.addEvent(eventname, func);
    },
    
    addEvent : function(eventname, func)
    {
        this.checkRadioObj();
        EventHandler.attachEvent(this.domobj, eventname, func);
    },
    
    //注销事件
    detachEvent : function(eventtag)
    {
        var eventarr = false;
        var eventindex = -1;
        for(var i = 0; i < this.eventList.length; i++)
        {
            if(this.eventList[i][0] == eventtag)
            {
                eventarr = this.eventList[i];
                eventindex = i;
            }
        }
        if(eventarr)
        {
            this.removeEvent(eventarr[1], eventarr[2]);
            this.eventList.splice(eventindex, 1);
        }
    },
    
    removeEvent : function(eventname, func)
    {
        this.checkRadioObj();
        EventHandler.detachEvent(this.domobj, eventname, func);
    },
    
    distory : function()
    {
        this.parameters = [];
        this.childobj = [];
        for(var i = 0; i < this.eventList.length; i++)
        {
            this.detachEvent(this.eventList[i][0]);
        }
        this.eventList = [];
        this.checkRadioObj();
        this.domobj.outerHTML = '';
        this.domobj = null;
    }
}

function DOMHandler() {}

DOMHandler.name = 'DOMHandler';

DOMHandler.draw = function(parentObj, obj)
{
    parentObj.appendChild(obj);
}

DOMHandler.move = function(obj, x, y)
{
    obj.styles([['left', x], ['top', y]]);
}

DOMHandler.remove = function(obj)
{
    obj.domObj().outerHTML = '';
}

DOMHandler.focus = function(obj)
{
    obj.styles([['border', 'solid 2px #00FF00']]);
}

DOMHandler.mainfocus = function(obj)
{
    obj.styles([['border', 'solid 2px #0000FF']]);
}

DOMHandler.unfocus = function(obj)
{
    obj.styles([['border', 'solid 1px #000000']]);
}


function DataExchange(model)
{
    this.modelObj = model;
}

DataExchange.prototype =
{
    show : function()
    {
        var names='';
        for(var name in this.modelObj)
        {
            var item = document.getElementsByName(name);
            if(item.length > 0)
            {
                var tagName = item[0].tagName;
                for(var i = 1; i < item.length; i++)
                {
                    if(item[i].tagName != tagName)
                    {
                        return;
                    }
                }
                switch(tagName)
                {
                    case 'INPUT':
                        this.setInput(item, this.modelObj[name]);
                        break;
                    case 'SELECT':
                        this.setSelect(item, this.modelObj[name]);
                        break;
                    case 'TEXTAREA':
                        this.setInput(item, this.modelObj[name]);
                        break;
                    //case 'DIV 自定义模型属性赋值 
                    case 'DIV':
                         var metaMap = this.modelObj[name];
                         var metakeyList = metaMap.keySet();
                          for(var it = metakeyList.iterator(); it.hasNext();)
                           {
                            var metakey = it.next();
                            var item = document.getElementsByName(metakey);
                                if(item.length > 0)
                                {
                                    var tagName = item[0].tagName;
                                    for(var i = 1; i < item.length; i++)
                                    {
                                        if(item[i].tagName != tagName)
                                        {
                                            return;
                                        }
                                    }
                                    switch(tagName)
                                    {
                                        case 'INPUT':
                                            this.setInput(item, this.modelObj[name].get(metakey));
                                            break;
                                        case 'SELECT':
                                            this.setSelect(item, this.modelObj[name].get(metakey));
                                            break;
                                        case 'TEXTAREA':
                                            this.setInput(item, this.modelObj[name].get(metakey));
                                            break;
                                    }
                               }
                           }
                        break;
                }
            }
        }
    },
    
    save : function()
    {
        var names='';
        for(var name in this.modelObj)
        {
            var item = document.getElementsByName(name);
            if(item.length > 0)
            {
                    var tagName = item[0].tagName;
                    for(var i = 1; i < item.length; i++)
                    {
                        if(item[i].tagName != tagName)
                        {
                            return;
                        }
                    }
                    switch(tagName)
                    {
                        case 'INPUT':
                            this.modelObj[name] = this.getInput(item);
                            break;
                        case 'SELECT':
                            this.modelObj[name] = this.getSelect(item);
                            break;
                        case 'TEXTAREA':
                            this.modelObj[name] = this.getInput(item);
                            break;
                        case 'DIV':
                             var metaMap = this.modelObj[name];
                             var metakeyList = metaMap.keySet();
                              for(var it = metakeyList.iterator(); it.hasNext();)
                               {
                                var metakey = it.next();
                                var item = document.getElementsByName(metakey);
                                    if(item.length > 0)
                                    {
                                        var tagName = item[0].tagName;
                                        for(var i = 1; i < item.length; i++)
                                        {
                                            if(item[i].tagName != tagName)
                                            {
                                                return;
                                            }
                                        }
                                        switch(tagName)
                                        {
                                            case 'INPUT':
                                                this.modelObj[name].put(metakey,this.getInput(item)); 
                                                break;
                                            case 'SELECT':
                                                this.modelObj[name].put(metakey,this.getSelect(item)); 
                                                //this.modelObj[name] = this.getSelect(item);
                                                break;
                                            case 'TEXTAREA':
                                                this.modelObj[name] = this.getInput(item);
                                                break;
                                        }
                                   }
                               }
                            break;
                    }
            }
            
        }
    },
    
    setInput : function(item, value)
    {
        switch(item[0].type)
        {
            case 'radio':
                for(var i = 0; i < item.length; i++) if(item[i].value == value)  item[i].checked = true;
                break;
            case 'checkbox':
                for(var i = 0; i < item.length; i++) if(item[i].value == value) item[i].checked = true;
                break;
            default:
                item[0].value = value != 'null' ? value : '';
                break;
        }
    },
    
    getInput : function(item)
    {
        switch(item[0].type)
        {
            case 'radio':
                for(var i = 0; i < item.length; i++) if(item[i].checked) return item[i].value;
                break;
            case 'checkbox':
                for(var i = 0; i < item.length; i++) if(item[i].checked) return item[i].value;
                break;
            default:
                return item[0].value;
                break;
        }
    },
    
    setSelect : function(item, value)
    {
        var optarr = item[0].options;
        for(var i = 0; i < optarr.length; i++)
        {
            if(optarr[i].value == value)
            {
                optarr[i].selected = true;
                break;
            }
        }
    },
    
    getSelect : function(item)
    {
        return item[0].options[item[0].selectedIndex].value;
    }
}

function ToolBar() {}

ToolBar.align = function(type)
{
    LinkManager.cancelcreate();
    if(DragAndDrop.selectedObjs.length > 1)
    {
        switch(type)
        {
            case 'LEFT':
                var alignX = DragAndDrop.selectedObjs[DragAndDrop.selectedObjs.length - 1][1].style('left');
                for(var i = 0; i < DragAndDrop.selectedObjs.length; i++)
                {
                    var pModel = bModel.processMap.get(DragAndDrop.selectedObjs[i][0]);
                    if(pModel.type == 'SPLIT' || pModel.type == 'JOIN'){
	                    var x = parseInt(alignX)+Config.modelwidth/2-Config.ovalheight/2;
	                    ProcessDOMHandler.move(pModel.processDOMModel, x, parseInt(pModel.processDOMModel.style('top')));
	                    PreviewBar.moveProcess(pModel.processDOMModel.id(), parseInt(x), parseInt(pModel.processDOMModel.style('top')));
	                    ProcessManager.moveProcessLink(pModel);
                    }else if(pModel.type == 'BEGIN' || pModel.type == 'END'){
                    	var x = parseInt(alignX)+Config.modelwidth/2-Config.ovalStartHeight/2;
	                    ProcessDOMHandler.move(pModel.processDOMModel, x, parseInt(pModel.processDOMModel.style('top')));
	                    PreviewBar.moveProcess(pModel.processDOMModel.id(), parseInt(x), parseInt(pModel.processDOMModel.style('top')));
	                    ProcessManager.moveProcessLink(pModel);
                    }else{
	                    ProcessDOMHandler.move(pModel.processDOMModel, alignX, parseInt(pModel.processDOMModel.style('top')));
	                    PreviewBar.moveProcess(pModel.processDOMModel.id(), parseInt(alignX), parseInt(pModel.processDOMModel.style('top')));}
	                    ProcessManager.moveProcessLink(pModel);
                	}
                break;
            case 'TOP':
                var alignY = DragAndDrop.selectedObjs[DragAndDrop.selectedObjs.length - 1][1].style('top');
                for(var i = 0; i < DragAndDrop.selectedObjs.length; i++)
                {
                    var pModel = bModel.processMap.get(DragAndDrop.selectedObjs[i][0]);
                    if(pModel.type == 'SPLIT' || pModel.type == 'JOIN'){
	                    var y = parseInt(alignY)+Config.modelheight/2-Config.ovalheight/2;
	                    ProcessDOMHandler.move(pModel.processDOMModel, parseInt(pModel.processDOMModel.style('left')), y);
	                    PreviewBar.moveProcess(pModel.processDOMModel.id(), parseInt(pModel.processDOMModel.style('left')), parseInt(y));
	                    ProcessManager.moveProcessLink(pModel);
                    }else if(pModel.type == 'BEGIN' || pModel.type == 'END'){
                    	var y = parseInt(alignY)+Config.modelheight/2-Config.ovalStartHeight/2;
	                    ProcessDOMHandler.move(pModel.processDOMModel, parseInt(pModel.processDOMModel.style('left')), y);
	                    PreviewBar.moveProcess(pModel.processDOMModel.id(), parseInt(pModel.processDOMModel.style('left')), parseInt(y));
	                    ProcessManager.moveProcessLink(pModel);
                    }else{
	                    ProcessDOMHandler.move(pModel.processDOMModel, parseInt(pModel.processDOMModel.style('left')), alignY);
	                    PreviewBar.moveProcess(pModel.processDOMModel.id(), parseInt(pModel.processDOMModel.style('left')), parseInt(alignY));
	                    ProcessManager.moveProcessLink(pModel);
                    }
                }
                break;
        }
    }
}

ToolBar.oldBtn = null;
ToolBar.activedBtn = null;

ToolBar.btnMouseDown = function(btnObj)
{
	btnObj.className = 'toolbarbutton_down';
	ToolBar.activedBtn = btnObj;
	btnObj.attachEvent('onmouseup', ToolBar.btnMouseUp);
}

ToolBar.btnMouseUp = function()
{
	if(ToolBar.activedBtn.type == 'sysbtn' || ToolBar.activedBtn.type == 'sizebtn')
	{
		ToolBar.activedBtn.className = 'toolbarbutton';
	}
	else if(ToolBar.activedBtn.type == 'toolbtn')
	{
		if(ToolBar.oldBtn != null && ToolBar.oldBtn.id != ToolBar.activedBtn.id)
		{
			ToolBar.oldBtn.className = 'toolbarbutton';
		}
		ToolBar.oldBtn = ToolBar.activedBtn;
	}
	ToolBar.activedBtn.detachEvent('onmouseup', ToolBar.btnMouseUp);
}

ToolBar.btnMouseOver = function(btnObj)
{
	if(btnObj.type == 'sysbtn' || btnObj.type == 'sizebtn' || btnObj.type == 'toolbtn')
	{
		btnObj.className = 'toolbarbutton_active';
	}
}

ToolBar.btnMouseOut = function(btnObj)
{
	if(btnObj.type == 'sysbtn' || btnObj.type == 'sizebtn' || btnObj.type == 'toolbtn')
	{
		btnObj.className = 'toolbarbutton';
	}
}

function RightMenuImg() {}

RightMenuImg.blank = '';

RightMenuImg.create = '';

RightMenuImg.edit = '';

RightMenuImg.del = '';

function RightMenu()
{
    this.items = [];
}

RightMenu.container = null;
RightMenu.rmAll = null;
RightMenu.rmObj = null;

RightMenu.initRightMenu = function(container)
{
    if(!this.rmObj)
    {
        var rmback = new DOMModel(DOM.div(), DOMHandler.name);
        rmback.id('rm_back');
        var rmshadow = new DOMModel(DOM.div(), DOMHandler.name);
        rmshadow.id('rm_shadow');
        rmshadow.style('display', 'none');
        rmshadow.appendChild(rmback);
        RightMenu.rmObj = rmback;
        RightMenu.rmAll = rmshadow;
        if(!container)
        {
            container = new DOMModel(document.body, DOMHandler.name);
        }
        RightMenu.container = container;
        DOMHandler.draw(container, RightMenu.rmAll);
    }
}

RightMenu.focus = function(obj)
{
    obj.cssclass('rm_item_active');
    obj.attachEvent('unfocus', EventType.mouseout, EventHandler.createEvent(RightMenu.unfocus, obj));        
}
    
RightMenu.unfocus = function(obj)
{
    obj.cssclass('rm_item');
    obj.detachEvent('unfocus');
}

RightMenu.hide = function()
{
    RightMenu.rmObj.clearChildren();
    RightMenu.rmAll.style('display', 'none');
    RightMenu.rmAll.domObj();
    RightMenu.container.detachEvent('hide');
}

RightMenu.prototype =
{
    addItem : function(itemText, img, eventObj)
    {
        this.items.push([itemText, img, eventObj]);
    },
    
    setItems : function(itemarr)
    {
        this.items = itemarr;
    },
    
    show : function(x, y)
    {
        for(var i = 0; i < this.items.length; i++)
        {
            var rmitem = new DOMModel(DOM.div(), DOMHandler.name);
            rmitem.cssclass('rm_item');
            var rmimg = new DOMModel(DOM.div(), DOMHandler.name);
            rmimg.cssclass('rm_image');
            if(this.items[i][1] && this.items[i][1] != '')
            {
                rmimg.style('backgroundImage', 'url(' + this.items[i][1] + ')');
            }
            var rmtext = new DOMModel(DOM.div(), DOMHandler.name);
            rmtext.cssclass('rm_text');
            rmtext.value(this.items[i][0]);
            rmitem.appendChild(rmimg);
            rmitem.appendChild(rmtext);
            rmitem.attachEvent('focus', EventType.mouseover, EventHandler.createEvent(RightMenu.focus, rmitem));
            if(this.items[i][2])
            {
                rmitem.attachEvent('eventFunc', EventType.mouseup, this.items[i][2]);
            }
            rmitem.attachEvent('hide', EventType.mouseup, RightMenu.hide);
            RightMenu.rmObj.appendChild(rmitem);
        }
        RightMenu.rmAll.style('left', x);
        RightMenu.rmAll.style('top', y);
        RightMenu.rmAll.style('display', 'block');
        RightMenu.container.attachEvent('hide', EventType.mouseup, RightMenu.hide);
    }
}

function Message() {}

Message.container = null;

Message.background = null;

Message.init = function(container)
{
    Message.container = container;
    
    Message.background = new DOMModel(DOM.div(), DOMHandler.name);
    Message.background.id('msgbg');
    Message.background.name('msgbg');
    Message.background.cssclass('msgbackground');
    DOMHandler.draw(Message.container, Message.background);
}

Message.blurwindow = function()
{
    Message.background.styles([
        ['display', 'block']
        ,['width', Message.container.domObj().clientWidth]
        ,['height', Message.container.domObj().clientHeight]
    ]);
}

Message.focuswindow = function()
{
    Message.background.styles([
        ['display', 'none']
        ,['width', '0px']
        ,['height', '0px']
    ]);
}

Message.dialog = function(windowUrl, wWidth, wHeight)
{
    window.showModalDialog(windowUrl, window, 'dialogHeight:' + wHeight + 'px;dialogWidth:' + wWidth + 'px;help:no;status:no;');
}

Message.openwindow = function(windowUrl, wWidth, wHeight)
{
	var top = (screen.height - wHeight) / 2;
    var left = (screen.width - wWidth) / 2;
    window.open(windowUrl, '_blank','directories=no,top=' + top + ',left=' + left + ',height=' + wHeight + ',width=' + wWidth + ',menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,titlebar=no');
}


function AjaxBus()
{
    this.ajaxObj = this.getXmlHttpObject();
}
//获取ActiveX对象
AjaxBus.prototype.getXmlHttpObject = function()
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
}

//使用get方式进行异步请求
AjaxBus.prototype.doCallBack = function(url)
{
	if(this.ajaxObj)
	{
		this.ajaxObj.open('GET', url);
		var othis = this;
		this.ajaxObj.onreadystatechange = function() {othis.readyStateChange()};
		this.ajaxObj.send(null);
	}
}

//使用post方式进行异步请求
AjaxBus.prototype.doPostBack = function(url, doc)
{
	if(this.ajaxObj)
	{
		this.ajaxObj.open('POST', url, 'true');
		var othis = this;
		this.ajaxObj.onreadystatechange = function() {othis.readyStateChange()};
		this.ajaxObj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		this.ajaxObj.send(doc);
	}
}

//终止请求
AjaxBus.prototype.abortCallBack = function()
{
	if(this.ajaxObj)
	{
		this.ajaxObj.abort();
	}
}

//请求状态跟踪
AjaxBus.prototype.readyStateChange = function()
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
}

//加载时触发的方法
AjaxBus.prototype.onLoading = function() {}

//加载完毕时触发的方法
AjaxBus.prototype.onLoaded = function() {}

//数据传输时触发的方法
AjaxBus.prototype.onInteractive = function() {}

//数据传输完毕时触发的方法
AjaxBus.prototype.onComplete = function(responseText, responseXml) {}

//数据传输失败时触发的方法
AjaxBus.prototype.onError = function(status, statusText, responseText) {}

//终止数据传输时触发的方法
AjaxBus.prototype.onAbort = function() {}