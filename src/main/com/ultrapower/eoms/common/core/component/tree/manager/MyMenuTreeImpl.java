package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 14, 2010 3:56:49 PM
 * @descibe 
 */
public class MyMenuTreeImpl extends TreeManager{

	private QueryAdapter queryAdapter = new QueryAdapter();
	private DataAdapter dataAdapter;
	/**
	 * 查询我的菜单树的菜单夹树形
	 * @param parentid
	 * @return
	 */
	public String getMyMenuFolderTree(String parentid, String userId)
	{
		if(parentid=="")
			return "";
		List<MenuDtree> dtreeMyMenuList = getChild(parentid, userId);
		return this.createDhtmlxtreeXml(dtreeMyMenuList);
	}
	
	public String getMyMenuTree(String parentid, String userId)
	{
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = getChildX(parentid, userId);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	public void updateParentId(String fromId,String toId){
		dataAdapter = new DataAdapter();
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("parentid", toId);
		String conditions = "pid=?";
		Object[] values = {fromId};
		int result = dataAdapter.putDataRow("bs_t_sm_mymenu", p_dtrow, conditions, values);
		if(result>0){
			RecordLog.printLog("节点："+fromId+",的父节点已更新为："+toId+"----成功!");
		}else{
			RecordLog.printLog("节点："+fromId+",的父节点已更新为："+toId+"----失败!");
		}
	}
	
	private List<DtreeBean> getChildX(String parentid, String userId)
	{
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, nodename, parentid,nodetype");
		sql.append(" from bs_t_sm_mymenu t");
		sql.append(" where 1=1");
		sql.append(" and userid = '" + userId + "'");
		sql.append(" and status = 1");	
		sql.append(" and parentid = '"+parentid+"'");
		sql.append(" order by ordernum");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("nodename")));
			HashMap map = new HashMap();
			map.put("type", StringUtils.checkNullString(dataRow.getString("nodetype")));
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	private List<MenuDtree> getChild(String parentid, String userId)
	{
		if(parentid=="")
			return null;
		List<MenuDtree> dtreeList = new ArrayList<MenuDtree>();
		StringBuffer sql = new StringBuffer();
		sql.append("select level, t.pid id, t.parentid parentid, t.nodename nodename");
		sql.append(" from bs_t_sm_mymenu t");
		sql.append(" where 1=1");
		sql.append(" and t.userid = '" + userId + "'");
		sql.append(" and t.nodetype = '1'");
		sql.append(" and t.status = '1'");
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
			menuDtree.setLevel(Integer.parseInt(StringUtils.checkNullString(dataRow.getString("level"))));
			menuDtree.setId(StringUtils.checkNullString(dataRow.getString("id")));
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("parentid")));
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("nodename")));
			menuDtree.setChild("1");
			dtreeList.add(menuDtree);
		}
		return dtreeList;
	}
}
