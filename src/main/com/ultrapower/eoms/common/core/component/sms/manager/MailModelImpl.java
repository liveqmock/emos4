package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sms.model.Mailmodel;
import com.ultrapower.eoms.common.core.component.sms.service.IMailModelService;
import com.ultrapower.eoms.common.core.dao.IDao;

public class MailModelImpl implements IMailModelService{
	private IDao<Mailmodel> mailModelDao;
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
	
	public void save(Mailmodel mailModel){
		mailModelDao.saveOrUpdate(mailModel);
		//保存到缓存中以供发送逻辑使用
		if(null==Send.mailModels || Send.mailModels.isEmpty()){
			Send.mailModels = getMailModelAll();
		}else{
			Send.mailModels.remove(mailModel);
			Send.mailModels.add(mailModel);
		}
	}
	
	public Mailmodel getMailModelById(String pid){
		return mailModelDao.get(pid);
	}
	
	public void del(String pid){
		mailModelDao.removeById(pid);
		//删除缓存中模板以供发送逻辑使用
		if(null==Send.mailModels || Send.mailModels.isEmpty()){
			Send.mailModels = getMailModelAll();
		}else{
			Send.mailModels.remove(new Mailmodel(pid));
		}
	}
	
	public Mailmodel getMailModelByModelType(String modelType){
		List<Mailmodel> list = mailModelDao.find("from Mailmodel where modelType=?", modelType);
		if(null!=list && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public List<Mailmodel> getMailModelAll(){
		return mailModelDao.find("from Mailmodel");
	}

	public void setMailModelDao(IDao<Mailmodel> mailModelDao) {
		this.mailModelDao = mailModelDao;
	}
	
}
