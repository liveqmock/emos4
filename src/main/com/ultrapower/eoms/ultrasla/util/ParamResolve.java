package com.ultrapower.eoms.ultrasla.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.xml.XmlParser;

/**
 * @author SunHailong
 * @date 2011-11-02 下午02:27:40
 * @version
 */
public class ParamResolve {
	/**
	 * map转xml
	 * @param paramMap
	 * @return
	 */
	public static String mapThansToXml (Map paramMap) {
		if(paramMap == null) {
			return "";
		}
		StringBuffer xmlStr = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		Set keys = paramMap.keySet();
		Object[] keyObj = keys.toArray();
		String key;
		xmlStr.append("<paramxml>");
		for(int i=0;i<keyObj.length;i++) {
			key = (String) keyObj[i];
			xmlStr.append("<param key=\"");
			xmlStr.append(key);
			xmlStr.append("\" value=\"");
			xmlStr.append(paramMap.get(key));
			xmlStr.append("\">");
			xmlStr.append("</param>");
		}
		xmlStr.append("</paramxml>");
		return xmlStr.toString();
	}
	
	/**
	 * xml转map
	 * @param xmlStr
	 * @return
	 */
	public static HashMap<String, String> xmlThansToMap (String xmlStr) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		if(xmlStr == null || "".equals(xmlStr))
			return paramMap;
		XmlParser xml = new XmlParser(xmlStr, 0);
		List<Element> elementList = xml.getElement("param");
		int elementLen = 0;
		if(elementList != null) {
			elementLen = elementList.size();
		}
		Element element;
		for(int i=0 ; i<elementLen ; i++) {
			element = elementList.get(i);
			paramMap.put(element.getAttributeValue("key"), element.getAttributeValue("value"));
		}
		return paramMap;
	}
	
	public static void main(String[] args) {
		//map转xml
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("pid", "001");
		paramMap.put("loginname", "zhangsan");
		paramMap.put("fullname", "张三");
		String xmlStr = mapThansToXml(paramMap);
		System.out.println("xml格式：" + xmlStr);
		//xml转map
		HashMap<String, String> paramMapNew = xmlThansToMap(xmlStr);
		System.out.println(paramMapNew.get("pid"));
		System.out.println(paramMapNew.get("loginname"));
		System.out.println(paramMapNew.get("fullname"));
	}
}
