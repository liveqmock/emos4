<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="../../common/style/blue/css/portal.css" />
<script src="../../javascript/main.js"></script>
<script language="javascript">
	//打开值班平台详细信息
	function openDutyPortal(organizationId){
		window.open('${ctx}/ultraduty/portal/dutyPortal.action?organizationId='+organizationId,organizationId);
	}
</script>
<title>information_topic</title>
</head>
<body>
<div class="title_right">
      <div class="title_left">
        <span class="title_bg">
          <span class="title_icon_infosubject">值班信息</span>
        </span>
        <span class="title_xieline"></span>
        <span class="title_more"></span>
      </div>
</div>
		<table class="info_content_table" style="height:200px;">
		  <tr>
		    <td class="Info" valign="top">
			    <div class="Infocontent">
			      <c:forEach var="calBean" items="${calBeanList}" begin="1" end="5" step="1">  
			        <li><a href="javascript:void(0)" onclick="openDutyPortal('${calBean.organizationId }')">${calBean.organizationName}(${calBean.shiftName })&nbsp;&nbsp;&nbsp;${calBean.userNames }</a></li><br/>
			      </c:forEach>
			    </div>
		    </td>
		  </tr>
		</table>
	</body>
</html>