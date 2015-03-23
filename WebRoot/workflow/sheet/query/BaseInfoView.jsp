<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.control.BaseManage"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.model.BaseModel"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.control.BaseOwnFieldInfoManage"%>
<%@ page import="com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel"%>
<%@ page import="com.ultrapower.eoms.workflow.design.control.DealProcessManager"%>
<%@ page import="com.ultrapower.eoms.workflow.design.model.DealProcess"%>
<%@ page import="com.ultrapower.eoms.common.core.util.TimeUtils"%>
<%@ page import="com.ultrapower.eoms.common.core.util.NumberUtils"%>
<%@ page import="com.ultrapower.eoms.workflow.util.PreviewUtils"%>
<%@page import="com.ultrapower.eoms.ultrasm.model.UserInfo"%>
<%@page import="com.ultrapower.eoms.ultrasm.service.DepManagerService"%>
<%@page import="com.ultrapower.workflow.role.service.IRoleService"%>
<%@page import="com.ultrapower.eoms.common.core.util.WebApplicationManager"%>
<%@page import="com.ultrapower.workflow.role.model.RoleUser"%>

<html>
	<head>
		<%@ include file="/common/core/taglibs.jsp"%>
		<title><eoms:lable name="wf_baseinfoview_title"/></title>
		<link rel="stylesheet" type="text/css" href="${ctx}/common/style/blue/css/baseinfoview.css" />
		<script src="${ctx}/common/javascript/util.js"></script>
		<script src="${ctx}/common/javascript/baseInfoView.js"></script>
