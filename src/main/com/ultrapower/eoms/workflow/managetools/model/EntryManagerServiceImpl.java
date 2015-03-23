package com.ultrapower.eoms.workflow.managetools.model;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.workflow.managetools.service.EntryManagerService;

public class EntryManagerServiceImpl implements EntryManagerService
{
	private IDao<Entry> entryManagerDao;
	
	public void insert(Entry entry)
	{
		try{
			entryManagerDao.saveOrUpdate(entry);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @return the entryManagerDao
	 */
	public IDao<Entry> getEntryManagerDao()
	{
		return entryManagerDao;
	}

	/**
	 * @param entryManagerDao the entryManagerDao to set
	 */
	public void setEntryManagerDao(IDao<Entry> entryManagerDao)
	{
		this.entryManagerDao = entryManagerDao;
	}

}
