package com.ultrapower.eoms.ultrasla.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrasla.model.EventRule;

/**
 * 事件规则服务接口
 * @author Administrator
 *
 */
public interface IEventRuleService 
{
   
   /**
    * 根据ID获得事件规则
    * @param id
    * @return
    */
   public EventRule getById(String id);
   
   /**
    * 根据业务分类获得业务规则
    * @param ruleType
    * @param pageNum
    * @param pageSize
    * @return
    */
   public List<EventRule> getByBusinessType(String ruleType, int pageNum, int pageSize);
   
   /**
    * 获得所有时间规则
    * @param pageNum
    * @param pageSize
    * @return
    */
   public List<EventRule> getAll(int pageNum, int pageSize);
   
   /**
    * 保存事件规则
    * @param eventRule
    * @return
    */
   public String save(EventRule eventRule);
   
   /**
    * 修改事件规则
    * @param eventRule
    * @return
    */
   public int update(EventRule eventRule);
   
   /**
    * 根据ID删除事件规则
    * @param id
    * @return
    */
   public int delete(String id);
   
   /**
    * 校验表达式是否成立
    * @param param 表达式参数
    * @param express 表达式
    * @return
    */
   public boolean checkExpress(Map param, String express);
}
