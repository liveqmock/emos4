package com.ultrapower.eoms.msextend.operateform.manager;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.msextend.operateform.service.OperateFormService;
import com.ultrapower.eoms.ultrabpp.api.BaseAction;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.workflow.engine.task.model.CurrentProcessTask;

/**
 *
 * @author yxg
 * @version May 17, 2013 3:42:30 PM
 * 工单操作
 */
@Transactional
public class OperateFormImpl implements OperateFormService{
	
	private IDao jdbcDao;
	private BaseAction baseApiAction;
	private IDao<CurrentProcessTask> currTaskDao;
	
	/**
	 * 删除工单
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public boolean deleteForm(String baseID,String baseSchema){
		boolean flag = false;
		if("".equals(StringUtils.checkNullString(baseID)) || "".equals(StringUtils.checkNullString(baseSchema))){
			RecordLog.printLog("删除工单，baseID或baseSchema为空，返回false！", RecordLog.LOG_LEVEL_WARN);
			return flag;
		}
		RecordLog.printLog("调用接口删除工单：baseID:"+baseID+";baseSchema:"+baseSchema);
		try{
			StringBuffer sqlBuffer1 = new StringBuffer();
			sqlBuffer1.append(" delete");
			sqlBuffer1.append(" bs_f_"+baseSchema+" ");
			sqlBuffer1.append(" where baseid=? and baseSchema=?");
			jdbcDao.executeUpdate(sqlBuffer1.toString(), new Object[]{baseID,baseSchema});
			
			StringBuffer sqlBuffer2 = new StringBuffer();
			sqlBuffer2.append(" delete");
			sqlBuffer2.append(" bs_f_"+baseSchema+"_fml ");
			sqlBuffer2.append(" where baseid=? and baseSchema=?");
			jdbcDao.executeUpdate(sqlBuffer2.toString(), new Object[]{baseID,baseSchema});
			
			String sql3 = "delete bs_t_bpp_baseinfor where baseid=?  and baseSchema=?";
			jdbcDao.executeUpdate(sql3, new Object[]{baseID,baseSchema});
			
			String sql4 = "delete bs_t_bpp_entryattribute where baseid=? and baseSchema=?";
			jdbcDao.executeUpdate(sql4, new Object[]{baseID,baseSchema});
			
			String sql5 = "delete bs_t_wf_currenttask where  baseid=? and baseSchema=?";
			jdbcDao.executeUpdate(sql5, new Object[]{baseID,baseSchema});
			
			String sql6 = "delete bs_t_wf_historytask where  baseid=? and baseSchema=?";
			jdbcDao.executeUpdate(sql6, new Object[]{baseID,baseSchema});
			
			String sql7 = "delete bs_t_wf_dealprocess where  baseid=? and baseSchema=?";
			jdbcDao.executeUpdate(sql7, new Object[]{baseID,baseSchema});
			
			String sql8 = "delete bs_t_wf_dealprocesslog where  baseid=? and baseSchema=?";
			jdbcDao.executeUpdate(sql8, new Object[]{baseID,baseSchema});
			
			String sql9 = "delete bs_t_wf_relation where  baseid=? or relatebaseid=?";
			jdbcDao.executeUpdate(sql9, new Object[]{baseID,baseID});
			
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return flag;
	}
	
	/**
	 * 作废工单
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public boolean doCancelForm(String baseID,String baseSchema,String loginName) throws Exception{
		boolean flag = false;
		String taskID = "";
		CurrentProcessTask currentProcessTask = this.getCurrentProcessTask(baseID, baseSchema);
		if(currentProcessTask != null){
			taskID = currentProcessTask.getId();
		}
		String newBaseId = this.cancleForm(baseID,baseSchema,loginName,taskID);
		System.out.println("===================="+newBaseId);
		RecordLog.printLog("newBaseId=="+newBaseId);
		flag = true;
		return flag;
	}
	
	public CurrentProcessTask getCurrentProcessTask(String baseID,String baseSchema){
		CurrentProcessTask cpt = null;
		if("".equals(StringUtils.checkNullString(baseID)) || "".equals(StringUtils.checkNullString(baseSchema))){
			return cpt;
		}
		String ql = "from CurrentProcessTask c where c.baseId=? and c.baseSchema=?";
		List<CurrentProcessTask> currentProcessTaskList = currTaskDao.find(ql, new Object[]{baseID,baseSchema});
		if(currentProcessTaskList != null && !currentProcessTaskList.isEmpty()){
			cpt = currentProcessTaskList.get(0);
		}
		return cpt;
	}
	
	/**
	 * 作废工单
	 * @param loginName 登陆人
	 * @param assignStr 派发人
	 * @param fields 字段
	 * @param attachs 附件
	 * @return
	 */
	public String cancleForm(String baseId,String baseSchema,String loginName,String taskID){
		String actionID = "CANCEL";
		String actionType = "CANCEL";
		String actionText = "作废";
		String dealDesc = "";
		String assignStr ="";
		Map fields = null;
		List<BaseAttachmentInfo> attachs = null;
		
		String newBaseId = baseApiAction.doAction(baseId, baseSchema, taskID, loginName, actionID, actionType, actionText, assignStr, dealDesc, fields, attachs);
		return newBaseId;
	}
	
	
	@Override
	public boolean modifyForm(String baseID, String baseSchema, String assigneeid,
			String assignee, String assigneedepid, String assigneedep,
			String assigneecorpid, String assigneecorp, String assigneednid,
			String assigneedn) {
		
		boolean flag = false;
		if("".equals(StringUtils.checkNullString(baseID)) || "".equals(StringUtils.checkNullString(baseSchema))){
			RecordLog.printLog("修改工单，baseID或baseSchema为空，返回false！", RecordLog.LOG_LEVEL_WARN);
			return flag;
		}
		try{
			CurrentProcessTask currentProcessTask = this.getCurrentProcessTask(baseID, baseSchema);
			String stepNo = currentProcessTask.getStepNo();
			//修改处理人功能
			StringBuilder sql = new StringBuilder();
			sql.append(" update bs_t_wf_currenttask ");
			sql.append(" set assigneeid=?,assignee=?,assigneedepid=?,assigneedep=?,assigneecorpid=?,assigneecorp=?,assigneednid=?,assigneedn=? ");
			sql.append(" where baseid=? and baseSchema=? ");
			jdbcDao.executeUpdate(sql.toString(), new Object[]{assigneeid,assignee,assigneedepid,assigneedep,assigneecorpid,assigneecorp,assigneednid,assigneedn,baseID,baseSchema});
			//修改流程图中的记录
			StringBuilder sql_flow = new StringBuilder();
			sql_flow.append(" update bs_t_wf_dealprocess ");
			sql_flow.append(" set assigneeid=?,assignee=?,assigneedepid=?,assigneedep=?,assigneecorpid=?,assigneecorp=?,assigneednid=?,assigneedn=? ");
			sql_flow.append(" where baseid=? and baseSchema=? and stepno=? ");
			jdbcDao.executeUpdate(sql_flow.toString(), new Object[]{assigneeid,assignee,assigneedepid,assigneedep,assigneecorpid,assigneecorp,assigneednid,assigneedn,baseID,baseSchema,stepNo});
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			return flag;
		}
		
		return flag;
		
	}
	
	public IDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(IDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public BaseAction getBaseApiAction() {
		return baseApiAction;
	}

	public void setBaseApiAction(BaseAction baseApiAction) {
		this.baseApiAction = baseApiAction;
	}

	public IDao<CurrentProcessTask> getCurrTaskDao() {
		return currTaskDao;
	}

	public void setCurrTaskDao(IDao<CurrentProcessTask> currTaskDao) {
		this.currTaskDao = currTaskDao;
	}

}
