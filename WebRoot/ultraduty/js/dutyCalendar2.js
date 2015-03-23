var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31); // 每月天数
var today = new Today(); // 今日对象
var year = today.year; // 当前显示的年份
var month = today.month; // 当前显示的月份

// 今日对象
function Today() {
	this.now = new Date();
	this.year = this.now.getFullYear();
	this.month = this.now.getMonth();
	this.day = this.now.getDate(); // 按照本地时间返回指定的 Date 对象中表示月中某天的值（1 到 31
									// 之间的整数）。
}
function show(){
	//document.getElementById('thisyear').style.display = "";
	//document.getElementById('thismonth').style.display = "";
}


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
   document.getElementById('row5').style.display='block';
   }
   if(flagO || flagT){
   document.getElementById('row6').style.display='block';
   }
}
// 根据当前年月填充每日单元格
function fillBox(type,appId) {
	updateDateInfo(); // 更新年月提示
	$("td.calBox").empty(); // 清空每日单元格

	var dayCounter = 1; // 设置天数计数器并初始化为1
	var cal = new Date(year, month, 1); // 以当前年月第一天为参数创建日期对象
	var startDay = cal.getDay(); // 计算填充开始位置 按照本地时间返回指定的 Date 对象中表示周几的值（0
									// 代表星期日，1 代表星期一，依此类推）。
	// 计算填充结束位置
	var endDay = startDay + getDays(cal.getMonth(), cal.getFullYear()) - 1;
	// 如果显示的是今日所在月份的日程，设置day变量为今日日期
	var day = -1;
	if (today.year == year && today.month == month) {
		day = today.day;
	}

	// 从startDay开始到endDay结束，在每日单元格内填入日期信息
	for (var i = startDay; i <= endDay; i++) {
		// ///////////// month 从0开始
		var tempmonth;
		var tempday;
		if (month + 1 < 10) {
			tempmonth = "0" + (month + 1);
		} else {
			tempmonth = (month + 1);
		}
		if (dayCounter < 10) {
			tempday = "0" + dayCounter;
		} else {
			tempday = dayCounter;
		}
		// //////////////////////
		if (dayCounter == day) {
			$("#calBox" + i).html("<div class='date today' id='" + year + "-"
					+ tempmonth + "-" + tempday
					+ "' title='"+type+"'>" + dayCounter + "</div>");
		} else {
			$("#calBox" + i).html("<div class='date' id='" + year + "-"
					+ tempmonth + "-" + tempday
					+ "' title='"+type+"'>" + dayCounter + "</div>");
		}
		var currentBuildDate = year + "-" + tempmonth + "-" + tempday;
		dayCounter++;
	}
	// 得到当前月的首天和尾天的信息
	var tempmonth;
	var tempday;
	if (month + 1 < 10) {
		tempmonth = "0" + (month + 1);
	} else {
		tempmonth = (month + 1);
	}
	tempday = getDays(cal.getMonth(), cal.getFullYear());
	var monthFirstDay = year + "-" + tempmonth + "-01";
	var monthLastDay = year + "-" + tempmonth + "-" + tempday;
	getAllMonthArrayInfo(monthFirstDay, monthLastDay,type,appId); // 初始化时只和服务器交互一次
	$('#load-ing').hide();
}

