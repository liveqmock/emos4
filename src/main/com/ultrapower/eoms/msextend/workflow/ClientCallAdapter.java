package com.ultrapower.eoms.msextend.workflow;

import java.lang.reflect.Method;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.runtime.service.ClientCallAdapterService;

public class ClientCallAdapter implements ClientCallAdapterService{

	public String call(String serviceName, String methodName, Map<String, String> parameters) {
		String result = null;
		try{
			Class<?> clazz = this.getClass();
			Method method = clazz.getDeclaredMethod(methodName,Map.class);
			result = (String) method.invoke(this, parameters);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}
}
