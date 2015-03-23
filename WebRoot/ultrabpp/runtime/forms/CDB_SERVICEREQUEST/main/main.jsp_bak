<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp"%>
<ubf:PanelGroup name="PG_Main" cell="4" type="tab" visiable="1" showtitle="1">
	<ubf:Panel name="cdb_servicerequest" label="主表单信息" visiable="1">
		<ubf:HiddenField name="audit_sequence" label="审批序列，1受理人，2...审批人" cell="1" length="50" />
		<ubf:HiddenField name="AcceptanceTime" label="受理时间" cell="1" length="2000" />
		<ubf:HiddenField name="AcceptanceTimeLimit" label="受理时限" cell="1" length="2000" />
		<ubf:HiddenField name="DealTime2" label="处理时间" cell="1" length="2000" />
		<ubf:HiddenField name="isSelfHelp" label="自助服务工单" cell="1" length="100" />
		<ubf:HiddenField name="service_category_2" label="二级服务分类" cell="1" length="2000" />
		<ubf:HiddenField name="deal_finish_time" label="处理完成时间" cell="1" length="2000" />
		<ubf:HiddenField name="service_category_1" label="一级服务分类" cell="1" length="2000" />
		<ubf:HiddenField name="dealuserloginname" label="处理人登录名" cell="1" length="2000" />
		<ubf:HiddenField name="audit_accept_time" label="审批人受理时间" cell="1" length="20" />
		<ubf:HiddenField name="service_category_3" label="三级服务分类" cell="1" length="2000" />
		<ubf:HiddenField name="submit_time" label="服务提交时间" cell="1" length="2000" />
		<ubf:HiddenField name="DealTimeLimit" label="处理时限" cell="1" length="2000" />
		<ubf:CharacterField name="BaseSummary" label="标题" row="1" cell="4" visiable="1" length="1000" />
		<ubf:PanelGroup name="sheetinfo" cell="4" type="panel" visiable="1" showtitle="1">
			<ubf:Panel name="serviceArea" label="服务说明区域" visiable="1">
				<ubf:CharacterField name="DaysResponseTime" label="工作日响应时间" row="1" cell="1" visiable="1" length="100" />
				<ubf:CharacterField name="NoWorkingDaysRespTime" label="非工作日响应时间" row="1" cell="1" visiable="1" length="100" />
				<ubf:CharacterField name="BASEACCEPTOUTTIME" label="响应时限" row="1" cell="1" visiable="1" length="100" />
				<ubf:CharacterField name="WorkingTimeLimit" label="解决时间上限(不更硬件)" row="1" cell="1" visiable="1" length="100" />
				<ubf:CharacterField name="NoWorkingTimeLimit" label="解决时间上限(更换硬件)" row="1" cell="1" visiable="1" length="100" />
				<ubf:CharacterField name="BASEDEALOUTTIME" label="处理时限" row="1" cell="1" visiable="1" length="100" />
			</ubf:Panel>
			<ubf:Panel name="requestUserInfo" label="请求人信息" visiable="1">
				<ubf:HiddenField name="requestUserID" label="请求人ID" cell="1" length="100" defaultValue="${displayCxt.userInfo.loginName}"/>
				<ubf:CharacterField name="requestUser" label="请求人" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.fullName}"/>
				<ubf:CharacterField name="requestUserDept" label="请求人单位" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.cdbUnitName}"/>
				<ubf:CharacterField name="requestUserGroup" label="请求人部门" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.cdbDepName}"/>
				<ubf:CharacterField name="requestUserOA" label="请求人OA号" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.oaNumber}"/>
				<ubf:CharacterField name="requestUserMobile" label="请求人手机" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.mobile}"/>
				<ubf:CharacterField name="requestUserPosition" label="请求人职位" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.position}"/>
				<ubf:CharacterField name="requestUserPhone" label="请求人电话" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.phone}"/>
				<ubf:CharacterField name="requestUserSite" label="请求人工位" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.station}"/>
				<ubf:CharacterField name="ISVIP" label="VIP用户" row="1" cell="1" visiable="1" length="20" defaultValue="${displayCxt.userInfo.isVip}"/>
			</ubf:Panel>
			<ubf:Panel name="requestInfo" label="服务请求信息" visiable="1">
				<ubf:HiddenField name="submit_request_person_name" label="提交请求人名字" cell="1" length="100" />
				<ubf:HiddenField name="submit_request_person" label="提交请求人" cell="1" length="50" />
				<ubf:SelectField name="dataResource" label="来源" type="sysdic" resource="resouce" paras="" cell="1" visiable="1"  />
				<ubf:SelectField name="relation_system" label="关联系统" type="table" resource="bs_t_sm_dicitem:dicfullname:dicfullname" paras="dtcode = 'cdb_system'" cell="1" visiable="1"  />
				<ubf:TimeField name="requireDealTime" label="要求服务实施时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:HiddenField name="service_categoryID" label="服务分类ID" cell="1" length="100" />
				<ubf:SelectField name="service_category" label="服务分类" type="bean" resource="serviceCategorySelect" paras="" cell="1" visiable="1"  />
				<ubf:HiddenField name="requestId" label="服务项ID" cell="1" length="100" />
				<ubf:CharacterField name="requestTitle" label="服务请求标签" row="1" cell="1" visiable="0" length="100" />
				<ubf:ButtonField name="download_template" label="点击获取模板按钮下载模板" cell="1" row="1" code="getTemplate:获取模板" visiable="1"></ubf:ButtonField>
				<ubf:CharacterField name="acceptTime" label="服务受理时间" row="1" cell="1" visiable="0" length="100" />
				<ubf:CharacterField name="acceptUser" label="服务受理人" row="1" cell="1" visiable="0" length="100" />
				<ubf:CharacterField name="dealTime" label="服务处理时间" row="1" cell="1" visiable="0" length="100" />
				<ubf:CharacterField name="dealUser" label="服务处理人" row="1" cell="1" visiable="0" length="100" />
				<ubf:CharacterField name="auditTime" label="服务审批时间" row="1" cell="1" visiable="0" length="100" />
				<ubf:CharacterField name="requestLevel" label="服务请求优先级" row="1" cell="1" visiable="1" length="20" />
				<ubf:CharacterField name="dealUserAcceptTime" label="服务处理人受理时间" row="1" cell="1" visiable="0" length="100" />
				<ubf:CharacterField name="auditor" label="服务审批人" row="1" cell="1" visiable="0" length="100" />
				<ubf:CharacterField name="requestDesc" label="服务请求描述" row="4" cell="4" visiable="1" length="4000" />
				<ubf:HiddenField name="cdbDealTeamName" label="处理人团队名称" cell="1" length="100" />
				<ubf:CharacterField name="closeUserAcceptTime" label="关单人受理时间" row="1" cell="1" visiable="0" length="20" />
				<ubf:HiddenField name="cdbDealTeamId" label="处理人团队Id" cell="1" length="100" />
			</ubf:Panel>
			<ubf:Panel name="deal_info_box" label="处理信息" visiable="1">
				<ubf:CharacterField name="deal_process_solution" label="处理过程与解决方案" row="4" cell="3" visiable="1" length="1000" />
				<ubf:CollectField name="isThirdpartSupport" label="是否第三方支持" type="collect" showtype="radio" resource="否:否,是:是" paras="" row="1" cell="1" visiable="1" defaultValue="否" />
				<ubf:TimeField name="reqRepairStartTime" label="报修开始时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="repairedEndTime" label="维修结束时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="RepairDays" label="报修天数" row="1" cell="1" visiable="1" length="10" defaultValue="0"/>
				<ubf:CharacterField name="timeout_reason" label="超时原因" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
			<ubf:Panel name="attachment" label="附件" visiable="1">
				<ubf:AttachmentField name="requestAttachment" label="附件" types="*.*" visiable="1">
					<atta:fileupload id="requestAttachment_tag"
				 		uploadBtnIsVisible="false" fileTypes="${requestAttachment_types}" uploadable="${requestAttachment_uploadable}"
						progressIsVisible="true" uploadTableVisible="true" isMultiDownload="true" isAutoUpload="true" downTableVisible="true" isMultiUpload="true"
						attchmentGroupId="${requestAttachment_relcationCode}" operationParams="0,${requestAttachment_edit},1" uploadDestination="${path}"
						flashUrl="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf" >
					</atta:fileupload>
				</ubf:AttachmentField>
			</ubf:Panel>
			<ubf:Panel name="records" label="处理记录" visiable="1">
				<ubf:ViewField name="deal_records"  row="1" cell="3" type="frame" visiable="1" url="${ctx}/wfrecord/showWfRecords.action?baseschema=${displayCxt.baseSchema}&baseid=${displayCxt.baseID}&recordViewFieldName=deal_records"/>
				<ubf:HiddenField name="cdbTeamName" label="建单人团队名称" cell="1" length="100" defaultValue="${displayCxt.userInfo.cdbTeamName}"/>
				<ubf:HiddenField name="cdbTeamId" label="建单人团队Id" cell="1" length="100" defaultValue="${displayCxt.userInfo.cdbTeamId}"/>
			</ubf:Panel>
			<ubf:Panel name="SatisfactionPanel" label="满意度" visiable="1">
				<ubf:SelectField name="satisfaction_level" label="满意度" type="collect" resource="非常满意:非常满意,不满意:不满意" paras="" cell="1" visiable="1" defaultValue="非常满意" />
				<ubf:CharacterField name="satisfaction_content" label="满意度评价" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
		</ubf:PanelGroup>
		<ubf:HiddenField name="viplevel" label="VIP等级" cell="1" length="100" />
	</ubf:Panel>
	<ubf:Panel name="relationsheet" label="关联工单" visiable="1">
		<ubf:ViewField name="relationsheetView"  row="20" cell="4" type="frame" visiable="1" url="${ctx}/relatesheet/list.action?baseSchema=${displayCxt.fieldMap['BASESCHEMA']}&baseId=${displayCxt.fieldMap['BASEID']}&baseSn=${displayCxt.fieldMap['BASESN']}}&relateSchema=PBCCRC_CHANGE,PBCCRC_PROBLEM &taskId=${displayCxt.taskID}&status=${displayCxt.fieldMap['BASESTATUS']}&flagActive=${displayCxt.currentTask.flagActive}？RelateData=1"/>
	</ubf:Panel>
	<ubf:Panel name="workflowlog" label="流程记录" visiable="1">
		<ubf:ViewField name="workflowlogpage"  row="20" cell="4" type="frame" visiable="1" url="${ctx}/workflow/sheet/query/BaseInfoViewBpp.jsp?baseschema=${displayCxt.baseSchema}&baseid=${displayCxt.baseID}&tplid=${displayCxt.defCode}"/>
	</ubf:Panel>
</ubf:PanelGroup>
