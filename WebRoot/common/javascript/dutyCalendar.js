var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);    //每月天数
var today = new Today();    //今日对象
var year = today.year;      //当前显示的年份
var month = today.month;    //当前显示的月份

//今日对象
function Today() {
    this.now = new Date();
    this.year = this.now.getFullYear();
    this.month = this.now.getMonth();
    this.day = this.now.getDate();   //按照本地时间返回指定的 Date 对象中表示月中某天的值（1 到 31 之间的整数）。
}
//根据当前年月填充每日单元格
function fillBox() {
    updateDateInfo();                   //更新年月提示
    $("td.calBox").empty();             //清空每日单元格

    var dayCounter = 1;                 //设置天数计数器并初始化为1
    var cal = new Date(year, month, 1); //以当前年月第一天为参数创建日期对象
    var startDay = cal.getDay();        //计算填充开始位置 按照本地时间返回指定的 Date 对象中表示周几的值（0 代表星期日，1 代表星期一，依此类推）。
    //计算填充结束位置
    var endDay = startDay + getDays(cal.getMonth(), cal.getFullYear()) - 1;
    //如果显示的是今日所在月份的日程，设置day变量为今日日期
    var day = -1;
    if (today.year == year && today.month == month) {
        day = today.day;
    }

    //从startDay开始到endDay结束，在每日单元格内填入日期信息
    for (var i=startDay; i<=endDay; i++) {
    /////////////// month 从0开始
    var tempmonth; var tempday;
    if(month+1<10 ){ tempmonth="0"+(month+1);}else{tempmonth=(month+1); }
    if(dayCounter<10 ){ tempday="0"+dayCounter;}else{ tempday= dayCounter; }
    ////////////////////////
        if (dayCounter==day) {
            $("#calBox" + i).html("<div class='date today' id='" + year + "-" + tempmonth + "-" + tempday + "' onclick='openAddBox(this)'>" + dayCounter + "</div>");
        } else {
            $("#calBox" + i).html("<div class='date' id='" + year + "-" + tempmonth + "-" + tempday + "' onclick='openAddBox(this)'>" + dayCounter + "</div>");
        }
        var currentBuildDate=year+ "-" + tempmonth + "-" + tempday;
        dayCounter++;
    }
    //得到当前月的首天和尾天的信息  add by weiguo 05-27
    var tempmonth; var tempday;
    if(month+1<10 ){ tempmonth="0"+(month+1);}else{tempmonth=(month+1); }
    tempday=getDays(cal.getMonth(), cal.getFullYear());
    var monthFirstDay=year+ "-" +tempmonth+ "-01";
    var monthLastDay=year+ "-" +tempmonth+ "-"+tempday;
    getAllMonthArrayInfo(monthFirstDay,monthLastDay); //初始化时只和服务器交互一次
    $('#load-ing').hide();
}

//构建页面显示的排班信息 for add edit
function buildTask(buildDate,shiftName,userInfoName,chargePInfoName) {

 if(shiftName[0]=="null") return false;
  var taskInfo="<table cellpadding='0' cellspacing='0'><tr><th>班次</th><th>执行人</th><th>组长</th></tr>";
  taskInfo = "<table cellpadding='0' cellspacing='0'>";
 for(var i=0;i<shiftName.length;i++){  
 taskInfo=taskInfo+"<tr><td colspan='1' class='dutyworktime'>"+shiftName[i]+"</td></tr><tr><td><p>"+userInfoName[i]+"</p></td><td><font color='#f00044'><p> "+chargePInfoName[i]+"</p></font></td></tr>";
 }
  taskInfo=taskInfo+"</table>";
  var string="<div  id='task" + buildDate + "' class='task' onclick='editTask(this)'>" + taskInfo + "</div>";
  var id="task"+buildDate;
  if(document.getElementById(id)==null)  //判断id是否存在，用document更好些
  $("#" + buildDate).parent().append("<div id='task" + buildDate + "' class='task' onclick='editTask(this)'>" + taskInfo + "</div>");
  else
  $("#task" + buildDate).html(string);
  
  
   
}
//构建页面显示的排班信息 for delete
function buildTaskForDel(buildDate) {
var day=buildDate.substring(8);
if(day.substring(0,1)=="0"){
  day=day.substring(1);
}
var string="<div id="+buildDate+" class='date' onclick='openAddBox(this)'>"+day+"</div>";
var currentDayStr="<div id="+buildDate+" class='date today' onclick='openAddBox(this)'>"+day+"</div>";
var currenDay=currentDate();
 if(buildDate==currenDay){
  $("#" + buildDate).parent().html(currentDayStr);
  }
 else{
  $("#" + buildDate).parent().html(string);
  }
}


