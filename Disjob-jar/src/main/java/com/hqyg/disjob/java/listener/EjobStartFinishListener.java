package com.hqyg.disjob.java.listener;

import com.hqyg.disjob.event.ObjectEvent;
import com.hqyg.disjob.event.ObjectListener;
import com.hqyg.disjob.java.bean.StartUpConfig;
import com.hqyg.disjob.java.event.EjobStartUp;
import com.hqyg.disjob.java.event.EventType;

public class EjobStartFinishListener implements ObjectListener<StartUpConfig> {
	
	private EjobStartUp ejobStartUp ;
	
	public EjobStartFinishListener(EjobStartUp ejobStartUp) {
		this.ejobStartUp = ejobStartUp ;
	}
	
	public void onEvent(ObjectEvent<StartUpConfig> event) {
		this.ejobStartUp.setCurrentState(EjobStartUp.StartUpState.STARTUP_FINISH);
		this.ejobStartUp.removeListener(EventType.START_FINISH);
		this.ejobStartUp.removeListener(EventType.START_UP);
	}
}
