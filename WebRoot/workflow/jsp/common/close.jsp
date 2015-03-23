<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/core/taglibs_jsp.jsp"%>

<script type="text/javascript">
		var msg = '${msg}';
		var error = '${error}';
		var params = [];
		if(msg != '' && msg != 'null') {
			params = [1, msg];
		} else {
			params = [2, error];
		}
		window.showModalDialog($ctx+'/workflow/jsp/common/MessagePopup.html?'+Math.random(),params,'dialogWidth:400px;dialogHeight:400px;edge:sunken;resizable:yes');
		try{
		  	opener.location.reload();  
		  }catch(e){
		  } 
		window.opener=null;
   		window.open('','_self');//不提示是否关闭
	 	window.close();
</script>
