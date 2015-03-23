<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp"%>
<ubf:PanelGroup name="PG_Main" cell="4" type="tab" visiable="1" showtitle="0">
	<ubf:Panel name="cdb_servicerequest" label="主表单信息" visiable="1">
		<ubf:HiddenField name="service_categoryID" label="服务分类ID" cell="1" length="100" />
		<ubf:HiddenField name="isSelfHelp" label="自助服务工单" cell="1" length="100" defaultValue="1"/>
		<ubf:HiddenField name="AcceptanceTimeLimit" label="受理时限" cell="1" length="2000" />
		<ubf:HiddenField name="audit_accept_time" label="审批人受理时间" cell="1" length="20" />
		<ubf:HiddenField name="DealTime2" label="处理时间" cell="1" length="2000" />
		<ubf:HiddenField name="audit_sequence" label="审批序列，1受理人，2...审批人" cell="1" length="50" />
		<ubf:HiddenField name="dealuserloginname" label="处理人登录名" cell="1" length="2000" />
		<ubf:HiddenField name="submit_time" label="服务提交时间" cell="1" length="2000" />
		<ubf:HiddenField name="BASEDEALOUTTIME" label="处理时限" cell="1" length="2000" />
		<ubf:HiddenField name="service_category_2" label="二级服务分类" cell="1" length="2000" />
		<ubf:HiddenField name="DealTimeLimit" label="处理时限" cell="1" length="2000" />
		<ubf:HiddenField name="service_category_3" label="三级服务分类" cell="1" length="2000" />
		<ubf:HiddenField name="BASEACCEPTOUTTIME" label="受理时限" cell="1" length="2000" />
		<ubf:HiddenField name="AcceptanceTime" label="受理时间" cell="1" length="2000" />
		<ubf:HiddenField name="service_category_1" label="一级服务分类" cell="1" length="2000" />
		<ubf:HiddenField name="deal_finish_time" label="处理完成时间" cell="1" length="2000" />
		<ubf:CharacterField name="BaseSummary" label="标题" row="1" cell="4" visiable="1" length="1000" />
		<ubf:PanelGroup name="sheetinfo" cell="4" type="panel" visiable="1" showtitle="1">
			<ubf:Panel name="requestUserInfo" label="自助信息" visiable="1">
				<ubf:HiddenField name="requestUserID" label="请求人ID" cell="1" length="100" defaultValue="${displayCxt.userInfo.loginName}"/>
				<ubf:CharacterField name="requestUser" label="请求人" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.fullName}"/>
				<ubf:CharacterField name="requestUserPhone" label="联系方式" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.phone}"/>
				<ubf:CharacterField name="requestUserSite" label="工位" row="1" cell="1" visiable="1" length="100" defaultValue="${displayCxt.userInfo.station}"/>
				<ubf:SelectField name="service_category" label="服务分类" type="bean" resource="serviceCategorySelectExternal" paras="" cell="1" visiable="1"  />
				<ubf:ButtonField name="download_template" label="点击获取模板按钮下载模板" cell="1" row="1" code="getTemplate:获取模板" visiable="1"></ubf:ButtonField>
				<ubf:TimeField name="requireDealTime" label="要求服务实施时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
				<ubf:CharacterField name="requestDesc" label="服务请求描述" row="4" cell="4" visiable="1" length="4000" />
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
			<ubf:Panel name="SatisfactionPanel" label="满意度" visiable="1">
				<ubf:SelectField name="satisfaction_level" label="满意度" type="collect" resource="非常满意:非常满意,不满意:不满意" paras="" cell="1" visiable="1" defaultValue="非常满意" />
				<ubf:CharacterField name="satisfaction_content" label="满意度评价" row="3" cell="4" visiable="1" length="4000" />
			</ubf:Panel>
		</ubf:PanelGroup>
		<ubf:HiddenField name="viplevel" label="VIP等级" cell="1" length="100" />
		<input type="hidden" name="customizedPage" value="mainSelfHelp"/>
		<input type="hidden" name="formjsp" value="formSelfHelp"/>
		<input type="hidden" name="dataResource" value="自助服务"/>
	</ubf:Panel>
</ubf:PanelGroup>
