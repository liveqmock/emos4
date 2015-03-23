package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class OrganizaTreeV2Impl extends TreeManager {
	
	/**
	 * 获取树信息
	 * @param treeid 树节点id
	 * @param sltType 树类型 0.部门 1.人 2.人和部门
	 * @param sltData 所选数据
	 * @return
	 */
	public String getTreeData(String treeid, String sltType, String sltData) {
		// 非空处理
		treeid = StringUtils.checkNullString(treeid);
		sltType = StringUtils.checkNullString(sltType);
		sltData = StringUtils.checkNullString(sltData);
		// 获取treeid节点的下级组织机构信息
		List<DtreeBean> dtreeBeanList = this.getSubTreeData(treeid, sltType, sltData);
		return this.dtreeThansStr(dtreeBeanList, treeid);
	}
	
	/**
	 * 获取下级树信息
	 * @param treeid 树节点id
	 * @param sltType 树类型 0.部门 1.人 2.人和部门
	 * @param sltData 所选数据
	 * @return
	 */
	private List<DtreeBean> getSubTreeData(String treeid, String sltType, String sltData) {
		List<DtreeBean> dtreeBeanList = null;
		if("".equals(treeid)) {
			return dtreeBeanList;
		}
		if(!"0".equals(treeid)) {
			// treeid格式为：D_depid 所以将id截取出来
			treeid = treeid.substring(2);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		// 部门sql
		// 部门表 c1:部门id c630000018:部门名称 c630000022:排序值 c630000020:父id c630000025:状态(0:启用)
		sql.append("select d.c1 as treeid, d.c630000018 as treetext, 'D' as treetype, d.c630000022 treeorder");
		sql.append("  from {UltraProcess:SysGroup} d");
		sql.append(" where d.c630000020 = '" + treeid + "'");
		sql.append("   and d.c630000025 = '0'");
		if(!"0".equals(treeid) && !"0".equals(sltType)) {
			// 用户sql
			// 用户表 c1:用户id c630000003:用户全名 c630000017:排序值 c630000012:状态(0:启用)
			// 关系表 c620000027:组id c620000028:用户id
			sql.append(" union all ");
			sql.append("select distinct u.c1 || '-' || u.c630000001 as treeid, u.c630000003 as treetext, 'U' as treetype, u.c630000017 treeorder");
			sql.append("  from {UltraProcess:SysUser} u, {UltraProcess:SysGroupUser} ud");
			sql.append(" where u.c1 = ud.c620000028");
			sql.append("   and u.c630000012 = '0'");
			sql.append("   and ud.c620000027 = '" + treeid + "'");
		}
		sql.append(")");
		sql.append(" order by treetype, treeorder");
		DataTable table = this.getDataBySql(sql.toString()); // 执行sql返回table列表
		dtreeBeanList = this.tableToTree(table, sltType, sltData); // 将table列表信息转换成树对象集
		return dtreeBeanList;
	}
	
	/**
	 * 将dtreeBeanList转换成xml字符串
	 * 此方法做特殊处理
	 * @param dtreeBeanList
	 * @param treeid
	 * @return
	 */
	private String dtreeThansStr(List<DtreeBean> dtreeBeanList, String treeid)
	{
		String xmlStr = "";
		if(dtreeBeanList != null && dtreeBeanList.size() > 0)
			xmlStr = this.createDhtmlxtreeXml(dtreeBeanList, treeid);
		else
			xmlStr = this.createDhtmlxtreeXml(null);
		return xmlStr;
	}
	
	/**
	 * 获取查询的数据集
	 * @param sltType 树类型 0.部门 1.人 2.人和部门
	 * @param sltData 所选数据
	 * @param rearchData 查询信息
	 * @return
	 */
	public String getRearchData(String sltType, String sltData, String rearchData) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (");
		// 部门sql
		// 部门表 c1:部门id c630000018:部门名称 c630000022:排序值 c630000020:父id c630000025:状态(0:启用)
		sql.append("select d.c1 as treeid, d.c630000018 as treetext, 'D' as treetype, d.c630000022 treeorder");
		sql.append("  from {UltraProcess:SysGroup} d");
		sql.append(" where d.c630000025 = '0'");
		sql.append("   and d.c630000018 like '%" + rearchData + "%'");
		if(!"0".equals(sltType)) {
			// 用户sql
			// 用户表 c1:用户id c630000003:用户全名 c630000017:排序值 c630000012:状态(0:启用)
			sql.append(" union all ");
			sql.append("select distinct u.c1 || '-' || u.c630000001 as treeid, u.c630000003 as treetext, 'U' as treetype, u.c630000017 treeorder");
			sql.append("  from {UltraProcess:SysUser} u");
			sql.append(" where u.c630000012 = '0'");
			sql.append("   and (u.c630000001 like '%" + rearchData + "%'");
			sql.append("     or u.c630000003 like '%" + rearchData + "%')");
		}
		sql.append(")");
		sql.append(" order by treetype, treeorder");
		DataTable table = this.getDataBySql(sql.toString());
		return this.apposeDhtmlXtreeXml(this.tableToTree(table, sltType, sltData));
	}
	
	/**
	 * 获取查询的结果，列表格式
	 * @param sltData
	 * @param rearchData
	 * @return
	 */
	public String getRearchForList(String sltData, String rearchData) {
		StringBuffer resultData = new StringBuffer();
		DataTable table = this.getRearchTableAdvanced(rearchData);
		String selectUser = ""; // 所选的用户
		String selectDep = ""; // 所选的部门
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
			String[] selectData = sltData.split(";");
			selectUser = selectData[0];
			if(selectData.length > 1) {
				selectDep = selectData[1];
			}
		}
		DataRow row;
		String treeid;
		String treetext;
		String treetype;
		String treeinfo;
		String check;
		for(int i=0 ; i<tableLen ; i++) {
			row = table.getDataRow(i);
			treeid = row.getString("treeid");
			treetext = row.getString("treetext");
			treetype = row.getString("treetype");
			treeinfo = row.getString("treetype");
			check = "false";
			if("U".equals(treetype)) {
				if(selectUser.indexOf(treeid) >= 0) {
					check = "true";
				}
			} else if ("D".equals(treetype)) {
				if(selectDep.indexOf(treeid) >= 0) {
					check = "true";
				}
			}
			treeid = treetype + "_" + treeid + ":" + check;
			resultData.append(";");
			resultData.append(treeid);
			resultData.append(",");
			resultData.append(treetext);
			resultData.append(",");
			resultData.append(treeinfo);
		}
		return resultData.length()>0 ? resultData.toString().substring(1) : "";
	}
	
	/**
	 * 获取查询的数据集（高级查询）
	 * @param sltType
	 * @param sltData
	 * @param rearchData
	 * @return
	 */
	public String getRearchDataAdvanced(String sltType, String sltData, String rearchData) {
		DataTable table = this.getRearchTableAdvanced(rearchData);
		return this.apposeDhtmlXtreeXml(this.tableToTree(table, sltType, sltData));
	}
	
	/**
	 * 根据查询条件获取查询结果集
	 * @param rearchData
	 * @return
	 */
	private DataTable getRearchTableAdvanced(String rearchData) {
		DataTable table = null;
		if("".equals(StringUtils.checkNullString(rearchData))) {
			return table;
		}
		StringBuffer sql = new StringBuffer();
		HashMap<String, String> rearchMap = this.getRearchInfoMap(rearchData);
		String rearchType = StringUtils.checkNullString(rearchMap.get("type"));
		String param;
		if("user".equals(rearchType)) {
			sql.append("select u.c1 || '-' || u.c630000001 as treeid, u.c630000003 as treetext, 'U' as treetype, u.c630000017 treeorder");
			sql.append("  from {UltraProcess:SysUser} u");
			sql.append(" where u.c630000012 = '0'");
			param = rearchMap.get("username");
			sql.append(this.combCondition(param, "u.c630000003", "like"));
			param = rearchMap.get("loginname");
			sql.append(this.combCondition(param, "u.c630000001", "like"));
			param = rearchMap.get("mobile");
			sql.append(this.combCondition(param, "u.c630000008", "like"));
			param = rearchMap.get("position");
			sql.append(this.combCondition(param, "u.c630000005", "="));
			sql.append(" order by u.c630000017");
		} else if("group".equals(rearchType)) {
			sql.append("select d.c1 as treeid, d.c630000018 as treetext, 'D' as treetype, d.c630000022 treeorder");
			sql.append("  from {UltraProcess:SysGroup} d");
			sql.append(" where d.c630000025 = '0'");
			sql.append("");
			sql.append(" order by d.c630000022");
		}
		table = this.getDataBySql(sql.toString());
		return table;
	}

	/**
	 * 合并查询条件
	 * @param param
	 * @param field
	 * @param type
	 * @return
	 */
	private String combCondition(String param, String field, String type) {
		StringBuffer condition = new StringBuffer();
		if("".equals(StringUtils.checkNullString(param))) {
			return condition.toString();
		}
		type = StringUtils.checkNullString(type);
		String[] paramArray = param.split(",");
		if(paramArray.length == 1) {
			condition.append(" and ");
			if("=".equals(type)) {
				condition.append(field);
				condition.append("='");
				condition.append(param);
				condition.append("'");
			} else {
				condition.append(field);
				condition.append(" like '%");
				condition.append(param);
				condition.append("%'");
			}
		} else {
			condition.append(" and (");
			for(int i=0 ; i<paramArray.length ; i++) {
				if(i > 0) {
					condition.append(" or ");
				}
				if("=".equals(type)) {
					condition.append(field);
					condition.append("='");
					condition.append(paramArray[i]);
					condition.append("'");
				} else {
					condition.append(field);
					condition.append(" like '%");
					condition.append(paramArray[i]);
					condition.append("%'");
				}
			}
			condition.append(")");
		}
		return condition.toString();
	}
	
	private HashMap<String, String> getRearchInfoMap(String rearchData) {
		HashMap<String, String> rearchMap = new HashMap<String, String> ();
		if("".equals(StringUtils.checkNullString(rearchData))) {
			return rearchMap;
		}
		String[] rearchArray = rearchData.split(";");
		String[] rearchSub;
		for(int i=0 ; i<rearchArray.length ; i++) {
			if("".equals(rearchArray[i])) {
				continue;
			}
			rearchSub = rearchArray[i].split("=");
			if(rearchSub.length < 2) {
				continue;
			}
			rearchMap.put(rearchSub[0], rearchSub[1]);
		}
		return rearchMap;
	}
	
	/**
	 * 将table列表信息转换成树对象集
	 * @param table 数据列表
	 * @param sltType 树类型 0.部门 1.人 2.人和部门
	 * @param sltData 所选数据
	 * @return
	 */
	private List<DtreeBean> tableToTree(DataTable table, String sltType, String sltData) {
		List<DtreeBean> dtreeBeanList = null;
		String selectUser = ""; // 所选的用户
		String selectDep = ""; // 所选的部门
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
			dtreeBeanList = new ArrayList<DtreeBean> ();
			String[] selectData = sltData.split(";");
			selectUser = selectData[0];
			if(selectData.length > 1) {
				selectDep = selectData[1];
			}
		}
		String treeid; // 节点id
		String treetext; // 节点名称
		String treetype; // 节点类型 D：部门 U：用户
		DataRow row;
		DtreeBean dtreeBean;
		for (int i=0 ; i<tableLen ; i++) {
			row = table.getDataRow(i);
			treeid = row.getString("treeid");
			treetext = row.getString("treetext");
			treetype = row.getString("treetype");
			dtreeBean = new DtreeBean();
			if("D".equals(treetype)) { // 如果treetype=D则为部门
				if("1".equals(sltType)) { // 如果sltType=1为只选人，所以设置部门前面没有复选框
					dtreeBean.setNocheckbox("1");
				} else if(selectDep.indexOf(treeid) >= 0) { // 如果sltType!=1并且存在已选部门，则初始化复选框自动被选
					dtreeBean.setChecked("1");
				}
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			} else { // 如果treetype=U或不为D则为用户
				if(selectUser.indexOf(treeid) >= 0) {
					dtreeBean.setChecked("1");
				}
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
				dtreeBean.setChild(""); // 用户没有下级，所以不需要展示加号
			}
			treeid = treetype + "_" + treeid;
			dtreeBean.setId(treeid);
			dtreeBean.setText(treetext);
			HashMap map = new HashMap();
			map.put("id", treeid);
			map.put("text", treetext);
			map.put("type", treetype);
			dtreeBean.setUserdata(map);
			dtreeBeanList.add(dtreeBean);
		}
		return dtreeBeanList;
	}
	
	public String getUserInfos(String userIds, String idType) {
		idType = StringUtils.checkNullString(idType);
		userIds = StringUtils.checkNullString(userIds).replaceAll(",", "','");
		StringBuffer sql = new StringBuffer();
		sql.append("select u.c1 || '-' || u.c630000001 as id, u.c630000001 as login, u.c630000003 as name");
		sql.append("  from {UltraProcess:SysUser} u");
		sql.append(" where 1=1");
		if("0".equals(idType)) {
			sql.append(" and u.c1 || '-' || u.c630000001 in ('" + userIds + "')");
		} else if("1".equals(idType)) {
			sql.append(" and u.c630000001 in ('" + userIds + "')");
		} else {
			sql.append(" and u.c1 in ('" + userIds + "')");
		}
		DataTable table = this.getDataBySql(sql.toString());
		return this.tableToInfos(table, idType);
	}
	
	public String getDepInfos(String depIds) {
		depIds = StringUtils.checkNullString(depIds).replaceAll(",", "','");
		StringBuffer sql = new StringBuffer();
		sql.append("select d.c1 as id, d.c630000018 as name");
		sql.append("  from {UltraProcess:SysGroup} d");
		sql.append(" where d.c1 in ('" + depIds + "')");
		DataTable table = this.getDataBySql(sql.toString());
		return this.tableToInfos(table, "");
	}
	
	private DataTable getDataBySql(String sql) {
		DataTable table = null;
		QueryAdapter queryAdapter = new QueryAdapter();
		try {
			table = queryAdapter.executeQuery(SqlReplace.stringReplaceAllVar(sql.toString(), null), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return table;
	}
	
	/**
	 * 列表数据转换成信息
	 * 信息格式：id1,id2,id3,...;name1,name2,name3,...
	 * @param table 数据集
	 * @param idType id类别 对用户id有效 0.id-loginname 1.loginname 2.id
	 * @return
	 */
	private String tableToInfos(DataTable table, String idType) {
		String infos = "";
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
		}
		String ids = "";
		String names = "";
		DataRow row;
		for(int i=0 ; i<tableLen ; i++) {
			row = table.getDataRow(i);
			String id = row.getString("id");
			String name = row.getString("name");
			String login = row.getString("login");
			if("0".equals(idType)) {
				ids += "," + id + "-" + login;
			} else if("1".equals(idType)) {
				ids += "," + login;
			} else {
				ids += "," + id;
			}
			names += "," + name;
		}
		if(!"".equals(ids)) {
			infos = ids.substring(1) + ":" + names.substring(1);
		}
		return infos;
	}
}
