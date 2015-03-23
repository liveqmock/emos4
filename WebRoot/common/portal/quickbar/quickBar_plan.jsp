<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_plan" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_plan')">
	<div class="quickBarItem" style="width:170px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=ExecByUser', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/executelist.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">待办列表</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=planExecuteMonth', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/executemonth.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">月视图</div>
			</div>
		</div>
		<div class="quickBarDesc">计划待办</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:225px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=qualityControlList', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/checklist.png" />
				</div>
				<div class="quickBarItemBtnText">自动质检列表</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/ultraplan/planCheckConfig/openPlanExtract.action', '_blank')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/newForm.png" />
				</div>
				<div class="quickBarItemSmallBtnText">新增抽查质检</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/ultraplan/planCheckConfig/planCheckConfig.action', '_blank')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/newForm.png" />
				</div>
				<div class="quickBarItemSmallBtnText">新增质检配置</div>
			</div>
		</div>
		<div class="quickBarDesc">自动质检</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:145px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/ultraplan/planYear/addPlanYear.action', '_blank')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/newForm.png" />
				</div>
				<div class="quickBarItemSmallBtnText">年计划制定</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/ultraplan/planMonth/addPlanMonth.action', '_blank')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/newForm.png" />
				</div>
				<div class="quickBarItemSmallBtnText">月计划制定</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/ultraplan/planNotcycle/addPlanNotcycle.action', '_blank')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/newForm.png" />
				</div>
				<div class="quickBarItemSmallBtnText">非周期计划制定</div>
			</div>
		</div>
		<div class="quickBarDesc">计划制定</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:145px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=yearPlanModify', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">年计划变更</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=monthPlanModify', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">月计划变更</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=aperiodicPlanModify', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/change.png" />
				</div>
				<div class="quickBarItemSmallBtnText">非周期计划变更</div>
			</div>
		</div>
		<div class="quickBarDesc">计划变更</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:145px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=yearPlanApprove', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/audit.png" />
				</div>
				<div class="quickBarItemSmallBtnText">年计划审批</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=monthPlanApprove', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/audit.png" />
				</div>
				<div class="quickBarItemSmallBtnText">月计划审批</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=aperiodicApprove', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/audit.png" />
				</div>
				<div class="quickBarItemSmallBtnText">非周期计划审批</div>
			</div>
		</div>
		<div class="quickBarDesc">计划审批</div>
	</div>
	<div class="quickBarSplit"></div>
	
	<div class="quickBarItem" style="width:290px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=yearPlanSearch', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/list.png" />
				</div>
				<div class="quickBarItemSmallBtnText">年计划查询</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=monthPlanSearch', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/list.png" />
				</div>
				<div class="quickBarItemSmallBtnText">月计划查询</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=planExecuteSearch', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/list.png" />
				</div>
				<div class="quickBarItemSmallBtnText">非周期计划查询</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=planExecuteSearch', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/list.png" />
				</div>
				<div class="quickBarItemSmallBtnText">月计划执行查询</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=notcycleSearch', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/list.png" />
				</div>
				<div class="quickBarItemSmallBtnText">非周期计划执行查询</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=planFormSearch', 'center')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/list.png" />
				</div>
				<div class="quickBarItemSmallBtnText">计划内容查询</div>
			</div>
		</div>
		<div class="quickBarDesc">计划查询</div>
	</div>
	<div class="quickBarSplit"></div>
	
	<div class="quickBarItem" style="width:140px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/ultraplan/planTemplate/newPlanTempalte.action', '_blank')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/newForm.png" />
				</div>
				<div class="quickBarItemSmallBtnText">新建模板</div>
			</div>
			<div class="quickBarItemSmallBtn" onclick="PageMenuActive('tdplan');openQuickBarBtnLink('${ctx}/ultraform/search/searchAddPlan.action', '_blank')" onmouseover="this.className='quickBarItemSmallBtn_hover'" onmouseout="this.className='quickBarItemSmallBtn'" onmousedown="this.className='quickBarItemSmallBtn_active'" onmouseup="this.className='quickBarItemSmallBtn_hover'">
				<div class="quickBarItemSmallBtnImgBox">
					<img src="${ctx}/common/style/office/images/plan/newForm.png" />
				</div>
				<div class="quickBarItemSmallBtnText">计划内容搜索配置</div>
			</div>
		</div>
		<div class="quickBarDesc">自动质检</div>
	</div>
	<div class="quickBarSplit"></div>
</div>