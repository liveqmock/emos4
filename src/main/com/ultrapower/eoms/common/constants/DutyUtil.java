package com.ultrapower.eoms.common.constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bmc.thirdparty.org.apache.commons.lang.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： yhg
 * @date： 2010-5-12 下午05:44:19
 * 当前版本：
 * 摘要: 定义公用方法的类
 */
public class DutyUtil {
	
	/**
	 * 排班规则放入map
	 * @return
	 */
	public static Map<Long , String> getAutoRules(){
		Map<Long,String> cMap = new LinkedHashMap<Long,String>(7);
		cMap.put(-1L, "请选择排班规则");
		cMap.put(0L, DutyConstants.YINYONG);
		cMap.put(1L, DutyConstants.MOBAN);
		cMap.put(2L, DutyConstants.LUNXIU);
		cMap.put(3L, DutyConstants.RENYUAN);
		cMap.put(4L, DutyConstants.BANCI);
		cMap.put(5L, DutyConstants.SHANCHU);
		cMap.put(6L, DutyConstants.GIVEN);
		return cMap;
	}
	/**
	 * 将字符串数组以逗号连接成字符串
	 * @param args
	 * @return
	 */
	public static String toString(String[] args) {
		StringBuffer sbf = new StringBuffer();
		if(args != null && args.length>0){
			for (int i = 0; i < args.length; i++) {
				sbf.append(args[i]).append(",");
			}
			if (sbf.length() > 0) {
				sbf.deleteCharAt(sbf.length() - 1);
			}
		}
		return sbf.toString();
	}
	/**
	 * 自动排班重新组合获得页面的参数数据
	 * 将数据中的数据每num个分割开，重新组成数组，放入list中
	 * @param args
	 * @param num
	 * @return
	 */
	public static List<String[]> subArray(String[] args,int num){
		List<String[]> list = new ArrayList<String[]>();
		for(int m = 0;m<args.length;m++){
			String[] newargs = args[m].split(",");
			String[] newArray = null;
			int mm = (newargs.length) / num;
			for(int j = 0 ; j < mm ; j++){
				newArray = new String[num]; 
				int k = j * num;
				int toal = k + num;
				int f = 0;
				for (int i = k; i < toal; i++) {
					newArray[f] = newargs[i];
					f++;
				}
				list.add(newArray);
			}
		}
		return list;
	}
	/**
	 *  周
	 * @return
	 */
	public static Map<Long , String> getWeeks(){
		Map<Long,String> cMap = new LinkedHashMap<Long,String>(7);
		cMap.put(1L, "星期一");
		cMap.put(2L, "星期二");
		cMap.put(3L, "星期三");
		cMap.put(4L, "星期四");
		cMap.put(5L, "星期五");
		cMap.put(6L, "星期六");
		cMap.put(7L, "星期日");
		return cMap;
	}
	
