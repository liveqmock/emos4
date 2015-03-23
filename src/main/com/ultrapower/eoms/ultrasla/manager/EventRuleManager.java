package com.ultrapower.eoms.ultrasla.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.rule.RuleException;
import com.ultrapower.eoms.common.core.component.rule.RuleExpression;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasla.model.EventRule;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;

@Transactional
public class EventRuleManager implements IEventRuleService {

	private IDao<EventRule> eventRuleDao;
	
	public EventRuleManager() {

	}

	public EventRule getById(String id) {
		if(id==null||eventRuleDao==null){
			return null;
		}
		try {
			return eventRuleDao.get(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<EventRule> getByBusinessType(String ruleType, int pageNum,
			int pageSize) {
		return null;
	}

	public List<EventRule> getAll(int pageNum, int pageSize) {
		return null;
	}

	public String save(EventRule eventRule) {
		if(eventRule==null||eventRuleDao==null)
			return null;
		try {
			if(eventRule.getPid()==null||"".equals(eventRule.getPid())) {
				RandomN random = new Random15();
				eventRule.setPid(random.getRandom(System.currentTimeMillis()));
				eventRuleDao.save(eventRule);
			} else
				eventRuleDao.saveOrUpdate(eventRule);
			return eventRule.getPid();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int update(EventRule eventRule) {
		String temp = save(eventRule);
		if(temp==null)
			return -1;
		else
			return 1;
	}

	public int delete(String id) {
		if(id==null||eventRuleDao==null)
			return -1;
		try {
			eventRuleDao.removeById(id);
			return 1;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean checkExpress(Map param, String express) {
		boolean result = false;
		if(express == null || "".equals(express)) {
			return true;
		}
		express = SqlReplace.stringReplaceAllVar(express, (HashMap) param); // 参数替换
		try {
			// 验证表达式
			if("true".equals(RuleExpression.execute(express))) {
				result = true;
			}
		} catch (RuleException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void setEventRuleDao(IDao<EventRule> eventRuleDao) {
		this.eventRuleDao = eventRuleDao;
	}
}
