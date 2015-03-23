package com.ultrapower.wfinterface.core.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.wfinterface.core.manager.DefaultWFInterfaceConfig;
import com.ultrapower.wfinterface.core.model.ConfigCallParameter;
import com.ultrapower.wfinterface.core.model.ConfigClient;
import com.ultrapower.wfinterface.core.model.ConfigField;
import com.ultrapower.wfinterface.core.model.ConfigMethod;
import com.ultrapower.wfinterface.core.model.WfiTempAttachment;
import com.ultrapower.wfinterface.core.model.WorkflowField;
import com.ultrapower.wfinterface.core.service.IWFInterfaceConfig;

import junit.framework.TestCase;

public class WFInterfaceConfigTest extends TestCase
{
	/**
	 *	<opDetail>
	 *		<recordInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段1（字符串）</fieldChName>
	 *				<fieldEnName>Test1_field1</fieldEnName>
	 *				<fieldContent><![CDATA[测试1 字段1的值<hehe /> / \ ~!@#$%^&*()]]></fieldContent>
	 *			</fieldInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段2（数字）</fieldChName>
	 *				<fieldEnName>Test1_field2</fieldEnName>
	 *				<fieldContent>11</fieldContent>
	 *			</fieldInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段3（字典）</fieldChName>
	 *				<fieldEnName>Test1_field3</fieldEnName>
	 *				<fieldContent><![CDATA[一般]]></fieldContent>
	 *			</fieldInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段4（时间）</fieldChName>
	 *				<fieldEnName>Test1_field4</fieldEnName>
	 *				<fieldContent>2011-07-07 12:23:34</fieldContent>
	 *			</fieldInfo>
	 *		</recordInfo>
	 *		<recordInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段1（字符串）</fieldChName>
	 *				<fieldEnName>Test1_field1</fieldEnName>
	 *				<fieldContent><![CDATA[测试2 字段1的值<hehe /> / \ ~!@#$%^&*()]]></fieldContent>
	 *			</fieldInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段2（数字）</fieldChName>
	 *				<fieldEnName>Test1_field2</fieldEnName>
	 *				<fieldContent>12</fieldContent>
	 *			</fieldInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段3（字典）</fieldChName>
	 *				<fieldEnName>Test1_field3</fieldEnName>
	 *				<fieldContent><![CDATA[二般]]></fieldContent>
	 *			</fieldInfo>
	 *			<fieldInfo>
	 *				<fieldChName>测试1 字段4（时间）</fieldChName>
	 *				<fieldEnName>Test1_field4</fieldEnName>
	 *				<fieldContent>2011-07-07 21:32:43</fieldContent>
	 *			</fieldInfo>
	 *		</recordInfo>
	 *	</opDetail>
	 */
	private String expectedXmlString = "<opDetail><recordInfo><fieldInfo><fieldChName>测试1 字段1（字符串）</fieldChName><fieldEnName>Test1_field1</fieldEnName><fieldContent><![CDATA[测试1 字段1的值<hehe /> / \\ ~!@#$%^&*()]]></fieldContent></fieldInfo><fieldInfo><fieldChName>测试1 字段2（数字）</fieldChName><fieldEnName>Test1_field2</fieldEnName><fieldContent>11</fieldContent></fieldInfo><fieldInfo><fieldChName>测试1 字段3（字典）</fieldChName><fieldEnName>Test1_field3</fieldEnName><fieldContent><![CDATA[一般]]></fieldContent></fieldInfo><fieldInfo><fieldChName>测试1 字段4（时间）</fieldChName><fieldEnName>Test1_field4</fieldEnName><fieldContent>2011-07-07 12:23:34</fieldContent></fieldInfo></recordInfo><recordInfo><fieldInfo><fieldChName>测试1 字段1（字符串）</fieldChName><fieldEnName>Test1_field1</fieldEnName><fieldContent><![CDATA[测试2 字段1的值<hehe /> / \\ ~!@#$%^&*()]]></fieldContent></fieldInfo><fieldInfo><fieldChName>测试1 字段2（数字）</fieldChName><fieldEnName>Test1_field2</fieldEnName><fieldContent>12</fieldContent></fieldInfo><fieldInfo><fieldChName>测试1 字段3（字典）</fieldChName><fieldEnName>Test1_field3</fieldEnName><fieldContent><![CDATA[二般]]></fieldContent></fieldInfo><fieldInfo><fieldChName>测试1 字段4（时间）</fieldChName><fieldEnName>Test1_field4</fieldEnName><fieldContent>2011-07-07 21:32:43</fieldContent></fieldInfo></recordInfo></opDetail>";
	private String expectedAttachXmlString = "<attachRef><attachInfo><attachName>file1</attachName><attachURL>ftp://aaa:bbb@localhost/test/file1.doc</attachURL><attachLength>200</attachLength></attachInfo><attachInfo><attachName>file2</attachName><attachURL>ftp://ccc:ddd@localhost/test/file2.doc</attachURL><attachLength>200</attachLength></attachInfo></attachRef>";
	
