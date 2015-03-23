function searchCategory(viewFieldName,className,releaseScope){
	var forwho = encodeURI(encodeURI("对外"));
	var ajaxURL = $ctx+'/autosearch/autoSearchEx.action?sqlQuery=SQL_CDB_SERVEICE_CATEGORY_S.query&opt.forwho='+forwho
				  + '&opt.releaseScope='+encodeURI(encodeURI(releaseScope))
				  + '&opt.dilever=3';
	var combox =  new ListCombox({"emptyText" : "",renderTo : viewFieldName,
		"fieldClass":className,showFormat:'{value}',listWidth:350,
		'AjaxUrl':ajaxURL,'queryParam':'paramKey','IdField':"","NameField":"view_"+viewFieldName,"NameFieldName":'',
		'listeners':{
        select: function (field, newValue, oldValue) {
        	window.open($ctx+"/ultrabpp/view.action?mode=NEW&baseSchema=CDB_SERVICEREQUEST&cp.serviceCategory="+encodeURI(encodeURI(newValue.json.value)));
        	combox.clearValue();
        },
        scope: this
    }
	});
}