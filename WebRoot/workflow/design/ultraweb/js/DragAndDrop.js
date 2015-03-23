function DragAndDrop() {}

DragAndDrop.container = null;

DragAndDrop.selectBox = null;

DragAndDrop.dragBox = null;

DragAndDrop.dragDivs = new Array();

DragAndDrop.enableSelectObjs = [];

DragAndDrop.selectedObjs = [];

DragAndDrop.drag = true;

DragAndDrop.init = function(parentObj)
{
    DragAndDrop.container = parentObj;
    DragAndDrop.selectBox = new DOMModel(DOM.div());
    DragAndDrop.selectBox.id('dadSelectBox');
    DragAndDrop.selectBox.name('dadSelectBox');
    var selectIn = new DOMModel(DOM.div());
    selectIn.id('dadSelectIn');
    selectIn.name('dadSelectIn');
    DragAndDrop.selectBox.appendChild(selectIn);
    DOMHandler.draw(DragAndDrop.container, DragAndDrop.selectBox);
    DragAndDrop.container.attachEvent('selectdown', EventType.mousedown, EventHandler.createEvent(DragAndDrop.startSelect));
    
    DragAndDrop.dragBox = new DOMModel(DOM.div());
    DragAndDrop.dragBox.id('dadDragBox');
    DragAndDrop.dragBox.name('dadDragBox');
    DOMHandler.draw(DragAndDrop.container, DragAndDrop.dragBox);
    
    DragAndDrop.createdragdiv(10);
}

DragAndDrop.createdragdiv = function(num)
{
    var divs = DragAndDrop.dragDivs.length;
    for(var i = divs; i < divs + num; i++)
    {
        var dragdiv = new DOMModel(DOM.div(), DOMHandler.name);
        dragdiv.id('dragdiv_' + i);
        dragdiv.name('dragdiv_' + i);
        dragdiv.cssclass('dadDragDiv');
        DOMHandler.draw(DragAndDrop.dragBox, dragdiv);
        DragAndDrop.dragDivs.push(dragdiv);
    }
}

DragAndDrop.enableDrag = function(objKey, obj)
{
    for(var i = 0; i < DragAndDrop.enableSelectObjs.length; i++)
    {
        if(DragAndDrop.enableSelectObjs[i][0] == objKey)
        {
            DragAndDrop.enableSelectObjs[i][1] = obj;
            return;
        }
    }
    DragAndDrop.enableSelectObjs.push([objKey, obj]);
}

DragAndDrop.startSelect = function()
{
    var ev = EventHandler.getEvent();
    var src = EventHandler.getElement(ev);
    if(DragAndDrop.drag && ev.clientX < DragAndDrop.container.domObj().clientWidth + DragAndDrop.container.domObj().offsetLeft && ev.clientY < DragAndDrop.container.domObj().clientHeight + DragAndDrop.container.domObj().offsetTop)
    {
        var srcName = src.nodeName;
        for(var i = 0; i < DragAndDrop.enableSelectObjs.length; i++)
        {
            var bx = parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft + ev.clientX;
            var by = parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop + ev.clientY;
            if(DragAndDrop.enableSelectObjs[i][1].type == 'group' && DragAndDrop.checkselect(DragAndDrop.enableSelectObjs[i][1], bx, by, bx, by))
            {
                DragAndDrop.selectBox.styles([['display', 'block'], ['left', bx], ['top', by], ['width', '0'], ['height', '0']]);
                
                var isactive = false;
                for(var j = 0; j < DragAndDrop.selectedObjs.length; j++)
                {
                    if(DragAndDrop.selectedObjs[j][0] == DragAndDrop.enableSelectObjs[i][0])
                    {
                        isactive = true;
                    }
                }
                if(!isactive)
                {
                    if(!ev.ctrlKey)
                    {
                        DragAndDrop.cancelSelect();
                        DragAndDrop.select(DragAndDrop.enableSelectObjs[i], ev.ctrlKey);
                    }
                }
                
                DragAndDrop.container.attachEvent('selectmove', EventType.mousemove, EventHandler.createEvent(DragAndDrop.startDrag, bx, by));
                DragAndDrop.container.attachEvent('selecteup', EventType.mouseup, EventHandler.createEvent(DragAndDrop.selected, ev.ctrlKey));
                DragAndDrop.container.attachEvent('scroll', EventType.scroll, EventHandler.createEvent(DragAndDrop.selected, false));
            }
        }
        
        if(src.id == DragAndDrop.container.id() || src.id == DragAndDrop.dragBox.id())
        {
            if(ev.button != 2)
            {
                var x = parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft + ev.clientX;
                var y = parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop + ev.clientY;
                DragAndDrop.selectBox.styles([['display', 'block'], ['left', x], ['top', y], ['width', '0'], ['height', '0']]);
                DragAndDrop.container.attachEvent('selectmove', EventType.mousemove, EventHandler.createEvent(DragAndDrop.selecting, x, y));
            }
            DragAndDrop.container.attachEvent('selecteup', EventType.mouseup, EventHandler.createEvent(DragAndDrop.selected, ev.ctrlKey));
        }
    }
}

