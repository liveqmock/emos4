package com.ultrapower.eoms.ultrabpp.cache.model;

import java.util.List;

import com.ultrapower.eoms.ultrabpp.model.BaseField;

/**
 * 动作的Meta对象
 * 
 * @author BigMouse
 * 
 */
public class ActionMeta
{
    private ActionModel actionModel;
    private List<ActionFieldRelationModel> fields;
    

    public List<ActionFieldRelationModel> getFields() {
	return fields;
    }

    public void setFields(List<ActionFieldRelationModel> fields) {
	this.fields = fields;
    }

    public ActionModel getActionModel() {
        return actionModel;
    }

    public void setActionModel(ActionModel actionModel) {
        this.actionModel = actionModel;
    }

}
