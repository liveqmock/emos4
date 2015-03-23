package com.ultrapower.eoms.ultrabpp.model.component.specialfield.attachmentfield;

import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.model.BaseFieldDraw;

public class AttachmentDrawPC implements BaseFieldDraw<AttachmentField>
{

	String RN = DeployManagerImpl.RN;
	
	public String doStartTag(AttachmentField field) {
		String html = "";
		if (field != null) {
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			int visiable = field.getVisiable();
			html = "<ubf:AttachmentField name=\""+fieldName+"\" label=\""+label+"\" types=\"*.*\" visiable=\""+visiable+"\">" + RN +
			"			<atta:fileupload id=\""+fieldName+"_tag\"" + RN +
			" 			 	uploadBtnIsVisible=\"false\" fileTypes=\"${"+fieldName+"_types}\" uploadable=\"${"+fieldName+"_uploadable}\"" + RN +
			"				progressIsVisible=\"true\" uploadTableVisible=\"true\" isMultiDownload=\"true\" isAutoUpload=\"true\" downTableVisible=\"true\" isMultiUpload=\"true\"" + RN +
			"				attchmentGroupId=\"${"+fieldName+"_relcationCode}\" operationParams=\"0,${"+fieldName+"_edit},1\" uploadDestination=\"${path}\"" + RN +
			"				flashUrl=\"${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf\" >" + RN +
			"			</atta:fileupload>" + RN;
		}
		return html;
	}
	
	public String doStartTag(AttachmentField field, String ct) {
		String html = "";
		if (field != null) {
			String fieldName = field.getFieldName();
			String label = field.getLabel();
			int visiable = field.getVisiable();
			html = "<ubf:AttachmentField name=\""+fieldName+"\" label=\""+label+"\" types=\"*.*\" visiable=\""+visiable+"\">" + RN +
			ct + "	<atta:fileupload id=\""+fieldName+"_tag\"" + RN +
			ct + " 		uploadBtnIsVisible=\"false\" fileTypes=\"${"+fieldName+"_types}\" uploadable=\"${"+fieldName+"_uploadable}\"" + RN +
			ct + "		progressIsVisible=\"true\" uploadTableVisible=\"true\" isMultiDownload=\"true\" isAutoUpload=\"true\" downTableVisible=\"true\" isMultiUpload=\"true\"" + RN +
			ct + "		attchmentGroupId=\"${"+fieldName+"_relcationCode}\" operationParams=\"0,${"+fieldName+"_edit},1\" uploadDestination=\"${path}\"" + RN +
			ct + "		flashUrl=\"${ctx}/ultrabpp/runtime/clientframework/component/SpecialField/AttachmentField/js/swfupload.swf\" >" + RN +
			ct + "	</atta:fileupload>" + RN;
		}
		return html;
	}

	public String doEndTag(AttachmentField field) {
		String html = "";
		if (field != null) {
			html = "</ubf:AttachmentField>" + RN;
		}
		return html;
	}

}
