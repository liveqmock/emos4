package com.ultrapower.wfinterface.core.manager;

import java.io.StringReader;
import org.xml.sax.InputSource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.ConfigCallParameter;
import com.ultrapower.wfinterface.core.model.ConfigClient;
import com.ultrapower.wfinterface.core.model.ConfigField;
import com.ultrapower.wfinterface.core.model.ConfigMethod;
import com.ultrapower.wfinterface.core.model.InterfaceFieldType;
import com.ultrapower.wfinterface.core.model.WfiTempAttachment;
import com.ultrapower.wfinterface.core.model.WorkflowField;
import com.ultrapower.wfinterface.core.service.IWFInterfaceConfig;

/**
 * 用于接口过程中的XML格式字符串、DataTable以及WorkflowField对象列表之间转换的工具类。
 * @author BigMouse
 *
 */
public class DefaultWFInterfaceConfig implements IWFInterfaceConfig
{
	protected static Map<String, ConfigMethod> methodMap = new HashMap<String, ConfigMethod>();
	protected static Map<String, ConfigClient> clientMap = new HashMap<String, ConfigClient>();

	/**
	 * 从DataTable对象转换成XML格式字符串
	 * @param table DataTable对象
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return XML格式字符串
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String formatTable2Xml(DataTable table, String className) throws Exception
	{
		
		List<ConfigField> fields = clientMap.get(className).getFields();
		
		StringBuilder xmlBuider = new StringBuilder();
		
		xmlBuider.append("<opDetail>");
		List<DataRow> rowList = table.getRowList();
		for(DataRow row : rowList)
		{
			if(fields.size() > 0) xmlBuider.append("<recordInfo>");
			
			for(ConfigField field : fields)
			{
				String value = "";
				value=row.getString(field.getFieldName().toUpperCase());
				value = checkField(field, value);
				if(field.getDataType() == InterfaceFieldType.ENUM)
				{
					if(field.getDicType() == 2)
					{
						String dicString = field.getDicData();
						if(dicString.endsWith(";")) dicString = dicString.substring(0, dicString.length()-1);
						String[] dics = dicString.split(";");
						for(String dic : dics)
						{
							String[] dicarr = dic.split(":");
							if(dicarr[0].trim().equals(value.trim()))
							{
								value = dicarr[1].trim();
							}
						}
					}
				}
				else if(field.getDataType() == InterfaceFieldType.DATETIME)
				{
					if(value.equals("$sys_date$"))
					{
						value = String.valueOf(System.currentTimeMillis()/1000);
					}
					else if(!value.equals(""))
					{
						value = TimeUtils.formatIntToDateString(Long.parseLong(value));
					}
				}
				
				xmlBuider.append("<fieldInfo>");
				xmlBuider.append("<fieldChName>" + field.getFieldText() + "</fieldChName>");
				xmlBuider.append("<fieldEnName>" + field.getXmlColName() + "</fieldEnName>");
				if(field.getDataType() == InterfaceFieldType.STRING || field.getDataType() == InterfaceFieldType.ENUM)
				{
					xmlBuider.append("<fieldContent><![CDATA[" + value + "]]></fieldContent>");
				}
				else
				{
					xmlBuider.append("<fieldContent>" + value + "</fieldContent>");
				}
				xmlBuider.append("</fieldInfo>");
			}
			
			if(fields.size() > 0) xmlBuider.append("</recordInfo>");
		}
		xmlBuider.append("</opDetail>");
		return xmlBuider.toString();
	}
	
	/**
	 * 从XML格式字符串转换成DataTable对象
	 * @param xmlString XML格式字符串
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return DataTable对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public DataTable formatXml2Table(String xmlString, String className) throws Exception
	{
		SAXBuilder builder = new SAXBuilder();
	    StringReader read = new StringReader(xmlString);
	    InputSource source = new InputSource(read);
	    
	    Document doc = builder.build(source);
		Element rootEle = doc.getRootElement();
		DataTable table = new DataTable("");
		List<Element> recordEleList = rootEle.getChildren("recordInfo");
		for(Element recordEle : recordEleList)
		{
			List<Element> fieldEleList = recordEle.getChildren("fieldInfo");
			DataRow row = new DataRow();
			for(Element fieldEle : fieldEleList)
			{
				String enName = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "fieldEnName", true, "");
				String value = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "fieldContent", false, "");
				row.put(enName, value);
			}
			table.putDataRow(row);
		}
		
		return table;
	}
	
	/**
	 * 从DataTable对象传唤成WorkflowField对象列表
	 * @param table DataTable对象
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return WorkflowField对象列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, WorkflowField>> formatTable2WFObj(DataTable table, String className) throws Exception
	{
		List<ConfigField> fields = methodMap.get(className).getFields();
		
		List<Map<String, WorkflowField>> fieldsList = new ArrayList<Map<String,WorkflowField>>();
		
		List<DataRow> rowList = table.getRowList();
		for(DataRow row : rowList)
		{
			Map<String, WorkflowField> fieldMap = new HashMap<String, WorkflowField>();
			
			for(ConfigField field : fields)
			{
				String value = "";
				value=row.getString(field.getXmlColName().toUpperCase());
				value = checkField(field, value);
				
				WorkflowField wfField = new WorkflowField();
				wfField.setFieldName(field.getFieldName());
				wfField.setFieldText(field.getFieldText());
				wfField.setDbColName(field.getDbColName());
				wfField.setDataType(field.getDataType());
				
				if(field.getDataType() == InterfaceFieldType.ENUM)
				{
					if(field.getDicType() == 2)
					{
						String dicString = field.getDicData();
						if(dicString.endsWith(";")) dicString = dicString.substring(0, dicString.length()-1);
						String[] dics = dicString.split(";");
						for(String dic : dics)
						{
							String[] dicarr = dic.split(":");
							if(dicarr[1].trim().equals(value.trim()))
							{
								value = dicarr[0].trim();
							}
						}
					}
				}
				else if(field.getDataType() == InterfaceFieldType.DATETIME)
				{
					if(value.equals("$sys_date$"))
					{
						value = String.valueOf(System.currentTimeMillis()/1000);
					}
					else if(!value.equals(""))
					{
						value = String.valueOf(TimeUtils.formatDateStringToInt(value));
					}
				}
				
				wfField.setValue(value);
				fieldMap.put(field.getFieldName(), wfField);
			}
			fieldsList.add(fieldMap);
		}
		return fieldsList;
	}

	/**
	 * 从WorkflowField对象列表转换成Xml格式字符串
	 * @param wfFieldsList WorkflowField对象列表
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return Xml格式字符串
	 * @throws Exception 
	 */
	public String formatWFObj2Xml(List<Map<String, WorkflowField>> wfFieldsList, String className) throws Exception
	{
		List<ConfigField> fields = clientMap.get(className).getFields();
		
		StringBuilder xmlBuider = new StringBuilder();
		
		xmlBuider.append("<opDetail>");
		
		for(Map<String, WorkflowField> wfFields : wfFieldsList)
		{
			if(fields.size() > 0)  xmlBuider.append("<recordInfo>");
			for(ConfigField field : fields)
			{
				String value = "";
				if(wfFields.containsKey(field.getFieldName()))
				{
					value = StringUtils.checkNullString(wfFields.get(field.getFieldName()).getValue());
				}
				value = checkField(field, value);
				
				xmlBuider.append("<fieldInfo>");
				xmlBuider.append("<fieldChName>" + field.getFieldText() + "</fieldChName>");
				xmlBuider.append("<fieldEnName>" + field.getXmlColName() + "</fieldEnName>");
				if(field.getDataType() == InterfaceFieldType.STRING || field.getDataType() == InterfaceFieldType.ENUM)
				{
					if(field.getDataType() == 6 && field.getDicType() == 2)
					{
						String dicString = field.getDicData();
						if(dicString.endsWith(";")) dicString = dicString.substring(0, dicString.length()-1);
						String[] dics = dicString.split(";");
						for(String dic : dics)
						{
							String[] dicarr = dic.split(":");
							if(dicarr[0].trim().equals(value.trim()))
							{
								value = dicarr[1].trim();
							}
						}
					}
					xmlBuider.append("<fieldContent><![CDATA[" + value + "]]></fieldContent>");
				}
				else if(field.getDataType() == InterfaceFieldType.DATETIME)
				{
					if(value.equals("$sys_date$"))
					{
						value = String.valueOf(System.currentTimeMillis()/1000);
					}
					else if(!value.equals(""))
					{
						value = TimeUtils.formatIntToDateString(Long.parseLong(value));
					}
					xmlBuider.append("<fieldContent>" + value + "</fieldContent>");
				}
				else
				{
					xmlBuider.append("<fieldContent>" + value + "</fieldContent>");
				}
				xmlBuider.append("</fieldInfo>");
			}
			if(fields.size() > 0) xmlBuider.append("</recordInfo>");
		}
		
		xmlBuider.append("</opDetail>");
		return xmlBuider.toString();
	}

