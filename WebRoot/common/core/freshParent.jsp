<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <%@ include file="/common/core/taglibs.jsp"%>
    <title>The bridge page for refreshing parent</title>
	<script language="javascript">
		var returnMessage = '${returnMessage}';
		var parafresh = '${parafresh}';
		alert(returnMessage);
		if(window.dialogArguments!=undefined)
		{//刷新父窗口--window.showModalDialog()
			var src_url = window.dialogArguments.location.href;
			var dynamic_url = '';
			if(src_url.lastIndexOf('?')==-1)
			{
				dynamic_url = src_url + "?time_refresh="+(new Date()).getTime();
			}
			else
			{
				dynamic_url = src_url + "&time_refresh="+(new Date()).getTime();
			}
			if(parafresh == 'true'){
			   window.dialogArguments.location = dynamic_url;
			   window.dialogArguments.location = src_url;
			   window.close();
			}else if(parafresh == 'false'){
			   window.close();
			}
		}
		else
		{//刷新父窗口--window.open()
			if(parafresh == 'true'){
			try{
				window.opener.location.reload();
				window.close();
			}
			catch(e)
			{
				window.close();
		    }
			}else if(parafresh == 'false'){
			   window.close();
			}
		}
		
   </script>
   </head>
</html>