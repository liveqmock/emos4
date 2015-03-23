package com.ultrapower.eoms.msextend.serverQuest.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class CreatTreeImpl extends TreeManager{
	private QueryAdapter queryAdapter = new QueryAdapter();
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid
	 * @return
	 */
	public String getChildXml(String parentid)
	{
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = null;
			dtreeChildrenList = getChildX(parentid);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	/**
	 * 根据传入的父节点,启用状态进行子节点树的xml拼接
	 */
	public String getChildXmlByStatus(String parentid,String status)
	{
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = getChildXByStatus(parentid, status);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	public String getMenuTreeXml(List<MenuDtree> menuDtreeList)
	{
		return this.createDhtmlxtreeXml(menuDtreeList);
	}
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChildX(String parentid)
	{
		return getChildXByStatus(parentid,null);
	}
	/**
	 * 根据启用状态获取服务请求信息
	 */
	@SuppressWarnings({"rawtypes","unchecked"})
	private List<DtreeBean> getChildXByStatus(String parentid,String status)
	{
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.pid,t.parentid,t.SERVERQUESTNAME,t.dilever,t.serverquestfullname   ");
		sql.append("  from BS_T_WF_SERVERQUEST t");
		sql.append(" where  t.parentid = '"+parentid+"'");
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(status)){
			sql.append(" and t.status='"+status+"'");
		}
		sql.append(" order by t.ordernum ");
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
			HashMap map = new HashMap();
			map.put("dilever", dataRow.getString("dilever"));
			map.put("serverquestfullname", dataRow.getString("serverquestfullname"));
			dtreeBean.setUserdata(map);
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("SERVERQUESTNAME")));
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	//获取完整的服务目录树人XML
	public String getServiceCategoryTreeXML(HashMap<String, String> parameter){
		return new TreeManager().createDHTMLXtreeXML(getServiceCategoryDtreeBean(parameter));
	}
	
	public DtreeBean getServiceCategoryDtreeBean(HashMap<String, String> parameter){
		RQuery query = new RQuery("SQL_CDB_SERVEICE_CATEGORY_S.query", parameter); 
		System.out.println(query.getSqlString());
		DataTable dataTable = query.getDataTable(); 
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		Set<String> serviceCategroySet = new HashSet<String>();
		for(int row=0,len=dataTable.length();row<len;row++){
			DataRow dataRow = dataTable.getDataRow(row);
			String fullname = dataRow.getString("value");
			String id = dataRow.getString("key");
			String[] nameArray = fullname.split("\\.");
			for(int i = 0,len2 = nameArray.length; i<len2; i++){
				String serviceCategoryFullname = "";
				for(int j = 0;j<=i;j++){
					serviceCategoryFullname += nameArray[j] + ".";
				}
				serviceCategoryFullname = serviceCategoryFullname.substring(0, serviceCategoryFullname.length()-1);
				if(!serviceCategroySet.contains(serviceCategoryFullname)){
					serviceCategroySet.add(serviceCategoryFullname);
					DtreeBean serviceCategory = new DtreeBean();
					serviceCategory.setText(nameArray[i]);
					serviceCategory.setId(serviceCategoryFullname);
					HashMap<String, String> userdata = new HashMap<String, String>();
					userdata.put("serverquestfullname", serviceCategoryFullname);
					if(i==0) {
						userdata.put("dilever", "1");
						serviceCategory.setUserdata(userdata);
						dtreeList.add(serviceCategory);//一级服务目录直接加入list
					}
					if(i==1){//二级服务目录加入到一级服务目录下
						userdata.put("dilever", "2");
						serviceCategory.setUserdata(userdata);
						for(DtreeBean dtb : dtreeList){
							if(dtb.getId().equals(nameArray[0])){
								if(dtb.getChildList()==null){
									dtb.setChildList(new ArrayList<DtreeBean>());
								}
								dtb.getChildList().add(serviceCategory);
								break;
							}
						}
					}
					if(i==2){//三级服务目录加入到二级服务目录下
						serviceCategory.setId(id);
						userdata.put("dilever", "3");
						serviceCategory.setUserdata(userdata);
						for(DtreeBean dtb : dtreeList){
							for(DtreeBean dtb2 : dtb.getChildList()){
								if(dtb2.getId().equals(nameArray[0]+"."+nameArray[1])){
									if(dtb2.getChildList()==null){
										dtb2.setChildList(new ArrayList<DtreeBean>());
									}
									dtb2.getChildList().add(serviceCategory);
									break;
								}
							}
						}
					}
				}
			}
		}
		DtreeBean tree = new DtreeBean();
		tree.setId("0");
		tree.setChildList(dtreeList);
		return tree;
	}
	
}
