package com.ultrapower.eoms.common.core.component.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.ultrapower.eoms.common.core.component.rule.function.string.ToUpperCase;

import com.ultrapower.eoms.common.core.component.rule.analysis.AnalyseChar;
import com.ultrapower.eoms.common.core.component.rule.analysis.NextOperator;
import com.ultrapower.eoms.common.core.component.rule.function.Function;
import com.ultrapower.eoms.common.core.component.rule.function.FunctionException;
import com.ultrapower.eoms.common.core.component.rule.function.FunctionResult;
import com.ultrapower.eoms.common.core.component.rule.node.NodeOperand;
import com.ultrapower.eoms.common.core.component.rule.node.NodeOperator;
import com.ultrapower.eoms.common.core.component.rule.node.NodeTree;
import com.ultrapower.eoms.common.core.component.rule.operator.AddtionOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.AndOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.DivisionOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.EqualOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.GreaterThan;
import com.ultrapower.eoms.common.core.component.rule.operator.GreaterThanOrEqual;
import com.ultrapower.eoms.common.core.component.rule.operator.IOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.LeftParentheses;
import com.ultrapower.eoms.common.core.component.rule.operator.LessThan;
import com.ultrapower.eoms.common.core.component.rule.operator.LessThanOrEqual;
import com.ultrapower.eoms.common.core.component.rule.operator.LikeOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.MultiplicationOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.NotEqualOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.OrOperator;
import com.ultrapower.eoms.common.core.component.rule.operator.RightParentheses;
import com.ultrapower.eoms.common.core.component.rule.operator.SubtractionOperator;


/**
 * 规则引擎
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-3-25
 */
public class RuleEngine {

	//操作符
	private List operators=null;
	//操作符堆栈
	private Stack preOperatorStack=null;
	//操作数堆栈
	private Stack preOperandStack=null;
	
	private HashMap functions=null;
	private HashMap hashVariable=null;
	Stack operandStack;
	Stack operatorStack;	
	
	private char quoteCharacter=RuleConstants.SINGLE_QUOTE;

	public char getQuoteCharacter()
	{
		return quoteCharacter;
	}
	
	public RuleEngine(char quote)
	{
		this(null);
		quoteCharacter=quote;
		
	}
	
	public RuleEngine()
	{
		this(null);
	}
	public RuleEngine(HashMap hashVariable)
	{
		this.init();
	}
	
	
	/**
	 * 返回结果
	 * @param expression
	 * @return
	 * @throws RuleException 
	 */
	public String execute(String expression) throws RuleException
	{
		String result="";
		
		try{
			parseExpression(expression);
			if(operandStack.size()<=0)
			{
				return expression;
			}			
			result=calResult(operandStack,operatorStack);
		}catch(RuleException ex)
		{
			throw new RuleException(ex.getMessage()+"  整体表达式: "+expression); 
		}
		return result;
	}
	/**
	 * 解析规则表达式,将解析完成的操作符号存入堆栈中
	 * @param exp
	 * @throws RuleException 
	 */
	private void parseExpression(final String exp) throws RuleException
	{
		
		operandStack=new Stack();
		operatorStack=new Stack();
		
		int expLen=exp.length();
		int charAt=0;
		NextOperator nextOperator;
		
		IOperator objOperator;
		int operandIndex;
		boolean haveOperand = false;
		boolean haveOperator = false;		
		//遍历表达式字符
		while(charAt<expLen)
		{
			//haveOperand = false;
			//haveOperator = false;			
			objOperator=null;
			if(AnalyseChar.isSpace(exp.charAt(charAt)))
			{
				charAt++;
				continue;
			}
			operandIndex=-1;
			nextOperator=getNextOperator(exp,charAt);
			if(nextOperator!=null)
			{
				objOperator=nextOperator.getOperator();
				operandIndex=nextOperator.getIndex();
			}
			
			//如果不是 or 等操作符号
			if(operandIndex>charAt|| operandIndex==-1 )
			{
				charAt=presssOperand(exp,charAt,operandIndex,operandStack);
				haveOperand = true; //有操作数
				haveOperator = false;				
			}
			
			if(charAt==operandIndex)
			{
				//如果有操作符号
				charAt=pressOperator(exp,charAt,operandStack,operatorStack,objOperator,haveOperand);
				haveOperand=false;
				haveOperator = true;
			}
			
		}
		preOperandStack=(Stack)operandStack.clone();
		preOperatorStack=(Stack)operatorStack.clone();
		
	}
	
