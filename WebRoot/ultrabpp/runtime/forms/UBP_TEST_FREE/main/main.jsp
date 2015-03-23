<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp"%>
<ubf:PanelGroup name="PG_Main" cell="4" type="tab" visiable="1" showtitle="1">
	<ubf:Panel name="P_Basic" label="基本信息" visiable="1">
		<ubf:CharacterField name="BaseSummary" label="会议主题" row="1" cell="4" visiable="1" length="1000" />
		<ubf:TimeField name="meetingTime" label="会议时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd"  />
		<ubf:SelectField name="meetingLevel" label="重要级别" type="collect" resource="一般:一般,重要:重要,非常重要:非常重要" paras="" cell="1" visiable="1"  />
		<ubf:CollectField name="isSubmit" label="是否提交客户" type="sysdic" showtype="radio" resource="isdefault" paras="" cell="2" visiable="1"  />
		<ubf:AttachmentField name="meetingSummary" label="会议纪要" types="*.*" visiable="1">
			<atta:fileupload id="meetingSummary_tag"
		 		uploadBtnIsVisible="false" fileTypes="${meetingSummary_types}" uploadable="${meetingSummary_uploadable}"
				progressIsVisible="true" uploadTableVisible="true" isMultiDownload="true" isAutoUpload="true" downTableVisible="true" isMultiUpload="true"
				attchmentGroupId="${meetingSummary_relcationCode}" operationParams="0,${meetingSummary_edit},1" uploadDestination="${path}"
				flashUrl="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf" >
			</atta:fileupload>
		</ubf:AttachmentField>
	</ubf:Panel>
</ubf:PanelGroup>
