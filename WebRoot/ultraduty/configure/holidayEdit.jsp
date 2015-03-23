<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script src="${ctx}/ultraduty/js/dutyHoliday.js"></script>
		<script language="javascript">
			window.onresize = function() {
				setCenter(0,59);
			}
			window.onload = function() {
				setCenter(0,59);
			}
		</script>
		<script type="text/javascript">
			//页面加载完毕后执行fillBox函数,加载数据
			$(function() {
			    fillBox('all');
			    tableControl();
			});
			//控制日历表格显示行数
			function tableControl(){
				document.getElementById('row5').style.display='none';
				document.getElementById('row6').style.display='none';
			   /*
				   当月31天  星期 >4  6行
				   当月30天  日期 >5  6行
				   当月28天  星期 =0  4行
			   */
			   var y = document.getElementById('selectYear').value; //获取页面年
			   var m = document.getElementById('selectMonth').value; //获取面面月
			   var new_year = y;      //取当前的年份   
			   var new_month =m++;    //取下一个月的第一天，方便计算（最后一天不固定）   
			    if(month>12){          //如果当前大于12月，则年份转到下一年   
			    new_month-=12;         //月份减   
			    new_year++;            //年份增   
			    }   
			   var new_date = new Date(new_year,new_month,1);             //取当年当月中的第一天   
			   var last_day=(new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期
			   var second_date = new Date(new_date.getTime()-1000*60*60*24);
			   second_date.setDate(1);
			   var week = second_date.getDay();
			   var flagO = false;
			   var flagT = false;
			   var flagR = false;
			   //alert(last_day+'-'+week);
			   if(last_day == 31 && week > 4){
			     flagO = true;
				}
			   if(last_day == 30 && week > 5){
			    flagT = true;
			   }
			   if(last_day == 28 && week == 0){
			     flagR = true;
			   }
			   if(!flagR){
			   document.getElementById('row5').style.display='';
			   }
			   if(flagO || flagT){
			   document.getElementById('row6').style.display='';
			   }
			}

			function openwin(pid){
				if (pid == '') {
					window.open('enterNewDutyHoliday.action','','width=500,height=300,top=250,left=400,Location=no,Toolbar=no,Resizable=no,scrollbars=no');
				} else {
					window.open('editDutyHoliday.action?pid='+pid,'','width=500,height=300,top=250,left=400,Location=no,Toolbar=no,Resizable=no,scrollbars=no');
				}
			}
		</script>
	</head>
	<body>
		<form name="calendarform" id="calendarform" method="post">
			<div class="content">
				<div class="title_right">
					<div class="title_left">
						<span class="title_bg"> <span class="title_icon2">${nodePath}</span>
						</span>
						<span class="title_xieline"></span>
					</div>
				</div>
				<div class="page_div_bg">
					<div class="page_div">
						<li class="page_add_button" onmouseover="this.className='page_add_button_hover'" 
						    onmouseout="this.className='page_add_button'" onclick="openwin('')">
						    <eoms:lable name='com_btn_add'/>
						</li>
					</div>
					<span class="pagenumber">

						<li>
							<div class="page_split_line"></div>
						</li>
						<li>
							<li class="lastyear_button" onClick=prevYear('all'); onmouseover="this.className
								= 'lastyear_button_hover';onmouseout=this.className='lastyear_button';" style="cursor:hand"></li>
							<li class="thisyear_button" onClick=thisYear('all'); onmouseover="this.className
								= 'thisyear_button_hover';onmouseout=this.className='thisyear_button';" style="cursor:hand"></li>
							<li class="nextyear_button" onClick=nextYear('all'); onmouseover="this.className
								= 'nextyear_button_hover';onmouseout=this.className='nextyear_button';" style="cursor:hand"></li>
							<li class="lastmonth_button" onClick=prevMonth('all'); onmouseover="this.className
								= 'lastmonth_button_hover';onmouseout=this.className='lastmonth_button';" style="cursor:hand"></li>
							<li class="thismonth_button" onClick=thisMonth('all'); onmouseover="this.className
								= 'thismonth_button_hover';onmouseout=this.className='thismonth_button';" style="cursor:hand"></li>
							<li class="nextmonth_button" onClick=nextMonth('all'); onmouseover="this.className
								= 'nextmonth_button_hover';onmouseout=this.className='nextmonth_button';" style="cursor:hand"></li>
						</li>
						<li>
							<div class="page_split_line"></div>
						</li>
						<li class="paddingHack">
							<select name="selectYear" id="selectYear"
								onchange="searchYearMonth('all')">
								<c:forEach var="year" items="${listYears}">
									<option value="${year}">
										${year}
									</option>
								</c:forEach>
							</select>
						</li>
						<li>
							年
						</li> <!-- selected="true" -->
						<li class="paddingHack">
							<select name="selectMonth" id="selectMonth"
								onchange="searchYearMonth('all')">
								<c:forEach var="month" items="${listMonths}">
									<option value="${month.key}">
										${month.value}
									</option>
								</c:forEach>
							</select>
						</li>
						<li>
							月
						</li>
						<li>
							<div class="page_split_line"></div>
						</li> 
					</span>
					<!-- 新建排班信息box -->
					<div id="addBox">
						<div id="taskDate"></div>
					</div>
				</div>
				<!-- 日历表格 -->
				<div class="scroll_div" id="center">
					<table id="calTable">
						<tr>
							<td class="day">
								<span class="weekbg">周日</span>
							</td>
							<td class="day">
								<span class="weekbg">周一</span>
							</td>
							<td class="day">
								<span class="weekbg">周二</span>
							</td>
							<td class="day">
								<span class="weekbg">周三</span>
							</td>
							<td class="day">
								<span class="weekbg">周四</span>
							</td>
							<td class="day">
								<span class="weekbg">周五</span>
							</td>
							<td class="day">
								<span class="weekbg">周六</span>
							</td>
						</tr>
						<tr>
							<td class="calBox sun" id="calBox0"></td>
							<td class="calBox" id="calBox1"></td>
							<td class="calBox" id="calBox2"></td>
							<td class="calBox" id="calBox3"></td>
							<td class="calBox" id="calBox4"></td>
							<td class="calBox" id="calBox5"></td>
							<td class="calBox sat" id="calBox6"></td>
						</tr>
						<tr>
							<td class="calBox sun" id="calBox7"></td>
							<td class="calBox" id="calBox8"></td>
							<td class="calBox" id="calBox9"></td>
							<td class="calBox" id="calBox10"></td>
							<td class="calBox" id="calBox11"></td>
							<td class="calBox" id="calBox12"></td>
							<td class="calBox sat" id="calBox13"></td>
						</tr>
						<tr>
							<td class="calBox sun" id="calBox14"></td>
							<td class="calBox" id="calBox15"></td>
							<td class="calBox" id="calBox16"></td>
							<td class="calBox" id="calBox17"></td>
							<td class="calBox" id="calBox18"></td>
							<td class="calBox" id="calBox19"></td>
							<td class="calBox sat" id="calBox20"></td>
						</tr>
						<tr>
							<td class="calBox sun" id="calBox21"></td>
							<td class="calBox" id="calBox22"></td>
							<td class="calBox" id="calBox23"></td>
							<td class="calBox" id="calBox24"></td>
							<td class="calBox" id="calBox25"></td>
							<td class="calBox" id="calBox26"></td>
							<td class="calBox sat" id="calBox27"></td>
						</tr>
						<tr id="row5">
							<td class="calBox sun" id="calBox28"></td>
							<td class="calBox" id="calBox29"></td>
							<td class="calBox" id="calBox30"></td>
							<td class="calBox" id="calBox31"></td>
							<td class="calBox" id="calBox32"></td>
							<td class="calBox" id="calBox33"></td>
							<td class="calBox sat" id="calBox34"></td>
						</tr>
						<tr id="row6">
							<td class="calBox sun" id="calBox35"></td>
							<td class="calBox" id="calBox36"></td>
							<td class="calBox" id="calBox37"></td>
							<td class="calBox" id="calBox38"></td>
							<td class="calBox" id="calBox39"></td>
							<td class="calBox" id="calBox40"></td>
							<td class="calBox sat" id="calBox41"></td>
						</tr>
					</table>
				</div>
			</div>
		</form>
		<div id="editdiv" style="position: absolute; left: 30pt; top: 100pt; z-index: 100; display: none">
			<iframe src="" width="800" height="300" marginwidth="1"
				marginheight="1" hspace="1" vspace="1" frameborder="1"
				name="editwindow" />
		</div>
	</body>
</html>