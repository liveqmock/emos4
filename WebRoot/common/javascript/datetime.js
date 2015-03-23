/**
* 修改整理:
* 功能:日期,时间类公用函数
* 日期20150104
* author:zhangxuegang
*/

var xDateTime={
	//--返回00:00:00这种格式的时间--
	getCurrentTime:function ()
	{
		var d, s = "";          // 声明变量
		//d = new Date();     	// 创建 Date 对象。 
		try{
			d = new sysDate();
		}catch(e){
			d = new Date(); 
		}
		var hours = ""+(d.getHours());
		var minutes = ""+d.getMinutes();
		var seconds = ""+d.getSeconds();
		if(hours.length==1){
			hours="0"+hours;
		}
		if(minutes.length==1){
			minutes="0"+minutes;
		}
		if(seconds.length==1){
			seconds="0"+seconds;
		}
		s += hours+ ":";
		s += minutes + ":"; 
		s += seconds;
		return(s);                                // 返回时间
	},	    
	//--返回2002/02/02这种格式的日期--
	getFullCurrentDate:function ()
	{
		var d, s = "";          // 声明变量。
		//d = new Date();     	// 创建 Date 对象。  
		var month = "";
		var day = "";
		var year = "";
		try{
			d = new sysYMD();//new sysDate();
			month = ""+d.getMonth();
			day = ""+d.getDay();
			year = ""+d.getFullYear();
		}catch(e){
			d = new Date(); 
			month = ""+(d.getMonth() + 1);
			day = ""+d.getDate();
			year = ""+d.getYear();
		}
		if(month.length==1){
			month="0"+month;
		}
		if(day.length==1){
			day="0"+day;
		}
		s += year+ "/";
		s += month + "/"; 
		s += day;
		return(s);                                // 返回日期
	},	    
	//--返回20020202这种格式的日期--
	getSimpleCurrentDate:function (){
		var d, s = "";          // 声明变量。
		//d = new Date();     	// 创建 Date 对象。  
		var month = "";
		var day = "";
		var year = "";
		try{
			d = new sysYMD();//new sysDate();
			month = ""+d.getMonth();
			day = ""+d.getDay();
			year = ""+d.getFullYear();
		}catch(e){
			d = new Date(); 
			month = ""+(d.getMonth() + 1);
			day = ""+d.getDate();
			year = ""+d.getYear();
		}
		if(month.length==1){
			month="0"+month;
		}
		if(day.length==1){
			day="0"+day;
		}
		s += year;
		s += month; 
		s += day;
		return(s);                                // 返回日期
	},
	//--判断日期是否是正确的日期格式--
	isDate:function (str){ 
		var reg = /^((((1[6-9]|[2-9]\d)\d{2})\/(0{1}[13578]|1[02])\/(0{1}[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})\/(0{1}[13456789]|1[012])\/(0{1}[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})\/0{1}2\/(0{1}[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))\/0{1}2\/29-))$/
		if (reg.test(str)) return true;
		return false;
	}
}
//--将20080228或者2008/02/28这种格式的字符串转换成日期对象--
Date.prototype.StringToDate= function(strDate){
	while(strDate.indexOf("/")!=-1){
		strDate=strDate.replace("/","");
	}
	strDate=strDate.replace(/(^\s*)|(\s*$)/g, "");
	var year = parseInt(strDate.substring(0,4),10);
	var month = parseInt(strDate.substring(4,6),10);
	var day = parseInt(strDate.substring(6,8),10);
	month--;
	return new Date(year,month,day);

}
//--将日期对象转换成指定格式的字符串 type:'simple'-返回20080228这种格式,'full'-表示返回2008/02/28这种格式--
Date.prototype.DateToString= function(type){
	var dtTmp = this;
	var year = ""+dtTmp.getFullYear();
	var month = ""+(dtTmp.getMonth()+1);
	var day = ""+dtTmp.getDate();
	if(month.length==1){
		month="0"+month;
	}
	if(day.length==1){
		day="0"+day;
	}
	if(type=="simple"){
		return formatReturnDate(year+month+day);
	}
	if(type=="full"){
		return year+"/"+month+"/"+day;
	}
}
//--在日期对象上增加指定的年,或月,或日返回一个日期对象 strInterval:'y'-表示增加年,'m'-表示增加月,'d'-表示增加日 Number-要增加的年月日的数量--
Date.prototype.DateAdd= function(strInterval,Number){
    var dtTmp = this; 
	var dtTmp2;
	Number = parseInt(Number);
	if(strInterval=="y"){
		dtTmp.setYear(dtTmp.getFullYear()+Number); 
	}
	if(strInterval=="m"){
		dtTmp.setMonth(dtTmp.getMonth()+Number); 
	}
	if(strInterval=="d"){
		dtTmp.setDate(dtTmp.getDate()+Number); 
	}
	return dtTmp;
}

//--将字符串类型的日期转换为毫秒,输入格式为2014-12-12 00:00:00这样的字符串时间--
function formatDateToTime(strDate){
	if(strDate==""){
		return 0;
	}else{
		if(strDate.indexOf("-")!=-1){
			strDate=strDate.replace("-","/");
			return new Date(strDate).getTime();
		}
		
	}
	
}

//将秒格式的时间转换成时间格式	
function parseSecondToStr(sec) {
		var dateString = '';
		if(sec != null && sec != '' && sec != 'null' && sec != '0')
		{
			dateTime = new Date(sec*1000);
			dateString = dateTime.getFullYear();
			dateString += '-' + formatDateTimeLength((dateTime.getMonth()+1));
			dateString += '-' + formatDateTimeLength((dateTime.getDate()));
			dateString += ' ' + formatDateTimeLength(dateTime.getHours());
			dateString += ':' + formatDateTimeLength(dateTime.getMinutes());
			dateString += ':' + formatDateTimeLength(dateTime.getSeconds());
		}
		return dateString;
	}
	function formatDateTimeLength(str) {
	if ((str + '').length == 1) {
		return '0' + str;
	} else {
		return str;
	}
}


