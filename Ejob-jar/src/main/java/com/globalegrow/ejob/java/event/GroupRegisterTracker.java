package com.hqyg.disjob.java.event;

import com.hqyg.disjob.java.ExecutorBuilder;
import com.hqyg.disjob.java.job.RegisterEJobAction;
import com.hqyg.disjob.quence.ActionQueue;
import com.hqyg.disjob.quence.BaseActionQueue;

public class GroupRegisterTracker {

	private ActionQueue groupRegisterQueue = new BaseActionQueue(ExecutorBuilder.getJobExecutor());
	
	public void enqueue(RegisterEJobAction registerEJobAction){
		groupRegisterQueue.enqueue(registerEJobAction);
	}
}
