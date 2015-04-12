package com.ultrapower.eoms.workflow.sort.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.cache.service.ThemeCacheService;
import com.ultrapower.eoms.ultrabpp.develop.service.TemplateService;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.workflow.sort.service.WfSortMenuService;
import com.ultrapower.eoms.workflow.util.PageLimitUtil;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfSort;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.utils.UUIDGenerator;

/**
 * 流程分类管理（Web层action）
 * 
 * @version UltraPower-Eoms4.0-0.1
 * @author：liuchuanzu
 * @since jdk1.5
 * @date 2010-05-31 当前版本：v1.0 流程分类管理 web Copyright (c) 2010 神州泰岳服务管理事业部产品组
 */
public class WfSortManagerAction extends BaseAction
{

    /**
     * 流程分类对象
     */
    private WfSort wfSort;

    /**
     * 流程类型对象
     */
    private WfType wfType;

    /**
     * 流程分类ID
     */
    private String wfSortId;

    /**
     * 流程分类父ID
     */
    private String wfSortPid;

    /**
     * 流程类型ID
     */
    private String wfTypeId;

    /**
     * 流程类型list
     */
    private List<WfType> wfTypeList;

    /**
     * 添加分类时，添加“同级”与“下级”标识
     */
    private String type;

    /**
     * 流程分类service对象
     */
    private IWfSortManager ver;

    private WfSortTreeImpl wfSortTreeImpl;

    private WfSortMenuService wfSortMenuFree;
    private WfSortMenuService wfSortMenuFix;
    
    private TemplateService templateService;
    
    private List<ThemeModel> themeList;
    

    /**
     * 目录树管理对象
     */
    private MenuManagerService menuManagerService;

    private RoleManagerService roleManagerService;
    
	private ThemeCacheService themeCacheService;

    /**
     * 操作是否成功标识
     */
    private boolean sign;

    public static final String prefix_wf = "wf";
    public static final String prefix_wf_manage = "wf_manage";
    public static final String prefix_sheet = "sheet";
    public static final String prefix_sheet_waiting = "sheet_waiting";
    public static final String prefix_sheet_mycreated = "sheet_mycreated";
    public static final String prefix_sheet_mydealed = "sheet_mydealed";

    public static final String prefix_wf_manage_ = "wf_manage_";
    public static final String prefix_wf_version_ = "wf_version_";
    public static final String prefix_wf_dimension_ = "wf_dimension_";
    public static final String prefix_wf_sheetrole_ = "wf_sheetrole_";
    public static final String prefix_sheet_ = "sheet_";
    public static final String prefix_sheet_waiting_ = "sheet_waiting_";
    public static final String prefix_sheet_mycreated_ = "sheet_mycreated_";
    public static final String prefix_sheet_mydealed_ = "sheet_mydealed_";

    /**
     * 进入流程分类管理主页面
     */
    public String toMain()
    {
	return this.findForward("wfSortFrame");
    }

    /**
     * 显示流程类型列表
     * 
     * @return
     */
    public String wfTypeList()
    {
	ver = WorkFlowServiceClient.clientInstance().getSortService();
	wfTypeList = new ArrayList<WfType>();
	List<WfSort> list = wfSortTreeImpl.getAllChildSort(wfSortId);
	try
	{
	    this.wfTypeList.addAll(ver.getWfTypeBySortId(wfSortId));
	} catch (RemoteException e)
	{
	    e.printStackTrace();
	}

	for (WfSort sort : list)
	{
	    try
	    {
		this.wfTypeList.addAll(ver.getWfTypeBySortId(sort.getId()));
	    } catch (RemoteException e)
	    {
		e.printStackTrace();
	    }
	}
	// Collections.sort(wfTypeList,new Comparator<WfType>(){
	//
	// public int compare(WfType o1, WfType o2) {
	// if (o1 != null && o2 != null) {
	// long t1 = o1.get
	// long t2 = o2.getCreateTime();
	// if (t1 > t2) {
	// return -1;
	// } else if(t1 < t2) {
	// return 1;
	// }
	// }
	// return 0;
	// }
	// });
	wfTypeList = PageLimitUtil.pageLimit(wfTypeList);
	return this.findForward("wfTypeList");
    }

