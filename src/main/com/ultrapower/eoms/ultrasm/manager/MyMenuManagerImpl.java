package com.ultrapower.eoms.ultrasm.manager;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.model.MyMenu;
import com.ultrapower.eoms.ultrasm.service.DnsManagerService;
import com.ultrapower.eoms.ultrasm.service.MyMenuManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 14, 2010 2:28:36 PM
 * @descibe 
 */
public class MyMenuManagerImpl implements MyMenuManagerService
{
	private IDao<MyMenu> myMenuDao;
	private DnsManagerService dnsManagerService;
	
	public String addMymenu(MyMenu myMenu)
	{
		if(myMenu == null)
			return null;
		String myMenuId = "";
		try
		{
			myMenuDao.save(myMenu);
			myMenuId = StringUtils.checkNullString(myMenu.getPid());
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return myMenuId;
	}
	
	public List<MyMenu> getList(String parentid,String userid) {
		List<MyMenu> mymenuList = myMenuDao.find(" from MyMenu where status = '1' and parentid = ? and userid = ? order by ordernum", new Object[]{parentid,userid});
		return mymenuList;
	}
	
	public String getMyMenuListHtml(String userid){
		List<MyMenu> mymenuList = getList("0",userid);
		StringBuffer firstItsmLi = new StringBuffer();
		for(MyMenu myMenu : mymenuList){
			String pid = myMenu.getPid();
			String nodename = myMenu.getNodename();
			String nodeurl = myMenu.getNodeurl();
			String nodeType = myMenu.getNodetype();
			firstItsmLi.append("<li class=\"first_item_li\">");
			if(!"".equals(StringUtils.checkNullString(nodeurl)))
				firstItsmLi.append("<a href=\"javascript:void(0)\" onclick=\"openwindow('"+nodeurl+"','',800,600);\" class=\"first_item_a\">");
			else
				firstItsmLi.append("<a href=\"#\" class=\"first_item_a\">");
			firstItsmLi.append("<span class=\"mymenu_icon\"></span>");
			firstItsmLi.append("<span class=\"first_item_aSpan\">");
			firstItsmLi.append(nodename+"</span></a>");
			if(nodeType.equals("1"))//菜单夹
				firstItsmLi.append(getMyMenuChildRenHtml(pid,userid));
			firstItsmLi.append("</li>");
		}  
		return firstItsmLi.toString();
	}
	
	public String getMyMenuChildRenHtml(String parentid,String userid){
		List<MyMenu> mymenuList = getList(parentid,userid);
		int mymenuListLen = 0;
		if(mymenuList!=null)
			mymenuListLen = mymenuList.size();
		StringBuffer secondItsmLi = new StringBuffer();
		if(mymenuListLen>0){
			secondItsmLi.append("<ul>");
			for(MyMenu myMenu : mymenuList){
				String pid = myMenu.getPid();
				String nodename = myMenu.getNodename();
				String nodeurl = myMenu.getNodeurl();
				String nodeType = myMenu.getNodetype();
				secondItsmLi.append("<li class=\"second_item_li\">");
				if(!"".equals(StringUtils.checkNullString(nodeurl)))
					secondItsmLi.append("<a href=\"javascript:void(0)\" onclick=\"openwindow('"+nodeurl+"','',800,600);\" class=\"first_item_a\">");
				else
					secondItsmLi.append("<a href=\"#\" class=\"first_item_a\">");
				secondItsmLi.append("<span class=\"mymenu_icon2\"></span>");
				secondItsmLi.append("<span class=\"first_item_aSpan\">");
				secondItsmLi.append(nodename+"</span></a>");
				if(nodeType.equals("1"))//菜单夹
					secondItsmLi.append(getMyMenuChildRenHtml(pid,userid));
				secondItsmLi.append("</li>");
			}
			secondItsmLi.append("</ul>");
		}
		return secondItsmLi.toString();
	}
	
	public MyMenu getMyMenuByID(String myMenuId)
	{
		if("".equals(StringUtils.checkNullString(myMenuId)))
			return null;
		MyMenu myMenu = null;
		try
		{
			myMenu = myMenuDao.get(myMenuId);
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return myMenu;
	}
	
	public String updateMymenu(MyMenu myMenu)
	{
		if(myMenu == null)
			return null;
		String myMenuId = "";
		try
		{
			myMenuDao.saveOrUpdate(myMenu);
			myMenuId = myMenu.getPid();
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return myMenuId;
	}
	
	public boolean deleteMyMenuByID(String myMenuId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(myMenuId)))
		{
			return result;
		}
		//获取当前节点的子集节点IDList
		List myMenuIdList = dnsManagerService.getSubIdList("bs_t_sm_mymenu", myMenuId);
		if(myMenuIdList != null && myMenuIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(myMenuIdList);
				//删除菜单节点与子集所有节点信息
				myMenuDao.executeUpdate(" delete MyMenu where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				result = true;
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}
	
	public void setMyMenuDao(IDao<MyMenu> myMenuDao) {
		this.myMenuDao = myMenuDao;
	}
	public void setDnsManagerService(DnsManagerService dnsManagerService) {
		this.dnsManagerService = dnsManagerService;
	}
}
