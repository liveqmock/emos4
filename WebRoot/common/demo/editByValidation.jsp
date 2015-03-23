<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>页面验证调试</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	
	<body>
		<div>
			<form action="${ctx}/demo/addTest.action" name="addStore"
				onsubmit="return Validator.Validate(this,1);">
				<table class="serachdivTable">
					<tr>
						<td class="serachdivTableTd"> 
							序号： 
						</td>
						<td>
						 <input type="text" name="id" class="textInput" />
						  <validation id="idV" require="true" dataType="Number"
								msg="序号必须是数字" />  
						</td>
					</tr>
					<tr>
						<td class="serachdivTableTd">
							英文名称：
						</td>
						<td>
							<input type="text" name="enname" class="textInput" />
							<validation id="ennameV" dataType="English" msg="<eoms:lable name="sm_lb_department"/>" />
						</td>
					</tr>
					<tr>
						<td class="serachdivTableTd">
							中文名称：
						</td>
						<td>
							<input type="text" name="zhname" class="textInput" />
							<validation id="zhnameV" require="true" dataType="Chinese"
								msg="中文名称必须是中文" />
						</td>
					</tr>
					<tr>
						<td class="serachdivTableTd">
							年龄：
						</td>
						<td>
							<input type="text" name="age" class="textInput" />
							<validation id="ageV" require="true" dataType="Number"
								msg="年龄必须是中文" />
						</td>
					</tr>
					<tr>
						<td class="serachdivTableTd">
							E-mail：
						</td>
						<td>
							<input type="text" name="email" class="textInput" />
							<validation id="emailV" require="true" dataType="Email"
								msg="邮件地址有误" />
						</td>
					</tr>
					<tr>
						<td class="serachdivTableTd">
							住址：
						</td>
						<td>
							<input type="text" name="place" class="textInput" />
							<validation id="placeV" require="true" dataType="Require"
								msg="住址不能为空" />
						</td>
					</tr>
					<tr>
					<td align="center">
						<input type="submit" name="button" id="button" value="添加"
							class="searchButton"
							onmouseover="this.className='searchButton_hover'"
							onmouseout="this.className='searchButton'" />
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
