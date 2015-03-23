/**
 * 在鼠标光标处插入字符内容
 * @param obj --需要插入内容的对象
 * @param charvalue --需要插入的字符内容
 */
 function   insertText(obj,charvalue)   
{          
	obj.focus();  
	var leng=obj.value.length;
	if(typeof document.selection !="undefined")	//ie
	{
		var r =document.selection.createRange();   
		r.text   =   charvalue;   
	}
	else                                       						//firefox
	{
		obj.value=obj.value.substr(0,obj.selectionStart)+ charvalue +obj.value.substring(obj.selectionStart,leng);
 	}         
 } 