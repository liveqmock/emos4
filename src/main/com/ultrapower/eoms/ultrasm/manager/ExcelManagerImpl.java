package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfg;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfgFld;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfgRow;
import com.ultrapower.eoms.ultrasm.service.ExcelManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class ExcelManagerImpl implements ExcelManagerService
{
	private IDao<ExcelExpCfg> excelExpDao;
	private IDao<ExcelExpCfgFld> excelExpFldDao;
	private IDao<ExcelExpCfgRow> excelExpRowDao;

	public ExcelExpCfg getExcelCfgByMark(String cfgMark)
	{
		if("".equals(StringUtils.checkNullString(cfgMark)))
		{
			return null;
		}
		ExcelExpCfg eec = null;
		List<ExcelExpCfg> eecList = excelExpDao.find(" from ExcelExpCfg where cfgmark = ? ", new Object[] {cfgMark});
		if(eecList != null && eecList.size() > 0)
			eec = eecList.get(0);
		return eec;
	}

	public ExcelExpCfg getExcelCfgByPid(String pid) {
		if("".equals(StringUtils.checkNullString(pid)))
		{
			return null;
		}
		ExcelExpCfg eec = null;
		List<ExcelExpCfg> eecList = excelExpDao.find(" from ExcelExpCfg where pid = ? ", new Object[] {pid});
		if(eecList != null && eecList.size() > 0)
			eec = eecList.get(0);
		return eec;
	}
	
	public boolean deleteExcelExpCfg(List<String> idList) {
		boolean b = false;
		if(idList!=null && idList.size()>0)
		{
			try
			{
				for(int i=0;i<idList.size();i++)
				{
					deleteExcelExpCfgData(idList.get(i));
				}
				Map map = UltraSmUtil.getSqlParameter(idList);
				excelExpDao.executeUpdate("delete ExcelExpCfg where pid in ( " + map.get("?") + " )", (Object[])map.get("obj"));
				b = true;
			}catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return b;
			}
		}
		return b;
	}

	public boolean deleteExcelExpCfgData(String excelCfgPid) {
		boolean b = false;
		if(!"".equals(StringUtils.checkNullString(excelCfgPid)))
		{
			try
			{
				List<String> rowlist = new ArrayList<String>();
				List<ExcelExpCfgRow> eecfrlist = excelExpRowDao.find("from ExcelExpCfgRow where eecid=?", new Object[]{excelCfgPid});
				if(eecfrlist!=null && eecfrlist.size()>0)
				{
					for(int i=0;i<eecfrlist.size();i++)
					{
						rowlist.add(eecfrlist.get(i).getPid());
					}
					if(rowlist!=null && rowlist.size()>0)
					{
						Map map = UltraSmUtil.getSqlParameter(rowlist);
						excelExpFldDao.executeUpdate("delete ExcelExpCfgFld where eecrid in ( " + map.get("?") + " )", (Object[])map.get("obj"));
					}
					excelExpRowDao.executeUpdate("delete ExcelExpCfgRow where eecid = ?", new Object[]{excelCfgPid});
				}
				b = true;
			}catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return b;
			}
		}
		return b;
	}

	public boolean saveExcelExpCfg(ExcelExpCfg excelExpCfg, Map cfgData) {
		boolean b = false;
		if(excelExpCfg!=null)
		{
			boolean isNew = "".equals(StringUtils.checkNullString(excelExpCfg.getPid()))?true:false;
			try
			{
				UserSession usersession = ActionContext.getUserSession();
				String cfgid = saveExcelExpCfg(excelExpCfg);
				if(cfgid!=null && cfgData!=null)
				{
					ExcelExpCfgFld[][] tdmatrix = (ExcelExpCfgFld[][]) cfgData.get("tdMatrix");
					if(!isNew)
					{//如果是修改
						deleteExcelExpCfgData(cfgid);
					}
					for(int i=0;i<tdmatrix.length;i++)
					{
						ExcelExpCfgRow eecr = new ExcelExpCfgRow();
						eecr.setEecid(cfgid);
						eecr.setRownumber((long)i);
						eecr.setDatarow((i==tdmatrix.length-1)?"1":"0");
						eecr.setCreater(usersession.getPid());
						eecr.setCreatetime(TimeUtils.getCurrentTime());
						eecr.setLastmodifier(usersession.getPid());
						eecr.setLastmodifytime(TimeUtils.getCurrentTime());
						String eecrid = saveExcelExpCfgRow(eecr);
						for(int j=0;j<tdmatrix[i].length;j++)
						{
							ExcelExpCfgFld eecfld = tdmatrix[i][j];
							eecfld.setOrdernum((long)j);
							eecfld.setEecrid(eecrid);
							eecfld.setCreater(usersession.getPid());
							eecfld.setCreatetime(TimeUtils.getCurrentTime());
							eecfld.setLastmodifier(usersession.getPid());
							eecfld.setLastmodifytime(TimeUtils.getCurrentTime());
							saveExcelExpCfgFld(eecfld);
						}
					}
				}
				b = true;
			}
			catch(Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
				return b;
			}
			
		}
		return b;
	}
	
	public String saveExcelExpCfg(ExcelExpCfg excelExpCfg) {
		if(excelExpCfg==null)
		{
			return null;
		}
		String cfgid = null;
		try
		{
			if("".equals(StringUtils.checkNullString(excelExpCfg.getPid())))
			{
				excelExpDao.save(excelExpCfg);
				cfgid = excelExpCfg.getPid();
			}
			else
			{
				excelExpDao.saveOrUpdate(excelExpCfg);
				cfgid = excelExpCfg.getPid();
			}
		}catch(Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return cfgid;
	}

	public String saveExcelExpCfgFld(ExcelExpCfgFld excelExpCfgFld) {
		if(excelExpCfgFld==null)
		{
			return null;
		}
		String eecfid = null;
		try
		{
			if("".equals(StringUtils.checkNullString(excelExpCfgFld.getPid())))
			{
				excelExpFldDao.save(excelExpCfgFld);
				eecfid = excelExpCfgFld.getPid();
			}
			else
			{
				excelExpFldDao.saveOrUpdate(excelExpCfgFld);
				eecfid = excelExpCfgFld.getPid();
			}
		}catch(Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return eecfid;
	}

	public String saveExcelExpCfgRow(ExcelExpCfgRow excelExpCfgRow) {
		if(excelExpCfgRow==null)
		{
			return null;
		}
		String eecrid = null;
		try
		{
			if("".equals(StringUtils.checkNullString(excelExpCfgRow.getPid())))
			{
				excelExpRowDao.save(excelExpCfgRow);
				eecrid = excelExpCfgRow.getPid();
			}
			else
			{
				excelExpRowDao.saveOrUpdate(excelExpCfgRow);
				eecrid = excelExpCfgRow.getPid();
			}
		}catch(Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return eecrid;
	}
	
	public boolean isCfgMarkUnique(String cfgMark)
	{
		boolean b = false;
		if("".equals(StringUtils.checkNullString(cfgMark)))
		{
			return b;
		}
		List<ExcelExpCfg> eecList = excelExpDao.find(" from ExcelExpCfg where cfgmark = ? ", new Object[] {cfgMark});
		if(eecList == null || eecList.size() == 0)
		{
			b = true;
		}
		return b;
	}
	
	public void setExcelExpDao(IDao<ExcelExpCfg> excelExpDao) {
		this.excelExpDao = excelExpDao;
	}

	public void setExcelExpFldDao(IDao<ExcelExpCfgFld> excelExpFldDao) {
		this.excelExpFldDao = excelExpFldDao;
	}

	public void setExcelExpRowDao(IDao<ExcelExpCfgRow> excelExpRowDao) {
		this.excelExpRowDao = excelExpRowDao;
	}

	public IDao<ExcelExpCfg> getExcelExpDao() {
		return excelExpDao;
	}

	public IDao<ExcelExpCfgFld> getExcelExpFldDao() {
		return excelExpFldDao;
	}

	public IDao<ExcelExpCfgRow> getExcelExpRowDao() {
		return excelExpRowDao;
	}
	
}
