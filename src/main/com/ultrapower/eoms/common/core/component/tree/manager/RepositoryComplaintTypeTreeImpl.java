package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.service.RepositorySortTypeTreeService;
import com.ultrapower.remedy4j.core.RemedySession;

public class RepositoryComplaintTypeTreeImpl extends TreeManager implements
             RepositorySortTypeTreeService {

	/**
	 * 案例库网络分类树形数据
	 * @param id
	 * @return
	 */
	public List<DtreeBean> getSortList(String id){
		 List<DtreeBean> list = new ArrayList();
		 id = id.split("_")[0];
		 if(10 == id.length()||"".equals(id)){
			 return list;
		 }
		 String tname = RemedySession.UtilInfor.getTableName("WF4:Config_EL_TTM_CCH_ComplaintType");
		 String sqlSortString = "";
		 String sqlidString = "";
		 QueryAdapter qAdapter  = new QueryAdapter();
		 int endnum = 0;
		 if("0".equals(id)){//取第一级节点
			 sqlSortString = "select DISTINCT C650000002 as sortone from "+tname;
			 sqlidString = "select DISTINCT C650000102 as idflag from "+tname+" where C650000002 = '";
			 endnum = 2;
		 }else if(2 == id.length()){//第二级节点
			 sqlSortString = "select DISTINCT C650000003 as sortone from "+tname+" where C650000102 like '"+id+"%'";
			 sqlidString = "select DISTINCT C650000102 as idflag from "+tname+" where C650000003 = '";
			 endnum = 4;
		 }else if(4 == id.length()){//三级节点
			 sqlSortString = "select DISTINCT C650000004 as sortone from "+tname+" where C650000102 like '"+id+"%'";
			 sqlidString = "select DISTINCT C650000102 as idflag from "+tname+" where C650000004 = '";
			 endnum = 6;
		 }else if(6 == id.length()){//四级节点
			 sqlSortString = "select DISTINCT C650000005 as sortone from "+tname+" where C650000102 like '"+id+"%'";
			 sqlidString = "select DISTINCT C650000102 as idflag from "+tname+" where C650000005 = '";
			 endnum = 8;
		 }else if(8 == id.length()){//五级节点
			 sqlSortString = "select DISTINCT C650000006 as sortone from "+tname+" where C650000102 like '"+id+"%'";
			 sqlidString = "select DISTINCT C650000102 as idflag from "+tname+" where C650000006 = '";
			 endnum = 10;
		 }
		 if ("".equals(sqlSortString)) {
			 return list;
		 }
		 DataTable dTable = qAdapter.executeQuery(sqlSortString, null, 0);
		 DtreeBean menuDtree = null;
		 DataRow dRow = null;
		 DataRow didRow = null;
		 HashMap map = null;
		 for (int j = 0; j < dTable.length(); j++) {
			menuDtree = new DtreeBean();
			map = new HashMap();
			dRow = dTable.getDataRow(j);
			String sortone = dRow.getString("sortone");
			if("".equals(sortone)) continue;
			String sql =  sqlidString +sortone+"'";
			didRow = qAdapter.executeQuery(sql, null, 0).getDataRow(0);
			if(didRow != null){
				String idflag = didRow.getString("idflag");
				if("".equals(idflag)) continue;
				menuDtree.setId(idflag.substring(0,endnum));
				map.put("id", idflag.substring(0,endnum));
			}
			map.put("text", sortone);
			menuDtree.setText(sortone);
			menuDtree.setUserdata(map);
			list.add(menuDtree);
		}
		 return list;
	}
	
	public String getSortXml(String parentid){
		if(parentid==null || "".equals(parentid))
			return "";
		List<DtreeBean> menuDtreeList = getSortList(parentid);
		return this.createDhtmlxtreeXml(menuDtreeList, parentid);
	}

}