	/**
	 * 得到当前年的前后5年
	 * return 
	 */
	public static List<String> getYears(){
		List years = new ArrayList();
		Calendar c = Calendar.getInstance();
		Integer curYear = c.get(Calendar.YEAR);
		for(int i=5; i>=1; i--){
			years.add((curYear+i)+"");
		}
		years.add(curYear+"");
		for(int i=1; i<=5; i++){
		   years.add((curYear-i)+"");
		}
		return years;
	}
	/**
	 * 得到12月份
	 * @return
	 */
	public static Map<String,Integer> getMonths(){
		Map<String,Integer> cMap = new LinkedHashMap<String,Integer>(12);
		for(int i=1; i<=12; i++){
			String k = i < 10 ? "0"+i : ""+i;
			cMap.put(k, i);
		}
		return cMap;
	}
	/**
	 * 根据年月取得天数
	 * @return
	 */
	public static Map<String,Integer> getDays(){
		Map<String,Integer> cMap = new LinkedHashMap<String,Integer>(31);
		for(int i=1; i<=31; i++){
			String k = i < 10 ? "0"+i : ""+i;
			cMap.put(k, i);
		}
		return cMap;
	}
	/**
	 * 判断值是否为空
	 * @param objs
	 * @return
	 */
	public static boolean isNull(Object... objs) {
		if (null != objs) {
			for (Object obj : objs) {
				if (null == obj || ("").equals(obj)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 取得值班状态集合
	 * @param state
	 * @return stateName
	 */
	public static Map<Long, String> getDutyCalendarState() {
		Map<Long, String> map = new LinkedHashMap<Long, String>();
		map.put(DutyConstants.BOTHNOTAPPLY, DutyConstants.BOTHNOTAPPLY_NAME);//双方均未提出交接班申请
		map.put(DutyConstants.OFFPERAPPLY, DutyConstants.OFFPERAPPLY_NAME);//交班方提出申请等待接班方确认
		map.put(DutyConstants.ONPERAPPLY, DutyConstants.ONPERAPPLY_NAME);//接班方提出申请等待交班方确认
		map.put(DutyConstants.CONFIRM, DutyConstants.CONFIRM_NAME);//对方已经确认
		map.put(DutyConstants.OFFPERREFUS, DutyConstants.OFFPERREFUS_NAME);//交班方拒绝
		map.put(DutyConstants.ONPERREFUS, DutyConstants.ONPERREFUS_NAME);//接班方拒绝
		map.put(DutyConstants.OFFPERNOTON, DutyConstants.OFFPERNOTON_NAME);//已接班未提出交班申请
		map.put(DutyConstants.OFFPER, DutyConstants.OFFPER_NAME);//已交班
		map.put(DutyConstants.OFFPERNO, DutyConstants.OFFPERNO_NAME);//已交班，下一班未接班
		return map;
	}
	
	/**
	 * 取得值班状态
	 * @param state
	 * @return stateName
	 */
	public static String getDutyCalendarStateName(Long state) {
		String stateName = "";
		String temp = getDutyCalendarState().get(state);
		if (isNull(temp)) {
			stateName = temp;
		}
		return stateName;
	}
	
	/**
	 * 将字符串str转换为String类型
	 * @author magl
	 * @param str
	 * @return
	 */
	public static String toStr(String str) {
		if (str == null || str.equals("null")) {
			str = "";
		} else {
			str = str.trim();
		}
		return str;
	}
	
	/**
	 * 取得标志：是，否
	 * @param flag 标志位：true：1,是 0,否 ; false: 1,否 0,是
	 * 。排列顺序为0,1
	 * @return
	 */
	public static Map<Integer, String> getFalseOrTrueInt(boolean flag){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if (flag) {
			map.put(0, DutyConstants.NO);
			map.put(1, DutyConstants.YES);
		} else {
			map.put(0, DutyConstants.YES);
			map.put(1, DutyConstants.NO);
		}
		return map;
	}
	
	/**
	 * 取得标志：是，否
	 * @param flag 标志位：true：1,是 0,否 ; false: 1,否 0,是
	 * 。排列顺序为1,0
	 * @return
	 */
	public static Map<Integer, String> getTrueOrFalseInt(boolean flag){
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if (flag) {
			map.put(1, DutyConstants.YES);
			map.put(0, DutyConstants.NO);
		} else {
			map.put(1, DutyConstants.NO);
			map.put(0, DutyConstants.YES);
		}
		return map;
	}
	
	/**
	 * 取得标志：是，否
	 * @param flag 标志位：true：1,是 0,否 ; false: 1,否 0,是
	 * @return
	 */
	public static Map<Long, String> getTrueOrFalseLong(boolean flag){
		Map<Long, String> map = new LinkedHashMap<Long, String>();
		if (flag) {
			map.put(1L, DutyConstants.YES);
			map.put(0L, DutyConstants.NO);
		} else {
			map.put(1L, DutyConstants.NO);
			map.put(0L, DutyConstants.YES);
		}
		return map;
	}
	
	/**
	 * 取得标志位名称：是，否
	 * @param type key值
	 * @param flag 标示 true：1,是 0,否 ; false: 1,否 0,是
	 * @return
	 */
	public static String getTrueOrFalseName(int type, boolean flag) {
		String name = "";
		String temp = getTrueOrFalseInt(flag).get(type);
		if (isNull(temp)) {
			name = temp;
		} else {
			name = DutyConstants.NO;
		}
		return name;
	}
	
	/**
	 * 取得标志位名称：是，否
	 * @param type key值
	 * @param flag 标示 true：1,是 0,否 ; false: 1,否 0,是
	 * @return
	 */
	public static String getTrueOrFalseName(Long type, boolean flag) {
		String name = "";
		String temp = getTrueOrFalseLong(flag).get(type);
		if (isNull(temp)) {
			name = temp;
		}
		return name;
	}
	
	/**
	 * 取得值班日志类型集合
	 * 1：普通日志，2：附加日志，3：作业计划，4：工单
	 * @return
	 */
	public static Map<Integer, String> getLogType() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(1, DutyConstants.GENERALLOG);
		map.put(2, DutyConstants.ADDITIONALLOG);
		map.put(3, DutyConstants.SCHEDULE);
		map.put(4, DutyConstants.WORKORDER);
		return map;
	}
	
	/**
	 * 取得值班日志中日志类型名称，1：普通日志，2：附加日志，3：作业计划，4：工单
	 * @param type
	 * @return
	 */
	public static String getLogTypeName(int type) {
		String name = "";
		String temp = getLogType().get(type);
		if (isNull(temp)) {
			name = temp;
		}
		return name;
	}
	
	/**
	 * 激活停用
	 * @return
	 */
	public static Map<Integer,String> getActive(){
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(2);
		cMap.put(1, DutyConstants.ACTIVE_HUO);
		cMap.put(0, DutyConstants.ACTIVE_TING);
		return cMap;
	}

   /**
	 * 年计划周期 0：年，1：半年，2：季度，3：月 放入map
	 * @return
	 */
	public static Map<Long , String> getYearCycle(){
		Map<Long,String> cMap = new LinkedHashMap<Long,String>(4);
		cMap.put(0L, DutyConstants.YEAR);
		cMap.put(1L, DutyConstants.HALFYEAR);
		cMap.put(2L, DutyConstants.SEASON);
		cMap.put(3L, DutyConstants.MONTH);
		return cMap;
	}
	/**
	 * 月计划周期 4：半月，5：周，6：日 放入map
	 * @return
	 */
	public static Map<Long , String> getMonthCycle(){
		Map<Long,String> cMap = new LinkedHashMap<Long,String>(3);
		cMap.put(4L, DutyConstants.HALFMONTH);
		cMap.put(5L, DutyConstants.WEEK);
		cMap.put(6L, DutyConstants.DAY);
		return cMap;
	}
	/**
	 * 月计划周期 0：年，1：半年，2：季度，3：月，4：半月，5：周，6：日 放入map
	 * @return
	 */
	public static Map<Long , String> getNotCycle(){
		Map<Long,String> cMap = new LinkedHashMap<Long,String>(7);
		cMap.put(0L, DutyConstants.YEAR);
		cMap.put(1L, DutyConstants.HALFYEAR);
		cMap.put(2L, DutyConstants.SEASON);
		cMap.put(3L, DutyConstants.MONTH);
		cMap.put(4L, DutyConstants.HALFMONTH);
		cMap.put(5L, DutyConstants.WEEK);
		cMap.put(6L, DutyConstants.DAY);
		return cMap;
	}
	/**
	 * 作业计划完成有限期限
	 * （1：1月、2：1周、3：3天、4：当天、5：无有效期）TIMELIMIT
	 */
	public static Map<Integer , String> getTimeLimitMap(){
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(5);
		cMap.put(1, DutyConstants.ONEMONTH);
		cMap.put(2, DutyConstants.ONEWEEK);
		cMap.put(3, DutyConstants.THREEDAYS);
		cMap.put(4, DutyConstants.CURRDAYS);
		cMap.put(5, DutyConstants.NODATE);
		return cMap;
	}
	/**
	 * 是否周末执行，1：是，0：否
	 * @return
	 */
	public static Map<Integer , String> getIsWeekendMap(){
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(1);
		cMap.put(1, "是");
		cMap.put(0, "否");
		return cMap;
	}
	/**
	 * 是否节假日执行，1：是，0：否
	 * @return
	 */
	public static Map<Integer , String> getIsHolidayMap(){
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(1);
		cMap.put(1, "是");
		cMap.put(0, "否");
		return cMap;
	}
	/**
	 * 是否巡检，1：是，0：否
	 * @return
	 */
	public static Map<Integer , String> getIsCheckMap(){
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(1);
		cMap.put(1, "是");
		cMap.put(0, "否");
		return cMap;
	}
	/**
	 * 执行方式（1：共同执行，2：分开执行）
	 * @return
	 */
	public static Map<Integer , String> getIsShareMap(){
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(1);
		cMap.put(1, "共同执行");
		cMap.put(2, "分开执行");
		return cMap;
	}
	
	/**
	 * 启用停用
	 * @param flag true 0：停用，1：启用
	 * @param flag false 1：停用，0：启用
	 * @return
	 */
	public static Map<Integer , String> getEnableAndDisableMap(boolean flag){
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(2);
		if (flag) {
			cMap.put(1, DutyConstants.ENABLE);
			cMap.put(0, DutyConstants.DISABLE);
		} else {
			cMap.put(0, DutyConstants.ENABLE);
			cMap.put(1, DutyConstants.DISABLE);
		}
		return cMap;
	}

	/**
	 * 值班室有无盲点值班
	 * 值班类型,0表示正常值班类型，1表示无盲点值班类型
	 * @return
	 */
	public static Map<Integer , String> getDutyTypeMap() {
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(2);
		cMap.put(0, DutyConstants.NORMALDUTY);
		cMap.put(1, DutyConstants.CONTINUOUSDUTY);
		return cMap;
	}
	
	/**
	 * 交接班人员类型1：只有主班人才能交接班；2：值班人都可以交接班；3：值班人必须交接班,4:自动交接
	 * 
	 * @return
	 */
	public static Map<Integer , String> getChiefTypeMap() {
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(2);
		cMap.put(1, DutyConstants.CHIEFTYPE_ONLYPRINCIPAL);
		cMap.put(2, DutyConstants.CHIEFTYPE_ALLONDUTY);
		cMap.put(3, DutyConstants.CHIEFTYPE_MUSTONDUTY);
		cMap.put(4, DutyConstants.CHIEFTYPE_AUTO);
		return cMap;
	}
	
	/**
	 * 交接班方式,1交接班无需确认，2交班方单向确认，3接班方单向确认，4交接方双向确认。
	 * 
	 * @return
	 */
	public static Map<Integer , String> getConfirmTypeMap() {
		Map<Integer,String> cMap = new LinkedHashMap<Integer,String>(2);
		cMap.put(1, DutyConstants.CONFIRMTYPE_OFFNO);
		cMap.put(2, DutyConstants.CONFIRMTYPE_OFFSIMPLE);
		cMap.put(3, DutyConstants.CONFIRMTYPE_ONSIMPLE);
		cMap.put(4, DutyConstants.CONFIRMTYPE_BOTH);
		return cMap;
	}
	
	public static String getCyclevalue(String cyclenum,String strvalue,int type){
		
		String checkmonth= "";
		String checkday = "";
		String checkweek = "";
		int num = Integer.parseInt(cyclenum);
		if(isNull(strvalue)){
			if(num != 5){
				if(num == 0 ||num == 1 ||num == 2){
					String thevalue = strvalue.split("=")[1];
					checkmonth = thevalue.substring(0, thevalue.length()-3);
					checkday = strvalue.split("=")[2];
				}else{
					checkday = strvalue.split("=")[1];
				}
			}else{
				checkweek = strvalue.split("=")[1];;
			}
		}
		String returnValue = ""; 
		if(type == 1){//月
			returnValue = checkmonth;
		}else if(type == 2){//日
			returnValue = checkday;
		}else if(type == 3){//周
			returnValue = checkweek;
		}
		return returnValue;
	
	}
	
	/**
	 * 取得作业计划状态Map（1：未填写，2：已填写，3：已完成）
	 * @return
	 */
	public static Map<Integer, String> getPlanExecStatusMap() {
		Map<Integer,String> statusMap = new LinkedHashMap<Integer,String>();
		statusMap.put(1, DutyConstants.PLAN_EXEC_NOFILLED);
		statusMap.put(2, DutyConstants.PLAN_EXEC_COMPLETED);
		statusMap.put(3, DutyConstants.PLAN_EXEC_CLOSED);
//		statusMap.put(4, DutyConstants.PLAN_EXEC_PREPAREAUDIT);
//		statusMap.put(5, DutyConstants.PLAN_EXEC_APPROVED);
//		statusMap.put(6, DutyConstants.PLAN_EXEC_NOAPPROVED);
		return statusMap;
	}
	
	/**
	 * 取得作业计划状态汉字名称（1：未填写，2：已完成，3：已关闭，4：待审核，5：审核通过，6：审核不通过）
	 * @param status
	 * @return
	 */
	public static String getPlanExecStatusName(int status) {
		return getPlanExecStatusMap().get(status);
	}
	/**
	 * 计划状态
	 * @return
	 */
	public static Map<Integer,String> getPlanStatus(){
		Map<Integer,String> map = new LinkedHashMap<Integer,String>(5);
		map.put(DutyConstants.PLAN_DRAFT, DutyConstants.PLAN_DRAFT_NAME);
		map.put(DutyConstants.PLAN_FORAPPROVE, DutyConstants.PLAN_FORAPPROVE_NAME);
		map.put(DutyConstants.PLAN_OVERAPPORVE, DutyConstants.PLAN_OVERAPPORVE_NAME);
		map.put(DutyConstants.PLAN_NOTAPPROVE, DutyConstants.PLAN_NOTAPPROVE_NAME);
//		map.put(DutyConstants.PLAN_USELESS, DutyConstants.PLAN_USELESS_NAME);
		
		return map;
	}
	
	/**
	 * 将List转化为Object[]{}
	 * @param list
	 * @return
	 */
	public static Object[] getObjects(List<Object> paramList) {
		Object[] objs = null;
		if (paramList != null) {
			int size = paramList.size();
			objs = new Object[size];
			for (int i = 0; i < size; i++) {
				objs[i] = paramList.get(i);
			}
		}
		return objs;
	}
	/**
     * 年份一样。月份不同，，月的最大天数
     * @param starttime
     * @param endtime
     * @return
     */
	public static int getMaxDays(String starttime,String endtime){
		int maxDays = 0;
		if(DutyUtil.isNull(starttime) && DutyUtil.isNull(endtime)){
			int begin = Integer.parseInt(starttime.split("-")[1]);
			int end = Integer.parseInt(endtime.split("-")[1]);
			if(end - begin > 2){
				maxDays = 31;
			}else if(end - begin == 0){
				maxDays = TimeUtils.getData(starttime.substring(0,7));
			}else{
				int beginMax = TimeUtils.getData(starttime.substring(0,7));
				int endMax = TimeUtils.getData(endtime.substring(0,7));
				maxDays = (endMax > beginMax) ? endMax : beginMax;
			}
		}
		return maxDays;
	}
	/**
	 * 给一个开始时间一个结束时间，判断能够选择的天
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public static List<Integer> getListdays(String starttime, String endtime) {
		List<Integer> listdays = new ArrayList();
		int startYear = TimeUtils.getYearOfDate(TimeUtils.formatStrToDate(starttime));
		int endYear = TimeUtils.getYearOfDate(TimeUtils.formatStrToDate(endtime));
		int startMonth = TimeUtils.getMonthOfDate(TimeUtils.formatStrToDate(starttime));
		int endMonth = TimeUtils.getMonthOfDate(TimeUtils.formatStrToDate(endtime));
		int startDay = TimeUtils.getDayOfDate(TimeUtils.formatStrToDate(starttime));
		int endDay = TimeUtils.getDayOfDate(TimeUtils.formatStrToDate(endtime));
		if(startYear == endYear){//同年
			if(startMonth == endMonth){//同月
				for(int i = startDay;i <= endDay;i++){
					listdays.add(i);
				}
			}else{
				int days = getMaxDays(starttime, endtime);
				for(int i = 1;i <= days;i++){
					listdays.add(i);
				}
			}
		}else{//不同年，一般非周期计划制定同年的
			for(int i = 1;i <= 31;i++){
				listdays.add(i);
			}
		}
		return listdays;
	}
	/**
	 * 去掉string 用“,分割的重复数据”
	 * @param strdata   拼接的字符串
	 * @param splitchar  分隔符
	 * @return
	 */
	public static String delSameData(String strdata,String splitchar){
		String newStr = "";
	    if(DutyUtil.isNull(strdata)&&DutyUtil.isNull(splitchar)){
	    	String[] oldStr = strdata.split(splitchar);
	    	List list = new ArrayList();
	    	for(String oldStrData : oldStr){
	    		list.add(oldStrData);
	    	}
	    	Set someSet = new HashSet(list);
	    	Iterator iterator = someSet.iterator(); 
            while(iterator.hasNext()){
            	newStr += iterator.next()+splitchar;
            }
            newStr = StringUtils.removeEnd(newStr, splitchar);
	    }
		return newStr;
	}
	public static void main(String[] args){
		System.out.println(DutyUtil.delSameData("北京,上海,重庆,北京,上海",","));
	}
}