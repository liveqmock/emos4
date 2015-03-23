<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp"%>
<ubf:PanelGroup name="PG_Main" cell="4" type="tab" visiable="1" showtitle="1">
	<ubf:Panel name="P_Main" label="主表单信息" visiable="1">
		<ubf:CharacterField name="Title" label="标题" row="1" cell="4" visiable="1" length="255" />
		<ubf:PanelGroup name="DetailTicketGP" cell="4" type="panel" visiable="1" showtitle="1">
			<ubf:Panel name="ContactInfoPanel" label="联系人信息" visiable="1">
				<ubf:CharacterField name="ContactPerson" label="联系人" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactPersonSite" label="联系人单位" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactDepartment" label="联系人部门" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactStation" label="联系人工位" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactPosition" label="联系人职位" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactPhone" label="联系人手机" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="ContactTel" label="联系人电话" row="1" cell="1" visiable="1" length="128" />
				<ubf:SelectField name="IsVIP" label="是否VIP用户" type="sysdic" resource="是:是,否:否" paras="" cell="1" visiable="1"  />
			</ubf:Panel>
			<ubf:Panel name="IncidentInfoPanel" label="事件单信息" visiable="1">
				<ubf:CharacterField name="PhenomenonClassification" label="现象分类" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="Source" label="来源" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="AssociateSystem" label="关联系统" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="EffectDgree" label="事件影响度" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="UrgentDgree" label="事件紧急度" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="Priority" label="优先级" row="1" cell="1" visiable="1" length="128" />
				<ubf:SelectField name="IsMajor" label="是否重大事件" type="collect" resource="是:是,否:否" paras="" cell="1" visiable="1"  />
				<ubf:TimeField name="OccurrenceTime" label="发生时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd"  />
				<ubf:CharacterField name="IncidentLable" label="事件标签" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="IncidentDes" label="事件描述" row="2" cell="4" visiable="1" length="4000" />
				<ubf:AttachmentField name="AttachmentsList" label="附件列表" types="*.*" visiable="1">
					<atta:fileupload id="AttachmentsList_tag"
				 		uploadBtnIsVisible="false" fileTypes="${AttachmentsList_types}" uploadable="${AttachmentsList_uploadable}"
						progressIsVisible="true" uploadTableVisible="true" isMultiDownload="true" isAutoUpload="true" downTableVisible="true" isMultiUpload="true"
						attchmentGroupId="${AttachmentsList_relcationCode}" operationParams="0,${AttachmentsList_edit},1" uploadDestination="${path}"
						flashUrl="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf" >
					</atta:fileupload>
				</ubf:AttachmentField>
			</ubf:Panel>
			<ubf:Panel name="ProcessPanel" label="处理过程" visiable="1">
				<ubf:CharacterField name="CausesClassification" label="原因分类" row="1" cell="1" visiable="1" length="128" />
				<ubf:TimeField name="RecoveryTime" label="恢复时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd"  />
				<ubf:CharacterField name="ServiceInterruptionTime" label="服务中断时间" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="IncidentProperties" label="事件性质" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="SolutionType" label="解决方案类型" row="1" cell="1" visiable="1" length="128" />
				<ubf:CharacterField name="Solution" label="解决方案" row="2" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="IncidentReason" label="事件原因" row="2" cell="4" visiable="1" length="4000" />
				<ubf:CharacterField name="ProcedureProcessing" label="处理过程" row="2" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
		</ubf:PanelGroup>
	</ubf:Panel>
</ubf:PanelGroup>
