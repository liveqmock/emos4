package com.ultrapower.eoms.common.core.component.rule.function.sqlstring;

import com.ultrapower.eoms.common.core.util.StringUtils;

import com.ultrapower.eoms.common.core.component.rule.function.Function;
import com.ultrapower.eoms.common.core.component.rule.function.FunctionConstants;
import com.ultrapower.eoms.common.core.component.rule.function.FunctionException;
import com.ultrapower.eoms.common.core.component.rule.function.FunctionResult;

public class Like implements Function{

	public FunctionResult execute(String arguments) throws FunctionException {
		// TODO Auto-generated method stub
		String args=StringUtils.checkNullString(arguments);
		String like="";
		if(!args.equals(""))
		{
			String[] strary=getVar(arguments);//arguments.split(",");
			if(strary.length==3||strary.length==2)
			{
				like=" "+strary[0]+" like '%"+strary[1]+"%'";
			}
			if(arguments.charAt(0)=='\'' &&  arguments.charAt(arguments.length()-1)=='\'' )
			{
				arguments=arguments.substring(1,arguments.length()-1);
			}
			
			return new FunctionResult(like, 
					FunctionConstants.FUNCTION_RESULT_TYPE_STRING);
		}
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "like";
	}
	
	private String checkLike(String arguments)
	{
		String result="";
		
		return result;
		
	}
	
	public static String[] getVar(String arguments)
	{
		
		if(StringUtils.checkNullString(arguments).equals(""))
		{
			return null;
		}
		int tempLen=0;
		String strary[]=arguments.split("},");
		for(int i=0;i<strary.length;i++)
		{
			if(!strary[i].equals(""))
			{
				if(strary[i].indexOf("${")==0)
				{
					strary[i]=strary[i].replaceFirst("\\$\\{", "");
					if((strary[i].indexOf("}")+1)==strary[i].length())
					{
						strary[i]=strary[i].substring(0,strary[i].length()-1);
						String s[]=strary[i].split(",");
						if(s.length>0)
							strary[i]="";
						for(int j=0;j<s.length;j++)
						{
							tempLen=s[j].length();
							if(s[j].charAt(0)=='\'' &&  s[j].charAt(tempLen-1)=='\'' )
							{
								s[j]=s[j].substring(0,tempLen-1);
							}
							if(strary[i].equals(""))
								strary[i]=s[j];
							else
								strary[i]=","+s[j];
						}
					}
				}
			}
		}
		return strary;
	}
	
	public static void main(String[] args)
	{
		String str1="${'aa','nnbvc'},${bbb,dd,ee,ff},${ccc}";
		String strary[]=getVar(str1);
		//String strary[]=str1.split("},");
/*		for(int i=0;i<strary.length;i++)
		{
			if(!strary[i].equals(""))
			{
				int inti=strary[i].indexOf("${");
				if(strary[i].indexOf("${")==0)
				{
					strary[i]=strary[i].replaceFirst("\\$\\{", "");
					inti=strary[i].indexOf("}");
					inti=strary[i].length();
					if((strary[i].indexOf("}")+1)==strary[i].length())
					{
						strary[i]=strary[i].substring(0,strary[i].length()-1);
					}
				}
				//strary[i]=strary[0];
			}
		}*/
		
		for(int i=0;i<strary.length;i++)
		{
			System.out.println(i+": "+ strary[i]);
		}
	}

}
