<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var $ctx="${ctx}";
</script>
<script type="text/javascript" language="javascript" src="${ctx}/common/javascript/date/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/Iterator.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/List.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/Map.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/Util.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/Url.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/Message.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/DataTransfer.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/util/KMHandler.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/model/ActionModel.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/model/TaskModel.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/model/ClientContext.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/model/KMModel.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/CompositeField/PanelGroupField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/CompositeField/PanelField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DataField/CharacterField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DataField/SelectField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DataField/CollectField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DataField/TimeField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DataField/HiddenField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DisplayField/ViewField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DisplayField/LabelField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/DisplayField/ButtonField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AssignTreeField/AssignTreeField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/RollbackListField.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/ui/ButtonPanel.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/ui/ActionPanel.js"></script>
<script type="text/javascript" src="${ctx}/ultrabpp/runtime/clientframework/ui/CommonFunctions.js"></script>
<%@ include file="/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/import.jsp"%>

<script type="text/javascript">
ClientContext.loginName = "${displayCxt.userInfo.loginName}";
ClientContext.theme = "${displayCxt.theme.name}";
ClientContext.baseID = "${displayCxt.baseID}";
ClientContext.baseSchema = "${displayCxt.baseSchema}";
ClientContext.baseSN = "<c:choose><c:when test="${displayCxt.editType=='MODIFY'}">${displayCxt.fieldMap['BASESN']}</c:when><c:otherwise></c:otherwise></c:choose>";
ClientContext.baseName = "${displayCxt.baseName}";
ClientContext.defCode = "${displayCxt.defCode}";
ClientContext.taskID = "${displayCxt.taskID}";
ClientContext.flagActive = ${displayCxt.flagActive};
ClientContext.editType = "${displayCxt.editType}";
ClientContext.formFolder = "${displayCxt.formFolder}";
if(ClientContext.editType == "NEW") ClientContext.baseStatus = "草稿";
else ClientContext.baseStatus = "${displayCxt.fieldMap['BASESTATUS']}";

ClientContext.currentTask = new TaskModel("${displayCxt.taskID}", "${displayCxt.currentTask.stepCode}");

ClientContext.layoutJSon = ${displayCxt.layoutJSON};
</script>