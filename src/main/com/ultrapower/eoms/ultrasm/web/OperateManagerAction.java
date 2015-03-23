package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.Operate;
import com.ultrapower.eoms.ultrasm.service.OperateManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class OperateManagerAction extends BaseAction
{
	private OperateManagerService operateManagerService;
	private Operate operate;

	/**
	 * 返回操作列表页面
	 * @return
	 */
	public String operateList()
	{
		return SUCCESS;
	}
	
	/**
	 * 保存操作信息
	 * @return
	 */
	public String saveOperate()
	{
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userpid = "";
		if(userSession != null)
			userpid = userSession.getPid();
		long currentTime = TimeUtils.getCurrentTime();
		//设置最后修改人和最后修改时间
		String saveType = this.getRequest().getParameter("saveType");
		operate.setLastmodifier(userpid);
		operate.setLastmodifytime(currentTime);
		if("add".equals(saveType))//如果保存类型是添加
		{
			//添加需设置创建人和创建时间
			operate.setCreater(userpid);
			operate.setCreatetime(currentTime);
		}
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(operateManagerService.saveOperateInfo(operate))
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 加载给定ID的操作对象
	 * @return
	 */
	public String loadOp()
	{
		String opid = StringUtils.checkNullString(this.getRequest().getParameter("opid"));
		if("".equals(opid))
		{
			return findForward("operateSave");
		}
		operate = operateManagerService.getOperateByID(opid);
		return findForward("operateSave");
	}
	
	/**
	 * 删除操作
	 * @return
	 */
	public String deleteOperate()
	{
		String opId = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if("".equals(opId))
		{
			return this.findForward("operateList");
		}
		List idList = UltraSmUtil.arrayToList(opId.split(","));
		String isAudit = PropertiesUtils.getProperty("eoms.isaudit");//是否添加审计日志
		String opName = "";
		if("true".equals(isAudit))
		{
			opName = UltraSmUtil.getNameById("bs_t_sm_operate", "opname", idList);
		}
		boolean result = operateManagerService.deleteOperateById(idList);
		if("true".equals(isAudit) && result && !"".equals(opName))
		{
			RecordLog.printOperateAuditLog("1", "10402", "删除了("+opName+")操作！");
		}
		return this.findForward("operateList");
	}
	
	/**
	 * 验证唯一性,是否可创建此操作
	 * @throws IOException
	 */
	public void checkUnique() throws IOException
	{
		String opId = StringUtils.checkNullString(this.getRequest().getParameter("opId"));
		boolean result = operateManagerService.isUnique(opId);
		PrintWriter out = this.getResponse().getWriter();
		out.print(String.valueOf(result));
	}
	
	/*
	 * 以下为属性的GET/SET方法区域 
	*/
	public void setOperateManagerService(OperateManagerService operateManagerService) 
	{
		this.operateManagerService = operateManagerService;
	}
	
	public Operate getOperate() 
	{
		return operate;
	}
	
	public void setOperate(Operate operate) 
	{
		this.operate = operate;
	}
}
