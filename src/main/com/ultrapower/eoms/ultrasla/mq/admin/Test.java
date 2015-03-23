package com.ultrapower.eoms.ultrasla.mq.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;

/**
 * @author RenChenglin
 * @date 2012-1-6 下午03:19:33
 * @version
 */
public class Test {

	public void test()
	{
		try {
			JMXServiceURL url
				= new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:60006/jmxrmi");
			JMXConnector connector = JMXConnectorFactory.connect(url,null);
			connector.connect();
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			ObjectName name = new ObjectName("eoms4BrokerM:BrokerName=eoms4Broker,Type=Broker");
			BrokerViewMBean mbean = 
			(BrokerViewMBean)MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
			System.out.println("borker id:"+mbean.getBrokerId());
			System.out.println("borker name:"+mbean.getBrokerName());
//			Object obj = WebApplicationManager.getBean("eoms4Broker");
//			System.out.println(obj);
			System.out.println("total message count:"+mbean.getTotalMessageCount());
			System.out.println("total number of consumers:"+mbean.getTotalConsumerCount());
			System.out.println("total number of queues:"+mbean.getQueues().length);
			System.out.println("---------------------------------------------------");
			ObjectName[] on = mbean.getQueues();
			if(on!=null)
			{
				int len = on.length;
				ObjectName ton;
				QueueViewMBean qvb;
				for(int i=0;i<len;i++)
				{
					ton = on[i];
					if(ton!=null)
					{
						qvb = (QueueViewMBean)MBeanServerInvocationHandler.newProxyInstance(connection, ton, QueueViewMBean.class, true);
						System.out.println("queue name:"+qvb.getName());
						System.out.println("queue size:"+qvb.getQueueSize());
						System.out.println("queue consumers:"+qvb.getConsumerCount());
					}
				}
			}
			mbean = null;
			connection = null;
			connector.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		};
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "a中文";
		try {
			s = new String(s.getBytes("iso8859-1"),"iso8859-1");
			System.out.println(new String(s.getBytes("utf-8"),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
