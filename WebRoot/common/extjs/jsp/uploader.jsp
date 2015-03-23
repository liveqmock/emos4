<link type="text/css" href="${ctx}/common/extjs/js/uploader/uploader.css" rel="stylesheet"  />
<script type="text/javascript" src="${ctx}/common/extjs/js/uploader/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/common/extjs/js/uploader/uploaderPanel.js"></script>
<script type="text/javascript">
Ext.onReady(function(){
	   swfuploadUrl='${ctx}/common/extjs/js/uploader/swfupload.swf';
 });

var showUpload =function(uploadAction,postFileName,savepath,store){
     parentStore=store;
     Ext.QuickTips.init();
     uploadWin=new Ext.Window({width:600,height:300,title:'文件上传',
                  layout:'fit',closable:true,closeAction: 'close'})
      var panelItems = [{xtype:'uploadPanel', border : true,draggable : false,
			       uploadUrl : uploadAction,
			       filePostName : postFileName,fileTypes : '*.*',  
			       postParams : {savePath:savepath}
	 }];
	 uploadWin.add(panelItems);
	 uploadWin.doLayout();
	 uploadWin.show();
}
 
</script>