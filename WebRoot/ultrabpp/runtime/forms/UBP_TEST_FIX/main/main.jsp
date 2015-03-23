<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp"%>
<ubf:PanelGroup name="PG_Main" cell="4" type="tab" visiable="1" showtitle="1">
	<ubf:Panel name="P_Main" label="基本信息" visiable="1">
		<ubf:CharacterField name="amount" label="申请金额" row="1" cell="1" visiable="1" length="10" defaultValue="100"/>
		<ubf:TimeField name="startTime" label="出差开始时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd"  />
		<ubf:TimeField name="endTime" label="出差结束时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd"  />
		<ubf:SelectField name="eLevel" label="紧急程度" type="collect" resource="一般:一般,紧急:紧急,非常紧急:非常紧急" paras="" cell="1" visiable="1"  />
		<ubf:CharacterField name="evectionCause" label="出差理由" row="3" cell="4" visiable="1" length="1000" />
		<ubf:CharacterField name="approvalResult" label="审批结果" row="1" cell="2" visiable="0" length="10" />
		<ubf:CharacterField name="recheckResult" label="复核结果" row="1" cell="2" visiable="0" length="10" />
		<ubf:PanelGroup name="ParticularGP" cell="4" type="panel" visiable="1" showtitle="1">
			<ubf:Panel name="ParticularPanel" label="工单详细信息容器" visiable="1">
				<ubf:CharacterField name="BaseSummary" label="主题" row="1" cell="4" visiable="1" length="1000" />
				<ubf:CollectField name="CheckBoxTest" label="紧急程度" type="sysdic" showtype="checkbox" resource="emergency" paras="" cell="2" visiable="1"  />
				<ubf:CollectField name="RadioBoxTest" label="问题解决情况" type="sysdic" showtype="radio" resource="isOk" paras="" cell="2" visiable="1"  />
				<ubf:LabelField name="TextFieldTest" label="请选择关联按钮创建工单" cell="1" row="1" visiable="1" ></ubf:LabelField>
				<ubf:ButtonField name="ButtonFieldTest"  cell="1" row="1" code="bt1:关联建单" visiable="1"></ubf:ButtonField>
				<ubf:AssignTreeField name="SuggestPerson" label="建议复核人" row="1" cell="1" single="1"      next="0" visiable="1" />
				<ubf:AttachmentField name="AttachmentsList" label="附件列表" types="*.*" visiable="1">
					<atta:fileupload id="AttachmentsList_tag"
				 		uploadBtnIsVisible="false" fileTypes="${AttachmentsList_types}" uploadable="${AttachmentsList_uploadable}"
						progressIsVisible="true" uploadTableVisible="true" isMultiDownload="true" isAutoUpload="true" downTableVisible="true" isMultiUpload="true"
						attchmentGroupId="${AttachmentsList_relcationCode}" operationParams="0,${AttachmentsList_edit},1" uploadDestination="${path}"
						flashUrl="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf" >
					</atta:fileupload>
				</ubf:AttachmentField>
			</ubf:Panel>
		</ubf:PanelGroup>
	</ubf:Panel>
	<ubf:Panel name="DealDescPanelField" label="处理记录" visiable="1">
		<ubf:ViewField name="DealInfoViewField"  row="20" cell="4" type="frame" visiable="1" url="${ctx}/workflow/sheet/query/BaseInfoViewBpp.jsp?baseschema=${displayCxt.baseSchema}&baseid=${displayCxt.baseID}&tplid=${displayCxt.defCode}"/>
	</ubf:Panel>
</ubf:PanelGroup>
