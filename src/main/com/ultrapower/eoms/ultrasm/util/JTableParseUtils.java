package com.ultrapower.eoms.ultrasm.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.FieldInfo;
import com.ultrapower.eoms.ultrasm.model.ResProperty;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 10, 2010 2:51:44 PM
 * @descibe 
 */
public class JTableParseUtils {

	/**
	 * 对页面传进来的数据进行处理封装(资源的增删改信息)
	 * @param jtableXmlData
	 * @return
	 */
	public static HashMap getResPropertyData(String jtableXmlData)
	{
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
	    List<ResProperty> addResPropertyList = new ArrayList();//需要增加的数据
	    List<ResProperty> modResPropertyList = new ArrayList();//需要修改的数据
	    List delResPropertyPidList = new ArrayList();//需要删除的数据的主键
	    for(int i=0;i<rowListLen;i++)
	    {
	    	ResProperty resProperty = new ResProperty();
	    	Element rowElement = (Element)rowList.get(i);
	    	//获取操作的类型
	    	String opt = rowElement.getAttributeValue("opt");
	    	if(opt.equals(""))
	    	{
	    		continue;
	    	}
	    	//获取xml中的数据
	    	String pid = StringUtils.checkNullString(rowElement.getChildText("pid"));
	    	String fieldname = StringUtils.checkNullString(rowElement.getChildText("fieldname"));
	    	String fielddisplayvalue = StringUtils.checkNullString(rowElement.getChildText("fielddisplayvalue"));
	    	long intype = NumberUtils.formatToLong(rowElement.getChildText("intype"));
	    	long ordernum = NumberUtils.formatToLong(rowElement.getChildText("ordernum"));
	    	long invaluetype = NumberUtils.formatToLong(rowElement.getChildText("invaluetype"));
	    	long indatasourtype = NumberUtils.formatToLong(rowElement.getChildText("indatasourtype"));
	    	String indata = StringUtils.checkNullString(rowElement.getChildText("indata"));
	    	String remark = StringUtils.checkNullString(rowElement.getChildText("remark"));
	    	String creater = StringUtils.checkNullString(rowElement.getChildText("creater"));
	    	long createtime = NumberUtils.formatToLong(rowElement.getChildText("createtime"));
	    	//对象赋参
	    	resProperty.setPid(pid);
	    	resProperty.setFieldname(fieldname);
	    	resProperty.setFielddisplayvalue(fielddisplayvalue);
	    	resProperty.setIntype(intype);
	    	resProperty.setOrdernum(ordernum);
	    	resProperty.setInvaluetype(invaluetype);
	    	resProperty.setIndatasourtype(indatasourtype);
	    	resProperty.setIndata(indata);
	    	resProperty.setRemark(remark);
	    	resProperty.setCreater(creater);
	    	resProperty.setCreatetime(createtime);
	    	
	    	if(opt.equals("add"))
	    	{
	    		addResPropertyList.add(resProperty);
	    	}
	    	else if(opt.equals("mod"))
	    	{
	    		modResPropertyList.add(resProperty);
	    	}
	    	else
	    	{}
	    }
	    
	    if(!del.equals(""))
	    {
	    	String[] delArr = del.split(",");
	    	for(int i=0;i<delArr.length;i++)
	    	{
	    		delResPropertyPidList.add(delArr[i]);
	    	}
	    }
	    
	    map.put("add", addResPropertyList);
	    map.put("mod", modResPropertyList);
	    map.put("del", delResPropertyPidList);
	    return map;
	}
	
	/**
	 * 对页面传进来的数据进行处理封装(库表字段的增删改信息)
	 * @param jtableXmlData
	 * @param usePid
	 * @return
	 */
	public static HashMap getTableFieldData(String jtableXmlData)
	{
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
	    List<FieldInfo> addFieldList = new ArrayList();//需要增加的数据
	    List<FieldInfo> modFieldList = new ArrayList();//需要修改的数据
	    List delFieldPidList = new ArrayList();//需要删除的数据的主键
	    for(int i=0;i<rowListLen;i++)
	    {
	    	FieldInfo fieldInfo = new FieldInfo();
	    	Element rowElement = (Element)rowList.get(i);
	    	//获取操作的类型
	    	String opt = rowElement.getAttributeValue("opt");
	    	if(opt.equals(""))
	    	{
	    		continue;
	    	}
	    	//获取xml中的数据
	    	String pid = StringUtils.checkNullString(rowElement.getChildText("pid"));
	    	String field = StringUtils.checkNullString(rowElement.getChildText("field"));
	    	String fieldname = StringUtils.checkNullString(rowElement.getChildText("fieldname"));
	    	String fieldtype = StringUtils.checkNullString(rowElement.getChildText("fieldtype"));
	    	long length = NumberUtils.formatToLong(rowElement.getChildText("length"));
	    	long precision = NumberUtils.formatToLong(rowElement.getChildText("precision"));
	    	long isrequired = NumberUtils.formatToLong(rowElement.getChildText("isrequired"));
	    	String defaultvalue = StringUtils.checkNullString(rowElement.getChildText("defaultvalue"));
	    	String dtcode = StringUtils.checkNullString(rowElement.getChildText("dtcode")); 
	    	String remark = StringUtils.checkNullString(rowElement.getChildText("remark"));
	    	String creater = StringUtils.checkNullString(rowElement.getChildText("creater"));
	    	long createtime = NumberUtils.formatToLong(rowElement.getChildText("createtime"));
	    	//对象赋参
	    	fieldInfo.setPid(pid);
	    	fieldInfo.setFieldname(fieldname);
	    	fieldInfo.setField(field);
	    	fieldInfo.setFieldtype(fieldtype);
	    	fieldInfo.setLength(length);
	    	fieldInfo.setPrecision(precision);
	    	fieldInfo.setIsrequired(isrequired);
	    	fieldInfo.setDefaultvalue(defaultvalue);
	    	fieldInfo.setDtcode(dtcode);
	    	fieldInfo.setRemark(remark);
	    	fieldInfo.setCreater(creater);
	    	fieldInfo.setCreatetime(createtime);
	    	
	    	if(opt.equals("add")){
	    		addFieldList.add(fieldInfo);
	    	}else if(opt.equals("mod")){
	    		modFieldList.add(fieldInfo);
	    	}else{}
	    }
	    
	    if(!del.equals("")){
	    	String[] delArr = del.split(",");
	    	for(int i=0;i<delArr.length;i++){
	    		delFieldPidList.add(delArr[i]);
	    	}
	    }
	    
	    map.put("add", addFieldList);
	    map.put("mod", modFieldList);
	    map.put("del", delFieldPidList);
	    return map;
	}
}
