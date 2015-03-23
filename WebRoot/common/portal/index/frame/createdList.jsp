<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="java.util.*,com.ultrapower.eoms.common.portal.web.WaitDealSheetQueryAction,com.ultrapower.eoms.common.core.component.data.DataTable,com.ultrapower.eoms.common.core.component.data.DataRow,com.ultrapower.eoms.common.plugin.datagrid.grid.Limit"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="eoms"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%-- <script type="text/javascript"
	src="${ctx}/ultrabpp/runtime/clientframework/util/jquery-1.7.2.js"></script> --%>
<body>
	<form id='form1' method='post' target="_self">
	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="list_table hasthbg">
        <tbody>
            <tr>
                <th style="width:10%">建单人 </th>
                <th>工单主题 </th>
                <!-- <th>处理组/人 </th> -->
                <th style="width:22%">建单时间 </th>
                <th style="width:10%">状态 </th>
                <th style="width:20%">流水号 </th>
                <!-- <th>是否超时</th> -->
           </tr>
        </tbody>
    </table>
    <div style="overflow-x:hidden;overflow-y:auto;width:100%;height:392px;">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" class="list_table hasthbg" id="tab">
               <tbody>
                 <%
						DataTable table = (DataTable) request.getAttribute("datatable");
						for (int i = 0; i < table.length(); i++) {
					%>
					<%
						DataRow row = table.getDataRow(i);
							String worksheettitle = (String) row.getRowHashMap().get(
									"BASESUMMARY");
							String basesn = (String) row.getRowHashMap().get("BASESN");
							String basestatus = (String) row.getRowHashMap().get(
									"BASESTATUSFORFREE");
							if(basestatus == null || "".equals(basestatus)){
								basestatus = (String) row.getRowHashMap().get(
									"BASESTATUS");
							}
							String createTime = TimeUtils.formatIntToDateString(row.getLong("basecreatedate"));
							String creatorname = (String) row.getRowHashMap().get(
									"BASECREATORFULLNAME");
							String baseid = (String) row.getRowHashMap().get("BASEID");
							String baseschema = (String) row.getRowHashMap().get(
									"BASESCHEMA");
					%>
					<tr style="cursor: pointer;"
						onclick="openSheet('<%=baseschema%>','<%=baseid%>');">
						<td style="width:10%"><%=creatorname%></td>
						<td title="<%=worksheettitle %>">
							<%=worksheettitle.length()>20?worksheettitle.substring(0,20):worksheettitle%>
						</td>
						<td style="width:22%"><%=createTime%></td>
						<td style="width:10%"><%=basestatus%></td>
						<td style="width:20%"><%=basesn%></td>
					</tr>
					<%
						}
					%>
               </tbody>
          </table>
		</div>
              <%
			Limit limit = (Limit) request.getAttribute("limit");
			int totalPage = limit.getTotalPage();
			int pageSize = limit.getPageSize();
			int currentPage = limit.getPage();
			int totalRow = limit.getTotalRows();
		%>
              <div class="table_bottom1 nobt">
               
               
                <div class="total_num">
                当前第
			<select class='paddingHack' id='var_currentpage'
				name='var_currentpage' onchange="return tranferPageCreate('go')">
				<%
					for (int i = 1; i < (totalPage + 1); i++) {
						if (currentPage == i) {
				%>

				<option selected value='<%=i%>'><%=i%></option>
				<%
					} else {
				%>
				<option value='<%=i%>'><%=i%></option>
				<%
					}
					}
				%>
			</select>
			页&nbsp;&nbsp;共<%=totalPage%>页&nbsp;&nbsp;每页
			<select class='paddingHack' id='var_pagesize' name='var_pagesize'
				onchange="return tranferPageCreate('setsize')">
				<option <%if (pageSize == 5) {%> selected <%}%> value='5'>
					5
				</option>
				<option <%if (pageSize == 10) {%> selected <%}%> value='10'>
					10
				</option>
				<option <%if (pageSize == 20) {%> selected <%}%> value='20'>
					20
				</option>
			</select>
			条&nbsp;
                </div>
               <div class="page">
                <span>
                <a href="javascript:;" onclick="return tranferPageCreate('frist')" target="_self">第一页&nbsp;</a>
			<a href="javascript:;" onclick="return tranferPageCreate('previous')"
				target="_self"> &nbsp; 上一页 </a>
			&nbsp;&nbsp;
			<a href="javascript:;" onclick="return tranferPageCreate('next')" target="_self">下一页 </a>
			<a href="javascript:;" onclick="return tranferPageCreate('last')"
				target="_self"> &nbsp;最后页&nbsp;</a>
                </span>
                </div>
              </div>

		<div>
			<input id='var_totalpages' name='var_totalpages' type='hidden'
				value='<%=totalPage%>' />
			<input id='var_selectvalues' name='var_selectvalues' type='hidden'
				value='' />
			<input id='var_istranfer' name='var_istranfer' type='hidden' value='' />
			<input id='var_sortfield' name='var_sortfield' type='hidden' value='' />
			<input id='var_sorttype' name='var_sorttype' type='hidden' value='0' />
			<input id='id' name='id' type='hidden' value='' />
		</div>
	</form>
	<form id="sheetform" action="${ctx}/sheet/openDealedSheet.action"
		target="_blank" style="display: none;">
		<input type="hidden" name="baseSchema" id="baseSchemas" />
		<input type="hidden" name="baseId" id="baseId" />
		<input type="hidden" name="pageSize" id="pageSize"
			value="<%=pageSize%>" />
	</form>
	<%
		String workSheetType = (String) request
				.getAttribute("workSheetType");
	%>
	<input name="workSheetType" type="hidden" id="workSheetType"
		value="<%=workSheetType%>" />
