<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script language="javascript">
		window.onload = function() 
		{
			document.getElementById("kmform").submit();
		}
</script>
</head>
<form id="kmform" name="kmform" method="post" action="http://${kmUrl}/${kmProject}/kmAddExperie.do?method=addkm" >
	 <input type="hidden" id="kmapptype_name" name="kmapptype_name" value="EOMS"/>
	 <input type="hidden" id="isFormat" name="isFormat" value="no"/>
	 <input type="hidden" id="username" name="username" value="${username}"/>
	 <input type="hidden" id="caption" name="caption" value="${kmcaption}"/>
	 <input type="hidden" id="keyword" name="keyword" value="${kmkeyword}"/>
	 <input type="hidden" id="content" name="content" value="${kmcontent}"/>
	 <!-- 按照自定义模版显示参数 begin -->
   	 <input type = "hidden" name = "creatorPartReplace" id = "creatorPartReplace" value = "辅助运维产品部" /> 
   	 <input type = "hidden" name = "prepareShowReplace" id = "prepareShowReplace" value = "100%" />
   	 <!-- end --> 
</form>
</html>