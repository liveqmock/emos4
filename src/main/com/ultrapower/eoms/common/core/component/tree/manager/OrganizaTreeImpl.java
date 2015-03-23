package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.WorkflowRole;
import com.ultrapower.workflow.role.service.IRoleService;

public class OrganizaTreeImpl extends TreeManager
{
	public final String TREETYPE_ORGANIZA = "Organiza";				//组织机构
	public final String TREETYPE_ROLECHILD = "RoleChild";			//角色细分
	public final String TREETYPE_USERTEMPLATE = "UserTemplate";		//人员模版
	public final String TREETYPE_POSITION = "Position";				//职位
	public final String TREETYPE_CONFIG = "Config";				    //职位
	
	/**
	 * 获取下级树信息的总方法
	 * @param parentid
	 * @param selectId
	 * @param treeType
	 * @param wfVersion
	 * @return
	 */
	public String getCommonTreeData(String parentid, String selectId, String isSelectType, String treeType, String useType, String utType, String typeMark, String wfVersion)
	{
		if("".equals(StringUtils.checkNullString(parentid)))
			return "";
		List<DtreeBean> dtreeBeanList = this.getSubCommonTreeData(parentid, selectId, isSelectType, treeType, useType, utType, typeMark, wfVersion);
		return this.dtreeThansStr(dtreeBeanList, parentid);
	}
	