//打开编辑排班的box
function openAddBox(src) {
	//审批状态
	var appType = $("input[id="+src.id+"]").attr("value");
	 // 判断当天日期前的日期不让操作：
	var opDate = src.id;
	var caltype = src.title;
	var organizationId=$("#organizationId").val();
	var quartersflag = $("#quartersflag").val();
	if("0" == quartersflag){
	 	showWindow("editDutyPerGroup.action?organizationId="+organizationId+"&cudatetime="+opDate+"&caltype=all&quartersflag=0&showbutton="+caltype);
	}else if("1" == quartersflag){
		showWindow("editDutyPerGroupQuar.action?organizationId="+organizationId+"&cudatetime="+opDate+"&caltype=all&quartersflag=1&showbutton="+caltype);
	}
}
function hideWindow(){
	  $("#calendarform").attr("disabled",false);
	  editwindow.location.href="#";
      $("#editdiv").hide();
      window.location.reload();
}
//增加编辑按钮
function addEditBtn(buildDate){
	//增加编辑按钮
	$("#"+buildDate+".date").click(function(){openAddBox(this);});
	$("#"+buildDate+".date").html("<span>"+$("#"+buildDate+".date").html()+"</span><div class='btn'><img src='../../common/style/blue/images/tag7.png' alt='编辑'/></div>");
}
// 构建页面显示的排班信息 for add edit
function buildTask(buildDate, shiftName, shiftTime, userInfoName, chargePInfoName,type,appType) {
	if(!isLeader)
		addEditBtn(buildDate);
	//增加编辑按钮
	if (shiftName[0] == "null")
		return false;
	var taskInfo = "<table cellpadding='0' cellspacing='0' class='dutyworktimeTable'>";
	for (var i = 0; i < shiftName.length; i++) {
		if(userInfoName[i] == "null" || typeof(userInfoName[i]) == "undefined" || userInfoName[i] == ""){
			userInfoName[i] = "";
		}else{
			userInfoName[i] = ","+userInfoName[i];
		}
		if(typeof(chargePInfoName[i]) == "undefined"){
			chargePInfoName[i] = "";
		}
		taskInfo = taskInfo + "<tr><td colspan='2' class='dutyworktime'><span>"
		+ shiftName[i] + "</span></td></tr><tr><td>"+ shiftTime[i] +"<td></tr><tr><td style='padding-left:5px;'><font color='red'>" ;
		if(chargePInfoName[i]!='null'){
			taskInfo = taskInfo +chargePInfoName[i];
		}else{
			taskInfo = taskInfo +"无主班人";
		}
		taskInfo = taskInfo+"</font>"+ userInfoName[i]+ "</td></tr>";
	}
   taskInfo=taskInfo+"</table>";
   var string="<div  id='" + buildDate + "' title='"+type+"' class='task' >" + taskInfo + "</div>";
   var id="task"+buildDate;
   if(document.getElementById(id)==null)  
     $("#" + buildDate).parent().append("<div id='" + buildDate + "' title='"+type+"' class='task' >" + taskInfo + "</div>");
   else
     $("#task" + buildDate).html(string);

}

