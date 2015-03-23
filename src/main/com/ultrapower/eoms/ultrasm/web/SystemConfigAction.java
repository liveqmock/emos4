package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.io.PrintWriter;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.service.DataShiftService;

public class SystemConfigAction extends BaseAction {
	private DataShiftService dataShiftService;
	
	/**
	 * 加载在线帮助配置文件
	 * @throws IOException
	 */
//	public void refreshCacheXml() throws IOException
//	{
//		ResolveHelpNodeXml.resolveHelpNode();
//		PrintWriter out = this.getResponse().getWriter();
//		out.print("true");
//	}
	
	/**
	 * 设置登录手机验证启停
	 * @throws IOException
	 */
	public void setCheckCodeStatus() throws IOException
	{
		String type = StringUtils.checkNullString(this.getRequest().getParameter("type"));
		String checkcode = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.checkcode"));
		String result = "";
		if("true".equals(checkcode))
		{
			if("click".equals(type))
			{
				if(Constants.CHECKCODE_MANAGE)
					Constants.CHECKCODE_MANAGE = false;
				else
					Constants.CHECKCODE_MANAGE = true;
			}
			result = String.valueOf(Constants.CHECKCODE_MANAGE);
		}
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}
	
	/**
	 * 修改后门登录标识
	 * @throws IOException
	 */
	public void updateLoginCode() throws IOException
	{
		String logincode = StringUtils.checkNullString(this.getRequest().getParameter("logincode"));
		String newCode = "";
		if(!"".equals(logincode))
			Constants.LOGIN_CODE = logincode;
		newCode = Constants.LOGIN_CODE;
		PrintWriter out = this.getResponse().getWriter();
		out.print(newCode);
	}
	
	/**
	 * 更新菜单目录树当前位置数据
	 * @throws IOException
	 */
	public void updateNodepath() throws IOException
	{
		dataShiftService.updateNodepath();
		PrintWriter out = this.getResponse().getWriter();
		out.print("true");
	}
	
	/**
	 * 整体更新用户的拼音名称
	 * @throws IOException
	 */
	public void updateUserPyName() throws IOException
	{
		dataShiftService.updateUserPyName();
		PrintWriter out = this.getResponse().getWriter();
		out.print("true");
	}
	
	public void validateTime() throws IOException
	{
		long start = TimeUtils.formatDateStringToInt(StringUtils.checkNullString(this.getRequest().getParameter("start")));
		long end = TimeUtils.formatDateStringToInt(StringUtils.checkNullString(this.getRequest().getParameter("end")));
		PrintWriter out = this.getResponse().getWriter();
		out.print(end - start);
	}
	
	public void setDataShiftService(DataShiftService dataShiftService) {
		this.dataShiftService = dataShiftService;
	}
}
