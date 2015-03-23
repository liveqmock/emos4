package com.ultrapower.eoms.workflow.api;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.workflow.api.action.AcceptAction;
import com.ultrapower.eoms.workflow.api.action.ActiveAction;
import com.ultrapower.eoms.workflow.api.action.AppendAssignAction;
import com.ultrapower.eoms.workflow.api.action.AssignAction;
import com.ultrapower.eoms.workflow.api.action.AssistAuditAction;
import com.ultrapower.eoms.workflow.api.action.AssistAuditNoPassAction;
import com.ultrapower.eoms.workflow.api.action.AssistAuditPassAction;
import com.ultrapower.eoms.workflow.api.action.AuditAction;
import com.ultrapower.eoms.workflow.api.action.AuditNoPassAction;
import com.ultrapower.eoms.workflow.api.action.AuditPassAction;
import com.ultrapower.eoms.workflow.api.action.CancelAction;
import com.ultrapower.eoms.workflow.api.action.ChaseAction;
import com.ultrapower.eoms.workflow.api.action.CloseAction;
import com.ultrapower.eoms.workflow.api.action.ConfirmAction;
import com.ultrapower.eoms.workflow.api.action.FinishAction;
import com.ultrapower.eoms.workflow.api.action.HastenAction;
import com.ultrapower.eoms.workflow.api.action.MakeCopyAction;
import com.ultrapower.eoms.workflow.api.action.NextAction;
import com.ultrapower.eoms.workflow.api.action.NoticeAction;
import com.ultrapower.eoms.workflow.api.action.ReAssignAction;
import com.ultrapower.eoms.workflow.api.action.ReAuditAction;
import com.ultrapower.eoms.workflow.api.action.RejectAction;
import com.ultrapower.eoms.workflow.api.action.NewAction;
import com.ultrapower.eoms.workflow.api.action.SaveAction;
import com.ultrapower.eoms.workflow.api.action.SendBackAction;
import com.ultrapower.eoms.workflow.api.action.SuggestAction;
import com.ultrapower.eoms.workflow.api.action.SuspendAction;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.workflow.api.action.BaseAction;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;


public class FreeActionTest {

