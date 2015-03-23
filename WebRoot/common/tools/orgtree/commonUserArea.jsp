<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/header_list.jsp"%>
		<title>人员区域选择</title>
		<script type="text/javascript" src="${ctx}/common/tools/orgtree/js/commonTree.js"></script>
		<style>
			.div_area_bg {
				background:#ffffff url(${ctx}/common/style/blue/images/index/menu_bg.png) repeat-x;
				height:24px;
			}
		</style>
		<script language="javascript">
			var isRadio = '${isRadio}';
			window.onresize = function()
			{
				setCenter(0,24);
			}
			window.onload = function()
			{
				setCenter(0,24);
				initUserData('${commonUserData}', '${selectId}');
			}
			
			function setResultArea(sourceObj, userid, username)
			{
				if(sourceObj.checked)
				{
					bottomFrame.contentWindow.addOneResult(userid, username, 'U');
					leftFrame.contentWindow.setSelect(userid, 'U');
				}
				else
				{
					bottomFrame.contentWindow.delOneResult(userid);
					leftFrame.contentWindow.cancelSelect(userid, 'U');
				}
			}
			
			function setSelect(userid)
			{
				var checkbox = document.getElementById(userid);
				if(checkbox != null)
					document.getElementById(userid).checked = 'checked';
			}
			
			function cancelSelect(userid)
			{
				var checkbox = document.getElementById(userid);
				if(checkbox != null)
					document.getElementById(userid).checked = '';
			}
			
			function clearAllSelect()
			{
				var m = document.getElementsByName('checkbox');
				for (var i=0; i<m.length; i++)
				{
					m[i].checked = '';
				}
			}
			
		</script>
	</head>
	<body>
	<div class='content'>
		<div class="div_area_bg">
			<a href="#" onclick="setStatus('A');return false;" id="select_A" style="color:blank;font-family:宋体;font-size:12px"><u>A</u></a>
			<a href="#" onclick="setStatus('B');return false;" id="select_B" style="color:blank;font-family:宋体;font-size:12px"><u>B</u></a>
			<a href="#" onclick="setStatus('C');return false;" id="select_C" style="color:blank;font-family:宋体;font-size:12px"><u>C</u></a>
			<a href="#" onclick="setStatus('D');return false;" id="select_D" style="color:blank;font-family:宋体;font-size:12px"><u>D</u></a>
			<a href="#" onclick="setStatus('E');return false;" id="select_E" style="color:blank;font-family:宋体;font-size:12px"><u>E</u></a>
			<a href="#" onclick="setStatus('F');return false;" id="select_F" style="color:blank;font-family:宋体;font-size:12px"><u>F</u></a>
			<a href="#" onclick="setStatus('G');return false;" id="select_G" style="color:blank;font-family:宋体;font-size:12px"><u>G</u></a>
			<a href="#" onclick="setStatus('H');return false;" id="select_H" style="color:blank;font-family:宋体;font-size:12px"><u>H</u></a>
			<a href="#" onclick="setStatus('I');return false;" id="select_I" style="color:blank;font-family:宋体;font-size:12px"><u>I</u></a>
			<a href="#" onclick="setStatus('J');return false;" id="select_J" style="color:blank;font-family:宋体;font-size:12px"><u>J</u></a>
			<a href="#" onclick="setStatus('K');return false;" id="select_K" style="color:blank;font-family:宋体;font-size:12px"><u>K</u></a>
			<a href="#" onclick="setStatus('L');return false;" id="select_L" style="color:blank;font-family:宋体;font-size:12px"><u>L</u></a>
			<a href="#" onclick="setStatus('M');return false;" id="select_M" style="color:blank;font-family:宋体;font-size:12px"><u>M</u></a>
			<a href="#" onclick="setStatus('N');return false;" id="select_N" style="color:blank;font-family:宋体;font-size:12px"><u>N</u></a>
			<a href="#" onclick="setStatus('O');return false;" id="select_O" style="color:blank;font-family:宋体;font-size:12px"><u>O</u></a>
			<a href="#" onclick="setStatus('P');return false;" id="select_P" style="color:blank;font-family:宋体;font-size:12px"><u>P</u></a>
			<a href="#" onclick="setStatus('Q');return false;" id="select_Q" style="color:blank;font-family:宋体;font-size:12px"><u>Q</u></a>
			<a href="#" onclick="setStatus('R');return false;" id="select_R" style="color:blank;font-family:宋体;font-size:12px"><u>R</u></a>
			<a href="#" onclick="setStatus('S');return false;" id="select_S" style="color:blank;font-family:宋体;font-size:12px"><u>S</u></a>
			<a href="#" onclick="setStatus('T');return false;" id="select_T" style="color:blank;font-family:宋体;font-size:12px"><u>T</u></a>
			<a href="#" onclick="setStatus('U');return false;" id="select_U" style="color:blank;font-family:宋体;font-size:12px"><u>U</u></a>
			<a href="#" onclick="setStatus('V');return false;" id="select_V" style="color:blank;font-family:宋体;font-size:12px"><u>V</u></a>
			<a href="#" onclick="setStatus('W');return false;" id="select_W" style="color:blank;font-family:宋体;font-size:12px"><u>W</u></a>
			<a href="#" onclick="setStatus('X');return false;" id="select_X" style="color:blank;font-family:宋体;font-size:12px"><u>X</u></a>
			<a href="#" onclick="setStatus('Y');return false;" id="select_Y" style="color:blank;font-family:宋体;font-size:12px"><u>Y</u></a>
			<a href="#" onclick="setStatus('Z');return false;" id="select_Z" style="color:blank;font-family:宋体;font-size:12px"><u>Z</u></a>
		</div>
		<div class="add_scroll_div_x" id="center">
			<table class="tablesimple" id="table_tree">
				<tr id="th_A" style="display:none"><th>A</th></tr>
				<tr id="td_A" style="display:none"><td></td></tr>
				<tr id="th_B" style="display:none"><th>B</th></tr>
				<tr id="td_B" style="display:none"><td></td></tr>
				<tr id="th_C" style="display:none"><th>C</th></tr>
				<tr id="td_C" style="display:none"><td></td></tr>
				<tr id="th_D" style="display:none"><th>D</th></tr>
				<tr id="td_D" style="display:none"><td></td></tr>
				<tr id="th_E" style="display:none"><th>E</th></tr>
				<tr id="td_E" style="display:none"><td></td></tr>
				<tr id="th_F" style="display:none"><th>F</th></tr>
				<tr id="td_F" style="display:none"><td></td></tr>
				<tr id="th_G" style="display:none"><th>G</th></tr>
				<tr id="td_G" style="display:none"><td></td></tr>
				<tr id="th_H" style="display:none"><th>H</th></tr>
				<tr id="td_H" style="display:none"><td></td></tr>
				<tr id="th_I" style="display:none"><th>I</th></tr>
				<tr id="td_I" style="display:none"><td></td></tr>
				<tr id="th_J" style="display:none"><th>J</th></tr>
				<tr id="td_J" style="display:none"><td></td></tr>
				<tr id="th_K" style="display:none"><th>K</th></tr>
				<tr id="td_K" style="display:none"><td></td></tr>
				<tr id="th_L" style="display:none"><th>L</th></tr>
				<tr id="td_L" style="display:none"><td></td></tr>
				<tr id="th_M" style="display:none"><th>M</th></tr>
				<tr id="td_M" style="display:none"><td></td></tr>
				<tr id="th_N" style="display:none"><th>N</th></tr>
				<tr id="td_N" style="display:none"><td></td></tr>
				<tr id="th_O" style="display:none"><th>O</th></tr>
				<tr id="td_O" style="display:none"><td></td></tr>
				<tr id="th_P" style="display:none"><th>P</th></tr>
				<tr id="td_P" style="display:none"><td></td></tr>
				<tr id="th_Q" style="display:none"><th>Q</th></tr>
				<tr id="td_Q" style="display:none"><td></td></tr>
				<tr id="th_R" style="display:none"><th>R</th></tr>
				<tr id="td_R" style="display:none"><td></td></tr>
				<tr id="th_S" style="display:none"><th>S</th></tr>
				<tr id="td_S" style="display:none"><td></td></tr>
				<tr id="th_T" style="display:none"><th>T</th></tr>
				<tr id="td_T" style="display:none"><td></td></tr>
				<tr id="th_U" style="display:none"><th>U</th></tr>
				<tr id="td_U" style="display:none"><td></td></tr>
				<tr id="th_V" style="display:none"><th>V</th></tr>
				<tr id="td_V" style="display:none"><td></td></tr>
				<tr id="th_W" style="display:none"><th>W</th></tr>
				<tr id="td_W" style="display:none"><td></td></tr>
				<tr id="th_X" style="display:none"><th>X</th></tr>
				<tr id="td_X" style="display:none"><td></td></tr>
				<tr id="th_Y" style="display:none"><th>Y</th></tr>
				<tr id="td_Y" style="display:none"><td></td></tr>
				<tr id="th_Z" style="display:none"><th>Z</th></tr>
				<tr id="td_Z" style="display:none"><td></td></tr>
				<!-- 下面这行添加的目的是这个table的最后一行的样式和其他行不一样，暂时添加此隐藏行 -->
				<tr id="th_END" style="display:none"><th>END</th></tr>
			</table>
		</div>
	</div>
	</body>
</html>
