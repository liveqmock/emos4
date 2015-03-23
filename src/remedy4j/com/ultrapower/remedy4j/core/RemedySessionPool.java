package com.ultrapower.remedy4j.core;

import com.bmc.arsys.api.ARServerUser;
import com.ultrapower.remedy4j.core.UtilInforHandler.SessionType;

/**
 * Remedy登录对象的管理类
 * @author BigMouse
 * @version UltraPower-EOMS v4.0
 * @since jdk1.5
 */
public class RemedySessionPool
{
	/**
	 * 对于"THREAD"时Remedy登录对象的管理
	 */
	private static ThreadLocal<ARServerUserObj> sessionTL = new ThreadLocal<ARServerUserObj>();

	/**
	 * 对于"GLOBAL"和"THREAD"时Remedy登录对象的管理
	 */
	private static ARServerUserObj sessionGlobal = null;
	
	public static boolean SessionGlobalThreadLoginStatus = false;
	
	/**
	 * 获取Remedy登录对象
	 * @return Remedy登录对象
	 */
	public static ARServerUserObj getSession()
	{
		ARServerUserObj session = null;
		
		if(RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.THREAD))
		{
			session = sessionTL.get();
			if(session == null)
			{
				ARServerUser server = new ARServerUser();
				sessionTL.set(new ARServerUserObj(server));
				session = sessionTL.get();
			}
		}
		else if(RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.GLOBAL))
		{
			session = sessionGlobal;
			if(session == null)
			{
				ARServerUser server = new ARServerUser();
				sessionGlobal = new ARServerUserObj(server);
				session = sessionGlobal;
			}
		}
		else
		{
			ARServerUser server = new ARServerUser();
			session = new ARServerUserObj(server);
		}
		
		return session;
	}
	
	/**
	 * 移除session对象池中的对象
	 * @param session
	 */
	public static void removeSession()
	{
		if(RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.THREAD))
		{
			sessionTL.remove();
		}
	}
	
	/**
	 * 终止AR连接
	 */
	public static void terminate()
	{
		
		if(RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.THREAD))
		{
			if(sessionTL != null)
			{
				if(SessionGlobalThreadLoginStatus)
				{
					sessionTL.get().getServer().logout();
					sessionTL.get().setLogin(false);
					SessionGlobalThreadLoginStatus = false;
				}
				sessionTL.remove();
			}
		}
		else if(RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.GLOBAL))
		{
			if(sessionGlobal != null)
			{
				if(SessionGlobalThreadLoginStatus)
				{
					sessionGlobal.getServer().logout();
					sessionGlobal.setLogin(false);
					SessionGlobalThreadLoginStatus = false;
				}
				sessionGlobal = null;
			}
		}
	}
}
