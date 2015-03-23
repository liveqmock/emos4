package com.ultrapower.wfinterface.core.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.WfiDataout;
import com.ultrapower.wfinterface.core.service.WfiDataoutService;
import com.ultrapower.wfinterface.core.service.WfiTempAttachmentService;

@Transactional
public class WfiDataoutImpl implements WfiDataoutService {

	private IDao<WfiDataout>  wfiDataoutDao;
	private WfiTempAttachmentService wfiTempAttachmentService;


	/**
	 * 根据Id取得接口数据
	 * @param pid
	 * @return
	 */
	public WfiDataout getWfiDataout(String pid){
		WfiDataout wfiDataout = wfiDataoutDao.get(pid);
		return wfiDataout;
	}

	
	/**
	 * 保存接口数据
	 * 
	 * @param wfiDataout
	 * @return
	 */
	public String saveWfiDataout(WfiDataout wfiDataout){

		wfiDataout.setCreatetime(TimeUtils.getCurrentTime());
		//保存数据
		wfiDataoutDao.saveOrUpdate(wfiDataout);

		return "数据保存成功！";
	}

	/**
	 * 保存接口数据和附件
	 * 
	 * @param wfiDataout
	 * @param list
	 * @return
	 */
	public String saveWfiDataoutAndAttachments(WfiDataout wfiDataout,List<Attachment> list) throws Exception{

		saveWfiDataout(wfiDataout);
		wfiTempAttachmentService.saveAttachment(list);

		return "数据保存成功！";
	}

	/**
	 * 根据Id删除接口数据
	 * @param pid
	 * @return
	 */
	public void deleteWfiDataout(String pid){
		wfiDataoutDao.removeById(pid);
	}

	/**
	 * 根据状态取得接口数据列表
	 * @param opState
	 * @return
	 */
	public List<WfiDataout> getWfiDataoutList(int opState){

		//PageLimit pageLimit = PageLimit.getInstance();
		//List<Object> paramList = new ArrayList<Object>();
		StringBuffer sbf = new StringBuffer();
		sbf.append(" from WfiDataout wd where opstate = ? ");
		//paramList.add(opState);

		sbf.append(" order by wd.createtime");
		//List<WfiDataout> wfiDataoutList = wfiDataoutDao.pagedQuery(sbf.toString(), pageLimit, opState);
		List<WfiDataout> wfiDataoutList = wfiDataoutDao.find(sbf.toString(), opState);
		return wfiDataoutList;
	}


	public IDao<WfiDataout> getWfiDataoutDao() {
		return wfiDataoutDao;
	}

	public void setWfiDataoutDao(IDao<WfiDataout> wfiDataoutDao) {
		this.wfiDataoutDao = wfiDataoutDao;
	}
	public WfiTempAttachmentService getWfiTempAttachmentService() {
		return wfiTempAttachmentService;
	}

	public void setWfiTempAttachmentService(WfiTempAttachmentService wfiTempAttachmentService) {
		this.wfiTempAttachmentService = wfiTempAttachmentService;
	}
}
