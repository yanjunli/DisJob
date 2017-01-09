package com.hqyg.disjob.java.listener;

import java.util.concurrent.CountDownLatch;
import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.event.ObjectListener;
import com.hqyg.disjob.java.bean.StartUpConfig;
import com.hqyg.disjob.java.service.StartUpService;
import com.hqyg.disjob.java.utils.Log;

public class EjobInitServerListener implements ObjectListener<StartUpConfig>{

	public void onEvent(ObjectEvent<StartUpConfig> event) {
		if(StartUpService.isInitSuccess == false){
			return ;
		}
		CountDownLatch countDownLatch = StartUpService.initExecutorServer();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.error(e);
			StartUpService.isInitSuccess = false;
			return ;
		}
		StartUpService.initCurator();
	}
}
