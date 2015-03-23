package com.ultrapower.eoms.common.sheettag.web;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.sheettag.common.PageInfo;
import com.ultrapower.eoms.common.sheettag.model.SheetTag;
import com.ultrapower.eoms.common.sheettag.model.SheetType;
import com.ultrapower.eoms.common.sheettag.service.SheetTagService;
import com.ultrapower.eoms.ultrasm.model.RoleOrgShow;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

/**
 * 首页工单标签配置管理action
 * 
 * @author <a href="mailto:liuzhuo@ultrapower.com.cn">liuzhuo</a>
 * 
 * @version
 * 
 * @since 2013-2-27
 */
public class SheetTagManagerAction extends BaseAction {
	private static final long serialVersionUID = -1465625738250298459L;
	private SheetTagService sheetTagService;
	private PrivilegeManagerService privilegeManagerService;
	private UserManagerService userManagerService;
	private List<SheetTag> listTag;

	private List<SheetType> listType;

	private SheetTag sheetTag;

	private SheetType sheetType;

	private String id;

	private PageInfo pageInfo;
	private String page;

	private String typeIds;
	private String typeNames;

	private String type;
	private String sourceId;
	
	private String baseschema;
	private RoleManagerService roleManagerService;
	/**
	 * 获取tag列表
	 * @return
	 */
	public String listTag() {
		Long count = sheetTagService.getTagCount();
		pageInfo = new PageInfo(count, 20);
		if (page != null && !"".equals(page)) {
			pageInfo.setPage(new Integer(page).intValue());
		}
		listTag = sheetTagService.listTag(pageInfo);
		return "listTag";
	}

	/**
	 * 编辑tag
	 * @return
	 */
	public String editTag() {
		if (null != id && !"".equals(id)) {
			sheetTag = sheetTagService.getTagById(id);
		}
		return "editTag";
	}

	/**
	 * 删除tag
	 * @return
	 */
	public String removeTag() {
		sheetTagService.removeTagById(id);
		return "operateTagSuccess";
	}

	/**
	 * 保存或修改，id不为空修改
	 * @return
	 */
	public String saveTag() {
		if (null != id && !"".equals(id)) {
			sheetTag.setId(id);
		}
		sheetTagService.saveTag(sheetTag);
		return "operateTagSuccess";
	}

	/**
	 * 获取type列表
	 * @return
	 */
	public String listType() {
		Long count = sheetTagService.getTypeCount();
		pageInfo = new PageInfo(count, 20);
		if (null != type) {
			if (page != null && !"".equals(page)) {
				pageInfo.setPage(new Integer(page).intValue());
			}
			listType = sheetTagService.listPageType(pageInfo);
			return "listTypeCheckbox";
		} else {
			listType = sheetTagService.listType(pageInfo);
			return "listType";
		}
	}

	/**
	 * 编辑type
	 * @return
	 */
	public String editType() {
		if (null != id && !"".equals(id)) {
			sheetType = sheetTagService.getTypeById(id);
		}
		return "editType";
	}
	
	/**
	 * 编辑type
	 * @return
	 */
	public String addeditType() {
		if (null != id && !"".equals(id)) {
			sheetType = sheetTagService.getTypeById(id);
		}
		return "addeditType";
	}
	

	/**
	 * 删除type
	 * @return
	 */
	public String removeType() {
		sheetTagService.removeTypeById(id);
		return "operateTypeSuccess";
	}

	/**
	 * 保存或修改type
	 * @return
	 */
	public String saveType() {
		if (null != id && !"".equals(id)) {
			sheetType.setId(id);
		}
		sheetTagService.saveType(sheetType);
		return "operateTypeSuccess";
	}

	/**
	 * 编辑工单标签和类别的关联关系
	 * 
	 * @return
	 */
	public String eidtTagType() {
		List<SheetType> list = sheetTagService.listTypeByTag(id);
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		for (SheetType o : list) {
			ids.append(o.getId());
			ids.append(",");
			names.append(o.getName());
			names.append(",");

		}
		// 去掉最后一个","
		if (ids.toString().length() > 0) {
			typeIds = ids.toString().substring(0, ids.toString().length() - 1);
		}
		if (names.toString().length() > 0) {
			typeNames = names.toString().substring(0,
					names.toString().length() - 1);

		}
		return "eidtTagType";
	}

	/**
	 * 保存tag type关联
	 * @return
	 */
	public String saveTagTypeLink() {
		sheetTagService.saveTagTypeLink(id, typeIds.split(","));
		return "operateTagSuccess";
	}
	
