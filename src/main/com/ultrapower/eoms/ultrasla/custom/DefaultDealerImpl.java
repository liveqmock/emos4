package com.ultrapower.eoms.ultrasla.custom;

import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.service.ICustomOrganizationService;
import com.ultrapower.eoms.ultrasla.service.IOrganizationService;
import com.ultrapower.eoms.ultrasla.util.Receiver;
import com.ultrapower.eoms.ultrasla.util.ConstantMark.OrgType;

/**
 * 获取自定义组织机构接收者接口实现（默认处理人）
 * @author SunHailong
 */
public class DefaultDealerImpl implements ICustomOrganizationService {
	private IOrganizationService slaOrganizationService;
	
	public List<Receiver> getReceiverFromImpl(HashMap param) {
		List<Receiver> receiverList = null;
		// 获取必要参数
		String baseId = ""; // 工单id
		String baseSchema = ""; // 工单类别
		if(param != null) {
			Object obj = param.get("BASEID");
			if(obj != null) {
				baseId = (String) obj;
			}
			obj = param.get("BASESCHEMA");
			if(obj != null) {
				baseSchema = (String) obj;
			}
		}
		StringBuffer sql = new StringBuffer();
		// c700020010：执行人  c700020006：负责人  c700020008：负责组  别名代表着优先级
		sql.append("select t.c700020010 first, t.c700020006 second, t.c700020008 third");
		sql.append("  from {WF:App_DealProcess} t");
		sql.append(" where t.c700020001 = ?"); // 工单id
		sql.append("   and t.c700020002 = ?"); // 工单类别
		sql.append("   and t.c700020020 = '1'"); // 活动状态
		sql.append("   and t.c700020019 = '0'"); // 主办协办类型 0:主办
		DataTable table = this.getDataBySql(sql.toString(), new Object[] {baseId, baseSchema});
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
		}
		DataRow row;
		String dealer = ""; // 默认处理人(或组)
		OrgType orgType = null;
		List<Receiver> tmpRecList;
		for(int i=0 ; i<tableLen ; i++) {
			row = table.getDataRow(i);
			for(int j=0 ; j<row.length() ; j++) {
				dealer = row.getString(j);
				if(!"".equals(StringUtils.checkNullString(dealer))) {
					if(j < 2) {
						orgType = OrgType.USER;
					} else {
						orgType = OrgType.GROUP;
						dealer = String.format("%1$015d", row.getInt(j));
					}
					break;
				}
			}
			tmpRecList = slaOrganizationService.getReceiver(dealer, orgType, 1); // 获取接收者信息
			// 合并接收者信息
			if(receiverList == null) {
				receiverList = tmpRecList;
			} else {
				if(tmpRecList != null) {
					receiverList.addAll(tmpRecList);
				}
			}
		}
		return receiverList;
	}

	private DataTable getDataBySql(String sql, Object[] obj) {
		DataTable table = null;
		QueryAdapter queryAdapter = new QueryAdapter();
		try {
			table = queryAdapter.executeQuery(SqlReplace.stringReplaceAllVar(sql.toString(), null), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	
	public void setSlaOrganizationService(IOrganizationService slaOrganizationService) {
		this.slaOrganizationService = slaOrganizationService;
	}
}
