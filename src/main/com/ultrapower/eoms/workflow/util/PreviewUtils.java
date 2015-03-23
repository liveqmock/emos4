package com.ultrapower.eoms.workflow.util;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.util.WebUtils;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.workflow.flowmap.control.BaseOwnFieldInfoManage;
import com.ultrapower.eoms.workflow.flowmap.control.FieldModifyInfoViewManager;
import com.ultrapower.eoms.workflow.flowmap.model.BaseFieldBean;
import com.ultrapower.eoms.workflow.flowmap.model.BaseModel;
import com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel;
import com.ultrapower.eoms.workflow.flowmap.model.ModifyFieldView;
import com.ultrapower.eoms.workflow.sheet.attachment.service.WfAttachmentService;

public class PreviewUtils {
	
	private static AttachmentManagerService attachmentManagerService = (AttachmentManagerService)WebApplicationManager.getBean("attachmentManagerService");
	private static WfAttachmentService wfAttachmentImpl = (WfAttachmentService)WebApplicationManager.getBean("wfAttachmentImpl");
	public static UserManagerService userService = (UserManagerService)WebApplicationManager.getBean("userManagerService");
		
	public static String printMainHtml(List<BaseOwnFieldInfoModel> m_BaseOwnFieldInfoList, BaseModel m_BaseModel) {
		int colCount = 0;
    	StringBuffer sb = new StringBuffer();
    	
    	for(int i=0;i<m_BaseOwnFieldInfoList.size();i++) {
    		String m_str_field_ShowValue = "";
			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)m_BaseOwnFieldInfoList.get(i);
			long m_PrintOneLine				= m_BaseOwnFieldInfoModel.getPrintOneLine();
			long m_Base_field_Is_AllPrint	= m_BaseOwnFieldInfoModel.getBase_field_Is_AllPrint();
			String purposePrint	= m_BaseOwnFieldInfoModel.getBase_field_Purpose_Print();
			String m_Base_field_ShowName	= m_BaseOwnFieldInfoModel.getBase_field_ShowName();
			String m_Base_field_Type		= m_BaseOwnFieldInfoModel.getBase_field_Type();
			String m_Base_field_TypeValue	= m_BaseOwnFieldInfoModel.getBase_field_TypeValue();
			String m_Base_field_DBName		= m_BaseOwnFieldInfoModel.getBase_field_DBName();
			String baseCategorySchama = m_BaseOwnFieldInfoModel.getBaseCategorySchama();
			String fieldId = m_BaseOwnFieldInfoModel.getBase_field_ID();
			String fixEditPhase = m_BaseOwnFieldInfoModel.getBaseFix_field_EditPhase();
			String m_Base_field_Value = null;
			if (m_BaseModel != null) {//处理信息调用
				String baseSchema = m_BaseModel.getBaseSchema();
				if (!WorkflowUtils.isARflow(baseSchema)) {
//					if (StringUtils.isNotBlank(fixEditPhase) && fixEditPhase.indexOf("#sheet#") > -1) {
						if ("1".equals(purposePrint)) {
							m_Base_field_Is_AllPrint = 1;
						}
//					} else {
//						continue;
//					}
				}
				BaseFieldBean fieldBean = m_BaseModel.getOneBaseFieldBean(m_Base_field_DBName);
				if (fieldBean != null) {
					m_Base_field_Value = fieldBean.getM_BaseFieldValue();
					if(m_Base_field_Type.equals("7")) {//处理时间字段 
						m_str_field_ShowValue = m_Base_field_Value;
						if (StringUtils.isNotBlank(m_Base_field_Value) && m_Base_field_Value.indexOf(":") < 0) {
							m_str_field_ShowValue = TimeUtils.formatIntToDateString(NumberUtils.formatToInt(m_Base_field_Value));
						}
					}
					if (m_Base_field_Type.equals("6")) {
						String str_field_TypeValue = m_Base_field_TypeValue;
						String str_field_TypeValue_Array[] = str_field_TypeValue.split(";");
						for (int tmp_i = 0; tmp_i < str_field_TypeValue_Array.length ; tmp_i++) {
							String str_field_TypeValue_Tmp[] = str_field_TypeValue_Array[tmp_i].split("=");
							String field_TypeValue_DB_Value="";
							String field_TypeValue_DB_TureValue="";
							if(str_field_TypeValue_Tmp.length>1) {
								field_TypeValue_DB_Value			= str_field_TypeValue_Tmp[0];
								field_TypeValue_DB_TureValue		= str_field_TypeValue_Tmp[1];
								if (field_TypeValue_DB_Value.equals(m_Base_field_Value)) {
									m_str_field_ShowValue = field_TypeValue_DB_TureValue;
									break;
								}
							}
						}	  									
					}
					if ("8".equals(m_Base_field_Type)) {//字典值
						if (StringUtils.isNotBlank(m_Base_field_TypeValue)) {
							DicManagerService dicManagerService  = (DicManagerService)WebApplicationManager.getBean("dicManagerService");
							DicItem di = dicManagerService.getDicItemByValue(m_Base_field_TypeValue, m_Base_field_Value);
							if (di != null) {
								m_str_field_ShowValue = di.getDicfullname();
							}
						}
					}
				}
			} else {//工单模板调用
				m_Base_field_Value = m_BaseOwnFieldInfoModel.getBase_field_Value();
				m_str_field_ShowValue = m_Base_field_Value;
			}
			if (m_Base_field_Is_AllPrint == 0) {
				continue;
			}
			
			// 2 数字 4 字符 6 选择 7 时间 999 附件
			if(m_Base_field_Type.equals("2") || m_Base_field_Type.equals("4")) {
				m_str_field_ShowValue = m_Base_field_Value;
			}
			m_str_field_ShowValue = (StringUtils.isBlank(m_str_field_ShowValue) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : m_str_field_ShowValue.replaceAll("\\r\\n", "<br />"));
			if(m_PrintOneLine == 1) {//独占一行
				fillBlank(colCount, sb);
				colCount = 0;
				sb.append("<tr><td class='ColumntableTD_LookProcess'>"+m_Base_field_ShowName+"：</td><td colSpan='7' class='ColumntableTD'>"+m_str_field_ShowValue+"</td></tr>");
			} else {
				colCount++;
				if(colCount == 1) {
					sb.append("<tr>");
				}
				sb.append("<td class='ColumntableTD_LookProcess'>"+m_Base_field_ShowName+"：</td><td class='ColumntableTD'>"+m_str_field_ShowValue+"</td>");
				if(colCount == 4) {
					sb.append("</tr>");
					colCount = 0;
				}
			}
		}
    	fillBlank(colCount, sb);
		return sb.toString();
	}
	
	public static String printMainHtmlBpp(List<BaseField> baseFields, BaseModel m_BaseModel) {
		int colCount = 0;
		StringBuffer sb = new StringBuffer();
		
		for(int i=0;i<baseFields.size();i++) {
			String m_str_field_ShowValue = "";
			BaseField baseField = (BaseField)baseFields.get(i);
			if (baseField instanceof DataField) {
				DataField df = (DataField) baseField;
				int colSpan = df.getColspan();
				String m_Base_field_ShowName = baseField.getLabel();
				String m_Base_field_DBName = baseField.getFieldName();
				if (m_BaseModel != null) {//处理信息调用
					BaseFieldBean fieldBean = m_BaseModel.getOneBaseFieldBean(m_Base_field_DBName);
					if (fieldBean == null) {
						continue;
					}
					String dbValue = fieldBean.getM_BaseFieldValue();
					m_str_field_ShowValue = baseField.getDisplayValue(dbValue);
				} 
				m_str_field_ShowValue = (StringUtils.isBlank(m_str_field_ShowValue) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : m_str_field_ShowValue.replaceAll("\\r\\n", "<br />"));
				m_str_field_ShowValue = m_str_field_ShowValue.replaceAll("<script", "<ｓｃｒｉｐｔ").replaceAll("</script>", "</ｓｃｒｉｐｔ>");
				m_str_field_ShowValue = m_str_field_ShowValue.replaceAll("<SCRIPT", "<ｓｃｒｉｐｔ").replaceAll("</SCRIPT>", "</ｓｃｒｉｐｔ>");
				m_str_field_ShowValue = m_str_field_ShowValue.replaceAll("<Script", "<ｓｃｒｉｐｔ").replaceAll("</Script>", "</ｓｃｒｉｐｔ>");
				
//				if(m_PrintOneLine == 1) {//独占一行
//					fillBlank(colCount, sb);
//					colCount = 0;
//					sb.append("<tr><td class='ColumntableTD_LookProcess'>"+m_Base_field_ShowName+"：</td><td colSpan='7' class='ColumntableTD'>&nbsp;"+m_str_field_ShowValue+"</td></tr>");
//				} else {
//					colCount++;
//					if(colCount == 1) {
//						sb.append("<tr>");
//					}
//					sb.append("<td class='ColumntableTD_LookProcess'>"+m_Base_field_ShowName+"：</td><td class='ColumntableTD'>&nbsp;"+m_str_field_ShowValue+"</td>");
//					if(colCount == 4) {
//						sb.append("</tr>");
//						colCount = 0;
//					}
//				}
				
				if(colSpan + colCount > 4)
				{
					fillBlank(colCount, sb);
					colCount = 0;
				}
				if(colCount == 0)
				{
					sb.append("<tr>");
				}
				String colspanStr = "";
				if(colSpan > 1)
				{
					colspanStr = "colSpan='" + ((colSpan*2)-1) + "'";
				}
				sb.append("<td class='ColumntableTD_LookProcess'>"+m_Base_field_ShowName+"：</td><td "+colspanStr+" class='ColumntableTD'>&nbsp;"+m_str_field_ShowValue+"</td>");
				
				colCount += colSpan;
				
				if(colCount == 4)
				{
					sb.append("</tr>");
					colCount = 0;
				}
			}
		}
		fillBlank(colCount, sb);
		return sb.toString();
	}

	private static void fillBlank(int colCount, StringBuffer sb) {
		if(colCount > 0) {//把上一行余下的列补齐
			int spaceCol = (4 - colCount);
//			for(int h=0;h<spaceCol;h++) {
//				sb.append("<td class='ColumntableTD_LookProcess'>&nbsp;</td><td class='ColumntableTD'>&nbsp;</td>");	
//			}
			sb.append("<td colspan='"+(spaceCol*2)+"' class='ColumntableTD'>&nbsp;</td>");
			sb.append("</tr>");
		}
	} 
	
	/**
	 * jsp表单环节页面预览
	 * @param baseSchema
	 * @param stepCode
	 * @param actionType
	 * @return
	 */
	public static String preview(String baseSchema, String stepCode, String actionType) {
		int colCount = 0;
    	StringBuffer sb = new StringBuffer();
    	String con = " isPrint=1 ";
    	if (StringUtils.isNotBlank(stepCode)) {
			con += " and defEditStep like '%#"+stepCode+"#%' ";
		} 
    	if (StringUtils.isNotBlank(actionType)) {
    		con += " and freeEditStep like '%#"+actionType+"#%' ";
    	}
		List<BaseOwnFieldInfoModel> ownList= (new BaseOwnFieldInfoManage()).getList(baseSchema, null, con);
		if(CollectionUtils.isNotEmpty(ownList)) {
			for(int j = 0; j < ownList.size(); j++) {
				BaseOwnFieldInfoModel ownFieldInfoModel = ownList.get(j);
				String showName = ownFieldInfoModel.getBase_field_ShowName();
				long colSpan = ownFieldInfoModel.getPrintOneLine();
				String value = "&nbsp;";
//				if(printOneLine == 1) {//独占一行
//					fillBlank(colCount, sb);
//					colCount = 0;
//					sb.append("<tr><td class='ColumntableTD_LookProcess' width='12%'>"+showName+"：</td><td colSpan='7' class='ColumntableTD' width='13%'>"+value+"</td></tr>");
//				} else {
//					colCount++;
//					if(colCount == 1) {
//						sb.append("<tr>");
//					}
//					sb.append("<td class='ColumntableTD_LookProcess' width='12%'>"+showName+"：</td><td class='ColumntableTD' width='13%'>"+value+"</td>");
//					if(colCount == 4) {
//						sb.append("</tr>");
//						colCount = 0;
//					}
//				}
				
				if(colSpan + colCount > 4)
				{
					fillBlank(colCount, sb);
					colCount = 0;
				}
				if(colCount == 0)
				{
					sb.append("<tr>");
				}
				String colspanStr = "";
				if(colSpan > 1)
				{
					colspanStr = "colSpan='" + ((colSpan*2)-1) + "'";
				}
				sb.append("<td class='ColumntableTD_LookProcess' width='12%'>"+showName+"：</td><td "+colspanStr+" class='ColumntableTD' width='13%'>"+value+"</td>");
				
				colCount += colSpan;
				
				if(colCount == 4)
				{
					sb.append("</tr>");
					colCount = 0;
				}
			}
		}
		fillBlank(colCount, sb);
		return sb.toString();
	}
	
	public static String printStepHtml(List<BaseOwnFieldInfoModel> m_BaseOwnFieldInfoList, String processid, String taskId, String baseId, String baseSchema, String tplId) {
		int colCount = 0;
    	String m_str_field_ShowValue = "";
    	StringBuffer sb = new StringBuffer();
		
		List drawSubFieldInfo = new FieldModifyInfoViewManager().drawSubFieldInfo(m_BaseOwnFieldInfoList, baseId, baseSchema, processid, taskId, tplId);
		if(CollectionUtils.isNotEmpty(drawSubFieldInfo)) {
			for(int j = 0; j < drawSubFieldInfo.size(); j++) {
				ModifyFieldView modView = (ModifyFieldView) drawSubFieldInfo.get(j);
				String dbName = modView.getDbName();
				String value = modView.getValue();
				String name = modView.getName();
				int colSpan = Integer.valueOf(modView.getPrintOneLine());
				value = (StringUtils.isBlank(value) ? "&nbsp;" : value.replaceAll("\\r\\n", "<br />"));
//				if(printOneLine.equals("1")) {//独占一行
//					fillBlank(colCount, sb);
//					colCount = 0;
//					sb.append("<tr><td class='ColumntableTD_LookProcess'>"+name+"：</td><td colSpan='7' class='ColumntableTD'>"+value+"</td></tr>");
//				} else {
//					colCount++;
//					if(colCount == 1) {
//						sb.append("<tr>");
//					}
//					sb.append("<td class='ColumntableTD_LookProcess'>"+name+"：</td><td class='ColumntableTD'>"+value+"</td>");
//					if(colCount == 4) {
//						sb.append("</tr>");
//						colCount = 0;
//					}
//				}
				if(colSpan + colCount > 4)
				{
					fillBlank(colCount, sb);
					colCount = 0;
				}
				if(colCount == 0)
				{
					sb.append("<tr>");
				}
				String colspanStr = "";
				if(colSpan > 1)
				{
					colspanStr = "colSpan='" + ((colSpan*2)-1) + "'";
				}
				sb.append("<td class='ColumntableTD_LookProcess'>"+name+"：</td><td "+colspanStr+" class='ColumntableTD'>"+value+"</td>");
				
				colCount += colSpan;
				
				if(colCount == 4)
				{
					sb.append("</tr>");
					colCount = 0;
				}
			}
		}
		fillBlank(colCount, sb);
		return sb.toString();
	}
	
	public static String printStepHtmlBpp(List<BaseField> baseFields, String processid, String taskId, String baseId, String baseSchema, String tplId) {
		int colCount = 0;
		String m_str_field_ShowValue = "";
		StringBuffer sb = new StringBuffer();
		
		List drawSubFieldInfo = new FieldModifyInfoViewManager().drawSubFieldInfoBpp(baseId, baseSchema, processid, taskId, tplId);
		if(CollectionUtils.isNotEmpty(drawSubFieldInfo)) {
			for(int j = 0; j < drawSubFieldInfo.size(); j++) {
				ModifyFieldView modView = (ModifyFieldView) drawSubFieldInfo.get(j);
				String dbName = modView.getDbName();
				String value = modView.getValue();
				String name = modView.getName();
				int colSpan = Integer.valueOf(modView.getPrintOneLine());
				value = (StringUtils.isBlank(value) ? "&nbsp;" : value.replaceAll("\\r\\n", "<br />"));
				value = value.replaceAll("<script", "<ｓｃｒｉｐｔ").replaceAll("</script>", "</ｓｃｒｉｐｔ>");
				value = value.replaceAll("<SCRIPT", "<ｓｃｒｉｐｔ").replaceAll("</SCRIPT>", "</ｓｃｒｉｐｔ>");
				value = value.replaceAll("<Script", "<ｓｃｒｉｐｔ").replaceAll("</Script>", "</ｓｃｒｉｐｔ>");
//				if(printOneLine.equals("1")) {//独占一行
//					fillBlank(colCount, sb);
//					colCount = 0;
//					sb.append("<tr><td class='ColumntableTD_LookProcess'>"+name+"：</td><td colSpan='7' class='ColumntableTD'>&nbsp;"+value+"</td></tr>");
//				} else {
//					colCount++;
//					if(colCount == 1) {
//						sb.append("<tr>");
//					}
//					sb.append("<td class='ColumntableTD_LookProcess'>"+name+"：</td><td class='ColumntableTD'>&nbsp;"+value+"</td>");
//					if(colCount == 4) {
//						sb.append("</tr>");
//						colCount = 0;
//					}
//				}
				if(colSpan + colCount > 4)
				{
					fillBlank(colCount, sb);
					colCount = 0;
				}
				if(colCount == 0)
				{
					sb.append("<tr>");
				}
				String colspanStr = "";
				if(colSpan > 1)
				{
					colspanStr = "colSpan='" + ((colSpan*2)-1) + "'";
				}
				//value = colSpan + ":" + colCount + ":" + value;
				sb.append("<td class='ColumntableTD_LookProcess'>"+name+"：</td><td "+colspanStr+" class='ColumntableTD'>&nbsp;"+value+"</td>");
				
				colCount += colSpan;
				
				if(colCount == 4)
				{
					sb.append("</tr>");
					colCount = 0;
				}
			}
		}
		fillBlank(colCount, sb);
		return sb.toString();
	}
	
	public static String printStepAttachment(String processId) {
		StringBuffer sb = new StringBuffer();
		List<Attachment> attaches = attachmentManagerService.getAttachmentByRelation(processId);
		if (CollectionUtils.isNotEmpty(attaches)) {
			sb.append("<TR>");
			sb.append("<TD class=ColumntableTD_LookProcess valign='middle'>处理附件：</TD>");
			sb.append("<TD class=ColumntableTD colSpan=7>");
			for (int i = 0; i < attaches.size(); i++) {
				Attachment atta = attaches.get(i);
				String path = WebUtils.getContextPath() + "/attachment/download.action?";
				String fileRealName = atta.getRealname();
				String fileName = atta.getName().replace(" ", "");
				String filePath = Constants.WORKSHEET_UPLOAD_PATH + File.separator + atta.getPath();
				sb.append("<a href=\""+path+"fileFileName="+fileName+"&fileNewName="+fileRealName+"&savePath="+filePath+"\")>");
				sb.append(atta.getName() + "；");
				sb.append("</a>");
			}
			sb.append("</TD>");
			sb.append("</TR>");
		}
		return sb.toString();
	}
	
	public static UserInfo getUserInfo(String userName)
	{
		return userService.getUserByLoginName(userName);
	}
	
}
