package com.ultrapower.eoms.ultrasm.web;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.service.DataShiftService;

public class DataShiftAction extends BaseAction
{
	private DataShiftService dataShiftService;
	private List<String> shiftInfoList;

	public String orgShift()
	{
		shiftInfoList = dataShiftService.organizationShift();
		return SUCCESS;
	}
	
	public void createDepDns()
	{
		dataShiftService.createDepDns();
	}
	
	public void exportInitData()
	{
		UserSession userSession = this.getUserSession();
		String loginName = "";
		if(userSession != null)
			loginName = userSession.getLoginName();
		if("Demo".equals(loginName))
		{
			String dateStr = TimeUtils.getCurrentDate("yyyyMMdd");
			String folderPath = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.exportinitdata.path"));//存储文件夹路径
			folderPath = folderPath.replaceAll("\\\\", "/");
			if("".equals(folderPath))
			{
				System.out.println("导出失败！未配置导出路径，请查看配置文件security.properties中的eoms.exportinitdata.path");
			}
			else
			{
				String downLoadPath = folderPath + "/sm_data" + "_" + dateStr + ".sql";//初始化文件下载地址
				List<String> tbNameList = new ArrayList();
				//人员初始化数据 Demo;角色初始化数据 系统管理员;人员和角色关系
				tbNameList.add("bs_t_sm_user");
				tbNameList.add("bs_t_sm_role");
				tbNameList.add("bs_t_sm_roleorg");
				tbNameList.add("bs_t_sm_menutree");//菜单目录树
				tbNameList.add("bs_t_sm_resource");//资源信息
				//tbNameList.add("bs_t_sm_resproperty");//资源属性信息
				tbNameList.add("bs_t_sm_operate");//操作信息
				tbNameList.add("bs_t_sm_resourceurl");//资源URL信息
				tbNameList.add("bs_t_sm_rolemenutree,roleid='297e39d1298150e8012981714a5a0004'");//角色菜单目录配置信息
				tbNameList.add("bs_t_sm_roleresop,roleid='297e39d1298150e8012981714a5a0004'");//角色资源操作配置信息
				tbNameList.add("bs_t_sm_dictype");//数据字典类型信息
				tbNameList.add("bs_t_sm_dicitem");//数据字典信息项信息
				//tbNameList.add("bs_t_sm_table");//库表结构表信息
				//tbNameList.add("bs_t_sm_field");//库表结构字段信息
				tbNameList.add("bs_t_sm_excelexpcfg");   //EXCEL导出配置信息 表
				tbNameList.add("bs_t_sm_excelexpcfgrow");//EXCEL导出配置信息 行
				tbNameList.add("bs_t_sm_excelexpcfgfld");//EXCEL导出配置信息 字段
				if(dataShiftService.exportInitData(downLoadPath, tbNameList))
					System.out.println("导出成功！");
				else
					System.out.println("导出失败！");
			}
		}
		else
		{
			System.out.println("无权导出！");
		}

	}
	
	public void updateByMidTable()
	{
		dataShiftService.updateByMidTable();
	}
	
	public void updateNodepath()
	{
		dataShiftService.updateNodepath();
	}
	
	public void updateUserPyName()
	{
		dataShiftService.updateUserPyName();
	}
	
	public void setDataShiftService(DataShiftService dataShiftService) {
		this.dataShiftService = dataShiftService;
	}
	public List<String> getShiftInfoList() {
		return shiftInfoList;
	}
}
