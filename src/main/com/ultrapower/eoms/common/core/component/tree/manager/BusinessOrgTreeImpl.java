package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2011-6-14 下午01:58:00
 * @descibe 
 */
public class BusinessOrgTreeImpl extends TreeManager{

	private QueryAdapter queryAdapter = new QueryAdapter();
	
	public String getBusinessOrgTreeXml(String parentid){
		List<MenuDtree> menuDtreeList = getBusinessOrgTreeTree(parentid);
		return this.createDhtmlxtreeXml(menuDtreeList);
	}
	
	public List<MenuDtree> getBusinessOrgTreeTree(String parentid){
		List<MenuDtree> menuTreeList = new ArrayList<MenuDtree>();
		StringBuffer sql = new StringBuffer();
		sql.append("select level, pid, orgname, parentid");
		sql.append(" from bs_t_sm_businessorg");
		sql.append(" where status = 1");
		sql.append(" start with parentid = ?");
		sql.append(" connect by parentid = prior pid");
		sql.append(" order by ordernum");
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), new Object[] {parentid}, 0, 0, 2);
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
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));
			menuDtree.setId(pid);
			String text = StringUtils.checkNullString(dataRow.getString("orgname"));
			menuDtree.setText(text);
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("parentid")));
			HashMap map = new HashMap();
			map.put("id", pid);
			map.put("text", text);
			menuDtree.setUserdata(map);
			menuTreeList.add(menuDtree);
		}
		return menuTreeList;
	}
}