	private String calResult(final Stack operandStack,final Stack operatorStack) throws RuleException
	{
		String result="";
		while (operatorStack.size() > 0) 
		{
			pressNodeTree( operatorStack,operandStack);
		}
		if (operandStack.size() != 1) {
			throw new RuleException("表达式无效(calResult)");
		}
		final Object operand=operandStack.pop();
		if (operand instanceof NodeTree) {
			// Get the final result.
			result = ((NodeTree) operand).calOperand();
		}
		else if(operand instanceof NodeOperand)
		{
			result=((NodeOperand)operand).getOperand();
		}
		else if (operand instanceof ParsedFunction) {
			final ParsedFunction parsedFunction = (ParsedFunction) operand;
			final Function function = parsedFunction.getFunction();
			String arguments = parsedFunction.getArguments();
			FunctionResult fncresult;
			try {
				fncresult = function.execute(arguments);
				if(fncresult!=null)
					result = fncresult.getResult();
			} catch (FunctionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}
	
	/**
	 * 将操作数压入堆栈
	 * @param exp
	 * @param operatorIndex
	 * @param charAt
	 * @param operandStack
	 * @throws RuleException 
	 */
	private int presssOperand(final String exp,int charAt,int operatorIndex,Stack operandStack) throws RuleException
	{
		int result=-1;
		String operand="";
		if(operatorIndex==-1)
		{
			//最后一个操作数
			operand=exp.substring(charAt).trim();
			result=exp.length();
			
		}
		else
		{
			operand=exp.substring(charAt,operatorIndex).trim();
			result=operatorIndex;
		}
		
		if(operand.length()>1)
		{
			char bgchar=operand.charAt(0);
			char edchar=operand.charAt(operand.length()-1);
			
			if(bgchar==this.quoteCharacter || edchar==this.quoteCharacter)
			{
				if(bgchar!=edchar)
				{
					throw new RuleException("操作数错误,缺少引用符号："+operand);
				}
			}
		}
		
		NodeOperand nodeOperand=new NodeOperand(operand);
		operandStack.push(nodeOperand);		
		return result;
	}
	
	/**
	 * 将操作符号压入堆栈
	 * @param charAt
	 * @param operandStack
	 * @param operatorStack
	 * @param operator
	 * @return
	 * @throws RuleException 
	 */
	private int pressOperator(final String expression, int charAt,
			Stack operandStack, Stack operatorStack, IOperator operator,
			boolean haveOperand) throws RuleException
	{
		int result=charAt;
		NodeOperator objNodeOperator=null;
		
		//如果有操作数并且 操作符是左括号
		if (haveOperand && operator instanceof LeftParentheses) 
		{
			NextOperator nextOperator = processFunction(expression,charAt, operandStack);
			charAt=nextOperator.getIndex()+nextOperator.getOperator().getLength();
			
			nextOperator = getNextOperator(expression, charAt);

			// Look to see if there is another operator.
			// If there is, the process it, else get out of this routine.
			if (nextOperator != null) {
				operator = nextOperator.getOperator();
				charAt = nextOperator.getIndex();
				result=charAt;
			} else {
				return charAt;
			}
			
		}
		
		if(operator instanceof LeftParentheses)
		{
			final NodeOperator nodeOperator=new NodeOperator(operator);
			operatorStack.add(nodeOperator);
		}
		else if(operator instanceof RightParentheses)
		{
			if (operatorStack.size() > 0) 
			{
				objNodeOperator = (NodeOperator) operatorStack.peek();
			}
			while(objNodeOperator!=null && !(objNodeOperator.getOperator() instanceof LeftParentheses))
			{
				pressNodeTree(operatorStack,operandStack);
				if (operatorStack.size() > 0) {
					objNodeOperator = (NodeOperator) operatorStack.peek();
				} else {
					objNodeOperator = null;
				}				
			}
			
			if(operatorStack.isEmpty())
			{
//				System.out.println("表达式无效");
				throw new RuleException("表达式无效(pressOperator)");
				
			}
			objNodeOperator = (NodeOperator) operatorStack.pop();
			if(!(objNodeOperator.getOperator() instanceof LeftParentheses))
			{
//				System.out.println("表达式无效");
				throw new RuleException("表达式无效(pressOperator)");
			}
		}
		else
		{
			if(operatorStack.size()>0)
			{
				objNodeOperator=(NodeOperator)operatorStack.peek();
				while(objNodeOperator!=null&& objNodeOperator.getOperator().getPrecedence()>=operator.getPrecedence())
				{
					pressNodeTree(operatorStack,operandStack);
					if(operatorStack.size()>0)
					{
						objNodeOperator=(NodeOperator)operatorStack.peek();
					}
					else
					{
						objNodeOperator=null;
					}
					
				}
				
			}
			final NodeOperator nodeOperator=new NodeOperator(operator);
			operatorStack.add(nodeOperator);
		}
		result+=operator.getLength();
		return result;
	}
	
	
	private void pressNodeTree(Stack operatorStack,Stack operandStack)
	{
		Object leftOperand=null;
		Object rightOperand=null;
		IOperator operator=null;

		if(operandStack.size()>0)
		{
			rightOperand=operandStack.pop();
		}
		
		if(operandStack.size()>0)
		{
			leftOperand=operandStack.pop();
		}		
		
		operator=((NodeOperator)operatorStack.pop()).getOperator();
		
		final NodeTree nodeTree=new NodeTree(leftOperand,rightOperand,operator);
		
		operandStack.push(nodeTree);
		
	}
	
	/**
	 * 查找下一个操作符号
	 * @return
	 */
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
	
	/**
	 * 解析字符串中的函数
	 * @param expression
	 * @param operatorIndex
	 * @param operandStack
	 * @return
	 * @throws RuleException
	 */
	private NextOperator processFunction(final String expression,
			final int operatorIndex, final Stack operandStack)
			throws RuleException {
		NextOperator nextOperator = null;
		int parenthesisCount = 1;
		int nextOperatorIndex = operatorIndex;

		// 查找函数，直到函数的结束符 右括号
		while (parenthesisCount > 0) {
			nextOperator = getNextOperator(expression, nextOperatorIndex + 1);

			if (nextOperator == null) {
				throw new RuleException("Function is not closed.");
			} else if (nextOperator.getOperator() instanceof LeftParentheses) {
				parenthesisCount++;
			} else if (nextOperator.getOperator() instanceof RightParentheses) {
				parenthesisCount--;
			}
			// Get the next operator index.
			nextOperatorIndex = nextOperator.getIndex();
		}
		// 获得函数参数
		String arguments = expression.substring(operatorIndex + 1,nextOperatorIndex);
		
		final NodeOperand operand = (NodeOperand) operandStack.pop();
		//函数名称
		final String functionName = operand.getOperand();
		
		final Function function = (Function) functions.get(functionName);
		final ParsedFunction parsedFunction = new ParsedFunction(function,arguments);
		operandStack.push(parsedFunction);
		return nextOperator;
		  
	}
	
	/**
	 * 初始化操作符
	 */
	private void init()
	{
		operators=new ArrayList();
		
		
		operators.add(new AddtionOperator());
		operators.add(new SubtractionOperator());
		operators.add(new DivisionOperator());
		operators.add(new MultiplicationOperator());
		operators.add(new LeftParentheses());
		operators.add(new RightParentheses());
		operators.add(new AndOperator());
		operators.add(new OrOperator());
		operators.add(new EqualOperator());
		
		operators.add(new NotEqualOperator());
		
		operators.add(new GreaterThanOrEqual());
		operators.add(new GreaterThan());
		
		operators.add(new LessThanOrEqual());
		operators.add(new LessThan());
		operators.add(new LikeOperator());
		
		functions=new HashMap();
		
		functions.put("toUpperCase", new ToUpperCase());
		
				
	}
}
