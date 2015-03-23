package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.CustomOrganize;
import com.ultrapower.eoms.ultrasm.model.FormCustSendTree;
import com.ultrapower.eoms.ultrasm.model.FormCustSenderDelPara;
import com.ultrapower.eoms.ultrasm.model.FormSenderTreeView;
import com.ultrapower.eoms.ultrasm.service.FormCustSendTreeService;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-20 下午04:32:44
 */
@Transactional
public class FormCustSendTreeImpl implements FormCustSendTreeService {

	private QueryAdapter queryAdapter = new QueryAdapter();
	private DataAdapter dataAdapter = new DataAdapter();
	private IDao<FormCustSendTree> customTreeDao;
	private IDao<CustomOrganize>  customOrganizeDao;
	
	
	public FormCustSendTree getFormCustSendTree(String basescheam,String loginname) {
		FormCustSendTree formCustSendTree = null;
		if(basescheam!=null&&!basescheam.equals("")){
			String hql = "from FormCustSendTree where baseschema = ? and loginname = ?";
			Object[] values = {basescheam,loginname};
			formCustSendTree = customTreeDao.findUnique(hql, values);
		}
		return formCustSendTree;
	}
	
	public void addCustomTree(FormCustSendTree customTree,
			List<CustomOrganize> customOrganizeList) {
		
		if(customTree==null)
			return;
		customTreeDao.save(customTree);
		
		int customOrganizeListLen = 0;
		if(customOrganizeList!=null)
			customOrganizeListLen = customOrganizeList.size();
		for(int i=0;i<customOrganizeListLen;i++){
			CustomOrganize customOrganize = customOrganizeList.get(i);
			customOrganize.setCustominfopid(customTree.getPid());
			customOrganizeDao.save(customOrganize);
		}
	}
	
	public void addCustomTree(FormCustSendTree customTree) {
		if(customTree==null)
			return ;
		customTreeDao.save(customTree);
	}
	
	public void delCustomTree(FormCustSendTree customTree) {
		if(customTree==null)
			return ;
		if(this.delCustomorganize(customTree.getPid()))//先删除子表 再删除树主表
			customTreeDao.remove(customTree);
	}
	
	public void delCustomTree(String id) {
		if(StringUtils.checkNullString(id).equals(""))
			return ;
		if(this.delCustomorganize(id))//先删除子表 再删除树主表
			this.deleteCustomTree(id);
	}
	
	public void updateCustomTree(FormCustSendTree customTree,
			List<CustomOrganize> customOrganizeList) {
		if(customTree==null)
			return ;
		customTreeDao.saveOrUpdate(customTree);
		
		if(this.delCustomorganize(customTree.getPid())){//先删除节点信息,再进行添加
			int customOrganizeListLen = 0;
			if(customOrganizeList!=null)
				customOrganizeListLen = customOrganizeList.size();
			for(int i=0;i<customOrganizeListLen;i++){
				CustomOrganize customOrganize = customOrganizeList.get(i);
				customOrganize.setCustominfopid(customTree.getPid());
				customOrganizeDao.save(customOrganize);
			}
		}
	}	
	
	public void transStatus(List<String> pid, int status) {
		int pidLen = 0;
		if(pid!=null)
			pidLen = pid.size();
		for(int i=0;i<pidLen;i++){
			this.updateCustomTreeStatus(pid.get(i),status);
		}
	}
	
	public void delCustomTree(List<String> pids) {
		int pidsLen = 0;
		if(pids!=null)
			pidsLen = pids.size();
		for(int i=0;i<pidsLen;i++){
			if(this.delCustomorganize(pids.get(i)))
				this.deleteCustomTree(pids.get(i));
		}
	}
	
