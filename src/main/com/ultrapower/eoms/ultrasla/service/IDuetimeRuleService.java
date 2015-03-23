package com.ultrapower.eoms.ultrasla.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrasla.model.DuetimeRule;
import com.ultrapower.eoms.ultrasla.util.BaseDuetime;

/**
 * 时限规则服务接口
 * @author Administrator
 *
 */
public interface IDuetimeRuleService 
{
   
   /**
    * 保存规则时限
    * @param duetimeRule
    * @return
    */
   public String save(DuetimeRule duetimeRule);
   
   /**
    * 修改规则时限
    * @param duetimeRule
    * @return
    */
   public int update(DuetimeRule duetimeRule);
   
   /**
    * 根据id删除规则时限
    * @param id
    * @return
    */
   public int delete(String id);
   
   /**
    * 根据规则时限id获得时限规则
    * @param id
    * @return
    */
   public DuetimeRule get(String id);
   
   /**
    * 根据规则类型获得时限规则
    * @param ruletype
    * @param pageNum
    * @param pageSize
    * @return
    */
   public List<DuetimeRule> get(String ruletype, int pageNum, int pageSize);
   
   /**
    * 根据传入的参数，匹配符合条件的时限规则，返回时限对象
    * @param param
    * @param type
    * @return
    */
   public BaseDuetime match(Map param,String type);
}
