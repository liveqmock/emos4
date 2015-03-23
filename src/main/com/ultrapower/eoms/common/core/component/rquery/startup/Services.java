package com.ultrapower.eoms.common.core.component.rquery.startup;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;


public class Services implements ServletContextListener{
	
	public void contextInitialized(ServletContextEvent ctxEvt)
	{ 
		String path=ctxEvt.getServletContext().getRealPath("");
		//String str2=getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		//String str=getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
		//String str=getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1).split("classes")[0];
		//RConstants.xmlPath=str+"classes"+File.separatorChar+"sqlconfig";
		RConstants.xmlPath=path+File.separator+"WEB-INF"+File.separator+"classes"+File.separatorChar+"sqlconfig";
		//RConstants.xmlPath=ctxEvt.getServletContext().getRealPath("/");
		//ctxEvt.getClass().get
		StartUp.loadFile(RConstants.xmlPath);
		
	}
	public void contextDestroyed(ServletContextEvent ctxEvt) 
	{ 
		
	}

}
