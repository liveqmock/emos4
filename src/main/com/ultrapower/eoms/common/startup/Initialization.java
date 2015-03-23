package com.ultrapower.eoms.common.startup;

import javax.servlet.ServletContext;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.email.EmailPara;
import com.ultrapower.eoms.common.core.component.sms.manager.MailProxyPara;
import com.ultrapower.eoms.common.core.component.sms.manager.SmsProxyPara;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.msextend.change.model.CIClass;
import com.ultrapower.eoms.msextend.change.service.CIRelevanceService;
import com.ultrapower.eoms.msextend.constants.ExtendConstants;
import com.ultrapower.eoms.ultrabpp.cache.service.ExtendFunctionCacheService;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.cache.service.ThemeCacheService;
import com.ultrapower.eoms.ultrabpp.develop.service.TemplateService;
import com.ultrapower.eoms.ultrasla.init.InitSlaConfigInfo;
import com.ultrapower.eoms.ultrasm.util.ResolvePwdManageCfg;
import com.ultrapower.remedy4j.core.RemedySession;
import com.ultrapower.wfinterface.core.util.ConfigUtil;
import com.ultrapower.workflow.bizworkflow.server.RMIServerMain;
import com.ultrapower.workflow.engine.def.WorkflowFactory;
import com.ultrapower.workflow.utils.ApplicationContextUtils;
import com.ultrapower.workflow.utils.WfEngineUtils;
import com.ultrapower.workflow.utils.WorkflowConfigParser;

public class Initialization {
	
	private ServletContext servletContext;
	
