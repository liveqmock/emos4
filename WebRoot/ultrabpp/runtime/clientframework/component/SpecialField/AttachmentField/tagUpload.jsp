<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/plugin/swfupload/import.jsp"%>
<html>
<head>
	<title>上传下载组件例子</title>
</head>
<body>
<%--
	以下是常用属性的解释，请选择使用。
	id:该上传组件的ID，可用通过id.startUploadFile()实现自己的上传按钮，必须指定；
	uploadAction：上传请求的URL；
	queryAction:附件列表请求的URL；
	downloadAction：附件下载的请求URL；
	deleteAction：附件删除的URL；
	uploadDestination：文件存储位置；
	attchmentGroupId:附件关系ID，具有相同关系ID的附件作为一组显示到下载列表中，如果不指定，则由系统生成uuid；
	uploadBtnIsVisible:系统上传下载按钮是否可见，如果您自定义了上传按钮，可以将系统上传按钮隐藏(true|false)，默认为true；
	downTableVisible:是否显示现在列表(true|false)，默认为true；
	uploadable：是否可以上传(true|false)，默认为true；
	isMultiUpload：是否可以批量上传（true|false），默认为true；
	isMultiDownload:是否可以批量下载(true|false)，默认为true；
	isAutoUpload:是否自动上传附件(true|false)，默认为false；
	uploadTableVisible:是否显示上传列表（true|false）,默认为true；
	progressIsVisible:上传进度条是否可见(true|false)，默认为true；
	createDirByDate：是否根据年份_月份在指定存储路径下创建附件存储子路径(true|false)，默认为false，如果需要按天，您可以自定义动态存储路径来实现；
	attachmentUser：指定附件负责人；
	fileTypes:上传文件类型，如"*.jpg;*.png"或者"*.*"，默认为"*.*"；
	fileTypesDescription：文件类型描述，在选择文件的时候显示在对话框中，默认为“project reference”；
	returnValue：每个附件上传成功后，自定义从服务器端的返回信息，，无默认值；	//返回值格式：1-文件名；2-文件真实名；3-存储路径；4-文件大小；5-文件类型；6-关系ID；
																//比如您要返回文件名、存储路径、文件大小，您应该在标签中添加属性为returnValue="1,3,4"
																//返回值也将以这个顺序返回，如"卡农.mp3,D:\music\,3072KB"
	delSelfOnly：是否只能删除自己上传的附件，默认值为false；前提是要有附件的删除权限；
	operationParams：对列表中附件的操作权限参数（权限顺序为：编辑,删除,下载 1表示有此权限，0表示无此权限 例如：1,0,1表示可编辑,不可删除,可下载，默认为不可编辑，可删除，可下载）
					由于普通附件没有与在线编辑进行结合，所以编辑权限务必设置为0；
	@Deprecated
	downable：附件是否可以下载（不推荐使用该属性，可能会在后期去掉，推荐使用operationParams进行替代）；
	@Deprecated
	isEdit：附件是否可以删除（不推荐使用该属性，可能会在后期去掉，推荐使用operationParams进行替代）；
--%>
<atta:fileupload 
	id="myfileupload" 
	uploadDestination="rcl_test/test2"
	uploadable="true" 
	downTableVisible="true" 
	isMultiDownload="true" 
	isMultiUpload="true"
	uploadBtnIsVisible="true" 
	uploadTableVisible="true"
	isAutoUpload="false"
	createDirByDate="false" 
	fileTypes="*.*" 
	progressIsVisible="true"
	attchmentGroupId="rcl-02-upload" 
	returnValue="1,2,3,4,5"
	delSelfOnly="true"
	>
</atta:fileupload>
<input type="button" value="自定义上传按钮" onclick="submitfrm();" />
<script>
	function submitfrm()
	{
		//手动调用附件上传，myfileupload是标签中的id属性值
		myfileupload.startUploadFile();
	}
	myfileupload.afterUploadSuccess = function(serverData)
	{
		//上传成功后调用的函数，如果上传队列中有5个附件，那么该函数将会回调5次
		//alert(serverData);
	}
	myfileupload.afterUploadComplete = function( serverData )
	{
		//上传完成后调用的函数，不管上传队列中有多少个附件，该函数只会在所有附件上传完毕后回调1次
		//alert("afterUploadComplete-------------"+serverData);
	}
</script>
</body>
</html>
