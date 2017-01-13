package com.hqyg.job.pack.pck2;

import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.core.annotation.JobDec;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.java.utils.TimeUtils;
import com.hqyg.disjob.quence.TaskExecuteException;

@JobDec(group="packagesOne",jobName="packageTwo_1Job",quartz="0 0/1 * * * ?")
public class PackageTwo_1Job implements EJob{

	@Override
	public void beforeExecute(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void execute(SchedulerParam schedulerParam)throws TaskExecuteException {
		System.err.println(this.getClass().getName()+"; execute at time:"+TimeUtils.getFormatNow());
	}

	@Override
	public void executeSuccess(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void executeFail(SchedulerParam schedulerParam) {
		
	}

}
