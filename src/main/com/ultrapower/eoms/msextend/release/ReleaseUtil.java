package com.ultrapower.eoms.msextend.release;

import java.util.HashMap;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.ultrasm.model.ChgBatchInfo;

public class ReleaseUtil {
	//根据id查询批次信息对象
	public static ChgBatchInfo queryChgBatchInfo(String ids){
		Map<String,String> ChgBatchInfoMap = new HashMap<String, String>(); 
		if(ids != null && !ids.equals("")){
			String querySql = "select * from BS_T_SM_BATCHCONF where pid = '" + ids + "'";
			QueryAdapter query = new QueryAdapter();
			DataTable dt = query.executeQuery(querySql);
			if(dt != null && dt.length() == 1){
				ChgBatchInfoMap = dt.getDataRow(0).getRowHashMap();
			}
		}
		ChgBatchInfo chgBatchInfo = new ChgBatchInfo();
		chgBatchInfo.setPid(ChgBatchInfoMap.get("PID"));
		chgBatchInfo.setBatch_no(ChgBatchInfoMap.get("BATCH_NO"));
		chgBatchInfo.setChg_start_time(ChgBatchInfoMap.get("CHG_START_TIME"));
		chgBatchInfo.setChg_end_time(ChgBatchInfoMap.get("CHG_END_TIME"));
		chgBatchInfo.setReviewtime(ChgBatchInfoMap.get("REVIEWTIME"));
		chgBatchInfo.setLatestreviewtime(ChgBatchInfoMap.get("LATESTREVIEWTIME"));
		chgBatchInfo.setExec_flag(ChgBatchInfoMap.get("EXEC_FLAG"));
		return chgBatchInfo;
	}
}
