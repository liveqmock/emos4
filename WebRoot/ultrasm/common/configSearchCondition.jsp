<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<%@ include file="/common/core/taglibs.jsp"%>
		<title></title>

		<script language="javascript">
    window.onload = function(){
	    setCenter(0,61); 
	}

	window.onresize = function(){
		setCenter(0,61);
	}
	function formSubmit(){
	
		var conditions = "";
		var selectSize = 0;
	  	var checkboxObjs = document.getElementsByName("conditionCB");
	  	for(var cbIndex=0;cbIndex<checkboxObjs.length;cbIndex++)
		{
			if(checkboxObjs[cbIndex].checked)
			{
				selectSize++;
				if(conditions.length==0)
				{
					conditions = checkboxObjs[cbIndex].id;
				}else
				{
					conditions +=","+ checkboxObjs[cbIndex].id;
				}
			}
		}
		if(selectSize==checkboxObjs.length||selectSize==0)
		{
			conditions = "";
		}
		document.getElementById('conditions').value = conditions;
		document.getElementById('form').submit(); 
	}
	function selectAllCB(){
		var obj = document.getElementById("selectAll");
		var checkboxObjs = document.getElementsByName("conditionCB");
		for(var cbIndex=0;cbIndex<checkboxObjs.length;cbIndex++)
		{
			checkboxObjs[cbIndex].checked = obj.checked;
		}
	}
</script>
	</head>
	<body>
		<div class="content">
			<div class="title_right">
				<div class="title_left">
					<span class="title_bg"> <span class="title_icon2">
							用户所有条件配置 </span> </span>
					<span class="title_xieline"></span>
				</div>
			</div>
			 <div class="scroll_div" id="center">
					<table id='tab' class='tableborder'>
						<tr>
							<th>
								搜索条件名称
							</th>
							<th>
								搜索条件标识
							</th>
							<th>
								全选
								<input type="checkbox" name="selectAll" id="selectAll"  onclick="selectAllCB()"/>
							</th>
						</tr>
						<c:forEach var="item" items="${conditonMap}">
							<tr>
								<td>
									${item.value}
								</td>
								<td>
									${item.key}
								</td>
								<td>
									<input type="checkbox" name="conditionCB" id="${item.key}" />
								</td>
							</tr>
						</c:forEach>
					</table>

				</div>
				<div class="add_bottom">
					<input type="button" value="<eoms:lable name="com_btn_save"/>"
						class="save_button"
						onmouseover="this.className='save_button_hover'"
						onmouseout="this.className='save_button'" onclick="formSubmit();" />
					<input type="button" value="<eoms:lable name="com_btn_cancel"/>"
						class="cancel_button"
						onmouseover="this.className='cancel_button_hover'"
						onmouseout="this.className='cancel_button'"
						onclick="window.close();" />
				</div>
		</div>
		<form action="saveSearchCondition.action" name="form" method="post" id="form">
				<input type="hidden" id="sqlname" name="sqlname" value="${sqlname}" />
				<input type="hidden" id="conditions" name="conditions" value="" />
		</form>
	</body>
	<script language="javascript">
    initCheckbox();
	function initCheckbox(){
		var conditions = '${conditions}';
		if(conditions.length>0)
		{
			var conditionArray  = conditions.split(',');
			var checkboxObjs = document.getElementsByName("conditionCB");
			for(var cbIndex=0;cbIndex<checkboxObjs.length;cbIndex++)
			{
				for(var conditionIndex=0;conditionIndex<conditionArray.length;conditionIndex++)
				{
					if(conditionArray[conditionIndex]==checkboxObjs[cbIndex].id){
						checkboxObjs[cbIndex].checked = true;
					}
				}
			}
		}
	}
	
</script>
</html>