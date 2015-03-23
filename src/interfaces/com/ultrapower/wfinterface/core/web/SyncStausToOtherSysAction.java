package com.ultrapower.wfinterface.core.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.wfinterface.core.manager.DefaultWFInterfaceConfig;
import com.ultrapower.wfinterface.core.manager.SyncStausToOtherSysImpl;
import com.ultrapower.wfinterface.core.service.IWFInterfaceConfig;
import com.ultrapower.wfinterface.itms.BusinessProcessSheetService;
import com.ultrapower.wfinterface.itms.ITMISImplFinishService;
import com.ultrapower.wfinterface.itms.ITMISImplFinishServiceLocator;

public class SyncStausToOtherSysAction extends BaseAction {

	ApplicationContext context = new ClassPathXmlApplicationContext("spring/wfinterface.xml");
	SyncStausToOtherSysImpl syncStausToOtherSysService = (SyncStausToOtherSysImpl) context.getBean("syncStausToOtherSysService");

	/**
	 * 工单同步入口方法
	 */
	public void syncReSheetToOtherSys() {

		RecordLog.printLog("ready to sync the sheetInfo",RecordLog.LOG_LEVEL_INFO);
		int dataTableLen = 0;
		// 获取需要进行同步的工单
		DataTable reSheetDt;
		DataRow dataRow;
		reSheetDt = syncStausToOtherSysService.getReSheetWaitToSync();
		if (reSheetDt != null)
			dataTableLen = reSheetDt.length();
		RecordLog.printLog("syncReSheetToOtherSys 扫描BS_F_CDB_RELEASE表,获取到等待同步的工单数目:"+ dataTableLen, RecordLog.LOG_LEVEL_INFO);
		reSheetDt = syncStausToOtherSysService.getReSheetWaitToSync();
		String callTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		//读取ITMIS webservice地址
		String url = PropertiesUtils.getProperty("ifc.itms.url");
		URL u = null;
		try {
			 u = new URL(url);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DataTable tempTable = new DataTable("");
		for (int row = 0; row < dataTableLen; row++) {
			dataRow = reSheetDt.getDataRow(row);
			tempTable.removeAllDataRow();
			tempTable.putDataRow(dataRow);
			String opdetail = "";
			try {
				opdetail = config.formatTable2Xml(tempTable,"com.ultrapower.wfinterface.core.test");
			} catch (Exception e) {
				RecordLog.printLog("转换XML格式数据失败！", RecordLog.LOG_LEVEL_ERROR, e);
				e.printStackTrace();
			}

			String baseId = dataRow.getString("BASEID");
			String resultStr = "";
			//调用itmiswebservice
			ITMISImplFinishService isf = new ITMISImplFinishServiceLocator();
			BusinessProcessSheetService service = null;
			try {
				service = isf.getSOAPEventSource (u);
				resultStr = service.implFinish("ITSM", "password", callTime, opdetail,"<attachRef></attachRef>");
			} catch (ServiceException e) {
				RecordLog.printLog("AFIXException", RecordLog.LOG_LEVEL_ERROR, e);
				e.printStackTrace();
			}
			catch (RemoteException e) {
				RecordLog.printLog("调用远程服务失败", RecordLog.LOG_LEVEL_ERROR, e);
				e.printStackTrace();
			}

			// 如果调用成功
			if (resultStr.startsWith("0")) {
				updateReSheetSyncStatus(baseId, "1");
			} else {
				RecordLog.printLog("远程服务同步失败", RecordLog.LOG_LEVEL_ERROR);
			}
			// 调用失败增加日志信息

		}

	}

	/**
	 * 更新工单同步状态
	 * @param baseId
	 * syncstatus: 0表示不进行同步的工单,由流程系统创建;null需要同步的工单,由接口建单;1表示已同步的工单
	 */

	private void updateReSheetSyncStatus(String baseId, String syncStatus) {
		Boolean flag = syncStausToOtherSysService.updateReSheetSyncStatus(baseId, syncStatus);
		if (flag) {
			RecordLog.printLog("BaseId为:" + baseId + "的工单状态同步成功",RecordLog.LOG_LEVEL_INFO);
		} else {
			RecordLog.printLog("BaseId为:" + baseId + "的工单状态同步失败",RecordLog.LOG_LEVEL_INFO);
		}
	}

}
