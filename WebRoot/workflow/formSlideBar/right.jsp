<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/WEB-INF/app.tld" prefix="eoms"%>
<%@ taglib uri="/WEB-INF/attachment.tld" prefix="atta"%>
<%@ taglib uri="/WEB-INF/datagrid" prefix="dg"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
<c:when test="${userSession==null}">
<c:set var="skintype" value="blue" />
</c:when>
<c:otherwise>
<c:set var="skintype" value="${userSession.skinType}" />
</c:otherwise>
</c:choose>

<fmt:setBundle basename="i18n.Messages" var="i18Bundle"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/plugin/jquery/jquery.jsp"%>
	<head>
		<link rel="stylesheet" href="${ctx }/workflow/formSlideBar/css/right.css" />
		<script src="${ctx }/common/javascript/main.js"></script>
<script>
function changediv(id_num, id,n)
{
		for(var i=1;i<=n;i++)
		{
			var divName=document.getElementById("div1_" + id_num + i);
			var divObj=document.getElementById("div" + id_num + i);
			if(i == id)
			{
				divName.className='on';
				divObj.style.display = '';
			}
			else
			{
				divName.className='off';
				divObj.style.display = 'none';
			}
		}
 
}

function hideBar()
{
	document.getElementById('hideBtn').style.display = '';
	document.getElementById('slide1').style.display = 'none';
	document.getElementById('slide2').style.display = 'none';
	document.getElementById('slide3').style.display = 'none';
	document.getElementById('slide4').style.display = 'none';
	document.getElementById('slide5').style.display = 'none';
	document.getElementById('slide6').style.display = 'none';
	parent.hideSlidBar();
}

