package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.CustomOrganize;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;

/**
 * 人员部门树
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 4, 2010 10:00:03 AM
 */
public class UserDepTreeImpl extends TreeManager{

	private IDao<CustomOrganize>  customOrganizeDao;
	private QueryAdapter queryAdapter = new QueryAdapter();
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid 父节点
	 * @return 返回查询到的树xml节点数据
	 */
	public String getChildXml(String parentid){
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = getChild(parentid);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	public String getChildDepOfWhere(String whereValue){
		List<DtreeBean> dtreeList = getDepListOfWhere(whereValue);
		return this.apposeDhtmlXtreeXml(dtreeList);
	}
	
	/**
	 * 根据传入的条件进行人员和组的查询
	 * @param whereValue 人或者组的查询值
	 * @return
	 */
	public String getDepUserListXml(String topid,String whereValue,String isSelectType){
		List<DtreeBean> dtreeBeanList = getDepOrUserList(topid,whereValue,isSelectType);
		return this.apposeDhtmlXtreeXml(dtreeBeanList);
	}
	
	/**
	 * 自定义树的搜索
	 * @param whereValue
	 * @param isSelectType
	 * @return
	 */
	public String getDepUserListXmlOfCust(String whereValue,String formCustSendTree){
		List<DtreeBean> dtreeBeanList = getDepOrUserListOfcust(whereValue,formCustSendTree);
		return this.apposeDhtmlXtreeXml(dtreeBeanList);
	}
	/**
	 * 派发树配置,返回派发树配置树形结构
	 */
	public String getDepUserListXmlOfAssignTree(String whereValue,String formCustSendTree){
		List<DtreeBean> dtreeBeanList = getDepOrUserListOfAssignTree(whereValue,formCustSendTree);
		return this.apposeDhtmlXtreeXml(dtreeBeanList);
	}
	
	/**
	 * 获取部门、用户的集合树
	 * @param parentid
	 * @return
	 */
	public String getDepUserXml(String parentid,String isSelectType){
		if(parentid=="")
			return "";
		List<DtreeBean> menuDtreeList = getDepUserData(parentid,false,isSelectType);
		return this.createDhtmlxtreeXml(menuDtreeList, parentid);
	}
	
	public String getDepUserXmlNew(String parentid,boolean first,String isSelectType){
		if(parentid=="")
			return "";
		List<DtreeBean> menuDtreeList = getDepUserData(parentid,first,isSelectType);
		if(first) {
			parentid = "0";
		}
		return this.createDhtmlxtreeXml(menuDtreeList, parentid);
	}
	
	public String getDepUserXmlOftargetDataArr(String parentid,boolean first,String isSelectType,String targetDataArr){
		if(parentid=="")
			return "";
		//List<DtreeBean> menuDtreeList = getDepUserData(parentid,isSelectType);
		List<DtreeBean> menuDtreeList = getDepUserDataOftargetDataArr(parentid,first,isSelectType,targetDataArr);
		return this.createDhtmlxtreeXml(menuDtreeList, parentid);
	}
	
	/**
	 * 获取匹配选择的部门、用户的集合树
	 * @param parentid
	 * @param isSelectType
	 * @param formCustSendTree
	 * @return
	 */
	public String getDepUserXml(String parentid,String isSelectType,String formCustSendTree){
		if(parentid=="")
			return "";
		List<DtreeBean> menuDtreeList = getDepUserData(parentid,isSelectType,formCustSendTree);
		return this.createDhtmlxtreeXml(menuDtreeList, parentid);
	}
	
	public String getCustomDepUserXml(String parentid,String isSelectType,String formCustSendTree){
		if(parentid=="")
			return "";
		List<DtreeBean> menuDtreeList = getCustomDepUserData(parentid,isSelectType,formCustSendTree);
		return this.createDhtmlxtreeXml(menuDtreeList, parentid);
	}
	
	public String getAssignTreeDepUserXml(String parentid,String isSelectType,String formCustSendTree){
		if(parentid=="")
			return "";
		List<DtreeBean> menuDtreeList = getAssignTreeDepUserData(parentid,isSelectType,formCustSendTree);
		return this.createDhtmlxtreeXml(menuDtreeList, parentid);
	}
	/**
	 * 查询展示一定级别的树结构
	 * @param parentid 父节点id
	 * @param level 查询级别
	 * @return 查询获取到的xml数据
	 */
	public String getChildXml(String parentid,int level){
		if(parentid=="")
			return "";
		List<MenuDtree> menuDtreeList = getChild(parentid,level);
		return this.createtreeRecursion(parentid, menuDtreeList, "parentid", (level-1));
	}
	
	/**
	 * 按照级别查询部分树
	 * @param parentid 父节点
	 * @param level 查询级别
	 * @return 返回查询到的节点数据
	 */
	private List<MenuDtree> getChild(String parentid,int level){
		if(parentid=="")
			return null;
		List<MenuDtree> dtreeList = new ArrayList<MenuDtree>();
		StringBuffer sql = new StringBuffer();		
		sql.append("select level, pid, depname, parentid");
		sql.append("  from bs_t_sm_dep t");
		sql.append(" where status = 1");
		sql.append(" and level <= "+level+"");
		sql.append(" start with parentid = '"+parentid+"'");
		sql.append(" connect by parentid = prior pid");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		MenuDtree menuDtree;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			menuDtree = new MenuDtree();
			menuDtree.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			menuDtree.setLevel(Integer.parseInt(StringUtils.checkNullString(dataRow.getString("level"))));
			menuDtree.setText(StringUtils.checkNullString(dataRow.getString("depname")));
			menuDtree.setParentid(StringUtils.checkNullString(dataRow.getString("parentid")));
			menuDtree.setIm0("groupImg.png");
			menuDtree.setIm1("groupImg.png");
			menuDtree.setIm2("groupImg.png");
			dtreeList.add(menuDtree);
		}
		return dtreeList;
	}
	
