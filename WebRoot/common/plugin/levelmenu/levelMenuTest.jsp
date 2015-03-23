<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/common/core/taglibs.jsp"%>
	<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/levelmenu/css/levelMenu.css"/>
	<script src="${ctx}/common/plugin/levelmenu/js/levelMenu.js"></script>
	<script type="text/javascript">
		window.onload = function () {
		}
		var data = '001,001,1;002,002,1;003,003,1;004,004,1;005,005,0;006,006,1;007,007,1';
		function test1 (source) {
			LM_MENU_NUM = 5; // 显示5个节点
			LM_HAS_NEXT = false;
			showLevelMenu(source, 'SystemVar');
		}
		function test2 (source) {
			LM_NEXT_WAY = '1';
			showLevelMenu(source, 'AuditLog');
		}
		function test3 (source) {
			LM_FULL_ID = true;
			showLevelMenu(source, 'AuditLog');
		}
		function test4(source) {
			var jsondata = [{value:"1",text:"东北三省",subdata:[{value:"01",text:"黑龙江省",subdata:[{value:"001",text:"哈尔滨"},{value:"002",text:"大庆"}]}
														 	 ,{value:"02",text:"吉林省",subdata:[{value:"001",text:"吉林市"},{value:"002",text:"长春市"}]}
														 	 ,{value:"03",text:"辽宁省",subdata:[{value:"001",text:"沈阳"},{value:"002",text:"铁岭"}]}
														 	 ]}];
			LM_DATA_WAY = '1';
			LM_NEXT_WAY = '1';
			showLevelMenu(source, jsondata);
		}
	</script>
</head>
<body>
<div class="content">
	<table class="tableborder">
		<tr>
			<th width="30px">序号</th>
			<th width="200px">选择域</th>
			<th width="200px">选择域值</th>
			<th>说明</th>
		</tr>
		<tr>
			<td>01</td>
			<td><input type="text" id="test1" onclick="test1(this);" value="点击这里..." class="textInput"/></td>
			<td><input type="text" id="test1id"/></td>
			<td>LM_MENU_NUM = 5;LM_HAS_NEXT = false;</td>
		</tr>
		<tr>
			<td>02</td>
			<td><input type="text" id="test2" onclick="test2(this);" value="点击这里..." class="textInput"/></td>
			<td><input type="text" id="test2id"/></td>
			<td>LM_NEXT_WAY = '1';</td>
		</tr>
		<tr>
			<td>03</td>
			<td><input type="text" id="test3" onclick="test3(this);" value="点击这里..." class="textInput"/></td>
			<td><input type="text" id="test3id"/></td>
			<td>LM_FULL_ID = true;</td>
		</tr>
		<tr>
			<td>04</td>
			<td><input type="text" id="test4" onclick="test4(this);" value="点击这里..." class="textInput"/></td>
			<td><input type="text" id="test4id"/></td>
			<td>LM_DATA_WAY = '1';LM_NEXT_WAY = '1';</td>
		</tr>
	</table>
	<table class="tableborder">
		<tr>
			<th width="30px">序号</th>
			<th width="200px">可修改的属性</th>
			<th width="200px">默认属性值</th>
			<th width="400px">属性值说明</th>
			<th>备注</th>
		</tr>
		<tr>
			<td>01</td>
			<td>LM_DATA_WAY</td>
			<td>0</td>
			<td>0:逐级查询 1:整体传入json数据</td>
			<td>
				逐级查询需传如第一个标识，会将这个标识传给你设置的LM_ACTION
				<br/>
				json格式:[{value:"1",text="显示值",subdata:[{value:"01",text:"显示值",subdata:[……]},{……}]},{value:"2",text="显示值",subdata:[……]},{……}]
			</td>
		</tr>
		<tr>
			<td>02</td>
			<td>LM_ACTION</td>
			<td>$ctx + '/dicManager/getLevelMenu.action'</td>
			<td>获取下级菜单的url</td>
			<td>会将2个参数传递给此url：top_id（你传入的标识）；lm_id（每级你点击节点的value值，即是你欲展现下级的的节点）。此默认url是配合数据字典提供的链接。</td>
		</tr>
		<tr>
			<td>03</td>
			<td>LM_NEXT_WAY</td>
			<td>0</td>
			<td>0:点击展示下级 1:鼠标移动展示下级</td>
			<td></td>
		</tr>
		<tr>
			<td>04</td>
			<td>LM_MENU_NUM</td>
			<td>10</td>
			<td>最多同时显示节点的个数</td>
			<td></td>
		</tr>
		<tr>
			<td>05</td>
			<td>LM_FULL_ID</td>
			<td>false</td>
			<td>true/false，是否获取fullId，也就是多级别id集合并用.分割</td>
			<td></td>
		</tr>
		<tr>
			<td>06</td>
			<td>LM_HAS_NEXT</td>
			<td>true</td>
			<td>true/false，是否是多级菜单</td>
			<td></td>
		</tr>
	</table>
</div>
</body>
</html>
