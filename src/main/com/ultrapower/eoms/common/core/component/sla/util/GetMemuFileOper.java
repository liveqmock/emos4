package com.ultrapower.eoms.common.core.component.sla.util;

import java.util.ArrayList;
import java.util.List;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * 获取sla目录下的xml文件名列表
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-6 下午05:42:07
 */
public class GetMemuFileOper extends TreeManager{

	public String getFileNameTree(){
		DataTable dt= StartUp.sqlDt;
		int dtLen = 0;
		if(dt!=null)
			dtLen = dt.length();
		List<DtreeBean> sqlNameList = new ArrayList<DtreeBean>();
		DtreeBean dtreeBean;
		for(int row=0;row<dtLen;row++){
			DataRow dataRow = dt.getDataRow(row);
			String sqlname = StringUtils.checkNullString(dataRow.getString("sqlname"));
			if(sqlname.startsWith("SQL_SLA_")){
				dtreeBean = new DtreeBean();
				dtreeBean.setId(String.valueOf(row));
				dtreeBean.setText(sqlname);
				dtreeBean.setIm0("folderClosed.gif");
				dtreeBean.setIm1("folderClosed.gif");
				dtreeBean.setIm2("folderClosed.gif");
				sqlNameList.add(dtreeBean);
			}
		}
		return this.apposeDhtmlXtreeXml(sqlNameList);
	}
	
//	public String getFileNameTree(){
//		List<DtreeBean> dtreeBeanList =  getSlaFolderFileName();
//		return this.apposeDhtmlXtreeXml(dtreeBeanList);
//	}
	
//	public List<DtreeBean> getSlaFolderFileName(){
//		List<DtreeBean> list = new ArrayList<DtreeBean>();
//		String filepath = RConstants.xmlPath + File.separator + "sla";
//		File file = new File(filepath);
//		if(file.exists()){
//			File[] fileArr = file.listFiles(new MyFileFilter());
//			int fileArrLen = 0;
//			if(fileArr!=null)
//				fileArrLen = fileArr.length;
//			DtreeBean dtreeBean;
//			for(int i=0;i<fileArrLen;i++){
//				dtreeBean = new DtreeBean();
//				dtreeBean.setId(String.valueOf(i));
//				dtreeBean.setText(fileArr[i].getName());
//				dtreeBean.setIm0("folderClosed.gif");
//				dtreeBean.setIm1("folderClosed.gif");
//				dtreeBean.setIm2("folderClosed.gif");
//				list.add(dtreeBean);
//			}
//		}
//		return list;
//	}
//	
//	class MyFileFilter implements FileFilter{   
//        public boolean accept(File pathname) {   
//            String filename = pathname.getName().toLowerCase();   
//            if(filename.contains(".xml")){   
//                return true;   
//            }else{   
//                return false;   
//            }   
//        }   
//    }  

}
