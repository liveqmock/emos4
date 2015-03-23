package com.ultrapower.eoms.workflow.sheet.role.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.workflow.role.service.IRoleService;

public class ChildRoleUserTag extends TagSupport
{
	public String childroleid;
	
	private IRoleService roleService;

	public String getChildroleid()
	{
		return childroleid;
	}

	public void setChildroleid(String childroleid)
	{
		this.childroleid = childroleid;
	}

	@Override
	public int doEndTag() throws JspException
	{
		String querySql = "select roleuserid, childroleid, rolecode, loginname, fullname from BS_T_WF_ROLEUSER where CHILDROLEID=?";
		QueryAdapter qa = new QueryAdapter();
		DataTable table = qa.executeQuery(querySql, new Object[]{childroleid});
		
		StringBuilder strText = new StringBuilder();
		if(table != null)
		{
			for(int i = 0; i < table.length(); i++)
			{
				strText.append("," + table.getDataRow(i).getString("fullname"));
			}
		}
		
		JspWriter out = pageContext.getOut();
		try 
		{
			out.print(strText.length() > 0 ? strText.substring(1) : "<i>用户管理</i>");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	
	
}