DragAndDrop.selecting = function(x, y)
{
    var ev = EventHandler.getEvent();
    var sx = 0;
    var sy = 0;
    var w = 0;
    var h = 0;
    if(parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft + ev.clientX >= x)
    {
        sx = x;
        w = parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft + ev.clientX - x;
    }
    else
    {
        sx = parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft + ev.clientX;
        w = x - (parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft + ev.clientX);
    }
    if(parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop + ev.clientY >= y)
    {
        sy = y;
        h = parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop + ev.clientY - y;
    }
    else
    {
        sy = parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop + ev.clientY;
        h = y - (parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop + ev.clientY);
    }
    DragAndDrop.selectBox.styles([['display', 'block'],['left', sx], ['top', sy], ['width', w], ['height', h]]);
}

DragAndDrop.selected = function(isCtrlKey)
{
    
    DragAndDrop.container.detachEvent('selectmove');
    DragAndDrop.container.detachEvent('scroll');
    DragAndDrop.container.detachEvent('selecteup');
    var startX = parseInt(DragAndDrop.selectBox.style('left'));
    var startY = parseInt(DragAndDrop.selectBox.style('top'));
    var endX = startX + parseInt(DragAndDrop.selectBox.style('width'));
    var endY = startY + parseInt(DragAndDrop.selectBox.style('height'));
    DragAndDrop.selectBox.styles([['display', 'none'], ['top', '0'], ['left', '0'], ['width', '0'], ['height', '0']]);
    
    var ev = EventHandler.getEvent();
    var isactive = false;
    var srcID = Util.ie ? ev.srcElement.id : ev.target.id;
    for(var i = 0; i < DragAndDrop.selectedObjs.length; i++)
    {
        if(DragAndDrop.selectedObjs[i][0] == srcID)
        {
            isactive = true;
        }
    }
    if(ev.button != 2 || !isactive)
    {
        if(!isCtrlKey)
        {
            DragAndDrop.cancelSelect();
        }
        
        var hasSelect = false;
        for(var i = 0; i < DragAndDrop.enableSelectObjs.length; i++)
        {
            if(DragAndDrop.checkselect(DragAndDrop.enableSelectObjs[i][1], startX, startY, endX, endY))
            {
                DragAndDrop.select(DragAndDrop.enableSelectObjs[i], isCtrlKey);
                hasSelect = true;
            }
        }
        if(!hasSelect)
        {
            DragAndDrop.dragBox.styles([
                ['display', 'none']
                ,['left', 0]
                ,['top', 0]
                ,['width', 0]
                ,['height', 0]
            ]);
        }
    }
}

DragAndDrop.checkselect = function(obj, startX, startY, endX, endY)
{
    var objsX = parseInt(obj.style('left'));
    var objsY = parseInt(obj.style('top'));
    var objeX = objsX + obj.domObj().clientWidth;
    var objeY = objsY + obj.domObj().clientHeight;
    
    var isin = false;
    if(objeX > startX && objsX < endX && objeY > startY && objsY < endY)
    {
        isin = true;
    }
    return isin;
}

DragAndDrop.select = function(obj, isCtrlKey)
{
    var ishas = false;
    var delindex = -1;
    
    for(var i = 0; i < DragAndDrop.selectedObjs.length; i++)
    {
        if(DragAndDrop.selectedObjs[i][0] == obj[0])
        {
            ishas = true;
            delindex = i;
        }
    }
    if(!ishas)
    {
        eval(obj[1].handlerFunc + '.focus(obj[1])');
        DragAndDrop.selectedObjs.push(obj);
    }
    else
    {
        eval(obj[1].handlerFunc + '.unfocus(obj[1])');
        DragAndDrop.selectedObjs.splice(delindex, 1);
    }
    
    if(DragAndDrop.selectedObjs.length > 0)
    {
        var bsX = parseInt(DragAndDrop.selectedObjs[0][1].style('left'));
        var bsY = parseInt(DragAndDrop.selectedObjs[0][1].style('top'));
        var beX = bsX + DragAndDrop.selectedObjs[0][1].domObj().clientWidth;
        var beY = bsY + DragAndDrop.selectedObjs[0][1].domObj().clientHeight;
        for(var i = 1; i < DragAndDrop.selectedObjs.length; i++)
        {
            var osX = parseInt(DragAndDrop.selectedObjs[i][1].style('left'));
            var osY = parseInt(DragAndDrop.selectedObjs[i][1].style('top'));
            var oeX = osX + DragAndDrop.selectedObjs[i][1].domObj().clientWidth;
            var oeY = osY + DragAndDrop.selectedObjs[i][1].domObj().clientHeight;
            
            if(osX < bsX) bsX = osX;
            if(osY < bsY) bsY = osY;
            if(oeX > beX) beX = oeX;
            if(oeY > beY) beY = oeY;
        }
        
        if(DragAndDrop.selectedObjs.length > DragAndDrop.dragDivs.length)
        {
            DragAndDrop.createdragdiv(DragAndDrop.selectedObjs.length - DragAndDrop.dragDivs.length);
        }
        
        for(var i = 0; i < DragAndDrop.selectedObjs.length - 1; i++)
        {
            eval(DragAndDrop.selectedObjs[i][1].handlerFunc + '.focus(DragAndDrop.selectedObjs[i][1])');
        }
        eval(DragAndDrop.selectedObjs[DragAndDrop.selectedObjs.length - 1][1].handlerFunc + '.mainfocus(DragAndDrop.selectedObjs[DragAndDrop.selectedObjs.length - 1][1])');
      
        DragAndDrop.dragBox.styles([
            ['display', 'block']
            ,['left', bsX]
            ,['top', bsY]
            ,['width', beX - bsX]
            ,['height', beY - bsY]
        ]);
    }
    else
    {
        DragAndDrop.dragBox.styles([
            ['display', 'none']
            ,['left', 0]
            ,['top', 0]
            ,['width', 0]
            ,['height', 0]
        ]);
    }
}

