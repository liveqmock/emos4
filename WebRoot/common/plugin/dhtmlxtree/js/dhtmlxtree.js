//v.1.6 build 71114
/*
Copyright DHTMLX LTD. http://www.dhtmlx.com
To use this component please contact sales@dhtmlx.com to obtain license
*/
function xmlPointer(data) {
	this.d = data;
}
xmlPointer.prototype = {text:function () {
	if (!_isFF) {
		return this.d.xml;
	}
	var x = new XMLSerializer();
	return x.serializeToString(this.d);
}, get:function (name) {
	return this.d.getAttribute(name);
}, exists:function () {
	return !!this.d;
}, content:function () {
	return this.d.firstChild ? this.d.firstChild.data : "";
}, each:function (name, f, t, i) {
	var a = this.d.childNodes;
	var c = new xmlPointer();
	if (a.length) {
		for (i = i || 0; i < a.length; i++) {
			if (a[i].tagName == name) {
				c.d = a[i];
				if (f.apply(t, [c, i]) == -1) {
					return;
				}
			}
		}
	}
}, get_all:function () {
	var a = {};
	var b = this.d.attributes;
	for (var i = 0; i < b.length; i++) {
		a[b[i].name] = b[i].value;
	}
	return a;
}, sub:function (name) {
	var a = this.d.childNodes;
	var c = new xmlPointer();
	if (a.length) {
		for (var i = 0; i < a.length; i++) {
			if (a[i].tagName == name) {
				c.d = a[i];
				return c;
			}
		}
	}
}, up:function (name) {
	return new xmlPointer(this.d.parentNode);
}, set:function (name, val) {
	this.d.setAttribute(name, val);
}, clone:function (name) {
	return new xmlPointer(this.d);
}, sub_exists:function (name) {
	var a = this.d.childNodes;
	if (a.length) {
		for (var i = 0; i < a.length; i++) {
			if (a[i].tagName == name) {
				return true;
			}
		}
	}
	return false;
}, through:function (name, rule, v, f, t) {
	var a = this.d.childNodes;
	if (a.length) {
		for (var i = 0; i < a.length; i++) {
			if (a[i].tagName == name && a[i].getAttribute(rule) != null && a[i].getAttribute(rule) != "" && (!v || a[i].getAttribute(rule) == v)) {
				var c = new xmlPointer(a[i]);
				f.apply(t, [c, i]);
			}
			var w = this.d;
			this.d = a[i];
			this.through(name, rule, v, f, t);
			this.d = w;
		}
	}
}};
function dhtmlXTreeObject(htmlObject, width, height, rootId) {
	if (_isIE) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		}
		catch (e) {
		}
	}
	if (typeof (htmlObject) != "object") {
		this.parentObject = document.getElementById(htmlObject);
	} else {
		this.parentObject = htmlObject;
	}
	this._itim_dg = true;
	this.dlmtr = ",";
	this.dropLower = false;
	this.enableIEImageFix();
	this.xmlstate = 0;
	this.mytype = "tree";
	this.smcheck = true;
	this.paddingLeft = 8;
	this.paddingTop = 8;
	this.paddingRight = 0;
	this.paddingBottom = 0;
	this.width = width;
	this.height = height;
	this.rootId = rootId;
	this.childCalc = null;
	this.def_img_x = "18px";
	this.def_img_y = "18px";
	this.def_line_img_x = "18px";
	this.def_line_img_y = "18px";
	this._dragged = new Array();
	this._selected = new Array();
	this.style_pointer = "pointer";
	if (_isIE) {
		this.style_pointer = "hand";
	}
	this._aimgs = true;
	this.htmlcA = " [";
	this.htmlcB = "]";
	this.lWin = window;
	this.cMenu = 0;
	this.mlitems = 0;
	this.dadmode = 0;
	this.slowParse = false;
	this.autoScroll = true;
	this.hfMode = 0;
	this.nodeCut = new Array();
	this.XMLsource = 0;
	this.XMLloadingWarning = 0;
	this._idpull = {};
	this._pullSize = 0;
	this.treeLinesOn = true;
	this.tscheck = false;
	this.timgen = true;
	this.dpcpy = false;
	this._ld_id = null;
	this._oie_onXLE = [];
	this.imPath = "treeGfx/";
	this.checkArray = new Array("iconUncheckAll.gif", "iconCheckAll.gif", "iconCheckGray.gif", "iconUncheckDis.gif", "iconCheckDis.gif", "iconCheckDis.gif");
	this.radioArray = new Array("radio_off.gif", "radio_on.gif", "radio_on.gif", "radio_off.gif", "radio_on.gif", "radio_on.gif");
	this.lineArray = new Array("line2.gif", "line3.gif", "line4.gif", "blank.gif", "blank.gif", "line1.gif");
	this.minusArray = new Array("minus2.gif", "minus3.gif", "minus4.gif", "minus.gif", "minus5.gif");
	this.plusArray = new Array("plus2.gif", "plus3.gif", "plus4.gif", "plus.gif", "plus5.gif");
	this.imageArray = new Array("leaf.gif", "folderOpen.gif", "folderClosed.gif");
	this.cutImg = new Array(0, 0, 0);
	this.cutImage = "but_cut.gif";
	this.dragger = new dhtmlDragAndDropObject();
	this.htmlNode = new dhtmlXTreeItemObject(this.rootId, "", 0, this);
	this.htmlNode.htmlNode.childNodes[0].childNodes[0].style.display = "none";
	this.htmlNode.htmlNode.childNodes[0].childNodes[0].childNodes[0].className = "hiddenRow";
	this.allTree = this._createSelf();
	this.allTree.appendChild(this.htmlNode.htmlNode);
	if (_isFF) {
		this.allTree.childNodes[0].width = "100%";
	}
	var self = this;
	this.allTree.onselectstart = new Function("return false;");
	if (_isMacOS) {
		this.allTree.oncontextmenu = function (e) {
			return self._doContClick(e || window.event);
		};
	}
	this.allTree.onmousedown = function (e) {
		return self._doContClick(e || window.event);
	};
	this.XMLLoader = new dtmlXMLLoaderObject(this._parseXMLTree, this, true, this.no_cashe);
	if (_isIE) {
		this.preventIECashing(true);
	}
	this.selectionBar = document.createElement("DIV");
	this.selectionBar.className = "selectionBar";
	this.selectionBar.innerHTML = "&nbsp;";
	this.selectionBar.style.display = "none";
	this.allTree.appendChild(this.selectionBar);
	if (window.addEventListener) {
		window.addEventListener("unload", function () {
			try {
				self.destructor();
			}
			catch (e) {
			}
		}, false);
	}
	if (window.attachEvent) {
		window.attachEvent("onunload", function () {
			try {
				self.destructor();
			}
			catch (e) {
			}
		});
	}
	this.dhx_Event();
	this._onEventSet = {onMouseIn:function () {
		this.ehlt = true;
	}, onMouseOut:function () {
		this.ehlt = true;
	}, onSelect:function () {
		this._onSSCF = true;
	}};
	return this;
}
dhtmlXTreeObject.prototype.setDataMode = function (mode) {
	this._datamode = mode;
};
dhtmlXTreeObject.prototype._doContClick = function (ev) {
	if (ev.button != 2) {
		if (this._acMenu) {
			this.cMenu._contextEnd();
		}
		return true;
	}
	var el = (_isIE ? ev.srcElement : ev.target);
	while ((el) && (el.tagName != "BODY")) {
		if (el.parentObject) {
			break;
		}
		el = el.parentNode;
	}
	if ((!el) || (!el.parentObject)) {
		return true;
	}
	var obj = el.parentObject;
	this._acMenu = (obj.cMenu || this.cMenu);
	if (this._acMenu) {
		el.contextMenuId = obj.id;
		el.contextMenu = this._acMenu;
		el.a = this._acMenu._contextStart;
		if (_isIE) {
			ev.srcElement.oncontextmenu = function () {
				event.cancelBubble = true;
				return false;
			};
		}
		el.a(el, ev);
		el.a = null;
		ev.cancelBubble = true;
		return false;
	}
	return true;
};
dhtmlXTreeObject.prototype.enableIEImageFix = function (mode) {
	if (!mode) {
		this._getImg = function (id) {
			return document.createElement((id == this.rootId) ? "div" : "img");
		};
		this._setSrc = function (a, b) {
			a.src = b;
		};
		this._getSrc = function (a) {
			return a.src;
		};
	} else {
		this._getImg = function () {
			var z = document.createElement("DIV");
			z.innerHTML = "&nbsp;";
			z.className = "dhx_bg_img_fix";
			return z;
		};
		this._setSrc = function (a, b) {
			a.style.backgroundImage = "url(" + b + ")";
		};
		this._getSrc = function (a) {
			var z = a.style.backgroundImage;
			return z.substr(4, z.length - 5);
		};
	}
};
dhtmlXTreeObject.prototype.destructor = function () {
	for (var a in this._idpull) {
		var z = this._idpull[a];
		if (!z) {
			continue;
		}
		z.parentObject = null;
		z.treeNod = null;
		z.childNodes = null;
		z.span = null;
		z.tr.nodem = null;
		z.tr = null;
		z.htmlNode.objBelong = null;
		z.htmlNode = null;
		this._idpull[a] = null;
	}
	this.allTree.innerHTML = "";
	this.XMLLoader.destructor();
	for (var a in this) {
		this[a] = null;
	}
};
function cObject() {
	return this;
}
cObject.prototype = new Object;
cObject.prototype.clone = function () {
	function _dummy() {
	}
	_dummy.prototype = this;
	return new _dummy();
};
function dhtmlXTreeItemObject(itemId, itemText, parentObject, treeObject, actionHandler, mode) {
	this.htmlNode = "";
	this.acolor = "";
	this.scolor = "";
	this.tr = 0;
	this.childsCount = 0;
	this.tempDOMM = 0;
	this.tempDOMU = 0;
	this.dragSpan = 0;
	this.dragMove = 0;
	this.span = 0;
	this.closeble = 1;
	this.childNodes = new Array();
	this.userData = new cObject();
	this.checkstate = 0;
	this.treeNod = treeObject;
	this.label = itemText;
	this.parentObject = parentObject;
	this.actionHandler = actionHandler;
	this.images = new Array(treeObject.imageArray[0], treeObject.imageArray[1], treeObject.imageArray[2]);
	this.id = treeObject._globalIdStorageAdd(itemId, this);
	if (this.treeNod.checkBoxOff) {
		this.htmlNode = this.treeNod._createItem(1, this, mode);
	} else {
		this.htmlNode = this.treeNod._createItem(0, this, mode);
	}
	this.htmlNode.objBelong = this;
	return this;
}
dhtmlXTreeObject.prototype._globalIdStorageAdd = function (itemId, itemObject) {
	if (this._globalIdStorageFind(itemId, 1, 1)) {
		itemId = itemId + "_" + (new Date()).valueOf();
		return this._globalIdStorageAdd(itemId, itemObject);
	}
	this._idpull[itemId] = itemObject;
	this._pullSize++;
	return itemId;
};
dhtmlXTreeObject.prototype._globalIdStorageSub = function (itemId) {
	if (this._idpull[itemId]) {
		this._unselectItem(this._idpull[itemId]);
		this._idpull[itemId] = null;
		this._pullSize--;
	}
	if ((this._locker) && (this._locker[itemId])) {
		this._locker[itemId] = false;
	}
};
dhtmlXTreeObject.prototype._globalIdStorageFind = function (itemId, skipXMLSearch, skipParsing, isreparse) {
	var z = this._idpull[itemId];
	if (z) {
		if ((z.unParsed) && (!skipParsing)) {
			this.reParse(z, 0);
		}
		if (this._srnd && !z.htmlNode) {
			this._buildSRND(z, skipParsing);
		}
		if ((isreparse) && (this._edsbpsA)) {
			for (var j = 0; j < this._edsbpsA.length; j++) {
				if (this._edsbpsA[j][2] == itemId) {
					dhtmlxError.throwError("getItem", "Requested item still in parsing process.", itemId);
					return null;
				}
			}
		}
		return z;
	}
	if ((this.slowParse) && (itemId != 0) && (!skipXMLSearch)) {
		return this.preParse(itemId);
	} else {
		return null;
	}
};
dhtmlXTreeObject.prototype._getSubItemsXML = function (p) {
	var z = [];
	p.each("item", function (c) {
		z.push(c.get("id"));
	}, this);
	return z.join(",");
};
dhtmlXTreeObject.prototype.enableSmartXMLParsing = function (mode) {
	this.slowParse = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.findXML = function (node, par, val) {
};
dhtmlXTreeObject.prototype._getAllCheckedXML = function (p, list, mode) {
	var z = [];
	if (mode == 2) {
		p.through("item", "checked", mode, function (c) {
			z.push(c.get("id"));
		}, this);
	}
	if (mode == 1) {
		p.through("item", "id", null, function (c) {
			if (c.get("checked") && (c.get("checked") != -1)) {
				z.push(c.get("id"));
			}
		}, this);
	}
	if (mode == 0) {
		p.through("item", "id", null, function (c) {
			if (!c.get("checked") || c.get("checked") == 0) {
				z.push(c.get("id"));
			}
		}, this);
	}
	return list + (list ? "," : "") + z.join(",");
	if (list) {
		return list;
	} else {
		return "";
	}
};
dhtmlXTreeObject.prototype._setSubCheckedXML = function (state, p) {
	var val = state ? "1" : "";
	p.through("item", "id", null, function (c) {
		c.set("checked", val);
	}, this);
};
dhtmlXTreeObject.prototype._getAllScraggyItemsXML = function (p, x) {
	var z = [];
	var fff = function (c) {
		if (!c.sub_exists("item")) {
			z.push(c.get("id"));
		} else {
			c.each("item", fff, this);
		}
	};
	fff(p);
	return z.join(",");
};
dhtmlXTreeObject.prototype._getAllFatItemsXML = function (p, x) {
	var z = [];
	var fff = function (c) {
		if (!c.sub_exists("item")) {
			return;
		}
		z.push(c.get("id"));
		c.each("item", fff, this);
	};
	fff(p);
	return z.join(",");
};
dhtmlXTreeObject.prototype._getAllSubItemsXML = function (itemId, z, p) {
	var z = [];
	p.through("item", "id", null, function (c) {
		z.push(c.get("id"));
	}, this);
	return z.join(",");
};
dhtmlXTreeObject.prototype.reParse = function (node) {
	var that = this;
	if (!this.parsCount) {
		that.callEvent("onXLS", [that, node.id]);
	}
	this.xmlstate = 1;
	var tmp = node.unParsed;
	node.unParsed = 0;
	this.XMLloadingWarning = 1;
	var oldpid = this.parsingOn;
	var oldmd = this.waitUpdateXML;
	var oldpa = this.parsedArray;
	this.parsedArray = new Array();
	this.waitUpdateXML = false;
	this.parsingOn = node.id;
	this.parsedArray = new Array();
	this.setCheckList = "";
	this._parse(tmp, node.id, 2);
	var chArr = this.setCheckList.split(this.dlmtr);
	for (var i = 0; i < this.parsedArray.length; i++) {
		node.htmlNode.childNodes[0].appendChild(this.parsedArray[i]);
	}
	if (tmp.get("order") && tmp.get("order") != "none") {
		this._reorderBranch(node, tmp.get("order"), true);
	}
	this.oldsmcheck = this.smcheck;
	this.smcheck = false;
	for (var n = 0; n < chArr.length; n++) {
		if (chArr[n]) {
			this.setCheck(chArr[n], 1);
		}
	}
	this.smcheck = this.oldsmcheck;
	this.parsingOn = oldpid;
	this.waitUpdateXML = oldmd;
	this.parsedArray = oldpa;
	this.XMLloadingWarning = 0;
	this._redrawFrom(this, node);
	if (this._srnd && !node._sready) {
		this.prepareSR(node.id);
	}
	return true;
};
dhtmlXTreeObject.prototype.preParse = function (itemId) {
	if (!itemId || !this._p) {
		return null;
	}
	var result = false;
	this._p.clone().through("item", "id", itemId, function (c) {
		this._globalIdStorageFind(c.up().get("id"));
		return result = true;
	}, this);
	if (result) {
		var n = this._globalIdStorageFind(itemId, true, false);
		if (!n) {
			dhtmlxError.throwError("getItem", "The item " + itemId + " not operable. Seems you have non-unique|incorrect IDs in tree's XML.", itemId);
		}
	}
	return n;
};
dhtmlXTreeObject.prototype._escape = function (str) {
	switch (this.utfesc) {
	  case "none":
		return str;
		break;
	  case "utf8":
		return encodeURI(str);
		break;
	  default:
		return escape(str);
		break;
	}
};
dhtmlXTreeObject.prototype._drawNewTr = function (htmlObject, node) {
	var tr = document.createElement("tr");
	var td1 = document.createElement("td");
	var td2 = document.createElement("td");
	td1.appendChild(document.createTextNode(" "));
	td2.colSpan = 3;
	td2.appendChild(htmlObject);
	tr.appendChild(td1);
	tr.appendChild(td2);
	return tr;
};
dhtmlXTreeObject.prototype.loadXMLString = function (xmlString, afterCall) {
	var that = this;
	if (!this.parsCount) {
		this.callEvent("onXLS", [that, null]);
	}
	this.xmlstate = 1;
	if (afterCall) {
		this.XMLLoader.waitCall = afterCall;
	}
	this.XMLLoader.loadXMLString(xmlString);
};
dhtmlXTreeObject.prototype.loadXML = function (file, afterCall) {
	if (this._datamode && this._datamode != "xml") {
		return this["load" + this._datamode.toUpperCase()](file, afterCall);
	}
	var that = this;
	if (!this.parsCount) {
		this.callEvent("onXLS", [that, this._ld_id]);
	}
	this._ld_id = null;
	this.xmlstate = 1;
	this.XMLLoader = new dtmlXMLLoaderObject(this._parseXMLTree, this, true, this.no_cashe);
	if (afterCall) {
		this.XMLLoader.waitCall = afterCall;
	}
	this.XMLLoader.loadXML(file);
};
dhtmlXTreeObject.prototype._attachChildNode = function (parentObject, itemId, itemText, itemActionHandler, image1, image2, image3, optionStr, childs, beforeNode, afterNode) {
	if (beforeNode && beforeNode.parentObject) {
		parentObject = beforeNode.parentObject;
	}
	if (((parentObject.XMLload == 0) && (this.XMLsource)) && (!this.XMLloadingWarning)) {
		parentObject.XMLload = 1;
		this._loadDynXML(parentObject.id);
	}
	var Count = parentObject.childsCount;
	var Nodes = parentObject.childNodes;
	if (afterNode) {
		if (afterNode.tr.previousSibling.previousSibling) {
			beforeNode = afterNode.tr.previousSibling.nodem;
		} else {
			optionStr = optionStr.replace("TOP", "") + ",TOP";
		}
	}
	if (beforeNode) {
		var ik, jk;
		for (ik = 0; ik < Count; ik++) {
			if (Nodes[ik] == beforeNode) {
				for (jk = Count; jk != ik; jk--) {
					Nodes[1 + jk] = Nodes[jk];
				}
				break;
			}
		}
		ik++;
		Count = ik;
	}
	if (optionStr) {
		var tempStr = optionStr.split(",");
		for (var i = 0; i < tempStr.length; i++) {
			switch (tempStr[i]) {
			  case "TOP":
				if (parentObject.childsCount > 0) {
					beforeNode = new Object;
					beforeNode.tr = parentObject.childNodes[0].tr.previousSibling;
				}
				parentObject._has_top = true;
				for (ik = Count; ik > 0; ik--) {
					Nodes[ik] = Nodes[ik - 1];
				}
				Count = 0;
				break;
			}
		}
	}
	var n;
	if (!(n = this._idpull[itemId]) || n.span != -1) {
		n = Nodes[Count] = new dhtmlXTreeItemObject(itemId, itemText, parentObject, this, null, 1);
		itemId = Nodes[Count].id;
		parentObject.childsCount++;
	}
	if (!n.htmlNode) {
		n.label = itemText;
		n.htmlNode = this._createItem((this.checkBoxOff ? 1 : 0), n);
		n.htmlNode.objBelong = n;
	}
	if (image1) {
		n.images[0] = image1;
	}
	if (image2) {
		n.images[1] = image2;
	}
	if (image3) {
		n.images[2] = image3;
	}
	var tr = this._drawNewTr(n.htmlNode);
	if ((this.XMLloadingWarning) || (this._hAdI)) {
		n.htmlNode.parentNode.parentNode.style.display = "none";
	}
	if ((beforeNode) && (beforeNode.tr.nextSibling)) {
		parentObject.htmlNode.childNodes[0].insertBefore(tr, beforeNode.tr.nextSibling);
	} else {
		if (this.parsingOn == parentObject.id) {
			this.parsedArray[this.parsedArray.length] = tr;
		} else {
			parentObject.htmlNode.childNodes[0].appendChild(tr);
		}
	}
	if ((beforeNode) && (!beforeNode.span)) {
		beforeNode = null;
	}
	if (this.XMLsource) {
		if ((childs) && (childs != 0)) {
			n.XMLload = 0;
		} else {
			n.XMLload = 1;
		}
	}
	n.tr = tr;
	tr.nodem = n;
	if (parentObject.itemId == 0) {
		tr.childNodes[0].className = "hiddenRow";
	}
	if ((parentObject._r_logic) || (this._frbtr)) {
		this._setSrc(n.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0], this.imPath + this.radioArray[0]);
	}
	if (optionStr) {
		var tempStr = optionStr.split(",");
		for (var i = 0; i < tempStr.length; i++) {
			switch (tempStr[i]) {
			  case "SELECT":
				this.selectItem(itemId, false);
				break;
			  case "CALL":
				this.selectItem(itemId, true);
				break;
			  case "CHILD":
				n.XMLload = 0;
				break;
			  case "CHECKED":
				if (this.XMLloadingWarning) {
					this.setCheckList += this.dlmtr + itemId;
				} else {
					this.setCheck(itemId, 1);
				}
				break;
			  case "HCHECKED":
				this._setCheck(n, "unsure");
				break;
			  case "OPEN":
				n.openMe = 1;
				break;
			}
		}
	}
	if (!this.XMLloadingWarning) {
		if ((this._getOpenState(parentObject) < 0) && (!this._hAdI)) {
			this.openItem(parentObject.id);
		}
		if (beforeNode) {
			this._correctPlus(beforeNode);
			this._correctLine(beforeNode);
		}
		this._correctPlus(parentObject);
		this._correctLine(parentObject);
		this._correctPlus(n);
		if (parentObject.childsCount >= 2) {
			this._correctPlus(Nodes[parentObject.childsCount - 2]);
			this._correctLine(Nodes[parentObject.childsCount - 2]);
		}
		if (parentObject.childsCount != 2) {
			this._correctPlus(Nodes[0]);
		}
		if (this.tscheck) {
			this._correctCheckStates(parentObject);
		}
		if (this._onradh) {
			if (this.xmlstate == 1) {
				var old = this.onXLE;
				this.onXLE = function (id) {
					this._onradh(itemId);
					if (old) {
						old(id);
					}
				};
			} else {
				this._onradh(itemId);
			}
		}
	}
	return n;
};
dhtmlXTreeObject.prototype.enableContextMenu = function (menu) {
	if (menu) {
		this.cMenu = menu;
	}
};
dhtmlXTreeObject.prototype.setItemContextMenu = function (itemId, cMenu) {
	var l = itemId.toString().split(this.dlmtr);
	for (var i = 0; i < l.length; i++) {
		var temp = this._globalIdStorageFind(l[i]);
		if (!temp) {
			continue;
		}
		temp.cMenu = cMenu;
	}
};
dhtmlXTreeObject.prototype.insertNewItem = function (parentId, itemId, itemText, itemActionHandler, image1, image2, image3, optionStr, children) {
	var parentObject = this._globalIdStorageFind(parentId);
	if (!parentObject) {
		return (-1);
	}
	var nodez = this._attachChildNode(parentObject, itemId, itemText, itemActionHandler, image1, image2, image3, optionStr, children);
	if ((!this.XMLloadingWarning) && (this.childCalc)) {
		this._fixChildCountLabel(parentObject);
	}
	return nodez;
};
dhtmlXTreeObject.prototype.insertNewChild = function (parentId, itemId, itemText, itemActionHandler, image1, image2, image3, optionStr, children) {
	return this.insertNewItem(parentId, itemId, itemText, itemActionHandler, image1, image2, image3, optionStr, children);
};
dhtmlXTreeObject.prototype._parseXMLTree = function (a, b, c, d, xml) {
	var p = new xmlPointer(xml.getXMLTopNode("tree"));
	a._parse(p);
	a._p = p;
};
dhtmlXTreeObject.prototype._parseItem = function (c, temp, preNode, befNode) {
	var id;
	if (this._srnd && (!this._idpull[id = c.get("id")] || !this._idpull[id].span)) {
		this._addItemSRND(temp.id, id, c);
		return;
	}
	var a = c.get_all();
	if ((typeof (this.waitUpdateXML) == "object") && (!this.waitUpdateXML[a.id])) {
		this._parse(c, a.id, 1);
		return;
	}
	if ((a.text === null) || (typeof (a.text) == "undefined")) {
		a.text = c.sub("itemtext").content();
	}
	var zST = [];
	if (a.select) {
		zST.push("SELECT");
	}
	if (a.top) {
		zST.push("TOP");
	}
	if (a.call) {
		nodeAskingCall = a.id;
	}
	if (a.checked == -1) {
		zST.push("HCHECKED");
	} else {
		if (a.checked) {
			zST.push("CHECKED");
		}
	}
	if (a.open) {
		zST.push("OPEN");
	}
	if (this.waitUpdateXML) {
		if (this._globalIdStorageFind(a.id)) {
			var newNode = this.updateItem(a.id, a.text, a.im0, a.im1, a.im2, a.checked);
		} else {
			if (this.npl == 0) {
				zST.push("TOP");
			} else {
				preNode = temp.childNodes[this.npl];
			}
			var newNode = this._attachChildNode(temp, a.id, a.text, 0, a.im0, a.im1, a.im2, zST.join(","), a.child, 0, preNode);
			preNode = null;
		}
	} else {
		var newNode = this._attachChildNode(temp, a.id, a.text, 0, a.im0, a.im1, a.im2, zST.join(","), a.child, (befNode || 0), preNode);
	}
	if (a.tooltip) {
		if (this._dhxTT) {
			dhtmlxTooltip.setTooltip(newNode.span.parentNode.parentNode, a.tooltip);
		} else {
			newNode.span.parentNode.parentNode.title = a.tooltip;
		}
	}
	if (a.style) {
		if (newNode.span.style.cssText) {
			newNode.span.style.cssText += (";" + a.style);
		} else {
			newNode.span.setAttribute("style", newNode.span.getAttribute("style") + ";" + a.style);
		}
	}
	if (a.radio) {
		newNode._r_logic = true;
	}
	if (a.nocheckbox) {
		newNode.span.parentNode.previousSibling.previousSibling.childNodes[0].style.display = "none";
		newNode.nocheckbox = true;
	}
	if (a.disabled) {
		if (a.checked != null) {
			this._setCheck(newNode, convertStringToBoolean(a.checked));
		}
		this.disableCheckbox(newNode, 1);
	}
	newNode._acc = a.child || 0;
	if (this.parserExtension) {
		this.parserExtension._parseExtension(node.childNodes[i], this.parserExtension, a.id, parentId);
	}
	this.setItemColor(newNode, a.aCol, a.sCol);
	if (a.locked == "1") {
		this._lockItem(newNode, true, true);
	}
	if ((a.imwidth) || (a.imheight)) {
		this.setIconSize(a.imwidth, a.imheight, newNode);
	}
	if ((a.closeable == "0") || (a.closeable == "1")) {
		this.setItemCloseable(newNode, a.closeable);
	}
	var zcall = "";
	if (a.topoffset) {
		this.setItemTopOffset(newNode, a.topoffset);
	}
	if ((!this.slowParse) || (typeof (this.waitUpdateXML) == "object")) {
		if (c.sub_exists("item")) {
			zcall = this._parse(c, a.id, 1);
		}
	} else {
		if ((!newNode.childsCount) && c.sub_exists("item")) {
			newNode.unParsed = c.clone();
		}
		c.each("userdata", function (u) {
			this.setUserData(a.id, u.get("name"), u.content());
		}, this);
	}
	if (zcall != "") {
		this.nodeAskingCall = zcall;
	}
	c.each("userdata", function (u) {
		this.setUserData(c.get("id"), u.get("name"), u.content());
	}, this);
};
dhtmlXTreeObject.prototype._parse = function (p, parentId, level, start) {
	if (this._srnd && !this.parentObject.offsetHeight) {
		var self = this;
		return window.setTimeout(function () {
			self._parse(p, parentId, level, start);
		}, 100);
	}
	if (!p.exists()) {
		return;
	}
	this.skipLock = true;
	this.parsCount = this.parsCount ? (this.parsCount + 1) : 1;
	this.XMLloadingWarning = 1;
	this.nodeAskingCall = "";
	if (!parentId) {
		parentId = p.get("id");
		if (p.get("radio")) {
			this.htmlNode._r_logic = true;
		}
		this.parsingOn = parentId;
		this.parsedArray = new Array();
		this.setCheckList = "";
	}
	var temp = this._globalIdStorageFind(parentId);
	if (!temp) {
		return dhtmlxError.throwError("DataStructure", "XML reffers to not existing parent");
	}
	if ((temp.childsCount) && (!start) && (!this._edsbps) && (!temp._has_top)) {
		var preNode = temp.childNodes[temp.childsCount - 1];
	} else {
		var preNode = 0;
	}
	this.npl = 0;
	p.each("item", function (c, i) {
		temp.XMLload = 1;
		if ((this._epgps) && (this._epgpsC == this.npl)) {
			this._setNextPageSign(temp, this.npl + 1 * (start || 0), level, node);
			return -1;
		}
		this._parseItem(c, temp, preNode);
		if ((this._edsbps) && (this.npl == this._edsbpsC)) {
			this._distributedStart(p, i + 1, parentId, level, temp.childsCount);
			return -1;
		}
		this.npl++;
	}, this, start);
	if (!level) {
		p.each("userdata", function (u) {
			this.setUserData(p.get("id"), u.get("name"), u.content());
		}, this);
		temp.XMLload = 1;
		if (this.waitUpdateXML) {
			this.waitUpdateXML = false;
			for (var i = temp.childsCount - 1; i >= 0; i--) {
				if (temp.childNodes[i]._dmark) {
					this.deleteItem(temp.childNodes[i].id);
				}
			}
		}
		var parsedNodeTop = this._globalIdStorageFind(this.parsingOn);
		for (var i = 0; i < this.parsedArray.length; i++) {
			temp.htmlNode.childNodes[0].appendChild(this.parsedArray[i]);
		}
		this.lastLoadedXMLId = parentId;
		this.XMLloadingWarning = 0;
		var chArr = this.setCheckList.split(this.dlmtr);
		for (var n = 0; n < chArr.length; n++) {
			if (chArr[n]) {
				this.setCheck(chArr[n], 1);
			}
		}
		if ((this.XMLsource) && (this.tscheck) && (this.smcheck) && (temp.id != this.rootId)) {
			if (temp.checkstate === 0) {
				this._setSubChecked(0, temp);
			} else {
				if (temp.checkstate === 1) {
					this._setSubChecked(1, temp);
				}
			}
		}
		if (this.onXLE) {
			this.onXLE(this, parentId);
		}
		this._redrawFrom(this, null, start);
		if (p.get("order") && p.get("order") != "none") {
			this._reorderBranch(temp, p.get("order"), true);
		}
		if (this.nodeAskingCall != "") {
			this.selectItem(this.nodeAskingCall, true);
		}
		if (this._branchUpdate) {
			this._branchUpdateNext(p);
		}
	}
	if (this.parsCount == 1) {
		if ((this.slowParse) && (this.parsingOn == this.rootId)) {
			p.through("item", "open", null, function (c) {
				this.openItem(c.get("id"));
			}, this);
		}
		this.parsingOn = null;
		if ((!this._edsbps) || (!this._edsbpsA.length)) {
			var that = this;
			window.setTimeout(function () {
				that.callEvent("onXLE", [that, parentId]);
			}, 1);
			this.xmlstate = 0;
		}
		this.skipLock = false;
	}
	this.parsCount--;
	var that = this;
	if (this._edsbps) {
		window.setTimeout(function () {
			that._distributedStep(parentId);
		}, this._edsbpsD);
	}
	if ((this._epgps) && (start)) {
		this._setPrevPageSign(temp, (start || 0), level, node);
	}
	return this.nodeAskingCall;
};
dhtmlXTreeObject.prototype._branchUpdateNext = function (p) {
	p.each("item", function (c) {
		var nid = c.get("id");
		if (this._idpull[nid] && (!this._idpull[nid].XMLload)) {
			return;
		}
		this._branchUpdate++;
		this.smartRefreshItem(c.get("id"), c);
	}, this);
	this._branchUpdate--;
};
dhtmlXTreeObject.prototype.checkUserData = function (node, parentId) {
	if ((node.nodeType == 1) && (node.tagName == "userdata")) {
		var name = node.getAttribute("name");
		if ((name) && (node.childNodes[0])) {
			this.setUserData(parentId, name, node.childNodes[0].data);
		}
	}
};
dhtmlXTreeObject.prototype._redrawFrom = function (dhtmlObject, itemObject, start, visMode) {
	if (!itemObject) {
		var tempx = dhtmlObject._globalIdStorageFind(dhtmlObject.lastLoadedXMLId);
		dhtmlObject.lastLoadedXMLId = -1;
		if (!tempx) {
			return 0;
		}
	} else {
		tempx = itemObject;
	}
	var acc = 0;
	for (var i = (start ? start - 1 : 0); i < tempx.childsCount; i++) {
		if ((!this._branchUpdate) || (this._getOpenState(tempx) == 1)) {
			if ((!itemObject) || (visMode == 1)) {
				tempx.childNodes[i].htmlNode.parentNode.parentNode.style.display = "";
			}
		}
		if (tempx.childNodes[i].openMe == 1) {
			this._openItem(tempx.childNodes[i]);
			tempx.childNodes[i].openMe = 0;
		}
		dhtmlObject._redrawFrom(dhtmlObject, tempx.childNodes[i]);
		if (this.childCalc != null) {
			if ((tempx.childNodes[i].unParsed) || ((!tempx.childNodes[i].XMLload) && (this.XMLsource))) {
				if (tempx.childNodes[i]._acc) {
					tempx.childNodes[i].span.innerHTML = tempx.childNodes[i].label + this.htmlcA + tempx.childNodes[i]._acc + this.htmlcB;
				} else {
					tempx.childNodes[i].span.innerHTML = tempx.childNodes[i].label;
				}
			}
			if ((tempx.childNodes[i].childNodes.length) && (this.childCalc)) {
				if (this.childCalc == 1) {
					tempx.childNodes[i].span.innerHTML = tempx.childNodes[i].label + this.htmlcA + tempx.childNodes[i].childsCount + this.htmlcB;
				}
				if (this.childCalc == 2) {
					var zCount = tempx.childNodes[i].childsCount - (tempx.childNodes[i].pureChilds || 0);
					if (zCount) {
						tempx.childNodes[i].span.innerHTML = tempx.childNodes[i].label + this.htmlcA + zCount + this.htmlcB;
					}
					if (tempx.pureChilds) {
						tempx.pureChilds++;
					} else {
						tempx.pureChilds = 1;
					}
				}
				if (this.childCalc == 3) {
					tempx.childNodes[i].span.innerHTML = tempx.childNodes[i].label + this.htmlcA + tempx.childNodes[i]._acc + this.htmlcB;
				}
				if (this.childCalc == 4) {
					var zCount = tempx.childNodes[i]._acc;
					if (zCount) {
						tempx.childNodes[i].span.innerHTML = tempx.childNodes[i].label + this.htmlcA + zCount + this.htmlcB;
					}
				}
			} else {
				if (this.childCalc == 4) {
					acc++;
				}
			}
			acc += tempx.childNodes[i]._acc;
			if (this.childCalc == 3) {
				acc++;
			}
		}
	}
	if ((!tempx.unParsed) && ((tempx.XMLload) || (!this.XMLsource))) {
		tempx._acc = acc;
	}
	dhtmlObject._correctLine(tempx);
	dhtmlObject._correctPlus(tempx);
	if ((this.childCalc) && (!itemObject)) {
		dhtmlObject._fixChildCountLabel(tempx);
	}
};
dhtmlXTreeObject.prototype._createSelf = function () {
	var div = document.createElement("div");
	div.className = "containerTableStyle";
	div.style.width = this.width;
	div.style.height = this.height;
	div.style.paddingLeft = this.paddingLeft;
	div.style.paddingTop = this.paddingTop;
	div.style.paddingRight = this.paddingRight;
	div.style.paddingBottom = this.paddingBottom;
	this.parentObject.appendChild(div);
	return div;
};
dhtmlXTreeObject.prototype._xcloseAll = function (itemObject) {
	if (itemObject.unParsed) {
		return;
	}
	if (this.rootId != itemObject.id) {
		var Nodes = itemObject.htmlNode.childNodes[0].childNodes;
		var Count = Nodes.length;
		for (var i = 1; i < Count; i++) {
			Nodes[i].style.display = "none";
		}
		this._correctPlus(itemObject);
	}
	for (var i = 0; i < itemObject.childsCount; i++) {
		if (itemObject.childNodes[i].childsCount) {
			this._xcloseAll(itemObject.childNodes[i]);
		}
	}
};
dhtmlXTreeObject.prototype._xopenAll = function (itemObject) {
	this._HideShow(itemObject, 2);
	for (var i = 0; i < itemObject.childsCount; i++) {
		this._xopenAll(itemObject.childNodes[i]);
	}
};
dhtmlXTreeObject.prototype._correctPlus = function (itemObject) {
	if (!itemObject.htmlNode) {
		return;
	}
	var imsrc = itemObject.htmlNode.childNodes[0].childNodes[0].childNodes[0].lastChild;
	var imsrc2 = itemObject.htmlNode.childNodes[0].childNodes[0].childNodes[2].childNodes[0];
	var workArray = this.lineArray;
	if ((this.XMLsource) && (!itemObject.XMLload)) {
		var workArray = this.plusArray;
		this._setSrc(imsrc2, this.imPath + itemObject.images[2]);
		if (this._txtimg) {
			return (imsrc.innerHTML = "[+]");
		}
	} else {
		if ((itemObject.childsCount) || (itemObject.unParsed)) {
			if ((itemObject.htmlNode.childNodes[0].childNodes[1]) && (itemObject.htmlNode.childNodes[0].childNodes[1].style.display != "none")) {
				if (!itemObject.wsign) {
					var workArray = this.minusArray;
				}
				this._setSrc(imsrc2, this.imPath + itemObject.images[1]);
				if (this._txtimg) {
					return (imsrc.innerHTML = "[-]");
				}
			} else {
				if (!itemObject.wsign) {
					var workArray = this.plusArray;
				}
				this._setSrc(imsrc2, this.imPath + itemObject.images[2]);
				if (this._txtimg) {
					return (imsrc.innerHTML = "[+]");
				}
			}
		} else {
			this._setSrc(imsrc2, this.imPath + itemObject.images[0]);
		}
	}
	var tempNum = 2;
	if (!itemObject.treeNod.treeLinesOn) {
		this._setSrc(imsrc, this.imPath + workArray[3]);
	} else {
		if (itemObject.parentObject) {
			tempNum = this._getCountStatus(itemObject.id, itemObject.parentObject);
		}
		this._setSrc(imsrc, this.imPath + workArray[tempNum]);
	}
};
dhtmlXTreeObject.prototype._correctLine = function (itemObject) {
	if (!itemObject.htmlNode) {
		return;
	}
	var sNode = itemObject.parentObject;
	if (sNode) {
		if ((this._getLineStatus(itemObject.id, sNode) == 0) || (!this.treeLinesOn)) {
			for (var i = 1; i <= itemObject.childsCount; i++) {
				if (!itemObject.htmlNode.childNodes[0].childNodes[i]) {
					break;
				}
				itemObject.htmlNode.childNodes[0].childNodes[i].childNodes[0].style.backgroundImage = "";
				itemObject.htmlNode.childNodes[0].childNodes[i].childNodes[0].style.backgroundRepeat = "";
			}
		} else {
			for (var i = 1; i <= itemObject.childsCount; i++) {
				if (!itemObject.htmlNode.childNodes[0].childNodes[i]) {
					break;
				}
				itemObject.htmlNode.childNodes[0].childNodes[i].childNodes[0].style.backgroundImage = "url(" + this.imPath + this.lineArray[5] + ")";
				itemObject.htmlNode.childNodes[0].childNodes[i].childNodes[0].style.backgroundRepeat = "repeat-y";
			}
		}
	}
};
dhtmlXTreeObject.prototype._getCountStatus = function (itemId, itemObject) {
	if (itemObject.childsCount <= 1) {
		if (itemObject.id == this.rootId) {
			return 4;
		} else {
			return 0;
		}
	}
	if (itemObject.childNodes[0].id == itemId) {
		if (!itemObject.id) {
			return 2;
		} else {
			return 1;
		}
	}
	if (itemObject.childNodes[itemObject.childsCount - 1].id == itemId) {
		return 0;
	}
	return 1;
};
dhtmlXTreeObject.prototype._getLineStatus = function (itemId, itemObject) {
	if (itemObject.childNodes[itemObject.childsCount - 1].id == itemId) {
		return 0;
	}
	return 1;
};
dhtmlXTreeObject.prototype._HideShow = function (itemObject, mode) {
	if ((this.XMLsource) && (!itemObject.XMLload)) {
		if (mode == 1) {
			return;
		}
		itemObject.XMLload = 1;
		this._loadDynXML(itemObject.id);
		return;
	}
	if (itemObject.unParsed) {
		this.reParse(itemObject);
	}
	var Nodes = itemObject.htmlNode.childNodes[0].childNodes;
	var Count = Nodes.length;
	if (Count > 1) {
		if (((Nodes[1].style.display != "none") || (mode == 1)) && (mode != 2)) {
			this.allTree.childNodes[0].border = "1";
			this.allTree.childNodes[0].border = "0";
			nodestyle = "none";
		} else {
			nodestyle = "";
		}
		for (var i = 1; i < Count; i++) {
			Nodes[i].style.display = nodestyle;
		}
	}
	this._correctPlus(itemObject);
};
dhtmlXTreeObject.prototype._getOpenState = function (itemObject) {
	var z = itemObject.htmlNode.childNodes[0].childNodes;
	if (z.length <= 1) {
		return 0;
	}
	if (z[1].style.display != "none") {
		return 1;
	} else {
		return -1;
	}
};
dhtmlXTreeObject.prototype.onRowClick2 = function () {
	var that = this.parentObject.treeNod;
	if (!that.callEvent("onDblClick", [this.parentObject.id, that])) {
		return 0;
	}
	if ((this.parentObject.closeble) && (this.parentObject.closeble != "0")) {
		that._HideShow(this.parentObject);
	} else {
		that._HideShow(this.parentObject, 2);
	}
	if (that.checkEvent("onOpenEnd")) {
		if (!that.xmlstate) {
			that.callEvent("onOpenEnd", [this.parentObject.id, that._getOpenState(this.parentObject)]);
		} else {
			that._oie_onXLE.push(that.onXLE);
			that.onXLE = that._epnFHe;
		}
	}
};
dhtmlXTreeObject.prototype.onRowClick = function () {
	var that = this.parentObject.treeNod;
	if (!that.callEvent("onOpenStart", [this.parentObject.id, that._getOpenState(this.parentObject)])) {
		return 0;
	}
	if ((this.parentObject.closeble) && (this.parentObject.closeble != "0")) {
		that._HideShow(this.parentObject);
	} else {
		that._HideShow(this.parentObject, 2);
	}
	if (that.checkEvent("onOpenEnd")) {
		if (!that.xmlstate) {
			that.callEvent("onOpenEnd", [this.parentObject.id, that._getOpenState(this.parentObject)]);
		} else {
			that._oie_onXLE.push(that.onXLE);
			that.onXLE = that._epnFHe;
		}
	}
};
dhtmlXTreeObject.prototype._epnFHe = function (that, id, flag) {
	if (id != this.rootId) {
		this.callEvent("onOpenEnd", [id, that.getOpenState(id)]);
	}
	that.onXLE = that._oie_onXLE.pop();
	if (!flag && !that._oie_onXLE.length) {
		if (that.onXLE) {
			that.onXLE(that, id);
		}
	}
};
dhtmlXTreeObject.prototype.onRowClickDown = function (e) {
	e = e || window.event;
	var that = this.parentObject.treeNod;
	that._selectItem(this.parentObject, e);
};
dhtmlXTreeObject.prototype.getSelectedItemId = function () {
	var str = new Array();
	for (var i = 0; i < this._selected.length; i++) {
		str[i] = this._selected[i].id;
	}
	return (str.join(this.dlmtr));
};
dhtmlXTreeObject.prototype._selectItem = function (node, e) {
	if (this._onSSCF) {
		this._onSSCFold = this.getSelectedItemId();
	}
	if ((!this._amsel) || (!e) || ((!e.ctrlKey) && (!e.shiftKey))) {
		this._unselectItems();
	}
	if ((node.i_sel) && (this._amsel) && (e) && (e.ctrlKey)) {
		this._unselectItem(node);
	} else {
		if ((!node.i_sel) && ((!this._amselS) || (this._selected.length == 0) || (this._selected[0].parentObject == node.parentObject))) {
			if ((this._amsel) && (e) && (e.shiftKey) && (this._selected.length != 0) && (this._selected[this._selected.length - 1].parentObject == node.parentObject)) {
				var a = this._getIndex(this._selected[this._selected.length - 1]);
				var b = this._getIndex(node);
				if (b < a) {
					var c = a;
					a = b;
					b = c;
				}
				for (var i = a; i <= b; i++) {
					if (!node.parentObject.childNodes[i].i_sel) {
						this._markItem(node.parentObject.childNodes[i]);
					}
				}
			} else {
				this._markItem(node);
			}
		}
	}
	if (this._onSSCF) {
		var z = this.getSelectedItemId();
		if (z != this._onSSCFold) {
			this.callEvent("onSelect", [z]);
		}
	}
};
dhtmlXTreeObject.prototype._markItem = function (node) {
	if (node.scolor) {
		node.span.style.color = node.scolor;
	}
	node.span.className = "selectedTreeRow";
	node.i_sel = true;
	this._selected[this._selected.length] = node;
};
dhtmlXTreeObject.prototype.getIndexById = function (itemId) {
	var z = this._globalIdStorageFind(itemId);
	if (!z) {
		return null;
	}
	return this._getIndex(z);
};
dhtmlXTreeObject.prototype._getIndex = function (w) {
	var z = w.parentObject;
	for (var i = 0; i < z.childsCount; i++) {
		if (z.childNodes[i] == w) {
			return i;
		}
	}
};
dhtmlXTreeObject.prototype._unselectItem = function (node) {
	if ((node) && (node.i_sel)) {
		node.span.className = "standartTreeRow";
		if (node.acolor) {
			node.span.style.color = node.acolor;
		}
		node.i_sel = false;
		for (var i = 0; i < this._selected.length; i++) {
			if (!this._selected[i].i_sel) {
				this._selected.splice(i, 1);
				break;
			}
		}
	}
};
dhtmlXTreeObject.prototype._unselectItems = function () {
	for (var i = 0; i < this._selected.length; i++) {
		var node = this._selected[i];
		node.span.className = "standartTreeRow";
		if (node.acolor) {
			node.span.style.color = node.acolor;
		}
		node.i_sel = false;
	}
	this._selected = new Array();
};
dhtmlXTreeObject.prototype.onRowSelect = function (e, htmlObject, mode) {
	e = e || window.event;
	var obj = this.parentObject;
	if (htmlObject) {
		obj = htmlObject.parentObject;
	}
	var that = obj.treeNod;
	var lastId = that.getSelectedItemId();
	if ((!e) || (!e.skipUnSel)) {
		that._selectItem(obj, e);
	}
	if (!mode) {
		if ((e) && (e.button == 2)) {
			that.callEvent("onRightClick", [obj.id, e]);
		}
		if (obj.actionHandler) {
			obj.actionHandler(obj.id, lastId);
		} else {
			that.callEvent("onClick", [obj.id, lastId]);
		}
	}
};
dhtmlXTreeObject.prototype._correctCheckStates = function (dhtmlObject) {
	if (!this.tscheck) {
		return;
	}
	if (!dhtmlObject) {
		return;
	}
	if (dhtmlObject.id == this.rootId) {
		return;
	}
	var act = dhtmlObject.childNodes;
	var flag1 = 0;
	var flag2 = 0;
	if (dhtmlObject.childsCount == 0) {
		return;
	}
	for (var i = 0; i < dhtmlObject.childsCount; i++) {
		if (act[i].dscheck) {
			continue;
		}
		if (act[i].checkstate == 0) {
			flag1 = 1;
		} else {
			if (act[i].checkstate == 1) {
				flag2 = 1;
			} else {
				flag1 = 1;
				flag2 = 1;
				break;
			}
		}
	}
	if ((flag1) && (flag2)) {
		this._setCheck(dhtmlObject, "unsure");
	} else {
		if (flag1) {
			this._setCheck(dhtmlObject, false);
		} else {
			this._setCheck(dhtmlObject, true);
		}
	}
	this._correctCheckStates(dhtmlObject.parentObject);
};
dhtmlXTreeObject.prototype.onCheckBoxClick = function (e) {
	if (!this.treeNod.callEvent("onBeforeCheck", [this.parentObject.id, this.parentObject.checkstate])) {
		return;
	}
	if (this.parentObject.dscheck) {
		return true;
	}
	if (this.treeNod.tscheck) {
		if (this.parentObject.checkstate == 1) {
			this.treeNod._setSubChecked(false, this.parentObject);
		} else {
			this.treeNod._setSubChecked(true, this.parentObject);
		}
	} else {
		if (this.parentObject.checkstate == 1) {
			this.treeNod._setCheck(this.parentObject, false);
		} else {
			this.treeNod._setCheck(this.parentObject, true);
		}
	}
	this.treeNod._correctCheckStates(this.parentObject.parentObject);
	return this.treeNod.callEvent("onCheck", [this.parentObject.id, this.parentObject.checkstate]);
};
dhtmlXTreeObject.prototype._createItem = function (acheck, itemObject, mode) {

	var table = document.createElement("table");
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	if (this.hfMode) {
		table.style.tableLayout = "fixed";
	}
	table.style.margin = 0;
	table.style.padding = 0;	
	var tbody = document.createElement("tbody");
	var tr = document.createElement("tr");
	var td1 = document.createElement("td");
	td1.className = "standartTreeImage";
	if (this._txtimg) {
		var img0 = document.createElement("div");
		td1.appendChild(img0);
		img0.className = "dhx_tree_textSign";
	} else {
		var img0 = this._getImg(itemObject.id);
		img0.border = "0";
		if (img0.tagName == "IMG") {
			img0.align = "absmiddle";
		}
		td1.appendChild(img0);
		img0.style.padding = 0;
		img0.style.margin = 0;
		img0.style.width = this.def_line_img_x;
		img0.style.height = this.def_line_img_y;
	}
	var td11 = document.createElement("td");
	var inp = this._getImg(this.cBROf ? this.rootId : itemObject.id);
	inp.checked = 0;
	this._setSrc(inp, this.imPath + this.checkArray[0]);
	inp.style.width = "16px";
	inp.style.height = "16px";
	if (!acheck) {
		(((_isOpera) || (_isKHTML)) ? td11 : inp).style.display = "none";
	}
	td11.appendChild(inp);
	if ((!this.cBROf) && (inp.tagName == "IMG")) {
		inp.align = "absmiddle";
	}
	inp.onclick = this.onCheckBoxClick;
	inp.treeNod = this;
	inp.parentObject = itemObject;
	td11.width = "20px";
	var td12 = document.createElement("td");
	td12.className = "standartTreeImage";
	var img = this._getImg(this.timgen ? itemObject.id : this.rootId);
	img.onmousedown = this._preventNsDrag;
	img.ondragstart = this._preventNsDrag;
	img.border = "0";
	if (this._aimgs) {
		img.parentObject = itemObject;
		if (img.tagName == "IMG") {
			img.align = "absmiddle";
		}
		img.onclick = this.onRowSelect;
	}
	if (!mode) {
		this._setSrc(img, this.imPath + this.imageArray[0]);
	}
	td12.appendChild(img);
	img.style.padding = 0;
	img.style.margin = 0;
	if (this.timgen) {
		img.style.width = this.def_img_x;
		img.style.height = this.def_img_y;
	} else {
		img.style.width = "0px";
		img.style.height = "0px";
		if (_isOpera) {
			td12.style.display = "none";
		}
	}
	var td2 = document.createElement("td");
	td2.className = "standartTreeRow";
	itemObject.span = document.createElement("span");
	itemObject.span.className = "standartTreeRow";
	if (this.mlitems) {
		itemObject.span.style.width = this.mlitems;
		itemObject.span.style.display = "block";
	} else {
		td2.noWrap = true;
	}
	if (!_isKHTML) {
		td2.style.width = "100%";
	}
	itemObject.span.innerHTML = itemObject.label;
	td2.appendChild(itemObject.span);
	td2.parentObject = itemObject;
	td1.parentObject = itemObject;
	td2.onclick = this.onRowSelect;
	td1.onclick = this.onRowClick;
	td2.ondblclick = this.onRowClick2;
	if (this.ettip) {
		if (this._dhxTT) {
			dhtmlxTooltip.setTooltip(tr, itemObject.label);
		} else {
			tr.title = itemObject.label;
		}
	}
	if (this.dragAndDropOff) {
		if (this._aimgs) {
			this.dragger.addDraggableItem(td12, this);
			td12.parentObject = itemObject;
		}
		this.dragger.addDraggableItem(td2, this);
	}
	itemObject.span.style.paddingLeft = "5px";
	itemObject.span.style.paddingRight = "5px";
	td2.style.verticalAlign = "";
	td2.style.fontSize = "10pt";
	td2.style.cursor = this.style_pointer;
	tr.appendChild(td1);
	tr.appendChild(td11);
	tr.appendChild(td12);
	tr.appendChild(td2);
	tbody.appendChild(tr);
	table.appendChild(tbody);
	if (this.ehlt) {
		tr.onmousemove = this._itemMouseIn;
		tr[(_isIE) ? "onmouseleave" : "onmouseout"] = this._itemMouseOut;
	}
	if (this.checkEvent && this.checkEvent("onRightClick")) {
		tr.oncontextmenu = Function("e", "this.childNodes[0].parentObject.treeNod.callEvent('onRightClick',[this.childNodes[0].parentObject.id,(e||window.event)]);return false;");
	}
	return table;
};
dhtmlXTreeObject.prototype.setImagePath = function (newPath) {
	this.imPath = newPath;
};
dhtmlXTreeObject.prototype._getLeafCount = function (itemNode) {
	var a = 0;
	for (var b = 0; b < itemNode.childsCount; b++) {
		if (itemNode.childNodes[b].childsCount == 0) {
			a++;
		}
	}
	return a;
};
dhtmlXTreeObject.prototype._getChildCounterValue = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	if ((temp.unParsed) || ((!temp.XMLload) && (this.XMLsource))) {
		return temp._acc;
	}
	switch (this.childCalc) {
	  case 1:
		return temp.childsCount;
		break;
	  case 2:
		return this._getLeafCount(temp);
		break;
	  case 3:
		return temp._acc;
		break;
	  case 4:
		return temp._acc;
		break;
	}
};
dhtmlXTreeObject.prototype._fixChildCountLabel = function (itemNode, index) {
	if (this.childCalc == null) {
		return;
	}
	if ((itemNode.unParsed) || ((!itemNode.XMLload) && (this.XMLsource))) {
		if (itemNode._acc) {
			itemNode.span.innerHTML = itemNode.label + this.htmlcA + itemNode._acc + this.htmlcB;
		} else {
			itemNode.span.innerHTML = itemNode.label;
		}
		return;
	}
	switch (this.childCalc) {
	  case 1:
		if (itemNode.childsCount != 0) {
			itemNode.span.innerHTML = itemNode.label + this.htmlcA + itemNode.childsCount + this.htmlcB;
		} else {
			itemNode.span.innerHTML = itemNode.label;
		}
		break;
	  case 2:
		var z = this._getLeafCount(itemNode);
		if (z != 0) {
			itemNode.span.innerHTML = itemNode.label + this.htmlcA + z + this.htmlcB;
		} else {
			itemNode.span.innerHTML = itemNode.label;
		}
		break;
	  case 3:
		if (itemNode.childsCount != 0) {
			var bcc = 0;
			for (var a = 0; a < itemNode.childsCount; a++) {
				if (!itemNode.childNodes[a]._acc) {
					itemNode.childNodes[a]._acc = 0;
				}
				bcc += itemNode.childNodes[a]._acc * 1;
			}
			bcc += itemNode.childsCount * 1;
			itemNode.span.innerHTML = itemNode.label + this.htmlcA + bcc + this.htmlcB;
			itemNode._acc = bcc;
		} else {
			itemNode.span.innerHTML = itemNode.label;
			itemNode._acc = 1;
		}
		if ((itemNode.parentObject) && (itemNode.parentObject != this.htmlNode)) {
			this._fixChildCountLabel(itemNode.parentObject);
		}
		break;
	  case 4:
		if (itemNode.childsCount != 0) {
			var bcc = 0;
			for (var a = 0; a < itemNode.childsCount; a++) {
				if (!itemNode.childNodes[a]._acc) {
					itemNode.childNodes[a]._acc = 1;
				}
				bcc += itemNode.childNodes[a]._acc * 1;
			}
			itemNode.span.innerHTML = itemNode.label + this.htmlcA + bcc + this.htmlcB;
			itemNode._acc = bcc;
		} else {
			itemNode.span.innerHTML = itemNode.label;
			itemNode._acc = 1;
		}
		if ((itemNode.parentObject) && (itemNode.parentObject != this.htmlNode)) {
			this._fixChildCountLabel(itemNode.parentObject);
		}
		break;
	}
};
dhtmlXTreeObject.prototype.setChildCalcMode = function (mode) {
	switch (mode) {
	  case "child":
		this.childCalc = 1;
		break;
	  case "leafs":
		this.childCalc = 2;
		break;
	  case "childrec":
		this.childCalc = 3;
		break;
	  case "leafsrec":
		this.childCalc = 4;
		break;
	  case "disabled":
		this.childCalc = null;
		break;
	  default:
		this.childCalc = 4;
	}
};
dhtmlXTreeObject.prototype.setChildCalcHTML = function (htmlA, htmlB) {
	this.htmlcA = htmlA;
	this.htmlcB = htmlB;
};
dhtmlXTreeObject.prototype.setOnRightClickHandler = function (func) {
	this.attachEvent("onRightClick", func);
};
dhtmlXTreeObject.prototype.setOnClickHandler = function (func) {
	this.attachEvent("onClick", func);
};
dhtmlXTreeObject.prototype.setOnSelectStateChange = function (func) {
	this.attachEvent("onSelect", func);
	this._onSSCF = true;
};
dhtmlXTreeObject.prototype.setXMLAutoLoading = function (filePath) {
	this.XMLsource = filePath;
};
dhtmlXTreeObject.prototype.setOnCheckHandler = function (func) {
	this.attachEvent("onCheck", func);
};
dhtmlXTreeObject.prototype.setOnOpenHandler = function (func) {
	this.attachEvent("onOpenStart", func);
};
dhtmlXTreeObject.prototype.setOnOpenStartHandler = function (func) {
	this.attachEvent("onOpenStart", func);
};
dhtmlXTreeObject.prototype.setOnOpenEndHandler = function (func) {
	this.attachEvent("onOpenEnd", func);
};
dhtmlXTreeObject.prototype.setOnDblClickHandler = function (func) {
	this.attachEvent("onDblClick", func);
};
dhtmlXTreeObject.prototype.openAllItems = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	this._xopenAll(temp);
};
dhtmlXTreeObject.prototype.getOpenState = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return "";
	}
	return this._getOpenState(temp);
};
dhtmlXTreeObject.prototype.closeAllItems = function (itemId) {
	if (itemId === window.undefined) {
		itemId = this.rootId;
	}
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	this._xcloseAll(temp);
	this.allTree.childNodes[0].border = "1";
	this.allTree.childNodes[0].border = "0";
};
dhtmlXTreeObject.prototype.setUserData = function (itemId, name, value) {
	var sNode = this._globalIdStorageFind(itemId, 0, true);
	if (!sNode) {
		return;
	}
	if (name == "hint") {
		if (this._dhxTT) {
			dhtmlxTooltip.setTooltip(sNode.htmlNode.childNodes[0].childNodes[0], value);
		} else {
			sNode.htmlNode.childNodes[0].childNodes[0].title = value;
		}
	}
	if (typeof (sNode.userData["t_" + name]) == "undefined") {
		if (!sNode._userdatalist) {
			sNode._userdatalist = name;
		} else {
			sNode._userdatalist += "," + name;
		}
	}
	sNode.userData["t_" + name] = value;
};
dhtmlXTreeObject.prototype.getUserData = function (itemId, name) {
	var sNode = this._globalIdStorageFind(itemId, 0, true);
	if (!sNode) {
		return;
	}
	return sNode.userData["t_" + name];
};
dhtmlXTreeObject.prototype.getItemColor = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	var res = new Object();
	if (temp.acolor) {
		res.acolor = temp.acolor;
	}
	if (temp.acolor) {
		res.scolor = temp.scolor;
	}
	return res;
};
dhtmlXTreeObject.prototype.setItemColor = function (itemId, defaultColor, selectedColor) {
	if ((itemId) && (itemId.span)) {
		var temp = itemId;
	} else {
		var temp = this._globalIdStorageFind(itemId);
	}
	if (!temp) {
		return 0;
	} else {
		if (temp.i_sel) {
			if (selectedColor) {
				temp.span.style.color = selectedColor;
			}
		} else {
			if (defaultColor) {
				temp.span.style.color = defaultColor;
			}
		}
		if (selectedColor) {
			temp.scolor = selectedColor;
		}
		if (defaultColor) {
			temp.acolor = defaultColor;
		}
	}
};
dhtmlXTreeObject.prototype.getItemText = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	return (temp.htmlNode.childNodes[0].childNodes[0].childNodes[3].childNodes[0].innerHTML);
};
dhtmlXTreeObject.prototype.getParentId = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if ((!temp) || (!temp.parentObject)) {
		return "";
	}
	return temp.parentObject.id;
};
dhtmlXTreeObject.prototype.changeItemId = function (itemId, newItemId) {
	if (itemId == newItemId) {
		return;
	}
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	temp.id = newItemId;
	temp.span.contextMenuId = newItemId;
	this._idpull[newItemId] = this._idpull[itemId];
	delete this._idpull[itemId];
};
dhtmlXTreeObject.prototype.doCut = function () {
	if (this.nodeCut) {
		this.clearCut();
	}
	this.nodeCut = (new Array()).concat(this._selected);
	for (var i = 0; i < this.nodeCut.length; i++) {
		var tempa = this.nodeCut[i];
		tempa._cimgs = new Array();
		tempa._cimgs[0] = tempa.images[0];
		tempa._cimgs[1] = tempa.images[1];
		tempa._cimgs[2] = tempa.images[2];
		tempa.images[0] = tempa.images[1] = tempa.images[2] = this.cutImage;
		this._correctPlus(tempa);
	}
};
dhtmlXTreeObject.prototype.doPaste = function (itemId) {
	var tobj = this._globalIdStorageFind(itemId);
	if (!tobj) {
		return 0;
	}
	for (var i = 0; i < this.nodeCut.length; i++) {
		if (this._checkPNodes(tobj, this.nodeCut[i])) {
			continue;
		}
		this._moveNode(this.nodeCut[i], tobj);
	}
	this.clearCut();
};
dhtmlXTreeObject.prototype.clearCut = function () {
	for (var i = 0; i < this.nodeCut.length; i++) {
		var tempa = this.nodeCut[i];
		tempa.images[0] = tempa._cimgs[0];
		tempa.images[1] = tempa._cimgs[1];
		tempa.images[2] = tempa._cimgs[2];
		this._correctPlus(tempa);
	}
	this.nodeCut = new Array();
};
dhtmlXTreeObject.prototype._moveNode = function (itemObject, targetObject) {
	var mode = this.dadmodec;
	if (mode == 1) {
		var z = targetObject;
		if (this.dadmodefix < 0) {
			while (true) {
				z = this._getPrevNode(z);
				if ((z == -1)) {
					z = this.htmlNode;
					break;
				}
				if ((z.tr == 0) || (z.tr.style.display == "") || (!z.parentObject)) {
					break;
				}
			}
			var nodeA = z;
			var nodeB = targetObject;
		} else {
			while (true) {
				z = this._getNextNode(z);
				if ((z == -1)) {
					z = this.htmlNode;
					break;
				}
				if ((z.tr.style.display == "") || (!z.parentObject)) {
					break;
				}
			}
			var nodeB = z;
			var nodeA = targetObject;
		}
		if (this._getNodeLevel(nodeA, 0) > this._getNodeLevel(nodeB, 0)) {
			if (!this.dropLower) {
				return this._moveNodeTo(itemObject, nodeA.parentObject);
			} else {
				if (nodeB.id != this.rootId) {
					return this._moveNodeTo(itemObject, nodeB.parentObject, nodeB);
				} else {
					return this._moveNodeTo(itemObject, this.htmlNode, null);
				}
			}
		} else {
			return this._moveNodeTo(itemObject, nodeB.parentObject, nodeB);
		}
	} else {
		return this._moveNodeTo(itemObject, targetObject);
	}
};
dhtmlXTreeObject.prototype._fixNodesCollection = function (target, zParent) {
	var flag = 0;
	var icount = 0;
	var Nodes = target.childNodes;
	var Count = target.childsCount - 1;
	if (zParent == Nodes[Count]) {
		return;
	}
	for (var i = 0; i < Count; i++) {
		if (Nodes[i] == Nodes[Count]) {
			Nodes[i] = Nodes[i + 1];
			Nodes[i + 1] = Nodes[Count];
		}
	}
	for (var i = 0; i < Count + 1; i++) {
		if (flag) {
			var temp = Nodes[i];
			Nodes[i] = flag;
			flag = temp;
		} else {
			if (Nodes[i] == zParent) {
				flag = Nodes[i];
				Nodes[i] = Nodes[Count];
			}
		}
	}
};
dhtmlXTreeObject.prototype._recreateBranch = function (itemObject, targetObject, beforeNode, level) {
	var i;
	var st = "";
	if (beforeNode) {
		for (i = 0; i < targetObject.childsCount; i++) {
			if (targetObject.childNodes[i] == beforeNode) {
				break;
			}
		}
		if (i != 0) {
			beforeNode = targetObject.childNodes[i - 1];
		} else {
			st = "TOP";
			beforeNode = "";
		}
	}
	var t2 = this._onradh;
	this._onradh = null;
	var newNode = this._attachChildNode(targetObject, itemObject.id, itemObject.label, 0, itemObject.images[0], itemObject.images[1], itemObject.images[2], st, 0, beforeNode);
	newNode._userdatalist = itemObject._userdatalist;
	newNode.userData = itemObject.userData.clone();
	newNode.XMLload = itemObject.XMLload;
	if (t2) {
		this._onradh = t2;
		this._onradh(newNode.id);
	}
	if (itemObject.unParsed) {
		newNode.unParsed = itemObject.unParsed;
		this._correctPlus(newNode);
	} else {
		for (var i = 0; i < itemObject.childsCount; i++) {
			this._recreateBranch(itemObject.childNodes[i], newNode, 0, 1);
		}
	}
	if ((!level) && (this.childCalc)) {
		this._redrawFrom(this, targetObject);
	}
	return newNode;
};
dhtmlXTreeObject.prototype._moveNodeTo = function (itemObject, targetObject, beforeNode) {
	if (itemObject.treeNod._nonTrivialNode) {
		return itemObject.treeNod._nonTrivialNode(this, targetObject, beforeNode, itemObject);
	}
	if (targetObject.mytype) {
		var framesMove = (itemObject.treeNod.lWin != targetObject.lWin);
	} else {
		var framesMove = (itemObject.treeNod.lWin != targetObject.treeNod.lWin);
	}
	if (!this.callEvent("onDrag", [itemObject.id, targetObject.id, (beforeNode ? beforeNode.id : null), itemObject.treeNod, targetObject.treeNod])) {
		return false;
	}
	if ((targetObject.XMLload == 0) && (this.XMLsource)) {
		targetObject.XMLload = 1;
		this._loadDynXML(targetObject.id);
	}
	this.openItem(targetObject.id);
	var oldTree = itemObject.treeNod;
	var c = itemObject.parentObject.childsCount;
	var z = itemObject.parentObject;
	if ((framesMove) || (oldTree.dpcpy)) {
		var _otiid = itemObject.id;
		itemObject = this._recreateBranch(itemObject, targetObject, beforeNode);
		if (!oldTree.dpcpy) {
			oldTree.deleteItem(_otiid);
		}
	} else {
		var Count = targetObject.childsCount;
		var Nodes = targetObject.childNodes;
		if (Count == 0) {
			targetObject._open = true;
		}
		oldTree._unselectItem(itemObject);
		Nodes[Count] = itemObject;
		itemObject.treeNod = targetObject.treeNod;
		targetObject.childsCount++;
		var tr = this._drawNewTr(Nodes[Count].htmlNode);
		if (!beforeNode) {
			targetObject.htmlNode.childNodes[0].appendChild(tr);
			if (this.dadmode == 1) {
				this._fixNodesCollection(targetObject, beforeNode);
			}
		} else {
			targetObject.htmlNode.childNodes[0].insertBefore(tr, beforeNode.tr);
			this._fixNodesCollection(targetObject, beforeNode);
			Nodes = targetObject.childNodes;
		}
	}
	if ((!oldTree.dpcpy) && (!framesMove)) {
		var zir = itemObject.tr;
		if ((document.all) && (navigator.appVersion.search(/MSIE\ 5\.0/gi) != -1)) {
			window.setTimeout(function () {
				zir.parentNode.removeChild(zir);
			}, 250);
		} else {
			itemObject.parentObject.htmlNode.childNodes[0].removeChild(itemObject.tr);
		}
		if ((!beforeNode) || (targetObject != itemObject.parentObject)) {
			for (var i = 0; i < z.childsCount; i++) {
				if (z.childNodes[i].id == itemObject.id) {
					z.childNodes[i] = 0;
					break;
				}
			}
		} else {
			z.childNodes[z.childsCount - 1] = 0;
		}
		oldTree._compressChildList(z.childsCount, z.childNodes);
		z.childsCount--;
	}
	if ((!framesMove) && (!oldTree.dpcpy)) {
		itemObject.tr = tr;
		tr.nodem = itemObject;
		itemObject.parentObject = targetObject;
		if (oldTree != targetObject.treeNod) {
			if (itemObject.treeNod._registerBranch(itemObject, oldTree)) {
				return;
			}
			this._clearStyles(itemObject);
			this._redrawFrom(this, itemObject.parentObject);
		}
		this._correctPlus(targetObject);
		this._correctLine(targetObject);
		this._correctLine(itemObject);
		this._correctPlus(itemObject);
		if (beforeNode) {
			this._correctPlus(beforeNode);
		} else {
			if (targetObject.childsCount >= 2) {
				this._correctPlus(Nodes[targetObject.childsCount - 2]);
				this._correctLine(Nodes[targetObject.childsCount - 2]);
			}
		}
		this._correctPlus(Nodes[targetObject.childsCount - 1]);
		if (this.tscheck) {
			this._correctCheckStates(targetObject);
		}
		if (oldTree.tscheck) {
			oldTree._correctCheckStates(z);
		}
	}
	if (c > 1) {
		oldTree._correctPlus(z.childNodes[c - 2]);
		oldTree._correctLine(z.childNodes[c - 2]);
	}
	oldTree._correctPlus(z);
	oldTree._correctLine(z);
	this._fixChildCountLabel(targetObject);
	oldTree._fixChildCountLabel(z);
	this.callEvent("onDrop", [itemObject.id, targetObject.id, (beforeNode ? beforeNode.id : null), oldTree, targetObject.treeNod]);
	return itemObject.id;
};
dhtmlXTreeObject.prototype._clearStyles = function (itemObject) {
	var td1 = itemObject.htmlNode.childNodes[0].childNodes[0].childNodes[1];
	var td3 = td1.nextSibling.nextSibling;
	itemObject.span.innerHTML = itemObject.label;
	itemObject.i_sel = false;
	if (itemObject._aimgs) {
		this.dragger.removeDraggableItem(td1.nextSibling);
	}
	if (this.checkBoxOff) {
		td1.childNodes[0].style.display = "";
		td1.childNodes[0].onclick = this.onCheckBoxClick;
		this._setSrc(td1.childNodes[0], this.imPath + this.checkArray[itemObject.checkstate]);
	} else {
		td1.childNodes[0].style.display = "none";
	}
	td1.childNodes[0].treeNod = this;
	this.dragger.removeDraggableItem(td3);
	if (this.dragAndDropOff) {
		this.dragger.addDraggableItem(td3, this);
	}
	if (this._aimgs) {
		this.dragger.addDraggableItem(td1.nextSibling, this);
	}
	td3.childNodes[0].className = "standartTreeRow";
	td3.onclick = this.onRowSelect;
	td3.ondblclick = this.onRowClick2;
	td1.previousSibling.onclick = this.onRowClick;
	this._correctLine(itemObject);
	this._correctPlus(itemObject);
	for (var i = 0; i < itemObject.childsCount; i++) {
		this._clearStyles(itemObject.childNodes[i]);
	}
};
dhtmlXTreeObject.prototype._registerBranch = function (itemObject, oldTree) {
	if (oldTree) {
		oldTree._globalIdStorageSub(itemObject.id);
	}
	itemObject.id = this._globalIdStorageAdd(itemObject.id, itemObject);
	itemObject.treeNod = this;
	for (var i = 0; i < itemObject.childsCount; i++) {
		this._registerBranch(itemObject.childNodes[i], oldTree);
	}
	return 0;
};
dhtmlXTreeObject.prototype.enableThreeStateCheckboxes = function (mode) {
	this.tscheck = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.setOnMouseInHandler = function (func) {
	this.ehlt = true;
	this.attachEvent("onMouseIn", func);
};
dhtmlXTreeObject.prototype.setOnMouseOutHandler = function (func) {
	this.ehlt = true;
	this.attachEvent("onMouseOut", func);
};
dhtmlXTreeObject.prototype.enableMercyDrag = function (mode) {
	this.dpcpy = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.enableTreeImages = function (mode) {
	this.timgen = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.enableFixedMode = function (mode) {
	this.hfMode = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.enableCheckBoxes = function (mode, hidden) {
	this.checkBoxOff = convertStringToBoolean(mode);
	this.cBROf = (!(this.checkBoxOff || convertStringToBoolean(hidden)));
};
dhtmlXTreeObject.prototype.setStdImages = function (image1, image2, image3) {
	this.imageArray[0] = image1;
	this.imageArray[1] = image2;
	this.imageArray[2] = image3;
};
dhtmlXTreeObject.prototype.enableTreeLines = function (mode) {
	this.treeLinesOn = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.setImageArrays = function (arrayName, image1, image2, image3, image4, image5) {
	switch (arrayName) {
	  case "plus":
		this.plusArray[0] = image1;
		this.plusArray[1] = image2;
		this.plusArray[2] = image3;
		this.plusArray[3] = image4;
		this.plusArray[4] = image5;
		break;
	  case "minus":
		this.minusArray[0] = image1;
		this.minusArray[1] = image2;
		this.minusArray[2] = image3;
		this.minusArray[3] = image4;
		this.minusArray[4] = image5;
		break;
	}
};
dhtmlXTreeObject.prototype.openItem = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	} else {
		return this._openItem(temp);
	}
};
dhtmlXTreeObject.prototype._openItem = function (item) {
	var state = this._getOpenState(item);
	if ((state < 0) || (((this.XMLsource) && (!item.XMLload)))) {
		if (!this.callEvent("onOpenStart", [item.id, state])) {
			return 0;
		}
		this._HideShow(item, 2);
		if (this.checkEvent("onOpenEnd")) {
			if (this.onXLE == this._epnFHe) {
				this._epnFHe(this, item.id, true);
			}
			if (!this.xmlstate || !this.XMLsource) {
				this.callEvent("onOpenEnd", [item.id, this._getOpenState(item)]);
			} else {
				this._oie_onXLE.push(this.onXLE);
				this.onXLE = this._epnFHe;
			}
		}
	}
	if (item.parentObject) {
		this._openItem(item.parentObject);
	}
};
dhtmlXTreeObject.prototype.closeItem = function (itemId) {
	if (this.rootId == itemId) {
		return 0;
	}
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	if (temp.closeble) {
		this._HideShow(temp, 1);
	}
};
dhtmlXTreeObject.prototype.getLevel = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	return this._getNodeLevel(temp, 0);
};
dhtmlXTreeObject.prototype.setItemCloseable = function (itemId, flag) {
	flag = convertStringToBoolean(flag);
	if ((itemId) && (itemId.span)) {
		var temp = itemId;
	} else {
		var temp = this._globalIdStorageFind(itemId);
	}
	if (!temp) {
		return 0;
	}
	temp.closeble = flag;
};
dhtmlXTreeObject.prototype._getNodeLevel = function (itemObject, count) {
	if (itemObject.parentObject) {
		return this._getNodeLevel(itemObject.parentObject, count + 1);
	}
	return (count);
};
dhtmlXTreeObject.prototype.hasChildren = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	} else {
		if ((this.XMLsource) && (!temp.XMLload)) {
			return true;
		} else {
			return temp.childsCount;
		}
	}
};
dhtmlXTreeObject.prototype._getLeafCount = function (itemNode) {
	var a = 0;
	for (var b = 0; b < itemNode.childsCount; b++) {
		if (itemNode.childNodes[b].childsCount == 0) {
			a++;
		}
	}
	return a;
};
dhtmlXTreeObject.prototype.setItemText = function (itemId, newLabel, newTooltip) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	temp.label = newLabel;
	temp.span.innerHTML = newLabel;
	if (this.childCalc) {
		this._fixChildCountLabel(temp);
	}
	if (this._dhxTT) {
		dhtmlxTooltip.setTooltip(temp.span.parentNode.parentNode, (newTooltip || ""));
	} else {
		temp.span.parentNode.parentNode.title = newTooltip || "";
	}
};
dhtmlXTreeObject.prototype.getItemTooltip = function (itemId) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return "";
	}
	return (temp.span.parentNode.parentNode._dhx_title || temp.span.parentNode.parentNode.title || "");
};
dhtmlXTreeObject.prototype.refreshItem = function (itemId) {
	if (!itemId) {
		itemId = this.rootId;
	}
	var temp = this._globalIdStorageFind(itemId);
	this.deleteChildItems(itemId);
	this._loadDynXML(itemId);
};
dhtmlXTreeObject.prototype.setItemImage2 = function (itemId, image1, image2, image3) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	temp.images[1] = image2;
	temp.images[2] = image3;
	temp.images[0] = image1;
	this._correctPlus(temp);
};
dhtmlXTreeObject.prototype.setItemImage = function (itemId, image1, image2) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	if (image2) {
		temp.images[1] = image1;
		temp.images[2] = image2;
	} else {
		temp.images[0] = image1;
	}
	this._correctPlus(temp);
};
dhtmlXTreeObject.prototype.getSubItems = function (itemId) {
	var temp = this._globalIdStorageFind(itemId, 0, 1);
	if (!temp) {
		return 0;
	}
	if (temp.unParsed) {
		return (this._getSubItemsXML(temp.unParsed));
	}
	var z = "";
	for (i = 0; i < temp.childsCount; i++) {
		if (!z) {
			z = temp.childNodes[i].id;
		} else {
			z += this.dlmtr + temp.childNodes[i].id;
		}
	}
	return z;
};
dhtmlXTreeObject.prototype._getAllScraggyItems = function (node) {
	var z = "";
	for (var i = 0; i < node.childsCount; i++) {
		if ((node.childNodes[i].unParsed) || (node.childNodes[i].childsCount > 0)) {
			if (node.childNodes[i].unParsed) {
				var zb = this._getAllScraggyItemsXML(node.childNodes[i].unParsed, 1);
			} else {
				var zb = this._getAllScraggyItems(node.childNodes[i]);
			}
			if (zb) {
				if (z) {
					z += this.dlmtr + zb;
				} else {
					z = zb;
				}
			}
		} else {
			if (!z) {
				z = node.childNodes[i].id;
			} else {
				z += this.dlmtr + node.childNodes[i].id;
			}
		}
	}
	return z;
};
dhtmlXTreeObject.prototype._getAllFatItems = function (node) {
	var z = "";
	for (var i = 0; i < node.childsCount; i++) {
		if ((node.childNodes[i].unParsed) || (node.childNodes[i].childsCount > 0)) {
			if (!z) {
				z = node.childNodes[i].id;
			} else {
				z += this.dlmtr + node.childNodes[i].id;
			}
			if (node.childNodes[i].unParsed) {
				var zb = this._getAllFatItemsXML(node.childNodes[i].unParsed, 1);
			} else {
				var zb = this._getAllFatItems(node.childNodes[i]);
			}
			if (zb) {
				z += this.dlmtr + zb;
			}
		}
	}
	return z;
};
dhtmlXTreeObject.prototype._getAllSubItems = function (itemId, z, node) {
	if (node) {
		temp = node;
	} else {
		var temp = this._globalIdStorageFind(itemId);
	}
	if (!temp) {
		return 0;
	}
	z = "";
	for (var i = 0; i < temp.childsCount; i++) {
		if (!z) {
			z = temp.childNodes[i].id;
		} else {
			z += this.dlmtr + temp.childNodes[i].id;
		}
		var zb = this._getAllSubItems(0, z, temp.childNodes[i]);
		if (zb) {
			z += this.dlmtr + zb;
		}
	}
	if (temp.unParsed) {
		z = this._getAllSubItemsXML(itemId, z, temp.unParsed);
	}
	return z;
};
dhtmlXTreeObject.prototype.selectItem = function (itemId, mode, preserve) {
	mode = convertStringToBoolean(mode);
	var temp = this._globalIdStorageFind(itemId);
	if ((!temp) || (!temp.parentObject)) {
		return 0;
	}
	if (this.XMLloadingWarning) {
		temp.parentObject.openMe = 1;
	} else {
		this._openItem(temp.parentObject);
	}
	var ze = null;
	if (preserve) {
		ze = new Object;
		ze.ctrlKey = true;
		if (temp.i_sel) {
			ze.skipUnSel = true;
		}
	}
	if (mode) {
		this.onRowSelect(ze, temp.htmlNode.childNodes[0].childNodes[0].childNodes[3], false);
	} else {
		this.onRowSelect(ze, temp.htmlNode.childNodes[0].childNodes[0].childNodes[3], true);
	}
};
dhtmlXTreeObject.prototype.getSelectedItemText = function () {
	var str = new Array();
	for (var i = 0; i < this._selected.length; i++) {
		str[i] = this._selected[i].span.innerHTML;
	}
	return (str.join(this.dlmtr));
};
dhtmlXTreeObject.prototype._compressChildList = function (Count, Nodes) {
	Count--;
	for (var i = 0; i < Count; i++) {
		if (Nodes[i] == 0) {
			Nodes[i] = Nodes[i + 1];
			Nodes[i + 1] = 0;
		}
	}
};
dhtmlXTreeObject.prototype._deleteNode = function (itemId, htmlObject, skip) {
	if ((!htmlObject) || (!htmlObject.parentObject)) {
		return 0;
	}
	var tempos = 0;
	var tempos2 = 0;
	if (htmlObject.tr.nextSibling) {
		tempos = htmlObject.tr.nextSibling.nodem;
	}
	if (htmlObject.tr.previousSibling) {
		tempos2 = htmlObject.tr.previousSibling.nodem;
	}
	var sN = htmlObject.parentObject;
	var Count = sN.childsCount;
	var Nodes = sN.childNodes;
	for (var i = 0; i < Count; i++) {
		if (Nodes[i].id == itemId) {
			if (!skip) {
				sN.htmlNode.childNodes[0].removeChild(Nodes[i].tr);
			}
			Nodes[i] = 0;
			break;
		}
	}
	this._compressChildList(Count, Nodes);
	if (!skip) {
		sN.childsCount--;
	}
	if (tempos) {
		this._correctPlus(tempos);
		this._correctLine(tempos);
	}
	if (tempos2) {
		this._correctPlus(tempos2);
		this._correctLine(tempos2);
	}
	if (this.tscheck) {
		this._correctCheckStates(sN);
	}
	if (!skip) {
		this._globalIdStorageRecSub(htmlObject);
	}
};
dhtmlXTreeObject.prototype.setCheck = function (itemId, state) {
	var sNode = this._globalIdStorageFind(itemId, 0, 1);
	if (!sNode) {
		return;
	}
	if (state === "unsure") {
		this._setCheck(sNode, state);
	} else {
		state = convertStringToBoolean(state);
		if ((this.tscheck) && (this.smcheck)) {
			this._setSubChecked(state, sNode);
		} else {
			this._setCheck(sNode, state);
		}
	}
	if (this.smcheck) {
		this._correctCheckStates(sNode.parentObject);
	}
};
dhtmlXTreeObject.prototype._setCheck = function (sNode, state) {
	if (!sNode) {
		return;
	}
	if (((sNode.parentObject._r_logic) || (this._frbtr)) && (state)) {
		if (this._frbtrs) {
			if (this._frbtrL) {
				this._setCheck(this._frbtrL, 0);
			}
			this._frbtrL = sNode;
		} else {
			for (var i = 0; i < sNode.parentObject.childsCount; i++) {
				this._setCheck(sNode.parentObject.childNodes[i], 0);
			}
		}
	}
	var z = sNode.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
	if (state == "unsure") {
		sNode.checkstate = 2;
	} else {
		if (state) {
			sNode.checkstate = 1;
		} else {
			sNode.checkstate = 0;
		}
	}
	if (sNode.dscheck) {
		sNode.checkstate = sNode.dscheck;
	}
	this._setSrc(z, this.imPath + ((sNode.parentObject._r_logic || this._frbtr) ? this.radioArray : this.checkArray)[sNode.checkstate]);
};
dhtmlXTreeObject.prototype.setSubChecked = function (itemId, state) {
	var sNode = this._globalIdStorageFind(itemId);
	this._setSubChecked(state, sNode);
	this._correctCheckStates(sNode.parentObject);
};
dhtmlXTreeObject.prototype._setSubChecked = function (state, sNode) {
	state = convertStringToBoolean(state);
	if (!sNode) {
		return;
	}
	if (((sNode.parentObject._r_logic) || (this._frbtr)) && (state)) {
		for (var i = 0; i < sNode.parentObject.childsCount; i++) {
			this._setSubChecked(0, sNode.parentObject.childNodes[i]);
		}
	}
	if (sNode.unParsed) {
		this._setSubCheckedXML(state, sNode.unParsed);
	}
	if (sNode._r_logic || this._frbtr) {
		this._setSubChecked(state, sNode.childNodes[0]);
	} else {
		for (var i = 0; i < sNode.childsCount; i++) {
			this._setSubChecked(state, sNode.childNodes[i]);
		}
	}
	var z = sNode.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
	if (state) {
		sNode.checkstate = 1;
	} else {
		sNode.checkstate = 0;
	}
	if (sNode.dscheck) {
		sNode.checkstate = sNode.dscheck;
	}
	this._setSrc(z, this.imPath + ((sNode.parentObject._r_logic || this._frbtr) ? this.radioArray : this.checkArray)[sNode.checkstate]);
};
dhtmlXTreeObject.prototype.isItemChecked = function (itemId) {
	var sNode = this._globalIdStorageFind(itemId);
	if (!sNode) {
		return;
	}
	return sNode.checkstate;
};
dhtmlXTreeObject.prototype.deleteChildItems = function (itemId) {
	var sNode = this._globalIdStorageFind(itemId);
	if (!sNode) {
		return;
	}
	var j = sNode.childsCount;
	for (var i = 0; i < j; i++) {
		this._deleteNode(sNode.childNodes[0].id, sNode.childNodes[0]);
	}
};
dhtmlXTreeObject.prototype.deleteItem = function (itemId, selectParent) {
	if ((!this._onrdlh) || (this._onrdlh(itemId))) {
		var z = this._deleteItem(itemId, selectParent);
		this._fixChildCountLabel(z);
	}
	this.allTree.childNodes[0].border = "1";
	this.allTree.childNodes[0].border = "0";
};
dhtmlXTreeObject.prototype._deleteItem = function (itemId, selectParent, skip) {
	selectParent = convertStringToBoolean(selectParent);
	var sNode = this._globalIdStorageFind(itemId);
	if (!sNode) {
		return;
	}
	var pid = this.getParentId(itemId);
	var zTemp = sNode.parentObject;
	this._deleteNode(itemId, sNode, skip);
	this._correctPlus(zTemp);
	this._correctLine(zTemp);
	if ((selectParent) && (pid != this.rootId)) {
		this.selectItem(pid, 1);
	}
	return zTemp;
};
dhtmlXTreeObject.prototype._globalIdStorageRecSub = function (itemObject) {
	for (var i = 0; i < itemObject.childsCount; i++) {
		this._globalIdStorageRecSub(itemObject.childNodes[i]);
		this._globalIdStorageSub(itemObject.childNodes[i].id);
	}
	this._globalIdStorageSub(itemObject.id);
	var z = itemObject;
	z.span = null;
	z.tr.nodem = null;
	z.tr = null;
	z.htmlNode = null;
};
dhtmlXTreeObject.prototype.insertNewNext = function (itemId, newItemId, itemText, itemActionHandler, image1, image2, image3, optionStr, children) {
	var sNode = this._globalIdStorageFind(itemId);
	if ((!sNode) || (!sNode.parentObject)) {
		return (0);
	}
	var nodez = this._attachChildNode(0, newItemId, itemText, itemActionHandler, image1, image2, image3, optionStr, children, sNode);
	if ((!this.XMLloadingWarning) && (this.childCalc)) {
		this._fixChildCountLabel(sNode.parentObject);
	}
	return nodez;
};
dhtmlXTreeObject.prototype.getItemIdByIndex = function (itemId, index) {
	var z = this._globalIdStorageFind(itemId);
	if ((!z) || (index > z.childsCount)) {
		return null;
	}
	return z.childNodes[index].id;
};
dhtmlXTreeObject.prototype.getChildItemIdByIndex = function (itemId, index) {
	var z = this._globalIdStorageFind(itemId);
	if ((!z) || (index >= z.childsCount)) {
		return null;
	}
	return z.childNodes[index].id;
};
dhtmlXTreeObject.prototype.setDragHandler = function (func) {
	this.attachEvent("onDrag", func);
};
dhtmlXTreeObject.prototype._clearMove = function () {
	if (this._lastMark) {
		this._lastMark.className = this._lastMark.className.replace(/dragAndDropRow/g, "");
		this._lastMark = null;
	}
	this.selectionBar.style.display = "none";
	this.allTree.className = this.allTree.className.replace(" selectionBox", "");
};
dhtmlXTreeObject.prototype.enableDragAndDrop = function (mode, rmode) {
	if (mode == "temporary_disabled") {
		this.dADTempOff = false;
		mode = true;
	} else {
		this.dADTempOff = true;
	}
	this.dragAndDropOff = convertStringToBoolean(mode);
	if (this.dragAndDropOff) {
		this.dragger.addDragLanding(this.allTree, this);
	}
	if (arguments.length > 1) {
		this._ddronr = (!convertStringToBoolean(rmode));
	}
};
dhtmlXTreeObject.prototype._setMove = function (htmlNode, x, y) {
	if (htmlNode.parentObject.span) {
		var a1 = getAbsoluteTop(htmlNode);
		var a2 = getAbsoluteTop(this.allTree);
		this.dadmodec = this.dadmode;
		this.dadmodefix = 0;
		if (this.dadmode == 2) {
			var z = y - a1 + this.allTree.scrollTop + (document.body.scrollTop || document.documentElement.scrollTop) - 2 - htmlNode.offsetHeight / 2;
			if ((Math.abs(z) - htmlNode.offsetHeight / 6) > 0) {
				this.dadmodec = 1;
				if (z < 0) {
					this.dadmodefix = 0 - htmlNode.offsetHeight;
				}
			} else {
				this.dadmodec = 0;
			}
		}
		if (this.dadmodec == 0) {
			var zN = htmlNode.parentObject.span;
			zN.className += " dragAndDropRow";
			this._lastMark = zN;
		} else {
			this._clearMove();
			this.selectionBar.style.top = (a1 - a2 + ((parseInt(htmlNode.parentObject.span.parentNode.previousSibling.childNodes[0].style.height) || 18) - 1) + this.dadmodefix) + "px";
			this.selectionBar.style.left = "5px";
			if (this.allTree.offsetWidth > 20) {
				this.selectionBar.style.width = (this.allTree.offsetWidth - (_isFF ? 30 : 25)) + "px";
			}
			this.selectionBar.style.display = "";
		}
		this._autoScroll(null, a1, a2);
	}
};
dhtmlXTreeObject.prototype._autoScroll = function (node, a1, a2) {
	if (this.autoScroll) {
		if (node) {
			a1 = getAbsoluteTop(node);
			a2 = getAbsoluteTop(this.allTree);
		}
		if ((a1 - a2 - parseInt(this.allTree.scrollTop)) > (parseInt(this.allTree.offsetHeight) - 50)) {
			this.allTree.scrollTop = parseInt(this.allTree.scrollTop) + 20;
		}
		if ((a1 - a2) < (parseInt(this.allTree.scrollTop) + 30)) {
			this.allTree.scrollTop = parseInt(this.allTree.scrollTop) - 20;
		}
	}
};
dhtmlXTreeObject.prototype._createDragNode = function (htmlObject, e) {
	if (!this.dADTempOff) {
		return null;
	}
	var obj = htmlObject.parentObject;
	if (!obj.i_sel) {
		this._selectItem(obj, e);
	}
	this._checkMSelectionLogic();
	var dragSpan = document.createElement("div");
	var text = new Array();
	if (this._itim_dg) {
		for (var i = 0; i < this._selected.length; i++) {
			text[i] = "<table cellspacing='0' cellpadding='0'><tr><td><img width='18px' height='18px' src='" + this._getSrc(this._selected[i].span.parentNode.previousSibling.childNodes[0]) + "'></td><td>" + this._selected[i].span.innerHTML + "</td></tr><table>";
		}
	} else {
		text = this.getSelectedItemText().split(this.dlmtr);
	}
	dragSpan.innerHTML = text.join("");
	dragSpan.style.position = "absolute";
	dragSpan.className = "dragSpanDiv";
	this._dragged = (new Array()).concat(this._selected);
	return dragSpan;
};
dhtmlXTreeObject.prototype._focusNode = function (item) {
	var z = getAbsoluteTop(item.htmlNode) - getAbsoluteTop(this.allTree);
	if ((z > (this.allTree.scrollTop + this.allTree.offsetHeight - 30)) || (z < this.allTree.scrollTop)) {
		this.allTree.scrollTop = z;
	} else {
		this.parentObject.scrollTop = z;
	}
};
dhtmlXTreeObject.prototype._preventNsDrag = function (e) {
	if ((e) && (e.preventDefault)) {
		e.preventDefault();
		return false;
	}
	return false;
};
dhtmlXTreeObject.prototype._drag = function (sourceHtmlObject, dhtmlObject, targetHtmlObject) {
	if (this._autoOpenTimer) {
		clearTimeout(this._autoOpenTimer);
	}
	if (!targetHtmlObject.parentObject) {
		targetHtmlObject = this.htmlNode.htmlNode.childNodes[0].childNodes[0].childNodes[1].childNodes[0];
		this.dadmodec = 0;
	}
	this._clearMove();
	var z = sourceHtmlObject.parentObject.treeNod;
	if ((z) && (z._clearMove)) {
		z._clearMove("");
	}
	if ((!this.dragMove) || (this.dragMove())) {
		if ((!z) || (!z._clearMove) || (!z._dragged)) {
			var col = new Array(sourceHtmlObject.parentObject);
		} else {
			var col = z._dragged;
		}
		var trg = targetHtmlObject.parentObject;
		for (var i = 0; i < col.length; i++) {
			var newID = this._moveNode(col[i], trg);
			if ((this.dadmodec) && (newID !== false)) {
				trg = this._globalIdStorageFind(newID, true, true);
			}
			if ((newID) && (!this._sADnD)) {
				this.selectItem(newID, 0, 1);
			}
		}
	}
	if (z) {
		z._dragged = new Array();
	}
};
dhtmlXTreeObject.prototype._dragIn = function (htmlObject, shtmlObject, x, y) {
	if (!this.dADTempOff) {
		return 0;
	}
	var fobj = shtmlObject.parentObject;
	var tobj = htmlObject.parentObject;
	if ((!tobj) && (this._ddronr)) {
		return;
	}
	if (!this.callEvent("onDragIn", [fobj.id, tobj ? tobj.id : null, fobj.treeNod, this])) {
		return 0;
	}
	if (!tobj) {
		this.allTree.className += " selectionBox";
	} else {
		if (fobj.childNodes == null) {
			this._setMove(htmlObject, x, y);
			return htmlObject;
		}
		var stree = fobj.treeNod;
		for (var i = 0; i < stree._dragged.length; i++) {
			if (this._checkPNodes(tobj, stree._dragged[i])) {
				this._autoScroll(htmlObject);
				return 0;
			}
		}
		this.selectionBar.parentNode.removeChild(this.selectionBar);
		tobj.span.parentNode.appendChild(this.selectionBar);
		this._setMove(htmlObject, x, y);
		if (this._getOpenState(tobj) <= 0) {
			this._autoOpenId = tobj.id;
			this._autoOpenTimer = window.setTimeout(new callerFunction(this._autoOpenItem, this), 1000);
		}
	}
	return htmlObject;
};
dhtmlXTreeObject.prototype._autoOpenItem = function (e, treeObject) {
	treeObject.openItem(treeObject._autoOpenId);
};
dhtmlXTreeObject.prototype._dragOut = function (htmlObject) {
	this._clearMove();
	if (this._autoOpenTimer) {
		clearTimeout(this._autoOpenTimer);
	}
};
dhtmlXTreeObject.prototype._getNextNode = function (item, mode) {
	if ((!mode) && (item.childsCount)) {
		return item.childNodes[0];
	}
	if (item == this.htmlNode) {
		return -1;
	}
	if ((item.tr) && (item.tr.nextSibling) && (item.tr.nextSibling.nodem)) {
		return item.tr.nextSibling.nodem;
	}
	return this._getNextNode(item.parentObject, true);
};
dhtmlXTreeObject.prototype._lastChild = function (item) {
	if (item.childsCount) {
		return this._lastChild(item.childNodes[item.childsCount - 1]);
	} else {
		return item;
	}
};
dhtmlXTreeObject.prototype._getPrevNode = function (node, mode) {
	if ((node.tr) && (node.tr.previousSibling) && (node.tr.previousSibling.nodem)) {
		return this._lastChild(node.tr.previousSibling.nodem);
	}
	if (node.parentObject) {
		return node.parentObject;
	} else {
		return -1;
	}
};
dhtmlXTreeObject.prototype.findItem = function (searchStr, direction, top) {
	var z = this._findNodeByLabel(searchStr, direction, (top ? this.htmlNode : null));
	if (z) {
		this.selectItem(z.id, true);
		this._focusNode(z);
		return z.id;
	} else {
		return null;
	}
};
dhtmlXTreeObject.prototype.findItemIdByLabel = function (searchStr, direction, top) {
	var z = this._findNodeByLabel(searchStr, direction, (top ? this.htmlNode : null));
	if (z) {
		return z.id;
	} else {
		return null;
	}
};
dhtmlXTreeObject.prototype.findStrInXML = function (node, field, cvalue) {
	for (var i = 0; i < node.childNodes.length; i++) {
		if (node.childNodes[i].nodeType == 1) {
			var z = node.childNodes[i].getAttribute(field);
			if ((z) && (z.toLowerCase().search(cvalue) != -1)) {
				return true;
			}
			if (this.findStrInXML(node.childNodes[i], field, cvalue)) {
				return true;
			}
		}
	}
	return false;
};
dhtmlXTreeObject.prototype._findNodeByLabel = function (searchStr, direction, fromNode) {
	var searchStr = searchStr.replace(new RegExp("^( )+"), "").replace(new RegExp("( )+$"), "");
	searchStr = new RegExp(searchStr.replace(/([\*\+\\\[\]\(\)]{1})/gi, "\\$1").replace(/ /gi, ".*"), "gi");
	if (!fromNode) {
		fromNode = this._selected[0];
		if (!fromNode) {
			fromNode = this.htmlNode;
		}
	}
	var startNode = fromNode;
	if (!direction) {
		if ((fromNode.unParsed) && (this.findStrInXML(fromNode.unParsed.d, "text", searchStr))) {
			this.reParse(fromNode);
		}
		fromNode = this._getNextNode(startNode);
		if (fromNode == -1) {
			fromNode = this.htmlNode.childNodes[0];
		}
	} else {
		var z2 = this._getPrevNode(startNode);
		if (z2 == -1) {
			z2 = this._lastChild(this.htmlNode);
		}
		if ((z2.unParsed) && (this.findStrInXML(z2.unParsed.d, "text", searchStr))) {
			this.reParse(z2);
			fromNode = this._getPrevNode(startNode);
		} else {
			fromNode = z2;
		}
		if (fromNode == -1) {
			fromNode = this._lastChild(this.htmlNode);
		}
	}
	while ((fromNode) && (fromNode != startNode)) {
		if ((fromNode.label) && (fromNode.label.search(searchStr) != -1)) {
			return (fromNode);
		}
		if (!direction) {
			if (fromNode == -1) {
				if (startNode == this.htmlNode) {
					break;
				}
				fromNode = this.htmlNode.childNodes[0];
			}
			if ((fromNode.unParsed) && (this.findStrInXML(fromNode.unParsed.d, "text", searchStr))) {
				this.reParse(fromNode);
			}
			fromNode = this._getNextNode(fromNode);
			if (fromNode == -1) {
				fromNode = this.htmlNode;
			}
		} else {
			var z2 = this._getPrevNode(fromNode);
			if (z2 == -1) {
				z2 = this._lastChild(this.htmlNode);
			}
			if ((z2.unParsed) && (this.findStrInXML(z2.unParsed.d, "text", searchStr))) {
				this.reParse(z2);
				fromNode = this._getPrevNode(fromNode);
			} else {
				fromNode = z2;
			}
			if (fromNode == -1) {
				fromNode = this._lastChild(this.htmlNode);
			}
		}
	}
	return null;
};
dhtmlXTreeObject.prototype.moveItem = function (itemId, mode, targetId, targetTree) {
	var sNode = this._globalIdStorageFind(itemId);
	if (!sNode) {
		return (0);
	}
	switch (mode) {
	  case "right":
		alert("Not supported yet");
		break;
	  case "item_child":
		var tNode = (targetTree || this)._globalIdStorageFind(targetId);
		if (!tNode) {
			return (0);
		}
		(targetTree || this)._moveNodeTo(sNode, tNode, 0);
		break;
	  case "item_sibling":
		var tNode = (targetTree || this)._globalIdStorageFind(targetId);
		if (!tNode) {
			return (0);
		}
		(targetTree || this)._moveNodeTo(sNode, tNode.parentObject, tNode);
		break;
	  case "item_sibling_next":
		var tNode = (targetTree || this)._globalIdStorageFind(targetId);
		if (!tNode) {
			return (0);
		}
		if ((tNode.tr) && (tNode.tr.nextSibling) && (tNode.tr.nextSibling.nodem)) {
			(targetTree || this)._moveNodeTo(sNode, tNode.parentObject, tNode.tr.nextSibling.nodem);
		} else {
			(targetTree || this)._moveNodeTo(sNode, tNode.parentObject);
		}
		break;
	  case "left":
		if (sNode.parentObject.parentObject) {
			this._moveNodeTo(sNode, sNode.parentObject.parentObject, sNode.parentObject);
		}
		break;
	  case "up":
		var z = this._getPrevNode(sNode);
		if ((z == -1) || (!z.parentObject)) {
			return;
		}
		this._moveNodeTo(sNode, z.parentObject, z);
		break;
	  case "up_strict":
		var z = this._getIndex(sNode);
		if (z != 0) {
			this._moveNodeTo(sNode, sNode.parentObject, sNode.parentObject.childNodes[z - 1]);
		}
		break;
	  case "down_strict":
		var z = this._getIndex(sNode);
		var count = sNode.parentObject.childsCount - 2;
		if (z == count) {
			this._moveNodeTo(sNode, sNode.parentObject);
		} else {
			if (z < count) {
				this._moveNodeTo(sNode, sNode.parentObject, sNode.parentObject.childNodes[z + 2]);
			}
		}
		break;
	  case "down":
		var z = this._getNextNode(this._lastChild(sNode));
		if ((z == -1) || (!z.parentObject)) {
			return;
		}
		if (z.parentObject == sNode.parentObject) {
			var z = this._getNextNode(z);
		}
		if (z == -1) {
			this._moveNodeTo(sNode, sNode.parentObject);
		} else {
			if ((z == -1) || (!z.parentObject)) {
				return;
			}
			this._moveNodeTo(sNode, z.parentObject, z);
		}
		break;
	}
};
dhtmlXTreeObject.prototype.setDragBehavior = function (mode, select) {
	this._sADnD = (!convertStringToBoolean(select));
	switch (mode) {
	  case "child":
		this.dadmode = 0;
		break;
	  case "sibling":
		this.dadmode = 1;
		break;
	  case "complex":
		this.dadmode = 2;
		break;
	}
};
dhtmlXTreeObject.prototype._loadDynXML = function (id, src) {
	src = src || this.XMLsource;
	var sn = (new Date()).valueOf();
	this._ld_id = id;
	if (this.xmlalb == "function") {
		if (src) {
			src(this._escape(id));
		}
	} else {
		if (this.xmlalb == "name") {
			this.loadXML(src + this._escape(id));
		} else {
			if (this.xmlalb == "xmlname") {
				this.loadXML(src + this._escape(id) + ".xml?uid=" + sn);
			} else {
				this.loadXML(src + getUrlSymbol(src) + "uid=" + sn + "&id=" + this._escape(id));
			}
		}
	}
};
dhtmlXTreeObject.prototype.enableMultiselection = function (mode, strict) {
	this._amsel = convertStringToBoolean(mode);
	this._amselS = convertStringToBoolean(strict);
};
dhtmlXTreeObject.prototype._checkMSelectionLogic = function () {
	var usl = new Array();
	for (var i = 0; i < this._selected.length; i++) {
		for (var j = 0; j < this._selected.length; j++) {
			if ((i != j) && (this._checkPNodes(this._selected[j], this._selected[i]))) {
				usl[usl.length] = this._selected[j];
			}
		}
	}
	for (var i = 0; i < usl.length; i++) {
		this._unselectItem(usl[i]);
	}
};
dhtmlXTreeObject.prototype._checkPNodes = function (item1, item2) {
	if (item2 == item1) {
		return 1;
	}
	if (item1.parentObject) {
		return this._checkPNodes(item1.parentObject, item2);
	} else {
		return 0;
	}
};
dhtmlXTreeObject.prototype.enableDistributedParsing = function (mode, count, delay) {
	this._edsbps = convertStringToBoolean(mode);
	this._edsbpsA = new Array();
	this._edsbpsC = count || 10;
	this._edsbpsD = delay || 250;
};
dhtmlXTreeObject.prototype.getDistributedParsingState = function () {
	return (!((!this._edsbpsA) || (!this._edsbpsA.length)));
};
dhtmlXTreeObject.prototype.getItemParsingState = function (itemId) {
	var z = this._globalIdStorageFind(itemId, true, true);
	if (!z) {
		return 0;
	}
	if (this._edsbpsA) {
		for (var i = 0; i < this._edsbpsA.length; i++) {
			if (this._edsbpsA[i][2] == itemId) {
				return -1;
			}
		}
	}
	return 1;
};
dhtmlXTreeObject.prototype._distributedStart = function (node, start, parentId, level, start2) {
	if (!this._edsbpsA) {
		this._edsbpsA = new Array();
	}
	this._edsbpsA[this._edsbpsA.length] = [node, start, parentId, level, start2];
};
dhtmlXTreeObject.prototype._distributedStep = function (pId) {
	var self = this;
	if ((!this._edsbpsA) || (!this._edsbpsA.length)) {
		self.XMLloadingWarning = 0;
		return;
	}
	var z = this._edsbpsA[0];
	this.parsedArray = new Array();
	this._parse(z[0], z[2], z[3], z[1]);
	var zkx = this._globalIdStorageFind(z[2]);
	this._redrawFrom(this, zkx, z[4], this._getOpenState(zkx));
	var chArr = this.setCheckList.split(this.dlmtr);
	for (var n = 0; n < chArr.length; n++) {
		if (chArr[n]) {
			this.setCheck(chArr[n], 1);
		}
	}
	this._edsbpsA = (new Array()).concat(this._edsbpsA.slice(1));
	if ((!this._edsbpsA.length)) {
		window.setTimeout(function () {
			if (self.onXLE) {
				self.onXLE(self, pId);
			}
			self.callEvent("onXLE", [self, pId]);
		}, 1);
		self.xmlstate = 0;
	}
};
dhtmlXTreeObject.prototype.enablePaging = function (mode, page_size) {
	this._epgps = convertStringToBoolean(mode);
	this._epgpsC = page_size || 50;
};
dhtmlXTreeObject.prototype._setPrevPageSign = function (node, pos, level, xmlnode) {
	var z = document.createElement("DIV");
	z.innerHTML = "Previous " + this._epgpsC + " items";
	z.className = "dhx_next_button";
	var self = this;
	z.onclick = function () {
		self._prevPageCall(this);
	};
	z._pageData = [node, pos, level, xmlnode];
	var w = node.childNodes[0];
	var w2 = w.span.parentNode.parentNode.parentNode.parentNode.parentNode;
	w2.insertBefore(z, w2.firstChild);
};
dhtmlXTreeObject.prototype._setNextPageSign = function (node, pos, level, xmlnode) {
	var z = document.createElement("DIV");
	z.innerHTML = "Next " + this._epgpsC + " items";
	z.className = "dhx_next_button";
	var self = this;
	z.onclick = function () {
		self._nextPageCall(this);
	};
	z._pageData = [node, pos, level, xmlnode];
	var w = node.childNodes[node.childsCount - 1];
	w.span.parentNode.parentNode.parentNode.parentNode.parentNode.appendChild(z);
};
dhtmlXTreeObject.prototype._nextPageCall = function (node) {
	tree.deleteChildItems(node._pageData[0].id);
	node.parentNode.removeChild(node);
	var f = this._getOpenState(node._pageData[0]);
	this._parseXMLTree(this, node._pageData[3], node._pageData[0].id, node._pageData[2], null, node._pageData[1]);
	this._redrawFrom(this, node._pageData[0], 0);
	if (f > -1) {
		this._openItem(node._pageData[0]);
	}
	node._pageData = null;
};
dhtmlXTreeObject.prototype._prevPageCall = function (node) {
	tree.deleteChildItems(node._pageData[0].id);
	node.parentNode.removeChild(node);
	var f = this._getOpenState(node._pageData[0]);
	var xz = node._pageData[1] - this._epgpsC;
	if (xz < 0) {
		xz = 0;
	}
	this._parseXMLTree(this, node._pageData[3], node._pageData[0].id, node._pageData[2], null, xz);
	this._redrawFrom(this, node._pageData[0], 0);
	if (f > -1) {
		this._openItem(node._pageData[0]);
	}
	node._pageData = null;
};
dhtmlXTreeObject.prototype.enableTextSigns = function (mode) {
	this._txtimg = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.preventIECaching = function (mode) {
	this.no_cashe = convertStringToBoolean(mode);
	this.XMLLoader.rSeed = this.no_cashe;
};
dhtmlXTreeObject.prototype.preventIECashing = dhtmlXTreeObject.prototype.preventIECaching;
dhtmlXTreeObject.prototype.disableCheckbox = function (itemId, mode) {
	if (typeof (itemId) != "object") {
		var sNode = this._globalIdStorageFind(itemId, 0, 1);
	} else {
		var sNode = itemId;
	}
	if (!sNode) {
		return;
	}
	sNode.dscheck = convertStringToBoolean(mode) ? (((sNode.checkstate || 0) % 3) + 3) : ((sNode.checkstate > 2) ? (sNode.checkstate - 3) : sNode.checkstate);
	this._setCheck(sNode);
	if (sNode.dscheck < 3) {
		sNode.dscheck = false;
	}
};
dhtmlXTreeObject.prototype.smartRefreshBranch = function (itemId, source) {
	this._branchUpdate = 1;
	this.smartRefreshItem(itemId, source);
};
dhtmlXTreeObject.prototype.smartRefreshItem = function (itemId, source) {
	var sNode = this._globalIdStorageFind(itemId);
	for (var i = 0; i < sNode.childsCount; i++) {
		sNode.childNodes[i]._dmark = true;
	}
	this.waitUpdateXML = true;
	if (source && source.exists) {
		this._parse(source, itemId);
	} else {
		this._loadDynXML(itemId, source);
	}
};
dhtmlXTreeObject.prototype.refreshItems = function (itemIdList, source) {
	var z = itemIdList.toString().split(this.dlmtr);
	this.waitUpdateXML = new Array();
	for (var i = 0; i < z.length; i++) {
		this.waitUpdateXML[z[i]] = true;
	}
	this.loadXML((source || this.XMLsource) + getUrlSymbol(source || this.XMLsource) + "ids=" + this._escape(itemIdList));
};
dhtmlXTreeObject.prototype.updateItem = function (itemId, name, im0, im1, im2, achecked) {
	var sNode = this._globalIdStorageFind(itemId);
	sNode.userData = new cObject();
	if (name) {
		sNode.label = name;
	}
	sNode.images = new Array(im0 || this.imageArray[0], im1 || this.imageArray[1], im2 || this.imageArray[2]);
	this.setItemText(itemId, name);
	if (achecked) {
		this._setCheck(sNode, true);
	}
	this._correctPlus(sNode);
	sNode._dmark = false;
	return sNode;
};
dhtmlXTreeObject.prototype.setDropHandler = function (func) {
	this.attachEvent("onDrop", func);
};
dhtmlXTreeObject.prototype.setOnLoadingStart = function (func) {
	this.attachEvent("onXLS", func);
};
dhtmlXTreeObject.prototype.setOnLoadingEnd = function (func) {
	this.attachEvent("onXLE", func);
};
dhtmlXTreeObject.prototype.setXMLAutoLoadingBehaviour = function (mode) {
	this.xmlalb = mode;
};
dhtmlXTreeObject.prototype.enableSmartCheckboxes = function (mode) {
	this.smcheck = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.getXMLState = function () {
	return (this.xmlstate == 1);
};
dhtmlXTreeObject.prototype.setItemTopOffset = function (itemId, value) {
	if (typeof (itemId) == "string") {
		var node = this._globalIdStorageFind(itemId);
	} else {
		var node = itemId;
	}
	var z = node.span.parentNode.parentNode;
	for (var i = 0; i < z.childNodes.length; i++) {
		if (i != 0) {
			z.childNodes[i].style.height = 18 + parseInt(value) + "px";
		} else {
			var w = z.childNodes[i].firstChild;
			if (z.childNodes[i].firstChild.tagName != "DIV") {
				w = document.createElement("DIV");
				z.childNodes[i].insertBefore(w, z.childNodes[i].firstChild);
			}
			w.style.height = parseInt(value) + "px";
			if ((node.parentObject.id != this.rootId || node.parentObject.childNodes[0] != node) && this.treeLinesOn) {
				w.style.backgroundImage = "url(" + this.imPath + this.lineArray[5] + ")";
			}
			w.innerHTML = "&nbsp;";
			w.style.overflow = "hidden";
			if (parseInt(value) == 0) {
				z.childNodes[i].removeChild(w);
			}
		}
		z.childNodes[i].vAlign = "bottom";
	}
};
dhtmlXTreeObject.prototype.setIconSize = function (newWidth, newHeight, itemId) {
	if (itemId) {
		if ((itemId) && (itemId.span)) {
			var sNode = itemId;
		} else {
			var sNode = this._globalIdStorageFind(itemId);
		}
		if (!sNode) {
			return (0);
		}
		var img = sNode.span.parentNode.previousSibling.childNodes[0];
		img.style.width = newWidth;
		img.style.height = newHeight;
	} else {
		this.def_img_x = newWidth;
		this.def_img_y = newHeight;
	}
};
dhtmlXTreeObject.prototype.getItemImage = function (itemId, imageInd, fullPath) {
	var node = this._globalIdStorageFind(itemId);
	if (!node) {
		return "";
	}
	var img = node.images[imageInd || 0];
	if (fullPath) {
		img = this.imPath + img;
	}
	return img;
};
dhtmlXTreeObject.prototype.enableRadioButtons = function (itemId, mode) {
	if (arguments.length == 1) {
		this._frbtr = convertStringToBoolean(itemId);
		this.checkBoxOff = this.checkBoxOff || this._frbtr;
		return;
	}
	var node = this._globalIdStorageFind(itemId);
	if (!node) {
		return "";
	}
	mode = convertStringToBoolean(mode);
	if ((mode) && (!node._r_logic)) {
		node._r_logic = true;
		for (var i = 0; i < node.childsCount; i++) {
			this._setCheck(node.childNodes[i], node.childNodes[i].checkstate);
		}
	}
	if ((!mode) && (node._r_logic)) {
		node._r_logic = false;
		for (var i = 0; i < node.childsCount; i++) {
			this._setCheck(node.childNodes[i], node.childNodes[i].checkstate);
		}
	}
};
dhtmlXTreeObject.prototype.enableSingleRadioMode = function (mode) {
	this._frbtrs = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.openOnItemAdded = function (mode) {
	this._hAdI = !convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.openOnItemAdding = function (mode) {
	this._hAdI = !convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.enableMultiLineItems = function (width) {
	if (width === true) {
		this.mlitems = "100%";
	} else {
		this.mlitems = width;
	}
};
dhtmlXTreeObject.prototype.enableAutoTooltips = function (mode) {
	this.ettip = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.enableDHTMLXTooltips = function (mode) {
	this._dhxTT = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.clearSelection = function (itemId) {
	if (itemId) {
		this._unselectItem(this._globalIdStorageFind(itemId));
	} else {
		this._unselectItems();
	}
};
dhtmlXTreeObject.prototype.showItemSign = function (itemId, state) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	var z = temp.span.parentNode.previousSibling.previousSibling.previousSibling;
	if (!convertStringToBoolean(state)) {
		this._openItem(temp);
		temp.closeble = false;
		temp.wsign = true;
	} else {
		temp.closeble = true;
		temp.wsign = false;
	}
	this._correctPlus(temp);
};
dhtmlXTreeObject.prototype.showItemCheckbox = function (itemId, state) {
	if (!itemId) {
		for (var a in this._idpull) {
			this.showItemCheckbox(this._idpull[a], state);
		}
	}
	if (typeof (itemId) != "object") {
		itemId = this._globalIdStorageFind(itemId, 0, 1);
	}
	if (!itemId) {
		return 0;
	}
	itemId.nocheckbox = !convertStringToBoolean(state);
	itemId.span.parentNode.previousSibling.previousSibling.childNodes[0].style.display = (!itemId.nocheckbox) ? "" : "none";
};
dhtmlXTreeObject.prototype.setListDelimeter = function (separator) {
	this.dlmtr = separator;
};
dhtmlXTreeObject.prototype.setEscapingMode = function (mode) {
	this.utfesc = mode;
};
dhtmlXTreeObject.prototype.enableHighlighting = function (mode) {
	this.ehlt = true;
	this.ehlta = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype._itemMouseOut = function () {
	var that = this.childNodes[3].parentObject;
	var tree = that.treeNod;
	tree.callEvent("onMouseOut", [that.id]);
	if (that.id == tree._l_onMSI) {
		tree._l_onMSI = null;
	}
	if (!tree.ehlta) {
		return;
	}
	that.span.className = that.span.className.replace("_lor", "");
};
dhtmlXTreeObject.prototype._itemMouseIn = function () {
	var that = this.childNodes[3].parentObject;
	var tree = that.treeNod;
	if (tree._l_onMSI != that.id) {
		tree.callEvent("onMouseIn", [that.id]);
	}
	tree._l_onMSI = that.id;
	if (!tree.ehlta) {
		return;
	}
	that.span.className = that.span.className.replace("_lor", "");
	that.span.className = that.span.className.replace(/((standart|selected)TreeRow)/, "$1_lor");
};
dhtmlXTreeObject.prototype.enableActiveImages = function (mode) {
	this._aimgs = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.focusItem = function (itemId) {
	var sNode = this._globalIdStorageFind(itemId);
	if (!sNode) {
		return (0);
	}
	this._focusNode(sNode);
};
dhtmlXTreeObject.prototype.getAllSubItems = function (itemId) {
	return this._getAllSubItems(itemId);
};
dhtmlXTreeObject.prototype.getAllChildless = function () {
	return this._getAllScraggyItems(this.htmlNode);
};
dhtmlXTreeObject.prototype.getAllLeafs = dhtmlXTreeObject.prototype.getAllChildless;
dhtmlXTreeObject.prototype._getAllScraggyItems = function (node) {
	var z = "";
	for (var i = 0; i < node.childsCount; i++) {
		if ((node.childNodes[i].unParsed) || (node.childNodes[i].childsCount > 0)) {
			if (node.childNodes[i].unParsed) {
				var zb = this._getAllScraggyItemsXML(node.childNodes[i].unParsed, 1);
			} else {
				var zb = this._getAllScraggyItems(node.childNodes[i]);
			}
			if (zb) {
				if (z) {
					z += this.dlmtr + zb;
				} else {
					z = zb;
				}
			}
		} else {
			if (!z) {
				z = node.childNodes[i].id;
			} else {
				z += this.dlmtr + node.childNodes[i].id;
			}
		}
	}
	return z;
};
dhtmlXTreeObject.prototype._getAllFatItems = function (node) {
	var z = "";
	for (var i = 0; i < node.childsCount; i++) {
		if ((node.childNodes[i].unParsed) || (node.childNodes[i].childsCount > 0)) {
			if (!z) {
				z = node.childNodes[i].id;
			} else {
				z += this.dlmtr + node.childNodes[i].id;
			}
			if (node.childNodes[i].unParsed) {
				var zb = this._getAllFatItemsXML(node.childNodes[i].unParsed, 1);
			} else {
				var zb = this._getAllFatItems(node.childNodes[i]);
			}
			if (zb) {
				z += this.dlmtr + zb;
			}
		}
	}
	return z;
};
dhtmlXTreeObject.prototype.getAllItemsWithKids = function () {
	return this._getAllFatItems(this.htmlNode);
};
dhtmlXTreeObject.prototype.getAllFatItems = dhtmlXTreeObject.prototype.getAllItemsWithKids;
dhtmlXTreeObject.prototype.getAllChecked = function () {
	return this._getAllChecked("", "", 1);
};
dhtmlXTreeObject.prototype.getAllUnchecked = function (itemId) {
	if (itemId) {
		itemId = this._globalIdStorageFind(itemId);
	}
	return this._getAllChecked(itemId, "", 0);
};
dhtmlXTreeObject.prototype.getAllPartiallyChecked = function () {
	return this._getAllChecked("", "", 2);
};
dhtmlXTreeObject.prototype.getAllCheckedBranches = function () {
	var temp = this._getAllChecked("", "", 1);
	if (temp != "") {
		temp += this.dlmtr;
	}
	return temp + this._getAllChecked("", "", 2);
};
dhtmlXTreeObject.prototype._getAllChecked = function (htmlNode, list, mode) {
	if (!htmlNode) {
		htmlNode = this.htmlNode;
	}
	if (htmlNode.checkstate == mode) {
		if (!htmlNode.nocheckbox) {
			if (list) {
				list += this.dlmtr + htmlNode.id;
			} else {
				list = htmlNode.id;
			}
		}
	}
	var j = htmlNode.childsCount;
	for (var i = 0; i < j; i++) {
		list = this._getAllChecked(htmlNode.childNodes[i], list, mode);
	}
	if (htmlNode.unParsed) {
		list = this._getAllCheckedXML(htmlNode.unParsed, list, mode);
	}
	if (list) {
		return list;
	} else {
		return "";
	}
};
dhtmlXTreeObject.prototype.setItemStyle = function (itemId, style_string) {
	var temp = this._globalIdStorageFind(itemId);
	if (!temp) {
		return 0;
	}
	if (!temp.span.style.cssText) {
		temp.span.setAttribute("style", temp.span.getAttribute("style") + ";" + style_string);
	} else {
		temp.span.style.cssText += (";" + style_string);
	}
};
dhtmlXTreeObject.prototype.enableImageDrag = function (mode) {
	this._itim_dg = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.setOnDragIn = function (func) {
	this.attachEvent("onDragIn", func);
};
dhtmlXTreeObject.prototype.enableDragAndDropScrolling = function (mode) {
	this.autoScroll = convertStringToBoolean(mode);
};
dhtmlXTreeObject.prototype.dhx_Event = function () {
	this.dhx_SeverCatcherPath = "";
	this.attachEvent = function (original, catcher, CallObj) {
		if (this._onEventSet && this._onEventSet[original]) {
			this._onEventSet[original].apply(this, []);
		}
		CallObj = CallObj || this;
		original = "ev_" + original;
		if ((!this[original]) || (!this[original].addEvent)) {
			var z = new this.eventCatcher(CallObj);
			z.addEvent(this[original]);
			this[original] = z;
		}
		return (original + ":" + this[original].addEvent(catcher));
	};
	this.callEvent = function (name, a) {
		if (this["ev_" + name]) {
			return this["ev_" + name].apply(this, a);
		}
		return true;
	};
	this.checkEvent = function (name) {
		if (this["ev_" + name]) {
			return true;
		}
		return false;
	};
	this.eventCatcher = function (obj) {
		var dhx_catch = new Array();
		var m_obj = obj;
		var func_server = function (catcher, rpc) {
			catcher = catcher.split(":");
			var postVar = "";
			var postVar2 = "";
			var target = catcher[1];
			if (catcher[1] == "rpc") {
				postVar = "<?xml version=\"1.0\"?><methodCall><methodName>" + catcher[2] + "</methodName><params>";
				postVar2 = "</params></methodCall>";
				target = rpc;
			}
			var z = function () {
				var loader = new dtmlXMLLoaderObject(null, window, false);
				var request = postVar;
				if (postVar2) {
					for (var i = 0; i < arguments.length; i++) {
						request += "<param><value><string>" + (arguments[i] ? arguments[i].toString() : "") + "</string></value></param>";
					}
					request += postVar2;
				} else {
					for (var i = 0; i < arguments.length; i++) {
						request += ("&arg" + i + "=" + escape(arguments[i]));
					}
				}
				loader.loadXML(target, true, request, postVar2 ? true : false);
				try {
					if (postVar2) {
						var dt = loader.doXPath("//methodResponse/params/param/value/string");
						return convertStringToBoolean(dt[0].firstChild.data);
					} else {
						return convertStringToBoolean(loader.xmlDoc.responseText);
					}
				}
				catch (e) {
					dhtmlxError.throwError("rpcError", loader.xmlDoc.responseText);
					return false;
				}
			};
			return z;
		};
		var z = function () {
			if (dhx_catch) {
				var res = true;
			}
			for (var i = 0; i < dhx_catch.length; i++) {
				if (dhx_catch[i] != null) {
					var zr = dhx_catch[i].apply(m_obj, arguments);
					res = res && zr;
				}
			}
			return res;
		};
		z.addEvent = function (ev) {
			if (typeof (ev) != "function") {
				if (ev && ev.indexOf && ev.indexOf("server:") === 0) {
					ev = new func_server(ev, m_obj.rpcServer);
				} else {
					ev = eval(ev);
				}
			}
			if (ev) {
				return dhx_catch.push(ev) - 1;
			}
			return false;
		};
		z.removeEvent = function (id) {
			dhx_catch[id] = null;
		};
		return z;
	};
	this.detachEvent = function (id) {
		if (id != false) {
			var list = id.split(":");
			this[list[0]].removeEvent(list[1]);
		}
	};
};//(c)dhtmlx ltd. www.dhtmlx.com
//v.1.6 build 71114
/*
Copyright DHTMLX LTD. http://www.dhtmlx.com
To use this component please contact sales@dhtmlx.com to obtain license
*/

