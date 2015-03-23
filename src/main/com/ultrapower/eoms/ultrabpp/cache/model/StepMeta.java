package com.ultrapower.eoms.ultrabpp.cache.model;

import java.util.List;

public class StepMeta
{
    private StepModel stepModel;
    private List<ActionMeta> actions;
    private List<StepFieldRelationModel> fields;

    
    public StepModel getStepModel() {
        return stepModel;
    }
    public void setStepModel(StepModel stepModel) {
        this.stepModel = stepModel;
    }
    public List<ActionMeta> getActions() {
        return actions;
    }
    public void setActions(List<ActionMeta> actions) {
        this.actions = actions;
    }
    public List<StepFieldRelationModel> getFields() {
        return fields;
    }
    public void setFields(List<StepFieldRelationModel> fields) {
        this.fields = fields;
    }

    
}
