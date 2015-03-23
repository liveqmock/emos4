package com.ultrapower.eoms.common.core.component.sms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.sms.manager.Send;
import com.ultrapower.eoms.common.core.component.sms.manager.SmsModelImpl;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmodel;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class SmsModelAction extends BaseAction{
	private SmsModelImpl smsModelService;
	private Smsmodel smsModel;
	private List<String> schemaList;
	private String pid;
	private String modelType;
	
	public String smModelAdd(){
		schemaList = new ArrayList<String>();
		schemaList.add("");
		schemaList.add("DEFAULT");
		schemaList.addAll(smsModelService.getSchemaList());
		return findForward("smModelAdd");
	}
	
	public String smsModelSave(){
		if(null!=smsModel && null!=smsModel.getModelType() && !"".equals(smsModel.getModelType())){
			if(null!=smsModel.getPid() && "".equals(smsModel.getPid()) || null==smsModel.getPid()){
				smsModel.setPid(null);
				smsModel.setCreater(getUserSession().getFullName()+"("+getUserSession().getLoginName()+")");
				smsModel.setCreatetime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			}else{
				Smsmodel sm = smsModelService.getSmsModelById(smsModel.getPid());
				sm.setContent(smsModel.getContent());
				sm.setModifier(getUserSession().getFullName()+"("+getUserSession().getLoginName()+")");
				sm.setModifytime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
				smsModel = sm;
			}
			smsModelService.save(smsModel);
			this.getRequest().setAttribute("parafresh", "true");
			this.getRequest().setAttribute("returnMessage", "保存成功");
		}
		return "refresh";
	}
	
	public String smsModelUpdate(){
		schemaList = new ArrayList<String>();
		schemaList.add("");
		schemaList.add("DEFAULT");
		schemaList.addAll(smsModelService.getSchemaList());
		if(null!=pid && !"".equals(pid)){
			smsModel = smsModelService.getSmsModelById(pid);
		}
		return findForward("smModelAdd");
	}
	
	public String smsModelDel(){
		if(null!=pid && !"".equals(pid)){
			smsModelService.del(pid);
		}
		return findForward("smsModelList");
	}
	
	public String loadSmsModel(){
		smsModel = smsModelService.getSmsModelByModelType(modelType);
		String returnJson = "{\"pid\": \"\",\"content\": \"\"}";
		getResponse().setContentType("applictaion/json; charset=UTF-8");
		getResponse().setHeader("Cache-Control", "no-cache");
		if(null!=smsModel){
			returnJson = "{\"pid\": \""+smsModel.getPid()+"\",\"content\": \""+smsModel.getContent()+"\"}";
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
	
	public void setSmsModelService(SmsModelImpl smsModelService) {
		this.smsModelService = smsModelService;
	}


	public Smsmodel getSmsModel() {
		return smsModel;
	}


	public void setSmsModel(Smsmodel smsModel) {
		this.smsModel = smsModel;
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
