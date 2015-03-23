<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_sheet" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_sheet')">
	<div class="quickBarItem" style="width:320px;">
		<div class="quickBarListBox">
			<div class="quickBarListTable">
				<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?baseSchema=WF4:EL_TTM_TTH', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
					<img src="${ctx}/common/style/office/images/sheet/newForm.png" class="quickBarListItemImg" />
					<span>故障处理工单</span>
				</div>
				<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?baseSchema=WF4:EL_CM_NC_IU', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
					<img src="${ctx}/common/style/office/images/sheet/newForm.png" class="quickBarListItemImg" />
					<span>网络综合调整工单</span>
				</div>
				<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?baseSchema=WF4:EL_UVS_TSK', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
					<img src="${ctx}/common/style/office/images/sheet/newForm.png" class="quickBarListItemImg" />
					<span>通用任务工单</span>
				</div>
				<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?baseSchema=WF4:EL_TTM_CCH', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
					<img src="${ctx}/common/style/office/images/sheet/newForm.png" class="quickBarListItemImg" />
					<span>个人投诉处理工单</span>
				</div>
				<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?baseSchema=WF4:EL_CM_NC_DU', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
					<img src="${ctx}/common/style/office/images/sheet/newForm.png" class="quickBarListItemImg" />
					<span>网络数据管理工单</span>
				</div>
				<div class="quickBarListItem" onclick="openQuickBarBtnLink('${ctx}/sheet/createNewSheet.action?baseSchema=WF4:EL_CM_NC_CC', '_blank')" onmouseover="this.className='quickBarListItem_hover'" onmouseout="this.className='quickBarListItem'" onmousedown="this.className='quickBarListItem_active'" onmouseup="this.className='quickBarListItem_hover'">
					<img src="${ctx}/common/style/office/images/sheet/newForm.png" class="quickBarListItemImg" />
					<span>电路调度工单</span>
				</div>
			</div>
		</div>
		<div class="quickBarDesc">新建工单</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:240px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdsheet');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_waiting', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/sheet/waitingdeal.png" />
				</div>
				<div class="quickBarItemBtnText">待办工单</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdsheet');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_mycreated', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/sheet/mycreate.png" />
				</div>
				<div class="quickBarItemBtnText">我创建的工单</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdsheet');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_mydealed', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/sheet/dealed.png" />
				</div>
				<div class="quickBarItemBtnText">我处理的工单</div>
			</div>
		</div>
		<div class="quickBarDesc">常用查询列表</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:145px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1001', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">01-通用类工单</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1002', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">02-故障管理流程</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1003', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">03-网络变更与配置</div>
			</div>
		</div>
	</div>
	<div class="quickBarItem" style="width:145px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1004', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">04-新业务管理</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1005', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">05-流程管理</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1006', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">06-IT需求管理</div>
			</div>
		</div>
		<div class="quickBarDesc">工单分类查询</div>
	</div>
	<div class="quickBarItem" style="width:145px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1007', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">07-应急与事件管理</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=sheet_1008', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">08-网络优化管理</div>
			</div>
		</div>
	</div>
	<div class="quickBarSplit"></div>
</div>