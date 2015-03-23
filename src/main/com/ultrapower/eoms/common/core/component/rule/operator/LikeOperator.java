package com.ultrapower.eoms.common.core.component.rule.operator;

import com.ultrapower.eoms.common.core.component.rule.RuleConstants;

public class LikeOperator extends Operator {

	public LikeOperator()
	{
		super("like", 4);
	}
	
	public String evaluate(final String leftOperand, final String rightOperand) 
	{
		String str1=leftOperand;
		String str2=rightOperand;
		if(str1.charAt(0)=='\'' &&  str1.charAt(str1.length()-1)=='\'' )
		{
			str1=str1.substring(1,str1.length()-1);
		}
		if(str2.charAt(0)=='\'' &&  str2.charAt(str2.length()-1)=='\'' )
		{
			str2=str2.substring(1,str2.length()-1);
		}
		
		if(str2.charAt(0)=='%' && str2.charAt(str2.length()-1)=='%')
		{
			str2=str2.substring(1,str2.length()-1);
			if(str1.contains(str2))
			{
				return RuleConstants.BOOLEAN_STRING_TRUE;
			}
		}
		else if(str2.charAt(str2.length()-1)=='%')
		{
			str2=str2.substring(0,str2.length()-1);
			if(str1.indexOf(str2)==0)//出现在第一位则表示左包含
			{
				return RuleConstants.BOOLEAN_STRING_TRUE;
			}
		}
		else if(str2.charAt(0)=='%')
		{
			str2=str2.substring(1);
			int lastIndex=str1.lastIndexOf(str2);
			
			if(lastIndex>-1)
			{
				if(lastIndex+str2.length()==str1.length())
				//if(str1.contains(str2))
				{
					return RuleConstants.BOOLEAN_STRING_TRUE;
				}
			}
		}
		else
		{
			if (leftOperand.compareTo(rightOperand) == 0) 
			{
				return RuleConstants.BOOLEAN_STRING_TRUE;
			}
		}
		return RuleConstants.BOOLEAN_STRING_FALSE;
	}
	
	
	public static void main(String[] args)
	{
//	
//		String str3="aaaaaaaaaaaa";
//		String str2="aa";
//		
//		System.out.println(str3.contains(str2));
//		System.out.println(str3.indexOf(str2));
//		System.out.println(str3.lastIndexOf(str2));
		
		LikeOperator LikeOperator=new LikeOperator();
		String str1=LikeOperator.evaluate("'aaaa1'", "'a1%'");
		System.out.println(str1);
	   	
	}
	
}
