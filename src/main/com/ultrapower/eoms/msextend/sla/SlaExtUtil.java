package com.ultrapower.eoms.msextend.sla;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry; 

import org.apache.derby.tools.sysinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant;
import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestModel;
import com.ultrapower.eoms.msextend.serverQuest.service.ServerQuestService;
import com.ultrapower.eoms.ultrabpp.cache.manager.MetaCacheDBImpl;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultraduty.service.HolidayService;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.workflow.utils.ApplicationContextUtils;

/**
 * sla客户化规则类
 * @author zhangxuegang
 *
 */
public class SlaExtUtil {
	
	static Logger log = LoggerFactory.getLogger(SlaExtUtil.class);
	static HolidayService holidayService; 
	static DicManagerService dicManagerService;
	static ServerQuestService serverQuestService;
	static {
		holidayService = (HolidayService) ApplicationContextUtils.getBean("holidayService");
		dicManagerService = (DicManagerService)ApplicationContextUtils.getBean("dicManagerService");
		serverQuestService = (ServerQuestService)ApplicationContextUtils.getBean("serverQuestService");
	}
	
	
	
	/**
	 * sla处理逻辑,将受理时限和处理时限保存至表单字段
	 * @param commitCxt
	 * @return commitCxt 
	 */
	public static WorksheetCommitContext slaTimeDealLogic(WorksheetCommitContext commitCxt){
		String dtCode = "userisVip";
		String baseSchema = commitCxt.getBaseSchema(); //工单类型
		String actionName = commitCxt.getActionID(); //动作名称
		String actionType = commitCxt.getActionType();//动作类型
		String vipLevel = commitCxt.getFieldMap().get("viplevel"); //vip等级
		Map fieldMap = commitCxt.getFieldMap();//表单域 
		Long  CurrentTimeL = TimeUtils.getCurrentTime(); //当前时间的秒值
		String intoStepTime = TimeUtils.formatIntToDateStringT(CurrentTimeL); //进入工单的时间,获取当前 时间,返回格式为"yyyy-MM-dd"
		String CurrentTimeStr = TimeUtils.formatIntToDateString(CurrentTimeL); //进入工单的时间,获取当前时间,返回格式为"yyyy-MM-dd HH:mm:ss"
		boolean isHolidayOrWeekEnd = false; //判断当前时间是不是节假日(周末及节假日配置表)
		if(holidayService.getDateTypeByDate(intoStepTime,"")==2){
			isHolidayOrWeekEnd = true;
		};	
		
		if("CBD_INCIDENT".equals(baseSchema)){ 
		
	//		String IncidentPhenoClass = (String) fieldMap.get("IncidentPhenoClass");
			String IncidentPhenoClassID = (String) fieldMap.get("IncidentPhenoClassID");//获取工单现象分类
			//判断是不是受理建单环节的提交处理动作,如果,是计算受理时限并记录,将时间保存至工单公共表和对应的流程表中
			if("ASSIGNStep01".equals(actionName)&&"NEXT".equals(actionType)) 
			{
				//工单在退回后继续重新受理的时候,为防止通过退单方式来延迟超时时间的情况,受理时限不重新计算,这个时候要把受理的时间置为空
				if(!"".equals(commitCxt.getFieldMap().get("AcceptanceTimeLimit"))&&commitCxt.getFieldMap().get("AcceptanceTimeLimit")!=null){
					commitCxt.getFieldMap().put("BASEACCEPTDATE", "");
					commitCxt.getFieldMap().put("AcceptanceTime", "");
				}else{
					String acceptanceTimeLimit = getAcceptanceTimeLimit(CurrentTimeL,isHolidayOrWeekEnd,IncidentPhenoClassID,vipLevel,dtCode,commitCxt);
					commitCxt.getFieldMap().put("AcceptanceTimeLimit", acceptanceTimeLimit); 
					commitCxt.getFieldMap().put("BASEACCEPTOUTTIME", Long.toString(TimeUtils.formatDateStringToInt(acceptanceTimeLimit))); 
				}
			} 
			 
			//判断是不是处理环节的受理动作,如果是,计算处理时限,并将时间保存至工单公共表和对应的流程表中,如果已经记录了受理时间,则不再进行更新
			else if("AcceptedProcessing".equals(actionName)&&"ACCEPT".equals(actionType))
			{
				if(!"".equals(commitCxt.getFieldMap().get("AcceptanceTime"))&&commitCxt.getFieldMap().get("AcceptanceTime")!=null){
				}else{
				String detalTimeLimit = "";
				commitCxt.getFieldMap().put("AcceptanceTime", CurrentTimeStr);
				//查看工单是否记录了要求服务实施时间,如果记录了要求服务实施时间,处理时限按照要求服务实施时间计算
				if(!"".equals(commitCxt.getFieldMap().get("requireDealTime"))&&commitCxt.getFieldMap().get("requireDealTime")!=null){
				  Long requireDealTimeL = TimeUtils.formatDateStringToInt(commitCxt.getFieldMap().get("requireDealTime"));
				  detalTimeLimit = getDetalTimeLimit(requireDealTimeL,isHolidayOrWeekEnd,IncidentPhenoClassID,vipLevel,dtCode,commitCxt);
				}else{
				detalTimeLimit = getDetalTimeLimit(CurrentTimeL,isHolidayOrWeekEnd,IncidentPhenoClassID,vipLevel,dtCode,commitCxt);
				}
				commitCxt.getFieldMap().put("DealTimeLimit", detalTimeLimit); 
				commitCxt.getFieldMap().put("BASEACCEPTDATE", Long.toString(TimeUtils.formatDateStringToInt(CurrentTimeStr)));
				commitCxt.getFieldMap().put("BASEDEALOUTTIME", Long.toString(TimeUtils.formatDateStringToInt(detalTimeLimit)));
				}
			}
			
			//判断是不是处理环节的处理完成动作,如果是保存处理完成时间至工单公共表和对应的流程表中,如果已经记录了处理完成时间,则不再进行更新
			else if("ToFinishProcessing".equals(actionName)&&"NEXT".equals(actionType)){
				if(!"".equals(commitCxt.getFieldMap().get("DealTime"))&&commitCxt.getFieldMap().get("DealTime")!=null){
					log.info("处理时间已经存在,处理时间为："+commitCxt.getFieldMap().get("DealTime"));
				}else{
					log.info("处理时间不存在,记录新的处理时间："+CurrentTimeStr);
				commitCxt.getFieldMap().put("DealTime", CurrentTimeStr); 
				commitCxt.getFieldMap().put("BASEFINISHDATE", Long.toString(TimeUtils.formatDateStringToInt(CurrentTimeStr))); 
				}
			}
		} 
		
		else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			
//			String serviceCategory = (String) fieldMap.get("service_category"); 
			String serviceCategoryid = (String) fieldMap.get("service_categoryID"); //获取服务分类
			//判断是不是经过送审环节,如果经过送审,审核通过时,进入受理环节记录,计算受理时限,并将受理时限保存至工单公共表和对应的流程表中
			if("ToPassRequestAudit".equals(actionName)&&"NEXT".equals(actionType)){ 
				//工单在退回后继续重新受理的时候,为防止通过退单方式来延迟超时时间的情况,受理时限不重新计算,这个时候要把受理的时间置为空
				if(!"".equals(commitCxt.getFieldMap().get("AcceptanceTimeLimit"))&&commitCxt.getFieldMap().get("AcceptanceTimeLimit")!=null){
					commitCxt.getFieldMap().put("BASEACCEPTDATE", "");
					commitCxt.getFieldMap().put("AcceptanceTime", "");
				}else{
				String acceptanceTimeLimit = getSrAcceptanceTimeLimit(CurrentTimeL,isHolidayOrWeekEnd,serviceCategoryid,vipLevel,dtCode,commitCxt); 
				commitCxt.getFieldMap().put("AcceptanceTimeLimit", acceptanceTimeLimit); 
				commitCxt.getFieldMap().put("BASEACCEPTOUTTIME", Long.toString(TimeUtils.formatDateStringToInt(acceptanceTimeLimit))); 
				}
			}
			
			//如果没有经过送审环节,直接提交提交请求,计算受理时限,并将受理时限保存至工单公共表和对应的流程表中
			else if("ToRequest".equals(actionName)&&"NEXT".equals(actionType)){ 
				if(!"".equals(commitCxt.getFieldMap().get("AcceptanceTimeLimit"))&&commitCxt.getFieldMap().get("AcceptanceTimeLimit")!=null){
					commitCxt.getFieldMap().put("BASEACCEPTDATE", "");
				}else{
				String acceptanceTimeLimit = getSrAcceptanceTimeLimit(CurrentTimeL,isHolidayOrWeekEnd,serviceCategoryid,vipLevel,dtCode,commitCxt); 
				commitCxt.getFieldMap().put("AcceptanceTimeLimit", acceptanceTimeLimit); 
				commitCxt.getFieldMap().put("BASEACCEPTOUTTIME", Long.toString(TimeUtils.formatDateStringToInt(acceptanceTimeLimit))); 
				}
			}
			
			//判断是不是处理环节的受理动作,如果是,计算处理时限,并将时间保存至工单公共表和对应的流程表中,如果已经记录了受理时间,则不再进行更新
 			else if("ToAcceptDeal".equals(actionName)||"ToAcceptAccept".equals(actionName)&&"ACCEPT".equals(actionType)){ 
 				if("".equals(commitCxt.getFieldMap().get("AcceptanceTime"))||commitCxt.getFieldMap().get("AcceptanceTime")==null){
 				 String detalTimeLimit = "";
                 commitCxt.getFieldMap().put("AcceptanceTime", CurrentTimeStr);
                 //查看工单是否记录了要求服务实施时间,如果记录了要求服务实施时间,处理时限按照要求服务实施时间计算
                 if(!"".equals(commitCxt.getFieldMap().get("requireDealTime"))&&commitCxt.getFieldMap().get("requireDealTime")!=null){
                   Long requireDealTimeL = TimeUtils.formatDateStringToInt(commitCxt.getFieldMap().get("requireDealTime"));
                   detalTimeLimit = getSrDetalTimeLimit(requireDealTimeL,isHolidayOrWeekEnd,serviceCategoryid,vipLevel,dtCode,commitCxt);
                 }  
                 else{
                   detalTimeLimit = getSrDetalTimeLimit(CurrentTimeL,isHolidayOrWeekEnd,serviceCategoryid,vipLevel,dtCode,commitCxt);
                 }
				commitCxt.getFieldMap().put("DealTimeLimit", detalTimeLimit); 
				commitCxt.getFieldMap().put("BASEACCEPTDATE", Long.toString(TimeUtils.formatDateStringToInt(CurrentTimeStr))); 
				commitCxt.getFieldMap().put("BASEDEALOUTTIME", Long.toString(TimeUtils.formatDateStringToInt(detalTimeLimit))); 
 				}
			}
			//判断是不是处理环节的处理完成动作,如果是保存处理完成时间至工单公共表和对应的流程表中,如果已经记录了处理完成时间,则不再进行更新
			else if ("ToDealFinishFromDeal".equals(actionName)&&"NEXT".equals(actionType)){
				if(!"".equals(commitCxt.getFieldMap().get("DealTime2"))&&commitCxt.getFieldMap().get("DealTime2")!=null){
					log.info("处理时间已经存在,处理时间为："+commitCxt.getFieldMap().get("DealTime"));
				}else{
					log.info("处理时间不存在,记录新的处理时间："+CurrentTimeStr);
				commitCxt.getFieldMap().put("DealTime2", CurrentTimeStr); 
				commitCxt.getFieldMap().put("BASEFINISHDATE", Long.toString(TimeUtils.formatDateStringToInt(CurrentTimeStr))); 
				}
			}
		}
		
