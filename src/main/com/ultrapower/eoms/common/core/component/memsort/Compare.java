package com.ultrapower.eoms.common.core.component.memsort;
public class Compare extends Comparator{
 
	@Override
	public  int compare(Object obj1, Object obj2) {
		// TODO Auto-generated method stub
		int t1=Integer.parseInt(obj1.toString());
		int t2=Integer.parseInt(obj2.toString());
		int result=0;
		if(t1>t2) 
			result=1;
		else if(t1==t2)
		   result=0;
		else
			result= -1;
		return result;
	}
	
	

}