	/**
	 * 从WorkflowField对象列表转换成DataTable对象
	 * @param wfFieldsList WorkflowField对象列表
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return DataTable对象
	 * @throws Exception 
	 */
	public DataTable formatWFObj2Table(List<Map<String, WorkflowField>> wfFieldsList, String className) throws Exception
	{
		DataTable table = new DataTable("");
		
		for(Map<String, WorkflowField> wfFields : wfFieldsList)
		{
			DataRow row = new DataRow();
			
			for(WorkflowField field : wfFields.values())
			{
				String value = "";
				value = StringUtils.checkNullString(wfFields.get(field.getFieldName()).getValue());
				row.put(field.getFieldName(), value);
			}
			table.putDataRow(row);
		}
		return table;
	}
	
	@SuppressWarnings("unchecked")
	public List<Attachment> formatAttachXml2AttachObj(String xmlString) throws Exception
	{
		List<Attachment> attachs = null;
		if(xmlString != null && !xmlString.equals(""))
		{
			SAXBuilder builder = new SAXBuilder();
		    StringReader read = new StringReader(xmlString);
		    InputSource source = new InputSource(read);
		    
		    Document doc = builder.build(source);
			Element rootEle = doc.getRootElement();
			
			attachs = new ArrayList<Attachment>();
			List<Element> recordEleList = rootEle.getChildren("attachInfo");
			for(Element recordEle : recordEleList)
			{
				Attachment attach = new Attachment();
				attach.setName(loadNodeValue(recordEle, KeyType.CHILDTEXT, "attachName", true, ""));
				attach.setRemark(loadNodeValue(recordEle, KeyType.CHILDTEXT, "attachURL", true, ""));
				attachs.add(attach);
			}
		}
		
		return attachs;
	}
	
