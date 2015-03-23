package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.LinkedList;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;

/**
 * 用来存放待发送短信的短信队列(暂时没有在用)
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-7-30 下午03:21:42
 */
public class SmQueue {

	private LinkedList<Smsmonitor> smlist = new LinkedList<Smsmonitor>();
	private static final int initQueueCapacity = 1000;//队列初始化大小
	
	/**
	 * 入列
	 * @param smsmonitor
	 */
	public synchronized void enQueue(Smsmonitor smsmonitor){
		if(smlist.size()>=initQueueCapacity){//队列已经满了
			try{
				wait();
			}catch(InterruptedException e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			}catch(Exception e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			smlist.add(smsmonitor);
			notifyAll();
		}
	}
	
	/**
	 * 出列
	 * @return
	 */
	public synchronized Smsmonitor deQueue(){
		Smsmonitor smsmonitor = null;
		if(smlist.isEmpty()){
			try{
				wait();
			}catch (InterruptedException e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			}catch(Exception e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			smsmonitor = smlist.removeFirst();
			notifyAll();
		}
		return smsmonitor;
	}
}
