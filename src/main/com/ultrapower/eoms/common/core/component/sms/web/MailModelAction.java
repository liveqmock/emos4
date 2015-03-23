package com.ultrapower.eoms.common.core.component.sms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.sms.manager.MailModelImpl;
import com.ultrapower.eoms.common.core.component.sms.model.Mailmodel;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class MailModelAction extends BaseAction{
	private MailModelImpl mailModelService;
	private Mailmodel mailModel;
	private List<String> schemaList;
	private String pid;
	private String modelType;
	
	public String mailModelAdd(){
		schemaList = new ArrayList<String>();
		schemaList.add("");
		schemaList.add("DEFAULT");
		schemaList.addAll(mailModelService.getSchemaList());
		return findForward("mailModelAdd");
	}
	
	public String mailModelSave(){
		if(null!=mailModel && null!=mailModel.getModelType() && !"".equals(mailModel.getModelType())){
			if(null!=mailModel.getPid() && "".equals(mailModel.getPid()) || null==mailModel.getPid()){
				mailModel.setPid(null);
//				mailModel.setCreater(getUserSession().getFullName()+"("+getUserSession().getLoginName()+")");
				mailModel.setCreatetime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			}else{
				Mailmodel sm = mailModelService.getMailModelById(mailModel.getPid());
				sm.setContent(mailModel.getContent());
//				sm.setModifier(getUserSession().getFullName()+"("+getUserSession().getLoginName()+")");
				sm.setModifytime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				sm.setMailTitle(mailModel.getMailTitle());
				mailModel = sm;
			}
			mailModelService.save(mailModel);
			this.getRequest().setAttribute("parafresh", "true");
			this.getRequest().setAttribute("returnMessage", "保存成功");
		}
		return "refresh";
	}
	
	public String mailModelUpdate(){
		schemaList = new ArrayList<String>();
		schemaList.add("");
		schemaList.add("DEFAULT");
		schemaList.addAll(mailModelService.getSchemaList());
		if(null!=pid && !"".equals(pid)){
			mailModel = mailModelService.getMailModelById(pid);
		}
		return findForward("mailModelAdd");
	}
	
	public String mailModelDel(){
		if(null!=pid && !"".equals(pid)){
			mailModelService.del(pid);
		}
		return findForward("mailModelList");
	}
	
	public String loadMailModel(){
		mailModel = mailModelService.getMailModelByModelType(modelType);
		String returnJson = "{\"pid\": \"\",\"content\": \"\"}";
		getResponse().setContentType("applictaion/json; charset=UTF-8");
		getResponse().setHeader("Cache-Control", "no-cache");
		if(null!=mailModel){
			returnJson = "{\"pid\": \""+mailModel.getPid()+"\",\"content\": \""+mailModel.getContent()+"\"}";
		}
		PrintWriter out;
		try {
			out = getResponse().getWriter();
			out.write(returnJson);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setMailModelService(MailModelImpl mailModelService) {
		this.mailModelService = mailModelService;
	}

	public Mailmodel getMailModel() {
		return mailModel;
	}

	public void setMailModel(Mailmodel mailModel) {
		this.mailModel = mailModel;
	}

	public List<String> getSchemaList() {
		return schemaList;
	}

	public void setSchemaList(List<String> schemaList) {
		this.schemaList = schemaList;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
	
}
