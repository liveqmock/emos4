package com.ultrapower.eoms.workflow.api;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.workflow.api.action.AcceptAction;
import com.ultrapower.eoms.workflow.api.action.ChaseAction;
import com.ultrapower.eoms.workflow.api.action.HastenAction;
import com.ultrapower.eoms.workflow.api.action.NextAction;
import com.ultrapower.eoms.workflow.api.action.NoticeAction;
import com.ultrapower.eoms.workflow.api.action.RejectAction;
import com.ultrapower.eoms.workflow.api.action.NewAction;
import com.ultrapower.eoms.workflow.api.action.SaveAction;
import com.ultrapower.eoms.workflow.api.action.SuggestAction;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.workflow.api.action.BaseAction;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;


public class DefActionTest {

	public static void main(String[] args) {
		//故障工单
		List<BaseFieldInfo> tthFields = new ArrayList<BaseFieldInfo>();
		tthFields.add(new BaseFieldInfo("BaseSummary","700000011","huang 11.12 接口测试" + TimeUtils.getCurrentDate()));
		tthFields.add(new BaseFieldInfo("BaseItems","700000014","数据网.IP承载网.安全设备"));
		tthFields.add(new BaseFieldInfo("INC_FindQuomodo","800020012","作业计划"));
		tthFields.add(new BaseFieldInfo("INC_ResponseLevel","800020013","一级响应"));
		tthFields.add(new BaseFieldInfo("INC_AssignType","800020015","手工派发"));
		tthFields.add(new BaseFieldInfo("INC_EquipmentManufacturer","800020017","摩托罗拉"));
		tthFields.add(new BaseFieldInfo("INC_HappenTime","800020020","111",7));
		tthFields.add(new BaseFieldInfo("INC_Province","800020021","北京"));
		tthFields.add(new BaseFieldInfo("INC_City","800020022","北京"));
		tthFields.add(new BaseFieldInfo("AlarmLevel","800020026","一级告警"));
		
		List<DealObject> dealObj = new ArrayList<DealObject>();
		
		String baseSchema = "WF4:EL_TTM_TTH";
		String baseId = null;
		String dealDesc = null;
		String userName = "Demo";
		String actionText = "";
		List<BaseAttachmentInfo> attachs = null;
		BaseAction action = null;
		try {
			//新建
			action = new NewAction();
			if (action.doAction(baseSchema, baseId, userName, dealDesc, "保存草稿", dealObj, tthFields, attachs)) {
				System.out.println(action.getBaseId());;
			}
			
			//保存
//			dealDesc = "保存测试！";
//			action = new SaveAction();
//			baseId = "000000000006975";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//下一步
//			dealDesc = "角色细分匹配测试！";
//			action = new NextAction();
//			baseId = "000000000006975";
//			actionText = "nextTest";
//			userName = "huangwei";
//			tthFields.add(new BaseFieldInfo("AffectBussType","800020025","国际话务", true));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, null)) {
//				System.out.println(action.getBaseId());
//			}
			
			//催办
//			dealDesc = "催办测试！";
//			action = new HastenAction();
//			baseId = "000000000006975";
//			dealObj.add(new DealObject("liuchuanzu", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//阶段建议
//			dealDesc = "阶段建议测试！";
//			action = new SuggestAction();
//			baseId = "000000000006975";
//			dealObj.add(new DealObject("liuchuanzu", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//追回
//			dealDesc = "追回测试！";
//			action = new ChaseAction();
//			baseId = "000000000006975";
//			userName = "Demo";
//			dealObj.add(new DealObject("wangwumei", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, "xxxxxx", dealDesc, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());
//			}
			
			//驳回
//			dealDesc = "驳回测试！";
//			action = new RejectAction();
//			baseId = "000000000006975";
//			userName = "liuchuanzu";
//			dealObj.add(new DealObject("Demo", "", ""));
//			action.doAction(baseSchema, baseId, userName, "xxxxxx", dealDesc, dealObj, tthFields, attachs);
			
			//受理
//			dealDesc = "受理测试！";
//			action = new AcceptAction();
//			baseId = "000000000006975";
//			userName = "liuchuanzu";
//			action.doAction(baseSchema, baseId, userName, dealDesc, null, null, null, null);
			
			//阶段处理
//			dealDesc = "阶段处理测试！";
//			action = new NoticeAction();
//			baseId = "000000000006975";
//			userName = "liuchuanzu";
//			action.doAction(baseSchema, baseId, userName, dealDesc, null, null, null, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