	public String formatAttachObj2AttachXml(List<WfiTempAttachment> attachs) throws Exception
	{
		StringBuilder xmlBuider = new StringBuilder();
		
		xmlBuider.append("<attachRef>");
		
		for(WfiTempAttachment attach : attachs)
		{
			xmlBuider.append("<attachInfo>");
			xmlBuider.append("<attachName>" + attach.getAttachname() + "</attachName>");
			xmlBuider.append("<attachURL>" + attach.getAttachurl() + "</attachURL>");
			xmlBuider.append("<attachLength></attachLength>");
			xmlBuider.append("</attachInfo>");
		}
		
		xmlBuider.append("</attachRef>");
		
		return xmlBuider.toString();
	}

	/**
	 * 从配置文件中加载配置信息，默认路径在classes/wfinterface/*中
	 * @param configXmlPath 配置文件路径
	 */
	public void reflushConfiguration(String configXmlPath)
	{
		if(configXmlPath == null)
		{
			configXmlPath = this.getClass().getResource("").getPath();
			configXmlPath = configXmlPath.substring(0, configXmlPath.length() - 40) + "wfinterface";
		}
		
		//处理所有的服务端配置文件
		String serviceConfigXmlPath = configXmlPath + File.separator + "services";
		readServiceConfigFolder(serviceConfigXmlPath);
		
		//处理所有的客户端配置文件
		String clientConfigXmlPath = configXmlPath + File.separator + "clients";
		readClientConfigFolder(clientConfigXmlPath);
	}

