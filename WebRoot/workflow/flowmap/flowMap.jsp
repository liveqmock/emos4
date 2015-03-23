<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.ultrapower.eoms.workflow.design.control.DealProcessManager"%>
<%@page import="com.ultrapower.eoms.workflow.design.model.*"%>
<%@page import="com.ultrapower.eoms.workflow.design.control.FlowMapConfig"%>
<%@page import="com.ultrapower.eoms.workflow.flowmap.model.BaseModel"%>
<%@page import="com.ultrapower.eoms.workflow.flowmap.model.ModifyFieldView"%>
<%@page import="com.ultrapower.eoms.workflow.flowmap.control.BaseManage"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.QueryAdapter"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataTable"%>
<%@page import="com.ultrapower.eoms.common.core.component.data.DataRow"%>
<%
String baseid = request.getParameter("baseid");
String baseschema = request.getParameter("baseschema");
String maptype = request.getParameter("type");
String tplid = request.getParameter("tplid");
String flowid = request.getParameter("flowid");
String strisArchive = request.getParameter("isarchive");
String type = request.getParameter("ft");
String bp = request.getParameter("bp");

if(flowid!=null&&flowid.length()>0)
{
	QueryAdapter con  = new QueryAdapter();
	String[] flowidAndEntryid = flowid.split("_");
	String sql = "select defname from bs_t_wf_entry t left join bs_t_wf_dealprocess p on t.id=p.entryid where  p.parentflowid='"+flowidAndEntryid[0]+"' and p.entryid='"+flowidAndEntryid[1]+"'";
	DataTable rs = con.executeQuery(sql, null, 0);
	DataRow dRow = rs.getDataRow(0);
	tplid = dRow.getString("defname");
}else flowid="";
if (maptype.equals("tpl"))
{
    String m_par = "mode=map" + "&baseid=" + baseid + "&baseschema=" + baseschema+"&flowid="+flowid+"&tplid="+tplid+"&modeltype=EOMS";
	response.sendRedirect("../design/v3/vml/wfDesigner.jsp?" + m_par);
	return;
}

response.sendRedirect("../design/v4/WFDesigner.jsp?mode=map&baseid="+baseid+"&baseschema="+baseschema+"&type=free&tplid=&modeltype=EOMS");
