package com.ultrapower.eoms.ftrmaintain.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ftrmaintain.model.SysParam;
import com.ultrapower.eoms.ftrmaintain.service.IndexManagerService;
import com.ultrapower.eoms.fulltext.common.cache.SystemContext;
import com.ultrapower.eoms.fulltext.control.IndexScheduler;
import com.ultrapower.eoms.fulltext.control.InitController;
import com.ultrapower.eoms.fulltext.control.ManualMaintain;
import com.ultrapower.eoms.fulltext.model.IndexCategory;

public class IndexManagerAction extends BaseAction {
	private IndexManagerService indexManagerService;
	private List<SysParam> paramInfo;
	private Map<String,String> phyCategory;
	private boolean completeMaintain = true;
	public String loadInfo()
	{
		paramInfo = indexManagerService.getSystemParam();
		if(IndexScheduler.getScheduler().isRunning())
		{
			this.getRequest().setAttribute("mainThreadStatus", true);
		}
		else
		{
			this.getRequest().setAttribute("mainThreadStatus", false);
		}
		List<String> keys = SystemContext.getAllPhysicCategoryKey();
		int len = 0;
		if(keys!=null)
		{
			len = keys.size();
			phyCategory = new HashMap<String,String>();
		}
		IndexCategory category;
		for(int i=0;i<len;i++)
		{
			category = SystemContext.getPhysicalCategory(keys.get(i));
			if(category!=null)
			{
				phyCategory.put(keys.get(i), category.getDisplayName());
			}
		}
		if("no".equals(SystemContext.getSysParameter("isLocalInitRMI")))
		{
			IndexScheduler scheduler = IndexScheduler.getScheduler();
			completeMaintain = scheduler.isCompleteLastMaintain();
		}
		return findForward("indexMaintain");
	}
	
	public String maintain()
	{
		String startTime = this.getRequest().getParameter("startTime");
		String endTime = this.getRequest().getParameter("endTime");
		String method = this.getRequest().getParameter("method");
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		if("manual".equals(method))
		{
			long startTime2 = -1;
			if(startTime==null || "".equals(startTime) || "-1".equals(startTime))
			{
				startTime2 = -1;
			}
			else
			{
				try {
					startTime2 = sdf.parse(startTime).getTime()/1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(endTime!=null && !"".equals(endTime))
			{
				long endTime2 = -1;
				try {
					endTime2 = sdf.parse(endTime).getTime()/1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String idstr = getRequest().getParameter("maintainCaIds");
				List<String> caIdLst = null;
				if(idstr!=null)
				{
					caIdLst = new ArrayList<String>();
					String[] idArr = idstr.split("&comm;");
					for(int i=0;i<idArr.length;i++)
					{
						caIdLst.add(idArr[i]);
					}
				}
				ManualMaintain mm = new ManualMaintain();
				mm.manualMaintain(startTime2,endTime2,caIdLst);
			}
		}
		else if("auto_cycle".equals(method))
		{
			IndexScheduler scheduler = IndexScheduler.getScheduler();
			if(!scheduler.isRunning())
			{
				scheduler.start();
			}
		}
		else if("stop_auto_cycle".equals(method))
		{
			IndexScheduler scheduler = IndexScheduler.getScheduler();
			if(scheduler.isRunning())
			{
				scheduler.stopSchedule();
			}
		}
		return this.findRedirect("loadInfo");
	}
	
	public void initSystem()
	{
		InitController controller = InitController.getInstance();
    	boolean b1 = controller.initSystem();
    	boolean b2 = controller.initReader();
		String result = "false";
    	if(b1 && b2)
		{
			result = "true";
		}
    	try {
			this.getResponse().getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setIndexManagerService(IndexManagerService indexManagerService) {
		this.indexManagerService = indexManagerService;
	}

	public List<SysParam> getParamInfo() {
		return paramInfo;
	}

	public Map<String, String> getPhyCategory() {
		return phyCategory;
	}

	public boolean isCompleteMaintain() {
		return completeMaintain;
	}

	public void setCompleteMaintain(boolean completeMaintain) {
		this.completeMaintain = completeMaintain;
	}
	
}
