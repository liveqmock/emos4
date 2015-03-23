package com.ultrapower.eoms.common.core.component.rule;


public class RuleExpression {

	public static String execute(final String exp) throws RuleException
	{
		String result="";
		RuleEngine rule=new RuleEngine();
		result=rule.execute(exp);
		double d=-1;
		if(result.equals("1.0"))
		{
			result="true";
		}
		else if(result.equals("0.0"))
		{
			result="false";
		}
/*		try{
			d=Double.parseDouble(result);
		}catch(Exception ex){}
		if(d>-1)
		{
			if(d>0)
				result="true";
			else
				result="false";
		}	
*///		if()
		return result;
	}
	
	public static void main(String[] args)
	{
		String result="";
		
		try {
			result=execute("'1'='1'");
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
	}
}
