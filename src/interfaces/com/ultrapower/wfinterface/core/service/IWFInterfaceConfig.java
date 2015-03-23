package com.ultrapower.wfinterface.core.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.model.ConfigClient;
import com.ultrapower.wfinterface.core.model.ConfigMethod;
import com.ultrapower.wfinterface.core.model.WfiTempAttachment;
import com.ultrapower.wfinterface.core.model.WorkflowField;

/**
 * 用于接口过程中的XML格式字符串、DataTable以及WorkflowField对象列表之间转换的工具类。
 * @author BigMouse
 *
 */
public interface IWFInterfaceConfig
{
	/**
	 * 从配置文件中加载配置信息，默认路径在classes/wfinterface/*中
	 * @param configXmlPath 配置文件路径
	 */
	public void reflushConfiguration(String configXmlPath);
	
	/**
	 * 获取配置的服务方法Map
	 * @return 服务方法Map
	 */
	public Map<String, ConfigMethod> getMethodMap();
	public Map<String, ConfigClient> getClientMap();
	
	/**
	 * 从DataTable对象转换成XML格式字符串
	 * @param table DataTable对象
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return XML格式字符串
	 * @throws Exception
	 */
	public String formatTable2Xml(DataTable table, String className) throws Exception;
	
	/**
	 * 从XML格式字符串转换成DataTable对象
	 * @param xmlString XML格式字符串
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return DataTable对象
	 * @throws Exception
	 */
	public DataTable formatXml2Table(String xmlString, String className) throws Exception;
	
	/**
	 * 从DataTable对象传唤成WorkflowField对象列表
	 * @param table DataTable对象
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return WorkflowField对象列表
	 * @throws Exception
	 */
	public List<Map<String, WorkflowField>> formatTable2WFObj(DataTable table, String className) throws Exception;
	
	/**
	 * 从WorkflowField对象列表转换成Xml格式字符串
	 * @param wfFieldsList WorkflowField对象列表
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return Xml格式字符串
	 * @throws Exception 
	 */
	public String formatWFObj2Xml(List<Map<String, WorkflowField>> wfFieldsList, String className) throws Exception;
	
	/**
	 * 从WorkflowField对象列表转换成DataTable对象
	 * @param wfFieldsList WorkflowField对象列表
	 * @param methodName 方法名
	 * @param serviceName 服务名
	 * @return DataTable对象
	 * @throws Exception 
	 */
	public DataTable formatWFObj2Table(List<Map<String, WorkflowField>> wfFieldsList, String className) throws Exception;
	
	/**
	 * 从附件的Xml格式字符串转为WfiTempAttachment对象列表
	 * @param xmlString 附件的Xml格式字符串
	 * @return WfiTempAttachment对象列表
	 * @throws Exception
	 */
	public List<Attachment> formatAttachXml2AttachObj(String xmlString) throws Exception;
	
	public String formatAttachObj2AttachXml(List<WfiTempAttachment> attachs) throws Exception;
}
