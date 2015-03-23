/**
 * 文档管理器 
 * @module	DocManager
 * @requires KISSY core
 * @creator	苗鹏<miaopeng1@ultrapower.com.cn>
 */
 
 KISSY.add('docmanager', function(S){
	var DOM = S.DOM, Event = S.Event, IO = S.IO, sub = S.substitute, 
		CLS_NAVLINK = 'navlink', CLS_DOC = 'item', CLS_DIR = 'dir',
		ATTR_DATAID = 'dataId', ATTR_DATANAME = 'dataName',
		PRE_FUN_EVT = '_doc_', PRE_EVT_BEFORE = 'doc.before.', PRE_EVT_AFTER = 'doc.after.',
		JSON_ROOT = 'list', JSON_PREVENT = 'prevent',
		navData = [], imgTypes = ['jpg', 'gif', 'bmp', 'png']
		
		// 操作类型定义
		actions = {
			dir:{
				'open':'打开',
				'new':'新增',
				'delete':'删除',
				'update':'修改',
				'setopt':'操作权限设置',
				'setapv':'审批权限设置'
			},
			doc:{
				'view':'查看文件信息',
				'download':'下载',
				'upload':'上传',
				'delete':'删除',
				'update':'修改',
				'send':'发送',
				'submit':'提交',
				'favorite':'收藏'
			}
		},
		
		// 文件操作权限类别 - folderType
		defaultDocMenu = ['view'],
		docMenuTypes = [
			['view','download','delete','update','favorite'], //文档库
			['view','download','delete','update','send','submit'], //我的文件
			['view','download','delete','update','submit','favorite'], //部门文件
			[],
			[],
			['view','download','delete'] //我的收藏
		],
		
		// 文件夹操作权限类别 - permission
		dirMenuTypes = [
			['delete','update'], // private dir:delete,update, file:all
			[], //permission - 1 dir:readonly, file:readonly
			[], //permission - 2 dir:readonly, file:all
			['delete','update','setopt','setapv'] //permission - 3 dir:all, file:all
			
		],
		
		defaultConfig = {
			prevent : {} // 动作阻止列表
		};
	
	function DocManager(config){
		this.config = S.merge(defaultConfig, config);
		S.mix(this, this.config);
		this.container = S.get(this.container);
		this.selected = [];// 已选中的文件id集合
		this.selectedEls = []; // 已选中的文件元素集合
		this._init();
	}
	
	S.augment(DocManager, S.EventTarget, {
		/**
		 * 初始化并打开当前目录
		 */
		_init : function(){
			this._initBreadCrumbs();
			this._initToolbar();
			this._initContainer();
			this.explor(this.dir.id, this.dir.name);
			this._initCtxMenu();		
			this._initDialog();
		},
		
		/**
		 * 初始化容器事件
		 */
		_initContainer : function(){
			
			// markups
			this.listheader = this.make('<div class="listheader">', {
				'html' : '<span class="name">名称</span><div class="size">大小</div>' +
						'<div class="date">日期</div><div class="author">上传人</div>'
			});
			this.box = this.make('<div class="box">');
			
			// 全局事件
			IO.on('start', this.loading, this);
			IO.on('complete', this.unloading, this);
			Event.on(document, 'click', this.hideCtxMenu, this);
			
			// 文档事件
			var self = this;
			Event.on(this.box, 'click dblclick mouseover mouseout contextmenu', function(ev){
            	var target = ev.target, handler = self[PRE_FUN_EVT + ev.type];
            	
            	// 可能点击在SPAN上
            	if (!DOM.hasClass(target, CLS_DOC)) {
                	target = DOM.parent(target, '.'+CLS_DOC);
            	}
            	            	
            	if (target === self.container){
            		return;
            	}
            	
            	if ( S.isFunction( handler ) ){
            		if(self.fire(PRE_EVT_BEFORE + ev.type, {el : target}) === false) return;
            		handler.call(self, target, ev);
            		self.fire(PRE_EVT_AFTER + ev.type, {el : target});
            	}        	
			});
			
		},

		/**
		 * 初始化toolbar
		 */
		_initToolbar : function(){		
			this.toolbar = this.make('<div class="btnbar">', {
				'html':'<div class="tools"></div><div class="search"></div>' +
					'<div class="btns"><a id="btn-view-icon" title="切换到图标视图"></a></div></div>'
			});
			
			var self = this;
			var foldertype = this.dir.type;
			if(foldertype == '5')
			{
				S.each([
					{act:'refresh', text:'刷新'},
					{act:'dir_new', text:'新建目录'}
				], this.createTool, this);
			}
			else
			{
				S.each([
					{act:'refresh', text:'刷新'},
					{act:'dir_new', text:'新建目录'},
					{act:'doc_upload', text:'上传文件'}
				], this.createTool, this);
			}
			
			var viewHandler = S.get('#btn-view-icon');
			Event.on(viewHandler, 'click',function(){
				DOM.toggleClass(self.container, 'view-icon');
				DOM.attr(viewHandler, 'title', DOM.hasClass(self.container, 'view-icon') ? '切换到列表视图' : '切换到图标视图');
				
			});

		},
		
		/**
		 * 初始化面包屑内容并绑定事件
		 */
		_initBreadCrumbs : function(){
			this.breadcrumbs = this.make('<div class="breadcrumbs">');
			
			var self = this;
			S.each(this.dir.data, function(item){
				var data = item.split('#');
				navData.push(crumbs(data[0], data[1]));
				self.dir.name = data[1];
			});
			DOM.html(this.breadcrumbs, navData.join(" / "));
								
			Event.on(this.breadcrumbs, 'mousedown', function(ev){
				ev.preventDefault();
				var target = ev.target;
				if (target.className === CLS_NAVLINK){
					self.explor(DOM.attr(target, ATTR_DATAID), DOM.attr(target, ATTR_DATANAME));
				}
			});
		},
		
		/**
		 * 初始化右键菜单，绑定事件
		 */
		_initCtxMenu : function(){
			this.ctxmenu = this.make('<div class="ctxmenu">');
			Event.on(this.ctxmenu, 'click mouseover mouseout', function(ev){
				var target = ev.target;
				if(!DOM.hasClass(target, 'ctx-item')) return;
				
				if(ev.type === 'mouseover'){
					DOM.addClass(target, 'over');
				}
				if(ev.type === 'mouseout'){
					DOM.removeClass(target, 'over');
				}
				if(ev.type === 'click'){
					var handler = target.id.replace('ctx-',isDir(this.selectedEls[0])?'dir_':'doc_');
					if( S.isFunction(this[handler]) ){
						this[handler](this.selected);
					}
				}
			}, this);
		},
		
		_initDialog : function(){
			this.make('<div id="dlg" class="ks-dialog">', {
				'html':'<div class="ks-contentbox">' +
						'<div class="ks-stdmod-header"></div>' +
						'<div class="ks-stdmod-body"><div style="padding:10px;"></div></div>' +
						'<div class="ks-stdmod-footer"></div>' +
						'<a class="ks-ext-close"><span class="ks-ext-close-x">X</span></a>' +
						'</div>'
			});
			this.dialog = new S.Dialog({            
				srcNode:S.one("#dlg"),
            	width:200,
            	autoRender:true,
            	mask:true
			});
						
			this.imgproxy = this.make('<img style="position:absolute;left:-10000px;top:-10000px">');
			Event.on(this.imgproxy, 'load', function(){
				DOM.attr(this.imgproxy, 'width')>600 && DOM.attr(this.imgproxy, 'width', 600);
				this.dialog.set('width', DOM.attr(this.imgproxy, 'width')+20);
				this.dialog.set('height', DOM.attr(this.imgproxy, 'height')+70);
				this.showDialog();
				DOM.removeAttr(this.imgproxy, 'width');
			}, this);
		},
	
		/**
		 * 显示指定目录的内容
		 * @param {String} id 指定目录id
		 * @param {String} name 指定目录名称
		 */
		explor : function(id, name){
			var self = this, url = sub(this.url.list, {dirId:id, dirType:this.dir.type});
			IO.get(url,	't'+S.now(), function(data){
				
				// 更新当前目录信息
				self.dir = S.merge(self.dir, { id:id, name:name, permission:data.dir.permission||0 }, data.dir);
				DOM.html(self.breadcrumbs, self.getBreadCrumbs(id, name, self.dir.dns));
				
				// 更新toolbar
				self.updateToolbar();
				
				// 构建列表
				var html = [];
				if(self.dir.data.length > 1){
					var parent = self.dir.data[self.dir.data.length - 2];
					parent = parent.split('#');
					if(parent[0] !== ''){
						data[JSON_ROOT].unshift({
							id:parent[0],name:'..',type:'back'
						});
					}			
				}
				S.each(data[JSON_ROOT], function(item, index){
					var cls = [CLS_DOC, item.type.toLowerCase(), (index % 2 != 0 ? 'even' : '')].join(' '),
						prevent = item.prevent?' data-prevent="'+item.prevent+'"' : '',
						type = ' data-type="'+item.type+'"',
						temp = [
						'<div id="', item.id, '" class="', cls , '"', prevent, type, ' unselectable="on">', 
							'<span unselectable="on">', item.name, '</span>',
							'<div class="size">', formatSize(item.size) || '--', '</div>',
							'<div class="date">', (item.date||'--'), '</div>',
							'<div class="author">', (item.author||'--'), '</div>',
						'</div>'
						];
						
					html[html.length] = temp.join('');
				});
				if(html.length == 0){
					html.push('<span>(本文件夹为空)</span>')
				}
				DOM.html(self.box, html.join(''));
				
			}, 'json');
		},
		
		/**
		 * 在container中插入新元素并返回
		 */
		make : function(tag, data){
			return this.container.appendChild(DOM.create(tag, data));
		},
		
		/**
		 * 创建一个工具栏按钮
		 */
		createTool : function(item){
			var tools = DOM.get('.tools', this.toolbar), 
				cls = 'btn_'+item.act,
				tool = tools.appendChild( DOM.create( '<a class="'+cls+'">',{ 'title':item.text, 'data-act':item.act } ) );
			Event.on(tool, 'click', function(ev){
				ev.halt();
				S.isFunction(this[item.act]) && this[item.act](this.selected);
			}, this);
		},
		
		updateToolbar : function(){
			var prevent = [
				{},
				{'dir_new':true, 'doc_upload':true},
				{'dir_new':true},
				{}
			][this.dir.permission],
			tools = S.one('.tools',dm.toolbar).children();
			
			tools.each(function(item){
				var act = getData(item[0], 'act');
				item[prevent[act]? 'hide':'show']();
			});
				
		},
		
		showDialog : function(){
			this.dialog.center();
			this.dialog.show();
		},
		hideDialog : function(){
			this.dialog.hide();
		},
		setDialog : function(){
			
		},
		
		/**
		 * 选中一个或多个文档
		 */
		select : function(el){
			if(!el || DOM.hasClass(el, 'back')) return;
			var id = el.id;
			if(!S.inArray(id, this.selected)){
				DOM.removeClass(this.selectedEls, 'selected');
				this.selected.length = this.selectedEls.length = 0;
				
				this.selected.push(id);
				this.selectedEls.push(el);
				DOM.addClass(this.selectedEls, 'selected');
			}
		},
		
		/**
		 * 根据data重建右键菜单
		 * @param {Boolean} isDir 是否为文件夹
		 */
		updateCtxMenu : function(isDir){
			var	permission = this.dir.permission || 0,
				menu = isDir ? dirMenuTypes[permission] : ( docMenuTypes[this.dir.type] || defaultDocMenu ),
				menuItems = isDir ? actions.dir : actions.doc,
				html = [], i = 0, len = menu.length,
				tpl = '<div id="ctx-{id}" class="ctx-item">{text}</div>';
			
			for(i;i<len;i++){
				var id = menu[i], text = menuItems[id];
				html.push(sub(tpl, { id:id, text:text }));
			}
			
			return html.join('');
		},
		
		/**
		 * 显示右键菜单，显示前通过ajax数据中prevent的值来过滤需要阻止的动作
		 */
		showCtxMenu : function(ev){
			var el = this.selectedEls[0], prevent = getPrevent(el);
			if(this.dir.permission === 1){
				// 当permission为1时，仅有读权限
				S.mix(prevent, { 'delete':true, 'update':true });
			}
			DOM.html( this.ctxmenu, this.updateCtxMenu( isDir( el ) ) );
			
			S.each(DOM.children(this.ctxmenu, '.ctx-item'), function(ctxItem){
				var act = ctxItem.id.replace('ctx-', '');
				DOM[prevent[act] ? 'hide' : 'show'](ctxItem);
			}, this);
			
			DOM.css(this.ctxmenu, { left: ev.pageX, top: ev.pageY });
			this.fire('beforectxmenu', this.ctxmenu);
			DOM.show(this.ctxmenu);
		},
		
		/**
		 * 隐藏右键菜单
		 */
		hideCtxMenu : function(){
			DOM.hide(this.ctxmenu);
		},
		
		/**
		 * 操作按钮链接配置
		 */
		dir_new : function(id){
			var tmp_type = this.dir.type;
			if(tmp_type == '0')
				openwindow(sub(this.url['dir_new'], {dirId:this.dir.id, dirType:tmp_type}),'',900,550);
			else
				openwindow(sub(this.url['dir_new'], {dirId:this.dir.id, dirType:tmp_type}),'',550,350);
		},
		dir_delete : function(id){
			if(confirm('若删除此文件夹，则将子文件夹及文件一同删除，是否确认删除此文件夹？')) {
				window.location.href = sub(this.url['dir_delete'], {dirId:id, dirParId:this.dir.id, dirType:this.dir.type});
			}
		},
		dir_update : function(id){
			openwindow(sub(this.url['dir_update'], {dirId:id, dirType:this.dir.type}),'',550,350);
		},
		dir_setopt : function(id){
			openwindow(sub(this.url['dir_setopt'], {dirId:id, dirType:this.dir.type}),'',500,550);
		},
		dir_setapv : function(id){
			openwindow(sub(this.url['dir_setapv'], {dirId:id, dirType:this.dir.type}),'',500,350);
		},
		doc_upload : function(id){
			openwindow(sub(this.url['doc_upload'], {dirId:this.dir.id, dirType:this.dir.type}),'',900,550);
		},
		doc_view : function(id){
			openwindow(sub(this.url['doc_view'], {docId:id}),'',550,350);
		},
		doc_download : function(id){
			window.location.href = sub(this.url['doc_download'], {docId:id});
		},
		doc_delete : function(id){
			if(confirm('是否确认删除此文件？')) {
				window.location.href = sub(this.url['doc_delete'], {docId:id, dirId:this.dir.id, dirType:this.dir.type});
			}
		},
		doc_update : function(id){
			openwindow(sub(this.url['doc_update'], {docId:id}),'',550,350);
		},
		doc_send : function(id){
			openwindow(sub(this.url['doc_send'], {docId:id}),'',550,350);
		},
		doc_submit : function(id){
			openwindow(sub(this.url['doc_submit'], {docId:id}),'',550,350);
		},
		doc_favorite : function(id){
			openwindow(sub(this.url['doc_favorite'], {docId:id}),'',550,350);
		},
		
		_doc_click : function(el){
			if(isBack(el)){
				this.explor(el.id);
			}
			else{
				this.select(el);
			}
		},
		_doc_contextmenu : function(el, ev){
			ev.halt();
			this.select(el);
			this.showCtxMenu(ev);
		},
		_doc_dblclick :function(el){
            if (isDir(el) || isBack(el)){
				this.explor(el.id, DOM.children(el, 'span')[0].innerHTML);
            }
            else{
            	this.select(el);
            	if(S.indexOf(getData(el, 'type'), imgTypes) != -1){
            		this.imgproxy.src = sub(this.url['doc_download'], {docId:el.id});
            		this.dialog.set('headerContent', S.one('span', el).html());
            		this.dialog.set('bodyContent', 
            			'<div style="padding:10px"><img src="'+sub(this.url['doc_download'], {docId:el.id})+'" /></div>');
            	}
            	else {
            		this.doc_download(el.id);
            	}
            }
		},
		_doc_mouseover : function(el){
			DOM.addClass(el, 'over');
		},
		_doc_mouseout : function(el){
			DOM.removeClass(el, 'over');
		},
		
		/**
		 * 显示loading
		 */
		loading : function(){
			DOM.html(this.box, '<p></p>');
		},
		
		/**
		 * 隐藏loading
		 */
		unloading : function(){
			DOM.remove(this.config.box + ' p');
		},
		refresh : function(){
			this.explor(this.dir.id, this.dir.name);
		},
		/**
		 * 返回面包屑导航的html
		 * @param {String} id 当前目录id
		 * @param {String} name 当前目录名称
		 * @param {String} dns 当前路径的层次标识,例:''为第一层目录， '001.002'为第三层目录
		 */
		getBreadCrumbs : function (id, name, dns){
			var level = dns=='' ? 1 : dns.split('.').length+1;
			var str = crumbs(id, name);
			if(level > navData.length){
			    navData.push(str);
			    this.dir.data.push(id+'#'+name);
			}
			else{
			    navData = navData.slice(0, level);
			    this.dir.data = this.dir.data.slice(0, level)
			}
			return navData.join(" / ");
		}
	});
	
	/**
	 * 根据传入的一个目录的id和name,返回用于面包屑导航的一段html
	 */
	function crumbs(id, name){
		return '<span '+ATTR_DATAID+'="'+id+'" '+ATTR_DATANAME+'="'+name+'" class="'+CLS_NAVLINK+'">'+name+'</span>';
	}
	
	/**
	 * 获取元素上的阻止列表数据data-prevent
	 * @param {HTMLElement} el
	 * @return Object 需要阻止的菜单
	 */
	function getPrevent(el){
		var data = DOM.attr(el, 'data-prevent');
		return data ? eval('('+data+')') : {};	
	}
	
	function getData(el, dataname){
		return DOM.attr(el, 'data-'+dataname);
	}
	/**
	 * 判断一个元素是否为目录
	 */
	function isDir(el){
		return DOM.hasClass(el, CLS_DIR);
	}	
	
	/**
	 * 判断一个元素是否为后退按钮
	 */
	function isBack(el){
		return DOM.hasClass(el, 'back');
	}
	
	/**
	 * 格式化size属性，用KB表示，大于1024KB则用MB表示
	 */
	function formatSize(size){
		if(!size) return '--';
		var size = parseFloat(size, 10);
		if(size<0) return '--';
		return size >= 1024 ? Math.round(size / 1024 * 10) /10 + 'MB' : size + 'KB';
	}
	
	/**
	 * 打开新窗口
	 */
	function openwindow(url,name,w,h){
		var t = (window.screen.availHeight-30-h)/2,
			l = (window.screen.availWidth-10-w)/2;
		window.open(url,name,'height='+h+',innerHeight='+h+',width='+w+',innerWidth='+w+',top='+t+',left='+l
			+',toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=yes');
	}
	
	S.DocManager = DocManager;

}, { requires:['core'] });