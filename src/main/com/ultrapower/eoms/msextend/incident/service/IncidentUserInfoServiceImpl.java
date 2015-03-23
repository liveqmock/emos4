package com.ultrapower.eoms.msextend.incident.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.runtime.manage.AbsClientCallAdapter;
import com.ultrapower.eoms.ultrabpp.runtime.model.ClientCallDataModel;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.workflow.util.PreviewUtils;

public class IncidentUserInfoServiceImpl extends AbsClientCallAdapter implements IncidentUserInfoService {

	private DicManagerService dicManagerService;
	
	public ClientCallDataModel<?> call(String methodName, Map<String, String> parameters) 
	{
		if (methodName.equals("getUserinfoByName")) 
		{
			List<String> datas = getUserinfoByName(parameters.get("loginname"));
			ClientCallDataModel<String> model = new ClientCallDataModel<String>();
			model.setData(datas);
			return model;
		}
		else
		{
			return null;
		}

	}

	/**
	 * 根据用户名获取用户信息
	 */
	public ArrayList<String> getUserinfoByName(String userName) 
	{
		String isVipName = "";
		UserInfo userInfo = PreviewUtils.getUserInfo(userName);
		ArrayList<String> userInfoList = new ArrayList<String>();
		userInfoList.add(userInfo.getFullname()); //全名
		userInfoList.add(userInfo.getLocationzone()); //片区
		userInfoList.add(userInfo.getDepname());//部门
		userInfoList.add(userInfo.getJobnum());//工号
		userInfoList.add(this.getUserPos("userposition", userInfo.getPosition()));//职位
		userInfoList.add(userInfo.getMobile());//手机
		userInfoList.add(userInfo.getPhone());//电话
		userInfoList.add(userInfo.getStation());//工位
		if(!"".equals(userInfo.getIsVip())&&userInfo.getIsVip()!=null){
		if(userInfo.getIsVip().intValue()==1){
			isVipName="是";
		}
		if(userInfo.getIsVip().intValue()==0){
			isVipName="否";
		}
		}
		userInfoList.add(isVipName);//是否Vip用户
		userInfoList.add(userInfo.getOaNumber());//oa号
		
		return userInfoList;
	}

	/**
	 * 根据字典类型和字典分类值查找职位
	 */
	public String getUserPos(String dtcode, String divalue) 
	{
		String userPosition = dicManagerService.getTextByValue(dtcode, divalue);
		return userPosition;
	}

	
	public DicManagerService getDicManagerService() {
		return dicManagerService;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}
	
	
	
	
}