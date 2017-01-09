package com.hqyg.disjob.java.event;

import com.hqyg.disjob.event.AbstractEventObject;
import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.java.listener.DynamicJobListener;

public class DynamicJobStartUp extends AbstractEventObject<String>{
	
	@Override
	public void attachListener() {
		
		this.addListener(new DynamicJobListener(),EventType.DYNAMIC_JOB);
	}
	
	public void notifyDynamicJob(String path){
		ObjectEvent<String> startDynamicEvent = new ObjectEvent<String>(path,EventType.DYNAMIC_JOB);
		notifyListeners(startDynamicEvent);
	}
}
