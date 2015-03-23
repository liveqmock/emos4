package com.ultrapower.eoms.common.core.component.tree.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.ultrapower.eoms.common.core.component.tree.manager.OrganizaTreeV2Impl;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class OrganizaTreeV2Action extends BaseAction {
	private String rearchData;
	
	public void getOrganizaData() {
		String treeData = "";
		String sltType = StringUtils.checkNullString(this.getRequest().getParameter("sltType"));
		String sltData = StringUtils.checkNullString(this.getRequest().getParameter("selectId"));
		OrganizaTreeV2Impl organizaTreeV2Impl = new OrganizaTreeV2Impl();
		if("".equals(StringUtils.checkNullString(rearchData))) {
			treeData = organizaTreeV2Impl.getTreeData(id, sltType, sltData);
		} else {
			String findType = StringUtils.checkNullString(this.getRequest().getParameter("findType"));
			if("0".equals(findType)) {
				treeData = organizaTreeV2Impl.getRearchData(sltType, sltData, rearchData);
			} else {
				treeData = organizaTreeV2Impl.getRearchDataAdvanced(sltType, sltData, rearchData);
			}
		}
		this.renderXML(treeData);
	}
	
	public void rearchForList() throws IOException {
		String sltData = StringUtils.checkNullString(this.getRequest().getParameter("selectId"));
		OrganizaTreeV2Impl organizaTreeV2Impl = new OrganizaTreeV2Impl();
		String resultData = organizaTreeV2Impl.getRearchForList(sltData, rearchData);
		PrintWriter out = this.getResponse().getWriter();
		out.print(String.valueOf(resultData));
	}
	
	public void getUserTreeId() throws IOException {
		String sltData = StringUtils.checkNullString(this.getRequest().getParameter("sltData"));
		String idType = StringUtils.checkNullString(this.getRequest().getParameter("idType"));
		PrintWriter out = this.getResponse().getWriter();
		out.print(getFullSelectData(sltData, idType));
	}
	
	/**
	 * 获取所选数据信息
	 * @param sltData 所选数据id
	 * @param idType id类别 对用户id有效 0.id-loginname 1.loginname 2.id
	 * @return
	 */
	private String getFullSelectData(String sltData, String idType) {
		String newIds = "";
		if(!"".equals(sltData)) {
			String[] subId = sltData.split(";");
			String[] subData;
			HashMap<String, String> map = new HashMap<String, String>();
			for(int i=0 ; i<subId.length ; i++) {
				subData = subId[i].split(":");
				if(subData.length == 2) {
					map.put(subData[0], subData[1]);
				}
			}
			OrganizaTreeV2Impl organizaTreeV2Impl = new OrganizaTreeV2Impl();
			if(map.get("D") != null) {
				String depIds = map.get("D");
				newIds += ";D:" + organizaTreeV2Impl.getDepInfos(depIds);
			}
			if(map.get("U") != null) {
				String userIds = map.get("U");
				newIds += ";U:" + organizaTreeV2Impl.getUserInfos(userIds, idType);
			}
			if(!"".equals(newIds)) {
				newIds = newIds.substring(1);
			}
		}
		return newIds;
	}

	public void setRearchData(String rearchData) {
		try {
			this.rearchData = java.net.URLDecoder.decode(rearchData,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
