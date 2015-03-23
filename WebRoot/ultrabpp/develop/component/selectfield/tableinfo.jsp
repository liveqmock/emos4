<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/jquery/jquery.jsp" %>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/common/javascript/AjaxBus.js"></script>
		<script type="text/javascript" src="${ctx}/workflow/js/util.js"></script>
	<title>
	</title>
	
<script language="javascript">
    window.onload = function(){
        setCenter(0,61); 
        init();
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	
	function init(){
	
		var initValue = '<%=request.getParameter("resValue")%>';
		var attr = initValue.split(':');
		document.getElementById("tablename").value = attr[0]!=undefined?attr[0]:"";
		document.getElementById("colname").value = attr[1]!=undefined?attr[1]:"";
	}
	function submit()
		{
			var value = "";
			value =  document.getElementById("tablename").value;
			value += ":"+document.getElementById("colname").value+":"+document.getElementById("colname").value;
			if(opener.document.getElementById("selectField.typeResource")!=undefined){
				opener.document.getElementById("selectField.typeResource").value = value;
			}else{
				opener.document.getElementById("collectField.typeResource").value = value;
			}
			window.close();
		}
</script>
</head>
<body>${resValue}
<div class="content">
		<div class="title_right">
			<div class="title_left">
				<span class="title_bg">
						<span class="title_icon2">
							
						</span>
				</span>
				<span class="title_xieline"></span>
			</div>
		</div>
		    <div class="scroll_div" id="center">
						 <div class="tabContent_top">
							<table class="add_user">
								<tr>
									<td class="texttd">
									数据库表名：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="tablename" id="tablename" class="textInput" value="" maxlength="50"/>
									</td>
								</tr> 
								<tr>
									<td class="texttd">
									字段名：<span class="must">*</span>
									</td>
									<td>
									<input type="text" name="colname" id="colname" class="textInput" value="" maxlength="50"/>
									</td>
								</tr>
								
							 </table>
						</div>
						   
				</div>
		
	</div>
	<div class="add_bottom">
			 <input type="hidden" id="dtcode" name="dtcode" value=""/>
		     <input type="hidden" id="dtfullname" name="dtfullname" value=""/>
			<input type="button" value="确定" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="submit();"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
	</div>
</body>
</html>