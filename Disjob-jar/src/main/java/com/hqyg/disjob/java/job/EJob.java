package com.hqyg.disjob.java.job;

import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.quence.TaskExecuteException;


public interface EJob {
	public void beforeExecute(SchedulerParam schedulerParam) ;
	
	public void execute(SchedulerParam schedulerParam) throws TaskExecuteException;
	
	public void executeSuccess(SchedulerParam schedulerParam);
	
	public void executeFail(SchedulerParam schedulerParam);
}
