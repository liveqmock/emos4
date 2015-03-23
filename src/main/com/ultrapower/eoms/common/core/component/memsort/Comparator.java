package com.ultrapower.eoms.common.core.component.memsort;

public abstract class Comparator {
	/**
	 * 进行排序比较，obj1大于obj2返回1(正序,obj1<obj2返回1则为逆序), obj1等于obj2返回 0 否则返回-1
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public abstract int compare(Object obj1, Object obj2);
}