DragAndDrop.cancelSelect = function()
{
    for(var i = 0; i < DragAndDrop.selectedObjs.length; i++)
    {
        eval(DragAndDrop.selectedObjs[i][1].handlerFunc + '.unfocus(DragAndDrop.selectedObjs[i][1])')
    }
    DragAndDrop.selectedObjs = [];
    
    DragAndDrop.dragBox.styles([
        ['display', 'none']
        ,['left', 0]
        ,['top', 0]
        ,['width', 0]
        ,['height', 0]
    ]);
}

DragAndDrop.startDrag = function(startX, startY)
{
    var ev = EventHandler.getEvent();
   
    if(DragAndDrop.selectedObjs.length==0)return; //在编辑完环节信息时，出现点击图形，图形突然移动的问题，在此处加了判断，问题解决
    
    if(Math.abs(ev.clientX + parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft - startX) > 5 || Math.abs(ev.clientY + parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop - startY) > 5)
    {
        DragAndDrop.container.detachEvent('selectmove');
        DragAndDrop.container.detachEvent('selecteup');
        DragAndDrop.container.detachEvent('dragstart');
        DragAndDrop.dragFunc(ev);
        for(var i = 0; i < DragAndDrop.selectedObjs.length; i++)
        {
            var dadObj = DragAndDrop.selectedObjs[i][1];
            var left = parseInt(dadObj.style('left')) - parseInt(DragAndDrop.dragBox.style('left'));
            var top = parseInt(dadObj.style('top')) - parseInt(DragAndDrop.dragBox.style('top'));
            var width = parseInt(dadObj.style('width'));
            var height = parseInt(dadObj.style('height'));
            
            DragAndDrop.dragDivs[i].styles([
                ['display', 'block']
                ,['left', left]
                ,['top', top]
                ,['width', width]
                ,['height', height]
            ]);
        }
        DragAndDrop.container.attachEvent('dragmove', EventType.mousemove, EventHandler.createEvent(DragAndDrop.dragging, startX - parseInt(DragAndDrop.dragBox.style('left')), startY - parseInt(DragAndDrop.dragBox.style('top'))));
        DragAndDrop.container.attachEvent('drop', EventType.mouseup, EventHandler.createEvent(DragAndDrop.drop, parseInt(DragAndDrop.dragBox.style('left')), parseInt(DragAndDrop.dragBox.style('top'))));
    }
}

DragAndDrop.dragging = function(addX, addY)
{
    var ev = EventHandler.getEvent();
    DragAndDrop.dragBox.styles([
        ['left', ev.clientX + parseInt(DragAndDrop.container.domObj().scrollLeft) - DragAndDrop.container.domObj().offsetLeft - addX]
        ,['top', ev.clientY + parseInt(DragAndDrop.container.domObj().scrollTop) - DragAndDrop.container.domObj().offsetTop - addY]
    ]);
    DragAndDrop.draggingFunc(ev);
}

DragAndDrop.drop = function(startX, startY)
{
    DragAndDrop.container.detachEvent('dragmove');
    DragAndDrop.container.detachEvent('drop');
    
    var ev = EventHandler.getEvent();
    
    var moveX = parseInt(DragAndDrop.dragBox.style('left')) - startX;
    var moveY = parseInt(DragAndDrop.dragBox.style('top')) - startY;
    
    
    for(var i = 0; i < DragAndDrop.selectedObjs.length; i++)
    {
        var endX = parseInt(DragAndDrop.selectedObjs[i][1].style('left')) + moveX;
        var endY = parseInt(DragAndDrop.selectedObjs[i][1].style('top')) + moveY;
        eval(DragAndDrop.selectedObjs[i][1].handlerFunc + '.move(DragAndDrop.selectedObjs[i][1], endX, endY)');
    }
    for(var i = 0; i < DragAndDrop.dragDivs.length; i++)
    {
        DragAndDrop.dragDivs[i].styles([
            ['display', 'none']
            ,['left', '0px']
            ,['top', '0px']
            ,['width', '0px']
            ,['height', '0px']
        ]);
    }
    DragAndDrop.dropFunc(ev);
}

DragAndDrop.dragFunc = function(e) {}
DragAndDrop.draggingFunc = function(e) {}
DragAndDrop.dropFunc = function(e) {}
