package com.hqyg.ejob.java.app;

import java.util.Date;

import com.hqyg.disjob.java.bean.SchedulerParam;
import com.hqyg.disjob.java.core.annotation.JobDec;
import com.hqyg.disjob.java.job.EJob;
import com.hqyg.disjob.java.utils.DebugInfoPrintUtil;
import com.hqyg.disjob.java.utils.TimeUtils;
import com.hqyg.disjob.quence.TaskExecuteException;

@JobDec(group="cronJob_1",jobName="fireNowJobTemp_0",quartz="0/12 * * * * ?",fireNow=true)
public class FireNowJob_0 implements EJob{

	@Override
	public void beforeExecute(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void execute(SchedulerParam schedulerParam)
			throws TaskExecuteException {
		DebugInfoPrintUtil.debug("D:/cron_firenow.log", this.getClass().getName()+"; at time:"+TimeUtils.getFormat(new Date(), TimeUtils.YYYY_MM_DD_HH_MM_SS));
	}

	@Override
	public void executeSuccess(SchedulerParam schedulerParam) {
		
	}

	@Override
	public void executeFail(SchedulerParam schedulerParam) {
		
	}

}
