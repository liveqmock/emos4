package com.ultrapower.eoms.ultrasla.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.model.SlaDefine;
import com.ultrapower.eoms.ultrasla.service.ISlaDefineService;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;

/**
 * @author RenChenglin
 * @date 2011-11-15 下午03:46:36
 * @version
 */
@Transactional
public class SalDefineManager implements ISlaDefineService {
	private IDao<SlaDefine> slaDefineDao;

	public int deleteById(String id) {
		if(id==null||slaDefineDao==null){
			return -1;
		} 
		try {
			slaDefineDao.removeById(id);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public SlaDefine get(String id) {
		if(id==null||slaDefineDao==null){
			return null;
		}
		try {
			return slaDefineDao.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String save(SlaDefine slaDefine) {
		if(slaDefine==null||slaDefineDao==null){
			return null;
		}
		try {
			if("".equals(StringUtils.checkNullString(slaDefine.getPid()))){
				RandomN random = new Random15();
				slaDefine.setPid(random.getRandom(System.currentTimeMillis()));
				slaDefineDao.save(slaDefine);
			}else{
				slaDefineDao.saveOrUpdate(slaDefine);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return slaDefine.getPid();
	}

	public void setSlaDefineDao(IDao<SlaDefine> slaDefineDao) {
		this.slaDefineDao = slaDefineDao;
	}

	public SlaDefine getBySchema(String baseSchema) {
		if(baseSchema==null||slaDefineDao==null){
			return null;
		}
		List lst = slaDefineDao.find("from SlaDefine where baseschema = ?", baseSchema);
		if(lst==null||lst.size()<=0){
			return null;
		}else{
			return (SlaDefine)lst.get(0);
		}
	}

	public int deleteBySchema(String baseSchema) {
		if(baseSchema==null||slaDefineDao==null){
			return -1;
		}
		String hql = "delete SlaDefine where baseschema = ?";
		return slaDefineDao.executeUpdate(hql, baseSchema);
	}
}
