package com.ultrapower.eoms.common.core.component.sms.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.service.SendService;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2011-6-17 下午05:39:32
 * @descibe 天津短信发送网管
 */
public class SendSmByExe implements SendService{

	public String SendSm(String pid, String mobile, String content) {
		String flag = "false";
		String[] arg = new String[7];
		arg[0] = SmsProxyPara.executeclass;//调用命令
		arg[1] = SmsProxyPara.smsip;//eoms方ip
		arg[2] = SmsProxyPara.smsport;//eoms方port
		arg[3] = "1";//0不回复 1 回复
		arg[4] = pid;
		arg[5] = mobile;
		arg[6] = content;
		Runtime rt = Runtime.getRuntime();
		BufferedReader bufferedReader = null;
		try{
			StringBuffer result=new StringBuffer();
			Process ps=rt.exec(arg);
			bufferedReader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{ 
				result.append(line);
			}
			//System.out.print("调用发送短信返回值为： "+result.toString());
			RecordLog.printLog("调用发送短信返回值为： "+result.toString(),RecordLog.LOG_LEVEL_INFO);
			flag = "true";
			//RecordLog.printLog("信息发送成功：pid="+pid+",手机号码="+mobile, RecordLog.LOG_LEVEL_INFO);
		}catch(Exception e){
			e.printStackTrace();
			//RecordLog.printLog("信息发送失败：pid="+pid+",手机号码="+mobile, RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

}