// 判断是否闰年返回每月天数
function getDays(month, year) {
	if (1 == month) {
		if (((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400)) {
			return 29;
		} else {
			return 28;
		}
	} else {
		return daysInMonth[month];
	}
}
// 显示上月日程
function prevMonth(type) {
	if (month == 0) {//12月份取上月
		month = 11;
		document.getElementById("selectMonth").value='12';
		year--;
	} else if (month == 1) {//2月份取上月
		month = 0;
		document.getElementById("selectMonth").value='01';
	} else {
		month--;
	}
	fillBox(type,appId); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
// 显示上年日程
function prevYear(type) {
	year--;
	fillBox(type,appId); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
// 显示下月日程
function nextMonth(type) {
	if ((month + 1) > 11) {
		month = 0;
		document.getElementById("selectMonth").value='01';
		year++;
	} else {
		month++;
	}
	fillBox(type,appId); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
// 显示下年日程
function nextYear(type) {
	year++;
	fillBox(type,appId); // 填充每日单元格
	tableControl(); //设定单元格的行数
}

// 显示本月日程
function thisMonth(type) {
	year = today.year;
	month = today.month;
	fillBox(type,appId); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
// 显示本年日程
function thisYear(type) {
	year = today.year;
	fillBox(type,appId); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
/**
 * 绑定年月
 * yhg
 */
function updateDateInfo() {
	 var countYear = $("#selectYear")[0].length;
     for (var i = 1; i < countYear; i++) {
        if ($("#selectYear").get(0).options[i].value == year) {
            $("#selectYear").get(0).options[i].selected = true;
            break;
         }
       }///绑定年
	 var countMonth = $("#selectMonth")[0].length;
     for (var i = 1; i < countMonth; i++) {
        if ($("#selectMonth").get(0).options[i].value == month+1) {
            $("#selectMonth").get(0).options[i].selected = true;
            break;
         }
       }///绑定月

}

// 得到当前的日期格式
function currentDate() {
	var currentDate = new Today();
	var tempCuMonth;
	var tempCuDay;
	if (currentDate.month + 1 < 10) {
		tempCuMonth = "0" + (currentDate.month + 1);
	} else {
		tempCuMonth = (currentDate.month + 1);
	}
	if (currentDate.day < 10) {
		tempCuDay = "0" + currentDate.day;
	} else {
		tempCuDay = currentDate.day;
	}
	return currentDate.year + "-" + tempCuMonth + "-" + tempCuDay;

}

/**
 * 获得一个月的排班信息数据
 * 
 * @author yhg
 * @param {}
 *            monthFirstDay yyyy-mm-dd 某年某月的第一天
 * @param {}
 *            monthLastDay yyyy-mm-dd 某年某月的最后一天
 */
function getAllMonthArrayInfo(monthFirstDay, monthLastDay ,type,appId) {
	var organizationId = $("#organizationId").val();
	var action;
	//isLeader在页面中传入
	if(isLeader){
		action = "listMonthLeaderAppCalendar.action";
	}
	else{
		action = "listMonthAppCalendar.action";
	}
     $.getJSON(action + "?r="+Math.random(),
        {
            organizationId: organizationId,
            monthFirstDay: monthFirstDay,
            monthLastDay: monthLastDay,
            caltype: type,
            appId:appId
        },function(myJson) { 
            //一个循环，从月初到月末 遍历myJson数组
            for(var i=0; i<myJson.length; i++){
               //构建页面显示：
            	buildTask(myJson[i].buildDate,myJson[i].shiftName,myJson[i].shiftTime,myJson[i].userInfoName,myJson[i].chargePInfoName,type,myJson[i].appType); //建立任务节点
             }
            var ss = $("table tr td p");
			for(var i=0;i<ss.length;i++){
			    var s = ss[i].innerText;
				ss[i].title = ss[i].innerText;
				if(s.length>7){
					ss[i].innerText = s.substring(0,7) +"...";
				}
			}
          }
        );  
}

// 返回对象对页面左边距
function getLeft(src) {
	var obj = src;
	var objLeft = obj.offsetLeft;
	while (obj != null && obj.offsetParent != null
			&& obj.offsetParent.tagName != "BODY") {
		objLeft += obj.offsetParent.offsetLeft;
		obj = obj.offsetParent;
	}
	return objLeft;
}

// 返回对象对页面上边距
function getTop(src) {
	var obj = src;
	var objTop = obj.offsetTop;
	while (obj != null && obj.offsetParent != null
			&& obj.offsetParent.tagName != "BODY") {
		objTop += obj.offsetParent.offsetTop;
		obj = obj.offsetParent;
	}
	return objTop;
}
function resfush() {
	document.forms[0].submit();
}
function searchYearMonth(type){
	  var selectYear=$("#selectYear").attr("value");
	  var selectMonth=$("#selectMonth").attr("value");
	  if(selectMonth=="01" || selectMonth=="02"|| selectMonth=="03"|| selectMonth=="04" || selectMonth=="05" || selectMonth=="06" ||selectMonth=="07" ||selectMonth=="08" ||selectMonth=="09" )
	  {
	     selectMonth=selectMonth.substring(1)-1;
	  }else{
	     selectMonth=selectMonth-1;
	}
     year=selectYear;
     month =selectMonth;
	 fillBox(type,appId);
	 //关闭查询
	 cancelSearch();
	}
/**
 * 快速查询
 */	
function searchYearMonth(type) {
	var selectYear = $("#selectYear").val();
	var selectMonth = $("#selectMonth").val();
	if (selectMonth == "01" || selectMonth == "02" || selectMonth == "03"
			|| selectMonth == "04" || selectMonth == "05"
			|| selectMonth == "06" || selectMonth == "07"
			|| selectMonth == "08" || selectMonth == "09") {
		selectMonth = selectMonth.substring(1) - 1;

	} else {
		selectMonth = selectMonth - 1;
	}
	year = selectYear;
	month = selectMonth;
	fillBox(type,appId);
	tableControl(); //设定单元格的行数
}

/**
 *  根据模板天数初始化班次信息
 */
function initTemplate(){
	    var bdate = $("#beginDate").val();
	    if (bdate == '') {
	    	alert("排班起始日期不能为空");
			return false;
	    }
	    var edate = $("#endDate").val();
	    if (edate == '') {
	    	alert("排班截止日期不能为空");
			return false;
	    }
		var days = $("#initnum").val();
		var rex = /^[1-9][0-9]{0,2}$/;
		if(!rex.test(days)){
			alert("模板天数不正确");
			return false;
		}
		if(days <1 || days >365){
			alert("模板天数太大或太小");
			return false;
		}
		var organizationId = $("#organizationId").val();
       $.ajax({
			 url: 'checkExist.action',
			 type: 'POST',
			 dataType: 'TEXT',
			 data: 'beginDate='+bdate+'&endDate='+edate+'&organizationId='+organizationId,
			 success:  doCheckCalendar
	    });
		$("#beginDate").attr("disabled",true);
		$("#endDate").attr("disabled",true);
		$("#initnum").attr("disabled",true);
		$("#initbutton").attr("disabled",true);
		$("#dateType").attr("disabled",true);
		var quartersflag = $("#quartersflag").val();
    	if('1' == quartersflag){//按岗位排班
    		showquarter();
    	}else if('0' == quartersflag){
    		var dateType = $("#dateType").val();
    		$.ajax({
				 url: 'showpreviewdiv.action',
				 type: 'POST',
				 dataType: 'html',
				 data: 'organizationId='+organizationId+"&num="+days+"&dateType="+dateType,
				 success:  function (data) {
				 	$("#calplan").append(data);
				 }
		    });
    		//showpreviewdiv();
    	}
}
/**
 *  检查是否有排班信息
 * @param {} existText
 */
function doCheckCalendar(existText){
		if(existText == 'TRUE'){
			var override = window.confirm("选择日期内已有值班计划,是否覆盖?");
			if(!override){
				$("#save").attr("disabled",true);
			}else{
				$("#save").attr("disabled",false);
			}
		}else{
			$("#save").attr("disabled",false);
		}
	}
/**
 * 按模板天数列出班次信息
 */	
function showpreviewdiv(){
	 var num = $("#initnum").val();
	 var shiftName = $("#shiftNames").val();
	 var subsList = shiftName.split(",");
	 var shiftIds = $("#subids").val();
	 var shiftId = shiftIds.split(",");
	 var strcontent = "";
	 for(var i=0;i<num;i++){
	    var shifquar = "";
		for(var j=0;j<subsList.length;j++){
			var shift = "<div class='search_arrange_title'>";
		    shift = shift+"<li class='bold'>序号：</li>";
		    shift = shift+"<li class='padding'>第"+(i+1)+"天：</li>";
		    shift = shift+"<li class='bold'>班次：</li>";
		    shift = shift+"<li class='padding'>"+subsList[j]+"</li>";
	        shift = shift+"<li >值班人：<input type='hidden' id='user-"+i+"-"+shiftId[j]+"-type' name='usernametype'/> <input type='hidden' id='user-"+i+"-"+shiftId[j]+"' name='username'/><input type='text' id='user-"+i+"-"+shiftId[j]+"-label' /></li>";
	        shift = shift+"<li class='paddinghack2'><img src='../../common/style/blue/images/user.png' alt='执行人' id='user-"+i+"-"+shiftId[j]+"-per' onclick='showPopWinUser(this);' style='cursor: hand'/></li>";
	        shift = shift+"<li class='paddinghack2'><img src='../../common/style/blue/images/user_suit.png' alt='值班组' id='user-"+i+"-"+shiftId[j]+"-group' onclick='showPopWinGroup(this);' style='cursor: hand'/></li>";
	        shift = shift+"<li class='bold'>主班人：</li>";
		    shift = shift+"<li class='paddinghack'><input type='hidden' id='chief-"+i+"-"+shiftId[j]+"' name='chiefname'/><input type='text' id='chief-"+i+"-"+shiftId[j]+"-label' type='text' /></li>";
		    shift = shift+"<li class='paddinghack2'><img src='../../common/style/blue/images/user.png' alt='主班人' id='chief-"+i+"-"+shiftId[j]+"-img' onclick='showPopWinChief(this);' style='cursor: hand'/></li>";
	        shift = shift+"<li>&nbsp;&nbsp;&nbsp;&nbsp;</li>";
	        shift = shift+"</div>";
	        strcontent = strcontent + shift + "<div class='blank_ht'></div>";
		}
	 }
	 $("#calplan").append(strcontent);
	}
/**
 * 按岗位出排班信息
 */	
function showquarter(){
	var num = $("#initnum").val();
	var strcontent = "";
	for(var i=0;i<num;i++){
	    var day = "<div class='search_arrange_title'>";
	    day = day+"<li class='bold'>序号：</li>";
	    day = day+"<li class='padding'>第"+(i+1)+"天：</li>";
	    day = day+"</div>";
	    var shiftName = $("#shiftNames").val();
	    var subsList = shiftName.split(",");
	    var shiftIds = $("#subids").val();
	    var shiftId = shiftIds.split(",");
	    var shifquar = "";
		for(var j=0;j<subsList.length;j++){
		    var shift ="<div class='search_arrange_title2'>";
		    shift = shift+"<li class='bold'>班次：</li>";
		    shift = shift+"<li class='padding'>"+subsList[j]+"</li>";
		    shift = shift+"<li class='bold'>主班人：</li>";
		    shift = shift+"<li class='paddinghack'><input type='hidden' id='chief-"+i+"-"+shiftId[j]+"' name='chiefname'/>"+
		    		"<input type='text' id='chief-"+i+"-"+shiftId[j]+"-label'  type='text' /></li>";
		    shift = shift+"<li class='paddinghack2'><img src='../../common/style/blue/images/user.png' alt='主班人' id='chief-"+i+"-"+shiftId[j]+"-img' onclick='showPopWinChief(this);' style='cursor: hand'/></li>";
	        shift = shift+"</div>";
	        shifquar = shifquar + shift;
	        var quarName = $("#quarName").val();
	        var quarNameList = quarName.split(",");
	        var quarIds = $("#quarId").val();
			var quarId = quarIds.split(",");  
	        for(var k=0;k<quarNameList.length;k++){
	          var quarter = "<div class='duty_info'>";
	          quarter = quarter+"<li>对应岗位：</li>";
	          quarter = quarter+"<li class='padding'>"+quarNameList[k]+"</li>";
	          quarter = quarter+"<li >值班人：<input type='hidden' id='user-"+i+"-"+shiftId[j]+"-"+quarId[k]+"-type' name='usernametype'/> <input type='hidden' id='user-"+i+"-"+shiftId[j]+"-"+quarId[k]+"' name='username'/>"+
	          		"<input type='text' id='user-"+i+"-"+shiftId[j]+"-"+quarId[k]+"-label' /></li>";
	          quarter = quarter+"<li class='paddinghack2'><img src='../../common/style/blue/images/user.png' alt='执行人' id='user-"+i+"-"+shiftId[j]+"-"+quarId[k]+"-per' onclick='showPopWinUser(this);' style='cursor: hand'/></li>";
	          quarter = quarter+"<li class='paddinghack2'><img src='../../common/style/blue/images/user_suit.png' alt='值班组' id='user-"+i+"-"+shiftId[j]+"-"+quarId[k]+"-group' onclick='showPopWinGroup(this);' style='cursor: hand'/></li>";
	          quarter = quarter+"</div>";
	          shifquar = shifquar + quarter;
	        }
		}
		strcontent = strcontent + day + shifquar+ "<div class='blank_ht'></div>";
	 }
	 $("#calplan").append(strcontent);
}
/**
 * 显示列表视图
 */	
 function showMonthCalendar(calflag){
 	    var organizationId = $("#organizationId").val();
		window.location.href('calendarList.action?calflag='+calflag+'&organizationId='+organizationId);
 }
function showWindow(url){
	$("#calendarform").attr("disabled",true);
	editwindow.location.href=url;
	//editdiv.style.display="";
	$("#editdiv").show();
}
/**
鼠标拖动
*/
funs={
  index:100,  
  getFocus:function (target){  
      if(target.style.zIndex!=this.index){  
          this.index += 2;  
          var idx = this.index;  
          target.style.zIndex=idx;  
      }  
  },
  abs:function (element) {
      var result = { x: element.offsetLeft, y: element.offsetTop};
      element = element.offsetParent;
      while (element) {
          result.x += element.offsetLeft;
          result.y += element.offsetTop;
          element = element.offsetParent;
      }
      return result;
  }
};