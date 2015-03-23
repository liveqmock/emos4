package com.ultrapower.eoms.common.core.component.sla.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.ultrapower.eoms.common.core.component.sla.model.SlaTplProperty;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * 处理前台提交的关于规则属性的信息
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-1 下午04:02:07
 */
public class JTableParseRuleTplPropertyUtil {

	public static HashMap getSlaRuleTplPropertyPropertyData(String jtableXmlData){
		HashMap map = new HashMap();
		StringBuffer str = new StringBuffer(1024);
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		str.append("<root>");
		str.append(jtableXmlData);
		str.append("</root>");
		StringReader read = new StringReader(str.toString());
	    InputSource source = new InputSource(read);
	    SAXBuilder sb = new SAXBuilder();
	    Element rootElement=null;
	    try
	    {
		    Document doc = sb.build(source);
		    rootElement = doc.getRootElement();
	    }catch(Exception e)
	    {
	        e.printStackTrace();
	    }		
	    String del = rootElement.getChild("table").getAttributeValue("del");	
	    List rowList = rootElement.getChild("table").getChildren();
	    int rowListLen = 0;
	    if(rowList!=null)
	    {
	    	rowListLen = rowList.size();
	    }
	    List<SlaTplProperty> addSlaTplPropertyList = new ArrayList();//需要增加的数据
	    List<SlaTplProperty> modSlaTplPropertyList = new ArrayList();//需要修改的数据
	    List<String> delSlaTplPropertyPidList = new ArrayList();//需要删除的数据的主键
	    for(int i=0;i<rowListLen;i++)
	    {
	    	SlaTplProperty slaTplProperty = new SlaTplProperty();
	    	Element rowElement = (Element)rowList.get(i);
	    	//获取操作的类型
	    	String opt = rowElement.getAttributeValue("opt");
	    	if(opt.equals(""))
	    	{
	    		continue;
	    	}
	    	//获取xml中的数据
	    	String pid = StringUtils.checkNullString(rowElement.getChildText("pid"));
	    	String fieldid = StringUtils.checkNullString(rowElement.getChildText("fieldid"));
	    	String fieldname = StringUtils.checkNullString(rowElement.getChildText("fieldname"));
	    	long inputtype = NumberUtils.formatToLong(rowElement.getChildText("inputtype"));
	    	long inputvaluetype = NumberUtils.formatToLong(rowElement.getChildText("inputvaluetype"));
	    	long inputdatasourcetype = NumberUtils.formatToLong(rowElement.getChildText("inputdatasourcetype"));
	    	String indata = StringUtils.checkNullString(rowElement.getChildText("indata"));
	    	long status = NumberUtils.formatToLong(rowElement.getChildText("status"));
	    	long ordernum = NumberUtils.formatToLong(rowElement.getChildText("ordernum"));
	    	String describe = StringUtils.checkNullString(rowElement.getChildText("describe"));
	    	
	    	//对象赋参
	    	slaTplProperty.setPid(pid);
	    	slaTplProperty.setFieldid(fieldid);
	    	slaTplProperty.setFieldname(fieldname);
	    	slaTplProperty.setInputtype(inputtype);
	    	slaTplProperty.setInputvaluetype(inputvaluetype);
	    	slaTplProperty.setInputdatasourcetype(inputdatasourcetype);
	    	slaTplProperty.setIndata(indata);
	    	slaTplProperty.setStatus(status);
	    	slaTplProperty.setOrdernum(ordernum);
	    	slaTplProperty.setDescribe(describe);

	    	if(opt.equals("add"))
	    	{
	    		addSlaTplPropertyList.add(slaTplProperty);
	    	}
	    	else if(opt.equals("mod"))
	    	{
	    		modSlaTplPropertyList.add(slaTplProperty);
	    	}
	    	else
	    	{}
	    }
	    
	    if(!del.equals(""))
	    {
	    	String[] delArr = del.split(",");
	    	for(int i=0;i<delArr.length;i++)
	    	{
	    		delSlaTplPropertyPidList.add(delArr[i]);
	    	}
	    }
	    
	    map.put("add", addSlaTplPropertyList);
	    map.put("mod", modSlaTplPropertyList);
	    map.put("del", delSlaTplPropertyPidList);
	    return map;
	}
}
