<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>   
   	<%@ include file="/common/core/taglibs.jsp"%>
   	<title><eoms:lable name='sm_t_roleMenuCfg' /></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/common/plugin/dhtmlxtree/codebase/dhtmlxtree.css">
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxcommon.js"></script>
	<script type="text/javascript" src="${ctx}/common/plugin/dhtmlxtree/js/dhtmlxtree.js"></script>
	<script type="text/javascript">
		window.onresize = function() {
			setCenter(0,50);
		}
		window.onload = function() 
		{
			setCenter(0,50);
			openMenuTree('${theroleid}');
		}
		var tree;
		function openMenuTree(roleid)
		{
			tree=new dhtmlXTreeObject("center","100%","100%",0);
			tree.setImagePath("${ctx}/common/plugin/dhtmlxtree/codebase/imgs/csh_vista/");
			tree.enableCheckBoxes(1);
			tree.enableTreeLines(true);
			tree.setOnClickHandler(doHandler);
			tree.setXMLAutoLoading("${ctx}/common/menuTree.action?roleid="+roleid);
			tree.loadXML("${ctx}/common/menuTree.action?id=0&roleid="+roleid);
			tree.enableThreeStateCheckboxes(1);
		}
		function doHandler(key){}
		
		function delTheRoleMenu()
		{ 
			var menuids = tree.getAllChecked();
			document.getElementById('menuids').value = menuids;
			var roleids = document.getElementById('roleids').value;
			if(''==menuids)
			{
				alert("<eoms:lable name='sm_msg_chooseOpObject' />！");
				return;
			}
			if(menuids!='' && roleids!='')
			{
				if(confirm("<eoms:lable name='sm_msg_delMenuinfo' />？"))
				{
					document.roleMenuForm.submit();
				}
			}
		}
	</script>
  </head>
  <body>
  	<form action="${ctx}/roleManager/delTheRoleMenu.action" method="post" name="roleMenuForm">
  		<input type="hidden" name="menuids" id="menuids" value=""/>
  		<input type="hidden" name="roleids" id="roleids" value="${theroleid }"/>
  		<input type="hidden" name="parentid" id="parentid" value="${parentid }"/>
  	</form>
  	<div class="content">
	   <div class="title_right">
	      <div class="title_left">
	        <span class="title_bg">
	          <span class="title_icon2"><eoms:lable name='sm_t_roleMenuCfg' /></span>
	        </span>
	        <span class="title_xieline"></span>
	      </div>
	    </div>
	    <div class="page_div_bg">
			<div class="page_div_bg">
			    <div class="page_div">
			    <c:choose>
			    	<c:when test="${session.userSession.isAdmin=='1'}">
				        <eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
			  			   onmouseout="this.className='page_add_button'"  
			  			   onclick="showModalDialog('${ctx}/roleManager/theRoleMenuAdd.action?theroleid=${theroleid }&parentid=${parentid }&isRadio=1&isSelectChild=0&isSelectType=2',window,'help:no;center:true;scroll:no;status:no;dialogWidth:300px;dialogHeight:400px');"
			  			   text="com_btn_add" 
			  		    />
				        <eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
			  			   onmouseout="this.className='page_del_button'"  
			  			   onclick="delTheRoleMenu()" 
			  			   text="com_btn_delete" 
			  		    />
		  			</c:when>
		  			<c:otherwise>
		  				<c:set var="mytag" value="no" scope="page"/>
		  				<c:forTokens var="myvar" items="${session.userSession.roleId}" delims=",">
		  					<c:if test="${myvar==param.theroleid}">
		  						<c:set var="mytag" value="yes" scope="page"/>
		  					</c:if>
		  				</c:forTokens>
		  				<c:if test="${pageScope.mytag=='no'}">
		  					<eoms:operate cssclass="page_add_button" id="com_btn_add" onmouseover="this.className='page_add_button_hover'" 
				  			   onmouseout="this.className='page_add_button'"  
				  			   onclick="showModalDialog('${ctx}/roleManager/theRoleMenuAdd.action?theroleid=${theroleid }&parentid=${parentid }&isRadio=1&isSelectChild=0&isSelectType=2',window,'help:no;center:true;scroll:no;status:no;dialogWidth:300px;dialogHeight:400px');"
				  			   text="com_btn_add" 
				  		    />
					        <eoms:operate cssclass="page_del_button" id="com_btn_del" onmouseover="this.className='page_del_button_hover'" 
				  			   onmouseout="this.className='page_del_button'"  
				  			   onclick="delTheRoleMenu()" 
				  			   text="com_btn_delete" 
				  		    />
		  				</c:if>
		  			</c:otherwise>
		  		</c:choose>
			    </div>
			</div>
		</div>
	    <div id="center" ></div>
	</div>	
  </body>
</html>
