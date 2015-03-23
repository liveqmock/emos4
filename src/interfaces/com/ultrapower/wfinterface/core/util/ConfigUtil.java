package com.ultrapower.wfinterface.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 用于读取接口的自定义配置文件，文件路径在classes/resources/wfinterface/wfinterface-config.properties<p />
 * 使用方法：
 * <pre>ConfigUtil.getProperty(String name)</pre>
 * @author BigMouse
 *
 */
public class ConfigUtil
{
	private static Properties props = null;
	
	/**
	 * 获取配置文件中配置的属性值
	 * @param name 属性key
	 * @return 属性值
	 * @throws Exception 配置文件读取失败
	 */
	public static String getProperty(String name) throws Exception
	{
		String result = null;
		if(props == null)
		{
			new ConfigUtil();
		}
		result = props.getProperty(name);
		return result;
	}
	
	private ConfigUtil() throws Exception
	{
		String configXmlPath = this.getClass().getResource("").getPath();
		configXmlPath = configXmlPath.substring(0, configXmlPath.length() - 37) + "wfinterface";
		FileInputStream propsIS = new FileInputStream(configXmlPath + File.separator + "wfinterface-config.properties");
		props = new Properties();
		props.load(propsIS);
	}
}
