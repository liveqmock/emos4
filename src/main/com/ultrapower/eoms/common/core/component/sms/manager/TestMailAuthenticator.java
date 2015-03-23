package com.ultrapower.eoms.common.core.component.sms.manager;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class TestMailAuthenticator extends Authenticator {
	private String user;
    private String pwd;
 
    public TestMailAuthenticator(String user, String pwd) {  
        this.user = user;
        this.pwd = pwd;
    }  
 
    @Override 
    protected PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(user, pwd);
    }  
}
