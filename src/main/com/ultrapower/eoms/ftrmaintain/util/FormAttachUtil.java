package com.ultrapower.eoms.ftrmaintain.util;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.fulltext.common.util.interfaces.IDocumentAttachment;
import com.ultrapower.eoms.fulltext.model.AttachmentInfo;

public class FormAttachUtil implements IDocumentAttachment {
	
	public List<AttachmentInfo> getAttachInfo(DataRow fieldValue) {
		if(fieldValue==null){
			return null;
		}
		String attachRela = fieldValue.getString("formattachrelation");
		if("".equals(StringUtils.checkNullString(attachRela))){
			return null;
		}
		List<AttachmentInfo> attachments = null;
		StringBuffer sql = new StringBuffer();
//		sql.append("select a2.name aname,a2.realname arealname,a2.path apath");
		sql.append("select a2.name aname,a.attachid attid");
		sql.append(" from T357 f,BS_T_WF_ATTACHMENT a, bs_t_sm_attachment a2");
		sql.append(" where f.c710000012 = ? and f.c710000012 = a.sheetid and a.attachid = a2.pid");
		QueryAdapter qa = new QueryAdapter();
		DataTable dt = qa.executeQuery(sql.toString(), new Object[]{attachRela});
		int len = 0;
		if(dt!=null){
			len = dt.length();
			if(len>0){
				attachments = new ArrayList<AttachmentInfo>(len);
			}
		}
		DataRow row;
		AttachmentInfo attach;
		for(int i=0;i<len;i++)
		{
			row = dt.getDataRow(i);
			attach = new AttachmentInfo();
			attach.setName(row.getString("aname"));
//			attach.addParam("fileFileName", row.getString("aname"));
//			attach.addParam("fileNewName", row.getString("arealname"));
//			attach.addParam("savePath", row.getString("apath"));
			attach.addParam("attid", row.getString("attid"));
			attachments.add(attach);
		}
		return attachments;
	}

}
