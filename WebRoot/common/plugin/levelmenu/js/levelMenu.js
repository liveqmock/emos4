// 可修改的属性常量
var LM_DATA_WAY = '0'; // 0:逐级查询 1:整体传入json数据
var LM_ACTION = $ctx + '/dicManager/getLevelMenu.action'; // 获取下级菜单的url
var LM_NEXT_WAY = '0'; // 0:点击展示下级 1:鼠标移动展示下级
var LM_MENU_NUM = 10; // 最多同时显示节点的个数
var LM_FULL_ID = false; // true/false，是否获取fullId，也就是多级别id集合并用.分割
var LM_HAS_NEXT = true; // true/false，是否包含下级菜单
// 私有属性常量
var PRI_LM_WIDTH = 0;
var PRI_LM_CURLEVEL = 0; // 当前菜单展开的级别
var PRI_LM_TOPID = ''; // 记录首次传入数据
var PRI_LM_SOURCE = new Object(); // 第一层传递过来的标签对象
var PRI_LM_IDARR = new Array(); // 用来存放每层激活的节点id
var PRI_LM_NUM = new Array(); // 菜单每级的元素个数
// 初始化属性
function initProperty() {
	// 可修改的属性常量
	LM_DATA_WAY = '0'; // 0:逐级查询 1:整体传入json数据
	LM_ACTION = $ctx + '/dicManager/getLevelMenu.action'; // 获取下级菜单的url
	LM_NEXT_WAY = '0'; // 0:点击展示下级 1:鼠标移动展示下级
	LM_MENU_NUM = 10; // 最多同时显示节点的个数
	LM_FULL_ID = false; // 是否获取fullId，也就是多级别id集合并用.分割
	LM_HAS_NEXT = true; // 是否包含下级菜单
	// 私有属性常量
	PRI_LM_WIDTH = 0;
	PRI_LM_CURLEVEL = 0; // 当前菜单展开的级别
	PRI_LM_TOPID = ''; // 记录首次传入数据
	PRI_LM_SOURCE = new Object(); // 第一层传递过来的标签对象
	PRI_LM_IDARR = new Array(); // 用来存放每层激活的节点id
	PRI_LM_NUM = new Array(); // 菜单每级的元素个数
}
// 显示菜单
function showLevelMenu(source, data) {
	this.PRI_LM_TOPID = data;
	if(LM_DATA_WAY == '0') {
		loadMenu(source, data, 1);
	} else if(LM_DATA_WAY == '1') {
		loadMenu(source, '0', 1);
	}
}
// 加载菜单
function loadMenu(source, data, level) {
	if(LM_DATA_WAY == '0') {
		if(LM_ACTION != '') {
			$.post(LM_ACTION,{top_id:PRI_LM_TOPID,lm_id:data},function(result) {
				createMenu(source, data, level, result);
			});
		}
	} else if(LM_DATA_WAY == '1') {
		var newdata = getSubJsonData(data, level);
		createMenu(source, data, level, newdata);
	}
}
// 获取json下级数据
function getSubJsonData(value, level) {
	var returnData = '';
	var subData = PRI_LM_TOPID;
	for(var i=1 ; i<level ; i++) {
		for(var j=0 ; j<subData.length ; j++) {
			if(i == level - 1) {
				if(subData[j].value == value) {
					subData = subData[j].subdata;
					break;
				}
			} else {
				if(subData[j].value == PRI_LM_IDARR[i]) {
					subData = subData[j].subdata;
					break;
				}
			}
		}
	}
	for(var i=0 ; i<subData.length ; i++) {
		returnData += ';' + subData[i].value + ',' + subData[i].text + ',' + (subData[i].subdata && (subData[i].subdata.length > 0) ? 1 : 0);
	}
	return returnData.length > 0 ? returnData.substring(1) : returnData;
}
// 创建菜单
function createMenu(source, id, level, data) {
	if(data == '') {
		return false;
	}
	if(level == 1) { // 避免第二次激活菜单重新初始化全局常量
		this.PRI_LM_CURLEVEL = 0;
	}
	this.removeAllMenu(parseInt(level)); // 移除指定级别下所有菜单
	PRI_LM_IDARR[PRI_LM_CURLEVEL] = id;
	var mousePos = absolutePoint(source); // 获取鼠标位置，以便计算新弹出菜单位置
	this.PRI_LM_CURLEVEL = parseInt(level); // 设置当前div级别
	if(level == 1) { // 第一级时
		this.PRI_LM_SOURCE = source; // 设置标签对象
		this.PRI_LM_WIDTH = getDivPos(source, 'right') - getDivPos(source, 'left');
	}
	var levelDiv = createDiv(level); // 获取DIV模版
	levelDiv.style.width = this.PRI_LM_WIDTH; // 设置div宽度
	levelDiv.innerHTML = this.spelDiv(data); // 拼写菜单的代码
	document.body.appendChild(levelDiv);
	// 固定div位置
	if(level > 1) {
		var left = mousePos.x + 12;
		var top = mousePos.y + 5;
		levelDiv.style.left = left + 'px';
		levelDiv.style.top = top + 'px';
	} else {
		var left = getDivPos(source, 'left');
		var top = getDivPos(source, 'bottom');
		if(left > 0) {
			left -= 2;
		}
		if(top > 0) {
			top -= 2;
		}
		levelDiv.style.left = left + 'px';
		levelDiv.style.top = top + 'px';
	}
}
// 移除指定级别下所有菜单
function removeAllMenu(level) {
	for(var i=this.PRI_LM_CURLEVEL+1 ; i>=level ; i--) {
		var levelDiv = document.getElementById('levelDiv_'+i);
		if(levelDiv) {
			levelDiv.innerHTML = '';
			document.body.removeChild(levelDiv);
		}
	}
	if(!this.PRI_LM_CURLEVEL == 0 && level == 1) {
		initProperty();
	}
}
// 获取DIV模版
function createDiv(level) {
	var levelDiv = document.createElement('<div id="levelDiv_'+level+'"/>');
	levelDiv.className = "levelDiv";
	levelDiv.onmouseout = function() {
		if(!verifyMouse(levelDiv)) {
			var parDiv = document.getElementById('levelDiv_' + (parseInt(level) - 1));
			if(verifyMouse(parDiv)) {
				removeAllMenu(level);
			} else {
				removeAllMenu(1);
			}
		}
	};
	return levelDiv;
}
// 拼写节点Div
function spelDiv(menuData) {
	var menuArray = menuData.split(';');
	PRI_LM_NUM[PRI_LM_CURLEVEL] = menuArray.length;
	var htmlDiv = '';
	if(LM_MENU_NUM >= menuArray.length) {
		for (var i = 0 ; i < menuArray.length ; i ++ ) {
			var subMenu = menuArray[i].split(',');
			htmlDiv += this.spelSpan(i, subMenu, i == menuArray.length - 1, '');
		}
	} else {
		htmlDiv += this.spelDrag('up');
		for (var i = 0 ; i < LM_MENU_NUM ; i ++ ) {
			var subMenu = menuArray[i].split(',');
			htmlDiv += this.spelSpan(i, subMenu, i == LM_MENU_NUM - 1, '');
		}
		for (var i = LM_MENU_NUM ; i < menuArray.length ; i ++ ) {
			var subMenu = menuArray[i].split(',');
			htmlDiv += this.spelSpan(i, subMenu, true, 'none');
		}
		htmlDiv += this.spelDrag('down');
	}
	return htmlDiv;
}
// 拼写节点Span
function spelSpan(num, subMenu, islast, display) {
	var htmlSpan = '';
	var span_id = 'levelSpan_' + PRI_LM_CURLEVEL + '_' + num;
	var style_display = 'style="display:'+display+'"';
	if(islast) {
		htmlSpan = '<span id="'+span_id+'" class="levelSpanBottom" onmouseout="this.className=\'levelSpanBottom\'" onmouseover="this.className=\'levelSpanBottomOver\'" '+style_display+'>';
	} else {
		htmlSpan = '<span id="'+span_id+'" class="levelSpan" onmouseout="this.className=\'levelSpan\'" onmouseover="this.className=\'levelSpanOver\'" '+style_display+'>';
	}
	htmlSpan += '<span class="spanText" onclick="dataDeal(\''+subMenu[0]+'\',\''+subMenu[1]+'\');">'+subMenu[1]+'</span>';
	if(LM_HAS_NEXT) {
		htmlSpan += '<span class="spanBtn"';
		if(LM_NEXT_WAY == '0') {
			if(subMenu[2] == '1') { // 包含下级
				htmlSpan += ' onclick="loadMenu(this, \''+subMenu[0]+'\', '+(this.PRI_LM_CURLEVEL + 1)+');"';
			}
		} else if(LM_NEXT_WAY == '1') {
			if(subMenu[2] == '1') { // 包含下级
				htmlSpan += ' onmouseover="loadMenu(this, \''+subMenu[0]+'\', '+(this.PRI_LM_CURLEVEL + 1)+');"';
			}
		}
		htmlSpan += '>';
		if(subMenu[2] == '1') { // 包含下级
			htmlSpan += '<img src="image/next_show.png"/></span>';
		} else { // 不包含下级
			htmlSpan += '<img src="image/next_hide.png"/></span>';
		}
	}
	htmlSpan += '</span>';
	return htmlSpan;
}
// 拼写拖动的span部分
function spelDrag(type) {
	var htmlSpan = '';
	if(type == 'up') {
		htmlSpan = '<input type="hidden" id="drag_up_'+PRI_LM_CURLEVEL+'" value="1"/>';
		htmlSpan += '<span id="dragSpanUp_'+PRI_LM_CURLEVEL+'" class="dragSpanUp" onmousedown="this.className=\'dragSpanUp\'" onmouseup="this.className=\'dragSpanUp\'" onclick="showMoreMenu(\''+type+'\', '+PRI_LM_CURLEVEL+');">△</span>';
	} else if(type == 'down') {
		htmlSpan = '<input type="hidden" id="drag_down_'+PRI_LM_CURLEVEL+'" value="'+LM_MENU_NUM+'"/>';
		htmlSpan += '<span id="dragSpanDown_'+PRI_LM_CURLEVEL+'" class="dragSpanDown" onmousedown="this.className=\'dragSpanDownMd\'" onmouseup="this.className=\'dragSpanDown\'" onclick="showMoreMenu(\''+type+'\', '+PRI_LM_CURLEVEL+');">▼</span>';
	}
	return htmlSpan;
}
// 显示向上或向下的更多菜单
function showMoreMenu(type, level) {
	var upV = parseInt(document.getElementById('drag_up_'+level).value);
	var downV = parseInt(document.getElementById('drag_down_'+level).value);
	var span_up = document.getElementById('dragSpanUp_'+level);
	var span_down = document.getElementById('dragSpanDown_'+level);
	if(type == 'up') {
		if(upV > 1) {
			upV --;
			downV --;
			setLevelSpan(upV, downV, level);
		}
		setDragSpan(span_down, 'down', false);
		if(upV > 1) {
			setDragSpan(span_up, 'up', false);
		} else {
			setDragSpan(span_up, 'up', true);
		}
	} else if (type == 'down') {
		if(downV < PRI_LM_NUM[level]) {
			downV ++;
			upV ++;
			setLevelSpan(upV, downV, level);
		}
		setDragSpan(span_up, 'up', false);
		if(downV < PRI_LM_NUM[level]) {
			setDragSpan(span_down, 'down', false);
		} else {
			setDragSpan(span_down, 'down', true);
		}
	}
	document.getElementById('drag_up_'+level).value = upV;
	document.getElementById('drag_down_'+level).value = downV;
}
// 设置levelSpan样式
function setLevelSpan(upV, downV, level) {
	upV --;
	downV --;
	for (var i = 0 ; i < PRI_LM_NUM[level] ; i++) {
		var span = document.getElementById('levelSpan_'+level+'_'+i);
		if(i < upV) {
			span.style.display = 'none';
		} else if (i < downV) {
			span.style.display = '';
			span.className = 'levelSpan';
			span.onmouseout = function () {this.className = 'levelSpan'};
			span.onmouseover = function () {this.className = 'levelSpanOver'};
		} else if (i == downV) {
			span.style.display = '';
			span.className = 'levelSpanBottom';
			span.onmouseout = function () {this.className = 'levelSpanBottom'};
			span.onmouseover = function () {this.className = 'levelSpanBottomOver'};
		} else {
			span.style.display = 'none';
		}
	}
}
// 设置DragSpan样式
function setDragSpan(source, type, isEdge) {
	if(!source) {
		return false;
	}
	if(type == 'up') { // 向上滚动一个菜单节点
		if(isEdge) { // 菜单节点以在最上端的处理
			source.innerHTML = '△';
			source.onmouseup = function () {this.className = 'dragSpanUp'};
			source.onmousedown = function () {this.className = 'dragSpanUp'};
		} else {
			source.innerHTML = '▲';
			source.onmouseup = function () {this.className = 'dragSpanUp'};
			source.onmousedown = function () {this.className = 'dragSpanUpMd'};
		}
	} else { // 向下滚动一个菜单节点
		if(isEdge) { // 菜单节点以在最底端的处理
			source.innerHTML = '▽';
			source.onmouseup = function () {this.className = 'dragSpanDown'};
			source.onmousedown = function () {this.className = 'dragSpanDown'};
		} else {
			source.innerHTML = '▼';
			source.onmouseup = function () {this.className = 'dragSpanDown'};
			source.onmousedown = function () {this.className = 'dragSpanDownMd'};
		}
	}
}
// 数据处理，将数据返回调用菜单的域中
function dataDeal(value, text) {
	if(PRI_LM_SOURCE) {
		PRI_LM_SOURCE.value = text;
		var idsrc = document.getElementById(PRI_LM_SOURCE.id + 'id');
		if(idsrc) {
			if(LM_FULL_ID) {
				idsrc.value = '';
				for(var i=1 ; i<PRI_LM_CURLEVEL ; i++) {
					idsrc.value = idsrc.value + PRI_LM_IDARR[i] + '.';
				}
				idsrc.value = idsrc.value + value;
			} else {
				idsrc.value = value;
			}
		}
	}
	removeAllMenu(1);
}
// 计算鼠标位置
function absolutePoint(element) {
	var result = { x: element.offsetLeft, y: element.offsetTop };
	element = element.offsetParent;
	while (element) {
		result.x += element.offsetLeft;
		result.y += element.offsetTop;
		element = element.offsetParent;
	}
	return result;
}
// 验证鼠标是否在指定div中
function verifyMouse(divSource) {
	var mouse = mousePos();
	var div_left = getDivPos(divSource, 'left');
	var div_right = getDivPos(divSource, 'right');
	var div_top = getDivPos(divSource, 'top');
	var div_bottom = getDivPos(divSource, 'bottom');
	if(div_left > 0 && div_right > 0 && div_top > 0 && div_bottom > 0) {
		if(mouse.x > div_left && mouse.x < div_right && mouse.y > div_top && mouse.y < div_bottom) {
			return true;
		}
	}
	return false;
}
// 获取鼠标在浏览器中的坐标
function mousePos(e) {
	var x,y;
	var e = e||window.event;
	return {
		x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
		y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop
	};
}
// 获取Div坐标
function getDivPos(divSource, p) {
	var pos = -1;
	if(divSource) {
		if (p == 'left') {
			pos = divSource.getBoundingClientRect().left;
		} else if(p == 'right') {
			pos = divSource.getBoundingClientRect().right;
		} else if(p == 'top') {
			pos = divSource.getBoundingClientRect().top;
		} else if(p == 'bottom') {
			pos = divSource.getBoundingClientRect().bottom;
		}
	}
	return pos;
}