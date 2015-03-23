package com.ultrapower.eoms.ultrabpp.utils;

public class AssignStrModel
{
	/**
	 * 处理人类型，U（用户），G（组），R（角色细分）。默认为U（用户）
	 */
	public String actorType = "U";
	/**
	 * 登录名，组id，角色细分id
	 */
	public String actorID = "";
	/**
	 * 动作类型，固定环节为NEXT，其他的为ASSIGN或其他
	 */
	public String actionCode = "";
	/**
	 * 组处理模式，[数字型属性]，1（共享）,2（独占）,3（管理者管理）。默认为2（独占）
	 */
	public int dealType = 2;
	/**
	 * 受理时限，[数字型属性]，10位秒格式。默认为0
	 */
	public int acceptOutTime = 0;
	/**
	 * 派发时限，[数字型属性]，10位秒格式。默认为0
	 */
	public int assignOutTime = 0;
	/**
	 * 处理时限，[数字型属性]，10位秒格式。默认为0
	 */
	public int dealOutTime = 0;
	/**
	 * 驳回、追回等回退类动作需要指定的stepId
	 */
	public String rollbackStepID = "";
	/**
	 * 环节号，只有固定流程并行分支时才需指定，其余情况可以空着或为”dp0”
	 */
	public String targetPhaseNo = "";
	/**
	 * 子流程版本号，派发到多个固定子流程时需要指定具体的版本号，派发到单个固定子流程可以为空
	 */
	public String subflowVersionID = "";
	/**
	 * 派发说明
	 */
	public String desc = "";
	
	public String getAssignString()
	{
		String asStr = this.actorType+"#:"+this.actorID+"#:"+this.actionCode+"#:"+this.dealType+"#:"+this.acceptOutTime+"#:"+this.assignOutTime+"#:"+this.dealOutTime+"#:"+this.rollbackStepID+"#:"+this.targetPhaseNo+"#:"+this.subflowVersionID+"#:"+this.desc+"#;";
		return asStr;
	}
	
	public static AssignStrModel buildAssignStrModel(String assignStr)
	{
		if(assignStr == null || assignStr == "") return null;
		String str = assignStr.substring(0, assignStr.length() - 2);
		
		String[] strArr = str.split("#:");
		if(strArr.length != 11) return null;
		
		AssignStrModel model = new AssignStrModel();
		
		model.actorType = strArr[0];
		model.actorID = strArr[1];
		model.actionCode = strArr[2];
		model.dealType = Integer.valueOf(strArr[3]);
		model.acceptOutTime = Integer.valueOf(strArr[4]);
		model.assignOutTime = Integer.valueOf(strArr[5]);
		model.dealOutTime = Integer.valueOf(strArr[6]);
		model.rollbackStepID = strArr[7];
		model.targetPhaseNo = strArr[8];
		model.subflowVersionID = strArr[9];
		model.desc = strArr[10];
		
		return model;
	}
}
