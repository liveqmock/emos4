package com.ultrapower.eoms.msextend.serverQuest.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestModel;
import com.ultrapower.eoms.msextend.serverQuest.service.ServerQuestService;
/**
 * @author孙凯
 *	服务请求相关操作
 */
@SuppressWarnings("unchecked")
public class ServerQuestServiceImpl  implements ServerQuestService{
	private IDao<ServerQuestModel> serverQuestModelDao;
	public List<ServerQuestModel> getHelpInfoByScheme(String baseSchema) {
		String hql=" From CriterionHelp where yesornoshow=1 and  (nodeType=? or helpType='通用帮助') order by compositorNum ";
		List<ServerQuestModel> criterionHelpList=serverQuestModelDao.find(hql, new Object[]{baseSchema});
		return criterionHelpList;
	}
	
	public void saveServerQuest(ServerQuestModel serverQuestModel) {
		if(serverQuestModel!=null&&serverQuestModel.getPid()!=null&&!"".equals(serverQuestModel.getPid())){
			serverQuestModelDao.saveOrUpdate(serverQuestModel);
		
		}else{
			serverQuestModelDao.save(serverQuestModel);
		}
	}
	//删除节点并删除其下的子节点
	public void delServerQuest(String id) {
		ServerQuestModel serverQuestModel=getServerQuestById(id);
		DataAdapter qAdapter = new DataAdapter();
		
		String sql1=" delete BS_T_WF_SERVERQUEST where parentid in (select pid from  BS_T_WF_SERVERQUEST where parentid='"+serverQuestModel.getPid()+"')";
		qAdapter.execute(sql1.toString(), null);
		String sql=" delete BS_T_WF_SERVERQUEST where parentid='"+serverQuestModel.getPid()+"'";
		qAdapter.execute(sql.toString(), null);
		if(serverQuestModel!=null&&serverQuestModel.getPid()!=null&&!"".equals(serverQuestModel.getPid())){
			serverQuestModelDao.remove(serverQuestModel);
		}
		
	}
	//得到父节点
	public ServerQuestModel getPranetCriterInfo(ServerQuestModel criterionHelp) {
		ServerQuestModel parentcriterionHelp=null;
		if(criterionHelp!=null){
			parentcriterionHelp=getServerQuestById(criterionHelp.getPid());
		}
		if(parentcriterionHelp==null){
			parentcriterionHelp=new ServerQuestModel();
		}
		return parentcriterionHelp;
	}
	//根据id得到信息
	public ServerQuestModel getServerQuestById(String id) {
		ServerQuestModel serverQuestModel=serverQuestModelDao.get(id);
		if(serverQuestModel==null){
			serverQuestModel=new ServerQuestModel();
		}
		return serverQuestModel;
	}
//	//得到节点树形结构xml
//	public String getHelpXml( String id ) {
//		 DictionaryTreeImpl dicTreeImpl = new DictionaryTreeImpl();
//		 List<MenuDtree> list=new ArrayList<MenuDtree>();
//		 list=this.getHelpList();
//		 String rendXml="";
//		 rendXml=dicTreeImpl.createDhtmlxtreeXml(list);
//		return rendXml;
//	}	
//	public List<MenuDtree>  getHelpList(){
//		QueryAdapter queryAdapter = new QueryAdapter();
//		List<MenuDtree> menuTreeList = new ArrayList<MenuDtree>();
//		StringBuffer sql = new StringBuffer();
//		sql.append("select level,id,nodeName,compositorNum,nodeLink,remark,pid ");
//		sql.append("   from BS_T_WF_SERVERQUEST ");
//		sql.append(" start with pid = '0'");
//		sql.append(" connect by pid = prior id");
//		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), new Object[] {});
//		int dataTableLen = 0;
//		if(dataTable!=null)
//			dataTableLen = dataTable.length();
//		MenuDtree menuDtree = null;
//		DataRow dataRow;
//		for(int row=0;row<dataTableLen;row++){
//			dataRow = dataTable.getDataRow(row);
//			menuDtree = new MenuDtree();
//			menuDtree.setLevel(Integer.parseInt(dataRow.getString("level")));
//			String id = StringUtils.checkNullString(dataRow.getString("id"));
//			//String divalue = StringUtils.checkNullString(dataRow.getString("divalue"));
//			menuDtree.setId(id);
//			String text = StringUtils.checkNullString(dataRow.getString("nodeName"));
//			menuDtree.setText(text);
//			String pid = StringUtils.checkNullString(dataRow.getString("pid"));
//			menuDtree.setParentid(pid);
//			HashMap map = new HashMap();
//			map.put("id", id);
//			map.put("text", text);
//			map.put("pid", pid);
//			menuDtree.setUserdata(map);
//			menuTreeList.add(menuDtree);
//		}
//		return menuTreeList;
//	
//	}

	public IDao<ServerQuestModel> getServerQuestModelDao() {
		return serverQuestModelDao;
	}

	public void setServerQuestModelDao(IDao<ServerQuestModel> serverQuestModelDao) {
		this.serverQuestModelDao = serverQuestModelDao;
	}

	public String getHelpXml(String id) {
		return null;
	}

	public ServerQuestModel getServerQuestByFullname(String fullname) {
		return serverQuestModelDao.findUnique("from ServerQuestModel t where t.serverquestfullname = ?", fullname);
	}

	public List<ServerQuestModel> getCommonServerQuest(HashMap<String, String> parameter) {
		List<ServerQuestModel> list = new ArrayList<ServerQuestModel>();
		RQuery query = new RQuery("SQL_CDB_SERVEICE_CATEGORY_S.query", parameter);
		DataTable dataTable = query.getDataTable();
		for(Object o : dataTable.getRowList()){
			DataRow row = (DataRow)o;
			ServerQuestModel model = new ServerQuestModel();
			model.setServerquestfullname(row.getString("serverquestfullname"));
			model.setServerquestname(row.getString("SERVERQUESTNAME"));
			list.add(model);
		}
		return list;
	}
	public ServerQuestModel getServerQuestByPID(String serviceCategoryid) {
		return serverQuestModelDao.findUnique("from ServerQuestModel t where t.pid = ?", serviceCategoryid);
	}

	public List<ServerQuestModel> getCommonServerQuest() {
		List<ServerQuestModel> list = serverQuestModelDao.find("from ServerQuestModel t where t.isCommon = '1'");
		return list;
	}

	@Override
	public String isSendAudit(String fullname) {
		return getServerQuestByFullname(fullname).getIsSendAudit();
	}
	
}
