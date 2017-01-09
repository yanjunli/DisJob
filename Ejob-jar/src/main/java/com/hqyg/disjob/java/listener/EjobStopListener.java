package com.hqyg.disjob.java.listener;

import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.event.ObjectListener;
import com.hqyg.disjob.java.ExecutorBuilder;
import com.hqyg.disjob.java.bean.StartUpConfig;
import com.hqyg.disjob.java.event.EjobStartUp;
import com.hqyg.disjob.java.service.EjobConfigService;

public class EjobStopListener implements ObjectListener<StartUpConfig>{
	
	private EjobStartUp ejobStartUp ;
	public EjobStopListener(EjobStartUp ejobStartUp) {
		this.ejobStartUp = ejobStartUp ;
	}
	public void onEvent(ObjectEvent<StartUpConfig> event) {
		EjobConfigService.destory();
		ExecutorBuilder.getJobExecutor().stop();
		this.ejobStartUp.clearListener();
	}
}
