package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ultrapower.eoms.common.plugin.datagrid.core.GridConstants;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.DataInfo;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.model.Operate;
import com.ultrapower.eoms.ultrasm.model.ResProperty;
import com.ultrapower.eoms.ultrasm.model.ResPropertyShow;
import com.ultrapower.eoms.ultrasm.model.Resource;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleResOpShow;
import com.ultrapower.eoms.ultrasm.model.SqlDataPrivilege;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.OperateManagerService;
import com.ultrapower.eoms.ultrasm.service.ResourceManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class RoleResOpManagerAction extends BaseAction
{
	private RoleManagerService roleManagerService;
	private ResourceManagerService resourceManagerService;
	private OperateManagerService operateManagerService;
	private DicManagerService dicManagerService;
	private List<RoleResOpShow> rroShowList;
	private RoleResOpShow rroShow;
	private List<Operate> opList;
	private List<ResPropertyShow> rpShowList;
	private List<Resource> resourceList;
	private List<DataInfo> dataList;
	private String sqlCon;

	/**
	 * 获取角色资源操作关系列表
	 * @return
	 */
	public String roleResOpList()
	{
		//rroShowList = roleManagerService.getRoleResOp(null);
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userId = "";
		if(userSession != null)
			userId = userSession.getPid();
		List<RoleInfo> lst = roleManagerService.getRoleByUserID(userId);
		StringBuffer roleids = new StringBuffer();
		Map map = new HashMap();
		for(RoleInfo role:lst)
		{
			roleids.append(role.getRoledns()+",");
		}
		if(roleids.lastIndexOf(",")!=-1)
		{
			map.put("roledns", roleids);
			this.getRequest().setAttribute("valuemap", map);
		}
		return SUCCESS;
	}

	/**
	 * 获取操作权限数据
	 * @return	返回设置操作权限页面
	 */
	public String getOpPrivilege()
	{
		String rroId = this.getRequest().getParameter("rroId");
		rroShow = roleManagerService.getRroShowByID(rroId);
		if(rroShow == null)
		{
			//失败
			return "";
		}
		opList = roleManagerService.getOpByRoleResID(rroShow.getRoleid(), rroShow.getResid());
		if(opList != null && opList.size() > 0)
		{
			for(int i=0;i<opList.size();i++)
			{
				if(rroShow.getOpid().equals(opList.get(i).getPid()))
				{
					opList.remove(i);
					break;
				}
			}
		}
		rpShowList = roleManagerService.getOdpShowByRroID(rroId);
		SqlDataPrivilege sdp = roleManagerService.getSqlDataByRroId(rroId);
		if(sdp != null)
			sqlCon = sdp.getPrivilegedata();
		return this.findForward("setOpPrivilege");
	}
	
	public String getValue()
	{
		String type = this.getRequest().getParameter("type");
		String rpid = this.getRequest().getParameter("rpid");
		type = StringUtils.checkNullString(type);
		dataList = new ArrayList();
		if("1".equals(type))//type=1时,为系统变量配置
		{
			List<DicItem> diList = dicManagerService.getDicItemByDicType("SystemVar");
			if(diList != null && diList.size() > 0)
			{
				DicItem di = null;
				DataInfo data = null;
				for(int i=0;i<diList.size();i++)
				{
					di = diList.get(i);
					data = new DataInfo();
					data.setId(di.getDivalue());
					data.setValue(di.getDiname());
					dataList.add(data);
				}
			}
		}
		if("2".equals(type) && !"".equals(StringUtils.checkNullString(rpid)))//type=2时,为数据字典取值
		{
			ResProperty rp = resourceManagerService.getResPropertyInfo(rpid);
			List<DicItem> diList = dicManagerService.getDicItemByDicType(rp.getIndata());
			if(diList != null && diList.size() > 0)
			{
				DicItem di = null;
				DataInfo data = null;
				for(int i=0;i<diList.size();i++)
				{
					di = diList.get(i);
					data = new DataInfo();
					data.setId(di.getDivalue());
					data.setValue(di.getDiname());
					dataList.add(data);
				}
			}
		}
		else if("3".equals(type) && !"".equals(StringUtils.checkNullString(rpid)))//type=3时,为SQL配置取值
		{
			ResProperty rp = resourceManagerService.getResPropertyInfo(rpid);
			dataList = roleManagerService.getInfoBySql(rp.getIndata());
		}
		else if("6".equals(type) && !"".equals(StringUtils.checkNullString(rpid)))//type=6时,手工配置取值
		{
			ResProperty rp = resourceManagerService.getResPropertyInfo(rpid);
			String value = rp==null ? "" : StringUtils.checkNullString(rp.getIndata());
			if(!"".equals(value))
			{
				value = value.replaceAll("；", ";");//将全角的分号替换成半角的分号
				value = value.replaceAll("：", ":");//将全角的冒号替换成半角的冒号
				String[] values = value.split(";");
				DataInfo data = null;
				for(int k=0;k<values.length;k++)
				{
					if(!"".equals(values[k]))
					{
						String[] dics = values[k].split(":");
						if(dics.length > 1)
						{
							data = new DataInfo();
							data.setId(dics[0]);
							data.setValue(dics[1]);
							dataList.add(data);
						}
					}
				}
			}
		}
		this.getRequest().setAttribute("input_name", this.getRequest().getParameter("input_name"));
		this.getRequest().setAttribute("input_id", this.getRequest().getParameter("input_id"));
		return this.findForward("dataSelect");
	}
	
	/**
	 * 保存操作数据权限
	 * @return
	 */
	public String saveOpPrivilege()
	{
		String rpDatas = this.getRequest().getParameter("rpdata");
		boolean saveResult = false;
		String roleId = this.getRequest().getParameter("roleId");
		String resId = this.getRequest().getParameter("resId");
		String opids = this.getRequest().getParameter("opId");
		List rroIdList = roleManagerService.getRroIdByRoleResOpID(roleId, resId, UltraSmUtil.arrayToList(opids.split(",")));
		if("".equals(StringUtils.checkNullString(rpDatas)))
		{
			saveResult = roleManagerService.addSqlDataPrivilege(rroIdList, sqlCon);//保存SQL权限
		}
		else
		{
			List rpIdList = new ArrayList();
			List rpValueList = new ArrayList();
			String[] rpData = rpDatas.split(";");
			for(int i=0;i<rpData.length;i++)
			{
				String[] data = rpData[i].split(":");
				rpIdList.add(data[0]);
				rpValueList.add(data[1]+";"+data[2]+";"+data[3]);
			}
			saveResult = roleManagerService.addDataPrivilege(rroIdList, rpIdList, rpValueList, sqlCon);
		}
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(saveResult)
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 清空指定操作的权限
	 * @return	返回列表页面
	 * @throws IOException 
	 */
	public void clearPrivilege() throws IOException
	{
		String rroId = StringUtils.checkNullString(this.getRequest().getParameter("rroId"));
		boolean b = roleManagerService.clearDataPrivilegeByRroID(rroId);
		this.getResponse().getWriter().print(String.valueOf(b));
	}
	
	/**
	 * 获取指定角色的资源列表
	 * @return
	 */
	public String getResourceById()
	{
		String roleid = this.getRequest().getParameter("proleid");
		if(roleid==null||"".equals(roleid))
		{
			return "resourceList";
		}
		if("0".equals(roleid))
		{
			//resourceList = resourceManagerService.getResource();
			Map map = new HashMap();
			map.put("role_id", null);
			this.getRequest().setAttribute("valuemap", map);
			this.getRequest().setAttribute("role_id_page", null);
		}
		else
		{
			//resourceList = roleManagerService.getResByRoleID(roleid);
			Map map = new HashMap();
			map.put("role_id", roleid);
			this.getRequest().setAttribute("valuemap", map);
			this.getRequest().setAttribute("role_id_page", roleid);
		}
		return findForward("resourceList");
	}
	
	/**
	 * 根据角色ID和资源ID得到资源的操作数据，并构造jsonArray字符串
	 * @return
	 * @throws IOException 
	 */
	public void getResourceOp() throws IOException
	{
		String roleid = StringUtils.checkNullString(this.getRequest().getParameter("roleid"));//本级角色ID
		String proleid = StringUtils.checkNullString(this.getRequest().getParameter("proleid"));//父角色ID
		String resid = StringUtils.checkNullString(this.getRequest().getParameter("resid"));//资源ID
		if("".equals(roleid) || "".equals(proleid) || "".equals(resid))
		{
			this.renderText("[]");
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			String jsonStr = null;
			sb.append("[");
			List<Operate> opList_ed = null;//本级角色针对这个资源已有的操作集合
			List<Operate> opList_all = null;//父角色针对这个资源所拥有的操作集合
			if("0".equals(proleid))
			{//顶级角色
				opList_all = operateManagerService.getAllOperate();
				opList_ed = roleManagerService.getOpByRoleResID(roleid, resid);
			}
			else
			{//非顶级角色
				opList_all = roleManagerService.getOpByRoleResID(proleid, resid);
				opList_ed = roleManagerService.getOpByRoleResID(roleid, resid);
			}
			if(opList_all!=null && opList_all.size()>0)
			{
				if(opList_ed==null||opList_ed.size()<=0)
				{
					for(Operate op:opList_all)
					{
						sb.append("{\"opid\":\""+op.getPid()+"\",\"opname\":\""+op.getOpname()+"\",\"own\":false},");
					}
				}
				else
				{
					List<String> ids_ed = new ArrayList<String>();
					for(int i=0;i<opList_ed.size();i++)
					{//取得已有的操作的ID集合
						ids_ed.add(opList_ed.get(i).getPid());
					}
					for(Operate op:opList_all)
					{
						if(ids_ed.indexOf(op.getPid())==-1)
						{
							sb.append("{\"opid\":\""+op.getPid()+"\",\"opname\":\""+op.getOpname()+"\",\"own\":false},");
						}else
						{
							sb.append("{\"opid\":\""+op.getPid()+"\",\"opname\":\""+op.getOpname()+"\",\"own\":true},");
						}
					}
				}
				jsonStr = sb.substring(0,sb.lastIndexOf(","));
				jsonStr = jsonStr + "]";
			}
			else
			{
				jsonStr = "[]";
			}
			this.getResponse().getWriter().print(jsonStr);
		}
	}
	
	/**
	 * 保存角色资源操作关系
	 * @return
	 */
	public String saveRoleResOp()
	{
		String roleid = StringUtils.checkNullString(this.getRequest().getParameter("roleids"));
		String resids = StringUtils.checkNullString(this.getRequest().getParameter("resids"));
		String opids = StringUtils.checkNullString(this.getRequest().getParameter("opids"));
		String parafresh = "true";
		if(roleManagerService.addRoleResOp(roleid, UltraSmUtil.arrayToList(resids.split(",")), UltraSmUtil.arrayToList(opids.split(","))))
		{
			String returnMessage = Internation.language("com_msg_saveSuccess")+"!";
			this.getRequest().setAttribute("parafresh", parafresh);
			this.getRequest().setAttribute("returnMessage", returnMessage);
			return "refresh";
		}
		else
		{
			String returnMessage = Internation.language("com_msg_saveErr")+"!";
			this.getRequest().setAttribute("parafresh", parafresh);
			this.getRequest().setAttribute("returnMessage", returnMessage);
			return "refresh";
		}
	}
	
	/**
	 * 删除角色资源操作关系
	 * @return
	 */
	public String delRoleResOp()
	{
		String delids = StringUtils.checkNullString(this.getRequest().getParameter(GridConstants.HIDDEN_CHECKBOX_SELECTVALUES));
		if(!"".equals(delids))
		{
			roleManagerService.deleteRoleResOpByID(UltraSmUtil.arrayToList(delids.split(",")));
		}
		return this.findRedirectPar("roleResOpList.action?id="+this.getRequest().getParameter("id"));
	}
	
	/*
	 * 以下为属性的get/set方法
	 */
	public List<RoleResOpShow> getRroShowList() 
	{
		return rroShowList;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) 
	{
		this.roleManagerService = roleManagerService;
	}
	public RoleResOpShow getRroShow() 
	{
		return rroShow;
	}
	public List<Operate> getOpList() 
	{
		return opList;
	}
	public List<ResPropertyShow> getRpShowList() 
	{
		return rpShowList;
	}
	public List<Resource> getResourceList() 
	{
		return resourceList;
	}
	public void setResourceList(List<Resource> resourceList) 
	{
		this.resourceList = resourceList;
	}
	public void setResourceManagerService(
			ResourceManagerService resourceManagerService) 
	{
		this.resourceManagerService = resourceManagerService;
	}
	public List<DataInfo> getDataList() 
	{
		return dataList;
	}
	public void setDicManagerService(DicManagerService dicManagerService) 
	{
		this.dicManagerService = dicManagerService;
	}
	public void setOperateManagerService(OperateManagerService operateManagerService) 
	{
		this.operateManagerService = operateManagerService;
	}
	public String getSqlCon() 
	{
		return sqlCon;
	}
	public void setSqlCon(String sqlCon) 
	{
		this.sqlCon = sqlCon;
	}
}
