package com.ultrapower.eoms.common.plugin.datagrid.core;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

public class DataGridFnc {

	public static void getJSFunction(JspWriter out) throws IOException
	{
		out.println("<script language=\"javascript\">");
		getConditionDiv(out);
		getCheckBoxSelectFunction(out);
		getCheckBoxSelectValue(out);
		getPageNumberFunction(out);
		out.println("</script>");
	}
	/**
	 * 显示和隐藏查询条件区域
	 * @throws IOException
	 */
	public static void getConditionDiv(JspWriter out) throws IOException
	{
		out.println("function showsearch(obj)");
		out.println("{");
		out.println("   var objdiv=document.getElementById('serachdiv')");
		
		out.println("   if(objdiv!=null)");
		out.println("   {"); 
		out.println("		var display = document.getElementById('serachdiv').style.display;");
		out.println("		if(display == 'block'){");
		out.println("				document.getElementById('serachdiv').style.display='none';");
		out.println("		}");
		out.println("	   else{");
		out.println("			document.getElementById('serachdiv').style.display='block';");
		out.println("		}");
		out.println("	}");
		out.println("}");
		
	}
	/**
	 * 分页Javascript
	 * @param out
	 * @throws IOException
	 */
	public static void getPageNumberFunction(JspWriter out) throws IOException
	{
		
		out.println("function setSelectValue(objSel,objValue)");
		out.println("{  ");
		out.println("  objSel.options[objValue-1].selected=true;");  
		out.println("}");//out.println("function setSelectValue(objSel,objValue)");
		
		out.println("function tranferPage(type)");
		out.println("{");
		
		out.println("    document.getElementById('"+GridConstants.HIDDEN_ISTRANFER+"').value='1';");
		//out.println("   alert(document.getElementById('"+GridConstants.HIDDEN_ISTRANFER+"').value);");		
		out.println("    var objCurpage=document.getElementById('"+GridConstants.HIDDEN_CURRENT_PAGE+"');");
		out.println("    var pageCount=document.getElementById('"+GridConstants.HIDDEN_TOTAL_PAGES+"').value;");
		out.println("    var pageNumber=objCurpage.value;");
		out.println("    if(pageNumber=='')");
		out.println("      pageNumber='1';");
		out.println("      ");
		out.println("  	 if(type=='frist')");
		out.println("  	 {");
		out.println("   	 if(pageNumber!='1')");
		out.println(" 		{");
		out.println("    		setSelectValue(objCurpage,1);");
		out.println("    		document.forms[0].submit();");
		out.println("  	     }");
		out.println("	 }");
		out.println("	else if(type=='previous')");
		out.println("	{");
		out.println("  		var intpageNumber=parseInt(pageNumber);");
		out.println("  		if(intpageNumber>1)");
		out.println("  		{");
		out.println("    		intpageNumber--;");
		out.println("    		setSelectValue(objCurpage,intpageNumber);");
		out.println("    		document.forms[0].submit();");
		out.println("  		}");
		out.println("	}  ");
		out.println("	else if(type=='next')");
		out.println("   {");
		out.println("     var intpageNumber=parseInt(pageNumber);");
		out.println("     var intpageCount=parseInt(pageCount);");
		out.println("     if(intpageCount>intpageNumber)");
		out.println("     {");
		out.println("       intpageNumber++;");
		out.println("    	setSelectValue(objCurpage,intpageNumber);");
		out.println("       document.forms[0].submit();");            
		out.println("     }");
		out.println("   }");
		out.println("   else if(type=='last')");
		out.println("   {");
		out.println("     if(objCurpage.value!=pageCount)");
		out.println("     {");
		out.println("       setSelectValue(objCurpage,pageCount);");
		out.println("       document.forms[0].submit();");
		out.println("      }");
		out.println("    }");
		out.println("    else if(type=='go')");
		out.println("    {");
		out.println("      document.forms[0].submit();");
		out.println("    }");
		out.println("    else if(type=='setsize')");
		out.println("    {");
		out.println("      setSelectValue(objCurpage,1);");
		out.println("      document.forms[0].submit();");
		out.println("      ");
		out.println("    }    ");            
		out.println("}  ");
	}
	
	public static void getCheckBoxSelectValue(JspWriter out) throws IOException
	{
		out.println("function getCheckValue(checkName)");
		out.println("{  ");
//		out.println("	alert(document.getElementsByName("+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+").value);");
		out.println("	var selvalues=''; ");
		out.println("	var m = document.getElementsByName(checkName)");
		out.println("	var len = m.length;");
		out.println("	for ( var i=0; i< len; i++)");
		out.println("	{");
		out.println("		if(m[i].disabled) {continue;}");
		
		out.println("   	if(m[i].checked)");
		out.println("    	{");
		out.println("    		if(selvalues=='')");
		out.println("    		{selvalues=m[i].value;}");
		out.println("			else {selvalues+=','+m[i].value}");
		out.println("    	}");
		out.println("    }");
		out.println("	 document.getElementsByName('"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"').value=selvalues;");
		out.println("	 //alert(document.getElementsByName('"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"').value);");
		out.println("}");		
	}
	public static void getCheckBoxSelectFunction(JspWriter out) throws IOException
	{
		out.println("var ischeck=false;");
		out.println("function checkAll(checkName)");
		out.println("{  ");
//		out.println("	alert(document.getElementsByName("+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+").value);");
		out.println("	//var selvalues=''; ");
		out.println("	var m = document.getElementsByName(checkName)");
		out.println("	var len = m.length;");
		out.println("	for ( var i=0; i< len; i++)");
		out.println("	{");
		out.println("	  if(m[i].disabled) {continue;}");
		
		out.println("     if(ischeck)");
		out.println("     {");
		
		out.println("     	 m[i].checked = false;");
		out.println("     }");
		out.println("     else");
		out.println("     {");
		out.println("        m[i].checked = true;");
		out.println("		 //selvalues+=m[i].value+',';");
		out.println("     }");
		out.println("	}");
		//out.println("	alert(selvalues);");
		out.println("	if(ischeck)");
		out.println("	{");
		out.println("		ischeck=false;");
		out.println("		//document.getElementsByName('"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"').value='';");
		out.println("	}");
		out.println("	else");
		out.println("	{");
		out.println("		ischeck=true;");
		out.println("		//document.getElementsByName('"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"').value=selvalues;");
		out.println("	}");
		out.println("}");
	}	
	
}