	/**
	 * 获取人员选择页面的数据（姓的拼音首字母筛选）
	 * @param treeId
	 * @return
	 */
	public String getCommonUserData(String treeId)
	{
		if("".equals(StringUtils.checkNullString(treeId)))
			return "";
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = null;
		DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
		DepInfo depInfo = depManagerService.getDepByID(treeId);
		String depDns = depInfo == null ? "" : StringUtils.checkNullString(depInfo.getDepdns());
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct substr(u.pyname, 1, 1) py, 'U_' || u.pid || '-' || u.loginname userId, (case when u.holidaystate='isHoliday' then u.fullname||'(离岗)' else u.fullname end ) userName");
		sql.append("  from bs_t_sm_user u, bs_t_sm_userdep ud, bs_t_sm_dep d");
		sql.append(" where u.pid = ud.userid");
		sql.append("   and d.pid = ud.depid");
		if(!"".equals(depDns))
		{
			//sql.append("   and d.depdns like (select depdns from bs_t_sm_dep where pid = ?) || '%'");
			sql.append("   and d.depdns like '");
			sql.append(depDns);
			sql.append("%'");
		}
		else
		{
			sql.append("   and d.pid = '");
			sql.append(treeId);
			sql.append("'");
		}
		sql.append("   and u.status = 1 and d.status = 1");
		sql.append(" order by py");
		try
		{
			table = queryAdapter.executeQuery(sql.toString(), null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		int tableLen = 0;
		if(table != null)
			tableLen = table.length();
		String userData = "";
		DataRow row;
		String py;
		String userId;
		String userName;
		for(int i=0 ; i<tableLen ; i++)
		{
			row = table.getDataRow(i);
			py = StringUtils.checkNullString(row.getString("py")).toUpperCase();
			if(py.compareTo("A") >= 0 && py.compareTo("Z") <= 0)
			{
				userId = StringUtils.checkNullString(row.getString("userId"));
				userName = StringUtils.checkNullString(row.getString("userName"));
				if(!"".equals(userData))
					userData += ";";
				userData = userData + py + "," + userId + "," + userName;
			}
		}
		return userData;
	}
	
	/**
	 * 获取人员选择页面数据（职位来筛选）
	 * @param treeId
	 * @return
	 */
	public String getCommonUserPosition(String treeId)
	{
		if("".equals(StringUtils.checkNullString(treeId)))
			return "";
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = null;
		if(treeId.indexOf(":") < 0)
		{
			DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
			DepInfo depInfo = depManagerService.getDepByID(treeId);
			String depDns = depInfo == null ? "" : StringUtils.checkNullString(depInfo.getDepdns());
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct p.pid posId, p.positionname posName, u.pid || '-' || u.loginname userId, u.fullname userName, p.orderNum");
			sql.append("  from bs_t_sm_position     p,");
			sql.append("       bs_t_sm_positiondep  pd,");
			sql.append("       bs_t_sm_positionuser pu,");
			sql.append("       bs_t_sm_user         u,");
			sql.append("       bs_t_sm_userdep      ud,");
			sql.append("       bs_t_sm_dep          d");
			sql.append(" where p.pid = pd.posid");
			sql.append("   and d.pid = pd.depid");
			sql.append("   and p.pid = pu.posid");
			sql.append("   and u.pid = pu.userid");
			sql.append("   and d.pid = ud.depid");
			sql.append("   and u.pid = ud.userid");
			if(!"".equals(depDns))
			{
				//sql.append("   and d.depdns like (select depdns from bs_t_sm_dep where pid = ?) || '%'");
				sql.append("   and d.depdns like '");
				sql.append(depDns);
				sql.append("%'");
			}
			else
			{
				sql.append("   and d.pid = '");
				sql.append(treeId);
				sql.append("'");
			}
			sql.append(" order by p.ordernum, u.fullname");
			try
			{
				table = queryAdapter.executeQuery(sql.toString(), null);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			String[] ids = treeId.split(":");
			String depid = ids[0];
			treeId = ids[1];
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct p.pid posId, p.positionname posName, u.pid || '-' || u.loginname userId, u.fullname userName, p.orderNum");
			sql.append("  from bs_t_sm_position p, bs_t_sm_positionuser pu, bs_t_sm_user u,");
			sql.append("       (select posid from bs_t_sm_positiondep where depid = ?) pd,");
			sql.append("       (select userid from bs_t_sm_userdep where depid = ?) ud");
			sql.append(" where p.pid = pd.posid");
			sql.append("   and p.pid = pu.posid");
			sql.append("   and u.pid = pu.userid");
			sql.append("   and u.pid = ud.userid");
			sql.append("   and p.pid = ?");
			sql.append(" order by p.ordernum, u.fullname");
			try
			{
				table = queryAdapter.executeQuery(sql.toString(), new Object[] {depid, depid, treeId});
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		int tableLen = 0;
		if(table != null)
			tableLen = table.length();
		StringBuffer userData = new StringBuffer();
		DataRow row;
		String posId;
		String posName;
		String oldPosId = "";
		String userId;
		String userName;
		for(int i=0;i<tableLen;i++)
		{
			row = table.getDataRow(i);
			posId = StringUtils.checkNullString(row.getString("posId"));
			posName = StringUtils.checkNullString(row.getString("posName"));
			userId = StringUtils.checkNullString(row.getString("userId"));
			userName = StringUtils.checkNullString(row.getString("userName"));
			posId = "P_" + posId;
			userId = "U_" + userId;
			if(!oldPosId.equals(posId))
			{
				userData.append(";");
				userData.append(posId);
				userData.append(",");
				userData.append(posName);
				oldPosId = posId;
			}
			userData.append(":");
			userData.append(userId);
			userData.append(",");
			userData.append(userName);
		}
		//posid1,posname1:userid1,username1:userid2,username2:...;posid2,posname2:userid1,username1:userid2,username2:...;...
		return "".equals(userData.toString()) ? "" : userData.toString().substring(1);
	}
	
	public List<ChildRole> getCommonUserRoleChild(String wfRole, String crName)
	{
		IRoleService wfRoleManager = (IRoleService) WebApplicationManager.getBean("roleService");
		List<ChildRole> crList = null;
		if(wfRoleManager != null)
			crList = wfRoleManager.getChildRoleFromTree(wfRole, crName);
		return crList;
	}
	
	/**
	 * 查询树节点信息的总方法
	 * @param rearchInfo
	 * @param selectId
	 * @param treeType
	 * @return
	 */
	public String getCommonRearchData(String rearchInfo, String selectId, String isSelectType, String treeType, String useType, String utType, String typeMark)
	{
		List<DtreeBean> dtreeBeanList = null;
		if(TREETYPE_ORGANIZA.equals(treeType))
			dtreeBeanList = this.commonRearchOrganiza(rearchInfo, selectId, isSelectType, treeType, useType, utType, typeMark);
		else if(TREETYPE_ROLECHILD.equals(treeType))
			;//dtreeBeanList = this.rearchRoleChild(rearchInfo, selectId);
		else if(TREETYPE_USERTEMPLATE.equals(treeType))
			dtreeBeanList = this.commonRearchOrganiza(rearchInfo, selectId, isSelectType, treeType, useType, utType, typeMark);
		else if(TREETYPE_POSITION.equals(treeType))
			;//dtreeBeanList = this.rearchPosition(rearchInfo, selectId);
		return this.apposeDhtmlXtreeXml(dtreeBeanList);
	}
	
	private List<DtreeBean> commonRearchOrganiza(String rearchInfo, String selectId, String isSelectType, String treeType, String useType, String utType, String typeMark)
	{
		List<DtreeBean> dtreeBeanList = null;
		if("".equals(StringUtils.checkNullString(rearchInfo)))
			return dtreeBeanList;
		useType = StringUtils.checkNullString(useType);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		if(TREETYPE_ORGANIZA.equals(treeType) || !"myself".equals(useType))
		{
			// 查询的部门信息
			sql.append("select d.pid treeid, d.depname treetext, 'D' treetype, '1' orderby, d.ordernum ordernum, d.grouptype priparam1, d.parentid priparam2");
			sql.append("  from bs_t_sm_dep d");
			sql.append(" where 1=1");
			sql.append("   and depname like '%" + rearchInfo + "%'");
			if(!"0".equals(isSelectType) || !"myself".equals(useType))
			{
				sql.append(" union all ");
				// 查询的用户信息
				sql.append("select u.pid || '-' || u.loginname treeid, ( case when u.holidaystate ='isHoliday' then '(离岗)'||u.fullname||'['||u.loginname||b.depfullname||']'  else u.fullname||'['||u.loginname||b.depfullname||']'  end)  treetext, 'U' treetype, '2' orderby, u.ordernum ordernum, u.loginname priparam1, u.depname priparam2"); //grouptype和parentid是查部门时用的，这里是冗余字段
				sql.append("  from bs_t_sm_user u  ,bs_t_sm_dep b");
				sql.append(" where 1=1  and  u.depid =b.pid");
				sql.append("   and (lower(pyname) like '%' || lower('" + rearchInfo + "') || '%'");
				sql.append("     or fullname like '%" + rearchInfo + "%'");
				sql.append("     or loginname like '%" + rearchInfo + "%')");
			}
		}
		if(TREETYPE_USERTEMPLATE.equals(treeType) || (!"myself".equals(useType) && !"noTemplate".equals(useType)))
		{
			UserSession userSession = ActionContext.getUserSession();
			String userid = "";
			if(userSession != null)
				userid = StringUtils.checkNullString(userSession.getPid());
			if(!"myself".equals(useType))
				sql.append(" union all ");
			// 查询的模版信息
			sql.append("select ut.pid treeid, ut.templatename treetext, 'T' treetype, '0' orderby, ut.ordernum ordernum, '' priparam1, '' priparam2"); //grouptype和parentid是查部门时用的，这里是冗余字段
			if("".equals(StringUtils.checkNullString(utType)))
			{
				sql.append("  from bs_t_sm_usertemplate ut");
				sql.append(" where ut.templatename like '%" + rearchInfo + "%'");
				sql.append("   and ut.status = 1 and ut.creater = '" + userid + "'");
			}
			else if(!"".equals(StringUtils.checkNullString(typeMark)) && !"ALL".equals(StringUtils.checkNullString(typeMark)))
			{
				sql.append("  from bs_t_sm_usertemplate ut, bs_t_sm_usertemplatetype utt");
				sql.append(" where ut.pid = utt.utid and ut.templatename like '%" + rearchInfo + "%'");
				sql.append("   and (utt.typemark = '" + typeMark + "' or ut.typedata is null or ut.typedata = '' or ut.typedata = 'ALL')");
				sql.append("   and ut.status = 1 and ut.creater = '" + userid + "'");
				sql.append("   and ut.uttype = '" + utType + "'");
			}
			else
			{
				sql.append("  from bs_t_sm_usertemplate ut");
				sql.append(" where ut.templatename like '%" + rearchInfo + "%'");
				sql.append("   and ut.status = 1 and ut.creater = '" + userid + "'");
				sql.append("   and ut.uttype = '" + utType + "'");
			}
		}
		sql.append(") order by orderby, ordernum");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = queryAdapter.executeQuery(sql.toString(), null);
		int tableLen = 0;
		String selectUser = "";
		String selectDep = "";
		String selectTemplate = "";
		if(table != null)
		{
			tableLen = table.length();
			dtreeBeanList = new ArrayList<DtreeBean> ();
			String[] selectData = selectId.split(";");
			if(selectData.length > 0)
			{
				selectUser = selectData[0];
				if(selectData.length > 1)
					selectDep = selectData[1];
				if(selectData.length > 2)
					selectTemplate = selectData[2];
			}
		}
		DataRow row;
		DtreeBean dtreeBean;
		for(int i=0;i<tableLen;i++)
		{
			row = table.getDataRow(i);
			dtreeBean = new DtreeBean();
			String tmpid = StringUtils.checkNullString(row.getString("treeid"));
			String treetext = StringUtils.checkNullString(row.getString("treetext"));
			String treetype = StringUtils.checkNullString(row.getString("treetype"));
			String treeid = treetype + "_" + tmpid;
			dtreeBean.setId(treeid);
			dtreeBean.setText(treetext);
			HashMap map = new HashMap();
			map.put("id", treeid);
			map.put("text", treetext);
			map.put("type", treetype);
			if("U".equals(treetype))
			{
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
				dtreeBean.setChild("");
				dtreeBean.setSelect("1"); 
				if(selectUser.indexOf(tmpid) >= 0)
					dtreeBean.setChecked("1");
				String loginname = StringUtils.checkNullString(row.getString("priparam1"));
				String depname = StringUtils.checkNullString(row.getString("priparam2"));
				map.put("loginname", loginname);
				map.put("depname", depname);
			}
			else if("D".equals(treetype))
			{
				String grouptype = StringUtils.checkNullString(row.getString("priparam1"));
//				if("99".equals(grouptype))
//				{
//					String parentid = StringUtils.checkNullString(row.getString("priparam2"));
//					DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
//					DepInfo dep = depManagerService.getDepByID(parentid);
//					if(dep != null && "99".equals(StringUtils.checkNullString(dep.getGrouptype())))
//						continue;
//				}
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
				if("1".equals(isSelectType))
					dtreeBean.setNocheckbox("0");
				else if(selectDep.indexOf(tmpid) >= 0)
					dtreeBean.setChecked("1");
			}
			else if("T".equals(treetype))
			{
				dtreeBean.setIm0("userplat.png");
				dtreeBean.setIm1("userplat.png");
				dtreeBean.setIm2("userplat.png");
				if(selectTemplate.indexOf(tmpid) >= 0)
					dtreeBean.setChecked("1");
			}
			else
			{
				continue;
			}
			dtreeBean.setUserdata(map);
			dtreeBeanList.add(dtreeBean);
		}
		return dtreeBeanList;
	}

	public String getSubConfigData(String parentid, String selectId,
			String isSelectType, String treeType, String act, String step,
			String fieldID) {
		return getSubConfigData(null, parentid, selectId, isSelectType,
				treeType, act, step, fieldID);
	}

	public String getSubConfigData(String rearchInfo, String parentid,
			String selectId, String isSelectType, String treeType, String act,
			String step, String fieldID) {
		List<DtreeBean> dtreeBeanList = null;
		boolean checked = false;
		
		int _dic = parentid.indexOf("_");
		if (_dic < 0) {
			dtreeBeanList = this.getCommonConfigChild(parentid, false, selectId, act, step, fieldID, rearchInfo,isSelectType);
			return this.createDhtmlxtreeXml(dtreeBeanList, "0");
		} else {
			String pid = parentid.substring(_dic + 1);
			dtreeBeanList = this.getCommonConfigChild(pid, false, selectId, act, step, fieldID, rearchInfo,isSelectType);
			return this.createDhtmlxtreeXml(dtreeBeanList, parentid);
		}
	}
	
	private List<DtreeBean> getSubCommonTreeData(String parentid, String selectId, String isSelectType, String treeType, String useType, String utType, String typeMark, String wfVersion)
	{
		List<DtreeBean> dtreeBeanList = null;
		treeType = StringUtils.checkNullString(treeType);
		boolean checked = true;
		if(TREETYPE_POSITION.equals(treeType))
			checked = false;
		if("1".equals(StringUtils.checkNullString(isSelectType)))
			checked = false;
		int _dic = parentid.indexOf("_");
		if (_dic < 0) {
			if (TREETYPE_ORGANIZA.equals(treeType)) {// 组织机构
				dtreeBeanList = this.getCommonOrganiza(parentid, checked, selectId);
			} else if (TREETYPE_USERTEMPLATE.equals(treeType)) {// 人员模版
				dtreeBeanList = this.getCommonUserTemplate(parentid, selectId, useType, utType, typeMark);
			} else if (TREETYPE_POSITION.equals(treeType)) {// 职位
				dtreeBeanList = this.getCommonOrganiza(parentid, checked, "");
			} else if (TREETYPE_ROLECHILD.equals(treeType)) {// 角色细分
				dtreeBeanList = this.getCommonRoleChild("0", selectId, wfVersion);
			}
		}
		else
		{
			String treeid = parentid.substring(_dic + 1);
			String treetype = parentid.substring(0, _dic);
			if(TREETYPE_POSITION.equals(treeType))// 职位比较特殊，需要把职位展示在组织机构中
			{
				dtreeBeanList = this.getCommonPosition(treeid);
			}
			else
			{
				if("T".equals(treetype))
				{
					dtreeBeanList = this.getCommonUserTemplate(treeid, selectId, useType, utType, typeMark);
				}
				else if("D".equals(treetype))
				{
					dtreeBeanList = this.getCommonOrganiza(treeid, checked, selectId);
				}
				else if("R".equals(treetype))
				{
					dtreeBeanList = this.getCommonRoleChild(treeid, selectId, wfVersion);
				}
			}
		}
		return dtreeBeanList;
	}
	
	private List<DtreeBean> getCommonOrganiza(String parentid, boolean checked, String selectId)
	{
		List<DtreeBean> dtreeBeanList = null;
		selectId = StringUtils.checkNullString(selectId);
		String sql = "select d.pid treeid, d.depname treetext, 'D' treetype, d.grouptype grouptype from bs_t_sm_dep d where d.parentid = ? and status = 1 order by d.ordernum";
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = queryAdapter.executeQuery(sql, new Object[] {parentid});
		int tableLen = 0;
		if(table != null)
		{
			tableLen = table.length();
			dtreeBeanList = new ArrayList<DtreeBean> ();
		}
		DataRow row;
		DtreeBean dtreeBean;
		for(int i=0;i<tableLen;i++)
		{
			row = table.getDataRow(i);
			dtreeBean = new DtreeBean();
			String treeid = StringUtils.checkNullString(row.getString("treeid"));
			String treetext = StringUtils.checkNullString(row.getString("treetext"));
			String treetype = StringUtils.checkNullString(row.getString("treetype"));
			String grouptype = StringUtils.checkNullString(row.getString("grouptype"));
			if("99".equals(grouptype))
				dtreeBean.setChild("0");
			if(!checked)
				dtreeBean.setNocheckbox("0");
			else if(selectId.indexOf(treeid) >= 0)
				dtreeBean.setChecked("1");
			treeid = treetype + "_" + treeid;
			dtreeBean.setId(treeid);
			dtreeBean.setText(treetext);
			HashMap map = new HashMap();
			map.put("id", treeid);
			map.put("text", treetext);
			map.put("type", treetype);
			map.put("parentid", parentid);
			dtreeBean.setIm0("groupImg.png");
			dtreeBean.setIm1("groupImg.png");
			dtreeBean.setIm2("groupImg.png");
			dtreeBean.setUserdata(map);
//			if("0".equals(parentid))
//			{
//				dtreeBean.setOpen("1");
//			}
			dtreeBeanList.add(dtreeBean);
		}
		return dtreeBeanList;
	}
	
	private List<DtreeBean> getCommonUserTemplate(String parentid, String selectId, String useType, String utType, String typeMark)
	{
		List<DtreeBean> dtreeBeanList = null;
		if("0".equals(parentid))
		{
			//返回默认3个人员模版节点分类
			String[] userTemplateType = {"我的私有模版", "我的共享模版", "公共模版"};
			String[] userTemplateId = {"T_myself", "T_myshare", "T_shared"};
			DtreeBean dtreeBean;
			dtreeBeanList = new ArrayList<DtreeBean> ();
			for(int i=0 ; i<userTemplateId.length ; i++)
			{
				dtreeBean = new DtreeBean();
				dtreeBean.setId(userTemplateId[i]);
				dtreeBean.setText(userTemplateType[i]);
				HashMap map = new HashMap();
				map.put("id", userTemplateId[i]);
				map.put("text", userTemplateType[i]);
				map.put("type", "T");
				map.put("parentid", parentid);
				dtreeBean.setIm0("userplat.png");
				dtreeBean.setIm1("userplat.png");
				dtreeBean.setIm2("userplat.png");
				if(i == 0)
					dtreeBean.setOpen("1");
				dtreeBean.setNocheckbox("0");
				dtreeBean.setUserdata(map);
				dtreeBeanList.add(dtreeBean);
			}
		}
		else if("myself".equals(parentid) || "myshare".equals(parentid))
		{
			String userid = "";
			UserSession userSession = ActionContext.getUserSession();
			if(userSession != null)
				userid = StringUtils.checkNullString(userSession.getPid());
			StringBuffer sql = new StringBuffer();
			sql.append("select ut.pid treeid, ut.templatename treetext, 'T' treetype");
			if("".equals(StringUtils.checkNullString(utType)))
			{
				sql.append("  from bs_t_sm_usertemplate ut");
				sql.append(" where ut.status = 1 and ut.creater = ? and ut.isshare = ");
				sql.append(parentid.equals("myself") ? "0" : "1");
				sql.append(" order by ut.ordernum");
			}
			else if (!"".equals(StringUtils.checkNullString(typeMark)) && !"ALL".equals(StringUtils.checkNullString(typeMark)))
			{
				sql.append("  from bs_t_sm_usertemplate ut, bs_t_sm_usertemplatetype utt");
				sql.append(" where ut.pid = utt.utid (+)");
				sql.append("   and ut.status = 1 and ut.uttype = '" + utType + "'");
				sql.append("   and (utt.typemark = '" + typeMark + "' or ut.typedata is null or ut.typedata = '' or ut.typedata = 'ALL')");
				sql.append("   and ut.creater = ? and ut.isshare = ");
				sql.append(parentid.equals("myself") ? "0" : "1");
				sql.append(" order by ut.ordernum");
			}
			else
			{
				sql.append("  from bs_t_sm_usertemplate ut");
				sql.append(" where ut.status = 1 and ut.creater = ? and ut.isshare = ");
				sql.append(parentid.equals("myself") ? "0" : "1");
				sql.append("   and ut.uttype = '" + utType + "'");
				sql.append(" order by ut.ordernum");
			}
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), new Object[] {userid});
			int tableLen = 0;
			if(table != null)
			{
				tableLen = table.length();
				dtreeBeanList = new ArrayList<DtreeBean> ();
			}
			DataRow row;
			DtreeBean dtreeBean;
			for(int i=0;i<tableLen;i++)
			{
				row = table.getDataRow(i);
				dtreeBean = new DtreeBean();
				String treeid = StringUtils.checkNullString(row.getString("treeid"));
				String treetext = StringUtils.checkNullString(row.getString("treetext"));
				String treetype = StringUtils.checkNullString(row.getString("treetype"));
				if(selectId.indexOf(treeid) >= 0)
					dtreeBean.setChecked("1");
				treeid = treetype + "_" + treeid;
				dtreeBean.setId(treeid);
				dtreeBean.setText(treetext);
				HashMap map = new HashMap();
				map.put("id", treeid);
				map.put("text", treetext);
				map.put("type", treetype);
				map.put("parentid", parentid);
				dtreeBean.setIm0("userplat.png");
				dtreeBean.setIm1("userplat.png");
				dtreeBean.setIm2("userplat.png");
				dtreeBean.setUserdata(map);
				dtreeBeanList.add(dtreeBean);
			}
		}
		else if("shared".equals(parentid))
		{
			UserSession userSession = ActionContext.getUserSession();
			String userid = "";
			String groupid = "";
			if(userSession != null)
			{
				userid = StringUtils.checkNullString(userSession.getPid());
				groupid = StringUtils.checkNullString(userSession.getGroupId()).replaceAll(" ", "");
				String depdns = "";
				if(!"".equals(groupid))
				{
					DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
					if(depManagerService != null)
					{
						List<DepInfo> depList = depManagerService.getDepByID(UltraSmUtil.arrayToList(groupid.split(",")));
						int depLen = 0;
						if(depList != null)
							depLen = depList.size();
						DepInfo dep;
						for(int i=0 ; i<depLen ; i++)
						{
							dep = depList.get(i);
							if(!"".equals(depdns))
								depdns += ",";
							depdns += this.getAllParentDns(dep.getDepdns());
						}
					}
				}
				StringBuffer sql = new StringBuffer();
				Map dnsMap = UltraSmUtil.getSqlParameter(depdns);
				Object[] obj = new Object[] {userid, userid};
				sql.append("select ut.pid treeid, ut.templatename treetext, 'T' treetype");
				sql.append("  from bs_t_sm_usertemplate ut, bs_t_sm_usertemplateshare uts");
				sql.append(" where ut.pid = uts.utid");
				sql.append("   and ut.status = 1");
				sql.append("   and ut.creater <> ?");
				sql.append("   and ut.isshare = 1");
				sql.append("   and ((uts.orgtype = 1 and uts.orgid = ?)");
				if(!"".equals(depdns))
				{
					sql.append("     or (uts.orgtype = 2 and uts.depdns in (");
					sql.append(dnsMap.get("?"));
					sql.append("        ))");
					try {
						obj = ArrayTransferUtils.copyArraySimple(obj, (Object[]) dnsMap.get("obj"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sql.append(")");
				sql.append(" order by ut.ordernum");
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sql.toString(), obj);
				int tableLen = 0;
				if(table != null)
				{
					tableLen = table.length();
					dtreeBeanList = new ArrayList<DtreeBean> ();
				}
				DataRow row;
				DtreeBean dtreeBean;
				for(int i=0;i<tableLen;i++)
				{
					row = table.getDataRow(i);
					dtreeBean = new DtreeBean();
					String treeid = StringUtils.checkNullString(row.getString("treeid"));
					String treetext = StringUtils.checkNullString(row.getString("treetext"));
					String treetype = StringUtils.checkNullString(row.getString("treetype"));
					if(selectId.indexOf(treeid) >= 0)
						dtreeBean.setChecked("1");
					treeid = treetype + "_" + treeid;
					dtreeBean.setId(treeid);
					dtreeBean.setText(treetext);
					HashMap map = new HashMap();
					map.put("id", treeid);
					map.put("text", treetext);
					map.put("type", treetype);
					map.put("parentid", parentid);
					dtreeBean.setIm0("userplat.png");
					dtreeBean.setIm1("userplat.png");
					dtreeBean.setIm2("userplat.png");
					dtreeBean.setUserdata(map);
					dtreeBeanList.add(dtreeBean);
				}
			}
		}
		else
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select * from (");
			sql.append("select distinct d.pid treeid, d.depname treetext, 'D' treetype, d.ordernum ordernum");
			sql.append("  from bs_t_sm_dep d, bs_t_sm_usertemplatedata utd");
			sql.append(" where d.pid = utd.orgid");
			sql.append("   and utd.utid = ? and utd.orgtype = 2");
			sql.append("   and d.status = 1");
			sql.append(" union all ");
			sql.append("select distinct u.pid || '-' || u.loginname treeid, u.fullname treetext, 'U' treetype, u.ordernum ordernum");
			sql.append("  from bs_t_sm_user u, bs_t_sm_usertemplatedata utd");
			sql.append(" where u.pid = utd.orgid");
			sql.append("   and utd.utid = ? and utd.orgtype = 1");
			sql.append("   and u.status = 1");
			sql.append(") order by treetype, ordernum");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql.toString(), new Object[] {parentid, parentid});
			int tableLen = 0;
			if(table != null)
			{
				tableLen = table.length();
				dtreeBeanList = new ArrayList<DtreeBean> ();
			}
			DataRow row;
			DtreeBean dtreeBean;
			for(int i=0;i<tableLen;i++)
			{
				row = table.getDataRow(i);
				dtreeBean = new DtreeBean();
				String treeid = StringUtils.checkNullString(row.getString("treeid"));
				String treetext = StringUtils.checkNullString(row.getString("treetext"));
				String treetype = StringUtils.checkNullString(row.getString("treetype"));
				if(selectId.indexOf(treeid) >= 0)
					dtreeBean.setChecked("1");
				treeid = treetype + "_" + treeid;
				dtreeBean.setId(treeid);
				dtreeBean.setText(treetext);
				if("myself".equals(StringUtils.checkNullString(useType)))
				{
					dtreeBean.setNocheckbox("0");
					dtreeBean.setChild("");
				}
				HashMap map = new HashMap();
				map.put("id", treeid);
				map.put("text", treetext);
				map.put("type", treetype);
				map.put("parentid", parentid);
				if("D".equals(treetype))
				{
					dtreeBean.setIm0("groupImg.png");
					dtreeBean.setIm1("groupImg.png");
					dtreeBean.setIm2("groupImg.png");
					
				}
				else
				{
					dtreeBean.setIm0("userImg.png");
					dtreeBean.setIm1("userImg.png");
					dtreeBean.setIm2("userImg.png");
					dtreeBean.setChild("");
				}
				dtreeBean.setUserdata(map);
				dtreeBeanList.add(dtreeBean);
			}
		}
		return dtreeBeanList;
	}
	
	public List<DtreeBean> getCommonPosition(String parentid)
	{
		List<DtreeBean> dtreeBeanList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		sql.append("select d.pid treeid, d.depname treetext, 'D' treetype, d.ordernum ordernum, d.grouptype grouptype");
		sql.append("  from bs_t_sm_dep d");
		sql.append(" where d.parentid = ? and status = 1");
		if(!"0".equals(parentid))
		{
			sql.append(" union all ");
			sql.append("select p.pid treeid, p.positionname treetext, 'P' treetype, p.ordernum ordernum, '' grouptype");
			sql.append("  from bs_t_sm_position p, bs_t_sm_positiondep pd");
			sql.append(" where p.pid = pd.posid");
			sql.append("   and pd.depid = '" + parentid + "'");
			sql.append("   and p.status = 1");
		}
		sql.append(") order by treetype, ordernum");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = queryAdapter.executeQuery(sql.toString(), new Object[] {parentid});
		int tableLen = 0;
		if(table != null)
		{
			tableLen = table.length();
			dtreeBeanList = new ArrayList<DtreeBean> ();
		}
		DataRow row;
		DtreeBean dtreeBean;
		for(int i=0 ; i<tableLen ; i++)
		{
			row = table.getDataRow(i);
			dtreeBean = new DtreeBean();
			String treeid = StringUtils.checkNullString(row.getString("treeid"));
			String treetext = StringUtils.checkNullString(row.getString("treetext"));
			String treetype = StringUtils.checkNullString(row.getString("treetype"));
			if("P".equals(treetype))
				treeid = parentid + ":" + treeid;
			treeid = treetype + "_" + treeid;
			dtreeBean.setId(treeid);
			dtreeBean.setText(treetext);
			HashMap map = new HashMap();
			map.put("id", treeid);
			map.put("text", treetext);
			map.put("type", treetype);
			map.put("parentid", parentid);
			if("D".equals(treetype))
			{
				String grouptype = StringUtils.checkNullString(row.getString("grouptype"));
				if("99".equals(grouptype))
					dtreeBean.setChild("0");
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
				dtreeBean.setNocheckbox("0");
			}
			else
			{
				dtreeBean.setIm0("userImg_01.png");
				dtreeBean.setIm1("userImg_01.png");
				dtreeBean.setIm2("userImg_01.png");
				dtreeBean.setChild("");
				dtreeBean.setNocheckbox("0");
			}
			dtreeBeanList.add(dtreeBean);
		}
		return dtreeBeanList;
	}
	
	public List<DtreeBean> getCommonRoleChild(String parentid, String selectId, String wfVersion)
	{
		List<DtreeBean> dtreeBeanList = null;
		IRoleService wfRoleManager = (IRoleService) WebApplicationManager.getBean("roleService");
		if("0".equals(parentid))
		{
			List<WorkflowRole> wfRoleList = wfRoleManager.getWfRoleBySchema(wfVersion);
			int wfRoleLen = 0;
			if(wfRoleList != null)
			{
				wfRoleLen = wfRoleList.size();
				dtreeBeanList = new ArrayList<DtreeBean> ();
			}
			WorkflowRole wfRole;
			DtreeBean dtreeBean;
			for(int i=0 ; i<wfRoleLen ; i++)
			{
				wfRole = wfRoleList.get(i);
				dtreeBean = new DtreeBean();
				String treeid = StringUtils.checkNullString(wfRole.getRolecode());
				String treetext = StringUtils.checkNullString(wfRole.getRolename());
				String treetype = "R";
				treeid = treetype + "_" + treeid;
				dtreeBean.setId(treeid);
				dtreeBean.setText(treetext);
				HashMap map = new HashMap();
				map.put("id", treeid);
				map.put("text", treetext);
				map.put("type", treetype);
				map.put("parentid", "0");
				dtreeBean.setUserdata(map);
				dtreeBean.setChild("0");
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
				dtreeBean.setNocheckbox("1");
				dtreeBeanList.add(dtreeBean);
			}
		}
		else
		{
			List<ChildRole> crList = wfRoleManager.getChildRoleListByRoleCode(parentid);
			int crLen = 0;
			if(crList != null)
			{
				crLen = crList.size();
				dtreeBeanList = new ArrayList<DtreeBean> ();
			}
			ChildRole childRole;
			DtreeBean dtreeBean;
			for(int i=0 ; i<crLen ; i++)
			{
				childRole = crList.get(i);
				dtreeBean = new DtreeBean();
				String treeid = StringUtils.checkNullString(childRole.getChildRoleId());
				String treetext = StringUtils.checkNullString(childRole.getChildRoleName());
				String treetype = "R";
				treeid = treetype + "_" + treeid;
				dtreeBean.setId(treeid);
				dtreeBean.setText(treetext);
				HashMap map = new HashMap();
				map.put("id", treeid);
				map.put("text", treetext);
				map.put("type", treetype);
				map.put("parentid", parentid);
				dtreeBean.setUserdata(map);
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
				dtreeBean.setChild("");
				dtreeBeanList.add(dtreeBean);
			}
		}
		return dtreeBeanList;
	}
	
	public List<DtreeBean> getCommonConfigChild(String parentid,
			boolean checked, String selectId, String act, String step, String filedID, String rearchInfo,String isSelectType) {
		List<DtreeBean> dtreeBeanList = new ArrayList<DtreeBean>();
		QueryAdapter queryAdapter = new QueryAdapter();
		DataRow row;
		
		String cfSql = "SELECT * FROM BS_T_BPP_ASSINGTREECONFIG WHERE FIELDNAME=?";
		DataTable cfTable;
		if (!act.equals("") && !step.equals("")) {
			cfSql += " AND STEPNO=? AND ACTIONNAME=?";
			cfTable = queryAdapter.executeQuery(cfSql, new Object[] { filedID, step, act });
		} else {
			cfTable = queryAdapter.executeQuery(cfSql, new Object[] { filedID });
		}
		if (cfTable != null && cfTable.length() >0) {
			DataRow configRow = cfTable.getDataRow(0);
			String configId = configRow.getString("ID");
			String sql = "SELECT USERDEPINFO.TREEID, USERDEPINFO.TREETEXT TREETEXT, USERDEPINFO.TREETYPE, USERDEPINFO.GROUPTYPE "
					+ "FROM BS_T_BPP_ASSINGTREEORGANIZE O, ("
					+ "SELECT PID,PID TREEID, DEPNAME TREETEXT, 'D' TREETYPE, GROUPTYPE "
					+ "FROM BS_T_SM_DEP T WHERE STATUS = 1"
					+ (rearchInfo == null ? " AND PARENTID = ?"
							: " AND DEPNAME LIKE '%" + rearchInfo + "%'")
					+ " UNION ALL SELECT SYSUSER.PID, SYSUSER.PID || '-' || SYSUSER.LOGINNAME TREEID, "
					+ "SYSUSER.FULLNAME TREETEXT, 'U' TREETYPE, '' GROUPTYPE "
					+ "FROM BS_T_SM_USERDEP USERDEP, BS_T_SM_USER SYSUSER, BS_T_SM_DEP DEP "
					+ "WHERE USERDEP.USERID = SYSUSER.PID AND USERDEP.DEPID = DEP.PID"
					+ (rearchInfo == null ? " AND DEP.PID = ?"
							: " AND (LOWER(SYSUSER.PYNAME) LIKE '%' || LOWER('" + rearchInfo
									+ "') || '%' OR SYSUSER.FULLNAME LIKE '%" + rearchInfo
									+ "%' OR SYSUSER.LOGINNAME LIKE '%" + rearchInfo + "%')")
					+ ") USERDEPINFO WHERE USERDEPINFO.PID = O.ORGANIZEID "
					+ (rearchInfo == null ? "AND O.PARENTORGID = ? ":"")
					+ "AND O.CONFIGID=?";
			DataTable table;
			if (rearchInfo == null)
				table = queryAdapter.executeQuery(sql, new Object[] { parentid,
						parentid, parentid, configId });
			else
				table = queryAdapter.executeQuery(sql, configId);
			int tableLen = 0;
			if (table != null) {
				tableLen = table.length();
			}
			DtreeBean dtreeBean;
			for (int i = 0; i < tableLen; i++) {
				row = table.getDataRow(i);
				dtreeBean = new DtreeBean();
				String treeid = StringUtils.checkNullString(row.getString("TREEID"));
				String treetext = StringUtils.checkNullString(row.getString("TREETEXT"));
				String treetype = StringUtils.checkNullString(row.getString("TREETYPE"));
				//treetype 部门：D，人员：U
				//isSelectType 部门：0，人员：1， 部门与人员：2
				checked = false;
				if("D".equals(treetype)&&"0".equals(isSelectType))
				{
					checked = true;
				}
				if("U".equals(treetype)&&"1".equals(isSelectType))
				{
					checked = true;
				}
				if("2".equals(isSelectType)){
					checked = true;
				}
				if (!checked)
					dtreeBean.setNocheckbox("0");
				else if (selectId.indexOf(treeid) >= 0)
					dtreeBean.setChecked("1");
				
				//treeid = treetype + "_" + treeid;
				//fanying 2013-4-24 描述：当一个人员属于两个或者更多组时，选择人员后会出现错误信息，
				//所以进行修改，为了区分同一人员在不同虚拟组的情况，将人员节点id后追加部门id信息，用两个下划线__ 连接以示区分。
				if("U".equals(treetype)){
					treeid = treetype + "_" + treeid+"__"+parentid;
				}else{
					treeid = treetype + "_" + treeid;
				}
				dtreeBean.setId(treeid);
				dtreeBean.setText(treetext);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", treeid);
				map.put("text", treetext);
				map.put("type", treetype);
				map.put("parentid", parentid);
				dtreeBean.setUserdata(map);
				if ("D".equals(treetype)) {
					String grouptype = StringUtils.checkNullString(row.getString("GROUPTYPE"));
					if ("99".equals(grouptype))
						dtreeBean.setChild("0");
					dtreeBean.setIm0("groupImg.png");
					dtreeBean.setIm1("groupImg.png");
					dtreeBean.setIm2("groupImg.png");
					//dtreeBean.setNocheckbox("");
				} else {
					dtreeBean.setIm0("userImg_01.png");
					dtreeBean.setIm1("userImg_01.png");
					dtreeBean.setIm2("userImg_01.png");
					dtreeBean.setChild("");
					//dtreeBean.setNocheckbox("");
				}
				dtreeBeanList.add(dtreeBean);
			}
		}
		return dtreeBeanList;
	}
	
	public String getCommonTreeDataApporve(String approve, String selectId, String rearchUserOrDep)
	{
		List<DtreeBean> dtreeBeanList = null;
		String[] appArray = approve.split(";");
		if(appArray.length >= 3)
		{
			String baseSchema = appArray[1];
			String companyId = appArray[2];
			if(!"".equals(baseSchema) && !"".equals(companyId))
			{
				StringBuffer sql = new StringBuffer();
				sql.append("select distinct u.pid || '-' || u.loginname treeid, u.fullname treetext, 'U' treetype, u.ordernum");
				sql.append("  from bs_t_sm_user u, bs_t_wf_approve t");
				sql.append(" where 1 = 1");
				sql.append("   and u.loginname = t.loginname");
				sql.append("   and t.baseschema = ?");
				sql.append("   and t.companyid = ?");
				if(appArray.length == 4 && !"".equals(appArray[3]))
				{
					sql.append("   and t.phaseno = '");
					sql.append(appArray[3]);
					sql.append("'");
				}
				if(!"".equals(StringUtils.checkNullString(rearchUserOrDep)))
				{
					sql.append(" and (1=2");
					sql.append(" or u.fullname like '%"+rearchUserOrDep + "%'");
					sql.append(" or u.loginname like '%"+rearchUserOrDep + "%'");
					sql.append(" or u.pyname like '%"+rearchUserOrDep + "%'");
					sql.append(")");
				}
				sql.append(" order by u.ordernum");
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = null;
				try {
					table = queryAdapter.executeQuery(sql.toString(), new Object[] {baseSchema, companyId});
				} catch (Exception e) {
					e.printStackTrace();
				}
				int tableLen = 0;
				String selectUser = "";
				if(table != null)
				{
					tableLen = table.length();
					dtreeBeanList = new ArrayList<DtreeBean> ();
					String[] selectData = selectId.split(";");
					if(selectData.length > 0)
						selectUser = selectData[0];
				}
				DataRow row;
				DtreeBean dtreeBean;
				for(int i=0;i<tableLen;i++)
				{
					row = table.getDataRow(i);
					dtreeBean = new DtreeBean();
					String tmpid = StringUtils.checkNullString(row.getString("treeid"));
					String treetext = StringUtils.checkNullString(row.getString("treetext"));
					String treetype = StringUtils.checkNullString(row.getString("treetype"));
					String treeid = treetype + "_" + tmpid;
					dtreeBean.setId(treeid);
					dtreeBean.setText(treetext);
					HashMap map = new HashMap();
					map.put("id", treeid);
					map.put("text", treetext);
					map.put("type", treetype);
					dtreeBean.setUserdata(map);
					if("U".equals(treetype))
					{
						dtreeBean.setIm0("userImg.png");
						dtreeBean.setIm1("userImg.png");
						dtreeBean.setIm2("userImg.png");
						dtreeBean.setChild("");
						dtreeBean.setSelect("1"); 
						if(selectUser.indexOf(tmpid) >= 0)
							dtreeBean.setChecked("1");
					}
					else
					{
						continue;
					}
					dtreeBeanList.add(dtreeBean);
				}
			}
		}
		return this.createDhtmlxtreeXml(dtreeBeanList, "0");
	}
	
	public String getCommonUserDataApprove(String approve)
	{
		String userData = "";
		String[] appArray = approve.split(";");
		if(appArray.length >= 3)
		{
			String baseSchema = appArray[1];
			String companyId = appArray[2];
			if(!"".equals(baseSchema) && !"".equals(companyId))
			{
				StringBuffer sql = new StringBuffer();
				sql.append("select distinct substr(u.pyname, 1, 1) py, 'U_' || u.pid || '-' || u.loginname userId, u.fullname userName");
				sql.append("  from bs_t_sm_user u, bs_t_wf_approve t");
				sql.append(" where 1 = 1");
				sql.append("   and u.loginname = t.loginname");
				sql.append("   and t.baseschema = ?");
				sql.append("   and t.companyid = ?");
				if(appArray.length == 4 && !"".equals(appArray[3]))
				{
					sql.append("   and t.phaseno = '");
					sql.append(appArray[3]);
					sql.append("'");
				}
				sql.append(" order by py");
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = null;
				try {
					table = queryAdapter.executeQuery(sql.toString(), new Object[] {baseSchema, companyId});
				} catch (Exception e) {
					e.printStackTrace();
				}
				int tableLen = 0;
				if(table != null)
					tableLen = table.length();
				DataRow row;
				String py;
				String userId;
				String userName;
				for(int i=0 ; i<tableLen ; i++)
				{
					row = table.getDataRow(i);
					py = StringUtils.checkNullString(row.getString("py")).toUpperCase();
					if(py.compareTo("A") >= 0 && py.compareTo("Z") <= 0)
					{
						userId = StringUtils.checkNullString(row.getString("userId"));
						userName = StringUtils.checkNullString(row.getString("userName"));
						if(!"".equals(userData))
							userData += ";";
						userData = userData + py + "," + userId + "," + userName;
					}
				}
			}
		}
		return userData;
	}
	
	/**
	 * 将dtreeBeanList转换成xml字符串
	 * 此方法做特殊处理
	 * @param dtreeBeanList
	 * @param parentid
	 * @return
	 */
	private String dtreeThansStr(List<DtreeBean> dtreeBeanList, String parentid)
	{
		String xmlStr = "";
		if(dtreeBeanList != null && dtreeBeanList.size() > 0)
			xmlStr = this.createDhtmlxtreeXml(dtreeBeanList, parentid);
		else
			xmlStr = this.createDhtmlxtreeXml(null);
		return xmlStr;
	}
	
	private String getAllParentDns(String dns)
	{
		String dnsStr = "";
		if("".equals(StringUtils.checkNullString(dns)))
			return dnsStr;
		String[] dnArray = dns.split("\\.");
		String dnsTmp = "";
		for(int i=0 ; i<dnArray.length ; i++)
		{
			if(i > 0)
			{
				dnsTmp += ".";
				dnsStr += ",";
			}
			dnsTmp += dnArray[i];
			dnsStr += dnsTmp;
		}
		return dnsStr;
	}
	
	public String getPriTreeData(String parentid, String rearchInfo, String selectId, String isSelectType, String depids)
	{
		List<DtreeBean> dtreeBeanList = null;
		if("".equals(StringUtils.checkNullString(rearchInfo)))
			dtreeBeanList = this.getPriSubTreeData(parentid, selectId, isSelectType, depids);
		else
			dtreeBeanList = this.getPriRearchTreeData(parentid, rearchInfo, selectId, isSelectType, depids);
		return this.dtreeThansStr(dtreeBeanList, parentid);
	}
	
	private List<DtreeBean> getPriSubTreeData(String parentid, String selectId, String isSelectType, String depids)
	{
		if("".equals(StringUtils.checkNullString(parentid)))
			return null;
		selectId = StringUtils.checkNullString(selectId);
		isSelectType = StringUtils.checkNullString(isSelectType);
		List<DepInfo> depList = null;
		List<String> dnsList = null;
		int _dic = parentid.indexOf("_");
		if(_dic >= 0)
			parentid = parentid.substring(_dic + 1);
		if(!"".equals(StringUtils.checkNullString(depids)))
		{
			DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
			if(depManagerService != null)
				depList = depManagerService.getDepByID(UltraSmUtil.arrayToList(depids.split(",")));
			int depLen = 0;
			if(depList != null)
			{
				depLen = depList.size();
				dnsList = new ArrayList<String> ();
			}
			for(int i=0 ; i<depLen ; i++)
				dnsList.add(depList.get(i).getDepdns());
		}
		List<DtreeBean> dtreeBeanList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid treeid, depname treetext, 'D' treetype, depdns");
		sql.append(" from bs_t_sm_dep t");
		sql.append(" where status = 1");
		sql.append(" and parentid = '" + parentid + "'");
		sql.append(" and (1=2");
		int dnsLen = 0;
		if(dnsList != null)
			dnsLen = dnsList.size();
		if(dnsLen == 0)
			sql.append(" or 1=1");
		String dns;
		for(int i=0 ; i<dnsLen ; i++)
		{
			dns = dnsList.get(i);
			sql.append(" or (depdns like '" + dns + "%' or '" + dns + "' like depdns || '%')");
		}
		sql.append(")");
		sql.append(" order by ordernum");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int tableLen = 0;
		if(table!=null)
			tableLen = table.length();
		DataRow row;
		DtreeBean dtreeBean;
		String dbDns;
		String op;
		for(int i=0 ; i<tableLen ; i++)
		{
			row = table.getDataRow(i);
			dtreeBean = new DtreeBean();
			String treeid = StringUtils.checkNullString(row.getString("treeid"));
			String treetext = StringUtils.checkNullString(row.getString("treetext"));
			String treetype = StringUtils.checkNullString(row.getString("treetype"));
			if("1".equals(isSelectType)) //选人时，没有复选框
				dtreeBean.setNocheckbox("0");
			else if(selectId.indexOf(treeid) >= 0)
				dtreeBean.setChecked("1");
			dbDns = StringUtils.checkNullString(row.getString("depdns"));
			op = "true";
			for(int k=0 ; k<dnsLen ; k++)
			{
				dns = dnsList.get(k);
				if(dns.indexOf(dbDns) == 0 && !dns.equals(dbDns))
				{
					dtreeBean.setNocheckbox("0");
					dtreeBean.setOpen("1");
					op = "false";
					break;
				}
			}
			treeid = treetype + "_" + treeid;
			dtreeBean.setId(treeid);
			dtreeBean.setText(treetext);
			HashMap map = new HashMap();
			map.put("id", treeid);
			map.put("text", treetext);
			map.put("type", treetype);
			map.put("parentid", parentid);
			map.put("op", op);
			dtreeBean.setIm0("groupImg.png");
			dtreeBean.setIm1("groupImg.png");
			dtreeBean.setIm2("groupImg.png");
			dtreeBean.setUserdata(map);
			if("0".equals(parentid))
			{
				dtreeBean.setOpen("1");
			}
			dtreeBeanList.add(dtreeBean);
		}
		return dtreeBeanList;
	}
	
	private List<DtreeBean> getPriRearchTreeData(String parentid, String rearchInfo, String selectId, String isSelectType, String depids)
	{
		List<DtreeBean> dtreeBeanList = null;
		if("".equals(StringUtils.checkNullString(rearchInfo)))
			return dtreeBeanList;
		selectId = StringUtils.checkNullString(selectId);
		isSelectType = StringUtils.checkNullString(isSelectType);
		List<DepInfo> depList = null;
		List<String> dnsList = null;
		int _dic = parentid.indexOf("_");
		if(_dic >= 0)
			parentid = parentid.substring(_dic + 1);
		if(!"".equals(StringUtils.checkNullString(depids)))
		{
			DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
			if(depManagerService != null)
				depList = depManagerService.getDepByID(UltraSmUtil.arrayToList(depids.split(",")));
			int depLen = 0;
			if(depList != null)
			{
				depLen = depList.size();
				dnsList = new ArrayList<String> ();
			}
			for(int i=0 ; i<depLen ; i++)
				dnsList.add(depList.get(i).getDepdns());
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		// 查询的部门信息
		sql.append("select d.pid treeid, d.depname treetext, 'D' treetype, '1' orderby, d.ordernum ordernum");
		sql.append("  from bs_t_sm_dep d");
		sql.append(" where 1=1");
		sql.append(" and (1=2");
		int dnsLen = 0;
		if(dnsList != null)
			dnsLen = dnsList.size();
		if(dnsLen == 0)
			sql.append(" or 1=1");
		String dns;
		for(int i=0 ; i<dnsLen ; i++)
		{
			dns = dnsList.get(i);
			sql.append(" or (depdns like '" + dns + "%' or '" + dns + "' like depdns || '%')");
		}
		sql.append(")");
		sql.append("   and depname like '%" + rearchInfo + "%'");
		if(!"0".equals(isSelectType))
		{
			sql.append(" union all ");
			// 查询的用户信息
			sql.append("select u.pid || '-' || u.loginname treeid, u.fullname treetext, 'U' treetype, '2' orderby, u.ordernum ordernum");
			sql.append("  from bs_t_sm_user u, bs_t_sm_dep d");
			sql.append(" where d.pid = u.depid");
			sql.append(" and (1=2");
			if(dnsLen == 0)
				sql.append(" or 1=1");
			for(int i=0 ; i<dnsLen ; i++)
			{
				dns = dnsList.get(i);
				sql.append(" or (d.depdns like '" + dns + "%' or '" + dns + "' like d.depdns || '%')");
			}
			sql.append(")");
			sql.append("   and (lower(u.pyname) like '%' || lower('" + rearchInfo + "') || '%'");
			sql.append("     or u.fullname like '%" + rearchInfo + "%'");
			sql.append("     or u.loginname like '%" + rearchInfo + "%')");
		}
		sql.append(") order by orderby, ordernum");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int tableLen = 0;
		String selectUser = "";
		String selectDep = "";
		if(table != null)
		{
			tableLen = table.length();
			dtreeBeanList = new ArrayList<DtreeBean> ();
			String[] selectData = selectId.split(";");
			if(selectData.length > 0)
			{
				selectUser = selectData[0];
				if(selectData.length > 1)
					selectDep = selectData[1];
			}
		}
		DataRow row;
		DtreeBean dtreeBean;
		for(int i=0;i<tableLen;i++)
		{
			row = table.getDataRow(i);
			dtreeBean = new DtreeBean();
			String tmpid = StringUtils.checkNullString(row.getString("treeid"));
			String treetext = StringUtils.checkNullString(row.getString("treetext"));
			String treetype = StringUtils.checkNullString(row.getString("treetype"));
			String treeid = treetype + "_" + tmpid;
			dtreeBean.setId(treeid);
			dtreeBean.setText(treetext);
			HashMap map = new HashMap();
			map.put("id", treeid);
			map.put("text", treetext);
			map.put("type", treetype);
			dtreeBean.setUserdata(map);
			if("U".equals(treetype))
			{
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
				dtreeBean.setChild("");
				dtreeBean.setSelect("1"); 
				if(selectUser.indexOf(tmpid) >= 0)
					dtreeBean.setChecked("1");
			}
			else if("D".equals(treetype))
			{
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
				if("1".equals(isSelectType))
					dtreeBean.setNocheckbox("0");
				else if(selectDep.indexOf(tmpid) >= 0)
					dtreeBean.setChecked("1");
			}
			else
			{
				continue;
			}
			dtreeBeanList.add(dtreeBean);
		}
		return dtreeBeanList;
	}
}
