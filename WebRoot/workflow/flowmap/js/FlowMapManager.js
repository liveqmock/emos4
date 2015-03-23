function FlowMapManager(){}

FlowMapManager.container = null;

FlowMapManager.init = function(container)
{
    FlowMapManager.container = container;
}

FlowMapManager.drawFlowMap = function(parentObj,phaseNo,hasFreeProcess,title,content,prevProcess,arrowtype,arrowcontent,color,width,height,top,left)
{

     var process = new DOMModel(VML.RoundRect(), DOMHandler.name);
     process.id(phaseNo);
     process.name(phaseNo);
     process.styles([
                 ['width', width]
                ,['height', height]
                ,['position', 'absolute']
                ,['top', top]
                ,['left',left]
            ]);
     process.parameterss([
                 ['fillcolor', color]
                ,['arcsize', '0.15']
                ,['filled','t']
                ,['strokeweight',1]
            ]);
     var textbox = new DOMModel(VML.TextBox(), DOMHandler.name); 
     textbox.id(phaseNo + '_textbox');
     textbox.name(phaseNo + '_textbox');
     textbox.styles([
                ['textAlign', 'center']
                ,['fontSize', '11px']
            ]);
     textbox.parameterss([
                 ['inset', '0,0,0,0']
            ]);
            
     var ptitle = new DOMModel(DOM.div(), DOMHandler.name);
     ptitle.id(phaseNo + '_title');
     ptitle.name(phaseNo + '_title');
     ptitle.styles([  
                ['paddingTop', '2px']
            ]);
     
     var titleArray = title.split('##');       
     var ptitlespan = new DOMModel(DOM.span(), DOMHandler.name);
     ptitlespan.styles([
                ['fontWeight', 'bold']
            ]);
     ptitlespan.value(titleArray[0]);
     ptitle.appendChild(ptitlespan);
     var ptitlebr =new DOMModel(DOM.br(), DOMHandler.name); 
     ptitle.appendChild(ptitlebr);
     var ptitletime = new DOMModel(DOM.span(), DOMHandler.name);
     ptitletime.value(titleArray[1]);
     ptitle.appendChild(ptitletime);
     process.ptitletime = titleArray[1];
     //存在子流程时
     hasFreeProcess = 0;
     if(hasFreeProcess){
        var ptitlerect1 = new DOMModel(VML.rect(), DOMHandler.name); 
        ptitlerect1.styles([
                ['width', '5']
                ,['height', '5']
                ,['position', 'absolute']
                ,['top', '0']
                ,['left', '0']
            ]);
        ptitlerect1.parameterss([
                 ['fillcolor', 'red']
            ]);
        ptitle.appendChild(ptitlerect1);
        
        var ptitlerect2 = new DOMModel(VML.rect(), DOMHandler.name); 
        ptitlerect2.styles([
                ['width', '5']
                ,['height', '5']
                ,['position', 'absolute']
                ,['top', '38']
                ,['left', '0']
            ]);
        ptitlerect2.parameterss([
                 ['fillcolor', 'red']
            ]);
        ptitle.appendChild(ptitlerect2);
        
        var ptitlerect3 = new DOMModel(VML.rect(), DOMHandler.name); 
        ptitlerect3.styles([
                ['width', '5']
                ,['height', '5']
                ,['position', 'absolute']
                ,['top', '38']
                ,['left', '108']
            ]);
        ptitlerect3.parameterss([
                 ['fillcolor', 'red']
            ]);
        ptitle.appendChild(ptitlerect3);
        
        var ptitlerect4 = new DOMModel(VML.rect(), DOMHandler.name); 
        ptitlerect4.styles([
                ['width', '5']
                ,['height', '5']
                ,['position', 'absolute']
                ,['top', '0']
                ,['left', '108']
            ]);
        ptitlerect4.parameterss([
                 ['fillcolor', 'red']
            ]);
        ptitle.appendChild(ptitlerect4);
     }
     textbox.appendChild(ptitle);
     process.appendChild(textbox);
     
    var fill = new DOMModel(VML.fill(), DOMHandler.name);
    fill.id(phaseNo + '_fill');
    fill.name(phaseNo + '_fill');
    fill.parameterss([
        ['type', 'gradient']
        ,['colors2', 'White']
    ]);
    
    var shadow = new DOMModel(VML.shadow(), DOMHandler.name);
    shadow.parameterss([
        ['type', 'perspective']
       , ['color', 'black']
        ,['on', 't']
        ,['opacity', '.2']
        ,['obscured', 't']
        ,['offset', '1.8px,1.8px']
    ]);
    
    process.appendChild(fill);
    process.appendChild(shadow);
    
    parentObj.appendChild(process);
    
    if(prevProcess!=null)
        process.link = FlowMapManager.drawLink(parentObj,prevProcess,process,arrowtype,arrowcontent,titleArray[0].split('/')[0]);
    
    PreviewBar.drawProcess(phaseNo, left, top, color);
    
    //事件监听
    process.attachEvent('moverprocess', EventType.mouseover, EventHandler.createEvent(showProcessInfo,phaseNo));
    process.attachEvent('moutprocess', EventType.mouseout, EventHandler.createEvent(hideProcessInfo));
    process.attachEvent('mclickprocess', EventType.click, EventHandler.createEvent(InforBar.showProcessInfo,phaseNo));
    
    if(hasFreeProcess){
        process.attachEvent('mdblclickprocess', EventType.dbclick, EventHandler.createEvent(showFreeFlowMap,phaseNo));
    }
    
    var link = process.link;

    return process;
}

