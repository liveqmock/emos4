package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class DictionaryTreeImpl extends TreeManager{
	private QueryAdapter queryAdapter = new QueryAdapter();
	
	/**
	 * 
	 * @param dtcode
	 * @return
	 */
	public String getAllChildXml(String dtcode)
	{
		List<MenuDtree> menuDtreeList = getDicItemTree(dtcode);
		return this.createDhtmlxtreeXml(menuDtreeList);
	}
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid
	 * @return
	 */
	public String getChildXml(String parentid, String dictype){
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = null;
		if("0".equals(parentid))
			dtreeChildrenList = getChild(dictype);
		else
			dtreeChildrenList = getChildX(parentid);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChild(String dictype){
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, dtname, dtcode");
		sql.append(" from bs_t_sm_dictype dt");
		sql.append(" where 1 = 1");
		if(!"".equals(StringUtils.checkNullString(dictype)) && !"0".equals(StringUtils.checkNullString(dictype)))
			sql.append(" and dictype = '" + dictype + "'");
		sql.append(" order by nlssort(dtname, 'NLS_SORT=SCHINESE_PINYIN_M')");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid"))+":"+StringUtils.checkNullString(dataRow.getString("dtcode")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("dtname")));
			HashMap map = new HashMap();
			map.put("type", "1");
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChildX(String parentid){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, diname, parentid");
		sql.append(" from bs_t_sm_dicitem di");
		sql.append(" where 1 = 1");
		if(parentid.indexOf(":") > 0)
		{
			String[] dt = parentid.split(":");
			sql.append(" and parentid = '0' and dtid = '"+dt[0]+"'");
		}
		else
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
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("diname")));
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	private List<MenuDtree> getDicItemTree(String dtcode)
	{
		List<MenuDtree> menuTreeList = new ArrayList<MenuDtree>();
		StringBuffer sql = new StringBuffer();
		sql.append("select level, pid, divalue, diname, parentid");
		sql.append("  from bs_t_sm_dicitem");
		sql.append(" where dtcode = ?");
		sql.append("   and status = 1");
		sql.append(" start with parentid = '0'");
		sql.append("connect by parentid = prior pid");
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), new Object[] {dtcode}, 0, 0, 2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		MenuDtree menuDtree = null;
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++)
		{
			dataRow = dataTable.getDataRow(row);
			menuDtree = new MenuDtree();
			menuDtree.setLevel(Integer.parseInt(dataRow.getString("level")));
			//String id = StringUtils.checkNullString(dataRow.getString("pid"));
			String divalue = StringUtils.checkNullString(dataRow.getString("divalue"));
			menuDtree.setId(divalue);
			String text = StringUtils.checkNullString(dataRow.getString("diname"));
			menuDtree.setText(text);
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("parentid")));
			HashMap map = new HashMap();
			map.put("id", divalue);
			map.put("text", text);
			menuDtree.setUserdata(map);
			menuTreeList.add(menuDtree);
		}
		return menuTreeList;
	}
}
