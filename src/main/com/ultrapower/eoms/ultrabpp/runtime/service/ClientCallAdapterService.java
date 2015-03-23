package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.Map;

public interface ClientCallAdapterService
{
	public String call(String serviceName, String methodName, Map<String, String> parameters);
}
