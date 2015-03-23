package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.ultrapower.eoms.common.constants.DutyConstants;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.service.DynamicDataService;
import com.ultrapower.eoms.common.core.component.tree.service.RepositorySortTypeTreeService;
import com.ultrapower.eoms.common.core.util.StringUtils;
//import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.remedy4j.core.RemedySession;

public class RepositorySortTypeTreeImpl extends TreeManager implements RepositorySortTypeTreeService,DynamicDataService{
	
	private DicManagerService dicManagerService;
	/**
	 *  案例库左侧树                                                                                             数据字典值
	 *  网络专题分析             可以定义，定义成多级                                       1
                    设备应急方案             从工单“网络分类”中获得                                   2
                    网管制度                      可以定义，定义成多级                                         3
                    厂家维护手册             可以定义，定义成多级                                        4
                   维护经验/监控案例         从工单“网络分类”中获得                            5
                   维护经验/故障案例         从工单“网络分类”中获得                            6
                   客户投诉案例             从工单“投诉类型”中获得                                     7
	 */
	public List<DtreeBean> getChildList(String pareid){
			 List<DtreeBean> list = new ArrayList();
			 pareid = pareid.indexOf(",")>0 ? pareid.split(",")[1] : "0";
			 if(8 == pareid.length()){
				 return list;
			 }
			 String tname = RemedySession.UtilInfor.getTableName("WF4:Config_EL_00_NetType");
			 String tcompname = RemedySession.UtilInfor.getTableName("WF4:Config_EL_TTM_CCH_ComplaintType");
			 HashMap map = null;
			 String sqlSortString = "";
			 String sqlidString = "";
			 QueryAdapter qAdapter  = new QueryAdapter();
			 DtreeBean menuDtree = null;
			 int endnum = 0;
			 if("0".equals(pareid)){//第一级：故障、投诉。。。
				 List<DicItem> listDic = dicManagerService.getRootItsmByDicType("repositoryType");
				 for(DicItem dicItem : listDic){
					 menuDtree = new DtreeBean();
					 menuDtree.setId("sortTypeTreeService,0"+dicItem.getDivalue());//数据字典值充当ID，两位
					 menuDtree.setText(dicItem.getDiname());
					 map = new HashMap();
					 map.put("url","ultrarepository/shareRepositoryList.action?pid=0"+dicItem.getDivalue());
					 menuDtree.setUserdata(map);
					 list.add(menuDtree);
				 }
				 return list;
			 }
			 String gongdan = pareid.substring(0, 2);
			 String id = (2 == pareid.length())?"0":pareid.substring(2);
			 if("02".equals(gongdan)||"05".equals(gongdan)||"06".equals(gongdan)){////网络类型分类
				 if(1 == id.length()){//二级节点
					 sqlSortString = "select DISTINCT C650000001 as sortone from "+tname;
					 sqlidString = "select DISTINCT C650000005 as idflag from "+tname+" where C650000001 = '";
					 endnum = 2;
				 }else if(2 == id.length()){//第三级节点
					 sqlSortString = "select DISTINCT C650000002 as sortone from "+tname+" where C650000005 like '"+id+"%'";
					 sqlidString = "select DISTINCT C650000005 as idflag from "+tname+" where C650000002 = '";
					 endnum = 4;
				 }else if(4 == id.length()){//第四级节点
					 sqlSortString = "select DISTINCT C650000004 as sortone from "+tname+" where C650000005 like '"+id+"%'";
					 sqlidString = "select DISTINCT C650000005 as idflag from "+tname+" where C650000004 = '";
					 endnum = 6;
				 }
//				 if(4 == id.length()){//第四级节点为季度
//					 return this.getQuar(menuDtree, pareid);
//				 }
			 }
			 if("07".equals(gongdan)){//投诉分类
				 if(1 == id.length()){//取第一级节点
					 sqlSortString = "select DISTINCT C650000002 as sortone from "+tcompname;
					 sqlidString = "select DISTINCT C650000102 as idflag from "+tcompname+" where C650000002 = '";
					 endnum = 2;
				 }else if(2 == id.length()){//第二级节点
					 sqlSortString = "select DISTINCT C650000003 as sortone from "+tcompname+" where C650000102 like '"+id+"%'";
					 sqlidString = "select DISTINCT C650000102 as idflag from "+tcompname+" where C650000003 = '";
					 endnum = 4;
				 }else if(4 == id.length()){//三级节点
					 sqlSortString = "select DISTINCT C650000004 as sortone from "+tcompname+" where C650000102 like '"+id+"%'";
					 sqlidString = "select DISTINCT C650000102 as idflag from "+tcompname+" where C650000004 = '";
					 endnum = 6;
				 }else if(6 == id.length()){//四级节点
					 sqlSortString = "select DISTINCT C650000005 as sortone from "+tcompname+" where C650000102 like '"+id+"%'";
					 sqlidString = "select DISTINCT C650000102 as idflag from "+tcompname+" where C650000005 = '";
					 endnum = 8;
				 }else if(8 == id.length()){//五级节点
					 sqlSortString = "select DISTINCT C650000006 as sortone from "+tcompname+" where C650000102 like '"+id+"%'";
					 sqlidString = "select DISTINCT C650000102 as idflag from "+tcompname+" where C650000006 = '";
					 endnum = 10;
				 }
//				 if(4 == id.length()){//第四级节点为季度
//					 return this.getQuar(menuDtree, pareid);
//				 }
			 }
			 if("04".equals(gongdan)){//厂家维护手册
				 list = this.getChildX(id, "supplierHandbook", gongdan);
				 return list;
			 }else if("03".equals(gongdan)){//网管制度
				 list = this.getChildX(id, "networkManagement", gongdan);
				 return list;
			 }else if("01".equals(gongdan)){//网络专题分析
				 list = this.getChildX(id, "networkAnalysis", gongdan);
				 return list;
			 }
			 if("".equals(sqlSortString)) return list;
			 DataTable dTable = qAdapter.executeQuery(sqlSortString, null, 0);
			 DataRow dRow = null;
			 DataRow didRow = null;
			 for (int j = 0; j < dTable.length(); j++) {
				menuDtree = new DtreeBean();
				dRow = dTable.getDataRow(j);
				if(dRow == null) continue;
				String sortone = dRow.getString("sortone");
				if("".equals(sortone)) continue;
				String sql =  sqlidString +sortone+"'";
				didRow = qAdapter.executeQuery(sql, null, 0).getDataRow(0);
				if(didRow != null){
					String idflag = didRow.getString("idflag");
					if("".equals(idflag)) continue;
					menuDtree.setId("sortTypeTreeService,"+gongdan+idflag.substring(0,endnum));
					map = new HashMap();
					map.put("url","ultrarepository/shareRepositoryList.action?pid="+gongdan+idflag.substring(0,endnum));
					menuDtree.setUserdata(map);
				}
				menuDtree.setText(sortone);
				list.add(menuDtree);
			}
		 return list;
	}
	/**
	 * 从数据字典取数据展示左侧树
	 * @param parentid
	 * @param dictype
	 * @return
	 */
	private List<DtreeBean> getChildX(String parentid, String dictype, String gongdan){
		if("".equals(parentid))
			return null;
		if(!"0".equals(parentid)){
			parentid = parentid.split(";")[0];
		}
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, diname, parentid, divalue");
		sql.append(" from bs_t_sm_dicitem di");
		sql.append(" where 1 = 1");
		sql.append(" and dtcode = ?");
		sql.append(" and parentid = ?");
		sql.append(" order by ordernum");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), new Object[] {dictype, parentid});
		DtreeBean menuDtree = null;
		DataRow dataRow;
		HashMap map = null;
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		/*if(0 == datatableLen){
			int currYeay = TimeUtils.getYearOfDate(TimeUtils.getCurrentDate());
			for(int j = DutyConstants.START_YEAR;j <= currYeay;j++){
				for(int i = 1;i < 5;i++){
					 menuDtree = new DtreeBean();
					 menuDtree.setId(parentid+","+j+"0"+i);
					 switch (i) {
					 case 1:
						menuDtree.setText(j+"年1季度");
						break;
					 case 2:
						 menuDtree.setText(j+"年2季度");
						 break;
					 case 3:
						 menuDtree.setText(j+"年3季度");	
						 break;
					 default:
						 menuDtree.setText(j+"年4季度");
						 break;
					 }
					 map = new HashMap();
					 map.put("url","ultrarepository/shareRepositoryList.action?pid="+gongdan+"&dictype="+dictype+"&dicvalue="+dicvalue+"&yearquar="+j+"0"+i);
					 menuDtree.setUserdata(map);
					 dtreeList.add(menuDtree);
				 }
			}
		}*/
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			menuDtree = new DtreeBean();
			String divalue = StringUtils.checkNullString(dataRow.getString("divalue"));
			menuDtree.setId("sortTypeTreeService,"+gongdan+StringUtils.checkNullString(dataRow.getString("pid"))+";"+divalue);
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("diname")));
			map = new HashMap();
			map.put("url","ultrarepository/shareRepositoryList.action?pid="+gongdan+"&dictype="+dictype+"&dicvalue="+divalue);
			menuDtree.setUserdata(map);
			dtreeList.add(menuDtree);
		}
		return dtreeList;
	}
	/**
	 * 构造最后一级节点季度
	 * @return
	 */
	/*public List<DtreeBean> getQuar(DtreeBean menuDtree,String pareid){
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		HashMap map = null;
		int currYeay = TimeUtils.getYearOfDate(TimeUtils.getCurrentDate());
		for(int j = DutyConstants.START_YEAR;j<= currYeay;j++){
		 for(int i = 1;i < 5;i++){
			 menuDtree = new DtreeBean();
			 menuDtree.setId(""+pareid+","+j+"0"+i);
			 switch (i) {
			 case 1:
				menuDtree.setText(j+"年1季度");
				break;
			 case 2:
				 menuDtree.setText(j+"年2季度");
				 break;
			 case 3:
				 menuDtree.setText(j+"年3季度");	
				 break;
			 default:
				 menuDtree.setText(j+"年4季度");
				 break;
			 }
			 map = new HashMap();
			 map.put("url","ultrarepository/shareRepositoryList.action?pid="+menuDtree.getId());
			 menuDtree.setUserdata(map);
			 dtreeList.add(menuDtree);
		 }
		}
		 return dtreeList;
	}*/
	/**
	 * 案例库网络分类树形数据
	 * @param id
	 * @return
	 */
	public List<DtreeBean> getSortList(String id){
		 List<DtreeBean> list = new ArrayList();
		 if(6 == id.length()){
			 return list;
		 }
		 String tname = RemedySession.UtilInfor.getTableName("WF4:Config_EL_00_NetType");
		 String sqlSortString = "";
		 String sqlidString = "";
		 String sqlAllTextString = "";
		 QueryAdapter qAdapter  = new QueryAdapter();
		 int endnum = 0;
		 if("0".equals(id)){//取第一级节点
			 sqlSortString = "select DISTINCT C650000001 as sortone from "+tname;
			 sqlidString = "select DISTINCT C650000005 as idflag from "+tname+" where C650000001 = '";
			 sqlAllTextString = "select DISTINCT C650000001 as allText from "+tname+" where C650000001 = '";
			 endnum = 2;
		 }else if(2 == id.length()){//第二级节点
			 sqlSortString = "select DISTINCT C650000002 as sortone from "+tname+" where C650000005 like '"+id+"%'";
			 sqlidString = "select DISTINCT C650000005 as idflag from "+tname+" where C650000002 = '";
			 sqlAllTextString = "select DISTINCT C650000001 || '.' || C650000002 as allText from "+tname+" where C650000002 = '";
			 endnum = 4;
		 }else if(4 == id.length()){//三级节点
			 sqlSortString = "select DISTINCT C650000004 as sortone from "+tname+" where C650000005 like '"+id+"%'";
			 sqlidString = "select DISTINCT C650000005 as idflag from "+tname+" where C650000004 = '";
			 sqlAllTextString = "select DISTINCT C650000006 as allText from "+tname+" where C650000004 = '";
			 endnum = 6;
		 }
		 if ("".equals(sqlSortString)) {
			 return list;
		 }
		 DataTable dTable = qAdapter.executeQuery(sqlSortString, null, 0);
		 DtreeBean menuDtree = null;
		 DataRow dRow = null;
		 DataRow didRow = null;
		 DataRow allTextRow = null;
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
			allTextRow = qAdapter.executeQuery(sqlAllTextString+sortone+"'", null, 0).getDataRow(0);
			if(allTextRow != null){
				String allText = allTextRow.getString("allText");
				map.put("allText", allText);
			}
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
	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}
	
}
