package com.ultrapower.eoms.workflow.sheet.importexcel.constants;

public class ExcelFieldConstants {
	public static final String BASESTATUS = "工单状态";
	public static final String BASECREATEDATE = "建单时间";
	public static final String BASECLOSEDATE = "关单时间";
	public static final String BASECREATORFULLNAME = "建单人";
	public static final String BASECREATORLOGINNAME = "建单人OA号";

	public static final class CBD_INCIDENT {
		public static final String BASESUMMARY = "事件标题";
		public static final String BASEFINISHDATE = "二线处理完成时间";
		public static final String CONTACTPERSON = "联系人";
		public static final String CONTACTOA = "联系人OA";
		public static final String CONTACTSTATION = "联系人工位";
		public static final String CONTACTTEL = "联系人电话";
		public static final String ICIDENTSOURCE = "事件来源";
		public static final String INCIDENTDES = "事件描述";
		public static final String OCCURRENCETIME = "事件发生时间";
		public static final String INCIDENT_URGENTDGREE = "事件紧急度";
		public static final String INCIDENT_EFFECTDGREE = "事件影响度";
		public static final String PRIORITY = "事件优先级";
		public static final String INCIDENTPHENOCLASS1 = "事件现象一级分类";
		public static final String INCIDENTPHENOCLASS2 = "事件现象二级分类";
		public static final String INCIDENTPHENOCLASS3 = "事件现象三级分类";
		public static final String INCIDENTPHENOCLASS4 = "事件现象四级分类";
		public static final String INCIDENTIDENTIFICATION = "事件标识";
		public static final String INCIDENTPROPERTIE = "事件性质";
		public static final String INCIDENTSOURCECLASS1 = "事件原因一级分类";
		public static final String INCIDENTSOURCECLASS2 = "事件原因二级分类";
		public static final String INCIDENTSOURCECLASS3 = "事件原因三级分类";
		public static final String INCIDENTSOURCECLASS4 = "事件原因四级分类";
		public static final String RECOVERYTIME = "事件恢复时间";
		public static final String SERVICEINTERRUPTIONTIME = "服务中断时间";
		public static final String INCIDENTSOLUTIONTYPE = "解决方案类型";
		public static final String INCIDENTREASON = "事件原因";
		public static final String PROCEDUREPROCESSING = "事件处理过程";
		public static final String SOLUTION = "解决方案";
		public static final String CLOSECODE = "结束代码";
		public static final String RESOLVEUSER = "事件解决人";
		public static final String RESOLVEUSEROA = "事件解决人OA号";
	}

	public static final class CDB_SERVICEREQUEST {
		public static final String BASESUMMARY = "标题";
		public static final String BASEFINISHDATE = "处理完成时间";
		public static final String REQUESTUSER = "联系人";
		public static final String REQUESTUSEROA = "联系人OA号";
		public static final String REQUESTUSERSITE = "联系人工位";
		public static final String REQUESTUSERPHONE = "联系人电话";
		public static final String DATARESOURCE = "来源";
		public static final String REQUESTDESC = "服务请求描述";
		public static final String REQUIREDEALTIME = "要求实施时间";
		public static final String SERVICE_CATEGORY1 = "服务一级分类";
		public static final String SERVICE_CATEGORY2 = "服务二级分类";
		public static final String SERVICE_CATEGORY3 = "服务三级分类";
		public static final String SERVICE_CATEGORY4 = "服务四级分类";
		public static final String DEAL_PROCESS_SOLUTION = "处理过程与解决方案";
		public static final String CLOSE_CODE = "结束代码";
		public static final String DEALUSER = "处理人";
		public static final String DEALUSEROA = "处理人OA号";
	}
}
