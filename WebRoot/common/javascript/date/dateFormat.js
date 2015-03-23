/*  
  
  2006-1-1 15:14:16 
*/  
function parseDate(str){
	if(str != null && str != '') {
		if(typeof str == 'string'){   
	    var results = str.match(/^ *(\d{4})-(\d{1,2})-(\d{1,2}) +(\d{1,2}):(\d{1,2}):(\d{1,2}) *$/);   
	    if(results && results.length>6) {
			var date = new Date();
			date.setTime(0);
			date.setYear(results[1]);
			date.setMonth(results[2]-1);
			date.setDate(results[3]);
			date.setHours(results[4]);
			date.setMinutes(results[5]);
			date.setSeconds(results[6]);
			return date;
		}
	  }
	}
  return '';   
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
function formatDateTimeLength(str)
{
	if((str + '').length == 1)
	{
		return '0' + str;
	}
	else
	{
		return str;
	}
}

function parseDateToSeconds(str) {
	if(str != '' && str != '0') {
		return parseInt(parseDate(str).getTime()/1000);
	}
	return 0;	
}