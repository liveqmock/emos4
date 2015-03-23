package com.ultrapower.eoms.ultrasla.manager;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasla.model.DuetimeRule;
import com.ultrapower.eoms.ultrasla.model.SlaDefine;
import com.ultrapower.eoms.ultrasla.service.IDuetimeRuleService;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;
import com.ultrapower.eoms.ultrasla.service.ISlaDefineService;
import com.ultrapower.eoms.ultrasla.util.BaseDuetime;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;

@Transactional
public class DuetimeRuleManager implements IDuetimeRuleService {
	private IDao<DuetimeRule> duetimeRuleDao;
	private ISlaDefineService slaDefineService;
	private IEventRuleService eventRuleService;

	public DuetimeRuleManager() {

	}

	public String save(DuetimeRule duetimeRule) {
		if(duetimeRule==null||duetimeRuleDao==null)
			return null;
		try {
			if(duetimeRule.getPid()==null||"".equals(duetimeRule.getPid())){
				RandomN random = new Random15();
				duetimeRule.setPid( random.getRandom(System.currentTimeMillis()));
				duetimeRuleDao.save(duetimeRule);
			}
			else{
				duetimeRuleDao.saveOrUpdate(duetimeRule);
			}
			return duetimeRule.getPid();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int update(DuetimeRule duetimeRule) {
		String temp = save(duetimeRule);
		if(temp==null)
			return -1;
		else
			return 1;
	}

	public int delete(String id) {
		if(id==null||duetimeRuleDao==null)
			return -1;
		try {
			duetimeRuleDao.removeById(id);
			return 1;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public DuetimeRule get(String id) {
		if(id==null||duetimeRuleDao==null)
			return null;
		try {
			return duetimeRuleDao.get(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<DuetimeRule> get(String ruletype, int pageNum, int pageSize) {
		return null;
	}

	public BaseDuetime match(Map param, String type) {
		BaseDuetime baseDuetime = null;
		if(param == null) {
			return baseDuetime;
		}
		Object obj = param.get("BASESCHEMA");
		if(obj == null) {
			return baseDuetime;
		}
		String baseSchema = (String) obj;
		SlaDefine slaDefine = slaDefineService.getBySchema(baseSchema); // 获取sla规则定义
		if(slaDefine == null) {
			return baseDuetime;
		}
		// 根据多个以 , 分割的id来获取规则时限集合
		List<DuetimeRule> duetimeRuleList = this.getDuetimeRuleByIds(slaDefine.getFormtimeruleid());
		int dtRuleLen = 0;
		if(duetimeRuleList != null) {
			dtRuleLen = duetimeRuleList.size();
		}
		DuetimeRule duetimeRule;
		String ruleExpress; // 规则表达式
		// 遍历规则时限
		for(int i=0 ; i<dtRuleLen ; i++) {
			duetimeRule = duetimeRuleList.get(i);
			if(duetimeRule == null) {
				continue;
			}
			ruleExpress = duetimeRule.getRuleexpress();
			// 替换表达式中的动态参数
			if(eventRuleService.checkExpress(param, ruleExpress)) { // 如果验证表达式成立则获取时限退出循环
				baseDuetime = new BaseDuetime();
				baseDuetime.setAcceptTime(duetimeRule.getAccepttime());
				baseDuetime.setProcessTime(duetimeRule.getProcesstime());
				break;
			}
		}
		return baseDuetime;
	}
	
	/**
	 * 根据多个以 , 分割的id来获取规则时限集合
	 * @param ids
	 * @return
	 */
	private List<DuetimeRule> getDuetimeRuleByIds(String ids) {
		List<DuetimeRule> duetimeRuleList = null;
		if(ids == null || "".equals(ids)) {
			return duetimeRuleList;
		}
		ids = ids.replaceAll(",", "','");
		try {
			duetimeRuleList = duetimeRuleDao.find("from DuetimeRule where pid in ('" + ids + "') order by priority desc, accepttime, processtime", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return duetimeRuleList;
	}
	
	public void setduetimeRuleDao(IDao<DuetimeRule> duetimeRuleDao) {
		this.duetimeRuleDao = duetimeRuleDao;
	}
	public void setSlaDefineService(ISlaDefineService slaDefineService) {
		this.slaDefineService = slaDefineService;
	}
	public void setEventRuleService(IEventRuleService eventRuleService) {
		this.eventRuleService = eventRuleService;
	}
}
