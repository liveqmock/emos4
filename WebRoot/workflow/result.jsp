<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<script type="text/javascript">
		var message = '<%=request.getAttribute("message")%>';
		if(message != '' && message != null && message != 'null'){
			alert(message);
		}
		window.opener.location.reload();
		window.close();
	</script>
</html>