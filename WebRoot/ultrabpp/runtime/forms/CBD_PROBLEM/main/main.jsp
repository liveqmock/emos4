<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp"%>
<ubf:PanelGroup name="PG_Main" cell="4" type="tab" visiable="1" showtitle="1">
	<ubf:Panel name="P_Main" label="主表单信息" visiable="1">
		<ubf:CharacterField name="BaseSummary" label="标题" row="1" cell="4" visiable="1" length="255" />
		<ubf:PanelGroup name="DetailSheetMes" cell="4" type="panel" visiable="1" showtitle="1">
			<ubf:Panel name="ContactInfo" label="申报人信息" visiable="1">
				<ubf:CharacterField name="ContactPerson" label="申报人" row="1" cell="1" visiable="1" length="128" defaultValue="${displayCxt.userInfo.fullName}"/>
				<ubf:HiddenField name="ContactPersonlogin" label="申报人登陆名" cell="1" length="128" defaultValue="${displayCxt.userInfo.loginName}"/>
				<ubf:HiddenField name="ContactPersonId" label="申报人ID" cell="1" length="99" />
				<ubf:CharacterField name="ContactPersonSite" label="申报人单位" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactDepartment" label="申报人部门" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactOA" label="申报人OA号" row="1" cell="1" visiable="1" length="128" defaultValue="${displayCxt.userInfo.oaNumber}"/>
				<ubf:CharacterField name="ContactStation" label="申报人工位" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactPosition" label="申报人职位" row="1" cell="1" visiable="1" length="128" defaultValue="${displayCxt.userInfo.position}"/>
				<ubf:CharacterField name="ContactPhone" label="申报人手机" row="1" cell="1" visiable="1" length="128" defaultValue="${displayCxt.userInfo.mobile}"/>
				<ubf:CharacterField name="ContactTel" label="申报人电话" row="1" cell="1" visiable="1" length="128" defaultValue="${displayCxt.userInfo.phone}"/>
				<ubf:CharacterField name="CreatorLoginName" label="建单人" row="1" cell="1" visiable="1" length="128" defaultValue="${displayCxt.userInfo.loginName}"/>
			</ubf:Panel>
			<ubf:Panel name="ProBasicMes" label="问题基本信息" visiable="1">
				<ubf:SelectField name="ProSource" label="问题来源" type="sysdic" resource="problem" paras="问题来源" cell="1" visiable="1"  />
				<ubf:CharacterField name="RelateSheet" label="关联单号" row="1" cell="1" visiable="1" length="128" />
				<ubf:HiddenField name="closedCode" label="基本结论" cell="1" length="128" />
				<ubf:CharacterField name="ProDescription" label="问题描述" row="3" cell="4" visiable="1" length="4000" />
				<ubf:HiddenField name="cdbTeamName" label="建单人团队名" cell="1" length="100" defaultValue="${displayCxt.userInfo.cdbTeamName}"/>
				<ubf:HiddenField name="cdbTeamId" label="建单人团队Id" cell="1" length="100" defaultValue="${displayCxt.userInfo.cdbTeamId}"/>
				<ubf:HiddenField name="Processors" label="处理人" cell="1" length="128" />
				<ubf:HiddenField name="ProcessorsName" label="处理人名字" cell="1" length="128" />
				<ubf:HiddenField name="TurnSendPerson" label="分派人" cell="1" length="128" />
				<ubf:HiddenField name="TurnSendPersonID" label="分派人ID" cell="1" length="128" />
				<ubf:HiddenField name="cdbDealTeamName" label="处理人团队" cell="1" length="128" />
				<ubf:HiddenField name="cdbDealTeamId" label="处理人团队Id" cell="1" length="128" />
				<ubf:CharacterField name="acceptTime" label="受理时间" row="1" cell="1" visiable="0" length="100" />
			</ubf:Panel>
			<ubf:Panel name="ProAssignMes" label="问题分派信息" visiable="1">
				<ubf:SelectField name="ProEffectDgree" label="问题影响度" type="sysdic" resource="Incident_EffectDgree" paras="" cell="1" visiable="1" defaultValue="3" />
				<ubf:SelectField name="ProUrgentDgree" label="问题紧急度" type="sysdic" resource="Incident_UrgentDgree" paras="" cell="1" visiable="1" defaultValue="3" />
				<ubf:CharacterField name="Priority" label="优先级" row="1" cell="1" visiable="1" length="128" defaultValue="4"/>
				<ubf:SelectField name="ProPhenoClass" label="问题现象分类" type="table" resource="bs_t_sm_commonTree:fullname:fullname" paras="type='incPhenoClass'" cell="1" visiable="1"  />
				<ubf:TimeField name="ForecastResolutionTime" label="预计解决时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
			</ubf:Panel>
			<ubf:Panel name="ProHandleMes" label="问题处理信息" visiable="1">
				<ubf:SelectField name="ProReason" label="问题原因分类" type="table" resource="bs_t_sm_commonTree:fullname:fullname" paras="type='incCauseClass'" cell="1" visiable="1"  />
				<ubf:TimeField name="ResolutionTime" label="解决时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="ProReasonAnalysis" label="原因分析" row="3" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="ProSolutions" label="解决方案" row="3" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="SolvingProcess" label="解决过程" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
			<ubf:Panel name="ProReview" label="问题回顾信息" visiable="1">
				<ubf:CharacterField name="ProProcessDesc" label="处理过程总结" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
			<ubf:Panel name="AttachmentListPanel" label="附件列表" visiable="1">
				<ubf:AttachmentField name="AttachmentsList" label="附件列表" types="*.*" visiable="1">
					<atta:fileupload id="AttachmentsList_tag"
				 		uploadBtnIsVisible="false" fileTypes="${AttachmentsList_types}" uploadable="${AttachmentsList_uploadable}"
						progressIsVisible="true" uploadTableVisible="true" isMultiDownload="true" isAutoUpload="true" downTableVisible="true" isMultiUpload="true"
						attchmentGroupId="${AttachmentsList_relcationCode}" operationParams="0,${AttachmentsList_edit},1" uploadDestination="${path}"
						flashUrl="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf" >
					</atta:fileupload>
				</ubf:AttachmentField>
			</ubf:Panel>
			<ubf:Panel name="DealRecord" label="处理记录" visiable="1">
				<ubf:ViewField name="deal_records" label="处理记录" row="4" cell="4" type="frame" visiable="1" url="${ctx}/wfrecord/showWfRecords.action?baseschema=${displayCxt.baseSchema}&baseid=${displayCxt.baseID}&recordViewFieldName=deal_records"/>
			</ubf:Panel>
		</ubf:PanelGroup>
	</ubf:Panel>
	<ubf:Panel name="kmBase" label="关联知识" visiable="1">
		<ubf:ViewField name="view_kmBase"  row="10" cell="4" type="frame" visiable="1" url="${ctx}/extractKnowledge/showKnowledge.action?baseSchema=${displayCxt.baseSchema}&baseId=${displayCxt.baseID}"/>
	</ubf:Panel>
	<ubf:Panel name="relationsheet" label="关联工单" visiable="1">
		<ubf:ViewField name="relationsheetView" label="关联工单" row="20" cell="4" type="frame" visiable="1" url="${ctx}/relatesheet/list.action?baseSchema=${displayCxt.fieldMap['BASESCHEMA']}&baseId=${displayCxt.fieldMap['BASEID']}&baseSn=${displayCxt.fieldMap['BASESN']}&taskId=${displayCxt.taskID}&status=${displayCxt.fieldMap['BASESTATUS']}&flagActive=${displayCxt.currentTask.flagActive} &cRelateData=1&cRelateByConfig=1"/>
	</ubf:Panel>
	<ubf:Panel name="workflowlog" label="流程记录" visiable="1">
		<ubf:ViewField name="workflowlogpage" label="流程记录" row="20" cell="4" type="frame" visiable="1" url="${ctx}/workflow/sheet/query/BaseInfoViewBpp.jsp?baseschema=${displayCxt.baseSchema}&baseid=${displayCxt.baseID}&tplid=${displayCxt.defCode}"/>
	</ubf:Panel>
</ubf:PanelGroup>
