<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_duty" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_duty')">
	<div class="quickBarItem" style="width:160px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=logManager', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/duty/platform.png" />
				</div>
				<div class="quickBarItemBtnText">值班平台</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=QualityControl', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/TestImg.png" />
				</div>
				<div class="quickBarItemBtnText">值班质检</div>
			</div>
		</div>
		<div class="quickBarDesc">值班操作</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:240px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=dutyRalayAffirm', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/duty/changeconfirm.png" />
				</div>
				<div class="quickBarItemBtnText">替班确认</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=dutyrelayApprove', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/duty/changeaudit.png" />
				</div>
				<div class="quickBarItemBtnText">替班审批</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=dutyrelayApprove', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/duty/change.png" />
				</div>
				<div class="quickBarItemBtnText">我的替班</div>
			</div>
		</div>
		<div class="quickBarDesc">替班操作</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:240px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/dutyConfigure/dutyOrganizationAdd.action', '_blank')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/duty/currentlist.png" />
				</div>
				<div class="quickBarItemBtnText">当前所有值班信息</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=allcalendar', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/duty/list.png" />
				</div>
				<div class="quickBarItemBtnText">值班情况查询</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdduty');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=allcalendar', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/duty/check.png" />
				</div>
				<div class="quickBarItemBtnText">值班情况质检</div>
			</div>
		</div>
		<div class="quickBarDesc">值班情况</div>
	</div>
	<div class="quickBarSplit"></div>
</div>