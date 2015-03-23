//通过封装SWFUpload实现的上传下载组件
var UltraSWFUpload;
/**
 * id:组件标识 uploadAction：上传action queryAction：查询下载action downloadAction
 * ：下载action attachmentGroupId：附件组id savePath：上传/下载路径 uploadable：是否可上传
 * downTableVisible： 是否显示下载列表 uploadBtnable：上传按钮是否可用 btnImageUrl：选择附件按钮图片url flashUrl
 * ：SWFUpload.swf url fileTypes：上传文件类型 fileTypesDescription：文件类型描述
 * isMultiUpload：是否批量上传 isMultiDownload：是否批量下载 isAutoUpload：是否自动上传
 * progressIsVisible:进度条是否显示 uploadTableVisible:上传列表是否可见
 * imagePath:样式图片路径 createDirByDate：是否根据日期新建文件夹 returnValue：自定义返回值格式 typeDicCode:附件备注字典类型code queryAttachTypeAction:查询附件类型
 * editAction:附件在线编辑action mode:附件打开模式 operationParams:附件操作权限参数  editFileType：可编辑的文件类型
 */
if (UltraSWFUpload === undefined) 
{
	UltraSWFUpload = function(id, uploadAction, queryAction, downloadAction,
			attachmentGroupId, savePath, uploadable, uploadTableVisible, downTableVisible, uploadBtnable,
			btnImageUrl, flashUrl, fileTypes, fileTypesDescription,
			isMultiUpload, isMultiDownload, isAutoUpload,
			progressIsVisible, imagePath, operationParams, editFileType, deleteAction, 
			createDirByDate, attachmentUser, returnValue,delSelfOnly, typeDicCode, queryAttachTypeAction, editAction, mode)
	{
		//初始化UltraSWFUpload对象的属性
		this.preInitUltraSWFUpload(id, uploadAction, queryAction, downloadAction,
			attachmentGroupId, savePath, uploadable, uploadTableVisible, downTableVisible, uploadBtnable,
			btnImageUrl, flashUrl, fileTypes, fileTypesDescription,
			isMultiUpload, isMultiDownload, isAutoUpload,
			progressIsVisible, imagePath, operationParams, editFileType, deleteAction, 
			createDirByDate, attachmentUser, returnValue,delSelfOnly, typeDicCode, queryAttachTypeAction, editAction, mode);
	}
}

UltraSWFUpload.prototype.preInitUltraSWFUpload = function(id, uploadAction, queryAction, downloadAction,
			attachmentGroupId, savePath, uploadable, uploadTableVisible, downTableVisible, uploadBtnable,
			btnImageUrl, flashUrl, fileTypes, fileTypesDescription,
			isMultiUpload, isMultiDownload, isAutoUpload,
			progressIsVisible, imagePath, operationParams, editFileType, deleteAction, 
			createDirByDate, attachmentUser, returnValue,delSelfOnly, typeDicCode, queryAttachTypeAction, editAction,mode) {
	/*---初始化ultraswfupload对象的属性 BEGIN---*/
	this.uploaderId = id;
	this.uploadAction = uploadAction;
	this.queryAction = queryAction;
	this.downloadAction = downloadAction;
	this.attachmentGroupId = attachmentGroupId;
	this.savePath = savePath;
	this.uploadable = uploadable;
	this.uploadTableVisible = uploadTableVisible;
	this.downTableVisible = downTableVisible;
	this.uploadBtnable = uploadBtnable;
	this.btnImageUrl = btnImageUrl;
	this.flashUrl = flashUrl;
	this.fileTypes = fileTypes;
	this.fileTypesDescription = fileTypesDescription;
	this.isMultiUpload = isMultiUpload;
	this.isMultiDownload = isMultiDownload;
	this.isAutoUpload = isAutoUpload;
	this.progressIsVisible = progressIsVisible;
	this.imagePath = imagePath;
	this.operationParams = operationParams;
	this.editFileType = editFileType.split(",");
	this.deleteAction = deleteAction;
	this.createDirByDate = createDirByDate;
	this.attachmentUser = attachmentUser;
	this.returnValue = returnValue;
	this.delSelfOnly = delSelfOnly;
	this.typeDicCode = typeDicCode;
	this.queryAttachTypeAction = queryAttachTypeAction;
	this.editAction = editAction;
	this.mode = mode;
	/*---初始化ultraswfupload对象的属性 END---*/
	//下载列表数组
	this.batchDownArray = new Array();
	//把自身实例保存到数组中
	this.uploaderName = "UltraSWFUpload_" + UltraSWFUpload.movieCount++;
	UltraSWFUpload.instances[this.uploaderName] = this;
	this.initComplete = false; //初始化是否完毕
	/*-- 初始化界面 --*/
	this.initView();
};

