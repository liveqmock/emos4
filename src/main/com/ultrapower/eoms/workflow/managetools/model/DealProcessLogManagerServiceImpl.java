package com.ultrapower.eoms.workflow.managetools.model;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.workflow.managetools.service.DealProcessLogManagerService;

public class DealProcessLogManagerServiceImpl implements DealProcessLogManagerService
{
	private IDao<DealProcessLog> dpLogManagerDao;

	public void insert(DealProcessLog processlog)
	{
		try{
			dpLogManagerDao.saveOrUpdate(processlog);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @return the dpLogManagerDao
	 */
	public IDao<DealProcessLog> getDpLogManagerDao()
	{
		return dpLogManagerDao;
	}

	/**
	 * @param dpLogManagerDao the dpLogManagerDao to set
	 */
	public void setDpLogManagerDao(IDao<DealProcessLog> dpLogManagerDao)
	{
		this.dpLogManagerDao = dpLogManagerDao;
	}

}
