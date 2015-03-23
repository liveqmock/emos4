<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/core/taglibs.jsp"%>  
	<script language="javascript">
		window.onresize = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);
		}
		
		window.onload = function() 
		{
		  setCenter(0, LAYOUT_LIST_RIGHT);changeRow_color("tab");
		  removeUnReloadCache();
		}
		
		//清空缓存
		function reloadfresh()
		{
			var cachename = document.getElementsByName('checkid');
			var cachenames = '';
			for(var i=0;i<cachename.length;i++){
				if(cachename[i].checked){
					cachenames += cachename[i].value + ',' ;
				}
			}
			if(cachenames==''){
				alert('<eoms:lable name="sm_msg_chooseOpObject"/>'+'!');
			}else{
				if(confirm("<eoms:lable name='com_btn_confirm_del'/>"))
				{
					var temp = cachenames.lastIndexOf(',');
					cachenames = cachenames.substring(0,temp);
					window.location.href = '${ctx}/cache/reload.action?cachenames='+cachenames;
				}
			}	
		}
		
		
		//删除WF相关的不能刷缓存的项
		var unReloadCache = 'WFType|WFDefine|WFVersion|WFInterface';
		function removeUnReloadCache(){
			var caches = document.getElementsByName("checkid");
			for(var i=0;i<caches.length;i++){
				var cache = caches[i];
				if(unReloadCache.indexOf(cache.value) != -1){
					cache.disabled = 'false';
					cache.value = '';
				}
			}
		}
	</script>
	</head>
	<body>
	  	<dg:datagrid  var="cache" items="${caches}" title="${nodePath}" mempage="true">
	  		<dg:lefttoolbar>
		      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="reloadfresh();"><eoms:lable name="com_btn_refresh"/></li>
		      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'" ><eoms:lable name="com_btn_help"/></li>
	  		</dg:lefttoolbar>
	    	<dg:gridtitle>
		    	<tr>
		    		<th width="10"><input id="checkidAll" name="checkidAll" type="checkbox" onclick="checkAll('checkid')"></input></th>
		    		<th width="40%"><eoms:lable name="sm_lb_cachename"/></th>
		    		<th width="14%"><eoms:lable name="sm_lb_objectnum"/></th>
		    		<th width="14%"><eoms:lable name="com_lb_status"/></th>
		    		<th width="14%"><eoms:lable name="sm_lb_memory"/></th>
		    		<th width="14%"><eoms:lable name="sm_lb_disk"/></th>
		    	</tr>
	    	</dg:gridtitle> 
			<dg:gridrow>
				<tr class="${cache_row}">
					<td><input name="checkid" type="checkbox" value='${cache.cachename}'></input></td>
			        <td>${cache.cachename}</td>
			        <td>${cache.cacheEleCount}</td>
			        <td>${cache.status}</td>
			   		<td>${cache.memoryStoreSize}</td>
			   		<td>${cache.diskStoreSize}</td>
			    </tr>
			</dg:gridrow>
		</dg:datagrid>
	</body>
</html>