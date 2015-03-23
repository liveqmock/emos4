package com.ultrapower.eoms.workflow.sort.web;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfSort;

/**
 * 流程分类树
 * @author liuchuanzu
 * @version Jun 4, 2010 10:00:03 AM
 */
public class WfSortTreeImpl extends TreeManager{
	
	/** 
	 * 流程分类service对象
	 */
	private IWfSortManager ver;

	/**
	 * 接口ID
	 */ 
	private String wfSortId;
	
	/**
	 * 获取某一节点的所有子节点
	 */
	private List<WfSort> wfSortList = new ArrayList<WfSort>();
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid 父节点
	 * @return 返回查询到的树xml节点数据
	 */
	public String getChildXml(String parentid){
		if(parentid=="")
			return "";
		List<WfSort> sortList = new ArrayList<WfSort>();
		try {
			ver = WorkFlowServiceClient.clientInstance().getSortService();
			sortList = ver.getAllWfSort();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List<DtreeBean> dtreeChildrenList = getSortChild(parentid);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	/**
	 * 获取父节点下的分类和类型
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getSortChild(String parentid){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		
		List<WfSort> sortList = new ArrayList<WfSort>();
		try {
			ver = WorkFlowServiceClient.clientInstance().getSortService();
			sortList = ver.getChildSortById(parentid);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		for(WfSort sort:sortList){
			if(sort.getPid().equals(parentid)){
				DtreeBean dtree = new DtreeBean();
				dtree.setParentId(sort.getPid());
				dtree.setId(sort.getId());
				dtree.setText(sort.getName());
				HashMap<String,String> maps = new HashMap<String,String>();
				maps.put("sign","sort");
				dtree.setUserdata(maps);
//				dtree.setChild("1");
				//循环获取子节点
//				dtree.setChildList(this.getSortChild(dtree.getId(),sortList));
				dtreeList.add(dtree);
				
				
			}
		}
//		try {
//			List<WfType> typeList = ver.getWfTypeBySortId(parentid);
//			for(WfType type:typeList){
//				DtreeBean tree = new DtreeBean();
//				HashMap<String,String> maps = new HashMap<String,String>();
//				maps.put("sign","type");
//				tree.setUserdata(maps);
//				tree.setParentId(type.getSortId());
//				tree.setId(type.getId());
//				tree.setText(type.getName());
//				tree.setIm0("iconSound.gif");
//										
//				dtreeList.add(tree);
//			}
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
		return dtreeList;
	}

	
	/**
	 * 获取所有的子节点(递归)
	 * @return
	 */
	public List<WfSort> getAllChildSort(String pid){
		List<WfSort> list = new ArrayList<WfSort>();
		try {
			ver = WorkFlowServiceClient.clientInstance().getSortService();
			WfSort wfs = ver.getWfSortByid(pid);
			list.add(wfs);
			list = ver.getChildSortById(pid);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
//		wfSortList.addAll(list);
		for(WfSort wf:list){
			System.out.println(wf.getId()+"        "+wf.getPid());
			wfSortList.add(wf);
			this.getAllChildSort(wf.getId());
		}
		return list;
	}	
	

	public List<WfSort> getWfSortList(String pid) {
		this.getAllChildSort(pid);
		return wfSortList;
	}

	public void setWfSortList(List<WfSort> wfSortList) {
		this.wfSortList = wfSortList;
	}

	public String getWfSortId() {
		return wfSortId;
	}


	public void setWfSortId(String wfSortId) {
		this.wfSortId = wfSortId;
	}
}
