<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ultrapower.eoms.fulltext.common.cache.SystemContext"%>
<%@ page import="com.ultrapower.eoms.fulltext.common.constant.Constant"%>
<%@ page import="com.ultrapower.eoms.fulltext.dao.IndexCategoryDao" %>
<%@ page import="com.ultrapower.eoms.fulltext.common.util.KeyValueObj;"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	request.setAttribute("ctx",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link type="text/css" rel="stylesheet" href="${ctx}/fulltext/css/search.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/fulltext/js/common.js"></script>
		<script type="text/javascript" src="${ctx}/fulltext/js/subModal.js"></script>
		<script type="text/javascript" src="${ctx}/fulltext/js/search.js"></script>
		<title>EOMS全文检索</title>
	</head>
	
	<body onclick="document.getElementById('lstCondition').style.display='none';">
		<form name="searchForm" id="searchForm" action="${ctx}/fulltext/search/resultList.jsp" method="post" style="display:none">
			<input type="hidden" name="categoryId" id="form_categoryId" value=""/>
			<input type="hidden" name="keyWords" id="form_keyWords" value=""/>
			<input type="hidden" name="fieldAndValue" id="form_fieldAndValue" value=""/>
			<input type="hidden" name="page" id="form_page" value=""/>
			<input type="hidden" name="op" id="form_op" value=""/>
			<input type="hidden" name="searchLevel" id="searchLevel" value=""/>
		</form>
		<!--  
		<div style="position:relative;float:right;z-index:1;">
			<a href="${ctx}/fulltext/maintain/maintain.jsp"><font size="1" color="black">维护</font></a>
		</div>
		-->
		<div class="content">
			<div class="search"></div>
			<div class="search_content">
				<table width="500" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<%
								out.println("<span id=\"previous_cas\" onclick=\"switchCtgSpan('pre');\" class=\"category_name_more\" style=\"display:none;position:relative;float:left\">&lt;&lt;</span>");
								out.println("<span style=\"position:relative;float:left\"><span class=\"category_name_selected\" id=\"_allKeyLink\" onclick=\"setCategory(document.getElementById('allcategoryId').value,this);\">全部</span></span>");
								out.println("<span id=\"ctgSpan_1\" style=\"display:block;position:relative;float:left\">");
								int categoryCounter = 0;
								int categoryShowCount = 6; //一次展示索引类别的数目
								int categorySpanCounter = 1;
								StringBuffer allkey = new StringBuffer();
								String indexDisplayWay = SystemContext.getSysParameter("IndexDisplayWay");
								if(indexDisplayWay==null || "".equals(indexDisplayWay) || (!"1".equals(indexDisplayWay) && !"2".equals(indexDisplayWay)))
								{
									indexDisplayWay = "1";
								}
								IndexCategoryDao dao = new IndexCategoryDao();
							 	//List<List<KeyValueObj>> cShow = null;
							 	String cShow = null;
							 	if("2".equals(indexDisplayWay))//层级展示
							 	{
							 		
								 	//if(session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE)!=null)
								 	if(session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE)!=null && session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE) instanceof String )
									{
										//cShow = (List<List<KeyValueObj>>)session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE);
										cShow = (String)session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE);
									}
									else
									{
										cShow = dao.getCategoryForDisplay_topLevel();
										//if(cShow!=null && cShow.size()>1)
										if(cShow!=null)
											session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,cShow);
									}
								 	/*
								 	int len = 0;
								 	if(cShow!=null)
								 		len = cShow.size();
								 	if(len>0 && !(cShow.get(0) instanceof List))
								 	{
								 		session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,null);
								 		cShow = dao.getCategoryForDisplay_topLevel();
										if(cShow!=null && cShow.size()>1)
											session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,cShow);
								 	}
								 	else
								 	{
								 		List<KeyValueObj> clst;
									 	for(int i=0;i<len;i++)
									 	{
									 		clst = cShow.get(i);
									 		if(clst!=null && clst.size()>0)
									 		{
									 			if(SystemContext.getLogicalCategory(clst.get(0).getKey())!=null ||
									 				SystemContext.getPhysicalCategory(clst.get(0).getKey())!=null)
									 			{
									 				if(categoryCounter!=0 && categoryCounter%categoryShowCount==0)
									 				{
									 					categorySpanCounter +=1;
									 					out.println("</span>");
									 					out.println("<span id=\"ctgSpan_"+categorySpanCounter+"\" style=\"display:none;position:relative;float:left\">");
									 				}
									 				categoryCounter += 1;
									 				out.println("<span id=\"selectText"+i+"\" class=\"category_name\" onclick=\"setCategory('"+clst.get(0).getKey()+"',this);\">"+clst.get(0).getValue()+"</span>");
									 				allkey.append("&comm;"+clst.get(0).getKey());
									 				if(clst.size()>1)
									 					out.println("<span id=\"selectImg"+i+"\" style=\"cursor:pointer;margin-left:-10px;\" onclick=\"turnit('SelectArea"+i+"','selectImg"+i+"',this)\"><img src=\""+basePath+"/fulltext/images/dropSelect.png\"/></span>");
									 			}
									 		}
									 	}
								 	}
								 	*/
								 	if(!(cShow instanceof String))
								 	{
								 		cShow = dao.getCategoryForDisplay_topLevel();
										//if(cShow!=null && cShow.size()>1)
										if(cShow!=null)
											session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,cShow);
								 		else
								 			session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,null);
								 	}
								 	if(cShow!=null)
								 	{
								 		String caArr[] = cShow.split("&semi;");
								 		String curCa;
								 		String caAtt[];
								 		for(int i=0;i<caArr.length;i++)
								 		{
								 			curCa = caArr[i];
								 			caAtt = curCa.split("&comm;");
								 			if(caAtt.length==3)
								 			{
								 				if(SystemContext.getLogicalCategory(caAtt[0])!=null ||
									 				SystemContext.getPhysicalCategory(caAtt[0])!=null)
									 			{
									 				if(categoryCounter!=0 && categoryCounter%categoryShowCount==0)
									 				{
									 					categorySpanCounter +=1;
									 					out.println("</span>");
									 					out.println("<span id=\"ctgSpan_"+categorySpanCounter+"\" style=\"display:none;position:relative;float:left\">");
									 				}
									 				categoryCounter += 1;
									 				out.println("<span id=\"selectText"+i+"\" class=\"category_name\" onclick=\"setCategory('"+caAtt[0]+"',this);\">"+caAtt[1]+"</span>");
									 				allkey.append("&comm;"+caAtt[0]);
									 				if("0".equals(caAtt[2]))
									 					out.println("<span id=\"selectImg"+i+"\" style=\"cursor:pointer;margin-left:-10px;\" onclick=\"showChildCategory('"+caAtt[0]+"','"+basePath+"','1','selectImg"+i+"',this,'selectText"+i+"')\"><img src=\""+basePath+"/fulltext/images/dropSelect.png\"/></span>");
									 			}
								 			}
								 		}
								 	}
							 	}
							 	else if("1".equals(indexDisplayWay))//平铺展示
							 	{
							 		List<KeyValueObj> clst;
									if(session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE)!=null)
									{
										clst = (List<KeyValueObj>)session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE);
									}
									else
									{
										clst = dao.getCategoryForDisplay_flat();
										if(clst!=null && clst.size()>0)
											session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,clst);
									}
									int len = 0;
									if(clst!=null)
										len = clst.size();
									if(len>0 && !(clst.get(0) instanceof KeyValueObj))
									{
										session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,null);
										clst = dao.getCategoryForDisplay_flat();
										if(clst!=null && clst.size()>0)
											session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,clst);
									}
									else
									{
										KeyValueObj tempC;
										for(int i=0;i<len;i++)
										{
											tempC = clst.get(i);
											if(SystemContext.getPhysicalCategory(tempC.getKey())!=null 
												|| SystemContext.getLogicalCategory(tempC.getKey())!=null)
											{
												allkey.append("&comm;"+tempC.getKey());
									 			if(categoryCounter!=0 && categoryCounter%categoryShowCount==0)
									 			{
									 				categorySpanCounter +=1;
									 				out.println("</span>");
									 				out.println("<span id=\"ctgSpan_"+categorySpanCounter+"\" style=\"display:none;position:relative;float:left\">");
									 			}
									 			categoryCounter += 1;
												out.println("<span class=\"category_name\" onclick=\"setCategory('"+tempC.getKey()+"',this);\">"+tempC.getValue()+"</span>");
											}
										}
									}
							 	}
							 	out.println("</span>");
							 	if(categoryCounter>categoryShowCount)
							 	{
							 		out.println("<span id=\"next_cas\" onclick=\"switchCtgSpan('next');\" class=\"category_name_more\" style=\"display:block;position:relative;float:left;\">&gt;&gt;</span>");
							 		out.println("<input type=\"hidden\" id=\"allCtgSpan\" value=\""+categorySpanCounter+"\"/>");
							 		out.println("<input type=\"hidden\" id=\"curCtgSpan\" value=\"1\"/>");
							 	}
							 	if(allkey.length()>0)
								{
									out.println("<input type=\"hidden\" id=\"allcategoryId\" value=\""+allkey.substring("&comm;".length())+"\"/>");
								}
								else
								{
									out.println("<script type=\"text/javascript\">document.getElementById(\"_allKeyLink\").style.display='none';</script>");
								}
							 %>
						</td>
					</tr>
					<tr>
						<td>
							<%
							if(allkey.length()>0)
							{
							%>
							<div class="search_portcont">
								<input type="text" id="txtKeyword" maxlength="100" onkeydown="checkSubmit(event,1);" onfocus="getHisKWD('${ctx}');" onblur="clearTimer();" onclick="event.cancelBubble=true;"/>
								<a id="cmbCondition"></a>
								<a id="btnSearch" href="javascript:planeSearch();"></a>
								<a id="btnAdvancedSearch" onclick="advancedSearch('${ctx}');"><span class="category_name">高级</span></a>
							</div>
							<%
							}
							else
							{
								out.println("<div style=\"color:red;\">索引系统尚未初始化！</div>");
								out.println("<hr>");
							}
							%>
							<div id="lstCondition" class="lstCondition" style='display:none;'>
							</div>
						</td>
					</tr>
				</table>
				<div class="footer" align="center" style="float: left">
					<div class="hr1_line"></div>
					<p style="margin-top: 5px;">
						版权归神州泰岳所有
					</p>
				</div>
			</div>
		</div>
		<%
			/*
			if("2".equals(indexDisplayWay))
			{
				int len = 0;
			 	if(cShow!=null)
			 		len = cShow.size();
			 	if(len>0 && (cShow.get(0) instanceof List))
			 	{
			 		List<KeyValueObj> clst;
					for(int i=0;i<len;i++)
				 	{
				 		clst = cShow.get(i);
				 		if(clst!=null && clst.size()>0)
				 		{
				 			if(clst.size()>1)
				 			{
				 				out.println("<div id=\"SelectArea"+i+"\" class=\"selectDiv\" style=\"display:none\" onMouseOver=\"turnit('SelectArea"+i+"','selectImg"+i+"',this)\" onMouseOut=\"outSelectDiv('SelectArea"+i+"')\">");
				 				out.println("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
				 				for(int j=0;j<clst.size();j++)
				 				{
				 					out.print("<tr width=\"100%\">");
					 				out.print("<td id=\"t"+i+j+"\" onMouseOver=\"over('t"+i+j+"')\" onMouseOut =\"out('t"+i+j+"')\" onclick=\"sele('SelectArea"+i+"','t"+i+j+"','selectText"+i+"','"+clst.get(j).getKey()+"')\" class=\"selectOptionTrNoSeled\">");
					 				out.print(clst.get(j).getValue());
					 				out.print("</td>");
					 				out.print("<td class=\"selectOptionTrNoSeled_rightSel\"><img src=\""+basePath+"/fulltext/images/rightSelect.png\"/></td>");
					 				out.println("</tr>");
				 				}
				 				out.println("</table>");
				 				out.println("</div>");
				 			}
				 		}
				 	}
			 	}
			}
			*/
		 %>
	</body>
</html>