    public String viewLeftTree()
    {
	return this.findForward("wfSortLeft");
    }

    /**
     * 获取流程分类树
     */
    public void getWfSortTree()
    {
	this.renderXML(wfSortTreeImpl.getChildXml(id));
    }

    /**
     * 进入添加或修改流程分类页面
     */
    public String toEditOrAddWfSort()
    {
	ver = WorkFlowServiceClient.clientInstance().getSortService();
	// 添加子节点
	if (wfSortPid != null && !"".equals(wfSortPid))
	{
	    wfSort = new WfSort();
	    if (type != null && type.equals("lower"))
	    {// 下一级
		wfSort.setPid(wfSortPid);
	    } else if (type != null && type.equals("current"))
	    {// 同级
		try
		{
		    WfSort ws = ver.getWfSortByid(this.wfSortPid);
		    wfSort.setPid(ws.getPid());
		} catch (RemoteException e)
		{
		    e.printStackTrace();
		}
	    }
	    wfSort.setCreateTime(TimeUtils.getCurrentTime());
	    return this.findForward("wfSortSave");
	}

	if (wfSortId == null)
	{
	    wfSort = new WfSort();
	    wfSort.setPid("0");
	    wfSort.setCreateTime(TimeUtils.getCurrentTime());
	} else
	{
	    try
	    {
		wfSort = ver.getWfSortByid(wfSortId);
	    } catch (RemoteException e)
	    {
		e.printStackTrace();
	    }
	}
	return this.findForward("wfSortSave");
    }

