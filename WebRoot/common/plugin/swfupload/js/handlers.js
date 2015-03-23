function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("你试图添加多个文件.\n"
					+ (message === "0" ? "已经到了上传限制."
							: "你只可以选 "
									+ (message > 1 ? "上传 " + message + " 文件."
											: "一个文件.")));
			return;
		}

		var progress = new FileProgress(file, this.customSettings.upload_target);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("超过了上传文件大小.");
			this.debug("Error Code: File too big, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("不能上传(0)字节文件.");
			this.debug("Error Code: Zero byte file, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("无效的文件类型.");
			this.debug("Error Code: Invalid File Type, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			alert("你已经选了多个文件.  "
					+ (message > 1 ? "你只能添加 " + message + " 文件" : "你不能再添加文件了."));
			break;
		default:
			if (file !== null) {
				progress.setStatus("未处理错误");
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * 当文件选择对话框关闭消失时，如果选择的文件成功加入上传队列， 那么针对每个成功加入的文件都会触发一次该事件（N个文件成功加入队列，就触发N次此事件）。
 * 
 * @param {}
 *            file id : string, // SWFUpload控制的文件的id,通过指定该id可启动此文件的上传、退出上传等
 *            index : number, // 文件在选定文件队列（包括出错、退出、排队的文件）中的索引，getFile可使用此索引 name :
 *            string, // 文件名，不包括文件的路径。 size : number, // 文件字节数 type : string, //
 *            客户端操作系统设置的文件类型 creationdate : Date, // 文件的创建时间 modificationdate :
 *            Date, // 文件的最后修改时间 filestatus : number //
 *            文件的当前状态，对应的状态代码可查看SWFUpload.FILE_STATUS }
 */
function fileQueued(file) {
	addReadyFileInfo(file.id, file.name,
			this.customSettings.fileUploadTable,
			this.customSettings.currentSWFUpload,
			this.customSettings.currentBtnCancel,
			this.customSettings.imagePath,
			this.customSettings.uploadTableVisible);
}
function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesQueued > 0) {
			if (true == this.customSettings.isAutoUpload
					|| "true" == this.customSettings.isAutoUpload) {
				if(eval('('+this.customSettings.currentSWFUpload+'.typeDicCode)')==""){
					this.startUpload();
				}
				else{
					var typeValue = document.getElementById(eval('('+this.customSettings.currentSWFUpload+'.uploaderId)')+"_attachTypeSel").value;
					if(typeValue==""){
						//取消所有队列中的附件
						eval('('+this.customSettings.currentSWFUpload+'.cancelUploadAll())');
						alert("请选择附件类型！")
					}else{
						this.startUpload();
					}
				}
			}
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadProgress(file, bytesLoaded) {
	try {
		if (true == this.customSettings.progressIsVisible
				|| "true" == this.customSettings.progressIsVisible) {
			var percent = Math.ceil((bytesLoaded / file.size) * 100);
			var progress = new FileProgress(file,
					this.customSettings.upload_target);
			progress.setProgress(percent);
			if (percent === 100) {
				progress.setStatus("");// 正在创建缩略图...
				progress.toggleCancel(false, this);
			} else {
				progress.setStatus("正在上传...");
				progress.toggleCancel(true, this);
			}
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		if (true == this.customSettings.progressIsVisible
				|| "true" == this.customSettings.progressIsVisible) {
			var progress = new FileProgress(file,
					this.customSettings.upload_target);
		}
		addFileInfo(file.id,this.customSettings.imagePath);
		var serverDataStr = "serverData=" + serverData + ";"
		var aus = serverDataStr + this.customSettings.afterUploadSuccess
				+ "( serverData )";
		var auc = serverDataStr + this.customSettings.afterUploadComplete
				+ "( serverData )";
		//var aus = this.customSettings.afterUploadSuccess + "( serverData )";
		//var auc = this.customSettings.afterUploadComplete + "( serverData )";
		eval(aus);
		/*
		 * I want the next upload to continue automatically so I'll call
		 * startUpload here
		 */
		if (this.getStats().files_queued === 0) {
			eval(auc);
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function addFileInfo(fileId,imagePath) {
	var row = document.getElementById(fileId);
	row.cells[0].childNodes[1].innerHTML = "<span ><img src='"+imagePath+"images/upload/02.png' border='0'/></span>";
}
function addReadyFileInfo(fileid, fileName, fileTable, swfupload, btnCancel,imagePath,uploadTableVisible) {
	// 用表格显示
	var infoTable = document.getElementById(fileTable); 
	if(uploadTableVisible==true)
		infoTable.style.display = "block";
	var initTableRow = document.getElementById(fileTable + "Init");
	var row = infoTable.insertRow(-1);
	row.style.cssText = "height:21px;border-bottom:#CC6633;";
	row.id = fileid;
	var col1 = row.insertCell(-1);
	col1.innerHTML = "<span>"+fileName+"&nbsp;&nbsp;</span>";
	col1.innerHTML += "<span><a class='finish_overload1' href=\'javascript:deleteFile(\""
			+ fileid
			+ "\",\""
			+ fileTable
			+ "\",\""
			+ swfupload
			+ "\",\""
			+ btnCancel + "\")\'><img src='"+imagePath+"images/upload/del.png' border='0'/></a></span>";
	changeRow_color(fileTable);
}

function deleteFile(fileId, fileTable, swfupload, btnCancel) {
	// 用表格显示
	var infoTable = document.getElementById(fileTable);
	var row = document.getElementById(fileId);
	var rowNum = infoTable.rows.length;
	infoTable.deleteRow(row.rowIndex);
	this[swfupload].cancelUpload(fileId, false);
	
	if (rowNum == 2) {
		var btn_cancel = document.getElementById(btnCancel);
		btn_cancel.disabled = "disabled";
		infoTable.style.display = "none";
	}
	changeRow_color(fileTable);
}

function uploadComplete(file) {
	try {
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			if (true == this.customSettings.progressIsVisible
					|| "true" == this.customSettings.progressIsVisible) {
				var progress = new FileProgress(file,
						this.customSettings.upload_target);
				progress.setComplete();
				progress.setStatus("<font color='red'>所有文件上传完毕!</b></font>");
				progress.toggleCancel(false);
			}
			var uploadTbViewStr = this.customSettings.fileUploadTable;
			if(this.customSettings.uploadTableVisible==true){
				window.setTimeout(function(){
					$("#"+uploadTbViewStr).fadeOut("slow");
				},5000);
				window.setTimeout(function(){
					var uploadTb = document.getElementById(uploadTbViewStr);
					for(var i=uploadTb.rows.length-1;i>0;i--)
					{
						uploadTb.deleteRow(i);
					}
				},5000);
			}
			else{
				var uploadTb = document.getElementById(uploadTbViewStr);
				for(var i=uploadTb.rows.length-1;i>0;i--)
				{
					uploadTb.deleteRow(i);
				}
			}
			// 更新下载列表
			if (this.customSettings.isUpdateDownloadTable) {
				eval(this.customSettings.getUpdateDownTable);
			}
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		if (true == this.customSettings.progressIsVisible
				|| "true" == this.customSettings.progressIsVisible) {
			var progress = new FileProgress(file,
					this.customSettings.upload_target);
			progress.setError();
			progress.toggleCancel(false);
		}

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("文件上传出错: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name
					+ ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
			progress.setStatus("文件上传配置错误.");
			this.debug("Error Code: No backend file, File name: " + file.name
					+ ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("文件上传失败.");
			this.debug("Error Code: Upload Failed, File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("服务器 (IO) 异常");
			this.debug("Error Code: IO Error, File name: " + file.name
					+ ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("安全错误!");
			this.debug("Error Code: Security Error, File name: " + file.name
					+ ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("超出上传文件大小.");
			this.debug("Error Code: Upload Limit Exceeded, File name: "
					+ file.name + ", File size: " + file.size + ", Message: "
					+ message);
			break;
		case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
			progress.setStatus("文件不存在.");
			this.debug("Error Code: The file was not found, File name: "
					+ file.name + ", File size: " + file.size + ", Message: "
					+ message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("校验失败.");
			this.debug("Error Code: File Validation Failed, File name: "
					+ file.name + ", File size: " + file.size + ", Message: "
					+ message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			progress.setStatus("Cancelled");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("Stopped");
			break;
		default:
			progress.setStatus("错误码(此错误没处理): " + error_code);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name
					+ ", File size: " + file.size + ", Message: " + message);
			break;
		}
		addFileInfo(file.id, imageName,this.customSettings.imagePath);
	} catch (ex) {
		this.debug(ex);
	}

}

function addImage(src) {
	var newImg = document.createElement("img");
	newImg.style.margin = "5px";

	document.getElementById("thumbnails").appendChild(newImg);
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error. This
			// will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function() {
		fadeIn(newImg, 0);
	};
	newImg.src = src;
}

/*******************************************************************************
 * FileProgress Object Control object for displaying file info
 * ******************************************
 */

function FileProgress(file, targetID) {
	this.fileProgressID = targetID + "divFileProgress";

	this.opacity = 100;
	this.height = 0;
	this.progressTarget = document.getElementById(targetID);

	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName";
		progressText.appendChild(document.createTextNode("上传文件: " + file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		this.progressTarget.height = this.fileProgressWrapper.offsetHeight;
		this.progressTarget.style.height = "75px";
		this.progressTarget.appendChild(this.fileProgressWrapper);

		// fadeIn(progressTarget, this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = "上传文件: "
				+ file.name;
		this.reset();
	}

	this.height = this.fileProgressWrapper.offsetHeight;
	this.setTimer(null);
}
FileProgress.prototype.setProgress = function(percentage) {
	this.fileProgressElement.className = "progressContainer green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";

	this.appear();
};
FileProgress.prototype.setComplete = function() {
	this.fileProgressElement.className = "progressContainer blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	this.fileProgressElement.childNodes[3].style.width = "";
	this.fileProgressElement.childNodes[1].firstChild.nodeValue = "";

	var oSelf = this;
	this.setTimer(setTimeout(function() {
		oSelf.disappear();
	}, 1000));
};
FileProgress.prototype.setError = function() {
	this.fileProgressElement.className = "progressContainer red";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

	var oSelf = this;
	this.setTimer(setTimeout(function() {
		oSelf.disappear();
	}, 5000));
};
FileProgress.prototype.setCancelled = function() {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

	var oSelf = this;
	this.setTimer(setTimeout(function() {
		oSelf.disappear();
	}, 2000));
};
FileProgress.prototype.setStatus = function(status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};

FileProgress.prototype.setTimer = function(timer) {
	this.fileProgressElement["FP_TIMER"] = timer;
};
FileProgress.prototype.getTimer = function(timer) {
	return this.fileProgressElement["FP_TIMER"] || null;
};
FileProgress.prototype.reset = function() {
	this.progressTarget.style.height = "75px";
	this.progressTarget.style.display = "";

	this.fileProgressElement.className = "progressContainer";

	this.fileProgressElement.childNodes[2].innerHTML = "&nbsp;";
	this.fileProgressElement.childNodes[2].className = "progressBarStatus";

	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].style.width = "0%";

	this.appear();
};
FileProgress.prototype.appear = function() {
	if (this.getTimer() !== null) {
		clearTimeout(this.getTimer());
		this.setTimer(null);
	}

	if (this.fileProgressWrapper.filters) {
		try {
			this.fileProgressWrapper.filters
					.item("DXImageTransform.Microsoft.Alpha").opacity = 100;
		} catch (e) {
			// If it is not set initially, the browser will throw an error. This
			// will set it if it is not set yet.
			this.fileProgressWrapper.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity=100)";
		}
	} else {
		this.fileProgressWrapper.style.opacity = 1;
	}

	this.fileProgressWrapper.style.height = "";

	this.height = this.fileProgressWrapper.offsetHeight;
	this.opacity = 100;
	this.fileProgressWrapper.style.display = "";

};
// Fades out and clips away the FileProgress box.
FileProgress.prototype.disappear = function() {
	var reduceOpacityBy = 15;
	var reduceHeightBy = 4;
	var rate = 30; // 15 fps

	if (this.opacity > 0) {
		this.opacity -= reduceOpacityBy;
		if (this.opacity < 0) {
			this.opacity = 0;
		}

		if (this.fileProgressWrapper.filters) {
			try {
				this.fileProgressWrapper.filters
						.item("DXImageTransform.Microsoft.Alpha").opacity = this.opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.
				// This will set it if it is not set yet.
				this.fileProgressWrapper.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity="
						+ this.opacity + ")";
			}
		} else {
			this.fileProgressWrapper.style.opacity = this.opacity / 100;
		}
	}

	if (this.height > 0) {
		this.height -= reduceHeightBy;
		if (this.height < 0) {
			this.progressTarget.style.height = "0px";
			this.height = 0;
		}

		this.fileProgressWrapper.style.height = this.height + "px";
	}

	if (this.height > 0 || this.opacity > 0) {
		var oSelf = this;
		this.setTimer(setTimeout(function() {
			oSelf.disappear();
		}, rate));
	} else {
		this.fileProgressWrapper.style.display = "none";
		this.progressTarget.style.height = "0px";
		this.progressTarget.style.display = "none";
		this.setTimer(null);
	}
};

FileProgress.prototype.toggleCancel = function(show, swfuploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible"
			: "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function() {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
