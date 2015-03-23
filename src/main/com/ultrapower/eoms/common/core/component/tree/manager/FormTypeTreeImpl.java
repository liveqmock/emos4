package com.ultrapower.eoms.common.core.component.tree.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;

import org.apache.commons.collections.CollectionUtils;

import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.workflow.client.IWorkFlowServiceClient;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfSort;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public class FormTypeTreeImpl extends TreeManager {
	
	/**
	 * 获得工单类别树的XML串
	 * @return
	 */
	public String getTreeXml()
	{
		List<MenuDtree> menuDtree = createDtree();
		return this.createDhtmlxtreeXml(menuDtree);
	}
	public List<MenuDtree> createDtree()
	{
		List<MenuDtree> lst = new ArrayList<MenuDtree>();
		try {
			//采用流程提供的远程方式获取工单类别
			IWorkFlowServiceClient instance = WorkFlowServiceClient.clientInstance();
			List<WfSort> allWfSort = instance.getSortService().getAllWfSort();
			int allWfSortLen = 0;
			if(allWfSort!=null)
				allWfSortLen = allWfSort.size();
			MenuDtree menuDtree = null;
			for(int i=0;i<allWfSortLen;i++){
				WfSort wfSort = allWfSort.get(i);
				String parentid = wfSort.getPid();
				if(parentid == null || "0".equals(parentid))
					continue;
				menuDtree = new MenuDtree();
				menuDtree.setLevel(1);
				menuDtree.setId(wfSort.getCode());
				menuDtree.setText(wfSort.getName());
				lst.add(menuDtree);

				
//				menuDtree.setId(wfSort.getCode());;
//				menuDtree.setText(wfSort.getName());
//				menuDtree.setIm0("folderClosed.gif");
//				menuDtree.setIm1("folderClosed.gif");
//				menuDtree.setIm2("folderClosed.gif");
				
				String sortId = wfSort.getId();
				List<WfType> wfTypes = instance.getSortService().getWfTypeBySortId(sortId);
				if (CollectionUtils.isNotEmpty(wfTypes)) {
					List<DtreeBean> wfTypeMenuList = new ArrayList<DtreeBean>();
					for (int j = 0; j < wfTypes.size(); j++) {
						WfType wfType = wfTypes.get(j);
						menuDtree = new MenuDtree();
						menuDtree.setLevel(2);
						menuDtree.setId(wfType.getCode());
						menuDtree.setText(wfType.getName());
						HashMap hash=new HashMap();
						hash.put("wftype", wfType.getWfType());
						menuDtree.setUserdata(hash);
						lst.add(menuDtree);
//						MenuDtree menuDtreeWfType = new MenuDtree();
//						menuDtreeWfType.setId(wfType.getCode());;
//						menuDtreeWfType.setText(wfType.getName());
//						menuDtreeWfType.setIm0("folderClosed.gif");
//						menuDtreeWfType.setIm1("folderClosed.gif");
//						menuDtreeWfType.setIm2("folderClosed.gif");
//						menuDtreeWfType.setParentId(wfSort.getCode());
//						wfTypeMenuList.add(menuDtreeWfType);
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

//		StringBuffer sb = new StringBuffer();
//		sb.append("select dt.dtcode dtcode, dt.dtname dtname, dti.divalue divalue,dti.diname diname");
//		sb.append(" from bs_t_sm_dictype dt, bs_t_sm_dicitem dti");
//		sb.append(" where dti.dtcode='formtype' and dt.dtcode = dti.dtcode");
//		QueryAdapter qa = new QueryAdapter();
//		DataTable datatable = qa.executeQuery(sb.toString(), null, 0, 0, 2);
//		int datatableLen = 0;
//		if(datatable!=null)
//		{
//			datatableLen = datatable.length();
//		}
//		DataRow dataRow;
//		DtreeBean menuDtree = null;
//		for(int row=0;row<datatableLen;row++){
//			dataRow = datatable.getDataRow(row);
//			menuDtree = new DtreeBean();
//			menuDtree.setId(dataRow.getString("divalue"));
//			menuDtree.setText(dataRow.getString("diname"));
//			menuDtree.setIm0("folderClosed.gif");
//			menuDtree.setIm1("folderClosed.gif");
//			menuDtree.setIm2("folderClosed.gif");
//			lst.add(menuDtree);
//		}
		return lst;
	}
}
