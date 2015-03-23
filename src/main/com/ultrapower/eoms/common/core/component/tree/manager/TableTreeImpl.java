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

public class TableTreeImpl extends TreeManager{
	
	public String getChildXml(String parentid){
		if(parentid=="")
		{
			return "";
		}
		List<MenuDtree> menuDtreeList = getChild();
		return this.createDhtmlxtreeXml(menuDtreeList);//this.createtreeRecursion(parentid, menuDtreeList, "parentid", 1);
	}
	
	public String getTableFldTreeStr(String parentid)
	{
		if(parentid=="")
		{
			return "";
		}
		List<MenuDtree> menuDtreeList = getTableFldTreeData();
		return this.createDhtmlxtreeXml(menuDtreeList);
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<MenuDtree> getChild(){
		List<MenuDtree> menuTreeList = new ArrayList<MenuDtree>();
		StringBuffer sql = new StringBuffer();
		sql.append("select di.divalue, di.diname, t.enname, t.cnname");
		sql.append("  from bs_t_sm_dicitem di, bs_t_sm_table t");
		sql.append(" where di.dtcode = 'tabletype' and di.divalue = t.tabletype");
		sql.append(" order by di.ordernum, t.ordernum");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		MenuDtree menuDtree = null;
		String oldDivalue = "";
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			String divalue = StringUtils.checkNullString(dataRow.getString("divalue"));
			if(!divalue.equals(oldDivalue))
			{
				menuDtree = new MenuDtree();
				menuDtree.setLevel(1);
				menuDtree.setId(StringUtils.checkNullString(dataRow.getString("divalue")));
				menuDtree.setText(StringUtils.checkNullString(dataRow.getString("diname")));
				menuDtree.setParentid("0");
				menuTreeList.add(menuDtree);
			}
			oldDivalue = divalue;
			menuDtree = new MenuDtree();
			menuDtree.setLevel(2);
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("divalue")));
			menuDtree.setId(StringUtils.checkNullString(dataRow.getString("enname")));
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("cnname")));
			menuTreeList.add(menuDtree);
		}
		return menuTreeList;
	}
	
	private List<MenuDtree> getTableFldTreeData()
	{
		List<MenuDtree> menuTreeList = new ArrayList<MenuDtree>();
		StringBuffer sql = new StringBuffer();
		sql.append("select tb.enname,tb.cnname,fld.pid,fld.field");
		sql.append(" from bs_t_sm_table tb,bs_t_sm_field fld");
		sql.append(" where tb.enname = fld.enname");
		sql.append(" order by tb.enname");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
		{
			datatableLen = datatable.length();
		}
		DataRow dataRow;
		MenuDtree menuDtree = null;
		String oldDivalue = "";
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			String divalue = StringUtils.checkNullString(dataRow.getString("enname"));
			if(!divalue.equals(oldDivalue))
			{
				menuDtree = new MenuDtree();
				menuDtree.setLevel(1);
				menuDtree.setId(StringUtils.checkNullString(dataRow.getString("enname")));
				menuDtree.setText(StringUtils.checkNullString(dataRow.getString("cnname")));
				menuDtree.setParentid("0");
				menuTreeList.add(menuDtree);
			}
			oldDivalue = divalue;
			menuDtree = new MenuDtree();
			menuDtree.setLevel(2);
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("enname")));
			menuDtree.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("field")));
			menuTreeList.add(menuDtree);
		}
		return menuTreeList;
	}
}
