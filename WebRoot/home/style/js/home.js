$(function(){
	
	//首页公告模块
	$.post(basePath+'notice/noticeView.action',{},function(data) {
		if(data!=null && data!='null'){
			$("#gk_cnt").replaceWith(data);
		}
	});
	//加载常用功能
	if($("#commonFunctionality").length > 0){
		$.post(basePath+"home/getCreateSheetsType.action",{},function(data){
			var html = "";
			$.each(data,function(index,item){
					var href = basePath+"sheet/createNewSheet.action?baseSchema="+item.schema;
					html += "<li><a target='_blank' href='"+href+"'><span class='order_add'></span>"+item.name+"</a></li>";				
			});
			$("#commonFunctionality").append(html)
		},"json");
	}
	
	//加载常用服务目录
	
	if($("#commonServiceCategory").length > 0){
		var para = {};
		if(typeof selfService != "undefined") para.forwho = "1";
		$.post(basePath+"serverQuest/getCommonServerQuest.action",para,function(data){
			var html = "";
			$.each(data,function(index,item){
				var href = basePath+"ultrabpp/view.action?mode=NEW&baseSchema=CDB_SERVICEREQUEST&cp.serviceCategory="+encodeURI(encodeURI(item.serverquestfullname));
				if(typeof selfService != "undefined"){
					href += "&customizedPage=mainSelfHelp&formjsp=formSelfHelp"
				}
				html += "<li><a title='"+item.serverquestfullname+"' target='_blank' href='"+href+"'>"+item.serverquestname+"</a></li>";
			})
			$("#commonServiceCategory").append(html);
		},"json")
	}
});