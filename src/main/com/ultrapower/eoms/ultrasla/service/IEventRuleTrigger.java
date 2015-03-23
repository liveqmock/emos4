
package com.ultrapower.eoms.ultrasla.service;

import java.util.List;
import java.util.Map;

/**
 * 事件规则触发接口
 * @author Administrator
 *
 */
public interface IEventRuleTrigger 
{
   
   /**
    * 产生事件
    * @param eventId
    * @param ruleIdLst
    * @param dueTime
    * @param defaultUser
    * @param defaultDept
    * @param defaultRole
    * @param params
    * @return 10：成功；11：JMS异常；12：其他未知异常；13：非法触发参数；14：messageSender未初始化
    */
   public int produce(String eventId, List<String> ruleIdLst, Long dueTime, List<String> defaultUser, List<String> defaultDept, List<String> defaultRole, Map params);
   
   /**
    * 销毁事件
    * @param eventId
    * @return
    */
   public int destory(String eventId);
}
