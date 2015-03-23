package com.ultrapower.wfinterface.services;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.wfinterface.core.extend.ReleaseServiceImpl;
import com.ultrapower.wfinterface.core.model.WfiDatain;
import com.ultrapower.wfinterface.core.service.IWorkflowService;

@Transactional
public class ReleaseProcessSheet{

	/**
	 * @param serSupplier
	 * @param serCaller
	 * @param callerPwd
	 * @param callTime
	 * @param opDetail
	 * @return result
	 */
	@Transactional
	public String newWorkSheet(String serSupplier, String serCaller,String callerPwd,String callTime,String opDetail) 
	{
		String result = "";
		IWorkflowService wfService = new ReleaseServiceImpl();
		WfiDatain serviceContext = new WfiDatain();
		serviceContext.setSersupplier(serSupplier);
		serviceContext.setSercaller(serCaller);
		serviceContext.setCalltime(callTime);
		serviceContext.setOpdetail(opDetail);
		try {
			result = wfService.call(serviceContext);
			if(result==null)
			{
				DataRow sheetInfo = wfService.getWorkflowContexts().get(0).getSheetInfo();
				String baseId = wfService.getWorkflowContexts().get(0).getBaseID();
				if(sheetInfo != null){
					String basesn = sheetInfo.getString("basesn");
					if(StringUtils.isNotBlank(basesn)){
						result = "0|" + basesn;
					}
				}
			}
			else
			{
				result = result;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("建单错误:");			
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 	连通测试 (isAlive)
	 */
	public String isAlive()
	{		
		return "true";
	}
}
