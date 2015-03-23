package com.ultrapower.eoms.common.core.component.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.ultrapower.eoms.common.core.component.rule.analysis.AnalyseChar;
import com.ultrapower.eoms.common.core.component.rule.analysis.NextOperator;
import com.ultrapower.eoms.common.core.component.rule.function.Function;
import com.ultrapower.eoms.common.core.component.rule.function.FunctionException;
import com.ultrapower.eoms.common.core.component.rule.function.sqlstring.Like;
import com.ultrapower.eoms.common.core.component.rule.function.string.ToUpperCase;
import com.ultrapower.eoms.common.core.component.rule.operator.IOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.LeftParentheses;
import com.ultrapower.eoms.common.core.component.rule.operator.RightParentheses;

public class SqlParseRule {

	//操作符
	private List operators=null;
	private HashMap functions=null;
	private char quoteCharacter=RuleConstants.SINGLE_QUOTE;
	Queue<String> queue = new LinkedList<String>();
	
	public SqlParseRule()
	{
		this.init();
	}
	private NextOperator getNextOperator(String exp,int start)
	{
		NextOperator nextOperator=null;
		int expLen=exp.length();
		final int numOperators = operators.size();
		IOperator operator;
		int endCtr=0;
		int numQuoteCharacters=0;
		for(int charCtr=start;charCtr<expLen;charCtr++)
		{
			if (exp.charAt(charCtr) == quoteCharacter) {
				numQuoteCharacters++;
			}

			// Do not look into open strings.
			if ((numQuoteCharacters % 2) == 1) {
				continue;
			}			
			
			for(int i=0;i<numOperators;i++)
			{
				operator=(IOperator)this.operators.get(i);
				if(operator.getLength()>1)
				{
					endCtr=charCtr+operator.getLength();
					if(endCtr<=expLen)
					{
						if(exp.substring(charCtr,endCtr).equals(operator.getSymbol()))
						{
							nextOperator=new NextOperator(operator,charCtr);
							return nextOperator;
						}
					}
				}
				else
				{
					
					if (exp.charAt(charCtr) == operator.getSymbol().charAt(0))
					{
						nextOperator=new NextOperator(operator,charCtr);
						//System.out.println(exp.charAt(charCtr));
						return nextOperator;
					}
				}
				
			}
		}
		return nextOperator;
	}
	
	private int processFunction(final String sql,int endindex,int preindex) throws RuleException
	{
		String result="";
		int parenthesisCount = 1;
		NextOperator nextOperator = null;
		int nextOperatorIndex = endindex;
		
		int start=endindex;
		boolean isfrist=true;
		while(start>0)
		{
			if(AnalyseChar.isSpace(sql.charAt(start)))
			{
				if(!isfrist)
					break;
			}
			else
			{
				isfrist=false;
			}
			
			start--;
		}
		queue.offer(sql.substring(preindex,start));
		final String functionName = sql.substring(start,endindex).trim();
		
		// 查找函数，直到函数的结束符 右括号
		while (parenthesisCount > 0) {
			nextOperator = getNextOperator(sql, endindex + 1);

			if (nextOperator == null) {
				throw new RuleException("Function is not closed.");
			} else if (nextOperator.getOperator() instanceof LeftParentheses) {
				parenthesisCount++;
			} else if (nextOperator.getOperator() instanceof RightParentheses) {
				parenthesisCount--;
			}
			nextOperatorIndex=nextOperator.getIndex();
		}
		// 获得函数参数
		String arguments = sql.substring(endindex+1,nextOperatorIndex);
		
		final Function function = (Function) functions.get(functionName);
		try {
			result=function.execute(arguments).getResult();
			queue.offer(result);
		} catch (FunctionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nextOperatorIndex++;
		return  nextOperatorIndex;
	}
	public String parseSql(final String sql)
	{
		String result="";
		int expLen=sql.length();
		int charAt=0;
		int operandIndex;
		NextOperator nextOperator;
		boolean isSpace=false;
		//int spaceAt=0;//最近空格的位置
		int preSplit=0;//上一次截取字符串的位置
		IOperator objOperator;
		
		
		while(charAt<expLen)
		{
			if(AnalyseChar.isSpace(sql.charAt(charAt)))
			{
				isSpace=true;
				
				charAt++;
				continue;
			}
			operandIndex=-1;
			nextOperator=getNextOperator(sql,charAt);
			if(nextOperator!=null && (isSpace||preSplit==0))
			{
				objOperator=nextOperator.getOperator();
				if(objOperator instanceof LeftParentheses)
				{
					queue.offer(sql.substring(preSplit,charAt));
					operandIndex=nextOperator.getIndex();
					try{
						//String str=
						charAt=processFunction(sql,operandIndex,preSplit);
						
						//charAt=operandIndex;
						preSplit=charAt;//记录截取位置
						//spaceAt=charAt;
					}
					catch(Exception ex)
					{
						
					}
				}
			}
			else
			{
				queue.offer(sql.substring(preSplit));
				break;
			}
			isSpace=false;
			//charAt++;
		}
		
        String str;   
        while((str=queue.poll())!=null){   
        	result+=str;   
        } 
		if(result.equals(""))
			result=sql;
		return result;
	}
	private void init()
	{
		operators=new ArrayList();
		
		operators.add(new LeftParentheses());
		operators.add(new RightParentheses());
		
		functions=new HashMap();
		
		functions.put("like",new Like());
	}
	
	public static void main(String[] args)
	{
		
		SqlParseRule sqlParseRule=new SqlParseRule();
		System.out.println(sqlParseRule.parseSql("  1=1 like (${and},${basen},${'09'})   like (title,005)"));
		
     /*   Queue<String> queue = new LinkedList<String>();   
        queue.offer("Hello");   
        queue.offer("World!");   
        queue.offer("你好！");   
        System.out.println(queue.size());   
        String str;   
        while((str=queue.poll())!=null){   
            System.out.print(str);   
        }   
        System.out.println();   
        System.out.println(queue.size());*/  
	}
}
