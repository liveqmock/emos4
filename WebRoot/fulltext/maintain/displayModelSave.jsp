<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name='ftr_title_ftrMaintain'/></title>
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script> 
		<script type="text/javascript">
			window.onresize = function() {
				setCenter(0,60);
			}
			window.onload = function() 
			{
				setCenter(0,60);
				getPageMenu('div1_1','div1');
				PageMenuActive('div1_1','div1');
				constructFld("${displayModel.indextypeid}");
			}
			function resetFldTb()
			{
				var fldtb = document.getElementById("fldtb");
				var len = fldtb.rows.length;
				for(var i=len-1;i>0;i--)
				{
					fldtb.deleteRow(i);
				}
			}
			function constructFld(indexid)
			{
				$.getJSON("${ctx}/displayModelManager/getFieldSpanInfo.action",{indexid:indexid},function(data)
				{
					resetFldTb();
					var fldtb = document.getElementById("fldtb");
					var len = data.length;
					var fldid;
					var fldname;
					var flddisplayzone;
					var fldcols;
					var fldordernum;
					var fldposition;
					var selStr;
					for(var i=0;i<len;i++)
					{
						fldid = data[i].fldId;
						fldname = data[i].fldName;
						flddisplayzone = data[i].displayZone;
						fldcols = data[i].cols;
						fldordernum = data[i].ordernum;
						fldposition = data[i].fldposition;
						var row = fldtb.insertRow(-1);
						var col1 = row.insertCell(-1);
						var col2 = row.insertCell(-1);
						var col3 = row.insertCell(-1);
						var col4 = row.insertCell(-1);
						row.id = fldid+"&comm;"+fldposition;
						col1.innerHTML = fldname;
						col2.align = "center";
						selStr = "<select class=\"select\"><option value=\"\"></option>";
						if(flddisplayzone==1)
							selStr += "<option value=\"1\" selected=\"selected\"><eoms:lable name='ftr_lb_contentZone'/></option>";
						else
							selStr += "<option value=\"1\"><eoms:lable name='ftr_lb_contentZone'/></option>";
						if(flddisplayzone==2)
							selStr += "<option value=\"2\" selected=\"selected\"><eoms:lable name='ftr_lb_mustDisplayZone'/></option>";
						else
							selStr += "<option value=\"2\"><eoms:lable name='ftr_lb_mustDisplayZone'/></option>";
						selStr += "</select>";
						$(selStr).appendTo(col2);
						selStr = "<select class=\"select\">";
						for(var j=1;j<=20;j++)
						{
							if(j==parseInt(fldcols))
								selStr += "<option value=\""+j+"\" selected=\"selected\">"+j+"</option>";
							else
								selStr += "<option value=\""+j+"\">"+j+"</option>";
						}
						selStr += "</select>";
						$(selStr).appendTo(col3);
						$("<input type=\"text\" value=\""+fldordernum+"\" class=\"textInput\" style=\"width:90%;\"/>").appendTo(col4);
					}
					changeRow_color("fldtb");
				});
			}
			function getSortedFldtb()
			{
				var fldArr = new Array();
				var orderArr = new Array();
				var fldtb = document.getElementById("fldtb");
				var len = fldtb.rows.length;
				var row;
				for(var i=1;i<len;i++)
				{
					row = fldtb.rows[i];
					if(row.cells[1].childNodes[0].value=="")
						continue;
					fldArr.push(i);
					if(isNaN(row.cells[3].childNodes[0].value))
						orderArr.push(1);
					else
						orderArr.push(parseInt(row.cells[3].childNodes[0].value));
				}
				//开始排序
				var temp1;
				var temp2;
				var tag;
				for(var j=0;j<orderArr.length-1;j++)
				{
					tag = 0;
					for(var h=0;h<orderArr.length-1-j;h++)
					{
						if(orderArr[h]>orderArr[h+1])
						{
							temp1 = orderArr[h];
							orderArr[h] = orderArr[h+1];
							orderArr[h+1] = temp1;
							temp2 = fldArr[h];
							fldArr[h] = fldArr[h+1];
							fldArr[h+1] = temp2;
							tag++;
						}
					}
					if(tag==0)
						break;
				}
				return fldArr;
			}
			function accDiv(arg1,arg2){ 
				var t1=0,t2=0,r1,r2; 
				try{t1=arg1.toString().split(".")[1].length}catch(e){} 
				try{t2=arg2.toString().split(".")[1].length}catch(e){} 
				with(Math){ 
					r1=Number(arg1.toString().replace(".","")) 
					r2=Number(arg2.toString().replace(".","")) 
					return (r1/r2)*pow(10,t2-t1); 
				} 
			} 
			function accMul(arg1,arg2) 
			{ 
				var m=0,s1=arg1.toString(),s2=arg2.toString(); 
				try{m+=s1.split(".")[1].length}catch(e){} 
				try{m+=s2.split(".")[1].length}catch(e){} 
				return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m) 
			} 

			function preview()
			{
				var view_ltd = document.getElementById("view_ltd");
				var view_rtd = document.getElementById("view_rtd");
				var viewbtn = document.getElementById("viewbtn");
				if(view_ltd.style.display=="none")
				{
					view_ltd.style.display = "block";
					view_rtd.style.display = "none";
					viewbtn.value = '<eoms:lable name="ftr_lb_preview"/>';
					return;
				}
				var colcount = document.getElementById("displayModel.columns").value;
				colcount = colcount.replace(/(^\s*)|(\s*$)/g, "");
				if(isNaN(colcount) || colcount==0)
				{
					alert('<eoms:lable name="ftr_msg_modelDisplayClosIllegal"/>！');
					return;
				}
				var viewlen = parseInt(colcount);
				var avgpercent = parseInt(accDiv(accDiv(100,viewlen),3));
				var viewtb = document.getElementById("previewtb");
				var viewtblen = viewtb.rows.length;
				for(var i=viewtblen-1;i>=0;i--)
				{
					viewtb.deleteRow(i);
				}
				var stylerow = viewtb.insertRow(-1);
				for(var i=0;i<viewlen;i++)
				{
					var conCell = stylerow.insertCell(-1);
					conCell.width = avgpercent*3 +"%";
				}
				var fldtb = document.getElementById("fldtb");
				var fldArr = getSortedFldtb();
				var fldlen = fldArr.length;
				var counter = 0;
				var rownum;
				var mustStr = "";
				var conStr;
				var flag = true;//预览表格还没有插入第二行
				for(var i=0;i<fldlen;i++)
				{
					rownum = fldArr[i];
					var row = fldtb.rows[rownum];
					var fldname = row.cells[0].innerHTML;
					if(row.cells[1].childNodes[0].value=="2")
					{
						mustStr += "&nbsp;|&nbsp;<font color=\"black\">";
						mustStr += fldname;
						mustStr += "：</font><font color=\"#004B97\">";
						mustStr += '<eoms:lable name="ftr_lb_fieldContent"/>';
						mustStr += "</font>";
					}
					else if(row.cells[1].childNodes[0].value=="1")
					{
						var cols = parseInt(row.cells[2].childNodes[0].value);
						if(cols>viewlen)
							cols = viewlen;
						var newrow;
						if(flag || counter+cols>viewlen)
						{
							newrow = viewtb.insertRow(-1);
							counter = 0;
							flag = false;
						}
						else
							newrow = viewtb.rows[viewtb.rows.length-1];
						var conCell = newrow.insertCell(-1);
						conCell.colSpan = cols;
						conCell.className = "preview_content";
						conStr ="";
						conStr += "<font color=\"black\">";
						conStr += fldname;
						conStr += "：</font>";
						conStr += "<font color=\"#004B97\">";
						conStr += '<eoms:lable name="ftr_lb_fieldContent"/>';
						conStr += "</font>";
						conCell.innerHTML = conStr;
						counter += cols;
					}
				}
				if(mustStr.length>0)
					mustStr = mustStr.substring("&nbsp;|&nbsp;".length);
				document.getElementById("mustdiv").innerHTML = mustStr;
				view_ltd.style.display = "none";
				view_rtd.style.display = "block";
				viewbtn.value = '<eoms:lable name="ftr_lb_returnBack"/>';
			}
			
			function getSpanInfo()
			{
				var fldtb = document.getElementById("fldtb");
				var fldArr = getSortedFldtb();
				var fldlen = fldArr.length;
				var info = "";
				var fldid;
				var displayzone;
				var cols;
				var ordernum;
				var fldposition;
				var row;
				var idposi;
				for(var i=0;i<fldlen;i++)
				{
					row = fldtb.rows[fldArr[i]];
					idposi = row.id;
					fldid = idposi.split("&comm;")[0];
					fldposition = idposi.split("&comm;")[1];
					displayzone = row.cells[1].childNodes[0].value;
					cols = row.cells[2].childNodes[0].value;
					if(isNaN(row.cells[3].childNodes[0].value))
						ordernum = 1;
					else
						ordernum = row.cells[3].childNodes[0].value;
					info += ";"+fldid+","+displayzone+","+cols+","+ordernum+","+fldposition;
				}
				if(fldlen>0)
					info = info.substring(1);
				return info;
			}
			function formSubmit()
			{
				if(Validator.Validate(document.forms[0],2))
				{
					var spaninfo = getSpanInfo();
					document.getElementById("fldSpanInfo").value = spaninfo;
					document.forms[0].submit();
				}
			}
		</script>
	</head>

	<body>
	  <form action="${ctx}/displayModelManager/saveModel.action" method="post">
	  	<input type="hidden" name="displayModel.pid" value="${displayModel.pid}"/>
		<input type="hidden" name="fldSpanInfo" id="fldSpanInfo" value=""/>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2"><eoms:lable name="ftr_lb_addUpdateModel"/></span>
					</span>
					<span class="title_xieline"></span>
				</div>
			</div>

			<div class="add_scroll_div_x" id="center">
				<fieldset class="fieldset_style">
					<legend><eoms:lable name="ftr_lb_addUpdateModel"/></legend>
					<div class="blank_tr"></div>
					<div class="tab_bg">
						<div class="tab_show" id="div1_1"
							onclick="PageMenuActive('div1_1','div1')">
							<span><eoms:lable name="com_lb_basicInfo"/></span>
						</div>
						<div class="tab_hide" id="div1_2"
							onclick="PageMenuActive('div1_2','div2')">
							<span><eoms:lable name="ftr_lb_fieldColsDefine"/></span>
						</div>
					</div>
					<div class="tabContent">
						<div id="div1">
							<table class="add_user">
								<tr>
									<td class="texttd" style="width:15%"><eoms:lable name="ftr_lb_modelName"/>：</td>
									<td style="width:35%">
										<input type="text" name="displayModel.modelname" value="${displayModel.modelname}" class="textInput"/>
										<validation id="displayModel.modelnameV" dataType="Limit" max="50" msg="<eoms:lable name='ftr_msg_modelNameConst'/>！" />
									</td>
									<td class="texttd" style="width:15%"><eoms:lable name="ftr_lb_indexCategory"/>：<span class="must">*</span></td>
									<td style="width:35%">
										<c:if test="${displayModel.pid==null}">
											<select class="select" name="displayModel.indextypeid" onchange="constructFld(this.value);">
												<option value=""></option>
												<c:forEach items="${indexCategory}" var="ca">
													<c:choose>
														<c:when test="${displayModel.indextypeid!=ca.key}">
															<option value="${ca.key}">${ca.value}</option>
														</c:when>
													</c:choose>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${displayModel.pid!=null}">
											<select class="select" name="displayModel.indextypeid">
												<c:forEach items="${indexCategory}" var="ca">
													<c:if test="${displayModel.indextypeid==ca.key}">
														<option value="${ca.key}" selected="selected">${ca.value}</option>
													</c:if>
												</c:forEach>
											</select>
										</c:if>
										<validation id="displayModel.indextypeidV" dataType="Require" msg="<eoms:lable name='ftr_msg_selectIdxCategoryConst'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd"><eoms:lable name="ftr_lb_displayColumns"/>：</td>
									<td>
										<input type="text" name="displayModel.columns" id="displayModel.columns" value="${displayModel.columns}" class="textInput"/>
										<validation id="displayModel.columnsV" dataType="Custom" regexp="^[0-9]{1,5}$" msg="<eoms:lable name='ftr_msg_modelColumnsConst'/>！" />	
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top"><eoms:lable name="ftr_lb_modelHref"/>：</td>
									<td colspan="3">
										<textarea name="displayModel.docurl" rows="2" class="textInput" style="width:98.5%">${displayModel.docurl}</textarea>
										<validation id="displayModel.docurlV" dataType="Limit" max="200" msg="<eoms:lable name='ftr_msg_modelHyperLinkConst'/>！" />
									</td>
								</tr>
								<tr>
									<td class="texttd" valign="top"><eoms:lable name="ftr_lb_formAttachLink"/>：</td>
									<td colspan="3">
										<textarea name="displayModel.attachlink" rows="2" class="textInput" style="width:98.5%">${displayModel.attachlink}</textarea>
										<validation id="displayModel.attachlinkV" dataType="Limit" max="200" msg="<eoms:lable name='ftr_msg_formAttachLinkConst'/>！" />
									</td>
								</tr>
								<tr>
									<td colspan="4">
										<span class="must" style="font-weight:normal;"><eoms:lable name="ftr_lb_linkEncodeNotice"/></span><br/>
										<span class="must" style="font-weight:normal;"><eoms:lable name="ftr_lb_formAttachNotice"/></span><br/>
										<!-- 
										<span class="must" style="font-weight:normal;"><eoms:lable name="ftr_lb_sqlParamNotice"/></span>
										-->
									</td>
								</tr>
							</table>
						</div>
						<div id="div2" style="display:none;">
							<table style="width:100%;height:300px;">
								<tr style="height:100%">
									<td id="view_ltd" style="width:100%;display:block;">
										<fieldset style="border:1px #d2e5fe solid;padding:2px;height:100%;">
											<legend><font style="font-weight:bold"><eoms:lable name='ftr_lb_fieldChooseAvailable'/></font></legend>
											<div style="height:94%;overflow-y:scroll;overflow-x:hidden;">
												<table class="tableborder" style="width:95%" id="fldtb">
													<tr><th><eoms:lable name="ftr_lb_fieldDisplayName"/></th><th width="20%"><eoms:lable name="ftr_lb_displayZone"/></th>
														<th width="20%"><eoms:lable name="ftr_lb_colSpan"/></th><th width="20%"><eoms:lable name="ftr_lb_orderNumber"/></th></tr>
												</table>
											</div>
										</fieldset>
									</td>
									<td id="view_rtd" style="width:100%;display:none">
										<fieldset style="border:1px #d2e5fe solid;padding:2px;height:100%">
											<legend><font style="font-weight:bold"><eoms:lable name='ftr_lb_preview'/></font></legend>
											<div style="height:94%;overflow-y:scroll;overflow-x:hidden;">
												<table id="previewtb" width="98%"></table>
												<div class="preview_mustdiv" id="mustdiv" style="width:96%"></div>
											</div>
										</fieldset>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="add_bottom">
			<input type="button" id="viewbtn" value="<eoms:lable name="ftr_lb_preview"/>"
					class="operate_button" onmouseover="this.className='operate_button_hover'"
					onmouseout="this.className='operate_button'" onclick="preview();" />
			<input type="button" value="<eoms:lable name="com_btn_save"/>" class="save_button"
				onmouseover="this.className='save_button_hover'" 
				onmouseout="this.className='save_button'" onclick="formSubmit();" id="submitButton"/>
			<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
				class="cancel_button"
				onmouseover="this.className='cancel_button_hover'"
				onmouseout="this.className='cancel_button'"
				onclick="window.close();" />
		</div>
	  </form>
	</body>
</html>
