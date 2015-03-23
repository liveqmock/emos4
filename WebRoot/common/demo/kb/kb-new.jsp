<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/core/jspbase.jsp"%>
<!doctype html>
<html>
<head>
<%@ include file="/common/core/meta.jsp"%>
<title>Knowledge Base Demo</title>
<%@ include file="/common/core/cssbase.jsp"%>
<style>
html, body { overflow:hidden;}
body { background:#f5fffa}
.w90p #header,
.w90p #content,
.w90p #footer { width: 90%;}
#header { padding:10px}
#header h2 { font-size:14px; color:#555;font-weight:bold;}
.formtable{ width:100%;}
.formtable td { padding: 6px 6px;}
td.label { width:10%;vertical-align:top;text-align:right}
td.field { width:40%;vertical-align:top;}
input.text,
textarea.textarea{
	border:1px solid #B5B8C8;
	padding:2px 3px 0 3px;
	height:22px;
	line-height:18px;
	vertical-align:middle;
    color: #333;
    width:99%
}
td.sub input { padding-right:0}

textarea.textarea { height:100px}
</style>
<%@ include file="/common/core/jsbase.jsp"%>
</head>
<body class="w90p">
<div id="header"><h2>创建一条知识</h2></div>
<div id="content">
	<div class="col-main">
		<div class="main-wrap">
			<form>
				<table class="formtable">
					<tr>
						<td class="label">知识标题</td>
						<td class="field" colspan="3"><input class="text"></input></td>
					</tr>
					<tr>
						<td class="label">关键字</td>
						<td class="field"><input class="text"></input></td>
						<td class="label">知识类型</td>
						<td class="field sub"><input class="text"></input></td>
					</tr>
					<tr>
						<td class="label">关键字</td>
						<td class="field"><input class="text"></input></td>
						<td class="label">知识类型</td>
						<td class="field sub"><input class="text"></input></td>
					</tr>
					<tr>
						<td class="label">知识专业</td>
						<td  class="field" colspan="3"><input class="text"></input></td>
					</tr>
					<tr>
						<td class="label">摘要</td>
						<td  class="field" colspan="3"><textarea class="textarea"></textarea></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<div id="footer"></div>
</body>
</html>