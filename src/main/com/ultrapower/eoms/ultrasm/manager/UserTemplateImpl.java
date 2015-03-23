package com.ultrapower.eoms.ultrasm.manager;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserTemplate;
import com.ultrapower.eoms.ultrasm.model.UserTemplateData;
import com.ultrapower.eoms.ultrasm.model.UserTemplateShare;
import com.ultrapower.eoms.ultrasm.model.UserTemplateType;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserTemplateService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class UserTemplateImpl implements UserTemplateService
{
	private IDao<UserTemplate> userTemplateDao;
	private IDao<UserTemplateType> userTemplateTypeDao;
	private IDao<UserTemplateData> userTemplateDataDao;
	private IDao<UserTemplateShare> userTemplateShareDao;
	private DepManagerService depManagerService;
	
	public boolean addUserTemplate(UserTemplate userTemplate)
	{
		boolean result = false;
		if(userTemplate == null)
			return result;
		try
		{
			userTemplateDao.save(userTemplate);
			String utid = StringUtils.checkNullString(userTemplate.getPid());
			boolean typeResult = this.addUserTemplateType(utid, userTemplate.getUttype(), userTemplate.getTypedata());
			// 配置数据是否成功
			boolean dataResult = this.addUserTemplateData(utid, userTemplate.getUserdata(), userTemplate.getDepdata());
			// 配置共享是否成功
			boolean shareResult = this.addUserTemplateShare(utid, userTemplate.getTemplatename(), userTemplate.getUsershare(), userTemplate.getDepshare());
			result = typeResult && dataResult && shareResult;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteUserTemplate(String utid)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(utid)))
			return result;
		try
		{
			if(this.deleteUserTemplateShare(utid) && this.deleteUserTemplateData(utid) && this.deleteUserTemplateType(utid))
			{
				 // 当删除配置数据成功 并且 删除配置共享成功 并且删除人员模版类型成功 才删除此人员模版
				userTemplateDao.executeUpdate("delete UserTemplate where pid = ?", utid);
				result = true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateUserTemplate(UserTemplate userTemplate, boolean updateUtType, boolean updateUtName, boolean updateData, boolean updateShare)
	{
		boolean result = false;
		if(userTemplate == null)
			return result;
		try
		{
			userTemplateDao.saveOrUpdate(userTemplate);
			String utid = StringUtils.checkNullString(userTemplate.getPid());
			boolean typeResult = true;
			if(updateUtType && this.deleteUserTemplateType(utid))
			{
				typeResult = this.addUserTemplateType(utid, userTemplate.getUttype(), userTemplate.getTypedata());
			}
			if(updateUtName)
			{
				String utname = StringUtils.checkNullString(userTemplate.getTemplatename());
				userTemplateShareDao.executeUpdate("update UserTemplateShare set utname = ? where utid = ?", new Object[] {utname, utid});
			}
			boolean dataResult = true;
			if(updateData && this.deleteUserTemplateData(utid))
			{
				dataResult = this.addUserTemplateData(utid, userTemplate.getUserdata(), userTemplate.getDepdata());
			}
			boolean shareResult = true;
			if(updateShare && this.deleteUserTemplateShare(utid))
			{
				shareResult = this.addUserTemplateShare(utid, userTemplate.getTemplatename(), userTemplate.getUsershare(), userTemplate.getDepshare());
			}
			result = typeResult && dataResult && shareResult;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public UserTemplate getUserTemplateById(String utid)
	{
		if("".equals(StringUtils.checkNullString(utid)))
			return null;
		UserTemplate userTemplate = null;
		try
		{
			userTemplate = userTemplateDao.get(utid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return userTemplate;
	}
	
	public String getUserTemplateNamesByIds(String utids)
	{
		String utInfos = "";
		if("".equals(StringUtils.checkNullString(utids)))
			return utInfos;
		utids = utids.replaceAll(" ", "");
		try
		{
			Map map = UltraSmUtil.getSqlParameter(utids);
			List<UserTemplate> utList = userTemplateDao.find("from UserTemplate where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
			int utLen = 0;
			if(utList != null)
				utLen = utList.size();
			UserTemplate userTemplate;
			utids = "";
			String utNames = "";
			for(int i=0 ; i<utLen ; i++)
			{
				userTemplate = utList.get(i);
				if(!"".equals(utids))
				{
					utids += ",";
					utNames += ",";
				}
				utids += userTemplate.getPid();
				utNames += userTemplate.getTemplatename();
			}
			if(utLen > 0)
			{
				utInfos = utids + ";" + utNames;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return utInfos;
	}
	
	public String getUserTemplateDataByIds(String utids, String idType)
	{
		String utData = "";
		if("".equals(StringUtils.checkNullString(utids)))
			return utData;
		utids = utids.replaceAll(" ", "");
		try
		{
			idType = StringUtils.checkNullString(idType);
			String userIdFieldName = "u.pid";
			if("0".equals(idType))
				userIdFieldName = "u.pid || '-' || u.loginname";
			else if("1".equals(idType))
				userIdFieldName = "u.loginname";
			Map map = UltraSmUtil.getSqlParameter(utids);
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct d.pid orgid, d.depname orgname, utd.orgtype orgtype");
			sql.append("  from bs_t_sm_dep d, bs_t_sm_usertemplatedata utd");
			sql.append(" where d.pid = utd.orgid");
			sql.append("   and utd.utid in (" + map.get("?") + ")");
			sql.append("   and utd.orgtype = 2");
			sql.append("   and d.status = 1");
			sql.append(" union all ");
			sql.append("select distinct "+userIdFieldName+" orgid, u.fullname orgname, utd.orgtype orgtype");
			sql.append("  from bs_t_sm_user u, bs_t_sm_usertemplatedata utd");
			sql.append(" where u.pid = utd.orgid");
			sql.append("   and utd.utid in (" + map.get("?") + ")");
			sql.append("   and utd.orgtype = 1");
			sql.append("   and u.status = 1");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), ArrayTransferUtils.copyArraySimple((Object[]) map.get("obj"), (Object[]) map.get("obj")), 0, 0, 2);
			int tableLen = 0;
			if(table != null)
				tableLen = table.length();
			DataRow row;
			String userIds = "";
			String userNames = "";
			String depIds = "";
			String depNames = "";
			String orgid;
			String orgname;
			String orgtype;
			for(int i=0 ; i<tableLen ; i++)
			{
				row = table.getDataRow(i);
				orgtype = StringUtils.checkNullString(row.getString("orgtype"));
				orgid = StringUtils.checkNullString(row.getString("orgid"));
				orgname = StringUtils.checkNullString(row.getString("orgname"));
				if("2".equals(orgtype))
				{
					if(!"".equals(depIds))
					{
						depIds += ",";
						depNames += ",";
					}
					depIds += orgid;
					depNames += orgname;
				}
				else
				{
					if(!"".equals(userIds))
					{
						userIds += ",";
						userNames += ",";
					}
					userIds += orgid;
					userNames += orgname;
				}
			}
			if(tableLen > 0)
			{
				utData = userIds + ":" + userNames + ";" + depIds + ":" + depNames;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return utData;
	}
	
	private boolean addUserTemplateType(String utid, String uttype, String typeData)
	{
		boolean result = false;
		if("".equals(utid))
			return result;
		try
		{
			UserTemplateType userTemplateType;
			if(!"".equals(StringUtils.checkNullString(typeData)))
			{
				// 格式：mark1,mark2,mark3...;name1,name2,name3...
				String[] subType = typeData.split(";");
				if(subType.length == 2)
				{
					String[] markArray = subType[0].split(",");
					String[] nameArray = subType[1].split(",");
					for(int i=0 ; i<markArray.length ; i++)
					{
						if("".equals(markArray[i]))
							continue;
						userTemplateType = new UserTemplateType();
						userTemplateType.setUtid(utid);
						userTemplateType.setUttype(uttype);
						userTemplateType.setTypemark(markArray[i]);
						userTemplateType.setTypename(nameArray[i]);
						userTemplateTypeDao.save(userTemplateType);
					}
				}
			}
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean deleteUserTemplateType(String utid)
	{
		boolean result = false;
		if("".equals(utid))
			return result;
		try
		{
			userTemplateTypeDao.executeUpdate("delete UserTemplateType where utid = ?", utid);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean addUserTemplateData(String utid, String userIds, String depIds)
	{
		boolean result = false;
		if("".equals(utid))
			return result;
		try
		{
			UserTemplateData userTemplateData;
			if(!"".equals(StringUtils.checkNullString(userIds)))
			{
				String[] userArray = userIds.split(",");
				for(int i=0 ; i<userArray.length ; i++)
				{
					userTemplateData = new UserTemplateData();
					userTemplateData.setUtid(utid);
					userTemplateData.setOrgid(userArray[i]);
					userTemplateData.setOrgtype((long) 1);
					userTemplateDataDao.save(userTemplateData);
				}
			}
			if(!"".equals(StringUtils.checkNullString(depIds)))
			{
				String[] depArray = depIds.split(",");
				for(int i=0 ; i<depArray.length ; i++)
				{
					userTemplateData = new UserTemplateData();
					userTemplateData.setUtid(utid);
					userTemplateData.setOrgid(depArray[i]);
					userTemplateData.setOrgtype((long) 2);
					userTemplateDataDao.save(userTemplateData);
				}
			}
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private boolean deleteUserTemplateData(String utid)
	{
		boolean result = false;
		if("".equals(utid))
			return result;
		try
		{
			userTemplateDataDao.executeUpdate("delete UserTemplateData where utid = ?", utid);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean addUserTemplateShare(String utid, String utname, String userIds, String depIds)
	{
		boolean result = false;
		if("".equals(utid))
			return result;
		try
		{
			UserTemplateShare userTemplateShare;
			if(!"".equals(StringUtils.checkNullString(userIds)))
			{
				String[] userArray = userIds.split(",");
				for(int i=0 ; i<userArray.length ; i++)
				{
					userTemplateShare = new UserTemplateShare();
					userTemplateShare.setUtid(utid);
					userTemplateShare.setUtname(utname);
					userTemplateShare.setOrgid(userArray[i]);
					userTemplateShare.setOrgtype((long) 1);
					userTemplateShareDao.save(userTemplateShare);
				}
			}
			if(!"".equals(StringUtils.checkNullString(depIds)))
			{
				String[] depArray = depIds.split(",");
				DepInfo dep;
				for(int i=0 ; i<depArray.length ; i++)
				{
					userTemplateShare = new UserTemplateShare();
					userTemplateShare.setUtid(utid);
					userTemplateShare.setUtname(utname);
					userTemplateShare.setOrgid(depArray[i]);
					userTemplateShare.setOrgtype((long) 2);
					dep = depManagerService.getDepByID(depArray[i]);
					userTemplateShare.setDepdns(dep!=null ? dep.getDepdns() : "");
					userTemplateShareDao.save(userTemplateShare);
				}
			}
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean deleteUserTemplateShare(String utid)
	{
		boolean result = false;
		if("".equals(utid))
			return result;
		try
		{
			userTemplateDataDao.executeUpdate("delete UserTemplateShare where utid = ?", utid);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public void setUserTemplateDao(IDao<UserTemplate> userTemplateDao) {
		this.userTemplateDao = userTemplateDao;
	}
	public void setUserTemplateTypeDao(IDao<UserTemplateType> userTemplateTypeDao) {
		this.userTemplateTypeDao = userTemplateTypeDao;
	}
	public void setUserTemplateDataDao(IDao<UserTemplateData> userTemplateDataDao) {
		this.userTemplateDataDao = userTemplateDataDao;
	}
	public void setUserTemplateShareDao(IDao<UserTemplateShare> userTemplateShareDao) {
		this.userTemplateShareDao = userTemplateShareDao;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
}
