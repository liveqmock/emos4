package com.ultrapower.eoms.ultrabpp.develop.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.FieldConfig;
import com.ultrapower.eoms.ultrabpp.cache.model.FreeActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.WorksheetMeta;

import com.ultrapower.eoms.ultrabpp.cache.model.StepModel;
import com.ultrapower.eoms.ultrabpp.develop.model.TreeNode;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;

/**
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public interface TemplateService
{
    public String deployMain(String baseSchema);

    public String deployStep(String baseSchema, String stepCode);

    public String previewMain(String baseSchema);

    public String previewAction(String baseSchema, String actionID);

    /**
     * 结构提供两种数据 第一种，当allFlag=true时，提供给主表单配置页面所有的字段
     * 第二种，当allFlag=false时，提供给主表单部署创建页面所需的字段，不包括已经成功部署但是被删除的地段
     * 
     * @param baseSchema
     * @param allFlag
     * @return
     */
    public TreeNode buildTree(String baseSchema, boolean allFlag);

    public List<BaseField> mainFieldList(String baseSchema);

    public List<BaseField> actionFieldFreeList(String baseSchema);

    public List<StepFieldRelationModel> stepFieldList(String baseSchema, String stepNo);

    public List<ActionFieldRelationModel> actionFieldList(String baseSchema, String stepNo, String actionName);

    
    public List<ActionModel> getFreeActionList();

    public List<TreeNode> getPanelChildList(String parentID, String baseSchema);

    public List<TreeNode> getOtherChildList(String parentID, String baseSchema);

    /**
     * 主表单部署，返回生成jsp页面所需要的主表单配置字段
     * 
     * @param baseShema
     * @return
     */
    public TreeNode worksheetFieldForPage(String baseSchema);

    /**
     * 主表单部署，返回主表单配置时进行新建，修改，删除的字段(主表单+动作)，返回Map<String,List<DataField>>,key=add/update/delete,value=List<DataField>
     * 当主表单是第一次部署时,只存在key=add的map值。
     */
    public Map<String, List<BaseField>> worksheetFieldChangeMap(String baseSchema);
    /**
     * 主表单部署成功，调用回调接口，更新主表单字段信息，在后台将所有主表单字段更新为已经部署状态，并且将删除的数据进行物理删除。实际是完成了一次所有字段的清理更新动作。
     */
    public void worksheetFieldChangeUpdate(String baseSchema);
    /**
     * 动作部署 返回生成jsp页面所需要的配置字段
     * 
     * @param baseSchema
     * @return
     */
    public List<BaseField> actionFieldForPage(String baseSchema,String stepNo, String actionName);
    /**
     * 自由动作部署 返回生成jsp页面所需要的配置字段 Map<String,List<BaseField>> key=创建jsp页面名称
     */
    public Map<String,List<BaseField>> freeActionFieldForPage(String baseSchema,List<String> baseStatus,List<String> actionName,List<String> stepNo);
    /**
     * 自由动作预览
     */
    public List<BaseField> freeActionFieldDeploy(String baseSchema,String baseStatus,String actionName);
    /**
     * (固定)动作部署成功，调用回调接口，更新字段信息
     */
    //public void actionFieldChangeUpdate(String baseSchema,String stepNo,String actionName);
    
    public List<FreeActionFieldRelationModel> getFreeActionfieldrelList(String baseSchema);

    /**
     * 删除流程分类时 调用接口。
     */
    public void delByBaseSchema(String baseSchema);
    
    /**
     * 获取所有字段信息列表
     */
    public List<FieldConfig> getFieldInfoList();
    
    /*
     * 环节以及环节下所有动作列表
     */
    public WorksheetMeta getWorksheetMeta(String baseSchema);
    /**
     * 返回状态动作类型列表，去除没有后续动作的动作类型
     */
    public List<Object[]> getStatusAndActionType(String baseSchema);
    /**
     * 动作部署（固定）返回动作配置时进行新建，修改，删除的字段
     */
    //public Map<String, List<BaseField>> actionFieldChangeMap(String baseSchema,String stepNo, String actionName);
    /**
     * 动作部署（自由）返回动作配置时进行新建，修改，删除的字段
     */
    //public Map<String, List<BaseField>> actionFieldChangeFreeMap(String baseSchema,String baseStatus,String actionType);
    
    /**
     * (自由)动作部署成功，调用回调接口，更新字段信息
     */
    //public void actionFieldChangeUpdateFree(String baseSchema,String baseStatus,String actionType);
    /**
     * 部署成功后，将字段更新到部署成功状态，hasDeploy=1 and operate=add
     * 还要根据字段的parentid判断字段归属（主表单或者动作），同时更新关联表的字段状态
     */
    //public void fieldChangeUpdate(BaseField baseField);
    

}
