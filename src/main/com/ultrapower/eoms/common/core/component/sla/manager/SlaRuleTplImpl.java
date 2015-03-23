package com.ultrapower.eoms.common.core.component.sla.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleTpl;
import com.ultrapower.eoms.common.core.component.sla.model.SlaTplProperty;
import com.ultrapower.eoms.common.core.component.sla.service.SlaRuleTplService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 下午04:04:24
 */
public class SlaRuleTplImpl implements SlaRuleTplService{

	
	private IDao<SlaRuleTpl> slaRuleTplDao;
	private IDao<SlaTplProperty> slaTplPropertyDao;
	
	public boolean addSlaRuleTpl(SlaRuleTpl slaRuleTpl) {
		boolean flag = false;
		try{
			slaRuleTplDao.save(slaRuleTpl);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("添加规则模板出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public boolean addSlaRuleTpl(SlaRuleTpl slaRuleTpl,
			List<SlaTplProperty> slaTplPropertyList) {
		boolean flag = false;
		try{
			slaRuleTplDao.save(slaRuleTpl);
			if(slaTplPropertyList!=null){
				for(SlaTplProperty slaTplProperty : slaTplPropertyList){
					slaTplProperty.setRuletplpid(slaRuleTpl.getPid());
					slaTplProperty.setCreater(slaRuleTpl.getCreater());
					slaTplProperty.setCreatetime(slaRuleTpl.getCreatetime());
					slaTplProperty.setLastmodifier(slaRuleTpl.getLastmodifier());
					slaTplProperty.setLastmodifytime(slaRuleTpl.getLastmodifytime());
					slaTplPropertyDao.save(slaTplProperty);
				}
			}
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("添加规则模板出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public boolean deleteSlaRuleTpl(String id) {
		boolean flag = false;
		try{
			if(delSlaTplProperty(id)){//先删除规则模板的属性信息
				slaRuleTplDao.removeById(id);//再删除规则模板的信息
				flag = true;
			}
		}catch(Exception e){
			RecordLog.printLog("删除规则模板出错,pid="+id+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public SlaRuleTpl get(String id) {
		return slaRuleTplDao.get(id);
	}
	
	private boolean delSlaTplProperty(String slaRuleTplPid){
		boolean flag = false;
		String sql = "delete  from SlaTplProperty where ruletplpid = ?";
		Object[] values = {slaRuleTplPid};
		try{
			slaTplPropertyDao.executeUpdate(sql, values);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("删除规则模板属性信息出错,规则模板pid="+slaRuleTplPid+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public List<SlaTplProperty> getSlaTplProperty(String slaRuleTplPid) {
		List<SlaTplProperty> slaTplPropertyList = new ArrayList<SlaTplProperty>();
		String hql = " from SlaTplProperty where ruletplpid = ? order by ordernum";
		Object[] values = {slaRuleTplPid};
		try{
			slaTplPropertyList = slaTplPropertyDao.find(hql, values);
		}catch(Exception e){
			RecordLog.printLog("获取规则模板属性列表信息出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return slaTplPropertyList;
	}

	public boolean updateRuleTpl(SlaRuleTpl slaRuleTpl) {
		boolean flag = false;
		try{
			slaRuleTplDao.saveOrUpdate(slaRuleTpl);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("更新规则模板信息出错,规则模板id="+slaRuleTpl.getPid()+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}
	
	public boolean updateRuleTpl(SlaRuleTpl slaRuleTpl,
			List<SlaTplProperty> addSlaTplPropertyList,
			List<SlaTplProperty> modSlaTplPropertyList,
			List<String> delSlaTplPropertypid) {
		boolean flag = false;
		//更新规则模板信息
		if(this.updateRuleTpl(slaRuleTpl)){
			try{
				//添加属性
				if(addSlaTplPropertyList!=null){
					for(SlaTplProperty slaTplProperty : addSlaTplPropertyList){
						slaTplProperty.setRuletplpid(slaRuleTpl.getPid());
						slaTplProperty.setCreater(slaRuleTpl.getCreater());
						slaTplProperty.setCreatetime(slaRuleTpl.getCreatetime());
						slaTplProperty.setLastmodifier(slaRuleTpl.getLastmodifier());
						slaTplProperty.setLastmodifytime(slaRuleTpl.getLastmodifytime());
						slaTplPropertyDao.save(slaTplProperty);
					}
				}
				
				//更新属性
				if(modSlaTplPropertyList!=null){
					for(SlaTplProperty slaTplProperty : modSlaTplPropertyList){
						slaTplProperty.setRuletplpid(slaRuleTpl.getPid());
						slaTplProperty.setCreater(slaRuleTpl.getCreater());
						slaTplProperty.setCreatetime(slaRuleTpl.getCreatetime());
						slaTplProperty.setLastmodifier(slaRuleTpl.getLastmodifier());
						slaTplProperty.setLastmodifytime(slaRuleTpl.getLastmodifytime());
						slaTplPropertyDao.saveOrUpdate(slaTplProperty);
					}
				}
				
				//删除属性
				if(delSlaTplPropertypid!=null){
					for(String slaTplPropertypid : delSlaTplPropertypid){
						slaTplPropertyDao.removeById(slaTplPropertypid);
					}
				}
				flag = true;
			}catch(Exception e){
				RecordLog.printLog("更新规则模板及规则模板属性信息出错,规则模板pid="+slaRuleTpl.getPid()+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return flag;
	}
	
	public boolean checkTplMarkUnique(String mark)
	{
		boolean b = false;
		if(!"".equals(StringUtils.checkNullString(mark)))
		{
			String hql = "from SlaRuleTpl where tplmark = ?";
			List<SlaRuleTpl> temp = slaRuleTplDao.find(hql, new Object[]{mark});
			if(temp==null || temp.size()==0)
			{
				b = true;
			}
		}
		return b;
	}
	
	public SlaTplProperty getSlaTplPropertyInfo(String slaTplPropertypid) {
		return slaTplPropertyDao.get(slaTplPropertypid);
	}
	
	public void setSlaRuleTplDao(IDao<SlaRuleTpl> slaRuleTplDao) {
		this.slaRuleTplDao = slaRuleTplDao;
	}

	public void setSlaTplPropertyDao(IDao<SlaTplProperty> slaTplPropertyDao) {
		this.slaTplPropertyDao = slaTplPropertyDao;
	}
}
