package com.ultrapower.eoms.common.startup;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;

/**
 * 获取WebApplicationContext对象
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 22, 2010 6:19:20 PM
 */
public class WebApplicationListener implements ServletContextListener{


	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	
	/**
	 * 获取ServletContext对象
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext=sce.getServletContext();
		WebApplicationManager.servletContext=servletContext;
		Initialization initial = new Initialization();
		initial.setServletContext(servletContext);
		initial.init();
	}

}
