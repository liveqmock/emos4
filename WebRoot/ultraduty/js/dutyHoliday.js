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
// 根据当前年月填充每日单元格
function fillBox(type) {
	type = '新建';
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
			$("#calBox" + i).html("<div class='date1 today1' id='" + year + "-"
					+ tempmonth + "-" + tempday
					+ "' title='"+type+"' onclick='openAddBox(this)' align='center'>" + dayCounter + "</div>");
		} else {
			$("#calBox" + i).html("<div class='date1' id='" + year + "-"
					+ tempmonth + "-" + tempday
					+ "' title='"+type+"' onclick='openAddBox(this)' align='center'>" + dayCounter + "</div>");
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
	getAllMonthArrayInfo(monthFirstDay, monthLastDay); // 初始化时只和服务器交互一次
	$('#load-ing').hide();
}

/**
 * 获得一个月的节假日信息
 * 
 * @author yhg
 * @param {}
 *            monthFirstDay yyyy-mm-dd 某年某月的第一天
 * @param {}
 *            monthLastDay yyyy-mm-dd 某年某月的最后一天
 */
function getAllMonthArrayInfo(monthFirstDay, monthLastDay) {
	 $.getJSON("listHolidayAndNot.action?r="+Math.random(),
	    {
	        monthFirstDay: monthFirstDay,
	        monthLastDay: monthLastDay
	    },function(myJson) {
	        //一个循环，从月初到月末 遍历myJson数组
	        for(var i=0; i<myJson.length; i++){
	           //构建页面显示：
	           buildTask(myJson[i].buildDate,myJson[i].isHoliday,myJson[i].holidayName); //建立任务节点
	         }
	      }
	    );  
}

// 构建页面显示的节假日信息 for add edit
function buildTask(buildDate, isHoliday,holidayName) {
   if(isHoliday == 0){
	   document.getElementById(buildDate).style.color = 'red';
   }else if(isHoliday == 1){  
	   document.getElementById(buildDate).style.color = 'Black';
   }
   if(document.getElementById(buildDate)!=null){
	   document.getElementById(buildDate).title = holidayName;
	   document.getElementById(buildDate).innerHTML += "<br/><br/><font size='3px'>"+holidayName+"</font>";
   }
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
	fillBox(type); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
// 显示上年日程
function prevYear(type) {
	year--;
	fillBox(type); // 填充每日单元格
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
	fillBox(type); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
// 显示下年日程
function nextYear(type) {
	year++;
	fillBox(type); // 填充每日单元格
	tableControl(); //设定单元格的行数
}

// 显示本月日程
function thisMonth(type) {
	year = today.year;
	month = today.month;
	fillBox(type); // 填充每日单元格
	tableControl(); //设定单元格的行数
}
// 显示本年日程
function thisYear(type) {
	year = today.year;
	fillBox(type); // 填充每日单元格
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
// 打开编辑节假日
function openAddBox(thisObj) {
	var monthFirstDay = thisObj.id;
	 $.post("editDutyHolidayByDateValidate.action?r="+Math.random(),
	    {
	        monthFirstDay: monthFirstDay
	    },function(flag) {
	        if(flag == 'hasHoliday'){
	        	window.open('editDutyHolidayByDate.action?monthFirstDay='+monthFirstDay,'','width=500,height=300,top=250,left=400,Location=no,Toolbar=no,Resizable=no,scrollbars=no');
	        }else{
	        	window.open('dutyHolidayAddInDate.action?startTime='+monthFirstDay,'','width=500,height=300,top=250,left=400,Location=no,Toolbar=no,Resizable=no,scrollbars=no');
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
	fillBox(type);
	tableControl(); //设定单元格的行数
}

