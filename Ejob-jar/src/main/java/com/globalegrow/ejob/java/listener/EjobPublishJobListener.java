package com.hqyg.disjob.java.listener;

import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.event.ObjectListener;
import com.hqyg.disjob.java.EjobConstants;
import com.hqyg.disjob.java.bean.StartUpConfig;
import com.hqyg.disjob.java.service.StartUpService;

public class EjobPublishJobListener implements ObjectListener<StartUpConfig>{

	public void onEvent(ObjectEvent<StartUpConfig> event) {
		if(StartUpService.isInitSuccess == false){
			return ;
		}
		
		if(event.getValue().getType() != EjobConstants.StartUpType.SPRING_START_UP){
			StartUpService.initJobScanner();//不是spring 的需要扫描 job 所在的包发布job
		}
	}

}
