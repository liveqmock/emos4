package com.ultrapower.eoms.common.core.component.memsort;

import com.ultrapower.eoms.common.core.component.data.DataRow;

public class DtComparator extends Comparator{

	String[] filed;
	public DtComparator(String[] filed)
	{
		this.filed=filed;
	}
	@Override
	public int compare(Object obj1, Object obj2) {
		// TODO Auto-generated method stub
		DataRow dt1=(DataRow)obj1;
		DataRow dt2=(DataRow)obj2;
		int result=0;
		StringBuffer str1=new StringBuffer();
		StringBuffer str2=new StringBuffer();
		for(int i=0;i<filed.length;i++)
		{
			str1.append(dt1.getString(filed[i]));
			str2.append(dt2.getString(filed[i]));
		}
		result=str1.toString().compareTo(str2.toString());
		return result;
	}
	
	public static void main (String[] args)
	{
		String str1="10";
		String str2="2";
		System.out.println(str1.toString().compareTo(str2.toString()));
	}

}