<SCRIPT language=javascript>
	
	var list_close = "${ctx}/common/style/blue/images/workflow/List_close.gif";
	var list_open = "${ctx}/common/style/blue/images/workflow/List_open.gif";
	function collonmouseover(id)
	{
		document.all[id].border = "1";
	}
	function collonmouseout(id)
	{
		document.all[id].border = "0";
	}
	var i = 0;
	function controlbodyHeight()
	{
		try
		{
			parent.ControlDealListH(parseInt(parseInt(document.body.firstChild.lastChild.offsetTop) + parseInt(document.body.firstChild.lastChild.offsetHeight)) + 20);
		}
		catch(e) 
		{
			return;
		}
	}
	function expandcoll(id)
	{
		if (document.getElementById(id).style.display == "none" )
		{
			document.getElementById(id).style.display = "block";
			document.getElementById(id + '_img').src = list_open;
		}
		else
		{
			document.getElementById(id).style.display = "none";
			document.getElementById(id+"_img").src = list_close;
		}
		controlbodyHeight();
	}
	
	function opAll(BtnObj)
	{
		BtnLabelText = BtnObj.innerHTML;
		if (BtnLabelText == "<eoms:lable name='wf_baseinfoview_msg'/>")
		{
			expandAll();
			BtnObj.innerHTML = "<eoms:lable name='wf_baseinfoview_msg1'/>";
		}
		else
		{
			collapseAll();
			BtnObj.innerHTML = "<eoms:lable name='wf_baseinfoview_msg'/>";
		}
		controlbodyHeight();
	}
	function expandAll()
	{
		var divs = document.all.tags("div");
		for (var i = 0; i < divs.length; i++ )
		{
			var idvalue = divs[i].id;
			var idvalue_indexOf_int = idvalue.indexOf('DealProcess_infor_');
			if (idvalue_indexOf_int == 0)
			{
				document.all[idvalue].style.display = "block";
				document.all[idvalue+"_img"].src = list_open;
			}
		}
	}
	
	function collapseAll()
	{
		var divs = document.all.tags("div");
		for (var i = 0; i < divs.length; i++ )
		{
			var idvalue = divs[i].id;
			var idvalue_indexOf_int = idvalue.indexOf('DealProcess_infor_');
			if (idvalue_indexOf_int == 0)
			{
				document.all[idvalue].style.display = "none";
				document.all[idvalue+"_img"].src = list_close;
			}
		}
	}
	function Open_NewWindow()
	{
		window.open(window.location);
	}
	function getatt(attID,attName)
	{
		var attachurl = "BaseAttachManageDownload.jsp?attID="+attID+"&AttachName="+attName;
		attachurl = encodeURI(attachurl);
		window.open(attachurl,"","height=0, width=0, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
	} 			
  </SCRIPT>
	</head>
<%
		String baseschema = request.getParameter("baseschema");
		String baseid = request.getParameter("baseid");
		String processid = request.getParameter("processid");
		String strFlagNewWindow = request.getParameter("flagnewwindow");
		String tplId = request.getParameter("tplid");
		String strBodyStyle = "style=\"margin:0px;padding:0px;WORD-BREAK:break-all;overflow:auto;\"";
		String str_title_height = "";
		if(StringUtils.isBlank(strFlagNewWindow) || strFlagNewWindow.toLowerCase().equals("null")) {
			strFlagNewWindow = "0";
			strBodyStyle = "style=\"margin:0px;padding:0px;WORD-BREAK:break-all;overflow:auto;\"";
		} else {
			strFlagNewWindow = "1";
			str_title_height 	= "height:25px";
		}
		DepManagerService depManagerService = (DepManagerService)WebApplicationManager.getBean("depManagerService");
		IRoleService roleManagerService = (IRoleService)WebApplicationManager.getBean("roleService");
		
		List<BaseOwnFieldInfoModel> m_BaseOwnFieldInfoList= (new BaseOwnFieldInfoManage()).getList(baseschema, tplId, null);
		//查询工单
      	BaseModel m_BaseModel = (new BaseManage()).getOneModel(baseid, baseschema, m_BaseOwnFieldInfoList, "");
		String m_BaseSchema 		= m_BaseModel.getOneBaseFieldBean("BaseSchema").getM_BaseFieldValue();
		String m_BaseName 			= m_BaseModel.getOneBaseFieldBean("BaseName").getM_BaseFieldValue();
		String m_BaseSN 			= m_BaseModel.getOneBaseFieldBean("BaseSN").getM_BaseFieldValue();
		String m_BaseStatus			= m_BaseModel.getOneBaseFieldBean("BaseStatus").getM_BaseFieldValue();
		String m_BaseCreatorFullName		= m_BaseModel.getOneBaseFieldBean("BaseCreatorFullName").getM_BaseFieldValue();
		String m_BaseCreatorCorp			= m_BaseModel.getOneBaseFieldBean("BaseCreatorCorp").getM_BaseFieldValue();
		String m_BaseCreatorDep				= m_BaseModel.getOneBaseFieldBean("BaseCreatorDep").getM_BaseFieldValue();
		String m_BaseCreatorConnectWay		= m_BaseModel.getOneBaseFieldBean("BaseCreatorConnectWay").getM_BaseFieldValue();
		String m_str_BaseCreateDate			= TimeUtils.formatIntToDateString(NumberUtils.formatToInt(m_BaseModel.getOneBaseFieldBean("BaseCreateDate").getM_BaseFieldValue()));
		String m_str_BaseCloseDate			= TimeUtils.formatIntToDateString(NumberUtils.formatToInt(m_BaseModel.getOneBaseFieldBean("BaseCloseDate").getM_BaseFieldValue()));
		int m_BaseWorkFlowFlag		= NumberUtils.formatToInt(m_BaseModel.getOneBaseFieldBean("BaseWorkFlowFlag").getM_BaseFieldValue());
		String m_BaseTplID = m_BaseModel.getOneBaseFieldBean("BaseTplID").getM_BaseFieldValue();
%>	
  <body <%=strBodyStyle%>>
  	<div class="content">
		<%
		if (strFlagNewWindow.equals("1")) {
		%>
			<table  border="0" cellpadding="0" cellspacing="0" width="100%" class="treetitlehead">
				<tr>
					<td align="center" class="treecharhead" style="padding-top:10px;" colspan="2"><%=m_BaseName %></td>
				</tr>
				<tr>
					<td style="width:50%;height:25px;font-size:12px;" align="left" >&nbsp;&nbsp;<eoms:lable name="wf_baseinfoview_serialnum"></eoms:lable>：<%=m_BaseSN%></td>
					<td style="width:50%;height:25px;font-size:12px;" align="right" ><eoms:lable name="wf_baseinfoview_status"></eoms:lable>：<%=m_BaseStatus%>&nbsp;&nbsp;</td>						
				</tr>			
			</table>	
			<table  border="0" cellpadding="0" cellspacing="0" width="100%" align="center" class="treechar">
				<tr>
					<td style="<%=str_title_height %>" align="center" colspan="6" class="treetitle"><eoms:lable name="wf_baseinfoview_baseinfo"/></td>
				</tr>	
			</table> 
			<TABLE cellSpacing="1" cellPadding="0" width="100%" align="center" border="0" class="tableline">
				<TBODY>
			        <TR>
			          <TD class=ColumntableTD_LookProcess width="12%"><eoms:lable name="wf_sheet_creator"></eoms:lable>：</TD>
			          <TD class=ColumntableTD width="13%">&nbsp;<%=m_BaseCreatorFullName %>&nbsp;</TD>
			          <TD class=ColumntableTD_LookProcess><eoms:lable name="wf_baseinfoview_creatorConnectWay"></eoms:lable>：</TD>
			          <TD class=ColumntableTD colspan="5" >&nbsp;<%=m_BaseCreatorConnectWay %>&nbsp;</TD>
			        </TR>
			        <TR>		        		          
			          <TD class=ColumntableTD_LookProcess width="12%"><eoms:lable name="wf_baseinfoview_creatorCorp"></eoms:lable>：</TD>
			          <TD class=ColumntableTD width="13%">&nbsp;<%=m_BaseCreatorCorp %>&nbsp;</TD>
			          <TD class=ColumntableTD_LookProcess width="12%" valign="middle"><eoms:lable name="wf_baseinfoview_creatorDep"></eoms:lable>：</TD>
			          <TD class=ColumntableTD width="13%">&nbsp;<%=m_BaseCreatorDep %>&nbsp;</TD>
			          <TD class=ColumntableTD_LookProcess width="12%"><eoms:lable name="wf_baseinfoview_createDate"></eoms:lable>：</TD>
			          <TD class=ColumntableTD width="13%">&nbsp;<%=m_str_BaseCreateDate %>&nbsp;</TD>
			          <TD class=ColumntableTD_LookProcess width="12%" valign="middle"><eoms:lable name="wf_baseinfoview_closeDate"></eoms:lable>：</TD>
			          <TD class=ColumntableTD width="13%">&nbsp;<%=m_str_BaseCloseDate %>&nbsp;</TD>
			        </TR>
			        <%
						//读取工单特有信息
						out.println(PreviewUtils.printMainHtml(m_BaseOwnFieldInfoList, m_BaseModel));
					%>
				</TBODY>
			</TABLE>		
		<%
		}
		%>	
			<table  border="0" cellpadding="0" cellspacing="0" width="100%" class="treetitle" style="border-top:1px solid #E5ECEE;border-right:0px;border-left:0px;border-bottom:1px solid #E5ECEE; ">	
				<tr style="<%=str_title_height %>;background-color:#f2f5fd;">
					<td align="left" class="treechar" style="height:33px;  color:#666; padding-left:15px; font-size:14px;"><eoms:lable name="wf_baseinfoview_traceinfo"></eoms:lable>：</td>
					<td align="right">
					<%
					if (strFlagNewWindow.equals("0")) {
					%>
						<span class="treespan" onclick="Open_NewWindow();"><eoms:lable name="wf_baseinfoview_title1"/></span>
					<%
					}
					%>
						<span class="treespan" style="margin-right:5px;" onclick="opAll(this);"><eoms:lable name="wf_baseinfoview_title2"/></span> 
					</td>
				</tr>
			</table>  
	  	<div id="div_0"></div>
				<%
		      	//查询Process
		      	List<DealProcess> m_ProcessModelList= (new DealProcessManager()).getDealProcessList(baseschema, baseid);
				List<DealProcess> m_MainProcessList = new ArrayList<DealProcess>();
				Map<String, List<DealProcess>> m_SubProcessMap = new HashMap<String, List<DealProcess>>();
				
				if(m_BaseTplID == null || m_BaseTplID.equals("") || m_BaseTplID.equals(m_BaseSchema))
				{
					m_MainProcessList = m_ProcessModelList;
				}
				else
				{
					for (int Process_row = 0; Process_row < m_ProcessModelList.size(); Process_row++)
					{
						DealProcess m_ProcessModel = m_ProcessModelList.get(Process_row);
						if(m_ProcessModel.getParentFlowId() == null || m_ProcessModel.getParentFlowId().equals(""))
						{
							m_MainProcessList.add(m_ProcessModel);
						}
						else
						{
							if(!m_SubProcessMap.containsKey(m_ProcessModel.getParentFlowId()))
							{
								List<DealProcess> subList = new ArrayList<DealProcess>();
								subList.add(m_ProcessModel);
								m_SubProcessMap.put(m_ProcessModel.getParentFlowId(), subList);
							}
							else
							{
								List<DealProcess> subList = m_SubProcessMap.get(m_ProcessModel.getParentFlowId());
								subList.add(m_ProcessModel);
							}
						}
					}
				}
				
				LinkedList<Object[]> stackList = new LinkedList<Object[]>();
				Object[] mainArray = new Object[]{m_MainProcessList, 0};
				stackList.add(mainArray);
				
				boolean flagFirst = true;
				
				while(true)
				{
					Object[] tmpArray = stackList.getLast();
					List<DealProcess> tmp_ProcessList = (List<DealProcess>)tmpArray[0];
					int currentIndex = (Integer)tmpArray[1];

					if(tmp_ProcessList.size() == currentIndex)
					{
						stackList.removeLast();

						%>
						
												<!-- END - 环节内容结束 -->
												</TD>
											</TR>
										</TBODY>
									</TABLE>
								</TD>
							</TR>
						<!-- END - 环节结束 -->	
						</TBODY>
			</TABLE>
			</div>
						<%
						if(stackList.size() == 0)
						{
							break;
						}
						continue;
					}
					
					DealProcess m_ProcessModel = tmp_ProcessList.get(currentIndex);
					String 	m_str_processid 	= m_ProcessModel.getProcessId();
					String 	m_str_processtype 	= m_ProcessModel.getProcessType();
					String	m_str_DealLoginName	= m_ProcessModel.getDealerId();
					String 	m_str_DealFullName	= m_ProcessModel.getDealer();
					String 	m_str_AssginName	= m_ProcessModel.getAssignee() + m_ProcessModel.getAssignGroup();
					String	m_str_AssignLoginName= m_ProcessModel.getAssigneeId();
					String	m_str_ActorType		= m_ProcessModel.getActorType();
					String 	m_str_DealerCorp 	= m_ProcessModel.getDealerCorp();
					String 	m_str_DealerDep 	= m_ProcessModel.getDealerDep();
					String 	m_str_PhaseNo 		= m_ProcessModel.getPhaseNo();
					String 	m_str_ProcessStatus	= m_ProcessModel.getProcessStatus();
					String	m_str_ProDesc		= m_ProcessModel.getDpDesc();
					String 	m_str_RoleName		= m_ProcessModel.getRoleName();
					String	m_long_StDate		= m_ProcessModel.getStDate();
					String	m_long_BgDate		= m_ProcessModel.getBgDate();
					String	m_long_EdDate		= m_ProcessModel.getEdDate();
					String taskId = m_ProcessModel.getTaskId();
					String parentFlowId = m_ProcessModel.getParentFlowId();
					String flowID = m_ProcessModel.getFlowId();
					long 	m_long_FlagActive 	= m_ProcessModel.getFlagActive();
					long 	m_long_FlagPredefined= m_ProcessModel.getFlagPredefined();
		//			long 	m_long_FlagBegin 	= m_ProcessModel.getFlagBegin();
					String	m_long_AcceptOverTimeDate = m_ProcessModel.getAcceptOverTimeDate();
					String	m_long_DealOverTimeDate = m_ProcessModel.getDealOverTimeDate();
					//
					String m_prePhaseNo = m_ProcessModel.getPrePhaseNo();
					
					int int_Pro_id = 0;
					
					if (m_long_FlagActive==4 || m_str_ProcessStatus.equals("已跳过"))
					{
						if(tmp_ProcessList.size() == currentIndex+1)
						{
							stackList.removeLast();
						}
						else
						{
							tmpArray[1] = currentIndex+1;
						}
						continue;
					}
					if(m_BaseTplID != null && !m_BaseTplID.equals("") && !m_BaseTplID.equals(m_BaseSchema) && m_ProcessModel.getFlagPredefined() == 0 && m_ProcessModel.getPrePhaseNo().equals("BEGIN"))
					{
						if(tmp_ProcessList.size() == currentIndex+1)
						{
							stackList.removeLast();
						}
						else
						{
							tmpArray[1] = currentIndex+1;
						}
						continue;
					}
					String str_Pro_infor_id = "DealProcess_infor_"+m_str_processid;
					String str_Pro_image_id = str_Pro_infor_id+"_img";
	
					if (StringUtils.isNotBlank(m_str_DealerDep))
					{
						m_str_DealFullName =  m_str_DealerDep + "." + m_str_DealFullName;
					}
					if (StringUtils.isNotBlank(m_str_DealerCorp))
					{
						m_str_DealFullName =  m_str_DealerCorp + "." + m_str_DealFullName;
					}
					boolean fixSub = false;
					if(m_BaseTplID != null && !m_BaseTplID.equals("") && !m_BaseTplID.equals(m_BaseSchema) && parentFlowId != null && !parentFlowId.equals(""))
					{
						fixSub = true;
						int_Pro_id = currentIndex;
					}
					else
					{
						int_Pro_id = currentIndex + 1;
					}
				%>
				<div id="info_<%=m_str_processid%>"  style="">
			<TABLE cellSpacing="0" cellPadding="0" id="table_<%=m_str_processid%>" width="100%" align="center" border="0" style="margin-top:5px;">
				<TBODY>
					<TR style="HEIGHT: 1px"><TD></TD></TR>
					<!-- BEGIN - 环节开始 -->
					<TR>
						<TD>
							<TABLE cellSpacing="0" cellPadding="0" width="100%" align="center" border="0" class="tableline">
								<TBODY>
									<TR>
										<TD align="center" class="titleline"  <%if(fixSub) {%>style="padding-left:20px;"<%} %>>
										<!-- BEGIN - 环节表头开始 -->
											<TABLE cellSpacing="0" cellPadding="0" width="100%" align="center" border="0">
												<TBODY>
													<TR <%if(!fixSub) {%> onClick="expandcoll('<%=str_Pro_infor_id %>')" onmouseover="collonmouseover('<%=str_Pro_image_id %>')" onmouseout="collonmouseout('<%=str_Pro_image_id %>')"<%} %>>
														<TD class="title" align="left" valign="middle" title="<%=m_str_ProDesc %>" <%if(flowID != null && !flowID.equals("")){%>style="color:red;"<%}%>>
															<IMG id="<%=str_Pro_image_id %>" src="../../../common/style/blue/images/workflow/List_close.gif" border="0" valign="middle">&nbsp;
															<%
															String m_str_ProcessText = "";
															if (m_long_FlagPredefined==1) {
																m_str_ProcessText = "<eoms:lable name='wf_baseinfoview_text'/>";
															} else if (StringUtils.isNotBlank(parentFlowId)) {
																m_str_ProcessText = "<eoms:lable name='wf_baseinfoview_text1'/>";
															} else {
																m_str_ProcessText = "<eoms:lable name='wf_baseinfoview_text'/>";
															}
															%>
															<%=m_str_ProcessText %>(<%=int_Pro_id%>)：
															
															<%
															if (m_str_ProDesc.length()>25)
															{
																m_str_ProDesc = m_str_ProDesc.substring(0,25) + "...";
															}
															 %>
															 <%=m_str_ProDesc%>
															 &nbsp;
															 <span nowrap>
															 处理人：
															<%=m_str_RoleName %>
															<%
															if (m_str_AssginName.length()>30){m_str_AssginName = m_str_AssginName.substring(0,30) + "...";}
															if(m_str_DealFullName == null || m_str_DealFullName.equals(""))
															{
																if(m_str_ActorType.equals("USER"))
																{
																	UserInfo uInfo = PreviewUtils.getUserInfo(m_str_AssignLoginName);
																	out.print(m_str_AssginName + "（" + (uInfo != null ? uInfo.getMobile() : "") + "）");
																}
																else
																{
																	String tmp_UsersInGroup = "";
																	if(m_ProcessModel.getActorType().equals("GROUP"))
																	{
																		List<UserInfo> userList = depManagerService.getUserByDepID(m_ProcessModel.getAssignGroupId(), false);
																		if(userList != null && userList.size() > 0)
																		{
																			for(UserInfo userInfo : userList)
																			{
																				tmp_UsersInGroup += userInfo.getFullname() + " ";
																			}
																		}
																	}
																	else if(m_ProcessModel.getActorType().equals("ROLE"))
																	{
																		List<RoleUser> userList = roleManagerService.getRoleUserByCroleID(m_ProcessModel.getAssignGroupId());
																		if(userList != null && userList.size() > 0)
																		{
																			for(RoleUser userInfo : userList)
																			{
																				tmp_UsersInGroup += userInfo.getFullName() + " ";
																			}
																		}
																	}
																	out.print(m_str_AssginName + "（" + tmp_UsersInGroup + "）");
																}
															}
															else
															{
																UserInfo uInfo = PreviewUtils.getUserInfo(m_str_DealLoginName);
																out.print(m_str_DealFullName + "（" + uInfo.getMobile() + "）");
															}
															%>
															</span>
														</TD>
														<TD class="title" align="right" valign="middle" title="<%=m_str_AssginName %>" <%if(flowID != null && !flowID.equals("")){%>style="color:red;"<%}%>>
															开始时间：<%=m_long_StDate%>&nbsp;
															状态：&lt;<%=m_str_ProcessStatus %>&gt;&nbsp;
															
														</TD>
													</TR>
												</TBODY>
											</TABLE>
										<!-- END 	- 环节表头结束 -->
										<!-- BEGIN 	- 环节内容开始 -->
										<div id="<%if(fixSub) {%>sub_<%}%><%=str_Pro_infor_id %>" style="<%if(!fixSub && (!flagFirst || strFlagNewWindow.equals("1"))) {%>display: none;<%} else{%>display: block;<%} %>">
											<TABLE cellSpacing="1" cellPadding="0" width="100%" align="center" border="0"  class="tableline" style="border:#CCCCCC solid 1px;">
										        <TBODY>
										        <TR>
										          <TD class=ColumntableTD_LookProcess width="12%"><eoms:lable name="wf_baseinfoview_assignee"></eoms:lable>：</TD>
									             <TD class=ColumntableTD width="13%">&nbsp;<%=m_str_DealFullName %></TD>
										          <TD class=ColumntableTD_LookProcess width="12%"><eoms:lable name="wf_baseinfoview_time"></eoms:lable>：</TD>
										          <TD class=ColumntableTD width="13%">&nbsp;
										          	<% if (m_long_FlagActive==1) {%>
										          		<%=m_long_BgDate %>
										          	<%} else{%>
										          		<%=m_long_EdDate %>
										          	<%}%>
										          </TD>
										          <%if (m_str_processtype.equals("DEAL")){ %>
											          <TD class=ColumntableTD_LookProcess width="12%" valign="middle"><eoms:lable name="wf_baseinfoview_acceptOverTimeDate"></eoms:lable>：</TD>
											          <TD class=ColumntableTD width="13%">&nbsp;<%=m_long_AcceptOverTimeDate %></TD>
											          <TD class=ColumntableTD_LookProcess width="12%"><eoms:lable name="wf_baseinfoview_dealOverTimeDate"></eoms:lable>：</TD>
											          <TD class=ColumntableTD width="13%">&nbsp;<%=m_long_DealOverTimeDate %></TD>
										          <%} else { %>
											          <TD class=ColumntableTD_LookProcess width="12%" valign="middle">&nbsp;</TD>
											          <TD class=ColumntableTD width="13%">&nbsp;</TD>
											          <TD class=ColumntableTD_LookProcess width="12%">&nbsp;</TD>
											          <TD class=ColumntableTD width="13%">&nbsp;</TD>									          
										          <%} %> 
										        </TR>
			        <%
						String s = PreviewUtils.printStepHtml(m_BaseOwnFieldInfoList, m_str_processid, taskId, baseid, baseschema, tplId);
						out.println(s);
						//处理附件
						out.println(PreviewUtils.printStepAttachment(m_str_processid));
					%>						
					
										        <TR>
										          <TD class=ColumntableTD_LookProcess valign="middle"><eoms:lable name="wf_baseinfoview_lookProcess"></eoms:lable>：</TD>
										          <TD class=ColumntableTD colSpan=7>
										          <%
											          String log = new DealProcessManager().getDpLog(baseschema, baseid, m_ProcessModel, m_ProcessModelList);
											          out.println(log);
										           %>
												  </TD>
										        </TR>
											</TABLE>
			<%
			tmpArray[1] = currentIndex+1;
			flagFirst = false;

			if(m_BaseTplID != null && !m_BaseTplID.equals("") && flowID != null && !flowID.equals(""))
			{
				if(m_SubProcessMap.containsKey(flowID))
				{
					List<DealProcess> subProcessList =  m_SubProcessMap.get(flowID);
					stackList.add(new Object[]{subProcessList, 0});
				}
			}
			else
			{
				%>
				
										<!-- END - 环节内容结束 -->
										</div>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
						</TD>
					</TR>
				<!-- END - 环节结束 -->	
				</TBODY>
			</TABLE>
			</div>
				<%
			}
		}
		 %>
		 </div>
	</body>
</html>
<SCRIPT language=javascript>
controlbodyHeight();
</SCRIPT>
