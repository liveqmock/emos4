package com.ultrapower.eoms.common.core.component.memsort;

import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import java.util.Arrays; 

/**
 * 快速排序算法，将数组内的数据进行排序
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-8-18
 */
public class QuickSort {
	/*
	 * 排序比较类
	 */
/*	public static abstract class Comparator
	{
		*//**
		 * 进行排序比较，obj1大于obj2返回1(正序,obj1<obj2返回1则为逆序), obj1等于obj2返回 0 否则返回-1
		 * @param obj1
		 * @param obj2
		 * @return
		 *//*
		public abstract int compare(Object obj1, Object obj2);
	}*/

	private Comparator mComparator;

	public QuickSort(Comparator cp) 
	{
		mComparator = cp;
	}

   /*
    * 快速排序算法，对数组对象进行快速排序 
    * @param a       an Object array
    * @param lo0     left boundary of array partition
    * @param hi0     right boundary of array partition
    */
	private void qsort(Object[] a, int lo0, int hi0) 
	{
		int lo = lo0;
		int hi = hi0;
		if ( hi0 > lo0) 
		{
			/**
			 * 取中间数据为枢纽元
			 */
			Object mid = a[ ( lo0 + hi0 ) / 2 ];

			//循环左右指针，直到相遇
			while ( lo <= hi ) 
			{
				/*用左指针查找第一个大于或等于中间变量的数据的位置
				 */
				while (( lo < hi0 ) && ( mComparator.compare(a[lo], mid) < 0 ))
				{
					++lo;
				}
				/*
				 * 用右指针查找第一个大于或等于中间变量的数据的位置
				 */
				while (( hi > lo0 ) && ( mComparator.compare(a[hi], mid) > 0 ))
				{
					--hi;
				}
				//如果左右指针未交叉则进行数据交换
				if ( lo <= hi ) 
				{
					swap(a, lo, hi);
					++lo;
					--hi;
				}
			}
			/*
			 * 对左区进行再次排序
			 */
			if ( lo0 < hi )
				qsort( a, lo0, hi );
			/*
			 * 对右区进行再次排序
			 */
			if ( lo < hi0 )
				qsort( a, lo, hi0 );
		}
	}
	/**
	 * 数据交换
	 * @param a
	 * @param i
	 * @param j
	 */
	private static void swap(Object[] a, int i, int j)
	{
		Object temp = a[i]; 
		a[i] = a[j];
		a[j] = temp;
	}
	
	/**
	 * 对数组对像进行排序
	 * @param ary
	 */
	public void sort(Object[] ary) 
	{
		if(ary!=null)
		{
			qsort(ary, 0, ary.length - 1);
		}
	}
	
	public void sort(DataTable dt) 
	{
		List lst=dt.getRowList();
		Object[] ary=lst.toArray();
		sort(ary);
		lst=Arrays.asList(ary);
		dt.setRowList(lst);
	}
	/**
	 * 对数组对像进行排序，指定进行排序对象的个数
	 * @param ary
	 * @param length
	 */
	public void sort(Object[] ary, int length) 
	{
		
		if(ary!=null)
		{
//			Object[] a=ary.clone();
			qsort(ary, 0, length - 1);
//			return a;
		}
		
	}
	
	public static void main(String[] args)
	{
		Compare comp=new Compare();
		QuickSort qsort=new QuickSort(comp);
		//Object a[]={8,1,3,6,4,9,2,100};
		Object a[]={3,1,2};
		//qsort.sort(a);
/*		for(int i=0;i<a.length;i++)
		{
			System.out.print(" "+a[i].toString());
		}*/
		DataTable dt=new DataTable("table");
		DataRow newRow=new DataRow();
		newRow.put("a1", "江苏");
		newRow.put("a2", "400");
		dt.putDataRow(newRow);
		
		newRow=new DataRow();
		newRow.put("a1", "北京");
		newRow.put("a2", "5");
		dt.putDataRow(newRow);

		newRow=new DataRow();
		newRow.put("a1", "重庆");
		newRow.put("a2", "3");
		dt.putDataRow(newRow);
		
		newRow=new DataRow();
		newRow.put("a1", "江苏");
		newRow.put("a2", "3");
		dt.putDataRow(newRow);
		
		newRow=new DataRow();
		newRow.put("a1", "北京");
		newRow.put("a2", "3");
		dt.putDataRow(newRow);		

		String[] filed={"a1","a2"};
		DtComparator comp2=new DtComparator(filed);
		QuickSort qsort2=new QuickSort(comp2);
		qsort2.sort(dt);
		
		System.out.println(dt.getXmlString());

		
	}	
}
