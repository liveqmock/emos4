<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head> 
	<%@ include file="/common/core/taglibs.jsp"%>  
	<%@ include file="/common/plugin/jquery/jquery.jsp"%>
	<script type="text/javascript" src="${ctx}/ultraduty/js/dutyCalendar.js"></script>
	<script language="javascript">
		window.onresize = function() {
			setCenter(0,30);
		}
		window.onload = function() {
			setCenter(0,30);
			setTextareaFormat();
		}
			function setTextareaFormat(){
				var lastproblem = document.getElementById('lastproblem');
				lastproblem.style.posHeight=lastproblem.scrollHeight;
		}
	</script>
	<script>
		function openCalendarLog(calendarId){
			window.open('${ctx}/dutyLogMessage/openCalendarLog.action?calendarId='+calendarId+'&orgid=${orgid}','form','width=900,height=800,left=300,top=200,scrollbars=yes,resizable=yes');
		}
		
		function approveFailed(calId){
		 $.ajax({
			url: '${ctx}/ultraduty/portal/workerAuditFailed.action',
			type: 'POST',
			dataType: 'text',
			data:'pids='+calId,
			success: function(returnMessage){
				if(returnMessage=='审核完毕'){
				   alert('操作成功');
				   document.URL=location.href;
				}else{
				   alert(returnMessage);
				}
			  }
			});
		}
	 </script>
	</head>
	<body>
		<dg:datagrid var="statistics" sqlname="SQL_DUTY_ALL_HISTORY_QUERY.query" action="" title="${nodePath}">
			<dg:lefttoolbar>
			<!-- 
				<li class="page_search_button" onmouseover="this.className='page_search_button_hover'" onmouseout="this.className='page_search_button'" onclick="showsearch()"><eoms:lable name='com_btn_search'/></li>
				<li class="page_refresh_button" onmouseover="this.className='page_refresh_button_hover'" onmouseout="this.className='page_refresh_button'" onclick="location.reload();"><eoms:lable name='com_btn_refresh'/></li>
			 -->
			</dg:lefttoolbar>
			<dg:condition>
			<!-- 
					<table class="add_table">
					<input type="hidden" name="orgid" id="orgid" value="${orgid}"/> 
						<tr>
							<td width="80px;">
								值班人员：
							</td>
							<td>
									<input type="text" name="username" value=""/>
							</td>
							<td width="80px;">
								值班时间：
							</td>
							<td>
								<span class="desc">
									<input type="text" name="dutydate" id="dutydate"
										value="${date}" class="textInput" style="width: 200px"
										readonly="readonly"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'${dataVal}',autoPickDate:true})" />
								</span>
							</td>
						</tr>				
						<tr>
							<td colspan="2" align="center">
					          	<input type="submit" name="searchBt" id="searchBt" value="<eoms:lable name="com_btn_search"/>" class="searchButton" onmouseover="this.className='searchButton_hover'" onmouseout="this.className='searchButton'"/>
					        	<input type="reset" name="resetCondition" id="resetCondition" value="<eoms:lable name="com_btn_reset"/>" class="ResetButton" onmouseover="this.className='ResetButton_hover'" onmouseout="this.className='ResetButton'"/>
				          	</td>
				          </tr>
						</table>
						 -->
				</dg:condition>
				<dg:gridtitle>
					<tr>
						<th>岗位</th>
						<th>班次</th>
						<th>排班开始时间</th>
						<th>排班结束时间</th>
						<th>值班开始时间</th>
						<th>值班结束时间</th>
						<th>值班人</th>
						<th>交接班状态</th>
						<th>审核操作</th>
						<!-- 
						 -->
					</tr>
				</dg:gridtitle>
				<dg:gridrow>
				<tr>
					<td>
						${organizationname }
					</td>
					<td>
						${shiftname }
					</td>
					<td>
						${planondutytime }
					</td>
					<td>
						${planoffdutytime }
					</td>
					<td>
						${ondutytime }
					</td>
					<td>
						${offdutytime }
					</td>
					<td>
						${userid}
					</td>
					<td>
						<c:choose>
							<c:when test="${state== 6}">未交班</c:when>
							<c:when test="${state== 7}">已交班<font color="red">(下一班已接班)</font></c:when>
							<c:when test="${state== 8}">已交班<font color="red">(下一班未接班)</font></c:when>
							<c:when test="${state== 9}">提出交班申请</c:when>
							<c:otherwise>未接班</c:otherwise>
						</c:choose>
					</td>
					<td>
					<c:choose>
							<c:when test="${state== 9}">
							<input style="width: 90px" type="button"  class="cancel_button" value="审核不通过" onclick="approveFailed('${pid}');" />
							</c:when>
				    </c:choose>
					</td>
				</tr>
				<tr>
					<td>
						<font color=red>交班说明：</font>
					</td>
					<td colspan="8">
						<textarea onblur="this.style.posHeight='40';" onfocus="this.style.posHeight=this.scrollHeight;" rows="3" cols="" id="lastproblem" style="width: 100%;border: 0">${lastproblem }</textarea>
					</td>
				</tr>
				</dg:gridrow>
		</dg:datagrid>
	</body>
</html>
