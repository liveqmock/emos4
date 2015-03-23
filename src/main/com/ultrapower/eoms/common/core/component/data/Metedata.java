
package com.ultrapower.eoms.common.core.component.data;
/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-1-29
 */
public class Metedata 
{
   
   /**
   数据类型
    */
   private int DataType;
   private int FiledName;
   
   /**
   数据长度
    */
   private int DataLength;
   
   /**
   数据精度
    */
   private int DataPrecision;
   
   /**
   是否允许为空 Y 允许为空   N 不允许为空
    */
   private String NullLable;
   
   /**
   @roseuid 4B56BE30026B
    */
   public Metedata() 
   {
    
   }
}
