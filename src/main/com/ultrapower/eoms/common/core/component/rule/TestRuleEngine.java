package com.ultrapower.eoms.common.core.component.rule;

import com.ultrapower.eoms.common.core.support.SpringGenericTest;

public class TestRuleEngine extends SpringGenericTest {
 
	public static void main(String[] args)
	{
		
		RuleEngine rule=new RuleEngine();
		String result;
		//result=rule.getResult("('a'='b' and 2=3 or (1=1 and 2=2)) and 2<3");
		//System.out.println("r1:"+result);
		try {
			//result=rule.getResult("( ('ac''>='ac') and (1<=2) and 1>=1 )");
			//result=rule.execute("'a=a and b'>='bc' ");
			String expression="(3>9 or 3>1) and (1>1 or 1=1))";
			
			///expression="'ac'='ac'";
			//result=rule.execute(expression);
			//expression="3=2 or (1=1  and toUpperCase('def') = toUpperCase('def') or 1=1)";
			//expression="toUpperCase('esawq21') + toUpperCase('ddd')+'ddd'a'  and 1=1 ";
			expression="2=2";
			result=rule.execute(expression);
			
			System.out.println("计算结果: "+result);    
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
