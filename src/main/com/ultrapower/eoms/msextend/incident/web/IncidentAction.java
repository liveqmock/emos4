package com.ultrapower.eoms.msextend.incident.web;

import java.util.List;

import net.sf.json.JSONArray;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultraduty.manager.HolidayImpl;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class IncidentAction extends BaseAction {
	private DicManagerService dicManagerService;
	private String dtcode;
	private HolidayImpl holidayImpl;
	private String strTime;
	private String endTime;
	/**
	 * 根据Dtcode和divalue返回字典,返回json
	 */
	public void getDicItemByValue() {
		List<DicItem> diList = dicManagerService.getDicItemByDicType(dtcode);
		renderText(JSONArray.fromObject(diList).toString());
	};

	/**
	 * 计算报修时间
	 */
	public void getRepairDays() {
		int  i=0;
		if(strTime==null||"".equals(strTime)||endTime==null||"".equals(endTime)){
			i=0;
		}else{
			i=holidayImpl.getWorkDaysByStartTimeAndEndTime(strTime,endTime);
		}
		renderText(i+"");
	};

	/**
	 *  知识库请求内容
	 */
	
	public String requestKmContent(){
		return SUCCESS;
	}
	

	public DicManagerService getDicManagerService() {
		return dicManagerService;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}

	public String getDtcode() {
		return dtcode;
	}

	public void setDtcode(String dtcode) {
		this.dtcode = dtcode;
	}


	public HolidayImpl getHolidayImpl() {
		return holidayImpl;
	}

	public void setHolidayImpl(HolidayImpl holidayImpl) {
		this.holidayImpl = holidayImpl;
	}
	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
