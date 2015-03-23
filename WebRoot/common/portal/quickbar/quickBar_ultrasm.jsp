<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_ultrasm" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_ultrasm')">
	<div class="quickBarItem" style="width:640px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=myself', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/myinfor.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">我的信息</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=mymenu', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/mymenutree.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">我的菜单</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=formSmOrder', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/rssform.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">工单短信订阅</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=dutySmOrder', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/rssduty.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">值班短信订阅</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=planSmOrder', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/rssplan.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">计划短信订阅</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=manualSmSend', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/rssuser.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">手动短信发送</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=wf_agency', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/formagent.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">工单代办配置</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=formSendTree', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/formtree.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">工单派发树</div>
			</div>
		</div>
		<div class="quickBarDesc">个人信息设置</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:400px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=depInfo', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/deplist.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">部门列表</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=personInfo', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/userlist.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">人员列表</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=roleInfo', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/rolelist.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">角色列表</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=resourceInfo', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/resourcelist.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">资源列表</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdultrasm');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=menunode', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/ultrasm/menutree.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">菜单目录信息</div>
			</div>
		</div>
		<div class="quickBarDesc">系统管理常用功能</div>
	</div>
	<div class="quickBarSplit"></div>
</div>