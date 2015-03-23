/**
 * Copyright (c) 2012 神州泰岳服务管理事业部
 * All rights reserved.
 *
 * 文件名称: AutoCreateImpl.java
 * 摘   要: 月计划派发生成个人工作任务及月汇总
 * 
 * 当前版本：
 * 作   者：PanGC
 * 完成日期：
 */
package com.ultrapower.eoms.msextend.notice.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.msextend.notice.model.NoticeInfo;
import com.ultrapower.eoms.msextend.notice.model.NoticeLevelManage;
import com.ultrapower.eoms.msextend.notice.model.NoticeViewLog;
import com.ultrapower.eoms.msextend.notice.model.ReciverInfo;
import com.ultrapower.eoms.msextend.notice.service.INoticeLevelService;
import com.ultrapower.eoms.msextend.notice.service.INoticeViewLogService;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.sheettag.common.PageInfo;

@Transactional
public class NoticeViewLogImpl implements INoticeViewLogService{
	
	private IDao<NoticeLevelManage> noticeLevelDao;
	private IDao<NoticeInfo> noticeInfoDao;
	private IDao<NoticeViewLog> noticeViewLogDao;
	
	private INoticeLevelService noticeLevelService;
	
	public void create(String noticeLevel,String noitceid) {
		this.delete(noitceid);
		List<NoticeLevelManage> noticeLevelList = this.findNotcieLevelManageByLevel(noticeLevel);
		this.createNoticeView(noticeLevelList,noitceid);
	}
	
	/**
	 * 根据自定义公告查看人创建公告
	 * @param selectPerson 格式为D:id,name,fullname;U:id,name,loginname;
	 * @param noitceid
	 */
	public void createBySelectPerson(String selectPerson,String noitceid){
		this.delete(noitceid);
		List<ReciverInfo> reciverInfoList = noticeLevelService.splitUserStr(selectPerson);
		NoticeViewLog noticeViewLog = null;
		List<String> depList = new ArrayList<String>();
		for(int i=0,len=reciverInfoList.size();i<len;i++){
			ReciverInfo reciverInfo = reciverInfoList.get(i);
			if("U".equals(reciverInfo.getRecivertype())){
				noticeViewLog = new NoticeViewLog();
				noticeViewLog.setNoticeId(noitceid);
				noticeViewLog.setIsview("0");
				noticeViewLog.setUserId(reciverInfo.getReciverid());
				noticeViewLog.setReciverId(reciverInfo.getReciverid());
				noticeViewLogDao.save(noticeViewLog);
			}else if("D".equals(reciverInfo.getRecivertype())){
				depList.add(reciverInfo.getReciverid());
			}
		}
		this.createDepNoticeView(depList,noitceid);
	}
	
	public void delete(String noitceid) {
		String hql = "delete from NoticeViewLog nlm where nlm.noticeId = ?";
		RecordLog.printLog("noticeId:"+noitceid);
		noticeViewLogDao.executeUpdate(hql, new Object[]{noitceid});
	}
	
	/**
	 * 设置该人已查看公告
	 * @return
	 */
	public void setIsView(String noticeId,String userId){
		String hql = "update NoticeViewLog nlm set nlm.isview = '1' where nlm.noticeId = ? and nlm.userId = ?";
		RecordLog.printLog("noticeId:"+noticeId+";userId:"+userId);
		noticeViewLogDao.executeUpdate(hql, noticeId,userId);
	}
	
	private List<NoticeLevelManage> findNotcieLevelManageByLevel(String noticeLevel){
		String hql = " from NoticeLevelManage nlm where nlm.noticeLevel = ?";
		RecordLog.printLog("noticeLevel:"+noticeLevel);
		List<NoticeLevelManage> results= noticeLevelDao.find(hql, noticeLevel);
		return results;
	}
	
