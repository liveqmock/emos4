package com.ultrapower.eoms.msextend.change.manager;

import com.hp.ucmdb.api.ClientContext;
import com.hp.ucmdb.api.Credentials;
import com.hp.ucmdb.api.UcmdbService;
import com.hp.ucmdb.api.UcmdbServiceFactory;
import com.hp.ucmdb.api.UcmdbServiceProvider;

public class CreateUCMDBConnection {

	  public static String HOST_NAME = "192.168.189.130";
	  public static int PORT = 8080;
	 
	 
	 public static UcmdbService createSDKConnection() {
		UcmdbServiceProvider provider;
		try {
			provider = UcmdbServiceFactory.getServiceProvider(HOST_NAME, PORT);
		} catch (Exception e) {
			throw new RuntimeException("连接UCMDB失败",e);
		}
		final String USERNAME = "admin";
		final String PASSWORD = "admin";
		
		Credentials credentials = provider.createCredentials(USERNAME, PASSWORD);
		ClientContext clientContext = provider.createClientContext("ITSM");
		return provider.connect(credentials, clientContext);
		
	}
}
