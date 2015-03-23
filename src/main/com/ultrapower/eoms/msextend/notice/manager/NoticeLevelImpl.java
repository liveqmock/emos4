package com.ultrapower.eoms.msextend.notice.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.BeanUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.msextend.notice.model.NoticeLevelManage;
import com.ultrapower.eoms.msextend.notice.model.ReciverInfo;
import com.ultrapower.eoms.msextend.notice.service.INoticeLevelService;

/**
 * @author yxg
 * @version 创建时间：Dec 27, 2012 11:30:26 AM
 * 类说明：
 */

public class NoticeLevelImpl implements INoticeLevelService{

	private IDao<NoticeLevelManage> noticeLevelDao;
	
	public boolean saveOrUpdateNoticeLevel(NoticeLevelManage noticeLevelManage,String selectUser) {
		// 判断为添加
		if(noticeLevelManage.getPid() == null || "".equals(noticeLevelManage.getPid())){
			long currentTime = TimeUtils.getCurrentTime();
			noticeLevelManage.setCreateTime(currentTime);
		}
		List<ReciverInfo> userList = this.splitUserStr(selectUser);
		for(int i=0;i<userList.size();i++){
			ReciverInfo reciverInfo = userList.get(i);
			if(reciverInfo != null){
				NoticeLevelManage saveNoticeLevel = this.getNoticeLevelByReciverId(reciverInfo.getReciverid(),reciverInfo.getRecivertype(),noticeLevelManage.getNoticeLevel());
				if(saveNoticeLevel == null){
					try {
						saveNoticeLevel = (NoticeLevelManage)BeanUtils.cloneBean(noticeLevelManage);
					} catch (Exception e) {
						e.printStackTrace();
					}
					saveNoticeLevel.setReciverId(reciverInfo.getReciverid());
					saveNoticeLevel.setReciverType(reciverInfo.getRecivertype());
					noticeLevelDao.saveOrUpdate(saveNoticeLevel);
				}
			}
		}
 		return true;
	}
	
	public void delNoticeLevel(String delIds) {
		if (delIds != null && !"".equals(delIds)) {
			String[] ids = delIds.split(",");
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					noticeLevelDao.removeById(ids[i]);
				}
			}
		}
	}

	public IDao<NoticeLevelManage> getNoticeLevelDao() {
		return noticeLevelDao;
	}

	public void setNoticeLevelDao(IDao<NoticeLevelManage> noticeLevelDao) {
		this.noticeLevelDao = noticeLevelDao;
	}
	
	/**
	 * 根据人员id与公告级别查询级别配置
	 * @param reciverId
	 * @param noticeLevelStr
	 * @return
	 */
	public NoticeLevelManage getNoticeLevelByReciverId(String reciverid,String reciverType,String noticeLevelStr){
		NoticeLevelManage niticeLevel = null;
		String hql = "from NoticeLevelManage nl where nl.reciverId = ? and reciverType = ? and noticeLevel = ?";
		RecordLog.printLog("reciverId:"+reciverid+";reciverType="+reciverType+";noticeLevel:"+noticeLevelStr);
		List<NoticeLevelManage> list = noticeLevelDao.find(hql, new Object[]{reciverid,reciverType,noticeLevelStr});
		if(list != null && list.size() > 0){
			niticeLevel = list.get(0);
		}
		return niticeLevel;
	}
	
	/**
	 * 分解人员字符串
	 * @param userStr
	 * @return [0]人员id,[1]人员全名,[2]登录名
	 */
	public List<ReciverInfo> splitUserStr(String userStr){
		List<ReciverInfo> result = new ArrayList<ReciverInfo>();
		if(userStr != null && userStr.indexOf(";") > -1){
			String[] userStrs = userStr.split(";");
			for(int i=0;i<userStrs.length;i++){
				String users = userStrs[i];
				if(users.indexOf(",") > -1){
					ReciverInfo reciverInfo = new ReciverInfo();
					String[] user = users.split(",");
					String reciverid = user[0].split(":")[1];
					String recivertype = user[0].split(":")[0];
					String name = user[1].replaceAll("\\(.*?\\)", "");
					
					reciverInfo.setReciverid(reciverid);
					reciverInfo.setRecivertype(recivertype);
					reciverInfo.setName(name);
					result.add(reciverInfo);
				}
			}
		}
		return result;
	}
}
