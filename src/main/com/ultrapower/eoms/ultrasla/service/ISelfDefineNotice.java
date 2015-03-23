package com.ultrapower.eoms.ultrasla.service;

import java.util.Map;

/**
 * 自定义通知接口
 * @author Administrator
 *
 */
public interface ISelfDefineNotice 
{
   
   /**
    * 自定义通知处理
    * @param eventid 事件id
    * @param param 客户端传递的参数
    */
   public void doTask(Map param, String eventid);
}