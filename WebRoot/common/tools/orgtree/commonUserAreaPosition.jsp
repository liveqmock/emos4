<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/header_list.jsp"%>
		<script type="text/javascript" src="${ctx}/common/tools/orgtree/js/commonTree.js"></script>
		<title>人员区域选择</title>
		<script language="javascript">
			var isRadio = '${isRadio}';
			window.onresize = function()
			{
				setCenter(0,0);
			}
			window.onload = function()
			{
				setCenter(0,0);
				initUserDataPosition('${commonUserData}', '${selectId}');
			}
			
			function setResultArea(sourceObj, posid, userid, username)
			{
				if(sourceObj.checked)
				{
					bottomFrame.contentWindow.addOneResult(userid, username, 'U');
					leftFrame.contentWindow.setSelect(userid, 'U');
					setSelect(userid);
				}
				else
				{
					bottomFrame.contentWindow.delOneResult(userid);
					leftFrame.contentWindow.cancelSelect(userid, 'U');
					cancelSelect(userid);
				}
			}
			
			function setSelect(userid)
			{
				var check = document.getElementsByName(userid);
				if(check != null)
				{
					for(var i=0 ; i<check.length ; i++)
					{
						check[i].checked = 'checked';
					}
				}
			}
			
			function cancelSelect(userid)
			{
				var check = document.getElementsByName(userid);
				if(check != null)
				{
					for(var i=0 ; i<check.length ; i++)
					{
						check[i].checked = '';
					}
				}
			}
			
			function clearAllSelect()
			{
				var m = document.getElementsByName('checkbox');
				for (var i=0; i<m.length; i++)
				{
					m[i].checked = '';
				}
			}
			
			function selectAll(posId)
			{
				var checkPos = document.getElementsByName(posId);
				var checked = document.getElementById(posId).checked;
				for (var i=0; i<checkPos.length; i++)
				{
					if(checkPos[i].id.indexOf('_') > 0)
					{
						if(checked == true)
							bottomFrame.addOneResult(checkPos[i].id, checkPos[i].value, 'U');
						else
							bottomFrame.delOneResult(checkPos[i].id);
					}
					checkPos[i].checked = checked;
				}
			}
			
			//初始化人员选择窗口（职位）数据
			function initUserDataPosition(userData, selectId)
			{
				//userData: posid1,posname1:userid1,username1:userid2,username2:...;posid2,posname2:userid1,username1:userid2,username2:...;...
				if(userData == '')
					return ;
				var selectType = 'checkbox';
				if(isRadio == '0')
					selectType = 'radio';
				var js_table = document.getElementById('table_tree');
				var dataArray = userData.split(';');
				for(var i=0 ; i<dataArray.length ; i++)
				{
					var position = dataArray[i].split(':');
					if(position.length > 1)
					{
						var js_row_title = js_table.insertRow(-1);
						var js_cell_title = js_row_title.insertCell(-1);
						js_cell_title.style.setAttribute('background','#f4f8ff url(${ctx}/common/style/blue/images/list/table_title_bg.png) repeat-x');
						js_cell_title.style.setAttribute('color','#0000FF');
						js_cell_title.style.setAttribute('border-collapse','collapse');
						js_cell_title.style.setAttribute('height','22px');
						js_cell_title.style.setAttribute('font-weight','bold');
						js_cell_title.style.setAttribute('border-right','solid 1px #c7defe');
						js_cell_title.style.setAttribute('border-bottom','1px solid #c7defe');
						js_cell_title.style.setAttribute('border-top','1px solid #c7defe');
						js_cell_title.style.setAttribute('border-left','1px solid #c7defe');
						js_cell_title.style.setAttribute('text-align','left');
						js_cell_title.style.setAttribute('padding-left','3px');
						js_cell_title.style.setAttribute('line-height','22px');
						var pos = position[0].split(',');
						var posId = pos[0];
						var posName = pos[1];
						//if(selectType == 'checkbox')
						//	js_cell_title.innerHTML = '<input type=\"checkbox\" name=\"'+posId+'\" id=\"'+posId+'\" onclick=\"selectAll(\''+posId+'\');\"/>' + posName;
						//else
							js_cell_title.innerHTML = posName;
						var js_row_content = js_table.insertRow(-1);
						var js_cell_content = js_row_content.insertCell(-1);
						js_cell_content.style.setAttribute("height","25px");
						var content = '';
						for(var j=1 ; j<position.length ; j++)
						{
							var userInfo = position[j].split(',');
							var checked = '';
							if(userInfo[0].length >= 2 && selectId.indexOf(userInfo[0].substring(2)) >= 0)
								checked = 'checked';
							//content += '<nobr><input type=\"'+selectType+'\" name=\"'+posId+'\" id=\"'+posId+'_'+userInfo[0]+'\" '+checked+' value=\"'+userInfo[1]+'\" onclick=\"setResultArea(this,\''+posId+'_'+userInfo[0]+'\',\''+userInfo[1]+'\');\"/><u>' + userInfo[1] + '</u>；</nobr>';
							content += '<nobr><input type=\"'+selectType+'\" name=\"'+userInfo[0]+'\" id=\"'+posId+'_'+userInfo[0]+'\" '+checked+' value=\"'+userInfo[1]+'\" onclick=\"setResultArea(this,\''+posId+'\',\''+userInfo[0]+'\',\''+userInfo[1]+'\');\"/><u>' + userInfo[1] + '</u>；</nobr>';
						}
						js_cell_content.innerHTML = content;
					}
				}
			}
		</script>
	</head>
	<body>
	<div class='content'>
		<div class="add_scroll_div_x" id="center">
			<table class="tablesimple" id="table_tree">
			</table>
		</div>
	</div>
	</body>
</html>