	public static void main(String[] args) {
		//通用工单
		List<BaseFieldInfo> tthFields = new ArrayList<BaseFieldInfo>();
		tthFields.add(new BaseFieldInfo("BaseSummary","700000011","通用工单 888888 接口测试" + TimeUtils.getCurrentDate()));
		//连审带派
//		new BaseFieldInfo("PreAssign", "", "U#:yeyuan#:ASSIGN#:2#:0#:0#:0#:#:#:#:#;U#:yangjian#:ASSIGN#:2#:0#:0#:0#:#:#:#:#;", true);
		List<DealObject> dealObj = new ArrayList<DealObject>();
		String baseSchema = "WF4:App_Base_bak";
		String baseId = null;
		String dealDesc = null;
		String userName = "Demo";
		String actionText = "";
		List<BaseAttachmentInfo> attachs = new ArrayList<BaseAttachmentInfo>();
		BaseAction action = null;
		try {
			//新建
//			dealDesc = "通用工单 新建工单测试！";
//			action = new NewAction();
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//保存
//			dealDesc = "保存测试！";
//			action = new SaveAction();
//			baseId = "000000000000110";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//派发
//			dealDesc = "派发测试！";
//			action = new AssignAction();
//			baseId = "000000000000175";
//			userName = "wangwumei";
//			attachs.add(new BaseAttachmentInfo("C:\\ludashi.txt"));
//			dealObj.add(new DealObject("liuchuanzu", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//补派
//			dealDesc = "派发测试！";
//			action = new AppendAssignAction();
//			baseId = "000000000000171";
//			userName = "wangwumei";
//			attachs.add(new BaseAttachmentInfo("C:\\ludashi.txt"));
//			dealObj.add(new DealObject("liuchuanzu", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//转派
//			dealDesc = "转派测试！";
//			action = new ReAssignAction();
//			baseId = "000000000000143";
//			userName = "huangwei";
//			attachs.add(new BaseAttachmentInfo("C:\\setup.log"));
//			dealObj.add(new DealObject("admin", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//催办
//			dealDesc = "催办测试！";
//			action = new HastenAction();
//			baseId = "000000000000120";
//			dealObj.add(new DealObject("wangwumei", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
//			
//			//阶段建议
//			dealDesc = "阶段建议！";
//			action = new SuggestAction();
//			baseId = "000000000000120";
//			dealObj.add(new DealObject("wangwumei", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//受理
//			dealDesc = "受理测试！";
//			action = new AcceptAction();
//			baseId = "000000000000129";
//			userName = "liuchuanzu";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//阶段处理
//			dealDesc = "阶段处理测试！";
//			action = new NoticeAction();
//			baseId = "000000000000129";
//			userName = "liuchuanzu";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//超送
//			dealDesc = "超送测试！";
//			action = new MakeCopyAction();
//			baseId = "000000000000112";
//			userName = "wangwumei";
//			dealObj.clear();
//			dealObj.add(new DealObject("wangwumei", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//确认
//			dealDesc = "确认测试！";
//			action = new ConfirmAction();
//			baseId = "000000000000112";
//			userName = "liuchuanzu";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//完成
//			dealDesc = "完成测试！";
//			action = new FinishAction();
//			baseId = "000000000000158";
//			userName = "liuchuanzu";
//			attachs.add(new BaseAttachmentInfo("C:\\setup.log"));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//归档
//			dealDesc = "归档测试！";
//			action = new CloseAction();
//			baseId = "000000000000157";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//提交审批
//			dealDesc = "提交审批测试！";
//			action = new AuditAction();
//			baseId = "000000000000158";
//			userName = "liuchuanzu";
//			dealObj.add(new DealObject("admin", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//审批通过
//			dealDesc = "审批通过测试！";
//			action = new AuditPassAction();
//			baseId = "000000000000158";
//			userName = "admin";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//审批不通过
//			dealDesc = "审批不通过测试！";
//			action = new AuditNoPassAction();
//			baseId = "000000000000158";
//			userName = "admin";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//转审
//			dealDesc = "转审测试！";
//			action = new ReAuditAction();
//			baseId = "000000000000158";
//			userName = "huangwei";
//			dealObj.add(new DealObject("wangwumei", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//组织会审
//			dealDesc = "组织会审测试！";
//			action = new AssistAuditAction();
//			baseId = "000000000000158";
//			userName = "admin";
//			dealObj.add(new DealObject("huangwei", "", ""));
//			dealObj.add(new DealObject("wangwumei", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//会审通过
//			dealDesc = "会审通过测试！";
//			action = new AssistAuditPassAction();
//			baseId = "000000000000158";
//			userName = "wangwumei";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//会审不通过
//			dealDesc = "会审不通过测试！";
//			action = new AssistAuditNoPassAction();
//			baseId = "000000000000158";
//			userName = "huangwei";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//驳回
//			dealDesc = "驳回测试！";
//			action = new RejectAction();
//			baseId = "000000000000111";
//			userName = "huangwei";
//			dealObj.add(new DealObject("Demo", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//作废
//			dealDesc = "作废测试！";
//			action = new CancelAction();
//			baseId = "000000000000158";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, null, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//追回
//			dealDesc = "追回测试！";
//			action = new ChaseAction();
//			baseId = "000000000000158";
//			dealObj.add(new DealObject("wangwumei", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//挂起
//			dealDesc = "挂起测试！";
//			action = new SuspendAction();
//			baseId = "000000000000158";
//			userName = "liuchuanzu";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//激活
//			dealDesc = "激活测试！";
//			action = new ActiveAction();
//			baseId = "000000000000158";
//			userName = "liuchuanzu";
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			//退回
//			dealDesc = "退回测试！";
//			action = new SendBackAction();
//			baseId = "000000000000158";
//			userName = "wangwumei";
//			dealObj.add(new DealObject("liuchuanzu", "", ""));
//			if (action.doAction(baseSchema, baseId, userName, dealDesc, actionText, dealObj, tthFields, attachs)) {
//				System.out.println(action.getBaseId());;
//			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
