<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/ajaxupload/ajaxupload.jsp"%>
<html>
	<head>
		<link href="${ctx}/css/gdstyle.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/css/css.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
		<script type="text/javascript"
			src="<c:url value='/plugin/ajaxupload/upload.js'/>"></script>
	    <script type="text/javascript" src="${ctx}/plugin/validator/js/mootools-1.2-core.js"></script>
			
		<script language="JavaScript">
        
        function removelist(ids, id,tagId) {
          if(!confirm('确定删除此附件吗？')){
			return;
		  }
	      var par = "ids=" + ids + "&fileId=" + id + "&tagId=" + tagId;
	      var div_u = document.parentWindow.parent.document.getElementById("upload_div_"+tagId);
	      
          jQuery.post("${ctx}/AccessoriesRemove.action",   
		            {
		                ids : ids,
		                fileId : id,
		                tagId : tagId
		            },
		            function(responseText) { 
		              div_u.innerHTML = responseText;
		            }
		  ); 
        }
        
		function uploadlist(ids, tagId) {
			var par = "ids=" + ids + "&tagId=" + tagId;
			var div_u = document.parentWindow.parent.document.getElementById("upload_div_"+tagId);   
			var viewFlag = document.getElementById('viewflag').value;
			 jQuery.post("${ctx}/AccessoriesList.action",   
		            {
		                viewflag : viewFlag,
		                ids : ids,
		                tagId : tagId
		            },
		            function(responseText) { 
		              div_u.innerHTML = responseText;
		            }
		       ); 
			
			eval("document.parentWindow.parent.document.getElementById('"+tagId+"').value='" + ids + "';");
		}
        			
		function ajaxlist() {
	    var ids = '<s:property value="%{ids}" />';
	    var tagId = '<s:property value="%{tagId}" />';
	    if(getNums())
	    uploadlist(ids, tagId);
	    
	    
	    <s:if test="hasFieldErrors()">    
        <s:iterator value="fieldErrors">    
        <s:iterator value="value">    
             alert('<s:property/>');    
     	</s:iterator>
		</s:iterator>
		</s:if>
	      
        }	
			
        function getNums(flag){
    	if(flag=="-1"){
    		$("att_nums").value=$("att_nums").value-1;
    	}else{
    		$("att_nums").value="<s:property value="%{accessoriesList.size()}" />";
    		if($("att_nums").value=='0' || $("att_nums").value=='')
    		 return false;
    	}
    	return true;
    }
       
       function hideDiv(id){
       document.getElementById(id).style.display='none';
       }
       
       
       function checkFileName(){
       var file = document.getElementById('uploadFile').value;
       var match = document.getElementById('match').value;
       
        if(file.match(match)){
        hideDiv('fileBar');
        startProgress('uploadFile','save');
        return true;
        } else{
        alert('<s:text name="file.match" ></s:text>');
        return false;
        }      
       }
       
     </script>
	</head>
	<body onload="ajaxlist()"
		style="margin-top: 1px; background-color: transparent">
		<s:form action="AccessoriesUpload" name="AccessoriesForm"
			method="POST" enctype="multipart/form-data" theme="simple"
			onsubmit="return checkFileName()" cssClass="Attachment">
			<table width="100%" align="left" border=0 cellpadding=0 cellspacing=0>
				<tr>
					<td>
						<div id="progressBar" style="display: none;">
							<div id="theMeter">
								<div id="progressBarBox">
									<div id="progressBarBoxContent"></div>
								</div>
							</div>
						</div>
						<div id="fileBar">
						<s:file cssClass="file" name="upload" label="file upload" ContentEditable="false"
							id="uploadFile" cssStyle="width:500px;"/>
						<s:submit value="上传" id="save" targets="filelist"
							cssClass="button"/>
						<s:text name="file.size">
							<s:param name="value" value="maxSize" />
						</s:text>
						</div>
					</td>
				<tr>
			</table>
			<s:hidden name="match" id="match"/>
			<s:hidden name="path"/>
			<s:hidden name="att_nums" value="0" id="att_nums" />
			<s:hidden name="ids" value="%{ids}" id="ids" />
			<s:hidden name="tagName" value="%{tagName}" id="tagName" />
			<s:hidden name="tagId" value="%{tagId}" id="tagId" />
			<s:hidden name="viewflag"></s:hidden>
		</s:form>
	</body>
</html>

