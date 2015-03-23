package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class MenuTreeImpl extends TreeManager{
	private QueryAdapter queryAdapter = new QueryAdapter();
	
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid
	 * @return
	 */
	public String getChildXml(String parentid,String roleid)
	{
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = null;
		roleid = StringUtils.checkNullString(roleid);
		if("".equals(roleid) || "0".equals(roleid))
			dtreeChildrenList = getChild(parentid, roleid);
		else
			dtreeChildrenList = getChildX(parentid,roleid);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	public String getMenuTreeXml(List<MenuDtree> menuDtreeList)
	{
		return this.createDhtmlxtreeXml(menuDtreeList);
	}
	
	private List<DtreeBean> getChild(String parentid,int level)
	{
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, nodename, parentid");
		sql.append(" from bs_t_sm_menutree t");
		sql.append(" where status = 1");
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
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChild(String parentid, String roleid)
	{
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, nodename, parentid");
		sql.append(" from bs_t_sm_menutree t");
		sql.append(" where 1=1");
		if(!"".equals(roleid))
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
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChildX(String parentid,String roleid)
	{
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct m.pid, m.nodename, m.parentid, m.ordernum");
		sql.append("  from bs_t_sm_menutree m,bs_t_sm_rolemenutree rm");
		sql.append(" where rm.menuid=m.pid and status = 1");
		sql.append("   and rm.roleid = '"+roleid+"'");
		sql.append("   and m.parentid = '"+parentid+"'");
		sql.append(" order by m.ordernum");
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
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
}
