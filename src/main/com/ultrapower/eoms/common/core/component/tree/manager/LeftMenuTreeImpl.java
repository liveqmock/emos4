package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.component.tree.service.DynamicDataService;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;

/**
 * 左侧菜单树
 * @author 朱朝辉 E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 2, 2010 3:22:24 PM
 */
public class LeftMenuTreeImpl extends TreeManager{

	private QueryAdapter queryAdapter = new QueryAdapter();
	private DynamicDataService dynamicDataService;
	
	/**
	 * 获取动态节点信息
	 * @param className
	 * @param id
	 * @return
	 */
	public String getDynamicXmlTreeData(String className,String id){
		List<DtreeBean> dataList = null;
		if(!"".equals(StringUtils.checkNullString(className)))
		{
			try{
				dynamicDataService = (DynamicDataService)WebApplicationManager.getBean(className);
				dataList = dynamicDataService.getChildList(id);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(dataList!=null){
			return this.createDhtmlxtreeXml(dataList,id);
		}else{
			return "<?xml version=\"1.0\" encoding=\"utf-8\"?><tree id=\""+id+"\"></tree>";
		}
	}
	
	
	/**
	 * 根据角色、类型父Id获取左侧目录菜单的树xml
	 * @param parentId 父节点id
	 * @param roleList 角色集合信息
	 * @param openLevel 打开级别
	 * @return
	 */
//	public String getRoleLeftTreeXml(String parentId,List roleList,int openLevel){
//		List<MenuDtree> menuDtreeList = getRoleLeftTreeData(parentId,roleList);
//		return this.createDhtmlxtreeXml(menuDtreeList);//this.createtreeRecursion(parentId, menuDtreeList, "parentid", openLevel);
//	}
	
	public String getRoleLeftTreeXmlByCache(List<MenuDtree> menuDtreeList, List<String> openIdList){
		return this.createDhtmlxtreeXml(menuDtreeList, openIdList);
	}
	/**
     * 查询角色所拥有的左侧树集合
     * @param roleidList 多个角色
     * @return 返回树对象的集合
     */
	public List<MenuDtree> getRoleLeftTreeData(String id,List roleidList) {
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
		sql.append("select level,pid, name, url, parentid,classname,ordervalue");
		sql.append(" from (select distinct pid, name, url, parentid, classname, ordervalue");
		sql.append(" from (select menutree.pid pid,");
		sql.append(" menutree.nodename name,");
		sql.append(" menutree.nodeurl  url,");
		sql.append(" menutree.parentid parentid,");
		sql.append(" menutree.classname classname,");
		sql.append(" menutree.ordernum ordervalue");
		sql.append(" from BS_T_SM_ROLE         sysrole,");
		sql.append(" BS_T_SM_ROLEMENUTREE rolemenutree,");
		sql.append(" BS_T_SM_MENUTREE     menutree");
		sql.append(" where rolemenutree.roleid = sysrole.pid");
		sql.append(" and rolemenutree.menuid = menutree.pid");
		sql.append(" and menutree.status = 1");
		if(s_sql!="")
		{
			sql.append(" and ("+s_sql.substring(0, s_sql.length()-2));
			sql.append(")");
		}
		sql.append(") order by ordervalue )");
		sql.append(" start with parentid = '"+id+"'");
		sql.append(" connect by parentid = prior pid ");
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
			String classname = StringUtils.checkNullString(dataRow.getString("classname"));
			if(!StringUtils.checkNullString(classname).equals(""))
				menuDtree.setChild("1");
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("name")));
			HashMap map = new HashMap();
			map.put("url", StringUtils.checkNullString(dataRow.getString("url")));
			menuDtree.setUserdata(map);
			menuTreeList.add(menuDtree);
		}
		return menuTreeList;
	}


	public void setDynamicDataService(DynamicDataService dynamicDataService) {
		this.dynamicDataService = dynamicDataService;
	}
	
	
}
