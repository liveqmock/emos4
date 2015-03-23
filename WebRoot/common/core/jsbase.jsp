<%@ page pageEncoding="UTF-8"%>
<script src="${ctx}/common/plugin/kissy/kissy${param['ks-debug']!=null?'':'-min'}.js"></script>
<script>
KISSY.ready(function(S){
	var D = S.DOM, E = S.Event, 
	header = D.get('#header'), footer = D.get('#footer'), content = D.get('#content'),
	h = D.viewportHeight();
	
	if(D.hasClass('body', 'fit') && header && content && footer) {
		E.on(window, 'load resize', function(){
			D.height(content, h - D.height(header) - D.height(footer));
		});
	}
});
KISSY.add('dhtml', { fullpath:'${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js'});
KISSY.add('tree', { fullpath:'${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js', cssfullpath: '${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css', requires:['dhtml']});
</script>