	/**
	 * 首页ajax加载type列表
	 * @return
	 */
	public String types() { 
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		listType = sheetTagService.listTypeByTag(id);
		List<MenuDtree> menuDtreeList = privilegeManagerService.getMenuByNavigationID(userSession.getPid(), sourceId);
		List<SheetType> returnTypes = new ArrayList<SheetType>();
		
		String baseschema;
		String typeUrl;
		String type = "myWaitingDealSheetQuery.action?baseSchema=";
		int typeIndex;
		
		//处理快速通道中普通链接，根据菜单树项确定呈现与否
		String hrefUrl="upereqInfoQuery.action?baseSchema=UPEP_REQ&xtywxt=";
		String ywxt = "";
		String mainUrl = "/ultrabpp/view.action?mode=NEW&baseSchema=UPEP_REQ&ywxt=";
		StringBuilder str = new StringBuilder();
		StringBuilder stb = new StringBuilder();
		if(null!=menuDtreeList && !menuDtreeList.isEmpty()){
			for (MenuDtree mType : menuDtreeList) {
				typeUrl = mType.getUserdata().get("url");
				typeIndex = typeUrl.indexOf(type);
				if (typeIndex > 0) {
					baseschema = typeUrl.substring(typeIndex + type.length());
					if(!"".equals(baseschema)&&!"UPEP_REQ".equals(baseschema)){
						str.append(" or st.baseschema = '").append(baseschema).append("'");
					}
				}
			/*	if(hrefIndex>0){
					ywxt = typeUrl.substring(hrefIndex + hrefUrl.length());
					if(!"".equals(ywxt)){
						stb.append(" or st.url='").append(mainUrl).append(ywxt).append("' ");
					}
				}*/
			}
			
		}
		//得到所有的该角色的资源目录
		//List<MenuDtree> maxmenuDtreeList = privilegeManagerService.getNavigationMenu(userSession.getPid());
		//得到所有该用户的角色
		//List<RoleInfo> roleIdList = roleManagerService.getRoleByUserID(userSession.getPid());
		List<RoleOrgShow> roleIdList = userManagerService.getRoleOrgByUserID(userSession.getPid());

		String rolename="数据应用请求_";
		String roleendname="_创建";
		if(roleIdList != null)
		{
			for (RoleOrgShow roleInfo : roleIdList) {
				if(roleInfo.getRolename().startsWith(rolename)&&roleInfo.getRolename().endsWith(roleendname)){//如果角色是数据应用请求_XXX_创建 取中间的业务系统
					ywxt = roleInfo.getRolename().substring(7, roleInfo.getRolename().lastIndexOf("_"));
					if(!"".equals(ywxt)){
						stb.append(" or st.url='").append(mainUrl).append(ywxt).append("' ");
					}
				}
			}
		}
		for(SheetType sheetType : listType){
			if(-1 != str.indexOf("'"+sheetType.getBaseschema()+"'")){// && "1".equals(sheetType.getBstype())
				returnTypes.add(sheetType);
				continue;
			}
			if(-1 != stb.indexOf("'"+sheetType.getUrl()+"'")){
				// 排除连接配置为/ultrabpp/view.action?mode=NEW&baseSchema=UPEP_REQ&ywxt=
				// Schema配置成UPEP_REQ情况，则以Schema的权限为主
				returnTypes.add(sheetType);
				continue;
			}
			if("0".equals(sheetType.getBstype())
					&&( null==sheetType.getBaseschema() || "".equals(sheetType.getBaseschema()) )
					&& -1 == sheetType.getUrl().indexOf(mainUrl)){
				returnTypes.add(sheetType);
				continue;
			}
		}
		listType = returnTypes;
		return "types";
	}
	
	/**
	 * 验证工单标签名称
	 * @return
	 */
	public String editvalidate(){
	String flg = sheetTagService.editvalidate(typeNames);
		this.renderText(flg);
		return null;
		
	}
	/**
	 * 验证工单类别Schema
	 * @return
	 */
	public String editSchema(){
	  SheetType listtype =	sheetTagService.getSheetTypeByBaseSchema(baseschema);
	    if(listtype!= null && !"".equals(listtype) ){
	    	this.renderText("false");
	    }else{
	        this.renderText("true");
	    }
		return null;
		
	}

	/**
	 * @param sheetTagService
	 *            the sheetTagService to set
	 */
	public void setSheetTagService(SheetTagService sheetTagService) {
		this.sheetTagService = sheetTagService;
	}

	/**
	 * @return the listTag
	 */
	public List<SheetTag> getListTag() {
		return listTag;
	}

	/**
	 * @param listTag
	 *            the listTag to set
	 */
	public void setListTag(List<SheetTag> listTag) {
		this.listTag = listTag;
	}

	/**
	 * @return the listType
	 */
	public List<SheetType> getListType() {
		return listType;
	}

	/**
	 * @param listType
	 *            the listType to set
	 */
	public void setListType(List<SheetType> listType) {
		this.listType = listType;
	}

	/**
	 * @return the sheetTag
	 */
	public SheetTag getSheetTag() {
		return sheetTag;
	}

	/**
	 * @param sheetTag
	 *            the sheetTag to set
	 */
	public void setSheetTag(SheetTag sheetTag) {
		this.sheetTag = sheetTag;
	}

	/**
	 * @return the sheetType
	 */
	public SheetType getSheetType() {
		return sheetType;
	}

	/**
	 * @param sheetType
	 *            the sheetType to set
	 */
	public void setSheetType(SheetType sheetType) {
		this.sheetType = sheetType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pageInfo
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo
	 *            the pageInfo to set
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the typeIds
	 */
	public String getTypeIds() {
		return typeIds;
	}

	/**
	 * @param typeIds
	 *            the typeIds to set
	 */
	public void setTypeIds(String typeIds) {
		this.typeIds = typeIds;
	}

	/**
	 * @return the typeNames
	 */
	public String getTypeNames() {
		return typeNames;
	}

	/**
	 * @param typeNames
	 *            the typeNames to set
	 */
	public void setTypeNames(String typeNames) {
		this.typeNames = typeNames;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setPrivilegeManagerService(
			PrivilegeManagerService privilegeManagerService) {
		this.privilegeManagerService = privilegeManagerService;
	}

	public String getBaseschema() {
		return baseschema;
	}

	public void setBaseschema(String baseschema) {
		this.baseschema = baseschema;
	}

	public RoleManagerService getRoleManagerService() {
		return roleManagerService;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

}