	public  void init()
	{
		Constants.DATABASE_ALIAS=StringUtils.checkNullString(PropertiesUtils.getProperty("jdbc.alias"));
		Constants.PWD_MANAGE = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.pwdmanage")).equals("true") ? true : false;
		Constants.CHECKCODE_MANAGE = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.checkcode")).equals("true") ? true : false;
		ConstantsSynch.isSynch = PropertiesUtils.getProperty("iam.pasm.isSynch").equals("true")?true:false;
		ConstantsSynch.isUip = PropertiesUtils.getProperty("iam.uip.isSynch").equals("true")?true:false;
		ConstantsSynch.isPasmSynchEoms = PropertiesUtils.getProperty("iam.uip.isPasmSynchEoms").equals("true")?true:false;
		ConstantsSynch.isSynchToV2 = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.eoms_v2")).equals("true") ? true : false;
		ConstantsSynch.synchToV2Content = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.eoms_v2.content"));
		ConstantsSynch.isSynchToPasm = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.eoms_pasm")).equals("true") ? true : false;
		ConstantsSynch.synchToPasmContent = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.eoms_pasm.content"));
		ConstantsSynch.isSynchFromV2 = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.v2_eoms")).equals("true") ? true : false;
		ConstantsSynch.synchFromV2Content = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.v2_eoms.content"));
		ConstantsSynch.isSynchFromPasm = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.pasm_eoms")).equals("true") ? true : false;
		ConstantsSynch.synchFromPasmContent = StringUtils.checkNullString(PropertiesUtils.getProperty("synch.pasm_eoms.content"));
		Constants.WORKFLOW_SERVERMODE = StringUtils.checkNullString(PropertiesUtils.getProperty("wf.servermode"));
		SmsProxyPara.executeclass = StringUtils.checkNullString(PropertiesUtils.getPropertySm("executeclass"));
		SmsProxyPara.smsip = StringUtils.checkNullString(PropertiesUtils.getPropertySm("smsip"));
		SmsProxyPara.smsport = StringUtils.checkNullString(PropertiesUtils.getPropertySm("smsport"));
		SmsProxyPara.sendStatus = StringUtils.checkNullString(PropertiesUtils.getPropertySm("sendStatus"));
		SmsProxyPara.startTime = null==PropertiesUtils.getPropertySm("startTime")||"".equals(PropertiesUtils.getPropertySm("startTime"))?"0:00":PropertiesUtils.getPropertySm("startTime");
		SmsProxyPara.endTime = null==PropertiesUtils.getPropertySm("endTime")||"".equals(PropertiesUtils.getPropertySm("endTime"))?"23:59":PropertiesUtils.getPropertySm("endTime");
		MailProxyPara.sendStatus = StringUtils.checkNullString(PropertiesUtils.getPropertyMail("sendStatus"));
		MailProxyPara.startTime = null==PropertiesUtils.getPropertyMail("startTime")||"".equals(PropertiesUtils.getPropertyMail("startTime"))?"0:00":PropertiesUtils.getPropertyMail("startTime");
		MailProxyPara.endTime = null==PropertiesUtils.getPropertyMail("endTime")||"".equals(PropertiesUtils.getPropertyMail("endTime"))?"23:59":PropertiesUtils.getPropertyMail("endTime");
		MailProxyPara.MAIL_SMTP_HOST  = PropertiesUtils.getPropertyMail("mail.smtp.host");
		MailProxyPara.MAIL_SMTP_PORT  = PropertiesUtils.getPropertyMail("mail.smtp.host.port");
		MailProxyPara.MAIL_SMTP_AUTH  = PropertiesUtils.getPropertyMail("mail.smtp.auth");
		MailProxyPara.MAIL_FROM_ADD  = PropertiesUtils.getPropertyMail("mail.from");
		MailProxyPara.MAIL_FROM_USER  = PropertiesUtils.getPropertyMail("mail.user");
		MailProxyPara.MAIL_FROM_PASS  = PropertiesUtils.getPropertyMail("mail.password");
		MailProxyPara.MAIL_FROM_ADDTITLE  = PropertiesUtils.getPropertyMail("mail.from.AddTitle");
		
		//知识库相关配置
		ExtendConstants.KM_URL = PropertiesUtils.getProperty("km.url");
		ExtendConstants.KM_PROJECTNAME = PropertiesUtils.getProperty("km.projectName");
		
		RemedySession.UtilInfor.SERVER_NAME = PropertiesUtils.getProperty("eoms.remedy.servername");
		RemedySession.UtilInfor.SERVER_PORT = PropertiesUtils.getProperty("eoms.remedy.serverport");
		RemedySession.UtilInfor.DEMO_PASSWORD = PropertiesUtils.getProperty("eoms.remedy.demopwd");
		RemedySession.UtilInfor.CREATE_URL = PropertiesUtils.getProperty("eoms.remedy.createurl");
		RemedySession.UtilInfor.CREATE_DEF_URL = PropertiesUtils.getProperty("eoms.remedy.createdefurl");
		RemedySession.UtilInfor.QUERY_URL = PropertiesUtils.getProperty("eoms.remedy.queryurl");
		RemedySession.UtilInfor.QUERY_TASK_URL = PropertiesUtils.getProperty("eoms.remedy.querytaskurl");
		RemedySession.UtilInfor.FLOWMAP_URL = PropertiesUtils.getProperty("eoms.remedy.flowmapurl");
		RemedySession.UtilInfor.LOGOUT_URL = PropertiesUtils.getProperty("eoms.remedy.logouturl");
		
		//初始化SLA配置信息
		InitSlaConfigInfo.initConfig();
		
		EmailPara.MAIL_SMTP_HOST = PropertiesUtils.getPropertyMail("mail.smtp.host");
		EmailPara.MAIL_P0P3_HOST = PropertiesUtils.getPropertyMail("mail.pop3.host");
		EmailPara.MAIL_USER = PropertiesUtils.getPropertyMail("mail.user");
		EmailPara.MAIL_PASSWORD = PropertiesUtils.getPropertyMail("mail.password");
		EmailPara.MAIL_FROM = PropertiesUtils.getPropertyMail("mail.from");
		EmailPara.MAIL_SMTP_HOST_PORT = PropertiesUtils.getPropertyMail("mail.smtp.host.port");
		
		//初始化附件路径
		SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH = PropertiesUtils.getProperty("attachment.path").endsWith("\\")||PropertiesUtils.getProperty("attachment.path").endsWith("/")
												?PropertiesUtils.getProperty("attachment.path").substring(0,PropertiesUtils.getProperty("attachment.path").length()-1)
												:PropertiesUtils.getProperty("attachment.path");
		if(servletContext!=null)
			SwfuploadUtil.APP_ROOT_REALPATH = servletContext.getRealPath("");
		
//		if(servletContext!=null)
//			FileOperate.APP_ROOT_REALPATH = servletContext.getRealPath("");
												
		Constants.WORKSHEET_UPLOAD_PATH = PropertiesUtils.getProperty("worksheet.attachment.path");
		
		//附件临时目录清理线程启动
		SwfuploadUtil.tempAttachCleaner.startClear();
		
//		RepositoryLoadXML loadXML = new RepositoryLoadXML();
//		loadXML.getPropertyMap();
		
		//加载萨班斯密码管理配置
		if(Constants.PWD_MANAGE)
			ResolvePwdManageCfg.getPwdManageCfg();
    	
//		SynDataShiftThread synDataShiftThread=new SynDataShiftThread();
//		synDataShiftThread.start();
												
		//系统启动lookup RMI Server，加快后续访问速度
//		try {
//			WorkFlowServiceClient.clientInstance().getBizFacade().test();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
		
		try {
			
			if (Constants.WORKFLOW_SERVERMODE.equals("") || Constants.WORKFLOW_SERVERMODE.equalsIgnoreCase("true")) {
				ApplicationContextUtils.ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				RMIServerMain.startup();
			}
			
			com.ultrapower.wfinterface.core.util.Constants.sendMsgWSURL = ConfigUtil.getProperty("sendMsg.webservice_url");
			
			//表单缓存
			MetaCacheService metaCacheService = (MetaCacheService)WebApplicationManager.getBean("metaCacheService");
			/**
			 * 加载/workflows/fields_config.xml 文件 
			 */
			metaCacheService.reflushFieldInfo();
			/**
			 * 加载"/workflows/freeaction_config.xml"文件
			 */
			metaCacheService.reflushFreeAction();
			/**
			 * 加载所有表单配置文件进入缓存。
			 */
			metaCacheService.reflushCache(null);
			/**
			 * 加载样式信息
			 */
			ThemeCacheService themeCacheService = (ThemeCacheService)WebApplicationManager.getBean("themeCacheService");
			themeCacheService.initThemeConfig();
			
			/**
			 * 加载客户化逻辑
			 */
			ExtendFunctionCacheService extendFuncCacheService = (ExtendFunctionCacheService)WebApplicationManager.getBean("extendFuncCacheService");
			extendFuncCacheService.reflush(null);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
