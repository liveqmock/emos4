package com.ultrapower.eoms.ultrasla.service;

import com.ultrapower.eoms.ultrasla.exception.UnInitializationException;
import com.ultrapower.eoms.ultrasla.model.EventAction;
import com.ultrapower.eoms.ultrasla.model.EventParam;
import com.ultrapower.eoms.ultrasla.util.UltraSLAEvent;

/**
 * 事件规则解析
 * @author Administrator
 *
 */
public interface IEventRuleParser 
{
   
   /**
    * 解析
    * @param slaEvent
    */
   public void parse(UltraSLAEvent slaEvent);
   
   /**
    * 保存至事件动作
    * @param eventAction
    */
   public void saveToEventAction(EventAction eventAction) throws UnInitializationException;
   
   /**
    * 保存至事件参数
    * @param eventParam
    * @throws UnInitializationException
    */
   public void saveToEventParam(EventParam eventParam) throws UnInitializationException;
}
