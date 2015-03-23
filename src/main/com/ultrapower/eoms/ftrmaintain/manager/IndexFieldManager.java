package com.ultrapower.eoms.ftrmaintain.manager;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ftrmaintain.model.IndexCategory;
import com.ultrapower.eoms.ftrmaintain.model.IndexFieldCfg;
import com.ultrapower.eoms.ftrmaintain.service.IndexFieldService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class IndexFieldManager implements IndexFieldService {
	
	private IDao<IndexFieldCfg> indexFieldDao;
	
	public IndexFieldCfg getFieldById(String pid) {
		if(pid==null)
			return null;
		IndexFieldCfg field = indexFieldDao.get(pid);
		return field;
	}

	public void setIndexFieldDao(IDao<IndexFieldCfg> indexFieldDao) {
		this.indexFieldDao = indexFieldDao;
	}

	public boolean saveField(IndexFieldCfg field) {
		if(field==null)
			return false;
		try {
			if("".equals(StringUtils.checkNullString(field.getPid())))
			{
				indexFieldDao.save(field);
			}
			else
			{
				indexFieldDao.saveOrUpdate(field);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteField(List<String> pid) {
		if(pid==null)
			return false;
		try {
			Map map = UltraSmUtil.getSqlParameter(pid);
			String hql = "delete from IndexFieldCfg where pid in("+map.get("?")+")";
			indexFieldDao.executeUpdate(hql,(Object[])map.get("obj"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
