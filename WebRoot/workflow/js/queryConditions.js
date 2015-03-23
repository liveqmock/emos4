  function saveConditions(ticket_type){
			   var params = $("#form1").serialize();
			   var jsonData = encodeURI(params); 
			     $.ajax({
			    	 	contentType:"application/x-www-form-urlencoded;",charset:'utf-8',
						url: $ctx+"/business/saveConditions.action?ticket_type="+ticket_type+"&"+jsonData,
						type: "post",
						beforeSend: function() {
							return true;
						},
						success: function(jsonObject) {
							if(jsonObject == 'T'){
								alert("保存成功");
							}else{
								alert("保存失败");
							}
							
						}
					});	 
		   }
		   
   function setConditions(ticket_type,usersession_id){
	   var url = $ctx+"/business/setConditions.action";
	   var sqlName = "select condition_info from bs_t_sm_queryConditions where ticket_type = '"+ticket_type+"' and user_id = '"+usersession_id+"'";
		$.post(encodeURI(url),{contentType:"application/x-www-form-urlencoded;",charset:'utf-8',Action:"post","sqlName":sqlName},function(data){
			var items = data[0]
			for(i in items){
				//alert(i+":"+data[0][i])
				$(":input[name="+i+"]").val(items[i]);//给组件赋值
			}
		},"json");
   }
   
   
   function z_reset(){
	   $(':input','#form1')  
	   .not(':button, :submit, :reset ')
	   .val('')  
	   .removeAttr('checked')  
	   .removeAttr('selected'); 
   }