	/**
	 * 根据公告级别生成公告查看人
	 * @param noticeLevelList
	 * @param noitceid
	 */
	private void createNoticeView(List<NoticeLevelManage> noticeLevelList,String noitceid){
		if(noticeLevelList == null){
			return;
		}
		NoticeViewLog noticeViewLog = null;
		List<String> depList = new ArrayList<String>();
		for(int i=0,len=noticeLevelList.size();i<len;i++){
			NoticeLevelManage noticeLevelManage = noticeLevelList.get(i);
			if("U".equals(noticeLevelManage.getReciverType())){
				noticeViewLog = new NoticeViewLog();
				noticeViewLog.setNoticeId(noitceid);
				noticeViewLog.setIsview("0");
				noticeViewLog.setUserId(noticeLevelManage.getReciverId());
				noticeViewLog.setReciverId(noticeLevelManage.getReciverId());
				noticeViewLogDao.save(noticeViewLog);
			}else if("D".equals(noticeLevelManage.getReciverType())){
				depList.add(noticeLevelManage.getReciverId());
			}
		}
		this.createDepNoticeView(depList,noitceid);
	}
	
	/**
	 * 创建组公告
	 * @param list
	 * @param noitceid
	 */
	private void createDepNoticeView(List<String> depIdList,String noitceid){
		if(depIdList == null || depIdList.isEmpty()){
			return ;
		}
		NoticeViewLog noticeViewLog = null;
		DataTable dataTable = this.getUserByDepid(depIdList);
		for(int j=0,dlen = dataTable.length();j<dlen;j++){
			DataRow dataRow = dataTable.getDataRow(j);
			String userId = dataRow.getString("userid");
			String depid = dataRow.getString("depid");
			noticeViewLog = new NoticeViewLog();
			noticeViewLog.setNoticeId(noitceid);
			noticeViewLog.setIsview("0");
			noticeViewLog.setUserId(userId);
			noticeViewLog.setReciverId(depid);
			noticeViewLogDao.save(noticeViewLog);
		}
	}
	
