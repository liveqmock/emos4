package com.ultrapower.eoms.ultrasm.manager;

import java.util.List;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasm.model.BsTCmbcAlarmnote;
import com.ultrapower.eoms.ultrasm.service.BsTCmbcAlarmnoteService;

public class BsTCmbcAlarmnoteManagerImpl implements BsTCmbcAlarmnoteService {
	private IDao<BsTCmbcAlarmnote> BsTCmbcAlarmnoteDao;
	private BsTCmbcAlarmnote bsTCmbcAlarmnote;
	private BsTCmbcAlarmnoteService bsTCmbcAlarmnoteService;
	
	/**
	 * 根据Id，删除对象
	 */
	public boolean remove(String pid) {
		boolean result = false;
		if("".equals(pid))
			return result;
		try{
			if(!"".equals(pid))
			{
				BsTCmbcAlarmnoteDao.removeById(pid);
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 修改对象值
	 */
	public boolean updateBsTCmbcAlarmnote(BsTCmbcAlarmnote bsTCmbcAlarmnote) {
		boolean falg = false ;
		if(bsTCmbcAlarmnote==null||BsTCmbcAlarmnoteDao==null)
			return falg;
		try{
			if(!"".equals(bsTCmbcAlarmnote.getPid())&&bsTCmbcAlarmnote.getPid()!=null)
			{
				bsTCmbcAlarmnote.setIscreatesheet("1");
				BsTCmbcAlarmnoteDao.saveOrUpdate(bsTCmbcAlarmnote);
				falg = true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return falg;
		}
		return falg;
	}

	
	/**
	 * 根据pid获得对象
	 */
	public BsTCmbcAlarmnote getBsTCmbcAlarmnote(String pid) {
		if(!"".equals(pid)&&pid!=null)
		{
			bsTCmbcAlarmnote = BsTCmbcAlarmnoteDao.get(pid);		
		}
	
		
		return bsTCmbcAlarmnote;
	}
	
	
	public IDao<BsTCmbcAlarmnote> getBsTCmbcAlarmnoteDao() {
		return BsTCmbcAlarmnoteDao;
	}

	public void setBsTCmbcAlarmnoteDao(
			IDao<BsTCmbcAlarmnote> bsTCmbcAlarmnoteDao) {
		BsTCmbcAlarmnoteDao = bsTCmbcAlarmnoteDao;
	}

	public BsTCmbcAlarmnoteService getBsTCmbcAlarmnoteService() {
		return bsTCmbcAlarmnoteService;
	}

	public void setBsTCmbcAlarmnoteService(
			BsTCmbcAlarmnoteService bsTCmbcAlarmnoteService) {
		this.bsTCmbcAlarmnoteService = bsTCmbcAlarmnoteService;
	}




	
}
