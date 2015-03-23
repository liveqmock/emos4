<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp"%>
<ubf:PanelGroup name="PG_Main" cell="4" type="tab" visiable="1" showtitle="1">
	<ubf:Panel name="chgInfo" label="工单信息" visiable="1">
		<ubf:CharacterField name="BaseSummary" label="标题" row="1" cell="4" visiable="1" length="1000" />
		<ubf:PanelGroup name="chgMainPageInfo" cell="4" type="panel" visiable="1" showtitle="1">
			<ubf:Panel name="requestInfo" label="申请人信息" visiable="1">
				<ubf:CharacterField name="requestUser" label="请求人" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.fullName}"/>
				<ubf:HiddenField name="requestUserId" label="请求人ID" cell="1" length="1000" />
				<ubf:CharacterField name="requestDept" label="请求人部门" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.depName}"/>
				<ubf:CharacterField name="requestGroup" label="请求人单位" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.cdbUnitName}"/>
				<ubf:CharacterField name="requestOA" label="请求人OA号" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.loginName}"/>
				<ubf:CharacterField name="requestEmail" label="请求人邮件" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.email}"/>
				<ubf:CharacterField name="requestMobile" label="请求人手机" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.mobile}"/>
			</ubf:Panel>
			<ubf:Panel name="baseInfo" label="基本信息" visiable="1">
				<ubf:SelectField name="chgSort" label="变更分类" type="table" resource="bs_t_sm_commontree:FULLNAME:FULLNAME" paras="type='changeType'" cell="1" visiable="1"  />
				<ubf:SelectField name="chgObject" label="变更对象" type="table" resource="BS_T_WF_CHANGEDEALUSERCONFIG:bussystem:bussystem" paras="1=1" cell="1" visiable="1"  />
				<ubf:SelectField name="chgType" label="变更类型" type="collect" resource=":" paras="" cell="1" visiable="1"  />
				<ubf:SelectField name="chgState" label="变更状态" type="sysdic" resource="chg_changestate" paras="" cell="1" visiable="1"  />
				<ubf:SelectField name="connBusSystem" label="关联系统" type="table" resource="bs_t_sm_commontree:FULLNAME:FULLNAME" paras="type='busSystem'" cell="1" visiable="1"  />
				<ubf:TimeField name="planStartTime" label="计划开始时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="planFinishTime" label="计划完成时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:SelectField name="chgRiskDegree" label="风险程度" type="collect" resource="1:高,2:中,3:低" paras="" cell="1" visiable="1"  />
				<ubf:SelectField name="chgCategorys" label="变更类别" type="collect" resource="1:服务器,2:网络,3:存储,4:数据库,5:机房环境" paras="" cell="1" visiable="1"  />
				<ubf:SelectField name="chgReasons" label="变更原因" type="collect" resource="1:硬件,2:中间件,3:数据库,4:其它" paras="" cell="1" visiable="1"  />
				<ubf:CharacterField name="chgReasonsDes" label="变更原因说明" row="1" cell="2" visiable="1" length="1000" />
				<ubf:SelectField name="chgAffectUdSystem" label="对上下游系统影响" type="collect" resource="1:是,2:否" paras="" cell="1" visiable="1"  />
				<ubf:CharacterField name="chgAffectSystem" label="影响系统" row="1" cell="1" visiable="1" length="50" />
				<ubf:CharacterField name="chgImplementationContacts" label="实施联系人" row="1" cell="1" visiable="1" length="50" />
				<ubf:HiddenField name="chgAffectSystemId" label="影响系统ID" cell="1" length="100" />
				<ubf:SelectField name="chgAffenctEndUser" label="对终端用户的影响" type="collect" resource="1:是,2:否" paras="" cell="1" visiable="1"  />
				<ubf:CharacterField name="chgAffectEndUserDes" label="对终端用户影响说明" row="1" cell="2" visiable="1" length="1000" />
				<ubf:SelectField name="isNeedSyncDrChg" label="是否需要灾备变更同步" type="collect" resource="1:是,2:否" paras="" cell="1" visiable="1"  />
				<ubf:TimeField name="chgDrEnvironmentDate" label="灾备环境变更时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="last_chg_time" label="上次变更时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:SelectField name="batch_info" label="批次信息" type="table" resource="BS_T_SM_BATCHCONF:batch_no||'['||max(reviewtime)||']':batch_no||'['||max(reviewtime)||']'" paras="1=1  and active= '1'  group by batch_no,reviewtime order by reviewtime --" cell="1" visiable="1"  />
				<ubf:HiddenField name="batch_info_id" label="批次信息id" cell="1" length="100" />
				<ubf:SelectField name="pre_authorized_flag" label="是否预授权" type="collect" resource="是:是,否:否" paras="" cell="1" visiable="1"  />
				<ubf:CharacterField name="chgReason" label="变更原因及内容说明" row="3" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="chgIncidence" label="变更影响范围及风险评估" row="3" cell="4" visiable="1" length="4000" />
				<ubf:HiddenField name="chgImplementationContactsId" label="实施联系人ID" cell="1" length="100" />
				<ubf:CharacterField name="chgConfigInfo" label="配置相关信息变更说明" row="3" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="chgConstructionScheme" label="变更构建方案" row="3" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="chgPublishSchema" label="发布部署方案" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
			<ubf:Panel name="checkInfo" label="方案验证信息" visiable="1">
				<ubf:CharacterField name="chgCheckResult" label="验证结果" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
			<ubf:Panel name="executeCheckInfo" label="实施及验证信息" visiable="1">
				<ubf:TimeField name="realStartTime" label="实际开始时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="realFinishTime" label="实际完成时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="actualRecode" label="实施记录" row="3" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="actualResult" label="验证结果" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
			<ubf:Panel name="reviewInfo" label="回顾信息" visiable="1">
				<ubf:SelectField name="cmdbReload" label="通知CMDB更新" type="sysdic" resource="yesOrNo" paras="" cell="1" visiable="1"  />
				<ubf:CharacterField name="baseResult" label="基本结论" row="1" cell="1" visiable="1" length="100" />
				<ubf:CharacterField name="closeUser" label="关闭人" row="1" cell="1" visiable="1" length="100" />
				<ubf:TimeField name="closeTime" label="关闭时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="closeUserID" label="关闭人登陆名" row="1" cell="1" visiable="0" length="100" />
			</ubf:Panel>
			<ubf:Panel name="CI_Manange" label="配置管理" visiable="1">
				<ubf:ViewField name="CI"  row="5" cell="4" type="frame" visiable="1" url="${ctx}/ciRelevance/listCIRelate.action?ciRelevance.baseschema=CDB_CHANGE&ciRelevance.baseid=${displayCxt.baseID}"/>
			</ubf:Panel>
			<ubf:Panel name="attacheList" label="附件列表" visiable="1">
				<ubf:AttachmentField name="chgAttache" label="附件" types="*.*" visiable="1">
					<atta:fileupload id="chgAttache_tag"
				 		uploadBtnIsVisible="false" fileTypes="${chgAttache_types}" uploadable="${chgAttache_uploadable}"
						progressIsVisible="true" uploadTableVisible="true" isMultiDownload="true" isAutoUpload="true" downTableVisible="true" isMultiUpload="true"
						attchmentGroupId="${chgAttache_relcationCode}" operationParams="0,${chgAttache_edit},1" uploadDestination="${path}"
						flashUrl="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf" >
					</atta:fileupload>
				</ubf:AttachmentField>
			</ubf:Panel>
			<ubf:Panel name="deal_records_view" label="处理记录" visiable="1">
				<ubf:ViewField name="deal_records"  row="5" cell="4" type="frame" visiable="1" url="${ctx}/wfrecord/showWfRecords.action?baseschema=${displayCxt.baseSchema}&baseid=${displayCxt.baseID}&recordViewFieldName=deal_records"/>
			</ubf:Panel>
			<ubf:Panel name="hiddenArea" label="隐藏域" visiable="1">
				<ubf:HiddenField name="connBusSystemID" label="关联应用系统ID" cell="1" length="500" />
				<ubf:CharacterField name="reviseUserId" label="修订人" row="1" cell="1" visiable="0" length="100" />
				<ubf:TimeField name="reviseAcceptTime" label="修订受理时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="reviseTime" label="修订时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="caseCheckUserID" label="方案验证人" row="1" cell="1" visiable="0" length="100" />
				<ubf:TimeField name="caseAcceptTime" label="方案验证受理时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="caseCheckTime" label="方案验证时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="auditUserID" label="审批人" row="1" cell="1" visiable="0" length="525" />
				<ubf:TimeField name="auditAcceptTime" label="审批受理时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="auditTime" label="审批时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="auditResult" label="审批结果" row="1" cell="1" visiable="0" length="100" defaultValue="0"/>
				<ubf:CharacterField name="auditTimes" label="审批次数" row="1" cell="1" visiable="0" length="100" defaultValue="0"/>
				<ubf:CharacterField name="organizeUserID" label="评审人" row="1" cell="1" visiable="0" length="100" />
				<ubf:TimeField name="organizeAcceptTime" label="评审受理时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:TimeField name="organizeTime" label="评审时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="chgExecuteUserName" label="变更实施人" row="1" cell="1" visiable="0" length="100" />
				<ubf:TimeField name="realAcceptTime" label="实施受理时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="chgCheckUserName" label="变更验证人" row="1" cell="1" visiable="0" length="100" />
				<ubf:TimeField name="testAcceptTime" label="验证受理时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="reviewUserId" label="回顾人" row="1" cell="1" visiable="0" length="100" />
				<ubf:TimeField name="reviewAcceptTime" label="回顾受理时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="allAuditTime" label="需要审批次数" row="1" cell="1" visiable="1" length="10" defaultValue="1"/>
				<ubf:TimeField name="reviewTime" label="回顾时间" row="1" cell="1" visiable="0" format="yyyy-MM-dd HH:mm:ss"  />
			</ubf:Panel>
		</ubf:PanelGroup>
	</ubf:Panel>
	<ubf:Panel name="dealRecord" label="处理记录" visiable="1">
		<ubf:ViewField name="dealrecordinfo"  row="30" cell="4" type="frame" visiable="1" url="${ctx}/workflow/sheet/query/BaseInfoViewBpp.jsp?baseschema=${displayCxt.baseSchema}&baseid=${displayCxt.baseID}&tplid=${displayCxt.defCode}"/>
	</ubf:Panel>
	<ubf:Panel name="relationSheet" label="关联工单" visiable="1">
		<ubf:ViewField name="relation"  row="10" cell="4" type="frame" visiable="1" url="${ctx}/relatesheet/list.action?baseSchema=${displayCxt.fieldMap['BASESCHEMA']}&baseId=${displayCxt.fieldMap['BASEID']}&baseSn=${displayCxt.fieldMap['BASESN']}}&relateSchema=PBCCRC_CHANGE,PBCCRC_PROBLEM &taskId=${displayCxt.taskID}&status=${displayCxt.fieldMap['BASESTATUS']}&flagActive=${displayCxt.currentTask.flagActive}？RelateData=1"/>
	</ubf:Panel>
</ubf:PanelGroup>
