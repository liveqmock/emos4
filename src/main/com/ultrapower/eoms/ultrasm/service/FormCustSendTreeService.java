package com.ultrapower.eoms.ultrasm.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.CustomOrganize;
import com.ultrapower.eoms.ultrasm.model.FormCustSendTree;
import com.ultrapower.eoms.ultrasm.model.FormCustSenderDelPara;
import com.ultrapower.eoms.ultrasm.model.FormSenderTreeView;

/**
 * 自定义派发树服务
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-20 下午04:24:29
 */
public interface FormCustSendTreeService {
	
	/**
	 * 获取组织机构集合列表
	 * @param formcustpid 工单自定义标识
	 * @return 返回树节点对象集合
	 */
	public List<FormSenderTreeView> getFormSenderTreeView(String formcustpid);
	
	/**
	 * 获取自定义派发树信息
	 * @param basescheam 工单类别
	 * @return 返回派发树的对象
	 */
	public FormCustSendTree getFormCustSendTree(String basescheam,String loginname);
	
	/**
	 * 添加自定义派发树信息
	 * @param customTree 自定义派发树对象信息
	 * @param customOrganizeList 自定义树节点对象集合
	 */
	public void addCustomTree(FormCustSendTree customTree,List<CustomOrganize> customOrganizeList);
	
	/**
	 * 添加自定义派发树对象
	 * @param customTree 自定义派发树基本信息
	 */
	public void addCustomTree(FormCustSendTree customTree);
	
	/**
	 * 更新自定义派发树对象
	 * @param customTree 自定义派发树对象信息
	 * @param customOrganizeList 自定义树节点信息集合
	 */
	public void updateCustomTree(FormCustSendTree customTree,List<CustomOrganize> customOrganizeList);
	
	
	/**
	 * 更新自定义派发树对象
	 * @param formcustPid 自定义派发树标识
	 * @param customOrganizeList 自定义树节点信息
	 */
	public void updateCustOrgTree(String formcustPid,List<CustomOrganize> customOrganizeList);
	
	/**
	 * 删除自定义派发树对象
	 * @param customTree 自定义派发树基本信息
	 */
	public void delCustomTree(FormCustSendTree customTree);
	
	/**
	 * 删除自定义派发树信息
	 * @param id 自定义派发树标识
	 */
	public void delCustomTree(String id);
	
	/**
	 * 删除自定义派发树信息
	 * @param pids 自定义派发树标识集合
	 */
	public void delCustomTree(List<String> pids);
	
	/**
	 * 删除定义的组织机构,如果输入的类型为用户则直接进行删除;
	 * 如果输入的是部门,则需要对该部门下面的所有机构和人进行递归删除
	 * @param pids
	 */
	public void delCustOrganized(List<FormCustSenderDelPara> pids);
	/**
	 * 删除派发树定义的组织机构,如果输入的类型为用户则直接进行删除;
	 * 如果输入的是部门,则需要对该部门下面的所有机构和人进行递归删除
	 * @param pids
	 */
	public void delAssignTreeOrganized(List<FormCustSenderDelPara> pids);
	
	
	/**
	 * 自定义派发树状态修改
	 * @param pid 自定义派发树标识集合
	 * @param status 状态
	 */
	public void transStatus(List<String> pid,int status);
	
}