	/**
	 * 递归获取组内所有人员
	 * @param depIdList
	 * @return
	 */
	private DataTable getUserByDepid(List<String> depIdList){
		DataTable table = null;
		if(depIdList == null || depIdList.size() == 0){
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		for(int i=0;i<depIdList.size();i++){
			String depId = depIdList.get(i);
			if(!"".equals(depId)){
				if(i==0){
					buffer.append(" d.PID='");
				}else{
					buffer.append(" or d.PID='");
				}
				buffer.append(depId);
				buffer.append("'");
			}
		}
		if(buffer.length() == 0){
			return null;
		}
		
		sqlBuffer.append("SELECT ud.userid,ud.LOGINNAME,u.FULLNAME,ud.depid FROM bs_t_sm_userdep ud,bs_t_sm_user u WHERE  u.pid = ud.userid and ud.depid in (");
		sqlBuffer.append("SELECT d.pid FROM BS_T_SM_DEP d  WHERE 1 = 1 START WITH (");
		sqlBuffer.append(buffer);
		sqlBuffer.append(") CONNECT BY d.PARENTID = PRIOR d.PID)");
		
		QueryAdapter queryAdapter = new QueryAdapter();
		table = queryAdapter.executeQuery(sqlBuffer.toString());
		return table;
	}
	
	/**
	 * 弹出气泡
	 * @param userId
	 * @return
	 */
	public String getNoticePopView(String userId){
		String resultView = "";
		String sqlname = "SQL_NOITCEVIEWLOG_List.query";
		HashMap hashMap=new HashMap();
		RQuery rQuery=new RQuery(sqlname,hashMap);
		DataTable datatable=rQuery.getDataTable();
		RecordLog.printLog(rQuery.getSqlString());
		int rowCount = datatable.length();
		if(rowCount > 1){
			resultView = getRowCountView(rowCount);
		}else if(rowCount == 1){
			String noticetitle = datatable.getDataRow(0).getString("noticetitle");
			String noticeId = datatable.getDataRow(0).getString("noticeid");
			resultView = getContentView(noticetitle,noticeId);
		}
		return resultView;
	}
	
	public String getRowCountView(int rowCount){
		StringBuffer result = new StringBuffer();
		result.append("<a href=\"#\" onClick=\"openRowCountView()\">");
		result.append("您有"+rowCount+"条公告需要查看");
		result.append("</a>");
		return result.toString();
	}
	
	public String getContentView(String noticetitle,String noticeId){
		StringBuffer result = new StringBuffer();
		result.append("<a href=\"#\" onClick=\"openContentView('"+noticeId+"')\">");
		result.append(noticetitle);
		result.append("</a>");
		return result.toString();
	}
	
	/**
	 * 所有设置为已读
	 */
	public void allToRead(String userId) {
		String hql = " update NoticeViewLog nvl set nvl.isview = '1'  where nvl.userId = ?";
		RecordLog.printLog("userId:"+userId);
		noticeLevelDao.executeUpdate(hql, userId);
	}
	
	/**
	 * 为值班平台提供首页公告
	 * @param userId
	 * @return
	 */
	public List<NoticeInfo> dutynoticeView(String userId,PageInfo page){
		String sql = "select t.NOTICE_TITLE as title,t.NOTICE_CONTENT  as cont,t.pid  as pid from BS_T_NOTICE_INFO t where  " +
				     "exists (select 1 from BS_T_NOTICE_VIEWLOG nv  where  nv.NOTICE_ID = t.pid and (  nv.USER_ID = ?)) " +
				     "and (noticetype = '1' or noticetype = '10') and NOTICE_STARTTIME <= '"+TimeUtils.getCurrentTime()+"' and   NOTICE_LOSTTIME >= '"+TimeUtils.getCurrentTime()+"' and   NOTICE_STATUS <> '4' order by t.notice_createtime desc";
		QueryAdapter qa = new QueryAdapter();
		List<NoticeInfo> noticeList = new ArrayList<NoticeInfo>();
		NoticeInfo notice =  null;
		DataTable dt = qa.executeQuery(sql,new Object[] { userId }, page.getPage(),page.getPageSize(), 2);
		int length = 0;
		if(dt!=null){
			length = dt.length();
		}
		DataRow datarow = null;
		for(int row=0;row<length;row++){
			notice =  new NoticeInfo();
			datarow  =  dt.getDataRow(row);
			notice.setNoticeTitle(datarow.getString("title"));
			notice.setNoticeContent(datarow.getString("cont"));
			notice.setPid(datarow.getString("pid"));
			noticeList.add(notice);
		}
		return noticeList;
		
		
	//	String hql = " from NoticeInfo t where  exists (select 1 from NoticeViewLog nv  where  nv.noticeId = t.pid and (  nv.userId = ?))";
	//	return noticeInfoDao.createQuery(hql, new Object[] { userId }).setFirstResult(page.getFirstItemPos()).setMaxResults(page.getPageSize()).list();
	//	return noticeInfoDao.find(hql,userId);
	}

	public Long getCount(String userId){
		String hql = "select count(*) from NoticeInfo t where  exists (select 1 from NoticeViewLog nv  where  nv.noticeId = t.pid and (  nv.userId = ?))" +
		                 "and noticetype = '1' and noticeStartTime <= '"+TimeUtils.getCurrentTime()+"' and   noticeLostTime >= '"+TimeUtils.getCurrentTime()+"' and   noticeStatus <> '4' order by t.noticeCreateTime desc";
		return (Long) noticeInfoDao.find(hql,userId).get(0);
	}
	
	
	public IDao<NoticeLevelManage> getNoticeLevelDao() {
		return noticeLevelDao;
	}

	public void setNoticeLevelDao(IDao<NoticeLevelManage> noticeLevelDao) {
		this.noticeLevelDao = noticeLevelDao;
	}

	public IDao<NoticeInfo> getNoticeInfoDao() {
		return noticeInfoDao;
	}

	public void setNoticeInfoDao(IDao<NoticeInfo> noticeInfoDao) {
		this.noticeInfoDao = noticeInfoDao;
	}

	public IDao<NoticeViewLog> getNoticeViewLogDao() {
		return noticeViewLogDao;
	}

	public void setNoticeViewLogDao(IDao<NoticeViewLog> noticeViewLogDao) {
		this.noticeViewLogDao = noticeViewLogDao;
	}

	public INoticeLevelService getNoticeLevelService() {
		return noticeLevelService;
	}

	public void setNoticeLevelService(INoticeLevelService noticeLevelService) {
		this.noticeLevelService = noticeLevelService;
	}
	
}
