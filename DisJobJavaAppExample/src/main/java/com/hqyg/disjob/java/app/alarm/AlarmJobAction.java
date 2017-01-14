package com.hqyg.disjob.java.app.alarm;

import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.core.annotation.JobDec;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.quence.TaskExecuteException;

@JobDec(group="alarm",jobName="alarmJob1",quartz="0/10 * * * * ?",fireNow=true)
public class AlarmJobAction implements EJob{

	@Override
	public void beforeExecute(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void execute(SchedulerParam schedulerParam)throws TaskExecuteException {
		
		System.err.println("alarm----->");
	}

	@Override
	public void executeSuccess(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void executeFail(SchedulerParam schedulerParam) {
		
	}

}
