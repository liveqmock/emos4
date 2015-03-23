package com.ultrapower.wfinterface.core.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.WfiDatain;
import com.ultrapower.wfinterface.core.service.WfiDatainService;
import com.ultrapower.wfinterface.core.service.WfiTempAttachmentService;

@Transactional
public class WfiDatainImpl implements WfiDatainService {

	private IDao<WfiDatain>  wfiDatainDao;
	private WfiTempAttachmentService wfiTempAttachmentService;

	/**
	 * 根据Id取得接口数据
	 * @param pid
	 * @return
	 */
	public WfiDatain getWfiDatain(String pid){
		WfiDatain wfiDatain = wfiDatainDao.get(pid);
		return wfiDatain;
	}
	
	/**
	 * 保存接口数据
	 * 
	 * @param wfiDatain
	 * @return
	 */
	public String saveWfiDatain(WfiDatain wfiDatain){

		wfiDatain.setCreatetime(TimeUtils.getCurrentTime());
		//保存数据
		wfiDatainDao.saveOrUpdate(wfiDatain);

		return "数据保存成功！";
	}

	/**
	 * 保存接口数据和附件
	 * 
	 * @param wfiDatain
	 * @param list
	 * @return
	 */
	public String saveWfiDatainAndAttachments(WfiDatain wfiDatain,List<Attachment> list) throws Exception{

		saveWfiDatain(wfiDatain);
		if (list != null && list.size() > 0){
			wfiTempAttachmentService.saveAttachment(list);
		}

		return "数据保存成功！";
	}
	/**
	 * 根据Id删除接口数据
	 * @param pid
	 * @return
	 */
	public void deleteWfiDatain(String pid){
		wfiDatainDao.removeById(pid);
	}

	/**
	 * 根据状态取得接口数据列表
	 * @param wfiDatain
	 * @return
	 */
	public List<WfiDatain> getWfiDatainList(int opState){

		//PageLimit pageLimit = PageLimit.getInstance();
		//List<Object> paramList = new ArrayList<Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" from WfiDatain wd where opstate = ? ");
		//paramList.add(opState);

		sbf.append(" order by wd.createtime");
		//List<WfiDatain> wfiDatainList = wfiDatainDao.pagedQuery(sbf.toString(), pageLimit, opState);
		List<WfiDatain> wfiDatainList = wfiDatainDao.find(sbf.toString(), opState);
		return wfiDatainList;
	}


	public IDao<WfiDatain> getWfiDatainDao() {
		return wfiDatainDao;
	}

	public void setWfiDatainDao(IDao<WfiDatain> wfiDatainDao) {
		this.wfiDatainDao = wfiDatainDao;
	}

	public WfiTempAttachmentService getWfiTempAttachmentService() {
		return wfiTempAttachmentService;
	}

	public void setWfiTempAttachmentService(WfiTempAttachmentService wfiTempAttachmentService) {
		this.wfiTempAttachmentService = wfiTempAttachmentService;
	}
}
