package com.ultrapower.eoms.msextend.change.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.msextend.change.model.CIClass;
import com.ultrapower.eoms.msextend.change.model.CIRelevance;
import com.ultrapower.eoms.msextend.change.service.CIRelevanceService;
@SuppressWarnings("unchecked")
public class CIRelevanceServiceImpl implements CIRelevanceService {
	private IDao<CIClass> ciclassDao;
	public List<CIRelevance> getCIsBySchemaAndBaseid(String schema,String baseid) {
		
		return null;
	}

	public List<CIClass> getAllCIClasses() {
		List<CIClass> ciClasses = new ArrayList<CIClass>();
		if(BaseCacheManager.getElementSize(Constants.CICLASSESCACHE) == 0)
			loadAllCIClasses();
		CIClass ciClass = (CIClass) BaseCacheManager.get(Constants.CICLASSESCACHE, "managed_object");
		ciClasses.add(ciClass);
		return ciClasses;
	}

	public void setCiclassDao(IDao<CIClass> ciclassDao) {
		this.ciclassDao = ciclassDao;
	}

	@Override
	public List<CIClass> loadAllCIClasses() {
		List<CIClass> ciClasses = ciclassDao.find("from CIClass");
		for(CIClass ciClass : ciClasses){
			BaseCacheManager.put(Constants.CICLASSESCACHE, ciClass.getName(), ciClass);
		}
		RecordLog.printLog("CI类别加载完毕", RecordLog.LOG_LEVEL_INFO);
		return ciClasses;
	}

	@Override
	public List<CIRelevance> getCIRelevanceBySchemaBaseid(String baseschema,String baseid) {
		List<CIRelevance> ciRelevances = ciclassDao.find("from CIRelevance where baseschema = ? and baseid = ?", baseschema,baseid);
		return ciRelevances;
	}

	@Override
	public void saveCIRelevance(List<CIRelevance> ciRelevances) {
		System.out.println();
	}

	@Override
	public void deleteCIRelevance(String id) {
		
	}

}
