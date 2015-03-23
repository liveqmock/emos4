function textLength(id,length){
 var objs = document.getElementsByTagName('p');
		  for(var i=0;i<objs.length;i++){
		  	if(objs[i].id == id){
		  		objs[i].title = objs[i].innerText;
		  		if(objs[i].innerText.length > 20){
		  			objs[i].innerText = objs[i].innerText.substring(0,length) + '...';
		  		}
		  	}
		  }
}


		function check(val, msg) {
			var reg = /^[\w\u4E00-\u9FA5:-]+$/;
			if(!reg.test(val)) {
				alert(msg);
				return false;
			} 
			return true;
		}
		
		function checkReg(reg, val, msg) {
			if(!reg.test(val)) {
				alert(msg);
				return false;
			} 
			return true;
		}