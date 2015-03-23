package com.ultrapower.eoms.common.core.component.xml;

import java.io.File;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * XML解析
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-6-7
 */
public class XmlParser {

	Element rootElement=null;
	/**
	 * 根据xml字符串来解析xml
	 * @param xmlString
	 */
	public  XmlParser(String xmlString,int type)
	{
        //创建一个新的字符串
        StringReader read = new StringReader(xmlString);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try{
	        //通过输入源构造一个Document
	        Document doc = sb.build(source);
	        rootElement = doc.getRootElement();
        }catch(Exception ex)
        {
        	ex.printStackTrace();
        }		
	}
	/**
	 * 根据传入的xml文件逻辑来解析xml
	 * @param p_FilePath
	 */
	public XmlParser(String p_FilePath)
	{
		Document doc = null;
		String m_FilePath=StringUtils.checkNullString(p_FilePath);		
		try
		{	
			File file = null;
			file = new File(m_FilePath);
			SAXBuilder builder = new SAXBuilder();
	
				doc = builder.build(file);
				//doc.getRootElement();
				rootElement = doc.getRootElement();
		}catch(Exception ex)
		{  
			ex.printStackTrace();
		}
		
	}	

	/**
	 * 根据xml路径和属性名称，取属性值
	 * @param catalog xml目录，以#分割
	 * @param atrName 属性名
	 * @return
	 */
	public String getAttributeValue(String  catalog,String atrName)
	{
		catalog=StringUtils.checkNullString(catalog);
		Element element = rootElement;
		String tags[] =null;
		int len=0;
		if(!catalog.equals(""))
		{
			tags= catalog.split("#");
			len=tags.length;
		}
		try
		{	
			for (int i = 0; i < len; i++) {
				element = element.getChild(tags[i]);
				if(element==null)
					break;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		// 根据标识得到配置信息
		String text = "";
		if(element!=null)
			text=element.getAttributeValue(atrName);
		return text;
	}
	/**
	 * 根据获取值 如catalog: doc#author
	 * @param catalog 参数以#为分隔符
	 * @return
	 */
	public String getValue(String  catalog)
	{
		catalog=StringUtils.checkNullString(catalog);
		Element element = rootElement;
		String tags[] =null;
		int len=0;
		if(!catalog.equals(""))
		{
			tags= catalog.split("#");
			len=tags.length;
		}
		try
		{	
			for (int i = 0; i < len; i++) {
				element = element.getChild(tags[i]);
				if(element==null)
					break;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		// 根据标识得到配置信息
		String text = "";
		if(element!=null)
			text=element.getText();
		return text;
	}
	
	public List<Element> getElement(String  catalog)
	{
		return  getElement(null,catalog);
	}
	/**
	 * 根据目录获取目录下element的list
	 * @param catalog
	 * @return
	 */
	public List<Element> getElement(Element p_element,String  catalog)
	{
		List list=null;
		catalog=StringUtils.checkNullString(catalog);
		Element element = rootElement;
		if(p_element==null)
			element = rootElement;
		else
			element = p_element;
/*		String tags[] =null;
		int len=0;
		if(!catalog.equals(""))
		{
			tags= catalog.split("#");
			len=tags.length;
		}*/
		try
		{	
/*			for (int i = 0; i < len; i++) {
				element = element.getChild(tags[i]);
				if(element==null)
					break;
			}*/
			list=element.getChildren(catalog);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		// 根据标识得到配置信息
		
		return list;
	}
	
	public List<Element> getChildren(String  catalog)
	{
		return  getChildren(null,catalog);
	}
	/**
	 * 根据目录获取目录下element的list
	 * @param catalog
	 * @return
	 */
	public List<Element> getChildren(Element p_element,String  catalog)
	{
		catalog=StringUtils.checkNullString(catalog);
		Element element = rootElement;
		if(p_element==null)
			element = rootElement;
		else
			element = p_element;
		String tags[] =null;
		int len=0;
		if(!catalog.equals(""))
		{
			tags= catalog.split("#");
			len=tags.length;
		}
		try
		{	
			for (int i = 0; i < len; i++) {
				element = element.getChild(tags[i]);
				if(element==null)
					break;
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		// 根据标识得到配置信息
		List list=null;
		if(element!=null)
			list=element.getChildren();
		return list;
	}	
	
	public static void main(String[] args)
	{
		
		StringBuffer string=new StringBuffer();
		
		string.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		string.append("<sql-group>");
		string.append("	<doc>");
		string.append("		<author tag='test'>作者</author>"); 
		string.append("		<date>时间</date>"); 
		string.append("		<description>Sql 描述</description>"); 		
		string.append("	</doc>");
		string.append("</sql-group>");
		
		XmlParser xml=new XmlParser(string.toString());
		String author=xml.getValue("doc#author");
		System.out.println(author);
		System.out.println(xml.getAttributeValue("doc#author","tag"));
		System.out.println(xml.getChildren(null,"doc#author").size());
		
	}
}
