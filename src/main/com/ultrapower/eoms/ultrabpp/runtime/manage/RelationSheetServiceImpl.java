package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrabpp.runtime.model.RelationSheetModel;
import com.ultrapower.eoms.ultrabpp.runtime.service.RelationDataBuildService;
import com.ultrapower.eoms.ultrabpp.runtime.service.RelationSheetService;
import com.ultrapower.workflow.relate.model.RelateModel;

public class RelationSheetServiceImpl implements RelationSheetService
{

	IDao<RelateModel> relaDao;
	public List<RelationSheetModel> getRelationList(String baseID)
	{
		List<RelationSheetModel> relationList = new ArrayList<RelationSheetModel>();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select baseid as baseid,baseschema as baseschema,basename as basename,basesn as basesn,relatetype as relatetype,");
		sqlBuffer.append(" relatetime as relatetime,relateusername  as relateusername from bs_t_wf_relation t where t.relatebaseid='"+baseID+"' and t.baseschema<>'CHG_TASK'");
		sqlBuffer.append(" union select relatebaseid as baseid,relatebaseschema as baseschema,relatebasename as basename,relatebasesn as basesn,relatetype as relatetype,relatetime as relatetime,relateusername  as relateusername ");
		sqlBuffer.append(" from bs_t_wf_relation t where t.baseid='"+baseID+"'");
		QueryAdapter qAdapter = new QueryAdapter();
		DataTable dataTable = qAdapter.executeQuery(sqlBuffer.toString());
		Map<String,StringBuffer> tableName = new HashMap<String,StringBuffer>();
		if(dataTable!=null){
			List<DataRow> dataRows = dataTable.getRowList();
			for(DataRow dataRow : dataRows)
			{
				String baseid = dataRow.getString("baseid");
				String basesn = dataRow.getString("basesn");
				String baseschema = dataRow.getString("baseschema");
				String basename = dataRow.getString("basename");
				StringBuffer baseSql = tableName.get(baseschema);
				if(baseSql==null){
					baseSql=new StringBuffer(" select t.basesn as basesn,t.baseid as baseid,t.baseschema as baseschema,'"+basename+"' as basename,GET_DEAL(t.baseid) as dealer,t.basecreatedate as basecreatedate,t.basesummary as basesummary,t.basestatus as basestatus from ");
					baseSql.append(" BS_F_" + baseschema+" t ");
					baseSql.append(" where baseid='"+baseid+"' ");
				}else{
					baseSql.append("or baseid='"+baseid+"'");
				}
				tableName.put(baseschema, baseSql);
			}
			if(tableName.size()>0){
				StringBuffer bulitSql = new StringBuffer();
				Iterator iterator = tableName.keySet().iterator();
				while(iterator.hasNext()){
					String key = (String)iterator.next();
					if(bulitSql.length()!=0){
						bulitSql.append(" union ");
					}
					bulitSql.append(tableName.get(key));
				}
				
				DataTable sheetTable  = qAdapter.executeQuery(bulitSql.toString());
				if(sheetTable!=null){
					List<DataRow> sheetRows = sheetTable.getRowList();
					for(DataRow dataRow : sheetRows)
					{
						String baseid = dataRow.getString("baseid");
						String basesn = dataRow.getString("basesn");
						String baseschema = dataRow.getString("baseschema");
						String basename = dataRow.getString("basename");
						String dealer = dataRow.getString("dealer");
						Long basecreatedate = dataRow.getLong("basecreatedate");
						String basesummary = dataRow.getString("basesummary");
						String basestatus = dataRow.getString("basestatus"); 
						RelationSheetModel relationModel = new RelationSheetModel();
						relationModel.setBaseID(baseid);
						relationModel.setBaseSchema(baseschema);
						relationModel.setBaseSn(basesn);
						relationModel.setBaseStatus(basestatus);
						relationModel.setBaseSummary(basesummary);
						relationModel.setCreateTime(basecreatedate+"");
						relationModel.setDealor(dealer);
						relationModel.setBaseName(basename);
						relationList.add(relationModel);
					}
				}
			}
		}
		return relationList;
	}

	public void removeRelationById(String baseID, String relationBaseID)
	{
		RelateModel relateModel = relaDao.findUnique("from RelateModel where baseId=? and relateBaseId=?", new String[]{baseID,relationBaseID});
		if(relateModel==null){
			relateModel = relaDao.findUnique("from RelateModel where baseId=? and relateBaseId=?", new String[]{relationBaseID,baseID});
		}
		if(relateModel!=null){
			relaDao.remove(relateModel);
		}
	}

	public String addRelation(String baseID, String relationBaseID,String relationTaskID)
	{
		QueryAdapter qAdapter = new QueryAdapter();
		String sql = "select * from bs_t_bpp_baseinfor t where t.baseid='"+baseID+"' or t.baseid='"+relationBaseID+"'";
		DataTable dataTable = qAdapter.executeQuery(sql);
		if(!checkRelation(baseID,relationBaseID)){
			return "您选择的工单已经与当前工单存在关联关系，不能重复关联。";
		}
		if(dataTable!=null&&dataTable.length()==2){
			RelateModel model = new RelateModel();
			List<DataRow> dataRows = dataTable.getRowList();
			for(DataRow dataRow : dataRows)
			{
				String baseid = dataRow.getString("baseid");
				String basesn = dataRow.getString("basesn");
				String baseschema = dataRow.getString("baseschema");
				String basename = dataRow.getString("basename");
				if(baseID.equals(baseid)){
					model.setBaseId(baseid);
					model.setBaseSchema(baseschema);
					model.setBaseName(basename);
					model.setBaseSN(basesn);
				}else{
					model.setRelateBaseId(baseid);
					model.setRelateBaseSn(basesn);
					model.setRelateBaseSchema(baseschema);
					model.setRelateBaseName(basename);
					model.setRelateTaskId(relationTaskID);
				}
				model.setRelateType(0);
				model.setRelateTime(TimeUtils.getCurrentTime());
				//model.setRelateUserLoginName(actorId);
			}
			relaDao.save(model);
			return "工单关联成功。";
		}else{
			return "工单不存在，请联系管理员核对。";
		}
	}

	private boolean checkRelation(String baseID, String relationBaseID){
		RelateModel relateModel = relaDao.findUnique("from RelateModel where baseId=? and relateBaseId=?", new String[]{baseID,relationBaseID});
		if(relateModel==null){
			relateModel = relaDao.findUnique("from RelateModel where baseId=? and relateBaseId=?", new String[]{relationBaseID,baseID});
		}
		if(relateModel==null)return true;
		else return false;
	}
	
	
	public HashMap<String, String> buildRelateData(HashMap<String, String> relateDataMap, String relateBaseID, String relateBaseSchema, String baseSchema)
	{
		boolean flag = WebApplicationManager.webApplicationContext.containsBean("from"+relateBaseSchema+"To"+baseSchema);
		if(flag){
			RelationDataBuildService service = (RelationDataBuildService)WebApplicationManager.getBean("from"+relateBaseSchema+"To"+baseSchema);
			return service.buildRelateData(relateDataMap, relateBaseID, relateBaseSchema, baseSchema);
		}
		else{
			return relateDataMap;
		}
	}

	public IDao<RelateModel> getRelaDao()
	{
		return relaDao;
	}

	public void setRelaDao(IDao<RelateModel> relaDao)
	{
		this.relaDao = relaDao;
	}


}