FlowMapManager.drawProcessInfo = function(doc,node_Process,Status,Desc,Dealer,PreDealer,StDate,DealDate,FinishDate,Loglist,FieldList)
{
    
    //信息节点
    var node_ProcessInfo = doc.createElement('ProcessInfo');
    
    //信息子节点
    var node_Info = doc.createElement('Info');
    node_Info.appendChild(Model2Xml.createNode(doc, 'Status', Status));
    node_Info.appendChild(Model2Xml.createNode(doc, 'Desc', Desc));
    node_Info.appendChild(Model2Xml.createNode(doc, 'Dealer', Dealer));
    node_Info.appendChild(Model2Xml.createNode(doc, 'PreDealer', PreDealer));
    node_Info.appendChild(Model2Xml.createNode(doc, 'StDate', StDate));
    node_Info.appendChild(Model2Xml.createNode(doc, 'DealDate', DealDate));
    node_Info.appendChild(Model2Xml.createNode(doc, 'FinishDate', FinishDate));

    node_ProcessInfo.appendChild(node_Info);
    
    //用户输入信息节点
    
    for(var i=0;i<FieldList.size();i++){
    node_ProcessInfo.appendChild(FieldList.get(i));
    }
   
    //日志节点
    var node_Log = doc.createElement('Log');
    for(var i=0;i<Loglist.size();i++){
        node_Log.appendChild(Loglist.get(i));
    }
    node_ProcessInfo.appendChild(node_Log);
     
    
    node_Process.appendChild(node_ProcessInfo);
    return node_Process;
}

FlowMapManager.drawLogInfo = function(doc,act,logUser,stDate,result)
{
    var node_LogInfo = doc.createElement('LogInfo');
    node_LogInfo.appendChild(Model2Xml.createNode(doc, 'Act', act));
    node_LogInfo.appendChild(Model2Xml.createNode(doc, 'LogUser', logUser));
    node_LogInfo.appendChild(Model2Xml.createNode(doc, 'StDate', stDate));
    node_LogInfo.appendChild(Model2Xml.createNode(doc, 'Result', result));
    return node_LogInfo;
}

FlowMapManager.drawFieldInfo = function(doc,Name,Value,PrintOneLine)
{
    var node_Field = doc.createElement('Field');
    node_Field.appendChild(Model2Xml.createNode(doc, 'Name', Name));
    node_Field.appendChild(Model2Xml.createNode(doc, 'Value', Value));
    node_Field.appendChild(Model2Xml.createNode(doc, 'PrintOneLine', PrintOneLine));
    return node_Field;
}

