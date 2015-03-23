<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
   	<%@ include file="/common/core/taglibs.jsp"%>
	<%@ include file="/common/plugin/dictTree/jsp/dict.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
	<style>
	.div_dict_Css{
	z-index: 1000;
	position:absolute;
	border: 1px solid #444;
	width: 300px;
	height:	300px;
	overflow: auto;
}


.divClass{
	z-index:1000;
	background-color:red;
	position:absolute;
	border:2px;
	visibility:visible;
	background:#eee;
	border-color:blue;
	border-width:2px;
}
	</style>
</head>
<body style="background-color: white;">
		<table width="100%" >
			<tr>
				<td>
					单选：
				</td>
				<td>
					<eoms:dictTree name="name" dicttype="AuditLog" style="align:center"></eoms:dictTree>
				</td>
				<td>
					单选(fullname)：
				</td>
				<td>
					<eoms:dictTree name="name_1" dicttype="AuditLog" valueOf="fullname" style="align:center"></eoms:dictTree>
				</td>
				<td>
					单选(默认值)：
				</td>
				<td>
					<eoms:dictTree name="name_1_1" dicttype="AuditLog" value="2" style="align:center"></eoms:dictTree>
				</td>
				<td>
					单选(默认值)：
				</td>
				<td>
					<eoms:dictTree name="name_1_2" dicttype="AuditLog" valueOf="fullname" value="流程管理" style="align:center"></eoms:dictTree>
				</td>
			</tr>
			<tr>
				<td>
					多选：
				</td>
				<td>
					<eoms:dictTree name="name2" dicttype="AuditLog"	style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
				<td>
					多选(fullname)：
				</td>
				<td>
					<eoms:dictTree name="name_2" dicttype="AuditLog" valueOf="fullname"	style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
				<td>
					多选(默认值)：
				</td>
				<td>
					<eoms:dictTree name="name_2_1" dicttype="AuditLog" value="1,2,3" style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
				<td>
					多选(默认值)：
				</td>
				<td>
					<eoms:dictTree name="name_2_2" dicttype="AuditLog" valueOf="fullname" value="系统管理,流程管理,值班计划" style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
			</tr>
			<tr>
				<td>
					部门:
				</td>
				<td>
					<eoms:dictTree name="serverRequest" sqlName="SQL_DICT_GROUPQUERY.query" cssClass="width:200px;" valueOf="fullname"
						style="align:center" checkboxes="1"></eoms:dictTree>
				</td>
				<td>
					变更分类：
				</td>
				<td>
					<eoms:dictTree name="chgSort" dicttype="chgSort" checkLevelFlag="-2"
						style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
				<td>
					业务系统：
				</td>
				<td>
					<eoms:dictTree name="cdb_system" dicttype="cdb_system" checkLevelFlag="-2"
						style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
			</tr>
			<tr>
				<td>
					SQL(事后二级分类)：
				</td>
				<td>
					<eoms:dictTree name="afterIncidentClass2"  sqlName="SQL_SM_DictTreeQueryList.incident_afterIncidentClass2" pflied="afterIncidentClass1" checkboxes="2"></eoms:dictTree>
				</td>
				<td>
					SQL(事后三级分类)：
				</td>
				<td>
					<eoms:dictTree name="afterIncidentClass3" sqlName="SQL_SM_DictTreeQueryList.incident_afterIncidentClass3" pflied="afterIncidentClass2" style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
				<td>
					SQL(事后四级分类)：
				</td>
				<td>
					<eoms:dictTree name="afterIncidentClass4" sqlName="SQL_SM_DictTreeQueryList.incident_afterIncidentClass4" pflied="afterIncidentClass3" style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
			</tr>
			<tr>
				<td>
					SQL(所属机构)：
				</td>
				<td>
					<eoms:dictTree name="belongOrg" sqlName="SQL_SM_DictTreeQueryList.incident_belongOrg" style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
				<td>
					SQL(工单状态)：
				</td>
				<td>
					<eoms:dictTree name="basestatus" sqlName="SQL_SM_DictTreeQueryList.incident_basestatus" style="align:center" checkboxes="2"></eoms:dictTree>
				</td>
				<td>SQL(故障原因定位)：</td>
				<td style='width:22%'>
					<eoms:dictTree name="sourceLocal" sqlName="SQL_SM_DictTreeQueryList.incident_Source" style="align:center" checkboxes="2"></eoms:dictTree>
				</td> 
			</tr>
		</table>
	</body>
</html>