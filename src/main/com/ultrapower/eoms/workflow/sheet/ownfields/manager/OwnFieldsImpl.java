package com.ultrapower.eoms.workflow.sheet.ownfields.manager;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.workflow.sheet.ownfields.model.OwnFields;
import com.ultrapower.eoms.workflow.sheet.ownfields.service.OwnFieldsService;

@Transactional
public class OwnFieldsImpl implements OwnFieldsService {
	
	private IDao<OwnFields> ownFieldsDao;

	public void deleteById(String id) {
		ownFieldsDao.removeById(id);
	}

	public List<OwnFields> getAll(String baseSchema, String stepCode, String actionType) {
		String hql = "from OwnFields where baseSchema='"+baseSchema+"' ";
		if (StringUtils.isNotBlank(stepCode)) {
			hql += " and defEditStep like '%#"+stepCode+"#%'";
		}
		if (StringUtils.isNotBlank(actionType)) {
			hql += " and freeEditStep like '%#"+actionType+"#%'";
		}
		hql += " order by printOrder";
		PageLimit pageLimit = PageLimit.getInstance();
		return ownFieldsDao.pagedQuery(hql, pageLimit, null);
	}
	
	public List<OwnFields> getAll(String baseSchema) {
		return ownFieldsDao.getAll();
	}
	
	public OwnFields getOwnFieldbyCode(String baseSchema, String fieldCode) {
		OwnFields own = null;
		String hql = "from OwnFields where baseSchema='"+baseSchema+"' and fieldCode='"+fieldCode+"'";
		List find = ownFieldsDao.find(hql, null);
		if (CollectionUtils.isNotEmpty(find)) {
			own = (OwnFields) find.get(0);
		}
		return own;
	}

	public OwnFields getById(String id) {
		return ownFieldsDao.get(id);
	}

	public void saveOrUpdate(OwnFields ownFields) {
		if (StringUtils.isBlank(ownFields.getId())) {
			ownFields.setId(null);
			ownFields.setCreateTime(TimeUtils.getCurrentTime());
		}
		ownFieldsDao.saveOrUpdate(ownFields);
	}

	public IDao<OwnFields> getOwnFieldsDao() {
		return ownFieldsDao;
	}

	public void setOwnFieldsDao(IDao<OwnFields> ownFieldsDao) {
		this.ownFieldsDao = ownFieldsDao;
	}
}
