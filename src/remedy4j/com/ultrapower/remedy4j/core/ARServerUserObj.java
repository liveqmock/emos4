package com.ultrapower.remedy4j.core;

import java.util.HashMap;
import java.util.Map;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.SignalType;

public class ARServerUserObj
{
	private ARServerUser server;
	private boolean isLogin = false;
	
	public ARServerUserObj(ARServerUser _server)
	{
		server = _server;
	}
	
	public void login() throws ARException
	{
		server.login();
		isLogin = true;
	}
	
	public void logout()
	{
		server.logout();
		isLogin = false;
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		if(isLogin)
		{
			server.logout();
		}
		super.finalize();
	}
	
	public ARServerUser getServer()
	{
		return server;
	}
	public void setServer(ARServerUser server)
	{
		this.server = server;
	}

	public boolean isLogin()
	{
		return isLogin;
	}

	public void setLogin(boolean isLogin)
	{
		this.isLogin = isLogin;
	}
}
