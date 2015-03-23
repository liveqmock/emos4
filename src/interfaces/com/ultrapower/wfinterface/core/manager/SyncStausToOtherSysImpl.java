package com.ultrapower.wfinterface.core.manager;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.wfinterface.core.service.SyncStausToOtherSysService;


public class SyncStausToOtherSysImpl implements SyncStausToOtherSysService {
	public static QueryAdapter queryAdapter = new QueryAdapter();
	private static DataAdapter dataAdapter  = new DataAdapter();
	/**
	 * 获取等待同步的发布工单
	 */
	public DataTable getReSheetWaitToSync() {
		QueryAdapter query;
		String querySql = "SELECT * FROM BS_F_CDB_RELEASE T WHERE T.CONNBASEID IS NOT NULL AND T.SYNCSTATUS IS NULL AND T.BASESTATUS='已关闭' ";
		query = new QueryAdapter();
		DataTable dataTable = query.executeQuery(querySql);
		return dataTable;
	}
	 

	/**
	 * 更新工单同步状态
	 * @param baseId
	 * syncstatus: 0表示不进行同步的工单,由流程系统创建;null需要同步的工单,由接口建单;1表示已同步的工单
	 */
	
	public Boolean updateReSheetSyncStatus(String baseId, String syncStatus) {
		boolean flag = false;
		String tblName = "BS_F_CDB_RELEASE";
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("SYNCSTATUS", syncStatus);
		String conditions = "BASEID=?";
		Object[] values = {baseId};
		int result = dataAdapter.putDataRow(tblName, p_dtrow, conditions, values);
		if(result>0)
			flag = true;
		return flag;
	}

}

	