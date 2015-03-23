package com.ultrapower.eoms.workflow.sheet.role.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.plugin.excelutils.ExcelReader;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.RoleUser;
import com.ultrapower.workflow.role.service.IRoleService;

public class ChildRoleExtendAction extends BaseAction
{
	private String excelFileName;
	private String excelContentType;
	private File excel;
	private IRoleService roleService;
	private IDao<ChildRole> childRoleDao;
	
	private UserManagerService userManagerService;
	
	public IDao<ChildRole> getChildRoleDao()
	{
		return childRoleDao;
	}
	public void setChildRoleDao(IDao<ChildRole> childRoleDao)
	{
		this.childRoleDao = childRoleDao;
	}
	public String upload()
	{
		System.out.println("##################");
		System.out.println("开始导入角色细分");
		System.out.println("##################");
		
		try
		{
			ExcelReader reader = new ExcelReader(new FileInputStream(excel));

			System.out.println("##################");
			//读取第一行的配置信息
			//第一列为工单类型
			String baseShema = reader.readExcelCell(0, 0, 0);
			System.out.println("##-工单类型：" + baseShema);
			//第二列为大角色标识
			String roleCode = reader.readExcelCell(0, 0, 1);
			System.out.println("##-大角色标识：" + roleCode);
			//第三列为大角色中文名
			String roleName = reader.readExcelCell(0, 0, 2);
			System.out.println("##-大角色中文名：" + roleName);
			//第四列为导入方式：1为重新导入，其他未追加导入
			String importTypeStr = reader.readExcelCell(0, 0, 3);
			int importType = ((importTypeStr!=null && importTypeStr.equals("1")) ? 1 : 2);
			System.out.println("##-导入方式：" + (importType != 1 ? "追加导入" : "重新导入"));
			//如果关联流程版本，则第五列为流程版本
			String baseVersion = reader.readExcelCell(0, 0, 4);
			if(baseVersion == null) baseVersion = "";
			
			//删除原有的角色细分以及人员信息
			if(importType == 1)
			{
				System.out.println("##-删除原有角色信息：" + roleName);
				List<ChildRole> oldRoleList = null;
				if(baseVersion.equals(""))
				{
					oldRoleList = childRoleDao.find("from ChildRole c where c.roleCode = ?", roleCode);
				}
				else
				{
					oldRoleList = childRoleDao.find("from ChildRole c where c.roleCode = ? and c.baseVersion = ?", baseVersion);
				}
				for(ChildRole role : oldRoleList)
				{
					roleService.removeChildRoleById(role.getChildRoleId());
				}
			}
			
			//读取第二行的Excel中的维度数据
			List<String> dimList = new ArrayList<String>();
			int tempCellNum = 0;
			System.out.println("##-读取角色维度开始");
			while(true)
			{
				String dimString = reader.readExcelCell(0, 1, tempCellNum);
				if(dimString != null && !dimString.trim().equals(""))
				{
					dimList.add(dimString.trim());
					System.out.println("####-第"+ (tempCellNum+1) + "个维度为：" + dimString.trim());
				}
				else
				{
					break;
				}
				tempCellNum++;
			}
			System.out.println("##-读取角色维度结束");
			System.out.println("##################");
			
			//读取并插入角色细分和人员数据
			int tempReadLine = 2;
			int maxLine = reader.getRowNums(0);
			while(true)
			{
				System.out.println("##" + (tempReadLine+1) + "-开始处理角色细分记录");
				//获取角色细分数据
				StringBuilder childValueStr = new StringBuilder();
				if(maxLine <= tempReadLine)
				{
					break;
				}
				int matchCount = 0;
				boolean isfinish = false;
				StringBuilder dimValueStr = new StringBuilder();
				for(int i = 0; i < tempCellNum; i++)
				{
					String dimItem = reader.readExcelCell(0, tempReadLine, i);
					if(dimItem.trim().equals(""))
					{
						isfinish = true;
						break;
					}
					if(!dimItem.toUpperCase().equals("ALL"))
					{
						childValueStr.append("[" + dimItem + "]");
						dimValueStr.append("%#" + dimList.get(i) + "=" + dimItem);
						matchCount++;
					}
				}
				if(!isfinish)
				{
					childValueStr.append("[" + roleName + "]");
					System.out.println("##" + (tempReadLine+1) + "-角色细分为：" + childValueStr.toString());
	
					//存储角色细分信息
					ChildRole childRole = new ChildRole();
					childRole.setRoleCode(roleCode);
					childRole.setChildRoleName(childValueStr.toString());
					childRole.setMatchCount(new Long(String.valueOf(matchCount)));
					if(dimValueStr.length() > 0)
					{
						childRole.setDimensionValue(dimValueStr.substring(2));
					}
					else
					{
						childRole.setDimensionValue(dimValueStr.toString());
					}
//					if(!baseVersion.equals("")) childRole.setBaseVersion(baseVersion);
					List<ChildRole> hasChildRole = roleService.getChildRoleByHql("from ChildRole c where c.childRoleName= '" + childValueStr.toString() + "' and c.roleCode='" + roleCode + "'");
					if(hasChildRole == null || hasChildRole.size() == 0)
					{
						roleService.saveChildRole(childRole);
						System.out.println("##" + (tempReadLine+1) + "-存储角色细分完成：" + childValueStr.toString());
					}
					else
					{
						childRole = hasChildRole.get(0);
						System.out.println("##" + (tempReadLine+1) + "-获取原有角色细分：" + childValueStr.toString());
						List<RoleUser> oldRoleUserList = roleService.getRoleUserByCroleID(childRole.getChildRoleId());
						if(oldRoleUserList != null && oldRoleUserList.size() > 0)
						{
							for(RoleUser oldRoleUser : oldRoleUserList)
							{
								System.out.println("##" + (tempReadLine+1) + "-删除原有角色细分人员：" + oldRoleUser.getFullName());
								roleService.deleteRoleUserById(oldRoleUser.getRoleUserId());
							}
						}
					}
					
					//获取和操作人员列表
					String[] users = null;
					String userStr = reader.readExcelCell(0, tempReadLine, tempCellNum);
					if(userStr != null && !userStr.equals(""))
					{
						users = userStr.split(";");
					}
					System.out.println("##" + (tempReadLine+1) + "-角色细分人员为：" + userStr);
					
					if(users != null)
					{
						for(String user : users)
						{
							RoleUser roleUser = new RoleUser();
							roleUser.setChildRoleId(childRole.getChildRoleId());
							roleUser.setRoleCode(roleCode);
							roleUser.setLoginName(user);
							UserInfo userInfo = userManagerService.getUserByLoginName(user, false);
							if(userInfo != null)
							{
								roleUser.setFullName(userInfo.getFullname());
								roleService.saveOrUpdate(roleUser);
							}
						}
						System.out.println("##" + (tempReadLine+1) + "-存储角色细分人员：" + userStr);
					}
					tempReadLine++;
					System.out.println("##################");
				}
				else
				{
					break;
				}
			}
			System.out.println("##-插入角色细分以及人员信息结束");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String download()
	{
		System.out.println("##################");
		System.out.println("开始导出角色细分");
		System.out.println("##################");
		
		
		
		return SUCCESS;
	}
	
	public String getExcelFileName()
	{
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}
	public String getExcelContentType()
	{
		return excelContentType;
	}
	public void setExcelContentType(String excelContentType)
	{
		this.excelContentType = excelContentType;
	}
	public File getExcel()
	{
		return excel;
	}
	public void setExcel(File excel)
	{
		this.excel = excel;
	}
	public UserManagerService getUserManagerService()
	{
		return userManagerService;
	}
	public void setUserManagerService(UserManagerService userManagerService)
	{
		this.userManagerService = userManagerService;
	}
	public IRoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
}
