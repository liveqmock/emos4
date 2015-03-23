<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_docsManager" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_docsManager')">
	<div class="quickBarItem" style="width:640px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=dmPortal', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/myinfor.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">文档库首页</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=myDocs', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/mymenutree.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">我的文档</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=depDocs', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/rssform.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">部门文档</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=myFavoriten', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/rssduty.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">我收藏的文档</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=myReceive', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/rssplan.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">我收到的文档</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=recentDocs', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/rssuser.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">最近浏览的文档</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=waitApprove', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/formagent.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">待审文档</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tddocsManager');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=docsLib', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/docsManager/formtree.png" width="36" height="37" />
				</div>
				<div class="quickBarItemBtnText">文档库</div>
			</div>
		</div>
		<div class="quickBarDesc">文档操作</div>
	</div>
	<div class="quickBarSplit"></div>
</div>