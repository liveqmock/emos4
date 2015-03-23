package com.ultrapower.eoms.ultraduty.web;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.ultrapower.eoms.common.constants.DutyUtil;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultraduty.model.DutyHoliday;
import com.ultrapower.eoms.ultraduty.service.HolidayService;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： yhg
 * @date： 2010-5-7 下午04:08:04
 * 当前版本：
 * 摘要:
 */
public class HolidayAction extends BaseAction{
	private String pid;// 节假日主键ID
	private String holidayName;// 节假日名称
	private String startTime;// 节假日开始时间
	private String endTime;// 节假日结束时间
	private DutyHoliday dutyHoliday;// 节假日基本信息
	private List<DutyHoliday> dutyHolidayList;// 节假日列表
	private Map<Integer, String> enableOrDisableMap;//启用停用标志集合
	private List<String> listYears;//年份
	private Map<String,Integer> listMonths;//月份
	private String returnMessage;//弹出信息
	private String parafresh;//是否刷新父页面
	private String monthFirstDay;
	private String monthLastDay;
	private Long holidayFlag;
	
	private HolidayService holidayService; 
	
		/** 保存并提交节假日基本信息 */
	   public String saveDutyHoliday() {
		   holidayService.addHoliday(dutyHoliday, startTime, endTime);
		   returnMessage = "保存成功";
		   parafresh = "true";
		   return this.findForward("freshParent");
	   }
	   
	   /** @pdOid c108b6a5-d376-4f43-8323-c210d21546a5 */
	   public String getDutyHolidayId() {
	      // TODO: implement
		   return SUCCESS;
	   }
	   
	   /** 查询显示所有节假日信息列表 */
	   public String listDutyHoliday() {
		   dutyHolidayList = holidayService.listHoliday(holidayName, startTime, endTime,holidayFlag);
		   return this.findForward("dutyHolidayList");
	   }
	   
	   /** 
	    * 进入节假日修改功能页面
	    * @param pid 节假日主键
	    */
	   public String editDutyHoliday() {
		   enableOrDisableMap = DutyUtil.getEnableAndDisableMap(false);// 启用停用标志集合, 1,停用;0,启用
		   dutyHoliday = holidayService.getHolidayByid(pid);
		   return this.findForward("dutyHolidayEdit");
	   }
	   
	   /** 
	    * 根据日期进入节假日修改功能页面
	    * @param monthFirstDay 节假日日期
	    */
	   public String editDutyHolidayByDate() {
		   enableOrDisableMap = DutyUtil.getEnableAndDisableMap(false);// 启用停用标志集合, 1,停用;0,启用
		   dutyHoliday = holidayService.getHolidayByDate(monthFirstDay);
		   return this.findForward("dutyHolidayEdit");
	   }
	   
	   /** 
	    * 根据日期进入节假日修改功能页面验证
	    * @param monthFirstDay 节假日日期
	    */
	   public String editDutyHolidayByDateValidate() {
		   String flag = "";
		   dutyHoliday = holidayService.getHolidayByDate(monthFirstDay);
		   if(dutyHoliday!=null&&StringUtils.hasLength(dutyHoliday.getPid())){
			   flag = "hasHoliday";
		   }
		   this.renderText(flag);
		   return null;
	   }
	   
	   /** 
	    * 保存并提交节假日的修改内容
	    */
	   public String updateDutyHoliday() {
		   holidayService.updateHoliday(dutyHoliday);
		   returnMessage = "修改成功";
		   parafresh = "true";
		   return this.findForward("freshParent");
	   }
	   
	   /** 
	    * 删除选中的节假日信息
	    */
	   public String deleDutyHoliday() {
		   holidayService.delHoliday(pid);
		   return this.findRedirect("listDutyHoliday");
	   }
	   /** 
	    * 删除节假日信息
	    */
	   public String deleDutyHolidayById() {
		   holidayService.delHoliday(pid);
		   returnMessage = "删除成功";
		   parafresh = "true";
		   return this.findForward("freshParent");
	   }
	   
	   /** 进入节假日新增页面 */
	   public String enterNewDutyHoliday() {
		   enableOrDisableMap = DutyUtil.getEnableAndDisableMap(false);// 启用停用标志集合, 1,停用;0,启用
		   return this.findForward("dutyHolidayAdd");
	   }
	   
	   /**
	    * 打开某天添加节假日页面
	    * @return
	    */
	   public String dutyHolidayAddInDate(){
		   
		   enableOrDisableMap = DutyUtil.getEnableAndDisableMap(false);// 启用停用标志集合, 1,停用;0,启用
		   return SUCCESS;
	   }
	   
	   public String checkHoliday() {
		   String returnMessage = holidayService.checkHolidayIsExist(holidayName, startTime, endTime);
		   this.renderText(returnMessage);
		   return null;
	   }
	   
	   public String holidayEdit(){
		   
		   listYears = DutyUtil.getYears();
		   listMonths = DutyUtil.getMonths();
		   return SUCCESS;
	   }
	   
	   /**
	    * 返回节假日和非节假日json数组
	    * @return
	    */
	   public String listHolidayAndNot(){
		   
		   String isHolidays = holidayService.getHolidays(monthFirstDay,monthLastDay);
		   renderText(isHolidays);
		   return null;
	   }
	   
	   
	   /**
	    * 获取节假日AJAX，主要用于首页日历
	    * liuchuanzu
	    * @return
	    */
	   public void getHolidays(){
		   List<DutyHoliday> list = this.holidayService.listHoliday(null, startTime, endTime,null);
		   StringBuffer strBu = new StringBuffer();
		   strBu.append("<JsDate>");
		   for(DutyHoliday dh:list){
			   strBu.append("<Day>");
				   strBu.append("<Date>" + dh.getDateinfo() + "</Date>");
				   strBu.append("<Name>" + dh.getHolidayname() + "</Name>");
			   strBu.append("</Day>");
		   }
		   strBu.append("</JsDate>");
		   this.renderText(strBu.toString());
	   }
	   

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public DutyHoliday getDutyHoliday() {
		return dutyHoliday;
	}

	public void setDutyHoliday(DutyHoliday dutyHoliday) {
		this.dutyHoliday = dutyHoliday;
	}

	public List<DutyHoliday> getDutyHolidayList() {
		return dutyHolidayList;
	}

	public void setDutyHolidayList(List<DutyHoliday> dutyHolidayList) {
		this.dutyHolidayList = dutyHolidayList;
	}

	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Map<Integer, String> getEnableOrDisableMap() {
		return enableOrDisableMap;
	}

	public void setEnableOrDisableMap(Map<Integer, String> enableOrDisableMap) {
		this.enableOrDisableMap = enableOrDisableMap;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public String getParafresh() {
		return parafresh;
	}

	public List<String> getListYears() {
		return listYears;
	}

	public void setListYears(List<String> listYears) {
		this.listYears = listYears;
	}

	public Map<String, Integer> getListMonths() {
		return listMonths;
	}

	public void setListMonths(Map<String, Integer> listMonths) {
		this.listMonths = listMonths;
	}

	public String getMonthFirstDay() {
		return monthFirstDay;
	}

	public void setMonthFirstDay(String monthFirstDay) {
		this.monthFirstDay = monthFirstDay;
	}

	public String getMonthLastDay() {
		return monthLastDay;
	}

	public void setMonthLastDay(String monthLastDay) {
		this.monthLastDay = monthLastDay;
	}

	public Long getHolidayFlag() {
		return holidayFlag;
	}

	public void setHolidayFlag(Long holidayFlag) {
		this.holidayFlag = holidayFlag;
	}
	
}
