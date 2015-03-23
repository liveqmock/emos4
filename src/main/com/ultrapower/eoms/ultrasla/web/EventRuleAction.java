package com.ultrapower.eoms.ultrasla.web;

import com.ultrapower.eoms.common.core.util.TimeUtils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasla.model.EventHandlerComp;
import com.ultrapower.eoms.ultrasla.model.EventRule;
import com.ultrapower.eoms.ultrasla.service.IEventHandlerCompService;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;


public class EventRuleAction extends BaseAction{

	private EventRule eventRule;
	private IEventRuleService eventRuleService;
	private EventHandlerComp eventHandlerComp;
	private IEventHandlerCompService eventHandlerCompService;
	private String delIds;
	public String eventRuleList(){
		
		return SUCCESS;
	}	
	public String eventRuleSave() {
		String validstarttime = this.getRequest().getParameter("validstarttime");
		String validendtime = this.getRequest().getParameter("validendtime");
		
		String flag = null;
		if (eventRule != null) {
			eventRule.setUpdatetime(TimeUtils.getCurrentTime());
			if (eventRule.getPid() == null || "".equals(eventRule.getPid())) {
				eventRule.setCreatetime(TimeUtils.getCurrentTime());
			}
			eventRule.setValidstarttime(TimeUtils.formatDateStringToInt(validstarttime));
			eventRule.setValidendtime(TimeUtils.formatDateStringToInt(validendtime));
			Long defaultCheckType = eventRule.getDefaultchecktype();
			if(defaultCheckType == null) {
				eventRule.setDefaultchecktype(0L);
			}
			flag = eventRuleService.save(eventRule);
		}
		this.getRequest().setAttribute("parafresh", true);
		if (flag == null) {
			this.getRequest().setAttribute("returnMessage", "保存失败!");
		} else {
			this.getRequest().setAttribute("returnMessage", "保存成功!");
		}
		return "refresh";
	}
	public String editEventRule(){
		String id = this.getRequest().getParameter("id");
	    eventRule = eventRuleService.getById(id);
		String startTime = eventRule.getNoticestarttime();
		String endTime = eventRule.getNoticeendtime();
		long times = eventRule.getNoticetimes();
		this.getRequest().setAttribute("noticet", times);
		this.getRequest().setAttribute("stime", startTime);
		this.getRequest().setAttribute("etime", endTime);	
		long noticeinterval = eventRule.getNoticeinterval();			
		this.getRequest().setAttribute("noticeinters", noticeinterval/60);		
		
		//组件名称返回值转化
		String custnotices = "";
		String custnoticeids = "";
		String custnoticecomp = "";
		String custnoticeIds = eventRule.getCustnoticecompid();
		if(custnoticeIds!=null&&!"".equals("custnoticeIds")){
			String [] custnotice = custnoticeIds.split(",");
				for(int i=0;i<custnotice.length;i++){	    		
					try{
						eventHandlerComp = eventHandlerCompService.get(custnotice[i]);
					}catch(Exception e){
						e.printStackTrace();
					}
					if(eventHandlerComp!=null){
						String name = eventHandlerComp.getComponentname();
						custnoticeids += ","+custnotice[i];
						custnotices += ","+name;
					}
				}
				if(!"".equals(custnotices)){
					custnoticecomp = custnotices.substring(1);
					eventRule.setCustnoticecompid(custnoticeids.substring(1));
				}
				else{
					custnoticecomp = custnotices;
				}
		}
		this.getRequest().setAttribute("custnoticecomp", custnoticecomp);
	     
	     //判断通知对象是否为空，不为空则将人员、部门、角色进行分割
		if(eventRule.getNoticehandler() != null){
			try{
			    String userSum = eventRule.getNoticehandlerid();
			    if(!userSum.equals("")){			    
			    	String[] notusersid = userSum.split(";");
			    		if(notusersid.length==3){
			    			String notUserId = notusersid[0].substring(2);
							String notDepId  = notusersid[1].substring(2);
							String notRoleId  = notusersid[2].substring(2);	    	     
							this.getRequest().setAttribute("notuserid", notUserId);
							this.getRequest().setAttribute("notdepid", notDepId);
							this.getRequest().setAttribute("notroleid", notRoleId);
							
							String[] notusers = eventRule.getNoticehandler().split(";");				
							String notUser = notusers[0];
							String notDep = notusers[1];
							String notRole = notusers[2];
							this.getRequest().setAttribute("notuser", notUser);
							this.getRequest().setAttribute("notdep", notDep);
							this.getRequest().setAttribute("notrole", notRole);
			    		}
			    		if(notusersid.length==2){			    			
			    			if(userSum.indexOf("U:")!=-1 && userSum.indexOf("D:")!=-1){
			    				String notUserId = notusersid[0].substring(2);
								String notDepId  = notusersid[1].substring(2);								    	     
								this.getRequest().setAttribute("notuserid", notUserId);
								this.getRequest().setAttribute("notdepid", notDepId);
								
								String[] notusers = eventRule.getNoticehandler().split(";");				
								String notUser = notusers[0];
								String notDep = notusers[1];
								this.getRequest().setAttribute("notuser", notUser);
								this.getRequest().setAttribute("notdep", notDep);
			    			}
			    			if(userSum.indexOf("U:")!=-1 && userSum.indexOf("R:")!=-1){
			    				String notUserId = notusersid[0].substring(2);
								String notRoleId  = notusersid[1].substring(2);								    	     
								this.getRequest().setAttribute("notuserid", notUserId);
								this.getRequest().setAttribute("notroleid", notRoleId);
								
								String[] notusers = eventRule.getNoticehandler().split(";");				
								String notUser = notusers[0];
								String notRole = notusers[1];
								this.getRequest().setAttribute("notuser", notUser);
								this.getRequest().setAttribute("notrole", notRole);
			    			}
			    			if(userSum.indexOf("D:")!=-1 && userSum.indexOf("R:")!=-1){
			    				String notDepId  = notusersid[0].substring(2);
								String notRoleId  = notusersid[1].substring(2);								    	     
								this.getRequest().setAttribute("notdepid", notDepId);
								this.getRequest().setAttribute("notroleid", notRoleId);
								
								String[] notusers = eventRule.getNoticehandler().split(";");				
								String notDep = notusers[0];
								String notRole = notusers[1];
								this.getRequest().setAttribute("notdep", notDep);
								this.getRequest().setAttribute("notrole", notRole);
			    			}		    			
			    		}
			    		if(notusersid.length==1){
			    			if(userSum.indexOf("U:")!=-1){
			    				String notUserId = notusersid[0].substring(2);
			    				this.getRequest().setAttribute("notuserid", notUserId);
			    				String notusers = eventRule.getNoticehandler();
			    				this.getRequest().setAttribute("notuser", notusers.substring(0,notusers.length()-1));
			    			}
			    			if(userSum.indexOf("D:")!=-1){
			    				String notDepId  = notusersid[0].substring(2);
			    				this.getRequest().setAttribute("notudepid", notDepId);
			    				String notusers = eventRule.getNoticehandler();
								this.getRequest().setAttribute("notdep", notusers.substring(0,notusers.length()-1));
			    			}
			    			if(userSum.indexOf("R:")!=-1){
			    				String notRoleId  = notusersid[1].substring(2);	
			    				this.getRequest().setAttribute("notroleid", notRoleId);
			    				String notusers = eventRule.getNoticehandler();
								this.getRequest().setAttribute("notrole", notusers.substring(0,notusers.length()-1));
			    			}
			    		}			    	
			    }	
			}catch(Exception e){
			e.printStackTrace();
			}
		}
				

	      //判断抄送对象是否为空，不为空则将人员、部门、角色进行分割
		if(eventRule.getDuplicatehandler() != null){
			try{
			    String dupuserSum = eventRule.getDuplicatehandlerid();
			    if(!dupuserSum.equals("")){			    
			    		String[] dupusersid = dupuserSum.split(";");
			    		if(dupusersid.length==3){
			    			String dupUserId = dupusersid[0].substring(2);
							String dupDepId  = dupusersid[1].substring(2);
							String dupRoleId  = dupusersid[2].substring(2);	    	     
							this.getRequest().setAttribute("copyuserid", dupUserId);
							this.getRequest().setAttribute("copydepid", dupDepId);
							this.getRequest().setAttribute("copyroleid", dupRoleId);
							
							String[] dupusers = eventRule.getDuplicatehandler().split(";");				
							String dupUser = dupusers[0];
							String dupDep = dupusers[1];
							String dupRole = dupusers[2];
							this.getRequest().setAttribute("copyuser", dupUser);
							this.getRequest().setAttribute("copydep", dupDep);
							this.getRequest().setAttribute("copyrole", dupRole);
			    		}
			    		if(dupusersid.length==2){			    			
			    			if(dupuserSum.indexOf("U:")!=-1 && dupuserSum.indexOf("D:")!=-1){
			    				String dupUserId = dupusersid[0].substring(2);
								String dupDepId  = dupusersid[1].substring(2);								    	     
								this.getRequest().setAttribute("copyuserid", dupUserId);
								this.getRequest().setAttribute("copydepid", dupDepId);
								
								String[] dupusers = eventRule.getDuplicatehandler().split(";");				
								String dupUser = dupusers[0];
								String dupDep = dupusers[1];
								this.getRequest().setAttribute("copyuser", dupUser);
								this.getRequest().setAttribute("copydep", dupDep);
			    			}
			    			if(dupuserSum.indexOf("U:")!=-1 && dupuserSum.indexOf("R:")!=-1){
			    				String dupUserId = dupusersid[0].substring(2);
								String dupRoleId  = dupusersid[1].substring(2);								    	     
								this.getRequest().setAttribute("copyuserid", dupUserId);
								this.getRequest().setAttribute("copyroleid", dupRoleId);
								
								String[] dupusers = eventRule.getDuplicatehandler().split(";");				
								String dupUser = dupusers[0];
								String duoRole = dupusers[1];
								this.getRequest().setAttribute("copyuser", dupUser);
								this.getRequest().setAttribute("copyrole", duoRole);
			    			}
			    			if(dupuserSum.indexOf("D:")!=-1 && dupuserSum.indexOf("R:")!=-1){
			    				String dupDepId  = dupusersid[0].substring(2);
								String dupRoleId  = dupusersid[1].substring(2);								    	     
								this.getRequest().setAttribute("copydepid", dupDepId);
								this.getRequest().setAttribute("copyroleid", dupRoleId);
								
								String[] dupusers = eventRule.getDuplicatehandler().split(";");				
								String dupDep = dupusers[0];
								String dupRole = dupusers[1];
								this.getRequest().setAttribute("copydep", dupDep);
								this.getRequest().setAttribute("copyrole", dupRole);
			    			}		    			
			    		}
			    		if(dupusersid.length==1){
			    			if(dupuserSum.indexOf("U:")!=-1){
			    				String dupUserId = dupusersid[0].substring(2);
			    				this.getRequest().setAttribute("copyuserid", dupUserId);
			    				String dupusers = eventRule.getDuplicatehandler();
								this.getRequest().setAttribute("copyuser", dupusers.substring(0,dupusers.length()-1));
			    			}
			    			if(dupuserSum.indexOf("D:")!=-1){
			    				String dupDepId  = dupusersid[0].substring(2);
			    				this.getRequest().setAttribute("copydepid", dupDepId);
			    				String dupusers = eventRule.getDuplicatehandler();
								this.getRequest().setAttribute("copydep", dupusers.substring(0,dupusers.length()-1));
			    			}
			    			if(dupuserSum.indexOf("R:")!=-1){
			    				String dupRoleId  = dupusersid[1].substring(2);
			    				this.getRequest().setAttribute("copyroleid", dupRoleId);
			    				String dupusers = eventRule.getDuplicatehandler();
								this.getRequest().setAttribute("copyrole", dupusers.substring(0,dupusers.length()-1));
			    			}
			    		}
			    	
			    }	
			}catch(Exception e){
			e.printStackTrace();
			}
		}
	    //判断升级对象是否为空，不为空则将人员、部门、角色进行分割
		if(eventRule.getUpgradehandler() !=null){
			try{
			    String upSum = eventRule.getUpgradehandlerid();
			    if(!upSum.equals("")){			    
			    		String[] upid = upSum.split(";");
			    		if(upid.length==3){
			    			String uphaUserId = upid[0].substring(2);
							String uphaDepId  = upid[1].substring(2);
							String uphaRoleId  = upid[2].substring(2);	    	     
							this.getRequest().setAttribute("upgradeuserid", uphaUserId);
							this.getRequest().setAttribute("upgradedepid", uphaDepId);
							this.getRequest().setAttribute("upgraderoleid", uphaRoleId);							

							String[] upgradename = eventRule.getUpgradehandler().split(";");
							String upgradeUser = upgradename[0];
							String upgradeDepd = upgradename[1];
							String upgradeRole = upgradename[2];
							this.getRequest().setAttribute("upgradeuser", upgradeUser);
							this.getRequest().setAttribute("upgradedep", upgradeDepd);
							this.getRequest().setAttribute("upgraderole", upgradeRole);
			    		}
			    		if(upid.length==2){			    			
			    			if(upSum.indexOf("U:")!=-1 && upSum.indexOf("D:")!=-1){
			    				String uphaUserId = upid[0].substring(2);							
								String uphaDepId  = upid[1].substring(2);	    	     
								this.getRequest().setAttribute("upgradeuserid", uphaUserId);							
								this.getRequest().setAttribute("upgradedepid", uphaDepId);							

								
								String[] upgradename = eventRule.getUpgradehandler().split(";");
								String upgradeUser = upgradename[0];
								String upgradeDepd = upgradename[1];
								this.getRequest().setAttribute("upgradeuser", upgradeUser);
								this.getRequest().setAttribute("upgradedep", upgradeDepd);
			    			}
			    			if(upSum.indexOf("U:")!=-1 && upSum.indexOf("R:")!=-1){
			    				String uphaUserId = upid[0].substring(2);							
								String uphaRoleId  = upid[1].substring(2);	    	     
								this.getRequest().setAttribute("upgradeuserid", uphaUserId);							
								this.getRequest().setAttribute("upgraderoleid", uphaRoleId);	
								
								String[] upgradename = eventRule.getUpgradehandler().split(";");
								String upgradeUser = upgradename[0];							
								String upgradeRole = upgradename[1];
								this.getRequest().setAttribute("upgradeuser", upgradeUser);								
								this.getRequest().setAttribute("upgraderole", upgradeRole);
			    			}
			    			if(upSum.indexOf("D:")!=-1 && upSum.indexOf("R:")!=-1){
			    				String uphaDepId  = upid[0].substring(2);
								String uphaRoleId  = upid[1].substring(2);	    	     
								this.getRequest().setAttribute("upgradedepid", uphaDepId);
								this.getRequest().setAttribute("upgraderoleid", uphaRoleId);		
								
								String[] upgradename = eventRule.getUpgradehandler().split(";");								
								String upgradeDepd = upgradename[0];
								String upgradeRole = upgradename[1];
							
								this.getRequest().setAttribute("upgradedep", upgradeDepd);
								this.getRequest().setAttribute("upgraderole", upgradeRole);
			    			}		    			
			    		}
			    		if(upid.length==1){
			    			if(upSum.indexOf("U:")!=-1){
			    				String uphaUserId = upid[0].substring(2);
			    				this.getRequest().setAttribute("upgradeuserid", uphaUserId);
			    				String notusers = eventRule.getUpgradehandler();
								this.getRequest().setAttribute("upgradeuser", notusers.substring(0,notusers.length()-1));
			    			}
			    			if(upSum.indexOf("D:")!=-1){
			    				String uphaDepId = upid[0].substring(2);
			    				this.getRequest().setAttribute("upgradedepid", uphaDepId);
			    				String notusers = eventRule.getUpgradehandler();
								this.getRequest().setAttribute("upgradedep", notusers.substring(0,notusers.length()-1));
			    			}
			    			if(upSum.indexOf("R:")!=-1){
			    				String uphaRoleId = upid[0].substring(2);
			    				this.getRequest().setAttribute("upgraderoleid", uphaRoleId);
			    				String notusers = eventRule.getUpgradehandler();
								this.getRequest().setAttribute("upgraderole", notusers.substring(0,notusers.length()-1));
			    			}
			    		}
			    	
			    }	
			}catch(Exception e){
			e.printStackTrace();
			}
		}	     
		
		Long timeSpans = eventRule.getTimespan();
	     //提前、超期时间小于1小时时
		if(timeSpans < 3600){
			this.getRequest().setAttribute("span_minute", timeSpans/60); 
			this.getRequest().setAttribute("span_hour", "0"); 
			this.getRequest().setAttribute("span_day", "0"); 
		}else if(timeSpans < 86400){//提前、超期时间小于1天时
			int aboutHour = (int)(timeSpans/3600);
			int spanMinute = (int)(timeSpans - aboutHour*3600)/60;
			this.getRequest().setAttribute("span_minute", spanMinute); 
			this.getRequest().setAttribute("span_hour", aboutHour); 
			this.getRequest().setAttribute("span_day", "0");
		}else {//提前、超期时间大于一天时
			int aboutDay = (int)(timeSpans/86400);
			int aboutHour = (int)(timeSpans - (aboutDay*86400))/3600;
			int aboutMinute = (int)(timeSpans - (aboutDay*86400 + aboutHour*3600))/60;
			this.getRequest().setAttribute("span_minute", aboutMinute); 
			this.getRequest().setAttribute("span_hour", aboutHour); 
			this.getRequest().setAttribute("span_day", aboutDay);
		}
		return findForward("eventRuleSave");
		}
	public String delEventRule(){
		if (StringUtils.isNotBlank(delIds)) {
			String[] ids = delIds.split(",");
			if (!ArrayUtils.isEmpty(ids)) {
				for (int i = 0; i < ids.length; i++) {
					String edid = ids[i];
					if (StringUtils.isNotBlank(edid)) {
					eventRuleService.delete(edid);
					}
				}
			}
		}
		return this.findForward("eventRuleList");
		}
    public String addEventRule(){
    	this.getRequest().setAttribute("span_minute", 30); 
    	this.getRequest().setAttribute("span_hour", 0); 
    	this.getRequest().setAttribute("span_day", 0);
    	this.getRequest().setAttribute("noticet", 1);
    	this.getRequest().setAttribute("noticeinters", 0);
    	return findForward("eventRuleSave");
	}
	public EventRule getEventRule() {
		return eventRule;
	}

	public void setEventRule(EventRule eventRule) {
		this.eventRule = eventRule;
	}

	public void setEventRuleService(IEventRuleService eventRuleService) {
		this.eventRuleService = eventRuleService;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public IEventRuleService getEventRuleService() {
		return eventRuleService;
	}
	public EventHandlerComp getEventHandlerComp() {
		return eventHandlerComp;
	}
	public void setEventHandlerComp(EventHandlerComp eventHandlerComp) {
		this.eventHandlerComp = eventHandlerComp;
	}
	public IEventHandlerCompService getEventHandlerCompService() {
		return eventHandlerCompService;
	}
	public void setEventHandlerCompService(
			IEventHandlerCompService eventHandlerCompService) {
		this.eventHandlerCompService = eventHandlerCompService;
	}
}
