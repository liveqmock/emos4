<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.fulltext.common.cache.SystemContext" %>
<%@ page import="com.ultrapower.eoms.fulltext.common.constant.Constant" %>
<%@page import="com.ultrapower.eoms.fulltext.common.util.IndexCategoryUtil;"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("ctx",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>   
		
		<link href="${ctx}/fulltext/css/search.css" rel="stylesheet" type="text/css" />
		<title>高级检索</title>
		<style type="text/css">
			<!--
			#kw {
				width: 391px;
				padding: 3px 1px;
				margin: 0 6px 0 0;
				font: 16px/ 16px arial
			}
			#kw {
				vertical-align: middle
			}
			#su {
				vertical-align: middle
			}
			-->
		</style>
		<script type="text/javascript">
			function rechoose()
			{
				var cbxArr = document.getElementsByName("ctgcbx");
				var categoryId = "";
				for(var i=0;i<cbxArr.length;i++)
				{
					if(cbxArr[i].checked)
					{
						categoryId += ","+cbxArr[i].value;
					}
				}
				if(categoryId!="")
				{
					categoryId = categoryId.substring(1);
				}
				if(categoryId!="")
				{
					window.location.href = "advancedSearch.jsp?categoryId="+categoryId;
				}
			}
			
			function advancedSearch()
			{
				var cbxArr = document.getElementsByName("ctgcbx");
				var categoryId = "";
				for(var i=0;i<cbxArr.length;i++)
				{
					if(cbxArr[i].checked)
					{
						categoryId += "&comm;"+cbxArr[i].value;
					}
				}
				if(categoryId=="")
				{
					return;
				}
				else
				{
					categoryId = categoryId.substring("&comm;".length);
				}
				var conArr = document.getElementsByName("conInput");
				var conStr = "";
				for(var i=0;i<conArr.length;i++)
				{
					if(conArr[i].value!="" && conArr[i].value!=null)
					{
						conStr += "&semi;"+conArr[i].id+"&comm;"+conArr[i].value;
					}
				}
				if(conStr=="")
				{
					return;
				}
				else
				{
					conStr = conStr.substring("&semi;".length);
				}
				window.opener.document.getElementById("categoryId").value = categoryId;
				window.opener.document.getElementById("form_keyWords").value = "";
				window.opener.document.getElementById("fieldAndValue").value = conStr;
				window.opener.document.getElementById("form_page").value="1";
				window.opener.document.getElementById("form_op").value="1";
				window.opener.document.getElementById("searchLevel").value = "advanced";
				//window.parent.hidePopWin();
				window.opener.document.searchForm.submit();
				window.close();
			}
		</script>
	</head>
	<body>
		<div class="popupcontent">
			<div class="portal_title">
				<div class="portal_title_left">
					<span class="middle_norm">高级搜索</span>
					<div class="close" onclick="window.close();"
						onmouseover="this.className='close1'"
						onmouseout="this.className='close'"></div>
				</div>
			</div>
			<div class="middle_conten">
				<table width="90%" border="0" cellspacing="0" cellpadding="0">
					<%
						String categoryId = request.getParameter("categoryId");
						String[] idArr = categoryId.split(",");
						List<String> idList = new ArrayList<String>();
						for(String id:idArr)
						{
							idList.add(id);
						}
						if(idList.size()>1 || !SystemContext.isPhysicalCategory(idList.get(0)))
						{
							out.println("<tr>");
							out.println("	<td style=\"width:200px;\" align=\"right\">");
							out.println("		内容：");
							out.println("	</td>");
							out.println("	<td>");
							out.println("		<input value=\"\" name=\"conInput\" id=\""+SystemContext.getSysParameter("IndexFieldDefaultContent")+"\" type=\"text\" class=\"workSheet\" />");
							out.println("	</td>");
							out.println("</tr>");
						}
						else
						{
							Map<String,String> indexField = IndexCategoryUtil.getIndexField(idList.get(0));
							if(indexField!=null && indexField.size()>0)
							{
								Iterator<String> it = indexField.keySet().iterator();
								String fieldname = null;
								String displayname = null;
								while(it.hasNext())
								{
									fieldname = it.next();
									displayname = indexField.get(fieldname);
									out.println("<tr>");
									out.println("	<td style=\"width:200px;\" align=\"right\">");
									out.println(displayname+"：");
									out.println("	</td>");
									out.println("	<td>");
									out.println("		<input value=\"\" name=\"conInput\" id=\""+fieldname+"\" type=\"text\" class=\"workSheet\" />");
									out.println("	</td>");
									out.println("</tr>");
								}
							}
						}
					 %>
					<tr>
						<td align="right" valign="top">
							搜索范围：
						</td>

						<td>
							<% 	
								List<String> logkey = SystemContext.getAllLogicalCategoryKey();
								int rowSize = 4;//一行显示几个
								int counter = 0;
								int len = 0;
								if(logkey!=null)
									len = logkey.size();
								out.println("<table cellspacing=\"0\" cellpadding=\"0\">");
								for(int i=0;i<len;i++)
								{
									if(counter%rowSize==0)
										out.println("<tr>");
									Map<String,Object> logMap = SystemContext.getLogicalCategory(logkey.get(i));
									if(idList.contains(logkey.get(i)))
									{
										out.println("<td>");
										out.println("<input onclick=\"rechoose();\" value=\""+logkey.get(i)+"\" type=\"checkbox\" checked=\"checked\" name=\"ctgcbx\"/>"+
												logMap.get(Constant.INDEX_CATEGORY_LOGICAL_DISPLAYNAME)+"&nbsp;");
										out.println("</td>");
									}
									else
									{
										out.println("<td>");
										out.println("<input onclick=\"rechoose();\" value=\""+logkey.get(i)+"\" type=\"checkbox\" name=\"ctgcbx\"/>"+
												logMap.get(Constant.INDEX_CATEGORY_LOGICAL_DISPLAYNAME)+"&nbsp;");
										out.println("</td>");
									}
									counter++;
									if(counter%rowSize==0)
									{
										out.println("</tr>");
									}
								}
								List<String> phykey = SystemContext.getAllPhysicCategoryKey();
								len = 0;
								if(phykey!=null)
									len = phykey.size();
								for(int i=0;i<len;i++)
								{
									if(counter%rowSize==0)
									{
										out.println("<tr>");
									}
									if(idList.contains(phykey.get(i)))
									{
										out.println("<td>");
										out.println("<input onclick=\"rechoose();\" value=\""+phykey.get(i)+"\" type=\"checkbox\" checked=\"checked\" name=\"ctgcbx\"/>"+
												SystemContext.getPhysicalCategory(phykey.get(i)).getDisplayName()+"&nbsp;");
										out.println("</td>");
									}
									else
									{
										out.println("<td>");
										out.println("<input onclick=\"rechoose();\" value=\""+phykey.get(i)+"\" type=\"checkbox\" name=\"ctgcbx\"/>"+
												SystemContext.getPhysicalCategory(phykey.get(i)).getDisplayName()+"&nbsp;");
										out.println("</td>");
									}
									counter++;
									if(counter%rowSize==0)
									{
										out.println("</tr>");
									}
								}
								if(counter%rowSize!=0)
									out.println("<tr/>");
								out.println("</table>");
							%>
						</td>
					</tr>
				</table>
				<div align="center">
					<span class="bottom"><a class="button" style="padding-top:5px" href="javascript:advancedSearch();">搜&nbsp;索</a></span>
				</div>
			</div>
		</div>
	</body>
</html>
