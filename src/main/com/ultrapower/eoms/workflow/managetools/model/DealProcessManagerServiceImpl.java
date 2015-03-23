package com.ultrapower.eoms.workflow.managetools.model;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.workflow.managetools.service.DealProcessManagerService;

public class DealProcessManagerServiceImpl implements DealProcessManagerService
{
	private IDao<DealProcess> dealProcessManagerDao;

	public void insert(DealProcess process)
	{
		try{
			dealProcessManagerDao.saveOrUpdate(process);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @return the dealProcessManagerDao
	 */
	public IDao<DealProcess> getDealProcessManagerDao()
	{
		return dealProcessManagerDao;
	}

	/**
	 * @param dealProcessManagerDao the dealProcessManagerDao to set
	 */
	public void setDealProcessManagerDao(IDao<DealProcess> dealProcessManagerDao)
	{
		this.dealProcessManagerDao = dealProcessManagerDao;
	}

}
