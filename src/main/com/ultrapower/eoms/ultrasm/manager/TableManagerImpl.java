package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.FieldInfo;
import com.ultrapower.eoms.ultrasm.model.TableInfo;
import com.ultrapower.eoms.ultrasm.service.TableManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class TableManagerImpl implements TableManagerService {

	private IDao<TableInfo> tableDao;
	private IDao<FieldInfo> fieldDao;
	
	public boolean SaveTable(TableInfo tbInfo) {
		boolean b = false;
		if(tbInfo!=null)
		{
			try
			{
				if("".equals(StringUtils.checkNullString(tbInfo.getPid())))
				{
					tableDao.save(tbInfo);
				}
				else
				{
					tableDao.saveOrUpdate(tbInfo);
				}
				b = true;
			}catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return false;
			}
		}
		return b;
	}

	public boolean deleteTableById(List<String> ids) {
		boolean b = false;
		if(ids!=null && ids.size()>0)
		{
			ids = UltraSmUtil.removeNullData(ids);
			try
			{
				Map map = UltraSmUtil.getSqlParameter(ids);
				tableDao.executeUpdate("delete TableInfo where pid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				b = true;
			}
			catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				b = false;
			}
		}
		return b;
	}

	public boolean deleteTableByEnname(List<String> names)
	{
		
		boolean b = false;
		if(names!=null && names.size()>0)
		{
			names = UltraSmUtil.removeNullData(names);
			try
			{
				Map map = UltraSmUtil.getSqlParameter(names);
				fieldDao.executeUpdate("delete FieldInfo where enname in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				tableDao.executeUpdate("delete TableInfo where enname in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				b = true;
			}
			catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				b = false;
			}
		}
		return b;
	}
	
	public TableInfo getTableById(String id) {
		TableInfo tbInfo = null;
		if(id!=null)
		{
			String hql = "from TableInfo where pid=?";
			Object[] values = {id};
			List<TableInfo> lst = tableDao.find(hql, values);
			if(lst!=null&&lst.size()>0)
			{
				tbInfo = lst.get(0);
				return tbInfo;
			}
		}
		return null;
	}

	public TableInfo getTableByEnname(String enname)
	{
		TableInfo tbInfo = null;
		if(enname!=null)
		{
			String hql = "from TableInfo where enname=?";
			Object[] values = {enname};
			List<TableInfo> lst = tableDao.find(hql, values);
			if(lst!=null&&lst.size()>0)
			{
				tbInfo = lst.get(0);
				return tbInfo;
			}
		}
		return null;
	}
	
	public boolean checkEnameUnique(String ename) {
		ename = StringUtils.checkNullString(ename);
		if(!"".equals(ename))
		{
			String hql = "from TableInfo where enname=?";
			Object[] values = {ename};
			List<TableInfo> lst = tableDao.find(hql, values);
			if(lst!=null && lst.size()>0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	public boolean deleteField(List<String> ids) {
		boolean b = false;
		if(ids!=null && ids.size()>0)
		{
			ids = UltraSmUtil.removeNullData(ids);
			try
			{
				Map map = UltraSmUtil.getSqlParameter(ids);
				fieldDao.executeUpdate("delete FieldInfo where pid in ( " + map.get("?") + " )", (Object[]) map.get("obj"));
				b = true;
			}
			catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				b = false;
			}
		}
		return b;
	}

	public FieldInfo getFieldById(String id) {
		FieldInfo fieldInfo = null;
		if(id!=null)
		{
			String hql = "from FieldInfo where pid=?";
			Object[] values = {id};
			List<FieldInfo> lst = fieldDao.find(hql, values);
			if(lst!=null&&lst.size()>0)
			{
				fieldInfo = lst.get(0);
				return fieldInfo;
			}
		}
		return null;
	}

	public List<FieldInfo> getFieldById(List fieldIdList)
	{
		fieldIdList = UltraSmUtil.removeNullData(fieldIdList);
		List<FieldInfo> fieldList = new ArrayList();
		if(fieldIdList != null && fieldIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(fieldIdList);
				fieldList = fieldDao.find(" from FieldInfo where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return fieldList;
	}
	
	public List<FieldInfo> getFieldByTbName(String tableEnname) {
		List<FieldInfo> fieldList = null;
		if(tableEnname!=null)
		{
			try{
				String hql = "from FieldInfo where enname=?";
				Object[] values = {tableEnname};
				fieldList = fieldDao.find(hql, values);
				if(fieldList!=null&&fieldList.size()>0)
				{
					return fieldList;
				}
			}catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public boolean saveField(FieldInfo field) {
		boolean b = false;
		if(field!=null)
		{
			try
			{
				if("".equals(StringUtils.checkNullString(field.getPid())))
				{
					fieldDao.save(field);
				}
				else
				{
					fieldDao.saveOrUpdate(field);
				}
				b = true;
			}catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return false;
			}
		}
		return b;
	}
	
	public boolean checkFieldUnique(String tbEnname, String fieldEnname) {
		if(!"".equals(StringUtils.checkNullString(tbEnname)) && !"".equals(StringUtils.checkNullString(fieldEnname)))
		{
			String hql = "from FieldInfo where enname=? and field=?";
			Object[] values = {tbEnname,fieldEnname};
			List<FieldInfo> lst = fieldDao.find(hql, values);
			if(lst!=null && lst.size()>0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean addTableAndField(TableInfo tbinfo, List<FieldInfo> fieldlst) {
		if(tbinfo!=null)
		{
			try
			{
				String enname = tbinfo.getEnname();
				SaveTable(tbinfo);
				if(fieldlst!=null&&fieldlst.size()>0)
				{
					for(int i=0;i<fieldlst.size();i++)
					{
						fieldlst.get(i).setPid(null);
						fieldlst.get(i).setEnname(enname);
						saveField(fieldlst.get(i));
					}
				}
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean updateTableAndField(TableInfo tbinfo,
			List<FieldInfo> addlst, List<FieldInfo> updatelst,
			List<String> delIdlst) {
		if(tbinfo!=null)
		{
			try
			{
				String enname = tbinfo.getEnname();
				SaveTable(tbinfo);
				if(addlst!=null&&addlst.size()>0)
				{
					for(int i=0;i<addlst.size();i++)
					{
						addlst.get(i).setPid(null);
						addlst.get(i).setEnname(enname);
						saveField(addlst.get(i));
					}
				}
				if(updatelst!=null&&updatelst.size()>0)
				{
					for(int i=0;i<updatelst.size();i++)
					{
						updatelst.get(i).setEnname(enname);
						saveField(updatelst.get(i));
					}
				}
				if(delIdlst!=null&&delIdlst.size()>0)
				{
					deleteField(delIdlst);
				}
				return true;
			}catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public void setTableDao(IDao<TableInfo> tableDao) {
		this.tableDao = tableDao;
	}

	public void setFieldDao(IDao<FieldInfo> fieldDao) {
		this.fieldDao = fieldDao;
	}

}
