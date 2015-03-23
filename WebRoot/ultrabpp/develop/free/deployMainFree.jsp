<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0,30);
		}
		
		window.onload = function() 
		{
			setCenter(0,30);
		}
		function addPanelGroup(parentId)
		{
			alert("在容器组内 添加一个容器 容器组ID是 "+parentId);
		}
		function addMainFreeField(fieldType)
		{
		    if(fieldType != null && fieldType != ""){
		      var base = document.getElementById('base').value;
		      var src = '${ctx}/ultrabpp/develop/editField.action?baseSchema='+base+'&actionField=action&fieldType='+fieldType;
		      window.open(src,'','width=600px,height=350px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		    }
		}
		function addField(fieldType)
		{
		    if(fieldType!=null&&fieldType!=""){
		    var base = document.getElementById('base').value;
		    var src = '${ctx}/ultrabpp/develop/editField.action?baseSchema='+base+'&fieldType='+fieldType;	  
		    window.open(src,'','width=600px,height=350px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		    }  
		}
        function editField(baseSchema,fieldID,fieldType){
            var src = '${ctx}/ultrabpp/develop/editField.action?baseSchema='+baseSchema+'&fieldID='+fieldID+'&fieldType='+fieldType; 
            window.open(src,'','width=600px,height=350px,top=250,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
        }
        function deleteField(baseSchema,fieldID,fieldType){
            if(window.confirm("如果该组件为容器组，或者容器，它下面的所有其他组件将一并删除！您确定要删除该组件吗？")){
            	document.getElementById('delFieldID').value = fieldID;
            	document.getElementById('delFieldType').value = fieldType;
            	document.getElementById('delvalue').value = 'delvalue';
            	document.getElementById('fieldForm').action = '${ctx}/ultrabpp/develop/delWorksheetField.action';
				document.getElementById('fieldForm').submit();
            } 
        }
        function rollBack(baseSchema,fieldID,fieldType){
            if(window.confirm("您确定要恢复吗？")){   
			   	document.getElementById('delFieldID').value = fieldID;
            	document.getElementById('delFieldType').value = fieldType;  
            	document.getElementById('delvalue').value = 'delvalue';
            	document.getElementById('fieldForm').action = '${ctx}/ultrabpp/develop/rollBackField.action';
				document.getElementById('fieldForm').submit();	
            }
        }
        
</script>
	</head>
	<body>
		<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">${nodePath}</span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
  <dg:datagrid  var="baseField" items="${baseFieldList}">
  		<div class='page_div_bg'>
			<div class='page_div'>
	  		<li>
	  		<s:select  cssClass="paddingHack"  name="fieldType" list="#{'0':'请选择添加主表单组件','PanelField':'容器','PanelGroupField':'容器组','CharacterField':'字符型','ButtonField':'按钮','BlankField':'占位','LabelField':'文本','SelectField':'下拉框','ViewField':'视图','AssignTreeField':'派发树','TimeField':'日期','AttachmentField':'附件','RollbackListField':'回滚按钮'}" onchange="addField(this.value);" listKey="key" listValue="value">
	  		</s:select>		
	  		<s:select  cssClass="paddingHack"  name="fieldType" list="#{'0':'请选择添加动作组件','CharacterField':'字符型','ButtonField':'按钮','BlankField':'占位','LabelField':'文本','SelectField':'下拉框','ViewField':'视图','AssignTreeField':'派发树','TimeField':'日期','AttachmentField':'附件','RollbackListField':'回滚按钮'}" onchange="addMainFreeField(this.value);" listKey="key" listValue="value">
	  		</s:select>  
	  		</li>
  		 </div>
		</div>
                                               
			<table id='tab' class='tableborder'>   
			  <tr>
			     <th><eoms:lable name="bpp_develop_worksheet_operate"/></th><!--部署前操作  -->
	    	     <th><eoms:lable name="bpp_develop_worksheet_label"/></th><!--中文名称  -->
			     <th><eoms:lable name="bpp_develop_worksheet_fieldname"/></th><!--英文名称  -->
				 <th><eoms:lable name="bpp_develop_worksheet_fieldtype"/></th><!--类型  -->
				 <th><eoms:lable name="bpp_develop_worksheet_baseschema"/></th><!--工单类型  -->
				 <th><eoms:lable name="bpp_develop_worksheet_visiable"/></th><!--是否可见  -->
				 <th><eoms:lable name="bpp_develop_worksheet_ordernum"/></th><!--排序  -->
				 <th><eoms:lable name="bpp_develop_worksheet_additional_display"/></th><!--显示形式  -->
				 <th><eoms:lable name="bpp_develop_worksheet_operation"/></th><!--操作  -->
			  </tr>  
		      <c:forEach var="baseField" items="${baseFieldList}">
			    <tr>
			        <td><!--部署前操作-->
			          <c:choose >
						<c:when test="${baseField.operate == 'add'}">
							<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">+</a>
						</c:when>
						<c:when test="${baseField.operate == 'update'}">
							<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">*</a>
						</c:when>
						<c:when test="${baseField.operate == 'delete'}">
						    <a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">-</a>
						</c:when>
					  </c:choose>
					   &nbsp;&nbsp;(${baseField.hasDeploy})&nbsp;,&nbsp;(${baseField.operate})
			        </td>
			        <td><!--中文名称  -->
			           <c:if test="${baseField.operate == 'delete'}">${baseField.label}</c:if>
			           <c:if test="${baseField.operate != 'delete'}">
			                 <a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">${baseField.label}</a></c:if>
			        </td>
			        <td><!--英文名称  -->
			           <c:if test="${baseField.operate == 'delete'}">${baseField.fieldName}</c:if>
			           <c:if test="${baseField.operate != 'delete'}">
			                 <a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">${baseField.fieldName}</a></c:if>
			        </td>
			        <td><!--类型  -->
			           <c:if test="${baseField.operate == 'delete'}">
				           <c:choose>
					          <c:when test="${baseField.fieldType == 'PanelField'}"><eoms:lable name="bpp_develop_worksheet_additional_container"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'PanelGroupField'}">
		                          <eoms:lable name="bpp_develop_worksheet_additional_containergroup"/>
		                      </c:when>
					          <c:when test="${baseField.fieldType == 'CharacterField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_character"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'ButtonField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_button"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'BlankField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_occupying"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'LabelField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_text"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'SelectField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_select"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'ViewField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_view"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'AssignTreeField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_distributiontree"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'TimeField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_date"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'AttachmentField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_attachment"/>
					          </c:when>
					          <c:when test="${baseField.fieldType == 'RollbackListField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_rollback"/>
					          </c:when>
				          </c:choose>
				       </c:if>
				       <c:if test="${baseField.operate != 'delete'}">
				       <a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
				       <c:choose>
				          <c:when test="${baseField.fieldType == 'PanelField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_container"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'PanelGroupField'}">
	                          <eoms:lable name="bpp_develop_worksheet_additional_containergroup"/>
	                      </c:when>
				          <c:when test="${baseField.fieldType == 'CharacterField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_character"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'ButtonField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_button"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'BlankField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_occupying"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'LabelField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_text"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'SelectField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_select"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'ViewField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_view"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'AssignTreeField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_distributiontree"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'TimeField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_date"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'AttachmentField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_attachment"/>
				          </c:when>
				          <c:when test="${baseField.fieldType == 'RollbackListField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_rollback"/>
				          </c:when>
				          </c:choose>  
				        </a>
				       </c:if>
			        </td>
			        <td><!--工单类型  -->
			           <c:if test="${baseField.operate == 'delete'}">${baseField.baseSchema}</c:if>
			           <c:if test="${baseField.operate != 'delete'}"><a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">${baseField.baseSchema}</a></c:if>
			        </td>
			        <td><!--是否可见  -->
			            <c:if test="${baseField.operate == 'delete'}">
			             <c:choose>
			               <c:when test="${baseField.visiable == '1'}">
			               <eoms:lable name="bpp_develop_worksheet_additional_yes"/>
			               </c:when>
			               <c:otherwise>
			                 <eoms:lable name="bpp_develop_worksheet_additional_no"/>
			               </c:otherwise>
			             </c:choose>
			           </c:if>
			           <c:if test="${baseField.operate != 'delete'}">
				        <a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
				          <c:choose>
				             <c:when test="${baseField.visiable == '1'}"><eoms:lable name="bpp_develop_worksheet_additional_yes"/></c:when>
				             <c:otherwise><eoms:lable name="bpp_develop_worksheet_additional_no"/></c:otherwise>
				          </c:choose>
				        </a> 
				       </c:if> 
			        </td>
			        <td><!--排序  -->
				       <c:if test="${baseField.operate == 'delete'}">
				           ${baseField.orderNum}
				       </c:if>
				       <c:if test="${baseField.operate != 'delete'}">
				       <a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">${baseField.orderNum}</a>
				       </c:if>
			        </td>
			        <td>
			            <eoms:lable name="bpp_develop_worksheet_additional_form"/><!--主表单-->
			        </td>
			        <td>
			            <c:if test="${baseField.operate=='delete'}">
						   <a href="javascript:;"  onclick="rollBack('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
						      <eoms:lable name="bpp_develop_worksheet_additional_recovery"/></a>
					    </c:if>
					    <c:if test="${baseField.operate!='delete'}">
                           <a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
                              <eoms:lable name="bpp_develop_worksheet_additional_update"/></a>
                           <a href="javascript:;" onclick="deleteField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
                              <eoms:lable name="bpp_develop_worksheet_additional_delete"/></a>
                          <input type="hidden" id="base" value="${baseField.baseSchema}"/>
                       </c:if>
			        </td>
			    </tr>
		      </c:forEach>
		      <c:forEach var="actionField" items="${actionFieldFreeList}">
			    <tr> 
			        <td><!--部署前操作-->
			          <c:choose >
						<c:when test="${actionField.operate == 'add'}">
							<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">+</a>
						</c:when>
						<c:when test="${actionField.operate == 'update'}">
							<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">*</a>
						</c:when>
						<c:when test="${actionField.operate == 'delete'}">
						    <a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">-</a>
						</c:when>
					  </c:choose>
					   &nbsp;&nbsp;(${actionField.hasDeploy})&nbsp;,&nbsp;(${actionField.operate})
			        </td>
			         <td><!--中文名称  -->
			           <c:if test="${actionField.operate == 'delete'}">${actionField.label}</c:if>
			           <c:if test="${actionField.operate != 'delete'}">
			                 <a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">${actionField.label}</a></c:if>
			        </td>
			        <td><!--英文名称  -->
			           <c:if test="${actionField.operate == 'delete' || actionField.operate == 'update'}">${actionField.fieldName}</c:if>
			           <c:if test="${actionField.operate != 'delete' && actionField.operate != 'update'}">
			                 <a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">${actionField.fieldName}</a></c:if>
			        </td>
				    <td><!--类型  -->
			            <c:if test="${actionField.operate == 'delete'}">
				           <c:choose>
					          <c:when test="${actionField.fieldType == 'CharacterField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_character"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'ButtonField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_button"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'BlankField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_occupying"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'LabelField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_text"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'SelectField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_select"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'ViewField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_view"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'AssignTreeField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_distributiontree"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'TimeField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_date"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'AttachmentField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_attachment"/>
					          </c:when>
					          <c:when test="${actionField.fieldType == 'RollbackListField'}">
					              <eoms:lable name="bpp_develop_worksheet_additional_rollback"/>
					          </c:when>
				          </c:choose>
				       </c:if>
				       <c:if test="${actionField.operate != 'delete'}">
				       <a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
				       <c:choose>
				          <c:when test="${actionField.fieldType == 'CharacterField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_character"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'ButtonField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_button"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'BlankField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_occupying"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'LabelField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_text"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'SelectField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_select"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'ViewField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_view"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'AssignTreeField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_distributiontree"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'TimeField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_date"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'AttachmentField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_attachment"/>
				          </c:when>
				          <c:when test="${actionField.fieldType == 'RollbackListField'}">
				              <eoms:lable name="bpp_develop_worksheet_additional_rollback"/>
				          </c:when>
				          </c:choose>  
				        </a>
				       </c:if>
			        </td>
			        
			        <td><!--工单类型  -->
			           <c:if test="${actionField.operate == 'delete'}">${actionField.baseSchema}</c:if>
			           <c:if test="${actionField.operate != 'delete'}"><a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">${actionField.baseSchema}</a></c:if>
			        </td>
			        
			        <td><!--是否可见  -->
			          <c:if test="${actionField.operate == 'delete'}">
			             <c:choose>
			               <c:when test="${actionField.visiable == '1'}">
			               <eoms:lable name="bpp_develop_worksheet_additional_yes"/>
			               </c:when>
			               <c:otherwise>
			                 <eoms:lable name="bpp_develop_worksheet_additional_no"/>
			               </c:otherwise>
			             </c:choose>
			           </c:if>
			           <c:if test="${actionField.operate != 'delete'}">
				        <a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
				          <c:choose>
				             <c:when test="${actionField.visiable == '1'}"><eoms:lable name="bpp_develop_worksheet_additional_yes"/></c:when>
				             <c:otherwise><eoms:lable name="bpp_develop_worksheet_additional_no"/></c:otherwise>
				          </c:choose>
				        </a> 
				       </c:if>
			        </td>
			        <td><!--排序  -->
			            <c:if test="${actionField.operate == 'delete'}">
				           ${actionField.orderNum}
				        </c:if>
				        <c:if test="${actionField.operate != 'delete'}">
				          <a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">${actionField.orderNum}</a>
				         </c:if>
			       </td>
			       <td>
			           <eoms:lable name="bpp_develop_worksheet_additional_action"/><!--动作-->
			       </td>
			        <td><!--操作  -->
			            <c:if test="${actionField.operate=='delete'}">
						   <a href="javascript:;"  onclick="rollBack('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
						      <eoms:lable name="bpp_develop_worksheet_additional_recovery"/></a> <!--恢复-->
					    </c:if>
					    <c:if test="${actionField.operate!='delete'}">
                           <a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
                              <eoms:lable name="bpp_develop_worksheet_additional_update"/></a>
                           <a href="javascript:;" onclick="deleteField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
                              <eoms:lable name="bpp_develop_worksheet_additional_delete"/></a>
                       </c:if>
			        </td>
			    </tr>
		      </c:forEach>
		   </table>
			
	</dg:datagrid>
    <s:form name="fieldForm" id="fieldForm">
		<input type="hidden" name="baseSchema" id="baseSchema" value="${baseSchema}"/>
		<input type="hidden" name="delFieldID" id="delFieldID"/>
		<input type="hidden" name="delFieldType" id="delFieldType" />
		<input type="hidden" name="delvalue" id="delvalue"/>
	</s:form>
	
	</body>
</html>

