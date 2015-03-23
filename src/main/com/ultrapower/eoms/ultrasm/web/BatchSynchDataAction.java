package com.ultrapower.eoms.ultrasm.web;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.service.BatchSynchDataService;

public class BatchSynchDataAction extends BaseAction {
	private BatchSynchDataService batchSynchData;

	public String batchSynchDataList() {
		return SUCCESS;
	}
	
	/**
	 * 批量将pasm的数据同步到eoms
	 */
	public void patchSynchFromPasm() {
		this.patchSynchUserFromPasm(); // 批量同步用户
		this.patchSynchGroupFromPasm(); // 批量同步组
	}
	
	/**
	 * 批量将pasm的用户信息同步到eoms
	 */
	public void patchSynchUserFromPasm() {
		batchSynchData.pasmBatchSynchUserToEoms("Demo");
	}
	
	/**
	 * 批量将pasm的部门信息同步到eoms
	 */
	public void patchSynchGroupFromPasm() {
		batchSynchData.pasmBatchSynchDeptToEoms();
	}
	
	public void setBatchSynchData(BatchSynchDataService batchSynchData) {
		this.batchSynchData = batchSynchData;
	}
}
