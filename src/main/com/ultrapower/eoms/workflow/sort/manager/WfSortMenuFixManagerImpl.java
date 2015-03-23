package com.ultrapower.eoms.workflow.sort.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.workflow.sort.service.WfSortMenuService;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfSort;
import com.ultrapower.workflow.configuration.sort.model.WfType;

/**
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public class WfSortMenuFixManagerImpl implements WfSortMenuService
{

    /**
     * 流程分类service对象
     */
    private IWfSortManager ver;
    /**
     * 目录树管理对象
     */
    private MenuManagerService menuManagerService;

    public static final String prefix_wf_manage_ = "wf_manage_";
    public static final String prefix_wf_version_ = "wf_version_";
    public static final String prefix_wf_dimension_ = "wf_dimension_";
    public static final String prefix_wf_sheetrole_ = "wf_sheetrole_";
    public static final String prefix_wf_form_ = "wf_form_";// 配置表单字段
    public static final String prefix_wf_fix_ = "wf_fix_";// 配置表单环节
    public static final String prefix_wf_free_ = "wf_free_";// 配置表单环节
    public static final String prefix_sheet_ = "sheet_";
    public static final String prefix_sheet_waiting_ = "sheet_waiting_";
    public static final String prefix_sheet_mycreated_ = "sheet_mycreated_";
    public static final String prefix_sheet_mydealed_ = "sheet_mydealed_";

    /**
     * 保存目录树； 添加流程分类时，需要更新“工单管理”和“流程管理”中的相应目录
     */
    public List<String> saveMenu_type(WfType wfType, String code)
    {
	List<String> menuIdList = new ArrayList<String>();
	Long order = wfType.getOrderby();
	String menuCode_manage = null; // 流程类型(父)
	String menuCode_version = null; // 配置工单流程（父）
	String menuCode_dimension = null; // 配置角色细分维度（父）
	String menuCode_sheetrole = null; // 配置工单角色(父）
	String menuCode_form = null;// 配置表单字段（父）
	String menuCode_fix = null;// 配置表单环节（父）
	String menuCode_free = null;// 配置表单状态动作（父）
	String menuCode_sheet = null; // 工单管理(父)
	String menuCode_sheet_waiting = null; // 待办工单(父)
	String menuCode_sheet_mycreated = null; // 我建立的工单(父)
	String menuCode_sheet_mydealed = null; // 我处理的工单(父)

	String newMenuCode_manage = null; // 流程类型(新)
	String newMenuCode_version = null; // 配置工单流程(新)
	String newMenuCode_dimension = null; // 配置角色细分维度(新)
	String newMenuCode_sheetrole = null; // 配置工单角色(新）
	String newMenuCode_form = null;// 配置表单字段（新）
	String newMenuCode_fix = null;// 配置表单环节（新）
	String newMenuCode_free = null;// 配置表单状态（新）
	String newMenuCode_sheet = null; // 工单管理(新)
	String newMenuCode_sheet_waiting = null; // 待办工单(新)
	String newMenuCode_sheet_mycreated = null; // 我建立的工单(新)
	String newMenuCode_sheet_mydealed = null; // 我处理的工单(新)

	try
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    WfSort pwfSort = ver.getWfSortByid(wfType.getSortId());
	    menuCode_manage = prefix_wf_manage_ + pwfSort.getCode();
	    menuCode_version = prefix_wf_manage_ + wfType.getCode();
	    menuCode_dimension = prefix_wf_manage_ + wfType.getCode();
	    menuCode_sheetrole = prefix_wf_manage_ + wfType.getCode();
	    menuCode_form = prefix_wf_manage_ + wfType.getCode();
	    menuCode_fix = prefix_wf_manage_ + wfType.getCode();
	    menuCode_free = prefix_wf_manage_ + wfType.getCode();
	    menuCode_sheet = prefix_sheet_ + pwfSort.getCode();
	    menuCode_sheet_waiting = prefix_sheet_waiting_ + pwfSort.getCode();
	    menuCode_sheet_mycreated = prefix_sheet_mycreated_ + pwfSort.getCode();
	    menuCode_sheet_mydealed = prefix_sheet_mydealed_ + pwfSort.getCode();
	} catch (RemoteException e)
	{
	    e.printStackTrace();
	}

	newMenuCode_manage = prefix_wf_manage_ + wfType.getCode();
	newMenuCode_version = prefix_wf_version_ + wfType.getCode();
	newMenuCode_dimension = prefix_wf_dimension_ + wfType.getCode();
	newMenuCode_sheetrole = prefix_wf_sheetrole_ + wfType.getCode();
	newMenuCode_form = prefix_wf_form_ + wfType.getCode();
	newMenuCode_fix = prefix_wf_fix_ + wfType.getCode();
	newMenuCode_free = prefix_wf_free_ + wfType.getCode();
	newMenuCode_sheet = prefix_sheet_ + wfType.getCode();
	newMenuCode_sheet_waiting = prefix_sheet_waiting_ + wfType.getCode();
	newMenuCode_sheet_mycreated = prefix_sheet_mycreated_ + wfType.getCode();
	newMenuCode_sheet_mydealed = prefix_sheet_mydealed_ + wfType.getCode();

	// 流程版本控制下目录树
	MenuInfo newMenu = new MenuInfo();
	newMenu.setNodename(wfType.getName());
	newMenu.setNodetype(Long.parseLong("1"));
	newMenu.setNodeurl("");
	newMenu.setStatus(Long.parseLong("1"));
	newMenu.setNodemark(newMenuCode_manage);
	newMenu.setOpenway("1");
	newMenu.setOrdernum(order);

	MenuInfo pminfo_manage = menuManagerService.getMenuByMark(menuCode_manage);
	if (pminfo_manage != null)
	{
	    newMenu.setParentid(pminfo_manage.getPid());
	    // newMenu.setNodeurl("wfversion/list.action?baseCode=" +
	    // wfType.getCode());
	    newMenu.setNodeurl("");
	    MenuInfo newMenu_version = menuManagerService.getMenuByMark(newMenuCode_manage);
	    if (newMenu_version == null)
	    {
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_version.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	// remedy 工单 code 包含 WF4:
	if (!code.contains("WF4:"))
	{
	    // 工单类型下添加“配置表单字段”
	    MenuInfo pminfo_form = menuManagerService.getMenuByMark(menuCode_form);
	    if (pminfo_form != null)
	    {
		MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_form);
		newMenu.setParentid(pminfo_form.getPid());
		newMenu.setOrdernum(Long.parseLong("1"));
		newMenu.setNodeurl("ultrabpp/develop/deployMain.action?baseSchema=" + wfType.getCode());
		newMenu.setNodemark(newMenuCode_form);
		newMenu.setNodename("配置表单字段");
		if (newMenu_sheet == null)
		{
		    newMenu.setPid(null);
		    newMenu.setCreatetime(TimeUtils.getCurrentTime());
		    menuManagerService.addMenuInfo(newMenu);
		} else
		{
		    newMenu.setPid(newMenu_sheet.getPid());
		    menuManagerService.updateMenuInfo(newMenu);
		}
		menuIdList.add(newMenu.getPid());
	    }

	    // 工单类型下添加“配置表单环节”
	    MenuInfo pminfo_fix = menuManagerService.getMenuByMark(menuCode_fix);
	    if (pminfo_fix != null)
	    {
		MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_fix);
		newMenu.setParentid(pminfo_form.getPid());
		newMenu.setOrdernum(Long.parseLong("2"));
		newMenu.setNodeurl("ultrabpp/develop/stepList.action?baseSchema=" + wfType.getCode());
		newMenu.setNodemark(newMenuCode_fix);
		newMenu.setNodename("配置表单环节");
		if (newMenu_sheet == null)
		{
		    newMenu.setPid(null);
		    newMenu.setCreatetime(TimeUtils.getCurrentTime());
		    menuManagerService.addMenuInfo(newMenu);
		} else
		{
		    newMenu.setPid(newMenu_sheet.getPid());
		    menuManagerService.updateMenuInfo(newMenu);
		}
		menuIdList.add(newMenu.getPid());
	    }

	    // 工单类型下添加“配置表单状态动作字段”
	    MenuInfo pminfo_free = menuManagerService.getMenuByMark(menuCode_free);
	    if (pminfo_free != null)
	    {
		MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_free);
		newMenu.setParentid(pminfo_form.getPid());
		newMenu.setOrdernum(Long.parseLong("2"));
		newMenu.setNodeurl("ultrabpp/develop/freeActionfieldrelList.action?baseSchema=" + wfType.getCode());
		newMenu.setNodemark(newMenuCode_free);
		newMenu.setNodename("配置状态动作字段");
		if (newMenu_sheet == null)
		{
		    newMenu.setPid(null);
		    newMenu.setCreatetime(TimeUtils.getCurrentTime());
		    menuManagerService.addMenuInfo(newMenu);
		} else
		{
		    newMenu.setPid(newMenu_sheet.getPid());
		    menuManagerService.updateMenuInfo(newMenu);
		}
		menuIdList.add(newMenu.getPid());
	    }

	}

	// 工单类型下添加“配置工单流程”
	MenuInfo pminfo_version = menuManagerService.getMenuByMark(menuCode_version);
	if (pminfo_version != null)
	{
	    MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_version);
	    newMenu.setParentid(pminfo_version.getPid());
	    newMenu.setOrdernum(Long.parseLong("3"));
	    newMenu.setNodeurl("wfversion/list.action?baseCode=" + wfType.getCode());
	    newMenu.setNodemark(newMenuCode_version);
	    newMenu.setNodename("配置工单流程");
	    if (newMenu_sheet == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	// 工单类型下添加“角色细分维度列表”
	MenuInfo pminfo_dimension = menuManagerService.getMenuByMark(menuCode_dimension);
	if (pminfo_dimension != null)
	{
	    MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_dimension);
	    newMenu.setParentid(pminfo_dimension.getPid());
	    newMenu.setOrdernum(Long.parseLong("4"));
	    newMenu.setNodeurl("sheetrole/sheetDimensionQuery.action?baseSchema=" + wfType.getCode() + "&baseName=" + wfType.getName());
	    newMenu.setNodemark(newMenuCode_dimension);
	    newMenu.setNodename("配置角色细分维度");
	    if (newMenu_sheet == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	// 工单类型下添加“配置工单角色”
	MenuInfo pminfo_sheetrole = menuManagerService.getMenuByMark(menuCode_sheetrole);
	if (pminfo_version != null)
	{
	    MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_sheetrole);
	    newMenu.setParentid(pminfo_sheetrole.getPid());
	    newMenu.setOrdernum(Long.parseLong("5"));
	    newMenu.setNodeurl("");
	    newMenu.setNodemark(newMenuCode_sheetrole);
	    newMenu.setNodename("配置工单角色");
	    if (newMenu_sheet == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	// 工单管理下添加目录树
	MenuInfo pminfo_sheet = menuManagerService.getMenuByMark(menuCode_sheet);
	if (pminfo_sheet != null)
	{
	    MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_sheet);
	    newMenu.setParentid(pminfo_sheet.getPid());
	    newMenu.setOrdernum(order);
	    newMenu.setNodeurl("sheet/baseInfoQuery.action?baseSchema=" + wfType.getCode());
	    newMenu.setNodemark(newMenuCode_sheet);
	    newMenu.setNodename(wfType.getName());
	    if (newMenu_sheet == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	// 待办工单下添加目录树
	MenuInfo pminfo_sheet_waiting = menuManagerService.getMenuByMark(menuCode_sheet_waiting);
	if (pminfo_sheet_waiting != null)
	{
	    MenuInfo newMenu_sheet_waiting = menuManagerService.getMenuByMark(newMenuCode_sheet_waiting);
	    newMenu.setParentid(pminfo_sheet_waiting.getPid());
	    newMenu.setNodemark(newMenuCode_sheet_waiting);
	    newMenu.setNodename(wfType.getName());
	    newMenu.setNodeurl("sheet/myWaitingDealSheetQuery.action?baseSchema=" + wfType.getCode());
	    if (newMenu_sheet_waiting == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet_waiting.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	// 我建立的工单下添加目录树
	MenuInfo pminfo_sheet_mycreated = menuManagerService.getMenuByMark(menuCode_sheet_mycreated);
	if (pminfo_sheet_mycreated != null)
	{
	    MenuInfo newMenu_sheet_mycreated = menuManagerService.getMenuByMark(newMenuCode_sheet_mycreated);
	    newMenu.setParentid(pminfo_sheet_mycreated.getPid());
	    newMenu.setNodemark(newMenuCode_sheet_mycreated);
	    newMenu.setNodename(wfType.getName());
	    newMenu.setNodeurl("sheet/myDealedSheetQuery.action?baseSchema=" + wfType.getCode() + "&type=mycreated");
	    if (newMenu_sheet_mycreated == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet_mycreated.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	// 我处理的工单下添加目录树
	MenuInfo pminfo_sheet_mydealed = menuManagerService.getMenuByMark(menuCode_sheet_mydealed);
	if (pminfo_sheet_mycreated != null)
	{
	    MenuInfo newMenu_sheet_mydealed = menuManagerService.getMenuByMark(newMenuCode_sheet_mydealed);
	    newMenu.setParentid(pminfo_sheet_mydealed.getPid());
	    newMenu.setNodeurl("sheet/myDealedSheetQuery.action?baseSchema=" + wfType.getCode() + "&type=mydealed");
	    newMenu.setNodemark(newMenuCode_sheet_mydealed);
	    newMenu.setNodename(wfType.getName());
	    if (newMenu_sheet_mydealed == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet_mydealed.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    menuIdList.add(newMenu.getPid());
	}

	return menuIdList;
    }

    public String saveMenu_step(String baseSchema, String stepID, String stepNo)
    {
	String menuCode_fix = "wf_fix_" + baseSchema;

	String newMenuCode_fix = menuCode_fix + "_" + stepID;

	String newPid = "";

	MenuInfo pminfo_fix = menuManagerService.getMenuByMark(menuCode_fix);
	if (pminfo_fix != null)
	{
	    MenuInfo newMenu = new MenuInfo();
	    MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_fix);
	    newMenu.setParentid(pminfo_fix.getPid());
	    newMenu.setOrdernum(Long.parseLong("0"));
	    newMenu.setNodeurl("ultrabpp/develop/editStep.action?baseSchema=" + baseSchema + "&stepNo=" + stepNo);
	    newMenu.setNodemark(newMenuCode_fix);
	    newMenu.setNodename(stepNo);
	    if (newMenu_sheet == null)
	    {
		newMenu.setPid(null);
		newMenu.setCreatetime(TimeUtils.getCurrentTime());
		menuManagerService.addMenuInfo(newMenu);
	    } else
	    {
		newMenu.setPid(newMenu_sheet.getPid());
		menuManagerService.updateMenuInfo(newMenu);
	    }
	    newPid = newMenu.getPid();
	}
	return newPid;
    }

    public void updateMenu_nodename(String baseSchema, String stepID, String stepNo)
    {

	MenuInfo newMenu_sheet = menuManagerService.getMenuByMark("wf_fix_" + baseSchema + "_" + stepID);
	if (newMenu_sheet != null)
	{
	    newMenu_sheet.setNodename(stepNo);
	    menuManagerService.updateMenuInfo(newMenu_sheet);
	}

    }

    public IWfSortManager getVer()
    {
	return ver;
    }

    public void setVer(IWfSortManager ver)
    {
	this.ver = ver;
    }

    public MenuManagerService getMenuManagerService()
    {
	return menuManagerService;
    }

    public void setMenuManagerService(MenuManagerService menuManagerService)
    {
	this.menuManagerService = menuManagerService;
    }

}
