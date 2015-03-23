package com.ultrapower.wfinterface.core.test;

import com.ultrapower.wfinterface.core.util.ConfigUtil;

import junit.framework.TestCase;

public class ConfigUtilTest extends TestCase
{

	public void testGetProperty() throws Exception
	{
		String actualValue = ConfigUtil.getProperty("InterSwitchAlarm_T1_actorname");
		assertEquals("zhaoqiang", actualValue);
	}
}
