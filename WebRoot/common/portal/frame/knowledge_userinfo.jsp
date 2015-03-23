<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx }/common/style/blue/css/portal.css" />
<title>personal_information</title>
</head>
<body>
<div style="height:100%">

<div class="title_right">
      <div class="title_left">
        <span class="title_bg">
          <span class="title_icon_user">个人中心</span>
        </span>
        <span class="title_xieline"></span>
      </div>
</div>
<table  class="userinfo_content_table">
  <tr>
    <td width="20%" height="191"><img src="${ctx}/common/userimage/${userSession.image}" style="width:120px;height:130px"/></td>
    <td><div class="user_info">
    <div>
      <li>名称</li>
      <li>公司.部门</li>
      <li>排名：……</li></div>
      <div class="user_info_button">

      </div>
    </div></td>
  </tr>
</table>
</div>
</body>
</html>