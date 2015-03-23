<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<script language="javascript">
		window.onresize = function() 
		{
			setCenter(0,50);
		}
		
		window.onload = function() 
		{
			setCenter(0,50);
			
			initFieldInfo();
		}
		
		var fieldInfoList = new Array();
		function initFieldInfo()
		{
			
			var selectObj=document.getElementById("mainFieldSelect");
			selectObj.outerHTML = "${optionsString}";
			
			
			var selectObjOther=document.getElementById("actionFieldSelect");
			selectObjOther.outerHTML = "${optionsStringOther}";
		}
		function addActionField(fieldType)
		{
		    if(fieldType != null && fieldType != ""){
		      var base = document.getElementById('base').value;
		      var src = '${ctx}/ultrabpp/develop/editField.action?baseSchema='+base+'&actionField=action&fieldType='+fieldType;
		      window.open(src,'','width=600px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		    }
		    document.getElementById('actionFieldSelect').selectedIndex = '0';
		}
		function addMainField(fieldType)
		{
		    if(fieldType!=null&&fieldType!=""){
		    var base = document.getElementById('base').value;
		    
		    var src = '${ctx}/ultrabpp/develop/editField.action?baseSchema='+base+'&fieldType='+fieldType;	  
		    window.open(src,'','width=600px,height=400px,top=250px,left=300px,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
		    } 
		    document.getElementById('mainFieldSelect').selectedIndex = '0';
		}
        function editField(baseSchema,fieldID,fieldType,operate){
        	if(operate == 'delete'){
        		return false;
        	}
            var src = '${ctx}/ultrabpp/develop/editField.action?baseSchema='+baseSchema+'&fieldID='+fieldID+'&fieldType='+fieldType; 
            window.open(src,'','width=600px,height=400px,top=250,left=300,Location=no,Toolbar=no,Resizable=yes,scrollbars=no');
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
        
        function preview(baseSchema)
		{
		    var src = '${ctx}/ultrabpp/previewMain.action?baseSchema='+baseSchema+'&mode=PREVIEWMAIN';	  
		    window.open(src);
		}
		
        function deploy(baseSchema)
		{
		    var src = '${ctx}/ultrabpp/deployMain.action?baseSchema='+baseSchema;	  
		    window.open(src);
		}
		function FieldConfig(_name, _label,_extendsLabel)
		{
			this.key = _name;
			this.value = _label;
			this.group = _extendsLabel;
		}

        function deployAll(baseSchema)
		{
		    var src = '${ctx}/ultrabpp/deployAll.action?baseSchema='+baseSchema;	  
		    window.open(src);
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
		<div class='page_div_bg'>
			<div class='page_div'>
				<li>
					<select id="mainFieldSelect" name="mainFieldSelect" ></select>
					<select id="actionFieldSelect" name="actionFieldSelect"></select>
				</li>
				<li>
					<input type="button" value="预览" onclick="preview('${baseSchema}')" />
				</li>
				<li>
					<input type="button" value="主工单部署" onclick="deploy('${baseSchema}')" />
				</li>
				<li>
					<input type="button" value="整体部署" onclick="deployAll('${baseSchema}')" />
				</li>
			</div>
		</div>
		<div class='scroll_div' id='center'>
			<table id='tab' class='tableborder'>
				<tr>
					<th>
						<eoms:lable name="bpp_develop_worksheet_operate" />
					</th>
					<!--部署前操作  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_label" />
					</th>
					<!--中文名称  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_fieldname" />
					</th>
					<!--英文名称  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_fieldtype" />
					</th>
					<!--类型  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_baseschema" />
					</th>
					<!--工单类型  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_visiable" />
					</th>
					<!--是否可见  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_ordernum" />
					</th>
					<!--排序  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_additional_display" />
					</th>
					<!--显示形式  -->
					<th>
						<eoms:lable name="bpp_develop_worksheet_operation" />
					</th>
					<!--操作  -->
				</tr>
				<c:forEach var="baseField" items="${baseFieldList}">
					<tr>
						<td align="center" valign="middle">
							<!--部署前操作-->
							<c:choose>
								<c:when test="${baseField.hasDeploy=='0'&&baseField.operate == 'add'}">
									<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
									<img src="${ctx}/common/style/blue/images/add.png" title="新增" style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
								<c:when test="${baseField.hasDeploy=='0'&&baseField.operate == 'update'}">
									<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
									<img src="${ctx}/common/style/blue/images/title/tag7.png" title="修改" style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
								<c:when test="${baseField.hasDeploy=='0'&&baseField.operate == 'delete'}">
									<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
									<img src="${ctx}/common/style/blue/images/del.png" title="删除" style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
								<c:when test="${baseField.hasDeploy=='1'}">
									<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');">
									<img src="${ctx}/common/style/blue/images/upload/02.png" title="部署成功" style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
							</c:choose>
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}','${baseField.operate}');">${baseField.label}</a>
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}','${baseField.operate}');">${baseField.fieldName}</a>
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}','${baseField.operate}');"> 
							 
									<c:choose>
										<c:when test="${baseField.fieldType == 'PanelField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_container" />
										</c:when>
										<c:when test="${baseField.fieldType == 'PanelGroupField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_containergroup" />
										</c:when>
										<c:when test="${baseField.fieldType == 'CharacterField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_character" />
										</c:when>
										<c:when test="${baseField.fieldType == 'ButtonField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_button" />
										</c:when>
										<c:when test="${baseField.fieldType == 'BlankField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_occupying" />
										</c:when>
										<c:when test="${baseField.fieldType == 'LabelField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_text" />
										</c:when>
										<c:when test="${baseField.fieldType == 'SelectField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_select" />
										</c:when>
										<c:when test="${baseField.fieldType == 'CollectField'}">
											选择框
										</c:when>
										<c:when test="${baseField.fieldType == 'ViewField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_view" />
										</c:when>
										<c:when test="${baseField.fieldType == 'AssignTreeField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_distributiontree" />
										</c:when>
										<c:when test="${baseField.fieldType == 'TimeField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_date" />
										</c:when>
										<c:when test="${baseField.fieldType == 'AttachmentField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_attachment" />
										</c:when>
										<c:when test="${baseField.fieldType == 'RollbackListField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_rollback" />
										</c:when>
										<c:when test="${baseField.fieldType == 'HiddenField'}">
										隐藏域1
										</c:when>
									</c:choose> 
									
										</a>
									
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}','${baseField.operate}');">${baseField.baseSchema}</a>
						</td>
						<td>
								<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}','${baseField.operate}');"> 
									<c:choose>
										<c:when test="${baseField.visiable == '1'}">
											<eoms:lable name="bpp_develop_worksheet_additional_yes" />
										</c:when>
										<c:otherwise>
											<eoms:lable name="bpp_develop_worksheet_additional_no" />
										</c:otherwise>
									</c:choose>
								</a>
							
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}','${baseField.operate}');">${baseField.orderNum}</a>
						</td>
						<td>
							<eoms:lable name="bpp_develop_worksheet_additional_form" />
							<!--主表单-->
						</td>
						<td>
							<c:if test="${baseField.operate=='delete'}">
								<a href="javascript:;" onclick="rollBack('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');"> <eoms:lable name="bpp_develop_worksheet_additional_recovery" /> </a>
							</c:if>
							<c:if test="${baseField.operate!='delete'&&baseField.parentID!='main'}">
								<a href="javascript:;" onclick="editField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}','${baseField.operate}');"> <eoms:lable name="bpp_develop_worksheet_additional_update" /> </a>
								<a href="javascript:;" onclick="deleteField('${baseField.baseSchema}','${baseField.id}','${baseField.fieldType}');"> <eoms:lable name="bpp_develop_worksheet_additional_delete" /> </a>
							</c:if>
							<input type="hidden" id="base" value="${baseField.baseSchema}" />
						</td>
					</tr>
				</c:forEach>
				<c:forEach var="actionField" items="${actionFieldFreeList}">
					<tr>
						<td  align="center" valign="middle">
							<!--部署前操作-->
							<c:choose>
								<c:when test="${actionField.hasDeploy=='0'&&actionField.operate == 'add'}">
									<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
									<img src="${ctx}/common/style/blue/images/add.png"  title="新增"  style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
								<c:when test="${actionField.hasDeploy=='0'&&actionField.operate == 'update'}">
									<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
									<img  src="${ctx}/common/style/blue/images/title/tag7.png" title="修改"  style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
								<c:when test="${actionField.hasDeploy=='0'&&actionField.operate == 'delete'}">
									<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
									<img  src="${ctx}/common/style/blue/images/del.png" title="删除"  style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
								<c:when test="${actionField.hasDeploy=='1'}">
									<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');">
									<img src="${ctx}/common/style/blue/images/upload/02.png" title="部署成功"  style="width: 16px; height: 16px;border:0px;" />
									</a>
								</c:when>
							</c:choose>
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}','${actionField.operate}');">${actionField.label}</a>
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}','${actionField.operate}');">${actionField.fieldName}</a>
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}','${actionField.operate}');"> <c:choose>
										<c:when test="${actionField.fieldType == 'CharacterField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_character" />
										</c:when>
										<c:when test="${actionField.fieldType == 'ButtonField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_button" />
										</c:when>
										<c:when test="${actionField.fieldType == 'BlankField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_occupying" />
										</c:when>
										<c:when test="${actionField.fieldType == 'LabelField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_text" />
										</c:when>
										<c:when test="${actionField.fieldType == 'SelectField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_select" />
										</c:when>
										<c:when test="${actionField.fieldType == 'CollectField'}">
											选择框
										</c:when>
										<c:when test="${actionField.fieldType == 'ViewField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_view" />
										</c:when>
										<c:when test="${actionField.fieldType == 'AssignTreeField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_distributiontree" />
										</c:when>
										<c:when test="${actionField.fieldType == 'TimeField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_date" />
										</c:when>
										<c:when test="${actionField.fieldType == 'AttachmentField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_attachment" />
										</c:when>
										<c:when test="${actionField.fieldType == 'RollbackListField'}">
											<eoms:lable name="bpp_develop_worksheet_additional_rollback" />
										</c:when>
									</c:choose> </a>
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}','${actionField.operate}');">${actionField.baseSchema}</a>
						</td>
						<td>
							
								<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}','${actionField.operate}');"> <c:choose>
										<c:when test="${actionField.visiable == '1'}">
											<eoms:lable name="bpp_develop_worksheet_additional_yes" />
										</c:when>
										<c:otherwise>
											<eoms:lable name="bpp_develop_worksheet_additional_no" />
										</c:otherwise>
									</c:choose> </a>
							
						</td>
						<td>
							<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}','${actionField.operate}');">${actionField.orderNum}</a>
						</td>
						<td>
							<eoms:lable name="bpp_develop_worksheet_additional_action" />
							<!--动作-->
						</td>
						<td>
							<!--操作  -->
							<c:if test="${actionField.operate=='delete'}">
								<a href="javascript:;" onclick="rollBack('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');"> <eoms:lable name="bpp_develop_worksheet_additional_recovery" /> </a>
								<!--恢复-->
							</c:if>
							<c:if test="${actionField.operate!='delete'}">
								<a href="javascript:;" onclick="editField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');"> <eoms:lable name="bpp_develop_worksheet_additional_update" /> </a>
								<a href="javascript:;" onclick="deleteField('${actionField.baseSchema}','${actionField.id}','${actionField.fieldType}');"> <eoms:lable name="bpp_develop_worksheet_additional_delete" /> </a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<s:form name="fieldForm" id="fieldForm">
			<input type="hidden" name="baseSchema" id="baseSchema" value="${baseSchema}" />
			<input type="hidden" name="delFieldID" id="delFieldID" />
			<input type="hidden" name="delFieldType" id="delFieldType" />
			<input type="hidden" name="delvalue" id="delvalue" />
		</s:form>
	</body>
</html>
