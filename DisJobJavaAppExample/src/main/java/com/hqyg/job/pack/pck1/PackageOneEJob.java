package com.hqyg.job.pack.pck1;

import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.core.annotation.JobDec;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.java.utils.TimeUtils;
import com.hqyg.disjob.quence.TaskExecuteException;

@JobDec(group="packagesOne",jobName="packageOneEJob",quartz="0/5 * * * * ?",fireNow=true)
public class PackageOneEJob implements EJob{

	@Override
	public void beforeExecute(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void execute(SchedulerParam schedulerParam)
			throws TaskExecuteException {
		System.out.println(this.getClass().getName()+"; at time:"+TimeUtils.getFormatNow());
	}

	@Override
	public void executeSuccess(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void executeFail(SchedulerParam schedulerParam) {
		
	}

}
