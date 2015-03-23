package com.ultrapower.eoms.ultrasla.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.ultrasla.util.Receiver;

/**
 * SQL配置实现获取接收者的类
 * @author SunHailong
 */
public class ReceiverSqlImpl {
	/**
	 * 通过SQL查询获得消息接收者信息
	 * @param param
	 * @param sqlName
	 * @return
	 */
	public List<Receiver> getReceiverFromSql(final HashMap param, String sqlName) {
		List<Receiver> recList = null;
		RQuery rquery = new RQuery(sqlName, param);
		DataTable table = rquery.getDataTable(); // 获取SQL数据集
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
			recList = new ArrayList<Receiver> ();
		}
		Receiver receiver;
		DataRow row;
		// 以下为封装接收者列表信息代码
		for (int i=0 ; i<tableLen ; i++) {
			row = table.getDataRow(i);
			if(row == null) {
				continue;
			}
			receiver = new Receiver();
			receiver.setId(row.getString("pid"));
			receiver.setLoginName(row.getString("loginname"));
			receiver.setFullName(row.getString("fullname"));
			receiver.setMobile(row.getString("mobile"));
			receiver.setEmail(row.getString("email"));
			recList.add(receiver);
		}
		return recList;
	}
}
