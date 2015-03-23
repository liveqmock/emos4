package com.ultrapower.eoms.ultrabpp.utils;

import java.io.File;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;

import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;

public class PreviewTag extends TagSupport {
	
	private String name;
	
	private String type;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8127354097248752389L;

	@Override
	public int doStartTag() throws JspException {
		WorksheetDisplayContext displayCxt = (WorksheetDisplayContext)pageContext.getRequest().getAttribute("displayCxt");
		if (displayCxt != null) {
			String editType = displayCxt.getEditType();
			String mainPage = displayCxt.getMainPage();
			String baseSchema = displayCxt.getBaseSchema();
			if ("PREVIEWMAIN".equals(editType)) {
				File jsFile = new File(DeployManagerImpl.getPath(baseSchema, "main") + mainPage + ".jsp");
				if (jsFile.exists()) {
					jsFile.delete();
				}
			} else if ("PREVIEWACTION".equals(editType)) {
				File jsFile = new File(DeployManagerImpl.getPath(baseSchema, "theme") + mainPage + ".jsp");
				if (jsFile.exists()) {
					jsFile.delete();
				}
			}
		}
		return EVAL_PAGE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
