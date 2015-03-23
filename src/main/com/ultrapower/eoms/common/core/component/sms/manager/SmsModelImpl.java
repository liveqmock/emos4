package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmodel;
import com.ultrapower.eoms.common.core.component.sms.service.ISmsModelService;
import com.ultrapower.eoms.common.core.dao.IDao;

public class SmsModelImpl implements ISmsModelService {
	private IDao<Smsmodel> smsModelDao;
	public static QueryAdapter queryAdapter = new QueryAdapter();
	private static final int initQueryCount = 100;//初始化查询的条数
	
	public List<String> getSchemaList(){
		List<String> schemaList = new ArrayList<String>();
		StringBuffer p_sql = new StringBuffer(1024);
		p_sql.append("select CODE from bs_t_wf_type");
		Object[] values = {};
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString(), values, 0, 0, initQueryCount);
		int dataTableLen = 0;
		DataRow dataRow;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			schemaList.add(dataRow.getString("CODE"));
		}
		return schemaList;
	}
	
	public void save(Smsmodel smsModel){
		smsModelDao.saveOrUpdate(smsModel);
		//保存到缓存中以供发送逻辑使用
		if(null==Send.smsModels || Send.smsModels.isEmpty()){
			Send.smsModels = getSmsModelAll();
		}else{
			Send.smsModels.remove(smsModel);
			Send.smsModels.add(smsModel);
		}
	}
	
	public Smsmodel getSmsModelById(String pid){
		return smsModelDao.get(pid);
	}
	
	public void del(String pid){
		smsModelDao.removeById(pid);
		//删除缓存中模板以供发送逻辑使用
		if(null==Send.smsModels || Send.smsModels.isEmpty()){
			Send.smsModels = getSmsModelAll();
		}else{
			Send.smsModels.remove(new Smsmodel(pid));
		}
	}
	
	public Smsmodel getSmsModelByModelType(String modelType){
		List<Smsmodel> list = smsModelDao.find("from Smsmodel where modelType=?", modelType);
		if(null!=list && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public List<Smsmodel> getSmsModelAll(){
		return smsModelDao.find("from Smsmodel");
	}
	
	public void setSmsModelDao(IDao<Smsmodel> smsModelDao) {
		this.smsModelDao = smsModelDao;
	}
	
}
