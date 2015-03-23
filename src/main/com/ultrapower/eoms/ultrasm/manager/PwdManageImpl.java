package com.ultrapower.eoms.ultrasm.manager;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.PwdManage;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.PwdManageService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class PwdManageImpl implements PwdManageService
{
	private IDao<PwdManage> pwdManageDao;
	private UserManagerService userManagerService;
	
	public String isCanLogin(String loginName)
	{
		String result = "";
		if("".equals(StringUtils.checkNullString(loginName)))
			return "用户登陆名为空，请重新登陆系统！";
		HashMap<String, String> pwdManageCfg = this.getPwdManageCfg();
		if(pwdManageCfg == null)
			return "获取密码验证配置信息失败，请联系管理员！";
		List<PwdManage> pwdList = pwdManageDao.find(" from PwdManage where loginname = ? order by lastmodifytime desc", new Object[] {loginName});
		if(pwdList != null && pwdList.size() > 0)
		{
			int updateTime = 0;
			if(NumberUtils.isNumeric(pwdManageCfg.get("updateTime")))
				updateTime = NumberUtils.formatToInt(pwdManageCfg.get("updateTime"));
			if(updateTime > 0)
			{
				Long lastModifyTime = pwdList.get(0).getLastmodifytime();
				if(TimeUtils.getCurrentTime() - lastModifyTime > updateTime)
				{
					result = StringUtils.checkNullString(pwdManageCfg.get("updateTime_Msg"));
				}
			}
		}
		else
		{
			result = "登陆成功！首次登陆系统，请先修改密码！";
		}
		return result;
	}
	
	public String isEnablePwd(String loginName, String pwd)
	{
		String result = "";
		if("".equals(StringUtils.checkNullString(loginName)))
			return "用户登陆名为空，请重新登陆系统！";
		
		if(pwd == null)
			return "密码信息获取失败，请重新登陆系统！";
		
		HashMap<String, String> pwdManageCfg = this.getPwdManageCfg();
		if(pwdManageCfg == null)
			return "获取密码验证配置信息失败，请联系管理员！";
		
		//密码长度验证
		int pwdLen = pwd.length();
		String length_Min = StringUtils.checkNullString(pwdManageCfg.get("length_Min"));
		String length_Max = StringUtils.checkNullString(pwdManageCfg.get("length_Max"));
		int min = "".equals(length_Min) ? 3  : NumberUtils.formatToInt(length_Min);
		int max = "".equals(length_Max) ? 30 : NumberUtils.formatToInt(length_Max);
		if(pwdLen < min || pwdLen > max)
			return StringUtils.checkNullString(pwdManageCfg.get("length_Msg"));
		
		//密码字符验证
		if(!"false".equals(StringUtils.checkNullString(pwdManageCfg.get("wordType"))))
		{
			String simplePwd = pwd.replaceAll("\\p{P}", ""); //去除pwd字符串中所有的符号字符
			Matcher matcher  = Pattern.compile("^[0-9a-zA-Z]+$").matcher(simplePwd);
			boolean isFind = matcher.find();
			Matcher matcherSz = Pattern.compile("^[0-9]+$").matcher(simplePwd);
			boolean isFindSz = matcherSz.find();
			Matcher matcherZm = Pattern.compile("^[a-zA-Z]+$").matcher(simplePwd);
			boolean isFindZm = matcherZm.find();
			String word_word = StringUtils.checkNullString(pwdManageCfg.get("word_word"));
			if("must".equals(word_word)) {
				if(!isFind || isFindSz)
					return "根据规则定制，密码必须包含字母字符！";
			} else if("".equals(word_word)) {
				if(isFind && !isFindSz)
					return "根据规则定制，密码不能包含字母字符！";
			}
			String word_number = StringUtils.checkNullString(pwdManageCfg.get("word_number"));
			if("must".equals(word_number)) {
				if(!isFind || isFindZm)
					return "根据规则定制，密码必须包含数字字符！";
			} else if("".equals(word_number)) {
				if(isFind && !isFindZm)
					return "根据规则定制，密码不能包含数字字符！";
			}
			String word_symbol = StringUtils.checkNullString(pwdManageCfg.get("word_symbol"));
			if("must".equals(word_symbol)) {
				if(simplePwd.equals(pwd))
					return "根据规则定制，密码必须包含符号字符！";
			} else if("".equals(word_symbol)) {
				if(!simplePwd.equals(pwd))
					return "根据规则定制，密码不能包含符号字符！";
			}
		}
		
		//连续字符验证
		if("true".equals(StringUtils.checkNullString(pwdManageCfg.get("sameWord"))))
		{
			if("".equals(pwd.replaceAll(pwd.substring(0,1), "")))
			{
				return StringUtils.checkNullString(pwdManageCfg.get("sameWord_Msg"));
			}
		}
		try
		{
			//用户信息验证
			if("true".equals(StringUtils.checkNullString(pwdManageCfg.get("userInfo"))))
			{
				UserInfo user = userManagerService.getUserByLoginName(loginName, false);
				if(user == null)
					return "用户信息获取失败！";
				String detail = StringUtils.checkNullString(pwdManageCfg.get("detail"));
				boolean isAll = false;
				if("all".equals(detail))
					isAll = true;
				if(isAll || "true".equals(StringUtils.checkNullString(pwdManageCfg.get("loginname"))))
				{
					if(loginName.toLowerCase().indexOf(pwd.toLowerCase()) >= 0)//密码与用户登录名是否一致
						return "为了您的安全性，密码与用户登陆名类似，请重新输入！";
				}
				if(isAll || "true".equals(StringUtils.checkNullString(pwdManageCfg.get("fullname"))))
				{
					String pyname = StringUtils.checkNullString(user.getPyname());
					if(pyname.toLowerCase().indexOf(pwd.toLowerCase()) >= 0)//密码与用户全名的拼音类似
						return "为了您的安全性，密码与用户全名的拼音类似，请重新输入！";
				}
				if(isAll || "true".equals(StringUtils.checkNullString(pwdManageCfg.get("mobile"))))
				{
					if(pwd.equals(StringUtils.checkNullString(user.getMobile())))//密码是否与手机号码一致
						return "为了您的安全性，密码与手机号码一致，请重新输入！";
				}
				if(isAll || "true".equals(StringUtils.checkNullString(pwdManageCfg.get("phone"))))
				{
					if(pwd.equals(StringUtils.checkNullString(user.getPhone())))//密码是否与固定电话一致
						return "为了您的安全性，密码与固定电话号码一致，请重新输入！";
				}
				if(isAll || "true".equals(StringUtils.checkNullString(pwdManageCfg.get("fax"))))
				{
					if(pwd.equals(StringUtils.checkNullString(user.getFax())))//密码是否与QQ号码一致
						return "为了您的安全性，密码与传真一致，请重新输入！";
				}
				if(isAll || "true".equals(StringUtils.checkNullString(pwdManageCfg.get("qq"))))
				{
					if(pwd.equals(StringUtils.checkNullString(user.getQq())))//密码是否与QQ号码一致
						return "为了您的安全性，密码与QQ号码一致，请重新输入！";
				}
			}
			
			//密码记录次数验证 不能与近n次的密码一致
			List<PwdManage> pwdList = pwdManageDao.find(" from PwdManage where loginname = ? order by lastmodifytime desc", new Object[] {loginName});
			if(pwdList != null && pwdList.size() > 0)
			{
				String recordCount = StringUtils.checkNullString(pwdManageCfg.get("recordCount"));
				int count = "".equals(recordCount) ? 1 : NumberUtils.formatToInt(recordCount);
				int len = pwdList.size();
				if(len > count)
					len = count;
				PwdManage pwdManage;
				for(int i=0 ; i<len ; i++)
				{
					pwdManage = pwdList.get(i);
					if(pwd.equals(userManagerService.decodePwd(pwdManage.getPwd())))
					{
						result = StringUtils.checkNullString(pwdManageCfg.get("recordCount_Msg"));
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean saveUserPwd(String loginName, String pwd)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(loginName)))
		{
			return result;
		}
		List<PwdManage> pwdList = null;
		try
		{
			pwdList = pwdManageDao.find(" from PwdManage where loginname = ? order by lastmodifytime", new Object[] {loginName});
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return result;
		}
		PwdManage pwdManage;
		UserSession userSession = (UserSession) ActionContext.getRequest().getSession().getAttribute("userSession");
		String userpid = "";
		if(userSession != null)
		{
			userpid = userSession.getPid();
		}
		else
		{
			userpid = userManagerService.getPidByLoginName(loginName);
		}
		long currentTime = TimeUtils.getCurrentTime();
		int pwdLen = 0;
		if(pwdList != null)
			pwdLen = pwdList.size();
		String visiblepwd = pwd;
		pwd = userManagerService.encodeUserPwd(pwd);
		HashMap<String, String> pwdManageCfg = this.getPwdManageCfg();
		int count = 1;
		if(pwdManageCfg != null)
		{
			String recordCount = StringUtils.checkNullString(pwdManageCfg.get("recordCount"));
			if(!"".equals(recordCount))
				count = NumberUtils.formatToInt(recordCount);
		}
		if(pwdLen >= count)
		{
			pwdManage = pwdList.get(0);
			pwdManage.setVisiblepwd(visiblepwd);
			pwdManage.setPwd(pwd);
			pwdManage.setLastmodifier(userpid);
			pwdManage.setLastmodifytime(currentTime);
		}
		else
		{
			pwdManage = new PwdManage();
			pwdManage.setLoginname(loginName);
			pwdManage.setPwd(pwd);
			pwdManage.setVisiblepwd(visiblepwd);
			pwdManage.setCreater(userpid);
			pwdManage.setCreatetime(currentTime);
			pwdManage.setLastmodifier(userpid);
			pwdManage.setLastmodifytime(currentTime);
		}
		try
		{
			pwdManageDao.saveOrUpdate(pwdManage);
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	private HashMap<String, String> getPwdManageCfg()
	{
		Object obj = BaseCacheManager.get("pwdManageCfg", "pwdManageCfg");
		HashMap<String, String> pwdManageCfg = null;
		if(obj != null)
			pwdManageCfg = (HashMap<String, String>) obj;
		return pwdManageCfg;
	}

	public void setPwdManageDao(IDao<PwdManage> pwdManageDao) {
		this.pwdManageDao = pwdManageDao;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
}
