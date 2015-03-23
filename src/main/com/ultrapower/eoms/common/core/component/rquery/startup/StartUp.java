package com.ultrapower.eoms.common.core.component.rquery.startup;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.jdom.Element;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class StartUp {
	/**
	 * 初始化报表
	 * @param xmlFilePath
	 */
	public static HashMap sqlmapElement;
	public static DataTable sqlDt;
	public static void loadFile(String xmlFilePath)
	{
		sqlmapElement=new HashMap();
		sqlDt=new DataTable("sqlconfig");
		init(xmlFilePath);
	}
	public static void init(String xmlFilePath)
 	{
		xmlFilePath=StringUtils.checkNullString(xmlFilePath);
		//RConstants.sqlmapElement.clear();
		if(xmlFilePath.equals(""))
			xmlFilePath=RConstants.xmlPath;
		File inThePath=new File(xmlFilePath);//建立当前目录中文件的File对象
		File list[]=inThePath.listFiles();   //取得代表目录中所有文件的File对象数组
		int intlstCount=0;
		if(list!=null)
			intlstCount=list.length;
		for(int i=0;i<intlstCount;i++)
		{
			String filename=list[i].getName();
			
			if(filename.toLowerCase().endsWith(".xml")){
				
				if(filename.startsWith("SQL_")){
					RecordLog.printLog(filename, RecordLog.LOG_LEVEL_INFO);
					loadXml(xmlFilePath,filename);
				}
			}
			else
			{
				if(!filename.endsWith("."))
					init(xmlFilePath+File.separator+filename);
			}
		}//for(int i=0;i<intlstCount;i++)
	}	
	
	public  static void loadXml(String xmlPath,String filename)
	{
		
		String name;
		String filepath="";
		if(xmlPath==null || "".equals(xmlPath))
			xmlPath=RConstants.xmlPath;
		if(filename==null || "".equals(filename))	
			return ;
		
		filepath=xmlPath+File.separator+filename;
		XmlParser xmlFile=new XmlParser(filepath);
		List lstElement=xmlFile.getElement("sqlquery");
		int lstCount=0;
		if(lstElement!=null)
			lstCount=lstElement.size();
		for(int i=0;i<lstCount;i++)
		{
			Element element=(Element)lstElement.get(i);
			if(element==null)
				continue;
			name=StringUtils.checkNullString(element.getName());
			if(name.equals("sqlquery"))
			{
				DataRow dr=new DataRow();
				dr.put("path", xmlPath);
				dr.put("filename", filename);
				Element elementParSql=(Element)lstElement.get(i);
				name=elementParSql.getAttributeValue("name");
				sqlmapElement.put(name, elementParSql);
				dr.put("sqlname", name);
				
				sqlDt.putDataRow(dr);
			}
		}		
	}
}
