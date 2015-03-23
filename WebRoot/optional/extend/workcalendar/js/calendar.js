var $selectedInput;
var $counter = 0;
function workTimeSpanInit(){
	$("#workTimeTb .workTimeSpan").bind("focus",function(){
		$selectedInput = $(this);
		var offset = $(this).offset();
		$("#workTimeSpanDiv").css("left",offset.left);
		if(offset.top<200){
			$("#workTimeSpanDiv").css("top",offset.top+22);
		} else {
			$("#workTimeSpanDiv").css("top",offset.top-145-3);
		}
		$("#workTimeSpanDiv").css("display","block");
		var timeValue = $(this).val();
		$("#workTimeSel")[0].options.length = 0;
		//$("#workTimeInputFrom").val("");
		//$("#workTimeInputTo").val("");
		if(timeValue!=""){
			var valArr = timeValue.split(",");
			for(var i=0;i<valArr.length;i++){
				$("#workTimeSel").append("<option value='"+valArr[i]+"' title='双击移除！'>"+valArr[i]+"</option>");
			}
		}
	});
	$("#workTimeTb tr:gt(0)").each(function(){
		$(this).find("input:lt(2)").bind("click",selectDate);
	});
}
//选择日期
function selectDate(){
	var yearStr=$("#calYear").val();
	if(yearStr==""){
		alert("请选择模版年份！");
		return false;
	}
	var minDate=yearStr+'-01-01';
	var maxDate=yearStr+'-12-31';
	WdatePicker({dateFmt:'MM-dd',startDate:minDate,maxDate:maxDate});
}
//初始化创建日历模板页面
function initCalendarCreate() {
	$("#calYear").val((new Date()).getYear()+1);
	$("#calType").css("width","100%");
	$("#workTimeType").css("width","60px");
	$("input[name^='holidaySets']").bind("click",selectDate);
	workTimeSpanInit();
	$("#btn_AddWorkTime").click(function(){
		$counter++;
		var newRow = "<tr>\n";
		//newRow     += 	"<td><input name='workTimes["+$counter+"].mark' type='text' style='width:92%' value=''/></td>\n";
		newRow     += 	"<td><input name='workTimes["+$counter+"].startTime' type='text' style='width:94%' readonly='readonly'/></td>\n";
		newRow     += 	"<td><input name='workTimes["+$counter+"].endTime' type='text' style='width:94%' readonly='readonly'/></td>\n";
		newRow     += 	"<td><input name='workTimes["+$counter+"].timeSpanStr' type='text' style='width:97.5%' class='workTimeSpan' readonly='readonly'/></td>\n";
		newRow     += 	"<td align='center'><a href='javascript:;' style='text-decoration:underline' onclick='delWorkTimeRow(this);'>删除</a></td>\n";
		newRow     += "</tr>\n";
		$("#workTimeTb").append(newRow);
		workTimeSpanInit();
		var $selectedInput = null;
		$("#workTimeSpanDiv").css("display","none");
	});
	$("#addWorkTime").click(function(){
		var timeFrom = $("#workTimeInputFrom").val();
		var timeTo = $("#workTimeInputTo").val();
		if(timeFrom!="" && timeTo!=""){
			var time = timeFrom+"-"+timeTo;
			var timeType = $("#workTimeType").val();
			if(timeType!=""){
				time += " " + timeType;
			}
			var workTimeSel = $("#workTimeSel")[0];
			var options = workTimeSel.options;
			var tag = false;
			for(var i=0;i<options.length;i++){
				if(options[i].value==time){
					tag = true;
					break;
				}
			}
			if(tag==false){
				$("#workTimeSel").append("<option value='"+time+"' title='双击移除！'>"+time+"</option>");
			 	$("#workTimeInputFrom").val("");
			 	$("#workTimeInputTo").val("");
			}
		}
	});
	$("#workTimeSel").bind("dblclick",function(){
		var workTimeSel = $("#workTimeSel")[0];
		var index = workTimeSel.selectedIndex;
		if(index!=-1){
			workTimeSel.options.remove(index);
		}
	});
	$("#cancelWorkTime").click(function(){
		$("#workTimeSpanDiv").css("display","none");
	});
	$("#confirmAddWorkTime").click(function(){
		var options = $("#workTimeSel")[0].options;
		var value = "";
		for(var i=0;i<options.length;i++){
			if(i>0){
				value += ",";
			}
			value += options[i].value;
		}
		$selectedInput.val(value);
		$("#workTimeSpanDiv").css("display","none");
	});
}

function delWorkTimeRow(source) {
	var $selectedInput = null;
	$("#workTimeSpanDiv").css("display","none");
	var rowIndex = source.parentNode.parentNode.rowIndex;
	$("#workTimeTb")[0].deleteRow(rowIndex);
}