</body>

<script type="text/javascript" >
	//控制列表隔行换色
	//changeRow_color("tab");	
	changeRow_color('tab');
	/**
	 *打开工单
	 */
	 function openSheet(baseSchema,baseId){
	 	document.getElementById('baseSchemas').value = baseSchema ;
	 	document.getElementById('baseId').value = baseId ;
	 	document.getElementById('sheetform').submit();
	 }
	 
	 
	 
function tranferPageCreate(type) 
{	
	document.getElementById("var_istranfer").value = "1";
	var objCurpage = document.getElementById("var_currentpage");
	var pageCount = document.getElementById("var_totalpages").value;
	var workSheetType = document.getElementById("workSheetType").value;
	var pageSize = document.getElementById("var_pagesize").value;
	//alert('pageCount=' + pageCount);
	var pageNumber = objCurpage.value;
	if (pageNumber == "") {
		pageNumber = "1";
	}
	if (type == "frist") {
		if (pageNumber != "1") {
			//setSelectValue(objCurpage, 1);
			//document.forms[0].submit();
			jQuery("#sheet_info").load("${ctx}/myWaitDeal/deal_taskList.action?type=${type}&workSheetType="+workSheetType+"&var_pagesize="+pageSize+"&var_currentpage=1"+"&var_istranfer=1"+"&r="+Math.random());
		}
	} else {
		if (type == "previous") {
			var intpageNumber = parseInt(pageNumber);
			if (intpageNumber > 1) {
				intpageNumber--;
				//setSelectValue(objCurpage, intpageNumber);
				//document.forms[0].submit();
				jQuery("#sheet_info").load("${ctx}/myWaitDeal/deal_taskList.action?type=${type}&workSheetType="+workSheetType+"&var_pagesize="+pageSize+"&var_currentpage="+intpageNumber+"&var_istranfer=1"+"&r="+Math.random());
			}
		} else {
			if (type == "next") {
				var intpageNumber = parseInt(pageNumber);
				var intpageCount = parseInt(pageCount);
				if (intpageCount > intpageNumber) {
					intpageNumber++;
					//alert(intpageNumber);
					jQuery("#sheet_info").load("${ctx}/myWaitDeal/deal_taskList.action?type=${type}&workSheetType="+workSheetType+"&var_pagesize="+pageSize+"&var_currentpage="+intpageNumber+"&var_istranfer=1"+"&r="+Math.random());
				}
			} else {
				if (type == "last") {
					if (objCurpage.value != pageCount) {
						setSelectValue(objCurpage, pageCount);
						//document.forms[0].submit();
						//alert('pageCount='+pageCount);
						jQuery("#sheet_info").load("${ctx}/myWaitDeal/deal_taskList.action?type=${type}&workSheetType="+workSheetType+"&var_pagesize="+pageSize+"&var_currentpage="+pageCount+"&var_istranfer=1"+"&r="+Math.random());
					}
				} else {
					if (type == "go") {
						//document.forms[0].submit();
						jQuery("#sheet_info").load("${ctx}/myWaitDeal/deal_taskList.action?type=${type}&workSheetType="+workSheetType+"&var_pagesize="+pageSize+"&var_currentpage="+pageNumber+"&var_istranfer=1"+"&r="+Math.random());
					} else {
						if (type == "setsize") {
							//alert();
							jQuery("#sheet_info").load("${ctx}/myWaitDeal/deal_taskList.action?type=${type}&workSheetType="+workSheetType+"&var_pagesize="+pageSize+"&var_currentpage="+pageNumber+"&r="+Math.random());
							//document.forms[0].submit();
						}
					}
				}
			}
		}
	}
}

  </script>