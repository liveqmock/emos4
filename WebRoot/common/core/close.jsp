<script type="text/javascript">
	var msg = "${msg}";
	if(msg != '' && msg != 'null') {
		alert(msg);
	}
  try{
  	opener.location.reload();  
  }catch(e){
  } 
  window.opener=null;
  window.open('','_self');
  window.close();
</script>