	public void updateCustOrgTree(String formcustPid,
			List<CustomOrganize> customOrganizeList) {
		if(customOrganizeList==null)
			return ;
		int customOrganizeListLen = 0;
		customOrganizeListLen = customOrganizeList.size();
		for(int i=0;i<customOrganizeListLen;i++){
			CustomOrganize dustomOrganize = customOrganizeList.get(i);
			String hql = "from CustomOrganize where custominfopid = ? and organizepid = ? and organizetype = ?";
			Object[] values = {dustomOrganize.getCustominfopid(),dustomOrganize.getOrganizepid(),dustomOrganize.getOrganizetype()};
			List<CustomOrganize> custOrgList = customOrganizeDao.find(hql, values);
			if(custOrgList!=null){
				if(custOrgList.size() == 0){
					customOrganizeDao.save(dustomOrganize);
				}else{
					RecordLog.printLog("自定义节点：custominfopid="+dustomOrganize.getCustominfopid()+";organizepid="+dustomOrganize.getOrganizepid()+";organizetype="+dustomOrganize.getOrganizetype()+"。已经存在,不再进行添加!", RecordLog.LOG_LEVEL_INFO);
				}
			}
		}
	}
	
	public List<FormSenderTreeView> getFormSenderTreeView(String formcustpid) {
		if(StringUtils.checkNullString(formcustpid).equals(""))
			return null;
		List<FormSenderTreeView> slist = new ArrayList<FormSenderTreeView>(); 
		StringBuffer sql = new StringBuffer();
		sql.append("select orguser.pid, orguser.fullname text, orguser.organizetype, sysdep.pid parentid");
		sql.append(" from bs_t_sm_dep sysdep, bs_t_sm_userdep userdep,");
		sql.append("(select sysuser.pid, sysuser.fullname, custorg.organizetype ");
		sql.append(" from bs_t_sm_user sysuser, bs_t_sm_customorganize custorg");
		sql.append(" where sysuser.status = '1' and sysuser.pid = custorg.organizepid and custorg.custominfopid = ?) orguser");
		sql.append(" where userdep.depid = sysdep.pid and sysdep.status = 1 and userdep.userid = orguser.pid");
		sql.append("  union all ");
		sql.append(" select sysdep.pid, sysdep.depname text, custorg.organizetype,sysdep.parentid ");
		sql.append(" from bs_t_sm_dep sysdep, bs_t_sm_customorganize custorg ");
		sql.append(" where sysdep.status = 1 and sysdep.pid = custorg.organizepid and custorg.custominfopid = ? ");
		Object[] values = {formcustpid,formcustpid};
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), values);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		FormSenderTreeView formSenderTreeView;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			formSenderTreeView = new FormSenderTreeView();
			formSenderTreeView.setPid(StringUtils.checkNullString(dataRow.getString("pid")));
			formSenderTreeView.setText(StringUtils.checkNullString(dataRow.getString("text")));
			formSenderTreeView.setParentid(StringUtils.checkNullString(dataRow.getString("parentid")));
			formSenderTreeView.setOrgtype(StringUtils.checkNullString(dataRow.getString("organizetype")));
			slist.add(formSenderTreeView);
		}
		return slist;
	}
	
	public void delAssignTreeOrganized(List<FormCustSenderDelPara> pids) {
		if(pids==null)
			return ;
		int pidsLen = pids.size();
		for(int p=0;p<pidsLen;p++){
			FormCustSenderDelPara fdp = pids.get(p);
			StringBuffer sql = new StringBuffer(1024);
			if(fdp.getOrgtype().equals("1")){//(部门)递归删除当前组下所有的人和子组,包括当前组和当前组下的人
				sql.append(" delete from bs_t_bpp_assingtreeorganize atreeorg");
				sql.append(" where atreeorg.configid = '"+fdp.getCustSenderPid()+"' and atreeorg.organizeid in");
				sql.append(" (");
				//获取当前组下的所有子组,包括当前组
				sql.append(" select sysdep.pid from bs_t_sm_dep sysdep");
				sql.append(" where sysdep.depdns like (select depdns from bs_t_sm_dep sysdep where sysdep.pid = '"+fdp.getOrginzedpid()+"') || '%' ");
				sql.append("  union all ");
				//获取当前组下子组的人,包括当前组下的人
				sql.append(" select userdep.userid");
				sql.append(" from bs_t_sm_userdep userdep, (select sysdep.pid from bs_t_sm_dep sysdep where sysdep.depdns like (select depdns from bs_t_sm_dep sysdep where sysdep.pid = '"+fdp.getOrginzedpid()+"') || '%') sysdep ");
				sql.append(" where userdep.depid = sysdep.pid ");
				sql.append(" )");
			}else if(fdp.getOrgtype().equals("2")){//(用户)删除用户
				sql.append(" delete from bs_t_bpp_assingtreeorganize org");
				//sql.append(" where org.configid = '"+fdp.getCustSenderPid()+"'");
				//fanying bg 2013-6-20 解决自定义派发树同一个人员出现两次的问题
				sql.append(" where org.configid = '"+fdp.getCustSenderPid()+"' and org.parentorgid='"+fdp.getParentid()+"'");
				//fanying ed
				sql.append(" and org.organizeid = '"+fdp.getOrginzedpid()+"'");
				sql.append(" and org.organizetype = 2");		
			}else{}
			Object[] values = null;
			try{
				dataAdapter.execute(sql.toString(), values);
			}catch(Exception e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_INFO);
				e.printStackTrace();
			}
		}
	}
	public void delCustOrganized(List<FormCustSenderDelPara> pids) {
		if(pids==null)
			return ;
		int pidsLen = pids.size();
		for(int p=0;p<pidsLen;p++){
			FormCustSenderDelPara fdp = pids.get(p);
			StringBuffer sql = new StringBuffer(1024);
			if(fdp.getOrgtype().equals("1")){//(部门)递归删除当前组下所有的人和子组,包括当前组和当前组下的人
				sql.append(" delete from bs_t_sm_customorganize custorg");
				sql.append(" where custorg.custominfopid = '"+fdp.getCustSenderPid()+"' and custorg.organizepid in");
				sql.append(" (");
				//获取当前组下的所有子组,包括当前组
				sql.append(" select sysdep.pid from bs_t_sm_dep sysdep");
				sql.append(" where sysdep.depdns like (select depdns from bs_t_sm_dep sysdep where sysdep.pid = '"+fdp.getOrginzedpid()+"') || '%' ");
				sql.append("  union all ");
				//获取当前组下子组的人,包括当前组下的人
				sql.append(" select userdep.userid");
				sql.append(" from bs_t_sm_userdep userdep, (select sysdep.pid from bs_t_sm_dep sysdep where sysdep.depdns like (select depdns from bs_t_sm_dep sysdep where sysdep.pid = '"+fdp.getOrginzedpid()+"') || '%') sysdep ");
				sql.append(" where userdep.depid = sysdep.pid ");
				sql.append(" )");
			}else if(fdp.getOrgtype().equals("2")){//(用户)删除用户
				sql.append(" delete from bs_t_sm_customorganize org");
				sql.append(" where org.custominfopid = '"+fdp.getCustSenderPid()+"'");
				sql.append(" and org.organizepid = '"+fdp.getOrginzedpid()+"'");
				sql.append(" and org.organizetype = 2");		
			}else{}
			Object[] values = null;
			try{
				dataAdapter.execute(sql.toString(), values);
			}catch(Exception e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_INFO);
				e.printStackTrace();
			}
		}
	}
	
	private void deleteCustomTree(String pid){
		String hql = "delete FormCustSendTree where pid = ?";
		Object[] values = {pid};
		customTreeDao.executeUpdate(hql, values);
	}
	
	private void updateCustomTreeStatus(String pid,int status){
		String hql = " update FormCustSendTree set status = "+status+" where pid = ?";
		Object[] values = {pid};
		int result = customTreeDao.executeUpdate(hql, values);
		if(result==1){
			RecordLog.printLog("工单派发树信息表,pid="+pid+",修改状态为："+status+"--成功", RecordLog.LOG_LEVEL_INFO);
		}else{
			RecordLog.printLog("工单派发树信息表,pid="+pid+",修改状态为："+status+"--失败", RecordLog.LOG_LEVEL_INFO);
		}
	}
	
	private boolean delCustomorganize(String customTreeId){
		boolean flag = false;
		String hql = "delete from CustomOrganize where custominfopid = ?";
		Object[] values = {customTreeId};
		try{
			customOrganizeDao.executeUpdate(hql, values);
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public void setCustomTreeDao(IDao<FormCustSendTree> customTreeDao) {
		this.customTreeDao = customTreeDao;
	}

	public void setCustomOrganizeDao(IDao<CustomOrganize> customOrganizeDao) {
		this.customOrganizeDao = customOrganizeDao;
	}
}
