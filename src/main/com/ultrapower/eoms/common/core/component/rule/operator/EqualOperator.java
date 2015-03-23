package com.ultrapower.eoms.common.core.component.rule.operator;

import com.ultrapower.eoms.common.core.component.rule.RuleConstants;

public class EqualOperator extends Operator {
	
	public EqualOperator()
	{
		super("=",4);
	}

	public double evaluate(final double leftOperand, final double rightOperand)
	{
		if (leftOperand == rightOperand) {
			return 1;
		}
		return 0;
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
		if (str1.compareTo(str2) == 0) 
		{
			return RuleConstants.BOOLEAN_STRING_TRUE;
		}		
//		if (leftOperand.compareTo(rightOperand) == 0) {
//			return RuleConstants.BOOLEAN_STRING_TRUE;
//		}

		return RuleConstants.BOOLEAN_STRING_FALSE;
	}	
}
