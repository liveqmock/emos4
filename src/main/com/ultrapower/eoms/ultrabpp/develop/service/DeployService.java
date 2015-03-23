package com.ultrapower.eoms.ultrabpp.develop.service;

public interface DeployService {

	public String prevMain(String baseSchema);

	public String prevAction(String baseSchema, String stepNo, String actionName, String baseStatus, String actionType);

	public void depMain(String baseSchema, boolean isForce);

	public void depAction(String baseSchema, String stepNo, String actionName, String baseStatus, String actionType);
	
	public void depAll(String baseSchema);
}
