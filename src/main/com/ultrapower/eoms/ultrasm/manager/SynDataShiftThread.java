package com.ultrapower.eoms.ultrasm.manager;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.service.DataShiftService;

public class SynDataShiftThread extends Thread
{
	private DataShiftService dataShiftService = (DataShiftService) WebApplicationManager.getBean("dataShiftService");
	public boolean isRun = true;
	public void run() {
		//dataShiftService =(DataShiftService)WebApplicationManager.getBean("dataShiftService");
		System.out.println("-->开始同步更新");
		while (isRun)
		{
			dataShiftService.updateByMidTable();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		SynDataShiftThread synDataShiftThread=new SynDataShiftThread();
		synDataShiftThread.start();
		int a=10;
		a+=10;
		System.out.println("ok");
	}
}
