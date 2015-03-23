package com.ultrapower.wfinterface.core.service;

import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataTable;

public interface SyncStausToOtherSysService {

	//获取等待同步的发布工单
	public DataTable getReSheetWaitToSync();
}
