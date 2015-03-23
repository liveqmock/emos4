<%@ page language="java" pageEncoding="UTF-8"%>
<div id="quickBar_repository" class="quickBar" style="display:none;" onmouseout="leaveQuickBar()" onmouseover="showQuickBar('quickBar_repository')">
	<div class="quickBarItem" style="width:400px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/ultrarepository/addRepository.action?urlFrom=addNew', '_blank')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/new.png" />
				</div>
				<div class="quickBarItemBtnText">新建案例</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=myRepository', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/mycreate.png" />
				</div>
				<div class="quickBarItemBtnText">创建的案例</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=packAwayRepository', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/favorite.png" />
				</div>
				<div class="quickBarItemBtnText">收藏的案例</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=readRepository', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/rss.png" />
				</div>
				<div class="quickBarItemBtnText">订阅的案例</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=latelyLookRepository', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/history.png" />
				</div>
				<div class="quickBarItemBtnText">最近浏览案例</div>
			</div>
		</div>
		<div class="quickBarDesc">我的案例</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:320px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=listRepositoryByManager', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/audit.png" />
				</div>
				<div class="quickBarItemBtnText">审核案例</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=listRepositoryByExpert', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/TestImg.png" />
				</div>
				<div class="quickBarItemBtnText">修订案例</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=listReNewRepositoryByManager', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/TestImg.png" />
				</div>
				<div class="quickBarItemBtnText">恢复案例</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=listUpdRepositoryByExpert', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/TestImg.png" />
				</div>
				<div class="quickBarItemBtnText">修订案例查询</div>
			</div>
		</div>
		<div class="quickBarDesc">审核案例</div>
	</div>
	<div class="quickBarSplit"></div>
	<div class="quickBarItem" style="width:160px;">
		<div class="quickBarItemBtnBox">
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=repositoryPortal', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/repository.png" />
				</div>
				<div class="quickBarItemBtnText">案例首页</div>
			</div>
			<div class="quickBarItemBtn" onclick="PageMenuActive('tdrepository');openQuickBarBtnLink('${ctx}/portal/office_frame.action?nodemark=repositorysearch', 'center')" onmouseover="this.className='quickBarItemBtn_hover'" onmouseout="this.className='quickBarItemBtn'" onmousedown="this.className='quickBarItemBtn_active'" onmouseup="this.className='quickBarItemBtn_hover'">
				<div class="quickBarItemBtnImgBox">
					<img src="${ctx}/common/style/office/images/repository/list.png" />
				</div>
				<div class="quickBarItemBtnText">案例分类查询</div>
			</div>
		</div>
		<div class="quickBarDesc">查询案例</div>
	</div>
	<div class="quickBarSplit"></div>
</div>