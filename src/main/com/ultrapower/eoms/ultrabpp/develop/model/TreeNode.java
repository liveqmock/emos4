package com.ultrapower.eoms.ultrabpp.develop.model;

import java.util.List;
import com.ultrapower.eoms.ultrabpp.model.BaseField;

/**
 * 
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public class TreeNode
{
    private BaseField baseField;
    private List<TreeNode> chiNodes;

    public BaseField getBaseField()
    {
	return baseField;
    }

    public void setBaseField(BaseField baseField)
    {
	this.baseField = baseField;
    }

    public List<TreeNode> getChiNodes()
    {
	return chiNodes;
    }

    public void setChiNodes(List<TreeNode> chiNodes)
    {
	this.chiNodes = chiNodes;
    }

}