UltraSWFUpload.prototype.initUltraSWFUpload = function() {
	try {
		// 初始化SWFUpload
		/*---构造swfupload的setting参数（JSON格式）BEGIN---*/
		this.settings = {};
		this.settings.post_params = {};
		this.settings.custom_settings = {};
		/*---构造swfupload的setting参数（JSON格式）END---*/
		// post_params
		this.settings.post_params["uploader"] = "UltraSWFUpload";
		this.settings.post_params["attachmentGroupId"] = this.attachmentGroupId;
		this.settings.post_params["savePath"] = this.savePath;
		this.settings.post_params["fileTypes"] = this.fileTypes;
		this.settings.post_params["attachmentUser"] = this.attachmentUser;
		this.settings.post_params["createDirByDate"] = this.createDirByDate;
		this.settings.post_params["returnValue"] = this.returnValue;
		// File Upload Settings
		this.settings["upload_url"] = encodeURI(encodeURI(this.uploadAction));
		this.settings["button_image_url"] = this.btnImageUrl;
		this.settings["flash_url"] = this.flashUrl;
		this.settings["flash9_url"] = "/common/plugin/swfupload/js/swfupload_fp9.swf";
		this.settings["button_placeholder_id"] = this.uploaderId + UltraSWFUpload.SPAN_BTN;
		this.settings["file_post_name"] = "file";
		this.settings["file_size_limit"] = "100MB";
		this.settings["file_types"] = this.fileTypes;
		this.settings["file_types_description"] = this.fileTypesDescription;
		this.settings["file_upload_limit"] = "0";

		// upload event settings
		this.settings["file_queue_error_handler"] = fileQueueError;
		this.settings["file_dialog_complete_handler"] = fileDialogComplete;
		this.settings["file_queued_handler"] = fileQueued;
		this.settings["upload_progress_handler"] = uploadProgress;
		this.settings["upload_error_handler"] = uploadError;
		this.settings["upload_success_handler"] = uploadSuccess;
		this.settings["upload_complete_handler"] = uploadComplete;
		
		//button settings
		this.settings["button_width"] = "52";
		this.settings["button_height"] = "18";
		this.settings["button_text"] = '<span class="button">上传附件 <span class="buttonSmall">(10 MB Max)</span></span>';
		this.settings["button_text_style"] = '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }';
		this.settings["button_text_top_padding"] = "0";
		this.settings["button_text_left_padding"] = "0";
		this.settings["button_window_mode"] = SWFUpload.WINDOW_MODE.TRANSPARENT;
		this.settings["button_cursor"] = SWFUpload.CURSOR.HAND;
		if (false == this.isMultiUpload) {
			this.settings["file_queue_limit"] = "1";
			this.settings["button_action"] = SWFUpload.BUTTON_ACTION.SELECT_FILE;
		}
		
		//debug setting
		this.settings["debug"] = false;
		
		/*---传递程序员控制程序的自定义参数到swfupload对象当中，这些参数可以根据需要传递到事件处理脚本当中 BEGIN---*/
		this.settings.custom_settings["upload_target"] = this.uploaderId
				+ UltraSWFUpload.DIV_UP_PROGRESS;// 进度条
		this.settings.custom_settings["fileUploadTable"] = this.uploaderId
				+ UltraSWFUpload.TABLE_UP;       // 上传列表
		this.settings.custom_settings["currentSWFUpload"] = this.uploaderId;// 当前上传器
		this.settings.custom_settings["currentBtnCancel"] = this.uploaderId
				+ UltraSWFUpload.BTN_CANCEL_ALL; // 取消按钮
		this.settings.custom_settings["afterUploadSuccess"] = this.uploaderId
				+ ".afterUploadSuccess";         // 上传成功后的回调方法
		this.settings.custom_settings["afterUploadComplete"] = this.uploaderId
				+ ".afterUploadComplete";        // 上传完成后的回调方法
		this.settings.custom_settings["isUpdateDownloadTable"] = this
				.getDownTableVisible();              // 上传成功后是否更新下载列表
		this.settings.custom_settings["getUpdateDownTable"] = this.uploaderId
				+ ".getAndInitDownTable()";      // 更新下载列表方法
		this.settings.custom_settings["progressIsVisible"] = this.progressIsVisible;   // 更新下载列表方法
		this.settings.custom_settings["uploadTableVisible"] = this.uploadTableVisible;   // 是否显示上传列表
		// =========新增功能
		this.settings.custom_settings["isAutoUpload"] = this.isAutoUpload;             // 是否自动提交上传
		this.settings.custom_settings["imagePath"] = this.imagePath;              // 图片路径
		/*---传递程序员控制程序的自定义参数到swfupload对象当中，这些参数可以根据需要传递到事件处理脚本当中 END---*/
		//构造上传下载对象
		this.uploader = new SWFUpload(this.settings);
	} catch (ex) {
		alert(ex);
	}
};

UltraSWFUpload.prototype.postInitUltraSWFUpload = function() {
	// 如果可下载，获取下载列表
	if (this.getDownTableVisible()) 
	{
		this.getAndInitDownTable();
	}
};

