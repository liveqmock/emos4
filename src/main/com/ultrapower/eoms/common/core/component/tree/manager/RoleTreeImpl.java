package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

/**
 * 角色树
 * @author ZhuZhaohui
 */
public class RoleTreeImpl extends TreeManager{

	private QueryAdapter queryAdapter = new QueryAdapter();
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid 父节点
	 * @return 返回查询到的树xml节点数据
	 */
	public String getChildXml(String parentid, List roleIdList){
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = getChild(parentid, roleIdList);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid 父节点
	 * @return 返回查询到的树xml节点数据
	 */
	public String getChildXmlByChidren(String id){
		if(id=="")
			return "";
		List<DtreeBean> dtreeChildrenList = getChild(id);
		return this.createDhtmlxtreeXml(dtreeChildrenList, "0");
	}
	
	public String getSelfXmlRoleTree(List<String> roleIdList)
	{
		if(roleIdList==null||roleIdList.size()<=0)
		{
			return "";
		}else
		{
			List<DtreeBean> dtreeChildrenList = getSelfRoleChild(roleIdList);
			return this.createDhtmlxtreeXml(dtreeChildrenList,"0");
		}
	}
	
	/**
	 * 查询展示一定级别的树结构
	 * @param parentid 父节点id
	 * @param level 查询级别
	 * @return 查询获取到的xml数据
	 */
	public String getChildXml(String parentid,int level){
		if(parentid=="")
			return "";
		List<MenuDtree> menuDtreeList = getChild(parentid,level);
		return this.createtreeRecursion(parentid, menuDtreeList, "parentid", (level-1));
	}
	
	/**
	 * 按照级别查询部分树
	 * @param parentid 父节点
	 * @param level 查询级别
	 * @return 返回查询到的节点数据
	 */
	private List<MenuDtree> getChild(String parentid,int level){
		if(parentid=="")
			return null;
		List<MenuDtree> dtreeList = new ArrayList<MenuDtree>();
		StringBuffer sql = new StringBuffer();		
		sql.append("select level, pid, depname, parentid");
		sql.append("  from bs_t_sm_role t");
		sql.append(" where level <= "+level+"");
		sql.append(" start with parentid = '"+parentid+"'");
		sql.append(" connect by parentid = prior pid");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		MenuDtree menuDtree;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			menuDtree = new MenuDtree();
			menuDtree.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			menuDtree.setLevel(Integer.parseInt(StringUtils.checkNullString(dataRow.getString("level"))));
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("rolename")));
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("parentid")));
			dtreeList.add(menuDtree);
		}
		return dtreeList;
	}
	/**
	 * 获取该角色节点以及该节点以下的角色
	 * @param id
	 * @return
	 */
	public List<DtreeBean> getChild(String id)
	{
		if("".equals(StringUtils.checkNullString(id)))
		{
			return null;
		}
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct pid, rolename, parentid,definetype");
		sql.append(" from bs_t_sm_role t");
		sql.append(" where pid = '"+id+"'");
		sql.append(" order by definetype");
		DataTable datatable = null;
		datatable = queryAdapter.executeQuery(sql.toString(),null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("rolename")));
			HashMap map = new HashMap();
			map.put("text", StringUtils.checkNullString(dataRow.getString("rolename")));
			map.put("parentId",StringUtils.checkNullString(dataRow.getString("parentId")) );
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 获取父节点下的角色
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChild(String parentid, List roleIdList){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		Map mapCon = null;
		if(roleIdList!=null&&roleIdList.size()>0)
		{
			mapCon = UltraSmUtil.getSqlParameter(roleIdList);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct pid, rolename, parentid,definetype");
		sql.append(" from bs_t_sm_role t");
		if(mapCon==null)
		{
			sql.append(" where parentid = '"+parentid+"'");
		}
		if(mapCon!=null)
		{
			sql.append(" where pid in ("+mapCon.get("?")+")");
		}
		sql.append(" order by definetype");
		DataTable datatable = null;
		if(mapCon!=null)
		{
			datatable = queryAdapter.executeQuery(sql.toString(),(Object[]) mapCon.get("obj"), 0, 0, 2);
		}
		else
		{
			datatable = queryAdapter.executeQuery(sql.toString(),null, 0, 0, 2);
		}
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("rolename")));
			HashMap map = new HashMap();
			map.put("id", StringUtils.checkNullString(dataRow.getString("pid")));
			map.put("text", StringUtils.checkNullString(dataRow.getString("rolename")));
			map.put("parentId",StringUtils.checkNullString(dataRow.getString("parentId")) );
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 根据获取的角色ID取得所有的该ID对应的角色和其子角色所构造的DtreeBean集合
	 * @param roleIdList
	 * @return
	 */
	public List<DtreeBean> getSelfRoleChild(List<String> roleIdList)
	{
		if(roleIdList==null)
		{
			return null;
		}
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		Map mapCon = UltraSmUtil.getSqlParameter(roleIdList);
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct pid, rolename, parentid,definetype");
		sql.append(" from bs_t_sm_role t");
		sql.append(" where pid in ("+mapCon.get("?")+")");
		sql.append(" order by definetype");
		DataTable datatable = null;
		datatable = queryAdapter.executeQuery(sql.toString(),(Object[]) mapCon.get("obj"), 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("rolename")));
			HashMap map = new HashMap();
			map.put("text", StringUtils.checkNullString(dataRow.getString("rolename")));
			map.put("parentId",StringUtils.checkNullString(dataRow.getString("parentId")) );
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	
	/**
	 * 获取角色菜单树
	 * @param parentId
	 * @param roleList
	 * @param openLevel
	 * @return
	 */
	public String getRoleMenuTreeXml(String parentId,List<MenuDtree> menuDtreeList,int openLevel){
		return this.createtreeRecursion(parentId, menuDtreeList, "parentid", openLevel);
	}
	
	/**
	 * 提取角色菜单数据
	 * @param id
	 * @param roleidList
	 * @return
	 */
	private List<MenuDtree> getRoleMenu(String id,List roleidList) {
		List<MenuDtree> menuTreeList = new ArrayList<MenuDtree>();
		int roleidListLen = 0;
		if(roleidList!=null)
			roleidListLen = roleidList.size();
		String s_sql = "";
		for(int rid = 0;rid < roleidListLen;rid++){
			String roleid = (String) roleidList.get(rid);
			s_sql += " sysrole.pid = '"+roleid+"' or";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select level,pid, name, url, parentid, ordervalue");
		sql.append(" from (select distinct pid, name, url, parentid, ordervalue");
		sql.append(" from (select menutree.pid pid,");
		sql.append(" menutree.nodename name,");
		sql.append(" menutree.nodeurl  url,");
		sql.append(" menutree.parentid parentid,");
		sql.append(" menutree.ordernum ordervalue");
		sql.append(" from BS_T_SM_ROLE         sysrole,");
		sql.append(" BS_T_SM_ROLEMENUTREE rolemenutree,");
		sql.append(" BS_T_SM_MENUTREE     menutree");
		sql.append(" where rolemenutree.roleid = sysrole.pid");
		sql.append(" and rolemenutree.menuid = menutree.pid");
		sql.append(" and menutree.status = 1");
		if(s_sql!="")
			sql.append(" and ("+s_sql.substring(0, s_sql.length()-2));
		sql.append(") ))");
		sql.append(" start with parentid = '"+id+"'");
		sql.append(" connect by parentid = prior pid order by ordervalue");
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		MenuDtree menuDtree = null;
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			menuDtree = new MenuDtree();
			menuDtree.setLevel(Integer.parseInt(dataRow.getString("level")));
			menuDtree.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("parentid")));
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("name")));
			HashMap map = new HashMap();
			map.put("url", StringUtils.checkNullString(dataRow.getString("url")));
			map.put("text", StringUtils.checkNullString(dataRow.getString("name")));
			menuDtree.setUserdata(map);
			menuTreeList.add(menuDtree);
		}
		return menuTreeList;
	}
}
