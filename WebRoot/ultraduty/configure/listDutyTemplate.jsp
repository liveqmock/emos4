<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/common/core/taglibs.jsp"%>
        <%@ include file="/common/plugin/jquery/jquery.jsp"%>
		<script language="javascript"
			src="${ctx}/common/javascript/date/WdatePicker.js"></script>
		<title>表单管理</title>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,56);
			}
			window.onload = function() {
				setCenter(0,56);changeRow_color("tab");
			}
			function checkAll(){
			}
			function unselectall(){
			
			}
			function deleteh(){
			
			}
			function openwin(actname)
	        {
		       window.open(actname);
	        }
	        function edittmp(pid){
	           window.open('editDutyTemplate.action?pid='+pid);
	        }
	        function showpreview(id){
	           window.open('${ctx}/ultraform/formAdd.action?isEdit=no&templateId='+id);
	        }
		</script>
	</head>
	
	<body>
  	<dg:datagrid var="template" items="${dutyTemplateList}" title="${nodePath }">
  		<dg:lefttoolbar>
	      <li class="page_search_button"  onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'"  onclick="showsearch()"><eoms:lable name='com_btn_search'/></li>
	      <li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"><eoms:lable name='com_btn_refresh'/></li>
	      <li class="page_add_button" onmouseover="this.className='page_add_button_hover'" onmouseout="this.className='page_add_button'" onclick="openwin('newDutyTemplate.action')"><eoms:lable name='com_btn_add'/></li>
	      <li class="page_help_button" onmouseover="this.className='page_help_button_hover'" onmouseout="this.className='page_help_button'"><eoms:lable name='com_btn_help'/></li>
  		</dg:lefttoolbar>
  		<dg:condition>
	      <table  class="serachdivTable">
	        <tr>
	          <td class="serachdivTableTd">模板名称：</td>
	          <td width="35%"><input type="text" id="textfield" name="dutyTemplate.name" class="textInput" value="${dutyTemplate.name }"/></td>

	          <td class="serachdivTableTd">启用停用：</td>
	          <td width="35%">
	          	<select class="select" name="dutyTemplate.actived">
					<option selected="selected" value="-1">--请选择--</option>
					<c:forEach items="${trueOrFalseMap}" var="map">
						<option value="${map.key }" <c:if test="${dutyTemplate.actived == map.key}">selected="selected"</c:if>>${map.value }</option>
					</c:forEach>
				</select>
			  </td>
	        </tr>
	        <tr>
				<td colspan="6" align="center">
					<input type="submit" name="button" id="button" value="<eoms:lable name='com_btn_lookUp'/>" class="searchButton" 
					onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'" onclick="showsearch(0)" />
					<input type="reset" name="button2" id="button2" value="<eoms:lable name='com_btn_reset'/>" class="ResetButton" 
					onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
				</td>
	        </tr>
	      </table>  		
  		</dg:condition>
    	<dg:gridtitle>
	    	<tr>
	    		<th align="center">模板名称</th>
	    		<th align="center">创建人</th>
	    		<th align="center">模板状态</th>
	    		<th align="center">模板类型</th>
	    		<th align="center">操作</th>
	    	</tr>
    	</dg:gridtitle> 
		<dg:gridrow>
			<tr class="${template_row}">
		        <td width="20%" onclick="edittmp('${template.pid}');">${template.name}</td>
		        <td width="20%" onclick="edittmp('${template.pid}');">${template.createUser}</td>
		        <td width="20%" onclick="edittmp('${template.pid}');">${template.activedName}</td>
		        <td width="10%" onclick="edittmp('${template.pid}');"><eoms:dic dictype="dutyTemplateType" value="${template.templateType}" isfullname='true'/></td>

		        <td width="20%">
		        <a onclick="showpreview('${template.templateid}')"><u><b>预览</b></u></a>
		        </td>
		    </tr>
		</dg:gridrow>
	</dg:datagrid>
  </body>
</html>