function showBar()
{
	document.getElementById('hideBtn').style.display = 'none';
	document.getElementById('slide1').style.display = '';
	document.getElementById('slide2').style.display = '';
	document.getElementById('slide3').style.display = '';
	document.getElementById('slide4').style.display = '';
	document.getElementById('slide5').style.display = '';
	document.getElementById('slide6').style.display = '';
	parent.reShowSlidBar();
}
// 展示案例详情
function enterRepository(pid) {
	window.open('${ctx}/ultrarepository/editRepository.action?repository.pid='+pid,pid,"Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
}
// 显示更多案例
function showRepositoryList() {
	window.open('${ctx}/ultrarepository/repositoryList.action','list',"Location=no,Toolbar=no,Resizable=yes,scrollbars=no");
}
// 取得网络分类
function showDt(input_id,input_name){
   	showModalDialog('${ctx}/common/tools/selectRepositorySortTree.jsp?sortType=6&input_name='+input_name+"&input_id="+input_id+"&isRadio=0&isAllPath=1",window,'help:no;center:true;scroll:no;status:no;dialogWidth:350px;dialogHeight:450px');
}

function initData()
{
	if('${reqType}' == '0')
	{
		var schParaStr = document.getElementById('searchConditions').value;
		var schParaArr = schParaStr.split(';');
		for(var i = 0; i < schParaArr.length; i++)
		{
			var schPara = schParaArr[i];
			var webID = schPara.split('=')[0];
			var arID = schPara.split('=')[1];
			document.getElementById(webID).value = window.parent.F(arID).G();
		}
		document.getElementById('form').submit();
	}
}
</script>
<title>无标题文档</title>
</head>

<body style="overflow:hidden;" onload="initData()">
      <div id="hideBtn" style="height:28px; width:28px; display:none; background-color:#aaccff; border:#3399cc solid 1px;">
      	<a href="javascript:" onclick="showBar()" title="展开" style="text-decoration:none; margin-left:7px; line-height:26px; color:#666666;">>></a>
      </div>
  <div id="slide1" class="title_right" style="display:block;">
    <div class="title_left">
      <span class="title_bg">
        <span class="title_icon2">相关内容</span>
      </span>
      <span class="title_xieline">
      </span>
      <div align="right" style="float:righht; margin-right:5px; padding-top:2px;">
      	<a href="javascript:" title="收起" onclick="hideBar()" style="text-decoration:none; color:#666666;"><<</a>
      </div>
    </div>
  </div>
  <div id="slide2" class="serach_div_bg" style="display:block;">
    <div>
    	<form name="form" id="form" action="${ctx}/ultrarepository/searchRepositoryForSheet.action" method="post">
			<table style="width: 100%;">
				<tr>
					<td>
						<input name="" type="text" style="width: 99%; height: 16px;"/>
					</td>
					<td width="60" align="left">
						<input name="searchButton" type="submit" value="搜索"
							class="page_search_button" style="height: 22px;"
							onmouseover="this.className='page_search_button_hover'"
							onmouseout="this.className='page_search_button'" />
					</td>
				</tr>
			</table>
			<table style="width:100%;">
				<tr>
					<td width="80">网络分类：</td>
					<td>
						<input type="hidden" id="type" name="repository.type" value="6" />
						<input type="hidden" id="sorttype" name="repository.sorttype" value="${repository.sorttype }" />
						<input type="text" id="sorttypeName" name="repository.sorttypeName" class="textInput" value="${repository.sorttypeName }" readonly="readonly" onclick="showDt('sorttype', 'sorttypeName');"/>
					</td>
				</tr>
				<p id="xmlCondition">${searchContet }</p>
			</table>
		</form>
  	</div>
  </div>

  <div id="slide3" class="knowleage_bg" style="display:block;">
    <li class="left">相关知识</li>
    <li class="right" onclick="showRepositoryList();"><a href="javascript:void(0);" style="color:black;">..更多</a></li>
  </div>
  <div id="slide4" class="table_div_bg" style="height:200px; display:block;">
    <table class="tableborder" id="tab">
	  <tr>
	    <th><eoms:lable name='repository_lb_title'/><!-- 案例标题 --></th>
		<th><eoms:lable name='repository_lb_createtime'/><!-- 创建时间 --></th>
		<th><eoms:lable name='repository_lb_validnum'/><!-- 引用次数 --></th>
      </tr>
	  <c:forEach var="repository" items="${repositoryList}" end="5">
		 <tr style="cursor: pointer;" onclick="enterRepository('${repository.pid}');">
			<td width="50%">${repository.title}</td>
			<td width="25%">
				${fn:substring(repository.createtimeStr, 0, 10)}
			</td>
			<td width="25%">
				${repository.validnum}
			</td>
		</tr>
      </c:forEach>
	</table>
  </div>
   
   <div id="slide5" class="knowleage_bg" style="display:block;">
    <li class="left">相关工单</li>
    <li class="right"><a href="javascript:void(0);" style="color:black;">..更多</a></li>
   </div>
   <div id="slide6" class="table_div_bg" style="display:block;">
      <div class="tablabel">
       <div id="div1_11" class="on" onClick="changediv(1,1,2);"><span>故障处理工单</span></div>
       <div id="div1_12" class="off"  onclick="changediv(1,2,2)"><span>个人投诉工单</span></div>
     </div>
     <div class="tab_content" style="height:200px;">
       <div id="div11" class="info_content">
       <table class="tableborder" id="tab">
	  <tr>
	    <th>工单主题</th>
	   </tr>
	  <tr>
        <td>ID00204032-224</td>
	   </tr>
	  <tr>
	    <td>网络覆盖2009-07-30 18:42:44测试，请勿操作</td>
       </tr>
	  <tr>
        <td>ID00204032323-224</td>
	   </tr>
	  <tr>
	    <td>2009-09-24号测试工单bl-001</td>
       </tr>
	</table>
       </div>
      
      <div id="div12" class="info_content" style="display:none;">
       <table class="tableborder" id="tab">
	  <tr>
	    <th>工单主题</th>
	   </tr>
	  <tr>
        <td>ID00204032-324</td>
	   </tr>
	  <tr>
	    <td>这是一个投诉类工单的测试工单1</td>
       </tr>
	  <tr>
        <td>ID00204032323-325</td>
	   </tr>
	  <tr>
	    <td>这是一个投诉类工单的测试工单2</td>
       </tr>
	</table>
    </div>
    </div>
    
   </div>
</body>
</html>