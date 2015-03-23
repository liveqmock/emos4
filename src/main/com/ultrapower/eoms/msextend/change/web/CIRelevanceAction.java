package com.ultrapower.eoms.msextend.change.web;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.msextend.change.model.CIClass;
import com.ultrapower.eoms.msextend.change.model.CIRelevance;
import com.ultrapower.eoms.msextend.change.service.CIRelevanceService;
import com.ultrapower.eoms.msextend.change.service.UCMDBCIService;
import com.ultrapower.eoms.msextend.change.util.CIDataProcessor;

public class CIRelevanceAction extends BaseAction{
	private CIRelevanceService ciRelevanceService;
	private UCMDBCIService ucmdbciService;

	private static final long serialVersionUID = 3546242311248871839L;
	private String ciclass;
	private String ciname;
	private String gid;
	private List<CIRelevance> ciRelevances;
	private Map<String, String> ciAttributes;
	private CIRelevance ciRelevance;
	private String[] ciInfo;
	
	public String listCI() throws Exception{
		ciRelevances = ucmdbciService.getCIByTypeAndName(ciclass, ciname);
		return SUCCESS;
	}
	
	public String listAttributeByCI(){
		ciAttributes = ucmdbciService.getAttributeMapByCIGID(ciclass, gid);
		return SUCCESS;
	}
	
	public void saveCIRelevance(){
		try{
			ciRelevanceService.saveCIRelevance(CIDataProcessor.ciInfoToCIrelevance(ciInfo,ciRelevance.getBaseschema(),ciRelevance.getBaseid()));
		}catch(Exception e){
			renderText(ERROR);
			RecordLog.printLog("CI关联失败", RecordLog.LOG_LEVEL_ERROR, e);
			return;
		}
		renderText(SUCCESS);
	}
	
	public String searchCI(){
		return SUCCESS;
	}
	
	public String listCIRelate(){
		ciRelevances = ciRelevanceService.getCIRelevanceBySchemaBaseid(ciRelevance.getBaseschema(),ciRelevance.getBaseid());
		return SUCCESS;
	}
	
	public String listCIByClassAndName(){
		return SUCCESS;
	}
	
	public void getAllCIClassesJson(){
		List<CIClass> ciClasses = ciRelevanceService.getAllCIClasses();
		renderText(CIDataProcessor.getAllCICLassesJson4Easyui(ciClasses));
	}

	public void setCiRelevanceService(CIRelevanceService ciRelevanceService) {
		this.ciRelevanceService = ciRelevanceService;
	}

	public String getCiclass() {
		return ciclass;
	}

	public void setCiclass(String ciclass) {
		this.ciclass = ciclass;
	}

	public String getCiname() {
		return ciname;
	}

	public void setCiname(String ciname) {
		this.ciname = ciname;
	}

	public List<CIRelevance> getCiRelevances() {
		return ciRelevances;
	}

	public void setCiRelevances(List<CIRelevance> ciRelevances) {
		this.ciRelevances = ciRelevances;
	}

	public void setUcmdbciService(UCMDBCIService ucmdbciService) {
		this.ucmdbciService = ucmdbciService;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public Map<String, String> getCiAttributes() {
		return ciAttributes;
	}

	public void setCiAttributes(Map<String, String> ciAttributes) {
		this.ciAttributes = ciAttributes;
	}

	public CIRelevance getCiRelevance() {
		return ciRelevance;
	}

	public void setCiRelevance(CIRelevance ciRelevance) {
		this.ciRelevance = ciRelevance;
	}

	public String[] getCiInfo() {
		return ciInfo;
	}

	public void setCiInfo(String[] ciInfo) {
		this.ciInfo = ciInfo;
	}
	
}
