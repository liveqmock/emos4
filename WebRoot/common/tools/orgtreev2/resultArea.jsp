<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<%@ include file="/common/core/header_form.jsp"%>
	<script type="text/javascript" src="${ctx}/common/tools/orgtreev2/js/organizaTree.js"></script>
	<script language="javascript" defer="defer">
		var isRadio = '<%=request.getParameter("isRadio")%>';
		window.onresize = function() {
			setCenter(0, 0);
		}
		window.onload = function() {
			setCenter(0, 0);
		}
		
		/**
		 * 添加一个数据
		 * id：选择数据的id  text：选择数据的名称  type：添加这条数据的类型
		 */
		function addOneResult(id, text, type)
		{
			if(id == '')
				return ;
			if(document.getElementById(id) != null)
				return ;
			if(isRadio == '0')
				clearAll();
			var elementType = '';
			if(type == 'U')
				elementType = '人员';
			else if(type == 'D')
				elementType = '部门';
			else
				elementType = '未定义类型';
			$("#inertData").append("<div id="+id+" idText="+text+" idType="+type+" style='margin-right:20px;display:inline;'><nobr><b>"+text+"</b> ["+elementType+"]<img src=\"../../style/blue/images/del_user.jpg\" onclick=\"delOneResult('"+id+"','"+type+"')\" style=\"margin-left:2px; margin-bottom:-1px;\" alt=\"删除\"></img>；</nobr></div>");
			leftFrame.contentWindow.setSelect(id, type);
			leftFrame.contentWindow.resultToListSelect(id);
		}
		/**
		 * 删除一个数据
		 * id：删除数据的id type：要同步的数据是人还是其他
		 */
		function delOneResult(id, type)
		{
			if(id != '')
				$("#"+id).remove();
			leftFrame.contentWindow.cancelSelect(id, type);
			leftFrame.contentWindow.listCancelSelect(id, type);
			if(type == 'U' && rightFrame != null)
				rightFrame.contentWindow.cancelSelect(id);
		}

		/**
		 * 删除所有所选项，同时会同步到选择数据的页面
		 */
		function clearAll()
		{
			//清空人员选择区域所有被选中的节点
			if(rightFrame != null)
			{
				var divArray = document.getElementById('inertData').getElementsByTagName('div');
				if(divArray.length > 0)
				{
					for(var i=0 ; i<divArray.length ; i++)
					{
						rightFrame.contentWindow.cancelSelect(divArray[i].id);
					}
				}
			}
			//删除选择结果中所有的数据
			document.getElementById('inertData').innerHTML = '';
			//清空树中所有被选中的节点
			leftFrame.contentWindow.clearAllSelect();
			leftFrame.contentWindow.listCancelSelectAll();
		}
	</script>
</head>
<body>
	<div class="content">
		<div class="add_scroll_div_x" id="center">
			<div id="treeData" style="overFlow-y: scroll; height: 96%; padding: 2px 4px 0px 4px; margin-top: 2px;">
				<div>已经选择：
					<a href="#" onclick="clearAll();return false;"><span style="color:blue"><u>全部清空</u></span></a>
				</div>
				<div id="inertData" style="padding: 0px 4px 4px 4px;"></div>
			</div>
		</div>
	</div>
</body>
</html>
