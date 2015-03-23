<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_wf" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_wf')">
	<div class="quickBarItem" style="width:240px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=wf_sort', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/category.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">流程分类管理</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=wf_interface', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/interface.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">流程接口管理</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=wf_model', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/custommodel.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">自定义模型</div>
			</div>
		</div>
		<div class="quickBarDesc">流程配置</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:400px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=workflow_templateUsedCount', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/report.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">流程定义使用频率</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=workflow_stepUsedCount', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/report.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">流程环节使用频率</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=workflow_entrydealtime', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/report.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">流程实例处理时长</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=workflow_stepDealtime', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/report.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">流程实例各环节处理时长</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdwf');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=workflow_stepDealjishipro', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/wf/report.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">流程实例各环节处理及时率</div>
			</div>
		</div>
		<div class="quickBarDesc">流程综合分析</div>
	</div>
	<div class="quickBarSplit"></div>
</div>