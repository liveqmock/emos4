<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<title>工单派发人</title>
<script type="text/javascript">
</script>
<link href="${ctx}/common/style/${skintype}/css/newgongdan.css"  rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 派发字符串，派发人中文名字符串，移交人中文名字符串，抄送人中文名字符串，动作标识，下一环节号，单选或多选 -->
<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" style="width: 100%;height: 100%">
  <tr align="right">
    <td width="10%" align="right">派发人_code:</td>
    <td width="40%"><textarea rows="3" style="width: 100%" id='650042009' >U@:zhangsan@:ASSIGN@:2@:1274976000@:1274966000@:1274976000@:dp_3@:这里写派发说明。个性化派发说明@;U@:dulylau@:ASSIGN@:2@:1274976000@:1274966000@:1274976000@:dp_3@:这里写派发说明。个性化派发说明@;</textarea></td>
    <td width="10%" align="right">派发人_name:</td>
    <td width="40%"><textarea rows="3" style="width: 100%" id='650042006' >张三:dulylau</textarea></td>
  </tr>
  <tr>
    <td align="right">移交人_name:</td>
    <td><textarea rows="3" style="width: 100%" id='650042007' ></textarea></td>
    <td align="right">抄送人_name:</td>
    <td><textarea rows="3" style="width: 100%" id='650042008' ></textarea></td>
  </tr>
  <tr>
    <td align="right">动作:</td>
    <td><textarea rows="3" style="width: 100%" id='650042003' >ASSIGN</textarea></td>
    <td align="right">下一环节：</td>
    <td><textarea rows="3" style="width: 100%" id='650042004' >3</textarea></td>
  </tr>
  <tr>
    <td align="right">单选或多选：</td>
    <td><textarea rows="3" style="width: 100%" id='650042005' >0</textarea></td>
    <td><a
						href='http://192.168.20.139:8010/arsys/forms/workflow/WF%3AApp_Base_SelectDealProcess/'
						target="_self"> EOMS </a></td>
    <td><input type="button" value="派发人"
						onclick="window.open('${ctx}/workflow/actor/dealActor.jsp','_blank','height=700,width=1100,top='+(window.screen.height/2-200)+',left='+(window.screen.width/2-250)+',toolbar=no,status=no,location=no,directories=no,resizable=yes');" /></td>
  </tr>
  <tr>
    <iframe src="${ctx}/workflow/actor/dealActor.jsp"  frameborder="0" id="center" name="center" style="width: 100%;height: 300px"></iframe>
  </tr>
</table>
</body>
</html>
