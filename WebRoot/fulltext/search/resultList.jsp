<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.fulltext.common.cache.SystemContext" %>
<%@ page import="com.ultrapower.eoms.fulltext.common.constant.Constant" %>
<%@ page import="com.ultrapower.eoms.fulltext.control.SearchController"%>
<%@ page import="com.ultrapower.eoms.fulltext.model.Limit"%>
<%@ page import="com.ultrapower.eoms.fulltext.model.HitDoc"%>
<%@ page import="com.ultrapower.eoms.fulltext.dao.IndexCategoryDao" %>
<%@ page import="com.ultrapower.eoms.fulltext.common.util.KeyValueObj"%>
<%@page import="com.ultrapower.eoms.fulltext.model.SearchCondition"%>
<%@page import="com.ultrapower.eoms.fulltext.model.Condition"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	request.setAttribute("ctx",basePath);
%>
<%
	request.setCharacterEncoding("UTF-8");
	String categoryIdStr = request.getParameter("categoryId"); //要查询的索引类别，如果是多个索引类别，请用“&comm;”分割各个索引类别
	String keyWords = request.getParameter("keyWords"); //查询关键字（普通查询）
	String fieldAndValue = request.getParameter("fieldAndValue"); //查询字段与关键字的组合（高级查询），格式为field1&comm;keywords1&semi;field2&comm;keywords2...
	String pageNum = "1"; //默认查询第1页
	if(request.getParameter("page")!=null)
	{
		pageNum = request.getParameter("page"); //传递参数查询第几页
	}
	String op = "1"; //默认是新建查询
	if(request.getParameter("op")!=null)
	{
		op = request.getParameter("op"); //传递查询方式：1是新建查询；2是缓存查询
	}
	String searchLevel = "plane"; //普通搜索级别
	if(request.getParameter("searchLevel")!=null)
	{
		searchLevel = request.getParameter("searchLevel"); //取得搜索级别，是高级搜索还是普通搜索
	}
	List<String> cgyIdList = new ArrayList<String>();
	List<String> phykey = null;
	if(categoryIdStr!=null)
	{
		String[] tempArr = categoryIdStr.split("&comm;");
		
		for(String id:tempArr)
		{
			cgyIdList.add(id);
		}
	}
	else //如果索引类别ID为空，则默认搜索所有类别
	{
		phykey = SystemContext.getAllPhysicCategoryKey();
		cgyIdList = phykey;
	}
	//Map<String,String> searchCondition = new HashMap<String,String>();
	SearchCondition searchCondition = new SearchCondition();
	searchCondition.setOperator(SearchCondition.Operator.AND);
	List<Condition> conLst = new ArrayList<Condition>();
	searchCondition.setConditions(conLst);
	if(keyWords!=null || fieldAndValue!=null)
	{
		if("plane".equals(searchLevel))
		{//普通搜索
			String field = SystemContext.getSysParameter("IndexFieldDefaultContent");
			//searchCondition.put(field,keyWords);
			conLst.add(new Condition(field,keyWords,false,null,true));
		}
		else if("advanced".equals(searchLevel))
		{//高级搜索
			String[] conArr = fieldAndValue.split("&semi;");
			for(String conPair:conArr)
			{
				String fieldName = conPair.split("&comm;")[0];
				String fieldValue = conPair.split("&comm;")[1];
				if(!"".equals(fieldName)&&!"".equals(fieldValue))
				{
					//searchCondition.put(fieldName,fieldValue);
					conLst.add(new Condition(fieldName,fieldValue,false,null,true));
				}
			}
		}
	}
	Limit limit = new Limit();
	SearchController sc = new SearchController();
	if(cgyIdList!=null && cgyIdList.size()>0 && pageNum!=null && pageNum.matches("[1-9][0-9]*"))
	{
		limit.setPageSize(Integer.parseInt(SystemContext.getSysParameter("PageSize")));
		limit.setCurrentPage(Integer.parseInt(pageNum));
		if("1".equals(op))
		{
			limit = sc.search(cgyIdList,searchCondition,limit,session,true); //新建查询
		}
		else if("2".equals(op))
		{
			limit = sc.search(cgyIdList,searchCondition,limit,session,false); //缓存查询
		}
	}
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>检索结果</title>
		<link type="text/css" rel="stylesheet" href="${ctx}/fulltext/css/search.css" />
		<script type="text/javascript" src="${ctx}/common/plugin/jquery/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/fulltext/js/common.js"></script>
		<script type="text/javascript" src="${ctx}/fulltext/js/subModal.js"></script>
		<script type="text/javascript" src="${ctx}/fulltext/js/search.js"></script>
		<script type="text/javascript">
			function onRstTb(source)
			{
				source.style.background = "#BEBEBE";
			}
			function outRstTb(source)
			{
				source.style.background = "white";
			}
		</script>
	</head>
	<body onclick="document.getElementById('lstCondition').style.display='none';">
		<form name="searchForm" id="searchForm" action="${ctx}/fulltext/search/resultList.jsp" method="post" style="display:none">
			<input type="hidden" name="categoryId" id="form_categoryId" value="<%=(categoryIdStr==null?"":categoryIdStr)%>"/>
			<input type="hidden" name="keyWords" id="form_keyWords" value=""/>
			<input type="hidden" name="fieldAndValue" id="form_fieldAndValue" value="<%=(fieldAndValue==null?"":fieldAndValue)%>"/>
			<input type="hidden" name="page" id="form_page" value=""/>
			<input type="hidden" name="op" id="form_op" value=""/>
			<input type="hidden" name="searchLevel" id="searchLevel" value="<%=searchLevel%>"/>
		</form>
		<input type="hidden" id="advancedCategoryId" value="<%=(categoryIdStr==null?"":categoryIdStr)%>"/>
		<div class="content2">
			<div></div>
			<div class="search_content2">
				<table width="500" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<%
								out.println("<span id=\"previous_cas\" onclick=\"switchCtgSpan('pre');\" class=\"category_name_more\" style=\"display:none;position:relative;float:left\">&lt;&lt;</span>");
								out.println("<span style=\"position:relative;float:left\"><span class=\"category_name\" id=\"_allKeyLink\" onclick=\"setCategory(document.getElementById('allcategoryId').value,this);\">全部</span></span>");
								out.println("<span id=\"ctgSpan_1\" style=\"display:block;position:relative;float:left\">");
								int categoryCounter = 0;
								int categoryShowCount = 5; //一次展示索引类别的数目
								int categorySpanCounter = 1;
								int selectedSpanIndex = 1;
								StringBuffer allkey = new StringBuffer();
								String indexDisplayWay = SystemContext.getSysParameter("IndexDisplayWay");
								if(indexDisplayWay==null || "".equals(indexDisplayWay) || (!"1".equals(indexDisplayWay) && !"2".equals(indexDisplayWay)))
								{
									indexDisplayWay = "1";
								}
								boolean selectedFlag = false;
								IndexCategoryDao dao = new IndexCategoryDao();
							 	String cShow = null;
							 	if("2".equals(indexDisplayWay))//层级展示
							 	{
								 	if(session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE)!=null && session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE) instanceof String)
									{
										cShow = (String)session.getAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE);
									}
									else
									{
										cShow = dao.getCategoryForDisplay_topLevel();
										if(cShow!=null)
											session.setAttribute(Constant.FTR_SESSION_CATEGORYNAME_CACHE,cShow);
									}
								 	
								 	if(!(cShow instanceof String))
								 	{
								 		cShow = dao.getCategoryForDisplay_topLevel();
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
								 		String selectedText = request.getParameter("_selectedMenuText");
								 		String topLevelId = request.getParameter("_topLevelMenuId");
								 		if(selectedText==null)
								 			selectedText = "";
								 		if(topLevelId==null)
								 			topLevelId = "";
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
									 				if(categoryIdStr!=null && categoryIdStr.equals(caAtt[0]))
									 				{
									 					out.println("<span id=\"selectText"+i+"\" class=\"category_name_selected\" onclick=\"setCategory('"+caAtt[0]+"',this);\">"+caAtt[1]+"</span>");
									 					selectedFlag = true;
										 				selectedSpanIndex = categorySpanCounter;
									 				}
									 				else if(topLevelId.equals(caAtt[0]))
									 				{
									 					out.println("<span id=\"selectText"+i+"\" class=\"category_name_selected\" onclick=\"setCategory('"+caAtt[0]+"',this);\">"+selectedText+"</span>");
									 					selectedFlag = true;
										 				selectedSpanIndex = categorySpanCounter;
									 				}
									 				else
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
									}
									else
									{
										KeyValueObj tempC;
										for(int i=0;i<len;i++)
										{
											tempC = clst.get(i);
											if(SystemContext.getPhysicalCategory(tempC.getKey())!=null || SystemContext.getLogicalCategory(tempC.getKey())!=null)
											{
												if(categoryCounter!=0 && categoryCounter%categoryShowCount==0)
									 			{
									 				categorySpanCounter +=1;
									 				out.println("</span>");
									 				out.println("<span id=\"ctgSpan_"+categorySpanCounter+"\" style=\"display:none;position:relative;float:left\">");
									 			}
									 			categoryCounter += 1;
												allkey.append("&comm;"+tempC.getKey());
												if(categoryIdStr.equals(tempC.getKey()))
												{
													out.println("<span class=\"category_name_selected\" onclick=\"javascript:setCategory('"+tempC.getKey()+"',this);\">"+tempC.getValue()+"</span>");
													selectedFlag = true;
													selectedSpanIndex = categorySpanCounter;
												}
												else
													out.println("<span class=\"category_name\" onclick=\"javascript:setCategory('"+tempC.getKey()+"',this);\">"+tempC.getValue()+"</span>");
											}
										}
									}
							 	}
							 	out.println("</span>");
							 	if(categoryCounter>categoryShowCount)
							 	{
							 		out.println("<span id=\"next_cas\" onclick=\"switchCtgSpan('next');\" class=\"category_name_more\" style=\"display:block;position:relative;float:left;\">&gt;&gt;</span>");
							 		out.println("<input type=\"hidden\" id=\"allCtgSpan\" value=\""+categorySpanCounter+"\"/>");
							 		out.println("<input type=\"hidden\" id=\"curCtgSpan\" value=\""+selectedSpanIndex+"\"/>");
							 	}
							 	if(allkey.length()>0)
								{
									out.println("<input type=\"hidden\" id=\"allcategoryId\" value=\""+allkey.substring("&comm;".length())+"\"/>");
									if(!selectedFlag)
									{
										out.println("<script type=\"text/javascript\">document.getElementById(\"_allKeyLink\").className='category_name_selected';</script>");
									}
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
								<input type="text" id="txtKeyword" maxlength="100" value="<%=(keyWords==null?"":keyWords)%>"
								 	onkeydown="checkSubmit(event,2);" onfocus="getHisKWD('${ctx}');" onblur="clearTimer();" onclick="event.cancelBubble=true;"/>
								<a id="cmbCondition"></a>
								<a id="btnSearch" href="javascript:startSearch(1,1);"></a>
								<a id="btnAdvancedSearch" onclick="advancedSearch('${ctx}');"><span class="category_name">高级</span></a>
							</div>
							<%
								}
							%>
							<div id="lstCondition" class="lstCondition2" style="display:none">
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="point" align="right">
				<%
					if(limit!=null){
						String suggestionKWD = limit.getSuggestionKeyword();
						if(suggestionKWD!=null&&!"".equals(suggestionKWD)){
							out.print("<span style=\"position:relative;float:left;left:55px;\">");
							out.print("您要找的是不是：<a href=\"javascript:;\" onclick=\"suggestionSearch('");
							out.print(suggestionKWD);
							out.print("')\"><font color=\"red\"><b>");
							out.print(suggestionKWD);
							out.print("</b></font></a>");
							out.print("</span>");
						}
					}
					if(allkey.length()>0)
					{
						if("plane".equals(searchLevel))
						{
							out.print("<span>搜索<b>&nbsp;&nbsp;"+(keyWords==null?"":keyWords)+"&nbsp;&nbsp;</b>");
							out.print("获得约<b>&nbsp;&nbsp;"+limit.getTotalRecord()+"&nbsp;&nbsp;</b>条结果。");
						}
						else if("advanced".equals(searchLevel))
						{
							out.println("<span>搜索条件&nbsp;&nbsp;");
							Iterator<String> it = (searchCondition.getConditionForHLT()).keySet().iterator();
							StringBuffer conStr = new StringBuffer();
							while(it.hasNext())
							{
								conStr.append(searchCondition.getHLTConditionValue(it.next()));
								conStr.append("&nbsp;&nbsp;");
							}
							out.println("<b>"+conStr.toString()+"</b>");
							out.println("获得约<b>"+limit.getTotalRecord()+"</b>条结果。");
							out.println("</span>");
						}
					}
				%>
				
			</div>
			<div class="page_conten">
				<!-- 推广链接表格开始 -->
				<!-- 推广链接表格结束 -->
				<!--搜索结果列表开始-->
				<%
					if(allkey.length()>0)
					{
						if(limit.getHitDoc()==null || limit.getHitDoc().size()==0)
						{
								out.println("<table width=\"70%\" cellpadding=\"0\" cellspacing=\"0\" class=\"result\">");
								out.println("	<tr>");
								out.println("		<td class=\"f\">");
								out.println("			<font size=\"3\">对不起，没有检索到相关结果！</font>");
								out.println("			<br/>");
								out.println("		</td>");
								out.println("	</tr>");
								out.println("</table>");
						}
						else
						{
							List<HitDoc> hitDoc = limit.getHitDoc();
							HitDoc theHit;
							for(int i=0;i<hitDoc.size();i++)
							{
								theHit = hitDoc.get(i);
								out.println("<table width=\"70%\" cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"result\">");
								out.println("	<tr bgcolor=\"white\">");
								out.println("		<td class=\"f\">");
								out.println("           <a href=\"javascript:openDoc('"+theHit.getHref().replace("\\","\\\\")+"');\"><font size=\"3\">"+theHit.getTitle()+"</font></a>");
								out.println("			<br/>");
								if(theHit.getContent()!=null)
									out.println(theHit.getContent());
								if(theHit.getMustItem()!=null)
									out.println(theHit.getMustItem());
								out.println("		</td>");
								out.println("	</tr>");
								out.println("</table>");
							}
						}
					}
					else
					{
						out.println("<table width=\"70%\" cellpadding=\"0\" cellspacing=\"0\" class=\"result\">");
						out.println("<tr><td style=\"color:red\">索引系统尚未初始化！</td><tr>");
						out.println("</table>");
					}
				 %>
				<!-- 分页开始 -->
				<%
					if(SystemContext.getSysParameter("PageSize")!=null)
					{
						if(limit.getTotalRecord()>Integer.parseInt(SystemContext.getSysParameter("PageSize")))
						{
							out.println("<div class=\"flickr\">");
							if(limit.getCurrentPage()==1)
							{
								out.println("<span class=\"disabled\">上一页</span>");
							}
							else
							{
								out.println("<a href=\"javascript:startSearch("+(limit.getCurrentPage()-1)+",2);\">上一页</a>");
							}
							int pagesForShow = 10;
							int startPage = 1;
							if(limit.getCurrentPage()>=pagesForShow && limit.getTotalPage()>pagesForShow)
							{
								startPage = limit.getCurrentPage()-limit.getCurrentPage()%pagesForShow-1;
							}
							for(int i=startPage;i<=limit.getTotalPage();i++)
							{
								
								if(limit.getCurrentPage()==i)
								{
									out.println("<span  class=\"current\">"+i+"</span>");
								}
								else
								{
									out.println("<a href=\"javascript:startSearch("+i+",2);\">"+i+"</a>");
								}
								if(i!=startPage+1 && i%pagesForShow==0)
								{
									break;
								}
							}
							if(limit.getCurrentPage()==limit.getTotalPage())
							{
								out.println("<span class=\"disabled\">下一页</span>");
							}
							else
							{
								out.println("<a href=\"javascript:startSearch("+(limit.getCurrentPage()+1)+",2);\">下一页</a>");
							}
							out.println("</div>");
						}
					}
				%>
				<!-- 分页结束 -->
				<!-- 相关搜索开始 -->
				<%
				if(limit!=null){
					List<String> relaKwd = limit.getRelativeKeyword();
					if(relaKwd!=null&&relaKwd.size()>0){
						out.println("<div class=\"relakwd\">");
						out.println("	<table border=\"0\">");
						int relaSize = relaKwd.size();
						int relaRSize = 5;
						for(int i=0;i<relaSize;i++){
							if(i==0||i%relaRSize==0){
								out.println("<tr algn=\"left\">");
								if(i==0){
									out.println("<td><b>相关搜索</b></td>");
								}else{
									out.println("<td></td>");
								}	
							}
							out.println("		<td style=\"padding-right:20px;\"><a class=\"clearClass\" href=\"javascript:;\" onclick=\"suggestionSearch('"+relaKwd.get(i)+"')\">"+relaKwd.get(i)+"</a></td>");
							if((i!=0&&(i+1)%relaRSize==0)||i+1>=relaSize)
								out.println("</tr>");
							
						}
						out.println("	</table>");
						out.println("</div>");
					}
				}
				%>
				<!-- 相关搜索结束 -->
				<div class="point2" align="left">
					<%
						if(allkey.length()>0)
						{
					 %>
					<div class="point2Img"></div>
					<span><input type="text" id="subInput" maxlength="100" class="workSheet" value="<%=(keyWords==null?"":keyWords)%>" 
							onkeypress="checkSubmit(event,2,2);"/>
					<input value="搜&nbsp;索" id="su2" type="button" 
						onclick="document.getElementById('txtKeyword').value=document.getElementById('subInput').value;startSearch(1,1);" class="port1" /> 
					</span>
					<%
						}
					 %>
				</div>
			</div>
			<div class="footer2" align="center">
				<div class="hr1_line2"></div>
				<p style="margin-top: 5px;">版权归神州泰岳所有</p>
			</div>
		</div>
		<%--次数的JS设置当前选中的索引类别所在的页显示，而其他页隐藏--%>
		<script type="text/javascript">
		 	setSelectedSpan();
		 	if("<%=(request.getParameter("_topLevelMenuId")!=null && request.getParameter("_selectedMenuText")!=null)%>"=="true")
		 	recordIdAndName("<%=request.getParameter("_topLevelMenuId")%>","<%=request.getParameter("_selectedMenuText")%>");
		</script>
	</body>
</html>