	private void readServiceConfigFolder(String serviceConfigXmlPath)
	{
		File serviceDir = new File(serviceConfigXmlPath);
		File[] serviceConfigXmls = serviceDir.listFiles();
		
		for(File configXml : serviceConfigXmls)
		{
			if(configXml.getName().toLowerCase().endsWith(".xml"))
			{
					try
					{
						loadServiceConfigXml(configXml);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
			}
			else if(configXml.isDirectory())
			{
				readServiceConfigFolder(configXml.getPath());
			}
		}
	}

	private void readClientConfigFolder(String clientConfigXmlPath)
	{
		File clientDir = new File(clientConfigXmlPath);
		File[] clientConfigXmls = clientDir.listFiles();
		
		for(File configXml : clientConfigXmls)
		{
			if(configXml.getName().toLowerCase().endsWith(".xml"))
			{
					try
					{
						loadClientConfigXml(configXml);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
			}
			else if(configXml.isDirectory())
			{
				readClientConfigFolder(configXml.getPath());
			}
		}
	}
	
	/**
	 * 判断字段的值是否合法
	 * @param field 配置的字段
	 * @param value 字段值
	 * @throws Exception
	 */
	private String checkField(ConfigField field, String value) throws Exception
	{
		if(!field.isNull() && value.equals("") && field.getDefaultValue().equals(""))
		{
			throw new Exception("字段" + field.getFieldName() + "的值不能为空！");
		}
		
		if(field.getDataType() == InterfaceFieldType.STRING && field.getLength() > 0 && value.length() > field.getLength())
		{
			throw new Exception("字段" + field.getFieldName() + "的值超过限定长度" + field.getLength() + "！");
		}
		if(field.getDataType() == InterfaceFieldType.INTEGER && field.getLength() > 0 && Integer.parseInt(value) > field.getLength())
		{
			throw new Exception("字段" + field.getFieldName() + "的值超过限定最大值" + field.getLength() + "！");
		}
		
		if(value.equals("")) value = field.getDefaultValue();
		return value;
	}
	
	/**
	 * 读取单个服务配置文件
	 * @param xmlFile 服务配置文件
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void loadServiceConfigXml(File xmlFile) throws Exception
	{
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(xmlFile);
		Element serivceEle = doc.getRootElement();
		String serviceName = serivceEle.getAttributeValue("name");
		List<Element> methodEleList = serivceEle.getChildren("method");
		for(Element methodEle : methodEleList)
		{
			ConfigMethod method = loadServiceMethod(methodEle);
			method.setServiceName(serviceName);
			methodMap.put(method.getImplClassPath(), method);
		}
	}
	
	/**
	 * 读取单个客户端配置文件
	 * @param xmlFile 服务配置文件
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void loadClientConfigXml(File xmlFile) throws Exception
	{
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(xmlFile);
		Element clientEle = doc.getRootElement();
		String clientName = clientEle.getAttributeValue("name");
		List<Element> methodEleList = clientEle.getChildren("method");
		for(Element methodEle : methodEleList)
		{
			ConfigClient method = loadClientMethod(methodEle);
			method.setClientName(clientName);
			clientMap.put(method.getImplClassPath(), method);
		}
	}
	
	/**
	 * 读取方法的属性
	 * @param methodEle 方法的Element
	 * @return 方法对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected ConfigMethod loadServiceMethod(Element methodEle) throws Exception
	{
		ConfigMethod method = new ConfigMethod();
		String methodName = loadNodeValue(methodEle, KeyType.ATTRIBUTE, "name", true, "");
		method.setMethodName(methodName);

		String baseSchema = loadNodeValue(methodEle, KeyType.ATTRIBUTE, "baseschema", true, "");
		method.setBaseSchema(baseSchema);
		
		String implClassPath = loadNodeValue(methodEle, KeyType.ATTRIBUTE, "class", true, "");
		method.setImplClassPath(implClassPath);
		
		String operateCode = loadNodeValue(methodEle.getChild("operate"), KeyType.ATTRIBUTE, "code", true, "");
		method.setOperateCode(operateCode);
		
		String actionId = loadNodeValue(methodEle.getChild("operate"), KeyType.ATTRIBUTE, "actionid", true, "");
		method.setOperateActonid(actionId);
		
		String creator = loadNodeValue(methodEle.getChild("operate"), KeyType.ATTRIBUTE, "creator", true, "");
		method.setCreator(creator);
		
		String operateText = loadNodeValue(methodEle, KeyType.CHILDTEXT, "operate", true, "");
		method.setOperateText(operateText);
		
		List<Element> fieldEleList = methodEle.getChild("fields").getChildren("field");
		for(Element fieldEle : fieldEleList)
		{
			ConfigField field = loadField(fieldEle);
			method.getFields().add(field);
		}
		return method;
	}
	
	/**
	 * 读取方法的属性
	 * @param methodEle 方法的Element
	 * @return 方法对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected ConfigClient loadClientMethod(Element methodEle) throws Exception
	{
		ConfigClient method = new ConfigClient();
		String methodName = loadNodeValue(methodEle, KeyType.ATTRIBUTE, "name", true, "");
		method.setMethodName(methodName);
		
		String implClassPath = loadNodeValue(methodEle, KeyType.ATTRIBUTE, "class", true, "");
		method.setImplClassPath(implClassPath);
		
		Element eleService = methodEle.getChild("webservice");
		
		String serviceAddress = loadNodeValue(eleService, KeyType.ATTRIBUTE, "address", true, "");
		method.setServiceAddress(serviceAddress);
		
		String operate = loadNodeValue(eleService, KeyType.ATTRIBUTE, "operate", true, "");
		method.setServiceOperateName(operate);
		
		String namespace = loadNodeValue(eleService, KeyType.ATTRIBUTE, "namespace", false, "");
		method.setServiceNameSpace(namespace);
		
		List<Element> parasEle = eleService.getChildren("parameter");
		List<ConfigCallParameter> paras = new ArrayList<ConfigCallParameter>();
		for(Element paraEle : parasEle)
		{
			String paraName = loadNodeValue(paraEle, KeyType.ATTRIBUTE, "name", true, "");
			String dataType = loadNodeValue(paraEle, KeyType.ATTRIBUTE, "datatype", true, "");
			String dicdata = loadNodeValue(paraEle, KeyType.ATTRIBUTE, "dicdata", false, "");
			String paraType = loadNodeValue(paraEle, KeyType.ATTRIBUTE, "argtype", true, "");
			String paraMode = loadNodeValue(paraEle, KeyType.ATTRIBUTE, "mode", true, "");
			ConfigCallParameter para = new ConfigCallParameter(paraName, Integer.valueOf(dataType), ConfigCallParameter.toType(paraType), dicdata, ConfigCallParameter.toMode(paraMode));
			paras.add(para);
		}
		method.setParameters(paras);
		
		String querySql = loadNodeValue(methodEle, KeyType.CHILDTEXT, "querysql", true, "");
		method.setQuerySql(querySql);
		
		String updateSql = loadNodeValue(methodEle, KeyType.CHILDTEXT, "updatesql", false, "");
		method.setUpdateSql(updateSql);
		
		String exceptionSql = loadNodeValue(methodEle, KeyType.CHILDTEXT, "exceptionsql", false, "");
		method.setExceptionSql(exceptionSql);
		
		List<Element> fieldEleList = methodEle.getChild("opdetail").getChildren("field");
		for(Element fieldEle : fieldEleList)
		{
			ConfigField field = loadField(fieldEle);
			method.getFields().add(field);
		}
		return method;
	}
	
	/**
	 * 读取字段的属性
	 * @param fieldEle 字段的Element
	 * @return 字段对象
	 * @throws Exception
	 */
	protected ConfigField loadField(Element fieldEle) throws Exception
	{
		ConfigField field = new ConfigField();
		String fieldName = loadNodeValue(fieldEle, KeyType.ATTRIBUTE, "name", true, "");
		field.setFieldName(fieldName);
		
		String fieldText = loadNodeValue(fieldEle, KeyType.ATTRIBUTE, "text", false, "");
		field.setFieldText(StringUtils.checkNullString(fieldText));
		
		String xmlColName = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "xml-colname", true, "");
		field.setXmlColName(xmlColName);
		
		String dbColName = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "db-colname", true, "");
		field.setDbColName(dbColName);
		
		String dataType = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "datatype", true, "4");
		field.setDataType(Integer.parseInt(dataType));
		