FlowMapManager.drawLink = function(parentObj,prevProcess,process,arrowtype,arrowcontent,status)
{
    //环节的位置
    var preProcessTop = parseInt(prevProcess.style('top'));
    var preProcessLeft = parseInt(prevProcess.style('left'));
    var preProcessWidth = parseInt(prevProcess.style('width'));
    var preProcessHeight = parseInt(prevProcess.style('height'));
    
    var processTop = parseInt(process.style('top'));
    var processLeft = parseInt(process.style('left'));
    var processWidth = parseInt(process.style('width'));
    var processHeight = parseInt(process.style('height'));
    
    //入线
    var linepath = FlowMapManager.getlinkpath(prevProcess,process,'IN');
    var linkin = new DOMModel(VML.polyline(), DOMHandler.name);
    linkin.id(process.id()+'_linkin');
    linkin.styles([
        ['position', 'absolute']
        ,['zIndex', 50]
    ]);
    linkin.parameterss([
                ['act', '0']
                ,['points', linepath]
                ,['filled', 'f']
            ]);
    var stroke = new DOMModel(VML.stroke(), DOMHandler.name);
    stroke.parameterss([['dashstyle', 'solid'], ['endarrow', 'Block']]);
    linkin.appendChild(stroke);
    parentObj.appendChild(linkin);
    var lineArray = linepath.split(',');
    PreviewBar.drawLink('in', 'IN',lineArray[0],lineArray[1],lineArray[2],lineArray[3],lineArray[4],lineArray[5],lineArray[6],lineArray[7]);
    //连线上的内容:IN
        
    var indivcontent = new DOMModel(DOM.div(), DOMHandler.name);
    indivcontent.id(process.id()+'_incontent');
    indivcontent.styles([
        ['position', 'absolute']
        ,['zIndex', 50]
        ,['top',processTop+2]
        ,['left',preProcessLeft+preProcessWidth+15]
        ,['fontSize','12px']
    ]);
    var infontcontent = new DOMModel(DOM.font(), DOMHandler.name);
        infontcontent.parameterss([
                    ['title', prevProcess.ptitletime+' '+arrowcontent]
                ]);
    var content = '';
    if(arrowcontent.length>7)
    {
        content=arrowcontent.substring(0, 7)+'...';
    }else content=arrowcontent;
    infontcontent.value(content);
    indivcontent.appendChild(infontcontent);
    parentObj.appendChild(indivcontent);
    
    if(arrowtype=='OUT'){
        //出线
        var linepathout = FlowMapManager.getlinkpath(prevProcess,process,arrowtype);
        var linkout = new DOMModel(VML.polyline(), DOMHandler.name);
        linkout.id(process.id()+'_linkout');
        linkout.styles([
            ['position', 'absolute']
            ,['zIndex', 50]
        ]);
        linkout.parameterss([
                    ['act', '0']
                    ,['points', linepathout]
                    ,['filled', 'f']
                ]);
        var strokeout = new DOMModel(VML.stroke(), DOMHandler.name);
        strokeout.parameterss([['dashstyle', 'solid'], ['endarrow', 'Block']]);
        linkout.appendChild(strokeout);
        
        linkin.linkout = linkout;
        parentObj.appendChild(linkout);
        
        lineArray = linepathout.split(',');
        PreviewBar.drawLink('out', 'OUT',lineArray[0],lineArray[1],lineArray[2],lineArray[3],lineArray[4],lineArray[5],lineArray[6],lineArray[7]); 
        //入线与出线的距离
        var linepathother = FlowMapManager.getlinkpath(prevProcess,process,'OTHER');
        var linkother = new DOMModel(VML.polyline(), DOMHandler.name);
        linkother.styles([
            ['position', 'absolute']
            ,['zIndex', 50]
        ]);
        linkother.parameterss([
                    ['act', '0']
                    ,['points', linepathother]
                    ,['filled', 'f']
                ]);
        parentObj.appendChild(linkother);
        
        //连线上的内容:OUT
        
        var outdivcontent = new DOMModel(DOM.div(), DOMHandler.name);
        outdivcontent.id(process.id()+'_outcontent');
        outdivcontent.styles([
            ['position', 'absolute']
            ,['zIndex', 50]
            ,['top',processTop+processHeight/3+15]
            ,['left',preProcessLeft+preProcessWidth+15]
            ,['fontSize','12px']
        ]);
        var outfontcontent = new DOMModel(DOM.font(), DOMHandler.name);
        outfontcontent.parameterss([
                    ['title', process.ptitletime+' '+status]
                ]);
        outfontcontent.value(status);
        
        outdivcontent.appendChild(outfontcontent);
        parentObj.appendChild(outdivcontent);
    }
    return linkin;
}
FlowMapManager.getlinkpath = function(prevProcess,process,arrowtype){

    var pointarr = [];
    var preProcessTop = parseInt(prevProcess.style('top'));
    var preProcessLeft = parseInt(prevProcess.style('left'));
    var preProcessWidth = parseInt(prevProcess.style('width'));
    var preProcessHeight = parseInt(prevProcess.style('height'));
    
    var processTop = parseInt(process.style('top'));
    var processLeft = parseInt(process.style('left'));
    var processWidth = parseInt(process.style('width'));
    var processHeight = parseInt(process.style('height'));
    
    
    switch(arrowtype){
        case 'IN':
            //起点
            pointarr.push(preProcessLeft+preProcessWidth);
            pointarr.push(preProcessTop+preProcessHeight/3);
            
            //折点1
            pointarr.push(preProcessLeft+preProcessWidth+10);
            pointarr.push(preProcessTop+preProcessHeight/3);
            
            //折点2
            pointarr.push(preProcessLeft+preProcessWidth+10);
            pointarr.push(processTop+processHeight/3);
            
            //终点
            pointarr.push(processLeft);
            pointarr.push(processTop+processHeight/3);
            break;
        case 'OUT':
            //起点
            pointarr.push(processLeft);
            pointarr.push(processTop+processHeight/3+10);
            //终点
            pointarr.push(preProcessLeft+preProcessWidth+10);
            pointarr.push(processTop+processHeight/3+10);
        break;
        case 'OTHER':
            //起点
            pointarr.push(preProcessLeft+preProcessWidth+10);
            pointarr.push(processTop+processHeight/3+10);
            //终点
            pointarr.push(preProcessLeft+preProcessWidth+10);
            pointarr.push(processTop+processHeight/3);
        break;
    }
    
  
  return pointarr.toString();
}