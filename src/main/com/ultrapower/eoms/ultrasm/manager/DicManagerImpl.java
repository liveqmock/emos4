package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.model.DicType;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.DnsManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class DicManagerImpl implements DicManagerService
{
	private DnsManagerService dnsManagerService;
	private IDao<DicType> dicTypeDao;
	private IDao<DicItem> dicItemDao;
	private IDao<Map> depJdbcDao;
	private QueryAdapter queryAdapter;

	public boolean isUniqueDictype(String dtcode)
	{
		if("".equals(StringUtils.checkNullString(dtcode)))
			return false;
		List<DicType> dtList = dicTypeDao.find(" from DicType where dtcode = ? ", new Object[] {dtcode});
		if(dtList != null && dtList.size() > 0)
			return false;
		return true;
	}

	public String getDtNameByDtcode(String dtcode)
	{
		DicType dt = this.getDicTypeByCode(dtcode);
		if(dt != null)
			return dt.getDtname();
		else
			return "";
	}
	
	public String getTextByValue(String dtcode, String divalue)
	{
		if("".equals(StringUtils.checkNullString(dtcode)) && "".equals(StringUtils.checkNullString(divalue)))
			return "";
		String diname = "";
		try
		{
			boolean isusercache = true;//Constants.ISUSERCACHE;
			if(isusercache)
			{
				Object dtObj = BaseCacheManager.get(Constants.DICTYPE, dtcode);
				Map dicMap = null;
				if(dtObj == null)
				{
					dicMap = this.getDicMapByDtcode(dtcode);
					BaseCacheManager.put(Constants.DICTYPE, dtcode, dicMap);
				}
				else
				{
					dicMap = (HashMap) dtObj;
				}
				if(dicMap != null)
				{
					Object valueObj = dicMap.get(divalue);
					if(valueObj != null)
						diname = (String) valueObj;
				}
			}
			else
			{
				DicItem di = this.getDicItemByValue(dtcode, divalue);
				if(di != null)
					diname = di.getDiname();
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return diname;
	}
	
	private Map getDicMapByDtcode(String dtcode)
	{
		Map dicMap = new HashMap();
		List<DicItem> diList = this.getDicItemByDicType(dtcode);
		if(diList != null && diList.size() > 0)
		{
			DicItem di = null;
			for(int i=0;i<diList.size();i++)
			{
				di = diList.get(i);
				if(di != null)
					dicMap.put(di.getDivalue(), di.getDiname());
			}
		}
		return dicMap;
	}
	
	public boolean isUniqueDicItem(String dtcode, String divalue)
	{
		if("".equals(StringUtils.checkNullString(dtcode)) || "".equals(StringUtils.checkNullString(divalue)))
			return false;
		List<DicItem> diList = dicItemDao.find(" from DicItem where dtcode = ? and divalue = ? ", new Object[] {dtcode, divalue});
		if(diList != null && diList.size() > 0)
			return false;
		return true;
	}
	
	public String addDicType(DicType dictype)
	{
		if(dictype == null)
			return null;
		String dtId = "";
		try
		{
			dicTypeDao.save(dictype);
			dtId = dictype.getPid();
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dtId;
	}
	
	public String updateDicType(DicType dictype)
	{
		if(dictype == null)
			return null;
		String dtId = "";
		try
		{
			dicTypeDao.saveOrUpdate(dictype);
			dtId = dictype.getPid();
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dtId;
	}
	
	public String addDicItem(DicItem dicitem)
	{
		if(dicitem == null)
			return null;
		String diId = "";
		try
		{
			String dicDn = dnsManagerService.getCurrentDn("bs_t_sm_dicitem", "dicdn", StringUtils.checkNullString(dicitem.getParentid()), "dtcode", StringUtils.checkNullString(dicitem.getDtcode()));
			String dicDns = dnsManagerService.getCurrentDns("bs_t_sm_dicitem", "dicdns", "dicdn", StringUtils.checkNullString(dicitem.getParentid()), "dtcode", StringUtils.checkNullString(dicitem.getDtcode()));
			dicitem.setDicdn(dicDn);
			dicitem.setDicdns(dicDns);
			dicItemDao.save(dicitem);
			diId = dicitem.getPid();
			this.clearDicCache(dicitem);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return diId;
	}
	
	public String updateDicItem(DicItem dicitem)
	{
		if(dicitem == null)
			return null;
		String diId = "";
		try
		{
			String oldDiname="";
			String oldDicdns = "";
			String oldDicfullname = "";
			List mapList = depJdbcDao.find(" select diname,dicdns,dicfullname from bs_t_sm_dicitem where pid = ?", new Object[] {dicitem.getPid()});
			if(mapList != null && mapList.size() > 0)
			{
				Map depMap = (Map)mapList.get(0);
				oldDiname = StringUtils.checkNullString(depMap.get("diname"));
				oldDicdns = StringUtils.checkNullString(depMap.get("dicdns"));
				oldDicfullname = StringUtils.checkNullString(depMap.get("dicfullname"));
			}
			if(!oldDiname.equals(dicitem.getDiname()))
			{
				dnsManagerService.updateSubFullName("bs_t_sm_dicitem", "dicdns", "dicfullname", oldDicdns, oldDicfullname, dicitem.getDicfullname(), "dtcode", dicitem.getDtcode());
			}
			dicItemDao.saveOrUpdate(dicitem);
			diId = dicitem.getPid();
			this.clearDicCache(dicitem);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return diId;
	}
	
	public boolean deleteDicTypeById(String dtId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(dtId)))
			return result;
		try
		{
			if(this.deleteDicItemByDtId(dtId))
			{
				DicType dictype = this.getDicTypeById(dtId);
				dicTypeDao.removeById(dtId);
				this.clearDicCache(dictype);
				result = true;
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteDicItemById(String diId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(diId)))
			return result;
		List diIdList = dnsManagerService.getSubIdList("bs_t_sm_dicitem", diId);
		if(diIdList != null && diIdList.size() > 0)
		{
			try
			{
				DicItem dicitem = this.getDicItemById(diId);
				Map map = UltraSmUtil.getSqlParameter(diIdList);
				dicItemDao.executeUpdate(" delete DicItem where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				this.clearDicCache(dicitem);
				result = true;
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
	
	private boolean deleteDicItemByDtId(String dtId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(dtId)))
			return result;
		try
		{
			dicItemDao.executeUpdate(" delete DicItem where dtid = ? ", new Object[] {dtId});
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public DicType getDicTypeById(String dtId)
	{
		if("".equals(StringUtils.checkNullString(dtId)))
			return null;
		return dicTypeDao.get(dtId);
	}
	
	public DicType getDicTypeByCode(String dtcode)
	{
		if("".equals(StringUtils.checkNullString(dtcode)))
			return null;
		DicType dt = null;
		try
		{
			List<DicType> dtList = dicTypeDao.find(" from DicType where dtcode = ? ", new Object[] {dtcode});
			if(dtList != null && dtList.size() > 0)
				dt = dtList.get(0);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dt;
	}
	
	public DicItem getDicItemById(String diId)
	{
		if("".equals(StringUtils.checkNullString(diId)))
			return null;
		return dicItemDao.get(diId);
	}
	
	public DicItem getDicItemByValue(String dtcode, String divalue)
	{
		if("".equals(StringUtils.checkNullString(dtcode)) && "".equals(StringUtils.checkNullString(divalue)))
			return null;
		DicItem di = null;
		try
		{
			List<DicItem> diList = dicItemDao.find(" from DicItem where dtcode = ? and divalue = ? ", new Object[] {dtcode, divalue});
			if(diList != null && diList.size() > 0)
				di = diList.get(0);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return di;
	}
	
	public List<DicItem> getTopDicItemByDtcode(String dtcode)
	{
		List<DicItem> diList = null;
		if("".equals(StringUtils.checkNullString(dtcode))) {
			return diList;
		}
		try {
			diList = dicItemDao.find(" from DicItem where dtcode = ? and parentid = '0' and status = 1 order by ordernum", new Object[] {dtcode});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diList;
	}
	
	public List<DicItem> getDicItemByParentID(String parantid)
	{
		List<DicItem> diList = new ArrayList();
		if("".equals(StringUtils.checkNullString(parantid)))
			return diList;
		try {
			diList = dicItemDao.find(" from DicItem where parentid = ? and status = 1 order by ordernum", new Object[] {parantid});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diList;
	}
	
	public List<DicItem> getDicItemByFullName(String dtcode, String dicFullName)
	{
		List<DicItem> diList = new ArrayList<DicItem> ();
		if("".equals(StringUtils.checkNullString(dtcode)))
			return diList;
		if("".equals(StringUtils.checkNullString(dicFullName)))
		{
			return this.getTopDicItemByDtcode(dtcode);
		}
		try {
			DicItem dicItem = dicItemDao.findUnique("from DicItem where dtcode = ? and dicfullname = ?", new Object[] {dtcode, dicFullName});
			diList = this.getDicItemByParentID(dicItem == null ? "" : dicItem.getPid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diList;
	}
	
	public List<DicItem> getAllSubDicItemByFullName(String dtcode, String dicFullName)
	{
		List<DicItem> diList = null;
		if("".equals(StringUtils.checkNullString(dtcode)))
		{
			return diList;
		}
		if("".equals(StringUtils.checkNullString(dicFullName)))
		{
			return this.getTopDicItemByDtcode(dtcode);
		}
		try {
			diList = dicItemDao.find("from DicItem where dtcode = ? and dicfullname like '" + dicFullName + ".%' order by ordernum", new Object[] {dtcode});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diList;
	}

	public List<DicItem> getDicItem(String divalue,String dtcode) {
		List<DicItem> diList = new ArrayList();
		diList = dicItemDao.find(" from DicItem where status = 1 and divalue=? and dtcode = ? order by ordernum", new Object[] {divalue,dtcode});
		int diListLen = 0;
		if(diList!=null)
			diListLen = diList.size();
		if(diListLen>0)
		   return getDicItemByParentID(diList.get(0).getPid());
		else
		   return null;
	}
	
	public List<DicItem> getRootItsmByDicType(String dtcode){
		List<DicItem> list = new ArrayList<DicItem>();
		queryAdapter = new QueryAdapter();
		StringBuffer sql = new StringBuffer();
		sql.append("select dicitem.divalue dicvalue, dicitem.diname dicname,dicitem.isdefault isdefault");
		sql.append("  from bs_t_sm_dictype dictype, bs_t_sm_dicitem dicitem");
		sql.append(" where dictype.status = 1");
		sql.append("   and dicitem.status = 1");
		sql.append("   and dictype.dtcode = '"+dtcode+"'");
		sql.append("   and dictype.pid = dicitem.dtid and dicitem.parentid = '0'");
		sql.append(" order by dicitem.ordernum");
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		DicItem dicItem = null;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			dicItem = new DicItem();
			dicItem.setDivalue(dataRow.getString("dicvalue"));
			dicItem.setDiname(dataRow.getString("dicname"));
			dicItem.setIsdefault(dataRow.getString("isdefault"));
			list.add(dicItem);
		}
		return list;
	}

	public List<DicItem> getDicItemByDicType(String dtcode)
	{
		if("".equals(StringUtils.checkNullString(dtcode)))
			return null;
		List<DicItem> diList = dicItemDao.find(" from DicItem where dtcode = ? and status = 1 order by ordernum", new Object[] {dtcode});
		return diList;
	}
	
	public List<DicType> getDicType()
	{
		List<DicType> dtList = dicTypeDao.find(" from DicType where status = 1", null);
		return dtList;
	}
	
	public String getSubDivalue(String dtcode, String divalue, boolean isCon)
	{
		String divalues = "";
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select divalue");
			sql.append("  from bs_t_sm_dicitem");
			sql.append(" where dtcode = ?");
			sql.append(" start with divalue = ?");
			sql.append("connect by parentid = prior pid");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), new Object[] {dtcode, divalue});
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						if(!"".equals(divalues))
							divalues += ",";
						divalues += temp;
					}
				}
				if(isCon)
				{
					divalues = divalues.replaceAll(",", "','");
					divalues = "'" + divalues + "'";
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return divalues;
	}
	
	/**
	 * 清空字典类型缓存
	 * @param dicitem	字典信息对象
	 */
	private void clearDicCache(DicItem dicitem)
	{
		if(dicitem == null)
			return;
		String dtcode = dicitem.getDtcode();
		Object dtObj = BaseCacheManager.get(Constants.DICTYPE, dtcode);
		if(dtObj != null)
			BaseCacheManager.removeElement(Constants.DICTYPE, dtcode);
	}
	
	/**
	 * 清空字典类型缓存
	 * @param dictype	字典类型对象
	 */
	private void clearDicCache(DicType dictype)
	{
		if(dictype == null)
			return;
		String dtcode = dictype.getDtcode();
		Object dtObj = BaseCacheManager.get(Constants.DICTYPE, dtcode);
		if(dtObj != null)
			BaseCacheManager.removeElement(Constants.DICTYPE, dtcode);
	}
	
	public void setDnsManagerService(DnsManagerService dnsManagerService) {
		this.dnsManagerService = dnsManagerService;
	}
	public void setDicTypeDao(IDao<DicType> dicTypeDao) {
		this.dicTypeDao = dicTypeDao;
	}
	public void setDicItemDao(IDao<DicItem> dicItemDao) {
		this.dicItemDao = dicItemDao;
	}
	public void setDepJdbcDao(IDao<Map> depJdbcDao) {
		this.depJdbcDao = depJdbcDao;
	}
}