	/**
	 * 
	 * @param parentid
	 * @param isSelectType
	 * @return
	 */
	private List<DtreeBean> getDepUserData(String parentid,boolean first,String isSelectType){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid,pid treeid,depname,depfullname,parentid,'D' orgtype");
		sql.append(" from bs_t_sm_dep t");
		sql.append(" where status = 1");
		if(first) {
			sql.append(" and pid = '"+parentid+"'");
		} else {
			sql.append(" and parentid = '"+parentid+"'");			
		}
		if(!isSelectType.equals("0") && !first){//如果查询部门,则不显示人
			sql.append(" union all");
			sql.append(" select sysuser.pid pid,sysuser.loginname treeid, sysuser.fullname depname,sysuser.loginname depfullname,dep.pid parentid,'U' orgtype");
			sql.append(" from bs_t_sm_userdep userdep, bs_t_sm_user sysuser, bs_t_sm_dep dep");
			sql.append(" where userdep.userid = sysuser.pid");
			sql.append(" and userdep.depid = dep.pid");
			sql.append(" and dep.pid = '"+parentid+"'");
		}
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("treeid"));//树节点id
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));//业务ID
			String text = StringUtils.checkNullString(dataRow.getString("depname"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 D：部门 U：人员
			String depfullname = StringUtils.checkNullString(dataRow.getString("depfullname"));
			String parent_id = StringUtils.checkNullString(dataRow.getString("parentid"));
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			if(orgtype.equals("U"))
				dtreeBean.setChild("");
			HashMap map = new HashMap();
			map.put("id", pid);
			map.put("text", text);
			map.put("type", orgtype);
			map.put("name", depfullname);//0:部门  1:人员  2:默认
			map.put("parentid", first ? "0" : parent_id);
			dtreeBean.setUserdata(map);
			String nocheckbox = "";
			if(isSelectType.equals("0"))
			{
				if(orgtype.equals("U")){
					dtreeBean.setNocheckbox(orgtype);//如果不选 则赋值
					dtreeBean.setDisabled("1");
				}
			}
			if(isSelectType.equals("1"))//只能选择人
			{
				if(orgtype.equals("D")){
					dtreeBean.setNocheckbox(orgtype);
				}
			}
			if(orgtype.equals("D")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("U")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	private List<DtreeBean> getAssignTreeDepUserData(String parentid,String isSelectType,String formCustSendTree){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select userdepinfo.pid,treeid, userdepinfo.orgname text, userdepinfo.orgtype ");
		sql.append(" from bs_t_bpp_assingtreeorganize atreeorg,");
		sql.append(" (select pid,pid treeid, depname orgname, '1' orgtype");
		sql.append(" from bs_t_sm_dep t");
		sql.append(" where status = 1 and parentid = '"+parentid+"' ");
		sql.append(" union all");
		sql.append(" select sysuser.pid pid,userdep.pid treeid, sysuser.fullname orgname, '2' orgtype");
		sql.append(" from bs_t_sm_userdep userdep,bs_t_sm_user sysuser, bs_t_sm_dep dep");
		sql.append(" where userdep.userid = sysuser.pid and userdep.depid = dep.pid and dep.pid = '"+parentid+"' ) userdepinfo");
		sql.append(" where atreeorg.organizeid = userdepinfo.pid");
		sql.append("  and atreeorg.organizetype = userdepinfo.orgtype and atreeorg.configid = '"+formCustSendTree+"' ");
		//fanying bg 2013-4-24 解决自定义派发树同一个人员出现两次的问题
		sql.append("  and  atreeorg.parentorgid='"+parentid+"' ");
		sql.append("  order by ORGANIZETYPE ");
		//fanying ed
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("treeid"));//树节点id
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));//业务ID	
			
			String text = StringUtils.checkNullString(dataRow.getString("text"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 1：部门 2：人员
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			
			if(orgtype.equals("2"))
				dtreeBean.setChild("");
				
			HashMap map = new HashMap();
			map.put("id", pid);
			map.put("text", text);
			map.put("type", orgtype);
			dtreeBean.setUserdata(map);
			String nocheckbox = "";
			if(isSelectType.equals("0"))
			{
				if(orgtype.equals("2")){
					dtreeBean.setNocheckbox(orgtype);//如果不选 则赋值
					dtreeBean.setDisabled("1");
				}
			}
			if(isSelectType.equals("1"))//只能选择人
			{
				if(orgtype.equals("1")){
					dtreeBean.setNocheckbox(orgtype);
				}
			}
			if(orgtype.equals("1")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("U")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
		
	/**
	 * 自定义树的展示
	 * @param parentid
	 * @param isSelectType
	 * @param formCustSendTree
	 * @return
	 */
	private List<DtreeBean> getCustomDepUserData(String parentid,String isSelectType,String formCustSendTree){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select userdepinfo.pid,treeid, userdepinfo.orgname text, userdepinfo.orgtype ");
		sql.append(" from bs_t_sm_customorganize custorg,");
		sql.append(" (select pid,pid treeid, depname orgname, '1' orgtype");
		sql.append(" from bs_t_sm_dep t");
		sql.append(" where status = 1 and parentid = '"+parentid+"' ");
		sql.append(" union all");
		sql.append(" select sysuser.pid pid,userdep.pid treeid, sysuser.fullname orgname, '2' orgtype");
		sql.append(" from bs_t_sm_userdep userdep,bs_t_sm_user sysuser, bs_t_sm_dep dep");
		sql.append(" where userdep.userid = sysuser.pid and userdep.depid = dep.pid and dep.pid = '"+parentid+"' ) userdepinfo");
		sql.append(" where custorg.organizepid = userdepinfo.pid");
		sql.append("  and custorg.organizetype = userdepinfo.orgtype and custorg.custominfopid = '"+formCustSendTree+"' ");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("treeid"));//树节点id
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));//业务ID	
			
			String text = StringUtils.checkNullString(dataRow.getString("text"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 1：部门 2：人员
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			
			if(orgtype.equals("2"))
				dtreeBean.setChild("");
				
			HashMap map = new HashMap();
			map.put("id", pid);
			map.put("text", text);
			map.put("type", orgtype);
			dtreeBean.setUserdata(map);
			String nocheckbox = "";
			if(isSelectType.equals("0"))
			{
				if(orgtype.equals("2")){
					dtreeBean.setNocheckbox(orgtype);//如果不选 则赋值
					dtreeBean.setDisabled("1");
				}
			}
			if(isSelectType.equals("1"))//只能选择人
			{
				if(orgtype.equals("1")){
					dtreeBean.setNocheckbox(orgtype);
				}
			}
			if(orgtype.equals("1")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("U")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 根据已经定制的自定义树进行查询并默认选择已经定制的树节点
	 * @param parentid
	 * @param isSelectType
	 * @param formCustSendTree
	 * @return
	 */
	private List<DtreeBean> getDepUserData(String parentid,String isSelectType,String formCustSendTree){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid,depname,depfullname,parentid,'D' orgtype");
		sql.append(" from bs_t_sm_dep t");
		sql.append(" where status = 1");
		sql.append(" and parentid = '"+parentid+"'");
		if(!isSelectType.equals("0")){//如果查询部门,则不显示人
			sql.append(" union all");
			sql.append(" select sysuser.pid pid, sysuser.fullname depname,sysuser.loginname depfullname,dep.pid parentid,'U' orgtype");
			sql.append(" from bs_t_sm_userdep userdep, bs_t_sm_user sysuser, bs_t_sm_dep dep");
			sql.append(" where userdep.userid = sysuser.pid");
			sql.append(" and userdep.depid = dep.pid");
			sql.append(" and dep.pid = '"+parentid+"'");
		}
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("pid"));
			String text = StringUtils.checkNullString(dataRow.getString("depname"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 D：部门 U：人员
			String depfullname = StringUtils.checkNullString(dataRow.getString("depfullname"));
			String parent_pid = StringUtils.checkNullString(dataRow.getString("parentid"));
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			
			if(this.isSelect(formCustSendTree,id,orgtype))
				dtreeBean.setChecked("1");
			
			if(orgtype.equals("U"))
				dtreeBean.setChild("");
				
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("text", text);
			map.put("type", orgtype);
			map.put("name", depfullname);//0:部门  1:人员  2:默认
			map.put("parentid", parent_pid);
			dtreeBean.setUserdata(map);
			String nocheckbox = "";
			if(isSelectType.equals("0"))
			{
				if(orgtype.equals("U")){
					dtreeBean.setNocheckbox(orgtype);//如果不选 则赋值
					dtreeBean.setDisabled("1");
				}
			}
			if(isSelectType.equals("1"))//只能选择人
			{
				if(orgtype.equals("D")){
					dtreeBean.setNocheckbox(orgtype);
				}
			}
			if(orgtype.equals("D")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("U")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 根据条件进行部门树的查询
	 * @param whereValue
	 * @return
	 */
	public List<DtreeBean> getDepListOfWhere(String whereValue){
		if(whereValue=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select sysdep.pid, sysdep.depname text,deptype");
		sql.append(" from bs_t_sm_dep sysdep");
		sql.append(" where sysdep.depname like '%"+whereValue+"%'");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("text")));
			dtreeBean.setIm0("groupImg.png");
			dtreeBean.setIm1("groupImg.png");
			dtreeBean.setIm2("groupImg.png");
			HashMap map = new HashMap();
			map.put("text", StringUtils.checkNullString(dataRow.getString("text")));
			map.put("depType", StringUtils.checkNullString(dataRow.getString("deptype")));
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	public String getViewDataStr(String targetDataArr){
		StringBuffer str = new StringBuffer();
		String[] arrStr = null;
		if(targetDataArr!=null && targetDataArr!="")
			arrStr = targetDataArr.split(";");
		if(arrStr!=null && arrStr.length>0)
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select t.pid,t.fullname from bs_t_sm_user t where");
			for(int i=0;i<arrStr.length;i++){
				sql.append(" t.pid ='"+arrStr[i]+"' or");
			}
			
			DataTable datatable = queryAdapter.executeQuery(sql.toString().substring(0, sql.toString().length()-2), null, 0, 0, 2);
			int datatableLen = 0;
			if(datatable!=null)
				datatableLen = datatable.length();
			DataRow dataRow;
			DtreeBean dtreeBean;
			for(int row=0;row<datatableLen;row++){
				dataRow = datatable.getDataRow(row);
				String pid = StringUtils.checkNullString(dataRow.getString("pid"));
				String fullname = StringUtils.checkNullString(dataRow.getString("fullname"));
				str.append("<div id=" + pid+" idText="+fullname+"><b>" + fullname+"</b> [人]"+"；<img src=\"../style/blue/images/del_user.jpg\" onclick='delItem(\""+pid+"\")' style=\"margin-left:2px; margin-bottom:-1px;\" alt=\"删除\"></img></div>");
			}
		}
		return str.toString();
	}
	
	/**
	 * @param targetDataArr
	 * @param parentid
	 * @param isSelectType
	 * @return
	 */
	private List<DtreeBean> getDepUserDataOftargetDataArr(String parentid,boolean first,String isSelectType,String targetDataArr){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid,depname,depfullname,parentid,'D' orgtype");
		sql.append(" from bs_t_sm_dep t");
		sql.append(" where status = 1");
		if(first) {
			sql.append(" and pid = '"+parentid+"'");
		} else {
			sql.append(" and parentid = '"+parentid+"'");			
		}
		if(!isSelectType.equals("0") && !first){//如果查询部门,则不显示人
			sql.append(" union all");
			sql.append(" select sysuser.pid pid, sysuser.fullname depname,sysuser.loginname depfullname,dep.pid parentid,'U' orgtype");
			sql.append(" from bs_t_sm_userdep userdep, bs_t_sm_user sysuser, bs_t_sm_dep dep");
			sql.append(" where userdep.userid = sysuser.pid");
			sql.append(" and userdep.depid = dep.pid");
			sql.append(" and dep.pid = '"+parentid+"'");
		}
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("pid"));
			String text = StringUtils.checkNullString(dataRow.getString("depname"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 D：部门 U：人员
			String depfullname = StringUtils.checkNullString(dataRow.getString("depfullname"));
			String parent_id = StringUtils.checkNullString(dataRow.getString("parentid"));
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			if(orgtype.equals("U"))
				dtreeBean.setChild("");
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("text", text);
			map.put("type", orgtype);
			map.put("name", depfullname);//0:部门  1:人员  2:默认
			map.put("parentid", first ? "" : parent_id);
			dtreeBean.setUserdata(map);
			String nocheckbox = "";
			if(isSelectType.equals("0"))
			{
				if(orgtype.equals("U")){
					dtreeBean.setNocheckbox(orgtype);//如果不选 则赋值
					dtreeBean.setDisabled("1");
				}
			}
			if(isSelectType.equals("1"))//只能选择人
			{
				if(orgtype.equals("D")){
					dtreeBean.setNocheckbox(orgtype);
				}
				int ch = targetDataArr.indexOf(id);
				if(ch>=0)
					dtreeBean.setChecked("1");
				dtreeList.add(dtreeBean);
			}
			if(orgtype.equals("D")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("U")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
		}
		return dtreeList;
	}
	public List<DtreeBean> getDepOrUserListOfAssignTree(String whereValue,String formCustSendTree)
	{
		if(whereValue=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select selectDepUser.Pid pid,selectDepUser.Text text,selectDepUser.Orgtype orgtype");
		sql.append(" from bs_t_bpp_assingtreeorganize atree");
		sql.append(",(");
		sql.append(" select sysuser.pid, sysuser.fullname text, '2' orgtype from bs_t_sm_user sysuser where sysuser.fullname like '%"+whereValue+"%' or sysuser.loginname like '%"+whereValue+"%' " );
		sql.append(" union all");
		sql.append(" select sysdep.pid, sysdep.depname text, '1' orgtype");
		sql.append(" from bs_t_sm_dep sysdep");
		sql.append(" where sysdep.depname like '%"+whereValue+"%') selectDepUser");
		sql.append(" where atree.organizeid = selectDepUser.Pid and atree.configid = '"+formCustSendTree+"' ");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("pid"));
			String text = StringUtils.checkNullString(dataRow.getString("text"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 1：部门 2：人员
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			if(orgtype.equals("2"))
				dtreeBean.setChild("");
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("text", text);
			map.put("type", orgtype);
			dtreeBean.setUserdata(map);
			
			if(orgtype.equals("1")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("2")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
			
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	/**
	 * 自定义树的搜索
	 * @param whereValue
	 * @param isSelectType
	 * @return
	 */
	public List<DtreeBean> getDepOrUserListOfcust(String whereValue,String formCustSendTree){
		if(whereValue=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select selectDepUser.Pid pid,selectDepUser.Text text,selectDepUser.Orgtype orgtype");
		sql.append(" from bs_t_sm_customorganize cust,");
		sql.append(" (select sysuser.pid, sysuser.fullname text, '2' orgtype from bs_t_sm_user sysuser where sysuser.fullname like '%"+whereValue+"%' or sysuser.loginname like '%"+whereValue+"%' " );
		sql.append(" union all");
		sql.append(" select sysdep.pid, sysdep.depname text, '1' orgtype");
		sql.append(" from bs_t_sm_dep sysdep");
		sql.append(" where sysdep.depname like '%"+whereValue+"%') selectDepUser");
		sql.append(" where cust.organizepid = selectDepUser.Pid and cust.custominfopid = '"+formCustSendTree+"' ");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("pid"));
			String text = StringUtils.checkNullString(dataRow.getString("text"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 1：部门 2：人员
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			if(orgtype.equals("2"))
				dtreeBean.setChild("");
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("text", text);
			map.put("type", orgtype);
			dtreeBean.setUserdata(map);
			
			if(orgtype.equals("1")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("2")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
			
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 人员树的搜索
	 * @param whereValue
	 * @return
	 */
	public List<DtreeBean> getDepOrUserList(String topid,String whereValue,String isSelectType){
		if(whereValue=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		DepManagerService depManagerService = (DepManagerService) WebApplicationManager.getBean("depManagerService");
		DepInfo dep = depManagerService.getDepByID(topid);
		String depdns = "";
		if(dep != null) {
			depdns = dep.getDepdns();
		}
		StringBuffer sql = new StringBuffer();
		if(!isSelectType.equals("0")){//如果查询部门,则不显示人
			if("".equals(depdns)) {
				sql.append("select sysuser.pid, sysuser.fullname text, 'U' orgtype, sysuser.loginname loginname");
				sql.append(" from bs_t_sm_user sysuser");
				sql.append(" where sysuser.fullname like '%"+whereValue+"%' or sysuser.loginname like '%"+whereValue+"%' ");	
			} else {
				sql.append("select u.pid, u.fullname text, 'U' orgtype, u.loginname loginname");
				sql.append(" from bs_t_sm_user u, bs_t_sm_userdep ud, bs_t_sm_dep d");
				sql.append(" where u.pid = ud.userid and d.pid = ud.depid");
				sql.append(" and d.depdns like '" + depdns + "%'");
				sql.append(" and u.fullname like '%"+whereValue+"%' or u.loginname like '%"+whereValue+"%' ");
			}
			sql.append(" union all ");
		}
		sql.append(" select sysdep.pid, sysdep.depname text, 'D' orgtype, '' loginname");
		sql.append(" from bs_t_sm_dep sysdep");
		sql.append(" where sysdep.depname like '%"+whereValue+"%'");
		if(!"".equals(depdns)) {
			sql.append(" and sysdep.depdns like '" + depdns + "%'");
		}
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			String id = StringUtils.checkNullString(dataRow.getString("pid"));
			String text = StringUtils.checkNullString(dataRow.getString("text"));
			String loginname = StringUtils.checkNullString(dataRow.getString("loginname"));
			String orgtype = StringUtils.checkNullString(dataRow.getString("orgtype"));//类型 D：部门 U：人员
			dtreeBean.setId(id);
			dtreeBean.setText(text);
			if(orgtype.equals("U"))
				dtreeBean.setChild("");
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("text", text);
			map.put("type", orgtype);
			map.put("name", "U".equals(orgtype) ? loginname : text);
			dtreeBean.setUserdata(map);
			String nocheckbox = "";
			if(isSelectType.equals("0"))//选择部门
			{
				if(orgtype.equals("U")){
					dtreeBean.setNocheckbox(orgtype);//如果不选 则赋值
					dtreeBean.setDisabled("1");
				}
				
			}
			if(isSelectType.equals("1"))//只能选择人
			{
				if(orgtype.equals("D")){
					dtreeBean.setNocheckbox(orgtype);
				}
			}
			if(orgtype.equals("D")){
				dtreeBean.setIm0("groupImg.png");
				dtreeBean.setIm1("groupImg.png");
				dtreeBean.setIm2("groupImg.png");
			}else if(orgtype.equals("U")){
				dtreeBean.setIm0("userImg.png");
				dtreeBean.setIm1("userImg.png");
				dtreeBean.setIm2("userImg.png");
			}else{}
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	
	/**
	 * 获取父节点下的部门
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChild(String parentid){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, depname, parentid,deptype");
		sql.append(" from bs_t_sm_dep t");
		sql.append(" where status = 1");
		sql.append(" and parentid = '"+parentid+"'");
		sql.append(" order by ordernum");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("depname")));
			HashMap map = new HashMap();
			map.put("text", StringUtils.checkNullString(dataRow.getString("depname")));
			map.put("depType", StringUtils.checkNullString(dataRow.getString("deptype")));
			dtreeBean.setIm0("groupImg.png");
			dtreeBean.setIm1("groupImg.png");
			dtreeBean.setIm2("groupImg.png");
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 判断是否在自定义树表中已经选择了该组织节点
	 * @param formCustSendTree
	 * @param pid
	 * @param orgtype
	 * @return
	 */
	private boolean isSelect(String formCustSendTree,String pid,String orgtype){
		int organizetype = 0;
		if(orgtype.equals("D"))//D：部门 
			organizetype = 1;
		if(orgtype.equals("U"))//U：人员
			organizetype = 2;
		boolean flag = false;
		String hql = "from CustomOrganize where custominfopid = ? and organizepid = ? and organizetype = "+organizetype;
		Object[] values = {formCustSendTree,pid};
		List<CustomOrganize> customOrganizeList= customOrganizeDao.find(hql, values);
		int customOrganizeListLen = 0;
		if(customOrganizeList!=null)
			customOrganizeListLen = customOrganizeList.size();
		if(customOrganizeListLen>0)
			flag = true;
		return flag;
	}

	public String getformCustSendTreeId(String formSchema,String userloginname){
		String formCustSendid = "";
		StringBuffer sql =new StringBuffer();
		sql.append("select pid from bs_t_sm_formcustsendtree t ");
		sql.append(" where t.baseschema = '");
		sql.append(formSchema);
		sql.append("' and  t.loginname='");
		sql.append(userloginname);
		sql.append("'");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			formCustSendid = StringUtils.checkNullString(dataRow.getString("pid"));
		}
		return formCustSendid;
	}
	
	public void setCustomOrganizeDao(IDao<CustomOrganize> customOrganizeDao) {
		this.customOrganizeDao = customOrganizeDao;
	}
}
