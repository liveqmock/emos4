package com.ultrapower.eoms.ultrasla.mq.admin.manager;

import java.io.IOException;
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

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.ultrasla.mq.admin.service.MqMonitorService;
import com.ultrapower.eoms.ultrasla.mq.admin.util.MqRuntimeInfo;

/**
 * @author RenChenglin
 * @date 2012-1-10 上午10:11:07
 * @version
 */
public class MqMonitorManager implements MqMonitorService {

	public MqRuntimeInfo getMqRuntimeInfo() {
		MqRuntimeInfo info = new MqRuntimeInfo();
		try {
			JMXServiceURL url
				= new JMXServiceURL(PropertiesUtils.getProperty("sla.mq.jmx.serviceURL"));
			JMXConnector connector = JMXConnectorFactory.connect(url,null);
			connector.connect();
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			ObjectName name = new ObjectName("eoms4BrokerM:BrokerName=eoms4Broker,Type=Broker");
			BrokerViewMBean mbean = 
			(BrokerViewMBean)MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
			info.setBrokerId(mbean.getBrokerId());
			info.setBrokerName(mbean.getBrokerName());
//			info.setRemainMsgCount(mbean.getTotalMessageCount());
//			Object obj = WebApplicationManager.getBean("eoms4Broker");
//			System.out.println(obj);
			
//			System.out.println("total message count:"+mbean.getTotalMessageCount());
//			System.out.println("total number of consumers:"+mbean.getTotalConsumerCount());
//			System.out.println("total number of queues:"+mbean.getQueues().length);
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
						if("workflow.event".equals(qvb.getName()))
						{
							info.setRemainMsgCount(qvb.getQueueSize());
							break;
						}
//						System.out.println("queue name:"+qvb.getName());
//						System.out.println("queue size:"+qvb.getQueueSize());
//						System.out.println("queue consumers:"+qvb.getConsumerCount());
					}
				}
			}
			mbean = null;
			connection = null;
			connector.close();
			connector = null;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		};
		return info;
	}

	public String mqEventTest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