		return commitCxt;
	}

	/**
	 * 计算工单处理时限SR工单
	 * @param currentTimeL 当前时间
	 * @param isHolidayOrWeekEnd 是否周及节假日,走节假日配置规则
	 * @param serviceCategory 服务请求工单服务分类,走服务分类配置规则
	 * @param vipLevel vip的等级,非vip则为空,走不同等级vip配置规则
	 * @param dtCode  根据dtCode查询vip规则
	 * @return dealTimeLimit
	 */
	
	private static String getSrDetalTimeLimit(Long currentTimeL,boolean isHolidayOrWeekEnd, String serviceCategoryid,String vipLevel, String dtCode,WorksheetCommitContext commitCxt) {
		String responseTime = ""; //响应时间
		String processingTime ="";//处理时间
		String dealTimeLimit = ""; //处理时限
		String resolveTime = ""; //解决时间上限(不更换硬件),现所有工单以此时间计算
		String resolveTimeTwo= ""; //解决时间上限(更换硬件)
		ServerQuestModel serverQuestModel = serverQuestService.getServerQuestByPID(serviceCategoryid);
		//判断relovelTime是否未进行配置如果未配置赋默认值
		if(!"".equals(serverQuestModel.getDealtimelimit())&& serverQuestModel.getDealtimelimit()!=null){
			resolveTime = serverQuestModel.getDealtimelimit(); //解决时间上限
		}else{
			resolveTime ="5360000";
		}
		
		if(!"".equals(vipLevel)&&vipLevel!=null){
			double processingTimeF = 0;
			List<DicItem> diList =	dicManagerService.getDicItemByDicType(dtCode);
			String isVipConfRuleStr = "";
			for (DicItem dicItem : diList) {
				if(vipLevel.equals(dicItem.getDivalue())){
					isVipConfRuleStr = dicItem.getRemark();
				}
			}
			for (String TimeStr : isVipConfRuleStr.split(";"))
				if(TimeStr.split(":")[1].contains("/")){
					 processingTime = TimeStr.split(":")[1];
				}else{
					responseTime= TimeStr.split(":")[1];
				}
			
			if(!"".equals(processingTime)&&processingTime!=null){
				processingTimeF =(double)Integer.parseInt(processingTime.split("/")[0])/Integer.parseInt(processingTime.split("/")[1]);
			}
			//VIP获取处理时限（不更硬件）为空，不赋值
			DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");  
			if(serverQuestModel.getDealtimelimit()!=null||!serverQuestModel.getDealtimelimit().equals("")){
				commitCxt.getFieldMap().put("WorkingTimeLimit", df.format(Integer.parseInt(resolveTime)*processingTimeF)); 
			}
			//VIP获取处理时限（更换硬件）为空，不赋值
			resolveTimeTwo=serverQuestModel.getDealtimelimit2()==null?"":serverQuestModel.getDealtimelimit2();
			if(!"".equals(resolveTimeTwo)){
				commitCxt.getFieldMap().put("NoWorkingTimeLimit",df.format(Integer.parseInt(resolveTimeTwo)*processingTimeF));
			}
			dealTimeLimit = TimeUtils.formatIntToDateString((int)(currentTimeL+Integer.parseInt(resolveTime)*60*processingTimeF));
			
		}else{
		dealTimeLimit =  TimeUtils.formatIntToDateString(currentTimeL+Integer.parseInt(resolveTime)*60); //处理时限
		}
		return dealTimeLimit;
	}

	/**
	 * 计算工单受理时限SR工单  
	 * @param currentTimeL 进入处理/处理环节的时间,也就是工单到达的时间
	 * @param isHolidayOrWeekEnd 是否是周末及节假日,走节假日配置规则
	 * @param serviceCategory 服务请求工单服务分类,走服务分类配置规则
	 * @param vipLevel vip的等级,非vip则为空,走不同等级vip配置规则
	 * @param dtCode 根据dtCode查询vip规则
	 * @return acceptanceTimeLimit 服务请求受理时限
	 */
	
	private static String getSrAcceptanceTimeLimit(Long intoStepTimeL,boolean isHolidayOrWeekEnd, String serviceCategoryid, String vipLevel,String dtCode,WorksheetCommitContext commitCxt) {
		String acceptanceTimeLimit = "";//受理时限
		String responseTime ="";//响应时间
		String wokingStartTime = ""; //工作开始时间
		String wokingEndTime = "";//工作结束时间
		
		ServerQuestModel serverQuestModel = serverQuestService.getServerQuestByPID(serviceCategoryid);
		String currentTimeStr = TimeUtils.formatIntToDateString(intoStepTimeL);//获取当前时间,返回格式为"yyyy-MM-dd HH:mm:ss"
		
		//
		if(!"".equals(serverQuestModel.getWorkbegintime())&&serverQuestModel.getWorkbegintime()!=null) 	wokingStartTime= serverQuestModel.getWorkbegintime();
		else wokingStartTime="08:00:00";
		
		if(!"".equals(serverQuestModel.getWorkendtime())&&serverQuestModel.getWorkendtime()!=null) wokingEndTime = serverQuestModel.getWorkendtime();
		else wokingEndTime = "17:00:00";

		wokingStartTime =TimeUtils.formatIntToDateStringT(intoStepTimeL)+" "+wokingStartTime; //工作开始时间,返回格式为"yyyy-MM-dd HH:mm:ss"
		wokingEndTime = TimeUtils.formatIntToDateStringT(intoStepTimeL)+" "+wokingEndTime; //工作结束结束时间,返回格式为"yyyy-MM-dd HH:mm:ss"
		
		if(!"".equals(vipLevel)&&vipLevel!=null){ //判断请求人是否存在vip用户等级,如果存在用户等级,按照数据字典配置信息取vip配置规则
			String processingTime ="";
			double responeTimeF = 0;
			List<DicItem> diList =	dicManagerService.getDicItemByDicType(dtCode);
			String isVipConfRuleStr = "";
			for (DicItem dicItem : diList) {
				if(vipLevel.equals(dicItem.getDivalue())){
					isVipConfRuleStr = dicItem.getRemark();
				}
			}
			
			for (String TimeStr : isVipConfRuleStr.split(";"))
				if(TimeStr.split(":")[1].contains("/")){
					 processingTime = TimeStr.split(":")[1];
				}else{
					responseTime= TimeStr.split(":")[1];
				}
			
			if(!"".equals(processingTime)&&processingTime!=null){
				responeTimeF =(double)Integer.parseInt(processingTime.split("/")[0])/Integer.parseInt(processingTime.split("/")[1]);
			}
			commitCxt.getFieldMap().put("DaysResponseTime", responseTime); 
			//VIP非工作日响应时间和响应时间一致
			commitCxt.getFieldMap().put("NoWorkingDaysRespTime", responseTime); 
			acceptanceTimeLimit = TimeUtils.formatIntToDateString(intoStepTimeL+Integer.parseInt(responseTime)*60);
			
		}else{

			//如果是工作日,按照工作日响应时间计算,如果是非工作得到非工作日响应时间
			if(!isHolidayOrWeekEnd){
				if(!"".equals(serverQuestModel.getWorktime()) && serverQuestModel.getWorktime()!=null){
				responseTime =serverQuestModel.getWorktime();
				commitCxt.getFieldMap().put("DaysResponseTime", responseTime); 
				commitCxt.getFieldMap().put("NoWorkingDaysRespTime",serverQuestModel.getNoworktime()==null?"":serverQuestModel.getNoworktime()); 
				commitCxt.getFieldMap().put("WorkingTimeLimit", serverQuestModel.getDealtimelimit()==null?"":serverQuestModel.getDealtimelimit());
				commitCxt.getFieldMap().put("NoWorkingTimeLimit", serverQuestModel.getDealtimelimit2()==null?"":serverQuestModel.getDealtimelimit2());
				}else{
				responseTime="5360000";
//				commitCxt.getFieldMap().put("DaysResponseTime", responseTime);
				}
			}else{
				if(!"".equals(serverQuestModel.getNoworktime()) && serverQuestModel.getNoworktime()!=null){
				responseTime =serverQuestModel.getNoworktime();
				commitCxt.getFieldMap().put("DaysResponseTime", serverQuestModel.getWorktime()==null?"":serverQuestModel.getWorktime()); 
				commitCxt.getFieldMap().put("NoWorkingDaysRespTime",responseTime); 
				commitCxt.getFieldMap().put("WorkingTimeLimit", serverQuestModel.getDealtimelimit()==null?"":serverQuestModel.getDealtimelimit());
				commitCxt.getFieldMap().put("NoWorkingTimeLimit", serverQuestModel.getDealtimelimit2()==null?"":serverQuestModel.getDealtimelimit2());
				}else{
				responseTime ="5360000";
//				commitCxt.getFieldMap().put("NoWorkingDaysRespTime",responseTime);
				}
			}
			
			//判断提交时间是非节假日并且提交时间在工作时间的范围之内,则当天必须响应,响应时限为提交时间+响应时间
			if((!isHolidayOrWeekEnd)&&(intoStepTimeL>TimeUtils.formatDateStringToInt(wokingStartTime)&&intoStepTimeL<TimeUtils.formatDateStringToInt(wokingEndTime)))
			{
				acceptanceTimeLimit = TimeUtils.formatIntToDateString(intoStepTimeL+Integer.parseInt(responseTime)*60);
			}
			
			//判断提交时间是非节假日但是提交时间不在工作时间范围之内,则顺延至第二天工作时间(如果第二天是节假日日继续顺延至工作时间)响应,响应时限为顺延后的时间+响应时间
			if(((!isHolidayOrWeekEnd)&&!(intoStepTimeL>TimeUtils.formatDateStringToInt(wokingStartTime)&&intoStepTimeL<TimeUtils.formatDateStringToInt(wokingEndTime)))){
				String workStartDay = "";
				workStartDay = getNextWorkDay(intoStepTimeL)+" "+wokingStartTime.split(" ")[1];
				acceptanceTimeLimit =  TimeUtils.formatIntToDateString(TimeUtils.formatDateStringToInt(workStartDay)+Integer.parseInt(responseTime)*60);
			}
			
			//判断提交时间如果是节假日,走节假日时间配置
			if(isHolidayOrWeekEnd) 
			{
				acceptanceTimeLimit =  TimeUtils.formatIntToDateString(intoStepTimeL+Integer.parseInt(responseTime)*60);
			}
		}
		
		return acceptanceTimeLimit;
	}


	/**
	 * 计算工单受理时限INC工单
	 * @param intoStepTimeL 进入处理环节的时间,也就是工单到达的时间
	 * @param isHolidayOrWeekEnd 是否是周末及节假日,走节假日配置规则
	 * @param incidentPhenoClass 事件工单的现象分类,走现象分类配置规则
	 * @param vipLevel vip的等级,非vip则为空,走不同等级vip配置规则
	 * @param dtCode 根据dtCode查询vip规则
	 * @return acceptanceTimeLimit 受理时限
	 */
	private static String getAcceptanceTimeLimit(Long intoStepTimeL,boolean isHolidayOrWeekEnd,String IncidentPhenoClassID,String vipLevel,String dtCode,WorksheetCommitContext commitCxt) {

		String responseTime = ""; //响应时间
		String resolveUlNonRepHw = ""; //解决时间上限(不更换硬件)
		String resolveUlRepHw ="";//解决时间上限(更换硬件)
		String wokingStartTime = ""; //工作开始时间
		String wokingEndTime = "";//工作结束时间
		String acceptanceTimeLimit = ""; //受理时限
		//获取现象分类配置相关数据
		Map attrMap = new HashMap<String,String>();
		List<Map> treeDate = null;
		Map  treeDateMap = new HashMap<String,String>();
		attrMap.put("ID", IncidentPhenoClassID);
		try {
			treeDate = getTreeData(attrMap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		treeDateMap =treeDate.get(0);
		//如果没有配置工作开始时间和工作结束时间,设置工作开始时间和结束时间的默认值
		if(!"".equals(treeDateMap.get("PROPFIELD_20"))&& treeDateMap.get("PROPFIELD_20")!=null) wokingStartTime = (String) treeDateMap.get("PROPFIELD_20"); //工作开始时间
		else wokingStartTime = "08:00:00";
		
		if(!"".equals(treeDateMap.get("PROPFIELD_21"))&& treeDateMap.get("PROPFIELD_21")!=null) wokingEndTime = (String) treeDateMap.get("PROPFIELD_21"); //工作结束时间
		else wokingEndTime = "17:00:00";	
		
		String currentTimeStr = TimeUtils.formatIntToDateString(intoStepTimeL);//获取当前时间,返回格式为"yyyy-MM-dd HH:mm:ss"
		wokingStartTime =TimeUtils.formatIntToDateStringT(intoStepTimeL)+" "+wokingStartTime; //工作开始时间,返回格式为"yyyy-MM-dd HH:mm:ss"
		wokingEndTime = TimeUtils.formatIntToDateStringT(intoStepTimeL)+" "+wokingEndTime; //工作结束结束时间,返回格式为"yyyy-MM-dd HH:mm:ss"
		
		if(!"".equals(vipLevel)&&vipLevel!=null){ //判断请求人是否存在vip用户等级,如果存在用户等级,按照数据字典配置信息取vip配置规则
			String processingTime ="";
			double responeTimeF = 0;
			List<DicItem> diList =	dicManagerService.getDicItemByDicType(dtCode);
			String isVipConfRuleStr = "";
			for (DicItem dicItem : diList) {
				if(vipLevel.equals(dicItem.getDivalue())){
					isVipConfRuleStr = dicItem.getRemark();
				}
			}
			
			for (String TimeStr : isVipConfRuleStr.split(";"))
				if(TimeStr.split(":")[1].contains("/")){
					 processingTime = TimeStr.split(":")[1];
				}else{
					responseTime= TimeStr.split(":")[1];
//					commitCxt.getFieldMap().put("DaysResponseTime", responseTime); 
				}
			
			if(!"".equals(processingTime)&&processingTime!=null){
				responeTimeF =(double)Integer.parseInt(processingTime.split("/")[0])/Integer.parseInt(processingTime.split("/")[1]);
			}
			commitCxt.getFieldMap().put("DaysResponseTime", responseTime); 
			//VIP非工作日响应时间和响应时间一致
			commitCxt.getFieldMap().put("NoWorkingDaysRespTime", responseTime); 
			acceptanceTimeLimit = TimeUtils.formatIntToDateString(intoStepTimeL+Integer.parseInt(responseTime)*60);
			
		}else{
		//如果是工作日,按照工作日响应时间计算,如果是非工作日得到非工作日响应时间,判断响应时间是否配置如果未配置赋默认值
		if(!isHolidayOrWeekEnd){
			if(!"".equals(treeDateMap.get("PROPFIELD_16")) && treeDateMap.get("PROPFIELD_16")!=null){
			responseTime =(String) treeDateMap.get("PROPFIELD_16");
			commitCxt.getFieldMap().put("DaysResponseTime", responseTime); 
			commitCxt.getFieldMap().put("NoWorkingDaysRespTime", treeDateMap.get("PROPFIELD_17")==null?"":(String)treeDateMap.get("PROPFIELD_17")); 
			commitCxt.getFieldMap().put("WorkingTimeLimit", treeDateMap.get("PROPFIELD_17")==null?"":(String)treeDateMap.get("PROPFIELD_18"));
			commitCxt.getFieldMap().put("NoWorkingTimeLimit", treeDateMap.get("PROPFIELD_17")==null?"":(String)treeDateMap.get("PROPFIELD_19"));
			}else{   
			responseTime="5360000";
//			commitCxt.getFieldMap().put("DaysResponseTime", responseTime);
			}
		}else{
			if(!"".equals(treeDateMap.get("PROPFIELD_17")) && treeDateMap.get("PROPFIELD_17")!=null){
			responseTime =(String) treeDateMap.get("PROPFIELD_17");
			commitCxt.getFieldMap().put("DaysResponseTime",treeDateMap.get("PROPFIELD_17")==null?"":(String)treeDateMap.get("PROPFIELD_16")); 
			commitCxt.getFieldMap().put("NoWorkingDaysRespTime", responseTime); 
			commitCxt.getFieldMap().put("WorkingTimeLimit", treeDateMap.get("PROPFIELD_17")==null?"":(String)treeDateMap.get("PROPFIELD_18"));
			commitCxt.getFieldMap().put("NoWorkingTimeLimit", treeDateMap.get("PROPFIELD_17")==null?"":(String)treeDateMap.get("PROPFIELD_19"));
			}else{
			responseTime ="5360000";
//			commitCxt.getFieldMap().put("NoWorkingDaysRespTime", responseTime); 
			}
		}
		
		//判断提交时间是非节假日并且提交时间在工作时间的范围之内,则当天必须响应,响应时限为提交时间+响应时间
		if((!isHolidayOrWeekEnd)&&(intoStepTimeL>TimeUtils.formatDateStringToInt(wokingStartTime)&&intoStepTimeL<TimeUtils.formatDateStringToInt(wokingEndTime)))
		{
			acceptanceTimeLimit = TimeUtils.formatIntToDateString(intoStepTimeL+Integer.parseInt(responseTime)*60);
		}
		
		//判断提交时间是非节假日但是提交时间不在工作时间范围之内,则顺延至第二天工作时间(如果第二天是节假日日继续顺延至工作时间)响应,响应时限为顺延后的时间+响应时间
		if(((!isHolidayOrWeekEnd)&&!(intoStepTimeL>TimeUtils.formatDateStringToInt(wokingStartTime)&&intoStepTimeL<TimeUtils.formatDateStringToInt(wokingEndTime)))){
			String workStartDay = "";
			workStartDay = getNextWorkDay(intoStepTimeL)+" "+wokingStartTime.split(" ")[1];
			acceptanceTimeLimit =  TimeUtils.formatIntToDateString(TimeUtils.formatDateStringToInt(workStartDay)+Integer.parseInt(responseTime)*60);
		}
		
		//判断提交时间如果是节假日,走节假日时间配置
		if(isHolidayOrWeekEnd) 
		{
			acceptanceTimeLimit =  TimeUtils.formatIntToDateString(intoStepTimeL+Integer.parseInt(responseTime)*60);
		}
		
		}
		return acceptanceTimeLimit;
	}


	/**
	 * 查询下一天是否是工作日,如果是直接返回,如果不是递归查询,直到找到下一个工作日
	 * @param intoStepTimeL
	 * @return
	 */
	private static String getNextWorkDay(Long intoStepTimeL) {
		intoStepTimeL = 86400+intoStepTimeL;
		if(holidayService.getDateTypeByDate(TimeUtils.formatIntToDateStringT(intoStepTimeL),"")==1){
			return TimeUtils.formatIntToDateStringT(intoStepTimeL);
		}else{
			return getNextWorkDay(intoStepTimeL);
		}
	}
	
	//根据传的日期，查询最晚参与评审时间（评审会时间前推 2个工作日）
	public static String getLastRevTimeWorkDay(Long intoStepTimeL) {
		intoStepTimeL = intoStepTimeL-86400;
		if(holidayService.getDateTypeByDate(TimeUtils.formatIntToDateStringT(intoStepTimeL),"")==1){
			Long a= intoStepTimeL-86400;
			if(holidayService.getDateTypeByDate(TimeUtils.formatIntToDateStringT(a),"")!=1){
				getLastRevTimeWorkDay(a);
			}
			return TimeUtils.formatIntToDateStringT(a);
		}else{
			return getLastRevTimeWorkDay(intoStepTimeL);
		}
		
	}
	//前推一个工作日
	public static String getLastAcceptTimeWorkDay(Long intoStepTimeL) {
		intoStepTimeL = intoStepTimeL-86400;
		if(holidayService.getDateTypeByDate(TimeUtils.formatIntToDateStringT(intoStepTimeL),"")==1){
			return TimeUtils.formatIntToDateStringT(intoStepTimeL);
		}else{
			return getLastAcceptTimeWorkDay(intoStepTimeL);
		}
	}
	

	/**
	 * 计算工单处理时限INC工单
	 * @param currentTimeL 当前时间
	 * @param isHolidayOrWeekEnd 是否是周末及节假日,走节假日配置规则
	 * @param incidentPhenoClass 事件工单的现象分类,走现象分类配置规则
	 * @param vipLevel vip的等级,非vip则为空,走不同等级vip配置规则
	 * @param dtCode  根据dtCode查询vip规则
	 * @return dealTimeLimit 处理时限
	 */
	
	private static String getDetalTimeLimit(Long currentTimeL,boolean isHolidayOrWeekEnd, String incidentPhenoClassID,String vipLevel,String dtCode,WorksheetCommitContext commitCxt) {
		String responseTime = ""; //响应时间
		String processingTime ="";//处理时间
		String dealTimeLimit = ""; //处理时限
		String resolveTime = ""; //解决时间上限(不更换硬件),现所有工单以此时间计算
		String resolveTimeTwo = ""; //解决时间上限(更换硬件)
		Map attrMap = new HashMap<String,String>();
		List<Map> treeDate = null;
		Map  treeDateMap = new HashMap<String,String>();
		attrMap.put("ID", incidentPhenoClassID);
		try {
			treeDate = getTreeData(attrMap);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		treeDateMap =treeDate.get(0);
		
		//判断relovelTime是否未进行配置如果未配置赋默认值
		if(!"".equals(treeDateMap.get("PROPFIELD_18"))&& treeDateMap.get("PROPFIELD_18")!=null){
			resolveTime = (String) treeDateMap.get("PROPFIELD_18"); //解决时间上限
		}else{
			resolveTime ="5360000";
		}
		//解决时间上限(更换硬件)工单显示时候用
		if(!"".equals(treeDateMap.get("PROPFIELD_19"))&& treeDateMap.get("PROPFIELD_19")!=null){
			resolveTimeTwo = (String) treeDateMap.get("PROPFIELD_19"); //解决时间上限
		}
		if(!"".equals(vipLevel)&&vipLevel!=null){
			double processingTimeF = 0;
			List<DicItem> diList =	dicManagerService.getDicItemByDicType(dtCode);
			String isVipConfRuleStr = "";
			for (DicItem dicItem : diList) {
				if(vipLevel.equals(dicItem.getDivalue())){
					isVipConfRuleStr = dicItem.getRemark();
				}
			}
			for (String TimeStr : isVipConfRuleStr.split(";"))
				if(TimeStr.split(":")[1].contains("/")){
					 processingTime = TimeStr.split(":")[1];
				}else{
					responseTime= TimeStr.split(":")[1];
				}
			
			if(!"".equals(processingTime)&&processingTime!=null){
				processingTimeF =(double)Integer.parseInt(processingTime.split("/")[0])/Integer.parseInt(processingTime.split("/")[1]);
				
			}
			DecimalFormat   df   =new   java.text.DecimalFormat("#0.00");  
			//VIP获取处理解决时间上限(不更换硬件)为空，则不赋值，
			String reTime=(String) treeDateMap.get("PROPFIELD_18")==null?"":(String) treeDateMap.get("PROPFIELD_18");
			if(!reTime.equals("")){
				commitCxt.getFieldMap().put("WorkingTimeLimit", df.format(Integer.parseInt(resolveTime)*processingTimeF));
			}
			//VIP解决时间上限(更换硬件)为空，则不赋值，
			if(!"".equals(resolveTimeTwo)){
				commitCxt.getFieldMap().put("NoWorkingTimeLimit",df.format(Integer.parseInt(resolveTimeTwo)*processingTimeF));
			}
			dealTimeLimit = TimeUtils.formatIntToDateString((int)(currentTimeL+Integer.parseInt(resolveTime)*60*processingTimeF));
			
		}else{
		dealTimeLimit =  TimeUtils.formatIntToDateString(currentTimeL+Integer.parseInt(resolveTime)*60); //处理时限
		}
		return dealTimeLimit;
	
	}
	
	
	/**
	 * 根据传入字段取得List<Map>数据,Map字段名称为key,内容为value的数据
	 * @param conditionMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static List<Map> getTreeData(Map<String,String> conditionMap) throws UnsupportedEncodingException{
		QueryAdapter query;
		List<Map> list = new ArrayList<Map>();
			String querySql = "select * from bs_t_sm_commonTree where 1=1";
			if(conditionMap != null){
				String conStr = "";
				for(String key:conditionMap.keySet()){
					String value = java.net.URLDecoder.decode(conditionMap.get(key), "UTF-8");
					if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
						String[] values = value.split(",");
						for(String vl:values){
							if(StringUtils.isNotBlank(vl)){
								if(!conStr.equals("")){
									conStr += " or ";
								}
								conStr += key + " = '" +  vl + "' ";
							}
						}
					}
				}
				querySql += " and (" + conStr + ")";
			}
			query = new QueryAdapter();
			DataTable dt = query.executeQuery(querySql);
			for(Object obj:dt.getRowList()){
				DataRow dr = (DataRow)obj;
				list.add(dr.getRowHashMap());
			}
		return list;
	}
}
