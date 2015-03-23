package com.ultrapower.eoms.msextend.home.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.Resource;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;
import com.ultrapower.eoms.workflow.sheet.query.web.SheetQueryAction;

public class HomeAction extends SheetQueryAction{

	private static final long serialVersionUID = -2522253616399640709L;
	/**
	 * 工单管理的菜单ID
	 */
	public static final String SHEET_MANAGER_MENU_ID = "402894f5295d39ec01295e5724bf0002";
	
	private PrivilegeManagerService privilegeManagerService;
	/**
	 * 工作台
	 */
	public String workdesk() {
		
		return SUCCESS;
	}
	public void setPrivilegeManagerService(
			PrivilegeManagerService privilegeManagerService) {
		this.privilegeManagerService = privilegeManagerService;
	}
	
	/**
	 * 跳转到待处理页面
	 */
	public String waitDeal(){
		return SUCCESS;
	}
	
	/**
	 * 工单进度跟踪，根据类别展示待处理的工单
	 */
	public String listWaitDealByType(){
		myWaitingDealSheetQuery();
		if(baseSchema == null) return SUCCESS;
		String resource = "listWait"+baseSchema;
		String realPath = WebApplicationManager.servletContext.getRealPath("/") + "/home/"+resource+".jsp";
		Resource resource2 = WebApplicationManager.webApplicationContext.getResource("file:"+realPath);
		if(resource2.exists()){
			return findForward(resource);
		}
		return SUCCESS;
	}
	
	/**
	 * 自助服务工单进度跟踪（我的服务请求）
	 */
	public String listWaitDeal(){
		return SUCCESS;
	}
	
	/**
	 * 获取待处理的工单,返回json
	 */
	public void getWaitingDealSheet(){
		RQuery query = new RQuery("SQL_WF_WaitingDealSheet.query", null);
		DataTable dataTable = query.getDataTable();
		renderText(JSONArray.fromObject(dataTable.getRowList()).toString());
	}
	
	/**
	 * 待办工单信息
	 */
	public void getWaitingDealSheetsType(){
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		List<MenuDtree> menuDtreeList = privilegeManagerService.getMenuByNavigationID(userSession.getPid(), SHEET_MANAGER_MENU_ID);
		List<Map<String,String>> sheetsInfo = new ArrayList<Map<String,String>>();
		if (CollectionUtils.isNotEmpty(menuDtreeList)) {
			for (int i = 0; i < menuDtreeList.size(); i++) {
				MenuDtree menu = menuDtreeList.get(i);
				String url = menu.getUserdata().get("url");
				if(url.indexOf("sheet/myWaitingDealSheetQuery.action?baseSchema=")>-1){
					Map<String,String> sheetInfo = new HashMap<String, String>();
					sheetInfo.put("schema", url.split("=")[1]);
					sheetInfo.put("name", menu.getText());
					sheetsInfo.add(sheetInfo);
				}
			}
		}
		renderText(JSONArray.fromObject(sheetsInfo).toString());
	}
	
	/**
	 * 新建工单信息 
	 */
	public void getCreateSheetsType(){
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		List<MenuDtree> menuDtreeList = privilegeManagerService.getMenuByNavigationID(userSession.getPid(), SHEET_MANAGER_MENU_ID);
		List<Map<String,String>> sheetsInfo = new ArrayList<Map<String,String>>();
		if (CollectionUtils.isNotEmpty(menuDtreeList)) {
			for (int i = 0; i < menuDtreeList.size(); i++) {
				MenuDtree menu = menuDtreeList.get(i);
				String url = menu.getUserdata().get("url");
				if(url.indexOf("baseInfoQuery.action?baseSchema=")>-1){
					Map<String,String> sheetInfo = new HashMap<String, String>();
					sheetInfo.put("schema", url.split("=")[1]);
					sheetInfo.put("name", menu.getText());
					sheetsInfo.add(sheetInfo);
				}
			}
		}
		renderText(JSONArray.fromObject(sheetsInfo).toString());
	}
	
	public String getBaseSchema() {
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
}