UltraSWFUpload.prototype.cancelUploadAll = function() {
	var infoTable = document.getElementById(this.getUploaderId()
			+ UltraSWFUpload.TABLE_UP);
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if (rows == null) {
		return false;
	}
	for ( var i = 1; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	for ( var i = 0; i < ids.length; i++) {
		deleteFile(ids[i], this.getUploaderId() + UltraSWFUpload.TABLE_UP, this
				.getUploaderId(), this.getUploaderId()
				+ UltraSWFUpload.BTN_CANCEL_ALL);
	}
	document.getElementById(this.getUploaderId()
			+ UltraSWFUpload.BTN_CANCEL_ALL).disabled = true;
};

UltraSWFUpload.prototype.afterUploadSuccess = function(serverData) {
	//	
};

UltraSWFUpload.prototype.afterUploadComplete = function(serverData) {
	//	
};
UltraSWFUpload.prototype.afterReCountAttType = function() {
	//	
};
UltraSWFUpload.prototype.destory = function() {
	this.uploader.destroy();
	UltraSWFUpload.instances[this.uploaderName]  = null;
}
UltraSWFUpload.prototype.startUploadFile = function() {
	if(this.typeDicCode==""){
		this.getUploader().startUpload();
	}
	else{
		var typeValue = document.getElementById(this.uploaderId+"_attachTypeSel").value;
		if(typeValue==""){
			alert("请选择附件类型！")
		}else{
			this.getUploader().startUpload();
		}
	}
};

UltraSWFUpload.prototype.resetUploadUrl = function(index) {
	var tempurl = this.uploadAction;
	var poi = tempurl.indexOf('remark');
	var remarkValue = this.attachTypeName[index];
	//alert(remarkValue);
	if(poi==-1){
		if(tempurl.indexOf('?')==-1)
			tempurl += "?remark=" + remarkValue;
		else
			tempurl += "&remark=" + remarkValue;
		this.uploadAction = tempurl;
	}else{
		var poi2 = tempurl.substring(poi).indexOf('&');
		if(poi2==-1){
			tempurl = tempurl.substring(0,poi);
			tempurl += "remark=" + remarkValue;
		}else{
			var left = tempurl.substring(0,poi);
			left += "remark=" + remarkValue;
			left += tempurl.substring(poi2);
			tempurl = left;
		}
		this.uploadAction = tempurl;
	}
	this.getUploader().setUploadURL(encodeURI(encodeURI(this.uploadAction)));
	var rule = this.attachTypeRule[index];
	if(rule!=""){
		var poi1 = rule.indexOf("@T");
		if(poi1==-1)
			return;
		var fileTypeRule = rule.substring(poi1+2);
		poi1 = fileTypeRule.indexOf("@");
		if(poi1!=-1)
			fileTypeRule = fileTypeRule.substring(0,poi1);
		//alert(fileTypeRule);
		this.getUploader().setFileTypes(encodeURI(encodeURI(fileTypeRule)),"project reference("+fileTypeRule+")");
	}else{
		this.getUploader().setFileTypes(encodeURI(encodeURI(this.fileTypes)),"project reference("+this.fileTypes+")");
	}
};

/*
UltraSWFUpload.prototype.attachTypeCounterAdd = function(){
	if(this.typeDicCode=="" || this.attachTypeCounter==undefined)
		return;
	var index = document.getElementById(this.uploaderId+"_attachTypeSel").value;
	if(index=="")
		return;
	index = parseInt(index);
	this.attachTypeCounter[index] += 1;
}
UltraSWFUpload.prototype.attachTypeCounterSub = function(attachtype){
	
}
*/
//改变上传路径，路径请一律使用 /
UltraSWFUpload.prototype.changeSavePath = function(savePath) {
	this.savePath = savePath;
	var uploader = this.getUploader();
	uploader.removePostParam("savePath");
	uploader.addPostParam("savePath",savePath);
};
UltraSWFUpload.prototype.cancelUpload = function(fileId, trigger_error_event) {
	this.getUploader().cancelUpload(fileId, trigger_error_event);
};
// 属性操作
UltraSWFUpload.prototype.setQueryAction = function(queryAction) {
	this.queryAction = queryAction;
};

UltraSWFUpload.prototype.getQueryAction = function() {
	return this.queryAction;
};

UltraSWFUpload.prototype.setDownloadAction = function(downloadAction) {
	this.downloadAction = downloadAction;
};

UltraSWFUpload.prototype.getDownloadAction = function() {
	return this.downloadAction;
};

UltraSWFUpload.prototype.setImagePath = function(imagePath) {
	this.imagePath = imagePath;
};

UltraSWFUpload.prototype.getImagePath = function() {
	return this.imagePath;
};

UltraSWFUpload.prototype.setAttachmentGroupId = function(attachmentGroupId) {
	this.attachmentGroupId = attachmentGroupId;
};

UltraSWFUpload.prototype.getAttachmentGroupId = function() {
	return this.attachmentGroupId;
};
UltraSWFUpload.prototype.getFileTypesDescription = function()
{
	return this.fileTypesDescription;
};
UltraSWFUpload.prototype.setIsMultiDownload = function(isMultiDownload) {
	this.isMultiDownload = isMultiDownload;
};

UltraSWFUpload.prototype.getIsMultiDownload = function() {
	return this.isMultiDownload;
};

UltraSWFUpload.prototype.setProgressIsVisible = function(progressIsVisible) {
	this.progressIsVisible = progressIsVisible;
};

UltraSWFUpload.prototype.getProgressIsVisible = function() {
	return this.progressIsVisible;
};

UltraSWFUpload.prototype.setIsAutoUpload = function(isAutoUpload) {
	this.isAutoUpload = isAutoUpload;
};

UltraSWFUpload.prototype.getIsAutoUpload = function() {
	return this.isAutoUpload;
};

UltraSWFUpload.prototype.setIsMultiUploadd = function(isMultiUpload) {
	this.isMultiUpload = isMultiUpload;
};

UltraSWFUpload.prototype.getIsMultiUpload = function() {
	return this.isMultiUpload;
};
UltraSWFUpload.prototype.getDeleteAction = function()
{
	return this.deleteAction;
}
UltraSWFUpload.prototype.setDeleteAction = function(deleteAction)
{
	this.deleteAction = deleteAction;
}
/**
 * 上传按钮是否显示可用
 */
UltraSWFUpload.prototype.setUploadBtnable = function(usable) {
	if (usable == false || usable == "false") {
		this.uploadBtnable = false;
	} else {
		this.uploadBtnable = true;
	}

};

UltraSWFUpload.prototype.getUploadBtnable = function() {
	return this.uploadBtnable;
};

/**
 * 是否显示下载列表
 */
UltraSWFUpload.prototype.setDownTableVisible = function(downTableVisible) {
	if (downTableVisible == true || downTableVisible == "true") {
		this.downTableVisible = true;
	} else {
		this.downTableVisible = false;
	}
};

UltraSWFUpload.prototype.getDownTableVisible = function() {
	return this.downTableVisible;
};

/**
 * jsp组件id，即uploader id
 */
UltraSWFUpload.prototype.getUploaderId = function() {
	return this.uploaderId;
};

UltraSWFUpload.prototype.getUploader = function() {
	return this.uploader;
};
/**
 * 设置文件上传器
 */
UltraSWFUpload.prototype.setUploader = function(uploader) {
	this.uploader = uploader;
};
/**
 * 设置上传Url
 */
UltraSWFUpload.prototype.setUploadURL = function(url) {
	this.uploadService = url;
	this.getUploader().settings["upload_url"] = url;
};

UltraSWFUpload.prototype.getUploadURL = function() {
	return this.uploadService;
};

UltraSWFUpload.prototype.setPostParams = function(paramsObject) {
	this.getUploader().setPostParams(paramsObject);
};

/* ******************************************************** */
/* 下载列表 */
/* ******************************************************** */
UltraSWFUpload.prototype.getAndInitDownTable = function() {
	var params = {
		attachmentGroupId : this.getAttachmentGroupId(),
		fileTypesDescription: this.getFileTypesDescription()
	};
	var uploaderId = this.getUploaderId();
	var tableId = uploaderId + UltraSWFUpload.TABLE_DWN;
	var uploaderSelf = this;
	var tempArray = new Array();
	//清空附件业务类型计数器
	if(uploaderSelf.attachTypeCounter!=undefined){
		var curlen = uploaderSelf.attachTypeCounter.length;
		for(var i=0;i<curlen;i++){
			uploaderSelf.attachTypeCounter[i] = 0;
		}
	}
	var queryURL = this.getQueryAction();
	if(queryURL.indexOf('?')==-1)
		queryURL += "?attachmentGroupId="+uploaderSelf.getAttachmentGroupId()+"&fileTypesDescription="+this.getFileTypesDescription()+"&ajax=1";
	else
		queryURL += "&attachmentGroupId="+uploaderSelf.getAttachmentGroupId()+"&fileTypesDescription="+this.getFileTypesDescription()+"&ajax=1";
	$.post(queryURL, function(tableData) {
		var downtable = document.getElementById(tableId);
		if(tableData != null && tableId!=undefined && tableData.length==0)
		{
			try 
			{  
	          eval(uploaderId +"deal(0)");
	        } catch(e){}  
			UltraSWFUpload.reSetTable(tableId);
			var cellInfo = downtable.insertRow(-1).insertCell(-1);
			cellInfo.colSpan = downtable.rows[0].cells.length;
			cellInfo.align = "center";
			//cellInfo.innerHTML = "空附件列表!";
			cellInfo.innerHTML = "无附件!";
		}
		else
		{
			if (tableData != undefined) 
			{
			var rows = tableData.length;
			try 
			{  
	          eval(uploaderId +"deal("+rows+")");
	        } catch(e){}   
			UltraSWFUpload.reSetTable(tableId);
			for ( var i = 0; i < rows; i++) 
			{
				var row = downtable.insertRow(-1);
				row.id = uploaderSelf.getUploaderId() + tableData[i]["attachmentId"];
				var col1;
				if(uploaderSelf.isMultiDownload==true)
				{
					col1 = row.insertCell(-1);//复选框
				}
				var colSerialNum = row.insertCell(-1);//序号
				colSerialNum.innerHTML = i+1;
				var col2 = row.insertCell(-1);//附件名称
				var col3 = row.insertCell(-1);//上传人
				var col4 = row.insertCell(-1);//上传时间
				//var col5 = row.insertCell(-1);//附件大小
				//var col5 = row.insertCell(-1);//类型
				var col6 = row.insertCell(-1);//附件操作
				
				//添加checkbox对象
				var checkBox = document.createElement("input");
				checkBox.type = "checkbox";
				checkBox.value = tableData[i]["fileFileName"] + "@comm@"
						+ tableData[i]["fileNewName"] + "@comm@" + tableData[i]["savePath"]
						+ "@comm@" + tableData[i]["attachmentId"];
				checkBox.onclick = function()
				{
					if(this.checked==true)//注意，这里的this代表当前复选框
					{
						UltraSWFUpload.checkIsAllChecked(true,uploaderSelf.uploaderName);
					}
					else
					{
						UltraSWFUpload.checkIsAllChecked(false,uploaderSelf.uploaderName);
					}
				}
				tempArray.push(checkBox);
				
				//批量下载时,添加复选框
				if (uploaderSelf.getIsMultiDownload()==true) 
				{
					col1.appendChild(checkBox);
				} 
				
				if(/^.,.,1$/.test(uploaderSelf.operationParams)==true){
					col2.innerHTML = "<a href='javascript:"+uploaderSelf.getUploaderId()
										+".singleDownload("+(tempArray.length-1)+");'>"
										+tableData[i]["fileFileName"]
										+"</a>";
				}else{
					col2.innerHTML = tableData[i]["fileFileName"];
				}
				col3.innerHTML = tableData[i]["attUser"];
				col4.innerHTML = tableData[i]["uploadTime"];
				//col5.innerHTML = tableData[i]["attSize"];
				//col5.innerHTML = tableData[i]["remark"];
				//相应类型的附件计数器，将当前列表中的各附件类型的数量进行分类统计
				if(uploaderSelf.attachTypeCounter!=undefined){
					var ctrlen = uploaderSelf.attachTypeCounter.length;
					for(var j=0;j<ctrlen;j++){
						if(uploaderSelf.attachTypeName[j]==tableData[i]["remark"]){
							uploaderSelf.attachTypeCounter[j] += 1;
							break;
						}
					}
				}
				var col6html = "";
				//是否可下载
				if(/^.,.,1$/.test(uploaderSelf.operationParams)==true){
					col6html += "<a href='javascript:"+uploaderSelf.getUploaderId()
									+".singleDownload("+(tempArray.length-1)
									+");'><img src='"+uploaderSelf.getImagePath()
									+"images/upload/download.png' border='0' alt='下载' title='下载'/></a>&nbsp&nbsp;";//下载附件
				}
				//是否可编辑
				if(/^1,.,.$/.test(uploaderSelf.operationParams)==true){
					var tempFileName = tableData[i]["fileNewName"];
					var poix = tempFileName.lastIndexOf(".");
					if(poix!=-1 || poix!=tempFileName.length-1){
						var fileType = tempFileName.substring(poix + 1);
						if(uploaderSelf.checkEditType(fileType) && "合同正文"==tableData[i]["remark"]){
							col6html += "<a href='javascript:"+uploaderSelf.getUploaderId()
										+".editAttachment(\""+tableData[i]["attachmentId"]+"\",\""+(tempArray.length-1)+"\");'>"
										+"<img src='"+uploaderSelf.getImagePath()
										+"images/upload/edit.png' border='0' alt='编辑' title='编辑'/></a>&nbsp;";//编辑附件
							col6html += "&nbsp;&nbsp;";
						}
					}
				}
				//是否可删除
				if(/^.,1,.$/.test(uploaderSelf.operationParams)==true){
					if(uploaderSelf.delSelfOnly==true){
						if(Sys_CurrentLoginUserPid==tableData[i]["attUserPid"]){
							col6html += "<a href='javascript:"+uploaderSelf.getUploaderId()
									+".delAttachment(\""+tableData[i]["attachmentId"]
									+"\");'>"
									+"<img src='"+uploaderSelf.getImagePath()
									+"images/upload/del.png' border='0' alt='删除' title='删除'/></a>";//删除附件
						}
					}else{
						col6html += "<a href='javascript:"+uploaderSelf.getUploaderId()
									+".delAttachment(\""+tableData[i]["attachmentId"]
									+"\");'>"
									+"<img src='"+uploaderSelf.getImagePath()
									+"images/upload/del.png' border='0' alt='删除' title='删除'/></a>";//删除附件
					}
				}
				col6.innerHTML = col6html;
			}
			uploaderSelf.batchDownArray = tempArray;
		}
	}
	changeRow_color(tableId);
	//重新统计各业务类型附件数量后要干的事情
	uploaderSelf.afterReCountAttType();
	}, "json");
};

/**
单个下载
*/
UltraSWFUpload.prototype.singleDownload = function(checkPoi) {
	var valueStr =  (this.batchDownArray[checkPoi]).value;
	var valueArr = valueStr.split("@comm@");
	
	//var fileFileName = (valueArr[0].replace(/#/g,'@jing@')).replace(/&/g,'@join@');
	//var fileNewName = (valueArr[1].replace(/#/g,'@jing@')).replace(/&/g,'@join@');
	//var savePath = (valueArr[2].replace(/#/g,'@jing@')).replace(/&/g,'@join@');
	var attid = valueArr[3];
	var href = this.getDownloadAction();
	if(href.indexOf("?")==-1)
		href += "?downloadIds="+attid;
	else
		href += "&downloadIds="+attid;
	href = encodeURI(encodeURI(href));
	window.open(href);
}

/**
 * 批量下载附件
 * @memberOf {TypeName} 
 */
UltraSWFUpload.prototype.batchDownload = function() {
	var allfiles = this.batchDownArray;
	var fileNames = "";
	var fileNameSeparator = ",";
	var hasDownFile = false;
	for ( var i = 0; i < allfiles.length; i++) {
		if (allfiles[i].checked == true) {
			hasDownFile = true;
			fileNames += (allfiles[i].value.split("@comm@"))[3];
			fileNames += fileNameSeparator;
		}
	}
	if (hasDownFile) {
		fileNames = fileNames.substring(0,fileNames.lastIndexOf(fileNameSeparator));
		var downUrl = this.getDownloadAction();
		if(downUrl.indexOf("?")==-1)
			downUrl += "?downloadIds="+fileNames;
		else
			downUrl += "&downloadIds="+fileNames;
		window.open(downUrl);
		
	} else {
		alert("未选择下载文件,请确认!"); 
	}
};
/**
 * 编辑附件
 * @param {Object} checkPoi
 * @param {Object} attachmentId
 */
UltraSWFUpload.prototype.editAttachment = function(attachmentId,checkPoi)
{
	var valueStr =  (this.batchDownArray[checkPoi]).value;
	var valueArr = valueStr.split("@comm@");
	var fileName = valueArr[0];
	var fileNewName = valueArr[1];
	var docPath = valueArr[2];
	var href = this.editAction+"?id="+attachmentId+"&fileName="+fileName+"&fileNewName="+fileNewName+"&docPath="+docPath + "&mode=" + this.mode;
	href = encodeURI(encodeURI(href));
	window.open(href);
}

/**
 * 删除附件
 * @param {Object} attachmentId
 * @memberOf {TypeName} 
 */
UltraSWFUpload.prototype.delAttachment = function(attachmentId)
{
	var uploader = this;
	if(confirm("确认删除该附件！"))
	{
		var deleteUrl = this.getDeleteAction();
		if(deleteUrl.indexOf('?')==-1)
			deleteUrl += "?attachmentId="+attachmentId+"&jsoncallback=?";
		else
			deleteUrl += "&attachmentId="+attachmentId+"&jsoncallback=?";
		$.getJSON(deleteUrl,function(json){
			if("true"==json[0].result)
			{
				uploader.getAndInitDownTable();
				alert("删除成功！");
			}
			else
			{
				uploader.getAndInitDownTable();
				alert("删除失败！");
			}
		});
	}
}

UltraSWFUpload.reSetTable = function(tableId) {
	var table = document.getElementById(tableId);
	if(table==null)
		return;
	var rows = table.rows;
	if(rows!=null && rows.length>0)
	{
		for(var i=rows.length-1;i>0;i--)
		{
			table.deleteRow(i);
		}
	}
};

/*
全选或全不选复选框
*/
UltraSWFUpload.checkAllAttachment = function(checker,uploaderName)
{
	var currentuploader = UltraSWFUpload.instances[uploaderName];
	var allfiles = currentuploader.batchDownArray;
	for(var i=0;i<allfiles.length;i++)
	{
		allfiles[i].checked = checker.checked;
	}
}

/*
检查是否是全选
*/
UltraSWFUpload.checkIsAllChecked = function(ischeck,uploaderName)
{
	var currentuploader = UltraSWFUpload.instances[uploaderName];
	var allfiles = currentuploader.batchDownArray;
	var result = true;
	if(ischeck)
	{
		for(var i=0;i<allfiles.length;i++)
		{
			if(allfiles[i].checked==false)
			{
				result = false;
				break;
			}
		}
		if(result)
		{
			document.getElementById(currentuploader.uploaderId+"_checkAll").checked = true;
		}
	}
	else
	{
		document.getElementById(currentuploader.uploaderId+"_checkAll").checked = false;
	}
}


UltraSWFUpload.prototype.initView = function(){
	var uploaderSelf = this;
	if(this.typeDicCode!=""){
		var myurl = this.queryAttachTypeAction;
		if(myurl.indexOf('?')==-1)
			myurl += "?dicCode="+this.typeDicCode+"&jsoncallback=?";
		else
			myurl += "&dicCode="+this.typeDicCode+"&jsoncallback=?";
		$.getJSON(myurl,function(tableData){
			//var jsonobj = eval("("+tableData+")");
			//初始化试图组件
			uploaderSelf.view(tableData);
			//如果可以上传附件，就将UltraSWFUpload对象的属性设置到setting集合中
			if (uploaderSelf.uploadable==true)
				uploaderSelf.initUltraSWFUpload();
			//初始化完毕
			uploaderSelf.initComplete = true;
			//获得可下载列表
			if(uploaderSelf.downTableVisible==true)
				uploaderSelf.postInitUltraSWFUpload();
		});
	}
	else{
		//初始化试图组件
		uploaderSelf.view(null);
		//如果可以上传附件，就将UltraSWFUpload对象的属性设置到setting集合中
		if (uploaderSelf.uploadable==true)
			uploaderSelf.initUltraSWFUpload();
		//初始化完毕
		uploaderSelf.initComplete = true;
		//获得可下载列表
		if(uploaderSelf.downTableVisible==true)
			uploaderSelf.postInitUltraSWFUpload();
	}
}

/**
 *初始化组件试图
*/
UltraSWFUpload.prototype.view = function(jsonobj)
{
	var uploaderView = "";
	var uploaderContainer = document.getElementById(this.uploaderId + UltraSWFUpload.DIV_CONTENT);//放组件的div
	if (uploaderContainer == null) 
	{
		alert("没有定义存放组件的DIV块，初始化组件视图失败！");
		return;
	}
	/*--生成菜单栏_START--*/
	if((this.uploadable==false) && this.isMultiDownload==false)
	{
		uploaderView += "<div style='display:none;' id='" + this.uploaderId + UltraSWFUpload.DIV_UP_COMPONENT + "' class='choose_files'>\n";
	}
	else
	{
		uploaderView += "<div id='" + this.uploaderId + UltraSWFUpload.DIV_UP_COMPONENT + "' class='choose_files'>\n";
	}
	if(this.uploadable==true || this.isMultiDownload==true){
		uploaderView += "<table style='height:28'>"
		                  +"<tr>";
		if(this.typeDicCode!="" && this.uploadable==true){
			if(jsonobj!=null && jsonobj!=undefined){
				var len = jsonobj.length;
				uploaderView += "<td style='border:1px solid #7b8592;'><select id='"+this.uploaderId+"_attachTypeSel' onchange='";
				uploaderView += this.uploaderId+".resetUploadUrl(this.value)'><option value=''>- -附件类型- -</option>";
				this.attachTypeRule = new Array();//附件上传类型规则数组
				this.attachTypeName = new Array();//附件类型数组
				this.attachTypeCounter = new Array();//对应个附件类型上传附件的计数器
				for(var i=0;i<len;i++){
					uploaderView += "<option value='";
					uploaderView += i;
					uploaderView += "'>";
					uploaderView += jsonobj[i]["typeValue"];
					uploaderView += "</option>";
					this.attachTypeRule.push(jsonobj[i]["rule"]);
					this.attachTypeName.push(jsonobj[i]["typeValue"]);
					this.attachTypeCounter.push(0);
				}
				uploaderView += "</select></td>";
			}
		}
	}
	//选择文件按钮
	if(this.uploadable==true)
	{
		//uploaderView += "<td class='chooseupload_button5' onmouseover=\"this.className='chooseupload_button5_hover'\""
		//	            +" onmouseout=\"this.className='chooseupload_button5'\">";
		uploaderView += "<td class='button_selectfile' onmouseover=\"this.className='button_selectfile_hover'\""
			            +" onmouseout=\"this.className='button_selectfile'\">";
		uploaderView += "<span id='" + this.uploaderId + UltraSWFUpload.SPAN_BTN +"'></span>\n";
		uploaderView += "</td>"
	}
	//上传按钮
	if (this.uploadBtnable==true) 
	{
		/*
		uploaderView += "<td id='" + this.uploaderId + UltraSWFUpload.BTN_UPLOAD + "' \n"
				     + " onclick='"
				     + this.uploaderId
				     + ".startUploadFile();'\n"
				     + " class='edit_button5'"
				     + "\n onMouseOver='this.className=\"edit_button5_hover\"'\n"
				     + " onmouseout='this.className=\"edit_button5\"'>上传</td>\n";
		*/
		uploaderView += "<td id='" + this.uploaderId + UltraSWFUpload.BTN_UPLOAD + "' \n"
				     + " onclick='"
				     + this.uploaderId
				     + ".startUploadFile();'\n"
				     + " class='button_upload'"
				     + "\n onMouseOver='this.className=\"button_upload_hover\"'\n"
				     + " onmouseout='this.className=\"button_upload\"'>点击上传</td>\n";
	}
	//批量下载按钮
	if(this.isMultiDownload==true)
	{
		/*uploaderView += "<td id='" + this.uploaderId + UltraSWFUpload.BTN_BATCHDOWN + "' \n"
				     + " class='batchdown_button5'"
				     + " onClick='"+this.uploaderId
				     + ".batchDownload()'"
				     + "\n onMouseOver='this.className=\"batchdown_button5_hover\"'\n"
				     + " onmouseout='this.className=\"batchdown_button5\"'>批量下载</td>\n";
		*/
		uploaderView += "<td id='" + this.uploaderId + UltraSWFUpload.BTN_BATCHDOWN + "' \n"
				     + " class='button_battchdown'"
				     + " onClick='"+this.uploaderId
				     + ".batchDownload()'"
				     + "\n onMouseOver='this.className=\"button_battchdown_hover\"'\n"
				     + " onmouseout='this.className=\"button_battchdown\"'>批量下载</td>\n";
	}
	if(this.uploadable==true || this.isMultiDownload==true)
		uploaderView += "</tr></table>\n";
	uploaderView += "</div>\n";
	/*--生成菜单栏_END--*/
	/*--取消上传按钮_START_暂时没用这个按钮--*/
	uploaderView += "<input id='" + this.uploaderId + UltraSWFUpload.BTN_CANCEL_ALL
			     + "' type='hidden' value='取消全部'\n" + " onclick='" + this.uploaderId
			     + ".cancelUploadAll();' disabled='disabled'\n" + " class='"
			     + UltraSWFUpload.CSS_BTN_MOUSEOUT
			     + "' onMouseUp='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEUP + "\"'\n"
			     + " onmousedown='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEDOWN + "\"'\n"
			     + " onMouseOver='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEOVER + "\"'\n"
			     + " onmouseout='this.className=\""
			     + UltraSWFUpload.CSS_BTN_MOUSEOUT + "\"'/>\n";
	/*--取消上传按钮_END--*/
	//隐藏文本框，存储组ID
	uploaderView += "<input id='" + this.uploaderId
			     + UltraSWFUpload.INPUT_HIDDEN_ID + "' type='hidden' value='"
			     + this.getAttachmentGroupId() + "'\n" + " name='"
			     + UltraSWFUpload.INPUT_HIDDEN_NAME + "'/>\n";
	//进度条DIV
	uploaderView += "<div style='background:white;' id='" + this.uploaderId + UltraSWFUpload.DIV_UP_PROGRESS + "'></div>";
	//上传列表DIV
	uploaderView += "<div style='background:white;' id='" + this.uploaderId + UltraSWFUpload.DIV_UP_THUMBNAILS + "'>\n";
	uploaderView += "<table id='" + this.uploaderId + UltraSWFUpload.TABLE_UP + "' class='tableborder' ";
	uploaderView += " style='display:none' ";
	uploaderView += ">";
	uploaderView += "<tr><th style=\"font-weight:100;\">上传队列</th></tr>";
	uploaderView += "</table>\n";
	uploaderView += "</div>\n";
	//下载列表DIV
	uploaderView += "<div id='" + this.uploaderId
			     + UltraSWFUpload.DIV_DWN_THUMBNAILS + "'>\n";
	uploaderView += "<table id='" + this.uploaderId + UltraSWFUpload.TABLE_DWN + "' class='tableborder'>\n";
	if (this.getDownTableVisible()==true)
	{
		if(this.isMultiDownload==true)
		{
			uploaderView +="<tr>"
						    +"<th width='25'>"
						      +"<input id='"+this.uploaderId+"_checkAll' type='checkbox'"
						      +" onclick='UltraSWFUpload.checkAllAttachment(this,\""+this.uploaderName+"\");'/>"
						    +"</th>"
					        +"<th width='30' style=\"font-weight:100;\">序号</th>"
					        +"<th style=\"font-weight:100;\">文件名称</th>"
					        +"<th width='80' style=\"font-weight:100;\">上传人</th>"
					        +"<th width='120' style=\"font-weight:100;\">上传时间</th>"
					        //+"<th width='100' style=\"font-weight:100;\">附件大小</th>";
					        //+"<th width='150' style=\"font-weight:100;\">类型</th>";
		}
		else
		{
			uploaderView +="<tr>"
			                +"<th width='30' style=\"font-weight:100;\">序号</th>"
			                +"<th style=\"font-weight:100;\">文件名称</th>"
			                +"<th width='80' style=\"font-weight:100;\">上传人</th>"
			                +"<th width='120' style=\"font-weight:100;\">上传时间</th>"
			                //+"<th width='100' style=\"font-weight:100;\">附件大小</th>";
			                //+"<th width='100' style=\"font-weight:100;\">类型</th>";
		}
		uploaderView += "<th width='120' style=\"font-weight:100;\">操作</th>";
		uploaderView += "</tr>\n";
	}
	uploaderView += "</table>\n";
	uploaderView += "</div>\n";
	uploaderContainer.innerHTML += uploaderView;
	changeRow_color(this.uploaderId + UltraSWFUpload.TABLE_UP);
}

/* ******************************************************** */
/* 选择上传文件设置 */
/* ******************************************************** */
/**
 * 设置上传文件类型
 */
UltraSWFUpload.prototype.setFileTypes = function(types, description) {
	this.getUploader().setFileTypes(types, description);
};

/**
 * 设置上传文件大小
 */
UltraSWFUpload.prototype.setFileSizeLimit = function(fileSizeLimit) {
	this.getUploader().setFileSizeLimit(fileSizeLimit);
};

/**
 * 设置上传文件个数,0表示没限制
 */
UltraSWFUpload.prototype.setFileUploadLimit = function(fileUploadLimit) {
	this.getUploader().setFileUploadLimit(fileUploadLimit);
};

/**
 * 设置上传文件数组名称
 */
UltraSWFUpload.prototype.setFilePostName = function(filePostName) {
	this.getUploader().setFilePostName(filePostName);
};
/* ******************************************************** */
/* 选择上传文件的按钮 */
/* ******************************************************** */

/**
 * 设置选择上传文件按钮图片url 按钮图片中需要包括按钮的4个状态，从上到下依次是normal, hover, down/click, disabled.
 * 可以参照官方demo中的图片
 */
UltraSWFUpload.prototype.setButtonImageURL = function(btnImageUrl) {
	this.btnImageUrl = btnImageUrl;
	this.getUploader().setButtonImageURL(btnImageUrl);
};
/**
 * 设置选择上传文件按钮显示的内容,以html的形式,例如： '<span class="button">选择图片 <span
 * class="buttonSmall">(10 MB Max)</span></span>'
 */

UltraSWFUpload.prototype.setButtonText = function(html) {
	this.getUploader().setButtonText(html);
};
/**
 * 设置选择上传文件按钮显示的内容样式,例如： '.button { font-family: Helvetica, Arial, sans-serif;
 * font-size: 12pt; } .buttonSmall { font-size: 10pt; }'
 */

UltraSWFUpload.prototype.setButtonTextStyle = function(css) {
	this.getUploader().setButtonTextStyle(css);
};

UltraSWFUpload.prototype.setButtonTextPadding = function(left, top) {
	this.getUploader().setButtonTextPadding(left, top);
};

UltraSWFUpload.prototype.checkEditType = function(type)
{
	var len = this.editFileType.length;
	var result = false;
	for(var i=0;i<len;i++){
		if(this.editFileType[i]==type){
			result = true;
			break;
		}
	}
	return result;
}
/* ******************** */
/* 静态常量 */
/* ******************** */
UltraSWFUpload.instances = {};
UltraSWFUpload.movieCount = 0;
// div
UltraSWFUpload.DIV_CONTENT = "UploaderContainer";
UltraSWFUpload.DIV_UP_COMPONENT = "UploaderComponent";
UltraSWFUpload.DIV_DWN_THUMBNAILS = "DownloadThumbnails";
UltraSWFUpload.DIV_UP_THUMBNAILS = "Uploadthumbnails";
UltraSWFUpload.DIV_UP_OVER = "OverUploadDiv";
UltraSWFUpload.DIV_UP_PROGRESS = "UploadProgressContainer";// 进度条div
//from
UltraSWFUpload.SWFUPLOAD_FORM ="_SWFUploaderForm";
// span
UltraSWFUpload.SPAN_BTN = "SpanButtonPlaceholder";//
// button
UltraSWFUpload.BTN_UPLOAD = "btnUpload";
UltraSWFUpload.BTN_BATCHDOWN = "btnBatchDown";
UltraSWFUpload.BTN_CANCEL_ALL = "btnCancel";
// table
UltraSWFUpload.TABLE_DWN = "DownloadTable";
UltraSWFUpload.TABLE_UP = "UploadTable";
UltraSWFUpload.TABLE_UP_INIT = "Init";
UltraSWFUpload.TABLE_OVER = "UpOverTable";
UltraSWFUpload.TABLE_OVER_TD = "UpOverTableTD";
UltraSWFUpload.TABLE_OVER_FINISH = "UpOverTableFinish";
// hidden input
UltraSWFUpload.INPUT_HIDDEN_ID = "AttachmentGroupId";
UltraSWFUpload.INPUT_HIDDEN_NAME = "AttachmentGroupIdName";
// css
UltraSWFUpload.CSS_TABLE = "ultra_upload_table";
UltraSWFUpload.CSS_COMPONENT = "ultra_upload_component";
UltraSWFUpload.CSS_BTN_MOUSEOUT = "ultra_btn_mouseout";
UltraSWFUpload.CSS_BTN_MOUSEOVER = "ultra_btn_mouseover";
UltraSWFUpload.CSS_BTN_MOUSEDOWN = "ultra_btn_mousedown";
UltraSWFUpload.CSS_BTN_MOUSEUP = "ultra_btn_mouseup";
