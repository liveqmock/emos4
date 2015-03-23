package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;

public class RuleModelTreeImpl extends TreeManager {

	public String getTreeXml()
	{
		List<DtreeBean> dtreeList = createDtree();
		return this.apposeDhtmlXtreeXml(dtreeList);
	}
	public List<DtreeBean> createDtree()
	{
		List<DtreeBean> lst = new ArrayList<DtreeBean>();
		StringBuffer sb = new StringBuffer();
		sb.append("select t.pid, t.ruletemplatename from bs_t_sm_ruletpl t order by t.ordernum");
		QueryAdapter qa = new QueryAdapter();
		DataTable datatable = qa.executeQuery(sb.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
		{
			datatableLen = datatable.length();
		}
		DataRow dataRow;
		DtreeBean menuDtree = null;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			menuDtree = new DtreeBean();
			menuDtree.setId(dataRow.getString("pid"));
			menuDtree.setText(dataRow.getString("ruletemplatename"));
			menuDtree.setIm0("folderClosed.gif");
			menuDtree.setIm1("folderClosed.gif");
			menuDtree.setIm2("folderClosed.gif");
			lst.add(menuDtree);
		}
		return lst;
	}
}
