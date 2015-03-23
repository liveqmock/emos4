package com.ultrapower.eoms.common.core.component.tree.web;

import java.io.UnsupportedEncodingException;

import com.ultrapower.eoms.common.core.component.tree.manager.UserDepTreeImpl;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 人员、部门树
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 4, 2010 10:03:13 AM
 */
public class UserDepTreeAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private  UserDepTreeImpl   usermenuDtree;
	private  String rearchUserOrDep;   
	
	/**
	 * 查询的级别
	 */
	protected String level = "";
	
	/**
	 * 生成部门的展示树
	 */
	public void getDepTree(){
		if(!"".equals(StringUtils.checkNullString(rearchUserOrDep))&&!rearchUserOrDep.equals("null")){//进行模糊查询用户和组
			String xml = usermenuDtree.getChildDepOfWhere(rearchUserOrDep);
			this.renderXML(xml);
		}else{
			if(level!=null&&level!=""){
				this.renderXML(usermenuDtree.getChildXml(id, Integer.parseInt(level)));
			}else{
				this.renderXML(usermenuDtree.getChildXml(id));
			}
		}
	}
	
	
	public void getDepUserTree(){
		boolean first = "true".equals(StringUtils.checkNullString(this.getRequest().getParameter("first"))) ? true : false;
		if("0".equals(StringUtils.checkNullString(id))) {
			first = false;
		}
		String isSelectType = this.getRequest().getParameter("isSelectType");//0:部门  1:人员  2:默认
		String targetDataArr = StringUtils.checkNullString(this.getRequest().getParameter("targetDataArr"));
		if(!"".equals(StringUtils.checkNullString(rearchUserOrDep))&&!rearchUserOrDep.equals("null")){//进行模糊查询用户和组
			this.renderXML(usermenuDtree.getDepUserListXml(id,rearchUserOrDep,isSelectType));
		}else{
			if(targetDataArr!=null&&!targetDataArr.equals("")){
				this.renderXML(usermenuDtree.getDepUserXmlOftargetDataArr(id,first,isSelectType,targetDataArr));
			}else{
				this.renderXML(usermenuDtree.getDepUserXmlNew(id,first,isSelectType));
			}
			
		}
	}
	
	/**
	 * 自定义组织机构树(定制)
	 */
	public void getCustomDepUserTree(){
		String isSelectType = StringUtils.checkNullString(this.getRequest().getParameter("isSelectType"));//0:部门  1:人员  2:默认
		String formCustSendTree = StringUtils.checkNullString(this.getRequest().getParameter("formCustSendTree"));//自定义工单派发树表id
		if(!formCustSendTree.equals("")){//不为空,则要进行查询并进行id匹配
			this.renderXML(usermenuDtree.getDepUserXml(id,isSelectType,formCustSendTree));
		}else{
			this.renderXML(usermenuDtree.getDepUserXml(id,isSelectType));
		}
	}
	
	/**
	 * 自定义派发树(展示)
	 */
	public void customOrgnized(){
		
		String isSelectType = StringUtils.checkNullString(this.getRequest().getParameter("isSelectType"));//0:部门  1:人员  2:默认
		String formCustSendTree = StringUtils.checkNullString(this.getRequest().getParameter("formCustSendTree"));//自定义工单派发树表id
		String formSchema = StringUtils.checkNullString((this.getRequest().getParameter("formSchema")));//工单Schema
		
		UserSession userSession = ActionContext.getUserSession();
		String loginname ="";
		if(userSession!=null)
		{
			loginname=userSession.getLoginName();
		}
		if(formSchema!="")
			formCustSendTree = usermenuDtree.getformCustSendTreeId(formSchema,loginname);
		
		String returnXml = "";
		if(!"".equals(StringUtils.checkNullString(rearchUserOrDep))&&!rearchUserOrDep.equals("null")){//进行模糊查询用户和组
			returnXml = usermenuDtree.getDepUserListXmlOfCust(rearchUserOrDep,formCustSendTree);
			this.renderXML(returnXml);
		}else{
			if(!formCustSendTree.equals("")){//不为空,则查询自定义的树
				returnXml = usermenuDtree.getCustomDepUserXml(id,isSelectType,formCustSendTree);
				this.renderXML(returnXml);
			}else{//当不是查定制的树,则显示全部的树结构
				returnXml = usermenuDtree.getDepUserXml(id,isSelectType);
				this.renderXML(returnXml);
			}
		}
	}
	
	public void getViewData(){
		String returnstr = "";
		String targetDataArr = this.getRequest().getParameter("targetDataArr");
		if(targetDataArr!=null&&targetDataArr!=""){
			returnstr = usermenuDtree.getViewDataStr(targetDataArr);
		}
	  this.renderText(returnstr);
	}
	
	public void setUsermenuDtree(UserDepTreeImpl usermenuDtree) {
		this.usermenuDtree = usermenuDtree;
	}


	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}


	public String getRearchUserOrDep() {
		return rearchUserOrDep;
	}


	public void setRearchUserOrDep(String rearchUserOrDep) {
		try {
			this.rearchUserOrDep = java.net.URLDecoder.decode(rearchUserOrDep,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	
}
