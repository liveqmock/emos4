package com.ultrapower.eoms.msextend.workflow.manager;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.msextend.workflow.model.WfRecord;
import com.ultrapower.eoms.msextend.workflow.service.WorkflowService;

public class WorkflowServiceImpl implements WorkflowService{
	IDao<WfRecord> wfRecordDao;

	public void addWfRecord(WfRecord record) {
		wfRecordDao.save(record);
	}

	public void updateWfRecord(WfRecord record) {
		
	}

	public WfRecord getWfRecordById(String pid) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<WfRecord> getWfRecords(String baseschema,String baseid) {
		List<WfRecord> list = wfRecordDao.find("from WfRecord t where t.baseId = ? and t.baseSchema = ? and t.isView = '1' order by t.dealTime desc", baseid,baseschema);
		return list;
	}

	public void setWfRecordDao(IDao<WfRecord> wfRecordDao) {
		this.wfRecordDao = wfRecordDao;
	}

	
	public List getMailContentByType(String modelType) {
		String sql = "SELECT CONTENT FROM BS_T_SM_MAILMODEL WHERE MODELTYPE = :modelType";
		List list = wfRecordDao.createSQLQuery(sql).setParameter("modelType",modelType ).list();
		return list;
	}

	public List<WfRecord> getWfRecordByCondition(String baseId, String dealAction) {
		List<WfRecord> list= wfRecordDao.find("from WfRecord t where t.baseId = ? and t.dealAction = ?", baseId,dealAction);
		return list;
	}

	/**
	 * 更新baseinfor表中的cdbteamid和cdbteamname
	 */
    public void updateCustomBaseInfor(Map<String, String> map) {
      String upSql = "UPDATE  bs_t_bpp_baseinfor t set t.CDBTEAMID = '"+map.get("cdbTeamId")+"', t.CDBTEAMNAME= ' "+map.get("cdbTeamName")+"' where basesn = '"+map.get("BASESN")+"'";
      DataAdapter dataAdapter  = new DataAdapter();
      dataAdapter.execute(upSql,null);
    }

}