		String dicType = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "dictype", false, "0");
		field.setDicType(Integer.parseInt(dicType));
		
		String dicData = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "dicdata", false, "");
		field.setDicData(dicData);
		
		String length = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "length", false, "0");
		field.setLength(Integer.parseInt(length));
		
		String isNull = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "isnull", false, "true");
		field.setNull(Boolean.parseBoolean(isNull));
		
		String defaultValue = loadNodeValue(fieldEle, KeyType.CHILDTEXT, "defaultvalue", false, "");
		field.setDefaultValue(defaultValue);
		
		return field;
	}
	
	/**
	 * 根据参数在Element中获取对应的值
	 * @param ele Element
	 * @param type 获取类型，是属性还是子节点
	 * @param key 获取的属性或者子节点的名称
	 * @param require 是否必填
	 * @param defaultValue 默认值
	 * @return 获取到的值
	 * @throws Exception
	 */
	private String loadNodeValue(Element ele, KeyType type, String key, boolean require, String defaultValue) throws Exception
	{
		String value = StringUtils.checkNullString(defaultValue);
		String ele_String = "";
		try
		{
			if(type.equals(KeyType.ATTRIBUTE)) ele_String = ele.getAttributeValue(key);
			else ele_String = ele.getChildTextTrim(key);
		}
		catch (Exception e)
		{
			if(require && value.equals(""))
			{
				throw e;
			}
		}
		
		if(require && StringUtils.checkNullString(ele_String).equals("") && value.equals(""))
			throw new Exception((type.equals(KeyType.ATTRIBUTE)?"属性":"子节点") + key + "的值不能为空！");
		
		if(!StringUtils.checkNullString(ele_String).equals("")) value = ele_String;
		return value;
	}
	
	private enum KeyType {ATTRIBUTE, CHILDTEXT}

	public Map<String, ConfigMethod> getMethodMap()
	{
		return methodMap;
	}

	public Map<String, ConfigClient> getClientMap()
	{
		return clientMap;
	}
}
