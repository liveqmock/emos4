package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.Operate;
import com.ultrapower.eoms.ultrasm.service.OperateManagerService;
import com.ultrapower.eoms.ultrasm.service.ResourceUrlManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class OperateManagerImpl implements OperateManagerService
{
	private IDao<Operate> operateDao;
	private RoleManagerService roleManagerService;
	private ResourceUrlManagerService resourceUrlManagerService;

	public boolean isUnique(String opId)
	{
		if(operateDao.get(opId) == null)
		{
			return true;
		}
		return false;
	}
	
	public boolean saveOperateInfo(Operate operate)
	{
		boolean result = false;
		if(operate == null)
		{
			return result;
		}
		try
		{
			operateDao.saveOrUpdate(operate);
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteOperateById(List opIdList)
	{
		boolean result = false;
		opIdList = UltraSmUtil.removeNullData(opIdList);
		if(opIdList != null && opIdList.size() > 0)
		{
			try
			{
				if(roleManagerService.clearRroByOpId(opIdList) && resourceUrlManagerService.delResUrlByOpId(opIdList))
				{
					Map map = UltraSmUtil.getSqlParameter(opIdList);
					operateDao.executeUpdate(" delete Operate where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
					result = true;
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}
	
	public Operate getOperateByID(String opId)
	{
		if("".equals(StringUtils.checkNullString(opId)))
		{
			return null;
		}
		return operateDao.get(opId);
	}
	
	public List<Operate> getOperateByID(List opIdList)
	{
		List<Operate> opList = new ArrayList();
		opIdList = UltraSmUtil.removeNullData(opIdList);
		if(opIdList != null && opIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(opIdList);
			opList = operateDao.find(" from Operate where status = 1 and pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
		}
		return opList;
	}

	public List<Operate> getAllOperate()
	{
		List<Operate> opList = operateDao.find(" from Operate where status = 1");
		return opList;
	}
	
	public void setOperateDao(IDao<Operate> operateDao) {
		this.operateDao = operateDao;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
	public void setResourceUrlManagerService(ResourceUrlManagerService resourceUrlManagerService) {
		this.resourceUrlManagerService = resourceUrlManagerService;
	}
}
