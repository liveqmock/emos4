<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_taskbooking" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_taskbooking')">
	<div class="quickBarItem" style="width:160px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdtaskbooking');openQuickBarBtnLink('${ctx}/ultratask/addTask.action', '_blank')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/taskbooking/new.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">新建预约任务</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdtaskbooking');openQuickBarBtnLink('${ctx}/taskTemplate/newTaskTemplate.action', '_blank')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/taskbooking/newtemplate.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">新建任务模板</div>
			</div>
		</div>
		<div class="quickBarDesc">常用功能</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:240px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdtaskbooking');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=todayTask', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/taskbooking/todaytask.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">今日预约任务</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdtaskbooking');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=searchTaskBooking', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/taskbooking/alert.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">提醒任务查询</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdtaskbooking');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=TaskManage', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/taskbooking/tasklist.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">任务预约管理</div>
			</div>
		</div>
		<div class="quickBarDesc">常用查询</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:80px;">
		<div class="quickBarItemBtn" onclick="PageMenuActive('tdtaskbooking');openQuickBarBtnLink('${ctx}/taskTemplate/newTaskTemplate.action', '_blank')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
			<div class="quickBarItemBtnImgBox">
				<img src="${ctx}/common/style/office/images/TestImg.png" width="36" height="37" />
			</div>
			<div class="quickBarItemBtnText">新建任务模板</div>
		</div>	
	</div>	
	<div class="quickBarSplit"></div>

</div>