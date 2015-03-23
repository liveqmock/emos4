package com.ultrapower.eoms.common.startup;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * UserCounterListener class used to count the current number of active users
 * for the applications. Does this by counting how many user objects are stuffed
 * into the session. It Also grabs these users and exposes them in the servlet
 * context.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class SessionListener implements ServletContextListener,
		HttpSessionAttributeListener {
	/**
	 * Name of user counter variable
	 */
	public static final String COUNT_KEY = "userCounter";

	/**
	 * Name of users Set in the ServletContext
	 */
	public static final String USERS_KEY = "userNames";

	public static final String SESSION_CACHENAME="useronline";
	
	private transient ServletContext servletContext;

	private int counter;

	private Set<UserSession> users;

	
	
	/**
	 * Initialize the context
	 * 
	 * @param sce
	 *            the event
	 */
	public synchronized void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		servletContext.setAttribute((COUNT_KEY), Integer.toString(counter));
	}

	/**
	 * Set the servletContext, users and counter to null
	 * 
	 * @param event
	 *            The servletContextEvent
	 */
	public synchronized void contextDestroyed(ServletContextEvent event) {
		servletContext = null;
		users = null;
		counter = 0;
	}

	synchronized void incrementUserCounter() {
		counter = Integer.parseInt((String) servletContext
				.getAttribute(COUNT_KEY));
		counter++;
		servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));
	}

	synchronized void decrementUserCounter() {
		int counter = Integer.parseInt((String) servletContext
				.getAttribute(COUNT_KEY
						));
		counter--;

		if (counter < 0) {
			counter = 0;
		}

		servletContext.setAttribute(COUNT_KEY, Integer.toString(counter));
	}

	@SuppressWarnings("unchecked")
	synchronized void addUsername(UserSession user) {
/*		users = (Set) servletContext.getAttribute(USERS_KEY);

		if (users == null) {
			users = new LinkedHashSet<UserSession>();
		}
		users.add(user);
		servletContext.setAttribute(USERS_KEY, users);
*/		
		BaseCacheManager.put(SESSION_CACHENAME, user.getLoginName(), user);
		
		//incrementUserCounter();
	}

	synchronized void removeUsername(UserSession user) {
/*		users = (Set) servletContext.getAttribute(USERS_KEY);

		if (users != null) {
			users.remove(user);
		}

		servletContext.setAttribute(USERS_KEY, users);*/
		//BaseCacheManager.put("online", user.getLoginName(), user);
		
		BaseCacheManager.removeElement(SESSION_CACHENAME, user.getLoginName());
		//decrementUserCounter();
	}

	/**
	 * This method is designed to catch when user's login and record their name
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
	 * @param event
	 *            the event to process
	 */
	public void attributeAdded(HttpSessionBindingEvent event) {
		if (event.getSession().getAttribute("userSession") != null
				&& event.getValue() instanceof UserSession) {
			UserSession userSession = (UserSession) event.getSession().getAttribute("userSession");
			addUsername(userSession);

		}
	}

	/**
	 * When user's logout, remove their name from the hashMap
	 * 
	 * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
	 * @param event
	 *            the session binding event
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) {
		Object obj = event.getValue();
		if (obj instanceof UserSession) {
			UserSession sessionBean = (UserSession) obj;
			removeUsername(sessionBean);
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void sessionCreated(HttpSessionEvent se) {
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
	}

}
