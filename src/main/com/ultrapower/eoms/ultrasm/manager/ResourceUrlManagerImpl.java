package com.ultrapower.eoms.ultrasm.manager;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.ResourceUrl;
import com.ultrapower.eoms.ultrasm.service.ResourceUrlManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class ResourceUrlManagerImpl implements ResourceUrlManagerService{
	private IDao<ResourceUrl> resourceUrlDao;

	public IDao<ResourceUrl> getResourceUrlDao() {
		return resourceUrlDao;
	}

	public void setResourceUrlDao(IDao<ResourceUrl> resourceUrlDao) 
	{
		this.resourceUrlDao = resourceUrlDao;
	}

	/**
	 * 通过给定的url获得资源操作URL对象,只查询启用的url对象
	 */
	public ResourceUrl getResourceUrlByUrl(String url) 
	{
		ResourceUrl resourceUrl=null;
		if(url==null)
		{
			return null;
		}
		String ql="from ResourceUrl where  status=1 and url=?";
		Object[] values={url};
		List list=resourceUrlDao.find(ql, values);
		if(list!=null)
		{
			if(list.size()>0)
				resourceUrl=(ResourceUrl)list.get(0);
		}
		return resourceUrl;
	}

	/**
	 * 保存资源操作URL
	 */
	public boolean saveResourceUrl(ResourceUrl resurl) {
		if(resurl==null)
		{
			return false;
		}
		boolean b = false;
		if("".equals(StringUtils.checkNullString(resurl.getPid())))
		{
			b = addResourceUrl(resurl);
		}
		else
		{
			b = updateResourceUrl(resurl);
		}
		return b;
	}

	/**
	 * 添加资源操作URL
	 */
	public boolean addResourceUrl(ResourceUrl resurl) {
		if(resurl==null)
		{
			return false;
		}
		try
		{
			resourceUrlDao.save(resurl);
		}
		catch(Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 修改资源操作URL
	 */
	public boolean updateResourceUrl(ResourceUrl resurl) {
		if(resurl==null)
		{
			return false;
		}
		try
		{
			resourceUrlDao.saveOrUpdate(resurl);
		}
		catch(Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 根据id列表删除资源操作URL
	 */
	public boolean delResUrlByIdList(List idList)
	{
		boolean result = false;
		idList = UltraSmUtil.removeNullData(idList);
		if(idList != null && idList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(idList);
				resourceUrlDao.executeUpdate("delete ResourceUrl where pid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				result = true;
			}
			catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				result = false;
			}
		}
		else
		{
			result = true;
		}
		return result;
	}

	public boolean delResUrlByResId(List resIdList)
	{
		boolean result = false;
		resIdList = UltraSmUtil.removeNullData(resIdList);
		if(resIdList != null && resIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(resIdList);
				resourceUrlDao.executeUpdate("delete ResourceUrl where resid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				result = true;
			}
			catch(Exception e)
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
	
	public boolean delResUrlByOpId(List opIdList)
	{
		boolean result = false;
		opIdList = UltraSmUtil.removeNullData(opIdList);
		if(opIdList != null && opIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(opIdList);
				resourceUrlDao.executeUpdate("delete ResourceUrl where opid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				result = true;
			}
			catch(Exception e)
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

	public ResourceUrl getResourceUrlById(String pid) {
		// TODO Auto-generated method stub
		ResourceUrl resourceUrl=null;
		if(pid==null)
		{
			return null;
		}
		String ql="from ResourceUrl where pid=?";
		Object[] values={pid};
		List list=resourceUrlDao.find(ql, values);
		if(list!=null)
		{
			if(list.size()>0)
				resourceUrl=(ResourceUrl)list.get(0);
		}
		return resourceUrl;
	}

	/**
	 * 启用资源URL
	 */
	public boolean enableResUrl(List<String> idList)
	{
		if(idList!=null && idList.size()>0)
		{
			try
			{
				idList = UltraSmUtil.removeNullData(idList);
				Map map = UltraSmUtil.getSqlParameter(idList);
				resourceUrlDao.executeUpdate("update ResourceUrl set status = 1 where pid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				return true;
			}catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 停用资源URL
	 */
	public boolean disableResUrl(List<String> idList)
	{
		if(idList!=null && idList.size()>0)
		{
			try
			{
				idList = UltraSmUtil.removeNullData(idList);
				Map map = UltraSmUtil.getSqlParameter(idList);
				resourceUrlDao.executeUpdate("update ResourceUrl set status = 0 where pid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				return true;
			}catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