	private DataTable expectedInputTable = new DataTable("");
	private DataTable expectedOutputTable = new DataTable("");
	
	private List<Map<String, WorkflowField>> expectedFieldsList = new ArrayList<Map<String,WorkflowField>>();
	private List<WfiTempAttachment> expectedAttachList = new ArrayList<WfiTempAttachment>();
	
	private Map<String, ConfigMethod> methodMap = new HashMap<String, ConfigMethod>();
	private Map<String, ConfigClient> clientMap = new HashMap<String, ConfigClient>();

	public void testFormatTable2Xml()
	{
		initOutputDataTable();
		String actualXmlString = "";
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		try
		{
			actualXmlString = config.formatTable2Xml(expectedOutputTable, "com.ultrapower.wfinterface.core.test.TestClientImpl");
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
		assertEquals(expectedXmlString, actualXmlString);
	}

	@SuppressWarnings("unchecked")
	public void testFormatXml2Table()
	{
		initInputDataTable();
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		DataTable actualTable = null;
		try
		{
			actualTable = config.formatXml2Table(expectedXmlString, "com.ultrapower.wfinterface.core.test.TestServiceImpl");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(actualTable);
		
		List<DataRow> expectedRowList = expectedInputTable.getRowList();
		List<DataRow> actualRowList = actualTable.getRowList();
		assertEquals(2, actualRowList.size());
		
		DataRow expectedRow1 = expectedRowList.get(0);
		DataRow actualRow1 = actualRowList.get(0);
		assertEquals(expectedRow1.getString("Test1_field1"), actualRow1.getString("Test1_field1"));
		assertEquals(expectedRow1.getString("Test1_field2"), actualRow1.getString("Test1_field2"));
		assertEquals(expectedRow1.getString("Test1_field3"), actualRow1.getString("Test1_field3"));
		assertEquals(expectedRow1.getString("Test1_field4"), actualRow1.getString("Test1_field4"));
		
		DataRow expectedRow2 = expectedRowList.get(1);
		DataRow actualRow2 = actualRowList.get(1);
		assertEquals(expectedRow2.getString("Test1_field1"), actualRow2.getString("Test1_field1"));
		assertEquals(expectedRow2.getString("Test1_field2"), actualRow2.getString("Test1_field2"));
		assertEquals(expectedRow2.getString("Test1_field3"), actualRow2.getString("Test1_field3"));
		assertEquals(expectedRow2.getString("Test1_field4"), actualRow2.getString("Test1_field4"));
	}

	public void testFormatTable2WFObj()
	{
		initFieldsList();
		initInputDataTable();
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		
		List<Map<String, WorkflowField>> actualFieldsList = null;
		try
		{
			actualFieldsList = config.formatTable2WFObj(expectedInputTable, "com.ultrapower.wfinterface.core.test.TestServiceImpl");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertEquals(expectedFieldsList.size(), actualFieldsList.size());
		
		Map<String, WorkflowField> expectedFields1 = expectedFieldsList.get(0);
		Map<String, WorkflowField> expectedFields2 = expectedFieldsList.get(1);
		Map<String, WorkflowField> actualFields1 = actualFieldsList.get(0);
		Map<String, WorkflowField> actualFields2 = actualFieldsList.get(1);
		
		assertEquals("field1", expectedFields1.size(), actualFields1.size());
		assertEquals("field2", expectedFields2.size(), actualFields2.size());
		
		for(WorkflowField expectedField : expectedFields1.values())
		{
			WorkflowField actualField = null;
			for(WorkflowField field : actualFields1.values())
			{
				if(field.getFieldName().equals(expectedField.getFieldName()))
				{
					actualField = field;
				}
			}
			assertEquals("field1." + expectedField.getFieldName(), expectedField.getFieldName(), actualField.getFieldName());
			assertEquals("field1." + expectedField.getFieldName(), expectedField.getFieldText(), actualField.getFieldText());
			assertEquals("field1." + expectedField.getFieldName(), expectedField.getDbColName(), actualField.getDbColName());
			assertEquals("field1." + expectedField.getFieldName(), expectedField.getDataType(), actualField.getDataType());
			assertEquals("field1." + expectedField.getFieldName(), expectedField.getValue(), actualField.getValue());
		}
		for(WorkflowField expectedField : expectedFields2.values())
		{
			WorkflowField actualField = null;
			for(WorkflowField field : actualFields2.values())
			{
				if(field.getFieldName().equals(expectedField.getFieldName()))
				{
					actualField = field;
				}
			}
			assertEquals("field2." + expectedField.getFieldName(), expectedField.getFieldName(), actualField.getFieldName());
			assertEquals("field2." + expectedField.getFieldName(), expectedField.getFieldText(), actualField.getFieldText());
			assertEquals("field2." + expectedField.getFieldName(), expectedField.getDbColName(), actualField.getDbColName());
			assertEquals("field2." + expectedField.getFieldName(), expectedField.getDataType(), actualField.getDataType());
			assertEquals("field2." + expectedField.getFieldName(), expectedField.getValue(), actualField.getValue());
		}
	}

	public void testFormatWFObj2Xml()
	{
		initFieldsList();
		String actualXmlString = "";
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		try
		{
			actualXmlString = config.formatWFObj2Xml(expectedFieldsList, "com.ultrapower.wfinterface.core.test.TestClientImpl");
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
		
		System.out.println(expectedXmlString);
		System.out.println(actualXmlString);
		assertEquals(expectedXmlString, actualXmlString);
	}

	@SuppressWarnings("unchecked")
	public void testFormatWFObj2Table()
	{
		initFieldsList();
		initOutputDataTable();
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		DataTable actualTable = null;
		try
		{
			actualTable = config.formatWFObj2Table(expectedFieldsList, "com.ultrapower.wfinterface.core.test.TestClientImpl");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(actualTable);
		
		List<DataRow> expectedRowList = expectedOutputTable.getRowList();
		List<DataRow> actualRowList = actualTable.getRowList();
		assertEquals(2, actualRowList.size());
		
		DataRow expectedRow1 = expectedRowList.get(0);
		DataRow actualRow1 = actualRowList.get(0);
		assertEquals(expectedRow1.getString("field1"), actualRow1.getString("field1"));
		assertEquals(expectedRow1.getString("field2"), actualRow1.getString("field2"));
		assertEquals(expectedRow1.getString("field3"), actualRow1.getString("field3"));
		assertEquals(expectedRow1.getString("field4"), actualRow1.getString("field4"));
		
		DataRow expectedRow2 = expectedRowList.get(1);
		DataRow actualRow2 = actualRowList.get(1);
		assertEquals(expectedRow2.getString("field1"), actualRow2.getString("field1"));
		assertEquals(expectedRow2.getString("field2"), actualRow2.getString("field2"));
		assertEquals(expectedRow2.getString("field3"), actualRow2.getString("field3"));
		assertEquals(expectedRow2.getString("field4"), actualRow2.getString("field4"));
	}
	
	public void testFormatAttachXml2AttachObj()
	{
		initAttachList();
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		
		List<Attachment> actualAttachList = null;
		try
		{
			actualAttachList = config.formatAttachXml2AttachObj(expectedAttachXmlString);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		assertNotNull(actualAttachList);
		
		assertEquals(2, actualAttachList.size());
		
		assertEquals(expectedAttachList.get(0).getAttachname(), actualAttachList.get(0).getName());
		assertEquals(expectedAttachList.get(0).getAttachurl(), actualAttachList.get(0).getRemark());
		assertEquals(expectedAttachList.get(1).getAttachname(), actualAttachList.get(1).getName());
		assertEquals(expectedAttachList.get(1).getAttachurl(), actualAttachList.get(1).getRemark());
	}
	
	public void testReflushConfiguration_Service()
	{
		initMethodMap();
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		Map<String, ConfigMethod> actualMethodMap = config.getMethodMap();
		
		assertNotNull(actualMethodMap);
		assertTrue(actualMethodMap.containsKey("com.ultrapower.wfinterface.core.test.TestServiceImpl"));
		
		ConfigMethod actualMethod = actualMethodMap.get("com.ultrapower.wfinterface.core.test.TestServiceImpl");
		ConfigMethod expectedMethod = methodMap.get("com.ultrapower.wfinterface.core.test.TestServiceImpl");
		
		assertEquals(expectedMethod.getMethodName(), actualMethod.getMethodName());
		assertEquals(expectedMethod.getServiceName(), actualMethod.getServiceName());
		assertEquals(expectedMethod.getBaseSchema(), actualMethod.getBaseSchema());
		assertEquals(expectedMethod.getImplClassPath(), actualMethod.getImplClassPath());
		assertEquals(expectedMethod.getOperateCode(), actualMethod.getOperateCode());
		assertEquals(expectedMethod.getOperateText(), actualMethod.getOperateText());
		
		List<ConfigField> expectedFieldList = expectedMethod.getFields();
		
		for(ConfigField expectedfield : expectedFieldList)
		{
			assertNotNull(actualMethod.getField(expectedfield.getFieldName()));
			
			ConfigField actualField = actualMethod.getField(expectedfield.getFieldName());
			
			assertEquals(expectedfield.getXmlColName(), actualField.getXmlColName());
			assertEquals(expectedfield.getDbColName(), actualField.getDbColName());
			assertEquals(expectedfield.getFieldName(), actualField.getFieldName());
			assertEquals(expectedfield.getFieldText(), actualField.getFieldText());
			assertEquals(expectedfield.getDataType(), actualField.getDataType());
			assertEquals(expectedfield.getDicType(), actualField.getDicType());
			assertEquals(expectedfield.getDicData(), actualField.getDicData());
			assertEquals(expectedfield.getLength(), actualField.getLength());
			assertEquals(expectedfield.isNull(), actualField.isNull());
			assertEquals(expectedfield.getDefaultValue(), actualField.getDefaultValue());
		}
	}
	
	public void testReflushConfiguration_Client()
	{
		initClientMap();
		IWFInterfaceConfig config = new DefaultWFInterfaceConfig();
		config.reflushConfiguration(null);
		Map<String, ConfigClient> actualClientMap = config.getClientMap();
		assertNotNull(actualClientMap);
		assertTrue(actualClientMap.containsKey("com.ultrapower.wfinterface.core.test.TestClientImpl"));
		ConfigClient actualClient = actualClientMap.get("com.ultrapower.wfinterface.core.test.TestClientImpl");
		
		ConfigClient expectedClient = clientMap.get("com.ultrapower.wfinterface.core.test.TestClientImpl");
		
		assertEquals(expectedClient.getClientName(), actualClient.getClientName());
		assertEquals(expectedClient.getMethodName(), actualClient.getMethodName());
		assertEquals(expectedClient.getImplClassPath(), actualClient.getImplClassPath());
		assertEquals(expectedClient.getServiceAddress(), actualClient.getServiceAddress());
		assertEquals(expectedClient.getServiceOperateName(), actualClient.getServiceOperateName());
		assertEquals(expectedClient.getServiceNameSpace(), actualClient.getServiceNameSpace());
		assertNotNull(actualClient.getQuerySql());
		assertNotNull(actualClient.getUpdateSql());
		
		List<ConfigCallParameter> expectedParas = expectedClient.getParameters();
		List<ConfigCallParameter> acturalParas = actualClient.getParameters();
		
		assertNotNull(acturalParas);
		assertEquals(expectedParas.size(), acturalParas.size());
		
		for(int i = 0; i < expectedParas.size(); i++)
		{
			ConfigCallParameter expectedPara = expectedParas.get(i);
			ConfigCallParameter actualPara = acturalParas.get(i);
			assertEquals(expectedPara.getName(), actualPara.getName());
			assertEquals(expectedPara.getDataType(), actualPara.getDataType());
			assertEquals(expectedPara.getType(), actualPara.getType());
			assertEquals(expectedPara.getDicData(), actualPara.getDicData());
			assertEquals(expectedPara.getMode(), actualPara.getMode());
		}
		
		List<ConfigField> expectedFieldList = expectedClient.getFields();
		
		for(ConfigField expectedfield : expectedFieldList)
		{
			assertNotNull(actualClient.getField(expectedfield.getFieldName()));
			
			ConfigField actualField = actualClient.getField(expectedfield.getFieldName());
			
			assertEquals(expectedfield.getXmlColName(), actualField.getXmlColName());
			assertEquals(expectedfield.getDbColName(), actualField.getDbColName());
			assertEquals(expectedfield.getFieldName(), actualField.getFieldName());
			assertEquals(expectedfield.getFieldText(), actualField.getFieldText());
			assertEquals(expectedfield.getDataType(), actualField.getDataType());
			assertEquals(expectedfield.getDicType(), actualField.getDicType());
			assertEquals(expectedfield.getDicData(), actualField.getDicData());
			assertEquals(expectedfield.getLength(), actualField.getLength());
			assertEquals(expectedfield.isNull(), actualField.isNull());
			assertEquals(expectedfield.getDefaultValue(), actualField.getDefaultValue());
		}
	}
	
	private void initInputDataTable()
	{
		DataRow row1 = new DataRow();
		row1.put("Test1_field1", "测试1 字段1的值<hehe /> / \\ ~!@#$%^&*()");
		row1.put("Test1_field2", 11);
		row1.put("Test1_field3", "一般");
		row1.put("Test1_field4", "2011-07-07 12:23:34");
		DataRow row2 = new DataRow();
		row2.put("Test1_field1", "测试2 字段1的值<hehe /> / \\ ~!@#$%^&*()");
		row2.put("Test1_field2", 12);
		row2.put("Test1_field3", "二般");
		row2.put("Test1_field4", "2011-07-07 21:32:43");
		expectedInputTable.putDataRow(row1);
		expectedInputTable.putDataRow(row2);
	}
	
	private void initOutputDataTable()
	{
		DataRow row1 = new DataRow();
		row1.put("field1", "测试1 字段1的值<hehe /> / \\ ~!@#$%^&*()");
		row1.put("field2", 11);
		row1.put("field3", "2");
		row1.put("field4", "1310012614");
		DataRow row2 = new DataRow();
		row2.put("field1", "测试2 字段1的值<hehe /> / \\ ~!@#$%^&*()");
		row2.put("field2", 12);
		row2.put("field3", "3");
		row2.put("field4", "1310045563");
		expectedOutputTable.putDataRow(row1);
		expectedOutputTable.putDataRow(row2);
	}
	
	private void initAttachList()
	{
		WfiTempAttachment attach1 = new WfiTempAttachment();
		attach1.setAttachname("file1");
		attach1.setAttachurl("ftp://aaa:bbb@localhost/test/file1.doc");
		expectedAttachList.add(attach1);
		WfiTempAttachment attach2 = new WfiTempAttachment();
		attach2.setAttachname("file2");
		attach2.setAttachurl("ftp://ccc:ddd@localhost/test/file2.doc");
		expectedAttachList.add(attach2);
	}
	
	private void initFieldsList()
	{
		Map<String, WorkflowField> fields1 = new HashMap<String, WorkflowField>();
		WorkflowField field1_1 = new WorkflowField("001", "field1", "测试1 字段1（字符串）", 4, "测试1 字段1的值<hehe /> / \\ ~!@#$%^&*()", false);
		fields1.put(field1_1.getFieldName(), field1_1);
		WorkflowField field1_2 = new WorkflowField("002", "field2", "测试1 字段2（数字）", 2, "11", false);
		fields1.put(field1_2.getFieldName(), field1_2);
		WorkflowField field1_3 = new WorkflowField("003", "field3", "测试1 字段3（字典）", 6, "2", false);
		fields1.put(field1_3.getFieldName(), field1_3);
		WorkflowField field1_4 = new WorkflowField("004", "field4", "测试1 字段4（时间）", 7, "1310012614", false);
		fields1.put(field1_4.getFieldName(), field1_4);
		expectedFieldsList.add(fields1);
		Map<String, WorkflowField> fields2 = new HashMap<String, WorkflowField>();
		WorkflowField field2_1 = new WorkflowField("001", "field1", "测试1 字段1（字符串）", 4, "测试2 字段1的值<hehe /> / \\ ~!@#$%^&*()", false);
		fields2.put(field2_1.getFieldName(), field2_1);
		WorkflowField field2_2 = new WorkflowField("002", "field2", "测试1 字段2（数字）", 2, "12", false);
		fields2.put(field2_2.getFieldName(), field2_2);
		WorkflowField field2_3 = new WorkflowField("003", "field3", "测试1 字段3（字典）", 6, "3", false);
		fields2.put(field2_3.getFieldName(), field2_3);
		WorkflowField field2_4 = new WorkflowField("004", "field4", "测试1 字段4（时间）", 7, "1310045563", false);
		fields2.put(field2_4.getFieldName(), field2_4);
		expectedFieldsList.add(fields2);
	}
	
	private void initMethodMap()
	{
		ConfigField field1 = new ConfigField("field1", "测试1 字段1（字符串）", "Test1_field1", "001", 4, 0, "", 500, false, "");
		ConfigField field2 = new ConfigField("field2", "测试1 字段2（数字）", "Test1_field2", "002", 2, 0, "", 100, true, "0");
		ConfigField field3 = new ConfigField("field3", "测试1 字段3（字典）", "Test1_field3", "003", 6, 2, "1:紧急;2:一般;3:二般;", 0, true, "2");
		ConfigField field4 = new ConfigField("field4", "测试1 字段4（时间）", "Test1_field4", "004", 7, 0, "", 0, true, "$sys_date$");
		List<ConfigField> fieldList = new ArrayList<ConfigField>();
		fieldList.add(field1);
		fieldList.add(field2);
		fieldList.add(field3);
		fieldList.add(field4);
//		ConfigMethod method = new ConfigMethod("testMethod", "TestService", "WF:EL_TTM_TTH", "com.ultrapower.wfinterface.core.test.TestServiceImpl", "NEXT", "下一步", fieldList);
//		methodMap.put("com.ultrapower.wfinterface.core.test.TestServiceImpl", method);
	}
	
	private void initClientMap()
	{
		ConfigField field1 = new ConfigField("field1", "测试1 字段1（字符串）", "Test1_field1", "001", 4, 0, "", 500, false, "");
		ConfigField field2 = new ConfigField("field2", "测试1 字段2（数字）", "Test1_field2", "002", 2, 0, "", 100, true, "0");
		ConfigField field3 = new ConfigField("field3", "测试1 字段3（字典）", "Test1_field3", "003", 6, 2, "1:紧急;2:一般;3:二般;", 0, true, "2");
		ConfigField field4 = new ConfigField("field4", "测试1 字段4（时间）", "Test1_field4", "004", 7, 0, "", 0, true, "$sys_date$");
		List<ConfigField> fieldList = new ArrayList<ConfigField>();
		fieldList.add(field1);
		fieldList.add(field2);
		fieldList.add(field3);
		fieldList.add(field4);
		List<ConfigCallParameter> paras = new ArrayList<ConfigCallParameter>();
		ConfigCallParameter para1 = new ConfigCallParameter("serSupplier", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para2 = new ConfigCallParameter("serCaller", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para3 = new ConfigCallParameter("callerPwd", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para4 = new ConfigCallParameter("callTime", 7, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para5 = new ConfigCallParameter("field5", 6, ConfigCallParameter.toType("INT"), "紧急:1;一般:2;二般:3;", ConfigCallParameter.toMode("IN"));
		ConfigCallParameter para6 = new ConfigCallParameter("opDetail", 4, ConfigCallParameter.toType("STRING"), "", ConfigCallParameter.toMode("IN"));
		paras.add(para1);paras.add(para2);paras.add(para3);paras.add(para4);paras.add(para5);paras.add(para6);
		ConfigClient method = new ConfigClient("testMethod", "TestClient", "com.ultrapower.wfinterface.core.test.TestClientImpl", "http://localhost:8010/eoms4/services/TestService", "test", "", "querySql", "updateSql", "exceptionSql", paras, fieldList);
		clientMap.put("com.ultrapower.wfinterface.core.test.TestClientImpl", method);
	}
}
