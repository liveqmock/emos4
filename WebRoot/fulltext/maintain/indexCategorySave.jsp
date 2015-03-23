<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="ftr_title_ftrMaintain"/></title>
		<script language="javascript">
			window.onresize = function() 
			{
				setCenter(0,61);
			}
			window.onload = function() 
			{
				setCenter(0,61);
				getPageMenu('div1_1','div1');
				PageMenuActive('div1_1','div1');
				if("${indexCategory.isphysical}"=="0")
				{
					//alert("${childCategoryId}");
					var childStr = "${childCategoryId}";
					if(childStr!="")
					{
						childArr = childStr.split(",");
						var childCbx = document.getElementsByName("childcbx");
						if(childCbx!=null)
						{
							for(var i=0;i<childArr.length;i++)
							{
								for(var j=0;j<childCbx.length;j++)
								{
									if(childArr[i]==childCbx[j].value)
									{
										childCbx[j].checked = true;
										break;
									}
								}
							}
						}
					}
				}
			}
			function showChild(source)
			{
				if(source.value=="0")
				{
					document.getElementById("childSet").style.display = "block";
				}
				else
				{
					document.getElementById("childSet").style.display = "none";
				}
			}
			function delete_Row(sourceObj)
			{
				if(confirm('<eoms:lable name="ftr_msg_confirmDel"/>？'))
				{
					var cellObj = sourceObj.parentElement;
					var rowObj = cellObj.parentElement;
					var tbObj = rowObj.parentElement;
					tbObj.removeChild(rowObj);
				}
			}
			
			function addRow_path()
			{
				var refeSel = document.getElementsByName("path_cur_tag")[0];
				var pathTb = document.getElementById("pathTable");
				var rowNum = pathTb.rows.length - 1;
				var newRow = pathTb.insertRow(rowNum);
				var valueCell = newRow.insertCell(-1);
				valueCell.innerHTML = "<input type='text' value='' class='textInput'/>";
				var curCell = newRow.insertCell(-1);
				var selStr = "<select class='select'>";
				if(pathTb.rows.length==2)
				{
					for(var i=0;i<refeSel.options.length;i++)
					{
						selStr += "<option value='"+refeSel.options[i].value+"'>"+refeSel.options[i].text+"</option>";
					}
				}
				else
				{
					for(var i=refeSel.options.length-1;i>=0;i--)
					{
						selStr += "<option value='"+refeSel.options[i].value+"'>"+refeSel.options[i].text+"</option>";
					}
				}
				selStr += "</select>";
				curCell.innerHTML = selStr;
				var btnCell = newRow.insertCell(-1);
				btnCell.align = "right";
				var ipvalue = "<eoms:lable name='com_btn_delete'/>";
				btnCell.innerHTML = "<input type='button' value='"+ipvalue+"' class='operate_button'"
									+" onmouseover='this.className=\"operate_button_hover\"'"
									+" onmouseout='this.className=\"operate_button\"'"
									+" onclick='delete_Row(this);'/>";
			}
			function addRow_source()
			{
				var refeSel_type = document.getElementsByName("source_type_tag")[0];
				var refeSel_fileFrom = document.getElementsByName("file_from_tag")[0];
				var refCusSourceSel = document.getElementsByName("is_custom_source_tag")[0];
				var sourceTb = document.getElementById("sourceTable");
				var rowNum = sourceTb.rows.length - 1;
				var newRow = sourceTb.insertRow(rowNum);
				var typeCell = newRow.insertCell(-1);
				var selStr = "<select class='select'>";
				for(var i=0;i<refeSel_type.options.length;i++)
				{
					selStr += "<option value='"+refeSel_type.options[i].value+"'>"+refeSel_type.options[i].text+"</option>";
				}
				selStr += "</select>";
				typeCell.innerHTML = selStr;
				var valueCell = newRow.insertCell(-1);
				valueCell.innerHTML = "<input type='text' value='' class='textInput'/>";
				var isCustomSourceCell = newRow.insertCell(-1);
				selStr = "<select class='select'>";
				for(var i=0;i<refCusSourceSel.options.length;i++)
				{
					if(refCusSourceSel.options[i].value=="0")
						selStr += "<option value='"+refCusSourceSel.options[i].value+"' selected='selected'>"+refCusSourceSel.options[i].text+"</option>";
					else
						selStr += "<option value='"+refCusSourceSel.options[i].value+"'>"+refCusSourceSel.options[i].text+"</option>";
				}
				selStr += "</select>";
				isCustomSourceCell.innerHTML = selStr;
				var fileFromCell = newRow.insertCell(-1);
				selStr = "<select class='select'>";
				for(var i=0;i<refeSel_fileFrom.options.length;i++)
				{
					selStr += "<option value='"+refeSel_fileFrom.options[i].value+"'>"+refeSel_fileFrom.options[i].text+"</option>";
				}
				selStr += "</select>";
				fileFromCell.innerHTML = selStr;
				var btnCell = newRow.insertCell(-1);
				btnCell.align = "right";
				var btvalue = "<eoms:lable name='com_btn_delete'/>";
				btnCell.innerHTML = "<input type='button' value='"+btvalue+"' class='operate_button'"
									+" onmouseover='this.className=\"operate_button_hover\"'"
									+" onmouseout='this.className=\"operate_button\"'"
									+" onclick='delete_Row(this);'/>";
			}
			function hltSwitch(source)
			{
				if(source.value=="1")
				{
					var span = document.createElement("<span class='must' id='hltspan'></span>");
					span.innerHTML = "*";
					document.getElementById("hltspantd").appendChild(span);
					var vldtmsg = "<eoms:lable name='ftr_msg_hltPathConstraint'/>！";
					var validate = document.createElement("<validation id='indexCategory.hlttextpathV' dataType='Limit' min='1' max='200' msg='"+vldtmsg+"'/>");
					document.getElementById("hltvalidatetd").appendChild(validate);
				}
				else
				{
					var span = document.getElementById("hltspan");
					document.getElementById("hltspantd").removeChild(span);
					var validate = document.getElementById('indexCategory.hlttextpathV');
					document.getElementById("hltvalidatetd").removeChild(validate);
				}
			}
			function checkPath()
			{
				var pathStr = "";
				var currentCounter = 0;
				var pathTb = document.getElementById("pathTable");
				for(var i=1;i<pathTb.rows.length-1;i++)
				{
					var row = pathTb.rows[i];
					var value = row.cells[0].childNodes[0].value;
					value = value.replace(/(^\s*)|(\s*$)/g, "");
					if(value=="")
						continue;
					var isCurrent = row.cells[1].childNodes[0].value;
					pathStr += ";"+value+","+isCurrent;
					if(isCurrent=="1")
						currentCounter++;
				}
				if(currentCounter==0)
				{
					alert("<eoms:lable name='ftr_msg_noCurrentIndexPathNotice'/>！");
					return false;
				}
				if(currentCounter>1)
				{
					alert("<eoms:lable name='ftr_msg_moreCurrentIdxPathNOtice'/>！");
					return false;
				}
				pathStr = pathStr.substring(1);
				document.getElementById("_pathStr").value = pathStr;
				//alert(pathStr);
				return true;
			}
			function checkSource()
			{
				var sourceStr = "";
				var sourceCounter = 0;
				var sourceTb = document.getElementById("sourceTable");
				for(var i=1;i<sourceTb.rows.length-1;i++)
				{
					var row = sourceTb.rows[i];
					var sourceType = row.cells[0].childNodes[0].value;
					var sourceValue = row.cells[1].childNodes[0].value;
					sourceValue = sourceValue.replace(/(^\s*)|(\s*$)/g, "");
					if(sourceValue=="")
						continue;
					var isCustomSource = row.cells[2].childNodes[0].value;
					var fileFrom = row.cells[3].childNodes[0].value;
					if(sourceType=="2" && fileFrom=="")
					{
						alert("<eoms:lable name='ftr_msg_noFileFromNotice'/>！");
						return false;
					}
					sourceStr += ";"+sourceType+","+sourceValue+","+isCustomSource+","+fileFrom;
					sourceCounter++;
				}
				if(sourceCounter==0)
				{
					alert("<eoms:lable name='ftr_msg_noDataSourceNotice'/>！");
					return false;
				}
				sourceStr = sourceStr.substring(1);
				document.getElementById("_sourceStr").value = sourceStr;
				//alert(sourceStr);
				return true;
			}
			function checkChildren()
			{
				var childCbx = document.getElementsByName("childcbx");
				if(childCbx==null)
				{
					alert("<eoms:lable name='ftr_msg_noSelectiveCateg'/>！");
					return false;
				}
				var childStr = "";
				var childcounter = 0;
				for(var i=0;i<childCbx.length;i++)
				{
					if(childCbx[i].checked)
					{
						childStr += "," + childCbx[i].value;
						childcounter++;
					}
				}
				if(childcounter<=1)
				{
					alert("<eoms:lable name='ftr_msg_mustMoreThan2Categ'/>！");
					return false;
				}
				childStr = childStr.substring(1);
				document.getElementsByName("childCategoryId")[0].value = childStr;
				//alert(childStr);
				return true;
			}
			function submitForm()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					if(document.getElementsByName("indexCategory.isphysical")[0].value=="1")
					{
						if(checkPath())
						{
							if(checkSource())
							{
								document.forms[0].submit();
							}
						}
					}
					else
					{
						if(checkChildren())
						{
							document.forms[0].submit();
						}
					}
				}
			}
		</script>
	</head>

	<body>
	  <div style="display:none"><!-- 这三个SELECT主要用来获取字典的值，动态生成行的时候，生成select的时候用到 -->
		<eoms:select name="path_cur_tag" style="select" dataDicTypeCode="isdefault" isnull="false"/>
		<eoms:select name="source_type_tag" style="select" dataDicTypeCode="FTRSourceType" isnull="false"/>
		<eoms:select name="file_from_tag" style="select" dataDicTypeCode="FTRFileFrom"/>
		<eoms:select name="is_custom_source_tag" style="select" dataDicTypeCode="isdefault" isnull="false"/>
	  </div>
	  <form action="${ctx}/indexCategoryManager/saveCategoryInfo.action" method="post">
		<input type="hidden" name="_sourceStr" id="_sourceStr"/>
		<input type="hidden" name="_pathStr" id="_pathStr"/>
		<input type="hidden" name="indexCategory.pid" value="${indexCategory.pid }"/>
		<input type="hidden" name="indexCategory.parentid" value="${indexCategory.parentid }"/>
		<input type="hidden" name="indexCategory.creater" value="${indexCategory.creater }"/>
		<input type="hidden" name="indexCategory.createtime" value="${indexCategory.createtime }"/>
		<input type="hidden" name="childCategoryId" value="${childCategoryId}"/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="ftr_lb_addOrUpdateCategory"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>
			<div class="add_scroll_div_x" id="center" style="overflow-x:hidden;">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="ftr_lb_addOrUpdateCategory"/></legend>
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div class="tab_show" id="div1_1"
							onclick="PageMenuActive('div1_1','div1')">
							<span><eoms:lable name="com_lb_basicInfo"/></span>
						</div>
						<div class="tab_hide" id="div1_2"
							onclick="PageMenuActive('div1_2','div2')">
							<span><eoms:lable name="ftr_lb_indexCategoryPath"/></span>
						</div>
						<div class="tab_hide" id="div1_3"
							onclick="PageMenuActive('div1_3','div3')">
							<span><eoms:lable name="ftr_lb_indexCategorySource"/></span>
						</div>
					</div>
					<div class="tabContent">
						<div id="div1">
							<table class="add_user">
								<tr>
									<td style="width:15%">
										<eoms:lable name="ftr_lb_displayName"/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<input type="text" name="indexCategory.displayname" class="textInput" value="${indexCategory.displayname }"/>
										<validation id="indexCategory.displaynameV" dataType="Limit" min="1" max="30" msg="<eoms:lable name='ftr_msg_ctgDisplaynameConst'/>！" />
									</td>
									<td style="width:15%">
										<eoms:lable name="ftr_lb_isPhysicalCategory"/>：<span class="must">*</span>
									</td>
									<td style="width:35%">
										<c:choose>
											<c:when test="${indexCategory.isphysical==1}">
												<input type="hidden" name="indexCategory.isphysical" value="${indexCategory.isphysical}"/>
												<input type="text" class="textInput" value="<eoms:lable name='ftr_lb_yes'/>" disabled="disabled"/>
											</c:when>
											<c:when test="${indexCategory.isphysical!=1 && indexCategory.pid!=null}">
												<input type="hidden" name="indexCategory.isphysical" value="${indexCategory.isphysical}"/>
												<input type="text" class="textInput" value="<eoms:lable name='ftr_lb_no'/>" disabled="disabled"/>
											</c:when>
											<c:otherwise>
												<eoms:select name="indexCategory.isphysical" style="select" dataDicTypeCode="isdefault" 
													value="${indexCategory.isphysical}" isnull="false" onChangeFun="showChild(this);"/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td>
										<eoms:lable name="ftr_lb_orderNumber"/>：<span class="must">*</span>
									</td>
									<td>
										<input type="text" name="indexCategory.ordernum" class="textInput" value="${indexCategory.ordernum }"/>
										<validation id="indexCategory.ordernumV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='sm_msg_orderNumConstraint'/>！" />
									</td>
									<td>
										<eoms:lable name="ftr_lb_parentCategory"/>：
									</td>
									<td>
										<input type="text" readonly="readonly" class="textInput" value="${parentCategoryName}"/>
									</td>
								</tr>
								<tr>
									<td>
										<eoms:lable name="ftr_lb_isAddHLTText"/>：
									</td>
									<td>
										<c:if test="${indexCategory.isaddhlttext==null}">
											<eoms:select name="indexCategory.isaddhlttext" style="select" onChangeFun="hltSwitch(this);" value="0" dataDicTypeCode="isdefault" isnull="false"/>
										</c:if>
										<c:if test="${indexCategory.isaddhlttext!=null}">
											<eoms:select name="indexCategory.isaddhlttext" style="select" onChangeFun="hltSwitch(this);" value="${indexCategory.isaddhlttext }" dataDicTypeCode="isdefault" isnull="false"/>
										</c:if>
									</td>
									<td id="hltspantd">
										<c:if test="${indexCategory.isaddhlttext!=null && indexCategory.isaddhlttext!=0}">
											<eoms:lable name="ftr_lb_HLTTextPath"/>：<span class="must" id="hltspan">*</span>
										</c:if>
										<c:if test="${indexCategory.isaddhlttext==0 || indexCategory.isaddhlttext==null}">
											<eoms:lable name="ftr_lb_HLTTextPath"/>：
										</c:if>
									</td>
									<td id="hltvalidatetd">
										<input type="text" name="indexCategory.hlttextpath" class="textInput" value="${indexCategory.hlttextpath}"/>
										<c:if test="${indexCategory.isaddhlttext!=0 && indexCategory.isaddhlttext!=null}">
											<validation id="indexCategory.hlttextpathV" dataType="Limit" min="1" max="200" msg="<eoms:lable name='ftr_msg_hltPathConstraint'/>！" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td>
										文件实体实现类：
									</td>
									<td colspan="3">
										<input type="text" name="indexCategory.filesourcecreater" style="width:98.5%" value="${indexCategory.filesourcecreater }"/>
									</td>
								</tr>
								<tr>
									<td>
										附件信息实现类：
									</td>
									<td colspan="3">
										<input type="text" name="indexCategory.attachinfoclass"  style="width:98.5%" value="${indexCategory.attachinfoclass}"/>
									</td>
								</tr>
								<tr>
									<td>
										附件内容实现类：
									</td>
									<td colspan="3">
										<input type="text" name="indexCategory.relationattclass" style="width:98.5%" value="${indexCategory.relationattclass }"/>
									</td>
								</tr>
								<tr>
									<td class="textted" style="color:red" colspan="4">
										&nbsp;&nbsp;&nbsp;*
										"存储高亮文本"针对像字符文件这样的文本，是否将源文件的内容存储在索引字段以外的其他目录下,这些内容用做高亮摘要.<br/>
										&nbsp;&nbsp;&nbsp;*
										"高亮文本路径"表示高亮文本的存储路径,如果该索引类别为文件类型的数据源且选择了存储
										高亮文本或者该索引类别的关联附件实现类不为空,则必须填写该项，否则无法实现高亮.<br/>
										&nbsp;&nbsp;&nbsp;*
										"文件实体实现类"表示从文件数据源取文件的时候,将一些特定参数传递给该实现类并返回真实文件实体,该实现类必须实现
										com.ultrapower.eoms.fulltext.common.util.interfaces.ICustomFileSourceCreater接口.<br/>
										&nbsp;&nbsp;&nbsp;*
										"附件信息实现类"表示系统在构建该类索引的时候会通过这个类取得关联附信息,该类必须继承
										com.ultrapower.eoms.fulltext.common.util.interfaces.IDocumentAttachment接口.<br/>
										&nbsp;&nbsp;&nbsp;*
										"附件内容实现类"表示系统在构建该类索引的时候会通过这个类取得关联附内容,该类必须继承
										com.ultrapower.eoms.fulltext.common.util.interfaces.IDocumentAttachContent接口.<br/>
									</td>
								</tr>
							</table>
						</div>
						<div id="div2" style="display: none">
							<input type="hidden" id="path_counter" value="${fn:length(indexPath)}"/>
							<div class="blank_tr"></div>
							<table class="tableborder" id="pathTable">
								<tr>
									<th><eoms:lable name="ftr_lb_pathInfo"/></th><th width="20%"><eoms:lable name="ftr_lb_isCurrentPath"/></th><th width="60"><eoms:lable name="ftr_lb_deletePath"/></th>
								</tr>
								<c:if test="${indexPath!=null}">
									<c:forEach items="${indexPath}" var="path" varStatus="sta">
										<tr>
											<td><input type="text" name="path_value_${sta.index}" value="${path.indexpath}" class="textInput"/></td>
											<td><eoms:select name="path_cur_${sta.index}" style="select" value="${path.iscurrentpath }" dataDicTypeCode="isdefault" isnull="false"/></td>
											<td align="right">
												<input type="button" value="<eoms:lable name="com_btn_delete"/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'"
													onclick="delete_Row(this);"/>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<tr>
									<td colspan="2">
										<span class="must">*<eoms:lable name="ftr_lb_indexPathNotice"/>。</span>
									</td>
									<td align="right">
										<input type="button" value="<eoms:lable name="ftr_lb_add"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"
												onclick="addRow_path();"/>
									</td>
								</tr>
							</table>
						</div>
						<div id="div3" style="display: none">
							<input type="hidden" id="source_counter" value="${fn:length(indexSource)}"/>
							<div class="blank_tr"></div>
							<table class="tableborder" id="sourceTable">
								<tr>
									<th width="120"><eoms:lable name="ftr_lb_sourceType"/></th><th><eoms:lable name="ftr_lb_sourceInfo"/></th>
									<th width="120">是否自定义数据源</th>
									<th width="120"><eoms:lable name="ftr_lb_fileFrom"/></th><th width="70"><eoms:lable name="ftr_lb_deleteSource"/></th>
								</tr>
								<c:if test="${indexSource!=null}">
									<c:forEach items="${indexSource}" var="source" varStatus="sta">
										<tr>
											<td><eoms:select name="source_type_${sta.index}" style="select" value="${source.type}" dataDicTypeCode="FTRSourceType"/></td>
											<td><input type="text" name="source_value_${sta.index}" value="${source.value}" class="textInput"/></td>
											<td>
												<eoms:select name="is_custom_source_${sta.index}" style="select" dataDicTypeCode="isdefault" value="${source.isCustomSource }"/>
											</td>
											<td>
												<c:if test="${source.type==1}">
													<eoms:select name="file_from_${sta.index}" style="select" dataDicTypeCode="FTRFileFrom"/>
												</c:if>
												<c:if test="${source.type==2}">
													<eoms:select name="file_from_${sta.index}" style="select" value="${source.fileFrom}" dataDicTypeCode="FTRFileFrom"/>
												</c:if>
											</td>
											
											<td align="right">
												<input type="button" value="<eoms:lable name="com_btn_delete"/>"
													class="operate_button"
													onmouseover="this.className='operate_button_hover'"
													onmouseout="this.className='operate_button'"
													onclick="delete_Row(this);"/>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<tr>
									<td colspan="4">
										<span class="must">*<eoms:lable name="ftr_lb_sourceInfoNotice"/>。</span>
									</td>
									<td align="right">
										<input type="button" value="<eoms:lable name="ftr_lb_add"/>"
												class="operate_button"
												onmouseover="this.className='operate_button_hover'"
												onmouseout="this.className='operate_button'"
												onclick="addRow_source();"/>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</fieldset>
				<div class="blank_tr"></div>
				<c:if test="${indexCategory.isphysical!=1}">
					<c:if test="${indexCategory.isphysical==0}">
						<fieldset class="fieldset_style" id="childSet" style="display:block">
					</c:if>
					<c:if test="${indexCategory.isphysical!=0}">
						<fieldset class="fieldset_style" id="childSet" style="display:none">
					</c:if>
						<legend>
							<eoms:lable name="ftr_lb_selectiveChildCtg"/>
						</legend>
						<table width="100%">
							<tr><td width="25%"></td><td width="25%"></td><td width="25%"></td><td width="25%"></td></tr>
							<c:if test="${selectiveChildren==null || fn:length(selectiveChildren)==1}">
								<tr><td><eoms:lable name="ftr_msg_noSelectiveCateg"/>！</td></tr>
							</c:if>
							<c:if test="${selectiveChildren!=null && fn:length(selectiveChildren)>1}">
								<c:set var="childsize" value="${fn:length(selectiveChildren)}" scope="page"/>
								<c:forEach items="${selectiveChildren}" var="child" varStatus="sta">
									<c:if test="${sta.count==1 || sta.count%4==1}">
										<tr>
									</c:if>
									<td>
										<input type="checkbox" name="childcbx" value="${child.key }"/>${child.value }
									</td>
									<c:if test="${sta.count%4==0 || sta.count==childsize}">
										<c:forEach var="j" begin="1" end="${4-childsize%4}">
											<td></td>
										</c:forEach>
										</tr>
									</c:if>
								</c:forEach>
							</c:if> 
						</table>
						<div class="blank_tr"></div>
					</fieldset>
					<div class="blank_tr"></div>
				</c:if>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" value="<eoms:lable name="com_btn_save"/>" 
				class="save_button" onmouseover="this.className='save_button_hover'"
				onmouseout="this.className='save_button'" onclick="submitForm();" />
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
