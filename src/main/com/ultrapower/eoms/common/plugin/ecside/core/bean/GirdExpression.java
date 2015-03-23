package com.ultrapower.eoms.common.plugin.ecside.core.bean;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;


public class GirdExpression {
	private Context cx = Context.enter();
	private Scriptable scope = cx.initStandardObjects();
	private Scriptable scriptObject= cx.newObject(scope);
	
	private String argumentNames;
	
	private StringBuffer functionCode=new StringBuffer();

	
	private Function functionCore;
	
	public GirdExpression() {}
	public GirdExpression(String argumentNames) {
		setArgumentNames(argumentNames);
	}
	
	public void setArgument(String argumentName, Object argumentValue){
		scriptObject.put(argumentName, scriptObject,argumentValue);
	}
	
	public void build(String expression){
		build(argumentNames,expression);
	}
	
	public void build(String argumentNames, String expression){
		this.argumentNames=argumentNames;
		functionCode.append("function expressionFunction(").append(argumentNames).append(") { ");
		
		if (expression.toLowerCase().indexOf("return ")<0){
			functionCode.append(" return ( ").append(expression).append(" ); }");
		}else{
			functionCode.append(expression).append(" }");
		}
		
		functionCore = cx.compileFunction(scope,functionCode.toString(), "exfunction", 1,null);

	}
	
	public Object call(){
//		scriptObject.put("a", scriptObject,new Integer(0));
//		scriptObject.put("b", scriptObject,new Integer(1));
//		scriptObject.put("c", scriptObject,new Integer(2));
		
		return functionCore.call(cx, scope, scope, new Object[]{scriptObject});
	}


	public String getArgumentNames() {
		return argumentNames;
	}

	public void setArgumentNames(String argumentNames) {
		this.argumentNames = argumentNames;
	}
	
	public static void testJSFunction(){
		Context cx = Context.enter();
		Scriptable scope = cx.initStandardObjects();
		
		String expression;
		Object result ;
		
		
		expression="function test(col) { return (10+ col.a + col.b + col.c )/3; }";


		Function testFunction1 = cx.compileFunction(scope,expression, "cell", 1,null);
		Scriptable col= cx.newObject(scope);
		
		long t=System.currentTimeMillis();
		for (int i=0;i<20000;i++){
			col.put("a", col,new Integer(i));
			col.put("b", col,new Integer(i+1));
			col.put("c", col,new Integer(i+2));
			
            result = testFunction1.call(cx, scope, scope, new Object[]{col});
			;
		}
		;

	}
	
	public static void main(String[] args) {
		
		testJSFunction();

	}
	public Scriptable getScriptObject() {
		return scriptObject;
	}
}
