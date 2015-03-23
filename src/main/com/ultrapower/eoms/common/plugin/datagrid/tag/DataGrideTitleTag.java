package com.ultrapower.eoms.common.plugin.datagrid.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class DataGrideTitleTag extends BodyTagSupport{
	
//	public int doStartTag() {
//		
//		return EVAL_BODY_INCLUDE;
//	}	
	
	
	protected Object getBodyValue() throws JspException {
        Object result = getBodyContent().getString();
        if (result != null) {
            result = ExpressionEvaluatorManager.evaluate("extendResult", result.toString(), Object.class, this, pageContext);
        }
        return result;
    }
	
	public int doEndTag() {
		try {
			Object bodyvalue=getBodyValue();
			if(bodyvalue!=null)
			{
		        Tag t = findAncestorWithClass(this, DataGrideTag.class);    
		        DataGrideTag parent = (DataGrideTag) t;
		        parent.setGridtitle(bodyvalue.toString());
			} 

		} catch (Exception e) {
			System.out.println(e);
		}
		return EVAL_PAGE;
	}
	
	
//    public int doAfterBody() throws JspException {
//    	//Object bodyvalue=getBodyValue();
////        try {
////            TableModel model = TagUtils.getModel(this);
////            
////            if (StringUtils.isBlank(location)|| location.equalsIgnoreCase("toolbar")){
////            	model.getTable().setAttribute("ExtendTool", getBodyValue());
////            }else if (location.equalsIgnoreCase("top")){
////            	model.getTable().setAttribute("ExtendTableTop", getBodyValue());
////            }else if (location.equalsIgnoreCase("bottom")){
////            	model.getTable().setAttribute("ExtendTableBottom", getBodyValue());
////            }else{
////            	model.getTable().setAttribute("ExtendTool", getBodyValue());
////            }
////
////        } catch (Exception e) {
////            throw new JspException("TDExtendTag.doAfterBody() Problem: " + ExceptionUtils.formatStackTrace(e));
////        }
//
//        return SKIP_BODY;
//    }	
}
