package com.ultrapower.eoms.common.core.component.rquery.core;

public  class EnumUtil {

	/**
	 * 枚举大小写不敏感的字段
	 */
    public enum NonSensitiveFieldEnum {
    	workSheetTitle, requestUserLoginName, contactoa    
    }
    
    
    public static void main(String[] args) {
		for (NonSensitiveFieldEnum nfEnum :NonSensitiveFieldEnum.values()) {
			System.out.println(nfEnum);
		}
	}
}
