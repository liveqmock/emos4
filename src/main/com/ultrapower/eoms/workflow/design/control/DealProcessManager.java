package com.ultrapower.eoms.workflow.design.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.workflow.design.model.DealProcess;
import com.ultrapower.eoms.workflow.design.model.DealProcessLog;
import com.ultrapower.eoms.workflow.design.model.DrawLineModel;
import com.ultrapower.eoms.workflow.design.model.ProcessInfo;
import com.ultrapower.eoms.workflow.design.model.ProcessInfoView;
import com.ultrapower.eoms.workflow.flowmap.control.BaseOwnFieldInfoManage;
import com.ultrapower.eoms.workflow.flowmap.control.FieldModifyInfoViewManager;
import com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel;
import com.ultrapower.eoms.workflow.flowmap.model.ModifyFieldView;
import com.ultrapower.workflow.role.model.RoleUser;
import com.ultrapower.workflow.role.service.IRoleService;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品 All rights reserved. 描述：
 * 
 * @author 范莹
 * @date 2010-4-21
 */
public class DealProcessManager
{

	/**
	 * 封装环节信息
	 * 
	 * @param baseid
	 * @param baseschema
	 * @param type
	 * @return List
	 */
	public List buildProcessList(String baseid, String baseschema, String flowid, String type, String tplId, String entryId) throws Exception
	{

		List pInfoList = getProcessList(baseid, baseschema, flowid);
		List pLogInfoList = getProcessLogList(baseid, baseschema, flowid);
		List<BaseField> baseFieldList = (new BaseOwnFieldInfoManage()).getBppFieldList(baseschema, tplId, null);
		List<ProcessInfo> processList = new ArrayList<ProcessInfo>();

		//用于操作fieldmodify显示信息
		FieldModifyInfoViewManager fieldModifyInfoViewManager = new FieldModifyInfoViewManager();

		// 创建ProcessList，并将所有的ProcessInfo都放进去
		if (type.equals("free"))
		{
			ProcessInfo beginProcessInfo = getBeginProcessInfo(pInfoList);
			ProcessInfoView infoView = convert(beginProcessInfo, pLogInfoList);
			infoView.setProcessfields(fieldModifyInfoViewManager.drawBaseFieldInfo(baseid, baseschema, tplId));
			beginProcessInfo.getProcessinfoList().add(infoView);
			processList.add(beginProcessInfo);

			for (int i = 0; i < pInfoList.size(); i++)
			{
				ProcessInfo processInfo = (ProcessInfo) pInfoList.get(i);

				//封装环境信息显示
				infoView = convert(processInfo, pLogInfoList);
				infoView.setProcessfields(fieldModifyInfoViewManager.drawSubFieldInfoBpp(baseid, baseschema, processInfo.getProcessId(), processInfo.getTaskId(), tplId));
				processInfo.getProcessinfoList().add(infoView);

				boolean fatherProcessFlag = false;
				boolean brathProcessFlag = false;
				int indexProcess = 0;
				ProcessInfo processInfotemp = null;

				//复制品判断
				int processInfoIndex = this.checkProcessListHasProcessinfo(processList, processInfo);
				if (processInfoIndex != -1)
				{
					ProcessInfo processinfoSamePhaseNo = (ProcessInfo) processList.get(processInfoIndex);
					if (!processinfoSamePhaseNo.getFlagendduplicated().equals("1"))
					{
						processinfoSamePhaseNo.setPhaseNo(processInfo.getPhaseNo());
						processinfoSamePhaseNo.setStatus(processInfo.getStatus());
						processinfoSamePhaseNo.setHasFreeProcess(processInfo.isHasFreeProcess());
						processinfoSamePhaseNo.setTitle(processInfo.getTitle());
						processinfoSamePhaseNo.setStepid(processInfo.getStepid());
						processinfoSamePhaseNo.setForwardstepid(processInfo.getForwardstepid());
						processinfoSamePhaseNo.setPrephaseNo(processInfo.getPrephaseNo());
						processinfoSamePhaseNo.setFlagduplicated(processInfo.getFlagduplicated());
						processinfoSamePhaseNo.setFlagendduplicated(processInfo.getFlagendduplicated());
						processinfoSamePhaseNo.setFlagactive(processInfo.getFlagactive());
						processinfoSamePhaseNo.setProcessType(processInfo.getProcessType());
					}

					//环节信息复制品的显示封装
					processinfoSamePhaseNo.getProcessinfoList().add(infoView);
					continue;
				}

				for (int j = processList.size() - 1; j > -1; j--)
				{
					processInfotemp = (ProcessInfo) processList.get(j);
					if (processInfo.getPrephaseNo().equals(processInfotemp.getPrephaseNo()) && !processInfo.getPhaseNo().equals(processInfotemp.getPhaseNo()))
					{
						brathProcessFlag = true;
						indexProcess = j;
						break;
					}

				}

				for (int j = processList.size() - 1; !brathProcessFlag && j > -1; j--)
				{
					processInfotemp = (ProcessInfo) processList.get(j);
					if (processInfo.getPrephaseNo().equals(processInfotemp.getPhaseNo()))
					{
						fatherProcessFlag = true;
						indexProcess = j;
						break;
					}
				}

				//当前processInfo的上一环节的phaseNo与当前的processList的最后一个环节信息的phaseNo进行匹配,确定父子关系
				if (fatherProcessFlag)
				{

					//封装processList
					processList.add(indexProcess + 1, processInfo);

					continue;
				}
				//当前processInfo的PrephaseNo与当前的processList的最后一个环节信息的PrephaseNo进行匹配,确定兄弟关系
				if (brathProcessFlag)
				{
					processInfo.setBrerPhaseNo(processInfotemp.getPhaseNo());
					//封装processList
					//processList.add(processInfo);
					processList.add(indexProcess + 1, processInfo);

					//对兄弟节点进行排序操作 2010-11-11
					this.setBrotherOder(processList, indexProcess + 1, processInfo);

					continue;
				}
			}//遍历pInfoList结束，自由流程
		}
		else
		{
			String[] startAndEndDate = queryStartAndEndDate(baseid, baseschema, flowid, entryId);
			for (int i = 0; i < pInfoList.size(); i++)
			{
				ProcessInfo processInfo = (ProcessInfo) pInfoList.get(i);
				processInfo.setCreateDate(startAndEndDate[0]);
				processInfo.setFinishDate(startAndEndDate[1]);

				//封装环节信息显示
				ProcessInfoView infoView = convert(processInfo, pLogInfoList);
				infoView.setProcessfields(fieldModifyInfoViewManager.drawSubFieldInfoBpp(baseid, baseschema, processInfo.getProcessId(), processInfo.getTaskId(), tplId));
				processInfo.getProcessinfoList().add(infoView);
				processInfo.setFlowids(com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(processInfo.getFlowid()));
				String track = com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(processInfo.getTrack());
				if (track.length() > 0)
				{
					processInfo.setTracks(processInfo.getForwardstepid() + ":" + track);
				}
				else
				{
					processInfo.setTracks("");
				}
				//复制品判断
				int processInfoIndex = this.checkProcessListHasProcessinfo(processList, processInfo);
				//存在复制品的情况下，将当前处理记录与复制品记录对比后，更新到复制品对象中，最后将复制品保存。
				if (processInfoIndex != -1)
				{
					ProcessInfo processinfoSamePhaseNo = (ProcessInfo) processList.get(processInfoIndex);
					String forwardstepidStrings = processinfoSamePhaseNo.getForwardstepids();
					if (forwardstepidStrings == null)
						forwardstepidStrings = "";
					if (forwardstepidStrings.length() > 0)
						forwardstepidStrings += "," + processInfo.getForwardstepid();
					if (forwardstepidStrings.length() == 0)
						forwardstepidStrings = processInfo.getForwardstepid();
					processinfoSamePhaseNo.setForwardstepids(forwardstepidStrings);

					if (!processinfoSamePhaseNo.getFlagendduplicated().equals("1"))
					{
						processinfoSamePhaseNo.setPhaseNo(processInfo.getPhaseNo());
						processinfoSamePhaseNo.setStatus(processInfo.getStatus());
						processinfoSamePhaseNo.setHasFreeProcess(processInfo.isHasFreeProcess());
						processinfoSamePhaseNo.setTitle(processInfo.getTitle());
						processinfoSamePhaseNo.setStepid(processInfo.getStepid());
						processinfoSamePhaseNo.setForwardstepid(processInfo.getForwardstepid());
						processinfoSamePhaseNo.setPrephaseNo(processInfo.getPrephaseNo());
						processinfoSamePhaseNo.setFlagduplicated(processInfo.getFlagduplicated());
						processinfoSamePhaseNo.setFlagendduplicated(processInfo.getFlagendduplicated());
						processinfoSamePhaseNo.setFlagactive(processInfo.getFlagactive());
						processinfoSamePhaseNo.setProcessType(processInfo.getProcessType());
					}
					String sameflowidStrings = processinfoSamePhaseNo.getFlowids();
					if (sameflowidStrings == null)
						sameflowidStrings = "";
					if (!processInfo.getFlowid().equals("") && sameflowidStrings.length() > 0)
						sameflowidStrings += "," + processInfo.getFlowid();
					if (!processInfo.getFlowid().equals("") && sameflowidStrings.length() == 0)
						sameflowidStrings = processInfo.getFlowid();
					processinfoSamePhaseNo.setFlowids(sameflowidStrings);
					//环节信息复制品的显示封装
					processinfoSamePhaseNo.getProcessinfoList().add(infoView);
					//用于作为遍历后的标识，表示已经添加进processList过了

					//当两个环节之间存在多条路径，路径中包括多个分支时，将路径记录下来。
					String sameProcessinfoTracks = processinfoSamePhaseNo.getTracks();
					if (processInfo.getTracks().length() > 0)
					{
						if (sameProcessinfoTracks.length() > 0)
						{
							processinfoSamePhaseNo.setTracks(sameProcessinfoTracks + ";" + processInfo.getTracks());
						}
						else
						{
							processinfoSamePhaseNo.setTracks(processInfo.getTracks());
						}
					}

				}
				else
				{
					//不存在复制品
					processList.add(processInfo);
				}
			}//遍历pInfoList结束，固定流程
			//固定流程处理信息封装好之后，需要对固定环节有内部自由流程的操作信息进行封装
			for (ProcessInfo processInfo : processList)
			{
				String flowIDs = processInfo.getFlowids();
				if (flowIDs.length() > 0)
				{
					List<ProcessInfoView> infoViewList = processInfo.getProcessinfoList();
					for (ProcessInfoView infoView : infoViewList)
					{
						if (infoView.getFlowid().length() > 0)
						{
							this.bulitSubProcessList(baseid,processInfo, infoView);
						}
					}
				}
			}

		}
		return processList;
	}

