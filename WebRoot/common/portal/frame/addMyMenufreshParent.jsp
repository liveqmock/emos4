<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    <%@ include file="/common/core/taglibs.jsp"%>
    <title>刷新父窗口中转页面</title>
	<script language="javascript">
		var returnMessage = '${returnMessage}';
		var parafresh = '${parafresh}';
		if(parafresh == 'true'){
		  if(window.parent.opener){
		     alert(returnMessage);
		     //window.opener.location.reload();
		     window.close();
	      }
		}else if(parafresh == 'false'){
		   alert(returnMessage);
		   window.close();
		}
   </script>
   </head>
</html>