    /**
     * 保存流程分类
     */
    public String saveWfSort()
    {
	try
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    ver.saveWfSort(wfSort);
	    sign = true;
	} catch (RemoteException e)
	{
	    sign = false;
	    e.printStackTrace();
	}
	saveMenu_sort(wfSort);
	return this.findForward("wfSortSave");
    }

    /**
     * 给新增目录节点授权
     * 
     * @param menuIdList
     */
    public void addRoleAndMenu(List<String> menuIdList)
    {
	List<String> roleIdList = roleManagerService.getRoleIdByUserID(this.getUserSession().getPid());
	this.roleManagerService.addRoleMenu(roleIdList, menuIdList);
    }

    public RoleManagerService getRoleManagerService()
    {
	return roleManagerService;
    }

    public void setRoleManagerService(RoleManagerService roleManagerService)
    {
	this.roleManagerService = roleManagerService;
    }

    /**
     * 保存目录树； 添加流程分类时，需要更新“工单管理”和“流程管理”中的相应目录
     */
    public void saveMenu_sort(WfSort sort)
    {
	if (!"0".equals(sort.getPid()))
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    log.info("添加'" + sort.getCode() + "'相应目录树上节点（开始）.....");
	    List<String> menuIdList = new ArrayList<String>();
	    Long order = sort.getOrderby();
	    // String menuCode = null;
	    String menuCode_manage = null; // 流程版本
	    String menuCode_sheet = null; // 工单管理
	    String menuCode_sheet_waiting = null; // 待办工单
	    String menuCode_sheet_mycreated = null; // 我建立的工单
	    String menuCode_sheet_mydealed = null; // 我处理的工单

	    String newMenuCode_manage = null; // 流程版本(新)
	    String newMenuCode_sheet = null; // 工单管理(新)
	    String newMenuCode_sheet_waiting = null; // 待办工单(新)
	    String newMenuCode_sheet_mycreated = null; // 我建立的工单(新)
	    String newMenuCode_sheet_mydealed = null; // 我处理的工单
	    WfSort s = null;
	    try
	    {
		s = ver.getWfSortByid(sort.getPid());
	    } catch (RemoteException e1)
	    {
		e1.printStackTrace();
	    }
	    if (s != null && "0".equals(s.getPid()))
	    {
		menuCode_manage = prefix_wf_manage;
		menuCode_sheet = prefix_sheet;
		menuCode_sheet_waiting = prefix_sheet_waiting;
		menuCode_sheet_mycreated = prefix_sheet_mycreated;
		menuCode_sheet_mydealed = prefix_sheet_mydealed;
	    } else
	    {
		try
		{
		    WfSort pwfSort = ver.getWfSortByid(sort.getPid());
		    menuCode_manage = prefix_wf_manage_ + pwfSort.getCode();
		    menuCode_sheet = prefix_sheet_ + pwfSort.getCode();
		    menuCode_sheet_waiting = prefix_sheet_waiting_ + pwfSort.getCode();
		    menuCode_sheet_mycreated = prefix_sheet_mycreated_ + pwfSort.getCode();
		    menuCode_sheet_mydealed = prefix_sheet_mydealed_ + pwfSort.getCode();
		} catch (RemoteException e)
		{
		    e.printStackTrace();
		}
	    }

	    newMenuCode_manage = prefix_wf_manage_ + sort.getCode();
	    newMenuCode_sheet = prefix_sheet_ + sort.getCode();
	    newMenuCode_sheet_waiting = prefix_sheet_waiting_ + sort.getCode();
	    newMenuCode_sheet_mycreated = prefix_sheet_mycreated_ + sort.getCode();
	    newMenuCode_sheet_mydealed = prefix_sheet_mydealed_ + sort.getCode();

	    // 流程管理-》流程综合管理下添加工单目录树
	    MenuInfo pminfo_version = menuManagerService.getMenuByMark(menuCode_manage);
	    MenuInfo newMenu = new MenuInfo();
	    newMenu.setNodename(sort.getName());
	    newMenu.setNodetype(Long.parseLong("1"));
	    newMenu.setParentid(pminfo_version.getPid());
	    newMenu.setNodeurl("");
	    newMenu.setStatus(Long.parseLong("1"));
	    newMenu.setNodemark(newMenuCode_manage);
	    newMenu.setOpenway("1");
	    newMenu.setOrdernum(order);
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
	    log.info("添加流程版本控制下目录节点!");

	    // 工单管理下添加目录树
	    MenuInfo pminfo_sheet = menuManagerService.getMenuByMark(menuCode_sheet);
	    MenuInfo newMenu_sheet = menuManagerService.getMenuByMark(newMenuCode_sheet);
	    newMenu.setParentid(pminfo_sheet.getPid());
	    newMenu.setOrdernum(order + 2);
	    newMenu.setNodemark(newMenuCode_sheet);
	    newMenu.setNodeurl("sheet/baseInfoQuery.action?wfSortId=" + sort.getId());
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
	    log.info("添加工单管理下目录节点!");

	    // 待办工单下添加目录树
	    MenuInfo pminfo_sheet_waiting = menuManagerService.getMenuByMark(menuCode_sheet_waiting);
	    MenuInfo newMenu_sheet_waiting = menuManagerService.getMenuByMark(newMenuCode_sheet_waiting);
	    newMenu.setParentid(pminfo_sheet_waiting.getPid());
	    newMenu.setNodemark(newMenuCode_sheet_waiting);
	    newMenu.setNodeurl("sheet/myWaitingDealSheetQuery.action?wfSortId=" + sort.getId());
	    newMenu.setOrdernum(order);
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
	    log.info("添加待办工单下目录节点!");

	    // 我建立的工单下添加目录树
	    MenuInfo pminfo_sheet_mycreated = menuManagerService.getMenuByMark(menuCode_sheet_mycreated);
	    MenuInfo newMenu_sheet_mycreated = menuManagerService.getMenuByMark(newMenuCode_sheet_mycreated);
	    newMenu.setParentid(pminfo_sheet_mycreated.getPid());
	    newMenu.setNodemark(newMenuCode_sheet_mycreated);
	    newMenu.setNodeurl("sheet/myDealedSheetQuery.action?wfSortId=" + sort.getId() + "&type=mycreated");
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
	    log.info("添加我建立的工单下目录节点!");

	    // 我处理的工单下添加目录树
	    MenuInfo pminfo_sheet_mydealed = menuManagerService.getMenuByMark(menuCode_sheet_mydealed);
	    MenuInfo newMenu_sheet_mydealed = menuManagerService.getMenuByMark(newMenuCode_sheet_mydealed);
	    newMenu.setParentid(pminfo_sheet_mydealed.getPid());
	    newMenu.setNodemark(newMenuCode_sheet_mydealed);
	    newMenu.setNodeurl("sheet/myDealedSheetQuery.action?wfSortId=" + sort.getId() + "&type=mydealed");
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
	    log.info("添加我处理的工单下目录节点!");
	    this.addRoleAndMenu(menuIdList);
	}
    }

    /**
     * 删除流程分类(递归删除相应的子分类和流程类型)
     */
    public String delWfSort()
    {
	try
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    wfSort = ver.getWfSortByid(wfSortId);
	    ver.delWfSort(wfSortId);
	    List<WfSort> list = this.wfSortTreeImpl.getWfSortList(wfSortId);
	    for (WfSort wf : list)
	    {
		ver.delWfSort(wf.getId());
	    }
	    this.delMenu(wfSort.getCode());
	    sign = true;
	} catch (RemoteException e)
	{
	    sign = false;
	    e.printStackTrace();
	}
	return this.findForward("wfTypeList");
    }
    /**
     * 验证流程分类下是否存在流程,如果存在不允许删除流程分类
     * @throws IOException 
     */
    public void checkDelWfSort() throws IOException
    {
    	String wfSortId = StringUtils.checkNullString(this.getRequest().getParameter("wfSortId"));
        ver = WorkFlowServiceClient.clientInstance().getSortService();
    	try
		{
    		boolean checkFlag = true;
    		PrintWriter out = this.getResponse().getWriter();
			wfSort = ver.getWfSortByid(wfSortId);
			List<WfSort> list = this.wfSortTreeImpl.getWfSortList(wfSortId);
			if(list!=null&&list.size()>0)
			{
				checkFlag = false;
			}else{
				List<WfType> typeList = ver.getWfTypeBySortId(wfSortId);
				if(typeList!=null && typeList.size()>0)
				{
					checkFlag = false;
				}
			}
			out.print(checkFlag);
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
    }
    /**
     * 删除目录树； 删除流程分类时，需要更新“工单管理”和“流程管理”中的相应目录
     */
    public void delMenu(String code)
    {
	String menuCode_version = prefix_wf_manage_ + code;
	String newMenuCode_sheet = prefix_sheet_ + code;
	String newMenuCode_sheet_waiting = prefix_sheet_waiting_ + code;
	String newMenuCode_sheet_mycreated = prefix_sheet_mycreated_ + code;
	String newMenuCode_sheet_mydealed = prefix_sheet_mydealed_ + code;
	menuManagerService.deleteMenuByMark(menuCode_version);
	menuManagerService.deleteMenuByMark(newMenuCode_sheet);
	menuManagerService.deleteMenuByMark(newMenuCode_sheet_waiting);
	menuManagerService.deleteMenuByMark(newMenuCode_sheet_mycreated);
	menuManagerService.deleteMenuByMark(newMenuCode_sheet_mydealed);
    }

    /**
     * 进入添加或修改流程类型页面
     */
    public String toEditOrAddWfType()
    {
 	if (wfTypeId == null)
	{
	    wfType = new WfType();
	    wfType.setId(null);
	    wfType.setSortId(wfSortId);
	} else
	{
	    try
	    {
		ver = WorkFlowServiceClient.clientInstance().getSortService();
		wfType = ver.getWfTypeByid(wfTypeId);
	    } catch (RemoteException e)
	    {
		e.printStackTrace();
	    }
	}
	themeList = themeCacheService.getThemeList();
	return this.findForward("wfTypeSave");
    }

    /**
     * 保存流程类型
     */
    public String saveWfType()
    {
	try
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    String id = wfType.getId();
	    if (org.apache.commons.lang.StringUtils.isBlank(id)) {
	    	wfType.setId(UUIDGenerator.getId());
	    }
	    ver.saveWfType(wfType);
	    List<String> menuIdList;
	    if (wfType.getWfType()==1)
	    {
		menuIdList = wfSortMenuFix.saveMenu_type(wfType,wfType.getCode());
	    }else{
		menuIdList = wfSortMenuFree.saveMenu_type(wfType,wfType.getCode());
	    }
	    addRoleAndMenu(menuIdList);
	    this.sign = true;
	    if (org.apache.commons.lang.StringUtils.isBlank(id) && "JSP".equals(wfType.getFlowType())) {
	    	createWorkSheetTable(wfType);
	    	createModLogTable(wfType);
		}
	} catch (RemoteException e)
	{
	    this.sign = false;
	    e.printStackTrace();
	}
	return "result";
    }
    
    private void createWorkSheetTable(WfType wfType) {
    	String baseSchema = wfType.getCode();
    	String mainTable = "BS_F_" + baseSchema;
    	String mainSql = "create table "+mainTable+" (" +
		"  BASEID                VARCHAR2(50) not null primary key," +
		"  BASESCHEMA            VARCHAR2(255)," +
		"  BASENAME              VARCHAR2(255)," +
		"  BASESN                VARCHAR2(255)," +
		"  BASEENTRYID           VARCHAR2(50)," +
		"  BASECREATEDATE        NUMBER(15)," +
		"  BASESENDDATE          NUMBER(15)," +
		"  BASEACCEPTDATE        NUMBER(15)," +
		"  BASEFINISHDATE        NUMBER(15)," +
		"  BASECLOSEDATE         NUMBER(15)," +
		"  BASESTATUS            VARCHAR2(255)," +
//		"  BASESUMMARY           VARCHAR2(2000)," +
//		"  BASEITEMS             VARCHAR2(255)," +
//		"  BASEPRIORITY          VARCHAR2(255)," +
//		"  BASEACCEPTOUTTIME     NUMBER(15)," +
//		"  BASEDEALOUTTIME       NUMBER(15)," +
//		"  BASEDESCRIPTION       VARCHAR2(255)," +
//		"  BASECLOSESATISFY      VARCHAR2(255)," +
//		"  BASEATTACHGUID        VARCHAR2(255))" +
		"  BASETPLID             VARCHAR2(255)," +
		"  BASEISARCHIVE         VARCHAR2(255)," +
		"  BASEISTRUECLOSE       VARCHAR2(255)," +
		"  BASEWORKFLOWFLAG      NUMBER(15)," +
		"  BASECATAGORYNAME      VARCHAR2(255)," +
		"  BASECATAGORYID        VARCHAR2(255)," +
		"  BASECREATORFULLNAME   VARCHAR2(255)," +
		"  BASECREATORLOGINNAME  VARCHAR2(255)," +
		"  BASECREATORCONNECTWAY VARCHAR2(255)," +
		"  BASECREATORCORP       VARCHAR2(255)," +
		"  BASECREATORCORPID     VARCHAR2(255)," +
		"  BASECREATORDEP        VARCHAR2(255)," +
		"  BASECREATORDEPID      VARCHAR2(255)," +
		"  BASECREATORDN         VARCHAR2(255)," +
		"  BASECREATORDNID       VARCHAR2(255))";
    	try {
			DataAdapter qAdapter = new DataAdapter();
			qAdapter.execute(mainSql, null);
		} catch (Error e) {
		}
    }
    
    private void createModLogTable(WfType wfType) {
    	String baseSchema = wfType.getCode();
    	String modLogTable = "BS_F_" + baseSchema + "_FML";
    	String modLogSql = "create table "+modLogTable+"(" +
		"  ID         VARCHAR2(50) not null primary key," +
		"  BASEID     VARCHAR2(255)," +
		"  BASESCHEMA VARCHAR2(255)," +
		"  PHASENO    VARCHAR2(255)," +
		"  FIELDID    VARCHAR2(255)," +
		"  FIELDTYPE  VARCHAR2(255)," +
		"  FIELDVALUE clob," +
		"  MODIFYDATE NUMBER(15)," +
		"  DEALERID   VARCHAR2(255)," +
		"  DEALER     VARCHAR2(255)," +
		"  TASKID     VARCHAR2(255)," +
		"  FIELDCODE  VARCHAR2(255)," +
		"  ACTIONTYPE VARCHAR2(255)," +
		"  ACTIONNAME VARCHAR2(255)," +
		"  FIELDLABEL VARCHAR2(255)," +
		"  ORDERNUM   NUMBER(15)," +
		"  COLSPAN    NUMBER(15))";
    	try {
    		DataAdapter qAdapter = new DataAdapter();
			qAdapter.execute(modLogSql, null);
		} catch (Error e) {
		}
    }

    /**
     * 流程类型分类编号唯一验证
     * 
     * @throws IOException
     */
    public void wfTypeCheckUnique() throws IOException
    {
	String type = StringUtils.checkNullString(this.getRequest().getParameter("type"));
	String code = StringUtils.checkNullString(this.getRequest().getParameter("code"));
	boolean result = false;
	if (type != null && type.equals("wfType"))
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    result = ver.getWfTypeByCode(code) == null ? true : false;
	} else if ((type != null && type.equals("wfSort")))
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    result = ver.getWfSortByCode(code) == null ? true : false;
	}
	PrintWriter out = this.getResponse().getWriter();
	out.print(result);
    }

    /**
     * 删除流程类型
     */
    public String delWfType()
    {
	try
	{
	    ver = WorkFlowServiceClient.clientInstance().getSortService();
	    WfType wft = ver.getWfTypeByid(wfTypeId);
	    ver.delWfTypeById(wfTypeId);
	    this.delMenu(wft.getCode());
	    sign = true;
	    
	    templateService.delByBaseSchema(wft.getCode());
	} catch (RemoteException e)
	{
	    sign = false;
	    e.printStackTrace();
	}
	return "getWfTypeList";
    }

    public MenuManagerService getMenuManagerService()
    {
	return menuManagerService;
    }

    public void setMenuManagerService(MenuManagerService menuManagerService)
    {
	this.menuManagerService = menuManagerService;
    }

    public WfSort getWfSort()
    {
	return wfSort;
    }

    public boolean isSign()
    {
	return sign;
    }

    public void setSign(boolean sign)
    {
	this.sign = sign;
    }

    public void setWfSort(WfSort wfSort)
    {
	this.wfSort = wfSort;
    }

    public String getWfSortId()
    {
	return wfSortId;
    }

    public void setWfSortId(String wfSortId)
    {
	this.wfSortId = wfSortId;
    }

    public WfSortTreeImpl getWfSortTreeImpl()
    {
	return wfSortTreeImpl;
    }

    public String getWfTypeId()
    {
	return wfTypeId;
    }

    public void setWfTypeId(String wfTypeId)
    {
	this.wfTypeId = wfTypeId;
    }

    public void setWfSortTreeImpl(WfSortTreeImpl wfSortTreeImpl)
    {
	this.wfSortTreeImpl = wfSortTreeImpl;
    }

    public WfType getWfType()
    {
	return wfType;
    }

    public void setWfType(WfType wfType)
    {
	this.wfType = wfType;
    }

    public String getWfSortPid()
    {
	return wfSortPid;
    }

    public void setWfSortPid(String wfSortPid)
    {
	this.wfSortPid = wfSortPid;
    }

    public List<WfType> getWfTypeList()
    {
	return wfTypeList;
    }

    public void setWfTypeList(List<WfType> wfTypeList)
    {
	this.wfTypeList = wfTypeList;
    }

    public String getType()
    {
	return type;
    }

    public void setType(String type)
    {
	this.type = type;
    }

    public WfSortMenuService getWfSortMenuFree()
    {
	return wfSortMenuFree;
    }

    public void setWfSortMenuFree(WfSortMenuService wfSortMenuFree)
    {
	this.wfSortMenuFree = wfSortMenuFree;
    }

    public WfSortMenuService getWfSortMenuFix()
    {
	return wfSortMenuFix;
    }

    public void setWfSortMenuFix(WfSortMenuService wfSortMenuFix)
    {
	this.wfSortMenuFix = wfSortMenuFix;
    }

    public TemplateService getTemplateService()
    {
        return templateService;
    }

    public void setTemplateService(TemplateService templateService)
    {
        this.templateService = templateService;
    }

	/**
	 * @return the themeCacheService
	 */
	public ThemeCacheService getThemeCacheService()
	{
		return themeCacheService;
	}

	/**
	 * @param themeCacheService the themeCacheService to set
	 */
	public void setThemeCacheService(ThemeCacheService themeCacheService)
	{
		this.themeCacheService = themeCacheService;
	}

	/**
	 * @return the themeList
	 */
	public List<ThemeModel> getThemeList()
	{
		return themeList;
	}

	/**
	 * @param themeList the themeList to set
	 */
	public void setThemeList(List<ThemeModel> themeList)
	{
		this.themeList = themeList;
	}

}