	/**
	 * 在服务器端封装流程图连线路径
	 */
	public String drawProcessLine(String baseid, String baseschema, String flowid)
	{
		String lineString = "";
		List<DrawLineModel> nodeModelList = new ArrayList<DrawLineModel>();
		try
		{
			List pInfoList = getProcessList(baseid, baseschema, flowid);

			String beginStepID = null;
			for (int i = 0; i < pInfoList.size(); i++)
			{
				ProcessInfo processInfo = (ProcessInfo) pInfoList.get(i);
				if ("BEGIN".equals(processInfo.getForwardstepid()))
				{
					beginStepID = processInfo.getForwardstepid();
					DrawLineModel lineModel = new DrawLineModel();
					lineModel.setPath("BEGIN," + processInfo.getPhaseNo());
					lineModel.setEdProcessAction(processInfo.getEdProcessAction());
					List<DrawLineModel> nextLineStringList = getProcessLine(processInfo.getStepid(), pInfoList, lineModel, nodeModelList);
					if (nextLineStringList.size() == 0)
					{
						nodeModelList.add(lineModel);
					}
					lineModel.setNextModel(nextLineStringList);

				}
			}

			if (nodeModelList.size() > 0)
			{
				for (int i = 0; i < nodeModelList.size(); i++)
				{
					DrawLineModel lineModel = nodeModelList.get(i);
					if ("驳回".equals(lineModel.getEdProcessAction()))
					{
						String path = lineModel.getPath();
						path = path.substring(0, path.lastIndexOf(","));
						lineModel.setPath(path);
					}
					lineModel.setPath(this.drawValidLine(lineModel.getPath()));

					if (lineString.length() == 0)
					{
						lineString = lineModel.getPath();
					}
					else
					{
						lineString += "#" + lineModel.getPath();
					}
				}

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return lineString;
	}

	private String drawValidLine(String wholeLinePath)
	{
		String[] nodeArray = wholeLinePath.split(",");
		for (int i = 0; i < nodeArray.length; i++)
		{
			String node = nodeArray[i];
			int sameNode = 0;
			for (int j = nodeArray.length - 1; j > 0; j--)
			{
				if (node.equals(nodeArray[j]) && i < j)
				{
					sameNode = j;
					break;
				}
			}
			if (sameNode > i)
			{
				for (int index = i + 1; index < sameNode + 1; index++)
				{
					nodeArray[index] = "";
				}
			}
		}
		String validLine = "";
		for (int i = 0; i < nodeArray.length; i++)
		{
			String node = nodeArray[i];
			if (node.length() > 0)
			{
				if (validLine.length() == 0)
				{
					validLine = node;
				}
				else
				{
					validLine += "," + node;
				}
			}
		}
		return validLine;
	}

	public List<DrawLineModel> getProcessLine(String stepID, List pInfoList, DrawLineModel parentModel, List<DrawLineModel> nodeModelList)
	{
		List<DrawLineModel> lineModelList = new ArrayList<DrawLineModel>();
		for (int i = 0; i < pInfoList.size(); i++)
		{
			ProcessInfo processInfo = (ProcessInfo) pInfoList.get(i);
			if (stepID.equals(processInfo.getForwardstepid()))
			{
				DrawLineModel lineModel = new DrawLineModel();
				lineModel.setPath(parentModel.getPath() + "," + processInfo.getPhaseNo());
				lineModel.setEdProcessAction(processInfo.getEdProcessAction());
				List<DrawLineModel> nextLineStringList = getProcessLine(processInfo.getStepid(), pInfoList, lineModel, nodeModelList);
				if (nextLineStringList.size() == 0)
				{
					nodeModelList.add(lineModel);
				}
				lineModel.setNextModel(nextLineStringList);
				lineModelList.add(lineModel);
			}
		}
		return lineModelList;
	}

	/**
	 * 查询流程实例的创建和结束时间
	 * 
	 * @param baseid
	 * @param baseschema
	 * @param flowid
	 * @return
	 */
	private String[] queryStartAndEndDate(String baseid, String baseschema, String flowid, String entryId)
	{
		/**
		 * 注释：2010-11-16 子流程显示结束时间 String tname =
		 * RemedySession.UtilInfor.getTableName(baseschema); String sqlString =
		 * "select C700000009,C700000006 from "+tname+" where C1='"+baseid+"'";
		 */
		//2010-11-16 子流程显示结束时间
		QueryAdapter qAdapter = new QueryAdapter();
		String sqlString = "";
		if (flowid.indexOf("_") > -1)
		{
			sqlString = "select t.createtime,t.closetime  from bs_t_wf_entry t where t.id='" + flowid.substring(flowid.indexOf("_") + 1) + "'";
		}
		else
		{
			//			//2011-02-13 优化sql语句，将存在in的地方用 or 代替
			//			String insqlString = "select distinct s.entryid  from bs_t_wf_dealprocess s where s.baseid='"+baseid+"' and baseschema='"+baseschema+"' and s.parentflowid is null";
			//			DataTable entryidDB = qAdapter.executeQuery(insqlString, null, 0);
			//			String orsqlString = "";
			//			if (entryidDB != null) {
			//				DataRow row = entryidDB.getDataRow(0);
			//				orsqlString += " and t.id = '"+row.getString(0)+"'";
			//			}
			sqlString = "select t.createtime,t.closetime  from bs_t_wf_entry t where t.id='" + entryId + "'";
		}

		DataTable dTable = qAdapter.executeQuery(sqlString, null, 0);
		String finishDate = "";
		String createDate = "";
		if (dTable != null)
		{
			DataRow dRow = dTable.getDataRow(0);
			finishDate = dRow.getString(1);
			createDate = dRow.getString(0);
			if (finishDate != null && finishDate.length() > 0)
			{
				finishDate = TimeUtils.formatIntToDateString(new Long(finishDate));
			}
			if (createDate != null && createDate.length() > 0)
			{
				createDate = TimeUtils.formatIntToDateString(new Long(createDate));
			}
		}
		return new String[] { createDate, finishDate };
	}

	private ProcessInfoView convert(ProcessInfo processInfo, List pInfoList)
	{
		ProcessInfoView infoView = new ProcessInfoView();
		setDealerInfo(processInfo);
		infoView.setProcessinfoStatus(processInfo.getStatus());
		infoView.setProcessinfoDealer(processInfo.getDealer());
		infoView.setProcessinfoDealDate(processInfo.getBgDate());
		infoView.setProcessinfoStDate(processInfo.getStDate());
		infoView.setProcessinfoFinishDate(processInfo.getEdDate());
		infoView.setProcessinfoDesc(processInfo.getDesc());
		infoView.setProcesslogList(getDealProcessLogList(pInfoList, processInfo));
		infoView.setFlowid(processInfo.getFlowid());
		return infoView;
	}

	/**
	 * 获取草稿环节的第一条processInfo
	 * 
	 * @param pInfoList
	 * @return
	 */
	private ProcessInfo getBeginProcessInfo(List pInfoList)
	{
		ProcessInfo process = null;
		if (CollectionUtils.isNotEmpty(pInfoList))
		{
			for (int i = 0; i < pInfoList.size(); i++)
			{
				ProcessInfo processInfo = (ProcessInfo) pInfoList.get(i);
				if (processInfo.getPrephaseNo().equals("BEGIN"))
				{
					process = processInfo;
					pInfoList.remove(i);
					break;
				}
			}
		}
		return process;
	}

	private List getDealProcessLogList(List pLogInfoList, ProcessInfo processInfo)
	{
		List dealProcessLogList = new ArrayList();
		for (int j = 0; j < pLogInfoList.size(); j++)
		{
			DealProcessLog logtemp = (DealProcessLog) pLogInfoList.get(j);
			if (logtemp.getProcessid().equals(processInfo.getProcessId()))
			{
				dealProcessLogList.add(logtemp);
			}
		}
		return dealProcessLogList;
	}

	private void setDealerInfo(ProcessInfo processInfo)
	{
		DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
		IRoleService roleManagerService = (IRoleService) WebApplicationManager.getBean("roleService");
		if (processInfo.getDealer() == null || processInfo.getDealer().equals(""))
		{
			if (processInfo.getAssigngrouptype().equals("GROUP"))
			{
				List<UserInfo> userList = depManagerService.getUserByDepID(processInfo.getAssigngroupid(), false);
				if (CollectionUtils.isNotEmpty(userList))
				{
					processInfo.setDealer("(");
					for (UserInfo userInfo : userList)
					{
						processInfo.setDealer(processInfo.getDealer() + userInfo.getFullname() + " ");
					}
					processInfo.setDealer(processInfo.getDealer() + ")");
				}
			}
			else if (processInfo.getAssigngrouptype().equals("ROLE"))
			{
				processInfo.setDealer("(");
				List<RoleUser> userList = roleManagerService.getRoleUserByCroleID(processInfo.getAssigngroupid());
				for (RoleUser userInfo : userList)
				{
					processInfo.setDealer(processInfo.getDealer() + userInfo.getFullName() + " ");
				}
				processInfo.setDealer(processInfo.getDealer() + ")");
			}
		}
	}

	public String buildProcessXML(List processList)
	{
		Element root = new Element("ProcessInfos");
		Document doc = new Document(root);
		for (int i = 0; i < processList.size(); i++)
		{
			//meta.addContent(new Element("metakey").setText(wfproperty.getCode()));
			Element processinfoElt = new Element("ProcessInfo");
			ProcessInfo processInfo = (ProcessInfo) processList.get(i);
			processinfoElt.addContent(new Element("PhaseNo").setText(processInfo.getPhaseNo()));
			processinfoElt.addContent(new Element("ProcessId").setText(processInfo.getProcessId()));
			processinfoElt.addContent(new Element("HasFreeProcess").setText(processInfo.isHasFreeProcess() + ""));
			processinfoElt.addContent(new Element("Title").setText(processInfo.getTitle()));
			processinfoElt.addContent(new Element("Content").setText(processInfo.getContent()));
			processinfoElt.addContent(new Element("Status").setText(processInfo.getStatus()));
			processinfoElt.addContent(new Element("PrephaseNo").setText(processInfo.getPrephaseNo()));
			processinfoElt.addContent(new Element("BrerPhaseNo").setText(processInfo.getBrerPhaseNo()));
			processinfoElt.addContent(new Element("Flagduplicated").setText(processInfo.getFlagduplicated()));
			processinfoElt.addContent(new Element("Flagendduplicated").setText(processInfo.getFlagendduplicated()));
			processinfoElt.addContent(new Element("Flagpredefined").setText(processInfo.getFlagpredefined()));
			processinfoElt.addContent(new Element("Stepid").setText(processInfo.getStepid()));
			processinfoElt.addContent(new Element("Forwardstepid").setText(processInfo.getForwardstepid()));
			processinfoElt.addContent(new Element("Forwardstepids").setText(processInfo.getForwardstepids()));
			processinfoElt.addContent(new Element("Tracks").setText(processInfo.getTracks()));
			processinfoElt.addContent(new Element("Flowid").setText(processInfo.getFlowid()));
			processinfoElt.addContent(new Element("Flowids").setText(processInfo.getFlowids()));
			processinfoElt.addContent(new Element("Dealer").setText(processInfo.getDealer()));
			processinfoElt.addContent(new Element("Desc").setText(processInfo.getDesc()));
			processinfoElt.addContent(new Element("StDate").setText(processInfo.getStDate()));
			processinfoElt.addContent(new Element("BgDate").setText(processInfo.getBgDate()));
			processinfoElt.addContent(new Element("EdDate").setText(processInfo.getEdDate()));
			processinfoElt.addContent(new Element("Flagactive").setText(processInfo.getFlagactive()));
			processinfoElt.addContent(new Element("ProcessType").setText(processInfo.getProcessType()));
			processinfoElt.addContent(new Element("FinishDate").setText(processInfo.getFinishDate()));
			processinfoElt.addContent(new Element("CreateDate").setText(processInfo.getCreateDate()));

			Element infoviewsElt = new Element("InfoViews");
			List processinfoList = processInfo.getProcessinfoList();
			for (int j = 0; j < processinfoList.size(); j++)
			{
				ProcessInfoView infoView = (ProcessInfoView) processinfoList.get(j);
				Element infoviewElt = new Element("InfoView");
				infoviewElt.addContent(new Element("ProcessinfoStatus").setText(infoView.getProcessinfoStatus()));
				infoviewElt.addContent(new Element("ProcessinfoDesc").setText(infoView.getProcessinfoDesc()));
				infoviewElt.addContent(new Element("ProcessinfoDealer").setText(infoView.getProcessinfoDealer()));
				infoviewElt.addContent(new Element("ProcessinfoPreDealer").setText(infoView.getProcessinfoPreDealer()));
				infoviewElt.addContent(new Element("ProcessinfoStDate").setText(infoView.getProcessinfoStDate()));
				infoviewElt.addContent(new Element("ProcessinfoDealDate").setText(infoView.getProcessinfoDealDate()));
				infoviewElt.addContent(new Element("ProcessinfoFinishDate").setText(infoView.getProcessinfoFinishDate()));

				List processlogList = infoView.getProcesslogList();
				Element processLogsElt = new Element("ProcessLogs");
				for (int k = 0; processlogList != null && k < processlogList.size(); k++)
				{
					DealProcessLog processLog = (DealProcessLog) processlogList.get(k);
					Element processLogElt = new Element("ProcessLog");
					processLogElt.addContent(new Element("ActionName").setText(processLog.getActionName()));
					processLogElt.addContent(new Element("LogTime").setText(processLog.getLogTime()));
					processLogElt.addContent(new Element("LogUser").setText(processLog.getLogUser()));
					processLogElt.addContent(new Element("Processid").setText(processLog.getProcessid()));
					processLogElt.addContent(new Element("Result").setText(processLog.getResult()));
					processLogsElt.addContent(processLogElt);
				}
				infoviewElt.addContent(processLogsElt);

				List processfields = infoView.getProcessfields();
				Element processfieldsElt = new Element("Fields");
				for (int k = 0; processfields != null && k < processfields.size(); k++)
				{
					ModifyFieldView modifyFieldView = (ModifyFieldView) processfields.get(k);
					Element filedElt = new Element("Field");
					filedElt.addContent(new Element("Name").setText(modifyFieldView.getName()));
					filedElt.addContent(new Element("Value").setText(modifyFieldView.getValue()));
					filedElt.addContent(new Element("PrintOneLine").setText(modifyFieldView.getPrintOneLine()));
					processfieldsElt.addContent(filedElt);
				}
				infoviewElt.addContent(processfieldsElt);
				
				String[] logArray = infoView.getSubProcessLog();
				Element subFlowLogsElt = new Element("SubFlowLog");
				for (int k = 0;logArray!=null && k < logArray.length; k++)
				{
					Element logElt = new Element("Flowinfo");
					subFlowLogsElt.addContent(logElt.setText(logArray[k]));
				}
				infoviewElt.addContent(subFlowLogsElt);

				infoviewsElt.addContent(infoviewElt);
			}
			processinfoElt.addContent(infoviewsElt);

			root.addContent(processinfoElt);
		}
		Format format = Format.getPrettyFormat();// 
		XMLOutputter outp = new XMLOutputter(format);

		return outp.outputString(doc);
	}

	/**
	 * 获取环节信息列表
	 * 
	 * @param baseid
	 * @param baseschema
	 * @return
	 * @throws Exception
	 */
	public List<ProcessInfo> getProcessList(String baseid, String baseschema, String parentflowid) throws Exception
	{
		// 执行查询语句
		List<ProcessInfo> pInfoList = new ArrayList<ProcessInfo>();
		QueryAdapter con = new QueryAdapter();
		String flowidsql = "";
		if (parentflowid.length() == 0)
		{
			flowidsql = " parentflowid is null";
		}
		else
		{
			String[] flowidAndEntryid = parentflowid.split("_");
			flowidsql = " parentflowid='" + flowidAndEntryid[0] + "' and entryid='" + flowidAndEntryid[1] + "'";
		}
		String sql = "select * from bs_t_wf_dealprocess t where baseid='" + baseid + "' and baseschema = '" + baseschema + "' and " + flowidsql + " order by stdate";
		DataTable rs = con.executeQuery(sql, null, 0);
		for (int i = 0; i < rs.length(); i++)
		{
			DataRow dRow = rs.getDataRow(i);
			String phaseNo = dRow.getString("phaseno");
			String taskId = dRow.getString("taskid");
			String stepid = dRow.getString("stepid");
			String forwardstepid = dRow.getString("forwardstepid");
			String status = dRow.getString("processstatus");
			String date = TimeUtils.formatIntToDateString(new Long(dRow.getString("stdate")));
			String action = dRow.getString("stprocessaction");
			String assignee = dRow.getString("assignee");
			String assigngroup = dRow.getString("assigngroup");
			String assigngroupID = dRow.getString("assigngroupid");
			String assigngroupType = dRow.getString("actortype");
			String prephaseNo = dRow.getString("prephaseno");
			String flagduplicated = dRow.getString("flagduplicated");
			String flagendduplicated = dRow.getString("flagendduplicated");
			String processId = dRow.getString("processid");
			String flagpredefined = dRow.getString("flagpredefined");
			String desc = dRow.getString("dpdesc");
			String dealer = dRow.getString("dealer");
			String flowid = dRow.getString("flowid");
			Long stdate = new Long(dRow.getString("stdate"));
			Long bgdate = new Long(dRow.getString("bgdate"));
			Long eddate = new Long(dRow.getString("eddate"));
			String flagactive = dRow.getString("flagactive");
			String processType = dRow.getString("processtype");
			String track = dRow.getString("track");
			String edProcessAction = dRow.getString("edprocessaction");
			ProcessInfo process = new ProcessInfo();
			process.setProcessId(processId);
			process.setPhaseNo(phaseNo);
			process.setStatus(status);
			process.setHasFreeProcess(false);

			//修改处理人显示信息 2010-11-12
			String titledealer = (assignee.length() > 0 ? assignee : assigngroup);
			if (dealer.length() > 0)
				titledealer = dealer;

			process.setTitle(status + "/" + titledealer + "##" + date);
			process.setStepid(stepid);
			process.setTaskId(taskId);
			process.setForwardstepid(forwardstepid);
			process.setForwardstepids(forwardstepid);
			process.setTrack(track);
			process.setPrephaseNo(prephaseNo);
			process.setFlagduplicated(flagduplicated);
			process.setFlagendduplicated(flagendduplicated);
			process.setFlagpredefined(flagpredefined);

			process.setFlowid(flowid);
			process.setDesc(desc);
			process.setDealer(dealer);
			process.setAssigngroupid(assigngroupID);
			process.setAssigngrouptype(assigngroupType);
			process.setStDate(TimeUtils.formatIntToDateString(stdate));
			process.setBgDate(TimeUtils.formatIntToDateString(bgdate));
			process.setEdDate(TimeUtils.formatIntToDateString(eddate));

			process.setFlagactive(flagactive);
			process.setProcessType(processType);
			process.setEdProcessAction(edProcessAction);

			pInfoList.add(process);
		}
		return pInfoList;
	}

	/**
	 * 
	 */
	public void bulitSubProcessList(String baseID, ProcessInfo processInfo, ProcessInfoView infoView) throws Exception
	{
		List<ProcessInfo> pInfoList = new ArrayList<ProcessInfo>();
		String logTitle = "";
		QueryAdapter con = new QueryAdapter();
		String sql = "select * from bs_t_wf_dealprocess t where baseid='" + baseID + "' and parentflowid='" + infoView.getFlowid() + "' and stprocessaction<>'新建' order by stdate";
		DataTable rs = con.executeQuery(sql, null, 0);
		String[] logArray = new String[rs.length()];
		for (int i = 0; i < rs.length(); i++)
		{
			DataRow row = rs.getDataRow(i);
			String dealer = row.getString("dealer");
			String assignee = row.getString("assignee");
			String assigngroup = row.getString("assigngroup");
			String stprocessaction = row.getString("stprocessaction");
			String assigner = row.getString("assigner");
			String flagactive = row.getString("flagactive");
			String bgdate = row.getString("bgdate");
			String stdate = row.getString("stdate");
			if (dealer == null || dealer.length() == 0)
			{
				dealer = assignee;
			}
			if (dealer == null || dealer.length() == 0)
			{
				dealer = assigngroup;
			}
			if ("1".equals(flagactive))
			{
				if(logTitle.length()==0){
					logTitle = dealer;
				}else{
					logTitle+=","+dealer;
				}
			}
			if(logTitle.length()==0&&i==(rs.length()+1))
			{
				logTitle=dealer + "##" + TimeUtils.formatIntToDateString(Long.parseLong(stdate));
			}
			if ("1".equals(flagactive) && bgdate.length() > 1)
			{
				dealer += "(已受理)";
			}
			if ("1".equals(flagactive) && "0".equals(bgdate))
			{
				dealer += "(未受理)";
			}
			logArray[i] = TimeUtils.formatIntToDateString(Long.parseLong(stdate)) + " " + assigner + " " + stprocessaction + " " + dealer;
		}
		String processtitle = processInfo.getTitle();
		if(logTitle.length()>0){
			processtitle = processtitle.substring(0, processtitle.indexOf("/")+1)+logTitle;
			processInfo.setTitle(processtitle);
		}
		infoView.setSubProcessLog(logArray);
	}

	/**
	 * ki 获取环节日志信息类别
	 * 
	 * @param baseid
	 * @param baseschema
	 * @return
	 */
	private List<DealProcessLog> getProcessLogList(String baseid, String baseschema, String parentflowid)
	{
		QueryAdapter con = new QueryAdapter();
		String flowidsql = "";
		if (parentflowid.length() == 0)
			flowidsql = "parentflowid is null";
		else
		{
			String[] flowidAndEntryid = parentflowid.split("_");
			flowidsql = " parentflowid='" + flowidAndEntryid[0] + "' ";
		}
		String sql = "select * from bs_t_wf_dealprocesslog where baseid='" + baseid + "' and baseschema = '" + baseschema + "' and " + flowidsql + " order by logtime";
		DataTable rslog = con.executeQuery(sql, null, 0);
		List<DealProcessLog> pLogInfoList = new ArrayList<DealProcessLog>();
		try
		{
			for (int i = 0; i < rslog.length(); i++)
			{
				DataRow dRow = rslog.getDataRow(i);
				String dateNum = dRow.getString("logtime");

				DealProcessLog log1 = new DealProcessLog(dRow.getString("actionname"), dRow.getString("loguser"), TimeUtils.formatIntToDateString(Integer.parseInt(dateNum)), dRow.getString("logdesc"));
				log1.setProcessid(dRow.getString("processid"));
				pLogInfoList.add(log1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return pLogInfoList;
	}

	//private List getDealProcessLogListByOrder(List dealprocesslogList){
	//	Collections.sort(dealprocesslogList,new Comparator<DealProcessLog>(){ 
	//          public int compare(DealProcessLog arg0, DealProcessLog arg1) { 
	//              return arg0.getLogTime().compareTo(arg1.getLogTime()); 
	//          } 
	//      }); 
	//	return dealprocesslogList;
	//}

	/**
	 * 检查列表中的环节信息存在情况
	 * 
	 * @param processList
	 * @param processInfo
	 * @return
	 */
	private int checkProcessListHasProcessinfo(List processList, ProcessInfo processInfo)
	{
		if (CollectionUtils.isNotEmpty(processList))
		{
			for (int i = 0; i < processList.size(); i++)
			{
				ProcessInfo processInfoTemp = (ProcessInfo) processList.get(i);
				if (processInfoTemp.getPhaseNo().equals(processInfo.getPhaseNo()))
				{
					return i;
				}
			}
		}
		return -1;
	}

	public int checkEndDuplicated(List pInfoList)
	{
		int num = 0;
		for (int i = 0; i < pInfoList.size(); i++)
		{
			ProcessInfo processInfo = (ProcessInfo) pInfoList.get(i);
			if (processInfo.getFlagendduplicated().equals("1"))
				num++;
		}
		return num;
	}

	public List<DealProcess> getDealProcessList(String baseschema, String baseid)
	{
		List<DealProcess> dpList = new ArrayList<DealProcess>();
		QueryAdapter query = new QueryAdapter();
		String sql = "select * from bs_t_wf_dealprocess t where baseid='" + baseid + "' and baseschema = '" + baseschema + "' order by stdate,processid";
		DataTable rs = query.executeQuery(sql, null, 0);
		for (int i = 0; i < rs.length(); i++)
		{
			DataRow dRow = rs.getDataRow(i);
			dpList.add(convert(dRow));
		}
		return dpList;
	}

	public DealProcess getDealProcessById(String processId)
	{
		List<DealProcess> dpList = new ArrayList<DealProcess>();
		QueryAdapter query = new QueryAdapter();
		String sql = "select * from bs_t_wf_dealprocess t where processid='" + processId + "'";
		DataTable rs = query.executeQuery(sql, null, 0);
		for (int i = 0; i < rs.length(); i++)
		{
			DataRow dRow = rs.getDataRow(i);
			dpList.add(convert(dRow));
		}
		if (CollectionUtils.isNotEmpty(dpList))
		{
			DealProcess dealProcess = dpList.get(0);
			sql = "select defName from bs_t_wf_currenttask t where id='" + dealProcess.getTaskId() + "'";
			rs = query.executeQuery(sql, null, 0);
			if (rs != null && rs.length() > 0)
			{
				DataRow dr = rs.getDataRow(0);
				dealProcess.setDefName(dr.getString("defName"));
			}
			return dealProcess;
		}
		return null;
	}

	private DealProcess convert(DataRow dRow)
	{
		DealProcess process = null;
		if (dRow != null)
		{
			process = new DealProcess();
			String processId = dRow.getString("processid");
			String processType = dRow.getString("processtype");
			String stepId = dRow.getString("stepId");
			String parentFlowId = dRow.getString("parentFlowId");
			String forwardStepId = dRow.getString("forwardStepId");
			String phaseNo = dRow.getString("phaseno");
			String prephaseNo = dRow.getString("prephaseno");
			String status = dRow.getString("processstatus");
			String flagActive = dRow.getString("flagactive");
			String flagAssignType = dRow.getString("flagassigntype");
			String flagduplicated = dRow.getString("flagduplicated");
			String flagendduplicated = dRow.getString("flagendduplicated");
			String stAction = dRow.getString("stprocessaction");
			String edAction = dRow.getString("edprocessaction");
			String flowid = dRow.getString("flowid");
			String flagpredefined = dRow.getString("flagpredefined");
			String actorType = dRow.getString("actortype");
			String assignee = dRow.getString("assignee");
			String assigneeID = dRow.getString("assigneeid");
			String assignGroup = dRow.getString("assigngroup");
			String assigneeGroupID = dRow.getString("assigngroupid");
			String desc = dRow.getString("dpdesc");
			String dealer = dRow.getString("dealer");
			String dealerID = dRow.getString("dealerid");
			String baseid = dRow.getString("baseid");
			String roleName = dRow.getString("roleName");
			String baseschema = dRow.getString("baseschema");
			String taskId = dRow.getString("taskid");
			Long stdate = new Long(dRow.getString("stdate"));
			Long bgdate = new Long(dRow.getString("bgdate"));
			Long eddate = new Long(dRow.getString("eddate"));
			Long acceptOverTimeDate = new Long(dRow.getString("acceptOverTimeDate"));
			Long dealOverTimeDate = new Long(dRow.getString("dealOverTimeDate"));

			String processStatus = dRow.getString("processStatus");
			String flagTurnUp = dRow.getString("flagturnup");
			String flagRecall = dRow.getString("flagrecall");
			String flagAssign = dRow.getString("flagassign");
			String flagClose = dRow.getString("flagclose");
			String flagCancel = dRow.getString("flagcancel");
			String flagCopy = dRow.getString("flagcopy");
			String flagToAuditing = dRow.getString("flagtoauditing");
			String flagTransfer = dRow.getString("flagtransfer");
			String flagTurnDown = dRow.getString("flagturndown");

			process.setProcessId(processId);
			process.setTaskId(taskId);
			process.setProcessType(processType);
			process.setStepId(stepId);
			process.setParentFlowId(parentFlowId);
			process.setForwardStepId(forwardStepId);
			process.setPhaseNo(phaseNo);
			process.setPrePhaseNo(prephaseNo);
			process.setProcessStatus(status);
			process.setFlagActive(Long.parseLong(flagActive));
			process.setFlagDuplicated(Long.parseLong(flagduplicated));
			process.setFlagEndDuplicated(Long.parseLong(flagendduplicated));
			process.setStProcessAction(stAction);
			process.setEdProcessAction(edAction);
			process.setFlowId(flowid);
			process.setFlagPredefined(Long.parseLong(flagpredefined));
			process.setBaseId(baseid);
			process.setBaseSchema(baseschema);
			process.setActorType(actorType);
			process.setAssignee(assignee);
			process.setAssigneeId(assigneeID);
			process.setAssignGroup(assignGroup);
			process.setAssignGroupId(assigneeGroupID);
			process.setDealer(dealer);
			process.setDealerId(dealerID);
			process.setDpDesc(desc);
			process.setStDate(TimeUtils.formatIntToDateString(stdate));
			process.setBgDate(TimeUtils.formatIntToDateString(bgdate));
			process.setEdDate(TimeUtils.formatIntToDateString(eddate));
			process.setAcceptOverTimeDate(TimeUtils.formatIntToDateString(acceptOverTimeDate));
			process.setDealOverTimeDate(TimeUtils.formatIntToDateString(dealOverTimeDate));
			process.setRoleName(roleName);

			process.setFlagAssignType(flagAssignType);
			process.setProcessStatus(processStatus);
			process.setFlagTurnUp(NumberUtils.formatToLong(flagTurnUp));
			process.setFlagRecall(NumberUtils.formatToLong(flagRecall));
			process.setFlagAssign(NumberUtils.formatToLong(flagAssign));
			process.setFlagClose(NumberUtils.formatToLong(flagClose));
			process.setFlagCancel(NumberUtils.formatToLong(flagCancel));
			process.setFlagCopy(NumberUtils.formatToLong(flagCopy));
			process.setFlagToAuditing(NumberUtils.formatToLong(flagToAuditing));
			process.setFlagTransfer(NumberUtils.formatToLong(flagTransfer));
			process.setFlagTurnDown(NumberUtils.formatToLong(flagTurnDown));

			/*******************************************************************
			 * String tname =
			 * RemedySession.UtilInfor.getTableName("WF4:Config_BaseCategory");//Constants.arschemaName2Id.get("WF4:Config_BaseCategory");
			 * String sql = "select * from " + tname + " where c650000024='" +
			 * baseschema + "'"; QueryAdapter qAdapter = new QueryAdapter();
			 * DataTable dTable = qAdapter.executeQuery(sql, null, 0); DataRow
			 * row = dTable.length()>0?dTable.getDataRow(0):null; if (row !=
			 * null) { String[] stf = null; if (process.getFlagPredefined() ==
			 * 1) { String stForm =
			 * dRow.getString("C650000018");//各环节对应的form:WF4:SUB_AAA;WF4:SUB_BBB;
			 * if (StringUtils.isNotBlank(stForm)) { stf = stForm.split(";"); } }
			 * else { stf = freeSubForm; } if (!ArrayUtils.isEmpty(stf)) { for
			 * (int i = 0; i < stf.length; i++) { String subForm = stf[i]; tname =
			 * RemedySession.UtilInfor.getTableName(subForm);//Constants.arschemaName2Id.get(subForm);
			 * sql = "select * from " + tname + " where c700038041='" +
			 * processId + "'"; qAdapter = new QueryAdapter(); dTable =
			 * qAdapter.executeQuery(sql, null, 0); row =
			 * dTable.length()>0?dTable.getDataRow(0):null; if (row != null) {
			 * process.setSubForm(subForm); break; } } } }
			 ******************************************************************/
		}
		return process;
	}

	public String getDpLog(String baseschema, String baseid, DealProcess dp, List<DealProcess> dpList)
	{
		StringBuffer sb = new StringBuffer();
		if (dp != null)
		{
			String processid = dp.getProcessId();
			String beginProcessid = null;
			String flowId = dp.getFlowId();
			if (StringUtils.isNotBlank(flowId))
			{//如果派发了子流程，还需要查询子流程begin环节的dealprocesslog
				if (CollectionUtils.isNotEmpty(dpList))
				{
					for (int i = 0; i < dpList.size(); i++)
					{
						DealProcess tmpDp = dpList.get(i);
						String parentFlowId = tmpDp.getParentFlowId();
						String prePhaseNo = tmpDp.getPrePhaseNo();
						Long flagPredefined = tmpDp.getFlagPredefined();
						if (flowId.equals(parentFlowId) && "BEGIN".equals(prePhaseNo) && flagPredefined == 0)
						{
							beginProcessid = tmpDp.getProcessId();
							break;
						}
					}
				}
			}

			String sql = "select logtime as LogTime, loguser as LogUser, actionName as ActionName, logDesc from bs_t_wf_dealprocesslog where processid='" + processid + "' and baseid='" + baseid + "' and baseschema='" + baseschema + "' order by logtime desc";
			if (StringUtils.isNotBlank(beginProcessid))
			{
				sql = "select logtime as LogTime, loguser as LogUser, actionName as ActionName, logDesc from bs_t_wf_dealprocesslog where baseid='" + baseid + "' and baseschema='" + baseschema + "' and processid in ('" + processid + "','" + beginProcessid + "') order by logtime desc";
			}
			QueryAdapter qAdapter = new QueryAdapter();
			DataTable dt = qAdapter.executeQuery(sql, null, 0);
			if (dt != null)
			{
				for (int i = 0; i < dt.length(); i++)
				{
					DataRow row = dt.getDataRow(i);
					String long1 = row.getString("LogTime");
					String logTime = TimeUtils.formatIntToDateString(NumberUtils.formatToLong(long1));
					String logUser = row.getString("LogUser");
					String actionName = row.getString("ActionName");
					String logDesc = row.getString("logDesc");
					if (StringUtils.isNotBlank(logDesc))
					{
						sb.append(logTime + " " + logUser + " : " + actionName + " : " + logDesc + "； <br>");
					}
					else
					{
						sb.append(logTime + " " + logUser + " : " + actionName + "； <br>");
					}
				}
			}
		}
		return sb.toString();
	}

	//同级兄弟节点按照时间排序 2010-11-12
	public void setBrotherOder(List infolist, int index, ProcessInfo processInfo)
	{
		String prephaseNo = processInfo.getPrephaseNo();
		List brotherList = new ArrayList();
		int firstBrotherIndex = 0;
		for (int i = index; i >= 0; i--)
		{
			ProcessInfo processtemInfo = (ProcessInfo) infolist.get(i);
			if (!processtemInfo.getPrephaseNo().equals(prephaseNo))
				break;
			brotherList.add(processtemInfo);
			firstBrotherIndex = i;
		}

		if (brotherList.size() > 1)
		{
			Collections.sort(brotherList, new Comparator() {
				int ret = 0;

				public int compare(Object o1, Object o2)
				{
					/**
					 * 方法加载注释示例
					 * 
					 * @param note
					 *            参数及其意义
					 * @return String 返回值
					 * @exception IOException
					 *                异常类及抛出条件
					 * @see TemplateDoc
					 */
					ProcessInfo processInfo1 = (ProcessInfo) o1;
					ProcessInfo processInfo2 = (ProcessInfo) o2;

					Long stTimeLong1 = TimeUtils.formatDateStringToInt(processInfo1.getStDate());
					Long stTimeLong2 = TimeUtils.formatDateStringToInt(processInfo2.getStDate());

					ret = stTimeLong1.compareTo(stTimeLong2);
					return ret;
				}
			});
			for (int i = 0; i < brotherList.size(); i++)
			{
				ProcessInfo processtemInfo = (ProcessInfo) brotherList.get(i);
				if (i == 0)
					processtemInfo.setBrerPhaseNo("");
				else
				{
					ProcessInfo processlastInfo = (ProcessInfo) brotherList.get(i - 1);
					processtemInfo.setBrerPhaseNo(processlastInfo.getPhaseNo());
				}
			}

			for (int i = firstBrotherIndex, j = 0; i <= index; i++, j++)
			{
				infolist.set(i, brotherList.get(j));
			}
		}
	}

	public List<String[]> getProcessLogs(String baseID, String baseSchema)
	{
		List<String[]> processLogs = new ArrayList<String[]>();

		//String sql = "select logtime, loguser, actionname from bs_t_wf_dealprocesslog where baseid='" + baseID + "' and baseschema='"+baseSchema+"' order by logtime asc";
		StringBuffer sql = new StringBuffer();
		/**
		 * sql.append("select t.processid,
		 * t.assigner,d.actionname,t.assignee,t.assigngroup,t.stdate");
		 * sql.append(" from bs_t_wf_dealprocess t, "); sql.append(" (select
		 * actionname,stepid from bs_t_wf_dealprocess where baseid='"+baseID+"' )
		 * d"); sql.append(" where t.forwardstepid = d.stepid and t.flowid is
		 * null and t.baseid='"+baseID+"' order by stdate ");
		 */
		sql.append("select t.dealer,t.assignee,t.assigngroup,t.stprocessaction,t.assigner,t.flagactive,t.bgdate,t.stdate  from bs_t_wf_dealprocess t ");
		sql.append("where t.baseid='" + baseID + "' and t.stprocessaction<>'新建' and t.prephaseno<>'BEGIN' and t.flagactive<>'2' and t.flowid is null order by t.stdate");
		QueryAdapter qAdapter = new QueryAdapter();
		DataTable dt = qAdapter.executeQuery(sql.toString(), null, 0);
		if (dt != null)
		{
			for (int i = 0; i < dt.length(); i++)
			{
				DataRow row = dt.getDataRow(i);
				String dealer = row.getString("dealer");
				String assignee = row.getString("assignee");
				String assigngroup = row.getString("assigngroup");
				String stprocessaction = row.getString("stprocessaction");
				String assigner = row.getString("assigner");
				String flagactive = row.getString("flagactive");
				String bgdate = row.getString("bgdate");
				String stdate = row.getString("stdate");
				String[] log = new String[4];
				log[0] = stdate;
				log[1] = assigner;
				log[2] = stprocessaction;
				if (dealer == null || dealer.length() == 0)
				{
					dealer = assignee;
				}
				if (dealer == null || dealer.length() == 0)
				{
					dealer = assigngroup;
				}
				if ("1".equals(flagactive) && bgdate.length() > 1)
				{
					dealer += "(已受理)";
				}
				if ("1".equals(flagactive) && "0".equals(bgdate))
				{
					dealer += "(未受理)";
				}
				log[3] = dealer;
				processLogs.add(log);
			}
		}

		return processLogs;
	}
}
