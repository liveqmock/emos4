package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;

/**
 * 操作按钮，用于控制操作
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-6-8
 */
public class OperationTag  extends TagSupport
{
	private String id;//名称
	private String cssclass;
	private String onmouseover;
	private String onmouseout;
	private String onclick;
	private String text;
    private String operate;
    
	public String getOperate() 
	{
		return StringUtils.checkNullString(operate);
	}
	public void setOperate(String operate) 
	{
		this.operate = operate;
	}
	public String getId() 
	{
		return StringUtils.checkNullString(id);
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public String getCssclass() 
	{
		return StringUtils.checkNullString(cssclass);
	}
	public void setCssclass(String cssclass) 
	{
		this.cssclass = cssclass;
	}
	public String getOnmouseover() 
	{
		return StringUtils.checkNullString(onmouseover);
	}
	public void setOnmouseover(String onmouseover) 
	{
		this.onmouseover = onmouseover;
	}
	public String getOnmouseout() 
	{
		return StringUtils.checkNullString(onmouseout);
	}
	public void setOnmouseout(String onmouseout) 
	{
		this.onmouseout = onmouseout;
	}
	public String getOnclick() 
	{
		return StringUtils.checkNullString(onclick);
	}
	public void setOnclick(String onclick) 
	{
		this.onclick = onclick;
	}
	public String getText() 
	{
		return StringUtils.checkNullString(text);
	}
	public void setText(String text) 
	{
		this.text = text;
	}
	
	public int doStartTag()
	{
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() 
	{
		JspWriter out = pageContext.getOut();
		try {
			String resourceid="";
			if(!this.getOperate().equals(""))
			{
				ActionContext ac = ActionContext.getContext();
				if(ac!=null)
				{
					resourceid = StringUtils.checkNullString(ac.get(ActionContext.HTTP_RESOURCE));
				}
//				resourceid=StringUtils.checkNullString(ActionContext.getContext().get(ActionContext.HTTP_RESOURCE));
			}
			boolean isAllow=true;
			String userid="";
			Object objSession=pageContext.getSession().getAttribute("userSession");
			if(objSession!=null)
			{
				UserSession userSession=(UserSession)objSession;
				userid=StringUtils.checkNullString(userSession.getPid());
			}
			if(!resourceid.equals("")&& !this.getOperate().equals("")&&!userid.equals(""))
			{
				Object obj=WebApplicationManager.getBean("resourceUrlManagerService");
				obj=WebApplicationManager.getBean("privilegeManagerService");
				if(obj!=null)
				{
					PrivilegeManagerService privilegeManagerService=(PrivilegeManagerService)obj;
					isAllow=privilegeManagerService.isAllow(userid, resourceid, this.getOperate());
				}				
				
//				if(resourceid.equals("1")&& this.getOperate().equals("com_add_op"))
//				{
//					isPrivelge=false;
//				}
			}
/*			if(this.getOperate().equals("add"))
			{
				isPrivelge=false;
			}
*/			//有权限访问
			if(isAllow)
			{
				StringBuffer stringBuffer=new StringBuffer();
				stringBuffer.append("<li");
				stringBuffer.append(" class='"+this.getCssclass()+"'");
				stringBuffer.append(" id='"+this.getId()+"'");
				stringBuffer.append(" onmouseover=\""+this.getOnmouseover()+"\"");
				stringBuffer.append(" onmouseout=\""+this.getOnmouseout()+"\"");
				stringBuffer.append(" onclick=\""+this.getOnclick()+"\"");
				stringBuffer.append(">");
				String strText=Internation.language(this.getText());
				if(strText==null || "".equals(strText))
				{
					strText=this.getText();
				}
				stringBuffer.append(strText);
				stringBuffer.append("</li>");
				out.print(stringBuffer.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