//判断是否闰年返回每月天数
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
//显示上月日程
function prevMonth() {
    if ((month - 1) < 0) {
        month = 11;
        year--;
    } else {
        month--;
    }
    fillBox();              //填充每日单元格
}
//显示上年日程
function prevYear() {
        year--;
    fillBox();              //填充每日单元格
}
//显示下月日程
function nextMonth() {
    if((month + 1) > 11) {
        month = 0;
        year++;
    } else {
        month++;
    }
    fillBox();              //填充每日单元格
}
//显示下年日程
function nextYear() {
   year++;
    fillBox();              //填充每日单元格
}

//显示本月日程
function thisMonth() {
    year = today.year;
    month = today.month;
    fillBox();              //填充每日单元格
}
//显示本年日程
function thisYear() {
    year = today.year;
    fillBox();              //填充每日单元格
}
//更新年月提示
function updateDateInfo() {
    $("#dateInfo").html(year + "年" + (month + 1) + "月");
}

/////////////////////////单元格的编辑功能脚本了
//得到当前的日期格式
function currentDate(){
  var currentDate=new Today();
  var tempCuMonth;
  var tempCuDay;
  if(currentDate.month+1<10 ){ tempCuMonth="0"+(currentDate.month+1);}else{tempCuMonth=(currentDate.month+1); }
  if(currentDate.day<10 ){ tempCuDay="0"+currentDate.day;}else{ tempCuDay= currentDate.day; }
  return currentDate.year + "-" + tempCuMonth + "-" + tempCuDay;
  
  
}
//打开新建排班的box
function openAddBox(src) {
  //判断当天日期前的日期不让操作：
    var opDate=src.id;
    var currentDay=currentDate();
    if(opDate<currentDay){
      alert("对不起，所选日期小于当前日期，不能再进行排班操作");
      return false;
    }
    window.open('dutyCalendarAdd.html');
    //$("#taskDate").html(src.id);                    //设置新建日期
    //$("#addBox").slideDown(); 
   // mobify by weiguo 2009-06-17 变成弹出窗口
   //var selectedGroupID=$("#selectedGroupID").attr("value");
   //添加一个已经交接班的排班信息不让删除的判断  add by weiguo 2009-06-18
    // $.post("${ctx}/dayNightDuty/checkAddArrangeInfo.action",   //服务器页面地址
    //        {
    //           selectedGroupID: selectedGroupID,
    //            buildDate: opDate
    //        },
    //        function(data) { 
   //           if(data=="yes"){
    //          window.open("addArrangeList.action?selectedGroupID="+selectedGroupID+"&buildDate="+opDate,"newwindow","width=800,height=600,top=200,left=350");   
    //          }
   //           else{
    //           alert("对不起,当天已经进行了交班或接班，所以不能再新建或修改排班信息");
    //          }
     //       }
     //   ); 
  
}
//向服务器提交新建排班信息 mobify by weiguo 2009-06-17
function addTask(obj) {
    //得到页面上的填写信息
     var selectedGroupID=$("#selectedGroupID").attr("value");
     var shiftIDs=new Array();
     var userInfoIDs=new Array();
     var groupInfoIDs=new Array();
     var chargePInfoIDs=new Array();
     $("input[name=id]").each(function(index){
	 shiftIDs[index]=this.value;
	 });
	 $("input[name=userinfo]").each(function(index){
	  userInfoIDs[index]=this.value;
	 });
	  $("input[name=goupinfo]").each(function(index){
	  groupInfoIDs[index]=this.value;
	 });
	 $("input[name=chargePeople]").each(function(index){
	  chargePInfoIDs[index]=this.value;
	 });
    
     var error;
    //检查排班信息是否为空
     $("input[name=selectInfoName]").each(function(index){
	   if(this.value==""){
	   error="true";
	   }
	 });
	   //检查排班--组长信息是否为空
 /*    $("input[name=chargePeopleName]").each(function(index){
	   if(this.value==""){
	   error="true";
	   }
	 });*/
	 if(error=="true"){
	  alert("请输入完全排班人员信息再排班");
	  return false;
	 }
	 $(obj).attr("disabled","disabled"); 
	  var buildDate = $("#taskDate").html();
	          //获取任务日期
        $.post("${ctx}/dayNightDuty/saveArrangeInfo.action",   //服务器页面地址
            {
                selectedGroupID: selectedGroupID,
                buildDate: buildDate,
                shiftIDs:shiftIDs,
                userInfoIDs:userInfoIDs,
                groupInfoIDs:groupInfoIDs,
                chargePInfoIDs:chargePInfoIDs 
            },
            function(date) { 
                 var shiftName=new Array();
                 var userInfoName=new Array();
                 var chargePInfoName=new Array();
          $("input[name=name]").each(function(index){
	     shiftName[index]=this.value;
	      });
	      $("input[name=selectInfoName]").each(function(index){
	      userInfoName[index]=this.value;
	      });
	     $("input[name=chargePeopleName]").each(function(index){
	     chargePInfoName[index]=this.value;
	      });
                //构建页面显示：
                //buildTask(buildDate,shiftName,userInfoName,chargePInfoName); //建立任务节点
               // closeAddBox(obj);                          //关闭新建任务box
               //构建页面显示：
               window.opener.resfush();
                window.close();                          //关闭窗口
             
            }
        ); 
     

}
//从服务器获取任务信息，并填充排班信息
function getCurrentArrayInfo(buildDate){
   var selectedGroupID=$("#selectedGroupID").attr("value"); 
   $.getJSON("${ctx}/dayNightDuty/getJsonByDate.action",   //服务器页面地址
            {
                selectedGroupID: selectedGroupID,
                buildDate: buildDate
            },
            function(myJson) { 
                //构建页面显示：
                //buildTask(buildDate,myJson.shiftName,myJson.userInfoName,myJson.chargePInfoName); //建立任务节点
                alert(myJson.userInfoName);
            }
        );  
 
}
 function getAllMonthArrayInfo(monthFirstDay,monthLastDay){
   var selectedGroupID=$("#selectedGroupID").attr("value"); 
   $.getJSON("${ctx}/dayNightDuty/getJsonByAllDate.action?cudatetime="+(new Date()).getTime(),       //服务器页面地址(变换地址，每次json操作)
            {
                selectedGroupID: selectedGroupID,
                monthFirstDay: monthFirstDay,
                monthLastDay:monthLastDay
            },
            
            function(myJson) { 
                //一个循环，从月初到月末 遍历myJson数组
                for(var i=0; i<myJson.length; i++){
                //构建页面显示：
                 buildTask(myJson[i].buildDate,myJson[i].shiftName,myJson[i].userInfoName,myJson[i].chargePInfoName); //建立任务节点
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

//打开编辑排班box
function editTask(src) {
    var buildDate=$(src).attr("id").substr(4);
    //判断当天日期前的日期不让操作：
    var currentDay=currentDate();
    var selectedGroupID=$("#selectedGroupID").attr("value");
    if(buildDate<currentDay){
     //数据库中有什么，就显示什么，不管班次列表是否有变化。在原来基础上修改 超时不能修改
     window.open("editArrangeList.action?selectedGroupID="+selectedGroupID+"&buildDate="+buildDate+"&canEdit=false","newwindow","width=800,height=600,top=200,left=350");   
     
    }
    else{
     //如果当天记录为已经交接班，则不让操作 add by weiguo 2009-06-18
     $.post("${ctx}/dayNightDuty/checkAddArrangeInfo.action",   //服务器页面地址
            {
                selectedGroupID: selectedGroupID,
                buildDate: buildDate
            },
            function(data) { 
              if(data=="yes"){
              //数据库中有什么，就显示什么，不管班次列表是否有变化。在原来基础上修改
          window.open("editArrangeList.action?selectedGroupID="+selectedGroupID+"&buildDate="+buildDate,"newwindow","width=800,height=600,top=200,left=350");   
              }
              else{
           window.open("editArrangeList.action?selectedGroupID="+selectedGroupID+"&buildDate="+buildDate+"&canEdit=false","newwindow","width=800,height=600,top=200,left=350");   
              }
              
            }
        ); 
     
     
    
  }
   
   
  
}
//关闭新建排班box
function closeAddBox(obj) {
    alert("sss");
    $("#addBox").slideUp("slow",function(){
      $(obj).attr("disabled","");  
    });
}

//关闭编辑box
function closeEditBox() {
    $("#editBox").slideUp();
}
//关闭查询            
function cancelSearch() {
    $("#fastSearch").slideUp();
}


//返回对象对页面左边距
function getLeft(src){
    var obj = src;
    var objLeft = obj.offsetLeft;
    while(obj != null && obj.offsetParent != null && obj.offsetParent.tagName != "BODY"){
        objLeft += obj.offsetParent.offsetLeft;
        obj = obj.offsetParent;
    }
    return objLeft;
}

//返回对象对页面上边距
function getTop(src){
    var obj = src;
    var objTop = obj.offsetTop;
    while(obj != null && obj.offsetParent != null && obj.offsetParent.tagName != "BODY"){
        objTop += obj.offsetParent.offsetTop;
        obj = obj.offsetParent;
    }
    return objTop;
}
		function autoArrange(){
		   var selectedGroupID=$("#selectedGroupID").attr("value");
        //数据库中有什么，就显示什么，不管班次列表是否有变化。在原来基础上修改
        window.open("editAutoArrange.action?selectedGroupID="+selectedGroupID,"newwindow","width=750,height=350,top=200,left=250" );   
		}
		function resfush(){
		document.forms[0].submit();
		}
		//function searchYearMonth(){
		 // var selectYear=$("#selectYear").attr("value");
		//  var selectMonth=$("#selectMonth").attr("value");	 
		//  if(selectMonth=="01" || selectMonth=="02"|| selectMonth=="03"|| selectMonth=="04" || selectMonth=="05" || selectMonth=="06" ||selectMonth=="07" ||selectMonth=="08" ||selectMonth=="09" )
		//  {
		//  selectMonth=selectMonth.substring(1)-1;
		//  
		//  }
		//  else{
		//  selectMonth=selectMonth-1;
		//}
       //  year=selectYear;
       // month =selectMonth;
		// fillBox();
		 //关闭查询
		 //cancelSearch();
		//}
		function searchYearMonth(){
		  var selectYear=$("#selectYear").attr("value");
		  var selectMonth=$("#selectMonth").attr("value");	 
		  if(selectMonth=="01" || selectMonth=="02"|| selectMonth=="03"|| selectMonth=="04" || selectMonth=="05" || selectMonth=="06" ||selectMonth=="07" ||selectMonth=="08" ||selectMonth=="09" )
		  {
		  selectMonth=selectMonth.substring(1)-1;
		  
		  }
		  else{
		  selectMonth=selectMonth-1;
		}
         year=selectYear;
         month =selectMonth;
		 fillBox();
		}
		//快速查询 yyyy--mm
		function fastSearch(){
		  $("#fastSearch").slideDown(); 
		}
		//导出月信息
		function exportMonthInfo(){
		 var selectedGroupID=$("#selectedGroupID").attr("value");
		 var buildY =year;
		 var tempCuMonth;
		 if(month+1<10){tempCuMonth="0"+(month+1);}else{tempCuMonth=(month+1); }
		 var buildYM=year+"-"+tempCuMonth;
		  window.location.href="exportMonthInfoPro.action?selectedGroupID="+selectedGroupID+"&buildYM="+buildYM;
		}
		//导入月信息
		function importMonthInfo(){
		 var selectedGroupID=$("#selectedGroupID").attr("value");
		 window.open("loadTemplatePro.action?selectedGroupID="+selectedGroupID,"loadTemplatePro","width=800,height=350,top=200,left=250" ); 
		}
		
		
function addArrange(obj) {
    //得到页面上的填写信息
     var selectedGroupID=$("#selectedGroupID").attr("value");
     var shiftIDs=new Array();
     var userInfoIDs=new Array();
     var groupInfoIDs=new Array();
     var chargePInfoIDs=new Array();
     $("input[name=id]").each(function(index){
	 shiftIDs[index]=this.value;
	 });
	 $("input[name=userinfo]").each(function(index){
	  userInfoIDs[index]=this.value;
	 });
	  $("input[name=goupinfo]").each(function(index){
	  groupInfoIDs[index]=this.value;
	 });
	 $("input[name=chargePeople]").each(function(index){
	  chargePInfoIDs[index]=this.value;
	 });
    
     var error;
   //检查排班信息是否为空
     $("input[name=selectInfoName]").each(function(index){
	   if(this.value==""){
	   error="true";
	   }
	 });
	 if(error=="true"){
	  alert("请输入完全排班人员信息再排班");
	  return false;
	 }
	 $(obj).attr("disabled","disabled"); 
	  var buildDate = $("#taskDate").html();
	          //获取任务日期
        $.post("${ctx}/dayNightDuty/saveArrangeInfo.action",   //服务器页面地址
            {
                selectedGroupID: selectedGroupID,
                buildDate: buildDate,
                shiftIDs:shiftIDs,
                userInfoIDs:userInfoIDs,
                groupInfoIDs:groupInfoIDs,
                chargePInfoIDs:chargePInfoIDs 
            },
            function(date) { 
                var shiftName=new Array();
                var userInfoName=new Array();
                var chargePInfoName=new Array();
             $("input[name=name]").each(function(index){
	         shiftName[index]=this.value;
	         });
	         $("input[name=selectInfoName]").each(function(index){
	        userInfoName[index]=this.value;
	         });
	       $("input[name=chargePeopleName]").each(function(index){
	       chargePInfoName[index]=this.value;
	        });
               
                //构建页面显示：
               // window.opener.location.reload(); //建立排班单元格节点
                window.opener.resfush();
                window.close();                          //关闭窗口
             
            }
        );  

}
 function deleteArrange(){
   //提示一下要删除
   if(!window.confirm("你确定要删除这天的排班记录吗?")){
      return false;
   }  
  //得到页面上的填写信息
     var selectedGroupID=$("#selectedGroupID").attr("value");
     var buildDate = $("#taskDate").html();
     $.post("${ctx}/dayNightDuty/deleteArrangeInfo.action",   //服务器页面地址
            {
                selectedGroupID: selectedGroupID,
                buildDate: buildDate
            },
            function(data) { 
                if(data=="yes"){
	           //构建页面显示：
                window.opener.buildTaskForDel(buildDate); //清除任务节点
                window.close(); }  //关闭新建任务box
                else{
                alert("对不起，当天已经进行了交班或接班，所以不能修改排班信息 ");
                }
                                         
             
            }
        );  
     
 
 }
 //关闭修改页面
  function closeEditBox(){
     window.close();
  }
  //选择不同的组并提交信息
 function changeGroupID(){
   document.forms[0].submit(); 
 } 
 
  //弹出树
 function openUserTree(){
 	  inputlyte("../demoTree/groupGroupUserTreeById.action");
 }
 function changUser(object){
 	var index=object.parentNode.parentNode.id;
	document.getElementById('lb_focus').value = 'selectInfoName'+index ;
	document.getElementById('lb_value').value = 'userinfo'+index;
	document.getElementById('lb_radioFlag').value = "2";
	document.getElementById('goupinfo'+index).value='';
 }
 
 function openGroupTree(){
 	  inputlyte("../demoTree/groupGroupTreeById.action");
 }
 function changGroup(object){
 	var index=object.parentNode.parentNode.id;
	document.getElementById('lb_focus').value = 'selectInfoName'+index ;
	document.getElementById('lb_value').value = 'goupinfo'+index;
	document.getElementById('lb_radioFlag').value = "1";
	document.getElementById('userinfo'+index).value='';
 }
 
 function openGrouperTree(gId){
 	if(!document.getElementById('selectInfoName' + gId).value){
		alert('请先选择值班人！');
		return false;
	}
	var useri = document.getElementById('userinfo'+gId).value;
	var groupi = document.getElementById('goupinfo'+gId).value;
	if(useri == "" && groupi != ""){
		document.getElementById('fieldName').value = groupi;
		
	}else if(useri != "" && groupi == ""){
		document.getElementById('fieldName').value = useri;
	}
	inputlyte("../demoTree/groupGroupPeopleTreeById.action?map.selectInfo="+document.getElementById('fieldName').value);
 }
 function changGrouper(object){
 	var index=object.parentNode.parentNode.id;
    document.getElementById('lb_focus').value ='chargePeopleName'+index ; //定位姓名显示
	document.getElementById('lb_value').value = 'chargePeople'+index;  //定位id显示
	document.getElementById('lb_radioFlag').value = "1";
 }